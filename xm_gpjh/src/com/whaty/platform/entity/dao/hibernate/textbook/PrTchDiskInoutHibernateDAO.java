package com.whaty.platform.entity.dao.hibernate.textbook;

import com.whaty.platform.entity.bean.PrTchDiskInout;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.textbook.PrTchDiskInoutDAO;

public class PrTchDiskInoutHibernateDAO extends AbstractEntityHibernateDao<PrTchDiskInout, String>
		implements PrTchDiskInoutDAO {

	public PrTchDiskInoutHibernateDAO() {
		this.entityClass=PrTchDiskInout.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
