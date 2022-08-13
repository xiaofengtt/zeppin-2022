package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchPaperTitle;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchPaperTitleDAO;

public class PrTchPaperTitleHibernateDAO extends
		AbstractEntityHibernateDao<PrTchPaperTitle, String> implements
		PrTchPaperTitleDAO {

	public PrTchPaperTitleHibernateDAO() {
		this.entityClass=PrTchPaperTitle.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
