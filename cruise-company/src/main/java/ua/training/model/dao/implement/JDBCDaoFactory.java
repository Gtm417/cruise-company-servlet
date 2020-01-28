package ua.training.model.dao.implement;


import ua.training.model.dao.CruiseDao;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.OrderDao;
import ua.training.model.dao.UserDao;


public class JDBCDaoFactory extends DaoFactory {

    @Override
    public UserDao createUserDao(ConnectionPoolHolder connectionPoolHolder) {
        return new JDBCUserDao(connectionPoolHolder);
    }

    @Override
    public CruiseDao createCruiseDao(ConnectionPoolHolder connectionPoolHolder) {
        return new JDBCCruiseDao(connectionPoolHolder);
    }

    @Override
    public OrderDao createOrderDao(ConnectionPoolHolder connectionPoolHolder) {
        return new JDBCOrderDao(connectionPoolHolder);
    }
}
