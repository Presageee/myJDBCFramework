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
     * if field is PrimaryKey,return true;
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
     * if field is AutoColumn,return true;
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
     * if field is notColumn return true;
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
     * @param field unnameable field
     * @return
     */
    public final static String getColumnFieldName(Field field){
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
     * @param field  unnameable field
     * @return
     */
    public final static String getPrimaryKeyFieldName(Field field){
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

    /**
     * if setValue at annotation,return value, else return FieldName
     * @param field unnameable field
     * @return
     */
    public final static String getAutoColumnFieldName(Field field){
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

}
