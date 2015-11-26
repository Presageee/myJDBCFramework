package org.jdbcframework.test;

import junit.framework.*;
import org.jdbcframework.factory.ConnectionFactory;
import org.jdbcframework.factory.ConnectionFactoryBoss;
import org.jdbcframework.base.Connections;
import org.jdbcframework.entity.News;
import org.jdbcframework.properties.PropertiesLoad;
import org.jdbcframework.transaction.Transaction;
import org.junit.Before;
import java.sql.Timestamp;

/**
 * Created by LJT on 2015/11/25.
 */
public class ReflectTest extends TestCase{
    static ConnectionFactory connectionFactory;
    static Connections connections;
    @Before
    public void setUp() throws  Exception{
        PropertiesLoad.config("/db.properties");
        connectionFactory = ConnectionFactoryBoss.getConnectionFactoryBuilderByBoss().getConnectionFactory();
    }

    public void test1() throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        n.setId(12344);
        n.setUrl("http:12312313");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.save(n);
        //tx.commit();
        connections.close();
    }

    public void test2()throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        n.setId(1234);
        n.setUrl("http:12312313asdasdsadaas");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.update(n);
        tx.commit();
        connections.close();
    }

    public void test3() throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        n.setId(1234);
        n.setUrl("http:12312313");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.delete(n);
        tx.commit();
        connections.close();
    }
}
