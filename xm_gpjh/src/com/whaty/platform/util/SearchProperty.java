/**
 * ���������������������
 */
package com.whaty.platform.util;
 
/**
 * @author wq
 * �޸��ˣ�Terry
 * �޸����ݣ�ȥ�� order �ֶβ�Ϊ��������ע��
 *
 */
public class SearchProperty {

	private String field;
	
	private String value;
	
	private String relation;
	
	/**
	 * ����һ�� SearchProperty ����
	 */
	public SearchProperty(){
		
	}
	/**
	 * ����һ�� SearchProperty ��
	 * Ĭ���ж�����Ϊ "="
	 * @param field  - �����ֶ�
	 * @param value  - ����ֵ 
	 */	
	public SearchProperty(String field , String value){
		
		this.field = field;
		
		this.value = value;
		
		this.relation = "=";	
		
	}	
	/**
	 * ����һ�� SearchProperty ��
	 * @param field  - �����ֶ�
	 * @param value  - ����ֵ
	 * @param relation - �ж�����������: like, =, >, <,ע����������ֱȽϣ���Ҫ��relation�������"num"
	 * 					��������ڱȽ�ΪD>=����D<=,����ж�δ��,ΪisNull,isNotNull
	 */
	public SearchProperty(String field , String value , String relation){
		
		this.field = field;
		
		this.value = value;
		
		this.relation = relation;
		
	}

	/**
	 * ���ز����ֶ� 
	 * @return field - a <code>String</code>
	 */
	public String getField() {
		return field;
	}

	/**
	 * ���ò����ֶ�
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * �����ж�����
	 * @return relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * ���� �ж�����
	 * @param relation
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * ���ز���ֵ
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * ���ò���ֵ
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}	
}
