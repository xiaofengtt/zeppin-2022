package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PrTchOpencourseCourseware;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchOpencourseCoursewareDAO;

public class PrTchOpencourseCoursewareHibernateDAO extends
		AbstractEntityHibernateDao<PrTchOpencourseCourseware,String> implements PrTchOpencourseCoursewareDAO {
	public PrTchOpencourseCoursewareHibernateDAO() {
		this.entityClass=PrTchOpencourseCourseware.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
