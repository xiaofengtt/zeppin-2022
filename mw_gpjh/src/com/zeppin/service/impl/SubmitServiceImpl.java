package com.zeppin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.SubmitDao;
import com.zeppin.model.Submit;
import com.zeppin.service.SubmitService;

@Service("submitService")
public class SubmitServiceImpl   implements SubmitService {
    
    @Autowired
	private SubmitDao submitDao;

	@Override
	public Submit add(Submit t) {
		// TODO Auto-generated method stub
		return submitDao.add(t);
	}

	@Override
	public void delete(Submit t) {
		// TODO Auto-generated method stub
		submitDao.delete(t);
	}

	@Override
	public Submit load(Integer id) {
		// TODO Auto-generated method stub
		return submitDao.load(id);
	}

	@Override
	public List<Submit> loadAll() {
		// TODO Auto-generated method stub
		return submitDao.loadAll();
	}

	@Override
	public Submit update(Submit t) {
		// TODO Auto-generated method stub
		return submitDao.update(t);
	}

	@Override
	public Submit get(Integer id) {
		// TODO Auto-generated method stub
		return submitDao.get(id);
	}

    @Override
    public List<Object> findByHSQL(String querySql){
    	return submitDao.findByHSQL(querySql);
    }
    
	@Override
	public List<Submit> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return submitDao.getListForPage(hql, pageId, pageSize);
	}
	
	@Override
	public List executeSQL(final String sql) {
		return submitDao.executeSQL(sql);
	}
	
	@Override
	public int queryRowCount(final String hql){
		return submitDao.queryRowCount(hql);
	}

	@Override
	public List<Submit> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Submit> getListByHSQL(String hql) {
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
		submitDao.executeSQLUpdate(sql);
		
	}

	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}
}
