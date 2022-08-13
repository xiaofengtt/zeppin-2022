/** 
 * Project Name:ItemDatabase 
 * File Name:IOrganizationDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Organization;

/**
 * ClassName: IOrganizationDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月22日 下午12:43:29 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IOrganizationDAO extends IBaseDAO<Organization, Integer> {
	
	/**
	 * 获取所有机构
	 * @author Administrator
	 * @date: 2014年7月22日 下午6:40:20 <br/> 
	 * @param id
	 * @return
	 */
	public List<Organization> getAllOrganization();
	
	/**
	 * 获取机构
	 * @author Administrator
	 * @date: 2014年7月22日 下午6:40:20 <br/> 
	 * @param id
	 * @return
	 */
	public Organization getOrganizationByName(String name);
	
	/**
	 * 获取 机构个数
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:35 <br/> 
	 * @param map
	 * @return
	 */
	public int getOrganizationCountByParams(HashMap<String, Object> map);

	/**
	 * 获取机构
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:39 <br/> 
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Organization> getOrganizationByParams(HashMap<String, Object> map, int offset, int length);
	
	
	/**
	 * 获取机构下用用户个数
	 * @author Administrator
	 * @date: 2014年7月23日 上午10:44:43 <br/> 
	 * @param organizationId
	 * @return
	 */
	public int getOrganizationSysUserCount(int organizationId);
	
	/**
	 * 通过token获取机构
	 * @author Administrator
	 * @date: 2014年7月23日 上午10:44:43 <br/> 
	 * @param token
	 * @return
	 */
	public Organization getByToken(String token);
}
