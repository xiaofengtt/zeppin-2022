package com.gpjh.service;

import com.gpjh.model.Answer;

public interface AnswerService extends BaseService<Answer, String> {

	public void deleteByPaperId(String id);

}
