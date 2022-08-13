package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.AnswerDao;
import com.gpjh.model.Answer;

@Repository("answerDao")
public class AnswerDaoImpl extends BaseDaoImpl<Answer, String> implements AnswerDao{

}
