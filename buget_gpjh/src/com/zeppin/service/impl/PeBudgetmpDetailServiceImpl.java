/**  
 * @Title: PeBudgetmpDetailServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeBudgetmpDetailDao;
import com.zeppin.model.PeBudgetmpDetail;
import com.zeppin.service.PeBudgetmpDetailService;

/**
 * @author Administrator
 * 
 */
@Service("peBudgetmpDetailService")
public class PeBudgetmpDetailServiceImpl implements PeBudgetmpDetailService {

	@Autowired
	private PeBudgetmpDetailDao peBudgetmpDetailDao;

	public PeBudgetmpDetailDao getPeBudgetmpDetailDao() {
		return peBudgetmpDetailDao;
	}

	public void setPeBudgetmpDetailDao(PeBudgetmpDetailDao peBudgetmpDetailDao) {
		this.peBudgetmpDetailDao = peBudgetmpDetailDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public PeBudgetmpDetail add(PeBudgetmpDetail t) {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.add(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(PeBudgetmpDetail t) {
		// TODO Auto-generated method stub
		peBudgetmpDetailDao.delete(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#load(java.io.Serializable)
	 */
	@Override
	public PeBudgetmpDetail load(String id) {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.load(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#get(java.io.Serializable)
	 */
	@Override
	public PeBudgetmpDetail get(String id) {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#loadAll()
	 */
	@Override
	public List<PeBudgetmpDetail> loadAll() {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.loadAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#findAll()
	 */
	@Override
	public List<PeBudgetmpDetail> findAll() {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public PeBudgetmpDetail update(PeBudgetmpDetail t) {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#findByHSQL(java.lang.String)
	 */
	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.findByHSQL(querySql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#getListByHSQL(java.lang.String)
	 */
	@Override
	public List<PeBudgetmpDetail> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.getListByHSQL(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#getListForPage(java.lang.String, int,
	 * int)
	 */
	@Override
	public List<PeBudgetmpDetail> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return peBudgetmpDetailDao.getListForPage(hql, pageId, pageSize);
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
		return peBudgetmpDetailDao.executeSQL(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#executeHSQL(java.lang.String)
	 */
	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		peBudgetmpDetailDao.executeHSQL(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#executeSQLUpdate(java.lang.String)
	 */
	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		peBudgetmpDetailDao.executeSQLUpdate(sql);
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
		return peBudgetmpDetailDao.getListPage(sql, offset, length);
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
