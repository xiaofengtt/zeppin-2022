package cn.zeppin.service;

import cn.zeppin.entity.TeachingSubject;

public interface ITeachingSubjectService extends IBaseService<TeachingSubject, Integer> {

	public void deleteTeachingSubjectByTeacher(int teacher);
}
