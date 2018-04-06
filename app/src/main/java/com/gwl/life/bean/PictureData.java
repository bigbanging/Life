package com.gwl.life.bean;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.bean
 * 文件名： PictureData
 * 创建者： GWL
 * 创建时间：2018/4/5 11:31
 * 描述  ： 美女图片的实体类
 */
public class PictureData {
    //发布时间
    private String publishedAt;
    //图片的url
    private String pictureUrl;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
