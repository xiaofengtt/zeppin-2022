/**
 * OrderProperty 类的作用是构造一个Order属性类。这个类由需要排序的字段和
 * 升降序构成。
 * 公司名称：网梯
 * @author Terry  
 */
package com.whaty.platform.util;

public class OrderProperty {

	public static final String ASC = "0";

	public static final String DESC = "1";

	/** 排序字段 */
	private String orderName;

	/** 升降序 */
	private String order;

	/** 构造一个空的 OrderProperty 类 , 其中 orderName == null */
	public OrderProperty() {
		this.order = ASC;
		this.orderName = null;
	}

	/**
	 * 创建一个由排序字段和升降序构成的类，升降序只有升序和降序两种选择，
	 * 如果输入字符不等于 OrderProperty.DESC 或"1"则默认为升序.
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
	 * 创建一个默认为升序的OrderProperty类
	 */
	public OrderProperty(String orderName) {
		this.orderName = orderName;
		this.order = ASC;

	}

	/**
	 * 返回排序字段
	 * 
	 * @return orderName - a <code>String</code>
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * 设置排序字段
	 * 
	 * @param field -
	 *            a <code>String</code>
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	/**
	 * 返回升降序
	 * 
	 * @return order a <code>String</code>
	 */
	public String getOrder() {
		return this.order;
	}

	/**
	 * 设置升降序，如果输入字符不等于 OrderProperty.DESC 或"1"则默认为升序
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
