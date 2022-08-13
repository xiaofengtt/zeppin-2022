package com.whaty.platform.entity.dao.hibernate.textbook;

import com.whaty.platform.entity.bean.PeTchDisk;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.textbook.PeTchDiskDAO;

public class PeTchDiskHibernateDAO extends AbstractEntityHibernateDao<PeTchDisk, String> implements
		PeTchDiskDAO {

	public PeTchDiskHibernateDAO() {
		this.entityClass=PeTchDisk.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
