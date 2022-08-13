package com.whaty.platform.sso.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.sso.bean.PePriCategory;
import com.whaty.platform.sso.dao.PePriCategoryDao;
import com.whaty.platform.util.PlatformConstant;

/**
 * 
 * @author lwx 2008-7-27
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class PePriCategoryHibernateDao extends AbstractEntityHibernateDao<PePriCategory,String>
		implements PePriCategoryDao {

	public PePriCategoryHibernateDao() {
		this.entityClass=PePriCategory.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
	/**
 	 * 批量删除，级联删除其下的子分类;
 	 */
 	public void deleteByBatch(final List<PePriCategory> list){
 		
 		 getHibernateTemplate().execute(new HibernateCallback(){
 			
 			public Object doInHibernate(Session session)throws HibernateException,SQLException{
 				
 				for(int i= 0; i<list.size(); i++){
 					PePriCategory category = list.get(i);
 					session.delete(category);
 					if(i %PlatformConstant.HIBERNATE_BATCH_SIZE==0){
 						session.flush();
 						session.clear();
 					}
 				}
				return null;
 			}
 		});
 	}
	

}
