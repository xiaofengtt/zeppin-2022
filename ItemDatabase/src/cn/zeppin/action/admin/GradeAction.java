/**
 * Project Name:ItemDatabase  File Name:GradeAction.java Package
 * Name:cn.zeppin.action.base Copyright (c) 2014, Zeppin All Rights Reserved.
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
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.utility.DataTimeConvert;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * @category 学段操作 ClassName: GradeAction <br/>
 *           Function: TODO ADD FUNCTION. <br/>
 *           date: 2014年7月15日 下午2:12:15 <br/>
 * 
 * @author sj
 * @version
 * @since JDK 1.7
 */
public class GradeAction extends BaseAction implements IStandardAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3277893199613220095L;
	private IGradeService gradeService;

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	/**
	 * 
	 * @question 这个接口只能取一级的grade，有把接口设计变成了initpage的嫌疑
	 * @question 为何只有这个接口不返回pagenum等信息？
	 * @question 不序列化对象这个接口能输出么
	 */
	@Override
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void List() {

		ActionResult actionResult = new ActionResult();
		HashMap<String, Object> hashMap = getPageAndSortParas();// 获取分页及排序参数
		String sorts = hashMap.get("sorts").toString();
		int offset = (Integer) hashMap.get("offset");
		int pageSize = (Integer) hashMap.get("pagesize");
		HashMap<String, Object> paras = new HashMap<String, Object>();
		if (request.getParameterMap().containsKey("name")) {
			paras.put("name", request.getParameter("name"));
		}
		if (request.getParameterMap().containsKey("level")) {
			if (Utlity.isNumeric(request.getParameter("level"))) {
				paras.put("level", request.getParameter("level"));
			}
		}
		if (request.getParameterMap().containsKey("grade.id")) {
			if (Utlity.isNumeric(request.getParameter("grade.id"))) {
				paras.put("grade", request.getParameter("grade.id"));
			}
		}
		if (request.getParameterMap().containsKey("id")) {
			if (Utlity.isNumeric(request.getParameter("id"))) {
				paras.put("id", request.getParameter("id"));
			}
		}
		if (request.getParameterMap().containsKey("status")) {
			if (Utlity.isNumeric(request.getParameter("status"))) {
				paras.put("status", request.getParameter("status"));
			}
		}
		List<Grade> lstGrades = new ArrayList<>();
		lstGrades = gradeService.getAllGradesByAdmin(offset, pageSize, sorts, paras);
		List<Map<String, Object>> lstData = new ArrayList<>();

		if (lstGrades != null && lstGrades.size() > 0) {
			for (Grade tGrade : lstGrades) {
				Map<String, Object> data = SerializeEntity.grade2Map(tGrade);
				boolean hasChild = gradeService.hasChild(tGrade.getId());
				data.put("haschild", hasChild);
				lstData.add(data);
			}
		}

		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		int totalCount = gradeService.getCountByParas(paras, true);
		actionResult.setTotalCount(totalCount);
		actionResult.setPageSize(pageSize);
		actionResult.setPageNum(offset);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * SCODE是怎么录入进去的 pid如果emptyable=false那么传空值是不行的，对于网页类提交会不会有困难
	 * 
	 */
	@Override
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "fcode", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	public void Add() {

		ActionResult actionResult = new ActionResult();
		Grade grade = getGradeFromPage(actionResult);

		if (grade != null) {
			grade.setCreatetime(DataTimeConvert.getCurrentTime(""));
			SysUser currentUser = (SysUser) session.getAttribute("usersession");
			grade.setSysUser(currentUser);
			gradeService.add(grade);
			Map<String, Object> data = SerializeEntity.grade2Map(grade);
			actionResult.init(SUCCESS_STATUS, "添加" + grade.getName() + "成功", data);

		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * @question 参数没校验可能导致异常
	 * @question 在什么情况下grade==null
	 * @question 这个传入id的记录存不存在没有判断
	 */
	@Override
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "fcode", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	public void Update() {

		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		Grade oldGrade = gradeService.getById(id);
		if (oldGrade == null) {
			actionResult.init(FAIL_STATUS, "学段不存在", null);
		} else {
			Grade grade = null;
			grade = getGradeFromPage(actionResult);
			if (grade != null) {
				oldGrade.setFcode(grade.getFcode());
				oldGrade.setName(grade.getName());
				oldGrade.setStatus(grade.getStatus());
				gradeService.update(oldGrade);
				Map<String, Object> data = SerializeEntity.grade2Map(oldGrade);
				actionResult.init(SUCCESS_STATUS, "学段修改成功", data);
			}

		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * @question 物理删除的话只判断本表是否有下一级数据是否判断完整，请考虑删除方式
	 */
	@Override
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		ActionResult actionResult = new ActionResult();

		if (request.getParameterMap().containsKey("id")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Grade grade = gradeService.getById(id);
			if (grade == null) {
				actionResult.init(FAIL_STATUS, "不存在此学段", null);
			} else {
				gradeService.delete(grade);// 只改状态
				actionResult.init(SUCCESS_STATUS, grade.getName() + "删除成功", null);
			}

		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * @question 把一个map存到另一个map中再取出来。。。
	 * @param actionResult
	 * @return
	 */
	private Grade getGradeFromPage(ActionResult actionResult) {
		Grade grade = new Grade();

		String name = request.getParameter("name");
		grade.setName(name);

		String fcode = request.getParameter("fcode");
		grade.setFcode(fcode);
		
		short level = 1;

		Short status = Short.parseShort(request.getParameter("status"));
		grade.setStatus(status);

		Grade pGrade = null;// 父级
		if (request.getParameterMap().containsKey("grade.id")) {
			if (!request.getParameter("grade.id").equals("")) {
				int pid = Integer.parseInt(request.getParameter("grade.id"));
				level = (short) (gradeService.getLevelByPid(pid) + 1);
				pGrade = gradeService.getById(pid);
			}
		}
		grade.setLevel(level);
		grade.setGrade(pGrade);
		return grade;
	}

	@Override
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void Search() {

		ActionResult actionResult = new ActionResult();
		HashMap<String, Object> hashMap = getPageAndSortParas();// 获取分页及排序参数
		String sorts = hashMap.get("sorts").toString();
		int offset = (Integer) hashMap.get("offset");
		int pageSize = (Integer) hashMap.get("pagesize");
		HashMap<String, Object> paras = new HashMap<String, Object>();
		if (request.getParameterMap().containsKey("name")) {
			paras.put("name", request.getParameter("name"));
		}
		if (request.getParameterMap().containsKey("level")) {
			if (Utlity.isNumeric(request.getParameter("level"))) {
				paras.put("level", request.getParameter("level"));
			}
		}
		if (request.getParameterMap().containsKey("grade.id")) {
			if (Utlity.isNumeric(request.getParameter("grade.id"))) {
				paras.put("grade", request.getParameter("grade.id"));
			}
		}

		if (request.getParameterMap().containsKey("id")) {
			if (Utlity.isNumeric(request.getParameter("id"))) {
				paras.put("id", request.getParameter("id"));
			}
		}

		List<Grade> lstGrades = new ArrayList<>();

		lstGrades = gradeService.getAllGradesByUser(offset, pageSize, sorts, paras);

		List<Map<String, Object>> lstData = new ArrayList<>();
		if (lstGrades != null && lstGrades.size() > 0) {
			for (Grade tGrade : lstGrades) {
				Map<String, Object> data = SerializeEntity.grade2Map(tGrade);
				boolean hasChild = gradeService.hasChild(tGrade.getId());
				data.put("haschild", hasChild);
				lstData.add(data);
			}
		}
		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		int totalCount = gradeService.getCountByParas(paras, false);
		actionResult.setTotalCount(totalCount);
		actionResult.setPageSize(pageSize);
		actionResult.setPageNum(offset);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 学段导航
	 * 
	 * @author Administrator
	 * @date: 2014年8月27日 下午12:20:16 <br/>
	 */
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	public void LoadGradeNav() {

		String id = request.getParameter("grade.id");
		int i_id = this.getIntValue(id, 0);
		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		Grade grade = this.getGradeService().getById(i_id);
		if (grade == null) {
			result.init(FAIL_STATUS, null, null);
		} else {
			LinkedList<Grade> lnklist = new LinkedList<Grade>();
			lnklist.add(grade);

			while (grade.getGrade() != null) {
				grade = grade.getGrade();
				lnklist.add(grade);
			}
			List<Map<String, Object>> liM = new ArrayList<>();
			int i = lnklist.size() - 1;
			for (; i >= 0; i--) {
				Grade cate = lnklist.get(i);
				Map<String, Object> data = SerializeEntity.grade2Map(cate);
				liM.add(data);
			}
			result.init(SUCCESS_STATUS, null, liM);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@SuppressWarnings("unchecked")
	public void SearchAllGrade() {
		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		int gradeId = this.getIntValue(request.getParameter("grade.id"), 0);
		List<Grade> lstGrades = new ArrayList<>();

		// ====================判断角色的学科权限=============================
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		if (currentUser.getRole().getId() == Dictionary.USER_ROLE_EDITOR && session.getAttribute("usergrade") != null) {

			List<Grade> tmpGrade = (List<Grade>) session.getAttribute("usergrade");
			lstGrades.addAll(tmpGrade);

		} else {

			HashMap<String, Object> paras = new HashMap<String, Object>();
			if (gradeId != 0) {
				paras.put("grade", gradeId);
			} else {
				paras.put("level", 1);
			}
			int totalCount = gradeService.getCountByParas(paras, true);
			lstGrades.addAll(gradeService.getAllGradesByAdmin(0, totalCount, "", paras));

		}

		// 把下级所有学段加载出来
		List<Map<String, Object>> lstData = new ArrayList<>();
		if (gradeId != 0) {
			Grade parentGrade = this.getGradeService().getById(gradeId);
			Map<String, Object> parentData = SerializeEntity.grade2Map(parentGrade, split);
			lstData.add(parentData);
			if (lstGrades != null && lstGrades.size() > 0) {
				parentData.put("haschild", true);
			} else {
				parentData.put("haschild", false);
			}
		}
		if (lstGrades != null && lstGrades.size() > 0) {
			for (Grade tGrade : lstGrades) {
				Map<String, Object> data = SerializeEntity.grade2Map(tGrade, split);
				boolean hasChild = gradeService.hasChild(tGrade.getId());
				data.put("haschild", hasChild);
				if (hasChild) {
					loadGradeAll(tGrade, data, split);
				}
				lstData.add(data);
			}
		}

		result.init(SUCCESS_STATUS, "数据获成功", lstData);
		Utlity.ResponseWrite(result, dataType, response);

	}

	private void loadGradeAll(Grade grade, Map<String, Object> map, String split) {

		HashMap<String, Object> paras = new HashMap<String, Object>();
		paras.put("grade", grade.getId());
		int totalCount = gradeService.getCountByParas(paras, true);
		List<Grade> grades = gradeService.getAllGradesByAdmin(0, totalCount, "", paras);

		// 把下级所有学段加载出来
		List<Map<String, Object>> lstData = new ArrayList<>();

		for (Grade tGrade : grades) {
			Map<String, Object> data = SerializeEntity.grade2Map(tGrade, split);
			boolean hasChild = gradeService.hasChild(tGrade.getId());
			data.put("haschild", hasChild);
			if (hasChild) {
				loadGradeAll(tGrade, data, split);
			}
			lstData.add(data);
		}
		map.put("data", lstData);
	}

}
