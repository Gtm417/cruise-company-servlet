package ua.training.web.command;


import javax.servlet.http.HttpServletRequest;

public class LogOutCommand extends MultipleMethodCommand {

    @Override
    protected String performGet(HttpServletRequest request) {
        CommandUtility.deleteUserFromContext(request, String.valueOf(request.getSession().getAttribute("login")));
        request.getSession().invalidate();
        return "redirect:index";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return null;
    }
}
