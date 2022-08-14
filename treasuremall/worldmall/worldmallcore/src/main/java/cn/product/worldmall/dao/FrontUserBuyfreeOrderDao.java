package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserBuyfreeOrder;

public interface FrontUserBuyfreeOrderDao extends IDao<FrontUserBuyfreeOrder>{
	
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
	public List<FrontUserBuyfreeOrder> getListByParams(Map<String, Object> params);
	
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
	public List<FrontUserBuyfreeOrder> getGroupListByParams(Map<String, Object> params);
	
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
	public List<FrontUserBuyfreeOrder> getLeftListByParams(Map<String, Object> params);
	
	/**
	 * 用户兑奖
	 * @param params
	 */
	public void userReceive(FrontUserBuyfreeOrder frontUserBuyfreeOrder, Map<String, Object> params);
	
}
