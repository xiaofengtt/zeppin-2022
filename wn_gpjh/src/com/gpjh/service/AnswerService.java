package com.gpjh.service;

import com.gpjh.model.Answer;

public interface AnswerService extends BaseService<Answer, Integer> {

	public void deleteByPaperId(int id);

}
