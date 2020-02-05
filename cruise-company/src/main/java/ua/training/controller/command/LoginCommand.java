package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.UserNotFoundException;
import ua.training.model.entity.User;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final UserService userService;
    private final RequestMapper<User> userRequestMapper;

    public LoginCommand(UserService userService, RequestMapper<User> userRequestMapper) {
        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
    }

    @Override
    public String execute(HttpServletRequest request) {
        //todo new method
        if (request.getSession().getAttribute("user") != null) {
            return "redirect:logout";
        }

        String login = request.getParameter("name");
        String pass = request.getParameter("pass");

        //todo regex valid
        //todo default valid on null
        if (login == null || login.equals("") || pass == null || pass.equals("")) {
            return "/login.jsp";
        }

        // Выкинет ошибку если не существует пользователя в базе (не правильный логин)
        User user = userRequestMapper.mapToEntity(request);
        try {
            user = userService.findUserByLogin(user.getLogin());
        } catch (UserNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "login.jsp");
            return exceptionHandler.handling(request);
        }

        if (CommandUtility.checkUserIsLogged(request, user.getLogin())) {
            return "WEB-INF/error.jsp";
        }

        if (userService.checkInputPassword(pass, user.getPassword())) {
            CommandUtility.setUserInSession(request, user);
            return "redirect:main";
        } else {
            request.getSession().setAttribute("exception", true);
            return "/login.jsp";
        }


    }



}
