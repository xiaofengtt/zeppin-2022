/*
 * Dep.java
 *
 * Created on 2004��9��24��, ����2:50
 */

package com.whaty.platform.entity.basic;

/**
 * Ժϵ����
 * @author  chenjian
 */
public abstract class Dep implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String name = "";
    
    private String note = "";
    
    /**
     * ���� id �Ļ�ȡ������
     * @return ���� id ��ֵ��
     */
    public String getId() {
        return id;
    }
    
    /**
     * ���� id �����÷�����
     * @param id ���� id ����ֵ��
     */
    public void setId(String aId) {
        id = aId;
    }
    
    /**
     * ���� name �Ļ�ȡ������
     * @return ���� name ��ֵ��
     */
    public String getName() {
        return name;
    }
    
    /**
     * ���� name �����÷�����
     * @param name ���� name ����ֵ��
     */
    public void setName(String aName) {
        name = aName;
    }
    
    /**
     * ���� note �Ļ�ȡ������
     * @return ���� note ��ֵ��
     */
    public String getNote() {
        return note;
    }
    
    /**
     * ���� note �����÷�����
     * @param note ���� note ����ֵ��
     */
    public void setNote(String aNote) {
        note = aNote;
    }

    private String forshort;

    public String getForshort() {
        return forshort;
    }

    public void setForshort(String forshort) {
        this.forshort = forshort;
    }

    /**
     * �Ƿ���ڸ�ѧԺ���
     * @return 0Ϊ�����ڣ�����0����
     */
    public abstract int isIdExist();

    /**
     * �жϸ�ѧԺ���Ƿ���רҵ
     * @return 0Ϊû�У�����0����
     */
    public abstract int isHasMajor();
   
}
