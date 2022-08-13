/** 
 * Project Name:Self_Cool  
 * File Name:IBusinessCategoryDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Business;
import cn.zeppin.entity.BusinessCategory;

/** 
 * ClassName: IBusinessCategoryDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:41:37 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public interface IBusinessCategoryDAO extends IBaseDAO<BusinessCategory, Integer> {

	/**
	 * 删除业务下的分类
	 * @author Clark
	 * @date: 2014年7月20日 下午6:01:37 <br/> 
	 * @param business
	 */
	public void deleteByBusiness(Business business);

	/**
	 * 获取业务下的分类
	 * @param business
	 * @return List<BusinessCategory>
	 */
	public List<BusinessCategory> getBusinessCategorys(Map<String, Object> map);

}
