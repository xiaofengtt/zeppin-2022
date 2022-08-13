package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.dao.IJobDutyDao;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.service.IJobDutyService;

public class JobDutyServiceImpl extends BaseServiceImpl<JobDuty, Integer>
	implements IJobDutyService
{

    private IJobDutyDao jobDutyDao;

    public IJobDutyDao getJobDutyDao()
    {
	return jobDutyDao;
    }

    public void setJobDutyDao(IJobDutyDao jobDutyDao)
    {
	this.jobDutyDao = jobDutyDao;
    }

    public List<JobDuty> findByName(String name){
		return jobDutyDao.findByName(name);
	}
    
    @Override
    public JobDuty add(JobDuty t)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.add(t);
    }

    @Override
    public JobDuty update(JobDuty t)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.update(t);
    }

    @Override
    public void delete(JobDuty t)
    {
	// TODO Auto-generated method stub
	jobDutyDao.delete(t);
    }

    @Override
    public JobDuty load(Integer id)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.load(id);
    }

    @Override
    public JobDuty get(Integer id)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.get(id);
    }

    @Override
    public List<JobDuty> loadAll()
    {
	// TODO Auto-generated method stub
	return jobDutyDao.loadAll();
    }

    @Override
    public List<JobDuty> findAll()
    {
	// TODO Auto-generated method stub
	return jobDutyDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.findByHSQL(querySql);
    }

    @Override
    public List<JobDuty> getListForPage(String hql, int offset, int length)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	jobDutyDao.executeHSQL(hql);
    }

    @Override
    public List<JobDuty> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	jobDutyDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return jobDutyDao.getListPage(sql, offset, length, objParas);
    }

    public List<JobDuty> getDuties()
    {
	List<JobDuty> lstDuties = new ArrayList<>();
	String hqlString = "from JobDuty ";
	return lstDuties;
    }

    /*
     * (non-Javadoc)
     * @see cn.zeppin.service.IJobDutyService#getJobDutyById(java.lang.String)
     */
    @Override
    public JobDuty getJobDutyById(String id)
    {
	// TODO Auto-generated method stub
	String hqlsString = "from JobDuty where id=" + Short.parseShort(id);
	return jobDutyDao.getListByHSQL(hqlsString).get(0);
    }

}
