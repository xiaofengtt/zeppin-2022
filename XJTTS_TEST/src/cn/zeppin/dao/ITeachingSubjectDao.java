package cn.zeppin.dao;

import cn.zeppin.entity.TeachingSubject;

public interface ITeachingSubjectDao extends IBaseDao<TeachingSubject, Integer> {

	public void deleteTeachingSubjectByTeacher(int teacher);
}
