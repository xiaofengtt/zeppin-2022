package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ServiceApply;

public interface IServiceApplyDao extends IBaseDao<ServiceApply, Integer> {
	/**
	 * 根据条件，查询所有对应记录总数
	 * 
	 * @param params
	 * @return
	 */
	int getCountByParams(Map<String, Object> params);

	/**
	 * 根据条件，查询所有对应的记录List
	 * 
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	List<ServiceApply> getListByParams(Map<String, Object> params, int offset,
			int length);

}
