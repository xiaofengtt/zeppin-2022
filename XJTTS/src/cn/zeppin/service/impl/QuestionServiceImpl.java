package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IQuestionDao;
import cn.zeppin.dao.impl.QuestionDaoImpl;
import cn.zeppin.entity.Question;
import cn.zeppin.service.IQuestionService;

public class QuestionServiceImpl extends BaseServiceImpl<Question, Integer> implements IQuestionService {
	
	private IQuestionDao questionDao;

	public IQuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(IQuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public List getPsqQuestion(int paperid){
		return this.getQuestionDao().getPsqQuestion(paperid);
	}
	
	public List getPsqNotCountQuestion(int paperid){
		return this.getQuestionDao().getPsqNotCountQuestion(paperid);
	}
	
	@Override
	public void deleteByPaperId(int paperid) {
		
		this.getQuestionDao().deleteByPaperId(paperid);
		
	}
	
	
	
	

}
