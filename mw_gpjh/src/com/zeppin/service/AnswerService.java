package com.zeppin.service;

import com.zeppin.model.Answer;

public interface AnswerService extends BaseService<Answer, Integer> {

	public void deleteByPaperId(int id);

}
