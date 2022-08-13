package cn.zeppin.service.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IOrgaCateMapDao;
import cn.zeppin.entity.OrgaCateMap;
import cn.zeppin.service.IOrgaCateMapService;

public class OrgaCateMapServiceImpl extends BaseServiceImpl<OrgaCateMap, Integer> implements IOrgaCateMapService {

	private IOrgaCateMapDao orgaCategoryDao;

	public IOrgaCateMapDao getOrgaCategoryDao() {
		return orgaCategoryDao;
	}

	public void setOrgaCategoryDao(IOrgaCateMapDao orgaCategoryDao) {
		this.orgaCategoryDao = orgaCategoryDao;
	}

	@Override
	public OrgaCateMap add(OrgaCateMap t) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.add(t);
	}

	@Override
	public OrgaCateMap update(OrgaCateMap t) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.update(t);
	}

	@Override
	public void delete(OrgaCateMap t) {
		// TODO Auto-generated method stub
		orgaCategoryDao.delete(t);
	}

	@Override
	public OrgaCateMap load(Integer id) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.load(id);
	}

	@Override
	public OrgaCateMap get(Integer id) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.get(id);
	}

	@Override
	public List<OrgaCateMap> loadAll() {
		// TODO Auto-generated method stub
		return orgaCategoryDao.loadAll();
	}

	@Override
	public List<OrgaCateMap> findAll() {
		// TODO Auto-generated method stub
		return orgaCategoryDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.findByHSQL(querySql);
	}

	@Override
	public List<OrgaCateMap> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		orgaCategoryDao.executeHSQL(hql);
	}

	@Override
	public List<OrgaCateMap> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		orgaCategoryDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<OrgaCateMap> findByRoleId(short roleId, int level) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.findByRoleId(roleId, level);
		
	}

}
