package ua.training.web.command;


import ua.training.entity.Cruise;
import ua.training.entity.Role;
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
        Role role = (Role) request.getSession().getAttribute("role");
        return getMainPage(role);
    }

    private String getMainPage(Role role) {
        if (role == Role.USER) {
            return "user/main.jsp";
        }
        return "admin/main.jsp";
    }
}
