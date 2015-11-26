package org.jdbcframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by LJT on 2015/11/25.
 */
public class StatementUtil {

    public static String getSaveStatement(Object obj){
        Class<? extends Object> entityClass = obj.getClass();
        StringBuffer sql = new StringBuffer();
        String className = StringUtil.getLowerCase(entityClass.getName());
        sql.append("insert into ");
        sql.append( className + " ");
        sql.append("(");
        String temp = "";
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer params = new StringBuffer();
        for(Field field : fields){
            String first = field.getName().substring(0,1).toUpperCase();
            String getMethodName = "get" + first + field.getName().substring(1);

            try{
                Method m = entityClass.getMethod(getMethodName, new Class[] {});
                Object value = m.invoke(obj, new Object[] {});
                if(!field.getType().getName().equals(java.lang.Integer.class.getName())){
                    params.append(temp + "'" + value + "'");
                }else{
                    params.append(temp + value);
                }
            }catch (Exception e){

            }
            sql.append(temp + field.getName());
            temp = ",";
        }
        sql.append(") values(");
        sql.append(params);
        sql.append(")");
        System.out.println(sql);
        return new String(sql);
    }

    public static String getUpdateStatement(Object obj){
        Class<? extends Object> entityClass = obj.getClass();
        StringBuffer sql = new StringBuffer();
        String className = StringUtil.getLowerCase(entityClass.getName());
        sql.append("update ");
        sql.append(className + " set ");
        String temp = "";
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer params = new StringBuffer();
        StringBuffer whereValue = new StringBuffer();
        boolean isFirst = true;
        for(Field field : fields){
            String first = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + first + field.getName().substring(1);
            try{
                Method m = entityClass.getMethod(getMethodName, new Class[] {});
                Object value = m.invoke(obj, new Object[] {});
                if(getMethodName.indexOf("Id") != -1 && isFirst){
                    whereValue.append("where " + field.getName() + "='" + value + "'");
                }else{
                    sql.append(temp + field.getName() + "='" + value + "'");
                    temp = ",";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        sql.append(" " + whereValue);
        System.out.println(sql);
        return new String(sql);
    }

    public static String getDeleteStatement(Object obj){
        Class<? extends Object> entityClass = obj.getClass();
        StringBuffer sql = new StringBuffer();
        String className = StringUtil.getLowerCase(entityClass.getName());
        sql.append("delete from " + className + " ");
        String tmep = "";
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer whereValue = new StringBuffer();
        for(Field field : fields){
            String first = field.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + first + field.getName().substring(1);
            try{
                Method m = entityClass.getMethod(getMethodName, new Class[] {});
                Object value = m.invoke(obj, new Object[] {});
                if(getMethodName.indexOf("Id") != -1 ){
                    whereValue.append("where " + field.getName() + "='" + value + "'");
                    break;
                }else{
                    continue;
                }
            }catch (Exception e ){

            }
        }
        sql.append(whereValue);
        System.out.println(sql);
        return new String(sql);
    }
}
