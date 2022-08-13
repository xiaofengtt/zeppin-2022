package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.SubmitDao;
import com.gpjh.model.Submit;
import com.gpjh.service.SubmitService;

@Service("submitService")
public class SubmitServiceImpl  extends BaseServiceImpl<Submit, Integer> implements SubmitService {
    
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
}
