package com.epam.bar.controller.filter;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.AdminCommandMarker;
import com.epam.bar.command.marker.BarmanCommandMarker;
import com.epam.bar.command.marker.UserCommandMarker;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * The class filters {@link RequestContext} and blocks users without access
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
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
            log.warn("No access to " + commandName);
            request.getRequestDispatcher(PagePath.ERROR_PAGE).forward(servletRequest, servletResponse);
        }
    }


    private boolean hasAccess(String commandName, User user) {
        boolean isHasAccess = true;
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandName);
        if (commandOptional.isPresent()) {
            Command command = commandOptional.get();
            if (user != null && hasMarkerInterface(command)) {
                Role role = user.getRole();
                if (command instanceof AdminCommandMarker && role != Role.ADMIN) {
                    isHasAccess = false;
                }
                if (command instanceof BarmanCommandMarker && role != Role.BARMAN) {
                    isHasAccess = false;
                }
            }
            if (user == null && hasMarkerInterface(command)) {
                isHasAccess = false;
            }
        }
        return isHasAccess;
    }

    private boolean hasMarkerInterface(Command command) {
        return (command instanceof AdminCommandMarker || command instanceof BarmanCommandMarker ||
                command instanceof UserCommandMarker);
    }
}
