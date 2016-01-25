package main.command;

import main.helper.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface of business logic.
 */
public interface Command {
    /**
     * Execute part of business logic.
     * @param request client request
     * @param response servlet response
     * @return next page to show in browser
     * @throws ServletException
     * @throws IOException
     */
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
