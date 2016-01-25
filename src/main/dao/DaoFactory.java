package main.dao;

/**
 * Object factory to work with database.
 * @param <Context> context to work with datasource. It is <code>Connection</code> for database.
 */
public interface DaoFactory<Context> {
    /**
     * DAO object creator for corresponding context.
     * @param <Context> context to work with datasource. It is <code>Connection</code> for database.
     */
    interface DaoCreator<Context>{
        /**
         * Create DAO object.
         * @return created DAO object.
         */
        GenericDao create(Context context);
    }

    /**
     * Get DAO object to work with persistent properties of object.
     * @param dtoClass requested entity class for DAO object.
     * @param context datasource context.
     * @return DAO object, according to dtoClass parameter.
     * @throws PersistException
     */
    GenericDao getDao(Class dtoClass, Context context) throws PersistException;
}
