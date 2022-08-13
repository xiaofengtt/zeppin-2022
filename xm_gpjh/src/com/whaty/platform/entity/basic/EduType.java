/*
 * Edu_type.java
 *
 * Created on 2004��9��24��, ����2:24
 */

package com.whaty.platform.entity.basic;

/**
 * ��ζ���
 * @author  chenjian
 */
public abstract class EduType implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String name = "";
    
    private String code = ""; //新加入 ，为与bjsy2一致 
    
    private String forshort = "";
    
    private String introduction = "";
    
    private String recruit_status = "";
    
    /** Creates a new instance of Edu_type */
    public EduType() {
    }
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
    
    /**
     * ���� forshort �Ļ�ȡ������
     * @return ���� forshort ��ֵ��
     */
    public String getForshort() {
        return forshort;
    }
    
    /**
     * ���� forshort �����÷�����
     * @param forshort ���� forshort ����ֵ��
     */
    public void setForshort(String aForshort) {
        forshort = aForshort;
    }
    
    /**
     * ���� introduction �Ļ�ȡ������
     * @return ���� introduction ��ֵ��
     */
    public java.lang.String getIntroduction() {
        return introduction;
    }
    
    /**
     * ���� introduction �����÷�����
     * @param introduction ���� introduction ����ֵ��
     */
    public void setIntroduction(String aIntroduction) {
        introduction = aIntroduction;
    }
    
    /**
     * ���� recruit_status �Ļ�ȡ������
     * @return ���� recruit_status ��ֵ��
     */
    public String getRecruit_status() {
        return recruit_status;
    }
    
    /**
     * ���� recruit_status �����÷�����
     * @param recruit_status ���� recruit_status ����ֵ��
     */
    public void setRecruit_status(String aRecruit_status) {
        recruit_status = aRecruit_status;
    }

    /**
     * �жϸò��Id�Ƿ����
     * @return 0Ϊ�����ڣ�����0Ϊ����
     */
    public abstract int isIdExist();

    /**
     * �жϸò�����Ƿ���ѧ��
     * @return 0Ϊû�У�����0����
     */
    public abstract int isHasStudents();
    
 
}
