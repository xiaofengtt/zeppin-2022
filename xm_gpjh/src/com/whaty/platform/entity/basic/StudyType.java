package com.whaty.platform.entity.basic;
/**
 * ѧϰ��ʽ����
 * @author  reacol
 */
public class StudyType {
	private String id = "";
	private String name = "";
	
	public StudyType() {
    }
	
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
}
