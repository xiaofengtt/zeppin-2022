package cn.zeppin.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.zeppin.dao.IBaseDao;
import cn.zeppin.service.IBaseService;

public class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {

	private IBaseDao<T, PK> baseDao;

	public IBaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T add(T t) {
		// TODO Auto-generated method stub
		return baseDao.add(t);
	}

	@Override
	public T update(T t) {
		// TODO Auto-generated method stub
		return baseDao.update(t);
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		baseDao.delete(t);
	}

	@Override
	public T load(PK id) {
		// TODO Auto-generated method stub
		return baseDao.load(id);
	}

	@Override
	public T get(PK id) {
		// TODO Auto-generated method stub
		return baseDao.get(id);
	}

	@Override
	public List<T> loadAll() {
		// TODO Auto-generated method stub
		return baseDao.loadAll();
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return baseDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return baseDao.findByHSQL(querySql);
	}

	@Override
	public List<T> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return baseDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		baseDao.executeHSQL(hql);
	}

	@Override
	public List<T> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return baseDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return baseDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		baseDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return baseDao.getListForPage(sql, offset, length, objParas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.IBaseService#getListForPage(java.lang.String, int,
	 * int, java.lang.Object)
	 */
	@Override
	public List<T> getListForPage(String hql, int start, int length, Object[] object) {
		// TODO Auto-generated method stub
		return baseDao.getListForPage(hql, start, length, object);
	}

}
