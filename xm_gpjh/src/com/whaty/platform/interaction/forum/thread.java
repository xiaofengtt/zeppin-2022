/*
 * thread.java
 *
 * Created on 2005年1月6日, 下午5:41
 */

package com.whaty.platform.interaction.forum;

/**
 * 讨论区中主题的回复类
 * @author chenjian
 */
public abstract class thread extends Topic {
    
    private String topic_id;    
  
    /**
     * 属性 topic_id 的获取方法。
     * @return 属性 topic_id 的值。
     */
    public java.lang.String getTopic_id() {
        return topic_id;
    }
    
    /**
     * 属性 topic_id 的设置方法。
     * @param topic_id 属性 topic_id 的新值。
     */
    public void setTopic_id(java.lang.String topic_id) {
        this.topic_id = topic_id;
    }
    
}
