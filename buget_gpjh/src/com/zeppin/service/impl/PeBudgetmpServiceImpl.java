/**  
 * @Title: PeBudgetmpServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeBudgetmpDao;
import com.zeppin.model.PeBudgetmp;
import com.zeppin.service.PeBudgetmpService;

/**
 * @author Administrator
 * 
 */
@Service("peBudgetmpService")
public class PeBudgetmpServiceImpl implements PeBudgetmpService {

	@Autowired
	private PeBudgetmpDao peBudgetmpDao;

	public PeBudgetmpDao getPeBudgetmpDao() {
		return peBudgetmpDao;
	}

	public void setPeBudgetmpDao(PeBudgetmpDao peBudgetmpDao) {
		this.peBudgetmpDao = peBudgetmpDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#add(java.lang.Object)
	 */
	@Override
	public PeBudgetmp add(PeBudgetmp t) {
		// TODO Auto-generated method stub
		return peBudgetmpDao.add(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(PeBudgetmp t) {
		// TODO Auto-generated method stub
		peBudgetmpDao.delete(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#load(java.io.Serializable)
	 */
	@Override
	public PeBudgetmp load(String id) {
		// TODO Auto-generated method stub
		return peBudgetmpDao.load(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#get(java.io.Serializable)
	 */
	@Override
	public PeBudgetmp get(String id) {
		// TODO Auto-generated method stub
		return peBudgetmpDao.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#loadAll()
	 */
	@Override
	public List<PeBudgetmp> loadAll() {
		// TODO Auto-generated method stub
		return peBudgetmpDao.loadAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#findAll()
	 */
	@Override
	public List<PeBudgetmp> findAll() {
		// TODO Auto-generated method stub
		return peBudgetmpDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public PeBudgetmp update(PeBudgetmp t) {
		// TODO Auto-generated method stub
		return peBudgetmpDao.update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#findByHSQL(java.lang.String)
	 */
	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return peBudgetmpDao.findByHSQL(querySql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#getListByHSQL(java.lang.String)
	 */
	@Override
	public List<PeBudgetmp> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return peBudgetmpDao.getListByHSQL(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#getListForPage(java.lang.String, int,
	 * int)
	 */
	@Override
	public List<PeBudgetmp> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return peBudgetmpDao.getListForPage(hql, pageId, pageSize);
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
		return peBudgetmpDao.executeSQL(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#executeHSQL(java.lang.String)
	 */
	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		peBudgetmpDao.executeHSQL(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeppin.service.BaseService#executeSQLUpdate(java.lang.String)
	 */
	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		peBudgetmpDao.executeSQLUpdate(sql);
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
		return peBudgetmpDao.getListPage(sql, offset, length);
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
