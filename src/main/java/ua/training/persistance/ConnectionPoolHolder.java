package ua.training.persistance;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import ua.training.exception.DBConnectionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.Logger;


public class ConnectionPoolHolder {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolHolder.class);

    private static volatile ConnectionPoolHolder pool;
    private BasicDataSource dataSource;

    private ConnectionPoolHolder() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(bundle.getString("db.url"));
        ds.setUsername(bundle.getString("db.user"));
        ds.setPassword(bundle.getString("db.password"));
        ds.setDriverClassName(bundle.getString("db.driver"));
        ds.setMinIdle(Integer.parseInt(bundle.getString("db.minIdle")));
        ds.setMaxIdle(Integer.parseInt(bundle.getString("db.maxIdle")));
        ds.setInitialSize(Integer.parseInt(bundle.getString("db.initialSize")));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(bundle.getString("db.maxOpenStatement")));
        dataSource = ds;
    }

    public static ConnectionPoolHolder pool() {
        if (pool == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (pool == null) {
                    LOGGER.info("connection pool created");
                    pool = new ConnectionPoolHolder();
                }
            }
        }
        return pool;
    }


    public Connection getConnection() {
        LOGGER.info("connect");
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.info("connection error", e);
            throw new DBConnectionException(e);
        }
    }


}
