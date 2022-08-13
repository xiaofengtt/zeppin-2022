package com.whaty.platform.sso.dao.hibernate;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.sso.bean.PePriRole;
import com.whaty.platform.sso.dao.PePriRoleDao;

/**
 * @author lwx 2008-7-27
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class PePriRoleHibernateDao extends AbstractEntityHibernateDao<PePriRole,String> implements
		PePriRoleDao {

	public PePriRoleHibernateDao() {
		this.entityClass=PePriRole.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
	
}
