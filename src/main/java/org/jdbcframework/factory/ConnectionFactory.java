package org.jdbcframework.factory;

import org.jdbcframework.base.Connections;
import org.jdbcframework.util.CommandUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by LJT on 2015/11/23.
 */
public class ConnectionFactory {
    private volatile static ConnectionFactory connectionFactory;

    private DataSource dataSource = null;

    private ConnectionFactory(String driver) throws ClassNotFoundException{
        Class.forName(driver);
    }

    private ConnectionFactory(DataSource dataSource){
        setDataSource(dataSource);
    }

    /**
     * get private static connectionFactory
     * @param dataSource connection pool
     * @return singleton obj
     */
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

    /**
     * get private static connectionFactory
     * @param driver jdbc driver
     * @return singleton obj
     * @throws ClassNotFoundException
     */
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

    /**
     * get connection
     * @return
     */
    private Connection getConnection(){
        Connection conn = null;
        try{
            if(dataSource == null){
                conn = DriverManager.getConnection(ConnectionFactoryBuilder.url, ConnectionFactoryBuilder.username, ConnectionFactoryBuilder.password);
                return conn;
            }else{
                conn = dataSource.getConnection();
                return conn;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * get Connections
     * @return connections obj
     * @throws SQLException
     */
    public Connections getConnections() throws SQLException{
        return new Connections(getConnection(), new CommandUtil());
    }
}
