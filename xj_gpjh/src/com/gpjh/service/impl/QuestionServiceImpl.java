package com.gpjh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.QuestionDao;
import com.gpjh.model.Question;
import com.gpjh.service.QuestionService;

@Service("questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService {
    
    @Autowired
	private QuestionDao questionDao;

	@Override
	public Question add(Question t) {
		// TODO Auto-generated method stub
		return questionDao.add(t);
	}

	@Override
	public void delete(Question t) {
		// TODO Auto-generated method stub
		questionDao.delete(t);
	}

	@Override
	public Question load(String id) {
		// TODO Auto-generated method stub
		return questionDao.load(id);
	}

	@Override
	public List<Question> loadAll() {
		// TODO Auto-generated method stub
		return questionDao.loadAll();
	}

	@Override
	public Question update(Question t) {
		// TODO Auto-generated method stub
		return questionDao.update(t);
	}

	@Override
	public Question get(String id) {
		// TODO Auto-generated method stub
		return questionDao.get(id);
	}

	@Override
	public List<Question> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return questionDao.getListForPage(hql, pageId, pageSize);
	}
	
	@Override
	public List<Question> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		List<Question> ts = new ArrayList<Question>();
		List<Object> list = questionDao.findByHSQL(hql);
		for(Object obj : list) {
			ts.add((Question) obj);
		}
		return ts;
	}

	@Override
	public void deleteByPaperId(String id) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append("delete Question where paper=");
		hql.append(id);
		questionDao.executeHSQL(hql.toString());
	}

    @Override
    public List<Object> executeSQL(String querySql){
    	return questionDao.executeSQL(querySql);
    }
}
