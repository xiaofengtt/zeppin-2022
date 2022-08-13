package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchCourseTeacher;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchCourseTeacherDAO;

public class PrTchCourseTeacherHibernateDAO extends AbstractEntityHibernateDao<PrTchCourseTeacher, String> implements PrTchCourseTeacherDAO{

	public PrTchCourseTeacherHibernateDAO() {
		this.entityClass=PrTchCourseTeacher.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
