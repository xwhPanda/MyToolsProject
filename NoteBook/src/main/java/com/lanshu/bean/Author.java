package com.lanshu.bean;

import java.io.Serializable;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/14 11:55
 */
public class Author implements Serializable{
    /* 姓名 */
    private String name;
    /* 简介地址 */
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
