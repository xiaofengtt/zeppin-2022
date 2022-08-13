package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IProjectSubjectRangeDao;
import cn.zeppin.dao.IProjectCollegeRangeDao;
import cn.zeppin.entity.ProjectCollegeRange;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.service.IProjectCollegeRangeService;
import cn.zeppin.service.IProjectSubjectRangeService;

	public class ProjectSubjectRangeServiceImpl extends
	BaseServiceImpl<ProjectSubjectRange, Integer> implements
	IProjectSubjectRangeService
{
    IProjectSubjectRangeDao projectSubjectRangeDao;

    public void deleteByProject(int id){
    	this.projectSubjectRangeDao.deleteByProject(id);
    }

    public List<ProjectSubjectRange> getListByProject(int projectid){
    	return projectSubjectRangeDao.getListByProject(projectid);
    }
    
    public List<ProjectSubjectRange> getListByTrainingSubject(Integer subjectId)
    {
	return projectSubjectRangeDao.getListByTrainingSubject(subjectId);
    }


    public IProjectSubjectRangeDao getProjectSubjectRangeDao()
    {
	return projectSubjectRangeDao;
    }

    public void setProjectSubjectRangeDao(
	    IProjectSubjectRangeDao projectSubjectRangeDao)
    {
	this.projectSubjectRangeDao = projectSubjectRangeDao;
    }

}