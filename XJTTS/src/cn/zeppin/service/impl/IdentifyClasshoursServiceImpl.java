package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IIdentifyClasshoursDao;
import cn.zeppin.entity.IdentifyClasshours;
import cn.zeppin.service.IIdentifyClasshoursService;

@SuppressWarnings("rawtypes")
public class IdentifyClasshoursServiceImpl extends
		BaseServiceImpl<IdentifyClasshours, Integer> implements
		IIdentifyClasshoursService {

	private IIdentifyClasshoursDao identifyClasshoursDao;


	public IIdentifyClasshoursDao getIdentifyClasshoursDao() {
		return identifyClasshoursDao;
	}

	public void setIdentifyClasshoursDao(
			IIdentifyClasshoursDao identifyClasshoursDao) {
		this.identifyClasshoursDao = identifyClasshoursDao;
	}

	public IdentifyClasshours add(IdentifyClasshours t) {
		return identifyClasshoursDao.add(t);
	}

	public IdentifyClasshours update(IdentifyClasshours t) {
		return identifyClasshoursDao.update(t);
	}

	public void delete(IdentifyClasshours t) {
		identifyClasshoursDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return identifyClasshoursDao.getHibeTemplate();
	}

	public IdentifyClasshours load(Integer id) {
		return identifyClasshoursDao.load(id);
	}

	public IdentifyClasshours get(Integer id) {
		return identifyClasshoursDao.get(id);
	}

	public List<IdentifyClasshours> loadAll() {
		return identifyClasshoursDao.loadAll();
	}

	public List<IdentifyClasshours> findAll() {
		return identifyClasshoursDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return identifyClasshoursDao.findByHSQL(querySql);
	}

	public List<IdentifyClasshours> getListForPage(String hql, int offset,
			int length) {
		return identifyClasshoursDao.getListForPage(hql, offset, length);
	}

	public List<IdentifyClasshours> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<IdentifyClasshours> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		identifyClasshoursDao.executeHSQL(hql);
	}

	public List<IdentifyClasshours> getListByHSQL(String hql) {
		return identifyClasshoursDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return identifyClasshoursDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		identifyClasshoursDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return identifyClasshoursDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return identifyClasshoursDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return identifyClasshoursDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<IdentifyClasshours> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursDao.getListCountByParams(params);
	}
	
	
	
}
