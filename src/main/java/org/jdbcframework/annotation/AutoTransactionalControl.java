package org.jdbcframework.annotation;

import org.jdbcframework.transaction.Transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LJT on 2015/12/23.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoTransactionalControl {
    int value() default Transaction.READ_COMMITTED;
}
