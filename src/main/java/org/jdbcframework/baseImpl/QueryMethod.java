package org.jdbcframework.baseImpl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 2015/11/26.
 */
public interface QueryMethod {
/*    *//**
     * common's select
     * @throws SQLException
     *//*
    public abstract void query() throws SQLException;

    *//**
     * conditional's select
     * @throws SQLException
     *//*
    public abstract void queryByParams() throws SQLException;*/

    /**
     * orm's select
     * @param clazz entityClass
     * @return List data list
     */
    public abstract void queryByClass(Class<? extends Object> clazz) throws SQLException;

    /**
     * conditional orm's select
     * @param clazz entityClass
     * @return List :data list
     */
    public abstract void queryByClassAndParams(Class<? extends Object> clazz) throws SQLException;

    /**
     * common select
     * @param sql sql statement
     * @return list of map
     * @throws SQLException
     */
    public abstract void queryForMapList() throws SQLException;

    /**
     * conditional's select
     * @param sql sql statement
     * @param params param list
     * @return list of map
     * @throws SQLException
     */
    public abstract void queryByParamsForMapList() throws SQLException;

    /**
     * common's select
     * @param sql sql statement
     * @return ResultSet
     */
    public abstract ResultSet OriginalQuery() throws SQLException;

    /**
     * conditional select
     * @param sql sql statement
     * @param params param list
     * @return ResultSet
     */
    public abstract ResultSet OriginalQueryByParams() throws SQLException;

    /**
     * get data of targetPage
     * @param page targetPage
     * @param size page size
     * @return data of page
     * @throws SQLException
     */
    public abstract List<?> getPageQuery(Class<? extends Object> clazz, int page, int size) throws SQLException;

    /**
     * get data of targetPage(set params)
     * @param page targetPage
     * @param size page size
     * @return data of page
     * @throws SQLException
     */
    public abstract List<?> getPageQueryByParams(Class<? extends Object> clazz, int page, int size) throws SQLException;
}
