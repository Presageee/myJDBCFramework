package org.jdbcframework.util;

import org.jdbcframework.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
                //Object value = getFieldValue(obj,field);
                whereValue.append("where " + getFieldPrimaryKey(field) + "=?");
            }else{
                if(isAutoColumn(field)) continue;
                //Object value = getFieldValue(obj,field);
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
                //Object value = getFieldValue(obj, field);
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

    public String getQueryStatement(Class<? extends Object> clazz){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from " + getTableName(clazz));
        System.out.println(sql);
        return new String(sql);
    }

    public String getQueryStatement(Class<? extends Object> clazz, String where){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from " + getTableName(clazz) + " " + where);
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
