package org.jdbcframework.base;

import org.jdbcframework.baseImpl.QueryMethod;
import org.jdbcframework.util.PageUtil;

import static org.jdbcframework.classmap.ReflectEntity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 2015/11/26.
 */
public class Query implements QueryMethod {

    private Connection conn;

    private PreparedStatement preStat;

    private ResultSet rs;

    private String sql;

    private Object[] params = null;

    private PageUtil pageUtil;

    private List<Object> list = new ArrayList<Object>();

    private List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

    public Query(String sql, Connection conn){
        this.sql = sql;
        this.conn = conn;
        this.pageUtil = new PageUtil(-1, -1);
    }

    public Query(String sql, Connection conn, Object[] params){
        this.sql = sql;
        this.conn = conn;
        this.params = params;
        this.pageUtil = new PageUtil(-1, -1);
    }

    public List<?> getList(){
        if(getPage() == -1 || getSize() == -1) return list;
        else {
            int realPage = getPage();
            int realSize = getSize();
            List<Object> tmp = new ArrayList<Object>();
            int count = realPage * realSize;
            int begin = (realPage - 1) * realSize;
            if(count > list.size()){
                for(int i = begin; i < list.size(); i++){
                    tmp.add(list.get(i));
                }
            }else {
                for(int i = begin; i < begin + realSize; i++){
                    tmp.add(list.get(i));
                }
            }
            return tmp;
        }
    }

    public List<Map<String, Object>> getMapList(){
        if(getPage() == -1 || getSize() == -1) return mapList;
        else {
            int realPage = getPage();
            int realSize = getSize();
            List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
            int count = realPage * realSize;
            int begin = (realPage - 1) * realSize;
            if(count > mapList.size()){
                for(int i = begin; i < mapList.size(); i++){
                    tmp.add(mapList.get(i));
                }
            }else {
                for(int i = begin; i < begin + realSize; i++){
                    tmp.add(mapList.get(i));
                }
            }
            return tmp;
        }
    }

    public void setPageAndSize(int page, int size){
        pageUtil.setPageIndex(page);
        pageUtil.setSize(size);
    }

    private int getPage(){
        return pageUtil.getPageIndex();
    }

    private int getSize(){
        return pageUtil.getSize();
    }

    private PreparedStatement getPreparedStatement() throws SQLException{
        PreparedStatement tmpPreStat= conn.prepareStatement(sql);
        for(int i = 0; i < params.length; i++){
            tmpPreStat.setObject(i+1, params[i]);
        }
        return tmpPreStat;
    }

    @Override
    public void queryByClass(Class<? extends Object> clazz) throws SQLException{
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        list.clear();
        while(rs.next()){
            list.add(mapping(rs, clazz));
        }
    }

    @Override
    public void queryByClassAndParams(Class<? extends Object> clazz) throws SQLException{
        preStat = getPreparedStatement();
        rs = preStat.executeQuery();
        list.clear();
        while(rs.next()){
            list.add(mapping(rs, clazz));
        }
    }

    @Override
    public void queryForMapList() throws SQLException {
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        mapList.clear();
        Map<String, Object> map = null;
        ResultSetMetaData rsm = rs.getMetaData();
        int cnt = rsm.getColumnCount();
        while(rs.next()){
            map = new HashMap<String, Object>(cnt);
            for(int i = 0; i < cnt; i++){
                map.put(rsm.getColumnName(i), rs.getObject(i));
            }
            mapList.add(map);
        }
    }

    @Override
    public void queryByParamsForMapList() throws SQLException {
        preStat = getPreparedStatement();
        rs = preStat.executeQuery();
        mapList.clear();
        Map<String, Object> map = null;
        ResultSetMetaData rsm = rs.getMetaData();
        int cnt = rsm.getColumnCount();
        while(rs.next()){
            map = new HashMap<String, Object>(cnt);
            for(int i = 0; i < cnt; i++){
                map.put(rsm.getColumnName(i), rs.getObject(i));
            }
            mapList.add(map);
        }
    }

    @Override
    public ResultSet OriginalQuery() throws SQLException{
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        return rs;
    }

    @Override
    public ResultSet OriginalQueryByParams() throws SQLException{
        preStat = getPreparedStatement();
        rs = preStat.executeQuery();
        return rs;
    }

    @Override
    public List<?> getPageQuery(Class<? extends Object> clazz, int page, int size) throws SQLException {
        queryByClass(clazz);
        setPageAndSize(page, size);
        return getList();
    }

    @Override
    public List<?> getPageQueryByParams(Class<? extends Object> clazz, int page, int size) throws SQLException {
        queryByClassAndParams(clazz);
        setPageAndSize(page, size);
        return getList();
    }
}
