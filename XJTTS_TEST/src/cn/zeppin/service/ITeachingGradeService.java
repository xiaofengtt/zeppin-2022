package cn.zeppin.service;

import cn.zeppin.entity.TeachingGrade;

public interface ITeachingGradeService extends IBaseService<TeachingGrade, Integer> {
	public void deleteTeachingGradeByTeacher(int teacher);
}
