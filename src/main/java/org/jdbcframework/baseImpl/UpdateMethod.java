package org.jdbcframework.baseImpl;

import java.sql.SQLException;

/**
 * Created by LJT on 2015/11/26.
 */
public interface UpdateMethod {
    /**
     * insert ... select ...
     * @param sql sql statement
     * @return int : bool
     */
    public abstract int insert(String sql) throws SQLException;

    /**
     * common insert
     * @param sql sql statement
     * @param params param list
     * @return int : bool
     */
    public abstract int insert(String sql, Object[] params) throws SQLException;

    /**
     * common delete
     * @param sql sql statement
     * @return int : bool
     */
    public abstract int delete(String sql) throws SQLException;

    /**
     * conditional delete
     * @param sql statement
     * @param params param list
     * @return int : bool
     */
    public abstract int delete(String sql, Object[] params) throws SQLException;

    /**
     * common update
     * @param sql statement
     * @return int : bool
     */
    public abstract int update(String sql) throws SQLException;

    /**
     * common update
     * @param sql statement
     * @param params param list
     * @return int : bool
     */
    public abstract int update(String sql, Object[] params) throws SQLException;
}
