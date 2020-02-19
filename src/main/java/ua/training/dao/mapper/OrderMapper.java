package ua.training.dao.mapper;

import ua.training.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import static ua.training.dao.TableConstants.*;

public class OrderMapper implements ObjectMapper<Order> {

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getLong(ORDERS_ID_COLUMN))
                .firstName(rs.getString(ORDERS_FIRST_NAME_COLUMN))
                .secondName(rs.getString(ORDER_SECOND_NAME_COLUMN))
                .orderPrice(rs.getLong(ORDERS_PRICE_COLUMN))
                .build();
    }


}
