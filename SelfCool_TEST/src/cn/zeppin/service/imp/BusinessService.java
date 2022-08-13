/** 
 * Project Name:Self_Cool 
 * File Name:BusinessService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IBusinessDAO;
import cn.zeppin.entity.Business;
import cn.zeppin.service.api.IBusinessService;
import cn.zeppin.utility.Dictionary;

/**
 * ClassName: BusinessService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class BusinessService implements IBusinessService {

	private IBusinessDAO businessDAO;

	public IBusinessDAO getBusinessDAO() {
		return businessDAO;
	}

	public void setBusinessDAO(IBusinessDAO businessDAO) {
		this.businessDAO = businessDAO;
	}

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param business
	 */
	@Override
	public void addBusiness(Business business) {
		this.getBusinessDAO().save(business);
	}
	
	/**
	 * 修改
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 */
	@Override
	public void updateBusiness(Business business) {
		this.getBusinessDAO().update(business);
	}

	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/> 
	 * @param business
	 */
	@Override
	public void deleteBusiness(Business business) {
		business.setStatus(Dictionary.BUSINESS_STATUS_CLOSED);
		this.getBusinessDAO().update(business);
	}
	
	/**
	 * 根据 业务Name来获取 业务
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:13:41 <br/>
	 * @param Name
	 * @return
	 */
	@Override
	public Business getBusinessByName(String Name) {
		return this.getBusinessDAO().getBusinessByName(Name);
	}
	
	/**
	 * 根据 业务id来获取 业务
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Business getBusinessById(Integer id) {
		return this.getBusinessDAO().get(id);
	}

	/**
	 * 获取业务个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getBusinessCountByParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getBusinessDAO().getBusinessCountByParams(params);
	}

	/**
	 * 获取业务分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	@Override
	public List<Business> getBusinessListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		return this.getBusinessDAO().getBusinessListByParams(params, sorts, offset, length);
	}

}
