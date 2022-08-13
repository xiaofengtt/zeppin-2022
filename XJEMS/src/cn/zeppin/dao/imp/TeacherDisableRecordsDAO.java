package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITeacherDisableRecordsDao;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.TeacherDisableRecords;

public class TeacherDisableRecordsDAO extends HibernateTemplateDAO<TeacherDisableRecords, Short>
		implements ITeacherDisableRecordsDao {

	@Override
	public List<TeacherDisableRecords> searchTeacherDisableRecords(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from TeacherDisableRecords r where 1=1");
		if (searchMap.get("exam") != null && !searchMap.get("exam").equals("")) {
			hql.append(" and r.exam=").append(searchMap.get("exam"));
		}
		if (searchMap.get("teacher") != null && !searchMap.get("teacher").equals("")) {
			hql.append(" and r.teacher=").append(searchMap.get("teacher"));
		}
		if (sortParams != null && sortParams.size() > 0) {
			hql.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				String tkey = key.substring(0, key.length() - 1);
				hql.append("r." + tkey + " " + value + ",");
			}
			hql.delete(hql.length() - 1, hql.length());
		} else {
			hql.append(" order by r.createtime desc");
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

}
