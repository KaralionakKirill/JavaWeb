package com.epam.bar.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The class defending {@link com.epam.bar.command.RequestContext} from js injection
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@WebFilter(filterName = "XssAttackFilter", urlPatterns = {"/*"})
public class XssAttackFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
    }
}
