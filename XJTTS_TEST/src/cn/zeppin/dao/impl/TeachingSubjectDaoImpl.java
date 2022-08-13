package cn.zeppin.dao.impl;

import cn.zeppin.dao.ITeachingSubjectDao;
import cn.zeppin.entity.TeachingSubject;

public class TeachingSubjectDaoImpl extends BaseDaoImpl<TeachingSubject, Integer> implements ITeachingSubjectDao{

	@Override
	public void deleteTeachingSubjectByTeacher(int teacher) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("delete from TeachingSubject ts where 1=1");
		sb.append(" and ts.teacher="+teacher);
		this.executeHSQL(sb.toString());
	}

}
