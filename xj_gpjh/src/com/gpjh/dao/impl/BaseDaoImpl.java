package com.gpjh.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.gpjh.dao.BaseDao;
import com.gpjh.model.Question;


/**
 * The base interface implement for all the dao layer interface implements,it
 * provide common method
 *
 *
 * @param <T>
 *            The entity class type
 * @param <PK>
 *            The primary key of the entity class
 */
@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

    private Class<T> entityClass;
    private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * Method to judge the type of class
     */
    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) types[0];
        }
    }

    @Override
    public T add(T t) {
        hibernateTemplate.save(t);
        return t;
    }

    @Override
    public void delete(T t) {
        hibernateTemplate.delete(t);
    }

    @Override
    public T load(PK id) {
        return hibernateTemplate.load(entityClass, id);
    }

    @Override
    public List<T> loadAll() {
        return hibernateTemplate.loadAll(entityClass);
    }

    @Override
    public List<T> findAll(){
    	 return getHibernateTemplate().find("from " + entityClass.getName());  
    }
    
    @Override
    public T update(T t) {
        hibernateTemplate.update(t);
        return t;
    }

    @Override
    public T get(PK id) {
        return hibernateTemplate.get(entityClass, id);
    }
    
    @Override
	public List<T> getListForPage(final String hql, final int offset, final int length) {

		List list = hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
					
				Query query=session.createQuery(hql);
	            query.setFirstResult((offset-1)*length);
	            query.setMaxResults(length);
	            List list=query.list();
	            return list;
			}
		});
		
		return list;
	}
	
    /**
	
    /**
     * ??????HQL??????????????????????????????????????????
     * 
     * @param querySql HQL???????????????
     *            
     * @return ??????object
     */
	public List<Object> findByHSQL(final String querySql) {
		try {
			List<Object> obj = hibernateTemplate.find(querySql);

			if (obj != null) {
				return obj;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
    
    /**
     * If there is a session alive, we will use it instead of open an new
     * Session.
     *
     * @return
     */
    public Session getCurrentSession() {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        if (session == null) {
            session = hibernateTemplate.getSessionFactory().openSession();
        }
        return session;
    }

	@Override
	public void executeHSQL(final String hql) {
		// TODO Auto-generated method stub
		hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query= session.createQuery(hql);
				query.executeUpdate();
	            return null;
			}
		});
	}

	@Override
	public List executeSQL(final String sql) {
		// TODO Auto-generated method stub
		
		List list = hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createSQLQuery(sql);
	            List list=query.list();
	            return list;
			}
		});
		
		return list;
	}
	
	@Override
	public int queryRowCount(final String hql){
		try {
			List obj = hibernateTemplate.find(hql);

			if (obj != null) {
				return obj.size();
			} else {
				return 0;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@Override
	public List<T> getListByHSQL(String hql) {
		List<T> ts = new ArrayList<T>();
		List<Object> list = findByHSQL(hql);
		for(Object obj : list) {
			ts.add((T) obj);
		}
		return ts;
	}

}
