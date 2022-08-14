package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.Category;

public interface CategoryDao extends IDao<Category>{
	
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
	public List<Category> getListByParams(Map<String, Object> params);
	
	/**
	 * 添加
	 * @param params
	 * @return
	 */
	public void insertCategory(Category category);
	
	/**
	 * 级联删除
	 * @param params
	 * @return
	 */
	public void deleteCategory(Category category);
}
