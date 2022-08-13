/*
 * Topic.java
 *
 * Created on 2005年1月6日, 下午5:23
 */

package com.whaty.platform.interaction.forum;

/**
 * 讨论区中的主题类
 * @author chenjian
 */
public abstract class Topic implements com.whaty.platform.Items{
    
    private String id;
    
    private String title;
    
    private String body;
    
    private String date;
    
    private String submituser_id;
    
    private String submituser_name;
    
    private String submituser_type;
    
    private int clicknum;
    
    private String forum_id;
    
    private String related_id = "0";
    
    private String eliteflag = "0";
    
    /** Creates a new instance of Topic */
    public Topic() {
    }
    
}
