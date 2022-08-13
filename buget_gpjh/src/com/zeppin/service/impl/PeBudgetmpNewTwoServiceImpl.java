/**  
 * @Title: PeBudgetmpServiceImpl.java
 * @Package com.zeppin.service.impl
 * @author jiangfei  
 */
package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PeBudgetmpNewTwoDao;
import com.zeppin.dao.PeBudgetmpNewTwoDetailDao;
import com.zeppin.model.PeBudgetmpNewTwo;
import com.zeppin.model.PeBudgetmpNewTwoDetail;
import com.zeppin.service.PeBudgetmpNewTwoService;

/**
 * @author Administrator
 * 
 */
@Service("peBudgetmpNewTwoService")
public class PeBudgetmpNewTwoServiceImpl implements PeBudgetmpNewTwoService {
	
	@Autowired
	private PeBudgetmpNewTwoDao peBudgetmpNewTwoDao;
	
	@Autowired
	private PeBudgetmpNewTwoDetailDao peBudgetmpNewTwoDetailDao;

	public PeBudgetmpNewTwoDao getPeBudgetmpNewTwoDao() {
		return peBudgetmpNewTwoDao;
	}

	public void setPeBudgetmpNewTwoDao(PeBudgetmpNewTwoDao peBudgetmpNewTwoDao) {
		this.peBudgetmpNewTwoDao = peBudgetmpNewTwoDao;
	}

	public PeBudgetmpNewTwoDetailDao getPeBudgetmpNewTwoDetailDao() {
		return peBudgetmpNewTwoDetailDao;
	}

	public void setPeBudgetmpNewTwoDetailDao(
			PeBudgetmpNewTwoDetailDao peBudgetmpNewTwoDetailDao) {
		this.peBudgetmpNewTwoDetailDao = peBudgetmpNewTwoDetailDao;
	}

	@Override
	public PeBudgetmpNewTwo add(PeBudgetmpNewTwo t) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.add(t);
	}

	@Override
	public void delete(PeBudgetmpNewTwo t) {
		// TODO Auto-generated method stub
		this.peBudgetmpNewTwoDao.delete(t);
	}

	@Override
	public PeBudgetmpNewTwo load(String id) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.load(id);
	}

	@Override
	public PeBudgetmpNewTwo get(String id) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.get(id);
	}

	@Override
	public List<PeBudgetmpNewTwo> loadAll() {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.loadAll();
	}

	@Override
	public List<PeBudgetmpNewTwo> findAll() {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.findAll();
	}

	@Override
	public PeBudgetmpNewTwo update(PeBudgetmpNewTwo t) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.update(t);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.findByHSQL(querySql);
	}

	@Override
	public List<PeBudgetmpNewTwo> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.getListByHSQL(hql);
	}

	@Override
	public List<PeBudgetmpNewTwo> getListForPage(String hql, int pageId,
			int pageSize) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.getListForPage(hql, pageId, pageSize);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.executeSQL(sql);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.peBudgetmpNewTwoDao.executeHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		this.peBudgetmpNewTwoDao.executeSQLUpdate(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.getListPage(sql, offset, length);
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return this.peBudgetmpNewTwoDao.queryRowCount(hql);
	}

	/**
	 * 提交/更新预算
	 * @param isadd
	 * @param peFeeBudgetNewTwoDetail
	 * @param peFeeBudget
	 * @return
	 */
	@Override
	public int addAndUpdate(boolean isadd,
			PeBudgetmpNewTwoDetail peFeeBudgetNewTwoDetail, PeBudgetmpNewTwo peFeeBudget) {
		// TODO Auto-generated method stub
		try {
			if (isadd) {
				this.getPeBudgetmpNewTwoDetailDao().add(peFeeBudgetNewTwoDetail);
				peFeeBudget.setPeFeeBudgetNewTwoDetail(peFeeBudgetNewTwoDetail);
				this.getPeBudgetmpNewTwoDao().add(peFeeBudget);
				return 1;
			} else {
				this.getPeBudgetmpNewTwoDetailDao().update(peFeeBudgetNewTwoDetail);
				this.getPeBudgetmpNewTwoDao().update(peFeeBudget);
				return 1;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
	}

}
