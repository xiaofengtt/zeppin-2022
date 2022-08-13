package cn.zeppin.service.impl;

import java.util.List;



import cn.zeppin.dao.ITrainingAdminAuthorityDao;
import cn.zeppin.entity.TrainingAdminAuthority;
import cn.zeppin.service.ITrainingAdminAuthorityService;

public class TrainingAdminAuthorityServiceImpl extends BaseServiceImpl<TrainingAdminAuthority, Integer> implements ITrainingAdminAuthorityService {

	private ITrainingAdminAuthorityDao TrainingAdminAuthorityDao;

	public ITrainingAdminAuthorityDao getTrainingAdminAuthorityDao() {
		return TrainingAdminAuthorityDao;
	}

	public void setTrainingAdminAuthorityDao(ITrainingAdminAuthorityDao TrainingAdminAuthorityDao) {
		this.TrainingAdminAuthorityDao = TrainingAdminAuthorityDao;
	}

	@Override
	public TrainingAdminAuthority add(TrainingAdminAuthority t) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.add(t);
	}

	@Override
	public TrainingAdminAuthority update(TrainingAdminAuthority t) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.update(t);
	}

	@Override
	public void delete(TrainingAdminAuthority t) {
		// TODO Auto-generated method stub
		TrainingAdminAuthorityDao.delete(t);
	}

	@Override
	public TrainingAdminAuthority load(Integer id) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.load(id);
	}

	@Override
	public TrainingAdminAuthority get(Integer id) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.get(id);
	}

	@Override
	public List<TrainingAdminAuthority> loadAll() {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.loadAll();
	}

	@Override
	public List<TrainingAdminAuthority> findAll() {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.findByHSQL(querySql);
	}

	@Override
	public List<TrainingAdminAuthority> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		TrainingAdminAuthorityDao.executeHSQL(hql);
	}

	@Override
	public List<TrainingAdminAuthority> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		TrainingAdminAuthorityDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return TrainingAdminAuthorityDao.getListPage(sql, offset, length, objParas);
	}

}
