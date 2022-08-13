package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchOpencourse;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchOpencourseDAO;

public class PrTchOpencourseHibernateDAO extends AbstractEntityHibernateDao<PrTchOpencourse,String>
		implements PrTchOpencourseDAO {
	public PrTchOpencourseHibernateDAO() {
		this.entityClass=PrTchOpencourse.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
