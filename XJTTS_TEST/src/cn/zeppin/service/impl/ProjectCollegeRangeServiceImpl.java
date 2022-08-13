/**
 * 
 */
package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IProjectCollegeRangeDao;
import cn.zeppin.entity.ProjectCollegeRange;
import cn.zeppin.service.IProjectCollegeRangeService;

/**
 * @author sj
 * 
 */
public class ProjectCollegeRangeServiceImpl extends
	BaseServiceImpl<ProjectCollegeRange, Integer> implements
	IProjectCollegeRangeService
{
    IProjectCollegeRangeDao iprojectCollegeRangeDao;
    
    public void deleteByProject(int id){
    	iprojectCollegeRangeDao.deleteByProject(id);
    }
    
    @Override
    public List<ProjectCollegeRange> getListByTrainingCollege(Integer collegeId)
    {
	// TODO Auto-generated method stub
	return iprojectCollegeRangeDao.getListByTrainingCollege(collegeId);
    }

    /**
     * @return the iprojectCollegeRangeDao
     */
    public IProjectCollegeRangeDao getIprojectCollegeRangeDao()
    {
	return iprojectCollegeRangeDao;
    }

    /**
     * @param iprojectCollegeRangeDao
     *            the iprojectCollegeRangeDao to set
     */
    public void setIprojectCollegeRangeDao(
	    IProjectCollegeRangeDao iprojectCollegeRangeDao)
    {
	this.iprojectCollegeRangeDao = iprojectCollegeRangeDao;
    }

}
