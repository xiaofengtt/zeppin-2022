package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.ITeacherEduAdvanceDao;
import cn.zeppin.entity.TeacherEduAdvance;
import cn.zeppin.service.ITeacherEduAdvanceService;

@SuppressWarnings("rawtypes")
public class TeacherEduAdvanceServiceImpl extends
		BaseServiceImpl<TeacherEduAdvance, Integer> implements
		ITeacherEduAdvanceService {

	private ITeacherEduAdvanceDao teacherEduAdvanceDao;


	public ITeacherEduAdvanceDao getTeacherEduAdvanceDao() {
		return teacherEduAdvanceDao;
	}

	public void setTeacherEduAdvanceDao(ITeacherEduAdvanceDao teacherEduAdvanceDao) {
		this.teacherEduAdvanceDao = teacherEduAdvanceDao;
	}

	public TeacherEduAdvance add(TeacherEduAdvance t) {
		return teacherEduAdvanceDao.add(t);
	}

	public TeacherEduAdvance update(TeacherEduAdvance t) {
		return teacherEduAdvanceDao.update(t);
	}

	public void delete(TeacherEduAdvance t) {
		teacherEduAdvanceDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return teacherEduAdvanceDao.getHibeTemplate();
	}

	public TeacherEduAdvance load(Integer id) {
		return teacherEduAdvanceDao.load(id);
	}

	public TeacherEduAdvance get(Integer id) {
		return teacherEduAdvanceDao.get(id);
	}

	public List<TeacherEduAdvance> loadAll() {
		return teacherEduAdvanceDao.loadAll();
	}

	public List<TeacherEduAdvance> findAll() {
		return teacherEduAdvanceDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return teacherEduAdvanceDao.findByHSQL(querySql);
	}

	public List<TeacherEduAdvance> getListForPage(String hql, int offset,
			int length) {
		return teacherEduAdvanceDao.getListForPage(hql, offset, length);
	}

	public List<TeacherEduAdvance> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return teacherEduAdvanceDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<TeacherEduAdvance> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return teacherEduAdvanceDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		teacherEduAdvanceDao.executeHSQL(hql);
	}

	public List<TeacherEduAdvance> getListByHSQL(String hql) {
		return teacherEduAdvanceDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return teacherEduAdvanceDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		teacherEduAdvanceDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return teacherEduAdvanceDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return teacherEduAdvanceDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return teacherEduAdvanceDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<TeacherEduAdvance> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.teacherEduAdvanceDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.teacherEduAdvanceDao.getListCountByParams(params);
	}
	
	
	
}
