/**
 * This class is used for 监考教师信息操作
 * 
 */
package cn.zeppin.action.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.ExamRoom;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IEthnicService;
import cn.zeppin.service.api.IExamTeacherRoomService;
import cn.zeppin.service.api.IInvigilationTeacherService;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.IDCardUtil;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;
import cn.zeppin.utility.wx.CommonUtil;
import cn.zeppin.utility.wx.ConfigUtil;
import cn.zeppin.utility.wx.MessageUtil;

/**
 * ClassName: InvigilationTeacherAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class InvigilationTeacherAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 369393031897616109L;

	private IInvigilationTeacherService invigilationTeacherService;

	private IExamTeacherRoomService examTeacherRoomService;

	private IEthnicService ethnicService;

	private IResourceService resourceService;

	private ISysUserService sysUserService;

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public IInvigilationTeacherService getInvigilationTeacherService() {
		return invigilationTeacherService;
	}

	public void setInvigilationTeacherService(IInvigilationTeacherService invigilationTeacherService) {
		this.invigilationTeacherService = invigilationTeacherService;
	}

	public IEthnicService getEthnicService() {
		return ethnicService;
	}

	public void setEthnicService(IEthnicService ethnicService) {
		this.ethnicService = ethnicService;
	}

	public IExamTeacherRoomService getExamTeacherRoomService() {
		return examTeacherRoomService;
	}

	public void setExamTeacherRoomService(IExamTeacherRoomService examTeacherRoomService) {
		this.examTeacherRoomService = examTeacherRoomService;
	}

	/**
	 * 添加
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "idcard", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "mobile", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "sex", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "ethnic", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "photo", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "major", type = ValueType.STRING)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "organization", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "inschooltime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "isChiefExaminer", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "isMixedExaminer", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "specialty", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "intgral", type = ValueType.NUMBER, nullable = false, emptyable = false) // 是否需要去掉
	@ActionParam(key = "invigilateCampus", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "invigilateType", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "jobDuty", type = ValueType.STRING)
	@ActionParam(key = "studyMajor", type = ValueType.STRING)
	@ActionParam(key = "studyGrade", type = ValueType.STRING)
	@ActionParam(key = "remark", type = ValueType.STRING)
	@ActionParam(key = "bankCard", type = ValueType.STRING)
	@ActionParam(key = "sid", type = ValueType.STRING)
	public void Add() {

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult actionResult = new ActionResult();
		// 接受页面参数
		// 必填
		String name = request.getParameter("name");
		// String pinyin;
		String idcard = request.getParameter("idcard");
		String mobile = request.getParameter("mobile");
		Short sex = Short.parseShort(request.getParameter("sex"));
		Short ethnic = Short.parseShort(request.getParameter("ethnic"));
		Integer photo = this.getIntValue(request.getParameter("photo"));
		String major = request.getParameter("major");
		Short type = Short.parseShort(request.getParameter("type"));
		String organization = request.getParameter("organization");
		String inschooltime = request.getParameter("inschooltime");
		Short isChiefExaminer = Short.parseShort(request.getParameter("isChiefExaminer"));
		Short isMixedExaminer = Short.parseShort(request.getParameter("isMixedExaminer"));
		Integer intgral = this.getIntValue(request.getParameter("intgral"));
		String bankCard = request.getParameter("bankCard");
		String sid = request.getParameter("sid");

		// String reason;
		// Short checkStatus;
		// Timestamp checktime;
		// Integer checker;
		// String checkReason;
		// 选填 非空判断
		String specialty = request.getParameter("specialty") == null ? "" : request.getParameter("specialty");
		String invigilateCampus = request.getParameter("invigilateCampus") == null ? ""
				: request.getParameter("invigilateCampus");
		String invigilateType = request.getParameter("invigilateType");
		String jobDuty = request.getParameter("jobDuty") == null ? "" : request.getParameter("jobDuty");
		String studyMajor = request.getParameter("studyMajor") == null ? "" : request.getParameter("studyMajor");
		String studyGrade = request.getParameter("studyGrade") == null ? "" : request.getParameter("studyGrade");
		String remark = request.getParameter("remark") == null ? "" : request.getParameter("remark");

		if (idcard != null) {
			if (IDCardUtil.IDCardValidate(idcard).equals("")) {
				Map<String, Object> searchCard = new HashMap<String, Object>();
				searchCard.put("idcard", idcard);
				List<InvigilationTeacher> list = this.invigilationTeacherService.searchInvigilationTeacher(searchCard,
						null, -1, -1);
				if (list != null && list.size() > 0) {
					actionResult.init(FAIL_STATUS, "该身份证号已被注册", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}

			} else {
				actionResult.init(FAIL_STATUS, "请正确填写身份证号", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
		} else {
			actionResult.init(FAIL_STATUS, "必须填写身份证号", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (!Utlity.isMobileNO(mobile)) {
			actionResult.init(FAIL_STATUS, "请输入正确的手机号码", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("mobile", mobile);
		Integer count = this.invigilationTeacherService.searchInvigilationTeacherCount(searchParams);
		if (count > 0) {
			actionResult.init(FAIL_STATUS, "该手机号已注册", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		// 创建考试信息实例存储数据
		InvigilationTeacher teacher = new InvigilationTeacher();
		teacher.setName(name);
		teacher.setIdcard(idcard);
		teacher.setPinyin(pinyingUtil.getFirstSpell(name));
		teacher.setMobile(mobile);
		teacher.setSex(sex);
		Ethnic eth = this.ethnicService.getById(ethnic);
		teacher.setEthnic(eth);
		Resource pho = this.resourceService.getById(photo);
		teacher.setPhoto(pho);
		teacher.setMajor(major);
		teacher.setType(type);
		teacher.setOrganization(organization);
		// teacher.setInschooltime(Timestamp.valueOf(inschooltime));
		teacher.setInschooltime(inschooltime);
		teacher.setIsChiefExaminer(isChiefExaminer);
		teacher.setIsMixedExaminer(isMixedExaminer);
		teacher.setIntgral(intgral);

		teacher.setSpecialty(specialty);
		teacher.setInvigilateCampus(invigilateCampus);
		teacher.setInvigilateType(invigilateType);
		teacher.setInvigilateCount(0);
		teacher.setJobDuty(jobDuty);
		teacher.setStudyGrade(studyGrade);
		teacher.setStudyMajor(studyMajor);
		teacher.setRemark(remark);
		teacher.setBankCard(bankCard);
		teacher.setSid(sid);
		teacher.setCheckStatus((short) 2);// 管理员添加完直接通过
		teacher.setChecker(currentUser.getId());
		teacher.setChecktime(new Timestamp(System.currentTimeMillis()));

		teacher.setStatus((short) 1);// 初始化状态为 未发布
		teacher.setCreatetime(new Timestamp(System.currentTimeMillis()));
		teacher.setCreator(currentUser.getId());
		teacher.setInvigilateCount(0);
		teacher.setDisableType((short) 1);
		teacher.setIsDisable((short) 0);
		teacher.setIdCardPhoto(pho);
		// teacher.setPassword(bankCard.substring(bankCard.length()-6));//默认密码身份证后6位

		try {
			this.invigilationTeacherService.add(teacher);
			actionResult.init(SUCCESS, "添加成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "添加失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 修改
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "idcard", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "mobile", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "sex", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "ethnic", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "photo", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "major", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "organization", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "inschooltime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "isChiefExaminer", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "isMixedExaminer", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "specialty", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "intgral", type = ValueType.NUMBER, nullable = false, emptyable = false) // 是否需要去掉
	@ActionParam(key = "invigilateCampus", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "invigilateType", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "jobDuty", type = ValueType.STRING)
	@ActionParam(key = "studyMajor", type = ValueType.STRING)
	@ActionParam(key = "studyGrade", type = ValueType.STRING)
	@ActionParam(key = "remark", type = ValueType.STRING)
	@ActionParam(key = "bankCard", type = ValueType.STRING)
	@ActionParam(key = "sid", type = ValueType.STRING)
	public void Update() {

		ActionResult actionResult = new ActionResult();
		// check paras
		// 接受页面参数
		Integer id = this.getIntValue(request.getParameter("id"));
		String name = request.getParameter("name");
		// String pinyin;
		String idcard = request.getParameter("idcard");
		String mobile = request.getParameter("mobile");
		Short sex = Short.parseShort(request.getParameter("sex"));
		Short ethnic = Short.parseShort(request.getParameter("ethnic"));
		Integer photo = this.getIntValue(request.getParameter("photo"));
		String major = request.getParameter("major");
		Short type = Short.parseShort(request.getParameter("type"));
		String organization = request.getParameter("organization");
		String inschooltime = request.getParameter("inschooltime");
		Short isChiefExaminer = Short.parseShort(request.getParameter("isChiefExaminer"));
		Short isMixedExaminer = Short.parseShort(request.getParameter("isMixedExaminer"));
		Integer intgral = this.getIntValue(request.getParameter("intgral"));
		String bankCard = request.getParameter("bankCard");
		String sid = request.getParameter("sid");

		// String reason;
		// Short checkStatus;
		// Timestamp checktime;
		// Integer checker;
		// String checkReason;
		// 选填 非空判断
		String specialty = request.getParameter("specialty") == null ? "" : request.getParameter("specialty");
		String invigilateCampus = request.getParameter("invigilateCampus") == null ? ""
				: request.getParameter("invigilateCampus");
		String invigilateType = request.getParameter("invigilateType");
		String jobDuty = request.getParameter("jobDuty") == null ? "" : request.getParameter("jobDuty");
		String studyMajor = request.getParameter("studyMajor") == null ? "" : request.getParameter("studyMajor");
		String studyGrade = request.getParameter("studyGrade") == null ? "" : request.getParameter("studyGrade");
		String remark = request.getParameter("remark") == null ? "" : request.getParameter("remark");

		InvigilationTeacher teacher = invigilationTeacherService.getById(id);

		if (idcard != null) {
			if (IDCardUtil.IDCardValidate(idcard).equals("")) {
				Map<String, Object> searchCard = new HashMap<String, Object>();
				searchCard.put("idcard", idcard);
				List<InvigilationTeacher> list = this.invigilationTeacherService.searchInvigilationTeacher(searchCard,
						null, -1, -1);
				if (list != null && list.size() > 0 && !(idcard).equals(teacher.getIdcard())) {
					actionResult.init(FAIL_STATUS, "该身份证号已被注册", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}

			} else {
				actionResult.init(FAIL_STATUS, "请正确填写身份证号", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
		} else {
			actionResult.init(FAIL_STATUS, "必须填写身份证号", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (!Utlity.isMobileNO(mobile)) {
			actionResult.init(FAIL_STATUS, "请输入正确的手机号码", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("mobile", mobile);
		Integer count = this.invigilationTeacherService.searchInvigilationTeacherCount(searchParams);
		if (count > 0 && !(mobile).equals(teacher.getMobile())) {
			actionResult.init(FAIL_STATUS, "手机号码已被注册", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (!Utlity.isNumeric(bankCard)) {
			actionResult.init(FAIL_STATUS, "请输入正确的交通银行卡号", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		try {
			teacher = this.invigilationTeacherService.getById(id);
			if (teacher != null) {
				teacher.setName(name);
				teacher.setIdcard(idcard);
				teacher.setPinyin(pinyingUtil.getFirstSpell(name));
				teacher.setMobile(mobile);
				teacher.setSex(sex);
				Ethnic eth = this.ethnicService.getById(ethnic);
				teacher.setEthnic(eth);
				Resource pho = this.resourceService.getById(photo);
				teacher.setPhoto(pho);
				teacher.setMajor(major);
				teacher.setType(type);
				teacher.setOrganization(organization);
				// teacher.setInschooltime(Timestamp.valueOf(inschooltime + "
				// 00:00:00"));
				teacher.setInschooltime(inschooltime);
				teacher.setIsChiefExaminer(isChiefExaminer);
				teacher.setIsMixedExaminer(isMixedExaminer);
				teacher.setIntgral(intgral);

				teacher.setSpecialty(specialty);
				teacher.setInvigilateCampus(invigilateCampus);
				teacher.setInvigilateType(invigilateType);
				teacher.setJobDuty(jobDuty);
				teacher.setStudyGrade(studyGrade);
				teacher.setStudyMajor(studyMajor);
				teacher.setRemark(remark);
				teacher.setBankCard(bankCard);
				teacher.setSid(sid);
			} else {
				actionResult.init(FAIL_STATUS, "教师信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			this.invigilationTeacherService.update(teacher);
			actionResult.init(SUCCESS, "教师信息更新成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "教师信息更新异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 变更状态
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "reason", type = ValueType.STRING)
	public void Change() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		int status = getIntValue(request.getParameter("status"));
		String reason = request.getParameter("reason") == null ? "" : request.getParameter("reason");
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		try {
			InvigilationTeacher invigilationTeacher = invigilationTeacherService.getById(id);
			if (invigilationTeacher != null) {
				invigilationTeacher.setCheckStatus((short) status);
				invigilationTeacher.setChecker(currentUser.getId());
				invigilationTeacher.setCheckReason(reason);
				invigilationTeacher.setChecktime(new Timestamp(System.currentTimeMillis()));
				this.invigilationTeacherService.update(invigilationTeacher);
				// 0未通过 2已通过
				if (status == 2 && invigilationTeacher.getOpenID() != null) {
					Map<String, String> templateParams = new HashMap<>();
					templateParams.put("first", "收到审核通知");
					templateParams.put("keyword1", Utlity.timeSpanToString5(invigilationTeacher.getChecktime()));
					templateParams.put("keyword2", "您的注册信息已经审核通过!");
					templateParams.put("keyword3", currentUser.getName());
					templateParams.put("remark", "请查收");
					templateParams.put("url", "");
					templateParams.put("touser", invigilationTeacher.getOpenID());
					ConfigUtil.sendTemplate(CommonUtil.getAccessToken().getAccessToken(),
							MessageUtil.teacherCheckTemplate(templateParams));
				} else if (status == 0) {
					Map<String, String> templateParams = new HashMap<>();
					templateParams.put("first", "收到审核通知");
					templateParams.put("keyword1", Utlity.timeSpanToString5(invigilationTeacher.getChecktime()));
					templateParams.put("keyword2", "您的注册信息未通过审核，原因：" + reason);
					templateParams.put("keyword3", currentUser.getName());
					templateParams.put("remark", "请修改资料重新提交");
					templateParams.put("url", "");
					templateParams.put("touser", invigilationTeacher.getOpenID());
					ConfigUtil.sendTemplate(CommonUtil.getAccessToken().getAccessToken(),
							MessageUtil.teacherCheckTemplate(templateParams));
				}
				actionResult.init(SUCCESS, "操作成功", null);
			} else {
				actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "操作失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 停用
	 */
	// @AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	// @ActionParam(key = "id", type = ValueType.STRING, nullable = false,
	// emptyable = false)
	// @ActionParam(key = "reason", type = ValueType.STRING, nullable = false,
	// emptyable = false)
	// public void Disable() {
	// // TODO Auto-generated method stub
	// ActionResult actionResult = new ActionResult();
	// String reason = request.getParameter("reason") == null ? "" :
	// request.getParameter("reason");
	// String id = request.getParameter("id");
	// String ids[] = id.split(",");
	// try {
	// for (int i = 0; i < ids.length; i++) {
	// InvigilationTeacher InvigilationTeacher =
	// invigilationTeacherService.getById(Integer.parseInt(ids[i]));
	// if (InvigilationTeacher != null) {
	// InvigilationTeacher.setStatus((short) 0);
	// InvigilationTeacher.setReason(reason);
	// this.invigilationTeacherService.update(InvigilationTeacher);
	// }
	// }
	// } catch (Exception e) {
	// actionResult.init(FAIL_STATUS, "停用操作失败", null);
	// }
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// }

	/**
	 * 批量删除（物理删除 同时会把老师的监考记录一并删除）
	 */
	@ActionParam(key = "id", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult actionResult = new ActionResult();
		int id = getIntValue(request.getParameter("id"));
		try {
			InvigilationTeacher teacher = invigilationTeacherService.getById(id);
			this.invigilationTeacherService.deleteById(teacher);
			actionResult.init(SUCCESS_STATUS, "删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "删除失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "exam", type = ValueType.STRING)
	public void Resume() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		Integer id = getIntValue(request.getParameter("id"));
		String exam = request.getParameter("exam");

		try {
			// InvigilationTeacher InvigilationTeacher =
			// invigilationTeacherService.getById(id);
			// if (InvigilationTeacher != null) {
			// InvigilationTeacher.setStatus((short) 1);
			// this.invigilationTeacherService.update(InvigilationTeacher);
			// if (!Utlity.checkStringNull(exam)) {
			this.invigilationTeacherService.updateResume(id, exam);
			// }
			actionResult.init(SUCCESS, "启用成功", null);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "启用失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 根绝ID获取考试信息
	 */
	// @SuppressWarnings("rawtypes")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Get() {

		int id = -1;
		ActionResult actionResult = new ActionResult();
		id = Integer.parseInt(request.getParameter("id"));
		InvigilationTeacher InvigilationTeacher = invigilationTeacherService.getById(id);
		if (InvigilationTeacher != null) {

			Map<String, Object> data = null;
			try {
				data = SerializeEntity.InvigilationTeacher2Map(InvigilationTeacher);
				String[] arry = {};
				// 监考校区、考试类型格式化成数组
				if (data.get("invigilateCampus") != null && data.get("invigilateCampus").toString().length() > 0) {
					String invigilateCampus = data.get("invigilateCampus").toString();
					String[] invigilateCampusArry = invigilateCampus.split(",");
					data.put("invigilateCampusArry", invigilateCampusArry);
				} else {
					data.put("invigilateCampusArry", arry);
				}
				if (data.get("invigilateType") != null && data.get("invigilateType").toString().length() > 0) {
					String invigilateType = data.get("invigilateType").toString();
					String[] invigilateTypeArry = invigilateType.split(",");
					data.put("invigilateTypeArry", invigilateTypeArry);
				} else {
					data.put("invigilateTypeArry", arry);
				}
				// // 查询历史监考记录
				// Map<String, Object> searchMap = new HashMap<String,
				// Object>();
				// searchMap.put("teacher", id);
				// // searchMap.put("isConfirm", 1);
				// List listHistory =
				// this.examTeacherRoomService.searchExamTeacherRoom(searchMap,
				// null, -1, -1);
				// List<Map<String, Object>> listMap = new ArrayList<Map<String,
				// Object>>();
				// if (listHistory != null && !listHistory.isEmpty()) {
				//
				// for (int i = 0; i < listHistory.size(); i++) {
				// Map<String, Object> map = new HashMap<String, Object>();
				// Object o = listHistory.get(i);
				// Object[] obj = (Object[]) o;
				// ExamTeacherRoom exam = (ExamTeacherRoom) obj[0];
				// String examName = exam.getExam().getName();
				// ExamRoom room = exam.getRoom();
				// String roomIndex = "";
				// String roomAdd = "";
				// if (room != null) {
				// roomIndex = room.getRoomIndex();
				// roomAdd = room.getRoomAddress();
				// }
				// String time = Utlity.timeSpanToString2(exam.getCreatetime());
				// Integer score = exam.getCredit();
				// String remark = exam.getRemark();
				// String reason = exam.getReason();
				// map.put("index", i);
				// map.put("examName", examName);
				// map.put("roomIndex", roomIndex);
				// map.put("roomAdd", roomAdd);
				// map.put("applytime", time);
				// map.put("score", score);
				// map.put("remark", remark);
				// map.put("reason", reason);
				// listMap.add(map);
				// }
				// }
				// data.put("history", listMap);
			} catch (Exception e) {
				e.printStackTrace();
				actionResult.init(FAIL_STATUS, "信息异常", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			actionResult.init(SUCCESS_STATUS, "获取资源成功", data);

		} else {
			actionResult.init(FAIL_STATUS, "资源不存在", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@SuppressWarnings("rawtypes")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	public void HistoryList() {
		int id = -1;
		ActionResult actionResult = new ActionResult();
		id = Integer.parseInt(request.getParameter("id"));
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		InvigilationTeacher InvigilationTeacher = invigilationTeacherService.getById(id);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		int recordCount = 0;
		int pageCount = 0;
		if (InvigilationTeacher != null) {
			try {
				// 查询历史监考记录
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("teacher", id);
				searchMap.put("statusNormal", 1);
				searchMap.put("distribute", "1");
				List listHistory = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, null, offset, pagesize);
				if (listHistory != null && !listHistory.isEmpty()) {
					recordCount = listHistory.size();
					pageCount = (int) Math.ceil((double) recordCount / pagesize);
					for (int i = 0; i < listHistory.size(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						Object o = listHistory.get(i);
						Object[] obj = (Object[]) o;
						ExamTeacherRoom exam = (ExamTeacherRoom) obj[0];
						String examName = exam.getExam().getName();
						ExamRoom room = exam.getRoom();
						String roomIndex = "";
						String roomAdd = "";
						if (room != null) {
							roomIndex = room.getRoomIndex();
							roomAdd = room.getRoomAddress();
						}
						String time = Utlity.timeSpanToString2(exam.getCreatetime());
						String remark = exam.getRemark();
						String reason = exam.getReason();
						map.put("index", i);
						map.put("examName", examName);
						map.put("roomIndex", roomIndex);
						map.put("roomAdd", roomAdd);
						map.put("applytime", time);
						map.put("score", exam.getCredit() == null ? "无" : exam.getCredit());
						map.put("remark", remark);
						map.put("reason", reason);
						listMap.add(map);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				actionResult.init(FAIL_STATUS, "信息异常", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			actionResult.init(SUCCESS_STATUS, "获取资源成功", listMap);
		} else {
			actionResult.init(FAIL_STATUS, "资源不存在", null);
		}
		actionResult.setPageCount(pageCount);
		actionResult.setPageNum(pagenum);
		actionResult.setPageSize(pagesize);
		actionResult.setTotalCount(recordCount);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	// /**
	// * 获取当前时间内考试信息
	// */
	// @AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	// public void GetCurrent() {
	//
	// ActionResult actionResult = new ActionResult();
	//
	// Map<String, Object> searchMap = new HashMap<String, Object>();
	// searchMap.put("currenttime", Utlity.timeSpanToString(new
	// Timestamp(System.currentTimeMillis())));
	//
	// try {
	//
	// List<InvigilationTeacher> list =
	// this.invigilationTeacherService.searchInvigilationTeacher(searchMap,
	// null, -1, -1);
	// if(list != null && list.size() > 0){
	// InvigilationTeacher information = list.get(0);
	//
	// Map<String, Object> data = null;
	// try {
	// data = SerializeEntity.InvigilationTeacher2Map(information);
	// } catch (Exception e) {
	// e.printStackTrace();
	// actionResult.init(FAIL_STATUS, "信息转换异常", null);
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// return;
	// }
	//
	// actionResult.init(SUCCESS_STATUS, "获取资源成功", data);
	// }else{
	// actionResult.init(EMPTY_STATUS, "暂无正在进行的考试", null);
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// actionResult.init(FAIL_STATUS, "信息获取异常", null);
	// }
	//
	// Utlity.ResponseWrite(actionResult, dataType, response);
	// }

	/**
	 * 获取教师信息列表
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.STRING)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "checkStatus", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.NUMBER)
	@ActionParam(key = "isChief", type = ValueType.NUMBER)
	@ActionParam(key = "isMixed", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@SuppressWarnings("rawtypes")
	public void List() {

		ActionResult actionResult = new ActionResult();

		Integer exam = this.getIntValue(request.getParameter("exam") == null ? "0":request.getParameter("exam"));

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		// String sorts = this.getStrValue(request.getParameter("sorts"),
		// "").replaceAll("-", " ");
		String sorts = this.getStrValue(request.getParameter("sorts"), "");

		String name = request.getParameter("name") == null ? "" : request.getParameter("name");
		Integer checkStatus = getIntValue(request.getParameter("checkStatus"));// 审核状态
		Integer status = getIntValue(request.getParameter("status"));// 状态
		Integer type = getIntValue(request.getParameter("type"));// 身份
		Integer isChief = getIntValue(request.getParameter("isChief"));// 主考
		Integer isMixed = getIntValue(request.getParameter("isMixed"));// 副考

		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("teacherinfo", name);
		if (checkStatus > -1) {
			searchMap.put("checkStatus", checkStatus);
		}
		if (status > -1) {
			searchMap.put("status", status);
		}
		if (type > -1) {
			searchMap.put("type", type);
		}
		if (isChief > -1) {
			searchMap.put("isChiefExaminer", isChief);
		}
		if (isMixed > -1) {
			searchMap.put("isMixedExaminer", isMixed);
		}

		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}
		try {
			int recordCount = this.invigilationTeacherService.searchInvigilationTeacherCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			List<InvigilationTeacher> list = this.invigilationTeacherService.searchInvigilationTeacher(searchMap,
					sortParams, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (InvigilationTeacher information : list) {
					Map<String, Object> data = null;
					try {
						data = SerializeEntity.InvigilationTeacher2Map(information);
						if (information.getChecker() != null && information.getChecker() > 0) {
							SysUser currentUser = this.sysUserService.getSysUserById(information.getChecker());
							if (currentUser != null) {
								data.put("checkerName", currentUser.getName());
							} else {
								data.put("checkerName", "无");
							}
						} else {
							data.put("checkerName", "无");
						}
						if (exam > -1) {
							data.put("isApply", 0);// 可申请监考
							// if (information.getStatus() == 0) {// 教师停用不可申请监考
							// data.put("isApply", 1);
							// } else {
							Map<String, Object> searchMap2 = new HashMap<String, Object>();
							searchMap2.put("teacher", information.getId());
							searchMap2.put("exam", exam);
							List etrList = examTeacherRoomService.searchExamTeacherRoom(searchMap2, null, -1, -1);
							if (etrList != null && etrList.size() > 0) {
								Object o = etrList.get(0);
								Object[] obj = (Object[]) o;
								ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
								if (etr.getStatus() != 0) {// 禁用的和分配的都不可以再申请监考
									data.put("isApply", 1);// 不可申请监考 &&
															// etr.getRoom() !=
															// null
								}

								data.put("rid", etr.getId());
								// }
							}
						}
						dataList.add(data);
					} catch (Exception e) {
						e.printStackTrace();
						actionResult.init(FAIL_STATUS, "信息转换异常", null);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
				}

			}
			actionResult.init(SUCCESS_STATUS, "搜索完成！", dataList);
			actionResult.setPageCount(pageCount);
			actionResult.setPageNum(pagenum);
			actionResult.setPageSize(pagesize);
			actionResult.setTotalCount(recordCount);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息获取异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取民族列表
	 */
	public void EthnicList() {
		ActionResult actionResult = new ActionResult();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			List<Ethnic> list = ethnicService.getEthnicByWight();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> data = new HashMap<>();
				data.put("id", list.get(i).getId());
				data.put("name", list.get(i).getName());
				dataList.add(data);
			}
			actionResult.init(SUCCESS_STATUS, "获取成功！", dataList);
		} catch (Exception e) {
			actionResult.init(FAIL_STATUS, "获取异常", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "checkStatus", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.NUMBER)
	@ActionParam(key = "isChief", type = ValueType.NUMBER)
	@ActionParam(key = "isMixed", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void DownloadList() throws IOException {
		response.reset();
		ActionResult actionResult = new ActionResult();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("教师");
		HSSFRow row = sheet.createRow(0);
		Cell cell;
		// title
		String title[] = { "姓名", "性别", "身份证号", "手机号", "民族", "所学专业", "身份类型", "职务", "所在学院", "入校时间", "主考经验", "混考经验", "特长",
				"所在年级", "交通银行卡号", "学工号", "监考校区", "监考类型", "申请时间", "批准人", "状态", "审核状态" };
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}

		String sorts = this.getStrValue(request.getParameter("sorts"), "");

		String name = request.getParameter("name") == null ? "" : request.getParameter("name");
		Integer checkStatus = getIntValue(request.getParameter("checkStatus"));// 审核状态
		Integer status = getIntValue(request.getParameter("status"));// 状态
		Integer type = getIntValue(request.getParameter("type"));// 身份
		Integer isChief = getIntValue(request.getParameter("isChief"));// 主考
		Integer isMixed = getIntValue(request.getParameter("isMixed"));// 副考

		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("teacherinfo", name);
		if (checkStatus > -1) {
			searchMap.put("checkStatus", checkStatus);
		}
		if (status > -1) {
			searchMap.put("status", status);
		}
		if (type > -1) {
			searchMap.put("type", type);
		}
		if (isChief > -1) {
			searchMap.put("isChiefExaminer", isChief);
		}
		if (isMixed > -1) {
			searchMap.put("isMixedExaminer", isMixed);
		}

		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}
		String checkerName = "无";
		String sex = "";
		String inSchoolTime = "";
		String isChiefExaminer = "";
		String isMixedExaminer = "";
		String studyGrade = "无";
		String statusStr = "";
		String checkStatusStr = "";
		try {
			List<InvigilationTeacher> list = this.invigilationTeacherService.searchInvigilationTeacher(searchMap,
					sortParams, -1, -1);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow(i + 1);
					InvigilationTeacher teacher = list.get(i);
					if (teacher.getChecker() != null && teacher.getChecker() > 0) {
						SysUser currentUser = this.sysUserService.getSysUserById(teacher.getChecker());
						if (currentUser != null) {
							checkerName = currentUser.getName();
						}
					}
					sex = teacher.getSex() == 1 ? "男" : "女";
					inSchoolTime = Utlity.timeSpanToString4(Utlity.stringToDate(teacher.getInschooltime()));
					isChiefExaminer = teacher.getIsChiefExaminer() == 1 ? "是" : "否";
					isMixedExaminer = teacher.getIsMixedExaminer() == 1 ? "是" : "否";
					studyGrade = Utlity.checkStringNull(teacher.getStudyMajor()) ? "无" : teacher.getStudyMajor();
					statusStr = teacher.getStatus() == 1 ? "正常" : "停用";
					if (teacher.getCheckStatus() == 0) {
						checkStatusStr = "审核不通过";
					} else if (teacher.getCheckStatus() == 1) {
						checkStatusStr = "未审核";
					} else {
						checkStatusStr = "审核通过";
					}

					String info[] = { teacher.getName(), sex, teacher.getIdcard(), teacher.getMobile(),
							teacher.getEthnic().getName(), teacher.getMajor(), Utlity.getTeacherType(teacher.getType()),
							teacher.getJobDuty(), teacher.getOrganization(), inSchoolTime, isChiefExaminer,
							isMixedExaminer, teacher.getSpecialty(), studyGrade, teacher.getBankCard(),
							teacher.getSid(), Utlity.invigilateCampus(teacher.getInvigilateCampus()),
							Utlity.invigilateType(teacher.getInvigilateType()),
							Utlity.timeSpanToString2(teacher.getCreatetime()), checkerName, statusStr, checkStatusStr };
					for (int j = 0; j < info.length; j++) {
						cell = row.createCell(j);
						cell.setCellValue(info[j]);
						sheet.autoSizeColumn(j);
					}
					int percent = (int) Math.ceil(((i + 1) * 100) / list.size());
					session.setAttribute("percent", percent);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "下载失败", null);
		}
		session.removeAttribute("maxIndex");
		session.removeAttribute("percent");
		actionResult.init(SUCCESS, "下载成功", null);
		response.setContentType("application/vnd.ms-excel");
		String filename = "监考教师资格审核";
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String(filename.getBytes(), "iso-8859-1") + ".xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		wb.close();
	}
}
