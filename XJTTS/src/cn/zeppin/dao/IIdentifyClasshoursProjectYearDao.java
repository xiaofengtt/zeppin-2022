package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.IdentifyClasshoursProjectYear;

public interface IIdentifyClasshoursProjectYearDao extends IBaseDao<IdentifyClasshoursProjectYear, Integer> {

	/**
	 * 根据条件获取列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<IdentifyClasshoursProjectYear> getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取数目
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
}
