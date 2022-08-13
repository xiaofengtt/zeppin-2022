package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.PaperDao;
import com.zeppin.model.Paper;
import com.zeppin.service.PaperService;

@Service("paperService")
public class PaperServiceImpl implements PaperService {
    
    @Autowired
	private PaperDao paperDao;

	@Override
	public Paper add(Paper t) {
		// TODO Auto-generated method stub
		return paperDao.add(t);
	}

	@Override
	public void delete(Paper t) {
		// TODO Auto-generated method stub
		paperDao.delete(t);
	}

	@Override
	public Paper load(Integer id) {
		// TODO Auto-generated method stub
		return paperDao.load(id);
	}

	@Override
	public List<Paper> loadAll() {
		// TODO Auto-generated method stub
		return paperDao.loadAll();
	}

	@Override
	public Paper update(Paper t) {
		// TODO Auto-generated method stub
		return paperDao.update(t);
	}

	@Override
	public Paper get(Integer id) {
		// TODO Auto-generated method stub
		return paperDao.get(id);
	}

	@Override
	public List<Paper> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return paperDao.getListForPage(hql, pageId, pageSize);
	}

	@Override
	public List<Paper> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Paper> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}
		
}
