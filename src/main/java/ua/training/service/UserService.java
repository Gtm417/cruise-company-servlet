package ua.training.service;


import lombok.NonNull;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.entity.User;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.UserNotFoundException;
import ua.training.service.encoder.PasswordEncoder;
import ua.training.web.form.UserForm;

import java.util.Objects;
import java.util.Optional;


public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = DaoFactory.getInstance().createUserDao();
    }

    public User login(UserForm userForm) throws UserNotFoundException {
        return findUserByLogin(userForm.getLogin())
                .filter(user -> Objects.equals(user.getPassword(), passwordEncoder.encode(userForm.getPassword())))
                .orElseThrow(() -> new UserNotFoundException("User not found with login: ", userForm.getLogin()));
    }

    public boolean saveNewUser(@NonNull User user) throws DuplicateDataBaseException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userDao.create(user);
        } catch (DuplicateDataBaseException ex) {
            throw new DuplicateDataBaseException(ex);
        }
    }

    public Optional<User> findUserByLogin(String login) {
        return userDao.findByLogin(login);

    }

    public boolean addBalance(User user, long value) {
        user.setBalance(user.getBalance() + value);
        return userDao.update(user);
    }

}
