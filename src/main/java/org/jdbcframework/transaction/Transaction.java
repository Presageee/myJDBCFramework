package org.jdbcframework.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by LJT on 2015/11/23.
 */
public class Transaction {
    private Connection conn;

    public Transaction(Connection conn) throws SQLException{
        this.conn = conn;
        conn.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException{
        conn.rollback();
    }
}
