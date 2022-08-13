package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.peProApplyNoDao;
import com.zeppin.model.peProApplyNo;
import com.zeppin.service.peProApplyNoService;

@Service("peProApplyNoService")
public class peProApplyNoServiceImpl implements peProApplyNoService {
	
	@Autowired
	private peProApplyNoDao peProApplyNoDao;

	public peProApplyNoDao getBaseDao() {
		return peProApplyNoDao;
	}

	public void setBaseDao(peProApplyNoDao peProApplyNoDao) {
		this.peProApplyNoDao = peProApplyNoDao;
	}


	@Override
	public peProApplyNo add(peProApplyNo t) {
		// TODO Auto-generated method stub
		return peProApplyNoDao.add(t);
	}

	@Override
	public void delete(peProApplyNo t) {
		// TODO Auto-generated method stub
		peProApplyNoDao.delete(t);
	}

	@Override
	public peProApplyNo update(peProApplyNo t) {
		// TODO Auto-generated method stub
		return peProApplyNoDao.update(t);
	}

	@Override
	public peProApplyNo get(String id) {
		// TODO Auto-generated method stub
		return peProApplyNoDao.get(id);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return peProApplyNoDao.findByHSQL(querySql);
	}

	@Override
	public List<peProApplyNo> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return peProApplyNoDao.getListForPage(hql, offset, length);
	}

	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return peProApplyNoDao.getListPage(sql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		peProApplyNoDao.executeHSQL(hql);
		
	}

	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return peProApplyNoDao.executeSQL(sql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		peProApplyNoDao.executeSQLUpdate(sql);
		
	}

	@Override
	public peProApplyNo load(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<peProApplyNo> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<peProApplyNo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<peProApplyNo> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}

}
