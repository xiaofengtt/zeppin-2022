package com.whaty.platform.entity.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeArea;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzCourseFeedback;
import com.whaty.platform.entity.bean.PeBzzSuggestion;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.util.Page;

public class GeneralHibernateDao<T extends AbstractBean> extends
		HibernateDaoSupport implements GeneralDao<T> {

	private Class entityClass;

	private static final Log log = LogFactory.getLog(GeneralHibernateDao.class);

	/**
	 * ??????
	 */
	public T save(T transientInstance) {
		log.debug("saving  instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("????????????");
			return transientInstance;
		} catch (RuntimeException re) {
			log.error("????????????", re);
			throw re;
		}
	}

	/**
	 * ??????id??????
	 * 
	 * @param id
	 * @return
	 */
	public T getById(String id) {
		try {
			T instance = (T) getHibernateTemplate().get(this.entityClass, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("????????????", re);
			throw re;
		}
	}
	//??????????????????????????????
	public T getById(Class clazz,String id) {
		try {
			T instance = (T) getHibernateTemplate().get(clazz, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("????????????", re);
			throw re;
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param instance
	 * @return
	 */
	public List getByExample(final T instance) {
		try {

			return this.getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							List results = session.createCriteria(
									entityClass.getName()).add(
									Example.create(instance)).list();
							log.debug("????????????????????????, result size: "
									+ results.size());
							return results;
						}
					});
		} catch (RuntimeException re) {
			log.error("????????????????????????", re);
			throw re;
		}
	}

	/**
	 * ??????id?????? ??????
	 */

	public int deleteByIds(final List ids) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {

						int i = 0;

						Query query = session.createQuery("delete from "
								+ entityClass.getName() + " where id in(:ids)");
						query.setParameterList("ids", ids);

						try {
							i = query.executeUpdate();
						} catch (RuntimeException re) {
							i = 0;
							// log.error("??????????????????", re);
							for (Object object : ids) {
								try {
									T instance = getById((String) object);
									delete(instance);
									i++;
								} catch (RuntimeException re1) {
									log.error("??????????????????", re1);
									throw re1;
								}
							}
						}

						return i;
					}
				});

	}

	/**
	 * ????????????????????????
	 */
	public Page getByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex) {
		return (Page) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);

						CriteriaImpl impl = (CriteriaImpl) criteria;
						// ??????Projection???OrderBy?????????????????????????????????????Count??????
						Projection projection = impl.getProjection();

						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());

						// ????????????Projection???OrderBy??????????????????????
						criteria.setProjection(projection);
						if (projection == null) {
							criteria
									.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
						}

						List<T> items = null;

						try {
							items = criteria.setFirstResult(startIndex)
									.setMaxResults(pageSize).list();
						} catch (RuntimeException re) {
							log.error("??????????????????");
							throw re;
						}

						Page pg = new Page(items, totalCount, pageSize,
								startIndex);
						return pg;
					}
				}, true);

	}

	/**
	 * ??????????????????
	 */
	public List getList(final DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				return criteria.list();
			}
		});
	}

	/**
	 * ????????????
	 */
	public void delete(T persistentInstance) {
		try {
			getHibernateTemplate().delete(persistentInstance);
			// getHibernateTemplate().flush();
			log.debug("????????????");
		} catch (RuntimeException re) {
			log.error("????????????", re);
			throw re;
		}

	}

	public HibernateTemplate getMyHibernateTemplate() {
		return this.getHibernateTemplate();
	}

	/**
	 * ????????????????????????
	 */
	public int updateColumnByIds(final List ids, final String column,
			final String value) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {

						int count = 0;
						String[] columns = column.split(",");
						String[] values = value.split(",");
						if (columns.length != values.length) {
							throw new RuntimeException("???????????? ????????????????????????");
						}

						String sqlPrepare = " set";
						for (int i = 0; i < columns.length; i++) {
							sqlPrepare += " n." + columns[i] + " = ? ,";
						}
						sqlPrepare = sqlPrepare.substring(0, sqlPrepare
								.length() - 1);

						String sql = "update " + entityClass.getName() + " n "
								+ sqlPrepare + " where n.id in(:ids)";
						Query query = s.createQuery(sql);

						for (int j = 0; j < values.length; j++) {
							if (columns[j].toLowerCase().endsWith("date")) {
								query.setDate(j, new Date(Long
										.parseLong(values[j])));
							} else {
								query.setString(j, values[j]);
							}
						}
						query.setParameterList("ids", ids);

						try {
							count = query.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}

						return count;
					}
				});
	}

	/**
	 * ???service?????????sql??????????????????List?????????????????????
	 */
	public List getBySQL(final String sql) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				List list = new ArrayList();

				Query query = session.createSQLQuery(sql);

				try {
					list = query.list();
				} catch (RuntimeException re) {
					log.error("getBySQL ??????", re);
					throw re;
				}

				return list;
			}
		});
	}
	
	public List getBySQL(final String sql,final Map map){
		
	 	return (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {

						List list=new ArrayList();

						Query query = session.createSQLQuery(sql);
						
						Iterator iter = map.keySet().iterator();
						while(iter.hasNext()){
							Object key = iter.next();
							Object value = map.get(key);
							query.setParameter(key.toString(), value);  
						}

						try {
							list = query.list();
						} catch (RuntimeException re) {
							log.error("getBySQL ??????", re);
                            throw re;
						}

						return list;
					}
				});
	}


	/**
	 * ???????????????????????????????????????????????????????????? ????????????????????????????????????????????????????????????????????? ????????????????????????????????????????????????????????????name????????????
	 * ?????????SQL?????????????????????????????????????????????
	 */
	public Page getByPageSQL(String sql, int pageSize, int startIndex) {

		List list = new ArrayList();
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		/**
		 * ?????????
		 */
		String countSql = "select count(*) from ( " + sql + " )";

		String mySql = "select * from ( "
				+ "select  a.*, rownum rownum_ from ( " + sql
				+ ") a where rownum <= " + (startIndex + pageSize)
				+ ") b where rownum_ > " + startIndex;
		/**
		 * ????????????count
		 */

		int totalCount = 0;
		try {
			totalCount = Integer.parseInt(session.createSQLQuery(countSql)
					.list().get(0).toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			list = session.createSQLQuery(mySql).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Page pg = new Page(list, totalCount, pageSize, startIndex);
		session.clear();
		session.close();
		return pg;
	}

	/**
	 * ????????????????????????????????????????????????????????????
	 * ?????????????????????????????????????????????????????????????????????
	 * ????????????????????????????????????????????????????????????name????????????
	 * ?????????SQL?????????????????????????????????????????????
	 */
	public Page getByPageSQL(String sql, int pageSize, int startIndex,final Map map){
		
		List list=new ArrayList();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		
		/**
		 * ?????????
		 */
		String countSql = "select count(*) from ( "+
							sql+
							" )";
		
		String mySql = "select * from ( "+
							"select  a.*, rownum rownum_ from ( "+
							sql+
							") a where rownum <= " + ( startIndex+pageSize )+
							") b where rownum_ > "+startIndex;
		Query query1 = session.createSQLQuery(countSql);
		Query query2 = session.createSQLQuery(mySql);
		
		Iterator iter = map.keySet().iterator();
		while(iter.hasNext()){
			Object key = iter.next();
			Object value = map.get(key);
			query1.setParameter(key.toString(), value); 
			query2.setParameter(key.toString(), value);
		}
		/**
		 * ????????????count
		 */
		
		int totalCount = 0;
		try{
			totalCount = Integer.parseInt(query1.list().get(0).toString());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
		
		try{
			list=query2.list();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Page pg = new Page(list, totalCount, pageSize,
				startIndex);
		session.clear();
		session.close();
	 	return pg;
	}
	/**
	 * ???HQL????????????????????? ????????? 2008.8.2
	 * 
	 * @param hql
	 * @return
	 */
	public List getByHQL(final String hql) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				List list = new ArrayList();

				Query query = session.createQuery(hql);

				try {
					list = query.list();
				} catch (RuntimeException re) {
					log.error("getByHQL ??????", re);
					throw re;
				}

				return list;
			}
		});

	}

	public int executeByHQL(final String hql) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {

						Integer i = new Integer(0);

						Query query = session.createQuery(hql);

						try {
							i = query.executeUpdate();
						} catch (RuntimeException re) {
							log.error("executeByHQL ??????", re);
							throw re;
						}

						return i;
					}
				});
	}

	public int executeBySQL(final String sql) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {

						Integer i = new Integer(0);

						Query query = session.createSQLQuery(sql);

						try {
							i = query.executeUpdate();
						} catch (RuntimeException re) {
							log.error("executeBySQL ??????", re);
							throw re;
						}

						return i;
					}
				});
	}
	
	public int executeBySQL(final String sql,final Map map) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {

						Integer i = new Integer(0);

						Query query = session.createSQLQuery(sql);
						
						Iterator iter = map.keySet().iterator();
						while(iter.hasNext()){
							Object key = iter.next();
							Object value = map.get(key);
							query.setParameter(key.toString(), value);  
						}
						try {
							i = query.executeUpdate();
						} catch (RuntimeException re) {
							log.error("executeBySQL ??????", re);
                            throw re;
						}

						return i;
					}
				});
	}

	public EnumConst getEnumConstByNamespaceCode(String namespace, String code) {
		final String hql = "from EnumConst e where e.namespace='" + namespace
				+ "' and e.code='" + code + "'";
		List list = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						List list = new ArrayList();
						Query query = session.createQuery(hql);

						try {
							list = query.list();
						} catch (RuntimeException re) {
							re.printStackTrace();
						}

						return list;
					}
				});
		if (list != null && list.size() == 1) {
			return (EnumConst) list.get(0);
		}
		return null;

	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public PeBulletin getPeBulletin(String id) {
		return (PeBulletin) getHibernateTemplate().get(PeBulletin.class, id);
	}

	public PeInfoNews getPeInfoNews(String id) {
		// TODO Auto-generated method stub
		return (PeInfoNews) getHibernateTemplate().get(PeInfoNews.class, id);
	}

	public List getNewBulletins(final String id) {
		List tlist = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String sql = "select t.title, t.id,t.PUBLISH_DATE from pe_bulletin t where t.scope_string like '%students%' and t.scope_string like (select '%site:' || ps.id || '%' from PE_SITE ps, pe_student pt where pt.fk_site_id = ps.id and pt.fk_sso_user_id = '"
								+ id + "')";
						Query query = session.createSQLQuery(sql);
						return query.list();
					}
				});
		return tlist;
	}

	public PeSitemanager getPeSitemanager(String loginId) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String hql = "from PeSitemanager pe where pe.loginId='" + loginId + "'";
		Query query = session.createQuery(hql);
		PeSitemanager sitemanager = (PeSitemanager) query.uniqueResult();
		return sitemanager;
	}

	
	public PeEnterpriseManager getEnterprisemanager(String loginId) {
		// TODO Auto-generated method stub
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql ="from PeEnterpriseManager pe where pe.loginId='"+loginId+"'";
		Query query = session.createQuery(hql);
		PeEnterpriseManager enterprisemanager = (PeEnterpriseManager)query.uniqueResult();
		return	enterprisemanager;
	}

	public PeEnterprise getSubEnterprise(final String id) {
		return (PeEnterprise) this.getHibernateTemplate().get(PeEnterprise.class, id);
	}

	public List getStuBulletins() {
		List list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String hql = "from PeBulletin where scopeString like '%students%'";
						Query query = session.createQuery(hql);

						return query.list();
					}
				});
		return list;
	}

	public void saveTest(T entity,String id){
	    System.out.println(this.getHibernateTemplate().get(entity.getClass(), id));
	}

	public void saveSsoUser(SsoUser sso) {
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER);
		this.getHibernateTemplate().save(sso);

	}
	
	public void delete(SsoUser ssoUser) {
		this.getHibernateTemplate().delete(ssoUser);
	}
	
	public PeTrainee getStudentInfo(DetachedCriteria studc) {
		Session session  = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = studc.getExecutableCriteria(session);
		PeTrainee peStudent = (PeTrainee)criteria.uniqueResult();
		return peStudent;
	}
	
	
	public PrBzzTchOpencourse getPrBzzTchOpencourse(DetachedCriteria pdc) {
		Session session  = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = pdc.getExecutableCriteria(session);
		PrBzzTchOpencourse tchOpencourse = (PrBzzTchOpencourse)criteria.uniqueResult();
		return tchOpencourse;
	}
	public List<PeBzzCourseFeedback> getFeeDbackList(DetachedCriteria feeDback) {
		Session session  = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = feeDback.getExecutableCriteria(session);
		List<PeBzzCourseFeedback> list = criteria.list();
		return list;
	}
	
 	public void updatepeBzzSuggestion(PeBzzSuggestion peBzzSuggestion) {
		this.getHibernateTemplate().update(peBzzSuggestion);
	}
	public PeBzzSuggestion getPeBzzSuggestion(String sugid) {
		return (PeBzzSuggestion) this.getHibernateTemplate().get(PeBzzSuggestion.class, sugid);
	}
	public void updatePeBzzAssess(PeBzzAssess assess) {
		this.getHibernateTemplate().update(assess);
	}
	public void updateSsoUser(SsoUser ssoUser) {
		this.getHibernateTemplate().update(ssoUser);
	}
	public void updatePeEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.getHibernateTemplate().update(enterpriseManager);
	}
	public void updatePeManager(PeManager peManager) {
		this.getHibernateTemplate().update(peManager);
	}
	public void updateEnterpriseManager(PeEnterpriseManager enterpriseManager) {
		this.getHibernateTemplate().update(enterpriseManager);
	}
	public void updateelective(PrBzzTchStuElective elective) {
		this.getHibernateTemplate().update(elective);
	}
	public void updateelective(TrainingCourseStudent trainingCourseStudent) {
		this.getHibernateTemplate().update(trainingCourseStudent);
	}
	public PeBzzBatch getPeBzzBatch(DetachedCriteria dCriteria) {
		Session session  = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = dCriteria.getExecutableCriteria(session);
		PeBzzBatch peBzzBatch = (PeBzzBatch)criteria.uniqueResult();
		return peBzzBatch;
	}
	public void update(T persistentInstance) {
		this.getHibernateTemplate().update(persistentInstance);
	}
	
	public List getDetachList(final DetachedCriteria detachedCriteria) {
	  Session  session = this.getHibernateTemplate().getSessionFactory().openSession();
	  Criteria criteria = detachedCriteria.getExecutableCriteria(session);
	  List list = criteria.list();
	  session.evict(list);
	  session.close();
	    return list;
	}


	public WhatyuserLog4j saveLog(WhatyuserLog4j whatyuserLog4j) {
		try {
			getHibernateTemplate().saveOrUpdate(whatyuserLog4j);
			log.debug("????????????");
			return whatyuserLog4j;
		} catch (RuntimeException re) {
			log.error("????????????", re);
			throw re;
		}
	}
	

	public PeArea savePeArea(PeArea pa) {
		try {
			getHibernateTemplate().saveOrUpdate(pa);
			log.debug("????????????");
			return pa;
		} catch (RuntimeException re) {
			log.error("????????????", re);
			throw re;
		}
	}

	
}
