package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchOpencourseBook;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchOpencourseBookDAO;

public class PrTchOpencourseBookHibernateDAO extends AbstractEntityHibernateDao<PrTchOpencourseBook,String>
		implements PrTchOpencourseBookDAO {
	public PrTchOpencourseBookHibernateDAO() {
		this.entityClass=PrTchOpencourseBook.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
