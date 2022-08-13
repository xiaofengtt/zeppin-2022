package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Category;

public interface ICategoryDAO extends IBaseDAO<Category, Integer> {

	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 通过参数取列表
	 */
	public List<Category> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
	
	/**
	 * 获取子分类数量
	 */
	public Integer getChildCount(Integer id);
}
