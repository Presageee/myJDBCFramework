package org.jdbcframework.aop;

import net.sf.cglib.proxy.Enhancer;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by LJT on 2015/12/23.
 */
public class AutoTransaction<T> {

    /**
     * Dynamically generate a new class
     * @param clazz
     * @return
     */
    public static Object getAuthInstance(Class <? extends Object> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new TransactionAroundAdvice());
        return enhancer.create();
    }
    /**
     * Generics
     */
    /*public static <T> Object getAuthInstance(T obj){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(new TransactionAroundAdvice());
        return enhancer.create();
    }*/
}
