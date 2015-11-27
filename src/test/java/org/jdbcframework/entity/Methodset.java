package org.jdbcframework.entity;

import org.jdbcframework.annotation.TableName;

import java.lang.reflect.Field;

/**
 * Created by LJT on 2015/11/27.
 */
public class Methodset {
    public static void getValue(Object obj){
        String tablename = obj.getClass().getAnnotation(TableName.class).value();
        System.out.println(tablename);
    }
}
