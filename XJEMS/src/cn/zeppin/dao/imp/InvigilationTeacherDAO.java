package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IInvigilationTeacherDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.InvigilationTeacher;

/**
 * ClassName: InvigilationTeacherDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class InvigilationTeacherDAO extends HibernateTemplateDAO<InvigilationTeacher, Integer>
		implements IInvigilationTeacherDAO {

	@Override
	public List<InvigilationTeacher> searchInvigilationTeacher(Map<String, Object> searchMap, Map<String, String> sortParams, int offset,
			int length) {
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
		}else {
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
		//是否自己注册 ，目前用户只能通过微信号自己注册，其他情况由管理员添加
		if (searchMap.get("isRegister") != null && !searchMap.get("isRegister").equals("")) {
		    hql.append(" and su.openID is not null");
		}
		// 排序
//		if (sorts != null && sorts.length() > 0) {
//			String[] sortArray = sorts.split(",");
//			hql.append(" order by ");
//			String comma = "";
//			for (String sort : sortArray) {
//				hql.append(comma);
//				hql.append("su.").append(sort);
//				comma = ",";
//			}
//
//		}
		if (sortParams != null && sortParams.size() > 0) {
			hql.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				hql.append("su." + key + " " + value + ",");
			}
			hql.delete(hql.length() - 1, hql.length());
		}else {
			hql.append(" order by su.createtime desc");
		} 
		return this.getByHQL(hql.toString(), offset, length);
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
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}

	@Override
	public InvigilationTeacher getInvigilationTeacherInfoByOpenID(String openID) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from InvigilationTeacher su where 1=1 ");
		hql.append(" and su.openID='");
		hql.append(openID+"'");
		
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
		if (userlist!=null && userlist.size()>0){
			return userlist.get(0);
		}
		return null;
	}
}
