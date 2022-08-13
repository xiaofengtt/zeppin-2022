package com.gpjh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.TrainEeDao;
import com.gpjh.model.TrainEe;
import com.gpjh.service.TrainEeService;

@Service("trainEeService")
public class TrainEeServiceImpl extends BaseServiceImpl<TrainEe, String>
		implements TrainEeService {

	@Autowired
	private TrainEeDao trainEeDao;

	public TrainEe add(TrainEe t) {
		return trainEeDao.add(t);
	}

	public void delete(TrainEe t) {
		trainEeDao.delete(t);
	}

	public TrainEe load(String id) {
		return trainEeDao.load(id);
	}

	public TrainEe get(String id) {
		return trainEeDao.get(id);
	}

	public List<TrainEe> loadAll() {
		return trainEeDao.loadAll();
	}

	public List<TrainEe> findAll() {
		return trainEeDao.findAll();
	}

	public TrainEe update(TrainEe t) {
		return trainEeDao.update(t);
	}

	public List<Object> findByHSQL(String querySql) {
		return trainEeDao.findByHSQL(querySql);
	}

	public List<TrainEe> getListForPage(String hql, int offset, int length) {
		return trainEeDao.getListForPage(hql, offset, length);
	}

	public void executeHSQL(String hql) {
		trainEeDao.executeHSQL(hql);
	}

	public List executeSQL(String sql) {
		List<TrainEe> listrain = new ArrayList<TrainEe>();

		List li = trainEeDao.executeSQL(sql);

		for (Object obj : li) {

			listrain.add((TrainEe) obj);

		}
		
		return listrain;
	}

	public int queryRowCount(String hql) {
		return trainEeDao.queryRowCount(hql);
	}

	public List<TrainEe> getListByHSQL(String hql) {
		return trainEeDao.getListByHSQL(hql);
	}

}
