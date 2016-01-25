package main.command;

import main.dao.PersistException;
import main.entities.Subscription;
import main.entities.SubscriptionPart;
import main.entities.User;
import main.helper.Page;
import main.helper.RequestHelper;
import main.manager.SubscriptionManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Class that encapsulates business logic.
 * Class implements functionality of extending duration of user's subscription.
 */
public class ExtendSubscriptionCommand implements Command {
    /**
     * Names of request parameters.
     */
    private final static String SUBSCRIPTION_ID = "id";
    private final static String DURATION = "duration";
    /**
     * Manager to work with subscriptions in persistent context.
     */
    SubscriptionManager subscriptionManager = new SubscriptionManager();
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "profile.jsp";
        boolean redirect = true;
        bundle = RequestHelper.getResourceBundle(request);
        int duration = Integer.valueOf(request.getParameter(DURATION));
        int current = Integer.valueOf(request.getParameter(SUBSCRIPTION_ID));
        Date currentDate = new Date();
        try {
            User currentUser = (User) request.getSession().getAttribute("currentUser");
            Subscription subscription = currentUser.getSubscriptions().get(current);
            subscription.setStartDate(currentDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + duration));
            subscription.setEndDate(cal.getTime());
            subscriptionManager.update(subscription);
            subscription.setUser(currentUser);
        } catch (PersistException e) {
            Logger.getLogger(getClass()).error(e);
            page = "index.jsp";
            redirect = false;
            request.setAttribute("message", bundle.getString("command.extend_subscription.error"));
        }

        return new Page(page, redirect);
    }
}
