package ua.training.controller.command;



import ua.training.model.entity.Excursion;
import ua.training.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class CommandUtility {
    static void setUserRole(HttpServletRequest request,
                            User user, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        loginUserInContext(request, name);
        context.setAttribute("userName", name);
        session.setAttribute("role", user.getRole());
        session.setAttribute("login", name);
        session.setAttribute("user", user);
    }
    //todo delete
    static void setUserRole(HttpServletRequest request,
                            User.ROLE role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        loginUserInContext(request, name);
        context.setAttribute("userName", name);
        session.setAttribute("role", role);
        session.setAttribute("login", name);
    }
    private static void loginUserInContext(HttpServletRequest request, String login){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.add(login);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);

    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        return loggedUsers.stream().anyMatch(userName::equals);
    }

    //todo: norm return bool or HashSet;
    static boolean deleteUserFromContext(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        System.out.println("delete userName " + userName);

        loggedUsers.remove(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);

        return true;
    }

    public static void setSelectedExcursionsListToSession(HttpServletRequest request) {
        request.getSession().setAttribute("selectedExcursions", new ArrayList<Excursion>());
    }

    public static long countSelectedExcursionsPrice(HttpServletRequest request){
        List<Excursion> excursionList  = (List<Excursion>) request.getSession().getAttribute("selectedExcursions");
        return excursionList.stream().mapToLong(Excursion::getPrice).sum();
    }
}
