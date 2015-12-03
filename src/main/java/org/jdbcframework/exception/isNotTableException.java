package org.jdbcframework.exception;

/**
 * Created by LJT on 2015/12/3.
 */
public class isNotTableException extends RuntimeException{
    public isNotTableException(){
        super();
    }

    public isNotTableException(String message){
        super(message);
    }
}
