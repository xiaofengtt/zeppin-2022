package cn.zeppin.service.impl;

import cn.zeppin.dao.IAnswerDao;
import cn.zeppin.entity.Answer;
import cn.zeppin.service.IAnswerService;

public class AnswerServiceImpl extends BaseServiceImpl<Answer, Integer> implements IAnswerService {
	
	private IAnswerDao answerDao;

	public IAnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(IAnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	@Override
	public void deleteByPaperId(int paperid) {
		
		this.getAnswerDao().deleteByPaperId(paperid);
		
	}
	
	

}
