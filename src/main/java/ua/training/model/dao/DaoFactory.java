package ua.training.model.dao;


import ua.training.model.dao.implement.JDBCDaoFactory;
import ua.training.model.dao.ConnectionPoolHolder;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract CruiseDao createCruiseDao();

    public abstract OrderDao createOrderDao();

    public abstract TicketDao createTicketDao();

    public abstract ExcursionDao createExcursionDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory(ConnectionPoolHolder.pool());
                }
            }
        }
        return daoFactory;
    }
}
