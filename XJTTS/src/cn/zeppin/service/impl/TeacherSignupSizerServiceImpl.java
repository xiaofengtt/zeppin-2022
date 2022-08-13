package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.ITeacherSignupSizerDao;
import cn.zeppin.entity.TeacherSignupSizer;
import cn.zeppin.service.ITeacherSignupSizerService;

@SuppressWarnings("rawtypes")
public class TeacherSignupSizerServiceImpl extends
		BaseServiceImpl<TeacherSignupSizer, Integer> implements
		ITeacherSignupSizerService {

	private ITeacherSignupSizerDao teacherSignupSizerDao;


	public ITeacherSignupSizerDao getTeacherSignupSizerDao() {
		return teacherSignupSizerDao;
	}

	public void setTeacherSignupSizerDao(
			ITeacherSignupSizerDao teacherSignupSizerDao) {
		this.teacherSignupSizerDao = teacherSignupSizerDao;
	}

	public TeacherSignupSizer add(TeacherSignupSizer t) {
		return teacherSignupSizerDao.add(t);
	}

	public TeacherSignupSizer update(TeacherSignupSizer t) {
		return teacherSignupSizerDao.update(t);
	}

	public void delete(TeacherSignupSizer t) {
		teacherSignupSizerDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return teacherSignupSizerDao.getHibeTemplate();
	}

	public TeacherSignupSizer load(Integer id) {
		return teacherSignupSizerDao.load(id);
	}

	public TeacherSignupSizer get(Integer id) {
		return teacherSignupSizerDao.get(id);
	}

	public List<TeacherSignupSizer> loadAll() {
		return teacherSignupSizerDao.loadAll();
	}

	public List<TeacherSignupSizer> findAll() {
		return teacherSignupSizerDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return teacherSignupSizerDao.findByHSQL(querySql);
	}

	public List<TeacherSignupSizer> getListForPage(String hql, int offset,
			int length) {
		return teacherSignupSizerDao.getListForPage(hql, offset, length);
	}

	public List<TeacherSignupSizer> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return teacherSignupSizerDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<TeacherSignupSizer> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return teacherSignupSizerDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		teacherSignupSizerDao.executeHSQL(hql);
	}

	public List<TeacherSignupSizer> getListByHSQL(String hql) {
		return teacherSignupSizerDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return teacherSignupSizerDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		teacherSignupSizerDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return teacherSignupSizerDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return teacherSignupSizerDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return teacherSignupSizerDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<TeacherSignupSizer> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.teacherSignupSizerDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.teacherSignupSizerDao.getListCountByParams(params);
	}
	
	
	
}
