package com.gpjh.service;

import com.gpjh.model.Question;

public interface QuestionService extends BaseService<Question, Integer> {

	public void deleteByPaperId(int id);

}
