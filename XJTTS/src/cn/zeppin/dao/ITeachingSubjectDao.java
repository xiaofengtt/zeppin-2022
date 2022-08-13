package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeachingSubject;

public interface ITeachingSubjectDao extends IBaseDao<TeachingSubject, Integer> {

	public void deleteTeachingSubjectByTeacher(int teacher);

	/**
	 * 9月12日加：通过参数获取TeachingSubject表里的记录
	 * 
	 * @param map
	 * @return
	 */
	public List<TeachingSubject> getListByParams(Map<String, Object> map);
}