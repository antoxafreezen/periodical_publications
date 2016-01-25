package main.controller;

import main.command.Command;
import main.helper.Page;
import main.helper.RequestHelper;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller of business logic in system.
 * According to user's action executes corresponding command.
 */
@WebServlet(value = "/home",
            name = "MainController")
public class MainController extends HttpServlet {
    /**
     * Helper for defining user's action and corresponding business logic command.
     */
    RequestHelper requestHelper = RequestHelper.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        Page nextPage = new Page("home.jsp", false);

        try{
            Command command = requestHelper.getCommand(req);
            nextPage = command.execute(req, resp);
        } catch (ServletException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }

        if (nextPage.isRedirected()){
            resp.sendRedirect(nextPage.getPage());
        }
        else{
            RequestDispatcher dispatcher = req.getRequestDispatcher(nextPage.getPage());
            dispatcher.forward(req,resp);
        }
    }
}
