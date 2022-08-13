package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IJobTitleDao;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.service.IJobTitleService;

public class JobTitleServiceImpl extends BaseServiceImpl<JobTitle, Integer>
	implements IJobTitleService
{

    private IJobTitleDao jobTitleDao;

    public IJobTitleDao getJobTitleDao()
    {
	return jobTitleDao;
    }

    public void setJobTitleDao(IJobTitleDao jobTitleDao)
    {
	this.jobTitleDao = jobTitleDao;
    }

	public List<JobTitle> findByName(String name){
		return jobTitleDao.findByName(name);
	}
    
    @Override
    public JobTitle add(JobTitle t)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.add(t);
    }

    @Override
    public JobTitle update(JobTitle t)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.update(t);
    }

    @Override
    public void delete(JobTitle t)
    {
	// TODO Auto-generated method stub
	jobTitleDao.delete(t);
    }

    @Override
    public JobTitle load(Integer id)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.load(id);
    }

    @Override
    public JobTitle get(Integer id)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.get(id);
    }

    @Override
    public List<JobTitle> loadAll()
    {
	// TODO Auto-generated method stub
	return jobTitleDao.loadAll();
    }

    @Override
    public List<JobTitle> findAll()
    {
	// TODO Auto-generated method stub
	return jobTitleDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.findByHSQL(querySql);
    }

    @Override
    public List<JobTitle> getListForPage(String hql, int offset, int length)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	jobTitleDao.executeHSQL(hql);
    }

    @Override
    public List<JobTitle> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	jobTitleDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return jobTitleDao.getListPage(sql, offset, length, objParas);
    }

    /*
     * (non-Javadoc)
     * @see cn.zeppin.service.IJobTitleService#getJobTitleById(java.lang.String)
     */
    @Override
    public JobTitle getJobTitleById(String id)
    {
	// TODO Auto-generated method stub
	String hqlString = "from JobTitle where id=" + Short.parseShort(id);
	return jobTitleDao.getListByHSQL(hqlString).get(0);
    }

}
