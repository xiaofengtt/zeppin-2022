/*
 * Message.java
 *
 * Created on 2004��10��15��, ����1:55
 */

package com.whaty.platform.entity.basic;
import java.util.List;

import com.whaty.platform.util.Page;
/**
 * ������Ϣ����
 * @author chenjian
 */
public abstract class Note implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String title = "";
    
    private String content = "";
    
    private String input_date = "";
    
    private String ip = "";
    
    private String re_id = "";
    
    private String name = "";
    
    /**
     * ���� content �Ļ�ȡ������
     * @return ���� content ��ֵ��
     */
    public java.lang.String getContent() {
        return content;
    }
    
    /**
     * ���� content �����÷�����
     * @param content ���� content ����ֵ��
     */
    public void setContent(java.lang.String content) {
        this.content = content;
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
     * ���� input_date �Ļ�ȡ������
     * @return ���� input_date ��ֵ��
     */
    public java.lang.String getInput_date() {
        return input_date;
    }
    
    /**
     * ���� input_date �����÷�����
     * @param input_date ���� input_date ����ֵ��
     */
    public void setInput_date(java.lang.String input_date) {
        this.input_date = input_date;
    }
    
    /**
     * ���� ip �Ļ�ȡ������
     * @return ���� ip ��ֵ��
     */
    public java.lang.String getIp() {
        return ip;
    }
    
    /**
     * ���� ip �����÷�����
     * @param ip ���� ip ����ֵ��
     */
    public void setIp(java.lang.String ip) {
        this.ip = ip;
    }
    
    /**
     * ���� name �Ļ�ȡ������
     * @return ���� name ��ֵ��
     */
    public java.lang.String getName() {
        return name;
    }
    
    /**
     * ���� name �����÷�����
     * @param name ���� name ����ֵ��
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /**
     * ���� re_id �Ļ�ȡ������
     * @return ���� re_id ��ֵ��
     */
    public java.lang.String getRe_id() {
        return re_id;
    }
    
    /**
     * ���� re_id �����÷�����
     * @param re_id ���� re_id ����ֵ��
     */
    public void setRe_id(java.lang.String re_id) {
        this.re_id = re_id;
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
     * ��ɾ������
     * @param notelist
     */
    public abstract void delete(List notelist);
    
    /**
     * ��ȡ�����б�
     * @param page
     * @return �����б�
     */
    public abstract List getNoteList(Page page);
    
    /**
     * ��ȡ������Ŀ
     * @return ������Ŀ
     */
    public abstract int getNoteNum();
    
}
