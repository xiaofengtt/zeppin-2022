/*
 * thread.java
 *
 * Created on 2005��1��6��, ����5:41
 */

package com.whaty.platform.interaction.forum;

/**
 * ������������Ļظ���
 * @author chenjian
 */
public abstract class thread extends Topic {
    
    private String topic_id;    
  
    /**
     * ���� topic_id �Ļ�ȡ������
     * @return ���� topic_id ��ֵ��
     */
    public java.lang.String getTopic_id() {
        return topic_id;
    }
    
    /**
     * ���� topic_id �����÷�����
     * @param topic_id ���� topic_id ����ֵ��
     */
    public void setTopic_id(java.lang.String topic_id) {
        this.topic_id = topic_id;
    }
    
}
