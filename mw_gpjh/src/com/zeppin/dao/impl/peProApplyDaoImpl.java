package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.peProApplyDao;
import com.zeppin.dao.peProApplyNoDao;
import com.zeppin.model.peProApply;
import com.zeppin.model.peProApplyNo;

@Repository("peProAppDao")
public class peProApplyDaoImpl extends BaseDaoImpl<peProApply, String> implements peProApplyDao {

}
