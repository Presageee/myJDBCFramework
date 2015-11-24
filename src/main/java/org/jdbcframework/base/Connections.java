package org.jdbcframework.base;

import org.jdbcframework.classmap.Mapping;
import org.jdbcframework.transaction.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by LJT on 2015/11/23.
 */
public class Connections implements MethodSet{
    private Connection conn;

    private PreparedStatement preStat;

    private ResultSet rs;

    public Connections(Connection conn){
        this.conn = conn;
    }

    public void close() throws SQLException {
        close(conn);
        close(preStat);
        close(rs);
    }

    private PreparedStatement getPreparedStatement(String sql, Object[] params) throws SQLException{
        PreparedStatement tmpPreStat= conn.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            tmpPreStat.setObject(i+1, params[i]);
        }
        return tmpPreStat;
    }

    @Override
    public Transaction beginTransaction() {
        return new Transaction(conn);
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
    public int insert(String sql) throws SQLException{
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

    @Override
    public void close(Connection conn) throws SQLException{
        conn.close();
    }

    @Override
    public void close(PreparedStatement preStat) throws SQLException{
        preStat.close();
    }

    @Override
    public void close(ResultSet rs) throws SQLException{
        rs.close();
    }
}
