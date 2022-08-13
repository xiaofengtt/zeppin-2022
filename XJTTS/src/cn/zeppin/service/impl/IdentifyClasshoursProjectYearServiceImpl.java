package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IIdentifyClasshoursProjectYearDao;
import cn.zeppin.entity.IdentifyClasshoursProjectYear;
import cn.zeppin.service.IIdentifyClasshoursProjectYearService;

@SuppressWarnings("rawtypes")
public class IdentifyClasshoursProjectYearServiceImpl extends
		BaseServiceImpl<IdentifyClasshoursProjectYear, Integer> implements
		IIdentifyClasshoursProjectYearService {

	private IIdentifyClasshoursProjectYearDao identifyClasshoursProjectYearDao;


	public IIdentifyClasshoursProjectYearDao getIdentifyClasshoursProjectYearDao() {
		return identifyClasshoursProjectYearDao;
	}

	public void setIdentifyClasshoursProjectYearDao(
			IIdentifyClasshoursProjectYearDao identifyClasshoursProjectYearDao) {
		this.identifyClasshoursProjectYearDao = identifyClasshoursProjectYearDao;
	}

	public IdentifyClasshoursProjectYear add(IdentifyClasshoursProjectYear t) {
		return identifyClasshoursProjectYearDao.add(t);
	}

	public IdentifyClasshoursProjectYear update(IdentifyClasshoursProjectYear t) {
		return identifyClasshoursProjectYearDao.update(t);
	}

	public void delete(IdentifyClasshoursProjectYear t) {
		identifyClasshoursProjectYearDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return identifyClasshoursProjectYearDao.getHibeTemplate();
	}

	public IdentifyClasshoursProjectYear load(Integer id) {
		return identifyClasshoursProjectYearDao.load(id);
	}

	public IdentifyClasshoursProjectYear get(Integer id) {
		return identifyClasshoursProjectYearDao.get(id);
	}

	public List<IdentifyClasshoursProjectYear> loadAll() {
		return identifyClasshoursProjectYearDao.loadAll();
	}

	public List<IdentifyClasshoursProjectYear> findAll() {
		return identifyClasshoursProjectYearDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return identifyClasshoursProjectYearDao.findByHSQL(querySql);
	}

	public List<IdentifyClasshoursProjectYear> getListForPage(String hql, int offset,
			int length) {
		return identifyClasshoursProjectYearDao.getListForPage(hql, offset, length);
	}

	public List<IdentifyClasshoursProjectYear> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursProjectYearDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<IdentifyClasshoursProjectYear> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursProjectYearDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		identifyClasshoursProjectYearDao.executeHSQL(hql);
	}

	public List<IdentifyClasshoursProjectYear> getListByHSQL(String hql) {
		return identifyClasshoursProjectYearDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return identifyClasshoursProjectYearDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		identifyClasshoursProjectYearDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return identifyClasshoursProjectYearDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return identifyClasshoursProjectYearDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return identifyClasshoursProjectYearDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<IdentifyClasshoursProjectYear> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursProjectYearDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursProjectYearDao.getListCountByParams(params);
	}
	
	
	
}
