package org.jdbcframework.base;

import org.jdbcframework.baseImpl.ConnectionMethod;
import org.jdbcframework.cache.Cache;
import org.jdbcframework.transaction.Transaction;
import org.jdbcframework.util.CommandUtil;
import static org.jdbcframework.classmap.ReflectEntity.*;

import java.sql.*;
import java.util.*;


/**
 * Created by LJT on 2015/11/23.
 */
public class Connections implements ConnectionMethod {

    private Connection conn = null;

    private PreparedStatement preStat = null;

    private ResultSet rs = null;

    private CommandUtil util;

    private Cache cache;

    public Connections(Connection conn, CommandUtil util){
        this.conn = conn;
        this.util = util;
        this.cache = new Cache();
    }

    public void close() throws SQLException {
        cache.cacheClear();
        close(conn);
        close(preStat);
        close(rs);
    }

    public void syncBufferToDataBase(String cmd, Object obj) throws Exception{
        if (cmd.equals("save")){
            saved(obj);
        }else if (cmd.equals("update")){
            updated(obj);
        }else if (cmd.equals("delete")){
            deleted(obj);
        }
    }

    private void saved(Object obj) throws Exception{
        String sql = util.getSaveStatement(obj);
        preStat = conn.prepareStatement(sql);
        setPreStatParams(preStat, util.createInsertParams(obj));
        preStat.executeUpdate();
    }

    private void updated(Object obj) throws Exception{
        String sql = util.getUpdateStatement(obj);
        preStat = conn.prepareStatement(sql);
        setPreStatParams(preStat, util.createUpdateParams(obj));
        preStat.executeUpdate();
    }

    private void deleted(Object obj) throws Exception{
        String sql = util.getDeleteStatement(obj);
        preStat = conn.prepareStatement(sql);
        preStat.setObject(1, util.createPrimaryKeyParam(obj));
        preStat.executeUpdate();
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    private void setPreStatParams(PreparedStatement preStat, Object ... objects) throws Exception{
        if (objects != null && objects.length > 0){
            for (int i = 0; i < objects.length; i++){
                preStat.setObject(i + 1, objects[i]);
            }
        }
    }

    @Override
    public Transaction beginTransaction() throws SQLException{
        return new Transaction(conn, this);
    }

    @Override
    public Query createQuery(String sql) {
        return new Query(sql, conn);
    }

    @Override
    public Query createQuery(String sql, Object[] params) {
        return new Query(sql, conn, params);
    }

    @Override
    public Update createUpdate(String sql) {
        return new Update(sql, conn);
    }

    @Override
    public Update createUpdate(String sql, Object[] params) {
        return new Update(sql, conn, params);
    }


    @Override
    public void close(Connection conn) throws SQLException{
        if (conn != null){
            if (!conn.getAutoCommit())
                conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Override
    public void close(PreparedStatement preStat) throws SQLException{
        if (preStat != null)
            preStat.close();
    }

    @Override
    public void close(ResultSet rs) throws SQLException{
        if (rs != null)
            rs.close();
    }

    @Override
    public void save(Object obj) throws Exception {
        if (!cache.isContainsCache(obj)){
            cache.cacheAdd(cache.getIndex(), obj);
            cache.optionAdd(cache.getIndex(), "save");
            cache.setIndex(cache.getIndex() + 1);
        }else {
            if (!cache.isHaveCmd(obj, "save")){
                cache.cacheAdd(cache.getIndex(), obj);
                cache.optionAdd(cache.getIndex(), "save");
                cache.setIndex(cache.getIndex() + 1);
            }
        }
    }

    @Override
    public void delete(Object obj) throws Exception {
        if (!cache.isContainsCache(obj)){
            cache.cacheAdd(cache.getIndex(), obj);
            cache.optionAdd(cache.getIndex(), "delete");
            cache.setIndex(cache.getIndex() + 1);
        }else {
            if (!cache.isHaveCmd(obj, "delete")){
                cache.cacheAdd(cache.getIndex(), obj);
                cache.optionAdd(cache.getIndex(), "delete");
                cache.setIndex(cache.getIndex() + 1);
            }
        }
    }

    @Override
    public void update(Object obj) throws Exception {
        if (!cache.isContainsCache(obj)){
            cache.cacheAdd(cache.getIndex(), obj);
            cache.optionAdd(cache.getIndex(), "update");
            cache.setIndex(cache.getIndex() + 1);
        }else {
            if (!cache.isHaveCmd(obj, "update")){
                cache.cacheAdd(cache.getIndex(), obj);
                cache.optionAdd(cache.getIndex(), "update");
                cache.setIndex(cache.getIndex() + 1);
            }
        }
    }

    @Override
    public Object get(Class<? extends Object> clazz, Object obj) throws Exception {
        if (cache.getCacheByKey((Integer)obj) != null){
            return cache.getCacheByKey((Integer)obj);
        }else {
            String sql = util.getQueryStatement(clazz);
            preStat = conn.prepareStatement(sql);
            preStat.setObject(1, obj);
            rs = preStat.executeQuery();
            Object entity = null;
            rs.next();
            entity = mapping(rs, clazz);
            return entity;
        }
    }

    @Override
    public List<?> queryAll(Class<? extends Object> clazz, String condition) throws Exception {
        String sql = util.getQueryStatement(clazz, null);
        preStat = conn.prepareStatement(sql);
        rs = preStat.executeQuery();
        List<Object> list = new ArrayList<Object>();
        while (rs.next()){
            list.add(mapping(rs, clazz));
        }
        return list;
    }

}
