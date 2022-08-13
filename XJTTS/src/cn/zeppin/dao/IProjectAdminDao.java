package cn.zeppin.dao;

import java.util.Hashtable;
import java.util.List;

import cn.zeppin.entity.ProjectAdmin;

public interface IProjectAdminDao extends IBaseDao<ProjectAdmin, Integer> {
	
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
	public int getProjectAdminCount(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId,int projectId,String organizations, short status);



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
	public List getProjectAdmin(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId,int projectId,String organizations, int offset, int length, short status);

	/**
	 * 获取上级管理员的人数
	 * @param searchName 搜索值
	 * @param searchType 搜索类型
	 * @param sortName 排序名称
	 * @param sortTyep 排序类型
	 * @param organizationId 管理员机构id
	 * @return
	 */
	public int getHigherAdminCount(String searchName, String searchType, String sortName, String sortType, int organizationId);

	/**
	 * 获取上级管理员的人数
	 * @param searchName 搜索值
	 * @param searchType 搜索类型
	 * @param sortName 排序名称
	 * @param sortTyep 排序类型
	 * @param organizationId 管理员机构id
	 * @return
	 */
	public List getHigherAdmin(String searchName, String searchType, String sortName, String sortType, int organizationId,int offset, int length);
	
	/**
	 * 根据所属机构ID查询管理员信息列表
	 * @param organizationId
	 * @return
	 */
	public List<ProjectAdmin> getAdminByOrganization(Integer organizationId, int offset, int length);
	
	/**
	 * 20180313新增获取分状态统计数
	 * @param searchName
	 * @param searchType
	 * @param sortName
	 * @param sortType
	 * @param isAdmin
	 * @param organizationId
	 * @param projectId
	 * @param organizations
	 * @return
	 */
	public List getProjectAdminStatusList(String searchName, String searchType, boolean isAdmin, int organizationId,int projectId,String organizations);
}
