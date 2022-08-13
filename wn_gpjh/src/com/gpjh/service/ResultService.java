package com.gpjh.service;

import com.gpjh.model.LoginKey;
import com.gpjh.model.Result;
import com.gpjh.model.Submit;

public interface ResultService extends BaseService<Result, Integer> {
	public void addResult(String[] datas ,String paper_id ,LoginKey lk ,Submit submit);
}
