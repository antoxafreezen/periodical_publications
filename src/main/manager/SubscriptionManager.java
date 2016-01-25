package main.manager;

import main.dao.DaoFactory;
import main.dao.PersistException;
import main.dao.mysql.MySqlDaoFactory;
import main.dao.mysql.MySqlSubscriptionDao;
import main.dao.mysql.MySqlSubscriptionPartDao;
import main.database.ConnectionPool;
import main.entities.Subscription;
import main.entities.SubscriptionPart;

import java.sql.Connection;

/**
 * Class that manages actions with subscription in persistent context.
 */
public class SubscriptionManager {
    /**
     * Factory to create dao objects.
     */
    private static DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();
    /**
     * Connection pool to create connection to datasource.
     */
    private static ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * Insert publication into database
     * @param subscription to insert
     * @return created subscription
     * @throws PersistException
     */
    public Subscription create(Subscription subscription) throws PersistException {
        try(Connection connection = pool.getConnection()){
            Subscription sub;
            connection.setAutoCommit(false);
            try {
                MySqlSubscriptionDao subscriptionDao = (MySqlSubscriptionDao) factory.getDao(Subscription.class, connection);
                MySqlSubscriptionPartDao partDao = (MySqlSubscriptionPartDao) factory.getDao(SubscriptionPart.class, connection);
                sub = subscriptionDao.persist(subscription);
                for (SubscriptionPart part : subscription.getSubsParts()) {
                    part.setSubscription(sub);
                    sub.addSubscriptionPart(partDao.persist(part));
                }
                connection.commit();
            }catch (PersistException e){
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }
            connection.setAutoCommit(true);
            return sub;
        }catch (Exception e){
            throw new PersistException(e);
        }
    }
    /**
     * Update subscription
     * @param subscription subscription to update
     * @throws PersistException
     */
    public void update(Subscription subscription) throws PersistException {
        try(Connection connection = pool.getConnection()) {
            MySqlSubscriptionDao subscriptionDao = (MySqlSubscriptionDao) factory.getDao(Subscription.class, connection);
            subscriptionDao.update(subscription);
        }catch (Exception e){
            throw new PersistException(e);
        }
    }
}
