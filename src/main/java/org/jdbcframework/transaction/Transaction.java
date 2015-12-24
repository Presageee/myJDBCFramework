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

    public static final int READ_UNCOMMITED = 1;

    public static final int READ_COMMITED = 2;

    public static final int REPEATABLE_READ = 3;

    public static final int SERIALIZABLE = 4;

    private Connection conn = null;

    private Connections connections;


    public Transaction(Connection conn, Connections connections) throws SQLException{
        this.conn = conn;
        this.connections = connections;//??maybe cause a deadlock???i don't know
        conn.setAutoCommit(false);
    }

    /**
     * commit transaction
     * @throws Exception
     */
    public void commit() throws Exception {
        analyzeMap();
        conn.commit();
    }

    /**
     * rollback transaction
     * @throws SQLException
     */
    public void rollback() throws SQLException{
        conn.rollback();
    }

    /**
     * analyze cache
     * @throws Exception
     */
    private void analyzeMap() throws Exception{
        for (int i = 0; i < connections.getCache().getIndex(); i++){
            String tmp = connections.getCache().getOptionByKey(i);
            Object obj = connections.getCache().getCacheByKey(i);
            connections.syncBufferToDataBase(tmp, obj);
        }
    }

    public void setTransaction(int value) throws SQLException{
        switch (value){
            case READ_COMMITED:
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                break;
            case READ_UNCOMMITED:
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                break;
            case REPEATABLE_READ:
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                break;
            case SERIALIZABLE:
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                break;
            default:
                break;
        }
    }

}
