package com.zeppin.service;

import com.zeppin.model.PsqProjectMap;

public interface PsqProjectMapService extends BaseService<PsqProjectMap, Integer> {
	
	public void deleteByPaperId(int id);
}
