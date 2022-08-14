package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;

public interface ActivityInfoFirstchargePrizeDao extends IDao<ActivityInfoFirstchargePrize> {
	
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
    public List<ActivityInfoFirstchargePrize> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<ActivityInfoFirstchargePrize> getListByActivityInfo(String activity);
    
}
