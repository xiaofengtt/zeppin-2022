package com.whaty.platform.entity.dao.hibernate.recruit;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PeStudentDao;

/**
 * Data access object (DAO) for domain model class PeStudent.
 * 
 * @see com.whaty.platform.entity.bean.recruit.PeStudent
 * @author MyEclipse Persistence Tools
 */

public class PeStudentHibernateDAO extends AbstractEntityHibernateDao<PeStudent,String> implements PeStudentDao {

	public PeStudentHibernateDAO() {
		this.entityClass=PeStudent.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
		
	
}