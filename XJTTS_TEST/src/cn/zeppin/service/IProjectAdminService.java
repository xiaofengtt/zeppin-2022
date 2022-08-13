package cn.zeppin.service;

import java.util.Hashtable;
import java.util.List;

import cn.zeppin.entity.ProjectAdmin;

public interface IProjectAdminService extends IBaseService<ProjectAdmin, Integer> {
	public int checkUserInfo(Object[] pars);

	/**
	 * 获取 管理员的人数
	 * @param searchName 搜索值
	 * @param searchType 搜索类型
	 * @param sortName 排序名称
	 * @param sortTyep 排序类型
	 * @param isAdmin 是否是管理员
	 * @param organizationId 管理员机构id
	 * @return
	 */
	public int getProjectAdminCount(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId,int projectId,String organizations);



	/**
	 * 获取 管理员的人数
	 * @param searchName 搜索值
	 * @param searchType 搜索类型
	 * @param sortName 排序名称
	 * @param sortTyep 排序类型
	 * @param isAdmin 是否是管理员
	 * @param organizationId 管理员机构id
	 * @return
	 */
	public List getProjectAdmin(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId, int projectId,String organizations,int offset, int length);

	/**
	 * 根据所属机构的ID获取该机构所有管理员信息
	 * @param OrganizationId
	 * @return
	 */
	public List getAdminByOrganization(Integer organizationId, int offset, int length);
}
