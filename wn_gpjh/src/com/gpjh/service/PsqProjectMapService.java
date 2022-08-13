package com.gpjh.service;

import com.gpjh.model.PsqProjectMap;

public interface PsqProjectMapService extends BaseService<PsqProjectMap, Integer> {
	
	public void deleteByPaperId(int id);
}
