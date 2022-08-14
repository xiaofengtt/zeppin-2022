package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserDaily;

public interface FrontUserDailyDao extends IDao<FrontUserDaily>{
	
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
	public List<FrontUserDaily> getListByParams(Map<String, Object> params);
	
	/**
	 * 获取活跃统计数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getActiveListByParams(Map<String, Object> params);
}
