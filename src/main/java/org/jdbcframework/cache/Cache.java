package org.jdbcframework.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 2015/12/1.
 */
public class Cache {

    private int index = 0;

    private Map<Integer, Object> cacheMap = new HashMap<Integer, Object>();

    private Map<Integer, String> optionMap = new HashMap<Integer, String>();

    public Map<Integer, String> getOptionMap() {
        return optionMap;
    }

    public void setOptionMap(Map<Integer, String> optionMap) {
        this.optionMap = optionMap;
    }

    public Map<Integer, Object> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<Integer, Object> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void cacheAdd(Integer index, Object obj){
        cacheMap.put(index, obj);
    }

    public boolean isContainsCache(Object obj){
        return cacheMap.containsValue(obj);
    }

    public void optionAdd(Integer index, String cmd){
        optionMap.put(index, cmd);
    }

    public boolean isContainsOption(Object obj){
        return optionMap.containsValue(obj);
    }

    public String getOptionByKey(Integer index){
        return optionMap.get(index);
    }

    public Object getCacheByKey(Integer index){
        return cacheMap.get(index);
    }

    public boolean isHaveCmd(Object obj, String cmd){
        List<Integer> list = new ArrayList<>();
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

    public void cacheClear(){
        index = 0;
        cacheMap.clear();
        optionMap.clear();
    }
}
