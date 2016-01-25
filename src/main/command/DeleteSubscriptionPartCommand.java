package main.command;

import main.entities.Subscription;
import main.entities.SubscriptionPart;
import main.helper.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that encapsulates business logic.
 * Class implements functionality of deleting publication from user's subscription.
 */
public class DeleteSubscriptionPartCommand implements Command {
    /**
     * Names of request parameters.
     */
    private final static String SUBS_PART_ID = "id";

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "subscription.jsp";
        boolean redirect = true;

        int subsPartPosition = Integer.valueOf(request.getParameter(SUBS_PART_ID));

        Subscription subscription = (Subscription) request.getSession().getAttribute("currentSubscription");
        SubscriptionPart subsPart = subscription.getSubsParts().get(subsPartPosition);

        /*
         * Reduce total price of subscription.
         */
        subscription.setPrice(subscription.getPrice() -  subsPart.getPublication().getPrice());
        subscription.getSubsParts().remove(subsPartPosition);

        return new Page(page, redirect);
    }
}
