package com.zeppin.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 * The base interface for all the dao layer interface,it provide common method
 * 
 * @param <T>
 *            The entity class type
 * @param <PK>
 *            The primary key of the entity class
 * 
 */
public interface IBaseDao<T, PK extends Serializable>
{

    public T add(T t);

    public void addList(List<T> lst) throws SecurityException,
	    IllegalStateException, RollbackException, HeuristicMixedException,
	    HeuristicRollbackException, SystemException;

    public void delete(T t);

    public T load(PK id);

    public T get(PK id);

    public List<T> loadAll();

    public List<T> findAll();

    public void update(T t);

    public List<Object> findByHSQL(final String querySql);

    public List<T> getListForPage(final String hql, final int offset,
	    final int length);

    public void executeHSQL(final String hql);

    public List executeSQL(final String sql, final Object[] objParas);

    public void executeSQLUpdate(final String sql, final Object[] objParas);

    public List getListPage(final String sql, final int offset,
	    final int length, final Object[] objParas);

    public int queryRowCount(final String hql);

    public List<T> getListByHSQL(String hql);

}
