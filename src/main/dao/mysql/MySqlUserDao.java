package main.dao.mysql;

import main.dao.AbstractDao;
import main.dao.PersistException;
import main.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DAO implementation for User class and MySQL database.
 */
public class MySqlUserDao extends AbstractDao<Integer,User> {
    /**
     * Bundle with SQL queries.
     */
    private ResourceBundle queries = ResourceBundle.getBundle("query");

    /**
     * Allow using setters in this class.
     */
    private class PersistUser extends User{
        public void setId(int id){
            super.setId(id);
        }

        public void setAdmin(boolean admin){
            super.setAdmin(admin);
        }
    }

    public MySqlUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return queries.getString("USER.SELECT");
    }

    @Override
    public String getUpdateQuery() {
        return queries.getString("USER.UPDATE");
    }

    @Override
    public String getDeleteQuery() {
        return queries.getString("USER.DELETE");
    }

    @Override
    public String getCreateQuery() {
        return queries.getString("USER.INSERT");
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        List<User> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getInt("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setSecond_name(rs.getString("second_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                user.setAddress(rs.getString("address"));
                result.add(user);
            }
        }catch (Exception e){
            throw new PersistException();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getFirst_name());
            statement.setString(2, object.getSecond_name());
            statement.setString(3, object.getAddress());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getPassword());
            statement.setBoolean(6, object.isAdmin());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getFirst_name());
            statement.setString(2, object.getSecond_name());
            statement.setString(3, object.getAddress());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getPassword());
            statement.setBoolean(6, object.isAdmin());
            statement.setInt(7, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public User create() throws PersistException {
        User user = new User();
        return persist(user);
    }

    /**
     * @param email user's email
     * @return user who has this email
     * @throws PersistException
     */
    public User getByEmail(String email) throws PersistException {
        List<User> list;
        String sql = queries.getString("USER.SELECT") + " WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }
}
