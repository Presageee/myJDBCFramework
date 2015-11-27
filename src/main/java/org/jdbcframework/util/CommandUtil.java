package org.jdbcframework.util;

import org.jdbcframework.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJT on 2015/11/25.
 */
public class CommandUtil {

    /**
     * get insert sql
     * @param obj entity obj
     * @return sql
     * @throws Exception
     */
    public String getSaveStatement(Object obj) throws Exception{
        Class<? extends Object> entityClass = obj.getClass();
        StringBuffer sql = new StringBuffer();
        String className = getTableName(entityClass);
        sql.append("insert into " + className + " (");
        String temp = "";
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer params = new StringBuffer();
        for(Field field : fields){
            if(isAutoColumn(field) || isNotColumn(field))
                continue;
            params.append(temp + "?");
            if(!isPrimaryKey(field))
                sql.append(temp + getFieldColumn(field));
            else
                sql.append(temp + getFieldPrimaryKey(field));
            temp = ",";
        }
        sql.append(") values(" + params + ")");
        System.out.println(sql);
        return new String(sql);
    }

    /**
     * get update sql
     * @param obj entity obj
     * @return sql
     * @throws Exception
     */
    public String getUpdateStatement(Object obj) throws Exception{
        Class<? extends Object> entityClass = obj.getClass();
        StringBuffer sql = new StringBuffer();
        String className = getTableName(entityClass);
        sql.append("update " + className + " set ");
        String temp = "";
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer whereValue = new StringBuffer();
        for(Field field : fields){
            if(isNotColumn(field)) continue;
            if(isPrimaryKey(field)){
                Object value = getFieldValue(obj,field);
                whereValue.append("where " + getFieldPrimaryKey(field) + "=?");
            }else{
                if(isAutoColumn(field)) continue;
                Object value = getFieldValue(obj,field);
                sql.append(temp + getFieldColumn(field) + "=?");
                temp = ",";
            }
        }
        sql.append(" " + whereValue);
        System.out.println(sql);
        return new String(sql);
    }

    /**
     * get delete sql
     * @param obj entity obj
     * @return sql
     * @throws Exception
     */
    public String getDeleteStatement(Object obj) throws Exception{
        Class<? extends Object> entityClass = obj.getClass();
        StringBuffer sql = new StringBuffer();
        String className = StringUtil.getLowerCase(entityClass.getName());
        sql.append("delete from " + className + " ");
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer whereValue = new StringBuffer();
        for(Field field : fields){
            if(isPrimaryKey(field)){
                Object value = getFieldValue(obj, field);
                whereValue.append("where " + getFieldPrimaryKey(field) + "=?");
                break;
            }else{
                continue;
            }
        }
        sql.append(whereValue);
        System.out.println(sql);
        return new String(sql);
    }

    /**
     * if setValue at annotation,return value.else return ClassName
     * @param clazz annotation class
     * @return
     */
    private String getTableName(Class<? extends Object> clazz){
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
    private String getFieldColumn(Field field){
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
    private String getFieldPrimaryKey(Field field){
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
     * get field value by obj
     * @param obj this.obj
     * @param field obj.field
     * @return field value
     * @throws Exception
     */
    private Object getFieldValue(Object obj, Field field) throws Exception{
        String first = field.getName().substring(0, 1).toUpperCase();
        Method m = obj.getClass().getMethod("get" + first + field.getName().substring(1), new Class[]{});
        return m.invoke(obj, new Object[] {});
    }

    /**
     * is PrimaryKey?
     * @param field annotation field
     * @return
     */
    private boolean isPrimaryKey(Field field){
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
    private boolean isAutoColumn(Field field){
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
    private boolean isNotColumn(Field field){
        NotColumn notColumn = field.getAnnotation(NotColumn.class);
        if(notColumn == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * get insert params by obj
     * @param obj entity obj
     * @return param array
     * @throws Exception
     */
    public Object[] createInsertParams(Object obj) throws Exception{
        Class<? extends Object> entityClass = obj.getClass();
        List<Object> list = new ArrayList<Object>();
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields){
            if(!isAutoColumn(field) && !isNotColumn(field)){
                field.setAccessible(true);
                Object tmp = field.get(obj);
                list.add(tmp);
            }
        }
        return list.toArray();
    }

    /**
     * get PrimaryKeyParam
     * @param obj entity obj
     * @return param
     * @throws Exception
     */
    public Object createPrimaryKeyParam(Object obj) throws Exception{
        Class<? extends Object> entityClass = obj.getClass();
        Object tmp;
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields){
            if(isPrimaryKey(field)){
                field.setAccessible(true);
                tmp = field.get(obj);
                return tmp;
            }
        }
        return null;
    }

    /**
     * get update params
     * @param obj entity obj
     * @return param array
     * @throws Exception
     */
    public Object[] createUpdateParams(Object obj) throws Exception{
        Class<? extends Object> entityClass = obj.getClass();
        List<Object> list = new ArrayList<Object>();
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields){
            if(!isPrimaryKey(field) && !isNotColumn(field) && !isAutoColumn(field)){
                Object tmp;
                field.setAccessible(true);
                tmp = field.get(obj);
                list.add(tmp);
            }
        }
        list.add(createPrimaryKeyParam(obj));
        return list.toArray();
    }


}
