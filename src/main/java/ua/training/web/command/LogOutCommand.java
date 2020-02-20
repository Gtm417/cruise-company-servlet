package ua.training.web.command;


import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.SESSION_LOGIN_ATTR;
import static ua.training.web.CommandConstants.INDEX_COMMAND;
import static ua.training.web.CommandConstants.REDIRECT_COMMAND;

public class LogOutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.deleteUserFromContext(request, String.valueOf(request.getSession().getAttribute(SESSION_LOGIN_ATTR)));
        request.getSession().invalidate();
        return REDIRECT_COMMAND + INDEX_COMMAND;
    }

}
