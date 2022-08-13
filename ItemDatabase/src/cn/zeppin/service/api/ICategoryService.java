/** 
 * Project Name:ItemDatabase 
 * File Name:ICategoryService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Category;

/**
 * ClassName: ICategoryService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface ICategoryService {

	/**
	 * 添加一个分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param category
	 */
	public void addCategory(Category category);
	
	/**
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 上午11:27:43 <br/> 
	 * @param category
	 */
	public void updateCategory(Category category);
	
	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/> 
	 * @param category
	 */
	public void deleteCategory(Category category);

	/**
	 * 根据 分类id来获取 分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	public Category getCategoryById(int id);

	/**
	 * 根据 分类Name来获取 分类
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:13:41 <br/>
	 * @param Name
	 * @return
	 */
	public Category getCategoryByName(String Name);

	/**
	 * 获取分类个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public int getCategoryCountByParams(HashMap<String, Object> params);

	/**
	 * 获取分类分页列表
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/> 
	 * @param params
	 * @param sorts 
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Category> getCategoryListByParams(HashMap<String, Object> params, String sorts, int offset, int length);

}
