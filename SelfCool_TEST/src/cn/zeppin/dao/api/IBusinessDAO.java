/** 
 * Project Name:Self_Cool  
 * File Name:IBusinessDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Business;

/**
 * ClassName: IBusinessDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午8:33:58 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IBusinessDAO extends IBaseDAO<Business, Integer> {

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
	 * 获取业务个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	public Integer getBusinessCountByParams(HashMap<String, Object> params);

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
