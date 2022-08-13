package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.ResultDao;
import com.gpjh.model.Result;

@Repository("resultDao")
public class ResultDaoImpl extends BaseDaoImpl<Result, String> implements ResultDao{

}
