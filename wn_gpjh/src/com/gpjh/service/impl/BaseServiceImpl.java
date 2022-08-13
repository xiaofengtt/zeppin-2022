package com.gpjh.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gpjh.dao.BaseDao;
import com.gpjh.service.BaseService;

/**
 * * The base interface implement for all the service layer interface
 * implements,it provide common method
 * 
 * @author HongLiang
 * 
 * @param <T>
 *            The entity class type
 * 
 * @param <PK>
 *            The primary key of the entity class
 * 
 */
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T add(T t) {
		return baseDao.add(t);
	}

	@Override
	public void delete(T t) {
		baseDao.delete(t);
	}

	@Override
	public T load(PK id) {
		return baseDao.load(id);
	}

	@Override
	public List<T> loadAll() {
		return baseDao.loadAll();
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public T update(T t) {
		return baseDao.update(t);
	}

	@Override
	public T get(PK id) {
		return baseDao.get(id);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		return baseDao.findByHSQL(querySql);
	}

	@Override
	public List<T> getListForPage(String hql, int pageId, int pageSize) {
		return baseDao.getListForPage(hql, pageId, pageSize);
	}

	@Override
	public List<T> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		List<T> ts = new ArrayList<T>();
		List<Object> list = baseDao.findByHSQL(hql);
		for (Object obj : list) {
			ts.add((T) obj);
		}
		return ts;
	}

	public List executeSQL(final String sql) {
		return baseDao.executeSQL(sql);
	}

	public void executeHSQL(final String hql) {
		baseDao.executeHSQL(hql);
	}
	
	public void executeSQLUpdate(final String sql){
		
		baseDao.executeSQLUpdate(sql);
		
	}
	
	public List getListPage(final String sql, final int offset, final int length){		
		return baseDao.getListPage(sql, offset, length);
	}

	@Override
	public int queryRowCount(final String hql) {
		return baseDao.queryRowCount(hql);
	}

}
