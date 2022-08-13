package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeachingGrade;

public interface ITeachingGradeService extends
		IBaseService<TeachingGrade, Integer> {
	public void deleteTeachingGradeByTeacher(int teacher);

	/**
	 * 9月12日加：通过参数获取TeachingGrade表里的记录
	 * 
	 * @param map
	 * @return
	 */
	public List<TeachingGrade> getListByParams(Map<String, Object> map);
}
