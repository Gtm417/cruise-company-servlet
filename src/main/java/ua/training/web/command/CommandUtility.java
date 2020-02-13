package ua.training.web.command;


import ua.training.exception.AccessDenied;
import ua.training.entity.Cruise;
import ua.training.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtility {

    static void setUserInSession(HttpServletRequest request,
                                 User user) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        loginUserInContext(request, user.getLogin());
        session.setAttribute("role", user.getRole());
        session.setAttribute("login", user.getLogin());
        session.setAttribute("user", user);
    }

    private static void loginUserInContext(HttpServletRequest request, String login) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.add(login);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);

    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        return loggedUsers.stream().anyMatch(userName::equals);
    }

    static void deleteUserFromContext(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        loggedUsers.remove(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }

    public static Cruise checkCruiseInSession(HttpServletRequest request) {
        if (request.getSession().getAttribute("cruise") == null) {
            request.setAttribute("cruiseNotFound", true);
            throw new AccessDenied("You didn't choose the cruise");
        }
        return (Cruise) request.getSession().getAttribute("cruise");
    }

    public static void resetSessionPurchaseData(HttpServletRequest request) {
        request.getSession().removeAttribute("cruise");
        request.getSession().removeAttribute("order");
    }

}
