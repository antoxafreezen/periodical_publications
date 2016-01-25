package main.command;

import main.dao.PersistException;
import main.entities.User;
import main.helper.Page;
import main.helper.RequestHelper;
import main.manager.UserManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Class that encapsulates business logic.
 * Class implements functionality of logging user.
 */
public class LoginCommand implements Command {
    /**
     * Names of request parameters.
     */
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    /**
     * Manager to work with users in persistent context.
     */
    UserManager userManager = new UserManager();
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "index.jsp";
        boolean redirect = false;
        bundle = RequestHelper.getResourceBundle(request);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        String error = bundle.getString("command.login.error.general");

        try{
            User user = userManager.login(email,password);
            if (user != null){
                request.getSession().setAttribute("currentUser", user);
                if (user.isAdmin()){
                    page = "admin.jsp";
                    redirect = true;
                }else {
                    page = "publications.jsp";
                    redirect = true;
                }

                Logger.getLogger(this.getClass()).info("User: " + user.getFirst_name() + " " +
                                user.getSecond_name() + "(id: " + user.getId() + ") has successfully logged in.");
            }
            else {
                error += bundle.getString("command.login.error.check");
                request.setAttribute("message", error);
            }
        } catch (PersistException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
            error += bundle.getString("command.error.try_again");
            request.setAttribute("message", error);
        }

        return new Page(page, redirect);
    }
}
