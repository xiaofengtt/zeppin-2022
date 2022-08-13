package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

/**
 * @param
 * @version 创建时间：2009-6-29 下午03:50:31
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeBzzBatchDAO extends AbstractEntityHibernateDao<PeBzzBatch,String> {
	
	public PeBzzBatchDAO() {
		this.entityClass=PeBzzBatch.class;		//当前DAO操作的bean类名
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
