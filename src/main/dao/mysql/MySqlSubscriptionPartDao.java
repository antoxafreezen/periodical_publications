package main.dao.mysql;

import main.dao.AbstractDao;
import main.dao.PersistException;
import main.entities.Subscription;
import main.entities.SubscriptionPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DAO implementation for SubscriptionPart class and MySQL database.
 */
public class MySqlSubscriptionPartDao extends AbstractDao<Integer,SubscriptionPart> {
    /**
     * Bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");
    /**
     * Allow using setters in this class.
     */
    private class PersistSubscriptionPart extends SubscriptionPart {
        public void setId(int id){
            super.setId(id);
        }
    }

    public MySqlSubscriptionPartDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("SUBSCRIPTION_PART.SELECT");
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("SUBSCRIPTION_PART.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("SUBSCRIPTION_PART.DELETE");
    }

    @Override
    public String getCreateQuery() {
        return queries.getString("SUBSCRIPTION_PART.INSERT");
    }

    @Override
    protected List<SubscriptionPart> parseResultSet(ResultSet rs) throws PersistException {
        List<SubscriptionPart> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistSubscriptionPart subscriptionPart = new PersistSubscriptionPart();
                subscriptionPart.setId(rs.getInt("id"));
                result.add(subscriptionPart);
            }
        }catch (Exception e){
            throw new PersistException();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, SubscriptionPart object) throws PersistException {
        try {
            statement.setInt(1, object.getSubscription().getId());
            statement.setInt(2, object.getPublication().getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, SubscriptionPart object) throws PersistException {
        try {
            statement.setInt(1, object.getSubscription().getId());
            statement.setInt(2, object.getPublication().getId());
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public SubscriptionPart create() throws PersistException {
        SubscriptionPart subscriptionPart = new SubscriptionPart();
        return persist(subscriptionPart);
    }

    /**
     * @param sub subscription
     * @return list of subscription parts belong to subscription
     * @throws PersistException
     */
    public List<SubscriptionPart> getAllBelonging(Subscription sub) throws PersistException {
        List<SubscriptionPart> list;
        String sql = queries.getString("SUBSCRIPTION_PART.SELECT") + " WHERE id_subs = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sub.getId());
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }
}
