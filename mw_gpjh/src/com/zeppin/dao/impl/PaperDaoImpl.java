package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.PaperDao;
import com.zeppin.model.Paper;

@Repository("paperDao")
public class PaperDaoImpl extends BaseDaoImpl<Paper, Integer> implements PaperDao{

}
