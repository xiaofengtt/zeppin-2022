package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchStuPaperDAO;

public class PrTchStuPaperHibernateDAO extends
		AbstractEntityHibernateDao<PrTchStuPaper, String> implements
		PrTchStuPaperDAO {

	public PrTchStuPaperHibernateDAO() {
		this.entityClass=PrTchStuPaper.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
