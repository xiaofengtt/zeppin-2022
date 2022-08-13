package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingReplaceDao;
import cn.zeppin.entity.TeacherTrainingReplace;
import cn.zeppin.service.ITeacherTrainingReplaceService;

public class TeacherTrainingReplaceServiceImpl extends
		BaseServiceImpl<TeacherTrainingReplace, Integer> implements
		ITeacherTrainingReplaceService {
	
	private ITeacherTrainingReplaceDao iTeacherTrainingReplaceDao;

	public ITeacherTrainingReplaceDao getiTeacherTrainingReplaceDao() {
		return iTeacherTrainingReplaceDao;
	}

	public void setiTeacherTrainingReplaceDao(
			ITeacherTrainingReplaceDao iTeacherTrainingReplaceDao) {
		this.iTeacherTrainingReplaceDao = iTeacherTrainingReplaceDao;
	}

	@Override
	public TeacherTrainingReplace add(TeacherTrainingReplace t) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.add(t);
	}

	@Override
	public TeacherTrainingReplace update(TeacherTrainingReplace t) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.update(t);
	}

	@Override
	public void delete(TeacherTrainingReplace t) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingReplaceDao.delete(t);
	}

	@Override
	public TeacherTrainingReplace load(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.load(id);
	}

	@Override
	public TeacherTrainingReplace get(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.get(id);
	}

	@Override
	public List<TeacherTrainingReplace> loadAll() {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.loadAll();
	}

	@Override
	public List<TeacherTrainingReplace> findAll() {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.findByHSQL(querySql);
	}

	@Override
	public List<TeacherTrainingReplace> getListForPage(String hql, int offset,
			int length) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingReplaceDao.executeHSQL(hql);
	}

	@Override
	public List<TeacherTrainingReplace> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.getListByHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		this.iTeacherTrainingReplaceDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<TeacherTrainingReplace> getListForPage(String hql, int start,
			int length, Object[] object) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.getListForPage(hql, start, length, object);
	}

	@Override
	public List<TeacherTrainingReplace> getRecordsListByParams(
			Map<String, Object> params, Map<String, Object> sortMap,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.getRecordsListByParams(params, sortMap, offset, length);
	}

	@Override
	public int getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap) {
		// TODO Auto-generated method stub
		return this.iTeacherTrainingReplaceDao.getRecordsListByParams(params, sortMap);
	}
	
	

}
