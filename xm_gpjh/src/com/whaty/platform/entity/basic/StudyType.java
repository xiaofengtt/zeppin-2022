package com.whaty.platform.entity.basic;
/**
 * 学习形式对象
 * @author  reacol
 */
public class StudyType {
	private String id = "";
	private String name = "";
	
	public StudyType() {
    }
	
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
}
