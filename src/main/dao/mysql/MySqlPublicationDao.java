package main.dao.mysql;

import main.dao.AbstractDao;
import main.dao.PersistException;
import main.entities.Publication;
import main.entities.SubscriptionPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DAO implementation for Publication class and MySQL database.
 */
public class MySqlPublicationDao extends AbstractDao<Integer,Publication> {
    /**
     * Bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");
    /**
     * Allow using setters in this class.
     */
    private class PersistPublication extends Publication {
        public void setId(int id){
            super.setId(id);
        }
    }

    public MySqlPublicationDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("PUBLICATION.SELECT");
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("PUBLICATION.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("PUBLICATION.DELETE");
    }

    @Override
    public String getCreateQuery() {
        return queries.getString("PUBLICATION.INSERT");
    }

    @Override
    protected List<Publication> parseResultSet(ResultSet rs) throws PersistException {
        List<Publication> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistPublication publication = new PersistPublication();
                publication.setId(rs.getInt("id"));
                publication.setName(rs.getString("name"));
                publication.setDescription(rs.getString("description"));
                publication.setPrice(rs.getFloat("price"));
                result.add(publication);
            }
        }catch (Exception e){
            throw new PersistException();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Publication object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setFloat(3, object.getPrice());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Publication object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setFloat(3, object.getPrice());
            statement.setInt(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Publication create() throws PersistException {
        Publication publication = new Publication();
        return persist(publication);
    }

    /**
     * @param part part of subscription
     * @return publication corresponding to part
     * @throws PersistException
     */
    public Publication getPublication(SubscriptionPart part) throws PersistException{
        List<Publication> list;
        String sql = queries.getString("PUBLICATION.SUBS_PART");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, part.getId());
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }

        return list.get(0);
    }
}
