package com.epam.bar.controller;

import com.epam.bar.command.*;
import com.epam.bar.db.ConnectionPool;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Entry point for all simple requests
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@WebServlet(urlPatterns = {"/controller"})
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
        Command command = commandOptional.orElse(CommandType.ERROR.getCommand());
        CommandResult commandResult = command.execute(content);

        if (commandResult.getAttributes().containsKey(RequestAttribute.REMOVE)) {
            String attribute = (String) commandResult.getAttributes().get(RequestAttribute.REMOVE);
            request.getSession().removeAttribute(attribute);
        }

        commandResult.getAttributes().forEach(request::setAttribute);
        commandResult.getSessionAttributes().forEach(request.getSession()::setAttribute);
        ResponseContext responseContext = commandResult.getResponseContext();

        if (responseContext.getType().equals(ResponseContext.ResponseType.FORWARD)) {
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