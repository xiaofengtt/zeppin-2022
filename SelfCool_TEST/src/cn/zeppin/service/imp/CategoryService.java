/** 
 * Project Name:Self_Cool 
 * File Name:CategoryService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ICategoryDAO;
import cn.zeppin.dao.api.ICategoryRetrieveDAO;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.CategoryRetrieve;
import cn.zeppin.entity.RetrieveType;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.utility.Dictionary;

/**
 * ClassName: CategoryService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class CategoryService implements ICategoryService {

	private ICategoryDAO categoryDAO;
	private ICategoryRetrieveDAO categoryRetrieveDAO;

	public ICategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(ICategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public ICategoryRetrieveDAO getCategoryRetrieveDAO() {
		return categoryRetrieveDAO;
	}

	public void setCategoryRetrieveDAO(ICategoryRetrieveDAO categoryRetrieveDAO) {
		this.categoryRetrieveDAO = categoryRetrieveDAO;
	}
	/**
	 * 添加一个分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param category
	 */
	@Override
	public void addCategory(Category category,List<RetrieveType> retrieveTypeList) {
		// 增加scode
		this.getCategoryDAO().save(category);
		String str = String.format("%010d", category.getId());
		if (category.getCategory() != null) {
			str = category.getScode() + str;
		}
		
		category.setScode(str);
		this.getCategoryDAO().update(category);
		
		if (retrieveTypeList != null) {
			for (RetrieveType retrieveType : retrieveTypeList) {
				CategoryRetrieve categoryRetrieve = new CategoryRetrieve();
				categoryRetrieve.setCategory(category);;
				categoryRetrieve.setRetrieveType(retrieveType);;
				this.getCategoryRetrieveDAO().save(categoryRetrieve);
			}
		}
	}
	
	/**
	 * 修改一个分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 */
	@Override
	public void updateCategory(Category category,List<RetrieveType> retrieveTypeList) {
		this.getCategoryDAO().update(category);
		this.getCategoryRetrieveDAO().deleteByCategory(category);
		if (retrieveTypeList != null) {
			for (RetrieveType retrieveType : retrieveTypeList) {
				CategoryRetrieve categoryRetrieve = new CategoryRetrieve();
				categoryRetrieve.setCategory(category);;
				categoryRetrieve.setRetrieveType(retrieveType);;
				this.getCategoryRetrieveDAO().save(categoryRetrieve);
			}
		}
	}

	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/> 
	 * @param category
	 */
	@Override
	public void deleteCategory(Category category) {
		category.setStatus(Dictionary.CATEGORY_STATUS_CLOSED);
		this.getCategoryDAO().update(category);
	}

	/**
	 * 根据 分类id来获取 分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Category getCategoryById(int id) {
		return this.getCategoryDAO().get(id);
	}

	/**
	 * 根据 分类Name来获取 分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:13:41 <br/>
	 * @param Name
	 * @return
	 */
	@Override
	public Category getCategoryByName(String Name) {
		return this.getCategoryDAO().getCategoryByName(Name);
	}

	/**
	 * 获取分类个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getCategoryCountByParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getCategoryDAO().getCategoryCountByParams(params);
	}

	/**
	 * 获取分类分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	@Override
	public List<Category> getCategoryListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		return this.getCategoryDAO().getCategoryListByParams(params, sorts, offset, length);
	}

}
