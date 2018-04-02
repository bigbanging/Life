package com.gwl.life.bean;

import cn.bmob.v3.BmobUser;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.bean
 * 文件名： User
 * 创建者： GWL
 * 创建时间：2018/4/1 17:11
 * 描述  ： TODO
 */
public class User extends BmobUser {
    /**
     * 性别 true 为男 false为女
     */
    private Boolean sex;
    /**
     * 简介
     */
    private String intro;
    /**
     * 年龄
     */
    private Integer age;

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
