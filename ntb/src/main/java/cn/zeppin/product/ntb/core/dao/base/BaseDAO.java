/** 
 * Project Name:Self_Cool 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.product.ntb.core.dao.base;

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zeppin.product.ntb.core.entity.base.Entity;



/** 
 * ClassName: BaseDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2016年3月31日 上午11:27:42 <br/> 
 * 
 * @author Clark.R 2016-03-31 
 * @version  
 * @since JDK 1.8
 */
public abstract class BaseDAO <T, PK extends Serializable> {
	
	private Class<T> entityClass;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 当类构造时获得entity实体的类型
	 * @author Clark
	 * @date: 2014年6月10日 上午11:39:37 <br/> 
	 * @throws NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public BaseDAO() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] types = ((ParameterizedType) type).getActualTypeArguments();
			this.setEntityClass((Class<T>) types[0]);
		}
	}
	
	/**
	 * 获得hibernate连接池中的当前的一个会话
	 * @author Clark.R
	 * @date: 2014年6月10日 上午11:36:03 <br/> 
	 * @return HibernateSession
	 */
	public Session getSession() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return session;

	}
	
	/**
	 * 向数据库存入一个entity实体
	 * @author Clark.R
	 * @date: 2014年6月10日 上午11:47:50 <br/> 
	 * @param t
	 * @return 返回持久化后对象
	 */
	protected T insert(T t) {
		// TODO Auto-generated method stub
		this.getSession().save(t);
		return t;
	}
	
	/**
	 * 更新数据库中一个entity实体
	 * @author Clark.R
	 * @date: 2014年6月10日 上午11:57:47 <br/> 
	 * @param t
	 * @return 返回持久化后对象，如果需要数据库的一些自动生成的值，请使用update
	 */
	protected T update(T t) {
		// TODO Auto-generated method stub
		this.getSession().update(t);
		return t;
		
	}
	
	/**
	 * 更新数据库中一个entity实体，若没有该实体则创建一条记录
	 * 此方法替代hibernate早起版本的saveOrUpdate，会执行两条sql，不推荐广泛使用
	 * @author Clark.R
	 * @date: 2014年6月10日 上午11:58:36 <br/> 
	 * @param t
	 * @return 返回传入hibernate的参数对象
	 */
	protected T merge(T t) {
		// TODO Auto-generated method stub
		this.getSession().merge(t);
		return t;
	}
	
	/**
	 * 删除数据库中的一个entity实体，通过主键删除，只需主键有值
	 * @author Clark.R
	 * @date: 2014年6月10日 下午12:05:00 <br/> 
	 * @param t
	 * @return List<T>
	 */
	protected T delete(T t) {
		// TODO Auto-generated method stub
		this.getSession().delete(t);
		return t;
	}
	
	/**
	 * load方法取实体时，采用lazy的属性加载方式，认为数据库必存在此条记录
	 * 若不存在记录时，则抛出异常
	 * @author Clark.R
	 * @date: 2014年6月10日 下午1:58:12 <br/> 
	 * @param id
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	protected T load(PK id) {
		// TODO Auto-generated method stub
		T t = (T) this.getSession().load(entityClass, id);
		return t;
	}
	
	/**
	 * get方法若数据库没有此ID的记录，则返回null
	 * @author Clark.R
	 * @date: 2014年6月10日 下午2:02:21 <br/> 
	 * @param id
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	protected T get(PK id) {
		// TODO Auto-generated method stub
		T t = (T) this.getSession().get(entityClass, id);
		return t;
	}
	
	/**
	 * 查询全部实体
	 * @author Clark.R
	 * @date: 2014年6月10日 下午2:07:22 <br/> 
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getAll() {
		// TODO Auto-generated method stub
		String hql = "from " + entityClass.getName();
		Query query = this.getSession().createQuery(hql);
		return query.list();
		
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体
	 * @author Clark.R
	 * @date: 2014年6月10日 下午2:27:41 <br/> 
	 * @param hql
	 * @return List<T>
	 */
	protected List<T> getByHQL(String hql) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, -1, -1, (Object[]) null);
	}
	
	/**
	 * 通过HQL语句查询符合条件的数据记录, 并按照类模板返回值
	 * @author Clark
	 * @date: 2016年4月30日 
	 * @param sql
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getByHQL(String hql, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, -1, -1, null, resultClass);
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体（HQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:33:54 <br/> 
	 * @param hql
	 * @param paras
	 * @return List<T>
	 */
	protected List<T> getByHQL(String hql, Object[] paras) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, -1, -1, paras);
	}
	
	/**
	 * 通过HQL语句查询符合条件的数据记录（HQL传参）, 并按照类模板返回值
	 * @author Clark
	 * @date: 2016年4月30日 
	 * @param sql
	 * @param paras
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getByHQL(String hql, Object[] paras, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, -1, -1, paras, resultClass);
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体（分页）
	 * @author Clark
	 * @date: 2014年6月10日 下午2:27:41 <br/> 
	 * @param hql
	 * @return List<T>
	 */
	protected List<T> getByHQL(String hql, final int pageNum, final int pageSize) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, pageNum, pageSize, (Object[]) null);
	}
	

	/**
	 * 通过HQL语句查询符合条件的数据记录, 并按照类模板返回值（分页）
	 * @param hql
	 * @param pageNum
	 * @param pageSize
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getByHQL(String hql, final int pageNum, final int pageSize, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, pageNum, pageSize, null, resultClass);
	}
	
	
	/**
	 * 通过HQL语句查询符合条件的实体（分页，HQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午2:27:41 <br/> 
	 * @param hql
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getByHQL(String hql, final int pageNum, final int pageSize, Object[] paras) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		this.setParamValueTypes(query, paras);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		List<T> list = query.list();
		return list;
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体, 并按照类模板返回值（分页，HQL传参）
	 * @param hql
	 * @param pageNum
	 * @param pageSize
	 * @param paras
	 * @param resultClass
	 * @return List<Entity>
	 */
	@SuppressWarnings("unchecked")
	protected List<Entity> getByHQL(String hql, final int pageNum, final int pageSize, Object[] paras, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		this.setParamValueTypes(query, paras);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		if (resultClass != null) {
			query.setResultTransformer(new AliasToBeanResultTransformer(resultClass));
		}
		List<Entity> list = query.list();
		return list;
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体（HQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:33:54 <br/> 
	 * @param hql
	 * @param paras
	 * @return List<T>
	 */
	protected List<T> getByHQL(String hql, Map<String, Object> paras) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, paras, -1, -1);
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体, 并按模板返回值（HQL传参）
	 * @param hql
	 * @param paras
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getByHQL(String hql, Map<String, Object> paras, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getByHQL(hql, paras, -1, -1, resultClass);
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体（分页，HQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午2:27:41 <br/> 
	 * @param hql
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getByHQL(String hql, Map<String, Object> paras, final int pageNum, final int pageSize) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		query = this.setQueryParameters(query, paras);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}

		List<T> list = query.list();
		return list;
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体, 并按模板返回值（分页，HQL传参）
	 * @param hql
	 * @param paras
	 * @param pageNum
	 * @param pageSize
	 * @param resultClass
	 * @return List<Entity>
	 */
	@SuppressWarnings("unchecked")
	protected List<Entity> getByHQL(String hql, Map<String, Object> paras, final int pageNum, final int pageSize, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		query = this.setQueryParameters(query, paras);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		if (resultClass != null) {
			query.setResultTransformer(new AliasToBeanResultTransformer(resultClass));
		}
		List<Entity> list = query.list();
		return list;
	}
	
	

	
	/**
	 * 通过SQL语句查询符合条件的数据记录
	 * @author Clark
	 * @date: 2014年6月10日 下午7:32:10 <br/> 
	 * @param sql
	 * @return List<Object[]>
	 */
	protected List<Object[]> getBySQL(String sql) {
		// TODO Auto-generated method stub
		return this.getBySQL(sql, -1, -1, (Object[]) null);
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录, 并按照类模板返回值
	 * @author Clark
	 * @date: 2016年4月30日 
	 * @param sql
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getBySQL(String sql, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getBySQL(sql, -1, -1, (Object[]) null, resultClass);
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（SQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:32:14 <br/> 
	 * @param sql
	 * @param objParas
	 * @return List<Object[]>
	 */
	protected List<Object[]> getBySQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return this.getBySQL(sql, -1, -1, objParas);
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（SQL传参）, 并按照类模板返回值
	 * @author Clark
	 * @date: 2016年4月30日 
	 * @param sql
	 * @param objParas
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getBySQL(String sql, Object[] objParas, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getBySQL(sql, -1, -1, objParas, resultClass);
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（分页）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:32:18 <br/> 
	 * @param sql
	 * @param offset
	 * @param length
	 * @return List<Object[]>
	 */
	protected List<Object[]> getBySQL(String sql, final int pageNum, final int pageSize) {
		// TODO Auto-generated method stub
		return this.getBySQL(sql, pageNum, pageSize, (Object[]) null);
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（分页）, 并按照类模板返回值
	 * @author Clark
	 * @date: 2016年4月30日 
	 * @param sql
	 * @param offset
	 * @param length
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getBySQL(String sql, final int pageNum, final int pageSize, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getBySQL(sql, pageNum, pageSize, null, resultClass);
	}
	
	/**
	 * 查询样例数据
	 * @author Clark
	 * @date: 2014年6月22日 下午3:17:18 <br/> 
	 * @param instance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByExample(T instance, final int pageNum, final int pageSize) {
		Criteria criteria = getSession().createCriteria(instance.getClass()).add(create(instance));
		int offset = (pageNum - 1) * pageSize;
		if (offset > 0 && pageSize > 0) {
			criteria.setFirstResult(offset);
			criteria.setMaxResults(pageSize);
		}
		List<T> results = criteria.list();
		return results;
	}

	/**
	 * 通过SQL语句查询符合条件的数据记录（分页，SQL传参）
	 * @param sql
	 * @param offset
	 * @param length
	 * @param objParas
	 * @return List<Object>
	 */
	@SuppressWarnings("unchecked")
	protected List<Object> getObjectBySQL(String sql, final int pageNum, final int pageSize, Object[] objParas) {
		Query query = getSession().createSQLQuery(sql);
		this.setParamValueTypes(query, objParas);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		return query.list();		
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（分页，SQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:27:49 <br/> 
	 * @param sql
	 * @param offset
	 * @param length
	 * @param objParas
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	protected List<Object[]> getBySQL(String sql, final int pageNum, final int pageSize, Object[] objParas) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(sql);
		this.setParamValueTypes(query, objParas);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.list();
		return list;		
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（分页，SQL传参）, 并按照类模板返回值
	 * @author Clark
	 * @date: 2016年4月30日 
	 * @param sql
	 * @param offset
	 * @param length
	 * @param objParas
	 * @param resultClass
	 * @return List<Entity>
	 */
	@SuppressWarnings("unchecked")
	protected List<Entity> getBySQL(String sql, final int pageNum, final int pageSize, Object[] objParas, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(sql);
		this.setParamValueTypes(query, objParas);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		if (resultClass != null) {
			query.setResultTransformer(new AliasToBeanResultTransformer(resultClass));
		}
		
		List<Entity> list = query.list();
		return list;		
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（SQL传参）, 并按照类模板返回值
	 * @param sql
	 * @param objParas
	 * @param resultClass
	 * @return List<Entity>
	 */
	protected List<Entity> getBySQL(String sql, Map<String, Object> objParas, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getBySQL(sql, objParas, -1, -1, resultClass);		
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（分页，SQL传参）, 并按照类模板返回值
	 * @param sql
	 * @param objParas
	 * @param pageNum
	 * @param pageSize
	 * @param resultClass
	 * @return List<Entity>
	 */
	@SuppressWarnings("unchecked")
	protected List<Entity> getBySQL(String sql, Map<String, Object> objParas, final int pageNum, final int pageSize, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(sql);
		query = this.setQueryParameters(query, objParas);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		if (resultClass != null) {
			query.setResultTransformer(new AliasToBeanResultTransformer(resultClass));
		}
		
		List<Entity> list = query.list();
		return list;		
	}
	
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组
	 * @author Clark
	 * @date: 2014年6月10日 下午6:36:39 <br/> 
	 * @param hql
	 * @return List<Object[]>
	 */
	protected List<Object[]> getMultiByHQL(String hql) {
		// TODO Auto-generated method stub
		return this.getMultiByHQL(hql, -1, -1, null);
	}
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组（HQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:36:50 <br/> 
	 * @param hql
	 * @param paras
	 * @return List<Object[]>
	 */
	protected List<Object[]> getMultiByHQL(String hql, Object[] paras) {
		// TODO Auto-generated method stub
		return this.getMultiByHQL(hql, -1, -1, paras);
	}
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组（分页）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:06:05 <br/> 
	 * @param hql
	 * @param offset
	 * @param length
	 * @return List<Object[]>
	 */
	protected List<Object[]> getMultiByHQL(String hql, final int pageNum, final int pageSize) {
		// TODO Auto-generated method stub
		return this.getMultiByHQL(hql, pageNum, pageSize, null);
	}
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组（分页，HQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午6:38:49 <br/> 
	 * @param hql
	 * @param offset
	 * @param length
	 * @param paras
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	protected List<Object[]> getMultiByHQL(String hql, final int pageNum, final int pageSize, Object[] paras) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		this.setParamValueTypes(query, paras);
		int offset = (pageNum - 1) * pageSize;
		if (offset >= 0 && pageSize > 0){
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.list();
		return list;
	}
	
	/**
	 * 通过HQL语句查找唯一结果，一般用于通过主键查询、唯一索引查询、count(*)等操作
	 * @author Clark
	 * @date: 2014年6月10日 下午2:31:46 <br/> 
	 * @param hql
	 * @return Object
	 */
	protected Object getResultByHQL(final String hql) {
		// TODO Auto-generated method stub
		return this.getResultByHQL(hql, (Object[]) null);
	}
	
	/**
	 * 通过HQL语句查找唯一结果，一般用于通过主键查询、唯一索引查询、count(*)等操作
	 * @param hql
	 * @param resultClass
	 * @return Entity
	 */
	protected Entity getResultByHQL(final String hql, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.getResultByHQL(hql, null, resultClass);
	}
	
	/**
	 * 通过HQL语句查找唯一结果，一般用于通过主键查询、唯一索引查询、count(*)等操作（HQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午2:31:46 <br/> 
	 * @param hql
	 * @param paras
	 * @return Object
	 */
	protected Object getResultByHQL(final String hql, final Object[] paras) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		this.setParamValueTypes(query, paras);
		Object result = query.uniqueResult();
		return result;
	}
	
	/**
	 * 通过HQL语句查找唯一结果，一般用于通过主键查询、唯一索引查询、count(*)等操作（HQL传参）
	 * @param hql
	 * @param paras
	 * @param resultClass
	 * @return
	 */
	protected Entity getResultByHQL(final String hql, final Object[] paras, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		this.setParamValueTypes(query, paras);
		if (resultClass != null) {
			query.setResultTransformer(new AliasToBeanResultTransformer(resultClass));
		}
		return (Entity) query.uniqueResult();
	}
	
	/**
	 * 通过SQL语句查找唯一结果，一般用于通过主键查询、唯一索引查询、count(*)等操作
	 * @author Clark
	 * @date: 2014年6月10日 下午7:50:43 <br/> 
	 * @param sql
	 * @return Object
	 */
	protected Object getResultBySQL(final String sql){
		// TODO Auto-generated method stub
		return this.getResultBySQL(sql, (Object[]) null);
	}
	
	/**
	 * 通过SQL语句查找唯一结果, 一般用于通过主键查询、唯一索引查询、count(*)等操作
	 * @param sql
	 * @param resultClass
	 * @return Entity
	 */
	protected Entity getResultBySQL(final String sql, Class<? extends Entity> resultClass){
		return this.getResultBySQL(sql, null, resultClass);
	}
	
	/**
	 * 通过SQL语句查找唯一结果，一般用于count(*)等操作（SQL传参）
	 * @author Clark
	 * @date: 2014年6月10日 下午7:49:57 <br/> 
	 * @param sql
	 * @param paras
	 * @return Object
	 */
	protected Object getResultBySQL(final String sql, final Object[] paras) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(sql);
		this.setParamValueTypes(query, paras);
		return query.uniqueResult();
	}
	
	/**
	 * 通过SQL语句查找唯一结果, 一般用于通过主键查询、唯一索引查询、count(*)等操作
	 * @param sql
	 * @param paras
	 * @param resultClass
	 * @return Entity
	 */
	protected Entity getResultBySQL(final String sql, final Object[] paras, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(sql);
		this.setParamValueTypes(query, paras);
		if (resultClass != null) {
			query.setResultTransformer(new AliasToBeanResultTransformer(resultClass));
		}
		return (Entity) query.uniqueResult();
	}
	
	/**
	 * 通过HQL语句更新数据库
	 * @author Clark
	 * @date: 2014年6月10日 下午7:41:27 <br/> 
	 * @param hql
	 * @return 返回值是一个整数，指示受影响的数据行数（ create、drop等操作为零）
	 */
	protected int executeHQL(final String hql) {
		return this.executeHQL(hql, null);
	}
	
	/**
	 * 通过HQL语句更新数据库(传参)
	 * @author Clark
	 * @date: 2014年6月10日 下午7:19:40 <br/> 
	 * @param hql
	 * @return 返回值是一个整数，指示受影响的数据行数（ create、drop等操作为零）
	 */
	protected int executeHQL(final String hql, final Object[] paras) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		this.setParamValueTypes(query, paras);
		return query.executeUpdate();
	}
	
	

	/**
	 * 通过SQL语句更新数据库
	 * @author Clark
	 * @date: 2014年6月10日 下午7:47:42 <br/> 
	 * @param sql
	 * @return 返回值是一个整数，指示受影响的数据行数（ create、drop等操作为零）
	 */
	protected int executeSQL(final String sql) {
		return this.executeSQL(sql, null);
	}
	
	/**
	 * 通过SQL语句更新数据库(传参)
	 * @author Clark
	 * @date: 2014年6月10日 下午7:44:03 <br/> 
	 * @param sql
	 * @param objParas
	 * @return 返回值是一个整数，指示受影响的数据行数（ create、drop等操作为零）
	 */
	protected int executeSQL(final String sql, final Object[] paras) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(sql);
		this.setParamValueTypes(query, paras);
		return query.executeUpdate();
	}
	
	/**
	 * 设置query参数类型
	 * @param query
	 * @param paras
	 */
	protected void setParamValueTypes(Query query, Object[] paras) {
		// TODO Auto-generated method stub
		if (paras != null && paras.length > 0) {
			for (Integer i = 0; i < paras.length; i++) {
				if(paras[i] == null){
					continue;
				}
				query.setParameter(i.toString(), paras[i]);
//				if (paras[i] instanceof Integer) {
//					query.setInteger(i, ((Integer) paras[i]).intValue());
//				} else if (paras[i] instanceof String) {
//					query.setString(i, (String) paras[i]);
//				} else if (paras[i] instanceof BigDecimal) {
//					query.setBigDecimal(i, (BigDecimal) paras[i]);
//				} else if (paras[i] instanceof BigInteger) {
//					query.setBigInteger(i, (BigInteger) paras[i]);
//				} else if (paras[i] instanceof Byte) {
//					query.setByte(i, (Byte) paras[i]);
//				} else if (paras[i] instanceof Calendar) {
//					query.setCalendar(i, (Calendar) paras[i]);
//				} else if (paras[i] instanceof Short) {
//					query.setShort(i, (Short) paras[i]);
//				} else if (paras[i] instanceof Double) {
//					query.setDouble(i, ((Double) paras[i]).doubleValue());
//				} else if (paras[i] instanceof Float) {
//					query.setFloat(i, ((Float) paras[i]).floatValue());
//				} else if (paras[i] instanceof Long) {
//					query.setLong(i, ((Long) paras[i]).longValue());
//				} else if (paras[i] instanceof Boolean) {
//					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
//				} else if (paras[i] instanceof Date) {
//					query.setDate(i, (Date) paras[i]);
//				} else if (paras[i] instanceof Timestamp) {
//					query.setTimestamp(i, (Date) paras[i]);
//				} else {
//					query.setParameter(i, paras[i]);
//				}
			}
		}
	}
	
	/**
	 * 设置参数值
	 * @param query
	 * @param parameters
	 * @return
	 */
	protected Query setQueryParameters(Query query,Map<String, Object> parameters){
		if (parameters!=null&&parameters.size()> 0) {	//key   id=:id     enable=:enable
		//int begin=0;
			for(String key:parameters.keySet()){	//where id=:id and enable=:enable
				Pattern pattern = Pattern.compile(":\\w+");
				Matcher matcher = pattern.matcher(key);
				if(matcher.find()){
					//System.out.println(matcher.group(0));
					query.setParameter(matcher.group(0).substring(1, matcher.group(0).length()),parameters.get(key));
				}
			}
		}
		return query;
	}
	
	/**
	 * 检查SQL order by 的语句
	 * @param orderBy
	 * @return
	 */
	protected String checkOrderByStr(String orderBy){
		int i = orderBy.indexOf(";");
		if(i > 0){
			orderBy = orderBy.substring(0,orderBy.indexOf(";"));
		}
		
		return orderBy;
	}
	
	/**
	 * 批处理操作
	 * @param sql
	 * @param paras
	 */
	protected void insert(final String sql, final List<Object[]> paras){
		getSession().doWork(new Work() {
			
			@Override
			public void execute(Connection conn) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement stmt = conn.prepareStatement(sql);
				conn.setAutoCommit(false);
				for(int i = 0; i< paras.size(); i++){
					Object[] obj = paras.get(i);
					for (int j = 0; j < obj.length; j++) {
						stmt.setObject(j+1, obj[j]);
					}
					stmt.addBatch();
					if (i % 100 == 0) {
				        stmt.executeBatch();
				        conn.commit();
				    }
				}
				stmt.executeBatch();
				conn.commit();
				
//				getSession().close();
			}
		});
	}
}
