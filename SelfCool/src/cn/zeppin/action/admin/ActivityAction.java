/** 
 * Project Name:Self_Cool 
 * File Name:ManagerAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Activity;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IActivityService;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/** 
 * ClassName: ManagerAction <br/> 
 * date: 2014年7月20日 下午7:05:24 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class ActivityAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private IActivityService activityService;
	private IResourceService resourceService;
	
	public IActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(IActivityService activityService) {
		this.activityService = activityService;
	}
	
	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	/**
	 * 添加
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "resource.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "title", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "url", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "contentType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "weight", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add(){
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String url = request.getParameter("url");
		String content = request.getParameter("content") == null ? "" : request.getParameter("content");
		short status = Short.valueOf(this.request.getParameter("status"));
		short contentType = Short.valueOf(this.request.getParameter("contentType"));
		short weight = Short.valueOf(this.request.getParameter("weight"));
		Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
		
		Activity activity = new Activity();
		activity.setName(name);
		activity.setTitle(title);
		activity.setUrl(url);
		activity.setStatus(status);
		activity.setContent(content);
		activity.setContentType(contentType);
		activity.setWeight(weight);
		activity.setSysUser(currentUser);
		activity.setCreatetime(new Timestamp((new Date()).getTime()));
		
		Resource resource = this.getResourceService().getById(resourceID);
		if (resource == null) {
			result.init(FAIL_STATUS, "活动宣传图片不存在", null);
		}else{
			activity.setResource(resource);
			this.getActivityService().addActivity(activity);
			Map<String, Object> data = SerializeEntity.activity2Map(activity);
			result.init(SUCCESS_STATUS, "添加活动成功！", data);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 删除
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete(){
		ActionResult result = new ActionResult();
		Integer activityID = Integer.valueOf(request.getParameter("id"));
		Activity activity = this.getActivityService().getActivityById(activityID);
		if (activity != null){
			this.getActivityService().deleteActivity(activity);
			Map<String, Object> data = SerializeEntity.activity2Map(activity);
			result.init(SUCCESS_STATUS, "删除活动成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的活动ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 加载 用于修改的时候load
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load(){
		ActionResult result = new ActionResult();
		Integer activityID = Integer.valueOf(request.getParameter("id"));
		String split =request.getParameter("split");
		Activity activity = this.getActivityService().getActivityById(activityID);
		if (activity != null) {
			Map<String, Object> data = SerializeEntity.activity2Map(activity,split);
			result.init(SUCCESS_STATUS, "加载活动成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的活动ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 修改
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "resource.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "title", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "url", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "contentType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "weight", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update(){
		ActionResult result = new ActionResult();
		Integer activityID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String url = request.getParameter("url");
		String content = request.getParameter("content") == null ? "" : request.getParameter("content");
		short status = Short.valueOf(this.request.getParameter("status"));
		short contentType = Short.valueOf(this.request.getParameter("contentType"));
		short weight = Short.valueOf(this.request.getParameter("weight"));

		Activity activity = this.getActivityService().getActivityById(activityID);
		if (activity != null){
			activity.setName(name);
			activity.setName(name);
			activity.setTitle(title);
			activity.setUrl(url);
			activity.setStatus(status);
			activity.setContent(content);
			activity.setContentType(contentType);
			activity.setWeight(weight);
			
			if (request.getParameterMap().containsKey("resource.id")) {
				Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
				Resource resource = this.getResourceService().getById(resourceID);
				if (resource == null) {
					result.init(FAIL_STATUS, "图标不存在", null);
				}
				activity.setResource(resource);
			}
	
			this.getActivityService().updateActivity(activity); 
			Map<String, Object> data = SerializeEntity.activity2Map(activity);
			result.init(SUCCESS_STATUS, "修改活动成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的活动ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 分类列表
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "sysUser.name", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void List(){
		
		ActionResult result = new ActionResult();
		
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));
		searchMap.put("sysUser.name", request.getParameter("sysUser.name"));
		searchMap.put("status", request.getParameter("status"));
		searchMap.put("push",request.getParameter("push"));
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount =this.getActivityService().getActivityCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);

		List<Activity> activityList = getActivityService().getActivityListByParams(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (activityList != null && activityList.size() > 0){
			for (Activity activity : activityList){
				Map<String, Object> data = SerializeEntity.activity2Map(activity);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}
	
	/**
	 * 移动端获取活动列表
	 */
	public void GetList(){
		ActionResult result = new ActionResult();
		List<Map<String, Object>> datalist = new ArrayList<>();
		
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("status", Dictionary.ACTIVITY_STATUS_PUBLISH);
		String sort = "weight desc";
		List<Activity> activityList = this.getActivityService().getActivityListByParams(searchMap, sort, -1, -1);
		if (activityList != null && activityList.size() > 0) {
			for(Activity activity : activityList){
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", activity.getId());
				data.put("title", activity.getTitle());
				data.put("url", activity.getUrl());
				data.put("image" + split + "url", activity.getResource().getPath());
				datalist.add(data);
			}
			result.init(SUCCESS_STATUS, "加载活动成功！", datalist);
		}
		else {
			result.init(SUCCESS_STATUS, "无活动数据！", datalist);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}
}
