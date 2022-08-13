package com.whaty.platform.entity.dao.exam;

import com.whaty.platform.entity.bean.PrExamnoTime;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PrExamnoTimeDAO extends AbstractEntityHibernateDao<PrExamnoTime, String> {
	public PrExamnoTimeDAO() {
		this.entityClass = PrExamnoTime.class;
		this.table = entityClass.getName();
		this.DELETE_BY_IDS = "delete from " + table + " where id in(:ids)";
	}
}
