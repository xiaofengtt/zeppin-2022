package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.ITeachingGradeDao;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.service.ITeachingGradeService;

public class TeachingGradeServiceImpl extends BaseServiceImpl<TeachingGrade, Integer> implements ITeachingGradeService {

	private ITeachingGradeDao teachingGradeDao;
	
	public ITeachingGradeDao getTeachingGradeDao() {
		return teachingGradeDao;
	}

	public void setTeachingGradeDao(ITeachingGradeDao teachingGradeDao) {
		this.teachingGradeDao = teachingGradeDao;
	}

	@Override
	public TeachingGrade add(TeachingGrade t) {
		// TODO Auto-generated method stub
		return teachingGradeDao.add(t);
	}

	@Override
	public TeachingGrade update(TeachingGrade t) {
		// TODO Auto-generated method stub
		return teachingGradeDao.update(t);
	}

	@Override
	public void delete(TeachingGrade t) {
		// TODO Auto-generated method stub
		teachingGradeDao.delete(t);
	}

	@Override
	public TeachingGrade load(Integer id) {
		// TODO Auto-generated method stub
		return teachingGradeDao.load(id);
	}

	@Override
	public TeachingGrade get(Integer id) {
		// TODO Auto-generated method stub
		return teachingGradeDao.get(id);
	}

	@Override
	public List<TeachingGrade> loadAll() {
		// TODO Auto-generated method stub
		return teachingGradeDao.loadAll();
	}

	@Override
	public List<TeachingGrade> findAll() {
		// TODO Auto-generated method stub
		return teachingGradeDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return teachingGradeDao.findByHSQL(querySql);
	}

	@Override
	public List<TeachingGrade> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return teachingGradeDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		teachingGradeDao.executeHSQL(hql);
	}

	@Override
	public List<TeachingGrade> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return teachingGradeDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return teachingGradeDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		teachingGradeDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return teachingGradeDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public void deleteTeachingGradeByTeacher(int teacher) {
		// TODO Auto-generated method stub
		this.teachingGradeDao.deleteTeachingGradeByTeacher(teacher);
	}

	

}
