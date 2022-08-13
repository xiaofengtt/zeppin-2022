/*
 * Message.java
 *
 * Created on 2004��10��21��, ����2:34
 */

package com.whaty.platform.entity.basic;

/**
 * վ�ڶ���Ϣ��
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
     * ���� body �Ļ�ȡ������
     * @return ���� body ��ֵ��
     */
    public java.lang.String getBody() {
        return body;
    }
    
    /**
     * ���� body �����÷�����
     * @param body ���� body ����ֵ��
     */
    public void setBody(java.lang.String body) {
        this.body = body;
    }
    
    /**
     * ���� getuser_id �Ļ�ȡ������
     * @return ���� getuser_id ��ֵ��
     */
    public java.lang.String getGetuser_id() {
        return getuser_id;
    }
    
    /**
     * ���� getuser_id �����÷�����
     * @param getuser_id ���� getuser_id ����ֵ��
     */
    public void setGetuser_id(java.lang.String getuser_id) {
        this.getuser_id = getuser_id;
    }
    
    /**
     * ���� getuser_name �Ļ�ȡ������
     * @return ���� getuser_name ��ֵ��
     */
    public java.lang.String getGetuser_name() {
        return getuser_name;
    }
    
    /**
     * ���� getuser_name �����÷�����
     * @param getuser_name ���� getuser_name ����ֵ��
     */
    public void setGetuser_name(java.lang.String getuser_name) {
        this.getuser_name = getuser_name;
    }
    
    /**
     * ���� id �Ļ�ȡ������
     * @return ���� id ��ֵ��
     */
    public java.lang.String getId() {
        return id;
    }
    
    /**
     * ���� id �����÷�����
     * @param id ���� id ����ֵ��
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    /**
     * ���� senduser_id �Ļ�ȡ������
     * @return ���� senduser_id ��ֵ��
     */
    public java.lang.String getSenduser_id() {
        return senduser_id;
    }
    
    /**
     * ���� senduser_id �����÷�����
     * @param senduser_id ���� senduser_id ����ֵ��
     */
    public void setSenduser_id(java.lang.String senduser_id) {
        this.senduser_id = senduser_id;
    }
    
    /**
     * ���� senduser_name �Ļ�ȡ������
     * @return ���� senduser_name ��ֵ��
     */
    public String getSenduser_name() {
        return senduser_name;
    }
    
    /**
     * ���� senduser_name �����÷�����
     * @param senduser_name ���� senduser_name ����ֵ��
     */
    public void setSenduser_name(java.lang.String senduser_name) {
        this.senduser_name = senduser_name;
    }
    
    /**
     * ���� status �Ļ�ȡ������
     * @return ���� status ��ֵ��
     */
    public java.lang.String getStatus() {
        return status;
    }
    
    /**
     * ���� status �����÷�����
     * @param status ���� status ����ֵ��
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    /**
     * ���� time �Ļ�ȡ������
     * @return ���� time ��ֵ��
     */
    public java.lang.String getTime() {
        return time;
    }
    
    /**
     * ���� time �����÷�����
     * @param time ���� time ����ֵ��
     */
    public void setTime(java.lang.String time) {
        this.time = time;
    }
    
    /**
     * ���� title �Ļ�ȡ������
     * @return ���� title ��ֵ��
     */
    public java.lang.String getTitle() {
        return title;
    }
    
    /**
     * ���� title �����÷�����
     * @param title ���� title ����ֵ��
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }
    
    /**
     * �ж϶���Ϣ�Ƿ񷢳�
     * @return trueΪ������falseΪû�з���
     */
    public abstract boolean send();    
   
    
}
