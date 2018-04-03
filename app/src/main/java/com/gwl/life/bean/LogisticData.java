package com.gwl.life.bean;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.bean
 * 文件名： LogisticData
 * 创建者： GWL
 * 创建时间：2018/4/3 12:48
 * 描述  ： 物流信息实体类
 */
public class LogisticData {

    private String datetime;
    private String remark;
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "LogisticData{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
