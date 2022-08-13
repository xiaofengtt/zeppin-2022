package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.proChildApplyNoDao;
import com.zeppin.model.proChildApplyNo;
import com.zeppin.service.proChildApplyNoService;

@Service("proChildApplyNoService")
public class proChildApplyNoServiceImpl implements proChildApplyNoService {

	@Autowired
	private proChildApplyNoDao proChildApplyNoDao;

	public proChildApplyNoDao getProChildApplyNoDao() {
		return proChildApplyNoDao;
	}

	public void setProChildApplyNoDao(proChildApplyNoDao proChildApplyNoDao) {
		this.proChildApplyNoDao = proChildApplyNoDao;
	}

	@Override
	public proChildApplyNo add(proChildApplyNo t) {
		// TODO Auto-generated method stub
		return proChildApplyNoDao.add(t);
	}

	@Override
	public void delete(proChildApplyNo t) {
		// TODO Auto-generated method stub
		proChildApplyNoDao.delete(t);
	}

	@Override
	public proChildApplyNo update(proChildApplyNo t) {
		// TODO Auto-generated method stub
		return proChildApplyNoDao.update(t);
	}

	@Override
	public proChildApplyNo get(String id) {
		// TODO Auto-generated method stub
		return proChildApplyNoDao.get(id);
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return proChildApplyNoDao.findByHSQL(querySql);
	}

	@Override
	public List<proChildApplyNo> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return proChildApplyNoDao.getListForPage(hql, offset, length);
	}

	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return proChildApplyNoDao.getListPage(sql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		proChildApplyNoDao.executeHSQL(hql);
	}

	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return proChildApplyNoDao.executeSQL(sql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		proChildApplyNoDao.executeSQLUpdate(sql);
	}

	@Override
	public proChildApplyNo load(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<proChildApplyNo> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<proChildApplyNo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<proChildApplyNo> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}

}
