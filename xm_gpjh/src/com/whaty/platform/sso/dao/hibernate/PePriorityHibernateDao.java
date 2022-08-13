package com.whaty.platform.sso.dao.hibernate;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.sso.bean.PePriority;
import com.whaty.platform.sso.dao.PePriorityDao;

/**
 * @author lwx 2008-7-27
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class PePriorityHibernateDao extends AbstractEntityHibernateDao<PePriority,String>
		implements PePriorityDao {

	public PePriorityHibernateDao() {
		this.entityClass=PePriority.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
