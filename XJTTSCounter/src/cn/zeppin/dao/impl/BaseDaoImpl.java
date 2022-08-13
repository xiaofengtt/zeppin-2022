package cn.zeppin.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IBaseDao;

public class BaseDaoImpl<T, PK extends Serializable> implements IBaseDao<T, PK>
{

	private Class<T> entityClass;

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession()
	{

		Session session = null;
		session = sessionFactory.getCurrentSession();
		return session;

	}

	/**
	 * Method to judge the type of class
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() throws NullPointerException
	{
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType)
		{
			Type[] types = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) types[0];
		}
	}

	@Override
	public T add(T t)
	{
		// TODO Auto-generated method stub
		getCurrentSession().save(t);
		return t;
	}

	@Override
	public T update(T t)
	{
		// TODO Auto-generated method stub

		Session session = getCurrentSession();
		session.beginTransaction();
		session.merge(t);
		session.flush();
		session.getTransaction().commit();

		return t;
	}

	@Override
	public void delete(T t)
	{
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.beginTransaction();
		session.delete(t);
		session.flush();
		session.getTransaction().commit();
		// getCurrentSession().delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(PK id)
	{
		// TODO Auto-generated method stub
		return (T) getCurrentSession().load(entityClass, id);
	}

	@Override
	public T get(PK id)
	{
		// TODO Auto-generated method stub
		return (T) getCurrentSession().get(entityClass, id);
	}

	@Override
	public List<T> loadAll()
	{
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from " + entityClass.getName())
				.list();
	}

	@Override
	public List<T> findAll()
	{
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from " + entityClass.getName())
				.list();
	}

	@Override
	public List<Object> findByHSQL(String querySql)
	{
		try
		{
			List<Object> obj = getCurrentSession().createQuery(querySql).list();

			if (obj != null)
			{
				return obj;
			}
			else
			{
				return null;
			}
		}
		catch (RuntimeException re)
		{

			re.printStackTrace();

			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getListForPage(final String hql, final int offset,
			final int length)
			{

		Query query = getCurrentSession().createQuery(hql);
		int of = offset;
		if (of <= 0)
		{
			of = 0;
		}

		query.setFirstResult(of);
		query.setMaxResults(length);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
			}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getListForPageByParams(final String hql, final int offset,
			final int length, Object[] paras)
			{

		Query query = getCurrentSession().createQuery(hql);
		int of = offset;
		if (of <= 0)
		{
			of = 0;
		}

		if (paras != null && paras.length > 0)
		{
			for (int i = 0; i < paras.length; i++)
			{
				if (paras[i] instanceof Integer)
				{
					query.setInteger(i, ((Integer) paras[i]).intValue());
				}
				else if (paras[i] instanceof String)
				{
					query.setString(i, (String) paras[i]);
				}
				else if (paras[i] instanceof Double)
				{
					query.setDouble(i, ((Double) paras[i]).doubleValue());
				}
				else if (paras[i] instanceof Float)
				{
					query.setFloat(i, ((Float) paras[i]).floatValue());
				}
				else if (paras[i] instanceof Long)
				{
					query.setLong(i, ((Long) paras[i]).longValue());
				}
				else if (paras[i] instanceof Boolean)
				{
					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
				}
				else if (paras[i] instanceof Date)
				{
					query.setDate(i, (Date) paras[i]);
				}
				else
				{
					query.setParameter(i, paras[i]);
				}
			}
		}

		query.setFirstResult(of);
		query.setMaxResults(length);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
			}

	@SuppressWarnings("unchecked")
	public List<T> getListForPage(final String hql, final int offset,
			final int length, Object[] paras)
			{

		Query query = getCurrentSession().createQuery(hql);
		int of = offset;
		if (of <= 0)
		{
			of = 0;
		}
		if (paras != null && paras.length > 0)
		{
			for (int i = 0; i < paras.length; i++)
			{
				if (paras[i] instanceof Integer)
				{
					query.setInteger(i, ((Integer) paras[i]).intValue());
				}
				else if (paras[i] instanceof String)
				{
					query.setString(i, (String) paras[i]);
				}
				else if (paras[i] instanceof Double)
				{
					query.setDouble(i, ((Double) paras[i]).doubleValue());
				}
				else if (paras[i] instanceof Float)
				{
					query.setFloat(i, ((Float) paras[i]).floatValue());
				}
				else if (paras[i] instanceof Long)
				{
					query.setLong(i, ((Long) paras[i]).longValue());
				}
				else if (paras[i] instanceof Boolean)
				{
					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
				}
				else if (paras[i] instanceof Date)
				{
					query.setDate(i, (Date) paras[i]);
				}
				else
				{
					query.setParameter(i, paras[i]);
				}
			}
		}

		int start = of * length;
		int end = length;
		query.setFirstResult(start);
		query.setMaxResults(end);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		return list;
			}

	@Override
	public void executeHSQL(final String hql)
	{
		// TODO Auto-generated method stub

		Query query = getCurrentSession().createQuery(hql);
		query.executeUpdate();

	}

	@Override
	public List<T> getListByHSQL(String hql)
	{
		// TODO Auto-generated method stub
		List<T> ts = new ArrayList<T>();
		List<Object> list = findByHSQL(hql);
		for (Object obj : list)
		{
			ts.add((T) obj);
		}
		return ts;
	}

	@Override
	public List getListBySQL(final String sql, final Object[] objParas)
	{
		// TODO Auto-generated method stub

		Query query = getCurrentSession().createSQLQuery(sql);

		if (objParas != null && objParas.length > 0)
		{
			for (int i = 0; i < objParas.length; i++)
			{
				query.setParameter(i, objParas[i]);
			}
		}
		List list = query.list();
		return list;

	}

	@Override
	public void executeSQLUpdate(final String sql, final Object[] objParas)
	{
		// TODO Auto-generated method stub

		Query query = getCurrentSession().createSQLQuery(sql);

		if (objParas != null && objParas.length > 0)
		{
			for (int i = 0; i < objParas.length; i++)
			{
				query.setParameter(i, objParas[i]);
			}
		}
		query.executeUpdate();

	}

	@Override
	public List getListPage(final String sql, final int offset,
			final int length, final Object[] objParas)
	{
		// TODO Auto-generated method stub

		Query query = getCurrentSession().createSQLQuery(sql);
		int of = offset;

		if (of <= 0)
		{
			of = 0;
		}
		query.setFirstResult(of);
		query.setMaxResults(length);

		if (objParas != null && objParas.length > 0)
		{
			for (int i = 0; i < objParas.length; i++)
			{
				query.setParameter(i, objParas[i]);
			}
		}

		List list = query.list();
		return list;

	}

	@Override
	public HibernateTemplate getHibeTemplate()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectByHql(final String hql, final Object[] paras)
	{
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(hql);
		if (paras != null && paras.length > 0)
		{
			for (int i = 0; i < paras.length; i++)
			{
				if (paras[i] instanceof Integer)
				{
					query.setInteger(i, ((Integer) paras[i]).intValue());
				}
				else if (paras[i] instanceof String)
				{
					query.setString(i, (String) paras[i]);
				}
				else if (paras[i] instanceof Double)
				{
					query.setDouble(i, ((Double) paras[i]).doubleValue());
				}
				else if (paras[i] instanceof Float)
				{
					query.setFloat(i, ((Float) paras[i]).floatValue());
				}
				else if (paras[i] instanceof Long)
				{
					query.setLong(i, ((Long) paras[i]).longValue());
				}
				else if (paras[i] instanceof Boolean)
				{
					query.setBoolean(i, ((Boolean) paras[i]).booleanValue());
				}
				else if (paras[i] instanceof Date)
				{
					query.setDate(i, (Date) paras[i]);
				}
				else
				{
					query.setParameter(i, paras[i]);
				}
			}
		}
		Object result = query.uniqueResult();
		return result;
	}

	@Override
	public Object getObjectBySql(final String sql, final Object[] paras)
	{
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createSQLQuery(sql);
		if (paras != null && paras.length > 0)
		{
			for (int i = 0; i < paras.length; i++)
			{
				query.setParameter(i, paras[i]);
			}
		}
		return query.uniqueResult();
	}

}
