package cn.zeppin.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

public interface IBaseDao<T, PK extends Serializable>
{

    public T add(T t);

    public T update(T t);

    public void delete(T t);

    public HibernateTemplate getHibeTemplate();

    public T load(PK id);

    public T get(PK id);

    public List<T> loadAll();

    public List<T> findAll();

    public List<Object> findByHSQL(final String querySql);

    public List<T> getListForPage(final String hql, final int offset, final int length);
    public List<T> getListForPageByParams(final String hql, final int offset, final int length, Object[] paras);
    public List<T> getListForPage(final String hql, final int offset, final int length, Object[] paras);

    public void executeHSQL(final String hql);

    public List<T> getListByHSQL(String hql);

    public List getListBySQL(final String sql, final Object[] objParas);

    public void executeSQLUpdate(final String sql, final Object[] objParas);

    public List getListPage(final String sql, final int offset, final int length, final Object[] objParas);
    
    public Object getObjectByHql(final String hql, final Object[] paras);
    
    public Object getObjectBySql(final String sql, final Object[] paras);

}
