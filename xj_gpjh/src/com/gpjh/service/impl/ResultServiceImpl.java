package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.ResultDao;
import com.gpjh.model.Result;
import com.gpjh.service.ResultService;

@Service("resultService")
public class ResultServiceImpl extends BaseServiceImpl<Result, String> implements ResultService {
    
    @Autowired
	private ResultDao resultDao;

	@Override
	public Result add(Result t) {
		// TODO Auto-generated method stub
		return resultDao.add(t);
	}

	@Override
	public void delete(Result t) {
		// TODO Auto-generated method stub
		resultDao.delete(t);
	}

	@Override
	public Result load(String id) {
		// TODO Auto-generated method stub
		return resultDao.load(id);
	}

	@Override
	public List<Result> loadAll() {
		// TODO Auto-generated method stub
		return resultDao.loadAll();
	}

	@Override
	public Result update(Result t) {
		// TODO Auto-generated method stub
		return resultDao.update(t);
	}

	@Override
	public Result get(String id) {
		// TODO Auto-generated method stub
		return resultDao.get(id);
	}

    @Override
    public List<Object> findByHSQL(String querySql){
    	return resultDao.findByHSQL(querySql);
    }
    
	@Override
	public List<Result> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return resultDao.getListForPage(hql, pageId, pageSize);
	}
	
	@Override
	public List<Result> getListByHSQL(String hql) {
		return resultDao.getListByHSQL(hql);
	}

    @Override
    public List<Object> executeSQL(String querySql){
    	return resultDao.executeSQL(querySql);
    }
}