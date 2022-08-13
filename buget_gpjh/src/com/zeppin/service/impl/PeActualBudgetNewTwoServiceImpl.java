/**  
 * @Title: PeActualBudgetServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeActualBudgetNewTwoDao;
import com.zeppin.dao.PeActualBudgetNewTwoDetailDao;
import com.zeppin.model.PeActualBudgetNewTwo;
import com.zeppin.model.PeActualBudgetNewTwoDetail;
import com.zeppin.service.PeActualBudgetNewTwoService;

/**
 * @author Administrator
 * 
 */
@Service("peActualBudgetNewTwoService")
public class PeActualBudgetNewTwoServiceImpl implements PeActualBudgetNewTwoService {
	
	@Autowired
	private PeActualBudgetNewTwoDao peActualBudgetNewTwoDao;
	
	@Autowired
	private PeActualBudgetNewTwoDetailDao peActualBudgetNewTwoDetailDao;
	
	
	public PeActualBudgetNewTwoDetailDao getPeActualBudgetNewTwoDetailDao() {
		return peActualBudgetNewTwoDetailDao;
	}

	public void setPeActualBudgetNewTwoDetailDao(
			PeActualBudgetNewTwoDetailDao peActualBudgetNewTwoDetailDao) {
		this.peActualBudgetNewTwoDetailDao = peActualBudgetNewTwoDetailDao;
	}
	
	public PeActualBudgetNewTwoDao getPeActualBudgetNewTwoDao() {
		return peActualBudgetNewTwoDao;
	}

	public void setPeActualBudgetNewTwoDao(PeActualBudgetNewTwoDao peActualBudgetNewTwoDao) {
		this.peActualBudgetNewTwoDao = peActualBudgetNewTwoDao;
	}

	@Override
	public PeActualBudgetNewTwo add(PeActualBudgetNewTwo t) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.add(t);
	}

	@Override
	public void delete(PeActualBudgetNewTwo t) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewTwoDao.delete(t);
	}

	@Override
	public PeActualBudgetNewTwo load(String id) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.load(id);
	}

	@Override
	public PeActualBudgetNewTwo get(String id) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.get(id);
	}

	@Override
	public List<PeActualBudgetNewTwo> loadAll() {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.loadAll();
	}

	@Override
	public List<PeActualBudgetNewTwo> findAll() {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.findAll();
	}

	@Override
	public PeActualBudgetNewTwo update(PeActualBudgetNewTwo t) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.update(t);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.findByHSQL(querySql);
	}

	@Override
	public List<PeActualBudgetNewTwo> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.getListByHSQL(hql);
//		return this.getListByHSQL(hql);
	}

	@Override
	public List<PeActualBudgetNewTwo> getListForPage(String hql, int pageId,
			int pageSize) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.getListForPage(hql, pageId, pageSize);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return this.peActualBudgetNewTwoDao.executeSQL(sql);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewTwoDao.executeHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		this.peActualBudgetNewTwoDao.executeSQLUpdate(sql);
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
		return this.peActualBudgetNewTwoDao.queryRowCount(hql);
	}

	@Override
	public int addAndUpdate(boolean isadd,
			PeActualBudgetNewTwoDetail peFeeBudgetNewTwoDetail,
			PeActualBudgetNewTwo peFeeBudgetNewTwo) {
		// TODO Auto-generated method stub
		try {
			if (isadd) {
				this.getPeActualBudgetNewTwoDetailDao().add(peFeeBudgetNewTwoDetail);
				peFeeBudgetNewTwo.setPeFeeBudgetNewTwoDetail(peFeeBudgetNewTwoDetail);
				this.getPeActualBudgetNewTwoDao().add(peFeeBudgetNewTwo);
				return 1;
			} else {
				this.getPeActualBudgetNewTwoDetailDao().update(peFeeBudgetNewTwoDetail);
				this.getPeActualBudgetNewTwoDao().update(peFeeBudgetNewTwo);
				return 1;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

}
