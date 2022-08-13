package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.Organization;

public interface IOrganizationDao extends IBaseDao<Organization, Integer> {
	
	/**
	 * 通过部门，获取所属所有下级部门
	 * @param organization
	 * @return
	 */
	public List<Organization> getALLSubOrganizations(Organization organization);
	
	/**
	 * 通过部门，获取所属派出学校列表
	 * @param organization 父级部门列表
	 * @author Clark 2014.05.27
	 * @return List<Organization>学校列表 
	 */
	public List<Organization> getAllChildSchool(Organization organization);

	/**
	 * 获取下级组织机构列表
	 * @author Clark 2014.05.28
	 * @param organization 部门
	 * @return List<Organization>学校列表 
	 */
	public List<Organization> getSubOrganizations(Organization organization);
	
	
	public int checkUserInfo(Object[] pars);
	
	public List<Object> getLevelOrganization(int pid);
	
	public int getOrgHasChild(int pid);
	
	public void addOrganization(Organization organization);


} 
