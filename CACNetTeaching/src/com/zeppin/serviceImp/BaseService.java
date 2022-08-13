/**
 * 
 */
package com.zeppin.serviceImp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zeppin.dao.IBaseDao;
import com.zeppin.service.IBaseService;

/**
 * @author Administrator
 * 
 */
public class BaseService<T, PK extends Serializable> implements
	IBaseService<T, PK>
{
    private IBaseDao<T, PK> baseDao;

    /**
     * @return baseDao
     */
    public IBaseDao<T, PK> getBaseDao()
    {
	return baseDao;
    }

    /**
     * @param baseDao
     *            要设置的 baseDao
     */
    public void setBaseDao(IBaseDao<T, PK> baseDao)
    {
	this.baseDao = baseDao;
    }

    @Override
    public T add(T t)
    {
	return baseDao.add(t);
    }

    @Override
    public void delete(T t)
    {
	baseDao.delete(t);
    }

    @Override
    public T load(PK id)
    {
	return baseDao.load(id);
    }

    @Override
    public List<T> loadAll()
    {
	return baseDao.loadAll();
    }

    @Override
    public List<T> findAll()
    {
	return baseDao.findAll();
    }

    @Override
    public void update(T t)
    {
	baseDao.update(t);
    }

    @Override
    public T get(PK id)
    {
	return baseDao.get(id);
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	return baseDao.findByHSQL(querySql);
    }

    @Override
    public List<T> getListForPage(String hql, int pageId, int pageSize)
    {
	return baseDao.getListForPage(hql, pageId, pageSize);
    }

    @Override
    public List<T> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	List<T> ts = new ArrayList<T>();
	List<Object> list = baseDao.findByHSQL(hql);
	for (Object obj : list)
	{
	    ts.add((T) obj);
	}
	return ts;
    }

    public List executeSQL(final String sql, final Object[] objects)
    {
	return baseDao.executeSQL(sql, objects);
    }

    public void executeHSQL(final String hql)
    {
	baseDao.executeHSQL(hql);
    }

    public void executeSQLUpdate(final String sql, final Object[] objects)
    {

	baseDao.executeSQLUpdate(sql, objects);

    }

    public List getListPage(final String sql, final int offset,
	    final int length, final Object[] objects)
    {
	return baseDao.getListPage(sql, offset, length, objects);
    }

    @Override
    public int queryRowCount(final String hql)
    {
	return baseDao.queryRowCount(hql);
    }

}
