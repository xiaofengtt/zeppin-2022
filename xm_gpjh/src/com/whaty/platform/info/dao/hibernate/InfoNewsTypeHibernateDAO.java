package com.whaty.platform.info.dao.hibernate;

import java.sql.SQLException;
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
import com.whaty.platform.info.bean.InfoNewsType;
import com.whaty.platform.info.dao.InfoNewsTypeDao;

/**
 * Data access object (DAO) for domain model class InfoNewsType.
 * @see com.whaty.platform.info.bean.InfoNewsType
 * @author MyEclipse - Hibernate Tools
 */
public class InfoNewsTypeHibernateDAO extends HibernateDaoSupport implements InfoNewsTypeDao {

    private static final Log log = LogFactory.getLog(InfoNewsTypeHibernateDAO.class);

    private static final String DELETE_BY_IDS ="delete from InfoNews where id in(:ids)"; 
    
    
	/**
	 * 保存新闻类型
	 * @param transientInstance
	 * @return
	 */
    public InfoNewsType save(InfoNewsType transientInstance) {
        try {
            getHibernateTemplate().saveOrUpdate(transientInstance);
            log.debug("添加新闻类型成功");
            return transientInstance;
        } catch (RuntimeException re) {
            log.error("添加新闻类型失败", re);
            throw re;
        }
    }
    
    /**
     * 根据id列表 删除新闻信息
     * @param ids
     */
    public void deleteInfoNewsTypeByIds(final List ids){
    	getHibernateTemplate().execute(new HibernateCallback(){
    		public Object doInHibernate(Session session)throws HibernateException,SQLException{
    			Query query = session.createQuery(DELETE_BY_IDS);
    			query.setParameterList("ids",ids);
    			query.executeUpdate();
    			return null;
    		}
    	});
    }
    
    
    /**
     * 删除单个新闻类型
     * @param persistentInstance
     */
	public void delete(InfoNewsType persistentInstance) {
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("删除新闻类型成功");
        } catch (RuntimeException re) {
            log.error("删除新闻类型失败", re);
            throw re;
        }
    }
    
	/**
	 * 根据id查找新闻类型
	 * @param id
	 * @return
	 */
    public InfoNewsType getInfoNewsTypeById( java.lang.String id) {
        try {
            InfoNewsType instance = (InfoNewsType) getHibernateTemplate()
                    .get("com.whaty.platform.info.bean.InfoNewsType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("查找失败", re);
            throw re;
        }
    }
    
    /**
     * 根据样例查找新闻;
     * @param instance
     * @return
     */
    public List getInfoNewsTypeByExample(final InfoNewsType instance) {
		try {

			return  this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)throws HibernateException,SQLException {
							List results = session
									.createCriteria(
											"com.whaty.platform.info.bean.InfoNewsType")
									.add(Example.create(instance)).list();
							log.debug("根据样例查找成功, result size: "
									+ results.size());
							return results;
						}
					});
		} catch (RuntimeException re) {
			log.error("根据样例查找失败", re);
			throw re;
		}
	} 
    
   /**
    * 根据条件查找新闻类型
    * @param detachedCriteria
    * @return
    */
    public List getInfoNewsTypeList(final DetachedCriteria detachedCriteria){
    	return this.getHibernateTemplate().executeFind(new HibernateCallback(){
    		public Object doInHibernate(Session session)throws HibernateException,SQLException{
    			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
    			return criteria.list();
    		}
    	});
    }
    
    /**
	 * 根据条件分页获得新闻类型
	 */
	public Page getInfoNewsTypeByPage(final DetachedCriteria detachedCriteria,
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
						
						List<InfoNewsType> items = criteria
								.setFirstResult(startIndex).setMaxResults(
										pageSize).list();
						Page pg = new Page(items,totalCount,pageSize,startIndex);
						return pg;
					}
				}, true);

	}
	
	/**
	 * 根据条件获得新闻总数
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getInfoNewsTypeTotalCount(final DetachedCriteria detachedCriteria) {
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