package ua.training.controller.filters;


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



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("im in filter");

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;


        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();


        System.out.println(session);
        System.out.println("Session role = " + session.getAttribute("role"));
        System.out.println("Session login = " + session.getAttribute("login"));
        System.out.println("Context users = " + context.getAttribute("loggedUsers"));


        String  path = req.getRequestURI();
        System.out.println("filter  " + path);
        boolean isAccessedRequest = path.contains(LOGIN_REQUEST) || path.contains(LOGOUT_REQUEST)
                || path.contains(REGISTRATION_REQUEST) || path.contains(START_PAGE_REQUEST) || path.contains(ERROR_REQUEST);


        if((session.getAttribute("login")) == null && !isAccessedRequest){
            System.out.println("Not Login filter");
            System.out.println(START_PAGE_REQUEST);
            res.sendRedirect(context.getContextPath() + START_PAGE_REQUEST);
            System.out.println("filter redirect");
            return;
            //req.getRequestDispatcher(START_PAGE_REQUEST);
            //filterChain.doFilter(request,response);
        }
        if(isAccessedRequest){
            System.out.println("First if");
            filterChain.doFilter(request,response);
        }
        else if(session.getAttribute("login") != null){
            System.out.println("ELSE");
            if(path.contains(ADMIN_REQUEST) && session.getAttribute("role").equals(User.ROLE.ADMIN)){
                System.out.println("Sec if");
                filterChain.doFilter(request,response);
            }else if(path.contains(USER_REQUEST) && session.getAttribute("role").equals(User.ROLE.USER)){
                System.out.println("Third if");
                filterChain.doFilter(request,response);
            }else {
                System.out.println("Access Denied");
                response.getWriter().println("Access Denied");
            }
        }
          //filterChain.doFilter(request,response);
        System.out.println("im out filter");
    }

    @Override
    public void destroy() {

    }
}
