package com.whaty.platform.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BatchMethod {

	public static List addBatch(List dataList) {
		String sql = "";
		String sql_field = "";
		String sql_data = "";
		String field = "";
		String tableName = "";
		String tableSeq = "";
		List sql_group = new ArrayList();

		for (int i = 0; i < dataList.size(); i++) {
			HashMap data = (HashMap) dataList.get(i);
			Set dataset = data.keySet();
			for (Iterator dataiter = dataset.iterator(); dataiter.hasNext();) {
				field = (String) dataiter.next();
				if (field.equals("tableName")) {
					tableName = (String) data.get(field);
					continue;
				} else if (field.equals("tableSeq")) {
					tableSeq = (String) data.get(field);
					continue;
				}
				sql_field = sql_field + field + ",";
				sql_data = sql_data + (String) data.get(field) + ",";
			}
			sql_field = sql_field.substring(0, sql_field.length() - 1);
			sql_data = sql_data.substring(0, sql_data.length() - 1);
			sql = "insert into " + tableName + " (" + tableSeq + ","
					+ sql_field + ") values(" + sql_data + ")";
			sql_group.add(sql);
		}
		return sql_group;
	}

	public List deleteBatch(String tableName, List dataList) {
		String sql = "delete from " + tableName;
		List sql_group = new ArrayList();
		for (int i = 0; i < dataList.size(); i++) {
			List searchproperty = (List) dataList.get(i);
			sql = sql + Conditions.convertToCondition(searchproperty, null);
			sql_group.add(sql);
		}
		return sql_group;
	}

	public List updateBatch(List dataList, List searchProperties) {
		String tableName = "";
		String sql = "";
		String sql_data = "";
		String field = "";
		List sql_group = new ArrayList();
		for (int i = 0; i < dataList.size(); i++) {
			sql_data = "";
			List searchproperty = (List) searchProperties.get(i);
			HashMap data = (HashMap) dataList.get(i);
			Set dataset = data.keySet();
			for (Iterator dataiter = dataset.iterator(); dataiter.hasNext();) {
				field = (String) dataiter.next();
				if (field.equals("tableName")) {
					tableName = (String) data.get(field);
					continue;
				}
				sql_data = sql_data + field + "='" + (String) data.get(field)
						+ "',";
			}
			sql = "update " + tableName + " set "
					+ sql_data.substring(0, sql_data.length() - 1)
					+ Conditions.convertToCondition(searchproperty, null);
			sql_group.add(sql);
		}
		return sql_group;
	}

}
