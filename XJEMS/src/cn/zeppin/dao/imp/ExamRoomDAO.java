package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IExamRoomDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ExamRoom;

/**
 * ClassName: ExamRoomDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 */
public class ExamRoomDAO extends HibernateTemplateDAO<ExamRoom, Integer> implements IExamRoomDAO {

	@Override
	public List<ExamRoom> searchExamRoom(Map<String, Object> searchMap,Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from ExamRoom su where 1=1 ");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		} else {
			hql.append(" and su.status<>0");
		}
		if (searchMap.get("exam") != null && !searchMap.get("exam").equals("")) {
			hql.append(" and su.exam=").append(searchMap.get("exam"));
		}
		if (searchMap.get("room") != null && !searchMap.get("room").equals("")) {
			hql.append(" and (su.roomIndex like '%").append(searchMap.get("room")).append("%' ");
			hql.append(" or su.roomAddress like '%").append(searchMap.get("room")).append("%' ");
			hql.append(" or su.examnationInformation like '%").append(searchMap.get("room")).append("%' )");
		}
		if (searchMap.get("roomIndex") != null && !searchMap.get("roomIndex").equals("")) {
			hql.append(" and su.roomIndex like '%").append(searchMap.get("roomIndex")).append("%' ");
		}
		if (searchMap.get("roomAddress") != null && !searchMap.get("roomAddress").equals("")) {
			hql.append(" and su.roomAddress like '%").append(searchMap.get("roomAddress")).append("%' ");
		}
		if (searchMap.get("examnationInformation") != null && !searchMap.get("examnationInformation").equals("")) {
			hql.append(" and su.examnationInformation like '%").append(searchMap.get("examnationInformation"))
					.append("%' ");
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
		} else {
			hql.append(" order by su.id asc");
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

	@Override
	public int searchExamRoomCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from ExamRoom su where 1=1 ");
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			hql.append(" and su.status=").append(searchMap.get("status"));
		}else {
			hql.append(" and su.status<>0");
		}
		if (searchMap.get("exam") != null && !searchMap.get("exam").equals("")) {
			hql.append(" and su.exam=").append(searchMap.get("exam"));
		}
		if (searchMap.get("room") != null && !searchMap.get("room").equals("")) {
			hql.append(" and (su.roomIndex like '%").append(searchMap.get("room")).append("%' ");
			hql.append(" or su.roomAddress like '%").append(searchMap.get("room")).append("%' ");
			hql.append(" or su.examnationInformation like '%").append(searchMap.get("room")).append("%' )");
		}
		if (searchMap.get("roomIndex") != null && !searchMap.get("roomIndex").equals("")) {
			hql.append(" and su.roomIndex like '%").append(searchMap.get("roomIndex")).append("%' ");
		}
		if (searchMap.get("roomAddress") != null && !searchMap.get("roomAddress").equals("")) {
			hql.append(" and su.roomAddress like '%").append(searchMap.get("roomAddress")).append("%' ");
		}
		if (searchMap.get("examnationInformation") != null && !searchMap.get("examnationInformation").equals("")) {
			hql.append(" and su.examnationInformation like '%").append(searchMap.get("examnationInformation"))
					.append("%' ");
		}
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}

}
