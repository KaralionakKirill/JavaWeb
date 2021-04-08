package com.epam.bar.controller;

import com.epam.bar.command.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Entry point for all rest requests
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@WebServlet(urlPatterns = {"/rest"})
@MultipartConfig(location = "D:\\Labs\\EPAM\\JavaWeb\\target\\JavaWeb-1.0-SNAPSHOT\\uploads", maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 2)
public class RestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContext content = new RequestContext(request);

        String reqCommand = request.getParameter(RequestParameter.COMMAND);
        Optional<Command> commandOptional = CommandProvider.defineCommand(reqCommand);
        Command command = commandOptional.orElse(CommandType.ERROR.getCommand());
        CommandResult commandResult = command.execute(content);
        commandResult.getSessionAttributes().forEach(request.getSession()::setAttribute);
        response.getWriter().write(new ObjectMapper().writeValueAsString(commandResult.getAttributes()));
    }
}
