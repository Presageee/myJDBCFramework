package org.jdbcframework.factory;

import org.jdbcframework.base.Connections;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by LJT on 2015/11/23.
 */
public class ConnectionFactory {
    private volatile static ConnectionFactory connectionFactory;

    private ConnectionFactory(String driver) throws ClassNotFoundException{
        Class.forName(driver);
    }

    private ConnectionFactory(DataSource dataSource){
        setDataSource(dataSource);
    }

    public static ConnectionFactory getConnectionFactory(DataSource dataSource){
        if(connectionFactory == null){
            synchronized (ConnectionFactory.class){
                if(connectionFactory == null){
                    connectionFactory = new ConnectionFactory(dataSource);
                }
            }
        }
        return connectionFactory;
    }

    public static ConnectionFactory getConnectionFactory(String driver) throws ClassNotFoundException{
        if(connectionFactory == null){
            synchronized (ConnectionFactory.class){
                if(connectionFactory == null){
                    connectionFactory = new ConnectionFactory(driver);
                }
            }
        }
        return connectionFactory;
    }

    private Connection getConnection(){
        Connection conn = null;
        try{
            if(dataSource == null){
                conn = DriverManager.getConnection(ConnectionFactoryBuilder.url, ConnectionFactoryBuilder.username, ConnectionFactoryBuilder.password);
                conn.setAutoCommit(false);
                return conn;
            }else{
                conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                return conn;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    private DataSource dataSource = null;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connections getConnections() throws SQLException{
        return new Connections(getConnection());
    }
}
