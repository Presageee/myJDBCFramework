package org.jdbcframework.factory;

import org.jdbcframework.properties.PropertiesLoad;
import javax.sql.DataSource;

/**
 * Created by LJT on 2015/11/23.
 */
public class ConnectionFactoryBuilder {
    public static String driver = null;

    public static String username = null;

    public static String password = null;

    public static String url = null;

    public static int poolSize = -1;

    public static int loginTime = -1;

    public static String isUsePool = "NULL";

    private ConnectionFactory connectionFactory;

    private volatile static ConnectionFactoryBuilder connectionFactoryBuilder;

    private ConnectionFactoryBuilder() throws Exception{
        if(isUsePool == null || isUsePool.equals("FALSE")){
            connectionFactory = ConnectionFactory.getConnectionFactory(driver);
        }else{
            connectionFactory = ConnectionFactory.getConnectionFactory(dataSourceInit());
        }
    }

    /**
     * get a ConnectionFactoryBuilder
     * @param path path of database.properties
     * @return
     * @throws Exception
     */
    public static ConnectionFactoryBuilder getConnectionFactoryBuilder() throws Exception{
        if(connectionFactoryBuilder == null){
            synchronized (ConnectionFactoryBuilder.class){
                if(connectionFactoryBuilder == null){
                    connectionFactoryBuilder = new ConnectionFactoryBuilder();
                }
            }
        }
        return connectionFactoryBuilder;
    }

    /**
     * dataSource initialization
     * @return
     */
    private DataSource dataSourceInit(){
        DataSource dataSource = null;
        if(poolSize == -1 && loginTime == -1){
            dataSource = new ConnectionPool(driver, url,
                    username, password);
            return dataSource;
        }else if(poolSize == -1){
            dataSource = new ConnectionPool(driver, url,
                    username, password, loginTime);
            return dataSource;
        }else{
            dataSource = new ConnectionPool(driver, url,
                    username, password, poolSize);
            return dataSource;
        }
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
