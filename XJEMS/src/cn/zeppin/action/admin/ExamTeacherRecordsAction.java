/**
 * This class is used for 教师申报信息操作
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
import cn.zeppin.entity.ExamRoom;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IExamInformationService;
import cn.zeppin.service.api.IExamRoomService;
import cn.zeppin.service.api.IExamTeacherRoomService;
import cn.zeppin.service.api.IInvigilationTeacherService;
import cn.zeppin.service.api.ITeacherDisableRecordsService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.wx.CommonUtil;
import cn.zeppin.utility.wx.ConfigUtil;
import cn.zeppin.utility.wx.MessageUtil;

/**
 * ClassName: ExamTeacherRecordsAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamTeacherRecordsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 369393031897616109L;
	private ITeacherDisableRecordsService teacherDisableRecordsService;

	private IExamInformationService examInformationService;

	private IExamRoomService examRoomService;

	private IInvigilationTeacherService invigilationTeacherService;

	private IExamTeacherRoomService examTeacherRoomService;

	public IExamInformationService getExamInformationService() {
		return examInformationService;
	}

	public void setExamInformationService(IExamInformationService examInformationService) {
		this.examInformationService = examInformationService;
	}

	public IInvigilationTeacherService getInvigilationTeacherService() {
		return invigilationTeacherService;
	}

	public void setInvigilationTeacherService(IInvigilationTeacherService invigilationTeacherService) {
		this.invigilationTeacherService = invigilationTeacherService;
	}

	public IExamTeacherRoomService getExamTeacherRoomService() {
		return examTeacherRoomService;
	}

	public void setExamTeacherRoomService(IExamTeacherRoomService examTeacherRoomService) {
		this.examTeacherRoomService = examTeacherRoomService;
	}

	public IExamRoomService getExamRoomService() {
		return examRoomService;
	}

	public void setExamRoomService(IExamRoomService examRoomService) {
		this.examRoomService = examRoomService;
	}

	public ITeacherDisableRecordsService getTeacherDisableRecordsService() {
		return teacherDisableRecordsService;
	}

	public void setTeacherDisableRecordsService(ITeacherDisableRecordsService teacherDisableRecordsService) {
		this.teacherDisableRecordsService = teacherDisableRecordsService;
	}

	/**
	 * 添加
	 */
	@SuppressWarnings("rawtypes")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "teacher", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult actionResult = new ActionResult();
		// 接受页面参数
		Integer teacher = this.getIntValue(request.getParameter("teacher"));
		Integer exam = this.getIntValue(request.getParameter("exam"));

		// 校验页面参数格式
		InvigilationTeacher teacherE = this.invigilationTeacherService.getById(teacher);
		if (teacherE == null) {
			actionResult.init(FAIL_STATUS, "监考教师信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (teacherE.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "监考教师已被停用", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "考试已结束，不能申报监考", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (information.getStatus() == 2) {
			actionResult.init(FAIL_STATUS, "考试已停止申报，不能申报监考", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		if (information.getStatus() == -1) {
			actionResult.init(FAIL_STATUS, "考试还未发布，不能进行申请监考操作", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if (currentTime.getTime() > information.getApplyendtime().getTime()) {
			actionResult.init(FAIL_STATUS, "超出申报截止时间，不能申报监考", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		// // 判断是否已申报
		// Map<String, Object> searchMap = new HashMap<String, Object>();
		// searchMap.put("teacher", teacher);
		// searchMap.put("exam", exam);
		//
		// Integer count =
		// this.examTeacherRoomService.searchExamTeacherRoomCount(searchMap);
		// if (count != null && count > 0) {
		// actionResult.init(FAIL_STATUS, "已经申报过本场考试监考，不能重复申报", null);
		// Utlity.ResponseWrite(actionResult, dataType, response);
		// return;
		// }
		// 判断是否已申报
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("teacher", teacher);
		searchMap.put("exam", exam);

		List e_list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, null, -1, -1);
		if (e_list != null && e_list.size() > 0) {
			Object o = e_list.get(0);
			Object[] obj = (Object[]) o;
			ExamTeacherRoom etroom = (ExamTeacherRoom) obj[0];
			if (etroom.getStatus() == 0) {
				etroom.setStatus((short) 1);
				etroom.setApplytime(new Timestamp(System.currentTimeMillis()));
				etroom.setCreator(currentUser.getId());
				etroom.setIsFirstApply((short) 0);
				etroom.setIsAuto((short) 0);// 非自主申报
				this.examTeacherRoomService.update(etroom);
				actionResult.init(SUCCESS_STATUS, "申请成功", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			} else if (etroom.getStatus() == 2) {
				actionResult.init(FAIL_STATUS, "该教师已被禁用本次考试", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			} else {
				actionResult.init(FAIL_STATUS, "该教师已申请过本次考试", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			// switch (etroom.getStatus()) {
			// case 0:// 删除
			// etroom.setStatus((short) 1);
			// this.examTeacherRoomService.update(etroom);
			// actionResult.init(SUCCESS_STATUS, "申请成功", null);
			// Utlity.ResponseWrite(actionResult, dataType, response);
			// return;
			// case 1:// 已申报
			// actionResult.init(FAIL_STATUS, "已经申报过本场考试监考，不能重复申报", null);
			// Utlity.ResponseWrite(actionResult, dataType, response);
			// return;
			// case 2:// 禁用
			// actionResult.init(FAIL_STATUS, "该教师已被禁用该考试，不可再申报", null);
			// Utlity.ResponseWrite(actionResult, dataType, response);
			// return;
			// default:
			// break;
			// }
		} else {
			// 创建考试信息实例存储数据
			ExamTeacherRoom etr = new ExamTeacherRoom();
			etr.setTeacher(teacherE);
			etr.setExam(information);
			etr.setIsConfirm((short) 0);
			etr.setIsAuto((short) 0);
			// etr.setCredit(0);
			etr.setApplytime(new Timestamp(System.currentTimeMillis()));

			etr.setStatus((short) 1);
			etr.setCreatetime(new Timestamp(System.currentTimeMillis()));
			etr.setCreator(currentUser.getId());

			// 查询是否申报过
			searchMap.remove("exam");
			// searchMap.remove("status");
			List list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, null, -1, -1);
			if (list != null && list.size() > 0) {
				boolean flag = true;
				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etroom = (ExamTeacherRoom) obj[0];
					if (etroom.getExam().getId() == exam) {
						flag = false;
					}
				}
				if (!flag) {
					etr.setIsFirstApply((short) 0);
				} else {
					etr.setIsFirstApply((short) 1);
				}
			} else {
				etr.setIsFirstApply((short) 1);
			}

			try {
				this.examTeacherRoomService.add(etr);
				actionResult.init(SUCCESS, "申请成功", null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				actionResult.init(FAIL_STATUS, "添加失败", null);
			}

			Utlity.ResponseWrite(actionResult, dataType, response);
		}
	}

	/**
	 * 考场列表
	 */
	@SuppressWarnings("rawtypes")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "roominfo", type = ValueType.STRING)
	@ActionParam(key = "roomId", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "tsorts", type = ValueType.STRING)
	public void DistributeRoomList() {

		ActionResult actionResult = new ActionResult();

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "");
		String tsorts = this.getStrValue(request.getParameter("tsorts"), "");

		String roomId = request.getParameter("roomId");
		Integer exam = this.getIntValue(request.getParameter("exam"));
		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		// if (information.getStatus() == 0) {
		// actionResult.init(FAIL_STATUS, "考试已结束", null);
		// Utlity.ResponseWrite(actionResult, dataType, response);
		// return;
		// }

		String roominfo = request.getParameter("roominfo");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("room", roominfo);
		searchMap.put("exam", exam);
		searchMap.put("status", 1);// 正常的

		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}
		try {
			int recordCount = this.examRoomService.searchExamRoomCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			List<ExamRoom> list = this.examRoomService.searchExamRoom(searchMap, sortParams, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (ExamRoom room : list) {
					Map<String, Object> data = null;
					try {
						data = SerializeEntity.ExamRoom2Map(room);

						// 接受传过来的roomId 意为该考场是否被选中
						if (!Utlity.checkStringNull(roomId) && roomId.equals(room.getId() + "")) {
							data.put("isChecked", 1);
						} else {
							data.put("isChecked", 0);
						}
						// 查询已分配教师的信息
						Map<String, Object> searchMap2 = new HashMap<String, Object>();
						searchMap2.put("exam", exam);
						searchMap2.put("room", room.getId());
						searchMap2.put("status", 1);// 正常的
						Map<String, String> sortParams2 = new HashMap<String, String>();
						if (tsorts != null && !tsorts.equals("")) {
							String[] sortarray = tsorts.split("-");
							String sortname = sortarray[0];
							String sorttype = sortarray[1];

							sortParams2.put(sortname, sorttype);
						}
						List listTeacher = this.examTeacherRoomService.searchExamTeacherRoom(searchMap2, sortParams2,
								-1, -1);
						List<Map<String, Object>> teachers = new ArrayList<Map<String, Object>>();
						if (listTeacher != null && listTeacher.size() > 0) {
							for (int i = 0; i < listTeacher.size(); i++) {
								Object o = listTeacher.get(i);
								Object[] obj = (Object[]) o;
								ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", etr.getId());
								map.put("name", etr.getTeacher().getName());
								map.put("isChief", etr.getIsChief());
								teachers.add(map);
							}
						}
						data.put("teacher", teachers);
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
	 * 监考老师列表
	 */
	@SuppressWarnings("rawtypes")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "teacherinfo", type = ValueType.STRING)
	@ActionParam(key = "roomId", type = ValueType.STRING)
	@ActionParam(key = "teacherStatus", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.NUMBER)
	@ActionParam(key = "isChief", type = ValueType.NUMBER)
	@ActionParam(key = "isMixed", type = ValueType.NUMBER)
	@ActionParam(key = "invigilateCampus", type = ValueType.NUMBER)
	@ActionParam(key = "invigilateType", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void DistributeTeacherList() {

		ActionResult actionResult = new ActionResult();

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "");

		Integer roomId = this.getIntValue(request.getParameter("roomId"));
		Integer exam = this.getIntValue(request.getParameter("exam"));
		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "考试已结束", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		String teacherinfo = request.getParameter("teacherinfo");
		Integer teacherStatus = getIntValue(request.getParameter("teacherStatus"));// 教师状态
		Integer type = getIntValue(request.getParameter("type"));// 教师身份
		Integer isChief = getIntValue(request.getParameter("isChief"));// 主考经验
		Integer isMixed = getIntValue(request.getParameter("isMixed"));// 副考经验
		Integer invigilateCampus = getIntValue(request.getParameter("invigilateCampus"));// 监考校区
		Integer invigilateType = getIntValue(request.getParameter("invigilateType"));// 监考类型
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("teacherinfo", teacherinfo);
		searchMap.put("exam", exam);
		searchMap.put("status", 1);// 未删除的
		searchMap.put("roomId", roomId);// 分配的考场

		if (teacherStatus > -1) {
			searchMap.put("teacherStatus", teacherStatus);
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
		if (invigilateCampus > -1) {
			searchMap.put("invigilateCampus", invigilateCampus);
		}
		if (invigilateType > -1) {
			searchMap.put("invigilateType", invigilateType);
		}

		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}

		try {
			int recordCount = this.examTeacherRoomService.searchExamTeacherRoomCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			List list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, sortParams, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					Map<String, Object> data = null;
					try {
						data = SerializeEntity.ExamTeacherRoom2Map(etr);
						data.put("organization", etr.getTeacher().getOrganization());
						data.put("isPunish", (short) 0);// 1有扣分记录 ，0无

						Map<String, Object> searchParams = new HashMap<String, Object>();
						searchParams.put("teacher", etr.getTeacher().getId());
						List<ExamTeacherRoom> t_list = this.examTeacherRoomService.getByParams(searchParams);
						for (int j = 0; j < t_list.size(); j++) {
							// 有扣分记录或者禁用记录的教师
							if (t_list.get(j).getCredit() != null && t_list.get(j).getCredit() < 0
									|| etr.getTeacher().getIsDisable() == 1) {
								data.put("isPunish", (short) 1);
								break;
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
	 * 分配监考教师
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "room", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "isChief", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Distribute() {

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult actionResult = new ActionResult();
		// check paras
		// 接受页面参数
		Integer id = this.getIntValue(request.getParameter("id"));
		Integer room = this.getIntValue(request.getParameter("room"));
		Short isChief = Short.parseShort((request.getParameter("isChief")));

		// 校验页面参数格式

		try {

			ExamRoom er = this.examRoomService.getById(room);
			if (er == null) {
				actionResult.init(FAIL_STATUS, "考场信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			if (er.getExam().getStatus() == 0) {
				actionResult.init(FAIL_STATUS, "考试已结束", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
			ExamTeacherRoom etr = this.examTeacherRoomService.getById(id);
			if (etr == null) {
				actionResult.init(FAIL_STATUS, "教师申报信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			if (etr.getStatus() == 0) {
				actionResult.init(FAIL_STATUS, "教师申报信息已失效，请重新申报后再分配考场", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

			// if (etr.getStatus() == 1 && etr.getRoom() != null &&
			// etr.getRoom().getId() == room) {
			// if (etr.getIsChief() == 1) {
			// actionResult.init(FAIL_STATUS, "教师已被分配为该考场的主监考", null);
			// } else {
			// actionResult.init(FAIL_STATUS, "教师已被分配为该考场的副监考", null);
			// }
			// Utlity.ResponseWrite(actionResult, dataType, response);
			// return;
			// }

			etr.setRoom(er);
			etr.setOperater(currentUser.getId());
			etr.setApplytime(new Timestamp(System.currentTimeMillis()));
			etr.setIsChief(isChief);
			etr.setIsMixed((short) 0);

			this.examTeacherRoomService.update(etr);
			actionResult.init(SUCCESS, "信息更新成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息更新异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 取消分配
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Cancle() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		ExamTeacherRoom etr = this.examTeacherRoomService.getById(id);
		if (etr != null) {
			// 重置分配状态
			etr.setRoom(null);
			etr.setIsChief((short) 0);
			etr.setOperater(null);
			etr.setConfirtime(null);
			etr.setIsConfirm((short) 0);
			try {
				this.examTeacherRoomService.update(etr);
				actionResult.init(SUCCESS, "取消成功", null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				actionResult.init(FAIL_STATUS, "取消失败", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}

		} else {
			actionResult.init(FAIL_STATUS, "信息不存在", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 变更状态 id
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Change() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		try {
			ExamTeacherRoom etr = this.examTeacherRoomService.getById(Integer.parseInt(id));
			if (etr != null) {
				etr.setStatus(Short.parseShort(status));
				this.examTeacherRoomService.update(etr);
			}
			actionResult.init(SUCCESS, "操作成功", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "操作失败", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 批量删除 批量格式：1，2，3 逗号分隔
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Delete() {
		// TODO Auto-generated method stub
		ActionResult actionResult = new ActionResult();
		String id = request.getParameter("id");
		String ids[] = id.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				ExamTeacherRoom etr = this.examTeacherRoomService.getById(Integer.parseInt(ids[i]));
				if (etr != null) {
					etr.setStatus((short) 0);
					etr.setRoom(null);
					etr.setIsChief((short) 0);
					etr.setIsMixed((short) 0);
					etr.setIsConfirm((short) 0);
					etr.setConfirtime(null);
					etr.setCredit(null);
					this.examTeacherRoomService.update(etr);
				}
			}
			actionResult.init(SUCCESS, "删除成功", null);
		} catch (Exception e) {
			actionResult.init(FAIL_STATUS, "删除失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * 批量禁用 批量 格式：“1，2，3”
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "reason", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "id", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "exam", type = ValueType.STRING)
	@ActionParam(key = "disableType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Disable() {
		ActionResult actionResult = new ActionResult();
		String reason = request.getParameter("reason") == null ? "" : request.getParameter("reason");
		String id = request.getParameter("id");
		String exam = request.getParameter("exam");
		// 禁用方式
		int disableType = getIntValue(request.getParameter("disableType"));
		// String ids[] = id.split(",");
		try {
			SysUser currentUser = (SysUser) session.getAttribute("usersession");
			this.invigilationTeacherService.updateDisable(id, exam, reason, disableType, currentUser.getId());
			actionResult.init(SUCCESS, "停用成功", null);
		} catch (Exception e) {
			actionResult.init(FAIL_STATUS, "停用失败", null);
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
		ExamTeacherRoom etr = this.examTeacherRoomService.getById(id);
		if (etr != null) {

			Map<String, Object> data = null;
			try {
				data = SerializeEntity.ExamTeacherRoom2Map(etr);
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
	/**
	 * 获取教师申报列表
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "applyStatus", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "teacherStatus", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "isChief", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "isMixed", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "teacherinfo", type = ValueType.STRING)
	@ActionParam(key = "invigilateCampus", type = ValueType.STRING) // 增加监考校区和监考类型的筛选功能
	@ActionParam(key = "invigilateType", type = ValueType.STRING)
	public void AllList() {

		ActionResult actionResult = new ActionResult();

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "");

		Integer exam = this.getIntValue(request.getParameter("exam"));
		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "考试已结束", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		Integer applyStatus = this.getIntValue(request.getParameter("applyStatus"));
		Integer type = this.getIntValue(request.getParameter("type"), 0);
		Integer teacherStatus = this.getIntValue(request.getParameter("teacherStatus"));
		Integer isChief = this.getIntValue(request.getParameter("isChief"));
		Integer isMixed = this.getIntValue(request.getParameter("isMixed"));
		// 增加监考校区和监考类型的筛选功能
		Integer invigilateType = this.getIntValue(request.getParameter("invigilateType"));
		Integer invigilateCampus = this.getIntValue(request.getParameter("invigilateCampus"));

		String teacherinfo = request.getParameter("teacherinfo") == null ? "" : request.getParameter("teacherinfo");// 搜索参数

		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("exam", exam);
		if (applyStatus > -1) {
			if (applyStatus == 1) {
				searchMap.put("first", applyStatus);// 初次申报
			} else if (applyStatus == 2) {
				searchMap.put("distribute", "2");// 是否安排考场2未安排
			} else if (applyStatus == 3) { // 3安排
				searchMap.put("distribute", "3");
			} else if (applyStatus == 4) {
				searchMap.put("isConfirm", 0);// 未二次确认
			} else if (applyStatus == 5) {
				searchMap.put("isConfirm", 1);// 二次确认
			}
		}
		if (type > 0) {
			searchMap.put("type", type);
		}
		if (teacherStatus > -1) {
			searchMap.put("teacherStatus", teacherStatus);
		}
		if (isChief > -1) {
			searchMap.put("isChiefExaminer", isChief);
		}
		if (isMixed > -1) {
			searchMap.put("isMixedExaminer", isMixed);
		}

		searchMap.put("teacherinfo", teacherinfo);
		searchMap.put("statusNormal", 1);// 未删除的
		if (invigilateCampus > 0) {
			searchMap.put("invigilateCampus", invigilateCampus);// 监考校区
		}
		if (invigilateCampus > 0) {
			searchMap.put("invigilateType", invigilateType);// 监考类型
		}

		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}

		try {
			int recordCount = this.examTeacherRoomService.searchExamTeacherRoomCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			List list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, sortParams, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					Map<String, Object> data = null;
					try {
						data = SerializeEntity.ExamTeacherRoom2Map(etr);

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
	 * 获取教师二次确认列表
	 */
	@SuppressWarnings("rawtypes")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "isChief", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "teacherinfo", type = ValueType.STRING)
	@ActionParam(key = "isconfirm", type = ValueType.NUMBER)
	@ActionParam(key = "isCredit", type = ValueType.NUMBER)
	public void ConfirmList() {

		ActionResult actionResult = new ActionResult();

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "");

		Integer exam = this.getIntValue(request.getParameter("exam"));
		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "考试已结束", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		// Integer applyStatus =
		// this.getIntValue(request.getParameter("applyStatus"));
		Integer type = this.getIntValue(request.getParameter("type"));
		Integer isChief = this.getIntValue(request.getParameter("isChief"));
		Integer isconfirm = this.getIntValue(request.getParameter("isconfirm"));
		Integer isCredit = this.getIntValue(request.getParameter("isCredit"));
		String teacherinfo = request.getParameter("teacherinfo") == null ? "" : request.getParameter("teacherinfo");// 搜索参数

		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("exam", exam);
		searchMap.put("distribute", 3);
		if (type > -1) {
			searchMap.put("type", type);
		}
		if (isChief > -1) {
			searchMap.put("isChief", isChief);
		}
		// if (isChief > -1) {
		// searchMap.put("isChiefExaminer", isChief);
		// }

		searchMap.put("teacherinfo", teacherinfo);
		// searchMap.put("status", 1);// 正常的
		searchMap.put("statusNormal", "0");

		// 是否确认（评价教师列表用）
		// String isconfim = request.getParameter("isconfirm") == null ? "" :
		// request.getParameter("isconfirm");
		// if ("".equals(isconfim)) {
		// searchMap.put("isConfirm", 1);
		// }
		if (isconfirm > -1) {
			searchMap.put("isConfirm", isconfirm);
		}
		// 打分
		if (isCredit > -1) {
			searchMap.put("isCredit", isCredit + "");
		}

		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}

		try {
			int recordCount = this.examTeacherRoomService.searchExamTeacherRoomCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			List list = this.examTeacherRoomService.searchExamTeacherRoom(searchMap, sortParams, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					Map<String, Object> data = null;
					try {
						if (etr.getStatus() != 0) {
							data = SerializeEntity.ExamTeacherRoom2Map(etr);
							data.put("recordStatus", etr.getStatus());
							if (etr.getCredit() == null) {// 是否打分
								data.put("creditStatus", 0);
							} else {
								data.put("creditStatus", 1);
							}
							dataList.add(data);
						}
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
	 * 教师打分 积分更新操作 id 批量 格式：“1，2，3”
	 */
	@ActionParam(key = "id", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "score", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "reason", type = ValueType.STRING, nullable = false)
	public void IntgralUpdate() {

		ActionResult actionResult = new ActionResult();
		// check paras
		// 接受页面参数
		String id = request.getParameter("id");
		Integer score = this.getIntValue(request.getParameter("score"));
		String reason = request.getParameter("reason");

		// 校验页面参数格式
		try {
			String ids[] = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("id", ids[i]);
				List<ExamTeacherRoom> list = examTeacherRoomService.getByParams(searchMap);
				updateCredit(list, score, reason);
			}
			actionResult.init(SUCCESS, "打分成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "打分异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 获取教师本次积分
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void GetIntgral() {

		ActionResult actionResult = new ActionResult();
		// check paras
		// 接受页面参数
		Integer id = this.getIntValue(request.getParameter("id"));

		// 校验页面参数格式

		try {
			ExamTeacherRoom etr = this.examTeacherRoomService.getById(id);
			if (etr != null) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", etr.getId());
				data.put("credit", etr.getCredit() == null ? "" : etr.getCredit());
				data.put("reason", etr.getReason() == null ? "" : etr.getReason());
				Short isScore = 0;
				if (etr.getCredit() != null) {
					isScore = 1;
				}
				data.put("isScore", isScore);
				actionResult.init(SUCCESS, "获取成功", data);
			} else {
				actionResult.init(FAIL_STATUS, "信息不存在", null);
				Utlity.ResponseWrite(actionResult, dataType, response);
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "信息更新异常", null);
		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 教师二次确认管理“确认”
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Confirm() {
		// 监考记录id
		int id = Integer.parseInt(request.getParameter("id"));
		ActionResult actionResult = new ActionResult();
		
		try {
			ExamTeacherRoom etr = examTeacherRoomService.getById(id);
			if (etr != null) {
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				if (currentTime.getTime() > etr.getExam().getCheckendtime().getTime()) {
					actionResult.init(FAIL_STATUS, "已超出确认截止时间，不能进行二次确认", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				
				if (etr.getStatus() == 2 || etr.getTeacher().getStatus() == 0) {
					actionResult.init(FAIL_STATUS, "教师已被停用，无法进行二次确认", null);
					Utlity.ResponseWrite(actionResult, dataType, response);
					return;
				}
				etr.setConfirtime(new Timestamp(System.currentTimeMillis()));// 确认时间
				etr.setIsConfirm((short) 1);// 确认状态
				examTeacherRoomService.update(etr);
				actionResult.init(SUCCESS, "已确认参加", null);
			} else {
				actionResult.init(FAIL_STATUS, "考场信息不存在", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "操作失败", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 批量打分
	 * 
	 * @param list
	 * @param integral
	 * @param reason
	 */
	private void updateCredit(List<ExamTeacherRoom> list, int integral, String reason) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ExamTeacherRoom etr = list.get(i);
				if (etr != null) {
					// 同步更新教师积分
					InvigilationTeacher teacher = etr.getTeacher();
					Integer count = teacher.getIntgral();// 总分
					int credit = etr.getCredit() == null ? 0 : etr.getCredit();
					count = count - credit + integral;
					teacher.setIntgral(count);
					etr.setTeacher(teacher);
					// 更新教师得分
					etr.setCredit(integral);
					etr.setReason(reason);
					invigilationTeacherService.update(teacher);
					examTeacherRoomService.update(etr);
				}
			}
		}
	}

	/**
	 * 待分配教师
	 */
	@SuppressWarnings("rawtypes")
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = false)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void UndTeacherList() {
		ActionResult actionResult = new ActionResult();
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "");
		Map<String, String> sortParams = new HashMap<String, String>();
		if (sorts != null && !sorts.equals("")) {
			String[] sortarray = sorts.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		}

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Integer exam = this.getIntValue(request.getParameter("exam"));
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("distribute", "2");// “EXAM_ROOM”字段 为空
		searchMap.put("status", 1);//
		searchMap.put("exam", exam);//
		try {
			int recordCount = this.examTeacherRoomService.searchExamTeacherRoomCount(searchMap);
			List list = this.examTeacherRoomService.searchInvigilationTeacher(searchMap, sortParams, offset, pagesize);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					Map<String, Object> data = null;
					data = SerializeEntity.ExamTeacherRoom2Map(etr);
					dataList.add(data);
				}
			}
			actionResult.init(SUCCESS_STATUS, "获取成功", dataList);
			actionResult.setPageCount(pageCount);
			actionResult.setPageNum(pagenum);
			actionResult.setPageSize(pagesize);
			actionResult.setTotalCount(recordCount);
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "获取失败", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	/**
	 * 发送通知
	 */
	@ActionParam(key = "exam", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@SuppressWarnings("rawtypes")
	public void Send() {
		ActionResult actionResult = new ActionResult();
		Integer exam = this.getIntValue(request.getParameter("exam"));// 考试id
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("exam", exam);
		ExamInformation information = this.examInformationService.getById(exam);
		if (information == null) {
			actionResult.init(FAIL_STATUS, "考试信息不存在", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}

		if (information.getStatus() == 0) {
			actionResult.init(FAIL_STATUS, "考试已结束", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
			return;
		}
		try {
			List list = examTeacherRoomService.searchExamTeacherRoom(searchMap, null, -1, -1);
			if (list != null & list.size() > 0) {
				String access_token = CommonUtil.getAccessToken().getAccessToken();
				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					if (etr.getRoom() != null && etr.getStatus() == 1) {// 已分配教师并且是未二次确认的老师
						// 给用户发送系统消息通知
						if (etr.getIsConfirm() == 0) {
							Map<String, String> templateParams = new HashMap<>();
							templateParams.put("first", "您好，您有新的考试安排");
							templateParams.put("keyword1", etr.getExam().getName());
							templateParams.put("keyword2",
									etr.getRoom().getRoomIndex() + "-" + etr.getRoom().getRoomAddress());
							templateParams.put("keyword3",
									Utlity.timeSpanToDateString(etr.getExam().getExamStarttime()) + "(北京时间)");
							templateParams.put("keyword4",
									Utlity.timeSpanToDateString(etr.getExam().getExamEndtime()) + "(北京时间)");
							templateParams.put("remark", "请尽快进行二次确认！");
							templateParams.put("url", "http://" + request.getServerName()
									+ "/XJ_wechat/roomMessage.html?id=" + etr.getId());
							templateParams.put("touser", etr.getTeacher().getOpenID());
							ConfigUtil.sendTemplate(access_token, MessageUtil.teacherAgreeTemplate(templateParams));
						}
					} else if (etr.getStatus() != 0) {// 未分配(包括禁用)
						Map<String, String> templateParams = new HashMap<>();
						templateParams.put("first", "收到监考" + etr.getExam().getName() + "考试的申请反馈");
						templateParams.put("keyword1", etr.getTeacher().getName());
						templateParams.put("keyword2", etr.getTeacher().getMobile());
						templateParams.put("keyword3",
								Utlity.timeSpanToString5(new Timestamp(System.currentTimeMillis())));
						templateParams.put("keyword4", "未录用");
						templateParams.put("remark", "遗憾的通知您，您未被录用。");
						templateParams.put("url", "");
						templateParams.put("touser", etr.getTeacher().getOpenID());
						ConfigUtil.sendTemplate(access_token, MessageUtil.teacherDeclinedTemplate(templateParams));
					}
				}
			}
			actionResult.init(SUCCESS_STATUS, "发送成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			actionResult.init(FAIL_STATUS, "发送失败", null);
			Utlity.ResponseWrite(actionResult, dataType, response);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}
}
