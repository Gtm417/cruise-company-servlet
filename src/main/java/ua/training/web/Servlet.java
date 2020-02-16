package ua.training.web;


import ua.training.service.*;
import ua.training.service.encoder.PasswordEncoder;
import ua.training.web.command.*;
import ua.training.web.command.admin.AddTicketCommand;
import ua.training.web.command.admin.AllPassengersCommand;
import ua.training.web.command.admin.CruiseDescriptionCommand;
import ua.training.web.command.admin.CruiseEditCommand;
import ua.training.web.form.UserForm;
import ua.training.web.form.validation.OrderFormValidator;
import ua.training.web.form.validation.TicketFormValidator;
import ua.training.web.form.validation.UserFormValidator;
import ua.training.web.form.validation.Validator;
import ua.training.web.mapper.RequestFormMapper;
import ua.training.web.mapper.UserRequestFormMapper;
import ua.training.web.verification.request.BuySubmitRequestVerifier;
import ua.training.web.verification.request.Verifier;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static ua.training.web.CommandConstants.*;


public class Servlet extends HttpServlet {


    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) {
        UserService userService = new UserService(new PasswordEncoder());
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

        commands.put(INDEX_COMMAND,
                new StartPageCommand());

        commands.put(LOGOUT_COMMAND,
                new LogOutCommand());

        commands.put(LOGIN_COMMAND,
                new LoginCommand(userService, userRequestFormMapper));

        commands.put(REGISTRATION_COMMAND,
                new RegistrationCommand(userService, userRequestFormMapper, userValidator));

        commands.put(MAIN_COMMAND,
                new MainCommand(cruiseService));

        commands.put(BALANCE_COMMAND,
                new BalanceCommand(userService));

//        commands.put(BUY_FORM_COMMAND,
//                new BuyCruiseFormCommand());

        commands.put(BUY_COMMAND,
                new BuyCruiseCommand(ticketService, cruiseService, new OrderFormValidator()));

        commands.put(BUY_SUBMIT_COMMAND,
                new SubmitBuyCommand(orderService, excursionService, buySubmitVerifier));

//        commands.put(BUY_SUBMIT_FORM_COMMAND,
//                new SubmitBuyFormCommand(excursionService, buySubmitVerifier));

        commands.put(ADD_EXCURSION_COMMAND,
                excursionCommand);

        commands.put(REMOVE_EXCURSION_COMMAND,
                excursionCommand);

        commands.put(ADMIN_EDIT_CRUISE_COMMAND,
                new CruiseEditCommand(cruiseService));

        commands.put(ADMIN_EDIT_DESCRIPTION_COMMAND,
                new CruiseDescriptionCommand(cruiseService));

        commands.put(ADMIN_ADD_TICKET_COMMAND,
                new AddTicketCommand(ticketService, new TicketFormValidator()));

        commands.put(ADMIN_ALL_PASSENGERS_COMMAND,
                new AllPassengersCommand(orderService));

        commands.put(ALL_ORDERS_COMMAND,
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
        if (page.contains(REDIRECT_COMMAND)) {
            response.sendRedirect(page.replace(REDIRECT_COMMAND, CRUISE_COMPANY_DEFAULT_PATH));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
