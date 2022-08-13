package cn.zeppin.service.impl;

import java.util.List;

import org.hibernate.Query;

import cn.zeppin.dao.IBaseDao;
import cn.zeppin.dao.ITrainingAdminDao;
import cn.zeppin.dao.impl.TrainingAdminDaoImpl;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.ITrainingAdminService;

public class TrainingAdminServiceImpl extends BaseServiceImpl<TrainingAdmin, Integer> implements ITrainingAdminService {

	private ITrainingAdminDao trainingAdminDao;

	public ITrainingAdminDao getTrainingAdminDao() {
		return trainingAdminDao;
	}

	public void setTrainingAdminDao(ITrainingAdminDao trainingAdminDao) {
		this.trainingAdminDao = trainingAdminDao;
	}

	@Override
	public TrainingAdmin add(TrainingAdmin t) {
		// TODO Auto-generated method stub
		return trainingAdminDao.add(t);
	}

	@Override
	public TrainingAdmin update(TrainingAdmin t) {
		// TODO Auto-generated method stub
		return trainingAdminDao.update(t);
	}

	@Override
	public void delete(TrainingAdmin t) {
		// TODO Auto-generated method stub
		t.setStatus((short)2);
		trainingAdminDao.update(t);
	}

	@Override
	public TrainingAdmin load(Integer id) {
		// TODO Auto-generated method stub
		return trainingAdminDao.load(id);
	}

	@Override
	public TrainingAdmin get(Integer id) {
		// TODO Auto-generated method stub
		return trainingAdminDao.get(id);
	}

	@Override
	public List<TrainingAdmin> loadAll() {
		// TODO Auto-generated method stub
		return trainingAdminDao.loadAll();
	}

	@Override
	public List<TrainingAdmin> findAll() {
		// TODO Auto-generated method stub
		return trainingAdminDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return trainingAdminDao.findByHSQL(querySql);
	}

	@Override
	public List<TrainingAdmin> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return trainingAdminDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		trainingAdminDao.executeHSQL(hql);
	}

	@Override
	public List<TrainingAdmin> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return trainingAdminDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return trainingAdminDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		trainingAdminDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return trainingAdminDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public int checkUserInfo(Object[] pars) {
		
		return trainingAdminDao.checkUserInfo(pars);
	}

	@Override
	public List<TrainingAdmin> getTrainingAdminByStatus(short status) {
		// TODO Auto-generated method stub

		String hql = "from TrainingAdmin t where 1 = 1 and t.status=" + status;

		return this.getListByHSQL(hql);
	}

}
