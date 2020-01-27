package ua.training.model.service;


import lombok.NonNull;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.implement.ConnectionPoolHolder;
import ua.training.model.entity.User;
import ua.training.model.exception.DuplicateDataBaseException;


public class UserService {
    private DaoFactory daoFactory;

    public UserService() {
        this.daoFactory = DaoFactory.getInstance();
    }

    public boolean saveNewUser (@NonNull User user) throws DuplicateDataBaseException {
        //user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        try (UserDao dao = daoFactory.createUserDao(ConnectionPoolHolder.pool().connect())){
            return dao.create(user);
        }catch (DuplicateDataBaseException ex) {
            throw new DuplicateDataBaseException(ex);        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public User findUserByLogin(String login) {
        try(UserDao dao = daoFactory.createUserDao(ConnectionPoolHolder.pool().connect())){
            //TODO Exception user  not exist
            return dao.findByLogin(login).orElseThrow(RuntimeException::new);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
