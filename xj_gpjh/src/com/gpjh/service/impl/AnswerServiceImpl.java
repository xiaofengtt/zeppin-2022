package com.gpjh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.AnswerDao;
import com.gpjh.model.Answer;
import com.gpjh.service.AnswerService;

@Service("answerService")
public class AnswerServiceImpl  extends BaseServiceImpl<Answer, String> implements AnswerService {
    
    @Autowired
	private AnswerDao answerDao;

	@Override
	public Answer add(Answer t) {
		// TODO Auto-generated method stub
		return answerDao.add(t);
	}

	@Override
	public void delete(Answer t) {
		// TODO Auto-generated method stub
		answerDao.delete(t);
	}

	@Override
	public Answer load(String id) {
		// TODO Auto-generated method stub
		return answerDao.load(id);
	}

	@Override
	public List<Answer> loadAll() {
		// TODO Auto-generated method stub
		return answerDao.loadAll();
	}

	@Override
	public Answer update(Answer t) {
		// TODO Auto-generated method stub
		return answerDao.update(t);
	}

	@Override
	public Answer get(String id) {
		// TODO Auto-generated method stub
		return answerDao.get(id);
	}

	@Override
	public List<Answer> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return answerDao.getListForPage(hql, pageId, pageSize);
	}
	
	@Override
	public List<Answer> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		List<Answer> answers = new ArrayList<Answer>();
		List list = answerDao.findByHSQL(hql);
		for(Object obj : list) {
			answers.add((Answer) obj);
		}
		return answers;
	}

	@Override
	public void deleteByPaperId(String id) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append("delete Answer where paper=");
		hql.append(id);
		answerDao.executeHSQL(hql.toString());
	}
}
