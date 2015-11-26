package org.jdbcframework.base;

import org.jdbcframework.baseImpl.UpdateMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LJT on 2015/11/26.
 */
public class Update implements UpdateMethod{
    private Connection conn;

    private PreparedStatement preStat;

    private ResultSet rs;

    private String sql;

    private Object[] params = null;

    public Update(String sql, Connection conn){
        this.sql = sql;
        this.conn = conn;
    }

    public Update(String sql, Connection conn, Object[] params){
        this.sql = sql;
        this.conn = conn;
        this.params = params;
    }

    private PreparedStatement getPreparedStatement(String sql, Object[] params) throws SQLException{
        PreparedStatement tmpPreStat= conn.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            tmpPreStat.setObject(i+1, params[i]);
        }
        return tmpPreStat;
    }

    @Override
    public int insert(String sql) throws SQLException {
        preStat = conn.prepareStatement(sql);
        int num = preStat.executeUpdate();
        return num;
    }

    @Override
    public int insert(String sql, Object[] params) throws SQLException{
        preStat = getPreparedStatement(sql, params);
        int num = preStat.executeUpdate();
        return num;

    }

    @Override
    public int delete(String sql) throws SQLException{
        preStat = conn.prepareStatement(sql);
        int num = preStat.executeUpdate();
        return num;
    }

    @Override
    public int delete(String sql, Object[] params) throws SQLException {
        preStat = getPreparedStatement(sql, params);
        int num = preStat.executeUpdate();
        return num;
    }

    @Override
    public int update(String sql)throws SQLException{
        preStat = conn.prepareStatement(sql);
        int num = preStat.executeUpdate();
        return num;
    }

    @Override
    public int update(String sql, Object[] params) throws SQLException {
        preStat = getPreparedStatement(sql, params);
        int num = preStat.executeUpdate();
        return num;
    }
}
