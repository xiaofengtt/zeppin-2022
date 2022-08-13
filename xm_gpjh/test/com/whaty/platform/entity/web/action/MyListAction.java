package com.whaty.platform.entity.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.whaty.platform.entity.service.MyListService;
import com.whaty.platform.util.JsonUtil;

public class MyListAction extends ActionSupport {

	/**
	 * private members
	 */
	private MyListService myListService;
	
	private String bean;		// bean 的类名
	
	private String sql;
	
	private String jsonString;
	
	/**
	 * getters and setters
	 */
	public MyListService getMyListService() {
		return myListService;
	}

	public void setMyListService(MyListService myListService) {
		this.myListService = myListService;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String execute() {
		
		List list = null;
		
		if (sql!=null && !sql.equals("")) {
			list = myListService.queryBySQL(sql);
		} else {
			list = myListService.getIdNameList(this.getBean());
		}

		String[] keys = {"id", "name"};
		list = JsonUtil.JsonConversion(keys, list);
		
		Map map = new HashMap();
		map.put("totalCount", list.size());
		map.put("models", list);

		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
