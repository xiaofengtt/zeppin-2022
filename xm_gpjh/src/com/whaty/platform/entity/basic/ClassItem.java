/*
 * ClassItem.java
 *
 * Created on 2004年12月6日, 下午3:45
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
     * 属性 class_id 的获取方法。
     * @return 属性 class_id 的值。
     */
    public java.lang.String getClass_id() {
        return class_id;
    }
    
    /**
     * 属性 class_id 的设置方法。
     * @param class_id 属性 class_id 的新值。
     */
    public void setClass_id(java.lang.String class_id) {
        this.class_id = class_id;
    }
    
}
