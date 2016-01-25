package main.command;

import main.dao.PersistException;
import main.entities.Publication;
import main.entities.User;
import main.helper.Page;
import main.helper.RequestHelper;
import main.manager.PublicationManager;
import main.manager.UserManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Class that encapsulates business logic.
 * Class implements functionality of adding publication.
 */
public class AddPublicationCommand implements Command{
    /**
     * Names of request parameters.
     */
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    /**
     * Manager to work with publications in persistent context.
     */
    PublicationManager publicationManager = new PublicationManager();
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "index.jsp";
        boolean redirect = false;
        bundle = RequestHelper.getResourceBundle(request);

        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        Float price = Float.valueOf(request.getParameter(PRICE));

        String error = bundle.getString("command.add_publication.error.general");

        try{
            Publication publication = publicationManager.create(name, description, price);
            if (publication != null) {
                page = "admin.jsp";
                redirect = true;
                Logger.getLogger(this.getClass()).info("User: " + publication.getName() + " " +
                        "(id: " + publication.getId() + ") has successfully added.");
            }
            else {
                error += bundle.getString("command.error.internal");
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
