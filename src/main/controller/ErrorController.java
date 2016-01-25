package main.controller;

import main.helper.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Class that works when something went wrong.
 */
@WebServlet("/error")
public class ErrorController extends HttpServlet {
    /**
     * Supports and provides internationalization of the system.
     */
    ResourceBundle bundle;

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
        bundle = RequestHelper.getResourceBundle(req);
        req.setAttribute("message", bundle.getString("controller.error"));
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }


}
