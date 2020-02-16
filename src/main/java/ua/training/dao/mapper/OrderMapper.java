package ua.training.dao.mapper;

import ua.training.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

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


}
