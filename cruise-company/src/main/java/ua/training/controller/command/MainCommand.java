package ua.training.controller.command;


import ua.training.model.entity.Cruise;
import ua.training.model.entity.User;
import ua.training.service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainCommand implements Command {
    private final CruiseService cruiseService;

    public MainCommand(CruiseService cruiseService) {

        this.cruiseService = cruiseService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.resetSessionPurchaseData(request);
        List<Cruise> cruises = cruiseService.getAllCruises();
        request.setAttribute("cruises", cruises);
        User.ROLE role = (User.ROLE) request.getSession().getAttribute("role");
        return getMainPage(role);
    }

    private String getMainPage(User.ROLE role) {
        if (role == User.ROLE.USER) {
            return "user/main.jsp";
        }
        return "admin/main.jsp";
    }
}
