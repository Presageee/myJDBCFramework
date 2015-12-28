package org.jdbcframework.classmap;

import java.lang.reflect.Field;
import java.sql.ResultSet;

import static org.jdbcframework.util.TableUtil.*;

/**
 * Created by LJT on 2015/11/28.
 */
public class ReflectEntity {
    /**
     * reflect object for clazz
     *
     * @param rs    database resultSet
     * @param clazz Object class
     * @return
     */
    public static Object mapping(ResultSet rs, Class<? extends Object> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Object obj = null;
        try {
            obj = clazz.newInstance();
            for (Field field : fields) {
                if (!isNotColumn(field)) {
                    if (isPrimaryKey(field)) {
                        field.setAccessible(true);
                        field.set(obj, rs.getObject(getPrimaryKeyFieldName(field)));
                    } else if (isAutoColumn(field)) {
                        field.setAccessible(true);
                        field.set(obj, rs.getObject(getAutoColumnFieldName(field)));
                    } else {
                        field.setAccessible(true);
                        field.set(obj, rs.getObject(getColumnFieldName(field)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
