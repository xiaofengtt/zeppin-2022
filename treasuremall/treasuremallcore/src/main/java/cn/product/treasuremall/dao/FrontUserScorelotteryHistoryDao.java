package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserScorelotteryHistory;

public interface FrontUserScorelotteryHistoryDao extends IDao<FrontUserScorelotteryHistory>{
	
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
	public List<FrontUserScorelotteryHistory> getListByParams(Map<String, Object> params);
	
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
	public List<FrontUserScorelotteryHistory> getGroupListByParams(Map<String, Object> params);
	
	/**
	 * 用户签到
	 * @param params
	 */
	public void scorelottery(Map<String, Object> params);
	
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
	public List<FrontUserScorelotteryHistory> getLeftListByParams(Map<String, Object> params);
	
	/**
	 * 用户兑奖
	 * @param params
	 */
	public void userReceive(FrontUserScorelotteryHistory frontUserScorelotteryHistory, Map<String, Object> params);
	
}
