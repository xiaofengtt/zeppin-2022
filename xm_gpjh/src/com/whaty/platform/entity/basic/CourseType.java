/*
 * course_type.java
 *
 * Created on 2004��10��21��, ����9:51
 */

package com.whaty.platform.entity.basic;


/**
 * �γ����Ͷ���
 * @author  chenjian
 */
public abstract class CourseType implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String name = "";
    
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
     * �жϿγ����ͱ���Ƿ����
     * @return 0��ʾ�����ڣ�����0��ʾ����
     */
    public abstract int isIdExist();
    
    /**
     * �жϿγ������Ƿ���ڿγ�
     * @return 0��ʾ�����ڣ�����0��ʾ����
     */
    public abstract int hasCourse();
}
