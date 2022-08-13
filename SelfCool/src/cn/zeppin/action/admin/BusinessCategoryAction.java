/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.action.admin
 * SubjectItemTypeAction
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Business;
import cn.zeppin.entity.BusinessCategory;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IBusinessCategoryService;
import cn.zeppin.service.api.IBusinessService;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.utility.Utlity;

/**
 * @author Clark 下午4:02:40
 */
public class BusinessCategoryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8974816209865224021L;

	private IBusinessService businessService;
	private IBusinessCategoryService businessCategoryService;
	private ICategoryService categoryService;

	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessCategoryService getBusinessCategoryService() {
		return businessCategoryService;
	}

	public void setBusinessCategoryService(IBusinessCategoryService businessCategoryService) {
		this.businessCategoryService = businessCategoryService;
	}

	/**
	 * 业务下的分类管理
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "business.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "categorys", type = ValueType.NUMBER)
	public void Update() {
		ActionResult result = new ActionResult();
		Integer businessID = this.getIntValue(request.getParameter("business.id"));
		String[] categorys = request.getParameterValues("categorys");
		Business business = this.getBusinessService().getBusinessById(businessID);
		if (business != null) {
			List<Category> categoryList = new ArrayList<>();
			if (categorys != null) {
				for (String categoryid : categorys) {
					Category category = this.getCategoryService().getCategoryById(Integer.valueOf(categoryid));
					if (category != null) {
						categoryList.add(category);
					}
				}
			}
			this.getBusinessCategoryService().addBusinessCategorys(business, categoryList);
			List<Map<String, Object>> dataList = new ArrayList<>();
			Map<String, Object> data = SerializeEntity.business2Map(business);
			dataList.add(data);
			result.init(SUCCESS_STATUS, "更新学科题型成功！", dataList);
		} else {
			result.init(FAIL_STATUS, "学科ID信息不正确!", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 搜索
	 * 
	 * @author Clark
	 * @date 2014年7月30日下午5:14:52
	 */
	@ActionParam(key = "business.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Search() {
		ActionResult result = new ActionResult();
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("business.id", request.getParameter("business.id"));
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		List<BusinessCategory> businessCategorys = this.getBusinessCategoryService().getBusinessCategorys(searchMap);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (businessCategorys != null && businessCategorys.size() > 0) {
			for (BusinessCategory businessCategory : businessCategorys) {
				Map<String, Object> data = SerializeEntity.businessCategory2Map(businessCategory, split);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
}
