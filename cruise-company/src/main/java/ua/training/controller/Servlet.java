package ua.training.controller;


import ua.training.controller.command.*;
import ua.training.controller.command.admin.AddTicketCommand;
import ua.training.controller.command.admin.AllPassengersCommand;
import ua.training.controller.command.admin.CruiseDescriptionCommand;
import ua.training.controller.command.admin.CruiseEditCommand;
import ua.training.controller.validation.*;
import ua.training.controller.verification.request.AddRemoveExcursionRequestVerify;
import ua.training.controller.mapper.*;
import ua.training.controller.verification.request.BuySubmitRequestVerify;
import ua.training.controller.verification.request.Verify;
import ua.training.model.entity.User;
import ua.training.service.*;
import ua.training.service.encoder.PasswordEncoder;

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

    public void init(ServletConfig servletConfig) {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        UserService userService = new UserService(passwordEncoder);
        CruiseService cruiseService = new CruiseService();
        ExcursionService excursionService = new ExcursionService();
        OrderService orderService = new OrderService();
        TicketService ticketService = new TicketService();
        RequestMapper<User> userRequestMapper = new UserRequestMapper();
        ExcursionCommand excursionCommand = new ExcursionCommand(new AddRemoveExcursionRequestVerify(), excursionService);
        RequestParameterValidator userValidator = new UserRequestParameterValidator();
        Verify buySubmitVerify = new BuySubmitRequestVerify();

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout",
                new LogOutCommand());
        commands.put("login",
                new LoginCommand(userService, userRequestMapper, userValidator));
        commands.put("exception",
                new ExceptionCommand());
        commands.put("registration",
                new RegistrationCommand(userService, userRequestMapper, userValidator));
        commands.put("main",
                new MainCommand(cruiseService));
        commands.put("balance",
                new BalanceCommand(userService, new BalanceRequestValidator()));
        commands.put("buy-form",
                new BuyCruiseFormCommand(ticketService));
        commands.put("buy",
                new BuyCruiseCommand(ticketService, new OrderRequestMapper(), new OrderRequestValidator()));
        commands.put("buy-submit",
                new SubmitBuyCommand(orderService, buySubmitVerify));
        commands.put("buy-submit-form",
                new SubmitBuyFormCommand(excursionService, buySubmitVerify));
        commands.put("add-excursion",
                excursionCommand);
        commands.put("remove-excursion",
                excursionCommand);
        commands.put("admin/edit-cruise",
                new CruiseEditCommand(cruiseService));
        commands.put("admin/edit-description",
                new CruiseDescriptionCommand(cruiseService));
        commands.put("admin/add-ticket",
                new AddTicketCommand(ticketService, new TicketRequestMapper(), new TicketRequestParameterValidator()));
        commands.put("admin/all-passengers",
                new AllPassengersCommand(orderService));
        commands.put("all-orders",
                new AllOrdersCommand(orderService));
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
        String path = request.getRequestURI();
        path = path.replaceAll(".*/cruise-company/", "");
        Command command = commands.getOrDefault(path,
                (r) -> "/WEB-INF/404.jsp");
        String page = command.execute(request);
        if (page.contains("redirect")) {
            response.sendRedirect(page.replace("redirect:", "/cruise-company/"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
