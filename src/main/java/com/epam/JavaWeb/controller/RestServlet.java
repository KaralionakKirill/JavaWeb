package com.epam.JavaWeb.controller;

import com.epam.JavaWeb.command.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/rest"})
@MultipartConfig(location = "D:\\Labs\\EPAM\\data", maxFileSize = 1024 * 1024 * 5,
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
        Command command = commandOptional.orElseThrow(IllegalArgumentException::new);
        CommandResult commandResult = command.execute(content);
        commandResult.getSessionAttributes().forEach(request.getSession()::setAttribute);
        response.getWriter().write(new ObjectMapper().writeValueAsString(commandResult.getAttributes()));
    }
}
