package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.CategoryTopscore;

public interface CategoryTopscoreService extends IService<CategoryTopscore>{
	
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
	public List<CategoryTopscore> getListByParams(Map<String, Object> params);
	
	/**
	 * 更新
	 * @param category
	 * @param dataList
	 * @return
	 */
	public void updateByCategory(String category, List<CategoryTopscore> dataList);
}
