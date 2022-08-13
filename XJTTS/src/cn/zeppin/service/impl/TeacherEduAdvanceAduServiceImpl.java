package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.ITeacherEduAdvanceAduDao;
import cn.zeppin.entity.TeacherEduAdvanceAdu;
import cn.zeppin.service.ITeacherEduAdvanceAduService;

@SuppressWarnings("rawtypes")
public class TeacherEduAdvanceAduServiceImpl extends
		BaseServiceImpl<TeacherEduAdvanceAdu, Integer> implements
		ITeacherEduAdvanceAduService {

	private ITeacherEduAdvanceAduDao teacherEduAdvanceAduDao;


	public ITeacherEduAdvanceAduDao getTeacherEduAdvanceAduDao() {
		return teacherEduAdvanceAduDao;
	}

	public void setTeacherEduAdvanceAduDao(ITeacherEduAdvanceAduDao teacherEduAdvanceAduDao) {
		this.teacherEduAdvanceAduDao = teacherEduAdvanceAduDao;
	}

	public TeacherEduAdvanceAdu add(TeacherEduAdvanceAdu t) {
		return teacherEduAdvanceAduDao.add(t);
	}

	public TeacherEduAdvanceAdu update(TeacherEduAdvanceAdu t) {
		return teacherEduAdvanceAduDao.update(t);
	}

	public void delete(TeacherEduAdvanceAdu t) {
		teacherEduAdvanceAduDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return teacherEduAdvanceAduDao.getHibeTemplate();
	}

	public TeacherEduAdvanceAdu load(Integer id) {
		return teacherEduAdvanceAduDao.load(id);
	}

	public TeacherEduAdvanceAdu get(Integer id) {
		return teacherEduAdvanceAduDao.get(id);
	}

	public List<TeacherEduAdvanceAdu> loadAll() {
		return teacherEduAdvanceAduDao.loadAll();
	}

	public List<TeacherEduAdvanceAdu> findAll() {
		return teacherEduAdvanceAduDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return teacherEduAdvanceAduDao.findByHSQL(querySql);
	}

	public List<TeacherEduAdvanceAdu> getListForPage(String hql, int offset,
			int length) {
		return teacherEduAdvanceAduDao.getListForPage(hql, offset, length);
	}

	public List<TeacherEduAdvanceAdu> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return teacherEduAdvanceAduDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<TeacherEduAdvanceAdu> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return teacherEduAdvanceAduDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		teacherEduAdvanceAduDao.executeHSQL(hql);
	}

	public List<TeacherEduAdvanceAdu> getListByHSQL(String hql) {
		return teacherEduAdvanceAduDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return teacherEduAdvanceAduDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		teacherEduAdvanceAduDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return teacherEduAdvanceAduDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return teacherEduAdvanceAduDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return teacherEduAdvanceAduDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<TeacherEduAdvanceAdu> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.teacherEduAdvanceAduDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.teacherEduAdvanceAduDao.getListCountByParams(params);
	}
	
	
	
}
