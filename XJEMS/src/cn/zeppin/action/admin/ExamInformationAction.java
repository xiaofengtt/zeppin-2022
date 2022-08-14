/**
 * This class is used for 考试信息操作
 * 
 */
package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.ExamInformation;
import cn.zeppin.entity.InvigilationTemplate;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IExamInformationService;
import cn.zeppin.service.api.IExamTeacherRoomService;
import cn.zeppin.service.api.IInvigilationTeacherService;
import cn.zeppin.service.api.IInvigilationTemplateService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.wx.MessageUtil;

/**
 * ClassName: ExamInformationAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamInformationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 369393031897616109L;

	private IExamInformationService examInformationService;
	private IInvigilationTemplateService invigilationTemplateService;
	private IExamTeacherRoomService examTeacherRoomService;
	private IInvigilationTeacherService invigilationTeacherService;

	public IExamInformationService getExamInformationService() {
		return examInformationService;
	}

	public void setExamInformationService(IExamInformationService examInformationService) {
		this.examInformationService = examInformationService;
	}

	public IInvigilationTemplateService getInvigilationTemplateService() {
		return invigilationTemplateService;
	}

	public void setInvigilationTemplateService(IInvigilationTemplateService invigilationTemplateService) {
		this.invigilationTemplateService = invigilationTemplateService;
	}

	public IExamTeacherRoomService getExamTeacherRoomService() {
		return examTeacherRoomService;
	}

	public void setExamTeacherRoomService(IExamTeacherRoomService examTeacherRoomService) {
		this.examTeacherRoomService = examTeacherRoomService;
	}

	public IInvigilationTeacherService getInvigilationTeacherService() {
		return invigilationTeacherService;
	}

	public void setInvigilationTeacherService(IInvigilationTeacherService invigilationTeacherService) {
		this.invigilationTeacherService = invigilationTeacherService;
	}

	/**
	 * 添加
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "starttime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "endtime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "integral", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "information", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "invigilationContract", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "applyEndTime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "checkEndTime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "applyNotice", type = ValueType.STRING)
	@ActionParam(key = "invigilationNotice", type = ValueType.STRING)
	@ActionParam(key = "isSend", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult actionResult = new ActionResult();
		// 接受页面参数
		String name = request.getParameter("name");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		Integer integral = this.getIntValue(request.getParameter("integral"));
		String information = request.getParameter("information");
		String invigilationContract = request.getParameter("invigilationContract");
		String applyEndTime = request.getParameter("applyEndTime");
		String checkEndTime = request.getParameter("checkEndTime");

		String applyNotice = request.getParameter("applyNotice");
		String invigilationNotice = request.getParameter("invigilationNotice");
		
		Short isSend = request.getParameter("isSend") == null ? 1 : Short.valueOf(request.getParameter("isSend"));
		

		// 校验页面参数格式
		if (!Utlity.isDataFormat(starttime)) {
			actionResult.init(FAIL_STATUS, "考试开始日期格式错误", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (!Utlity.isDataFormat(endtime)) {
			actionResult.init(FAIL_STATUS, "考试结束日期格式错误", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Timestamp startTime = Timestamp.valueOf(starttime + " 00:00:00");
		Timestamp endTime = Timestamp.valueOf(endtime + " 23:59:55");
		if (startTime.compareTo(endTime) == 1) {
			actionResult.init(FAIL_STATUS, "结束日期应大于考试日期", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		// 按日期判断当前是否存在进行的考试
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("currenttime", Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis())));
		searchMap.put("statusNormal", 1);// 未结束的考试
		try {

			Integer count = this.examInformationService.searchExamInformationCount(searchMap);
			if (count > 0) {
				actionResult.init(FAIL_STATUS, "当前已有正在进行的考试，不能进行考试发布", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息获取异常", null);
		}
		// 创建考试信息实例存储数据
		ExamInformation eInformation = new ExamInformation();
		eInformation.setName(name);
		eInformation.setExamStarttime(Timestamp.valueOf(starttime + " 00:00:00"));
		eInformation.setExamEndtime(Timestamp.valueOf(endtime + " 23:59:55"));

		eInformation.setIntegral(integral);
		eInformation.setInformation(information);
		eInformation.setInvigilationContract(invigilationContract);

		eInformation.setApplyendtime(Timestamp.valueOf(applyEndTime));
		eInformation.setCheckendtime(Timestamp.valueOf(checkEndTime));
		eInformation.setStatus((short) -1);// 初始化状态为 待发布
		eInformation.setCreatetime(new Timestamp(System.currentTimeMillis()));
		eInformation.setCreator(currentUser.getId());
		eInformation.setApplyNotice(applyNotice);// 申报注意事项
		eInformation.setInvigilationNotice(invigilationNotice);// 监考注意事项
		eInformation.setIsSend(isSend);//是否需要发送群发消息

		try {
			this.examInformationService.add(eInformation);
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
	@ActionParam(key = "starttime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "endtime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "integral", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "information", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "invigilationContract", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "applyEndTime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "checkEndTime", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "applyNotice", type = ValueType.STRING)
	@ActionParam(key = "invigilationNotice", type = ValueType.STRING)
	@ActionParam(key = "isSend", type = ValueType.NUMBER)
	public void Update() {

		ActionResult actionResult = new ActionResult();
		// check paras
		// 接受页面参数
		Integer id = this.getIntValue(request.getParameter("id"));
		String name = request.getParameter("name");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		Integer integral = this.getIntValue(request.getParameter("integral"));
		String information = request.getParameter("information");
		String invigilationContract = request.getParameter("invigilationContract");
		String applyEndTime = request.getParameter("applyEndTime");
		String checkEndTime = request.getParameter("checkEndTime");
		int status = this.getIntValue(request.getParameter("status"));
		String applyNotice = request.getParameter("applyNotice");
		String invigilationNotice = request.getParameter("invigilationNotice");
		Short isSend = request.getParameter("isSend") == null ? 1 : Short.valueOf(request.getParameter("isSend"));

		// 校验页面参数格式
		if (!Utlity.isDataFormat(starttime)) {
			actionResult.init(FAIL_STATUS, "考试开始日期格式错误", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (!Utlity.isDataFormat(endtime)) {
			actionResult.init(FAIL_STATUS, "考试结束日期格式错误", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Timestamp startTime = Timestamp.valueOf(starttime + " 00:00:00");
		Timestamp endTime = Timestamp.valueOf(endtime + " 00:00:00");
		if (startTime.compareTo(endTime) == 1) {
			actionResult.init(FAIL_STATUS, "结束日期应大于考试日期", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (status != 0) {
			// 按日期判断当前是否存在进行的考试
			Map<String, Object> searchMap = new HashMap<String, Object>();
			// searchMap.put("currenttime", Utlity.timeSpanToString(new
			// Timestamp(System.currentTimeMillis())));
			searchMap.put("statusNormal", 1);// 未结束的考试
			List<ExamInformation> list = this.examInformationService.searchExamInformation(searchMap, null, -1, -1);
			if (list != null && list.size() > 0 && list.get(0).getId() != id) {
				actionResult.init(FAIL_STATUS, "当前已有正在进行的考试，不能进行考试发布", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
		}

		try {
			ExamInformation examInformation = this.examInformationService.getById(id);
			if (examInformation != null) {
				examInformation.setName(name);
				examInformation.setExamStarttime(Timestamp.valueOf(starttime + " 00:00:00"));
				examInformation.setExamEndtime(Timestamp.valueOf(endtime + " 00:00:00"));

				examInformation.setIntegral(integral);
				examInformation.setInformation(information);
				examInformation.setInvigilationContract(invigilationContract);

				examInformation.setApplyendtime(Timestamp.valueOf(applyEndTime));
				examInformation.setCheckendtime(Timestamp.valueOf(checkEndTime));
				examInformation.setStatus((short) status);//
				examInformation.setApplyNotice(applyNotice);// 申报注意事项
				examInformation.setInvigilationNotice(invigilationNotice);// 监考注意事项
				
				examInformation.setIsSend(isSend);
			} else {
				actionResult.init(FAIL_STATUS, "考试信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			this.examInformationService.update(examInformation);
			actionResult.init(SUCCESS, "考试信息更新成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "考试信息更新异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 变更状态
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Change() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		try {
			ExamInformation examInformation = examInformationService.getById(id);
			if (examInformation != null) {
				// 从待发布到发布状态，则群发提醒，停止审核再到开始审核（发布）则不提醒
				//20201023增加是否群发消息的判定
				if ("1".equals(status) && examInformation.getStatus() == -1 && examInformation.getIsSend() == 1) {// 已发布，公众号群发消息
					String pathimg = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")
							+ "img/cover.png";
					int result = MessageUtil.sendAll(examInformation, currentUser.getName(), "", pathimg);
					if (result == 45009) {
						examInformation.setStatus(Short.parseShort(status));
						this.examInformationService.update(examInformation);
						actionResult.init(FAIL_STATUS, "提醒，微信群发消息次数超限！", null);// 每月发布考试推送消息至多4条
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
				} 
//				else if ("0".equals(status)) {// 已结束，教师打分未处理的为默认打分
//					Map<String, Object> searchMap = new HashMap<String, Object>();
//					searchMap.put("exam", id);
//					searchMap.put("status", 1);// 正常
//					List<ExamTeacherRoom> list = examTeacherRoomService.getByParams(searchMap);
//					updateCredit(list, examInformation.getIntegral(), "");
//				}
				examInformation.setStatus(Short.parseShort(status));
				this.examInformationService.update(examInformation);
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
	 * 删除
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		ExamInformation examInformation = examInformationService.getById(id);
		if (examInformation != null) {
			examInformation.setStatus((short) 0);
			this.examInformationService.update(examInformation);

			actionResult.init(SUCCESS, "删除更新成功", null);
		} else {
			actionResult.init(FAIL_STATUS, "此资源不存在", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 根绝ID获取考试信息
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Get() {

		int id = -1;
		ActionResult actionResult = new ActionResult();
		id = Integer.parseInt(request.getParameter("id"));
		ExamInformation ExamInformation = examInformationService.getById(id);
		if (ExamInformation != null) {

			Map<String, Object> data = null;
			try {
				data = SerializeEntity.ExamInformation2Map(ExamInformation);
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

	/**
	 * 获取当前时间内考试信息
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	public void GetCurrent() {

		ActionResult actionResult = new ActionResult();

		Map<String, Object> searchMap = new HashMap<String, Object>();
		// searchMap.put("currenttime", Utlity.timeSpanToString(new
		// Timestamp(System.currentTimeMillis())));
		searchMap.put("statusNormal", 1);// 未结束的考试
		try {

			List<ExamInformation> list = this.examInformationService.searchExamInformation(searchMap, null, -1, -1);
			if (list != null && list.size() > 0) {
				ExamInformation information = list.get(0);
				Map<String, Object> data = null;
				try {
					data = SerializeEntity.ExamInformation2Map(information);
					if (information.getStatus() == 0) {
						actionResult.init(END_STATUS, "考试结束", data);
						Utlity.ResponseWrite(actionResult, dataType, response);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					actionResult.init(FAIL_STATUS, "信息转换异常", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}

				actionResult.init(SUCCESS_STATUS, "获取资源成功", data);
			} else {
				actionResult.init(EMPTY_STATUS, "暂无正在进行的考试", null);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息获取异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取考试信息列表
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {

		ActionResult actionResult = new ActionResult();

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		String name = request.getParameter("name");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);

		try {
			int recordCount = this.examInformationService.searchExamInformationCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			List<ExamInformation> list = this.examInformationService.searchExamInformation(searchMap, sorts, offset,
					pagesize);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (ExamInformation information : list) {
					Map<String, Object> data = null;
					try {
						data = SerializeEntity.ExamInformation2Map(information);
						data.put("time", Utlity.timeSpanToString3(information.getExamStarttime()) + "-"
								+ Utlity.timeSpanToString3(information.getExamEndtime()));
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
	 * 获取所有监考责任书的模板数据
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	public void TemplateList() {
		ActionResult actionResult = new ActionResult();
		try {
			List<InvigilationTemplate> list = invigilationTemplateService.getInvigilationTemplateList(-1, -1);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (InvigilationTemplate template : list) {
					Map<String, Object> data = null;
					data = SerializeEntity.InvigilationTemplate2Map(template);
					dataList.add(data);
				}

			}
			actionResult.init(SUCCESS_STATUS, "获取模板数据成功！", dataList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "数据获取异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 添加 监考责任书 模板
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.STRING)
	@ActionParam(key = "title", type = ValueType.STRING, nullable = false)
	@ActionParam(key = "content", type = ValueType.STRING, nullable = false)
	public void AddTemplate() {
		ActionResult actionResult = new ActionResult();
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int id = getIntValue(request.getParameter("id"));
		try {
			// int count = invigilationTemplateService.getCount();
			InvigilationTemplate template = invigilationTemplateService.getById(id);
			if (template != null) {
				template.setContent(content);
				this.invigilationTemplateService.update(template);
			} else {
				template = new InvigilationTemplate();
				template.setCreatetime(new Timestamp(System.currentTimeMillis()));
				template.setCreator(currentUser.getId());
				template.setTitle(title);
				template.setContent(content);
				this.invigilationTemplateService.add(template);
			}
			actionResult.init(SUCCESS, "添加成功", template);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "添加失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 根据监考责任书模板id，删除模板数据
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.STRING, nullable = false)
	public void DeteleTemplate() {
		ActionResult actionResult = new ActionResult();
		int id = this.getIntValue(request.getParameter("id"));
		InvigilationTemplate template = invigilationTemplateService.getById(id);
		try {
			template = this.invigilationTemplateService.delete(template);
			actionResult.init(SUCCESS, "删除成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "删除失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "invigilationNotice", type = ValueType.STRING, nullable = false, emptyable = false)
	public void ChangeInNotice() {
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		String invigilationNotice = request.getParameter("invigilationNotice");
		try {
			ExamInformation examInformation = examInformationService.getById(id);
			if (examInformation != null) {
				examInformation.setInvigilationNotice(invigilationNotice);
				this.examInformationService.update(examInformation);
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
	 * 结束考试时，对此次考试，未进行打分处理的老师，根据考试积分进行默认打分处理
	 * 
	 * @param list
	 * @param integral
	 * @param reason
	 */
//	private void updateCredit(List<ExamTeacherRoom> list, int integral, String reason) {
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				ExamTeacherRoom etr = list.get(i);
//				if (etr != null && etr.getCredit() == null) {
//					// 同步更新教师积分
//					InvigilationTeacher teacher = etr.getTeacher();
//					Integer count = teacher.getIntgral();// 总分
//					count += integral;
//					teacher.setIntgral(count);
//					etr.setTeacher(teacher);
//					// 更新教师得分
//					etr.setCredit(integral);
//					etr.setReason(reason);
//					invigilationTeacherService.update(teacher);
//					examTeacherRoomService.update(etr);
//				}
//			}
//		}
//	}
}
