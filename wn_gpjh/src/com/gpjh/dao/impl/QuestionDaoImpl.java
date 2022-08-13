package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.QuestionDao;
import com.gpjh.model.Question;

@Repository("questionDao")
public class QuestionDaoImpl extends BaseDaoImpl<Question, Integer> implements QuestionDao{

}
