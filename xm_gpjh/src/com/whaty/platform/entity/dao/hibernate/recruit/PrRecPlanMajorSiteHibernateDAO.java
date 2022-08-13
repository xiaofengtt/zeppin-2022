package com.whaty.platform.entity.dao.hibernate.recruit;

import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PrRecPlanMajorSiteDAO;

/**
 * Data access object (DAO) for domain model class PrRecPlanMajorSite.
 * 
 * @see com.whaty.platform.entity.bean.recruit.PrRecPlanMajorSite
 * @author MyEclipse Persistence Tools
 */

public class PrRecPlanMajorSiteHibernateDAO extends AbstractEntityHibernateDao<PrRecPlanMajorSite, String> implements PrRecPlanMajorSiteDAO {

	public PrRecPlanMajorSiteHibernateDAO() {
		this.entityClass=PrRecPlanMajorSite.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)"; 
		
	} 
	
	
}