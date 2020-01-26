package ua.training.model.service;


import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;

import java.sql.SQLException;


public class UserService {
    private DaoFactory daoFactory = DaoFactory.getInstance();


    public User findUserByLogin(String login) throws Exception {
        try(UserDao dao = daoFactory.createUserDao()){
            return dao.findByLogin(login).orElseThrow(RuntimeException::new);
        }
    }

}
