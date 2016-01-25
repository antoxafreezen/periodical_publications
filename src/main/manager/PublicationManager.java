package main.manager;

import main.dao.DaoFactory;
import main.dao.PersistException;
import main.dao.mysql.MySqlDaoFactory;
import main.dao.mysql.MySqlPublicationDao;
import main.database.ConnectionPool;
import main.entities.Publication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Class that manages actions with publications in persistent context.
 */
public class PublicationManager {
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
     * @param name publication name
     * @param description publication description
     * @param price publication price
     * @return created publication
     * @throws PersistException
     */
    public Publication create(String name, String description, Float price) throws PersistException {
        try (Connection connection = pool.getConnection()) {
            MySqlPublicationDao publicationDao = (MySqlPublicationDao) factory.getDao(Publication.class, connection);

            Publication publication = new Publication();
            publication.setName(name);
            publication.setPrice(price);
            publication.setDescription(description);

            return publicationDao.persist(publication);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * Update publication
     * @param publication publication to update
     * @throws PersistException
     */
    public void update(Publication publication) throws PersistException {
        try(Connection connection = pool.getConnection()) {
            MySqlPublicationDao publicationDao = (MySqlPublicationDao) factory.getDao(Publication.class, connection);
            publicationDao.update(publication);
        }catch (Exception e){
            throw new PersistException(e);
        }
    }

    /**
     * @return list of publications
     * @throws PersistException
     */
    public List<Publication> getPublications() throws PersistException{
        List<Publication> list;
        try(Connection connection = pool.getConnection()) {
            MySqlPublicationDao publicationDao = (MySqlPublicationDao) factory.getDao(Publication.class, connection);
            list = publicationDao.getAll();
        }catch (Exception e){
            throw new PersistException(e);
        }
        return list;
    }

    /**
     * Get publication by publication id
     * @param id publication id
     * @return publication corresponding to id
     * @throws PersistException
     */
    public Publication get(Integer id) throws PersistException {
        try (Connection connection = pool.getConnection()) {
            MySqlPublicationDao productDao = (MySqlPublicationDao) factory.getDao(Publication.class, connection);
            return productDao.getByPrimaryKey(id);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }
}
