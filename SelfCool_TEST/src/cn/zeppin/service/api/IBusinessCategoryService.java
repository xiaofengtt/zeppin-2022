/** 
 * Project Name:Self_Cool 
 * File Name:IBusinessCategoryService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Business;
import cn.zeppin.entity.BusinessCategory;
import cn.zeppin.entity.Category;

/** 
 * ClassName: IBusinessCategoryService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:19:52 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface IBusinessCategoryService {

	/**
	 * 获取业务的分类
	 * @param business
	 * @return  List<BusinessCategory>
	 */
	public List<BusinessCategory> getBusinessCategorys(Map<String, Object> map);
	
	/**
	 * 添加
	 * @param subject
	 * @param srs
	 */
	public void addBusinessCategorys(Business business, List<Category> bus);
}
