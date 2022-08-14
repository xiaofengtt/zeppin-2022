package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserPaymentOrder;

public interface FrontUserPaymentOrderDao extends IDao<FrontUserPaymentOrder>{
	
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
	public List<FrontUserPaymentOrder> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getGroupCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUserPaymentOrder> getGroupListByParams(Map<String, Object> params);
	
	/**
	 * 用户下单
	 * @param params
	 */
	public void userBet(Map<String, Object> params);
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<FrontUserPaymentOrder> getLeftListByParams(Map<String, Object> params);
	
	/**
	 * 获取奖品投注统计
	 * @param params
	 * @return
	 */
	public Integer getPaymentStatisticsCount(Map<String, Object> params);
	
	/**
	 * 获取奖品投注统计
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getPaymentStatistics(Map<String, Object> params);
}
