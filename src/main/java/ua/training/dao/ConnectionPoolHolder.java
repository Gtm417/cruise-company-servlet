package ua.training.dao;

import org.apache.commons.dbcp.BasicDataSource;
import ua.training.exception.DBConnectionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ConnectionPoolHolder {
    private static volatile ConnectionPoolHolder pool;
    private BasicDataSource dataSource;

    private ConnectionPoolHolder() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(bundle.getString("db.url"));
        ds.setUsername("db.root");
        ds.setPassword("db.password");
        ds.setDriverClassName("db.driver");
        ds.setMinIdle(Integer.parseInt(bundle.getString("db.minIdle")));
        ds.setMaxIdle(Integer.parseInt(bundle.getString("db.maxIdle")));
        ds.setInitialSize(Integer.parseInt(bundle.getString("db.initialSize")));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(bundle.getString("db.maxOpenStatement")));
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
            //logger.info("connection error", e);
            throw new DBConnectionException(e);
        }
    }


}
