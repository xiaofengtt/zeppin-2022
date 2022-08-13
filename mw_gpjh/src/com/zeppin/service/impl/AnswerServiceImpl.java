package com.zeppin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.AnswerDao;
import com.zeppin.model.Answer;
import com.zeppin.service.AnswerService;

@Service("answerService")
public class AnswerServiceImpl  implements AnswerService {
    
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
	public Answer load(Integer id) {
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
	public Answer get(Integer id) {
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
	public void deleteByPaperId(int id) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append("delete Answer where paper=");
		hql.append(id);
		answerDao.executeHSQL(hql.toString());
	}

	@Override
	public List<Answer> findAll() {
		// TODO Auto-generated method stub
		return answerDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return answerDao.findByHSQL(querySql);
	}

	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return answerDao.executeSQL(sql);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		answerDao.executeHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		answerDao.executeSQLUpdate(sql);
	}

	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return answerDao.getListPage(sql, offset, length);
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}
}
