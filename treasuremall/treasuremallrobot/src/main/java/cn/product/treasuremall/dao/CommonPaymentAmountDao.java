package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.CommonPaymentAmount;

public interface CommonPaymentAmountDao extends IDao<CommonPaymentAmount> {
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<CommonPaymentAmount> getListByParams(Map<String, Object> params);
}
