package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.InternationalRateHistory;

public interface InternationalRateHistoryDao extends IDao<InternationalRateHistory> {
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
	public List<InternationalRateHistory> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<InternationalRateHistory> getLessInfoListByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @param dailyDate
	 * @return
	 */
	public InternationalRateHistory getByDailyDate(String dailyDate);
	
	/**
	 * 
	 * @param params
	 */
	public void dailyUpdate(Map<String, Object> params);
	
}
