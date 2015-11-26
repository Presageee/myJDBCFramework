package org.jdbcframework.util;

/**
 * Created by LJT on 2015/11/26.
 */
public class StringUtil {

    public static String getLowerCase(String className){
        String tmp = className.substring(className.lastIndexOf(".") + 1);
/*        char tmpChar[] = tmp.toCharArray();
        for(int i = 0; i < tmpChar.length; i++){
            if( tmpChar[i] >= 'A' && tmpChar[i] <= 'Z'){
                tmpChar[i] += 32;
            }
        }
        tmp = new String(tmpChar);*/
        return tmp;
    }
}
