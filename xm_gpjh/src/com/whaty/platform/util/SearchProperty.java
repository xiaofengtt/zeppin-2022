/**
 * 这个类是用来辅助搜索的
 */
package com.whaty.platform.util;
 
/**
 * @author wq
 * 修改人：Terry
 * 修改内容：去掉 order 字段并为函数增加注释
 *
 */
public class SearchProperty {

	private String field;
	
	private String value;
	
	private String relation;
	
	/**
	 * 创建一个 SearchProperty 空类
	 */
	public SearchProperty(){
		
	}
	/**
	 * 创建一个 SearchProperty 类
	 * 默认判断条件为 "="
	 * @param field  - 查找字段
	 * @param value  - 查找值 
	 */	
	public SearchProperty(String field , String value){
		
		this.field = field;
		
		this.value = value;
		
		this.relation = "=";	
		
	}	
	/**
	 * 创建一个 SearchProperty 类
	 * @param field  - 查找字段
	 * @param value  - 查找值
	 * @param relation - 判断条件，例如: like, =, >, <,注意如果是数字比较，需要在relation后面加上"num"
	 * 					如果是日期比较为D>=或者D<=,如果判断未空,为isNull,isNotNull
	 */
	public SearchProperty(String field , String value , String relation){
		
		this.field = field;
		
		this.value = value;
		
		this.relation = relation;
		
	}

	/**
	 * 返回查找字段 
	 * @return field - a <code>String</code>
	 */
	public String getField() {
		return field;
	}

	/**
	 * 设置查找字段
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 返回判断条件
	 * @return relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * 设置 判断条件
	 * @param relation
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * 返回查找值
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置查找值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}	
}
