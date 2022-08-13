package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IChineseLanguageLevelDao;
import cn.zeppin.entity.ChineseLanguageLevel;
import cn.zeppin.service.IChineseLanguageLevelService;

public class ChineseLanguageLevelServiceImpl extends
	BaseServiceImpl<ChineseLanguageLevel, Integer> implements
	IChineseLanguageLevelService
{

    private IChineseLanguageLevelDao chineseLanguageLevelDao;

    public IChineseLanguageLevelDao getChineseLanguageLevelDao()
    {
	return chineseLanguageLevelDao;
    }

    public void setChineseLanguageLevelDao(
    		IChineseLanguageLevelDao chineseLanguageLevelDao)
    {
	this.chineseLanguageLevelDao = chineseLanguageLevelDao;
    }

    @Override
    public ChineseLanguageLevel add(ChineseLanguageLevel t)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.add(t);
    }

    @Override
    public ChineseLanguageLevel update(ChineseLanguageLevel t)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.update(t);
    }

    @Override
    public void delete(ChineseLanguageLevel t)
    {
	// TODO Auto-generated method stub
	chineseLanguageLevelDao.delete(t);
    }

    @Override
    public ChineseLanguageLevel load(Integer id)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.load(id);
    }

    @Override
    public ChineseLanguageLevel get(Integer id)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.get(id);
    }

    @Override
    public List<ChineseLanguageLevel> loadAll()
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.loadAll();
    }

    @Override
    public List<ChineseLanguageLevel> findAll()
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.findByHSQL(querySql);
    }

    @Override
    public List<ChineseLanguageLevel> getListForPage(String hql, int offset,
	    int length)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	chineseLanguageLevelDao.executeHSQL(hql);
    }

    @Override
    public List<ChineseLanguageLevel> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	chineseLanguageLevelDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return chineseLanguageLevelDao.getListPage(sql, offset, length,
		objParas);
    }

    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IChineseLanguageLevelService#getcChineseLanguageLevelById
     * (java.lang.String)
     */
    @Override
    public ChineseLanguageLevel getChineseLanguageLevelById(String id)
    {
	// TODO Auto-generated method stub
	String hqlString = " from ChineseLanguageLevel where id="
		+ Short.parseShort(id);

	return chineseLanguageLevelDao.getListByHSQL(hqlString).get(0);
    }
}
