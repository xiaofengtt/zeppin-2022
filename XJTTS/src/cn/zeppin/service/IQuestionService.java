package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.Question;

public interface IQuestionService extends IBaseService<Question, Integer> {
	public void deleteByPaperId(int paperid);
	
	public List getPsqQuestion(int paperid);
	
	public List getPsqNotCountQuestion(int paperid);
}
