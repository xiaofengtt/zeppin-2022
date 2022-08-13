package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeEdutypeDAO extends AbstractEntityHibernateDao<PeEdutype, String> {

	public PeEdutypeDAO() {
		this.entityClass=PeEdutype.class;		//当前DAO操作的bean类名
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
