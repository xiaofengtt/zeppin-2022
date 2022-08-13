package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.PaperDao;
import com.gpjh.model.Paper;

@Repository("paperDao")
public class PaperDaoImpl extends BaseDaoImpl<Paper, String> implements PaperDao{

}
