package ua.training.web.filter;


import ua.training.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.training.web.AttributeConstants.SESSION_ROLE_ATTR;
import static ua.training.web.AttributeConstants.SESSION_USER_ATTR;
import static ua.training.web.CommandConstants.CRUISE_COMPANY_DEFAULT_PATH;
import static ua.training.web.CommandConstants.INDEX_COMMAND;
import static ua.training.web.PageConstants.ACCESS_DENIED_PAGE;
import static ua.training.web.PageConstants.PAGE_404_JSP;


public class AuthFilter implements Filter {

    public static final String CRUISE_COMPANY_REGEX_PATTERN = ".*/cruise-company/.*";
    private static final String LOGIN_REQUEST = "/login";
    private static final String INDEX_REQUEST = "/index";
    private static final String REGISTRATION_REQUEST = "/registration";
    private static final String LOGOUT_REQUEST = "/logout";
    private static final String ADMIN_REQUEST = "/admin";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        String path = req.getRequestURI();

        boolean isAccessedRequest = path.contains(LOGIN_REQUEST) || path.contains(LOGOUT_REQUEST)
                || path.contains(REGISTRATION_REQUEST) || path.contains(INDEX_REQUEST);


        if ((session.getAttribute(SESSION_USER_ATTR)) == null && !isAccessedRequest) {
            res.sendRedirect(CRUISE_COMPANY_DEFAULT_PATH + INDEX_COMMAND);
            return;
        }
        if (isAccessedRequest) {
            filterChain.doFilter(request, response);
            return;
        }
        if (session.getAttribute(SESSION_USER_ATTR) != null) {
            if (path.contains(ADMIN_REQUEST) && session.getAttribute(SESSION_ROLE_ATTR) != (Role.ADMIN)) {
                req.getRequestDispatcher(ACCESS_DENIED_PAGE).forward(request, response);
            } else if (path.matches(CRUISE_COMPANY_REGEX_PATTERN)) {
                filterChain.doFilter(request, response);
            } else {
                req.getRequestDispatcher(PAGE_404_JSP).forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
