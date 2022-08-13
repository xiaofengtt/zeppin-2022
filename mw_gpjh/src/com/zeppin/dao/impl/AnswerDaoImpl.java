package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.AnswerDao;
import com.zeppin.model.Answer;

@Repository("answerDao")
public class AnswerDaoImpl extends BaseDaoImpl<Answer, Integer> implements AnswerDao{

}
