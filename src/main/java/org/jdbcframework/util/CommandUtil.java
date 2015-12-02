package org.jdbcframework.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.jdbcframework.util.TableUtil.*;

/**
 * Created by LJT on 2015/11/25.
 */
public class CommandUtil {

    /**
     * get insert sql
     * @param obj entity obj
     * @return insert sql
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
                sql.append(temp + getColumnFieldName(field));
            else
                sql.append(temp + getPrimaryKeyFieldName(field));
            temp = ",";
        }
        sql.append(") values(" + params + ")");
        System.out.println(sql);
        return new String(sql);
    }

    /**
     * get update sql
     * @param obj entity obj
     * @return update sql
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
                //Object value = getFieldValue(obj,field);
                whereValue.append("where " + getPrimaryKeyFieldName(field) + "=?");
            }else{
                if(isAutoColumn(field)) continue;
                //Object value = getFieldValue(obj,field);
                sql.append(temp + getColumnFieldName(field) + "=?");
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
     * @return delete sql
     * @throws Exception
     */
    public String getDeleteStatement(Object obj) throws Exception{
        Class<? extends Object> entityClass = obj.getClass();
        StringBuffer sql = new StringBuffer();
        String className = getTableName(entityClass);
        sql.append("delete from " + className + " ");
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer whereValue = new StringBuffer();
        for(Field field : fields){
            if(isPrimaryKey(field)){
                whereValue.append("where " + getPrimaryKeyFieldName(field) + "=?");
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
     * get query sql
     * @param clazz entityClass
     * @return query sql
     * @throws Exception
     */
    public String getQueryStatement(Class<? extends Object> clazz) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append("select * from " + getTableName(clazz));
        boolean isPrimaryKey = false;
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(isPrimaryKey(field)){
                isPrimaryKey = true;
                sql.append(" where " + getPrimaryKeyFieldName(field) + "=?");
                break;
            }
        }
        if(!isPrimaryKey) throw new Exception("have not primary key");
        System.out.println(sql);
        return new String(sql);
    }

    /**
     * get query sql
     * @param clazz entityClass
     * @param condition primaryKey name
     * @return query sql
     */
    public String getQueryStatement(Class<? extends Object> clazz, String condition){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from " + getTableName(clazz));
        if(condition != null){
            sql.append(" where" + condition);
        }
        System.out.println(sql);
        return new String(sql);
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
