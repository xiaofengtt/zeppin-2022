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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.CategoryRetrieve;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.entity.RetrieveType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ICategoryRetrieveService;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.service.api.IRetrieveService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISubjectRetrieveService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/** 
 * ClassName: CategoryAction <br/> 
 * date: 2014年7月20日 下午7:05:24 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class CategoryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private ICategoryService categoryService;
	private IResourceService resourceService;
	private ICategoryRetrieveService categoryRetrieveService;
	private IRetrieveService retrieveService;
	private ISubjectRetrieveService subjectRetrieveService;
	private ISsoUserService ssoUserService;
	
	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public ICategoryRetrieveService getCategoryRetrieveService() {
		return categoryRetrieveService;
	}

	public void setCategoryRetrieveService(ICategoryRetrieveService categoryRetrieveService) {
		this.categoryRetrieveService = categoryRetrieveService;
	}
	
	public IRetrieveService getRetrieveService() {
		return retrieveService;
	}

	public void setRetrieveService(IRetrieveService retrieveService) {
		this.retrieveService = retrieveService;
	}

	public ISubjectRetrieveService getSubjectRetrieveService() {
		return subjectRetrieveService;
	}

	public void setSubjectRetrieveService(
			ISubjectRetrieveService subjectRetrieveService) {
		this.subjectRetrieveService = subjectRetrieveService;
	}

	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	/**
	 * 添加分类
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "category.id", type = ValueType.NUMBER)
	@ActionParam(key = "resource.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "shortname", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add(){
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		short status = Short.valueOf(this.request.getParameter("status"));
		String[] retrieveTypes = request.getParameterValues("retrieveTypes");
		
		if(this.getCategoryService().getCategoryByName(name)!=null){
			result.init(FAIL_STATUS, "已存在同名分类！", null);
		}
		else{
			Category category = new Category();
			category.setSysUser(currentUser);
			category.setName(name);
			category.setCreatetime(new Timestamp((new Date()).getTime()));
			category.setStatus(status);
			category.setLevel((short)1);
			if (request.getParameterMap().containsKey("resource.id")) {
				if (Utlity.isNumeric(request.getParameter("resource.id"))) {
					Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
					Resource resource = this.getResourceService().getById(resourceID);
					if (resource == null) {
						result.init(FAIL_STATUS, "图标不存在", null);
					}
					category.setResource(resource);
				}
			}
			if (request.getParameterMap().containsKey("category.id")) {
				if (Utlity.isNumeric(request.getParameter("category.id"))) {
					Integer categoryID = Integer.parseInt(request.getParameter("category.id"));
					Category parent = this.getCategoryService().getCategoryById(categoryID);
					if (parent == null) {
						result.init(FAIL_STATUS, "父分类不存在", null);
					}
					category.setCategory(parent);
					category.setLevel((short)(parent.getLevel() + 1));
				}
			}
			List<RetrieveType> retrieveTypeList = new ArrayList<>();
			if (retrieveTypes != null) {
				for (String retrieveTypeID : retrieveTypes) {
					RetrieveType retrieveType = new RetrieveType();
					retrieveType.setId(Integer.valueOf(retrieveTypeID));
					retrieveTypeList.add(retrieveType);
				}
			}
			if (request.getParameterMap().containsKey("shortname")) {
				category.setShortname(request.getParameter("shortname"));
			}
			this.getCategoryService().addCategory(category,retrieveTypeList); 
			Map<String, Object> data = SerializeEntity.category2Map(category);
			result.init(SUCCESS_STATUS, "添加分类成功！", data);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 删除分类
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete(){
		ActionResult result = new ActionResult();
		Integer categoryID = Integer.valueOf(request.getParameter("id"));
		Category category = this.getCategoryService().getCategoryById(categoryID);
		if (category != null){
			this.getCategoryService().deleteCategory(category);
			Map<String, Object> data = SerializeEntity.category2Map(category);
			result.init(SUCCESS_STATUS, "删除分类成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的分类ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 加载分类，一般用于修改的时候load
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load(){
		ActionResult result = new ActionResult();
		Integer categoryID = Integer.valueOf(request.getParameter("id"));
		String split =request.getParameter("split");
		Category category = this.getCategoryService().getCategoryById(categoryID);
		if (category != null) {
			Map<String, Object> data = SerializeEntity.category2Map(category,split);
			List<Map<String, Object>> retrieveTypeList = new ArrayList<>();
			List<CategoryRetrieve> categoryRetrieves = this.getCategoryRetrieveService().getCategoryRetrieves(category);
			for (CategoryRetrieve sategoryRetrieve : categoryRetrieves) {
				Map<String, Object> retrieveType = new HashMap<>();
				retrieveType.put("retrieveType" + split + "id", sategoryRetrieve.getRetrieveType().getId());
				retrieveType.put("retrieveType" + split + "name", sategoryRetrieve.getRetrieveType().getName());
				retrieveTypeList.add(retrieveType);
			}
			data.put("retrieveTypes", retrieveTypeList);
			result.init(SUCCESS_STATUS, "加载分类成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的分类ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 修改分类
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "category.id", type = ValueType.NUMBER)
	@ActionParam(key = "resource.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "shortname", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update(){
		ActionResult result = new ActionResult();
		Integer categoryID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		String[] retrieveTypes = request.getParameterValues("retrieveTypes");
		short status = Short.valueOf(this.request.getParameter("status"));
		
		if(this.getCategoryService().getCategoryByName(name)!=null&&this.getCategoryService().getCategoryByName(name).getId()!=categoryID){
			result.init(FAIL_STATUS, "已存在同名分类！", null);
		}
		else{
			Category category = this.getCategoryService().getCategoryById(categoryID);
			if (category != null){
				category.setName(name);
				category.setStatus(status);
				if (request.getParameterMap().containsKey("resource.id")) {
					if (Utlity.isNumeric(request.getParameter("resource.id"))) {
						Integer resourceID = Integer.parseInt(request.getParameter("resource.id"));
						Resource resource = this.getResourceService().getById(resourceID);
						if (resource == null) {
							result.init(FAIL_STATUS, "图标不存在", null);
						}
						category.setResource(resource);
					}
				}
				if (request.getParameterMap().containsKey("category.id")) {
					if (Utlity.isNumeric(request.getParameter("category.id"))) {
						Integer parentID = Integer.parseInt(request.getParameter("category.id"));
						Category parent = this.getCategoryService().getCategoryById(parentID);
						if (parent == null) {
							result.init(FAIL_STATUS, "父分类不存在", null);
						}
						category.setCategory(parent);
					}
				}
				if (request.getParameterMap().containsKey("shortname")) {
					category.setShortname(request.getParameter("shortname"));
				}
				List<RetrieveType> retrieveTypeList = new ArrayList<>();
				if (retrieveTypes != null) {
					for (String retrieveTypeID : retrieveTypes) {
						RetrieveType retrieveType = new RetrieveType();
						retrieveType.setId(Integer.valueOf(retrieveTypeID));
						retrieveTypeList.add(retrieveType);
					}
				}
				this.getCategoryService().updateCategory(category,retrieveTypeList); 
				Map<String, Object> data = SerializeEntity.category2Map(category);
				result.init(SUCCESS_STATUS, "修改分类信息成功！", data);
			}
			else {
				result.init(FAIL_STATUS, "无效的分类ID信息！", null);
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 分类列表
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "shortname", type = ValueType.STRING)
	@ActionParam(key = "level", type = ValueType.NUMBER)
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
		searchMap.put("shortname", request.getParameter("shortname"));
		searchMap.put("level", request.getParameter("level"));
		searchMap.put("scode", request.getParameter("scode"));
		searchMap.put("category.id", request.getParameter("category.id"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));
		searchMap.put("sysUser.name", request.getParameter("sysUser.name"));
		searchMap.put("status", request.getParameter("status"));
		
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount =this.getCategoryService().getCategoryCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);

		List<Category> categoryList = getCategoryService().getCategoryListByParams(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (categoryList != null && categoryList.size() > 0){
			for (Category category : categoryList){
				Map<String, Object> data = SerializeEntity.category2Map(category);
				dataList.add(data);
			}
		}
		System.out.println("dataList:"+dataList);
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}
	
	/**
	 * 获取 分类 导航
	 * 
	 * @author Administrator
	 * @date: 2014年8月7日 下午5:49:19 <br/>
	 */
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void LoadCategoryNav() {
		String id = request.getParameter("category.id");
		Integer categoryID = this.getIntValue(id, 0);
		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		Category category = this.getCategoryService().getCategoryById(categoryID);

		if (category == null) {
			result.init(FAIL_STATUS, null, null);
		}
		else{
			
			LinkedList<Category> categorylist = new LinkedList<Category>();
			categorylist.add(category);
			
			while(category.getCategory()!=null){
				category = category.getCategory();
				categorylist.add(category);
			}
			List<Map<String, Object>> liM = new ArrayList<>();
			int i=categorylist.size()-1;
			for(;i>=0;i--){
				Category cate = categorylist.get(i);
				Map<String, Object> data = SerializeEntity.category2Map(cate);
				liM.add(data);
			}
			result.init(SUCCESS_STATUS, null, liM);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 用户选择考试分类 获取考试多维度的细分考试科目
	 * 
	 * @author elegantclack
	 * @date 2015-03-21 4:02:35pm  <br/>
	 */
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void LoadSubjects(){
		
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		
		String id = request.getParameter("category.id");
		Integer categoryID = this.getIntValue(id, 0);
		String dataType = request.getParameter("datatype");
		
		SsoUser ssoUser = (SsoUser) this.session.getAttribute("user");
		
		int uid = this.getIntValue(request.getParameter("user.id"));

		if (ssoUser == null) {
			ssoUser = this.getSsoUserService().getById(uid);
			this.session.setAttribute("user", ssoUser);
		}
		
		ActionResult result = new ActionResult();
		Category category = this.getCategoryService().getCategoryById(categoryID);
		
		if (category == null || ssoUser == null) {
			result.init(FAIL_STATUS, "找不到此id的信息", null);
		}
		else{
			List<CategoryRetrieve> categoryRetrieves =  this.getCategoryRetrieveService().getCategoryRetrieves(category);
			List<Map<String, Object>> dataList = new ArrayList<>();
			if (categoryRetrieves.size() > 0 ) {
				/**
				 * categoryRetrieves.get(0);
				 * 目前采用扁平化的分类原则，暂不设置复杂分类，多维度的分类通过组合的方式形成扁平分类
				 * 如：计算机等级考试  等级：二级、三级、四级  语言：C、JAVA、VF、VB
				 * 扁平化后：二级C、二级JAVA、三级网络
				 */
				CategoryRetrieve categoryRetrieve = categoryRetrieves.get(0); 
				//
				HashMap<String, Object> retrieveSearchMap = new HashMap<>();
				retrieveSearchMap.put("retrieveType.id", categoryRetrieve.getRetrieveType().getId());
				retrieveSearchMap.put("status", Dictionary.RETRIEVE_STATUS_NOMAL);
				List<Retrieve> retrieves = this.getRetrieveService().searchRetrieves(retrieveSearchMap, null, -1, -1);
				if (retrieves.size() > 0) {
					//用户可选的学科列表
					List<Map<String, Object>> subjectRetrieves = this.getSubjectRetrieveService().
							getSubjectRetrieves(ssoUser, category, retrieves);
					
					for (Retrieve retrieve : retrieves){
						Map<String, Object> retrieveMap = new HashMap<>();
						retrieveMap.put("id", retrieve.getId());
						retrieveMap.put("name", retrieve.getName());
						retrieveMap.put("retrieveType" + split + "id", retrieve.getRetrieveType().getId());
						retrieveMap.put("retrieveType" + split + "name", retrieve.getRetrieveType().getName());
						List<Map<String, Object>> subjects = new ArrayList<>();
						for(Map<String, Object> subjectRetrieve : subjectRetrieves) {
							if (subjectRetrieve.get("retrieveid").equals(retrieve.getId())) {
								subjects.add(subjectRetrieve);
							}
						}
						retrieveMap.put("subjects", subjects);
						
						dataList.add(retrieveMap);
					}
				}
			}
			
			result.init(SUCCESS_STATUS, "加载成功", dataList);

		}
		Utlity.ResponseWrite(result, dataType, response);
	}
}
