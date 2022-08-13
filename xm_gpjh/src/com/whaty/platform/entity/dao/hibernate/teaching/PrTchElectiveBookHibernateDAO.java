package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchElectiveBook;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchElectiveBookDAO;

public class PrTchElectiveBookHibernateDAO extends AbstractEntityHibernateDao<PrTchElectiveBook,String>
		implements PrTchElectiveBookDAO {
	public PrTchElectiveBookHibernateDAO() {
		this.entityClass=PrTchElectiveBook.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}


}
