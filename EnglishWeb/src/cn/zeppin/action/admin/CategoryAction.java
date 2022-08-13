/** 
 * Project Name:CETV_TEST 
 * File Name:CategoryAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
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
import cn.zeppin.action.base.IStandardAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: CategoryAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月15日 下午6:19:08 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class CategoryAction extends BaseAction implements IStandardAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8208522217458301019L;

	private ICategoryService categoryService;

	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	private ISubjectService subjectService;

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	/**
	 * 添加一个分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午6:39:34 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = false, emptyable = true)
	public void Add() {

		// ***********************************
		//
		// 1.验证参数的合法性
		// 2.按名称判断分类是否存在
		//
		// ***********************************

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		SysUser currentUser = (SysUser) session.getAttribute("usersession");

		// 判断同级别下名称是否存在
		String name = request.getParameter("name");
		String pid = request.getParameter("category.id");

		Category tmpCategory = this.getCategoryService().getCategoryByName(name);

		if (tmpCategory != null) {
			result.init(FAIL_STATUS, "已经存在 “" + name + "” 分类!", null);
		} else {

			Category category = new Category();
			category.setName(name);
			category.setSysUser(currentUser);

			// 判断有没有pid
			String level = "1";

			if (!Utlity.isNumeric(pid) || pid.equals("0")) {

				level = "1";

			} else {

				int ipid = Integer.valueOf(pid);
				Category parentCategory = this.getCategoryService().getCategoryById(ipid);

				if (parentCategory != null) {

					int slevel = parentCategory.getLevel() + 1;
					level = slevel + "";
					category.setCategory(parentCategory);

				} else {
					level = "1";
				}
			}

			short slevel = Short.valueOf(level);
			category.setLevel(slevel);

			this.getCategoryService().addCategory(category);

			Map<String, Object> data = SerializeEntity.categroy2Map(category);
			result.init(SUCCESS_STATUS, "添加成功", data);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 编辑一个分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午6:39:41 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {

		// ***********************************
		//
		// 1.验证参数的合法性
		// 2.按名称判断分类是否存在
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		// 判断同级别下名称是否存在
		String name = request.getParameter("name");
		String id = request.getParameter("id");

		Category category = this.getCategoryService().getCategoryById(Integer.valueOf(id));

		if (category != null) {
			if (!category.getName().equals(name)) {

				Category tmpCategory = this.getCategoryService().getCategoryByName(name);

				if (tmpCategory != null) {
					result.init(FAIL_STATUS, "已经存在 “" + name + "” 分类!", null);
				} else {

					category.setName(name);
					this.getCategoryService().updateCategory(category);

					result.init(SUCCESS_STATUS, "修改成功", null);

				}
			} else {

				result.init(WARNING_STATUS, "名称与原来相同", null);
			}
		} else {

			result.init(FAIL_STATUS, "分类不存在!", null);

		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 加载某一个分类数据
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午1:41:38 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {

		// ***********************************
		//
		// 1.验证参数的合法性
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		String id = request.getParameter("id");

		ActionResult result = new ActionResult();

		Category category = this.getCategoryService().getCategoryById(Integer.valueOf(id));
		if (category != null) {
			Map<String, Object> map = SerializeEntity.categroy2Map(category);
			result.init(SUCCESS_STATUS, null, map);
		} else {
			result.init(FAIL_STATUS, "分类不存在!", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除摸个分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午1:41:53 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		// ***********************************
		//
		// 1.验证参数的合法性
		// 2.判断是否能删除
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		String id = request.getParameter("id");

		ActionResult result = new ActionResult();

		Category category = this.getCategoryService().getCategoryById(Integer.valueOf(id));

		if (category != null) {

			int subjectCount = this.getSubjectService().getSubjectCountByCategory(category.getId(), 0);

			if (subjectCount > 0) {
				// 当前分类下的学科个数大于0
				result.init(FAIL_STATUS, "删除失败，当前分类下存在学科！", null);
			} else {

				HashMap<String, Object> params = new HashMap<>();
				params.put("pid", category.getId());
				int subCount = this.getCategoryService().getCategoryCountByParams(params);

				if (subCount > 0) {
					result.init(FAIL_STATUS, "当前分类下存在子分类", null);
				} else {
					this.getCategoryService().deleteCategory(category);
					result.init(SUCCESS_STATUS, null, null);
				}
			}
		} else {
			result.init(FAIL_STATUS, "删除失败，当前分类不存在！", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 获取 分类列表 用于分类列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午6:39:44 <br/>
	 */
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "category.id", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		HashMap<String, Object> params = new HashMap<>();
		params.put("pid", request.getParameter("category.id"));
		params.put("name", request.getParameter("name"));

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int records = this.getCategoryService().getCategoryCountByParams(params);
		List<Category> liT = this.getCategoryService().getCategoryListByParams(params, sorts, offset, pagesize);

		List<Map<String, Object>> liM = new ArrayList<>();
		for (Category cate : liT) {
			Map<String, Object> data = SerializeEntity.categroy2Map(cate);

			HashMap<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("pid", cate.getId());
			int subCount = this.getCategoryService().getCategoryCountByParams(paramsMap);

			data.put("haschild", subCount > 0 ? true : false);

			liM.add(data);
		}
		result.init(SUCCESS_STATUS, null, liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 获取 分类列表 用于分类列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午6:39:44 <br/>
	 */
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "category.id", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void Search() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		HashMap<String, Object> params = new HashMap<>();
		params.put("pid", request.getParameter("category.id"));
		params.put("name", request.getParameter("name"));

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int records = this.getCategoryService().getCategoryCountByParams(params);
		List<Category> liT = this.getCategoryService().getCategoryListByParams(params, sorts, offset, pagesize);

		List<Map<String, Object>> liM = new ArrayList<>();
		for (Category cate : liT) {
			liM.add(SerializeEntity.categroy2Map(cate));
		}
		result.init(SUCCESS_STATUS, null, liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 获取 分类 导航
	 * 
	 * @author Administrator
	 * @date: 2014年8月7日 下午5:49:19 <br/>
	 */
	@ActionParam(key = "category.id", type = ValueType.NUMBER)
	public void LoadCategoryNav() {

		String id = request.getParameter("category.id");
		int i_id = this.getIntValue(id, 0);
		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		Category category = this.getCategoryService().getCategoryById(i_id);

		if (category == null) {
			result.init(FAIL_STATUS, null, null);
		}
		else{
			
			LinkedList<Category> lnklist = new LinkedList<Category>();
			lnklist.add(category);
			
			while(category.getCategory()!=null){
				category = category.getCategory();
				lnklist.add(category);
			}
			List<Map<String, Object>> liM = new ArrayList<>();
			int i=lnklist.size()-1;
			for(;i>=0;i--){
				Category cate = lnklist.get(i);
				Map<String, Object> data = SerializeEntity.categroy2Map(cate);
				liM.add(data);
			}
			result.init(SUCCESS_STATUS, null, liM);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}
}
