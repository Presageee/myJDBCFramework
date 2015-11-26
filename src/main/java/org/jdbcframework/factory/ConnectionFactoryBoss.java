package org.jdbcframework.factory;

/**
 * Created by LJT on 2015/11/24.
 */
public class ConnectionFactoryBoss {
    public static String path;
    public static ConnectionFactoryBuilder getConnectionFactoryBuilderByBoss() throws Exception{
        return ConnectionFactoryBuilder.getConnectionFactoryBuilder(path);
    }
}
