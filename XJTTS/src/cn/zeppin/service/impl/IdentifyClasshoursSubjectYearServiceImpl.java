package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IIdentifyClasshoursSubjectYearDao;
import cn.zeppin.entity.IdentifyClasshoursSubjectYear;
import cn.zeppin.service.IIdentifyClasshoursSubjectYearService;

@SuppressWarnings("rawtypes")
public class IdentifyClasshoursSubjectYearServiceImpl extends
		BaseServiceImpl<IdentifyClasshoursSubjectYear, Integer> implements
		IIdentifyClasshoursSubjectYearService {

	private IIdentifyClasshoursSubjectYearDao identifyClasshoursSubjectYearDao;


	public IIdentifyClasshoursSubjectYearDao getIdentifyClasshoursSubjectYearDao() {
		return identifyClasshoursSubjectYearDao;
	}

	public void setIdentifyClasshoursSubjectYearDao(
			IIdentifyClasshoursSubjectYearDao identifyClasshoursSubjectYearDao) {
		this.identifyClasshoursSubjectYearDao = identifyClasshoursSubjectYearDao;
	}

	public IdentifyClasshoursSubjectYear add(IdentifyClasshoursSubjectYear t) {
		return identifyClasshoursSubjectYearDao.add(t);
	}

	public IdentifyClasshoursSubjectYear update(IdentifyClasshoursSubjectYear t) {
		return identifyClasshoursSubjectYearDao.update(t);
	}

	public void delete(IdentifyClasshoursSubjectYear t) {
		identifyClasshoursSubjectYearDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return identifyClasshoursSubjectYearDao.getHibeTemplate();
	}

	public IdentifyClasshoursSubjectYear load(Integer id) {
		return identifyClasshoursSubjectYearDao.load(id);
	}

	public IdentifyClasshoursSubjectYear get(Integer id) {
		return identifyClasshoursSubjectYearDao.get(id);
	}

	public List<IdentifyClasshoursSubjectYear> loadAll() {
		return identifyClasshoursSubjectYearDao.loadAll();
	}

	public List<IdentifyClasshoursSubjectYear> findAll() {
		return identifyClasshoursSubjectYearDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return identifyClasshoursSubjectYearDao.findByHSQL(querySql);
	}

	public List<IdentifyClasshoursSubjectYear> getListForPage(String hql, int offset,
			int length) {
		return identifyClasshoursSubjectYearDao.getListForPage(hql, offset, length);
	}

	public List<IdentifyClasshoursSubjectYear> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursSubjectYearDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<IdentifyClasshoursSubjectYear> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursSubjectYearDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		identifyClasshoursSubjectYearDao.executeHSQL(hql);
	}

	public List<IdentifyClasshoursSubjectYear> getListByHSQL(String hql) {
		return identifyClasshoursSubjectYearDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return identifyClasshoursSubjectYearDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		identifyClasshoursSubjectYearDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return identifyClasshoursSubjectYearDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return identifyClasshoursSubjectYearDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return identifyClasshoursSubjectYearDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<IdentifyClasshoursSubjectYear> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursSubjectYearDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursSubjectYearDao.getListCountByParams(params);
	}
	
	
	
}
