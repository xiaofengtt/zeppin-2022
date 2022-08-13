package com.gpjh.service;

import com.gpjh.model.PsqProjectMap;

public interface PsqProjectMapService extends BaseService<PsqProjectMap, String> {
	
	public void deleteByPaperId(String id);
}
