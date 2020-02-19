package ua.training.web.command;

import ua.training.web.PageConstants;

import javax.servlet.http.HttpServletRequest;

public class StartPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return PageConstants.INDEX_JSP;
    }

}
