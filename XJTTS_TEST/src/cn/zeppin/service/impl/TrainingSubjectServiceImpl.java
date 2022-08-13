package cn.zeppin.service.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ITrainingSubjectDao;
import cn.zeppin.dao.impl.TrainingSubjectDaoImpl;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.ITrainingSubjectService;

public class TrainingSubjectServiceImpl extends BaseServiceImpl<TrainingSubject, Short> implements ITrainingSubjectService {

	private ITrainingSubjectDao trainingSubjectDao;

	public ITrainingSubjectDao getTrainingSubjectDao() {
		return trainingSubjectDao;
	}

	public void setTrainingSubjectDao(ITrainingSubjectDao trainingSubjectDao) {
		this.trainingSubjectDao = trainingSubjectDao;
	}

	public List<TrainingSubject> getTrainingSubjectList(){
		return trainingSubjectDao.getTrainingSubjectList();
	}
	
	@Override
	public TrainingSubject add(TrainingSubject t) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.add(t);
	}

	@Override
	public TrainingSubject update(TrainingSubject t) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.update(t);
	}

	@Override
	public void delete(TrainingSubject t) {
		// TODO Auto-generated method stub
		trainingSubjectDao.delete(t);
	}

	@Override
	public TrainingSubject load(Short id) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.load(id);
	}

	@Override
	public TrainingSubject get(Short id) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.get(id);
	}

	@Override
	public List<TrainingSubject> loadAll() {
		// TODO Auto-generated method stub
		return trainingSubjectDao.loadAll();
	}

	@Override
	public List<TrainingSubject> findAll() {
		// TODO Auto-generated method stub
		return trainingSubjectDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.findByHSQL(querySql);
	}

	@Override
	public List<TrainingSubject> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		trainingSubjectDao.executeHSQL(hql);
	}

	@Override
	public List<TrainingSubject> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.getListByHSQL(hql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		trainingSubjectDao.executeSQLUpdate(sql, objParas);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return trainingSubjectDao.getListPage(sql, offset, length, objParas);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingSubject> findByName(String value) {
		// TODO Auto-generated method stub
		return this.trainingSubjectDao.findByName(value);

	}

}
