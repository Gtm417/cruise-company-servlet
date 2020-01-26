package ua.training.controller.Command;



import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.deleteUserFromContext(request, String.valueOf(request.getSession().getAttribute("login")));
        System.out.println("After logout" + request.getSession().getServletContext().getAttribute("loggedUsers"));
        request.getSession().invalidate();
        CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");
        return "redirect:index.jsp";
    }
}
