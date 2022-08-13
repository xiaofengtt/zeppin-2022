package com.whaty.platform.entity.dao.hibernate.fee;

import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.dao.fee.PrFeeDetailDAO;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PrFeeDetailHibernateDAO extends AbstractEntityHibernateDao<PrFeeDetail, String>
		implements PrFeeDetailDAO {
	
	public PrFeeDetailHibernateDAO() {
		this.entityClass=PrFeeDetail.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}	
}
