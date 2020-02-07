package ua.training.controller.filter;


import ua.training.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthFilter implements Filter {

    private static final String LOGIN_REQUEST = "/login";
    private static final String START_PAGE_REQUEST = "/index.jsp";
    private static final String REGISTRATION_REQUEST = "/registration";
    private static final String LOGOUT_REQUEST = "/logout";
    private static final String ERROR_REQUEST = "/error.jsp";
    private static final String ADMIN_REQUEST = "/admin";
    private static final String USER_REQUEST = "/user";
    public static final String MAIN_REQUEST = "/main";
    public static final String BALANCE_REQUEST = "/balance";
    public static final String BUY_REQUEST = "/buy";
    public static final String BUY_SUBMIT_REQUEST = "/buy-submit";
    public static final String ADD_EXCURSION_REQUEST = "/add-excursion";
    public static final String REMOVE_EXCURSION_REQUEST = "/remove-excursion";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;


        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();


        String path = req.getRequestURI();

        boolean isAccessedRequest = path.contains(LOGIN_REQUEST) || path.contains(LOGOUT_REQUEST)
                || path.contains(REGISTRATION_REQUEST) || path.contains(START_PAGE_REQUEST)
                || path.contains(ERROR_REQUEST);


        if ((session.getAttribute("login")) == null && !isAccessedRequest) {
            res.sendRedirect(context.getContextPath() + START_PAGE_REQUEST);
            return;
            //req.getRequestDispatcher(START_PAGE_REQUEST);
            //filterChain.doFilter(request,response);
        }
        if (isAccessedRequest) {
            filterChain.doFilter(request, response);
        } else if (session.getAttribute("login") != null) {
            if (path.contains(ADMIN_REQUEST) && session.getAttribute("role") == (User.ROLE.ADMIN)) {
                filterChain.doFilter(request, response);
            } else if (path.contains(USER_REQUEST) && session.getAttribute("role") == (User.ROLE.USER)) {
                filterChain.doFilter(request, response);
            } else if (path.matches(".*/cruise-company/.*")) {
                filterChain.doFilter(request, response);
            } else {
                response.getWriter().println("Access Denied");
            }

        }
    }

    @Override
    public void destroy() {

    }
}
