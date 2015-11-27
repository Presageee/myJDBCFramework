package org.jdbcframework.entity;

import org.jdbcframework.annotation.AutoColumn;
import org.jdbcframework.annotation.Column;
import org.jdbcframework.annotation.PrimaryKey;
import org.jdbcframework.annotation.TableName;

import java.sql.Timestamp;

/**
 * Created by LJT on 2015/11/25.
 */
@TableName("news")
public class News {
    @AutoColumn
    @PrimaryKey
    private int id;

    @Column
    private String url;

    @Column
    private String title;

    @Column
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
