package org.jdbcframework.base;

import org.jdbcframework.baseImpl.QueryMethod;
import org.jdbcframework.classmap.Mapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 2015/11/26.
 */
public class Query implements QueryMethod {

    private Connection conn;

    private PreparedStatement preStat;

    private ResultSet rs;

    private String sql;

    private Object[] params = null;

    public Query(String sql, Connection conn){
        this.sql = sql;
        this.conn = conn;
    }
    public Query(String sql, Connection conn, Object[] params){
        this.sql = sql;
        this.conn = conn;
        this.params = params;
    }
    @Override
    public List<?> query(String sql, Mapping mapping) throws SQLException{
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        List<Object> list = new ArrayList<Object>();
        while(rs.next()){
            list.add(mapping.tranform(rs));
        }
        return list;
    }

    @Override
    public List<?> query(String sql, Object[] params, Mapping mapping) throws SQLException{
        preStat = getPreparedStatement(sql, params);
        rs = preStat.executeQuery();
        List<Object> list = new ArrayList<Object>();
        while(rs.next()){
            list.add(mapping.tranform(rs));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryForMapList(String sql) throws SQLException {
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        ResultSetMetaData rsm = rs.getMetaData();
        int cnt = rsm.getColumnCount();
        while(rs.next()){
            map = new HashMap<String, Object>(cnt);
            for(int i = 0; i < cnt; i++){
                map.put(rsm.getColumnName(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryForMapList(String sql, Object[] params) throws SQLException {
        preStat = getPreparedStatement(sql, params);
        rs = preStat.executeQuery();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        ResultSetMetaData rsm = rs.getMetaData();
        int cnt = rsm.getColumnCount();
        while(rs.next()){
            map = new HashMap<String, Object>(cnt);
            for(int i = 0; i < cnt; i++){
                map.put(rsm.getColumnName(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }

    private PreparedStatement getPreparedStatement(String sql, Object[] params) throws SQLException{
        PreparedStatement tmpPreStat= conn.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            tmpPreStat.setObject(i+1, params[i]);
        }
        return tmpPreStat;
    }

    @Override
    public ResultSet queryOriginal(String sql) throws SQLException{
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        return rs;
    }

    @Override
    public ResultSet queryOriginal(String sql, Object[] params) throws SQLException{
        preStat = getPreparedStatement(sql, params);
        rs = preStat.executeQuery();
        return rs;
    }
}
