package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IEthnicDao;
import cn.zeppin.dao.impl.EthnicDaoImpl;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.service.IEthnicService;

public class EthnicServiceImpl extends BaseServiceImpl<Ethnic, Short> implements
	IEthnicService
{

    private IEthnicDao ethnicDao;

    public IEthnicDao getEthnicDao()
    {
    	return ethnicDao;
    }

    public void setEthnicDao(IEthnicDao ethnicDao)
    {
    	this.ethnicDao = ethnicDao;
    }

    @Override
    public Ethnic add(Ethnic t)
    {
	// TODO Auto-generated method stub
	return ethnicDao.add(t);
    }

    @Override
    public Ethnic update(Ethnic t)
    {
	// TODO Auto-generated method stub
	return ethnicDao.update(t);
    }

    @Override
    public void delete(Ethnic t)
    {
	// TODO Auto-generated method stub
	ethnicDao.delete(t);
    }

    @Override
    public Ethnic load(Short id)
    {
	// TODO Auto-generated method stub
	return ethnicDao.load(id);
    }

    @Override
    public Ethnic get(Short id)
    {
	// TODO Auto-generated method stub
	return ethnicDao.get(id);
    }

    @Override
    public List<Ethnic> loadAll()
    {
	// TODO Auto-generated method stub
	return ethnicDao.loadAll();
    }

    @Override
    public List<Ethnic> findAll()
    {
	// TODO Auto-generated method stub
	return ethnicDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return ethnicDao.findByHSQL(querySql);
    }

    @Override
    public List<Ethnic> getListForPage(String hql, int offset, int length)
    {
	// TODO Auto-generated method stub
	return ethnicDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	ethnicDao.executeHSQL(hql);
    }

    @Override
    public List<Ethnic> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return ethnicDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return ethnicDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	ethnicDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return ethnicDao.getListPage(sql, offset, length, objParas);
    }

    @Override
    public List<Ethnic> getEthnicByWight()
    {
	// TODO Auto-generated method stub
	String hql = "from Ethnic t  order by t.weight desc";
	List<Ethnic> li = this.getListByHSQL(hql);
	return li;
    }

}
