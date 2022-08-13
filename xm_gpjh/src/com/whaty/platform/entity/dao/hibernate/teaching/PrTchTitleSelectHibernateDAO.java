package com.whaty.platform.entity.dao.hibernate.teaching;
 
import com.whaty.platform.entity.bean.PrTchTitleSelect;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.teaching.PrTchTitleSelectDAO;

public class PrTchTitleSelectHibernateDAO extends AbstractEntityHibernateDao<PrTchTitleSelect, String> implements PrTchTitleSelectDAO{

	public PrTchTitleSelectHibernateDAO() {
		this.entityClass=PrTchTitleSelect.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
