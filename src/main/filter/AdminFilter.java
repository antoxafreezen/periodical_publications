package main.filter;

import main.entities.User;
import main.helper.Page;
import main.helper.RequestHelper;
import main.manager.UserManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Filter for admin page.
 * Prepare list of users for administrator.
 */
@WebFilter(
        value = "/admin.jsp",
        filterName = "AdminFilter"
)
public class AdminFilter implements Filter {
    /**
     * Manager to work with users in persistent context.
     */
    private UserManager userManager;
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userManager = new UserManager();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        Page nextPage = new Page("admin.jsp", false);
        HttpServletRequest req = (HttpServletRequest) request;
        bundle = RequestHelper.getResourceBundle(req);
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null || !user.isAdmin()) {
            nextPage.setPage("home.jsp");
            nextPage.setRedirected(true);
        } else {
            try {
                List<User> users = userManager.getDefaulters();

                request.setAttribute("users", users);
            } catch (Exception e) {
                Logger.getLogger(getClass()).error(e.getMessage());
                request.setAttribute("message", bundle.getString("filter.error"));
                nextPage.setPage("index.jsp");
            }
        }

        if (nextPage.isRedirected()) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(nextPage.getPage());
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage.getPage());
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
