package com.whaty.platform.entity.dao.basic;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.bean.PeBzzStudent;

/**
 * @param
 * @version 创建时间：2009-6-29 下午03:48:11
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeBzzStudentDAO extends AbstractEntityHibernateDao<PeBzzStudent,String> {
		
	public PeBzzStudentDAO() {
		this.entityClass=PeBzzStudent.class;		//当前DAO操作的bean类名
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

}
