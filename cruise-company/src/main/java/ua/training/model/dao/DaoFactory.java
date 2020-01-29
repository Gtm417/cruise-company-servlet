package ua.training.model.dao;


import ua.training.model.dao.implement.ConnectionPoolHolder;
import ua.training.model.dao.implement.JDBCDaoFactory;
import ua.training.model.entity.Ticket;

import java.sql.Connection;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract CruiseDao createCruiseDao();
    public abstract OrderDao createOrderDao();
    public abstract TicketDao createTicketDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory == null){
                    daoFactory = new JDBCDaoFactory(ConnectionPoolHolder.pool());
                }
            }
        }
        return daoFactory;
    }
}
