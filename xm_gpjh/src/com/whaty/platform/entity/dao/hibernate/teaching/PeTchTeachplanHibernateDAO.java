package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PeTchTeachplan;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PeTchTeachplanDAO;

public class PeTchTeachplanHibernateDAO extends AbstractEntityHibernateDao<PeTchTeachplan,String>
		implements PeTchTeachplanDAO {
	public PeTchTeachplanHibernateDAO() {
		this.entityClass=PeTchTeachplan.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
