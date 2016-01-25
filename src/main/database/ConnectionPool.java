package main.database;


import main.dao.PersistException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Pool of connection to database. Singleton implementation.
 */
public class ConnectionPool {
    /**
     * Datasource for pool of connection.
     */
    private BasicDataSource dataSource;
    /**
     * Instance of this class.
     */
    public static ConnectionPool instance;

    /**
     * Private constructor with datasource configurations.
     */

    private ConnectionPool(){

        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("freezeN4653178");
        dataSource.setUrl("jdbc:mysql://localhost:3306/periodical_publications?autoReconnect=true&characterEncoding=utf8");

        dataSource.setInitialSize(2);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(15);
    }

    /**
     * @return Instance of this class.
     */
    public static synchronized ConnectionPool getInstance(){
        if (instance == null) return new ConnectionPool();
        return instance;
    }

    /**
     * @return Connection to database.
     * @throws PersistException when connection errors happens.
     */
    public synchronized Connection getConnection() throws PersistException {
        try{
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}
