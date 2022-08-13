/** 
 * Project Name:CETV_TEST 
 * File Name:SubjectAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.IStandardAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: SubjectAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月20日 下午5:23:35 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class SubjectAction extends BaseAction implements IStandardAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1381225742757194727L;

	private ISubjectService subjectService;
	private ICategoryService categoryService;
	private IGradeService gradeService;
	private ISysUserService sysUserService;

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

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 添加一个学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:26:18 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void Add() {

		// ***********************************
		//
		// 1.验证参数的合法性
		// 2.按名称判断名称是否存在
		//
		// ***********************************

		String dataType = request.getParameter("datatype");

		SysUser currentUser = (SysUser) session.getAttribute("usersession");

		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		String gradeId = request.getParameter("grade.id");
		String categoryId = request.getParameter("category.id");
		String status = request.getParameter("status");

		Subject tmpSubject = this.getSubjectService().getSubjectByName(name);
		if (tmpSubject != null) {
			result.init(FAIL_STATUS, "已经存在 “" + name + "” 学科！", null);
		} else {

			// 设置分类
			Category category = this.getCategoryService().getCategoryById(Integer.valueOf(categoryId));
			if (category == null) {
				result.init(FAIL_STATUS, "分配的分类不存在！", null);
			} else {

				Subject subject = new Subject();
				subject.setName(name);
				subject.setSysUser(currentUser);
				subject.setCategory(category);

				// 设置学段
				if (gradeId != null && Utlity.isNumeric(gradeId)) {
					Grade grade = this.getGradeService().getById(Integer.valueOf(gradeId));
					if (grade != null) {
						subject.setGrade(grade);
					}
				}

				// 设置status
				if (status == null) {
					subject.setStatus((short) Dictionary.SUBJECT_STATUS_NOMAL);
				} else {
					subject.setStatus(Short.valueOf(status));
				}

				this.getSubjectService().addSubject(subject);

				Map<String, Object> data = SerializeEntity.subject2Map(subject);
				result.init(SUCCESS_STATUS, null, data);
			}
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 编辑学科
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:08:49 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void Update() {

		// ***********************************
		//
		// 1.验证参数的合法性
		// 2.按名称判断名称是否存在
		//
		// ***********************************

		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String gradeId = request.getParameter("grade.id");
		String categoryId = request.getParameter("category.id");
		String status = request.getParameter("status");

		int subjectId = Integer.valueOf(id);
		Subject subject = this.getSubjectService().getSubjectById(subjectId);

		if (subject != null) {

			boolean flag = true;

			if (!subject.getName().equals(name)) {
				// 名称不相同
				Subject tmpSubject = this.getSubjectService().getSubjectByName(name);
				if (tmpSubject != null) {
					flag = false;
				}
			}

			if (!flag) {
				result.init(FAIL_STATUS, "已经存在 “" + name + "” 学科！", null);
			} else {

				Category category = this.getCategoryService().getCategoryById(Integer.valueOf(categoryId));
				if (category == null) {
					result.init(FAIL_STATUS, "分配的分类不存在！", null);
				} else {

					subject.setName(name);
					subject.setCategory(category);

					// 设置学段
					if (gradeId != null && Utlity.isNumeric(gradeId)) {
						Grade grade = this.getGradeService().getById(Integer.valueOf(gradeId));
						if (grade != null) {
							subject.setGrade(grade);
						}
					}

					// 设置status
					if (status != null) {
						subject.setStatus(Short.valueOf(status));
					}

					this.getSubjectService().updateSubject(subject);

					result.init(SUCCESS_STATUS, "修改成功", null);
				}
			}

		} else {

			result.init(FAIL_STATUS, "当前学科不存在！", null);

		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * load
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:29:41 <br/>
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
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		Subject subject = this.getSubjectService().getSubjectById(Integer.valueOf(id));
		if (subject != null) {

			if (!this.getSysUserService().isCanOpt(currentUser, subject)) {
				result.init(FAIL_STATUS, "无权查看学科：" + subject.getName(), null);
			} else {
				Map<String, Object> map = SerializeEntity.subject2Map(subject, split);
				result.init(SUCCESS_STATUS, null, map);
			}
		} else {
			result.init(FAIL_STATUS, "学科不存在!", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:29:47 <br/>
	 */

	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		// ***********************************
		//
		// 1.验证参数的合法性
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		String id = request.getParameter("id");

		ActionResult result = new ActionResult();

		// ==========删除，也就是修改状态=============
		int subjectId = Integer.valueOf(id);
		Subject subject = this.getSubjectService().getSubjectById(subjectId);

		if (subject != null) {

			subject.setStatus((short) Dictionary.SUBJECT_STATUS_CLOSED);
			this.getSubjectService().updateSubject(subject);

			result.init(SUCCESS_STATUS, "修改成功", null);
		} else {
			result.init(FAIL_STATUS, "当前学科不存在！", null);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 获取学科 搜索(后台管理接口)
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:10:43 <br/>
	 */
	@ActionParam(key = "name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "grade.name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "category.name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "pageunm", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void List() {

		// ***********************************
		//
		// 1.验证参数的合法性
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();
		SysUser currentUser = (SysUser) session.getAttribute("usersession");

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		String status = request.getParameter("status");
		String name = request.getParameter("name");
		String gradeId = request.getParameter("grade.id");
		int intGrade = this.getIntValue(gradeId, 0);
		Grade grade = this.getGradeService().getById(intGrade);
		String gradeName = request.getParameter("grade.name");
		String categoryId = request.getParameter("category.id");
		String categoryName = request.getParameter("category.name");
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		HashMap<String, Object> ht = new HashMap<>();
		ht.put("name", name);
		ht.put("status", status);
		// ht.put("gradeId", gradeId);
		if (grade != null) {
			String scode = grade.getScode().substring(0, 10);
			ht.put("gradescode", scode);
		}
		ht.put("gradeName", gradeName);
		ht.put("categoryId", categoryId);
		ht.put("categoryName", categoryName);
		ht.put("sorts", sorts);

		int records = this.getSubjectService().getSubjectCountByParams(ht);
		List<Subject> liS = this.getSubjectService().getSubjectByParams(ht, offset, pagesize);

		List<Map<String, Object>> liM = new ArrayList<>();
		for (Subject cate : liS) {
			if (this.getSysUserService().isCanOpt(currentUser, cate)) {
				liM.add(SerializeEntity.subject2Map(cate));
			}
		}

		result.init(SUCCESS_STATUS, null, liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 搜索接口
	 */
	@ActionParam(key = "name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "grade.name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "category.name", type = ValueType.STRING, nullable = true, emptyable = true)
	@ActionParam(key = "pageunm", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void Search() {

		// ***********************************
		//
		// 1.验证参数的合法性
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		String name = request.getParameter("name");
		String gradeId = request.getParameter("grade.id");
		int intGrade = this.getIntValue(gradeId, 0);
		Grade grade = this.getGradeService().getById(intGrade);
		String gradeName = request.getParameter("grade.name");
		String categoryId = request.getParameter("category.id");
		String categoryName = request.getParameter("category.name");
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		HashMap<String, Object> ht = new HashMap<>();
		ht.put("name", name);
		ht.put("status", Dictionary.SUBJECT_STATUS_NOMAL);
		// ht.put("gradeId", gradeId);
		if (grade != null) {
			String scode = grade.getScode().substring(0, 10);
			ht.put("gradescode", scode);
		}

		ht.put("gradeName", gradeName);
		ht.put("categoryId", categoryId);
		ht.put("categoryName", categoryName);
		ht.put("sorts", sorts);

		int records = this.getSubjectService().getSubjectCountByParams(ht);
		List<Subject> liS = this.getSubjectService().getSubjectByParams(ht, offset, pagesize);

		List<Map<String, Object>> liM = new ArrayList<>();
		for (Subject cate : liS) {
			liM.add(SerializeEntity.subject2Map(cate));
		}

		result.init(SUCCESS_STATUS, null, liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 搜索 学科 根据 知识分类分组
	 * 
	 * @author jiangfei
	 * @date: 2014年8月26日 下午5:26:24 <br/>
	 */
	@SuppressWarnings("unchecked")
	public void SearchAllSubject() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		List<Subject> liS = new ArrayList<Subject>();

		// ====================判断角色的学科权限=============================
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		if (currentUser.getRole().getId() == Dictionary.USER_ROLE_EDITOR && session.getAttribute("usersubject") != null) {

			List<Subject> tmpSubject = (List<Subject>) session.getAttribute("usersubject");
			liS.addAll(tmpSubject);

		} else {
			HashMap<String, Object> ht = new HashMap<>();
			ht.put("status", Dictionary.SUBJECT_STATUS_NOMAL);

			int records = this.getSubjectService().getSubjectCountByParams(ht);
			liS.addAll(this.getSubjectService().getSubjectByParams(ht, 0, records));
		}

		LinkedHashMap<Category, List<Subject>> mapSubject = new LinkedHashMap<Category, List<Subject>>();

		for (Subject subject : liS) {

			if (mapSubject.containsKey(subject.getCategory())) {

				LinkedList<Subject> lisubject = (LinkedList<Subject>) mapSubject.get(subject.getCategory());
				lisubject.add(subject);

			} else {

				LinkedList<Subject> lisubject = new LinkedList<Subject>();
				lisubject.add(subject);
				mapSubject.put(subject.getCategory(), lisubject);

			}

		}

		List<Map<String, Object>> liM = new ArrayList<>();
		Iterator<Entry<Category, List<Subject>>> iterator = mapSubject.entrySet().iterator();

		while (iterator.hasNext()) {

			Entry<Category, List<Subject>> entry = iterator.next();

			Map<String, Object> map = SerializeEntity.categroy2Map(entry.getKey(), split);
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();

			for (Subject sub : entry.getValue()) {
				listmap.add(SerializeEntity.subject2Map(sub, split));
			}
			map.put("data", listmap);

			liM.add(map);

		}

		result.init(SUCCESS_STATUS, null, liM);
		Utlity.ResponseWrite(result, dataType, response);

	}
}
