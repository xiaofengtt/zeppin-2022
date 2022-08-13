package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IQuestionDAO;
import cn.zeppin.entity.Question;
import cn.zeppin.service.api.IQuestionService;

public class QuestionService implements IQuestionService {
	
	private IQuestionDAO questionDAO;

	public IQuestionDAO getQuestionDAO() {
		return questionDAO;
	}

	public void setQuestionDAO(IQuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	@Override
	public void addQuestion(Question q) {
		this.questionDAO.save(q);
	}

	@Override
	public List<Question> getQuestionByMap(Map<String, String> map, int offset, int length) {
		return this.questionDAO.getQuestionByMap(map, offset, length);
	}

	@Override
	public Question getQuestion(int qid) {
		return this.questionDAO.get(qid);
	}

	@Override
	public void updateQuestion(Question q) {
		this.questionDAO.update(q);
	}
	
	
}
