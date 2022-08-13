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
     * 属性 id 的获取方法。
     * @return 属性 id 的值。
     */
    public java.lang.String getId() {
        return id;
    }
    
    /**
     * 属性 id 的设置方法。
     * @param id 属性 id 的新值。
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    /**
     * 属性 name 的获取方法。
     * @return 属性 name 的值。
     */
    public java.lang.String getName() {
        return name;
    }
    
    /**
     * 属性 name 的设置方法。
     * @param name 属性 name 的新值。
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /**
     * 属性 note 的获取方法。
     * @return 属性 note 的值。
     */
    public java.lang.String getNote() {
        return note;
    }
    
    /**
     * 属性 note 的设置方法。
     * @param note 属性 note 的新值。
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
