package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.RobotSetting;

public interface RobotSettingDao extends IDao<RobotSetting> {
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
	public List<RobotSetting> getListByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @param insert
	 * @param update
	 */
	public void batchSetting(List<RobotSetting> insert, List<RobotSetting> update);
	
	/**
	 * 批量更新状态
	 * @param params
	 */
	public void updateStatus(Map<String, Object> params);
	
	/**
	 * 机器人工作准备，进入ready队列
	 * 需要查询robotsetting 确定expire时间
	 * 确认frontUser状态
	 * 判断工作时间是否在时间内
	 * @param frontUser
	 */
	public void robotWorkReady(String[] frontUserArr);
	
	/**
	 * 批量停止机器人工作
	 * @param frontUserArr
	 */
	public void robotWorkStop(String[] frontUserArr);
}
