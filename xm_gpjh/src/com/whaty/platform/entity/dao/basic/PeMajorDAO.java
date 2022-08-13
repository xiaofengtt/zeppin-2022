package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeMajorDAO extends AbstractEntityHibernateDao<PeMajor, String> {

	public PeMajorDAO() {
		this.entityClass=PeMajor.class;		//当前DAO操作的bean类名
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
