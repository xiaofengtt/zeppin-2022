package cn.zeppin.dao;

import cn.zeppin.entity.TeachingGrade;

public interface ITeachingGradeDao extends IBaseDao<TeachingGrade, Integer> {

	public void deleteTeachingGradeByTeacher(int teacher);
}
