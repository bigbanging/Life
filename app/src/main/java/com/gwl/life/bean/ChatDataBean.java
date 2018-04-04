package com.gwl.life.bean;

/**
 * 项目名： Life
 * 包名  ： com.gwl.life.bean
 * 文件名： ChatDataBean
 * 创建者： GWL
 * 创建时间：2018/4/4 9:59
 * 描述  ： 聊天数据的实体类
 */
public class ChatDataBean {
    /**
     * 判别聊天结果的类型 机器人应答 还是用户提问？
     */
    private int type;
    /**
     * 聊天的内容
     */
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
