package com.whaty.platform.entity.service.imp;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.service.MyListService;

public class MyListServiceImp implements MyListService {
	
	private MyListDAO myListDao;
	
	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	public List getIdNameList(String bean) {
		return myListDao.getIdNameList(bean);
	}
	
	public String getIdByName(String bean, String name) {
		return myListDao.getIdByName(bean, name);
	}
	
	public List queryByHQL(String hql) {
		return myListDao.queryByHQL(hql);
	}
	
	public List queryBySQL(String sql) {
		return myListDao.queryBySQL(sql);
	}

	public AbstractBean getById(Class clazz, String id) {
		return myListDao.getById(clazz, id);
	}

	public EnumConst getEnumConstByNamespaceCode(String namespace, String code) {
		return myListDao.getEnumConstByNamespaceCode(namespace, code);
	}

	public String getSysValueByName(String name) {
		return myListDao.getSysValueByName(name);
	}
	
	public List getByIds(Class clazz, List ids) {
		return myListDao.getByIds(clazz, ids);
	}
}
