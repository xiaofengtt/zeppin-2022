package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.LoginKeyDao;
import com.gpjh.model.LoginKey;

@Repository("loginDao")
public class LoginKeyDaoImpl extends BaseDaoImpl<LoginKey, String> implements LoginKeyDao{

}