package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserCheckinHistory;

public interface FrontUserCheckinHistoryDao extends IDao<FrontUserCheckinHistory>{
	
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
	public List<FrontUserCheckinHistory> getListByParams(Map<String, Object> params);
	
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
	public List<FrontUserCheckinHistory> getGroupListByParams(Map<String, Object> params);
	
	/**
	 * 用户签到
	 * @param params
	 */
	public void checkin(Map<String, Object> params);
	
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
	public List<FrontUserCheckinHistory> getLeftListByParams(Map<String, Object> params);
	
	/**
	 * 用户兑奖
	 * @param params
	 */
	public void userReceive(FrontUserCheckinHistory frontUserCheckinHistory, Map<String, Object> params);
	
}
