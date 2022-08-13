package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.SubjectDao;
import com.gpjh.model.Subject;

@Repository("subjectDao")
public class SubjectDaoImpl extends BaseDaoImpl<Subject, String> implements SubjectDao{

}
