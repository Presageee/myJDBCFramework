package org.jdbcframework.baseImpl;

import org.jdbcframework.classmap.Mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 2015/11/26.
 */
public interface QueryMethod {
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
}
