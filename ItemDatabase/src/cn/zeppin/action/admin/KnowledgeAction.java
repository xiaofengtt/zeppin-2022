/**
 * Project Name:ItemDatabase File Name:GradeAction.java Package
 * Name:cn.zeppin.action.admin Copyright (c) 2014, Zeppin All Rights Reserved.
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

import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * This class is used for 知识点操作
 * 
 * @author Clark
 * @version 1.0, 2014年7月21日 下午7:51:58
 */
public class KnowledgeAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7223737324274167354L;
	private IKnowledgeService knowledgeService;
	private IGradeService gradeService;
	private ISubjectService subjectService;
	private ISysUserService sysUserService;

	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * 添加知识点信息
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午6:32:36 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	// @Authority()
	public void Add() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		String name = request.getParameter("name");
		Integer gradeID = Integer.valueOf(request.getParameter("grade.id"));
		Integer subjectID = Integer.valueOf(request.getParameter("subject.id"));
		Grade grade = this.getGradeService().getById(gradeID);
		Subject subject = this.getSubjectService().getSubjectById(subjectID);
		Integer pid = this.getIntValue(request.getParameter("knowledge.id"), null);
		if (grade == null) {
			result.init(FAIL_STATUS, "无此学段！", null);
		} else if (subject == null) {
			result.init(FAIL_STATUS, "无此学科！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, grade)) {
			result.init(FAIL_STATUS, "无权添加学段：" + grade.getName() + "(id:" + grade.getId() + ")下的知识点！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, subject)) {
			result.init(FAIL_STATUS, "无权添加学科：" + subject.getName() + "(id:" + subject.getId() + ")下的知识点！", null);
		} else {
			Knowledge knowledge = new Knowledge();
			knowledge.setName(name);
			knowledge.setGrade(grade);
			knowledge.setSubject(subject);
			knowledge.setStatus(Dictionary.KNOWLEDGE_STATUS_NOMAL);
			knowledge.setSysUser(currentUser);
			knowledge.setCreatetime(new Timestamp((new Date()).getTime()));
			Knowledge parent = null;
			if (pid != null) {
				parent = this.getKnowledgeService().getById(pid);
			}
			knowledge.setKnowledge(parent);
			knowledge.setLevel((parent == null) ? (short) 1 : (short) (parent.getLevel() + 1));
			knowledge = this.getKnowledgeService().addKnowledge(knowledge);
			Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
			result.init(SUCCESS_STATUS, "添加知识点" + name + "成功！", data);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改知识点信息
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午3:50:12 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void Update() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		Integer knowledgeID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		Integer gradeID = Integer.valueOf(request.getParameter("grade.id"));
		Integer subjectID = Integer.valueOf(request.getParameter("subject.id"));
		Grade grade = this.getGradeService().getById(gradeID);

		Subject subject = this.getSubjectService().getSubjectById(subjectID);
		// Integer pid = this.getIntValue(request.getParameter("knowledge.id"),
		// null);

		if (grade == null) {
			result.init(FAIL_STATUS, "无此学段！", null);
		} else if (subject == null) {
			result.init(FAIL_STATUS, "无此学科！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, grade)) {
			result.init(FAIL_STATUS, "无权添加学段：" + grade.getName() + "(id:" + grade.getId() + ")下的知识点！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, subject)) {
			result.init(FAIL_STATUS, "无权添加学科：" + subject.getName() + "(id:" + subject.getId() + ")下的知识点！", null);
		} else {
			Knowledge knowledge = this.getKnowledgeService().getById(knowledgeID);
			if (knowledge != null) {
				knowledge.setName(name);
				knowledge.setGrade(grade);
				knowledge.setSubject(subject);
				if (status != null) {
					Short shStatus = Short.valueOf(status);
					knowledge.setStatus(shStatus);
				}
				knowledge = this.getKnowledgeService().updateKnowledge(knowledge);
				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
				result.init(SUCCESS_STATUS, "编辑知识点" + name + "成功！", data);
			} else {
				result.init(FAIL_STATUS, "无此知识点！", null);
			}
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除知识点信息
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午3:50:29 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult result = new ActionResult();
		Integer knowledgeID = Integer.valueOf(request.getParameter("id"));
		Knowledge knowledge = this.getKnowledgeService().getById(knowledgeID);
		if (knowledge != null) {
			knowledge = this.getKnowledgeService().deleteKnowledge(knowledge);
			Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
			result.init(SUCCESS_STATUS, "删除知识点" + knowledge.getName() + "成功！", data);
		} else {
			result.init(FAIL_STATUS, "无此知识点！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 后台管理知识点列表
	 * 
	 * @author Clark
	 * @date: 2014年7月24日 下午12:39:21 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {
		ActionResult result = new ActionResult();
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("level", request.getParameter("level"));
		searchMap.put("knowledge.id", request.getParameter("knowledge.id"));
		searchMap.put("grade.id", request.getParameter("grade.id"));
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge knowledge : knowledgeList) {
				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);

				Map<String, Object> hasChildCount = new HashMap<String, Object>();
				hasChildCount.put("knowledge.id", knowledge.getId());
				boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
				data.put("haschild", hasChild);

				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, null, dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 查询知识点信息(这个接口只提供状态为正常的知识点数据)
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午3:50:43 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void Search() {
		ActionResult result = new ActionResult();

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("level", request.getParameter("level"));
		searchMap.put("knowledge.id", request.getParameter("knowledge.id"));
		searchMap.put("grade.id", request.getParameter("grade.id"));
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));

		// 对外接口不需要判断用户认证，不返回逻辑删除（停用）的信息
		searchMap.put("status", Dictionary.KNOWLEDGE_STATUS_NOMAL);

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge knowledge : knowledgeList) {
				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
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
	 * 知识点导航
	 * 
	 * @author Administrator
	 * @date: 2014年8月27日 下午12:20:16 <br/>
	 */
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	public void LoadKnowledgeNav() {

		String id = request.getParameter("knowledge.id");
		int i_id = this.getIntValue(id, 0);
		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		Knowledge grade = this.getKnowledgeService().getById(i_id);
		if (grade == null) {
			result.init(FAIL_STATUS, null, null);
		} else {
			LinkedList<Knowledge> lnklist = new LinkedList<Knowledge>();
			lnklist.add(grade);

			while (grade.getKnowledge() != null) {
				grade = grade.getKnowledge();
				lnklist.add(grade);
			}
			List<Map<String, Object>> liM = new ArrayList<>();
			int i = lnklist.size() - 1;
			for (; i >= 0; i--) {
				Knowledge cate = lnklist.get(i);
				Map<String, Object> data = SerializeEntity.knowledge2Map(cate);
				liM.add(data);
			}
			result.init(SUCCESS_STATUS, null, liM);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 查询出所有知识点
	 * 
	 * @author Administrator
	 * @date: 2014年9月4日 下午2:22:05 <br/>
	 */
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public void SearchAllKnowledge() {

		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("grade.id", request.getParameter("grade.id"));
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("level", 1);

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", 0, recordCount);
		List<Map<String, Object>> dataList = new ArrayList<>();

		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge knowledge : knowledgeList) {

				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge, split);

				Map<String, Object> hasChildCount = new HashMap<String, Object>();
				hasChildCount.put("knowledge.id", knowledge.getId());
				boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
				data.put("haschild", hasChild);
				if (hasChild) {
					LoadNextKnowledge(knowledge, data, split);
				}
				dataList.add(data);

			}
		}

		result.init(SUCCESS_STATUS, "数据获成功", dataList);
		Utlity.ResponseWrite(result, dataType, response);

	}

	public void LoadNextKnowledge(Knowledge knowledge, Map<String, Object> map, String split) {

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("knowledge.id", knowledge.getId());

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", 0, recordCount);
		List<Map<String, Object>> dataList = new ArrayList<>();

		for (Knowledge knowle : knowledgeList) {

			Map<String, Object> data = SerializeEntity.knowledge2Map(knowle, split);

			Map<String, Object> hasChildCount = new HashMap<String, Object>();
			hasChildCount.put("knowledge.id", knowle.getId());
			boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
			data.put("haschild", hasChild);

			if (hasChild) {
				LoadNextKnowledge(knowle, data, split);
			}
			dataList.add(data);

		}
		map.put("data", dataList);

	}

}
