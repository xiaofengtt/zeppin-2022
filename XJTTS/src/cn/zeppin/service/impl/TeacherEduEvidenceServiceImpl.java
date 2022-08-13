package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.ITeacherEduEvidenceDao;
import cn.zeppin.entity.TeacherEduEvidence;
import cn.zeppin.service.ITeacherEduEvidenceService;

@SuppressWarnings("rawtypes")
public class TeacherEduEvidenceServiceImpl extends
		BaseServiceImpl<TeacherEduEvidence, Integer> implements
		ITeacherEduEvidenceService {

	private ITeacherEduEvidenceDao teacherEduEvidenceDao;


	public ITeacherEduEvidenceDao getTeacherEduEvidenceDao() {
		return teacherEduEvidenceDao;
	}

	public void setTeacherEduEvidenceDao(ITeacherEduEvidenceDao teacherEduEvidenceDao) {
		this.teacherEduEvidenceDao = teacherEduEvidenceDao;
	}

	public TeacherEduEvidence add(TeacherEduEvidence t) {
		return teacherEduEvidenceDao.add(t);
	}

	public TeacherEduEvidence update(TeacherEduEvidence t) {
		return teacherEduEvidenceDao.update(t);
	}

	public void delete(TeacherEduEvidence t) {
		teacherEduEvidenceDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return teacherEduEvidenceDao.getHibeTemplate();
	}

	public TeacherEduEvidence load(Integer id) {
		return teacherEduEvidenceDao.load(id);
	}

	public TeacherEduEvidence get(Integer id) {
		return teacherEduEvidenceDao.get(id);
	}

	public List<TeacherEduEvidence> loadAll() {
		return teacherEduEvidenceDao.loadAll();
	}

	public List<TeacherEduEvidence> findAll() {
		return teacherEduEvidenceDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return teacherEduEvidenceDao.findByHSQL(querySql);
	}

	public List<TeacherEduEvidence> getListForPage(String hql, int offset,
			int length) {
		return teacherEduEvidenceDao.getListForPage(hql, offset, length);
	}

	public List<TeacherEduEvidence> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return teacherEduEvidenceDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<TeacherEduEvidence> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return teacherEduEvidenceDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		teacherEduEvidenceDao.executeHSQL(hql);
	}

	public List<TeacherEduEvidence> getListByHSQL(String hql) {
		return teacherEduEvidenceDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return teacherEduEvidenceDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		teacherEduEvidenceDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return teacherEduEvidenceDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return teacherEduEvidenceDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return teacherEduEvidenceDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<TeacherEduEvidence> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.teacherEduEvidenceDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.teacherEduEvidenceDao.getListCountByParams(params);
	}
	
	
	
}
