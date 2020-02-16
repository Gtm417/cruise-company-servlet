package ua.training.service;

import org.junit.Assert;
import org.junit.Test;
import ua.training.exception.UserNotFoundException;
import ua.training.entity.User;
import ua.training.service.encoder.PasswordEncoder;

public class UserServiceTest {

    @Test
    public void testRegex() {
        String regex = "([1-9][0-9]{0,3})";
        String name = "12345";
        Assert.assertTrue(name.matches(regex));
    }

}