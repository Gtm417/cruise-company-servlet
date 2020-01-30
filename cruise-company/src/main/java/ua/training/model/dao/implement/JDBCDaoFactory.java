package ua.training.model.dao.implement;


import ua.training.model.dao.*;


public class JDBCDaoFactory extends DaoFactory {
    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCDaoFactory(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(connectionPoolHolder);
    }

    @Override
    public CruiseDao createCruiseDao() {
        return new JDBCCruiseDao(connectionPoolHolder);
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao(connectionPoolHolder);
    }

    @Override
    public TicketDao createTicketDao() {
        return new JDBCTicketDao(connectionPoolHolder);
    }

    @Override
    public ExcursionDao createExcursionDao() {
        return new JDBCExcursionDao(connectionPoolHolder);
    }
}
