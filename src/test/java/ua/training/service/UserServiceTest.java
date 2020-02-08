package ua.training.service;

import org.junit.Assert;
import org.junit.Test;
import ua.training.exception.UserNotFoundException;
import ua.training.model.entity.User;
import ua.training.service.encoder.PasswordEncoder;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Test
    public void testRegex(){
        String regex = ".{1,}";
        String name = "dasda";
        System.out.println(name.matches(regex));
        Assert.assertTrue(name.matches(regex));
    }

    @Test
    public void findUserByLogin() {
        UserService userService = new UserService(new PasswordEncoder());
        User user = User.builder().id(1L).login("login").build();
        try {
            user = userService.findUserByLogin(user.getLogin());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(user);
    }
}