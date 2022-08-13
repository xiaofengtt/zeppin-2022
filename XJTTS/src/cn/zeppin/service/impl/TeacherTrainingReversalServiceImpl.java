package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingReversalDao;
import cn.zeppin.entity.TeacherTrainingReversal;
import cn.zeppin.service.ITeacherTrainingReversalService;

@SuppressWarnings("rawtypes")
public class TeacherTrainingReversalServiceImpl extends
		BaseServiceImpl<TeacherTrainingReversal, Integer> implements
		ITeacherTrainingReversalService {
	
	private ITeacherTrainingReversalDao iTeacherTrainingReversalDao;

	public ITeacherTrainingReversalDao getiTeacherTrainingReversalDao() {
		return iTeacherTrainingReversalDao;
	}

	public void setiTeacherTrainingReversalDao(
			ITeacherTrainingReversalDao iTeacherTrainingReversalDao) {
		this.iTeacherTrainingReversalDao = iTeacherTrainingReversalDao;
	}
	

	@Override
	public TeacherTrainingReversal add(TeacherTrainingReversal t) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.add(t);
	}

	@Override
	public TeacherTrainingReversal update(TeacherTrainingReversal t) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.update(t);
	}

	@Override
	public void delete(TeacherTrainingReversal t) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingReversalDao.delete(t);
	}

	@Override
	public TeacherTrainingReversal load(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.load(id);
	}

	@Override
	public TeacherTrainingReversal get(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.get(id);
	}

	@Override
	public List<TeacherTrainingReversal> loadAll() {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.loadAll();
	}

	@Override
	public List<TeacherTrainingReversal> findAll() {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.findByHSQL(querySql);
	}

	@Override
	public List<TeacherTrainingReversal> getListForPage(String hql, int offset,
			int length) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingReversalDao.executeHSQL(hql);
	}

	@Override
	public List<TeacherTrainingReversal> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.getListByHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingReversalDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<TeacherTrainingReversal> getListForPage(String hql, int start,
			int length, Object[] object) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.getListForPage(hql, start, length, object);
	}

	@Override
	public List<TeacherTrainingReversal> getRecordsListByParams(
			Map<String, Object> params, Map<String, Object> sortMap,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.getRecordsListByParams(params, sortMap, offset, length);
	}

	@Override
	public int getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReversalDao.getRecordsListByParams(params, sortMap);
	}
	
	

}
