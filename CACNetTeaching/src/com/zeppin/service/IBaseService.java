/**
 * 
 */
package com.zeppin.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public interface IBaseService<T, PK extends Serializable>
{
    public T add(T t);

    public void delete(T t);

    public T load(PK id);

    public T get(PK id);

    public List<T> loadAll();

    public List<T> findAll();

    public void update(T t);

    public List<Object> findByHSQL(String querySql);

    public List<T> getListByHSQL(String hql);

    public List<T> getListForPage(String hql, int pageId, int pageSize);

    public List executeSQL(final String sql, final Object[] objects);

    public void executeHSQL(final String hql);

    public void executeSQLUpdate(final String sql, final Object[] objects);

    public List getListPage(final String sql, final int offset,
	    final int length, final Object[] objects);

    public int queryRowCount(final String hql);

}
