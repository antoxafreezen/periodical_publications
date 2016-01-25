package main.command;

import main.helper.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class encapsulates business logic.
 * Work when some wrong command passed to servlet.
 */
public class NoCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();
        }

        String page = "login.jsp";
        boolean redirect = true;

        return new Page(page, redirect);
    }
}
