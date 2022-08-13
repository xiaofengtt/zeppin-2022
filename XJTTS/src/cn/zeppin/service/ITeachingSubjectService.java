package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeachingSubject;

public interface ITeachingSubjectService extends IBaseService<TeachingSubject, Integer> {

	public void deleteTeachingSubjectByTeacher(int teacher);
	/**
	 * 9月12日加：通过参数获取TeachingSubject表里的记录
	 * 
	 * @param map
	 * @return
	 */
	public List<TeachingSubject> getListByParams(Map<String, Object> map);
}
