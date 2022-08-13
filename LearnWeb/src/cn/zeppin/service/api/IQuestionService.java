package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Question;

public interface IQuestionService {

	void addQuestion(Question q);
	
	void updateQuestion(Question q);
	
	Question getQuestion(int qid);
	
	List<Question> getQuestionByMap(Map<String,String> map ,int offset,int length);
	
}
