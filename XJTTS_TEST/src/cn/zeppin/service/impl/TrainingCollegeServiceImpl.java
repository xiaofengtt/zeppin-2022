package cn.zeppin.service.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.ITrainingCollegeDao;
import cn.zeppin.dao.impl.TrainingCollegeDaoImpl;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.service.ITrainingCollegeService;

public class TrainingCollegeServiceImpl extends BaseServiceImpl<TrainingCollege, Integer> implements ITrainingCollegeService {

	private ITrainingCollegeDao trainingCollegeDao;

	public ITrainingCollegeDao getTrainingCollegeDao() {
		return trainingCollegeDao;
	}
	
	public List<TrainingCollege> getTrainingCollegeListForRange(){
		return trainingCollegeDao.getTrainingCollegeListForRange();
	}

	public List<TrainingCollege> getTrainingCollegeByName(String name){
		return trainingCollegeDao.getTrainingCollegeByName(name);
	}
	
	public void setTrainingCollegeDao(ITrainingCollegeDao trainingCollegeDao) {
		this.trainingCollegeDao = trainingCollegeDao;
	}

	@Override
	public TrainingCollege add(TrainingCollege t) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.add(t);
	}

	@Override
	public TrainingCollege update(TrainingCollege t) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.update(t);
	}

	@Override
	public void delete(TrainingCollege t) {
		// TODO Auto-generated method stub
		trainingCollegeDao.delete(t);
	}

	@Override
	public TrainingCollege load(Integer id) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.load(id);
	}

	@Override
	public TrainingCollege get(Integer id) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.get(id);
	}

	@Override
	public List<TrainingCollege> loadAll() {
		// TODO Auto-generated method stub
		return trainingCollegeDao.loadAll();
	}

	@Override
	public List<TrainingCollege> findAll() {
		// TODO Auto-generated method stub
		return trainingCollegeDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.findByHSQL(querySql);
	}

	@Override
	public List<TrainingCollege> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		trainingCollegeDao.executeHSQL(hql);
	}

	@Override
	public List<TrainingCollege> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		trainingCollegeDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public int checkUserInfo(Object[] pars) {
		// TODO Auto-generated method stub
		return trainingCollegeDao.checkUserInfo(pars);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingCollege> findByName(String value) {

		return this.trainingCollegeDao.findByName(value);

	}

	@Override
	public List<TrainingCollege> findByStatus(short status) {
		
		String hql = "from TrainingCollege t where t.status=" + status;
		return this.getListByHSQL(hql);
		
	}

	@Override
	public List<TrainingCollege> checkTrainingCollegeToken(String token) {
		// TODO Auto-generated method stub
		return this.trainingCollegeDao.getTrainingCollegeByToken(token);
	}

}
