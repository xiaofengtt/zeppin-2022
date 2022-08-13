package com.whaty.platform.sso.dao.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.sso.bean.PrPriManagerGrade;
import com.whaty.platform.sso.dao.PrPriManagerGradeDao;
/**
 * @author lwx 2008-7-28
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class PrPriManagerGradeHibernateDao extends AbstractEntityHibernateDao<PrPriManagerGrade,String>
		implements PrPriManagerGradeDao {

	private static String DELETE_BY_USERID = "delete from PrPriManagerGrade n where n.ssoUser.id  = :userId";
	
	public PrPriManagerGradeHibernateDao() {
		this.entityClass=PrPriManagerGrade.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

	
	
	/**
	 * 根据用户id 删除该用户的年级范围
	 */
	public void deleteByUserId(final String ssouserId){
		
		getHibernateTemplate().execute(new HibernateCallback(){
			
			public Object doInHibernate(Session s)throws HibernateException,SQLException{
				
				Query query = s.createQuery(DELETE_BY_USERID);
				query.setParameter("userId", ssouserId);
				query.executeUpdate();
				return null;
			}
		});
	}
}
