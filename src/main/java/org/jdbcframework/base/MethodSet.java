package org.jdbcframework.base;
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
public interface MethodSet {
    /**
     * the following are basic ways of database
     */


    /**
     * start transaction management
     * @return Transaciton transaction examples
     */
    public abstract Transaction beginTransaction();

    /**
     * common select
     * @param sql sql statement
     * @return ResultSet
     */
    public abstract ResultSet queryOriginal(String sql) throws SQLException;

    /**
     * conditional select
     * @param sql sql statement
     * @param params param list
     * @return ResultSet
     */
    public abstract ResultSet queryOriginal(String sql, Object[] params) throws SQLException;
    /**
     * common select
     * @param sql sql statement
     * @param mapping class mapping
     * @return List data list
     */
    public abstract List<?> query(String sql, Mapping mapping) throws SQLException;

    /**
     * conditional select
     * @param sql sql statement
     * @param params param list
     * @param mapping class mapping
     * @return List :data list
     */
    public abstract List<?> query(String sql, Object[] params, Mapping mapping) throws SQLException;

    /**
     * common select
     * @param sql sql statement
     * @return list of map
     * @throws SQLException
     */
    public abstract List<Map<String, Object>> queryForMapList(String sql) throws SQLException;
    /**
     * conditional select
     * @param sql sql statement
     * @param params param list
     * @return list of map
     * @throws SQLException
     */
    public abstract List<Map<String, Object>> queryForMapList(String sql,  Object[] params) throws SQLException;
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
}
