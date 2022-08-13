/** 
 * Project Name:Self_Cool 
 * File Name:ISysUserSubjectService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.Category;
import cn.zeppin.entity.CategoryRetrieve;


/** 
 * ClassName: ICategoryRetrieveService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:19:52 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface ICategoryRetrieveService {

	/**
	 * 获取分类的检索类型
	 * @param category
	 * @return  List<CategoryRetrieve>
	 */
	public List<CategoryRetrieve> getCategoryRetrieves(Category category);
}
