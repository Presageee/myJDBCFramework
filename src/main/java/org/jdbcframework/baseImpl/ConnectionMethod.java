package org.jdbcframework.baseImpl;
import org.jdbcframework.base.Query;
import org.jdbcframework.base.Update;
import org.jdbcframework.classmap.Mapping;
import org.jdbcframework.transaction.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 2015/11/23.
 */
public interface ConnectionMethod {


    /**
     * start transaction management
     * @return Transaciton transaction examples
     */
    public abstract Transaction beginTransaction();

    /**
     * select by sql statement
     * @return query object
     */
    public abstract Query createQuery(String sql);

    /**
     * select by sql statement and set params
     * @param sql sql statement
     * @param params list of params
     * @return
     */
    public abstract Query createQuery(String sql, Object[] params);

    /**
     * update by sql statement
     * @return update object
     */
    public abstract Update createUpdate(String sql);

    /**
     * update by sql statement and set params
     * @param sql sql statement
     * @param params list of params
     * @return update object
     */
    public abstract Update createUpdate(String sql, Object[] params);

    /**
     * close Connection
     * @param conn this.conn
     */
    public abstract void close(Connection conn) throws SQLException;

    /**
     * close PreparedStatement
     * @param preStat this.preStat
     */
    public abstract void close(PreparedStatement preStat) throws SQLException;

    /**
     * close ResultSet
     * @param rs this.rs
     */
    public abstract void close(ResultSet rs) throws SQLException;

    /**
     * insert obj
     * @param obj pojo object
     * @throws SQLException
     */
    public abstract void save(Object obj) throws Exception;

    /**
     * delete obj
     * @param obj pojo object
     * @throws SQLException
     */
    public abstract void delete(Object obj) throws Exception;

    /**
     * update obj
     * @param obj pojo object
     * @throws SQLException
     */
    public abstract void update(Object obj) throws Exception;

    /**
     * query by obj
     * @param clazz entity
     * @return list of entity
     * @throws Exception
     */
    public abstract Object get(Class<? extends Object> clazz, Object obj) throws Exception;

    /**
     * query entity and set condition
     * @param clazz
     * @param where
     * @return
     * @throws Exception
     */
    public abstract List<?> queryAll(Class<? extends Object> clazz, String where) throws Exception;
}
