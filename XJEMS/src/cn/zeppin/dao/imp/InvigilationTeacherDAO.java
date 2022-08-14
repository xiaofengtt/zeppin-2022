package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IInvigilationTeacherDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.InvigilationTeacher;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: InvigilationTeacherDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class InvigilationTeacherDAO extends HibernateTemplateDAO<InvigilationTeacher, Integer>
		implements IInvigilationTeacherDAO {

	@Override
	public List<InvigilationTeacher> searchInvigilationTeacher(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from InvigilationTeacher su where 1=1 ");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("openID") != null && !searchMap.get("openID").equals("")) {
			hql.append(" and su.openID=").append(searchMap.get("openID"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
			hql.append(" and su.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("pinyin") != null && !searchMap.get("pinyin").equals("")) {
			hql.append(" and su.pinyin like '%").append(searchMap.get("pinyin")).append("%' ");
		}
		if (searchMap.get("idcard") != null && !searchMap.get("idcard").equals("")) {
			hql.append(" and su.idcard like '%").append(searchMap.get("idcard")).append("%' ");
		}
		if (searchMap.get("mobile") != null && !searchMap.get("mobile").equals("")) {
			hql.append(" and su.mobile=").append(searchMap.get("mobile"));
		}
		if (searchMap.get("teacherinfo") != null && !searchMap.get("teacherinfo").equals("")) {
			hql.append(" and (su.name like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.pinyin like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.idcard like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.mobile like '%").append(searchMap.get("teacherinfo")).append("%' )");
		}
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")) {
			hql.append(" and su.type=").append(searchMap.get("type"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		} else {
			hql.append(" and su.status<>-1");
		}
		if (searchMap.get("isChiefExaminer") != null && !searchMap.get("isChiefExaminer").equals("")) {// 主考
			hql.append(" and su.isChiefExaminer=").append(searchMap.get("isChiefExaminer"));
		}
		if (searchMap.get("isMixedExaminer") != null && !searchMap.get("isMixedExaminer").equals("")) {// 混考
			hql.append(" and su.isMixedExaminer=").append(searchMap.get("isMixedExaminer"));
		}
		if (searchMap.get("checkStatus") != null && !searchMap.get("checkStatus").equals("")) {
			hql.append(" and su.checkStatus=").append(searchMap.get("checkStatus"));
		}
		if (searchMap.get("invigilateCampus") != null && !searchMap.get("invigilateCampus").equals("")) {
			hql.append(" and su.invigilateCampus=").append(searchMap.get("invigilateCampus"));
		}
		// 是否自己注册 ，目前用户只能通过微信号自己注册，其他情况由管理员添加
		if (searchMap.get("isRegister") != null && !searchMap.get("isRegister").equals("")) {
			hql.append(" and su.openID is not null");
		}
		if (searchMap.get("studyLength") != null && !searchMap.get("studyLength").equals("")) {
			hql.append(" and su.studyLength =").append(searchMap.get("studyLength"));
		}
		if (searchMap.get("studyGrade") != null && !searchMap.get("studyGrade").equals("")) {
			if ("other".equals(searchMap.get("studyGrade"))) {
				int currentYear = Integer.parseInt(Utlity.getSysYear());
				for (int i = currentYear; i > currentYear - 4; i--) {
					hql.append(" and su.studyGrade !='").append(i + "级'");
				}
				hql.append(" or su.studyGrade =''");
			} else {
				hql.append(" and su.studyGrade ='").append(searchMap.get("studyGrade") + "'");
			}
		}
		// 头像：-1全部；0未上传头像；1上传了头像
		if (searchMap.get("photo") != null && !searchMap.get("photo").equals("")) {
			int photo = (int) searchMap.get("photo");
			if (photo == 0) {
				hql.append(" and su.photo.id = 1");
			} else if (photo == 1) {
				hql.append(" and su.photo.id != 1");
			}
		}
		// 排序
		// if (sorts != null && sorts.length() > 0) {
		// String[] sortArray = sorts.split(",");
		// hql.append(" order by ");
		// String comma = "";
		// for (String sort : sortArray) {
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
				hql.append("su." + key + " " + value + ",");
			}
			hql.delete(hql.length() - 1, hql.length());
		} else {
			hql.append(" order by su.createtime desc");
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

	@Override
	public List<Object[]> searchInvigilationTeacherBySql(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append("select su.* from invigilation_teacher su where 1=1 ");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("openID") != null && !searchMap.get("openID").equals("")) {
			hql.append(" and su.openID=").append(searchMap.get("openID"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
			hql.append(" and su.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("pinyin") != null && !searchMap.get("pinyin").equals("")) {
			hql.append(" and su.pinyin like '%").append(searchMap.get("pinyin")).append("%' ");
		}
		if (searchMap.get("idcard") != null && !searchMap.get("idcard").equals("")) {
			hql.append(" and su.idcard like '%").append(searchMap.get("idcard")).append("%' ");
		}
		if (searchMap.get("mobile") != null && !searchMap.get("mobile").equals("")) {
			hql.append(" and su.mobile=").append(searchMap.get("mobile"));
		}
		if (searchMap.get("teacherinfo") != null && !searchMap.get("teacherinfo").equals("")) {
			hql.append(" and (su.name like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.pinyin like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.idcard like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.mobile like '%").append(searchMap.get("teacherinfo")).append("%' )");
		}
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")) {
			hql.append(" and su.type=").append(searchMap.get("type"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		} else {
			hql.append(" and su.status<>-1");
		}
		if (searchMap.get("isChiefExaminer") != null && !searchMap.get("isChiefExaminer").equals("")) {// 主考
			hql.append(" and su.is_Chief_Examiner=").append(searchMap.get("isChiefExaminer"));
		}
		if (searchMap.get("isMixedExaminer") != null && !searchMap.get("isMixedExaminer").equals("")) {// 混考
			hql.append(" and su.is_Mixed_Examiner=").append(searchMap.get("isMixedExaminer"));
		}
		if (searchMap.get("checkStatus") != null && !searchMap.get("checkStatus").equals("")) {
			hql.append(" and su.check_Status=").append(searchMap.get("checkStatus"));
		}
		if (searchMap.get("invigilateCampus") != null && !searchMap.get("invigilateCampus").equals("")) {
			hql.append(" and su.invigilate_Campus=").append(searchMap.get("invigilateCampus"));
		}
		// 是否自己注册 ，目前用户只能通过微信号自己注册，其他情况由管理员添加
		if (searchMap.get("isRegister") != null && !searchMap.get("isRegister").equals("")) {
			hql.append(" and su.openID is not null");
		}
		if (searchMap.get("studyLength") != null && !searchMap.get("studyLength").equals("")) {
			hql.append(" and su.study_Length =").append(searchMap.get("studyLength"));
		}
		if (searchMap.get("studyGrade") != null && !searchMap.get("studyGrade").equals("")) {
			if ("other".equals(searchMap.get("studyGrade"))) {
				int currentYear = Integer.parseInt(Utlity.getSysYear());
				for (int i = currentYear; i > currentYear - 4; i--) {
					hql.append(" and su.study_Grade !='").append(i + "级'");
				}
				hql.append(" or su.study_Grade =''");
			} else {
				hql.append(" and su.study_Grade ='").append(searchMap.get("studyGrade") + "'");
			}
		}
		// 头像：-1全部；0未上传头像；1上传了头像
		if (searchMap.get("photo") != null && !searchMap.get("photo").equals("")) {
			int photo = (int) searchMap.get("photo");
			if (photo == 0) {
				hql.append(" and su.photo.id = 1");
			} else if (photo == 1) {
				hql.append(" and su.photo.id != 1");
			}
		}
		
		if (sortParams != null && sortParams.size() > 0) {
			hql.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				hql.append("su." + key + " " + value + ",");
			}
			hql.delete(hql.length() - 1, hql.length());
		} else {
			hql.append(" order by su.createtime desc");
		}
		return this.getBySQL(hql.toString(), offset, length);
	}
	
	@Override
	public int searchInvigilationTeacherCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from InvigilationTeacher su where 1=1 ");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
			hql.append(" and su.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("pinyin") != null && !searchMap.get("pinyin").equals("")) {
			hql.append(" and su.pinyin like '%").append(searchMap.get("pinyin")).append("%' ");
		}
		if (searchMap.get("idcard") != null && !searchMap.get("idcard").equals("")) {
			hql.append(" and su.idcard like '%").append(searchMap.get("idcard")).append("%' ");
		}
		if (searchMap.get("mobile") != null && !searchMap.get("mobile").equals("")) {
			hql.append(" and su.mobile=").append(searchMap.get("mobile"));
		}
		if (searchMap.get("teacherinfo") != null && !searchMap.get("teacherinfo").equals("")) {
			hql.append(" and (su.name like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.pinyin like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.idcard like '%").append(searchMap.get("teacherinfo")).append("%' ");
			hql.append(" or su.mobile like '%").append(searchMap.get("teacherinfo")).append("%' )");
		}
		if (searchMap.get("type") != null && !searchMap.get("type").equals("")) {
			hql.append(" and su.type=").append(searchMap.get("type"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("isChiefExaminer") != null && !searchMap.get("isChiefExaminer").equals("")) {// 主考
			hql.append(" and su.isChiefExaminer=").append(searchMap.get("isChiefExaminer"));
		}
		if (searchMap.get("isMixedExaminer") != null && !searchMap.get("isMixedExaminer").equals("")) {// 混考
			hql.append(" and su.isMixedExaminer=").append(searchMap.get("isMixedExaminer"));
		}
		if (searchMap.get("checkStatus") != null && !searchMap.get("checkStatus").equals("")) {
			hql.append(" and su.checkStatus=").append(searchMap.get("checkStatus"));
		}
		if (searchMap.get("invigilateCampus") != null && !searchMap.get("invigilateCampus").equals("")) {
			hql.append(" and su.invigilateCampus=").append(searchMap.get("invigilateCampus"));
		}
		if (searchMap.get("bankCard") != null && !searchMap.get("bankCard").equals("")) {
			hql.append(" and su.bankCard=").append(searchMap.get("bankCard"));
		}
		if (searchMap.get("studyLength") != null && !searchMap.get("studyLength").equals("")) {
			hql.append(" and su.studyLength =").append(searchMap.get("studyLength"));
		}
		if (searchMap.get("studyGrade") != null && !searchMap.get("studyGrade").equals("")) {
			if ("other".equals(searchMap.get("studyGrade"))) {
				int currentYear = Integer.parseInt(Utlity.getSysYear());
				for (int i = currentYear; i > currentYear - 4; i--) {
					hql.append(" and su.studyGrade !='").append(i + "级'");
				}
				hql.append(" or su.studyGrade =''");
			} else {
				hql.append(" and su.studyGrade ='").append(searchMap.get("studyGrade") + "'");
			}
		}
		//头像：-1全部；0未上传头像；1上传了头像
		if (searchMap.get("photo") != null && !searchMap.get("photo").equals("")) {
			int photo = (int) searchMap.get("photo");
			if (photo == 0) {
				hql.append(" and su.photo.id = 1");
			} else if (photo == 1) {
				hql.append(" and su.photo.id != 1");
			}
		}
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}

	@Override
	public InvigilationTeacher getInvigilationTeacherInfoByOpenID(String openID) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from InvigilationTeacher su where 1=1 ");
		hql.append(" and su.openID='");
		hql.append(openID + "'");

		return (InvigilationTeacher) this.getResultByHQL(hql.toString());
	}

	@Override
	public InvigilationTeacher getTeacher(String loginname) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from InvigilationTeacher su where 1=1 ");
		hql.append(" and (su.idcard='").append(loginname).append("'");
		hql.append(" or su.mobile='").append(loginname).append("')");

		List<InvigilationTeacher> userlist = this.getByHQL(hql.toString());
		if (userlist != null && userlist.size() > 0) {
			return userlist.get(0);
		}
		return null;
	}

	@Override
	public int updateStopInvigilationTeacher(Map<String, Object> searchMap) {
		StringBuilder hql = new StringBuilder();
		hql.append("update  InvigilationTeacher set status = 0 where 1=1");
		if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
			hql.append(" and type=").append(searchMap.get("type"));
		}
		if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
			hql.append(" and status=").append(searchMap.get("status"));
		}
		if (searchMap.get("isChiefExaminer") != null && !"".equals(searchMap.get("isChiefExaminer"))) {
			hql.append(" and isChiefExaminer=").append(searchMap.get("isChiefExaminer"));
		}
		if (searchMap.get("isMixed") != null && !"".equals(searchMap.get("isMixed"))) {
			hql.append(" and isMixed=").append(searchMap.get("isMixed"));
		}
		if (searchMap.get("studyLength") != null && !"".equals(searchMap.get("studyLength"))) {
			hql.append(" and studyLength=").append(searchMap.get("studyLength"));
		}
		if (searchMap.get("studyGrade") != null && !"".equals(searchMap.get("studyGrade"))) {
			hql.append(" and studyGrade='").append(searchMap.get("studyGrade") + "'");
		}
		System.out.println("-=-=-==" + hql.toString());
		return this.executeHQL(hql.toString());
	}
}
