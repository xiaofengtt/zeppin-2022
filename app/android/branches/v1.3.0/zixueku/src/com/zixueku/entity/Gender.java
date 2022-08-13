package com.zixueku.entity;
/** 
 * 类说明 
 * @author  Email: huangweidong@zeppin.cn
 * @version V1.0  创建时间：2015-2-9 下午12:01:24 
 */
public enum Gender {
	//男性为“m”，女性为“f”，如果没有此字段信息，则返回null
	
	 /** 
     *男
     */  
	BOY("m"),  
    /** 
     * 女 
     */  
	GIRL("f");
   
    //定义自己的属性，可以是其他类型，例如String，boolean…  
    private final String value;
  
    /** 
     * 采用自定义属性的构造方法 
     */  
    Gender(String value) {  
        this.value = value;  
    }  
  
    /** 
     * 得到对应值 
     *  
     * @return the value 
     */  
    public String getValue() {  
        return value;  
    }

}
