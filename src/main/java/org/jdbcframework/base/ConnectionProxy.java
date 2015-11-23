package org.jdbcframework.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by LJT on 2015/11/23.
 */
public class ConnectionProxy implements InvocationHandler{
    private static final String COVERD = "close";
    private Object conn;
    private LinkedList<Connection> pool;
    private ConnectionProxy(Object obj, LinkedList<Connection> pool){
        this.conn = obj;
        this.pool = pool;
    }
    //get a Proxy Collection
    public static Connection getProxyCollection(Object obj, LinkedList<Connection> pool){
        Object _obj = Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                new Class[]{Connection.class}, new ConnectionProxy(obj, pool));
        return (Connection)_obj;
    }
    //shielding close method
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals(COVERD)){
            pool.add((Connection)proxy);
            return null;
        }
        return method.invoke(conn, args);
    }
}
