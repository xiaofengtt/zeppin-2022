package cn.zeppin.dao.impl;

import cn.zeppin.dao.ITeachingGradeDao;
import cn.zeppin.entity.TeachingGrade;

public class TeachingGradeDaoImpl extends BaseDaoImpl<TeachingGrade, Integer> implements ITeachingGradeDao{

	@Override
	public void deleteTeachingGradeByTeacher(int teacher) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("delete from TeachingGrade tg where 1=1");
		sb.append(" and tg.teacher="+teacher);
		this.executeHSQL(sb.toString());
	}

}
