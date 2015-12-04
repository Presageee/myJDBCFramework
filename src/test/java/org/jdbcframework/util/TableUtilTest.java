package org.jdbcframework.util;

import junit.framework.TestCase;
import org.jdbcframework.entity.News;
import org.junit.Before;
import org.junit.Test;
import static org.jdbcframework.util.TableUtil.*;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * Created by LJT on 2015/12/4.
 */
public class TableUtilTest extends TestCase {
    private News n;

    @Before
    public void setUp(){
        n = new News();
        n.setId(2);
        n.setTitle("tableUtil");
        n.setUrl("gitHub");
        n.setTimestamp(new Timestamp(1, 1, 1, 1, 1, 1, 1));
        n.setRoot(1);
    }

    @Test
    public void testIsTable(){
        boolean flag = isTable(n.getClass());
        assertEquals(true, flag);
    }

    @Test
    public void testIsPrimaryKey(){
        Field[] fields = n.getClass().getDeclaredFields();
        boolean flag = isPrimaryKey(fields[0]);
        assertEquals(true, flag);
    }

    @Test
    public void testIsAutoColumn(){
        Field[] fields = n.getClass().getDeclaredFields();
        boolean flag = isAutoColumn(fields[1]);
        assertEquals(false, flag);
    }

    @Test
    public void testIsNotColumn(){
        Field[] fields = n.getClass().getDeclaredFields();
        boolean flag = isNotColumn(fields[4]);
        assertEquals(true, flag);
    }

    @Test
    public void testGetTableName(){
        String name = getTableName(n.getClass());
        assertEquals("news", name);
    }

    @Test
    public void testGetColumnFieldName(){
        Field[] fields = n.getClass().getDeclaredFields();
        String name = getColumnFieldName(fields[1]);
        assertEquals("url", name);
    }

    @Test
    public void testGetPrimaryKeyFieldName(){
        Field[] fields = n.getClass().getDeclaredFields();
        String name = getPrimaryKeyFieldName(fields[0]);
        assertEquals("id", name);
    }

    @Test
    public void testGetAutoColumnFieldName(){
        Field[] fields = n.getClass().getDeclaredFields();
        String name = getAutoColumnFieldName(fields[0]);
        assertEquals("id", name);
    }
}
