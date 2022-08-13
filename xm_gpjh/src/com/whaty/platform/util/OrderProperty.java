/**
 * OrderProperty ��������ǹ���һ��Order�����ࡣ���������Ҫ������ֶκ�
 * �����򹹳ɡ�
 * ��˾���ƣ�����
 * @author Terry  
 */
package com.whaty.platform.util;

public class OrderProperty {

	public static final String ASC = "0";

	public static final String DESC = "1";

	/** �����ֶ� */
	private String orderName;

	/** ������ */
	private String order;

	/** ����һ���յ� OrderProperty �� , ���� orderName == null */
	public OrderProperty() {
		this.order = ASC;
		this.orderName = null;
	}

	/**
	 * ����һ���������ֶκ������򹹳ɵ��࣬������ֻ������ͽ�������ѡ��
	 * ��������ַ������� OrderProperty.DESC ��"1"��Ĭ��Ϊ����.
	 */
	public OrderProperty(String orderName, String order) {
		this.orderName = orderName;
		if (order != null && (order.equals("DESC") || order.equals("1"))) {
			this.order = DESC;
		} else {
			this.order = ASC;
		}

	}

	/**
	 * ����һ��Ĭ��Ϊ�����OrderProperty��
	 */
	public OrderProperty(String orderName) {
		this.orderName = orderName;
		this.order = ASC;

	}

	/**
	 * ���������ֶ�
	 * 
	 * @return orderName - a <code>String</code>
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * ���������ֶ�
	 * 
	 * @param field -
	 *            a <code>String</code>
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	/**
	 * ����������
	 * 
	 * @return order a <code>String</code>
	 */
	public String getOrder() {
		return this.order;
	}

	/**
	 * ������������������ַ������� OrderProperty.DESC ��"1"��Ĭ��Ϊ����
	 * 
	 * @param order -
	 *            a <code>String</code>
	 */
	public void setOrder(String order) {
		if (order != null && order.equals(DESC)) {
			this.order = order;
		} else {
			this.order = ASC;
		}
	}
}
