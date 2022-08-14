package cn.zeppin.service.imp;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IExamTeacherRoomDAO;
import cn.zeppin.dao.api.IInvigilationTeacherDAO;
import cn.zeppin.dao.api.IInvigilationTeacherOldDAO;
import cn.zeppin.dao.api.ITeacherDisableRecordsDao;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.entity.InvigilationTeacherOld;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.TeacherDisableRecords;
import cn.zeppin.service.api.IInvigilationTeacherService;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.wx.CommonUtil;
import cn.zeppin.utility.wx.ConfigUtil;
import cn.zeppin.utility.wx.MessageUtil;

/**
 * ClassName: InvigilationTeacherService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class InvigilationTeacherService implements IInvigilationTeacherService {

	IInvigilationTeacherDAO iInvigilationTeacherDAO;
	IInvigilationTeacherOldDAO iInvigilationTeacherOldDAO;
	IExamTeacherRoomDAO iExamTeacherRoomDAO;
	ITeacherDisableRecordsDao teacherDisableRecordsDao;

	/**
	 * @return the iInvigilationTeacherDAO
	 */
	public IInvigilationTeacherDAO getiInvigilationTeacherDAO() {
		return iInvigilationTeacherDAO;
	}

	/**
	 * @param iInvigilationTeacherDAO
	 *            the iInvigilationTeacherDAO to set
	 */
	public void setiInvigilationTeacherDAO(IInvigilationTeacherDAO iInvigilationTeacherDAO) {
		this.iInvigilationTeacherDAO = iInvigilationTeacherDAO;
	}

	public IInvigilationTeacherOldDAO getiInvigilationTeacherOldDAO() {
		return iInvigilationTeacherOldDAO;
	}

	public void setiInvigilationTeacherOldDAO(IInvigilationTeacherOldDAO iInvigilationTeacherOldDAO) {
		this.iInvigilationTeacherOldDAO = iInvigilationTeacherOldDAO;
	}

	public IExamTeacherRoomDAO getiExamTeacherRoomDAO() {
		return iExamTeacherRoomDAO;
	}

	public void setiExamTeacherRoomDAO(IExamTeacherRoomDAO iExamTeacherRoomDAO) {
		this.iExamTeacherRoomDAO = iExamTeacherRoomDAO;
	}

	public ITeacherDisableRecordsDao getTeacherDisableRecordsDao() {
		return teacherDisableRecordsDao;
	}

	public void setTeacherDisableRecordsDao(ITeacherDisableRecordsDao teacherDisableRecordsDao) {
		this.teacherDisableRecordsDao = teacherDisableRecordsDao;
	}

	@Override
	public InvigilationTeacher getById(int id) {

		return this.getiInvigilationTeacherDAO().get(id);
	}

	@Override
	public InvigilationTeacher add(InvigilationTeacher InvigilationTeacher) {

		return this.getiInvigilationTeacherDAO().save(InvigilationTeacher);
	}

	@Override
	public void update(InvigilationTeacher InvigilationTeacher) {

		this.getiInvigilationTeacherDAO().update(InvigilationTeacher);
	}

	@Override
	public void delById(int id) {

		InvigilationTeacher InvigilationTeacher = this.getiInvigilationTeacherDAO().get(id);
		InvigilationTeacher.setStatus((short) 0);
		this.getiInvigilationTeacherDAO().update(InvigilationTeacher);
	}

	@Override
	public List<InvigilationTeacher> searchInvigilationTeacher(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length) {
		return this.getiInvigilationTeacherDAO().searchInvigilationTeacher(searchMap, sortParams, offset, length);
	}

	@Override
	public List<Object[]> searchInvigilationTeacherBySql(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length) {
		return this.getiInvigilationTeacherDAO().searchInvigilationTeacherBySql(searchMap, sortParams, offset, length);
	}
	
	@Override
	public int searchInvigilationTeacherCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getiInvigilationTeacherDAO().searchInvigilationTeacherCount(searchMap);
	}

	@Override
	public InvigilationTeacher getInvigilationTeacherInfoByOpenID(String openID) {
		// TODO Auto-generated method stub
		return this.getiInvigilationTeacherDAO().getInvigilationTeacherInfoByOpenID(openID);
	}

	@Override
	public InvigilationTeacher getTeacher(String loginname) {
		// TODO Auto-generated method stub
		return this.getiInvigilationTeacherDAO().getTeacher(loginname);
	}

	@Override
	public InvigilationTeacherOld getTeacherOldByIdcard(String idcard) {
		// TODO Auto-generated method stub
		return this.getiInvigilationTeacherOldDAO().getTeacherOldByIdcard(idcard);
	}

	@Override
	public InvigilationTeacher deleteById(InvigilationTeacher t) {
		iExamTeacherRoomDAO.deleteByTeacherId(t.getId());
		return this.getiInvigilationTeacherDAO().delete(t);
	}

	@SuppressWarnings("rawtypes")
	public void updateDisable(String tid, String exam, String reason, int disableType, int creator) {
		Date releaseTime = null;
		if (disableType == 2) {// 禁用半年
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 6);
			releaseTime = c.getTime();
		}

		String ids[] = tid.split(",");
		for (int i = 0; i < ids.length; i++) {
			InvigilationTeacher teacher = this.getById(Integer.parseInt(ids[i]));
			if (teacher != null) {
				teacher.setStatus((short) 0);
				teacher.setReason(reason);
				// 禁用方式
				teacher.setDisableType((short) disableType);
				// 解禁时间
				if (releaseTime != null) {
					teacher.setReleaseTime(new Timestamp(releaseTime.getTime()));
				}
				// 修改状态被禁用过
				teacher.setIsDisable((short) 1);
				this.update(teacher);

				if (!Utlity.checkStringNull(exam)) {
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("exam", exam);
					searchMap.put("teacher", Integer.parseInt(ids[i]));
					List list = this.getiExamTeacherRoomDAO().searchExamTeacherRoom(searchMap, null, -1, -1);
					if (list != null && !list.isEmpty()) {
						for (int j = 0; j < list.size(); j++) {
							Object o = list.get(j);
							Object[] obj = (Object[]) o;
							ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
							etr.setStatus((short) 2);
							etr.setDisableReason(reason);
							this.getiExamTeacherRoomDAO().update(etr);
							// 保存禁用记录
							TeacherDisableRecords teacherDisableRecords = new TeacherDisableRecords();
							teacherDisableRecords.setTeacher(teacher);
							teacherDisableRecords.setExam(etr.getExam());
							teacherDisableRecords.setReason(reason);
							teacherDisableRecords.setCreator(creator);
							teacherDisableRecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
							this.getTeacherDisableRecordsDao().save(teacherDisableRecords);
						}
					}
				}
			}
		}

		//
		//
		// String ids[] = id.split(",");
		// for (int i = 0; i < ids.length; i++) {
		// ExamTeacherRoom etr =
		// this.getiExamTeacherRoomDAO().get(Integer.parseInt(ids[i]));
		// if (etr != null) {
		// etr.setStatus((short) 2);
		// // etr.setReason(reason);
		// etr.setDisableReason(reason);
		// this.getiExamTeacherRoomDAO().update(etr);
		// InvigilationTeacher teacher = etr.getTeacher();
		// teacher.setStatus((short) 0);
		// teacher.setReason(reason);
		// // 禁用方式
		// teacher.setDisableType((short) disableType);
		// // 解禁时间
		// if (releaseTime != null) {
		// teacher.setReleaseTime(new Timestamp(releaseTime.getTime()));
		// }
		// // 修改状态被禁用过
		// teacher.setIsDisable((short) 1);
		// this.update(teacher);
		// // 保存禁用记录
		// TeacherDisableRecords teacherDisableRecords = new
		// TeacherDisableRecords();
		// teacherDisableRecords.setTeacher(teacher);
		// teacherDisableRecords.setExam(etr.getExam());
		// teacherDisableRecords.setReason(reason);
		// teacherDisableRecords.setCreator(creator);
		// teacherDisableRecords.setCreatetime(new
		// Timestamp(System.currentTimeMillis()));
		// this.getTeacherDisableRecordsDao().save(teacherDisableRecords);
		// }
		// }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateResume(int id, String exam) {
		InvigilationTeacher teacher = this.getById(id);
		if (teacher != null) {
			teacher.setStatus((short) 1);
			teacher.setReason(null);
			teacher.setReleaseTime(null);
			teacher.setDisableType((short) 1);// 修改禁用类型为正常
			this.update(teacher);
		}
		if (!Utlity.checkStringNull(exam)) {
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("exam", exam);
			searchMap.put("teacher", id);
			List list = this.getiExamTeacherRoomDAO().searchExamTeacherRoom(searchMap, null, -1, -1);
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					if (etr.getStatus() != 0) {// 如果监考记录是删除状态，则不更改其状态
						etr.setStatus((short) 1);
						etr.setDisableReason(null);
						this.getiExamTeacherRoomDAO().update(etr);
					}
				}
			}
		}
	}

	@Override
	public int updateStopInvigilationTeacher(Map<String, Object> searchMap) {
		return this.getiInvigilationTeacherDAO().updateStopInvigilationTeacher(searchMap);
	}

	@Override
	public int updateChangeStatus(String id, short status, String reason, SysUser currentUser) {
		//return this.getiInvigilationTeacherDAO().updateChangeStatus(ids, status, reason, currentUserId);
		String ids[] = id.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				InvigilationTeacher invigilationTeacher = getById(Integer.parseInt(ids[i]));
				if (invigilationTeacher != null) {
					invigilationTeacher.setCheckStatus((short) status);
					invigilationTeacher.setChecker(currentUser.getId());
					invigilationTeacher.setCheckReason(reason);
					invigilationTeacher.setChecktime(new Timestamp(System.currentTimeMillis()));
					update(invigilationTeacher);
					
					String token = CommonUtil.getAccessToken().getAccessToken();
					if (token != null) {
						if (status == 2 && invigilationTeacher.getOpenID() != null) {
							Map<String, String> templateParams = new HashMap<>();
							templateParams.put("first", "收到审核通知");
							templateParams.put("keyword1",
									Utlity.timeSpanToString5(invigilationTeacher.getChecktime()));
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
							templateParams.put("keyword1",
									Utlity.timeSpanToString5(invigilationTeacher.getChecktime()));
							templateParams.put("keyword2", "您的注册信息未通过审核，原因：" + reason);
							templateParams.put("keyword3", currentUser.getName());
							templateParams.put("remark", "请修改资料重新提交");
							templateParams.put("url", "");
							templateParams.put("touser", invigilationTeacher.getOpenID());
							ConfigUtil.sendTemplate(CommonUtil.getAccessToken().getAccessToken(),
									MessageUtil.teacherCheckTemplate(templateParams));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	

	@Override
	public void deleteBatch(String id) {
		// TODO Auto-generated method stub
		String ids[] = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Integer tid = Integer.parseInt(ids[i]);
			InvigilationTeacher teacher = this.getById(tid);
			this.deleteById(teacher);

			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("teacher", tid);
			@SuppressWarnings("rawtypes")
			List list = this.getiExamTeacherRoomDAO().searchExamTeacherRoom(searchMap, null, -1, -1);
			if (list != null && !list.isEmpty()) {
				for (int j = 0; j < list.size(); j++) {
					Object o = list.get(j);
					Object[] obj = (Object[]) o;
					ExamTeacherRoom etr = (ExamTeacherRoom) obj[0];
					if (etr != null) {
						etr.setStatus((short) 0);
						etr.setRoom(null);
						etr.setIsChief((short) 0);
						etr.setIsMixed((short) 0);
						etr.setIsConfirm((short) 0);
						etr.setConfirtime(null);
						etr.setCredit(null);
						this.getiExamTeacherRoomDAO().update(etr);
					}
				}
			}			
		}
	}
}
