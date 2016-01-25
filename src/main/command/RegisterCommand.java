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
 * Class implements functionality of registering user.
 */
public class RegisterCommand implements Command {
    /**
     * Names of request parameters.
     */
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first_name";
    private static final String SECOND_NAME = "second_name";
    private static final String ADDRESS = "address";
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
        String page;
        boolean redirect = false;
        bundle = RequestHelper.getResourceBundle(request);

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String secondName = request.getParameter(SECOND_NAME);
        String address = request.getParameter(ADDRESS);


        String error = bundle.getString("command.register.error.general");

        try{
            User user = userManager.get(email);

            if (user == null){
                User newUser = userManager.create(firstName, secondName, address, email, password);
                if (newUser.getId() != null){
                    request.setAttribute("message", bundle.getString("command.register.error.success"));
                    page = "index.jsp";
                    return new Page(page, redirect);
                }
                else{
                    error += bundle.getString("command.error.internal");
                }
            }
            else{
                error += bundle.getString("command.register.error.user_exist");
            }
        } catch (PersistException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
            error += bundle.getString("command.error.try_again");
        }

        request.setAttribute("message", error);
        page = "index.jsp";
        return new Page(page, redirect);
    }
}
