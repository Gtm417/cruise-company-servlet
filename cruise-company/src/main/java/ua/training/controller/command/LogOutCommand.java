package ua.training.controller.command;


import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.deleteUserFromContext(request, String.valueOf(request.getSession().getAttribute("login")));
        request.getSession().invalidate();
        return "redirect:index.jsp";
    }
}
