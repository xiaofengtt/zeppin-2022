package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.dao.IBaseDao;
import cn.zeppin.dao.IOrganizationLevelDao;
import cn.zeppin.entity.OrganizationLevel;
import cn.zeppin.entity.ProjectLevel;
import cn.zeppin.service.IOrganizationLevelService;

public class OrganizationLevelServiceImpl extends BaseServiceImpl<OrganizationLevel, Short> implements IOrganizationLevelService {

	private IOrganizationLevelDao organizationLevelDao;

	public IOrganizationLevelDao getOrganizationLevelDao() {
		return organizationLevelDao;
	}

	public void setOrganizationLevelDao(IOrganizationLevelDao organizationLevelDao) {
		this.organizationLevelDao = organizationLevelDao;
	}

	@Override
	public OrganizationLevel add(OrganizationLevel t) {
		// TODO Auto-generated method stub
		return organizationLevelDao.add(t);
	}

	@Override
	public OrganizationLevel update(OrganizationLevel t) {
		// TODO Auto-generated method stub
		return organizationLevelDao.update(t);
	}

	@Override
	public void delete(OrganizationLevel t) {
		// TODO Auto-generated method stub
		organizationLevelDao.delete(t);
	}

	@Override
	public OrganizationLevel load(Short id) {
		// TODO Auto-generated method stub
		return organizationLevelDao.load(id);
	}

	@Override
	public OrganizationLevel get(Short id) {
		// TODO Auto-generated method stub
		return organizationLevelDao.get(id);
	}

	@Override
	public List<OrganizationLevel> loadAll() {
		// TODO Auto-generated method stub
		return organizationLevelDao.loadAll();
	}

	@Override
	public List<OrganizationLevel> findAll() {
		// TODO Auto-generated method stub
		return organizationLevelDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return organizationLevelDao.findByHSQL(querySql);
	}

	@Override
	public List<OrganizationLevel> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return organizationLevelDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		organizationLevelDao.executeHSQL(hql);
	}

	@Override
	public List<OrganizationLevel> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return organizationLevelDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return organizationLevelDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		organizationLevelDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return organizationLevelDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<OrganizationLevel> getList() {
		// TODO Auto-generated method stub
		String hqlsString = "from OrganizationLevel order by level";
		List<OrganizationLevel> tlst = new ArrayList<OrganizationLevel>();
		tlst = getListByHSQL(hqlsString);
		return tlst;
	}

}
