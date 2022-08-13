package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchElectiveDisk;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchElectiveDiskDAO;

public class PrTchElectiveDiskHibernateDAO extends AbstractEntityHibernateDao<PrTchElectiveDisk,String>
		implements PrTchElectiveDiskDAO {
	public PrTchElectiveDiskHibernateDAO() {
		this.entityClass=PrTchElectiveDisk.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
