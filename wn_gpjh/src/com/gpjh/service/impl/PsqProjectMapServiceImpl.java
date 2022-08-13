package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.PsqProjectMapDao;
import com.gpjh.model.PsqProjectMap;
import com.gpjh.service.PsqProjectMapService;

@Service("psqprojectmaptService")
public class PsqProjectMapServiceImpl  extends BaseServiceImpl<PsqProjectMap, Integer> implements PsqProjectMapService {
    
    @Autowired
	private PsqProjectMapDao psqprojectmapDao;

	@Override
	public PsqProjectMap add(PsqProjectMap t) {
		// TODO Auto-generated method stub
		return psqprojectmapDao.add(t);
	}

	@Override
	public void delete(PsqProjectMap t) {
		// TODO Auto-generated method stub
		psqprojectmapDao.delete(t);
	}

	@Override
	public PsqProjectMap load(Integer id) {
		// TODO Auto-generated method stub
		return psqprojectmapDao.load(id);
	}

	@Override
	public List<PsqProjectMap> loadAll() {
		// TODO Auto-generated method stub
		return psqprojectmapDao.loadAll();
	}

	@Override
	public PsqProjectMap update(PsqProjectMap t) {
		// TODO Auto-generated method stub
		return psqprojectmapDao.update(t);
	}

	@Override
	public PsqProjectMap get(Integer id) {
		// TODO Auto-generated method stub
		return psqprojectmapDao.get(id);
	}

	@Override
	public List<PsqProjectMap> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return psqprojectmapDao.getListForPage(hql, pageId, pageSize);
	}
	
	@Override
	public int queryRowCount(final String hql){
		return psqprojectmapDao.queryRowCount(hql);
	}
	
	@Override
	public void deleteByPaperId(int id) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append("delete PsqProjectMap where paper=");
		hql.append(id);
		psqprojectmapDao.executeHSQL(hql.toString());
	}
}