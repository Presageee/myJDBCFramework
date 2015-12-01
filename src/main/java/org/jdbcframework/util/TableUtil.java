package org.jdbcframework.util;

import org.jdbcframework.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by LJT on 2015/11/28.
 */
public class TableUtil {
    /**
     * is PrimaryKey?
     * @param field annotation field
     * @return
     */
    public final static boolean isPrimaryKey(Field field){
        PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
        if(primaryKey == null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * if AutoColumn?
     * @param field annotation field
     * @return
     */
    public final static boolean isAutoColumn(Field field){
        AutoColumn autoColumn = field.getAnnotation(AutoColumn.class);
        if(autoColumn == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * if notColumn?
     * @param field annotation field
     * @return
     */
    public final static boolean isNotColumn(Field field){
        NotColumn notColumn = field.getAnnotation(NotColumn.class);
        if(notColumn == null){
            return false;
        }else{
            return true;
        }
    }
    /**
     * if setValue at annotation,return value.else return ClassName
     * @param clazz annotation class
     * @return
     */
    public final static String getTableName(Class<? extends Object> clazz){
        if("".equals(clazz.getAnnotation(TableName.class).value())){
            return clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
        }else{
            return clazz.getAnnotation(TableName.class).value();
        }
    }

    /**
     * if setValue at annotation,return value.else return FieldName
     * @param field annotation field
     * @return
     */
    public final static String getFieldColumn(Field field){
        Column column = field.getAnnotation(Column.class);
        if(column != null){
            if("".equals(column.value())){
                return field.getName();
            }else{
                return column.value();
            }
        }
        return null;
    }

    /**
     * if setValue at annotation, return value, else return FieldName
     * @param field
     * @return
     */
    public final static String getFieldPrimaryKey(Field field){
        PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
        if(primaryKey != null){
            if("".equals(primaryKey.value())){
                return field.getName();
            }else{
                return primaryKey.value();
            }
        }
        return null;
    }

    public final static String getFieldAutoColumn(Field field){
        AutoColumn autoColumn = field.getAnnotation(AutoColumn.class);
        if(autoColumn != null){
            if("".equals(autoColumn.value())){
                return field.getName();
            }else {
                return autoColumn.value();
            }
        }
        return null;
    }
    /**
     * get field value by obj
     * @param obj this.obj
     * @param field obj.field
     * @return field value
     * @throws Exception
     */
    public final static Object getFieldValue(Object obj, Field field) throws Exception{
        String first = field.getName().substring(0, 1).toUpperCase();
        Method m = obj.getClass().getMethod("get" + first + field.getName().substring(1), new Class[]{});
        return m.invoke(obj, new Object[] {});
    }

}
