package com.epam.JavaWeb.controller;

import com.epam.JavaWeb.command.Command;
import com.epam.JavaWeb.command.CommandProvider;
import com.epam.JavaWeb.filter.EncodingFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Command> commandOptional = CommandProvider.defineCommand(request.getParameter("action"));
        Command command = commandOptional.orElseThrow(IllegalArgumentException::new);
        String page = command.execute(request);
        if (page != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        }
    }

    public void destroy() {
    }
}