package ua.training.web.command;

import javax.servlet.http.HttpServletRequest;

public class StartPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "index.jsp";
    }
}
