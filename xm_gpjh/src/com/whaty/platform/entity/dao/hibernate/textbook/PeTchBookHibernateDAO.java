package com.whaty.platform.entity.dao.hibernate.textbook;

import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.textbook.PeTchBookDAO;

public class PeTchBookHibernateDAO extends AbstractEntityHibernateDao<PeTchBook, String> implements
		PeTchBookDAO {

	public PeTchBookHibernateDAO() {
		this.entityClass=PeTchBook.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
