package org.jdbcframework.classmap;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LJT on 2015/11/23.
 */
public interface Mapping<T> {
    public abstract T tranform(ResultSet rs) throws SQLException;
}
