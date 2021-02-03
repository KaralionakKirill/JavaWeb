package com.epam.JavaWeb.controller;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.db.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        RequestContext content = new RequestContext(request);

        String reqCommand = request.getParameter(RequestParameter.COMMAND);
        Optional<Command> commandOptional = CommandProvider.defineCommand(reqCommand);
        Command command = commandOptional.orElseThrow(IllegalArgumentException::new);
        CommandResult commandResult = command.execute(content);

        commandResult.getAttributes().forEach(request::setAttribute);
        commandResult.getSessionAttributes().forEach(request.getSession()::setAttribute);

        if (commandResult.getResponseType().equals(ResponseType.FORWARD)) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commandResult.getPage());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + commandResult.getPage());
        }
    }

    public void destroy() {
        ConnectionPool.INSTANCE.destroyPool();
        super.destroy();
    }
}