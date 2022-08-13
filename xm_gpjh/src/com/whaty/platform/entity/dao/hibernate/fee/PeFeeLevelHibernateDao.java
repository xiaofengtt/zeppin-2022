package com.whaty.platform.entity.dao.hibernate.fee;

import java.util.List;

import com.whaty.platform.entity.bean.PeFeeLevel;
import com.whaty.platform.entity.dao.fee.PeFeeLevelDao;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeFeeLevelHibernateDao extends AbstractEntityHibernateDao<PeFeeLevel,String>
		implements PeFeeLevelDao {

	public PeFeeLevelHibernateDao() {
		this.entityClass=PeFeeLevel.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

	/**
	 * 获得默认fee水平
	 */
	public PeFeeLevel getDefaultFeeLevel(){
		List list =this.getHibernateTemplate().find("from PeFeeLevel fee where fee.flagDefailt = '1'");
		if(list != null && list.size()>0)
			return (PeFeeLevel)list.get(0);
		else
			return null;
	}

}
