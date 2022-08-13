package com.whaty.platform.entity.dao.hibernate.teaching;


import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PeTchCourseDAO;

public class PeTchCourseHibernateDAO extends
		AbstractEntityHibernateDao<PeTchCourse, String> implements
		PeTchCourseDAO {

	public PeTchCourseHibernateDAO() {
		this.entityClass=PeTchCourse.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	

}
