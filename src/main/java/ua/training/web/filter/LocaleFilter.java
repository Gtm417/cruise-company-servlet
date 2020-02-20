package ua.training.web.filter;

import ua.training.web.AttributeConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getParameter(AttributeConstants.SESSION_REQUEST_LANG_ATTR) != null) {
            request.getSession().setAttribute(AttributeConstants.SESSION_REQUEST_LANG_ATTR, request.getParameter(AttributeConstants.SESSION_REQUEST_LANG_ATTR));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
