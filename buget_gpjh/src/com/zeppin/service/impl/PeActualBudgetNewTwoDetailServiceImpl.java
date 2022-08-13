/**  
 * @Title: PeActualBudgetDetailServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeActualBudgetNewTwoDetailDao;
import com.zeppin.model.PeActualBudgetNewTwoDetail;
import com.zeppin.service.PeActualBudgetNewTwoDetailService;

/**
 * @author Administrator
 * 
 */
@Service("peActualBudgetNewTwoDetailService")
public class PeActualBudgetNewTwoDetailServiceImpl implements PeActualBudgetNewTwoDetailService {

	@Autowired
	private PeActualBudgetNewTwoDetailDao peActualBudgetNewTwoDetailDao;
	
	public PeActualBudgetNewTwoDetailDao getPeActualBudgetNewTwoDetailDao() {
		return peActualBudgetNewTwoDetailDao;
	}

	public void setPeActualBudgetNewTwoDetailDao(
			PeActualBudgetNewTwoDetailDao peActualBudgetNewTwoDetailDao) {
		this.peActualBudgetNewTwoDetailDao = peActualBudgetNewTwoDetailDao;
	}

	@Override
	public PeActualBudgetNewTwoDetail add(PeActualBudgetNewTwoDetail t) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.add(t);
	}

	@Override
	public void delete(PeActualBudgetNewTwoDetail t) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewTwoDetailDao.delete(t);
		
	}

	@Override
	public PeActualBudgetNewTwoDetail load(String id) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.load(id);
	}

	@Override
	public PeActualBudgetNewTwoDetail get(String id) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.get(id);
	}

	@Override
	public List<PeActualBudgetNewTwoDetail> loadAll() {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.loadAll();
	}

	@Override
	public List<PeActualBudgetNewTwoDetail> findAll() {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.findAll();
	}

	@Override
	public PeActualBudgetNewTwoDetail update(PeActualBudgetNewTwoDetail t) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.update(t);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.findByHSQL(querySql);
	}

	@Override
	public List<PeActualBudgetNewTwoDetail> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.getListByHSQL(hql);
	}

	@Override
	public List<PeActualBudgetNewTwoDetail> getListForPage(String hql, int pageId,
			int pageSize) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.getListForPage(hql, pageId, pageSize);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDetailDao.executeSQL(sql);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewTwoDetailDao.executeHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewTwoDetailDao.executeSQLUpdate(sql);
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
		return this.peActualBudgetNewTwoDetailDao.queryRowCount(hql);
	}

}
