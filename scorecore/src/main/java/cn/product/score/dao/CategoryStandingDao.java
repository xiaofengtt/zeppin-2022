package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.CategoryStanding;

public interface CategoryStandingDao extends IDao<CategoryStanding>{
	
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
	public List<CategoryStanding> getListByParams(Map<String, Object> params);
	
	/**
	 * 更新
	 * @param category
	 * @param season
	 * @param dataList
	 * @return
	 */
	public void updateByCategory(String category, String season, List<CategoryStanding> dataList);
}
