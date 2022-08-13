package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchExecuteplanCourse;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchExecuteplanCourseDAO;

public class PrTchExecuteplanCourseHibernateDAO extends
		AbstractEntityHibernateDao<PrTchExecuteplanCourse,String> implements PrTchExecuteplanCourseDAO {
	public PrTchExecuteplanCourseHibernateDAO() {
		this.entityClass=PrTchExecuteplanCourse.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}


}
