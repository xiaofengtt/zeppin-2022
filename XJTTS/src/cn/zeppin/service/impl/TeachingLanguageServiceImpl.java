package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeachingLanguageDao;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.service.ITeachingLanguageService;

public class TeachingLanguageServiceImpl extends BaseServiceImpl<TeachingLanguage, Integer> implements ITeachingLanguageService {

	private ITeachingLanguageDao teachingLanguageDao;

	public ITeachingLanguageDao getTeachingLanguageDao() {
		return teachingLanguageDao;
	}

	public void setTeachingLanguageDao(ITeachingLanguageDao teachingLanguageDao) {
		this.teachingLanguageDao = teachingLanguageDao;
	}

	@Override
	public TeachingLanguage add(TeachingLanguage t) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.add(t);
	}

	@Override
	public TeachingLanguage update(TeachingLanguage t) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.update(t);
	}

	@Override
	public void delete(TeachingLanguage t) {
		// TODO Auto-generated method stub
		teachingLanguageDao.delete(t);
	}

	@Override
	public TeachingLanguage load(Integer id) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.load(id);
	}

	@Override
	public TeachingLanguage get(Integer id) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.get(id);
	}

	@Override
	public List<TeachingLanguage> loadAll() {
		// TODO Auto-generated method stub
		return teachingLanguageDao.loadAll();
	}

	@Override
	public List<TeachingLanguage> findAll() {
		// TODO Auto-generated method stub
		return teachingLanguageDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.findByHSQL(querySql);
	}

	@Override
	public List<TeachingLanguage> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.getListForPage(hql, offset, length);
	}

	@Override
	public List<TeachingLanguage> getListForPage(String hql, int offset, int length, Object[] objects) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.getListForPage(hql, offset, length, objects);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		teachingLanguageDao.executeHSQL(hql);
	}

	@Override
	public List<TeachingLanguage> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		teachingLanguageDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return teachingLanguageDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public void deleteTeachingLanguage(int teacher) {
		// TODO Auto-generated method stub
		this.teachingLanguageDao.deleteTeachingLanguage(teacher);
	}

	@Override
	public List<TeachingLanguage> getListByParams(Map<String, Object> map) {
		return this.teachingLanguageDao.getListByParams(map);
	}

}
