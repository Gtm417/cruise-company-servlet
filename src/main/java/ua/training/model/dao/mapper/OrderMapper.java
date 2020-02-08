package ua.training.model.dao.mapper;

import ua.training.model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getLong("orders.id"))
                .firstName(rs.getString("first_name"))
                .secondName(rs.getString("second_name"))
                .orderPrice(rs.getLong("orders.price"))
                .build();
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
