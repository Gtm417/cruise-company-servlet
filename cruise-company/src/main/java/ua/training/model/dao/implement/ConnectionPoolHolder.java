package ua.training.model.dao.implement;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPoolHolder {
    private static volatile ConnectionPoolHolder pool;
    private DataSource dataSource;

    private ConnectionPoolHolder(){
        init();
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

    private void init() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/cruise_company_servlet?serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("1234");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        dataSource = ds;
    }


    public final Connection connect() {
        //logger.info("connect");
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            //logger.info("connection error", e);
            throw new RuntimeException(e);
        }
    }



}
