package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IGradeDao;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.service.IGradeService;

public class GradeServiceImpl extends BaseServiceImpl<Grade, Short> implements IGradeService {

	private IGradeDao gradeDao;

	public IGradeDao getGradeDao() {
		return gradeDao;
	}

	public void setGradeDao(IGradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	public List<Grade> findByName(String name){
		return gradeDao.findByName(name);
	}
	
	@Override
	public Grade add(Grade t) {
		// TODO Auto-generated method stub
		return gradeDao.add(t);
	}

	@Override
	public Grade update(Grade t) {
		// TODO Auto-generated method stub
		return gradeDao.update(t);
	}

	@Override
	public void delete(Grade t) {
		// TODO Auto-generated method stub
		gradeDao.delete(t);
	}

	@Override
	public Grade load(Short id) {
		// TODO Auto-generated method stub
		return gradeDao.load(id);
	}

	@Override
	public Grade get(Short id) {
		// TODO Auto-generated method stub
		return gradeDao.get(id);
	}

	@Override
	public List<Grade> loadAll() {
		// TODO Auto-generated method stub
		return gradeDao.loadAll();
	}

	@Override
	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		return gradeDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return gradeDao.findByHSQL(querySql);
	}

	@Override
	public List<Grade> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return gradeDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		gradeDao.executeHSQL(hql);
	}

	@Override
	public List<Grade> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return gradeDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return gradeDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		gradeDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return gradeDao.getListPage(sql, offset, length, objParas);
	}
	
	

}
