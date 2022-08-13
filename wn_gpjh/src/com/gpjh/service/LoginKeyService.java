package com.gpjh.service;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.gpjh.model.LoginKey;

public interface LoginKeyService extends BaseService<LoginKey, String> {
	public void deleteByPaperId(int id);
	public int insertLogin(String sql);
	public HibernateTemplate getHibeTemplate();	
}