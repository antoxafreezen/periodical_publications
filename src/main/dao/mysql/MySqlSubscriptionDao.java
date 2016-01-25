package main.dao.mysql;

import main.dao.AbstractDao;
import main.dao.PersistException;
import main.entities.Subscription;
import main.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DAO implementation for Subscription class and MySQL database.
 */
public class MySqlSubscriptionDao extends AbstractDao<Integer,Subscription> {
    /**
     * Bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");
    /**
     * Allow using setters in this class.
     */
    private class PersistSubscription extends Subscription {
        public void setId(int id){
            super.setId(id);
        }
    }

    public MySqlSubscriptionDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("SUBSCRIPTION.SELECT");
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("SUBSCRIPTION.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("SUBSCRIPTION.DELETE");
    }

    @Override
    public String getCreateQuery() {
        return queries.getString("SUBSCRIPTION.INSERT");
    }

    protected java.sql.Date convert(java.util.Date date) {
        if (date == null) { return null; }
        return new java.sql.Date(date.getTime());
    }

    @Override
    protected List<Subscription> parseResultSet(ResultSet rs) throws PersistException {
        List<Subscription> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistSubscription subscription = new PersistSubscription();
                subscription.setId(rs.getInt("id"));
                subscription.setStartDate(rs.getDate("start_date"));
                subscription.setEndDate(rs.getDate("end_date"));
                subscription.setPrice(rs.getFloat("price"));
                result.add(subscription);
            }
        }catch (Exception e){
            throw new PersistException();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Subscription object) throws PersistException {
        try {
            statement.setInt(1, object.getUser().getId());
            statement.setFloat(2, object.getPrice());
            statement.setDate(3, convert(object.getStartDate()));
            statement.setDate(4, convert(object.getEndDate()));
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Subscription object) throws PersistException {
        try {
            statement.setInt(1, object.getUser().getId());
            statement.setFloat(2, object.getPrice());
            statement.setDate(3, convert(object.getStartDate()));
            statement.setDate(4, convert(object.getEndDate()));
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public Subscription create() throws PersistException {
        Subscription subscription = new Subscription();
        return persist(subscription);
    }

    /**
     * @param user user in system
     * @return list of subscription which belong to user
     * @throws PersistException
     */
    public List<Subscription> getAllBelonging(User user) throws PersistException {
        List<Subscription> list;
        String sql = queries.getString("SUBSCRIPTION.SELECT") + " WHERE id_user = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }
}
