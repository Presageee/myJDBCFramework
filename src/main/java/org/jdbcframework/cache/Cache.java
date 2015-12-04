package org.jdbcframework.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 2015/12/1.
 */
public class Cache {

    //map key
    private int index = 0;

    //cache map
    private Map<Integer, Object> cacheMap = new HashMap<Integer, Object>();

    //cmd map
    private Map<Integer, String> optionMap = new HashMap<Integer, String>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * add obj to cache map
     * @param index key
     * @param obj value
     */
    public void cacheAdd(Integer index, Object obj){
        cacheMap.put(index, obj);
    }

    /**
     * if obj is contains in cache map,return true
     * @param obj entity obj
     * @return
     */
    public boolean isContainsCache(Object obj){
        return cacheMap.containsValue(obj);
    }

    /**
     * add obj to option map
     * @param index key
     * @param cmd value
     */
    public void optionAdd(Integer index, String cmd){
        optionMap.put(index, cmd);
    }

    /**
     * get option obj by key
     * @param index key
     * @return obj
     */
    public String getOptionByKey(Integer index){
        return optionMap.get(index);
    }

    /**
     * get cache obj by key
     * @param index key
     * @return obj
     */
    public Object getCacheByKey(Integer index){
        return cacheMap.get(index);
    }

    /**
     * if it have cmd mapper obj,return true
     * @param obj value
     * @param cmd sql cmd
     * @return
     */
    public boolean isHaveCmd(Object obj, String cmd){
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < cacheMap.size(); i++){
            if (cacheMap.get(new Integer(i)).equals(obj)){
                list.add(new Integer(i));
            }
        }
        boolean flag = false;
        for (int j = 0; j < list.size(); j++){
            if(optionMap.get(list.get(j)).equals(cmd)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * clear cache map and option map and index
     */
    public void cacheClear(){
        index = 0;
        cacheMap.clear();
        optionMap.clear();
    }
}
