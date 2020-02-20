package ua.training.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.dao.UserDao;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.UserNotFoundException;
import ua.training.web.form.UserForm;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private final static User USER = User.builder().id(1L).login("test").balance(1000L).role(Role.USER).build();
    private static final String LOGIN = "test";
    private static final String PASSWORD = "test";
    @Rule
    public ExpectedException expectedException;
    @InjectMocks
    UserService userService;
    @Mock
    UserDao userDao;
    @Mock
    PasswordEncoderService passwordEncoderService;

    @Test
    public void login() throws UserNotFoundException {
        USER.setPassword("password");
        UserForm userForm = new UserForm(LOGIN, PASSWORD);
        when(userDao.findByLogin(anyString())).thenReturn(Optional.of(USER));
        when(passwordEncoderService.encode(anyString())).thenReturn("password");

        User actual = userService.login(userForm);

        verify(userDao, times(1)).findByLogin(USER.getLogin());
        Assert.assertEquals(USER, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void loginIfUserNotFound() throws UserNotFoundException {
        USER.setPassword("password");
        UserForm userForm = new UserForm(LOGIN, PASSWORD);
        when(userDao.findByLogin(anyString())).thenReturn(Optional.empty());

        userService.login(userForm);
    }

    @Test
    public void addBalance() {
        when(userDao.update(any(User.class))).thenReturn(true);

        boolean actual = userService.addBalance(USER, 1000L);


        verify(userDao, times(1)).update(any(User.class));
        Assert.assertEquals(USER.getBalance(), 2000L);
        Assert.assertTrue(actual);
    }


    @Test
    public void saveNewUser() throws DuplicateDataBaseException {
        USER.setPassword(PASSWORD);
        when(userDao.create(any(User.class))).thenReturn(true);
        when(passwordEncoderService.encode(anyString())).thenReturn("password");

        boolean actual = userService.saveNewUser(USER);

        verify(userDao, times(1)).create(USER);
        verify(passwordEncoderService, times(1)).encode(PASSWORD);
        Assert.assertEquals(USER.getPassword(), "password");
        Assert.assertTrue(actual);
    }

    @Test(expected = DuplicateDataBaseException.class)
    public void saveNewUserIfExistInDataBase() throws DuplicateDataBaseException {

        when(userDao.create(any(User.class))).thenThrow(DuplicateDataBaseException.class);

        userService.saveNewUser(USER);
    }

}