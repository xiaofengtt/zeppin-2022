package cn.zeppin.service;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.zeppin.entity.Organization;

public interface IOrganizationService extends IBaseService<Organization, Integer> {

	public int checkUserInfo(Object[] pars);

	public List<Object> getLevelOrganization(int pid);

	public int getOrgHasChild(int id);

//	public List<Organization> getAllChildByOrganization(Organization organization);

	public void addOrganization(Organization organization);
	

	/**
	 * 获取组织架构功能列表数据
	 * 
	 * @return
	 */
	public Hashtable<String, Object> getOrganizationsListPage(HttpServletRequest request);
	
	
	/**
	 * 通过多个父级部门，获取所属派出学校列表
	 * @param parentList 父级部门列表
	 * @author Clark 2014.05.27
	 * @return List<Organization>学校列表 
	 */
	public List<Organization> getAllChildSchool(Organization organization);
	
	/**
	 * 通过多个父级部门，获取所属派出学校列表
	 * @author Clark 2014.05.28
	 * @param organization 部门
	 * @return List<Organization>学校列表 
	 */
	public List<Organization> getSubOrganization(Organization organization);

	/**
	 * 通过PID查找下级部门列表
	 * @param organization
	 * @return
	 */
	public List<Organization> getOrganizationByPid(Integer organization);
}
