package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

/**
 * @param
 * @version 创建时间：2009-6-18 上午09:59:42
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeEnterpriseDAO extends AbstractEntityHibernateDao<PeEnterprise,String> {
	
	public PeEnterpriseDAO() {
		this.entityClass=PeEnterprise.class;		//当前DAO操作的bean类名
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
