package main.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for managing persistent objects.
 * @param <PrimaryKey> type of primary key
 * @param <T> type of persistent object
 */
public interface GenericDao<PrimaryKey extends Serializable, T extends Identified<PrimaryKey>> {
    /**
     * Create new record and corresponding object.
     * @return created object.
     * @throws PersistException
     */
    public T create() throws PersistException;

    /**
     * Create new record from <code>object</code>.
     * @param object object to persist.
     * @return persisted object.
     * @throws PersistException
     */
    public T persist(T object) throws PersistException;

    /**
     * @param key primary key of record and corresponding object.
     * @return object with corresponding <code>key</code> or null.
     * @throws PersistException
     */
    public T getByPrimaryKey(PrimaryKey key) throws PersistException;

    /**
     * Update existing record from <code>object</code>.
     * @param object updated object.
     * @throws PersistException
     */
    public void update(T object) throws PersistException;

    /**
     * Delete record from <code>object</code>.
     * @param object deleted object.
     * @throws PersistException
     */
    public void delete(T object) throws PersistException;

    /**
     * @return list of objects from all records of database.
     * @throws PersistException
     */
    public List<T> getAll() throws PersistException;
}
