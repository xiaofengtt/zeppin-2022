/*
 * ClassItem.java
 *
 * Created on 2004��12��6��, ����3:45
 */

package com.whaty.platform.entity.basic;

/**
 *
 * @author  chenjian
 */
public abstract class ClassItem {
    
    private String class_id = "";
    
    /** Creates a new instance of ClassItem */
    public ClassItem() {
    }
    
    /**
     * ���� class_id �Ļ�ȡ������
     * @return ���� class_id ��ֵ��
     */
    public java.lang.String getClass_id() {
        return class_id;
    }
    
    /**
     * ���� class_id �����÷�����
     * @param class_id ���� class_id ����ֵ��
     */
    public void setClass_id(java.lang.String class_id) {
        this.class_id = class_id;
    }
    
}
