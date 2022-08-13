package com.whaty.platform.vote.dao.hibernate;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.vote.bean.VotePaper;
import com.whaty.platform.vote.dao.VotePaperDao;

public class VotePaperHibernateDao extends AbstractEntityHibernateDao<VotePaper,String> implements VotePaperDao {

	public VotePaperHibernateDao() {
		this.entityClass=VotePaper.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
