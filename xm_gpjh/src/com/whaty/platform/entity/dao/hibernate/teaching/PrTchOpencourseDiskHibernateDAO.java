package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchOpencourseDisk;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchOpencourseDiskDAO;

public class PrTchOpencourseDiskHibernateDAO extends AbstractEntityHibernateDao<PrTchOpencourseDisk,String>
		implements PrTchOpencourseDiskDAO {
	public PrTchOpencourseDiskHibernateDAO() {
		this.entityClass=PrTchOpencourseDisk.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
