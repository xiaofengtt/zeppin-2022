/** 
 * Project Name:CETV_TEST 
 * File Name:UserAccessLogService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.service.imp;

import java.util.Map;

import cn.zeppin.dao.api.IUserAccessLogDAO;
import cn.zeppin.entity.UserAccessLog;
import cn.zeppin.service.api.IUserAccessLogService;

/** 
 * ClassName: UserAccessLogService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年10月20日 下午3:21:31 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public class UserAccessLogService implements IUserAccessLogService {
	
	private IUserAccessLogDAO userAccessLogDAO;

	public IUserAccessLogDAO getUserAccessLogDAO() {
		return userAccessLogDAO;
	}

	public void setUserAccessLogDAO(IUserAccessLogDAO userAccessLogDAO) {
		this.userAccessLogDAO = userAccessLogDAO;
	}
	
	
	/**
	 * 分析request基本信息
	 * @author Administrator
	 * @date: 2014年10月20日 下午3:40:43 <br/> 
	 * @param request
	 */
	public void addUserAccessLog(UserAccessLog accessLog){
		this.getUserAccessLogDAO().save(accessLog);
	}

	/**
	 * 按参数查询count
	 * @author Administrator
	 * @date: 2014年10月20日 下午3:40:43 <br/> 
	 * @param request
	 * @return count
	 */
	public int getCountByParam(Map<String, Object> searchMap){
		return this.getUserAccessLogDAO().getCountByParam(searchMap);
	}
}
