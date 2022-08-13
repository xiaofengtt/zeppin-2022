package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeSemesterDAO extends AbstractEntityHibernateDao<PeSemester, String> {
	public PeSemesterDAO() {
		this.entityClass = PeSemester.class;
		this.table = entityClass.getName();
		this.DELETE_BY_IDS = "delete from " + table + " where id in(:ids)";
	}
} 
