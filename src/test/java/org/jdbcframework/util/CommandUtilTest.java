package org.jdbcframework.util;

import junit.framework.TestCase;
import org.jdbcframework.entity.News;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by LJT on 2015/12/4.
 */
public class CommandUtilTest extends TestCase {
    CommandUtil commandUtil;

    private News n ;

    private List<Object> list = new ArrayList<Object>();

    @Before
    public void setUp(){
        commandUtil = new CommandUtil();
        n = new News();
        n.setId(1);
        n.setUrl("gitHub");
        n.setTitle("CommandUtilTest");
        n.setTimestamp(new Timestamp(1, 1, 1, 1, 1, 1, 1));
        n.setRoot(2);
    }

    @Test
    public void testGetSaveStatement() throws Exception{
        String sql = commandUtil.getSaveStatement(n);
        System.out.println(sql);
        String expected = "insert into news (url,title,timestamp) values(?,?,?)";
        assertEquals(expected, sql);
    }

    @Test
    public void testGetUpdateStatement() throws Exception{
        String sql = commandUtil.getUpdateStatement(n);
        System.out.println(sql);
        String expected = "update news set url=?,title=?,timestamp=? where id=?";
        assertEquals(expected, sql);
    }

    @Test
    public void testGetDeteleStatement() throws Exception{
        String sql = commandUtil.getDeleteStatement(n);
        System.out.println(sql);
        String expected = "delete from news where id=?";
        assertEquals(expected, sql);
    }

    @Test
    public void testGetQueryStatement() throws Exception{
        String sql = commandUtil.getQueryStatement(n.getClass());
        System.out.println(sql);
        String expected = "select * from news where id=?";
        assertEquals(expected, sql);
    }

    @Test
    public void testGetQueryStatementTwo() throws Exception{
        String sql = commandUtil.getQueryStatement(n.getClass(), " test");
        System.out.println(sql);
        String expected = "select * from news where test";
        assertEquals(expected, sql);
    }

    @Test
    public void testCreateInsertParams() throws Exception{
        Object[] objects = commandUtil.createInsertParams(n);
        list.add((Object) n.getUrl());
        list.add((Object) n.getTitle());
        list.add((Object) n.getTimestamp());
        assertArrayEquals(list.toArray(), objects);
    }

    @Test
    public void testCreatePrimaryKeyParam() throws Exception{
        Object objects = commandUtil.createPrimaryKeyParam(n);
        Object object = (Object)1;
        assertEquals(object, objects);
    }

    @Test
    public void testCreateUpdateParams() throws Exception{
        Object[] objects = commandUtil.createUpdateParams(n);
        list.add((Object) n.getUrl());
        list.add((Object) n.getTitle());
        list.add((Object) n.getTimestamp());
        list.add((Object) 1);
        assertArrayEquals(list.toArray(), objects);
    }
}
