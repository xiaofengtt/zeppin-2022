/*
 * Message.java
 *
 * Created on 2004年10月21日, 下午2:34
 */

package com.whaty.platform.entity.basic;

/**
 * 站内短消息类
 * @author  chenjian
 */
public abstract class Message implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String title = "";
    
    private String body = "";
    
    private String senduser_id = "";
    
    private String senduser_name = "";
    
    private String time = "";
    
    private String getuser_id = "";
    
    private String getuser_name = "";
    
    private String status = "";
    
    /**
     * 属性 body 的获取方法。
     * @return 属性 body 的值。
     */
    public java.lang.String getBody() {
        return body;
    }
    
    /**
     * 属性 body 的设置方法。
     * @param body 属性 body 的新值。
     */
    public void setBody(java.lang.String body) {
        this.body = body;
    }
    
    /**
     * 属性 getuser_id 的获取方法。
     * @return 属性 getuser_id 的值。
     */
    public java.lang.String getGetuser_id() {
        return getuser_id;
    }
    
    /**
     * 属性 getuser_id 的设置方法。
     * @param getuser_id 属性 getuser_id 的新值。
     */
    public void setGetuser_id(java.lang.String getuser_id) {
        this.getuser_id = getuser_id;
    }
    
    /**
     * 属性 getuser_name 的获取方法。
     * @return 属性 getuser_name 的值。
     */
    public java.lang.String getGetuser_name() {
        return getuser_name;
    }
    
    /**
     * 属性 getuser_name 的设置方法。
     * @param getuser_name 属性 getuser_name 的新值。
     */
    public void setGetuser_name(java.lang.String getuser_name) {
        this.getuser_name = getuser_name;
    }
    
    /**
     * 属性 id 的获取方法。
     * @return 属性 id 的值。
     */
    public java.lang.String getId() {
        return id;
    }
    
    /**
     * 属性 id 的设置方法。
     * @param id 属性 id 的新值。
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    /**
     * 属性 senduser_id 的获取方法。
     * @return 属性 senduser_id 的值。
     */
    public java.lang.String getSenduser_id() {
        return senduser_id;
    }
    
    /**
     * 属性 senduser_id 的设置方法。
     * @param senduser_id 属性 senduser_id 的新值。
     */
    public void setSenduser_id(java.lang.String senduser_id) {
        this.senduser_id = senduser_id;
    }
    
    /**
     * 属性 senduser_name 的获取方法。
     * @return 属性 senduser_name 的值。
     */
    public String getSenduser_name() {
        return senduser_name;
    }
    
    /**
     * 属性 senduser_name 的设置方法。
     * @param senduser_name 属性 senduser_name 的新值。
     */
    public void setSenduser_name(java.lang.String senduser_name) {
        this.senduser_name = senduser_name;
    }
    
    /**
     * 属性 status 的获取方法。
     * @return 属性 status 的值。
     */
    public java.lang.String getStatus() {
        return status;
    }
    
    /**
     * 属性 status 的设置方法。
     * @param status 属性 status 的新值。
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    /**
     * 属性 time 的获取方法。
     * @return 属性 time 的值。
     */
    public java.lang.String getTime() {
        return time;
    }
    
    /**
     * 属性 time 的设置方法。
     * @param time 属性 time 的新值。
     */
    public void setTime(java.lang.String time) {
        this.time = time;
    }
    
    /**
     * 属性 title 的获取方法。
     * @return 属性 title 的值。
     */
    public java.lang.String getTitle() {
        return title;
    }
    
    /**
     * 属性 title 的设置方法。
     * @param title 属性 title 的新值。
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }
    
    /**
     * 判断短消息是否发出
     * @return true为发出；false为没有发出
     */
    public abstract boolean send();    
   
    
}
