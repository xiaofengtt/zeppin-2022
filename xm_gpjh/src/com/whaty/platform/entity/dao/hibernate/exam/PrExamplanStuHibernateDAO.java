package com.whaty.platform.entity.dao.hibernate.exam;

import com.whaty.platform.entity.bean.PrExamplanStu;
import com.whaty.platform.entity.dao.exam.PrExamplanStuDAO;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;

public class PrExamplanStuHibernateDAO extends AbstractEntityHibernateDao<PrExamplanStu, String> implements PrExamplanStuDAO{
	public PrExamplanStuHibernateDAO() {
		this.entityClass = PrExamplanStu.class;
		this.table = entityClass.getName();
		this.DELETE_BY_IDS = "delete from " + table + " where id in(:ids)";
	}	
}
