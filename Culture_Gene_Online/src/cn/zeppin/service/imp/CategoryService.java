package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ICategoryDAO;
import cn.zeppin.entity.Category;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.utility.Dictionary;

public class CategoryService implements ICategoryService {

	
	private ICategoryDAO categoryDAO;
	
	public ICategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(ICategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	/**
	 * 获取
	 */
	public Category getCategory(Integer id) {
		return this.getCategoryDAO().get(id);
	}

	/**
	 * 添加
	 */
	public Category addCategory(Category category) {
		return getCategoryDAO().save(category);
	}

	/**
	 * 停用
	 */
	public Category deleteCategory(Category category) {
		category.setStatus(Dictionary.CATEGORY_STATUS_CLOSED);
		return this.getCategoryDAO().update(category);
	}

	/**
	 * 修改
	 */
	public Category updateCategory(Category Category) {
		return this.getCategoryDAO().update(Category);
	}

	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		return this.getCategoryDAO().getCountByParams(searchMap);
	}
	
	/**
	 * 获取页面信息
	 */
	public List<Category> getListForPage(HashMap<String,String> searchMap, String sort,  Integer offset, Integer pagesize ){
		return this.getCategoryDAO().getListByParams(searchMap, sort, offset, pagesize);
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<Category> getListByParams(HashMap<String,String> searchMap){
		return this.getCategoryDAO().getListByParams(searchMap, null, null, null);
	}
	
	/**
	 * 是否有子分类
	 */
	public Boolean hasChild(Integer id){
		Integer count = this.getCategoryDAO().getChildCount(id);
		if (count > 0){
			return true;
		}else{
			return false;
		}
	}
}
