package org.jdbcframework.factory;

/**
 * Created by LJT on 2015/11/24.
 */
public class ConnectionFactoryBoss {


    /**
     * get connectionFactoryBuilder by connectionFactoryBoss
     * @return connectionFactoryBuilder
     * @throws Exception
     */
    public static ConnectionFactoryBuilder getConnectionFactoryBuilderByBoss() throws Exception{
        return ConnectionFactoryBuilder.getConnectionFactoryBuilder();
    }
}
