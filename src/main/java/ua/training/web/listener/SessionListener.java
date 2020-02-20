package ua.training.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

import static ua.training.web.AttributeConstants.LOGGED_USERS_ATTR;
import static ua.training.web.AttributeConstants.SESSION_LOGIN_ATTR;


public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute(LOGGED_USERS_ATTR);
        String login = (String) httpSessionEvent.getSession()
                .getAttribute(SESSION_LOGIN_ATTR);
        loggedUsers.remove(login);
        httpSessionEvent.getSession().setAttribute(LOGGED_USERS_ATTR, loggedUsers);
    }
}
