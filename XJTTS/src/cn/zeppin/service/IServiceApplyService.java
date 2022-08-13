package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ServiceApply;

public interface IServiceApplyService extends
		IBaseService<ServiceApply, Integer> {
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

	/**
	 * 添加数据
	 */
	public void addServiceApply(ServiceApply serviceApply);

	/**
	 * 修改数据 注：如果是删除则只修改状态“status”为“-1”
	 */
	public void updateServiceApply(ServiceApply serviceApply);
}
