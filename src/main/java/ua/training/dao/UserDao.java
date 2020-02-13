package ua.training.dao;

import ua.training.entity.User;

import java.util.Optional;


public interface UserDao extends GenericDao<User> {
    Optional<User> findByLogin(String login);
}
