package com.zeppin.dao;

import com.zeppin.model.peProApplyNo;

public interface peProApplyNoDao extends BaseDao<peProApplyNo, String> {
	
	public String toJson(peProApplyNo po);

}
