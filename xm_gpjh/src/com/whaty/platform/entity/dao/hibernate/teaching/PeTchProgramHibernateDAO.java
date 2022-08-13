package com.whaty.platform.entity.dao.hibernate.teaching;

import com.whaty.platform.entity.bean.PeTchProgram;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PeTchProgramDAO;

public class PeTchProgramHibernateDAO extends AbstractEntityHibernateDao<PeTchProgram,String> implements PeTchProgramDAO{
	public PeTchProgramHibernateDAO() {
		this.entityClass=PeTchProgram.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
