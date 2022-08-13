package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.TrainingUnitDao;
import com.gpjh.model.TrainingUnit;
import com.gpjh.service.TrainingUnitService;

@Service("trainingUnitService")
public class TrainingUnitServiceImpl extends BaseServiceImpl<TrainingUnit, String> implements TrainingUnitService {

    @Autowired
	private TrainingUnitDao trainingUnitDao;

	@Override
	public TrainingUnit add(TrainingUnit t) {
		// TODO Auto-generated method stub
		return trainingUnitDao.add(t);
	}

	@Override
	public void delete(TrainingUnit t) {
		// TODO Auto-generated method stub
		trainingUnitDao.delete(t);
	}

	@Override
	public TrainingUnit load(String id) {
		// TODO Auto-generated method stub
		return trainingUnitDao.load(id);
	}

	@Override
	public List<TrainingUnit> loadAll() {
		// TODO Auto-generated method stub
		return trainingUnitDao.loadAll();
	}

	@Override
	public List<TrainingUnit> findAll() {
		// TODO Auto-generated method stub
		return trainingUnitDao.findAll();
	}
	
	@Override
	public TrainingUnit update(TrainingUnit t) {
		// TODO Auto-generated method stub
		return trainingUnitDao.update(t);
	}

	@Override
	public TrainingUnit get(String id) {
		// TODO Auto-generated method stub
		return trainingUnitDao.get(id);
	}

    @Override
    public List<Object> findByHSQL(String querySql){
    	return trainingUnitDao.findByHSQL(querySql);
    }
    
	@Override
	public List<TrainingUnit> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return trainingUnitDao.getListForPage(hql, pageId, pageSize);
	}
}
