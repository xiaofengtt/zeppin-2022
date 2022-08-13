package cn.zeppin.service.impl;

import java.util.Hashtable;
import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IProjectAdminDao;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.service.IProjectAdminService;

public class ProjectAdminServiceImpl extends BaseServiceImpl<ProjectAdmin, Integer> implements IProjectAdminService {

	private IProjectAdminDao projectAdminDao;

	public IProjectAdminDao getProjectAdminDao() {
		return projectAdminDao;
	}

	public void setProjectAdminDao(IProjectAdminDao projectAdminDao) {
		this.projectAdminDao = projectAdminDao;
	}

	@Override
	public ProjectAdmin add(ProjectAdmin t) {
		return projectAdminDao.add(t);
	}

	@Override
	public ProjectAdmin update(ProjectAdmin t) {
		return projectAdminDao.update(t);
	}

	@Override
	public void delete(ProjectAdmin t) {
		t.setStatus(Short.parseShort("2"));
		projectAdminDao.update(t);
	}

	@Override
	public ProjectAdmin load(Integer id) {
		return projectAdminDao.load(id);
	}

	@Override
	public ProjectAdmin get(Integer id) {
		return projectAdminDao.get(id);
	}

	@Override
	public List<ProjectAdmin> loadAll() {
		return projectAdminDao.loadAll();
	}

	@Override
	public List<ProjectAdmin> findAll() {
		return projectAdminDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		return projectAdminDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectAdmin> getListForPage(String hql, int offset, int length) {
		return projectAdminDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		projectAdminDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectAdmin> getListByHSQL(String hql) {
		return projectAdminDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		return projectAdminDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		projectAdminDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		return projectAdminDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public int checkUserInfo(Object[] pars) {
		return this.projectAdminDao.checkUserInfo(pars);
	}

	@Override
	public int getProjectAdminCount(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId, int projectId, String organizations, short status) {
		return this.projectAdminDao.getProjectAdminCount(searchName, searchType, sortName, sortType, isAdmin, organizationId, projectId, organizations,status);
	}

	@Override
	public List getProjectAdmin(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId, int projectId, String organizations, int offset, int length, short status) {
		return this.projectAdminDao.getProjectAdmin(searchName, searchType, sortName, sortType, isAdmin, organizationId, projectId, organizations, offset, length,status);
	}

	@Override
	public int getHigherAdminCount(String searchName, String searchType, String sortName, String sortType, int organizationId) {
		return this.projectAdminDao.getHigherAdminCount(searchName, searchType, sortName, sortType, organizationId);
	}

	@Override
	public List getHigherAdmin(String searchName, String searchType, String sortName, String sortType,int organizationId, int offset, int length) {
		return this.projectAdminDao.getHigherAdmin(searchName, searchType, sortName, sortType, organizationId, offset, length);
	}
	
	@Override
	public List<ProjectAdmin> getAdminByOrganization(Integer organizationId, int offset, int length) {
		return this.projectAdminDao.getAdminByOrganization(organizationId,offset,length);
	}

	@Override
	public List getProjectAdminStatusList(String searchName, String searchType,
			boolean isAdmin, int organizationId, int projectId,
			String organizations) {
		// TODO Auto-generated method stub
		return this.projectAdminDao.getProjectAdminStatusList(searchName, searchType, isAdmin, organizationId, projectId, organizations);
	}

}
