package org.jdbcframework.test;

import junit.framework.*;
import org.jdbcframework.base.Query;
import org.jdbcframework.entity.Methodset;
import org.jdbcframework.factory.ConnectionFactory;
import org.jdbcframework.factory.ConnectionFactoryBoss;
import org.jdbcframework.base.Connections;
import org.jdbcframework.entity.News;
import org.jdbcframework.properties.PropertiesLoad;
import org.jdbcframework.transaction.Transaction;
import org.junit.Before;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

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

/*    public void test1() throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        //n.setId(12344);
        n.setUrl("http:12312313123233asdasd");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.save(n);
        tx.commit();
        connections.close();
    }

    public void test2()throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        n.setId(1);
        n.setUrl("http:12312313asdasdsadaas");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.update(n);
        tx.commit();
        connections.close();
    }*/

/*    public void test3() throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        n.setId(1);
        n.setUrl("http:12312313");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.delete(n);
        tx.commit();
        connections.close();
    }*/

    /*public void test4() throws Exception{
        connections = connectionFactory.getConnections();
        Transaction tx = connections.beginTransaction();
        List<News> list = (List<News>)connections.queryAll(News.class, null);
        tx.commit();
        connections.close();
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getUrl());
        }
    }*/

    public void testPageQuery() throws Exception{
        connections = connectionFactory.getConnections();
        Query query = connections.createQuery("select * from news");
        List<News> list = (List<News>)query.getPageQuery(News.class, 1, 2);
        connections.close();
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getTitle() + list.get(i).getUrl());
        }
    }
}
