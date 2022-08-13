package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchExecuteplan;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchExecuteplanDAO;

public class PrTchExecuteplanHibernateDAO extends AbstractEntityHibernateDao<PrTchExecuteplan,String>
		implements PrTchExecuteplanDAO {
	public PrTchExecuteplanHibernateDAO() {
		this.entityClass=PrTchExecuteplan.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
