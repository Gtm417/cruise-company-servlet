package ua.training.controller.command;


import javax.servlet.http.HttpServletRequest;

public class BuyCruiseCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String cruiseId = request.getParameter("cruiseId");

        if(cruiseId == null) {
            return "redirect:main";
        }
        return null;
    }
}
