/** 
 * Project Name:ItemDatabase 
 * File Name:OrganizationService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IOrganizationDAO;
import cn.zeppin.entity.Organization;
import cn.zeppin.service.api.IOrganizationService;

/**
 * ClassName: OrganizationService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月22日 下午4:42:16 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class OrganizationService implements IOrganizationService {

	private IOrganizationDAO organizationDAO;

	public IOrganizationDAO getOrganizationDAO() {
		return organizationDAO;
	}

	public void setOrganizationDAO(IOrganizationDAO organizationDAO) {
		this.organizationDAO = organizationDAO;
	}

	@Override
	public Organization getOrganizationById(int id) {
		return this.getOrganizationDAO().get(id);
	}

	@Override
	public Organization getOrganizationByName(String name) {
		return this.getOrganizationDAO().getOrganizationByName(name);
	}

	/**
	 * 添加一个机构表
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:25 <br/>
	 * @param organization
	 */
	@Override
	public void addOrganization(Organization organization) {
		this.getOrganizationDAO().save(organization);
	}

	/**
	 * 跟新机构表
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:28 <br/>
	 * @param organization
	 */
	@Override
	public void updateOrganization(Organization organization) {
		this.getOrganizationDAO().update(organization);
	}
	
	/**
	 * 获取所有机构
	 * @author Administrator
	 * @date: 2014年7月22日 下午6:40:20 <br/> 
	 * @param id
	 * @return
	 */
	public List<Organization> getAllOrganization(){
		return this.getOrganizationDAO().getAllOrganization();
	}
	
	/**
	 * 删除机构
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:31 <br/>
	 * @param organization
	 */
	@Override
	public void deleteOrganization(Organization organization) {
		this.getOrganizationDAO().delete(organization);
	}

	/**
	 * 获取 机构个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:35 <br/>
	 * @param map
	 * @return
	 */
	@Override
	public int getOrganizationCountByParams(HashMap<String, Object> map) {
		return this.getOrganizationDAO().getOrganizationCountByParams(map);
	}

	/**
	 * 获取机构
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:39 <br/>
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	@Override
	public List<Organization> getOrganizationByParams(HashMap<String, Object> map, int offset, int length) {
		return this.getOrganizationDAO().getOrganizationByParams(map, offset, length);
	}

	/**
	 * 获取机构下用用户个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月23日 上午10:44:43 <br/>
	 * @param organizationId
	 * @return
	 */
	@Override
	public int getOrganizationSysUserCount(int organizationId) {
		return this.getOrganizationDAO().getOrganizationSysUserCount(organizationId);
	}

	/**
	 * 通过token获取机构
	 * @author Administrator
	 * @date: 2014年7月23日 上午10:44:43 <br/> 
	 * @param token
	 * @return
	 */
	public Organization getByToken(String token){
		return this.getOrganizationDAO().getByToken(token);
	}
}
