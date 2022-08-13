/** 
 * Project Name:CETV_TEST 
 * File Name:UserAccessLogDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.sql.Timestamp;
import java.util.Map;

import cn.zeppin.dao.api.IUserAccessLogDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserAccessLog;

/** 
 * ClassName: UserAccessLogDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年10月14日 下午12:48:49 <br/> 
 * 
 * @author Administrator 
 * @version  
 * @since JDK 1.7 
 */
public class UserAccessLogDAO extends HibernateTemplateDAO<UserAccessLog,Long> implements IUserAccessLogDAO {

	/**
	 * 按参数查询count
	 * @author Administrator
	 * @date: 2014年10月20日 下午3:40:43 <br/> 
	 * @param request
	 * @return count
	 */
	@SuppressWarnings("deprecation")
	public int getCountByParam(Map<String, Object> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct ual.user) from UserAccessLog ual where 1=1 ");

		if (searchMap.get("user.subject.id") != null) {
			sb.append(" and ual.user.subject=").append(searchMap.get("user.subject.id"));
		}

		if (searchMap.get("user.grade.scode") != null){
			sb.append(" and ual.user.grade.scode like '").append(searchMap.get("user.grade.scode")).append("%'");
		}
		
		if (searchMap.get("time") != null&&searchMap.get("time").equals("today")){
			Timestamp stamp=new Timestamp(System.currentTimeMillis());
			stamp.setHours(0);stamp.setMinutes(0);stamp.setSeconds(0);
			sb.append(" and ual.accessTime > '").append(stamp).append("'");	
		}
		int result = Integer.parseInt(getResultByHQL(sb.toString()).toString());
		
		return result;
	}
}
