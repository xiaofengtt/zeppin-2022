package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchTeachplanCourse;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchTeachplanCourseDAO;

public class PrTchTeachplanCourseHibernateDAO  extends
	AbstractEntityHibernateDao<PrTchTeachplanCourse, String>
	implements PrTchTeachplanCourseDAO {

	public PrTchTeachplanCourseHibernateDAO() {
		this.entityClass=PrTchTeachplanCourse.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
}
