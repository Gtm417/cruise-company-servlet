package ua.training.web.command;


import ua.training.entity.Role;
import ua.training.service.CruiseService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.CRUISES_REQUEST_ATTR;
import static ua.training.web.AttributeConstants.LOGGED_USERS_ATTR;
import static ua.training.web.PageConstants.ADMIN_MAIN_JSP;
import static ua.training.web.PageConstants.USER_MAIN_JSP;

public class MainCommand implements Command {
    private final CruiseService cruiseService;

    public MainCommand(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.resetSessionPurchaseData(request);
        request.setAttribute(CRUISES_REQUEST_ATTR, cruiseService.getAllCruises());
        return getMainPageByRole((Role) request.getSession().getAttribute(LOGGED_USERS_ATTR));
    }

    private String getMainPageByRole(Role role) {
        if (role == Role.USER) {
            return USER_MAIN_JSP;
        }
        return ADMIN_MAIN_JSP;
    }
}
