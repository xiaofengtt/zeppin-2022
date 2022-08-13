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
		// TODO Auto-generated method stub
		return projectAdminDao.add(t);
	}

	@Override
	public ProjectAdmin update(ProjectAdmin t) {
		// TODO Auto-generated method stub
		return projectAdminDao.update(t);
	}

	@Override
	public void delete(ProjectAdmin t) {
		// TODO Auto-generated method stub
		t.setStatus(Short.parseShort("2"));
		projectAdminDao.update(t);
	}

	@Override
	public ProjectAdmin load(Integer id) {
		// TODO Auto-generated method stub
		return projectAdminDao.load(id);
	}

	@Override
	public ProjectAdmin get(Integer id) {
		// TODO Auto-generated method stub
		return projectAdminDao.get(id);
	}

	@Override
	public List<ProjectAdmin> loadAll() {
		// TODO Auto-generated method stub
		return projectAdminDao.loadAll();
	}

	@Override
	public List<ProjectAdmin> findAll() {
		// TODO Auto-generated method stub
		return projectAdminDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return projectAdminDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectAdmin> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return projectAdminDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		projectAdminDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectAdmin> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return projectAdminDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectAdminDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		projectAdminDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectAdminDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public int checkUserInfo(Object[] pars) {
		return this.projectAdminDao.checkUserInfo(pars);
	}

	@Override
	public int getProjectAdminCount(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId, int projectId, String organizations) {
		// TODO Auto-generated method stub
		return this.projectAdminDao.getProjectAdminCount(searchName, searchType, sortName, sortType, isAdmin, organizationId, projectId, organizations);
	}

	@Override
	public List getProjectAdmin(String searchName, String searchType, String sortName, String sortType, boolean isAdmin, int organizationId, int projectId, String organizations, int offset, int length) {
		// TODO Auto-generated method stub
		return this.projectAdminDao.getProjectAdmin(searchName, searchType, sortName, sortType, isAdmin, organizationId, projectId, organizations, offset, length);
	}

	@Override
	public List getAdminByOrganization(Integer organizationId, int offset, int length) {
		// TODO Auto-generated method stub
		return this.projectAdminDao.getAdminByOrganization(organizationId,offset,length);
	}

}
