package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IIdentifyStudyhourDao;
import cn.zeppin.entity.IdentifyStudyhour;
import cn.zeppin.service.IIdentifyStudyhourService;

@SuppressWarnings("rawtypes")
public class IdentifyStudyhourServiceImpl extends BaseServiceImpl<IdentifyStudyhour, Integer> implements IIdentifyStudyhourService {

	private IIdentifyStudyhourDao identifyStudyhourDao;


	public IIdentifyStudyhourDao getIdentifyStudyhourDao() {
		return identifyStudyhourDao;
	}

	public void setIdentifyStudyhourDao(
			IIdentifyStudyhourDao identifyStudyhourDao) {
		this.identifyStudyhourDao = identifyStudyhourDao;
	}

	public IdentifyStudyhour add(IdentifyStudyhour t) {
		return identifyStudyhourDao.add(t);
	}

	public IdentifyStudyhour update(IdentifyStudyhour t) {
		return identifyStudyhourDao.update(t);
	}

	public void delete(IdentifyStudyhour t) {
		identifyStudyhourDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return identifyStudyhourDao.getHibeTemplate();
	}

	public IdentifyStudyhour load(Integer id) {
		return identifyStudyhourDao.load(id);
	}

	public IdentifyStudyhour get(Integer id) {
		return identifyStudyhourDao.get(id);
	}

	public List<IdentifyStudyhour> loadAll() {
		return identifyStudyhourDao.loadAll();
	}

	public List<IdentifyStudyhour> findAll() {
		return identifyStudyhourDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return identifyStudyhourDao.findByHSQL(querySql);
	}

	public List<IdentifyStudyhour> getListForPage(String hql, int offset, int length) {
		return identifyStudyhourDao.getListForPage(hql, offset, length);
	}

	public List<IdentifyStudyhour> getListForPageByParams(String hql, int offset, int length, Object[] paras) {
		return identifyStudyhourDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<IdentifyStudyhour> getListForPage(String hql, int offset, int length, Object[] paras) {
		return identifyStudyhourDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		identifyStudyhourDao.executeHSQL(hql);
	}

	public List<IdentifyStudyhour> getListByHSQL(String hql) {
		return identifyStudyhourDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return identifyStudyhourDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		identifyStudyhourDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		return identifyStudyhourDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return identifyStudyhourDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return identifyStudyhourDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<IdentifyStudyhour> getListByParams(Map<String, Object> params, int start, int limit, String sort) {
		return this.identifyStudyhourDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		return this.identifyStudyhourDao.getListCountByParams(params);
	}
	
	
	
}
