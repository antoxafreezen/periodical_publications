package main.dao;

import java.io.Serializable;

/**
 * Interface defines objects, which have identified field;
 * @param <PrimaryKey> Serializable type of primary key.
 */
public interface Identified<PrimaryKey extends Serializable> {
    /**
     * @return Object's ID value from database.
     */
    public PrimaryKey getId();
}
