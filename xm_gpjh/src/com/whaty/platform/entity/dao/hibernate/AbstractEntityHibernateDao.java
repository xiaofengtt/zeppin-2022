package com.whaty.platform.entity.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.whaty.platform.entity.dao.AbstractEntityDao;
import com.whaty.platform.entity.util.Page;

public abstract class AbstractEntityHibernateDao<T, PK extends Serializable>
		extends HibernateDaoSupport implements AbstractEntityDao<T, PK> {

	protected Class entityClass;

	protected String table = "";

	private static final Log log = LogFactory
			.getLog(AbstractEntityHibernateDao.class);

	protected String DELETE_BY_IDS = "";

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
			log.error("添加失败");
			throw re;
		}
	}

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T getById(PK id) {
		try {
			T instance = (T) getHibernateTemplate().get(this.entityClass, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("查找失败");
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
							List results = session.createCriteria(table).add(
									Example.create(instance)).list();
							log.debug("根据样例查找成功, result size: "
									+ results.size());
							return results;
						}
					});
		} catch (RuntimeException re) {
			log.error("根据样例查找失败");
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

						Integer i = new Integer(0);

						Query query = session.createQuery(DELETE_BY_IDS);
						query.setParameterList("ids", ids);

						try {
							i = query.executeUpdate();
						} catch (RuntimeException re) {
							log.error("根据ID删除失败");
                            throw re;
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

						List<T> items = criteria.setFirstResult(startIndex)
								.setMaxResults(pageSize).list();

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
			log.debug("删除成功");
		} catch (RuntimeException re) {
			log.error("删除失败");
			throw re;
		}

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

						String sql = "update " + table + " n set n." + column+ " = ? where n.id in(:ids)";
						Query query = s.createQuery(sql);
						query.setString(0, value);
						query.setParameterList("ids", ids);
						try {
							count = query.executeUpdate();
						} catch (Exception e) {
							
							throw new RuntimeException(e);
						}

						return count;
					}
				});
	}
	/**
	 * 在service中拼好sql传入，返回的List为数组而非对象
	 */
	public List getBySQL(String sql){
		List list=new ArrayList();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			list=session.createSQLQuery(sql).list();
		}catch(Exception e){
			
			throw new RuntimeException(e);
		}
		session.clear();
		session.close();
	 	return list;
	}
	/**
	 * 对多表进行连接查询时，如果查询结果中含有
	 * 多个表的相同字段，比如说学生和教师连表查询时，
	 * 如果查询结果包含学生和教师的姓名，即都是name字段时，
	 * 查询的SQL语句中必须对所查询的字段起别名
	 */
	public Page getByPageSQL(String sql, int pageSize, int startIndex){
		
		List list=new ArrayList();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		
		/**
		 * 求总数
		 */
		String countSql = "select count(*) from ( "+
							sql+
							" )";
		
		String mySql = "select * from ( "+
							"select a.*, rownum rownum_ from ( "+
							sql+
							") a where rownum <= " + ( startIndex+pageSize )+
							") b where rownum_ > "+startIndex;
		/**
		 * 首先求出count
		 */
		
		int totalCount = 0;
		try{
			totalCount = Integer.parseInt(session.createSQLQuery(countSql).list().get(0).toString());
		}catch(Exception e){
			
		}
		
		try{
			list=session.createSQLQuery(mySql).list();
		}catch(Exception e){
			
			throw new RuntimeException(e);
		}
		
		Page pg = new Page(list, totalCount, pageSize,
				startIndex);
		session.clear();
		session.close();
	 	return pg;
	}

	/**
	 * 用HQL语句查询的方法
	 * 张利斌 2008.8.2
	 * @param hql
	 * @return
	 */
	public List getByHQL(String hql) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		List list = session.createQuery(hql).list();
		session.clear();
  		session.close();
	 	return list;
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
							log.error("executeByHQL 失败");
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
							log.error("executeBySQL 失败");
                            throw re;
						}

						return i;
					}
				});
	}
}
