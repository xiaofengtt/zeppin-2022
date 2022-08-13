/**  
 * @Title: PeBudgetmpDetailServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeBudgetmpNewTwoDetailDao;
import com.zeppin.model.PeBudgetmpNewTwoDetail;
import com.zeppin.service.PeBudgetmpNewTwoDetailService;

/**
 * @author Administrator
 * 
 */
@Service("peBudgetmpNewTwoDetailService")
public class PeBudgetmpNewTwoDetailServiceImpl implements PeBudgetmpNewTwoDetailService {

	@Autowired
	private PeBudgetmpNewTwoDetailDao peBudgetmpNewTwoDetailDao;
	
	public PeBudgetmpNewTwoDetailDao getPeBudgetmpNewTwoDetailDao() {
		return peBudgetmpNewTwoDetailDao;
	}

	public void setPeBudgetmpNewTwoDetailDao(
			PeBudgetmpNewTwoDetailDao peBudgetmpNewTwoDetailDao) {
		this.peBudgetmpNewTwoDetailDao = peBudgetmpNewTwoDetailDao;
	}

	@Override
	public PeBudgetmpNewTwoDetail add(PeBudgetmpNewTwoDetail t) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.add(t);
	}

	@Override
	public void delete(PeBudgetmpNewTwoDetail t) {
		// TODO Auto-generated method stub
		this.peBudgetmpNewTwoDetailDao.delete(t);
	}

	@Override
	public PeBudgetmpNewTwoDetail load(String id) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.load(id);
	}

	@Override
	public PeBudgetmpNewTwoDetail get(String id) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.get(id);
	}

	@Override
	public List<PeBudgetmpNewTwoDetail> loadAll() {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.loadAll();
	}

	@Override
	public List<PeBudgetmpNewTwoDetail> findAll() {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.findAll();
	}

	@Override
	public PeBudgetmpNewTwoDetail update(PeBudgetmpNewTwoDetail t) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.update(t);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.findByHSQL(querySql);
	}

	@Override
	public List<PeBudgetmpNewTwoDetail> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.getListByHSQL(hql);
	}

	@Override
	public List<PeBudgetmpNewTwoDetail> getListForPage(String hql, int pageId,
			int pageSize) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.getListForPage(hql, pageId, pageSize);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.executeSQL(sql);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.peBudgetmpNewTwoDetailDao.executeHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		this.peBudgetmpNewTwoDetailDao.executeSQLUpdate(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.getListPage(sql, offset, length);
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDetailDao.queryRowCount(hql);
	}

}
