package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchStuElectiveDAO;

public class PrTchStuElectiveHibernateDAO extends AbstractEntityHibernateDao<PrTchStuElective,String>
		implements PrTchStuElectiveDAO {
	public PrTchStuElectiveHibernateDAO() {
		this.entityClass=PrTchStuElective.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
