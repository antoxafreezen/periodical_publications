package main.dao.mysql;

import main.dao.DaoFactory;
import main.dao.GenericDao;
import main.dao.PersistException;
import main.entities.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Object factory to work with MySQL database.
 * Singleton implementation.
 */
public class MySqlDaoFactory implements DaoFactory<Connection>{
    /**
     * Mapping between database entity classes and DAO implementation.
     */
    private Map<Class, DaoCreator> creators;
    /**
     * Instance of this class.
     */
    private static MySqlDaoFactory instance;

    @Override
    public GenericDao getDao(Class dtoClass, Connection connection) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null){
            throw new PersistException("Dao object for " + dtoClass + " not found");
        }
        return creator.create(connection);
    }

    /**
     * Empty constructor. Build mapping.
     */
    private MySqlDaoFactory(){
        creators = new HashMap<>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlUserDao(connection);
            }
        });
        creators.put(Publication.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlPublicationDao(connection);
            }
        });
        creators.put(Subscription.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlSubscriptionDao(connection);
            }
        });
        creators.put(SubscriptionPart.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlSubscriptionPartDao(connection);
            }
        });
    }

    /**
     * @return instance of this class.
     */
    public synchronized static MySqlDaoFactory getInstance(){
        if (instance == null) return new MySqlDaoFactory();
        return instance;
    }
}
