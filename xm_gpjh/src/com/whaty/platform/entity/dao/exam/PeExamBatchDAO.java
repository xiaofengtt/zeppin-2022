package com.whaty.platform.entity.dao.exam;

import com.whaty.platform.entity.bean.PeExamplan;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeExamBatchDAO extends AbstractEntityHibernateDao<PeExamplan, String> {
	public PeExamBatchDAO() {
		this.entityClass = PeExamplan.class;
		this.table = entityClass.getName();
		this.DELETE_BY_IDS = "delete from " + table + " where id in(:ids)";
	}
}
