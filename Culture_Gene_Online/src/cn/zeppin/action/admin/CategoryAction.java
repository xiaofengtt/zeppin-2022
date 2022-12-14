package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class CategoryAction extends BaseAction {

	private static final long serialVersionUID = -3277893199613220095L;
	private ICategoryService categoryService;

	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@ActionParam(key = "parent", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void List() {
		ActionResult result = new ActionResult();
		HashMap<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("parent", request.getParameter("parent"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("level", request.getParameter("level"));
		searchMap.put("status", request.getParameter("status"));

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.categoryService.getCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
		
		List<Category> categoryList = this.categoryService.getListForPage(searchMap, sorts, offset, pagesize);

		List<Map<String, Object>> dataList = new ArrayList<>();
		if (categoryList != null && categoryList.size() > 0) {
			for (Category category : categoryList) {
				Map<String, Object> data = SerializeEntity.category2Map(category);
				boolean hasChild = this.categoryService.hasChild(category.getId());
				data.put("haschild", hasChild);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "???????????????", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * ??????
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));

		Category category = this.categoryService.getCategory(id);
		if (category != null) {
			Map<String, Object> data = SerializeEntity.category2Map(category);
			result.init(SUCCESS_STATUS, "???????????????????????????", data);
		} else {
			result.init(FAIL_STATUS, "???????????????ID???", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * ??????
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "parent", type = ValueType.NUMBER)
	public void Add() {
		ActionResult result = new ActionResult();
		String name = request.getParameter("name").trim();
		Integer status = Integer.valueOf(request.getParameter("status"));
		String parent = request.getParameter("parent");
		
		if(name == null || name.equals("")){
			result.init(FAIL_STATUS, "??????????????????", null);
		}else{
			Category category = new Category();
			category.setName(name);
			category.setStatus(status);
			Integer level = 1;
			if(parent!=null && !parent.equals("")){
				Category parentCategory = this.categoryService.getCategory(Integer.valueOf(parent));
				if(parentCategory !=null){
					level = parentCategory.getLevel() + 1;
					parentCategory.setId(Integer.valueOf(parent));
					category.setParent(parentCategory);
				}
			}
			category.setLevel(level);
			category = this.categoryService.addCategory(category);
			Map<String, Object> data = SerializeEntity.category2Map(category);
			result.init(SUCCESS_STATUS, "????????????", data);
		}
		
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * ??????
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Edit() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name").trim();
		Integer status = Integer.valueOf(request.getParameter("status"));
		
		if(name == null || name.equals("")){
			result.init(FAIL_STATUS, "??????????????????", null);
		}else{
			Category category = this.categoryService.getCategory(id);
			if(category == null){
				result.init(FAIL_STATUS, "???????????????", null);
			}else{
				category.setName(name);
				category.setStatus(status);
				category = this.categoryService.updateCategory(category);
				Map<String, Object> data = SerializeEntity.category2Map(category);
				result.init(SUCCESS_STATUS, "????????????", data);
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * ??????
	 */
	@AuthorityParas(userGroupName = "ADMIN")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult result = new ActionResult();
		Integer id = Integer.valueOf(request.getParameter("id"));
		Category category = this.categoryService.getCategory(id);
		if (category != null) {
			category = this.categoryService.deleteCategory(category);
			Map<String, Object> data = SerializeEntity.category2Map(category);
			result.init(SUCCESS_STATUS, "???????????????", data);
		} else {
			result.init(FAIL_STATUS, "??????????????????", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * ?????????
	 */
	@ActionParam(key = "parent", type = ValueType.NUMBER)
	public void LoadCategoryNav() {
		ActionResult result = new ActionResult();
		
		Integer id = 0;
		if(request.getParameter("parent")!=null && !request.getParameter("parent").equals("")){
			id = Integer.valueOf(request.getParameter("parent"));
		}
		Category category = this.getCategoryService().getCategory(id);

		if (category == null) {
			result.init(FAIL_STATUS, null, null);
		} else {
			LinkedList<Category> categorylist = new LinkedList<Category>();
			categorylist.add(category);

			while (category.getParent() != null) {
				category = category.getParent();
				categorylist.add(category);
			}
			List<Map<String, Object>> dataList = new ArrayList<>();
			int i=categorylist.size()-1;
			for(;i>=0;i--){
				Category cate = categorylist.get(i);
				Map<String, Object> data = SerializeEntity.category2Map(cate);
				dataList.add(data);
			}
			result.init(SUCCESS_STATUS, null, dataList);
		}
		Utlity.ResponseWrite(result, dataType, response);

	}

}
