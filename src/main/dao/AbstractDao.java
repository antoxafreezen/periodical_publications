package main.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Abstract class implements create, read, update and delete operations with using JDBC.
 * @param <PrimaryKey> type of primary key
 * @param <T> persistent type of object
 */
public abstract class AbstractDao<PrimaryKey extends Integer,T extends Identified<PrimaryKey>> implements GenericDao<PrimaryKey,T>{
    /**
     * Connection to database.
     */
    protected Connection connection;

    /**
     * @return SQL query for getting all records from table in database.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getSelectQuery();

    /**
     * @return SQL query for updating record in database.
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?
     */
    public abstract String getUpdateQuery();

    /**
     * @return SQL query for deleting record from database.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     */
    public abstract String getDeleteQuery();

    /**
     * @return SQL query for inserting record to table in database.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    public abstract String getCreateQuery();

    /**
     * Parse ResultSet to List.
     * @return List of objects.
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

    /**
     * Sets INSERT query arguments according to <code>object</code> fields
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;
    /**
     * Sets UPDATE query arguments according to <code>object</code> fields
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    public AbstractDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public T persist(T object) throws PersistException {
        T persistInstance;
        String sql = getCreateQuery();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            prepareStatementForInsert(statement,object);
            int count = statement.executeUpdate();
            if (count != 1){
                throw new PersistException("On persist modify more than 1 record " + count);
            }
        }catch (Exception e){
            Logger.getLogger(this.getClass()).error(e.getMessage());
            throw new PersistException(e);
        }
        sql = getSelectQuery() + " WHERE id = last_insert_id()";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)){
                throw new PersistException("Exception on finding by primary key new persist data.");
            }
            persistInstance = list.iterator().next();
        }catch (Exception e){
            Logger.getLogger(this.getClass()).error(e.getMessage());
            throw new PersistException(e);
        }
        Logger.getLogger(this.getClass()).info(object.getClass() + "have been inserted to database.");
        return persistInstance;
    }

    @Override
    public T getByPrimaryKey(Integer primaryKey) throws PersistException {
        List<T> list;
        String sql = getSelectQuery() + " WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, primaryKey);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        }catch (Exception e){
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0){
            return null;
        }
        if (list.size() > 1){
            throw new PersistException("Received more than 1 record.");
        }
        return list.iterator().next();
    }

    @Override
    public void update(T object) throws PersistException {
        String sql = getUpdateQuery();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1){
                throw new PersistException("On update modify more than 1 record " + count);
            }
        }catch (Exception e){
            Logger.getLogger(this.getClass()).error(e.getMessage());
            throw new PersistException(e);
        }
        Logger.getLogger(this.getClass()).info(object.getClass() + "have been updated.");
    }

    @Override
    public void delete(T object) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
            throw new PersistException(e);
        }
        Logger.getLogger(this.getClass()).info(object.getClass() + "have been deleted from database.");
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        }catch (Exception e){
            throw new PersistException(e);
        }
        return list;
    }
}
