/** 
 * Project Name:ItemDatabase 
 * File Name:IOrganization.java 
 * Package Name:cn.zeppin.service.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Organization;

/**
 * ClassName: IOrganization <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月22日 下午4:23:51 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IOrganizationService {

	/**
	 * 添加一个机构表
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:25 <br/> 
	 * @param organization
	 */
	public void addOrganization(Organization organization);

	/**
	 * 跟新机构表
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:28 <br/> 
	 * @param organization
	 */
	public void updateOrganization(Organization organization);
	
	
	/**
	 * 获取机构
	 * @author Administrator
	 * @date: 2014年7月22日 下午6:40:20 <br/> 
	 * @param id
	 * @return
	 */
	public Organization getOrganizationById(int id);
	
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
	 * 删除机构
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:31 <br/> 
	 * @param organization
	 */
	public void deleteOrganization(Organization organization);

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
