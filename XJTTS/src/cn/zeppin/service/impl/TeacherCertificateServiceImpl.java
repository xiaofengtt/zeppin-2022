package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.ITeacherCertificateDao;
import cn.zeppin.entity.TeacherCertificate;
import cn.zeppin.service.ITeacherCertificateService;

@SuppressWarnings("rawtypes")
public class TeacherCertificateServiceImpl extends
		BaseServiceImpl<TeacherCertificate, Integer> implements
		ITeacherCertificateService {

	private ITeacherCertificateDao teacherCertificateDao;


	public ITeacherCertificateDao getTeacherCertificateDao() {
		return teacherCertificateDao;
	}

	public void setTeacherCertificateDao(
			ITeacherCertificateDao teacherCertificateDao) {
		this.teacherCertificateDao = teacherCertificateDao;
	}

	public TeacherCertificate add(TeacherCertificate t) {
		return teacherCertificateDao.add(t);
	}

	public TeacherCertificate update(TeacherCertificate t) {
		return teacherCertificateDao.update(t);
	}

	public void delete(TeacherCertificate t) {
		teacherCertificateDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return teacherCertificateDao.getHibeTemplate();
	}

	public TeacherCertificate load(Integer id) {
		return teacherCertificateDao.load(id);
	}

	public TeacherCertificate get(Integer id) {
		return teacherCertificateDao.get(id);
	}

	public List<TeacherCertificate> loadAll() {
		return teacherCertificateDao.loadAll();
	}

	public List<TeacherCertificate> findAll() {
		return teacherCertificateDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return teacherCertificateDao.findByHSQL(querySql);
	}

	public List<TeacherCertificate> getListForPage(String hql, int offset,
			int length) {
		return teacherCertificateDao.getListForPage(hql, offset, length);
	}

	public List<TeacherCertificate> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return teacherCertificateDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<TeacherCertificate> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return teacherCertificateDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		teacherCertificateDao.executeHSQL(hql);
	}

	public List<TeacherCertificate> getListByHSQL(String hql) {
		return teacherCertificateDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return teacherCertificateDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		teacherCertificateDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return teacherCertificateDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return teacherCertificateDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return teacherCertificateDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<TeacherCertificate> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.teacherCertificateDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.teacherCertificateDao.getListCountByParams(params);
	}
	
	
	
}
