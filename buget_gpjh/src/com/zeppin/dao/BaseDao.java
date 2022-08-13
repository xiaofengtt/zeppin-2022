package com.zeppin.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

public interface BaseDao<T, PK extends Serializable> {

    public T add(T t);

    public void delete(T t);
    
    public HibernateTemplate getHibeTemplate();

    public T load(PK id);

    public T get(PK id);

    public List<T> loadAll();

    public List<T> findAll();
    
    public T update(T t);
    
    public List<Object> findByHSQL(final String querySql);
    
    public List<T> getListForPage(final String hql, final int offset, final int length);
    
    public void executeHSQL(final String hql);
    
    @SuppressWarnings("rawtypes")
	public List executeSQL(final String sql);
    
    public void executeSQLUpdate(final String sql);
    
    @SuppressWarnings("rawtypes")
	public List getListPage(final String sql, final int offset, final int length);
    
    public int queryRowCount(final String hql);
    
	public List<T> getListByHSQL(String hql);
	
	public int insertLogin(String sql);

}
