package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.VoteCountDao;
import com.gpjh.model.VoteCount;
import com.gpjh.service.VoteCountService;

@Service("votecountService")
public class VoteCountServiceImpl extends BaseServiceImpl<VoteCount, String> implements VoteCountService {
    
    @Autowired
	private VoteCountDao voteCountDao;

	@Override
	public VoteCount add(VoteCount t) {
		// TODO Auto-generated method stub
		return voteCountDao.add(t);
	}

	@Override
	public void delete(VoteCount t) {
		// TODO Auto-generated method stub
		voteCountDao.delete(t);
	}

	@Override
	public VoteCount load(String id) {
		// TODO Auto-generated method stub
		return voteCountDao.load(id);
	}

	@Override
	public List<VoteCount> loadAll() {
		// TODO Auto-generated method stub
		return voteCountDao.loadAll();
	}

	@Override
	public VoteCount update(VoteCount t) {
		// TODO Auto-generated method stub
		return voteCountDao.update(t);
	}

	@Override
	public VoteCount get(String id) {
		// TODO Auto-generated method stub
		return voteCountDao.get(id);
	}

	@Override
	public List<VoteCount> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return voteCountDao.getListForPage(hql, pageId, pageSize);
	}
	

	@Override
	public VoteCount getByLoginKey(String loginkey) {
		// TODO Auto-generated method stub
		List<Object> votecounts = voteCountDao.findByHSQL("from VoteCount where loninKey='" + loginkey + "'");
		if (votecounts.size()>0) return (VoteCount) votecounts.get(0);
		else return null;
	}
	
    @Override
    public List<Object> executeSQL(String querySql){
    	return voteCountDao.executeSQL(querySql);
    }
}
