package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Category;

public interface ICategoryService {
	
	/**
	 * 获取
	 */
	public Category getCategory(Integer id);
	
	/**
	 * 添加
	 */
	public Category addCategory(Category category);

	/**
	 * 删除
	 */
	public Category deleteCategory(Category category);

	/**
	 * 修改
	 */
	public Category updateCategory(Category category);
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	/**
	 * 获取页面信息
	 */
	public List<Category> getListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize );
	
	/**
	 * 通过参数取列表
	 */
	public List<Category> getListByParams(HashMap<String,String> searchMap);
	
	/**
	 * 是否有子分类
	 */
	public Boolean hasChild(Integer id);
}
