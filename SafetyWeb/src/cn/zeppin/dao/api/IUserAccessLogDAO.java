/** 
 * Project Name:CETV_TEST 
 * File Name:IUserAccessLogDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.Map;

import cn.zeppin.entity.UserAccessLog;

/**
 * ClassName: IUserAccessLogDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月13日 下午4:57:15 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IUserAccessLogDAO extends IBaseDAO<UserAccessLog, Long> {

	/**
	 * 按参数查询count
	 * @author Administrator
	 * @date: 2014年10月20日 下午3:40:43 <br/> 
	 * @param request
	 * @return count
	 */
	public int getCountByParam(Map<String, Object> searchMap);

}
