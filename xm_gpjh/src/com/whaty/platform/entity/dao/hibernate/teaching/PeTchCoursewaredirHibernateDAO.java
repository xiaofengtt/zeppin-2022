package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PeTchCoursewaredir;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PeTchCoursewaredirDAO;

public class PeTchCoursewaredirHibernateDAO extends AbstractEntityHibernateDao<PeTchCoursewaredir, String> implements PeTchCoursewaredirDAO{

	public PeTchCoursewaredirHibernateDAO() {
		this.entityClass=PeTchCoursewaredir.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
