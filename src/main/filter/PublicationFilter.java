package main.filter;


import main.dao.PersistException;
import main.entities.Publication;
import main.entities.Subscription;
import main.entities.SubscriptionPart;
import main.helper.Page;
import main.helper.RequestHelper;
import main.manager.PublicationManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Filter for publications page.
 * Prepare list of publications for user.
 */
@WebFilter(
        value = "/publications.jsp",
        filterName = "PublicationFilter"
)
public class PublicationFilter implements Filter {
    /**
     * Manager to work with publications in persistent context.
     */
    private PublicationManager publicationManager;
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        publicationManager = new PublicationManager();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Page nextPage = new Page("publications.jsp", false);

        HttpServletRequest req = (HttpServletRequest) request;
        bundle = RequestHelper.getResourceBundle(req);
        try {
            List<Publication> publications = publicationManager.getPublications();
            Subscription currentSubscription = (Subscription) req.getSession().getAttribute("currentSubscription");
            if (currentSubscription != null){
                List<SubscriptionPart> parts = currentSubscription.getSubsParts();
                for (SubscriptionPart part: parts){
                    publications.remove(part.getPublication());
                }
            }
            request.setAttribute("publications", publications);
        } catch (PersistException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
            request.setAttribute("message", bundle.getString("filter.error"));
            nextPage.setPage("index.jsp");
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(nextPage.getPage());
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
