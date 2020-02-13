package ua.training.service;

import org.junit.Assert;
import org.junit.Test;
import ua.training.exception.UserNotFoundException;
import ua.training.entity.User;
import ua.training.service.encoder.PasswordEncoder;

public class UserServiceTest {

    @Test
    public void testRegex() {
        String regex = "[1-9]{1}[0-9]*";
        String name = "23123";
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
    }
}