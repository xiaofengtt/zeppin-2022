package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IExamTeacherRoomDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ExamTeacherRoom;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: ExamTeacherRoomDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamTeacherRoomDAO extends HibernateTemplateDAO<ExamTeacherRoom, Integer> implements IExamTeacherRoomDAO {

	@SuppressWarnings("rawtypes")
	@Override
	public List searchExamTeacherRoom(Map<String, Object> searchMap, Map<String, String> sortParams, int offset,
			int length) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from ExamTeacherRoom su, InvigilationTeacher t where 1=1 and su.teacher=t.id ");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("isApply") != null && !searchMap.get("isApply").equals("")) {
			hql.append(" and su.status<>0");
		}
		if (searchMap.get("exam") != null && !searchMap.get("exam").equals("")) {
			hql.append(" and su.exam=").append(searchMap.get("exam"));
		}
		if (searchMap.get("room") != null && !searchMap.get("room").equals("")) {
			hql.append(" and su.room=").append(searchMap.get("room"));
		}
		if (searchMap.get("teacher") != null && !searchMap.get("teacher").equals("")) {
			hql.append(" and su.teacher=").append(searchMap.get("teacher"));
		}
		if (searchMap.get("isConfirm") != null && !searchMap.get("isConfirm").equals("")) {
			hql.append(" and su.isConfirm=").append(searchMap.get("isConfirm"));
		}
		// if (searchMap.get("roominfo") != null &&
		// !searchMap.get("roominfo").equals("")){
		// hql.append(" and (er.roomIndex like
		// '%").append(searchMap.get("roominfo")).append("%' ");
		// hql.append(" or er.roomAddress like
		// '%").append(searchMap.get("roominfo")).append("%' ");
		// hql.append(" or er.examnationInformation like
		// '%").append(searchMap.get("roominfo")).append("%' )");
		// }
		if (searchMap.get("first") != null && !searchMap.get("first").equals("")) {
			hql.append(" and su.isFirstApply=").append(searchMap.get("first"));
		}
		if (searchMap.get("distribute") != null && !searchMap.get("distribute").equals("")) {
			if ("2".equals(searchMap.get("distribute"))) {
				hql.append(" and su.room is null");
			} else {
				hql.append(" and su.room is not null");
			}
		}
		if (searchMap.get("isChief") != null && !searchMap.get("isChief").equals("")) {
			hql.append(" and su.isChief=").append(searchMap.get("isChief"));
		}
		if (searchMap.get("statusNormal") != null && !searchMap.get("statusNormal").equals("")) {// 监考校区
			hql.append(" and su.status>0");
		}
		if (searchMap.get("isCredit") != null && !searchMap.get("isCredit").equals("")) {// 打分
			if ("1".equals(searchMap.get("isCredit"))) {
				hql.append(" and su.credit is not null");// 已打分
			} else {
				hql.append(" and su.credit is null");// 未打分
			}
		}
		// 待分配的老师和已分配roomId对应的考场的老师
		if (searchMap.get("roomId") != null && !searchMap.get("roomId").equals("")) {
			hql.append(" and (su.room is null").append(" or su.room.id=" + searchMap.get("roomId") + ")");
		}

		//考场分类
		if (searchMap.get("roomType") != null && !searchMap.get("roomType").equals("")) {
			hql.append(" and su.room.roomType='").append(searchMap.get("roomType")).append("'");
		}
		// if (searchMap.get("status") != null &&
		// !searchMap.get("status").equals("")){
		// hql.append(" and su.status=").append(searchMap.get("status"));
		// }
		// if (searchMap.get("confirm") != null &&
		// !searchMap.get("confirm").equals("")){
		// hql.append(" and
		// su.confirmstatus=").append(searchMap.get("confirm"));
		// }
		if (searchMap.get("teacherinfo") != null && !searchMap.get("teacherinfo").equals("")) {
			hql.append(" and (t.name like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or t.pinyin like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or t.idcard like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or t.mobile like '%").append(searchMap.get("teacherinfo")).append("%' )");
		}
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")) {
			hql.append(" and t.type=").append(searchMap.get("type"));
		}
		if (searchMap.get("teacherStatus") != null && !searchMap.get("teacherStatus").equals("")) {
			hql.append(" and t.status=").append(searchMap.get("teacherStatus"));
		}
		if (searchMap.get("isChiefExaminer") != null && !searchMap.get("isChiefExaminer").equals("")) {// 主考
			hql.append(" and t.isChiefExaminer=").append(searchMap.get("isChiefExaminer"));
		}
		if (searchMap.get("isMixedExaminer") != null && !searchMap.get("isMixedExaminer").equals("")) {// 混考
			hql.append(" and t.isMixedExaminer=").append(searchMap.get("isMixedExaminer"));
		}
		if (searchMap.get("invigilateType") != null && !searchMap.get("invigilateType").equals("")) {
			hql.append(" and t.invigilateType=").append(searchMap.get("invigilateType"));
		}
		if (searchMap.get("invigilateCampus") != null && !searchMap.get("invigilateCampus").equals("")) {
			hql.append(" and t.invigilateCampus=").append(searchMap.get("invigilateCampus"));
		}
		if (searchMap.get("startTime") != null && !searchMap.get("startTime").equals("")) {
			hql.append(" and su.startTime = '").append(searchMap.get("startTime")+" 00:00:00'");
		}
		if (searchMap.get("endTime") != null && !searchMap.get("endTime").equals("")) {
			hql.append(" and su.endTime = '").append(searchMap.get("endTime")+ " 00:00:00'");
		}
		if (searchMap.get("studyGrade") != null && !searchMap.get("studyGrade").equals("")) {
			if ("other".equals(searchMap.get("studyGrade"))) {
				int currentYear = Integer.parseInt(Utlity.getSysYear());
				for (int i = currentYear; i > currentYear - 4; i--) {
					hql.append(" and t.studyGrade !='").append(i + "级'");
				}
//				hql.append(" or t.studyGrade =''");
			} else {
				hql.append(" and t.studyGrade ='").append(searchMap.get("studyGrade") + "'");
			}
		}

		// 排序
		// if (sorts != null && sorts.length() > 0) {
		// String[] sortArray = sorts.split(",");
		// hql.append(" order by ");
		// String comma = "";
		// for (String sort : sortArray){
		// hql.append(comma);
		// hql.append("su.").append(sort);
		// comma = ",";
		// }
		//
		// }
		if (sortParams != null && sortParams.size() > 0) {
			hql.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				if (key.endsWith("0")) {
					String tkey = key.substring(0, key.length() - 1);
					hql.append("su." + tkey + " " + value + ",");
				} else {
					hql.append("t." + key + " " + value + ",");
				}
			}
			hql.delete(hql.length() - 1, hql.length());
		} else {
			hql.append(" order by su.createtime desc");
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

	@Override
	public int searchExamTeacherRoomCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from ExamTeacherRoom su, InvigilationTeacher t where 1=1 and su.teacher=t.id ");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("isApply") != null && !searchMap.get("isApply").equals("")) {
			hql.append(" and su.status<>0");
		}
		if (searchMap.get("exam") != null && !searchMap.get("exam").equals("")) {
			hql.append(" and su.exam=").append(searchMap.get("exam"));
		}
		if (searchMap.get("room") != null && !searchMap.get("room").equals("")) {
			hql.append(" and su.room=").append(searchMap.get("room"));
		}
		if (searchMap.get("teacher") != null && !searchMap.get("teacher").equals("")) {
			hql.append(" and su.teacher=").append(searchMap.get("teacher"));
		}
		if (searchMap.get("isConfirm") != null && !searchMap.get("isConfirm").equals("")) {
			hql.append(" and su.isConfirm=").append(searchMap.get("isConfirm"));
		}
		// if (searchMap.get("roominfo") != null &&
		// !searchMap.get("roominfo").equals("")){
		// hql.append(" and (er.roomIndex like
		// '%").append(searchMap.get("roominfo")).append("%' ");
		// hql.append(" or er.roomAddress like
		// '%").append(searchMap.get("roominfo")).append("%' ");
		// hql.append(" or er.examnationInformation like
		// '%").append(searchMap.get("roominfo")).append("%' )");
		// }
		if (searchMap.get("first") != null && !searchMap.get("first").equals("")) {
			hql.append(" and su.isFirstApply=").append(searchMap.get("first"));
		}
		if (searchMap.get("distribute") != null && !searchMap.get("distribute").equals("")) {
			if ("2".equals(searchMap.get("distribute"))) {
				hql.append(" and su.room is null");
			} else {
				hql.append(" and su.room is not null");
			}
		}
		if (searchMap.get("isChief") != null && !searchMap.get("isChief").equals("")) {
			hql.append(" and su.isChief=").append(searchMap.get("isChief"));
		}
		if (searchMap.get("statusNormal") != null && !searchMap.get("statusNormal").equals("")) {// 监考校区
			hql.append(" and su.status>0");
		}
		if (searchMap.get("isCredit") != null && !searchMap.get("isCredit").equals("")) {// 打分
			if ("1".equals(searchMap.get("isCredit"))) {
				hql.append(" and su.credit is not null");// 已打分
			} else {
				hql.append(" and su.credit is null");// 未打分
			}
		}
		// 待分配的老师和已分配roomId对应的考场的老师
		if (searchMap.get("roomId") != null && !searchMap.get("roomId").equals("")) {
			hql.append(" and (su.room is null").append(" or su.room.id=" + searchMap.get("roomId") + ")");
		}
		//考场分类
		if (searchMap.get("roomType") != null && !searchMap.get("roomType").equals("")) {
			hql.append(" and su.room.roomType=").append(searchMap.get("roomType"));
		}
		// if (searchMap.get("status") != null &&
		// !searchMap.get("status").equals("")){
		// hql.append(" and su.status=").append(searchMap.get("status"));
		// }
		// if (searchMap.get("confirm") != null &&
		// !searchMap.get("confirm").equals("")){
		// hql.append(" and
		// su.confirmstatus=").append(searchMap.get("confirm"));
		// }
		if (searchMap.get("teacherinfo") != null && !searchMap.get("teacherinfo").equals("")) {
			hql.append(" and (t.name like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or t.pinyin like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or t.idcard like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or t.mobile like '%").append(searchMap.get("teacherinfo")).append("%' )");
		}
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")) {
			hql.append(" and t.type=").append(searchMap.get("type"));
		}
		if (searchMap.get("teacherStatus") != null && !searchMap.get("teacherStatus").equals("")) {
			hql.append(" and t.status=").append(searchMap.get("teacherStatus"));
		}
		if (searchMap.get("isChiefExaminer") != null && !searchMap.get("isChiefExaminer").equals("")) {// 主考
			hql.append(" and t.isChiefExaminer=").append(searchMap.get("isChiefExaminer"));
		}
		if (searchMap.get("isMixedExaminer") != null && !searchMap.get("isMixedExaminer").equals("")) {// 混考
			hql.append(" and t.isMixedExaminer=").append(searchMap.get("isMixedExaminer"));
		}
		if (searchMap.get("invigilateType") != null && !searchMap.get("invigilateType").equals("")) {
			hql.append(" and t.invigilateType=").append(searchMap.get("invigilateType"));
		}
		if (searchMap.get("invigilateCampus") != null && !searchMap.get("invigilateCampus").equals("")) {
			hql.append(" and t.invigilateCampus=").append(searchMap.get("invigilateCampus"));
		}
		if (searchMap.get("startTime") != null && !searchMap.get("startTime").equals("")) {
			hql.append(" and su.startTime = '").append(searchMap.get("startTime")+" 00:00:00'");
		}
		if (searchMap.get("endTime") != null && !searchMap.get("endTime").equals("")) {
			hql.append(" and su.endTime = '").append(searchMap.get("endTime")+ " 00:00:00'");
		}
		if (searchMap.get("studyGrade") != null && !searchMap.get("studyGrade").equals("")) {
			if ("other".equals(searchMap.get("studyGrade"))) {
				int currentYear = Integer.parseInt(Utlity.getSysYear());
				for (int i = currentYear; i > currentYear - 4; i--) {
					hql.append(" and t.studyGrade !='").append(i + "级'");
				}
//				hql.append(" or t.studyGrade =''");
			} else {
				hql.append(" and t.studyGrade ='").append(searchMap.get("studyGrade") + "'");
			}
		}
		
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List searchUndistributionTeacher(Map<String, Object> searchMap, Map<String, String> sortParams, int offset,
			int length) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from ExamTeacherRoom su, InvigilationTeacher t where 1=1 and t.id = su.teacher");
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("room") != null && !searchMap.get("room").equals("")) {
			hql.append(" and su.room=").append(searchMap.get("room"));
		}
		if (searchMap.get("exam") != null && !searchMap.get("exam").equals("")) {
			hql.append(" and su.exam=").append(searchMap.get("exam"));
		}
		if (searchMap.get("isConfirm") != null && !searchMap.get("isConfirm").equals("")) {
			hql.append(" and su.isConfirm=1");
		}
		if (searchMap.get("distribute") != null && !searchMap.get("distribute").equals("")) {
			if ("2".equals(searchMap.get("distribute"))) {
				hql.append(" and su.room is null");
			} else {
				hql.append(" and su.room is not null");
			}
		}
		if (sortParams != null && sortParams.size() > 0) {
			hql.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				if (key.endsWith("0")) {
					String tkey = key.substring(0, key.length() - 1);
					hql.append("su." + tkey + " " + value + ",");
				} else {
					hql.append("t." + key + " " + value + ",");
				}
			}
			hql.delete(hql.length() - 1, hql.length());
		} else {
			hql.append(" order by t.createtime desc");
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

	@Override
	public List<ExamTeacherRoom> getByParams(Map<String, Object> searchMap) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from ExamTeacherRoom su where 1=1");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("exam") != null && !searchMap.get("exam").equals("")) {
			hql.append(" and su.exam=").append(searchMap.get("exam"));
		}
		if (searchMap.get("teacher") != null && !searchMap.get("teacher").equals("")) {
			hql.append(" and su.teacher=").append(searchMap.get("teacher"));
		}
		// 得分
		if (searchMap.get("credit") != null && !searchMap.get("credit").equals("")) {// 得分
			hql.append(" and su.credit=").append(searchMap.get("credit"));
		}
		if (searchMap.get("isCredit") != null && !searchMap.get("isCredit").equals("")) {// 打分
			if ("1".equals(searchMap.get("isCredit"))) {
				hql.append(" and su.credit is not null");// 已打分
			} else {
				hql.append(" and su.credit is null");// 未打分
			}
		}
		hql.append(" order by su.createtime desc");
		return this.getByHQL(hql.toString(), -1, -1);
	}

	@Override
	public int deleteByTeacherId(int tid) {
		String hql = "delete from ExamTeacherRoom where teacher=" + tid;
		return this.executeHQL(hql);
	}
}