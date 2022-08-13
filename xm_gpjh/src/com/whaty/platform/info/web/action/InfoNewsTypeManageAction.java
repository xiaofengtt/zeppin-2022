package com.whaty.platform.info.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNewsType;
import com.whaty.platform.info.exception.InfoException;
import com.whaty.platform.info.service.InfoNewsTypeService;

public class InfoNewsTypeManageAction extends InfoBaseAction {
//	 list返回给extjs的json字符串
	private String jsonString;

	// ----extjs 分页程序提交的request参数
	private String start; // 列表开始的位置
	private String limit; // 每页要显示的条目
	private String sort; // 排序的对应列
	private String dir; // 升序asc，降序desc
	private String newsIds;  //删除对应的教师id的json表达式
	
	//----------新闻类型信息;
	private String name;
    private String note;
    private String parentId;
    private String status;
    private String tag;
    //------------------------
	
	private InfoNewsTypeService infoNewsTypeService;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public InfoNewsTypeService getInfoNewsTypeService() {
		return infoNewsTypeService;
	}

	public void setInfoNewsTypeService(InfoNewsTypeService infoNewsTypeService) {
		this.infoNewsTypeService = infoNewsTypeService;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getNewsIds() {
		return newsIds;
	}

	public void setNewsIds(String newsIds) {
		this.newsIds = newsIds;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	
	/**
	 * 获得新闻类型列表
	 */
	public String listByPage()throws InfoException{
//		增加查询条件
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InfoNewsType.class);
		

		
//		List newsList = this.infoNewsTypeService.getInfoNewsTypeByPage(detachedCriteria,Integer.parseInt(this.getLimit()),Integer.parseInt(this.getStart()));
		Page page = this.infoNewsTypeService.getInfoNewsTypeByPage(detachedCriteria,Integer.parseInt(this.getLimit()),Integer.parseInt(this.getStart()));
		
//		获得总条数与列表;
		String strCount = "";
//		int totalCount = this.infoNewsTypeService.getInfoNewsTypeTotalCount(detachedCriteria);
		int totalCount = page.getTotalCount();
		if(totalCount>0)
			strCount = (new Integer(totalCount)).toString();
		
		
//		json处理
		Map map = new HashMap();
		map.put("totalCount",strCount);
//		map.put("newstype",newsList);
		map.put("newstype",page.getItems());
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		logger.debug("json string: "+jsonString);
		
		return "listjson";
	}
	
	/**
	 * 获得全部新闻类型
	 */
	public String listAll()throws InfoException{
//		增加查询条件
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InfoNewsType.class);
		//		获得总条数与列表;
		String strCount = "";
		int totalCount = this.infoNewsTypeService.getInfoNewsTypeTotalCount(detachedCriteria);
		if(totalCount>0)
			strCount = (new Integer(totalCount)).toString();
		
		List newsList = this.infoNewsTypeService.getInfoNewsTypeList(detachedCriteria);
		
//		json处理
		Map map = new HashMap();
		map.put("totalCount",strCount);
		map.put("newstype",newsList);
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		logger.debug("json string: "+jsonString);
		
		return "listjson";
	}
	
	/**
	 * 添加
	 */
	public String add()throws InfoException{
		
		InfoNewsType type = new InfoNewsType();
		type.setName(this.getName());
		type.setNote(this.getNote());
		type.setStatus(this.getStatus());
		type.setTag(this.getTag());
//		type.setParentId(this.getParentId());
		
		InfoNewsType t = this.getInfoNewsTypeService().save(type);
		
		Map map = new HashMap();
		if(t != null && t.getId() !=null){
			map.put("success","true");
			map.put("info", t.getId());
			
			logger.info("添加新闻类型成功! id= "+t.getId());
		}else{
			map.put("failure","true");
			map.put("errorInfo", "add error");
			
			logger.error("添加新闻类型失败!");
		}
		
		//json处理
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		return "listjson";
	}
	
	/**
	 * 修改
	 */
	public String edit()throws InfoException{
		
		InfoNewsType type = new InfoNewsType();
		type.setName(this.getName());
		type.setNote(this.getNote());
		type.setStatus(this.getStatus());
		type.setTag(this.getTag());
//		type.setParentId(this.getParentId());
		
		InfoNewsType t = this.getInfoNewsTypeService().save(type);
		
		Map map = new HashMap();
		if(t != null && t.getId() !=null){
			map.put("success","true");
			map.put("info", t.getId());
			
			logger.info("添加新闻类型成功! id= "+t.getId());
		}else{
			map.put("failure","true");
			map.put("errorInfo", "add error");
			
			logger.error("添加新闻类型失败!");
		}
		
		//json处理
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		return "listjson";
	}
}
