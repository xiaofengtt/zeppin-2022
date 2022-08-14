package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.ActivityInfoScorelotteryPrize;

public interface ActivityInfoScorelotteryPrizeDao extends IDao<ActivityInfoScorelotteryPrize> {
	
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
    public List<ActivityInfoScorelotteryPrize> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<ActivityInfoScorelotteryPrize> getListByActivityInfo(String activity);
    
}
