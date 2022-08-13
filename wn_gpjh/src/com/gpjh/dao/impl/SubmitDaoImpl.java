package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.SubmitDao;
import com.gpjh.model.Submit;

@Repository("submitDao")
public class SubmitDaoImpl extends BaseDaoImpl<Submit, Integer> implements SubmitDao{

}
