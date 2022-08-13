package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.peProApplyNoDao;
import com.zeppin.model.peProApplyNo;

@Repository("peProApplyNoDao")
public class peProApplyNoDaoImpl extends BaseDaoImpl<peProApplyNo, String> implements peProApplyNoDao {

	@Override
	public String toJson(peProApplyNo po) {
		// TODO Auto-generated method stub
		return "{\"id\":\"" + po.getId() + "\"}";
	}

}
