package main.manager;

import main.dao.DaoFactory;
import main.dao.PersistException;
import main.dao.mysql.*;
import main.database.ConnectionPool;
import main.entities.Publication;
import main.entities.Subscription;
import main.entities.SubscriptionPart;
import main.entities.User;

import java.sql.Connection;
import java.util.List;

/**
 * Class that manages actions with users in persistent context.
 */
public class UserManager {
    /**
     * Factory to create dao objects.
     */
    private static DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();
    /**
     * Connection pool to create connection to datasource.
     */
    private static ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * @param email of user
     * @return user by email
     * @throws PersistException
     */
    public User get(String email) throws PersistException {
        try(Connection connection = pool.getConnection()){
            MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);
            MySqlSubscriptionDao subscriptionDao = (MySqlSubscriptionDao) factory.getDao(Subscription.class, connection);
            MySqlSubscriptionPartDao partDao = (MySqlSubscriptionPartDao) factory.getDao(SubscriptionPart.class, connection);
            MySqlPublicationDao publicationDao = (MySqlPublicationDao) factory.getDao(Publication.class, connection);

            User user = userDao.getByEmail(email);

            if (user != null){
                user.setSubscriptions(subscriptionDao.getAllBelonging(user));
                for (Subscription sub: user.getSubscriptions()){
                    sub.setUser(user);
                    sub.setSubsParts(partDao.getAllBelonging(sub));
                    for (SubscriptionPart subPart: sub.getSubsParts()){
                        subPart.setSubscription(sub);
                        Publication publication = publicationDao.getPublication(subPart);
                        subPart.setPublication(publication);
                    }
                }
            }

            return user;

        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * Log in user in system
     * @param email user's email
     * @param password user's password
     * @return logged user
     * @throws PersistException
     */
    public User login(String email, String password) throws PersistException {
        User user = get(email);
        if (user != null && password.equals(user.getPassword())){
            return user;
        }
        return null;
    }

    /**
     * Insert user into database
     * @param firstName of user
     * @param secondName of user
     * @param address of user
     * @param email of user
     * @param password of user
     * @return created user
     * @throws PersistException
     */
    public User create(String firstName, String secondName, String address, String email, String password) throws PersistException {
        try(Connection connection = pool.getConnection()){

            MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);
            User user = new User();
            user.setFirst_name(firstName);
            user.setSecond_name(secondName);
            user.setAddress(address);
            user.setEmail(email);
            user.setPassword(password);

            return userDao.persist(user);

        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * @return list of users in system
     * @throws PersistException
     */
    public List<User> getDefaulters() throws PersistException {
        try(Connection connection = pool.getConnection()) {
            MySqlUserDao userDao = (MySqlUserDao) factory.getDao(User.class, connection);
            MySqlSubscriptionDao subscriptionDao = (MySqlSubscriptionDao) factory.getDao(Subscription.class, connection);
            MySqlSubscriptionPartDao partDao = (MySqlSubscriptionPartDao) factory.getDao(SubscriptionPart.class, connection);

            List<User> defaulters = userDao.getAll();
            for (User defaulter: defaulters){
                defaulter.setSubscriptions(subscriptionDao.getAllBelonging(defaulter));
                for (Subscription sub: defaulter.getSubscriptions()){
                    sub.setUser(defaulter);
                    sub.setSubsParts(partDao.getAllBelonging(sub));
                    for (SubscriptionPart subPart: sub.getSubsParts()){
                        subPart.setSubscription(sub);
                    }
                }
            }
            return defaulters;
        }catch (Exception e){
            throw new PersistException(e);
        }
    }
}
