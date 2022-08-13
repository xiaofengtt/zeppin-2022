package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IBaseDao;
import cn.zeppin.dao.ITeachingSubjectDao;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.service.ITeachingSubjectService;

public class TeachingSubjectServiceImpl extends BaseServiceImpl<TeachingSubject, Integer> implements ITeachingSubjectService {
	
	private ITeachingSubjectDao teachingSubjectDao;
	

	public ITeachingSubjectDao getTeachingSubjectDao() {
		return teachingSubjectDao;
	}


	public void setTeachingSubjectDao(ITeachingSubjectDao teachingSubjectDao) {
		this.teachingSubjectDao = teachingSubjectDao;
	}


	@Override
	public TeachingSubject add(TeachingSubject t) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.add(t);
	}


	@Override
	public TeachingSubject update(TeachingSubject t) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.update(t);
	}


	@Override
	public void delete(TeachingSubject t) {
		// TODO Auto-generated method stub
		teachingSubjectDao.delete(t);
	}


	@Override
	public TeachingSubject load(Integer id) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.load(id);
	}


	@Override
	public TeachingSubject get(Integer id) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.get(id);
	}


	@Override
	public List<TeachingSubject> loadAll() {
		// TODO Auto-generated method stub
		return teachingSubjectDao.loadAll();
	}


	@Override
	public List<TeachingSubject> findAll() {
		// TODO Auto-generated method stub
		return teachingSubjectDao.findAll();
	}


	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.findByHSQL(querySql);
	}


	@Override
	public List<TeachingSubject> getListForPage(String hql, int offset,
			int length) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.getListForPage(hql, offset, length);
	}


	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		teachingSubjectDao.executeHSQL(hql);
	}


	@Override
	public List<TeachingSubject> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.getListByHSQL(hql);
	}


	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.getListBySQL(sql, objParas);
	}


	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		teachingSubjectDao.executeSQLUpdate(sql, objParas);
	}


	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return teachingSubjectDao.getListPage(sql, offset, length, objParas);
	}


	@Override
	public void deleteTeachingSubjectByTeacher(int teacher) {
		// TODO Auto-generated method stub
		this.teachingSubjectDao.deleteTeachingSubjectByTeacher(teacher);
	}


	@Override
	public List<TeachingSubject> getListByParams(Map<String, Object> map) {
		return this.teachingSubjectDao.getListByParams(map);
	}

	

}
