package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.PaperDao;
import com.gpjh.model.Paper;
import com.gpjh.service.PaperService;

@Service("paperService")
public class PaperServiceImpl extends BaseServiceImpl<Paper, Integer> implements PaperService {
    
    @Autowired
	private PaperDao paperDao;

	@Override
	public Paper add(Paper t) {
		// TODO Auto-generated method stub
		return paperDao.add(t);
	}

	@Override
	public void delete(Paper t) {
		// TODO Auto-generated method stub
		paperDao.delete(t);
	}

	@Override
	public Paper load(Integer id) {
		// TODO Auto-generated method stub
		return paperDao.load(id);
	}

	@Override
	public List<Paper> loadAll() {
		// TODO Auto-generated method stub
		return paperDao.loadAll();
	}

	@Override
	public Paper update(Paper t) {
		// TODO Auto-generated method stub
		return paperDao.update(t);
	}

	@Override
	public Paper get(Integer id) {
		// TODO Auto-generated method stub
		return paperDao.get(id);
	}

	@Override
	public List<Paper> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return paperDao.getListForPage(hql, pageId, pageSize);
	}
		
}
