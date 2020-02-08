package ua.training.model.dao;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPoolHolder {
    private static volatile ConnectionPoolHolder pool;
    private BasicDataSource dataSource;

    private ConnectionPoolHolder() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/cruise_company_servlet?serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("1234");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setMinIdle(5);
        ds.setInitialSize(10);
        ds.setMaxIdle(1);
        ds.setMaxOpenPreparedStatements(100);
        this.dataSource = ds;
    }

    public static ConnectionPoolHolder pool() {
        if (pool == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (pool == null) {
                    //logger.info("connection pool created");
                    pool = new ConnectionPoolHolder();
                }
            }
        }
        return pool;
    }


    public final Connection getConnection() {
        // todo
        //logger.info("connect");
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.info("connection error", e);
            throw new RuntimeException(e);
        }
    }


}
