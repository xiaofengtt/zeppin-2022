package com.whaty.platform.vote.dao.hibernate;

import com.whaty.platform.entity.bean.VoteQuestion;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.vote.dao.VoteQuestionDao;

public class VoteQuestionHibernateDao extends AbstractEntityHibernateDao<VoteQuestion,String>
		implements VoteQuestionDao {

	public VoteQuestionHibernateDao() {
		this.entityClass=VoteQuestion.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

	

}
