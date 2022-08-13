/**  
 * @Title: PeActualBudgetServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeActualBudgetDao;
import com.zeppin.model.PeActualBudget;
import com.zeppin.service.PeActualBudgetService;

/**
 * @author Administrator
 * 
 */
@Service("peActualBudgetService")
public class PeActualBudgetServiceImpl implements PeActualBudgetService {

	@Autowired
	private PeActualBudgetDao peActualBudgetDao;

	public PeActualBudgetDao getPeActualBudgetDao() {
		return peActualBudgetDao;
	}

	public void setPeActualBudgetDao(PeActualBudgetDao peActualBudgetDao) {
		this.peActualBudgetDao = peActualBudgetDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public PeActualBudget add(PeActualBudget t) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.add(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(PeActualBudget t) {
		// TODO Auto-generated method stub
		peActualBudgetDao.delete(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#load(java.io.Serializable)
	 */
	@Override
	public PeActualBudget load(String id) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.load(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#get(java.io.Serializable)
	 */
	@Override
	public PeActualBudget get(String id) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#loadAll()
	 */
	@Override
	public List<PeActualBudget> loadAll() {
		// TODO Auto-generated method stub
		return peActualBudgetDao.loadAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#findAll()
	 */
	@Override
	public List<PeActualBudget> findAll() {
		// TODO Auto-generated method stub
		return peActualBudgetDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public PeActualBudget update(PeActualBudget t) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#findByHSQL(java.lang.String)
	 */
	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.findByHSQL(querySql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#getListByHSQL(java.lang.String)
	 */
	@Override
	public List<PeActualBudget> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.getListByHSQL(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#getListForPage(java.lang.String, int,
	 * int)
	 */
	@Override
	public List<PeActualBudget> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.getListForPage(hql, pageId, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#executeSQL(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.executeSQL(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#executeHSQL(java.lang.String)
	 */
	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		peActualBudgetDao.executeHSQL(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#executeSQLUpdate(java.lang.String)
	 */
	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		peActualBudgetDao.executeSQLUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#getListPage(java.lang.String, int,
	 * int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return peActualBudgetDao.getListPage(sql, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#queryRowCount(java.lang.String)
	 */
	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}

}
