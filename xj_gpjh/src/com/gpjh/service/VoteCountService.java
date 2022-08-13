package com.gpjh.service;

import java.util.List;

import com.gpjh.model.VoteCount;

public interface VoteCountService extends BaseService<VoteCount, String> {

	public VoteCount getByLoginKey(String loginkey);

}
