package com.whaty.platform.entity.dao.hibernate.textbook;

import com.whaty.platform.entity.bean.PrTchBookInout;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.textbook.PrTchBookInoutDAO;

public class PrTchBookInoutHibernateDAO extends AbstractEntityHibernateDao<PrTchBookInout, String>
		implements PrTchBookInoutDAO {

	public PrTchBookInoutHibernateDAO() {
		this.entityClass=PrTchBookInout.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
