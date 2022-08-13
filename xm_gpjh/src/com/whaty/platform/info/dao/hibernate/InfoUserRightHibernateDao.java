package com.whaty.platform.info.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.info.bean.InfoUserRight;
import com.whaty.platform.info.dao.InfoUserRigthDao;

public class InfoUserRightHibernateDao extends AbstractEntityHibernateDao<InfoUserRight,String>
		implements InfoUserRigthDao {

	private static final String DELETE_BY_NEWSTYPE_IDS = "delete from InfoUserRight r where r.infoNewsType.id in (:typeIds)";
	
	public InfoUserRightHibernateDao() {
		this.entityClass=InfoUserRight.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
	/**
	 * 根据newtype 列表删除记录
	 */
	public int deleteByNewsTypeIds(final List newsTypeIds){
		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session s)throws HibernateException,SQLException{
				
				int c = 0 ;
				Query query = s.createQuery(DELETE_BY_NEWSTYPE_IDS);
				query.setParameterList("typeIds", newsTypeIds);
				c = query.executeUpdate();
				return c;
			}
		});
	}


}
