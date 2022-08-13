package com.whaty.platform.entity.dao.hibernate.student;

import com.whaty.platform.entity.bean.PrStuChangeSchool;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.student.PrStuChangeSchoolDAO;

public class PrStuChangeSchoolHibernateDAO extends AbstractEntityHibernateDao<PrStuChangeSchool,String>
		implements PrStuChangeSchoolDAO {

	public PrStuChangeSchoolHibernateDAO(){
		this.entityClass = PrStuChangeSchool.class;
		this.table = entityClass.getName();
		this.DELETE_BY_IDS = "delete from "+table+" where id in(:ids)";
	}
	
	

}
