package com.whaty.platform.info.dao.hibernate;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNews;
import com.whaty.platform.info.dao.InfoNewsDAO;

/**
 * Data access object (DAO) for domain model class InfoNews.
 * @see com.whaty.platform.info.bean.InfoNews
 * @author MyEclipse - Hibernate Tools
 */
public class InfoNewsHibernateDAO extends HibernateDaoSupport implements InfoNewsDAO {

    private static final Log log = LogFactory.getLog(InfoNewsHibernateDAO.class);
    
    private static final String DELETE_BY_IDS ="delete from InfoNews where id in(:ids)"; 
    
    private static final String LOAD_BY_ID = " from InfoNews i left join fetch i.infoNewsType t where i.id = ? ";
    
	/**
	 * 保存各更新
	 * @param transientInstance
	 */
    public InfoNews save(InfoNews transientInstance) {
        try {
            getHibernateTemplate().saveOrUpdate(transientInstance);
            log.debug("添加成功!");
            return transientInstance;
        } catch (RuntimeException re) {
            log.error("添加失败", re);
            throw re;
        }
    }
    
    /**
     * 批量更新活动状态
     */
    public int updateActiveStatusByIds(final List list){
    	
    	return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
    		public Object doInHibernate(Session s) throws HibernateException,SQLException{

    			Iterator it = list.iterator();
    			InfoNews news = null;
    			int count=0;
    			while(it.hasNext()){
    				//获得单个状态
    				String id = (String)it.next();
    				news = getInfoNewsById(id);
    				long reg = 0L;
    				
    				if("1".equals(String.valueOf(news.getIsactive()))){
    					reg = 0L;
    				}else{
    					reg = 1L;
    				}
    				String sql ="update InfoNews n set n.isactive = ? where n.id = ?";
    				Query query = s.createQuery(sql);
    				query.setLong(0,reg);
    				query.setString(1,news.getId());
    				int c = query.executeUpdate();
    				if(c == 1){
    					count ++;
    				}
    			}
    			return count;
    		}
    	});
    }
    
    /**
     * 批量更新置顶状态
     */
    public int updateTopStatusByIds(final List list){
    	
    	return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
    		public Object doInHibernate(Session s) throws HibernateException,SQLException{

    			Iterator it = list.iterator();
    			InfoNews news = null;
    			int count=0;
    			while(it.hasNext()){
    				//获得单个状态
    				String id = (String)it.next();
    				news = getInfoNewsById(id);
    				long reg = 0L;
    				
    				if("1".equals(String.valueOf(news.getIstop()))){
    					reg = 0L;
    				}else{
    					reg = 1L;
    				}
    				String sql ="update InfoNews n set n.istop = ? where n.id = ?";
    				Query query = s.createQuery(sql);
    				query.setLong(0,reg);
    				query.setString(1,news.getId());
    				int c = query.executeUpdate();
    				if(c == 1){
    					count ++;
    				}
    			}
    			return count;
    		}
    	});
    }
    
    /**
     * 批量更新弹出状态
     */
    public int updatePopStatusByIds(final List list){
    	
    	return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
    		public Object doInHibernate(Session s) throws HibernateException,SQLException{

    			Iterator it = list.iterator();
    			InfoNews news = null;
    			int count=0;
    			while(it.hasNext()){
    				//获得单个状态
    				String id = (String)it.next();
    				news = getInfoNewsById(id);
    				long reg = 0L;
    				
    				if("1".equals(String.valueOf(news.getIspop()))){
    					reg = 0L;
    				}else{
    					reg = 1L;
    				}
    				String sql ="update InfoNews n set n.ispop = ? where n.id = ?";
    				Query query = s.createQuery(sql);
    				query.setLong(0,reg);
    				query.setString(1,news.getId());
    				int c = query.executeUpdate();
    				if(c == 1){
    					count ++;
    				}
    			}
    			return count;
    		}
    	});
    }
    
    /**
     * 批量更新审核状态
     */
    public int updateConfirmStatusByIds(final List list){
    	
    	return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
    		public Object doInHibernate(Session s) throws HibernateException,SQLException{

    			Iterator it = list.iterator();
    			InfoNews news = null;
    			int count=0;
    			while(it.hasNext()){
    				//获得单个状态
    				String id = (String)it.next();
    				news = getInfoNewsById(id);
    				long reg = 0L;
    				
    				if("1".equals(String.valueOf(news.getIsconfirm()))){
    					reg = 0L;
    				}else{
    					reg = 1L;
    				}
    				String sql ="update InfoNews n set n.isconfirm = ? where n.id = ?";
    				Query query = s.createQuery(sql);
    				query.setLong(0,reg);
    				query.setString(1,news.getId());
    				int c = query.executeUpdate();
    				if(c == 1){
    					count ++;
    				}
    			}
    			return count;
    		}
    	});
    }
    
    /**
     * 根据id 删除新闻信息
     * @param ids
     */
    public void deleteInfoNewsByIds(final List ids) {
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
    
    
    /**
     * 删除
     * @param persistentInstance
     */
	public void delete(InfoNews persistentInstance) {
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("删除成功");
        } catch (RuntimeException re) {
            log.error("删除失败", re);
            throw re;
        }
    }
	
    
	/**
	 * 根据id获得新闻信息
	 * @param id
	 * @return
	 */
    public InfoNews getInfoNewsById( java.lang.String id) {
        try {
            
            List<InfoNews> list = this.getHibernateTemplate().find(LOAD_BY_ID,id);
            if(list ==null || list.isEmpty()){
    			return null;
    		}else{
    			return list.get(0);
    		}
        } catch (RuntimeException re) {
            log.error("获得信息失败", re);
            throw re;
        }
    }
    
    /**
     * 根据样例获的条件得新闻信息
     * @param instance
     * @return
     */
    public List getInfoNewsByExample(final InfoNews instance) {

    	return this.getHibernateTemplate().executeFind(new HibernateCallback(){
    		public Object doInHibernate(Session session)throws HibernateException,SQLException{
    			Criteria criteria = session.createCriteria("com.whaty.platform.info.bean.InfoNews")
    										.add(Example.create(instance));
    			return criteria.list();
    		}
    	});
    }    
   
    /**
     * 根据条件查找新闻信息
     * @param detachedCriteria
     * @return
     */
   public List getInfoNewsList(final DetachedCriteria detachedCriteria){
	   return this.getHibernateTemplate().executeFind(new HibernateCallback(){
		   public Object doInHibernate(Session session)throws HibernateException,SQLException{
			   Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			   return criteria.list();
		   }
	   });
   }
   
   /**
	 * 根据条件分页获得新闻信息
	 */
	public Page getInfoNewsByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex) {

		return (Page) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						
						CriteriaImpl impl = (CriteriaImpl) criteria;
						 //先把Projection和OrderBy条件取出来,清空两者来执行Count操作
                      Projection projection = impl.getProjection();
                      
						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());
						
						 // 将之前的Projection和OrderBy条件重新设回去
	                        criteria.setProjection(projection);
	                        if (projection == null) {
	                            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	                        }
						
						List<InfoNews> items = criteria
								.setFirstResult(startIndex).setMaxResults(
										pageSize).list();
						
						Page pg = new Page(items,totalCount,pageSize,startIndex);
						return pg;
					}
				},true);

	}
	
	/**
	 * 根据条件获得新闻总数
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getInfoNewsTotalCount(final DetachedCriteria detachedCriteria) {
		return (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
						public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria.getExecutableCriteria(session);
						
						CriteriaImpl impl = (CriteriaImpl) criteria;
						 //先把Projection和OrderBy条件取出来,清空两者来执行Count操作
                       Projection projection = impl.getProjection();
                       
						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());
						
						 // 将之前的Projection和OrderBy条件重新设回去
	                        criteria.setProjection(projection);
	                        if (projection == null) {
	                            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	                        }
						return totalCount;
					}
				}, true);
	}
   
}