package com.whaty.platform.entity.dao.hibernate.student;

import com.whaty.platform.entity.bean.PrStuChangeMajor;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.student.PrStuChangeMajorDAO;

public class PrStuChangeMajorHibernateDAO extends AbstractEntityHibernateDao<PrStuChangeMajor,String>
		implements PrStuChangeMajorDAO {

	public PrStuChangeMajorHibernateDAO(){
		this.entityClass = PrStuChangeMajor.class;
		this.table = entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
