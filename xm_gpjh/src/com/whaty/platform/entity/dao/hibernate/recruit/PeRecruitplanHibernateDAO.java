package com.whaty.platform.entity.dao.hibernate.recruit;

import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PeRecruitplanDAO;


/**
 * Data access object (DAO) for domain model class PeRecruitplan.
 * 
 * @see com.whaty.platform.entity.bean.recruit.PeRecruitplan
 * @author MyEclipse Persistence Tools
 */

public class PeRecruitplanHibernateDAO extends AbstractEntityHibernateDao<PeRecruitplan,String> implements PeRecruitplanDAO{
	public PeRecruitplanHibernateDAO(){
		this.entityClass=PeRecruitplan.class;
    	this.table=entityClass.getName();
    	this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";

	}

}