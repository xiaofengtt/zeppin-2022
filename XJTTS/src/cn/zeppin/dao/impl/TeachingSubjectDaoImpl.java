package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeachingSubjectDao;
import cn.zeppin.entity.TeachingSubject;

public class TeachingSubjectDaoImpl extends
		BaseDaoImpl<TeachingSubject, Integer> implements ITeachingSubjectDao {

	@Override
	public void deleteTeachingSubjectByTeacher(int teacher) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("delete from TeachingSubject ts where 1=1");
		sb.append(" and ts.teacher=" + teacher);
		this.executeHSQL(sb.toString());
	}

	@Override
	public List<TeachingSubject> getListByParams(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("from TeachingSubject where 1=1");
		if (map.get("subject") != null && !map.get("subject").equals("")) {
			sb.append(" and subject = '").append(map.get("subject"))
					.append("'");
		}
		if (map.get("id") != null && !map.get("id").equals("")) {
			sb.append(" and id = '").append(map.get("id")).append("'");
		}
		return this.getListByHSQL(sb.toString());
	}

}
