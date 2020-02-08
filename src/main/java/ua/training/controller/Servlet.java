package ua.training.controller;


import ua.training.controller.command.*;
import ua.training.controller.command.admin.AddTicketCommand;
import ua.training.controller.command.admin.AllPassengersCommand;
import ua.training.controller.command.admin.CruiseDescriptionCommand;
import ua.training.controller.command.admin.CruiseEditCommand;
import ua.training.controller.form.UserForm;
import ua.training.controller.form.validation.OrderFormValidator;
import ua.training.controller.form.validation.TicketFormValidator;
import ua.training.controller.form.validation.UserFormValidator;
import ua.training.controller.form.validation.Validator;
import ua.training.controller.mapper.RequestFormMapper;
import ua.training.controller.mapper.UserRequestFormMapper;
import ua.training.controller.verification.request.BuySubmitRequestVerifier;
import ua.training.controller.verification.request.Verifier;
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
        RequestFormMapper<UserForm> userRequestFormMapper = new UserRequestFormMapper();
        ExcursionCommand excursionCommand = new ExcursionCommand(excursionService);
        Validator<UserForm> userValidator = new UserFormValidator();
        Verifier buySubmitVerifier = new BuySubmitRequestVerifier();

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout",
                new LogOutCommand());
        commands.put("login",
                new LoginCommand(userService, userRequestFormMapper, userValidator));
        commands.put("registration",
                new RegistrationCommand(userService, userRequestFormMapper, userValidator));
        commands.put("main",
                new MainCommand(cruiseService));
        commands.put("balance",
                new BalanceCommand(userService));
        commands.put("buy-form",
                new BuyCruiseFormCommand(ticketService));
        commands.put("buy",
                new BuyCruiseCommand(ticketService, new OrderFormValidator()));
        commands.put("buy-submit",
                new SubmitBuyCommand(orderService, buySubmitVerifier));
        commands.put("buy-submit-form",
                new SubmitBuyFormCommand(excursionService, buySubmitVerifier));
        commands.put("add-excursion",
                excursionCommand);
        commands.put("remove-excursion",
                excursionCommand);
        commands.put("admin/edit-cruise",
                new CruiseEditCommand(cruiseService));
        commands.put("admin/edit-description",
                new CruiseDescriptionCommand(cruiseService));
        commands.put("admin/add-ticket",
                new AddTicketCommand(ticketService, new TicketFormValidator()));
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
