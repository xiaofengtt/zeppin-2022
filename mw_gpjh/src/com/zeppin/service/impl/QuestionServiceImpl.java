package com.zeppin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeppin.dao.QuestionDao;
import com.zeppin.model.Question;
import com.zeppin.service.QuestionService;

@Service("questionService")
public class QuestionServiceImpl  implements QuestionService {

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
	public Question load(Integer id) {
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
	public Question get(Integer id) {
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
		for (Object obj : list) {
			ts.add((Question) obj);
		}
		return ts;
	}

	@Override
	public void deleteByPaperId(int id) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append("delete Question where paper=");
		hql.append(id);
		questionDao.executeHSQL(hql.toString());
	}

	@Override
	public List<Object> executeSQL(String querySql) {
		return questionDao.executeSQL(querySql);
	}

	public void executeHSQL(final String hql) {
		questionDao.executeHSQL(hql);
	}

	public void executeSQLUpdate(final String sql) {

		questionDao.executeSQLUpdate(sql);

	}

	public List getListPage(final String sql, final int offset, final int length) {

		return questionDao.getListPage(sql, offset, length);
	}

	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryRowCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}
}
