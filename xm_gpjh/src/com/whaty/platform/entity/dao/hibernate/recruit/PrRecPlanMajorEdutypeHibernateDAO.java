package com.whaty.platform.entity.dao.hibernate.recruit;


import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PrRecPlanMajorEdutypeDAO;

/**
 * Data access object (DAO) for domain model class PrRecPlanMajorEdutype.
 * 
 * @see com.whaty.platform.entity.bean.recruit.PrRecPlanMajorEdutype
 * @author MyEclipse Persistence Tools
 */

public class PrRecPlanMajorEdutypeHibernateDAO extends AbstractEntityHibernateDao<PrRecPlanMajorEdutype, String> implements PrRecPlanMajorEdutypeDAO{

	public PrRecPlanMajorEdutypeHibernateDAO() {
		this.entityClass=PrRecPlanMajorEdutype.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)"; 
	}
	
}