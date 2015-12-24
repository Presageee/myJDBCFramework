package org.jdbcframework.entity;

import org.jdbcframework.annotation.AutoTransactionalControl;
import org.jdbcframework.aop.AutoTransaction;
import org.jdbcframework.base.Connections;

/**
 * Created by LJT on 2015/12/24.
 */
public class NewsDao extends AutoTransaction {

    private Connections connections;

    public Connections getConnections() {
        return connections;
    }

    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    @AutoTransactionalControl
    public void save(News news) throws Exception{
        connections.save(news);
    }

    public void update(News news) throws Exception{
        connections.update(news);
    }
}
