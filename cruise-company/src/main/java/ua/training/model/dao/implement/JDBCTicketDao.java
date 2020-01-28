package ua.training.model.dao.implement;

import ua.training.model.dao.TicketDao;
import ua.training.model.entity.Ticket;
import ua.training.model.exception.DuplicateDataBaseException;

import java.util.List;

public class JDBCTicketDao implements TicketDao {
    @Override
    public boolean create(Ticket entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Ticket findById(int id) {
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public boolean update(Ticket entity) {
        return false;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Long> getTicketsPriceWithDiscountByCruiseId(Long id) {
        return null;
    }
}
