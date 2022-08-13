package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.IdentifyStudyhour;

public interface IIdentifyStudyhourService extends
		IBaseService<IdentifyStudyhour, Integer> {
	/**
	 * 根据条件获取列表
	 * @param params
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	List<IdentifyStudyhour> getListByParams(Map<String, Object> params, int start, int limit, String sort);
	
	/**
	 * 根据条件获取总数
	 * @param params
	 * @return
	 */
	int getListCountByParams(Map<String, Object> params);
}
