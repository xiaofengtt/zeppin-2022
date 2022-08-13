/*
 * Dep.java
 *
 * Created on 2004年9月24日, 下午2:50
 */

package com.whaty.platform.entity.basic;

/**
 * 院系对象
 * @author  chenjian
 */
public abstract class Dep implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String name = "";
    
    private String note = "";
    
    /**
     * 属性 id 的获取方法。
     * @return 属性 id 的值。
     */
    public String getId() {
        return id;
    }
    
    /**
     * 属性 id 的设置方法。
     * @param id 属性 id 的新值。
     */
    public void setId(String aId) {
        id = aId;
    }
    
    /**
     * 属性 name 的获取方法。
     * @return 属性 name 的值。
     */
    public String getName() {
        return name;
    }
    
    /**
     * 属性 name 的设置方法。
     * @param name 属性 name 的新值。
     */
    public void setName(String aName) {
        name = aName;
    }
    
    /**
     * 属性 note 的获取方法。
     * @return 属性 note 的值。
     */
    public String getNote() {
        return note;
    }
    
    /**
     * 属性 note 的设置方法。
     * @param note 属性 note 的新值。
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
     * 是否存在该学院编号
     * @return 0为不存在；大于0存在
     */
    public abstract int isIdExist();

    /**
     * 判断该学院下是否有专业
     * @return 0为没有；大于0则有
     */
    public abstract int isHasMajor();
   
}
