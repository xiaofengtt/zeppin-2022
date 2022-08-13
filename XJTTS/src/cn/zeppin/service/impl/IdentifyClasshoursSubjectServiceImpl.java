package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IIdentifyClasshoursSubjectDao;
import cn.zeppin.entity.IdentifyClasshoursSubject;
import cn.zeppin.service.IIdentifyClasshoursSubjectService;

@SuppressWarnings("rawtypes")
public class IdentifyClasshoursSubjectServiceImpl extends
		BaseServiceImpl<IdentifyClasshoursSubject, Integer> implements
		IIdentifyClasshoursSubjectService {

	private IIdentifyClasshoursSubjectDao identifyClasshoursSubjectDao;


	public IIdentifyClasshoursSubjectDao getIdentifyClasshoursSubjectDao() {
		return identifyClasshoursSubjectDao;
	}

	public void setIdentifyClasshoursSubjectDao(
			IIdentifyClasshoursSubjectDao identifyClasshoursSubjectDao) {
		this.identifyClasshoursSubjectDao = identifyClasshoursSubjectDao;
	}

	public IdentifyClasshoursSubject add(IdentifyClasshoursSubject t) {
		return identifyClasshoursSubjectDao.add(t);
	}

	public IdentifyClasshoursSubject update(IdentifyClasshoursSubject t) {
		return identifyClasshoursSubjectDao.update(t);
	}

	public void delete(IdentifyClasshoursSubject t) {
		identifyClasshoursSubjectDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return identifyClasshoursSubjectDao.getHibeTemplate();
	}

	public IdentifyClasshoursSubject load(Integer id) {
		return identifyClasshoursSubjectDao.load(id);
	}

	public IdentifyClasshoursSubject get(Integer id) {
		return identifyClasshoursSubjectDao.get(id);
	}

	public List<IdentifyClasshoursSubject> loadAll() {
		return identifyClasshoursSubjectDao.loadAll();
	}

	public List<IdentifyClasshoursSubject> findAll() {
		return identifyClasshoursSubjectDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return identifyClasshoursSubjectDao.findByHSQL(querySql);
	}

	public List<IdentifyClasshoursSubject> getListForPage(String hql, int offset,
			int length) {
		return identifyClasshoursSubjectDao.getListForPage(hql, offset, length);
	}

	public List<IdentifyClasshoursSubject> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursSubjectDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<IdentifyClasshoursSubject> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return identifyClasshoursSubjectDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		identifyClasshoursSubjectDao.executeHSQL(hql);
	}

	public List<IdentifyClasshoursSubject> getListByHSQL(String hql) {
		return identifyClasshoursSubjectDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return identifyClasshoursSubjectDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		identifyClasshoursSubjectDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return identifyClasshoursSubjectDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return identifyClasshoursSubjectDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return identifyClasshoursSubjectDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<IdentifyClasshoursSubject> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursSubjectDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.identifyClasshoursSubjectDao.getListCountByParams(params);
	}
	
	
	
}
