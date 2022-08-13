package com.whaty.platform.entity.dao;

import java.util.List;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.dao.hibernate.GeneralHibernateDao;

/**
 * Data access object (DAO) for domain model class C.
 * 
 * @see com.whaty.platform.entity.bean.C
 * @author MyEclipse Persistence Tools
 */

public class EnumConstDAO extends GeneralHibernateDao<EnumConst> {
	
	public EnumConstDAO() {
		this.setEntityClass(EnumConst.class);		//当前DAO操作的bean类名
		
	}
	
	public EnumConst getDefaultByNamespace(String namespace) {
		
		EnumConst enumConst = null;
		String hql = "from EnumConst where isDefault='1' and namespace='" + namespace + "'";
		
		List list = this.getByHQL(hql);
		
		if (list.size() == 1)
			enumConst = (EnumConst)list.get(0);
		
		return enumConst;
	}
	
	public EnumConst getByNamespaceCode(String namespace,String code) {
		
		EnumConst enumConst = null;
		String hql = "from EnumConst where code='"+ code +"' and namespace='" + namespace + "'";
		
		List list = this.getByHQL(hql);
		
		if (list.size() == 1)
			enumConst = (EnumConst)list.get(0);
		
		return enumConst;
	}
}
