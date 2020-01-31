package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.model.entity.User;
import ua.training.model.exception.DuplicateDataBaseException;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command{
    UserService userService;

    public RegistrationCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request){
        String login = request.getParameter("name");
        String pass = request.getParameter("pass");
        System.out.println(login + " " + pass);

        if( login == null || login.equals("") || pass == null || pass.equals("")  ){
            return "/registration.jsp";
        }

        User user = User.builder()
                .login(login)
                .password(pass)
                .build();
        try {
            userService.saveNewUser(user);
        } catch (DuplicateDataBaseException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e,"registration.jsp");
            return exceptionHandler.handling(request);
        }
        request.getSession().setAttribute("success", true);
        return "redirect:login";
    }
}
