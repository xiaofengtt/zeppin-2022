package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.CapitalAccountStatistics;

public interface CapitalAccountStatisticsDao extends IDao<CapitalAccountStatistics>{
	
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
	public List<CapitalAccountStatistics> getListByParams(Map<String, Object> params);
	
	/**
	 * 清空日累计量
	 */
	public void dailyRefrush();
}
