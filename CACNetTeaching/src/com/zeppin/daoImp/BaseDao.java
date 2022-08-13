/**
 * 
 */
package com.zeppin.daoImp;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.zeppin.dao.IBaseDao;

/**
 * The base interface implement for all the dao layer interface implements,it
 * provide common method
 * 
 * 
 * @param <T>
 *            The entity class type
 * @param <PK>
 *            The primary key of the entity class
 * 
 **/

@Repository
public class BaseDao<T, PK extends Serializable> implements IBaseDao<T, PK>
{
    private Class<T> entityClass;
    private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate()
    {
	return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
    {
	this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * Method to judge the type of class
     */
    @SuppressWarnings("unchecked")
    public BaseDao()
    {
	Type type = this.getClass().getGenericSuperclass();
	if (type instanceof ParameterizedType)
	{
	    Type[] types = ((ParameterizedType) type).getActualTypeArguments();
	    this.entityClass = (Class<T>) types[0];
	}
    }

    @Override
    public T add(T t)
    {
	hibernateTemplate.save(t);
	return t;
    }

    @Override
    public void delete(T t)
    {
	hibernateTemplate.delete(t);
    }

    @Override
    public T load(PK id)
    {
	return hibernateTemplate.load(entityClass, id);
    }

    @Override
    public List<T> loadAll()
    {
	return hibernateTemplate.loadAll(entityClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll()
    {
	return getHibernateTemplate().find("from " + entityClass.getName());
    }

    @Override
    public void update(T t)
    {
	Session session = getCurrentSession();
	session.beginTransaction();
	session.update(t);
	session.getTransaction().commit();

	/*
	 * try { this.getHibernateTemplate().update(t); } catch (Exception e) {
	 * String string = e.getMessage(); }
	 */

	// return t;
    }

    @Override
    public T get(PK id)
    {
	return hibernateTemplate.get(entityClass, id);
    }

    @Override
    public List<T> getListForPage(final String hql, final int offset,
	    final int length)
    {

	List list = hibernateTemplate.executeFind(new HibernateCallback()
	{
	    public Object doInHibernate(Session session)
		    throws HibernateException, SQLException
	    {

		Query query = session.createQuery(hql);

		int of = offset - 1;
		if (of < 0)
		{
		    of = 0;
		}
		query.setFirstResult(of * length);
		query.setMaxResults((of + 1) * length);
		List list = query.list();
		return list;
	    }
	});

	return list;
    }

    /**
     * /** 根据HQL查询字符串来返回实例集合对象
     * 
     * @param querySql
     *            HQL查询字符串
     * 
     * @return 返回object
     */
    public List<Object> findByHSQL(final String querySql)
    {
	try
	{
	    List<Object> obj = hibernateTemplate.find(querySql);

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
	    throw re;
	}
    }

    /**
     * If there is a session alive, we will use it instead of open an new
     * Session.
     * 
     * @return
     */
    public Session getCurrentSession()
    {
	Session session = hibernateTemplate.getSessionFactory()
		.getCurrentSession();
	if (session == null)
	{
	    session = hibernateTemplate.getSessionFactory().openSession();
	}
	return session;
    }

    @Override
    public void executeHSQL(final String hql)
    {
	// TODO Auto-generated method stub
	hibernateTemplate.executeFind(new HibernateCallback()
	{
	    public Object doInHibernate(Session session)
		    throws HibernateException, SQLException
	    {
		Query query = session.createQuery(hql);
		query.executeUpdate();
		return null;
	    }
	});
    }

    public void executeSQLUpdate(final String sql, final Object[] objParas)
    {

	hibernateTemplate.executeFind(new HibernateCallback()
	{
	    public Object doInHibernate(Session session)
		    throws HibernateException, SQLException
	    {
		Query query = session.createSQLQuery(sql);
		if (objParas != null && objParas.length > 0)
		{
		    for (int i = 0; i < objParas.length; i++)
		    {
			query.setParameter(i, objParas[i]);

		    }
		}
		query.executeUpdate();
		return null;
	    }
	});
    }

    public List getListPage(final String sql, final int offset,
	    final int length, final Object[] objParas)
    {

	List list = hibernateTemplate.executeFind(new HibernateCallback()
	{
	    public Object doInHibernate(Session session)
		    throws HibernateException, SQLException
	    {
		Query query = session.createSQLQuery(sql);
		if (objParas != null && objParas.length > 0)
		{
		    for (int i = 0; i < objParas.length; i++)
		    {
			query.setParameter(i, objParas[i]);

		    }
		}
		int of = offset - 1;
		if (of < 0)
		{
		    of = 0;
		}
		query.setFirstResult(of * length);
		query.setMaxResults((of + 1) * length);
		List list = query.list();
		return list;
	    }
	});

	return list;

    }

    @Override
    public List executeSQL(final String sql, final Object[] objParas)
    {
	// TODO Auto-generated method stub

	List list = hibernateTemplate.executeFind(new HibernateCallback()
	{
	    public Object doInHibernate(Session session)
		    throws HibernateException, SQLException
	    {
		Query query = session.createSQLQuery(sql);
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
	});

	return list;
    }

    @Override
    public int queryRowCount(final String hql)
    {
	try
	{
	    List obj = hibernateTemplate.find(hql);

	    if (obj != null)
	    {
		return obj.size();
	    }
	    else
	    {
		return 0;
	    }
	}
	catch (RuntimeException re)
	{
	    throw re;
	}
    }

    @SuppressWarnings({ "unchecked", "unchecked" })
    @Override
    public List<T> getListByHSQL(String hql)
    {
	List<T> ts = new ArrayList<T>();
	List<Object> list = findByHSQL(hql);
	for (Object obj : list)
	{
	    ts.add((T) obj);
	}
	return ts;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.dao.IBaseDao#addList(java.util.List)
     */
    @Override
    public void addList(List<T> lst) throws SecurityException,
	    IllegalStateException, RollbackException, HeuristicMixedException,
	    HeuristicRollbackException, SystemException
    {
	Session session = getCurrentSession();
	org.hibernate.Transaction tx = session.beginTransaction();
	int i = 0;
	for (T t : lst)
	{
	    i++;
	    session.save(t);
	    if (i % 30 == 0)
	    { // 20, same as the JDBC batch size //30,与JDBC批量设置相同
	      // flush a batch of inserts and release memory:
	      // 将本批插入的对象立即写入数据库并释放内存
		session.flush();
		session.clear();
	    }
	}
	tx.commit();
	session.close();

    }

}
