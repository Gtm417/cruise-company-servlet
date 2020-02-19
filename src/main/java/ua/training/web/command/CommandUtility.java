package ua.training.web.command;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.entity.Cruise;
import ua.training.entity.User;
import ua.training.exception.AccessDenied;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static ua.training.web.AttributeConstants.*;

public class CommandUtility {
    private static final Logger LOGGER = LogManager.getLogger(CommandUtility.class);

    static void setUserInSession(HttpServletRequest request,
                                 User user) {
        HttpSession session = request.getSession();
        loginUserInContext(request, user.getLogin());
        session.setAttribute(SESSION_ROLE_ATTR, user.getRole());
        session.setAttribute(SESSION_LOGIN_ATTR, user.getLogin());
        session.setAttribute(SESSION_USER_ATTR, user);
    }

    private static void loginUserInContext(HttpServletRequest request, String login) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute(LOGGED_USERS_ATTR);
        loggedUsers.add(login);
        request.getSession().getServletContext()
                .setAttribute(LOGGED_USERS_ATTR, loggedUsers);

    }

    static void deleteUserFromContext(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute(LOGGED_USERS_ATTR);

        loggedUsers.remove(userName);
        request.getSession().getServletContext()
                .setAttribute(LOGGED_USERS_ATTR, loggedUsers);
    }

    public static Cruise checkCruiseInSession(HttpServletRequest request) {
        if (request.getSession().getAttribute(SESSION_CRUISE_ATTR) == null) {
            request.setAttribute(CRUISE_NOT_FOUND_ATTR, true);
            LOGGER.info("User try to get page without cruise in session");
            throw new AccessDenied("Cruise not found in session");
        }
        return (Cruise) request.getSession().getAttribute(SESSION_CRUISE_ATTR);
    }

    public static void resetSessionPurchaseData(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_CRUISE_ATTR);
        request.getSession().removeAttribute(SESSION_ORDER_ATTR);
    }

}
