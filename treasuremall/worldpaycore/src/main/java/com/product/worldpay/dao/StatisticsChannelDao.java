package com.product.worldpay.dao;

import java.util.List;
import java.util.Map;

import com.product.worldpay.entity.StatisticsChannel;

public interface StatisticsChannelDao extends IDao<StatisticsChannel>{
	
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
	public List<StatisticsChannel> getListByParams(Map<String, Object> params);
	
	/**
	 * 每日统计
	 * @param dailyDate
	 * @return
	 */
	void statisticsDaily(String dailyDate);
}
