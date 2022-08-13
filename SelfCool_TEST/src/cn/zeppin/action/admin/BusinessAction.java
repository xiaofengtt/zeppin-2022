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
import cn.zeppin.entity.Business;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IBusinessCategoryService;
import cn.zeppin.service.api.IBusinessService;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/** 
 * ClassName: BusinessAction <br/> 
 * date: 2014年7月20日 下午7:05:24 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class BusinessAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private IBusinessService businessService;
	private ICategoryService categoryService;
	private IBusinessCategoryService businessCategoryService;
	
	public IBusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}
	
	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public IBusinessCategoryService getBusinessCategoryService() {
		return businessCategoryService;
	}

	public void setBusinessCategoryService(IBusinessCategoryService businessCategoryService) {
		this.businessCategoryService = businessCategoryService;
	}
	/**
	 * 添加
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add(){
		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		Short status = Short.valueOf(this.request.getParameter("status"));
		
		if(this.getBusinessService().getBusinessByName(name)!=null){
			result.init(FAIL_STATUS, "已存在同名业务！", null);
		}
		else{
			Business business = new Business();
			business.setName(name);
			business.setCreatetime(new Timestamp((new Date()).getTime()));
			business.setStatus(status);
			this.getBusinessService().addBusiness(business); 
			Map<String, Object> data = SerializeEntity.business2Map(business);
			result.init(SUCCESS_STATUS, "添加业务成功！", data);
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
		Integer businessID = Integer.valueOf(request.getParameter("id"));
		Business business = this.getBusinessService().getBusinessById(businessID);
		if (business != null){
			this.getBusinessService().deleteBusiness(business);
			Map<String, Object> data = SerializeEntity.business2Map(business);
			result.init(SUCCESS_STATUS, "删除业务成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的业务ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 加载，一般用于修改的时候load
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load(){
		ActionResult result = new ActionResult();
		Integer businessID = Integer.valueOf(request.getParameter("id"));
		String split =request.getParameter("split");
		Business business = this.getBusinessService().getBusinessById(businessID);
		if (business != null) {
			Map<String, Object> data = SerializeEntity.business2Map(business,split);
			result.init(SUCCESS_STATUS, "加载业务成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的业务ID信息！", null);
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
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update(){
		ActionResult result = new ActionResult();
		Integer businessID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		Short status = Short.valueOf(this.request.getParameter("status"));
		
		if(this.getBusinessService().getBusinessByName(name)!=null&&this.getBusinessService().getBusinessByName(name).getId()!=businessID){
			result.init(FAIL_STATUS, "已存在同名业务！", null);
		}
		else{
			Business business = this.getBusinessService().getBusinessById(businessID);
			if (business != null){
				business.setName(name);
				business.setStatus(status);
				this.getBusinessService().updateBusiness(business); 
				Map<String, Object> data = SerializeEntity.business2Map(business);
				result.init(SUCCESS_STATUS, "修改业务信息成功！", data);
			}
			else {
				result.init(FAIL_STATUS, "无效的业务ID信息！", null);
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 列表
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void List(){
		
		ActionResult result = new ActionResult();
		
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("status", request.getParameter("status"));
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount =this.getBusinessService().getBusinessCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);

		List<Business> businessList = getBusinessService().getBusinessListByParams(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (businessList != null && businessList.size() > 0){
			for (Business business : businessList){
				Map<String, Object> data = SerializeEntity.business2Map(business);
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
	 * 给业务添加分类
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "business.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void AddCategory() {

		ActionResult result = new ActionResult();

		int businessId = this.getIntValue(this.request.getParameter("business.id"));
		Business business = this.getBusinessService().getBusinessById(businessId);

		String[] categorys = this.request.getParameterValues("categoryId");

		List<Category> lsCas = new ArrayList<Category>();

		for (String ca : categorys) {

			int caId = this.getIntValue(ca, -1);
			Category category = this.getCategoryService().getCategoryById(caId);
			if (category != null) {
				lsCas.add(category);
			}

		}

		this.getBusinessCategoryService().addBusinessCategorys(business, lsCas);

		result.init(SUCCESS_STATUS, "添加成功", null);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

}
