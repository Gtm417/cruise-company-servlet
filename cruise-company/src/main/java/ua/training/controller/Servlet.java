package ua.training.controller;


import ua.training.controller.command.*;
import ua.training.controller.command.admin.AddTicketCommand;
import ua.training.controller.command.admin.AllPassengersCommand;
import ua.training.controller.command.admin.CruiseDescriptionCommand;
import ua.training.controller.command.admin.CruiseEditCommand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout",
                new LogOutCommand());
        commands.put("login",
                new LoginCommand());
        commands.put("exception" ,
                new ExceptionCommand());
        commands.put("registration" ,
                new RegistrationCommand());
        commands.put("main" ,
                new MainCommand());
        commands.put("balance" ,
                new BalanceCommand());
        commands.put("buy-form" ,
                new BuyCruiseFormCommand());
        commands.put("buy",
                new BuyCruiseCommand());
        commands.put("buy-submit" ,
                new SubmitBuyCommand());
        commands.put("buy-submit-form" ,
                new SubmitBuyFormCommand());
        commands.put("add-excursion",
                new AddExcursionCommand());
        commands.put("remove-excursion",
                new RemoveExcursionCommand());
        commands.put("admin/edit-cruise",
                new CruiseEditCommand());
        commands.put("admin/edit-description",
                new CruiseDescriptionCommand());
        commands.put("admin/add-ticket",
                new AddTicketCommand());
        commands.put("admin/all-passengers",
                new AllPassengersCommand());
        commands.put("all-orders",
                new AllOrdersCommand());
    }



    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
            processRequest(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
            processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("im in servlet");
        System.err.println("Servlet:  " + response.getCharacterEncoding());
        String path = request.getRequestURI();
        System.out.println("path 1 :" + path);
        path = path.replaceAll(".*/cruise-company/" , "");
        System.out.println("path 1 :" + path);
        Command command = commands.getOrDefault(path ,
                (r)->"/WEB-INF/404.jsp");
        System.out.println("command: " + command.getClass().getName());
        String page = command.execute(request);
        System.out.println("execute end");
        System.out.println("page:  " + page);
        if(page.contains("redirect")){
            System.out.println("page replace: " + page.replace("redirect:", "/cruise-company/"));
            response.sendRedirect(page.replace("redirect:", "/cruise-company/"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
