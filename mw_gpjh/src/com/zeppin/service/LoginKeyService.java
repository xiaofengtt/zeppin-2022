package com.zeppin.service;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zeppin.model.LoginKey;

public interface LoginKeyService extends BaseService<LoginKey, String> {
	public void deleteByPaperId(int id);
	public int insertLogin(String sql);
	public HibernateTemplate getHibeTemplate();	
	public void sendSms(String[] listpids,String msgformat);
}