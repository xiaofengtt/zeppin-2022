package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.ILanguageDao;
import cn.zeppin.entity.Language;
import cn.zeppin.service.ILanguageService;

public class LanguageServiceImpl extends BaseServiceImpl<Language, Short>
	implements ILanguageService
{

    private ILanguageDao languageDao;

    public ILanguageDao getLanguageDao()
    {
	return languageDao;
    }

    public void setLanguageDao(ILanguageDao languageDao)
    {
	this.languageDao = languageDao;
    }

    @Override
    public Language add(Language t)
    {
	// TODO Auto-generated method stub
	return languageDao.add(t);
    }

    @Override
    public Language update(Language t)
    {
	// TODO Auto-generated method stub
	return languageDao.update(t);
    }

    @Override
    public void delete(Language t)
    {
	// TODO Auto-generated method stub
	languageDao.delete(t);
    }

    @Override
    public Language load(Short id)
    {
	// TODO Auto-generated method stub
	return languageDao.load(id);
    }

    @Override
    public Language get(Short id)
    {
	// TODO Auto-generated method stub
	return languageDao.get(id);
    }

    @Override
    public List<Language> loadAll()
    {
	// TODO Auto-generated method stub
	return languageDao.loadAll();
    }

    @Override
    public List<Language> findAll()
    {
	// TODO Auto-generated method stub
	return languageDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return languageDao.findByHSQL(querySql);
    }

    @Override
    public List<Language> getListForPage(String hql, int offset, int length)
    {
	// TODO Auto-generated method stub
	return languageDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	languageDao.executeHSQL(hql);
    }

    @Override
    public List<Language> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return languageDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return languageDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	languageDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return languageDao.getListPage(sql, offset, length, objParas);
    }

}
