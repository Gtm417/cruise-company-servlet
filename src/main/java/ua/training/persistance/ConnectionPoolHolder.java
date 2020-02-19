package ua.training.persistance;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.exception.DBConnectionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static ua.training.persistance.DbConnectionConstants.*;


public class ConnectionPoolHolder {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolHolder.class);


    private static volatile ConnectionPoolHolder pool;
    private BasicDataSource dataSource;

    private ConnectionPoolHolder() {
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_DATABASE);
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(bundle.getString(DB_URL));
        ds.setUsername(bundle.getString(DB_USER));
        ds.setPassword(bundle.getString(DB_PASSWORD));
        ds.setDriverClassName(bundle.getString(DB_DRIVER));
        ds.setMinIdle(Integer.parseInt(bundle.getString(DB_MIN_IDLE)));
        ds.setMaxIdle(Integer.parseInt(bundle.getString(DB_MAX_IDLE)));
        ds.setInitialSize(Integer.parseInt(bundle.getString(DB_INITIAL_SIZE)));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(bundle.getString(DB_MAX_OPEN_STATEMENT)));
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
