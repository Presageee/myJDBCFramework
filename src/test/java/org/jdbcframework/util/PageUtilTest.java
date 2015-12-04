package org.jdbcframework.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by LJT on 2015/12/4.
 */
public class PageUtilTest {
    private PageUtil pageUtil;

    @Before
    public void setUp(){
        pageUtil = new PageUtil(2, 10);
    }

    @Test
    public void testGetBeginIndex(){
        int target = pageUtil.getBeginIndex();
        assertEquals(10, target);
    }

    @Test
    public void testGetPageCmd(){
        String sql = pageUtil.getPageCmd("test + ");
        assertEquals("test +  limit 10,10", sql);
    }
}
