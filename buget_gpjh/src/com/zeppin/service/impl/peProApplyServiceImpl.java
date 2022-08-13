package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.peProApplyDao;
import com.zeppin.model.peProApply;
import com.zeppin.service.peProApplyService;

@Service("peProApplyService")
public class peProApplyServiceImpl implements peProApplyService {

	@Autowired
	private peProApplyDao peProAppDao;

	public peProApplyDao getBaseDao() {
		return peProAppDao;
	}

	public void setBaseDao(peProApplyDao peProAppDao) {
		this.peProAppDao = peProAppDao;
	}

	@Override
	public peProApply add(peProApply t) {
		// TODO Auto-generated method stub
		return peProAppDao.add(t);
	}

	@Override
	public void delete(peProApply t) {
		// TODO Auto-generated method stub
		peProAppDao.delete(t);
	}

	@Override
	public peProApply update(peProApply t) {
		// TODO Auto-generated method stub
		return peProAppDao.update(t);
	}

	@Override
	public peProApply get(String id) {
		// TODO Auto-generated method stub
		return peProAppDao.get(id);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return peProAppDao.findByHSQL(querySql);
	}

	@Override
	public List<peProApply> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return peProAppDao.getListForPage(hql, offset, length);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return peProAppDao.getListPage(sql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		peProAppDao.executeHSQL(hql);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return peProAppDao.executeSQL(sql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		peProAppDao.executeSQLUpdate(sql);
	}

	@Override
	public peProApply load(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<peProApply> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<peProApply> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<peProApply> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}

}
