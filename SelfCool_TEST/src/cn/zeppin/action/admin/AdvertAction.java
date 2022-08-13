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
import cn.zeppin.entity.Advert;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IAdvertService;
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
public class AdvertAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private IAdvertService advertService;
	private IResourceService resourceService;
	
	public IAdvertService getAdvertService() {
		return advertService;
	}

	public void setAdvertService(IAdvertService advertService) {
		this.advertService = advertService;
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
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "weight", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add(){
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		short status = Short.valueOf(this.request.getParameter("status"));
		short weight = Short.valueOf(this.request.getParameter("weight"));
		Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
		
		Advert advert = new Advert();
		advert.setSysUser(currentUser);
		advert.setName(name);
		advert.setCreatetime(new Timestamp((new Date()).getTime()));
		advert.setStatus(status);
		advert.setWeight(weight);
		
		Resource resource = this.getResourceService().getById(resourceID);
		if (resource == null) {
			result.init(FAIL_STATUS, "广告图片不存在", null);
		}else{
			advert.setResource(resource);			
			this.getAdvertService().addAdvert(advert); 
			Map<String, Object> data = SerializeEntity.advert2Map(advert);
			result.init(SUCCESS_STATUS, "添加广告成功！", data);
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
		Integer advertID = Integer.valueOf(request.getParameter("id"));
		Advert advert = this.getAdvertService().getAdvertById(advertID);
		if (advert != null){
			this.getAdvertService().deleteAdvert(advert);
			Map<String, Object> data = SerializeEntity.advert2Map(advert);
			result.init(SUCCESS_STATUS, "删除广告成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的广告ID信息！", null);
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
		Integer advertID = Integer.valueOf(request.getParameter("id"));
		String split =request.getParameter("split");
		Advert advert = this.getAdvertService().getAdvertById(advertID);
		if (advert != null) {
			Map<String, Object> data = SerializeEntity.advert2Map(advert,split);
			result.init(SUCCESS_STATUS, "加载广告成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的广告ID信息！", null);
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
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "weight", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update(){
		ActionResult result = new ActionResult();
		Integer advertID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		short status = Short.valueOf(this.request.getParameter("status"));
		short weight = Short.valueOf(this.request.getParameter("weight"));

		Advert advert = this.getAdvertService().getAdvertById(advertID);
		if (advert != null){
			advert.setName(name);
			advert.setStatus(status);
			advert.setWeight(weight);
			if (request.getParameterMap().containsKey("resource.id")) {
				if (Utlity.isNumeric(request.getParameter("resource.id"))) {
					Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
					Resource resource = this.getResourceService().getById(resourceID);
					if (resource == null) {
						result.init(FAIL_STATUS, "图标不存在", null);
					}
					advert.setResource(resource);
				}
			}	
			this.getAdvertService().updateAdvert(advert); 
			Map<String, Object> data = SerializeEntity.advert2Map(advert);
			result.init(SUCCESS_STATUS, "修改广告成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的广告ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 分类列表
	 */
//	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "sysUser.name", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "push", type = ValueType.NUMBER)
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
		
		int recordCount =this.getAdvertService().getAdvertCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);

		List<Advert> advertList = getAdvertService().getAdvertListByParams(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (advertList != null && advertList.size() > 0){
			for (Advert advert : advertList){
				Map<String, Object> data = SerializeEntity.advert2Map(advert);
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
	
}
