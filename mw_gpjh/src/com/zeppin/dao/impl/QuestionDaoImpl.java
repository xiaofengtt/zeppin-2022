package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.QuestionDao;
import com.zeppin.model.Question;

@Repository("questionDao")
public class QuestionDaoImpl extends BaseDaoImpl<Question, Integer> implements QuestionDao{

}
