package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectCountdown;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ISubjectCountDownService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class SubjectCountDownAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6133434448684083467L;

	private ISubjectCountDownService subjectCountDownService;
	private ISubjectService subjectService;

	/**
	 * 添加考试倒计时
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "examtime", type = ValueType.STRING, emptyable = false, nullable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, emptyable = false, nullable = false)
	public void Add() {

		String exam_time = this.request.getParameter("examtime");
		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		short status = Short.valueOf(this.request.getParameter("status"));

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		// =======================================================
		// 1.添加 考试倒计时
		// 2.获取学科
		// =======================================================

		Subject subject = this.getSubjectService().getSubjectById(subjectId);
		if (subject == null) {

			result.init(ERROR_STATUS, "不存在学科", null);

		} else {

			SubjectCountdown subjectCountDown = new SubjectCountdown();
			subjectCountDown.setExamTime(Timestamp.valueOf(exam_time + " 00:00:00"));
			subjectCountDown.setSysUser(currentUser);
			subjectCountDown.setSubject(subject);
			subjectCountDown.setStatus(status);
			subjectCountDown.setCreateTime(new Timestamp(System.currentTimeMillis()));

			this.getSubjectCountDownService().addSubjectCountDown(subjectCountDown);

			Map<String, Object> map = SerializeEntity.subjectCountDown2Map(subjectCountDown);

			result.init(SUCCESS_STATUS, "添加成功", map);

		}

		Utlity.ResponseWrite(result, null, response);

	}

	/**
	 * 删除
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		int id = this.getIntValue(this.request.getParameter("id"));

		ActionResult result = new ActionResult();

		SubjectCountdown subjectCountdown = this.getSubjectCountDownService().getSubjectCountDownById(id);
		if (subjectCountdown != null) {

			this.getSubjectCountDownService().deleteSubjectCountDown(subjectCountdown);

			result.init(SUCCESS_STATUS, "停用成功", null);

		} else {
			result.init(ERROR_STATUS, "不存在当前考试时间", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}
	/**
	 * 修改 考试倒计时
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	@ActionParam(key = "examtime", type = ValueType.STRING, emptyable = false, nullable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, emptyable = false, nullable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, emptyable = false, nullable = false)
	public void Update() {

		String exam_time = this.request.getParameter("examtime");
		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		int id = this.getIntValue(this.request.getParameter("id"));
		short status = Short.valueOf(this.request.getParameter("status"));

		ActionResult result = new ActionResult();

		// =======================================================
		// 1.添加 考试倒计时
		// 2.获取学科
		// 3. 获取考试倒计时
		// =======================================================

		Subject subject = this.getSubjectService().getSubjectById(subjectId);
		SubjectCountdown subjectCountDown = this.getSubjectCountDownService().getSubjectCountDownById(id);

		if (subject == null) {
			result.init(ERROR_STATUS, "不存在学科", null);
		} else if (subjectCountDown == null) {
			result.init(ERROR_STATUS, "不存在考试倒计时", null);
		} else {

			subjectCountDown.setExamTime(Timestamp.valueOf(exam_time + " 00:00:00"));
			subjectCountDown.setSubject(subject);
			subjectCountDown.setStatus(status);

			this.getSubjectCountDownService().updateSubjectCountDown(subjectCountDown);

			Map<String, Object> map = SerializeEntity.subjectCountDown2Map(subjectCountDown);

			result.init(SUCCESS_STATUS, "修改成功", map);

		}

		Utlity.ResponseWrite(result, null, response);
	}
	
	/**
	 * 加载
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {
		ActionResult result = new ActionResult();
		Integer subjectCountID = Integer.valueOf(request.getParameter("id"));
		String split = this.request.getParameter("split");
		split = split == null ? "_" : split;

		SubjectCountdown subjectCountDown = this.getSubjectCountDownService().getSubjectCountDownById(subjectCountID);

		if (subjectCountDown != null) {
			
			Map<String, Object> data = SerializeEntity.subjectCountDown2Map(subjectCountDown, split);
			result.init(SUCCESS_STATUS, "加载考试倒计时成功！", data);
			
		} else {
			result.init(FAIL_STATUS, "无效的考试倒计时ID信息！", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 获取列表
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "examtime", type = ValueType.STRING)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void List() {

		ActionResult result = new ActionResult();
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("status", request.getParameter("status"));
		
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount  = this.getSubjectCountDownService().getSubjectCountdownCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);
		
		List<SubjectCountdown> listT = this.getSubjectCountDownService().getSubjectCountdowns(searchMap, offset, pagesize);
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (listT != null && listT.size() > 0){
			for (SubjectCountdown subjectCountdown : listT){
				Map<String, Object> data = SerializeEntity.subjectCountDown2Map(subjectCountdown);
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

	public ISubjectCountDownService getSubjectCountDownService() {
		return subjectCountDownService;
	}

	public void setSubjectCountDownService(ISubjectCountDownService subjectCountDownService) {
		this.subjectCountDownService = subjectCountDownService;
	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

}
