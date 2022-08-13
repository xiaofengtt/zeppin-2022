package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingCertificateDao;
import cn.zeppin.entity.TeacherTrainingCertificate;
import cn.zeppin.service.ITeacherTrainingCertificateService;

public class TeacherTrainingCertificateServiceImpl extends
		BaseServiceImpl<TeacherTrainingCertificate, Integer> implements ITeacherTrainingCertificateService {
	
	private ITeacherTrainingCertificateDao iTeacherTrainingCertificateDao;
	
	
	public ITeacherTrainingCertificateDao getiTeacherTrainingCertificateDao() {
		return iTeacherTrainingCertificateDao;
	}

	public void setiTeacherTrainingCertificateDao(
			ITeacherTrainingCertificateDao iTeacherTrainingCertificateDao) {
		this.iTeacherTrainingCertificateDao = iTeacherTrainingCertificateDao;
	}

	@Override
	public TeacherTrainingCertificate add(TeacherTrainingCertificate t) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.add(t);
	}

	@Override
	public TeacherTrainingCertificate update(TeacherTrainingCertificate t) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.update(t);
	}

	@Override
	public void delete(TeacherTrainingCertificate t) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingCertificateDao.delete(t);
	}

	@Override
	public TeacherTrainingCertificate load(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.load(id);
	}

	@Override
	public TeacherTrainingCertificate get(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.get(id);
	}

	@Override
	public List<TeacherTrainingCertificate> loadAll() {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.loadAll();
	}

	@Override
	public List<TeacherTrainingCertificate> findAll() {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.findByHSQL(querySql);
	}

	@Override
	public List<TeacherTrainingCertificate> getListForPage(String hql,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingCertificateDao.executeHSQL(hql);
	}

	@Override
	public List<TeacherTrainingCertificate> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.getListByHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingCertificateDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<TeacherTrainingCertificate> getListForPage(String hql,
			int start, int length, Object[] object) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.getListForPage(hql, start, length, object);
	}

	@Override
	public List<TeacherTrainingCertificate> getTeacherCertificateListByParams(
			Map<String, Object> params, Map<String, Object> sortParams,
			int offset, int limit) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.getTeacherCertificateListByParams(params, sortParams, offset, limit);
	}

	@Override
	public int getTeacherCertificateListByParams(Map<String, Object> params,
			Map<String, Object> sortParams) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingCertificateDao.getTeacherCertificateListByParams(params, sortParams);
	}

}
