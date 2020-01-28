package ua.training.controller.command;


import ua.training.model.entity.Cruise;
import ua.training.model.entity.User;
import ua.training.model.service.CruiseService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainCommand implements Command {
    private final CruiseService cruiseService;

    public MainCommand() {
        this.cruiseService = new CruiseService();
    }


    @Override
    public String execute(HttpServletRequest request){
        System.out.println("im in main command");
        List<Cruise> cruises = cruiseService.getAllCruises();
        request.setAttribute("cruises", cruises);
        System.out.println("Cruises " + cruises);
        System.out.println("Session " + request.getParameter("cruises"));
        User.ROLE role =(User.ROLE) request.getSession().getAttribute("role");
        if(role == User.ROLE.USER){
            return "user/main.jsp";
        }
        return "admin/main.jsp";
    }
}
