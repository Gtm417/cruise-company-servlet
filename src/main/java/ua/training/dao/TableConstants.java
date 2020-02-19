package ua.training.dao;

public interface TableConstants {
    String CRUISES_TABLE = "cruises";
    String USERS_TABLE = "users";
    String EXCURSIONS_TABLE = "excursions";
    String SHIPS_TABLE = "ships";
    String TICKETS_TABLE = "tickets";
    String ORDERS_TABLE = "orders";
    String PORTS_TABLE = "ports";
    String PORTS_CRUISES_TABLE = "ports_cruises";
    String ORDER_EXCURSIONS_TABLE = "order_excursions";

    String PRICE_COLUMN = "price";
    String ID = "id";

    String CRUISES_ID_COLUMN = "cruises.id";
    String CRUISES_SHIP_ID = "cruises.ship_id";
    String CRUISES_NAME_COLUMN = "cruise_name";
    String CRUISE_DEPARTURE_DATE_COLUMN = "departure_date";
    String CRUISE_DESCRIPTION_ENG_COLUMN = "description_eng";
    String CRUISE_DESCRIPTION_RU_COLUMN = "description_ru";
    String CRUISE_ARRIVAL_DATE = "arrival_date";

    String PORTS_ID_COLUMN = "ports.id";
    String PORT_NAME_COLUMN = "port_name";

    String PORTS_CRUISES_PORT_ID_COLUMN = "ports_cruises.port_id";
    String PORTS_CRUISES_CRUISE_ID_COLUMN = "ports_cruises.cruise_id";

    String EXCURSIONS_PORT_ID_COLUMN = "excursions.port_id";
    String EXCURSIONS_ID_COLUMN = "excursions.id";
    String EXCURSION_NAME_COLUMN = "excursion_name";
    String EXCURSIONS_DURATION_COLUMN = "duration";

    String SHIPS_ID_COLUMN = "ships.id";
    String SHIPS_NAME_COLUMN = "ship_name";
    String SHIP_CURRENT_AMOUNT_OF_PASSENGER_COLUMN = "current_amount_of_passenger";
    String SHIP_MAX_AMOUNT_OF_PASSENGER_COLUMN = "max_amount_of_passenger";

    String ORDER_EXCURSIONS_ORDER_ID_COLUMN = "order_id";
    String ORDERS_EXCURSIONS_EXCURSION_ID = "excursion_id";

    String ORDERS_ID_COLUMN = "orders.id";
    String ORDERS_USER_ID_COLUMN = "user_id";
    String ORDERS_CRUISE_ID_COLUMN = "orders.cruise_id";
    String ORDERS_TICKET_ID_COLUMN = "orders.ticket_id";
    String ORDER_SECOND_NAME_COLUMN = "second_name";
    String ORDERS_FIRST_NAME_COLUMN = "first_name";
    String ORDERS_PRICE_COLUMN = "orders.price";

    String TICKETS_ID_COLUMN = "tickets.id";
    String TICKETS_CRUISE_ID = "tickets.cruise_id";
    String TICKET_NAME_COLUMN = "ticket_name";
    String TICKETS_DISCOUNT_COLUMN = "discount";
    String TICKETS_DISCOUNT_PRICE_COLUMN = "discount_price";
    String TICKETS_PRICE_COLUMN = "tickets.price";

    String USERS_BALANCE_COLUMN = "balance";
    String USERS_LOGIN_COLUMN = "login";
    String USERS_PASSWORD_COLUMN = "password";
    String USERS_ROLE_COLUMN = "role";



}
