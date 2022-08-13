/** 
 * Project Name:Self_Cool  
 * File Name:ICategoryRetrieveDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.Category;
import cn.zeppin.entity.CategoryRetrieve;

/** 
 * ClassName: ICategoryRetrieveDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:41:37 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ICategoryRetrieveDAO extends IBaseDAO<CategoryRetrieve, Integer> {

	/**
	 * 删除分类的检索类别
	 * @author Clark
	 * @date: 2014年7月20日 下午6:01:37 <br/> 
	 * @param category
	 */
	public void deleteByCategory(Category category);

	/**
	 * 获取分类的检索类别
	 * @param category
	 * @return List<CategoryRetrieve>
	 */
	public List<CategoryRetrieve> getCategoryRetrieves(Category category);

}
