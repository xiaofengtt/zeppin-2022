/**
 * 
 */
package com.whaty.platform.util;

import java.util.Iterator;
import java.util.List;

/**
 * @author wq
 * 
 */
/**
 * @author Administrator
 * 
 */
public class Conditions {

	/**
	 * �õ�searchProperty ��ɵ�����ַ�
	 * 
	 * @param searchProperty
	 * @return
	 */
	private String getConditons(List searchProperty) {
		String condition = "";
		if (searchProperty != null) {
			for (Iterator iter = searchProperty.iterator(); iter.hasNext();) {
				SearchProperty sp = (SearchProperty) iter.next();
				String field = sp.getField();
				String value = sp.getValue();
				String relation = sp.getRelation();
				if (field == null || field.length() < 1) {
					continue;
				} else if (value == null || value.length() < 1) {
					if (relation == null)
						continue;
					if (relation.trim().equalsIgnoreCase("isNull"))
						condition += field + " is null " + " and ";
					else if (relation.trim().equalsIgnoreCase("isNotNull"))
						condition += field + " is not null " + " and ";
					else
						continue;

				} else {
					if (relation.equals("like")) {
						condition += field + " " + relation + " '%" + value
								+ "%' and ";
					} else if (relation.equals("in")) {
						condition += field + " " + relation + " (" + value
								+ ") and ";
					} else if (relation.equals("notIn")) {
						condition += field + " not in (" + value + ") and ";
					} else if (relation.equals("D>=") || relation.equals("d>=")) {
						condition += (field + "  >= to_date('" + value + "','yyyy-mm-dd') and ");
					} else if (relation.equals("D<=") || relation.equals("d<=")) {
						condition += (field + " <= to_date('" + value + "','yyyy-mm-dd') and ");
					}else if (relation.equals("D=") || relation.equals("d=")) {
						condition += (field + " = to_date('" + value + "','yyyy-mm-dd') and ");
					} else if (relation.trim().endsWith("num")) {
						condition += field
								+ relation.trim().substring(0,
										relation.trim().length() - 3) + value
								+ " and ";
					} else if (relation.trim().equalsIgnoreCase("isNull")) {
						condition += field + " is null " + " and ";
					} else if (relation.trim().equalsIgnoreCase("isNotNull")) {
						condition += field + " is not null " + " and ";
					} else if (relation.equalsIgnoreCase("or")) {
						condition += "(" + field + " or " + value + ") and ";
					}else {
						condition += field + relation + "'" + value + "' and ";
					}
				}

			}
		}
		return condition;
	}

	/**
	 * �õ�orderProperty ��ɵ������ַ�
	 * 
	 * @param orderProperty
	 * @return
	 */
	private String getOrders(List orderProperty) {
		String orders = "";
		if (orderProperty != null) {
			for (Iterator iter = orderProperty.iterator(); iter.hasNext();) {
				OrderProperty op = (OrderProperty) iter.next();
				String orderName = op.getOrderName();
				String order = op.getOrder();
				if (order.equals(OrderProperty.DESC))
					orders += orderName + " desc,";
				else
					orders += orderName + ",";
			}
			if (!orders.equals(""))
				orders = " order by "
						+ orders.substring(0, orders.length() - 1);
		}
		return orders;
	}

	/**
	 * �õ��ܵ�SQL����µĲ�ѯ�������ַ���where��ͷ����(order by
	 * 
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public static String convertToCondition(List searchProperty,
			List orderProperty) {
		Conditions con = new Conditions();
		String condition = con.getConditons(searchProperty);
		if (!condition.equals(""))
			condition = " where "
					+ condition.substring(0, condition.length() - 4);
		String orders = con.getOrders(orderProperty);
		return condition + orders;
	}

	/**
	 * �õ����ֲ�ѯ�ַ���and��ͷ
	 * 
	 * @param searchProperty
	 * @return
	 */
	public static String convertToAndCondition(List searchProperty) {
		Conditions con = new Conditions();
		String condition = con.getConditons(searchProperty);
		if (!condition.equals(""))
			condition = " and "
					+ condition.substring(0, condition.length() - 4);
		return condition;
	}

	public static String convertToAndCondition(List searchProperty,
			List orderProperty) {
		Conditions con = new Conditions();
		String orders = con.getOrders(orderProperty);
		return convertToAndCondition(searchProperty) + orders;
	}
}
