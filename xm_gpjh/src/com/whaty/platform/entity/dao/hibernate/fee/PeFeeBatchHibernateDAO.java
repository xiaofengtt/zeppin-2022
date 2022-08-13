package com.whaty.platform.entity.dao.hibernate.fee;

import com.whaty.platform.entity.bean.PeFeeBatch;
import com.whaty.platform.entity.dao.fee.PeFeeBatchDAO;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeFeeBatchHibernateDAO extends AbstractEntityHibernateDao<PeFeeBatch, String>
		implements PeFeeBatchDAO {

	public PeFeeBatchHibernateDAO() {
		this.entityClass=PeFeeBatch.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
