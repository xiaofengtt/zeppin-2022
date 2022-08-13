package com.whaty.platform.entity.dao.hibernate.priority;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.priority.PeManagerDao;
import com.whaty.platform.util.PlatformConstant;
/**
 * 管理员DAO
 * @author lwx 2008-7-28
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class PeManagerHibernateDao extends AbstractEntityHibernateDao<PeManager,String> implements
		PeManagerDao {
	
	public PeManagerHibernateDao() {
		this.entityClass=PeManager.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
	
	/**
     * 批量更新
     */
    public int updateBatch(final List managerList){
    	return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
    		
    		public Object doInHibernate(Session s)throws HibernateException,SQLException{
    			
    			PeManager pe = null;
    			int count = 0;
    			for(int i = 0 ;i<managerList.size();i++){
    				pe = (PeManager)managerList.get(i);
    				s.save(pe);
    				count++;
    				if(i % PlatformConstant.HIBERNATE_BATCH_SIZE == 0){
    					s.flush();
    					s.clear();
    				}
    			}
    			return count;
    		}
    	});
    }
	
}
