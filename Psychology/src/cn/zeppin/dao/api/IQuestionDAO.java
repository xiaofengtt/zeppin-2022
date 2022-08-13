package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Question;

public interface IQuestionDAO extends IBaseDAO<Question, Integer> {
	
	List<Question> getQuestionByMap(Map<String,String> map ,int offset,int length);
	
}
