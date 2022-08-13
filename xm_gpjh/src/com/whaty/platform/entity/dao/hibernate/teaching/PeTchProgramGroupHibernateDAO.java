package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PeTchProgramGroup;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PeTchProgramGroupDAO;

public class PeTchProgramGroupHibernateDAO extends AbstractEntityHibernateDao<PeTchProgramGroup,String>
		implements PeTchProgramGroupDAO {
	public PeTchProgramGroupHibernateDAO() {
		this.entityClass=PeTchProgramGroup.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
