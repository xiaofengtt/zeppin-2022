package cn.zeppin.action.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.UserSubject;
import cn.zeppin.service.api.ICategoryRetrieveService;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IRetrieveService;
import cn.zeppin.service.api.ISsoTaskService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISsoUserTestService;
import cn.zeppin.service.api.ISubjectCountDownService;
import cn.zeppin.service.api.ISubjectRetrieveService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ITaskService;
import cn.zeppin.service.api.IUserSubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class UserSubjectAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9048353537181313697L;

	private ISubjectService subjectService;
	private ICategoryService categoryService;
	private IGradeService gradeService;
	private ISubjectCountDownService subjectCountDownService;
	private ICategoryRetrieveService categoryRetrieveService;
	private IRetrieveService retrieveService;
	private ISubjectRetrieveService subjectRetrieveService;
	private IUserSubjectService userSubjectService;

	private ISsoUserService ssoUserService;
	private ITaskService taskService;
	private ISsoTaskService ssoTaskService;

	private ISsoUserTestService ssoUserTestService;
	
    private IItemService itemService;

	/**
	 * 获取用户学科列表
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	public void List() {

		ActionResult result = new ActionResult();
		
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		Integer uid = this.getIntValue(request.getParameter("user.id"));

		if (currentUser == null) {
			currentUser = this.getSsoUserService().getById(uid);
			this.session.setAttribute("user", currentUser);
		}
		if (currentUser != null) {
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("user.id", currentUser.getId());
			searchMap.put("status", Dictionary.NORMAL);
	
			//根据userId,查询出该用户关注的所有学科:
			List<Map<String, Object>> userSubjects = this.userSubjectService.getUserSubjects(uid);
					
			result.init(SUCCESS_STATUS, "获取数据成功！", userSubjects);
		}
		else {
			result.init(ERROR_STATUS, "用户参数有误", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 添加用户备考记录
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER_ARRAY ,emptyable = false, nullable = false)
	public void Add() {

		// =========================================================================================================//
		// 1.先查询出与用户关联的学科，用于判断用户是否有关联过改学科，状态为停用的不做查询。
		// 2.生成MAP，用于key查找
		// =========================================================================================================//

		ActionResult result = new ActionResult();
		SsoUser ssoUser = (SsoUser) this.session.getAttribute("user");
		int uid = this.getIntValue(request.getParameter("user.id"));

		if (ssoUser == null) {
			ssoUser = this.getSsoUserService().getById(uid);
			this.session.setAttribute("user", ssoUser);
		}

		String[] subjectStrs = this.request.getParameterValues("subject.id");
		
		if (ssoUser != null && subjectStrs != null && subjectStrs.length > 0) {
			
			Integer[] subjectIDs = new Integer[subjectStrs.length];
			
			for (int i = 0; i < subjectStrs.length; i++) {
				subjectIDs[i] = this.getIntValue(subjectStrs[i]); 
			}
			
			this.getUserSubjectService().saveUserSubjects(ssoUser, subjectIDs);

			result.init(SUCCESS_STATUS, "添加成功", null);

		} else {
			result.init(ERROR_STATUS, "用户或学科参数有误", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 删除用户备考记录
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	public void Delete() {

		// ============================================================//
		// 1.把状态 改为 Close
		// ============================================================//

		ActionResult result = new ActionResult();
		SsoUser ssoUser = (SsoUser) this.session.getAttribute("user");
		int uid = this.getIntValue(request.getParameter("user.id"));
		int subjectId = this.getIntValue(request.getParameter("subject.id"));

		if (ssoUser == null) {
			ssoUser = this.getSsoUserService().getById(uid);
			this.session.setAttribute("user", ssoUser);
		}
		
		UserSubject us = this.getUserSubjectService().getByKey(uid, subjectId);
		
		if (us != null) {
			us = this.getUserSubjectService().deleteUserSubject(us);
			result.init(SUCCESS_STATUS, "删除成功", null);
		} else {
			// 没有可以删除的
			result.init(ERROR_STATUS, "不存在备考记录", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}
	
	
	/**
	 * 获取用户该学科的一些主观题题型信息
	 * @return
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, emptyable = false, nullable = false)	
	@ActionParam(key = "isStandard", type = ValueType.NUMBER)
	public void ItemTypeList() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		
		SsoUser ssoUser = (SsoUser) this.session.getAttribute("user");
		Integer uid = this.getIntValue(request.getParameter("user.id"));
		Integer subjectId = this.getIntValue(request.getParameter("subject.id"));
		Integer isStandard = this.getIntValue(request.getParameter("isStandard"));
		
		UserSubject us = this.getUserSubjectService().getByKey(uid, subjectId);
		
		if (ssoUser == null) {
			ssoUser = this.getSsoUserService().getById(uid);
			this.session.setAttribute("user", ssoUser);
		}
		
		if (ssoUser != null && us != null) {
			Subject subject = us.getSubject();
			List<Map<String,Object>> itemTypeList = this.getUserSubjectService().getUserSubjectItemType(ssoUser, subject, isStandard);
			List<Map<String,Object>> dataList = new ArrayList<>();
			for (Map<String,Object> itemTypeMap : itemTypeList) {
				Map<String,Object> data = new HashMap<>();
				data.put("id", itemTypeMap.get("id"));
				data.put("inx", itemTypeMap.get("inx"));
				data.put("itemType" + split + "id", itemTypeMap.get("itemTypeId"));
				data.put("itemType" + split + "name", itemTypeMap.get("itemTypeName"));
				data.put("subject" + split + "id", itemTypeMap.get("subjectId"));
				data.put("lastRightItemCount", itemTypeMap.get("lastRightItemCount"));
				data.put("releasedItemCount", itemTypeMap.get("releasedItemCount"));
				
				dataList.add(data);
				
			}
			result.init(SUCCESS_STATUS, "获取成功", dataList);
		}
		else {
			result.init(ERROR_STATUS, "不存在备考记录", null);
		}
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	public ISubjectCountDownService getSubjectCountDownService() {
		return subjectCountDownService;
	}

	public void setSubjectCountDownService(ISubjectCountDownService subjectCountDownService) {
		this.subjectCountDownService = subjectCountDownService;
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

	public void setSubjectRetrieveService(ISubjectRetrieveService subjectRetrieveService) {
		this.subjectRetrieveService = subjectRetrieveService;
	}

	public IUserSubjectService getUserSubjectService() {
		return userSubjectService;
	}

	public void setUserSubjectService(IUserSubjectService userSubjectService) {
		this.userSubjectService = userSubjectService;
	}

	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public ISsoTaskService getSsoTaskService() {
		return ssoTaskService;
	}

	public void setSsoTaskService(ISsoTaskService ssoTaskService) {
		this.ssoTaskService = ssoTaskService;
	}

	public ISsoUserTestService getSsoUserTestService() {
		return ssoUserTestService;
	}

	public void setSsoUserTestService(ISsoUserTestService ssoUserTestService) {
		this.ssoUserTestService = ssoUserTestService;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
}
