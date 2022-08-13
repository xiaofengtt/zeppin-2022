package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherEduAdvance;

public interface ITeacherEduAdvanceDao extends IBaseDao<TeacherEduAdvance, Integer> {

	/**
	 * 根据条件获取列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<TeacherEduAdvance> getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取记录数
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
}
