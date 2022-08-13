package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherCertificate;

public interface ITeacherCertificateDao extends IBaseDao<TeacherCertificate, Integer> {

	/**
	 * 根据条件获取列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<TeacherCertificate> getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取记录数
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
}
