package com.zeppin.service;

import com.zeppin.model.LoginKey;
import com.zeppin.model.Result;
import com.zeppin.model.Submit;

public interface ResultService extends BaseService<Result, Integer> {
	public void addResult(String[] datas ,String paper_id ,LoginKey lk ,Submit submit);
}
