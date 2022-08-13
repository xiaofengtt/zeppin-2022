/** 
 * Project Name:Self_Cool 
 * File Name:BusinessCategoryDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;



import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IBusinessCategoryDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Business;
import cn.zeppin.entity.BusinessCategory;

/** 
 * ClassName: CategoryRetrieveDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午6:43:46 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class BusinessCategoryDAO extends HibernateTemplateDAO<BusinessCategory, Integer> implements
		IBusinessCategoryDAO {

	/**
	 * 删除业务下的分类
	 * @author Clark
	 * @date: 2014年7月20日 下午6:02:25 <br/> 
	 * @param category
	 */
	@Override
	public void deleteByBusiness(Business business) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" delete from BusinessCategory where business=").append(business.getId());
		this.executeHQL(sb.toString());
	}

	/**
	 * 获取业务下的分类
	 * @param category
	 * @return  List<BusinessCategory>
	 */
	@Override
	public List<BusinessCategory> getBusinessCategorys(Map<String, Object> map) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" from BusinessCategory bc where 1=1 ");
		if (map.containsKey("business.id") && map.get("business.id") != null) {
			sb.append(" and bc.business = ").append(map.get("business.id"));
		}
		return this.getByHQL(sb.toString());
		
	}


}
