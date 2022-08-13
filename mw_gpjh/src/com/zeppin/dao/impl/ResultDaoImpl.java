package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.ResultDao;
import com.zeppin.model.Result;

@Repository("resultDao")
public class ResultDaoImpl extends BaseDaoImpl<Result, Integer> implements ResultDao{

}
