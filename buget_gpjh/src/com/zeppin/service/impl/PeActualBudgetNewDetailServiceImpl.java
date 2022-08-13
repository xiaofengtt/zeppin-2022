/**  
 * @Title: PeActualBudgetDetailServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeActualBudgetNewDetailDao;
import com.zeppin.model.PeActualBudgetNewDetail;
import com.zeppin.service.PeActualBudgetNewDetailService;

/**
 * @author Administrator
 * 
 */
@Service("peActualBudgetNewDetailService")
public class PeActualBudgetNewDetailServiceImpl implements PeActualBudgetNewDetailService {

	@Autowired
	private PeActualBudgetNewDetailDao peActualBudgetNewDetailDao;
	
	
	public PeActualBudgetNewDetailDao getPeActualBudgetNewDetailDao() {
		return peActualBudgetNewDetailDao;
	}

	public void setPeActualBudgetNewDetailDao(
			PeActualBudgetNewDetailDao peActualBudgetNewDetailDao) {
		this.peActualBudgetNewDetailDao = peActualBudgetNewDetailDao;
	}

	@Override
	public PeActualBudgetNewDetail add(PeActualBudgetNewDetail t) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.add(t);
	}

	@Override
	public void delete(PeActualBudgetNewDetail t) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewDetailDao.delete(t);
		
	}

	@Override
	public PeActualBudgetNewDetail load(String id) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.load(id);
	}

	@Override
	public PeActualBudgetNewDetail get(String id) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.get(id);
	}

	@Override
	public List<PeActualBudgetNewDetail> loadAll() {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.loadAll();
	}

	@Override
	public List<PeActualBudgetNewDetail> findAll() {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.findAll();
	}

	@Override
	public PeActualBudgetNewDetail update(PeActualBudgetNewDetail t) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.update(t);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.findByHSQL(querySql);
	}

	@Override
	public List<PeActualBudgetNewDetail> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.getListByHSQL(hql);
	}

	@Override
	public List<PeActualBudgetNewDetail> getListForPage(String hql, int pageId,
			int pageSize) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.getListForPage(hql, pageId, pageSize);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.executeSQL(sql);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewDetailDao.executeHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewDetailDao.executeSQLUpdate(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return this.getListPage(sql, offset, length);
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewDetailDao.queryRowCount(hql);
	}

//	@Autowired
//	private PeActualBudgetDetailDao peActualBudgetDetailDao;
//
//	public PeActualBudgetDetailDao getPeActualBudgetDetailDao() {
//		return peActualBudgetDetailDao;
//	}
//
//	public void setPeActualBudgetDetailDao(PeActualBudgetDetailDao peActualBudgetDetailDao) {
//		this.peActualBudgetDetailDao = peActualBudgetDetailDao;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#add(java.lang.Object)
//	 */
//	@Override
//	public PeActualBudgetDetail add(PeActualBudgetDetail t) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.add(t);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#delete(java.lang.Object)
//	 */
//	@Override
//	public void delete(PeActualBudgetDetail t) {
//		// TODO Auto-generated method stub
//		peActualBudgetDetailDao.delete(t);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#load(java.io.Serializable)
//	 */
//	@Override
//	public PeActualBudgetDetail load(String id) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.load(id);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#get(java.io.Serializable)
//	 */
//	@Override
//	public PeActualBudgetDetail get(String id) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.get(id);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#loadAll()
//	 */
//	@Override
//	public List<PeActualBudgetDetail> loadAll() {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.loadAll();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#findAll()
//	 */
//	@Override
//	public List<PeActualBudgetDetail> findAll() {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.findAll();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#update(java.lang.Object)
//	 */
//	@Override
//	public PeActualBudgetDetail update(PeActualBudgetDetail t) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.update(t);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#findByHSQL(java.lang.String)
//	 */
//	@Override
//	public List<Object> findByHSQL(String querySql) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.findByHSQL(querySql);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#getListByHSQL(java.lang.String)
//	 */
//	@Override
//	public List<PeActualBudgetDetail> getListByHSQL(String hql) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.getListByHSQL(hql);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#getListForPage(java.lang.String, int,
//	 * int)
//	 */
//	@Override
//	public List<PeActualBudgetDetail> getListForPage(String hql, int pageId, int pageSize) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.getListForPage(hql, pageId, pageSize);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#executeSQL(java.lang.String)
//	 */
//	@SuppressWarnings("rawtypes")
//	@Override
//	public List executeSQL(String sql) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.executeSQL(sql);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#executeHSQL(java.lang.String)
//	 */
//	@Override
//	public void executeHSQL(String hql) {
//		// TODO Auto-generated method stub
//		peActualBudgetDetailDao.executeHSQL(hql);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#executeSQLUpdate(java.lang.String)
//	 */
//	@Override
//	public void executeSQLUpdate(String sql) {
//		// TODO Auto-generated method stub
//		peActualBudgetDetailDao.executeHSQL(sql);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#getListPage(java.lang.String, int,
//	 * int)
//	 */
//	@SuppressWarnings("rawtypes")
//	@Override
//	public List getListPage(String sql, int offset, int length) {
//		// TODO Auto-generated method stub
//		return peActualBudgetDetailDao.getListPage(sql, offset, length);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.zeppin.service.BaseService#queryRowCount(java.lang.String)
//	 */
//	@Override
//	public int queryRowCount(String hql) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
