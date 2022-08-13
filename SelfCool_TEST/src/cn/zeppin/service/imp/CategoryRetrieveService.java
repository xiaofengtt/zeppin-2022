/** 
 * Project Name:Self_Cool 
 * File Name:SysUserSubjectService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.ICategoryRetrieveDAO;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.CategoryRetrieve;
import cn.zeppin.service.api.ICategoryRetrieveService;

/** 
 * ClassName: CategoryRetrieveService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:24:06 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class CategoryRetrieveService implements ICategoryRetrieveService {
	
	private ICategoryRetrieveDAO categoryRetrieveDAO;
	
	public ICategoryRetrieveDAO getCategoryRetrieveDAO() {
		return categoryRetrieveDAO;
	}

	public void setCategoryRetrieveDAO(ICategoryRetrieveDAO categoryRetrieveDAO) {
		this.categoryRetrieveDAO = categoryRetrieveDAO;
	}

	/**
	 * 获取分类的检索类型
	 * @param category
	 * @return  List<CategoryRetrieve>
	 */
	@Override
	public List<CategoryRetrieve> getCategoryRetrieves(Category category) {
		// TODO Auto-generated method stub
		return getCategoryRetrieveDAO().getCategoryRetrieves(category);
	}

}
