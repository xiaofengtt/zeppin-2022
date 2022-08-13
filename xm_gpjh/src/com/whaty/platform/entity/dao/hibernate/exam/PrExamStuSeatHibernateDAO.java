package com.whaty.platform.entity.dao.hibernate.exam;

import com.whaty.platform.entity.bean.PrExamStuSeat;
import com.whaty.platform.entity.dao.exam.PrExamStuSeatDAO;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PrExamStuSeatHibernateDAO extends AbstractEntityHibernateDao<PrExamStuSeat, String>
		implements PrExamStuSeatDAO {
	public PrExamStuSeatHibernateDAO(){
		this.entityClass=PrExamStuSeat.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";	
	}
}
