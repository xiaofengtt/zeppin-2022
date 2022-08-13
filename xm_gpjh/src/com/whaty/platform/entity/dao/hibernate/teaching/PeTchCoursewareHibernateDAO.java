package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PeTchCoursewareDAO;

public class PeTchCoursewareHibernateDAO extends AbstractEntityHibernateDao<PeTchCourseware, String> implements PeTchCoursewareDAO{

	public PeTchCoursewareHibernateDAO() {
		this.entityClass=PeTchCourseware.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
