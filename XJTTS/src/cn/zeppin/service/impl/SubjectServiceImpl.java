package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.ISubjectDao;
import cn.zeppin.dao.impl.SubjectDaoImpl;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Subject;
import cn.zeppin.service.ISubjectService;

public class SubjectServiceImpl extends BaseServiceImpl<Subject, Short> implements ISubjectService {

	private ISubjectDao subjectDao;

	public ISubjectDao getSubjectDao() {
		return subjectDao;
	}

	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	public List<Subject> findByName(String name){
		return subjectDao.findByName(name);
	}
	
	@Override
	public Subject add(Subject t) {
		// TODO Auto-generated method stub
		return subjectDao.add(t);
	}

	@Override
	public Subject update(Subject t) {
		// TODO Auto-generated method stub
		return subjectDao.update(t);
	}

	@Override
	public void delete(Subject t) {
		// TODO Auto-generated method stub
		subjectDao.delete(t);
	}

	@Override
	public Subject load(Short id) {
		// TODO Auto-generated method stub
		return subjectDao.load(id);
	}

	@Override
	public Subject get(Short id) {
		// TODO Auto-generated method stub
		return subjectDao.get(id);
	}

	@Override
	public List<Subject> loadAll() {
		// TODO Auto-generated method stub
		return subjectDao.loadAll();
	}

	@Override
	public List<Subject> findAll() {
		// TODO Auto-generated method stub
		return subjectDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return subjectDao.findByHSQL(querySql);
	}

	@Override
	public List<Subject> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return subjectDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		subjectDao.executeHSQL(hql);
	}

	@Override
	public List<Subject> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return subjectDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return subjectDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		subjectDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return subjectDao.getListPage(sql, offset, length, objParas);
	}

}
