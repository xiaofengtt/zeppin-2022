package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.SubmitDao;
import com.zeppin.model.Submit;

@Repository("submitDao")
public class SubmitDaoImpl extends BaseDaoImpl<Submit, Integer> implements SubmitDao{

}
