package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PeTeacherDAO;

public class PeTeacherHibernateDAO extends AbstractEntityHibernateDao<PeTeacher, String> implements PeTeacherDAO{

	public PeTeacherHibernateDAO() {
		this.entityClass=PeTeacher.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
