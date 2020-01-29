package ua.training.model.service;


import lombok.NonNull;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.implement.ConnectionPoolHolder;
import ua.training.model.entity.User;
import ua.training.model.exception.DuplicateDataBaseException;


public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = DaoFactory.getInstance().createUserDao();
    }

    public boolean saveNewUser (@NonNull User user) throws DuplicateDataBaseException {
        //user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        try {
            return userDao.create(user);
        }catch (DuplicateDataBaseException ex) {
            throw new DuplicateDataBaseException(ex);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public User findUserByLogin(String login) {
        try{
            System.out.println("im in service");
            //TODO Exception user  not exist
            return userDao.findByLogin(login).orElseThrow(RuntimeException::new);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean addBalance(User user,long value) {
        user.setBalance(user.getBalance() + value);
        return userDao.update(user);
    }
}
