package com.gpjh.service;

import java.io.Serializable;
import java.util.List;

/**
 * * The base interface for all the service layer interface,it provide common method
 * 
 */
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
    
    public List executeSQL(final String sql);
    
	public int queryRowCount(final String hql);
}
