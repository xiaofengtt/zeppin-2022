package cn.zeppin.service.api;

import java.util.Map;

import cn.zeppin.entity.UserAccessLog;


public interface IUserAccessLogService {
	
	/**
	 * 分析request基本信息
	 * @author Administrator
	 * @date: 2014年10月20日 下午3:40:43 <br/> 
	 * @param request
	 */
	public void addUserAccessLog(UserAccessLog accessLog);
	
	/**
	 * 按参数查询count
	 * @author Administrator
	 * @date: 2014年10月20日 下午3:40:43 <br/> 
	 * @param request
	 * @return count
	 */
	public int getCountByParam(Map<String, Object> searchMap);
}
