package org.jdbcframework.base;

import org.jdbcframework.baseImpl.ConnectionMethod;
import org.jdbcframework.transaction.Transaction;
import org.jdbcframework.util.StatementUtil;

import java.sql.*;


/**
 * Created by LJT on 2015/11/23.
 */
public class Connections implements ConnectionMethod {
    private Connection conn = null;

    private PreparedStatement preStat = null;

    private ResultSet rs = null;


    public Connections(Connection conn){
        this.conn = conn;
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
    public void save(Object obj) throws SQLException {
        String sql = StatementUtil.getSaveStatement(obj);
        preStat = conn.prepareStatement(sql);
        preStat.executeUpdate();
    }

    @Override
    public void delete(Object obj) throws SQLException {
        String sql = StatementUtil.getDeleteStatement(obj);
        preStat = conn.prepareStatement(sql);
        preStat.executeUpdate();
    }

    @Override
    public void update(Object obj) throws SQLException {
        String sql = StatementUtil.getUpdateStatement(obj);
        preStat = conn.prepareStatement(sql);
        preStat.executeUpdate();
    }
}
