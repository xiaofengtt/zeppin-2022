/** 
 * Project Name:Self_Cool 
 * File Name:BusinessCategoryService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IBusinessCategoryDAO;
import cn.zeppin.entity.Business;
import cn.zeppin.entity.BusinessCategory;
import cn.zeppin.entity.Category;
import cn.zeppin.service.api.IBusinessCategoryService;

/** 
 * ClassName: BusinessCategoryService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月8日 下午7:24:06 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class BusinessCategoryService implements IBusinessCategoryService {
	
	private IBusinessCategoryDAO businessCategoryDAO;
	
	public IBusinessCategoryDAO getBusinessCategoryDAO() {
		return businessCategoryDAO;
	}

	public void setBusinessCategoryDAO(IBusinessCategoryDAO businessCategoryDAO) {
		this.businessCategoryDAO = businessCategoryDAO;
	}

	/**
	 * 获取业务的分类
	 * @param business
	 * @return  List<BusinessCategory>
	 */
	@Override
	public List<BusinessCategory> getBusinessCategorys(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getBusinessCategoryDAO().getBusinessCategorys(map);
	}

	/**
	 * 添加
	 * 
	 * @param business
	 * @param cas
	 */
	public void addBusinessCategorys(Business business, List<Category> cas) {

		this.getBusinessCategoryDAO().deleteByBusiness(business);

		for (Category ca : cas) {

			BusinessCategory bc = new BusinessCategory();
			bc.setCategory(ca);
			bc.setBusiness(business);

			this.getBusinessCategoryDAO().save(bc);

		}

	}
}
