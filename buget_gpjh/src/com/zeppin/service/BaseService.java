package com.zeppin.service;

import java.io.Serializable;
import java.util.List;

//import org.springframework.orm.hibernate3.HibernateTemplate;

public interface BaseService<T, PK extends Serializable> {
    public T add(T t);

    public void delete(T t);

    public T load(PK id);

    public T get(PK id);

    public List<T> loadAll();

    public List<T> findAll();
    
    public T update(T t);
    
    public List<Object> findByHSQL(String querySql);
    
    public List<T> getListByHSQL(String hql);

    public List<T> getListForPage(String hql, int pageId, int pageSize);
    
    @SuppressWarnings("rawtypes")
	public List executeSQL(final String sql);
    public void executeHSQL(final String hql);
    public void executeSQLUpdate(final String sql);
    
    @SuppressWarnings("rawtypes")
	public List getListPage(final String sql, final int offset, final int length);
    
	public int queryRowCount(final String hql);
}
