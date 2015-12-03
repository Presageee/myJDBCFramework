package org.jdbcframework.exception;

/**
 * Created by LJT on 2015/12/3.
 */
public class withoutPrimaryKeyException extends RuntimeException {
    public withoutPrimaryKeyException(String message){
        super(message);
    }
}
