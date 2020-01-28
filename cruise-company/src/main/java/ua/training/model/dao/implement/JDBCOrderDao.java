package ua.training.model.dao.implement;

import ua.training.model.dao.OrderDao;
import ua.training.model.entity.Order;
import ua.training.model.exception.DuplicateDataBaseException;

import java.util.List;

public class JDBCOrderDao implements OrderDao {

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCOrderDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Order entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean update(Order entity) {
        return false;
    }

    @Override
    public void delete(int id) {

    }
}
