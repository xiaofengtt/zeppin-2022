/**
 * 
 */
package com.whaty.platform.training.basic;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;


/**
 * @author chenjian
 *
 */
public abstract class TrainingCourseType implements Items {

	
	private String id;
    
    private String name;
    
    private boolean isActive;
    
    private TrainingCourseType parentType;
    
    private int level;
    
    private String note;
    
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
     * ���� note �Ļ�ȡ������
     * @return ���� note ��ֵ��
     */
    public java.lang.String getNote() {
        return note;
    }
    
    /**
     * ���� note �����÷�����
     * @param note ���� note ����ֵ��
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }
    
    
	

	public TrainingCourseType getParentType() {
		return parentType;
	}

	public void setParentType(TrainingCourseType parentType) {
		this.parentType = parentType;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public abstract List getChildType(List searchproperty, List orderproperty) throws PlatformException;
    
	public abstract boolean isRoot()  throws PlatformException;
	
	public abstract void moveTo(TrainingCourseType parentType) throws PlatformException;
	
	public abstract TrainingCourseType getParent() throws PlatformException;

	public abstract void addChildType(List childTypeList) throws PlatformException;
	
	public abstract void removeChildType(List childTypeList) throws PlatformException;
	
	public abstract List getTypeTreeCanMove() throws PlatformException;
}
