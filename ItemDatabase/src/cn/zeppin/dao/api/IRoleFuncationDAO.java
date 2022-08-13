/** 
 * Project Name:ItemDatabase 
 * File Name:IRoleFuncationDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.RoleFuncation;

/**
 * ClassName: IRoleFuncationDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:51:19 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IRoleFuncationDAO extends IBaseDAO<RoleFuncation, Integer> {
	
	/**
	 * 根据角色获取功能
	 * @author Administrator
	 * @date: 2014年7月24日 下午1:39:47 <br/> 
	 * @param role
	 * @return
	 */
	List<RoleFuncation> getRoleFunctionByRoleid(int role);
	
}
