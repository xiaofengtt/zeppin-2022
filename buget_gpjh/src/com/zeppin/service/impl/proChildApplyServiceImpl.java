package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.proChildApplyDao;
import com.zeppin.model.proChildApply;
import com.zeppin.service.proChildApplyService;

@Service("proChildApplyService")
public class proChildApplyServiceImpl implements proChildApplyService {

	@Autowired
	private proChildApplyDao proChildApplyDao;

	public proChildApplyDao getProChildApplyDao() {
		return proChildApplyDao;
	}

	public void setProChildApplyDao(proChildApplyDao proChildApplyDao) {
		this.proChildApplyDao = proChildApplyDao;
	}

	@Override
	public proChildApply add(proChildApply t) {
		// TODO Auto-generated method stub
		return proChildApplyDao.add(t);
	}

	@Override
	public void delete(proChildApply t) {
		// TODO Auto-generated method stub
		proChildApplyDao.delete(t);
	}

	@Override
	public proChildApply update(proChildApply t) {
		// TODO Auto-generated method stub
		return proChildApplyDao.update(t);
	}

	@Override
	public proChildApply get(String id) {
		// TODO Auto-generated method stub
		return proChildApplyDao.get(id);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return proChildApplyDao.findByHSQL(querySql);
	}

	@Override
	public List<proChildApply> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return proChildApplyDao.getListForPage(hql, offset, length);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return proChildApplyDao.getListPage(sql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		proChildApplyDao.executeHSQL(hql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return proChildApplyDao.executeSQL(sql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		proChildApplyDao.executeSQLUpdate(sql);
	}

	@Override
	public proChildApply load(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<proChildApply> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<proChildApply> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<proChildApply> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return proChildApplyDao.getListByHSQL(hql);
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}

}
