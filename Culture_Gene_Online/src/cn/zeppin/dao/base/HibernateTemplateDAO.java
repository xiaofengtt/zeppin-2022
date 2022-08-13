package cn.zeppin.dao.base;

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class HibernateTemplateDAO<T, PK extends Serializable> {
	
	private Class<T> entityClass;
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
	 */
	@SuppressWarnings("unchecked")
	public HibernateTemplateDAO() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] types = ((ParameterizedType) type).getActualTypeArguments();
			this.setEntityClass((Class<T>) types[0]);
		}
	}
	
	/**
	 * 获得hibernate连接池中的当前的一个会话
	 */
	public Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;

	}
	
	/**
	 * 向数据库存入一个entity实体
	 */
	public T save(T t) {
		this.getSession().save(t);
		return t;
	}
	
	/**
	 * 更新数据库中一个entity实体
	 */
	public T update(T t) {
		this.getSession().update(t);
		return t;
		
	}
	
	/**
	 * 更新数据库中一个entity实体，若没有该实体则创建一条记录
	 * 此方法替代hibernate早起版本的saveOrUpdate，会执行两条sql，不推荐广泛使用
	 */
	public T merge(T t) {
		this.getSession().merge(t);
		return t;
	}
	
	/**
	 * 删除数据库中的一个entity实体，通过主键删除，只需主键有值
	 */
	public T delete(T t) {
		this.getSession().delete(t);
		return t;
	}
	
	/**
	 * load方法取实体时，采用lazy的属性加载方式，认为数据库必存在此条记录
	 * 若不存在记录时，则抛出异常
	 */
	@SuppressWarnings("unchecked")
	public T load(PK id) {
		T t = (T) this.getSession().load(entityClass, id);
		return t;
	}
	
	/**
	 * get方法若数据库没有此ID的记录，则返回null
	 */
	@SuppressWarnings("unchecked")
	public T get(PK id) {
		T t = (T) this.getSession().get(entityClass, id);
		return t;
	}
	
	/**
	 * 查询全部实体
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		Query query = this.getSession().createQuery(hql);
		return query.list();
		
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体
	 */
	protected List<T> getByHQL(String hql) {
		return this.getByHQL(hql, -1, -1, null);
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体（HQL传参）
	 */
	protected List<T> getByHQL(String hql, Object[] paras) {
		return this.getByHQL(hql, -1, -1, paras);
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体（分页）
	 */
	protected List<T> getByHQL(String hql, final int offset, final int length) {
		return this.getByHQL(hql, offset, length, null);
	}
	
	/**
	 * 通过HQL语句查询符合条件的实体（分页，HQL传参）
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getByHQL(String hql, final int offset, final int length, Object[] paras) {
		Query query = getSession().createQuery(hql);
		if (offset >= 0 && length > 0){
			query.setFirstResult(offset);
			query.setMaxResults(length);
		}
		if (paras != null && paras.length > 0) {
			for (int i = 0; i < paras.length; i++) {
				if (paras[i] instanceof Integer) {
					query.setInteger(i, ((Integer) paras[i]).intValue());
				} else if (paras[i] instanceof String) {
					query.setString(i, (String) paras[i]);
				} else if (paras[i] instanceof Double) {
					query.setDouble(i, ((Double) paras[i]).doubleValue());
				} else if (paras[i] instanceof Float) {
					query.setFloat(i, ((Float) paras[i]).floatValue());
				} else if (paras[i] instanceof Long) {
					query.setLong(i, ((Long) paras[i]).longValue());
				} else if (paras[i] instanceof Boolean) {
					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
				} else if (paras[i] instanceof Date) {
					query.setDate(i, (Date) paras[i]);
				} else {
					query.setParameter(i, paras[i]);
				}
			}
		}
		List<T> list = query.list();
		return list;
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录
	 */
	protected List<Object[]> getBySQL(String sql) {
		return this.getBySQL(sql, -1, -1, null);
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（SQL传参）
	 */
	protected List<Object[]> getBySQL(String sql, Object[] objParas) {
		return this.getBySQL(sql, -1, -1, objParas);
	}
	
	/**
	 * 通过SQL语句查询符合条件的数据记录（分页）
	 */
	protected List<Object[]> getBySQL(String sql, final int offset, final int length) {
		return this.getBySQL(sql, offset, length, null);
	}
	
	/**
	 * 查询样例数据
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T instance, final int offset, final int length) {
		Criteria criteria = getSession().createCriteria(instance.getClass()).add(create(instance));
		if (offset > 0 && length > 0) {
			criteria.setFirstResult(offset);
			criteria.setMaxResults(length);
		}
		List<T> results = criteria.list();
		return results;
	}

	
	/**
	 * 通过SQL语句查询符合条件的数据记录（分页，SQL传参）
	 */
	@SuppressWarnings("unchecked")
	protected List<Object[]> getBySQL(String sql, final int offset, final int length, Object[] objParas) {
		Query query = getSession().createSQLQuery(sql);
		if (offset >= 0 && length > 0){
			query.setFirstResult(offset);
			query.setMaxResults(length);
		}
		if (objParas != null && objParas.length > 0) {
			for (int i = 0; i < objParas.length; i++) {
				query.setParameter(i, objParas[i]);
			}
		}
		List<Object[]> list = query.list();
		return list;		
	}
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组
	 */
	protected List<Object[]> getMultiByHQL(String hql) {
		return this.getMultiByHQL(hql, -1, -1, null);
	}
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组（HQL传参）
	 */
	protected List<Object[]> getMultiByHQL(String hql, Object[] paras) {
		return this.getMultiByHQL(hql, -1, -1, paras);
	}
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组（分页）
	 */
	protected List<Object[]> getMultiByHQL(String hql, final int offset, final int length) {
		return this.getMultiByHQL(hql, offset, length, null);
	}
	
	/**
	 * 用HQL联表查询多个对象时使用，返回多个对象的Object数组（分页，HQL传参）
	 */
	@SuppressWarnings("unchecked")
	protected List<Object[]> getMultiByHQL(String hql, final int offset, final int length, Object[] paras) {
		Query query = getSession().createQuery(hql);
		if (paras != null && paras.length > 0) {
			for (int i = 0; i < paras.length; i++) {
				if (paras[i] instanceof Integer) {
					query.setInteger(i, ((Integer) paras[i]).intValue());
				} else if (paras[i] instanceof String) {
					query.setString(i, (String) paras[i]);
				} else if (paras[i] instanceof Double) {
					query.setDouble(i, ((Double) paras[i]).doubleValue());
				} else if (paras[i] instanceof Float) {
					query.setFloat(i, ((Float) paras[i]).floatValue());
				} else if (paras[i] instanceof Long) {
					query.setLong(i, ((Long) paras[i]).longValue());
				} else if (paras[i] instanceof Boolean) {
					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
				} else if (paras[i] instanceof Date) {
					query.setDate(i, (Date) paras[i]);
				} else {
					query.setParameter(i, paras[i]);
				}
			}
		}
		if (offset >= 0 && length > 0){
			query.setFirstResult(offset);
			query.setMaxResults(length);
		}
		List<Object[]> list = query.list();
		return list;
	}
	
	/**
	 * 通过HQL语句查找唯一结果，一般用于count(*)等操作
	 */
	protected Object getResultByHQL(final String hql) {
		return this.getResultByHQL(hql, null);
	}
	
	/**
	 * 通过HQL语句查找唯一结果，一般用于count(*)等操作（HQL传参）
	 */
	protected Object getResultByHQL(final String hql, final Object[] paras) {
		Query query = getSession().createQuery(hql);
		if (paras != null && paras.length > 0) {
			for (int i = 0; i < paras.length; i++) {
				if (paras[i] instanceof Integer) {
					query.setInteger(i, ((Integer) paras[i]).intValue());
				} else if (paras[i] instanceof String) {
					query.setString(i, (String) paras[i]);
				} else if (paras[i] instanceof Double) {
					query.setDouble(i, ((Double) paras[i]).doubleValue());
				} else if (paras[i] instanceof Float) {
					query.setFloat(i, ((Float) paras[i]).floatValue());
				} else if (paras[i] instanceof Long) {
					query.setLong(i, ((Long) paras[i]).longValue());
				} else if (paras[i] instanceof Boolean) {
					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
				} else if (paras[i] instanceof Date) {
					query.setDate(i, (Date) paras[i]);
				} else {
					query.setParameter(i, paras[i]);
				}
			}
		}
		Object result = query.uniqueResult();
		return result;
	}
	
	/**
	 * 通过SQL语句查找唯一结果，一般用于count(*)等操作
	 */
	protected Object getResultBySQL(final String sql){
		return this.getResultBySQL(sql, null);
	}
	
	/**
	 * 通过SQL语句查找唯一结果，一般用于count(*)等操作（SQL传参）
	 */
	protected Object getResultBySQL(final String sql, final Object[] paras) {
		Query query = getSession().createSQLQuery(sql);
		if (paras != null && paras.length > 0) {
			for (int i = 0; i < paras.length; i++) {
				query.setParameter(i, paras[i]);
			}
		}
		return query.uniqueResult();
	}
	
	/**
	 * 通过HQL语句更新数据库
	 */
	protected int executeHQL(final String hql) {
		return this.executeHQL(hql, null);
	}
	
	/**
	 * 通过HQL语句更新数据库(传参)
	 */
	protected int executeHQL(final String hql, final Object[] paras) {
		Query query = getSession().createQuery(hql);
		if (paras != null && paras.length > 0) {
			for (int i = 0; i < paras.length; i++) {
				if (paras[i] instanceof Integer) {
					query.setInteger(i, ((Integer) paras[i]).intValue());
				} else if (paras[i] instanceof String) {
					query.setString(i, (String) paras[i]);
				} else if (paras[i] instanceof Double) {
					query.setDouble(i, ((Double) paras[i]).doubleValue());
				} else if (paras[i] instanceof Float) {
					query.setFloat(i, ((Float) paras[i]).floatValue());
				} else if (paras[i] instanceof Long) {
					query.setLong(i, ((Long) paras[i]).longValue());
				} else if (paras[i] instanceof Boolean) {
					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
				} else if (paras[i] instanceof Date) {
					query.setDate(i, (Date) paras[i]);
				} else {
					query.setParameter(i, paras[i]);
				}
			}
		}
		return query.executeUpdate();
	}
	
	/**
	 * 通过SQL语句更新数据库
	 */
	protected int executeSQL(final String sql) {
		return this.executeSQL(sql, null);
	}
	
	/**
	 * 通过SQL语句更新数据库(传参)
	 */
	protected int executeSQL(final String sql, final Object[] objParas) {
		Query query = getSession().createSQLQuery(sql);
		if (objParas != null && objParas.length > 0) {
			for (int i = 0; i < objParas.length; i++) {
				query.setParameter(i, objParas[i]);
			}
		}
		return query.executeUpdate();
	}
	
	
}
