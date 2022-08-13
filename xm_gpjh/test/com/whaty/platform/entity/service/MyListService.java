package com.whaty.platform.entity.service;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;

public interface MyListService {

	public List getIdNameList(String bean);
	
	public String getIdByName(String bean, String name);
	
	public AbstractBean getById(Class clazz, String id);
	
	public List getByIds(Class clazz,List ids);
	
	public List queryByHQL(String hql);
	
	public List queryBySQL(String sql);
	
	public EnumConst getEnumConstByNamespaceCode(String namespace, String code);
	
	public String getSysValueByName(String name);
}
