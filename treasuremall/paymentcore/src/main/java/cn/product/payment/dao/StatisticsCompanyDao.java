package cn.product.payment.dao;

import java.util.List;
import java.util.Map;

import cn.product.payment.entity.StatisticsCompany;

public interface StatisticsCompanyDao extends IDao<StatisticsCompany>{
	
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
	public List<StatisticsCompany> getListByParams(Map<String, Object> params);
	
	/**
	 * 每日统计
	 * @param dailyDate
	 * @return
	 */
	public void statisticsDaily(String dailyDate);
	
	/**
	 * 获取平台统计数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getDailyStatisticsByParams(Map<String, Object> params);
	
	/**
	 * 获取商户统计数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getCompanyStatisticsByParams(Map<String, Object> params);
}
