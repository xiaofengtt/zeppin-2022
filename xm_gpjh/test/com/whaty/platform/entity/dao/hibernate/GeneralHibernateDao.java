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
	 * 保存
	 */
	public T save(T transientInstance) {
		log.debug("saving  instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("添加成功");
			return transientInstance;
		} catch (RuntimeException re) {
			log.error("添加失败", re);
			throw re;
		}
	}

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T getById(String id) {
		try {
			T instance = (T) getHibernateTemplate().get(this.entityClass, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("查找失败", re);
			throw re;
		}
	}
	//解决高并发出错的问题
	public T getById(Class clazz,String id) {
		try {
			T instance = (T) getHibernateTemplate().get(clazz, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("查找失败", re);
			throw re;
		}
	}

	/**
	 * 根据样例查找
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
	 * 根据id列表 删除
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
							// log.error("批量删除失败", re);
							for (Object object : ids) {
								try {
									T instance = getById((String) object);
									delete(instance);
									i++;
								} catch (RuntimeException re1) {
									log.error("单个删除失败", re1);
									throw re1;
								}
							}
						}

						return i;
					}
				});

	}

	/**
	 * 根据条件分页获得
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
						// 先把Projection和OrderBy条件取出�?清空两者来执行Count操作
						Projection projection = impl.getProjection();

						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());

						// 将之前的Projection和OrderBy条件重新设回�?
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
							log.error("分页查找失败");
							throw re;
						}

						Page pg = new Page(items, totalCount, pageSize,
								startIndex);
						return pg;
					}
				}, true);

	}

	/**
	 * 根据条件查找
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
	 * 删除单个
	 */
	public void delete(T persistentInstance) {
		try {
			getHibernateTemplate().delete(persistentInstance);
			// getHibernateTemplate().flush();
			log.debug("删除成功");
		} catch (RuntimeException re) {
			log.error("删除失败", re);
			throw re;
		}

	}

	public HibernateTemplate getMyHibernateTemplate() {
		return this.getHibernateTemplate();
	}

	/**
	 * 批量更新一个字段
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
							throw new RuntimeException("更新操作 列与值数量不匹配");
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
	 * 在service中拼好sql传入，返回的List为数组而非对象
	 */
	public List getBySQL(final String sql) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				List list = new ArrayList();

				Query query = session.createSQLQuery(sql);

				try {
					list = query.list();
				} catch (RuntimeException re) {
					log.error("getBySQL 失败", re);
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
							log.error("getBySQL 失败", re);
                            throw re;
						}

						return list;
					}
				});
	}


	/**
	 * 对多表进行连接查询时，如果查询结果中含有 多个表的相同字段，比如说学生和教师连表查询时， 如果查询结果包含学生和教师的姓名，即都是name字段时，
	 * 查询的SQL语句中必须对所查询的字段起别名
	 */
	public Page getByPageSQL(String sql, int pageSize, int startIndex) {

		List list = new ArrayList();
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();

		/**
		 * 求总数
		 */
		String countSql = "select count(*) from ( " + sql + " )";

		String mySql = "select * from ( "
				+ "select  a.*, rownum rownum_ from ( " + sql
				+ ") a where rownum <= " + (startIndex + pageSize)
				+ ") b where rownum_ > " + startIndex;
		/**
		 * 首先求出count
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
	 * 对多表进行连接查询时，如果查询结果中含有
	 * 多个表的相同字段，比如说学生和教师连表查询时，
	 * 如果查询结果包含学生和教师的姓名，即都是name字段时，
	 * 查询的SQL语句中必须对所查询的字段起别名
	 */
	public Page getByPageSQL(String sql, int pageSize, int startIndex,final Map map){
		
		List list=new ArrayList();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		
		/**
		 * 求总数
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
		 * 首先求出count
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
	 * 用HQL语句查询的方法 张利斌 2008.8.2
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
					log.error("getByHQL 失败", re);
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
							log.error("executeByHQL 失败", re);
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
							log.error("executeBySQL 失败", re);
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
							log.error("executeBySQL 失败", re);
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
			log.debug("添加成功");
			return whatyuserLog4j;
		} catch (RuntimeException re) {
			log.error("添加失败", re);
			throw re;
		}
	}
	

	public PeArea savePeArea(PeArea pa) {
		try {
			getHibernateTemplate().saveOrUpdate(pa);
			log.debug("添加成功");
			return pa;
		} catch (RuntimeException re) {
			log.error("添加失败", re);
			throw re;
		}
	}

	
}
