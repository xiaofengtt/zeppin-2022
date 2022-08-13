package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PeGradeDAO extends AbstractEntityHibernateDao<PeGrade, String> {

	public PeGradeDAO() {
		this.entityClass=PeGrade.class;		//当前DAO操作的bean类名
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
}
