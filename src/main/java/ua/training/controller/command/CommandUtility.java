package ua.training.controller.command;


import ua.training.exception.AccessDenied;
import ua.training.exception.UnreachableRequest;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Excursion;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    public static void setSelectedExcursionsListToSession(HttpServletRequest request) {
        request.getSession().setAttribute("selectedExcursions", new ArrayList<Excursion>());
    }

    private static long countSelectedExcursionsPrice(HttpServletRequest request) {
        List<Excursion> excursionList = (List<Excursion>) request.getSession().getAttribute("selectedExcursions");
        return excursionList.stream().mapToLong(Excursion::getPrice).sum();
    }

    public static List<Excursion> addExcursionToSelectedList(HttpServletRequest request, Excursion excursion) {
        List<Excursion> selectedExcursions = (List<Excursion>) request.getSession().getAttribute("selectedExcursions");
        if (selectedExcursions.stream().noneMatch(excursion::equals)) {
            selectedExcursions.add(excursion);
        }
        return (List<Excursion>) request.getSession().getAttribute("selectedExcursions");
    }

    public static void deleteExcursionFromSelectedList(HttpServletRequest request, Excursion excursion) {
        List<Excursion> selectedExcursions = (List<Excursion>) request.getSession().getAttribute("selectedExcursions");
        selectedExcursions.remove(excursion);
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
        request.getSession().removeAttribute("selectedExcursions");
    }


    public static long getCruiseId(HttpServletRequest request) {
        try{
            return Long.parseLong(request.getParameter("cruiseId"));
        }catch (NumberFormatException ex){
            throw new UnreachableRequest();
        }
    }
}
