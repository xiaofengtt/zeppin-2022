package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.SubjectDao;
import com.gpjh.model.Subject;
import com.gpjh.service.SubjectService;

@Service("subjectService")
public class SubjectServiceImpl extends BaseServiceImpl<Subject, String> implements SubjectService {

    @Autowired
	private SubjectDao subjectDao;

	@Override
	public Subject add(Subject t) {
		// TODO Auto-generated method stub
		return subjectDao.add(t);
	}

	@Override
	public void delete(Subject t) {
		// TODO Auto-generated method stub
		subjectDao.delete(t);
	}

	@Override
	public Subject load(String id) {
		// TODO Auto-generated method stub
		return subjectDao.load(id);
	}

	@Override
	public List<Subject> loadAll() {
		// TODO Auto-generated method stub
		return subjectDao.loadAll();
	}

	@Override
	public List<Subject> findAll() {
		// TODO Auto-generated method stub
		return subjectDao.findAll();
	}
	
	@Override
	public Subject update(Subject t) {
		// TODO Auto-generated method stub
		return subjectDao.update(t);
	}

	@Override
	public Subject get(String id) {
		// TODO Auto-generated method stub
		return subjectDao.get(id);
	}

    @Override
    public List<Object> findByHSQL(String querySql){
    	return subjectDao.findByHSQL(querySql);
    }
    
	@Override
	public List<Subject> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return subjectDao.getListForPage(hql, pageId, pageSize);
	}
}
