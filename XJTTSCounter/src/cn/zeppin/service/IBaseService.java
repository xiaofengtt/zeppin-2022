package cn.zeppin.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T, PK extends Serializable>
{

    public T add(T t);

    public T update(T t);

    public void delete(T t);

    public T load(PK id);

    public T get(PK id);

    public List<T> loadAll();

    public List<T> findAll();

    public List<Object> findByHSQL(final String querySql);

    public List<T> getListForPage(final String hql, final int offset,
	    final int length);

    public void executeHSQL(final String hql);

    public List<T> getListByHSQL(String hql);

    public List executeSQL(final String sql, final Object[] objParas);

    public void executeSQLUpdate(final String sql, final Object[] objParas);

    public List getListPage(final String sql, final int offset,
	    final int length, final Object[] objParas);

    /**
     * @param hql
     * @param start
     * @param length
     * @param object
     * @return
     */
    public List<T> getListForPage(String hql, int start, int length,
	    Object[] object);

}
