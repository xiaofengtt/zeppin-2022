package com.zeppin.service;

import com.zeppin.model.Question;

public interface QuestionService extends BaseService<Question, Integer> {

	public void deleteByPaperId(int id);

}
