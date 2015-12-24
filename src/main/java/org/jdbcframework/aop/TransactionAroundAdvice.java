package org.jdbcframework.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.jdbcframework.annotation.AutoTransactionalControl;
import org.jdbcframework.base.Connections;
import org.jdbcframework.transaction.Transaction;

import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * Created by LJT on 2015/12/24.
 */
public class TransactionAroundAdvice implements MethodInterceptor {

    /**
     * for the method have annotation, intercept it and add some behavior
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Class entityClass = o.getClass();
        Object rvt = null;
        if (method.getAnnotation(AutoTransactionalControl.class) != null) {//is have annotation
            Method method1 = entityClass.getMethod("getConnections", null);//class must have method that name is getConnections
            Connections connections = null;
            Transaction tx = null;
            try {
                connections = (Connections) method1.invoke(o, null);//reflect get connections
                tx = connections.beginTransaction();
                tx.setTransaction(method.getAnnotation(AutoTransactionalControl.class).value());
                rvt = methodProxy.invokeSuper(o, objects);
                tx.commit();
            } catch (Exception e){
                tx.rollback();
                e.printStackTrace();
            }finally {
                try {
                    connections.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        } else {
            rvt = methodProxy.invokeSuper(o, objects);
        }
        return rvt;
    }
}
