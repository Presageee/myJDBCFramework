package org.jdbcframework.factory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Created by LJT on 2015/11/23.
 */
public class ConnectionPool implements DataSource{
    private int poolSize;
    private int loginTime;
    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(String driver, String url, String username, String password){
        this(driver, url, username, password, 10, 60000);
    }

    public ConnectionPool(String driver, String url, String username, String password, int poolSize){
        this(driver, url, username, password, poolSize, 60000);
    }

    public ConnectionPool(String driver, String url, String username, String password, int poolSize, int loginTime){
        try{
            this.poolSize = poolSize;
            setLoginTimeout(loginTime);
            Class.forName(driver);
            for(int i = 0; i < poolSize; i++){
                Connection conn = DriverManager.getConnection(url, username, password);
                conn = ConnectionProxy.getProxyCollection(conn, pool);//get a proxy connection
                pool.add(conn);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Connection getConnection(){
        Connection conn = null;
        try{
            if(pool.size() > 0){
                conn = pool.removeFirst();
                return conn;
            }else{
                throw new RuntimeException("without idle link");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.loginTime = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return loginTime;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
