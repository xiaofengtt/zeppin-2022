package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.LoginKeyDao;
import com.zeppin.model.LoginKey;

@Repository("loginDao")
public class LoginKeyDaoImpl extends BaseDaoImpl<LoginKey, String> implements LoginKeyDao{

}