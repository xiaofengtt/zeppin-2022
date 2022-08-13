package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.LoginKeyDao;
import com.gpjh.model.LoginKey;
import com.gpjh.service.LoginKeyService;


//@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
@Service("loginService")
public class LoginKeyServiceImpl extends BaseServiceImpl<LoginKey, String> implements LoginKeyService {
    
    @Autowired
	private LoginKeyDao loginDao;

	@Override
	public LoginKey add(LoginKey t) {
		// TODO Auto-generated method stub
		return loginDao.add(t);
	}

	@Override
	public void delete(LoginKey t) {
		// TODO Auto-generated method stub
		loginDao.delete(t);
	}

	@Override
	public LoginKey load(String id) {
		// TODO Auto-generated method stub
		return loginDao.load(id);
	}

	@Override
	public List<LoginKey> loadAll() {
		// TODO Auto-generated method stub
		return loginDao.loadAll();
	}

	@Override
	public LoginKey update(LoginKey t) {
		// TODO Auto-generated method stub
		return loginDao.update(t);
	}

	@Override
	public LoginKey get(String id) {
		// TODO Auto-generated method stub
		return loginDao.get(id);
	}

	@Override
	public List<LoginKey> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return loginDao.getListForPage(hql, pageId, pageSize);
	}
	
	@Override
	public int queryRowCount(final String hql){
		return loginDao.queryRowCount(hql);
	}
	
}