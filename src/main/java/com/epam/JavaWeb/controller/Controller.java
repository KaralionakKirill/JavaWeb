package com.epam.JavaWeb.controller;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.db.ConnectionPool;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/controller", "*.do"})
@Log4j2
public class Controller extends HttpServlet {
    public void init() throws ServletException {
        super.init();
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
        ResponseContext responseContext = commandResult.getResponseContext();

        if (responseContext.getType().equals(ResponseType.FORWARD)) {
            String page = ((ForwardResponse) responseContext).getPage();
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        } else {
            String redirectCommand = ((RedirectResponse) responseContext).getCommand();
            response.sendRedirect(request.getContextPath() + redirectCommand);
        }
    }

    public void destroy() {
        ConnectionPool.INSTANCE.destroyPool();
        super.destroy();
    }
}