package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchProgramCourse;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchProgramCourseDAO;

public class PrTchProgramCourseHibernateDAO extends AbstractEntityHibernateDao<PrTchProgramCourse,String>
		implements PrTchProgramCourseDAO {
	public PrTchProgramCourseHibernateDAO() {
		this.entityClass=PrTchProgramCourse.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
