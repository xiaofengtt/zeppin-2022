package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IPoliticsDao;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Politics;
import cn.zeppin.service.IPoliticsService;

public class PoliticsServiceImpl extends BaseServiceImpl<Politics, Integer>
	implements IPoliticsService
{

    private IPoliticsDao politicsDao;

    public List<Politics> getPoliticsByWight(){
    	return this.politicsDao.getPoliticsByWight();
    }
    
    public IPoliticsDao getPoliticsDao()
    {
	return politicsDao;
    }

    public void setPoliticsDao(IPoliticsDao politicsDao)
    {
	this.politicsDao = politicsDao;
    }

    @Override
    public Politics add(Politics t)
    {
	// TODO Auto-generated method stub
	return politicsDao.add(t);
    }

    @Override
    public Politics update(Politics t)
    {
	// TODO Auto-generated method stub
	return politicsDao.update(t);
    }

    @Override
    public void delete(Politics t)
    {
	// TODO Auto-generated method stub
	politicsDao.delete(t);
    }

    @Override
    public Politics load(Integer id)
    {
	// TODO Auto-generated method stub
	return politicsDao.load(id);
    }

    @Override
    public Politics get(Integer id)
    {
	// TODO Auto-generated method stub
	return politicsDao.get(id);
    }

    @Override
    public List<Politics> loadAll()
    {
	// TODO Auto-generated method stub
	return politicsDao.loadAll();
    }

    @Override
    public List<Politics> findAll()
    {
	// TODO Auto-generated method stub
	return politicsDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return politicsDao.findByHSQL(querySql);
    }

    @Override
    public List<Politics> getListForPage(String hql, int offset, int length)
    {
	// TODO Auto-generated method stub
	return politicsDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	politicsDao.executeHSQL(hql);
    }

    @Override
    public List<Politics> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return politicsDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return politicsDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	politicsDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return politicsDao.getListPage(sql, offset, length, objParas);
    }

    /*
     * (non-Javadoc)
     * @see cn.zeppin.service.IPoliticsService#getPoliticsById()
     */
    @Override
    public Politics getPoliticsById(String id)
    {
	// TODO Auto-generated method stub
	String hqlString = "from Politics where id=" + Short.parseShort(id);

	return politicsDao.getListByHSQL(hqlString).get(0);
    }

}
