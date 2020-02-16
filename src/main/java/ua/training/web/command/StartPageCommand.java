package ua.training.web.command;

import javax.servlet.http.HttpServletRequest;

public class StartPageCommand extends MultipleMethodCommand {

    @Override
    protected String performGet(HttpServletRequest request) {
        return "index.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return null;
    }
}
