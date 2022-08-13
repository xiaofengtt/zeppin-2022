package com.whaty.platform.info.bean;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.info.web.action.InfoBaseAction;

public class PageSizeSetAction extends InfoBaseAction {
	
	private String pageSize;
	
	private String jsonString;
	
	
	/**
	 * @return the jsonString
	 */
	public String getJsonString() {
		return jsonString;
	}



	/**
	 * @param jsonString the jsonString to set
	 */
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}



	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}



	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}



	public String execute()
	{
		ActionContext.getContext().getSession().put("pageSize", this.getPageSize());
		Map map = new HashMap();
		map.put("success","true");
		map.put("info",getText("entity.page.setsuccess")+this.getPageSize());
		JSONObject jsonObject = JSONObject.fromObject(map);
		jsonString = jsonObject.toString();
		return "listjson";
	}

}
