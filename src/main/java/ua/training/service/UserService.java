package ua.training.service;


import lombok.NonNull;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.UserNotFoundException;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;
import ua.training.service.encoder.PasswordEncoder;


public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = DaoFactory.getInstance().createUserDao();
    }

    public boolean saveNewUser(@NonNull User user) throws DuplicateDataBaseException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userDao.create(user);
        } catch (DuplicateDataBaseException ex) {
            throw new DuplicateDataBaseException(ex);
        }
    }


    public User findUserByLogin(String login) throws UserNotFoundException {
            return userDao.findByLogin(login)
                    .orElseThrow(() -> new UserNotFoundException("User not found with login: ", login));
    }

    public boolean addBalance(User user, long value) {
        user.setBalance(user.getBalance() + value);
        return userDao.update(user);
    }

    public boolean checkInputPassword(String inputPassword, String userPassword) {

        return userPassword.equals(passwordEncoder.encode(inputPassword));
    }
}
