package ua.training.model.dao;


import ua.training.model.dao.implement.ConnectionPoolHolder;
import ua.training.model.dao.implement.JDBCDaoFactory;

import java.sql.Connection;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao(ConnectionPoolHolder connectionPoolHolder);
    public abstract CruiseDao createCruiseDao(ConnectionPoolHolder connectionPoolHolder);

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory == null){
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
