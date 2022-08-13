package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IEductionBackgroundDao;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.service.IEductionBackgroundService;

public class EductionBackgroundServiceImpl extends
	BaseServiceImpl<EductionBackground, Integer> implements
	IEductionBackgroundService
{

    private IEductionBackgroundDao eductionBackgroundDao;

    public List<EductionBackground> getEductionBackgroundByWight(){
    	return this.eductionBackgroundDao.getEductionBackgroundByWight();
    }
    
    public IEductionBackgroundDao getEductionBackgroundDao()
    {
	return eductionBackgroundDao;
    }

    public void setEductionBackgroundDao(
    		IEductionBackgroundDao eductionBackgroundDao)
    {
	this.eductionBackgroundDao = eductionBackgroundDao;
    }

    @Override
    public EductionBackground add(EductionBackground t)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.add(t);
    }

    @Override
    public EductionBackground update(EductionBackground t)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.update(t);
    }

    @Override
    public void delete(EductionBackground t)
    {
	// TODO Auto-generated method stub
	eductionBackgroundDao.delete(t);
    }

    @Override
    public EductionBackground load(Integer id)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.load(id.shortValue());
    }

    @Override
    public EductionBackground get(Integer id)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.get(id.shortValue());
    }

    @Override
    public List<EductionBackground> loadAll()
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.loadAll();
    }

    @Override
    public List<EductionBackground> findAll()
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.findByHSQL(querySql);
    }

    @Override
    public List<EductionBackground> getListForPage(String hql, int offset,
	    int length)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	eductionBackgroundDao.executeHSQL(hql);
    }

    @Override
    public List<EductionBackground> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	eductionBackgroundDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return eductionBackgroundDao.getListPage(sql, offset, length, objParas);
    }

    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IEductionBackgroundService#geteEductionBackgroundById
     * (java.lang.String)
     */
    @Override
    public EductionBackground geteEductionBackgroundById(String id)
    {
	// TODO Auto-generated method stub
	String hqlString = "from EductionBackground where id="
		+ Integer.valueOf(id);
	return eductionBackgroundDao.getListByHSQL(hqlString).get(0);
    }

}
