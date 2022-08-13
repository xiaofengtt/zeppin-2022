package com.gpjh.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.VoteCountDao;
import com.gpjh.model.VoteCount;

@Repository("votecountDao")
public class VoteCountDaoImpl extends BaseDaoImpl<VoteCount, String> implements VoteCountDao{



}
