package com.epam.bar.controller.filter;

import com.epam.bar.command.*;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
@WebFilter(filterName = "PageAccessFilter", urlPatterns = {"/*"})
public class PageAccessFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String commandName = request.getParameter(RequestParameter.COMMAND);
        User user = (User) session.getAttribute(RequestAttribute.USER);

        if (hasAccess(commandName, user)) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            log.error("No access to the page");
            request.getRequestDispatcher(PagePath.ERROR_PAGE).forward(servletRequest, servletResponse);
        }
    }

    private boolean hasAccess(String commandName, User user) {
        if (commandName == null) {
            return true;
        }
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        Command command = commandType.getCommand();
        if (AdminCommand.class.isAssignableFrom(command.getClass())) {
            return adminCommandAccess(user);
        }
        if (BarmanCommand.class.isAssignableFrom(command.getClass())) {
            return barmanCommandAccess(user);
        }
        if (UserCommand.class.isAssignableFrom(command.getClass())) {
            return userCommandAccess(user);
        }
        return true;
    }

    private boolean adminCommandAccess(User user) {
        boolean result = false;
        if (user != null && user.getRole() == Role.ADMIN) {
            result = true;
        }
        return result;
    }

    private boolean barmanCommandAccess(User user) {
        boolean result = false;
        if (user != null && user.getRole() == Role.BARMAN) {
            result = true;
        }
        return result;
    }

    private boolean userCommandAccess(User user) {
        boolean result = false;
        if (user != null) {
            result = true;
        }
        return result;
    }
}
