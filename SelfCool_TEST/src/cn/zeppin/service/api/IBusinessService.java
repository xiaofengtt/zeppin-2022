/** 
 * Project Name:Self_Cool 
 * File Name:IBusinessService.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Business;

/**
 * ClassName: IBusinessService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午4:42:39 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IBusinessService {

	/**
	 * 添加业务
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param business
	 */
	public void addBusiness(Business business);

	/**
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 上午11:27:43 <br/>
	 * @param business
	 */
	public void updateBusiness(Business business);

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/>
	 * @param business
	 */
	public void deleteBusiness(Business business);

	/**
	 * 根据 业务Name来获取 业务
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:13:41 <br/>
	 * @param Name
	 * @return
	 */
	public Business getBusinessByName(String Name);
	
	/**
	 * 根据 业务id来获取 业务
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	public Business getBusinessById(Integer id);

	/**
	 * 获取业务个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param searchMap
	 * @return
	 */
	public int getBusinessCountByParams(HashMap<String, Object> searchMap);

	/**
	 * 获取业务分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Business> getBusinessListByParams(HashMap<String, Object> params, String sorts, int offset, int length);

}
