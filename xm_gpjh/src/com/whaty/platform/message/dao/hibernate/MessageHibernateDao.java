package com.whaty.platform.message.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.message.bean.MsgInfo;
import com.whaty.platform.message.dao.MessageDao;

public class MessageHibernateDao extends HibernateDaoSupport implements
		MessageDao {

	 private static final String DELETE_BY_IDS ="delete from MsgInfo where id in(:ids)"; 
	 
	 private static final String MARKREADED_BY_IDS ="update MsgInfo set status='01',readTime=:curTime where id in(:ids)"; 
	 
	 private static final String LOAD_BY_ID = " from MsgInfo i where i.id = ? ";
	 
	 private static final String UNREADED_IDS=" from MsgInfo where userId= ? and status='00'";
	   
	
	public void delete(MsgInfo message) {
		// TODO Auto-generated method stub

	}

	public void deleteMsgInfosByIds(final List ids) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Query query = s.createQuery(DELETE_BY_IDS);
				query.setParameterList("ids", ids);
				query.executeUpdate();
				return null;
			}
		});

	}

	public MsgInfo getMsgInfoById(final String id) {
try {
            
            List<MsgInfo> list = this.getHibernateTemplate().find(LOAD_BY_ID,id);
            if(list ==null || list.isEmpty()){
    			return null;
    		}else{
    			return list.get(0);
    		}
        } catch (RuntimeException re) {
            throw re;
        }
	}

	public Page getMsgInfosByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex) {
		return (Page) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						//final DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(MsgInfo.class);
						Criteria criteria = detachedCriteria.getExecutableCriteria(session);
						
						CriteriaImpl impl = (CriteriaImpl) criteria;
						 //先把Projection和OrderBy条件取出来,清空两者来执行Count操作
                      Projection projection = impl.getProjection();
                      
					  Integer totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult());
						//Integer totalCount=new Integer(1);
						// 将之前的Projection和OrderBy条件重新设回去
	                    criteria.setProjection(projection);
	                     
						List<MsgInfo> items= null;
						items= criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
						
						Page pg = new Page(items,totalCount,pageSize,startIndex);
						return pg;
					}
				},true);

	}

	public List getMsgInfosList(final DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getMsgInfosTotalCount(final DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public MsgInfo save(final MsgInfo message) {
		 try {
	            getHibernateTemplate().saveOrUpdate(message);
	            return message;
	        } catch (RuntimeException re) {
	            throw re;
	        }
	}

	public int updateReadedStatusByIds(final List list) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void markReadedMsgInfosByIds(final List idList) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Query query = s.createQuery(MARKREADED_BY_IDS);
				query.setParameterList("ids", idList);
				query.setParameter("curTime", new Date());
				query.executeUpdate();
				return null;
			}
		});
		
	}

	public boolean getCurStatus(String userId) {
		 List<MsgInfo> list = this.getHibernateTemplate().find(UNREADED_IDS,userId);
         if(list ==null || list.isEmpty()){
 			return false;
 		}else{
 			return true;
 		}
	}

}
