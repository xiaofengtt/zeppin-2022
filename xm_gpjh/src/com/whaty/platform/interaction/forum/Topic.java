/*
 * Topic.java
 *
 * Created on 2005��1��6��, ����5:23
 */

package com.whaty.platform.interaction.forum;

/**
 * �������е�������
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
