package cn.product.treasuremall.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.RobotWinningRate;

public interface RobotWinningRateDao extends IDao<RobotWinningRate> {
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
	public List<RobotWinningRate> getListByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @param insert
	 * @param update
	 */
	public void batchWinningRate(List<RobotWinningRate> insert, List<RobotWinningRate> update);
	
	/**
	 * 批量更新状态
	 * @param params
	 */
	public void updateStatus(Map<String, Object> params);
	
	/**
	 * 根据商品price选择中奖率
	 * @param price
	 * @return
	 */
	public RobotWinningRate getByPirice(BigDecimal price);
}
