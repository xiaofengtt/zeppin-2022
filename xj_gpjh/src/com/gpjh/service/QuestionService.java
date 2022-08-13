package com.gpjh.service;

import com.gpjh.model.Question;

public interface QuestionService extends BaseService<Question, String> {

	public void deleteByPaperId(String id);

}
