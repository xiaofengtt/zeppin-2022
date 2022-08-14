package cn.product.treasuremall.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.WinningInfo;

public interface WinningInfoDao extends IDao<WinningInfo>{
	
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
	public List<WinningInfo> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByRobotParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<WinningInfo> getListByRobotParams(Map<String, Object> params);
	
	/**
	 * 根据日期取中奖总额
	 * @param dateStr
	 * @return
	 */
	public BigDecimal getWinningByDate(String dateStr);
	
	/**
	 * 根据日期取实物领奖总额
	 * @param dateStr
	 * @return
	 */
	public BigDecimal getDeliveryByDate(String dateStr);
}
