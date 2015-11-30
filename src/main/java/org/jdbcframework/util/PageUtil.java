package org.jdbcframework.util;

/**
 * Created by LJT on 2015/11/30.
 */
public class PageUtil {
    private int size;

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
}
