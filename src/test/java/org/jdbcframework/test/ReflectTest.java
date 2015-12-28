package org.jdbcframework.test;

import junit.framework.*;
import org.jdbcframework.factory.ConnectionFactory;
import org.jdbcframework.factory.ConnectionFactoryBoss;
import org.jdbcframework.base.Connections;
import org.jdbcframework.entity.News;
import org.jdbcframework.properties.PropertiesLoad;
import org.jdbcframework.transaction.Transaction;
import org.junit.Before;

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

 /*   public void test1() throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        n.setId(6);
        n.setUrl("http:12312313123233asdasd");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.save(n);
        int b = 3;
        n.setUrl("1233213");
        //connections.delete(n);
        int a = 5;
        tx.commit();
        connections.close();
    }

    public void test2()throws Exception{
        connections = connectionFactory.getConnections();
        News n = new News();
        n.setId(9);
        n.setUrl("http:12312313asdasdsadaas");
        n.setTitle("hhhahahah ");
        n.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Transaction tx = connections.beginTransaction();
        connections.update(n);
        n.setTitle("dashendaiwofei");
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

/*    public void test4() throws Exception{
        connections = connectionFactory.getConnections();
        Transaction tx = connections.beginTransaction();
        for(int i = 0; i < 10000; i++) {
            News news = new News();
            news.setUrl("asd");
            news.setTitle("asd");
            news.setTimestamp(new Timestamp(System.currentTimeMillis() + i));
            connections.save(news);
        }
        tx.commit();
        connections.close();
    }*/

    public void testPageQuery() throws Exception{
        connections = connectionFactory.getConnections();
        Transaction tx = connections.beginTransaction();
        long a = System.currentTimeMillis();
        List<News> list = (List<News>)connections.getAll(News.class, new String());
        tx.commit();
        connections.close();
        long b = System.currentTimeMillis();
        System.out.println((double)(b-a)/1000);
    }

/*    public void testInsertOneM() throws Exception{
        connections = connectionFactory.getConnections();
        NewsDao newsDao = (NewsDao) NewsDao.getAuthInstance(NewsDao.class);
        newsDao.setConnections(connections);
        News news = new News();
        news.setTimestamp(new Timestamp(System.currentTimeMillis()));
        news.setUrl("aop");
        news.setTitle("hahahahahah");
        news.setRoot(1);
        newsDao.save(news);
    }*/
}
