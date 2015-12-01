package org.jdbcframework.transaction;

import org.jdbcframework.base.Connections;
import org.jdbcframework.cache.Cache;
import org.jdbcframework.util.CommandUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.jdbcframework.util.CommandUtil.*;
/**
 * Created by LJT on 2015/11/23.
 */
public class Transaction {

    private Connection conn;

    private Connections connections;

    //private PreparedStatement preState;

    //private CommandUtil util;

    //private Cache cache;

    public Transaction(Connection conn, Connections connections) throws SQLException{
        this.conn = conn;
        this.connections = connections;
        //this.preState = preState;
        //this.util = util;
        //this.cache = cache;
        conn.setAutoCommit(false);
    }

    public void commit() throws Exception {
        analyzeMap();
        conn.commit();
    }

    public void rollback() throws SQLException{
        conn.rollback();
    }

    private void analyzeMap() throws Exception{
        for (int i = 0; i < connections.getCache().getIndex(); i++){
            String tmp = connections.getCache().getOptionByKey(i);
            Object obj = connections.getCache().getCacheByKey(i);
            connections.syncBufferToDataBase(tmp, obj);
        }
    }


}
