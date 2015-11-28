package org.jdbcframework.base;

import org.jdbcframework.baseImpl.ConnectionMethod;
import org.jdbcframework.transaction.Transaction;
import org.jdbcframework.util.CommandUtil;
import static org.jdbcframework.classmap.ReflectEntity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by LJT on 2015/11/23.
 */
public class Connections implements ConnectionMethod {
    private Connection conn = null;

    private PreparedStatement preStat = null;

    private ResultSet rs = null;

    private CommandUtil util;

    public Connections(Connection conn, CommandUtil util){
        this.conn = conn;
        this.util = util;
    }

    public void close() throws SQLException {
        close(conn);
        close(preStat);
        close(rs);
    }


    @Override
    public Transaction beginTransaction() {
        return new Transaction(conn);
    }

    @Override
    public Query createQuery(String sql) {
        return new Query(sql, conn);
    }

    @Override
    public Query createQuery(String sql, Object[] params) {
        return new Query(sql, conn, params);
    }

    @Override
    public Update createUpdate(String sql) {
        return new Update(sql, conn);
    }

    @Override
    public Update createUpdate(String sql, Object[] params) {
        return new Update(sql, conn, params);
    }


    @Override
    public void close(Connection conn) throws SQLException{
        if(conn != null)
            conn.close();
    }

    @Override
    public void close(PreparedStatement preStat) throws SQLException{
        if(preStat != null)
            preStat.close();
    }

    @Override
    public void close(ResultSet rs) throws SQLException{
        if(rs != null)
            rs.close();
    }

    @Override
    public void save(Object obj) throws Exception {
        String sql = util.getSaveStatement(obj);
        preStat = conn.prepareStatement(sql);
        setPreStatParams(preStat, util.createInsertParams(obj));
        preStat.executeUpdate();
    }

    @Override
    public void delete(Object obj) throws Exception {
        String sql = util.getDeleteStatement(obj);
        preStat = conn.prepareStatement(sql);
        preStat.setObject(1, util.createPrimaryKeyParam(obj));
        preStat.executeUpdate();
    }

    @Override
    public void update(Object obj) throws Exception {
        String sql = util.getUpdateStatement(obj);
        preStat = conn.prepareStatement(sql);
        setPreStatParams(preStat, util.createUpdateParams(obj));
        preStat.executeUpdate();
    }

    @Override
    public List<?> query(Class<? extends Object> clazz) throws Exception {
        String sql = util.getQueryStatement(clazz);
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        List<Object> list = new ArrayList<Object>();
        while(rs.next()){
            list.add(mapping(rs, clazz));
        }
        return list;
    }

    @Override
    public List<?> query(Class<? extends Object> clazz, String condition) throws Exception {
        String sql = util.getQueryStatement(clazz, condition);
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        List<Object> list = new ArrayList<Object>();
        while(rs.next()){
            list.add(mapping(rs, clazz));
        }
        return list;
    }

    private void setPreStatParams(PreparedStatement preStat, Object ... objects) throws Exception{
        if(objects != null && objects.length > 0){
            for(int i = 0; i < objects.length; i++){
                preStat.setObject(i + 1, objects[i]);
            }
        }
    }
}
