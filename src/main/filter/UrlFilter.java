package main.filter;

import main.entities.User;
import main.helper.Page;
import main.helper.RequestHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Filter for incorrect access to pages.
 * Check user's access to pages.
 */
public class UrlFilter implements Filter {
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            Page nextPage = new Page(req.getRequestURI(), false);
            User currentUser = (User) req.getSession().getAttribute("currentUser");


            bundle = RequestHelper.getResourceBundle(req);

            switch (req.getRequestURI()) {
                case "/subscription.jsp":
                case "/profile.jsp": {
                    if (currentUser == null) {
                        req.setAttribute("message", bundle.getString("filter.url.error.login_first"));
                        nextPage.setPage("index.jsp");
                    }
                    break;
                }
                case "/index.jsp": {
                    req.setAttribute("message", bundle.getString("filter.url.error.index"));
                    nextPage.setPage("index.jsp");
                    break;
                }
            }
                if (nextPage.isRedirected()) {
                    HttpServletResponse resp = (HttpServletResponse) servletResponse;
                    resp.sendRedirect(nextPage.getPage());
                } else {
                    RequestDispatcher dispatcher = req.getRequestDispatcher(nextPage.getPage());
                    dispatcher.forward(servletRequest, servletResponse);
                }
    }

    @Override
    public void destroy() {

    }
}
