package org.jdbcframework.util;

/**
 * Created by LJT on 2015/11/30.
 */
public class PageUtil {
    //page size
    private int size;
    //page index
    private int pageIndex;

    public PageUtil(int pageIndex, int size){
        this.size = size;
        this.pageIndex = pageIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageCmd(String sql){
        StringBuffer tmp = new StringBuffer();
        tmp.append(sql);
        tmp.append(" limit " + String.valueOf(getBeginIndex()) + "," + String.valueOf(size));
        return tmp.toString();
    }

    private int getBeginIndex(){
        return (pageIndex - 1) * size;
    }
}
