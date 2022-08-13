package com.whaty.platform.sso.dao.hibernate;


import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.sso.bean.PrPriRole;
import com.whaty.platform.sso.dao.PrPriRoleDao;

/**
 * @author lwx 2008-7-27
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class PrPriRoleHibernateDao extends AbstractEntityHibernateDao<PrPriRole,String> implements
		PrPriRoleDao {

	public PrPriRoleHibernateDao() {
		this.entityClass=PrPriRole.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

	

}
