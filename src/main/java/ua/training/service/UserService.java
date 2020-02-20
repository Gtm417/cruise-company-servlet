package ua.training.service;


import lombok.NonNull;
import ua.training.dao.UserDao;
import ua.training.entity.User;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.UserNotFoundException;
import ua.training.web.form.UserForm;

import java.util.Objects;


public class UserService {
    private final PasswordEncoderService passwordEncoderService;
    private UserDao userDao;

    public UserService(PasswordEncoderService passwordEncoderService, UserDao dao) {
        this.passwordEncoderService = passwordEncoderService;
        this.userDao = dao;
    }

    public User login(UserForm userForm) throws UserNotFoundException {
        return userDao.findByLogin(userForm.getLogin())
                .filter(user -> Objects.equals(user.getPassword(), passwordEncoderService.encode(userForm.getPassword())))
                .orElseThrow(() -> new UserNotFoundException("User not found with login: " + userForm.getLogin()));
    }

    public boolean saveNewUser(@NonNull User user) throws DuplicateDataBaseException {
        user.setPassword(passwordEncoderService.encode(user.getPassword()));
        return userDao.create(user);
    }

    public boolean addBalance(User user, long value) {
        user.setBalance(user.getBalance() + value);
        return userDao.update(user);
    }

}
