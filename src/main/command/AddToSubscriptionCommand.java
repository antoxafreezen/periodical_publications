package main.command;

import main.dao.PersistException;
import main.entities.Publication;
import main.entities.Subscription;
import main.entities.SubscriptionPart;
import main.entities.User;
import main.helper.Page;
import main.helper.RequestHelper;
import main.manager.PublicationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Class that encapsulates business logic.
 * Class implements functionality of adding publication to subscription.
 */
public class AddToSubscriptionCommand implements Command {
    /**
     * Names of request parameter.
     */
    private static final String PUBLICATION_ID = "publicationId";
    /**
     * Manager to work with publications in persistent context.
     */
    private PublicationManager publicationManager = new PublicationManager();
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;
    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "subscription.jsp";
        boolean redirect = true;
        bundle = RequestHelper.getResourceBundle(request);
        User user = (User) request.getSession().getAttribute("currentUser");
        Subscription subscription = (Subscription) request.getSession().getAttribute("currentSubscription");
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setUser(user);
        }
        String selected[] = request.getParameterValues(PUBLICATION_ID);
        try{
            if (selected != null && selected.length != 0) {
                float subscriptionPrice = 0;
                for (int i = 0; i < selected.length; i++) {
                    SubscriptionPart newPart = new SubscriptionPart();
                    Publication publication = publicationManager.get(Integer.valueOf(selected[i]));
                    newPart.setPublication(publication);
                    newPart.setSubscription(subscription);
                    subscription.addSubscriptionPart(newPart);
                    subscriptionPrice = subscriptionPrice + publication.getPrice();
                }
                subscription.setPrice(subscriptionPrice);
                user.addSubscription(subscription);
                request.getSession().setAttribute("currentUser", user);
                request.getSession().setAttribute("currentSubscription", subscription);
            }
            else {
                return new Page("publications.jsp", true);
            }
        }catch (PersistException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
            String error = bundle.getString("command.add_to_subscription.error");
            request.setAttribute("message", error);
            page = "index.jsp";
        }

        return new Page(page, redirect);
    }
}
