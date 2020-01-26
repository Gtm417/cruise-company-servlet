package ua.training.controller.Command;

import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        System.out.println(request.getCharacterEncoding());
        String login = request.getParameter("name");
        System.out.println(login);
        String pass = request.getParameter("pass");

        if( login == null || login.equals("") || pass == null || pass.equals("")  ){
            return "/login.jsp";
        }
        System.out.println(login + " " + pass);

        //?
        UserService userService = new UserService();
        // Выкинет ошибку если не существует пользователя в базе (не правильный логин)
        User user =  userService.findUserByLogin(login);

        System.out.println(user);
        System.out.println("AFTER TRY");
        if(CommandUtility.checkUserIsLogged(request, login)){
            return "redirect:error.jsp";
        }

        System.out.println("after input role");
        if(user.getRole().equals(User.ROLE.ADMIN) && checkInputPassword(pass, user.getPassword())){
            System.out.println("Redirect to admin");
            CommandUtility.setUserRole(request, user.getRole(), login);
            return "redirect:admin/admin-page.jsp";
        } else if(user.getRole().equals(User.ROLE.USER) && checkInputPassword(pass, user.getPassword())){
            CommandUtility.setUserRole(request, user.getRole(), login);
            return "redirect:user/user-page.jsp";
        } else {
            //todo: return with message(wrong pass or login)
            return "/login.jsp";
        }
    }

    private boolean checkInputPassword(String inputPassword,String userPassword){
        return userPassword.equals(inputPassword);
    }

}
