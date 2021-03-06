package org.jdbcframework.properties;

import org.jdbcframework.factory.ConnectionFactoryBoss;
import org.jdbcframework.factory.ConnectionFactoryBuilder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by LJT on 2015/11/23.
 */
public class PropertiesLoad {
    /**
     * Load config
     * @param tmpPath .properties path
     */
    public static void config(String tmpPath) throws Exception{
        propertiesLoad(tmpPath);
    }

    /**
     * read properties
     * @param path properties path
     * @throws Exception
     */
    private static void propertiesLoad(String path) throws Exception{
        Properties properties = new Properties();
        String tmpPath = System.getProperty("user.dir");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(tmpPath + "/src" + path));
        properties.load(inputStream);
        ConnectionFactoryBuilder.driver = properties.getProperty("driver");
        ConnectionFactoryBuilder.url = properties.getProperty("url");
        ConnectionFactoryBuilder.username = properties.getProperty("username");
        ConnectionFactoryBuilder.password = properties.getProperty("password");
        if(properties.getProperty("loginTime") != null){
            ConnectionFactoryBuilder.loginTime = Integer.parseInt(properties.getProperty("loginTime"));
        }
        if(properties.getProperty("poolSize") != null){
            ConnectionFactoryBuilder.poolSize = Integer.parseInt(properties.getProperty("poolSize"));
        }
        if(properties.getProperty("isUsePool") != null){
            ConnectionFactoryBuilder.isUsePool = properties.getProperty("isUsePool");
        }
    }
}
