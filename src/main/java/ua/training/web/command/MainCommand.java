package ua.training.web.command;


import ua.training.entity.Role;
import ua.training.service.CruiseService;

import javax.servlet.http.HttpServletRequest;

public class MainCommand extends MultipleMethodCommand {
    private final CruiseService cruiseService;

    public MainCommand(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        CommandUtility.resetSessionPurchaseData(request);
        request.setAttribute("cruises", cruiseService.getAllCruises());
        return getMainPageByRole((Role) request.getSession().getAttribute("role"));
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return null;
    }

    private String getMainPageByRole(Role role) {
        if (role == Role.USER) {
            return "user/main.jsp";
        }
        return "admin/main.jsp";
    }
}
