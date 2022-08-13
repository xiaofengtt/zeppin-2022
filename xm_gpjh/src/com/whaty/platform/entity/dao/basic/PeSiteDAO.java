package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeSiteDAO extends AbstractEntityHibernateDao<PeSite, String> {

	public PeSiteDAO() {
		this.entityClass=PeSite.class;		//当前DAO操作的bean类名
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
