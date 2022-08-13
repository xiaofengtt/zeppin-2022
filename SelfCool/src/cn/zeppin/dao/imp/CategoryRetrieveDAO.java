/** 
 * Project Name:Self_Cool 
 * File Name:CategoryRetrieveDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;



import java.util.List;

import cn.zeppin.dao.api.ICategoryRetrieveDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.CategoryRetrieve;

/** 
 * ClassName: CategoryRetrieveDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:43:46 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class CategoryRetrieveDAO extends HibernateTemplateDAO<CategoryRetrieve, Integer> implements
		ICategoryRetrieveDAO {

	/**
	 * 删除分类的检索类别
	 * @author Clark
	 * @date: 2014年7月20日 下午6:02:25 <br/> 
	 * @param category
	 */
	@Override
	public void deleteByCategory(Category category) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" delete from CategoryRetrieve where category=").append(category.getId());
		this.executeHQL(sb.toString());
	}

	/**
	 * 获取分类检索类别
	 * @param category
	 * @return  List<CategoryRetrieve>
	 */
	@Override
	public List<CategoryRetrieve> getCategoryRetrieves(Category category) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" from CategoryRetrieve cr ");
		sb.append(" where cr.category=").append(category.getId());
		return this.getByHQL(sb.toString());
		
	}


}
