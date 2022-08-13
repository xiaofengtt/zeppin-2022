package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchOpencourseTeacher;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchOpencourseTeacherDAO;

public class PrTchOpencourseTeacherHibernateDAO extends
		AbstractEntityHibernateDao<PrTchOpencourseTeacher,String> implements PrTchOpencourseTeacherDAO {
	public PrTchOpencourseTeacherHibernateDAO() {
		this.entityClass=PrTchOpencourseTeacher.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
