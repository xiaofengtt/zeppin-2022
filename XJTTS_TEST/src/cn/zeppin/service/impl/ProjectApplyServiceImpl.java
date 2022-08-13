package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectApplyDao;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.service.IProjectApplyService;

public class ProjectApplyServiceImpl extends
	BaseServiceImpl<ProjectApply, Integer> implements IProjectApplyService
{

    private IProjectApplyDao projectApplyDao;

    public IProjectApplyDao getProjectApplyDao()
    {
	return projectApplyDao;
    }

    public List getSubmitForExpert(int pid,int sid,int tc,int psq,int valuator){
    	return projectApplyDao.getSubmitForExpert(pid,sid,tc,psq,valuator);
    }
    
    public List getListForExpert(int expertid,int offset,int length){
    	return projectApplyDao.getListForExpert(expertid,offset,length);
    }
    
    public List getProjectApplyExpertInfo(int id){
    	return projectApplyDao.getProjectApplyExpertInfo(id);
    }
    
    public int getScoreBySubmit(int submit){
    	return projectApplyDao.getScoreBySubmit(submit);
    }
    public int getCountForExpert(){
    	return projectApplyDao.getCountForExpert();
    }
    
    public void setProjectApplyDao(IProjectApplyDao projectApplyDao)
    {
	this.projectApplyDao = projectApplyDao;
    }

    public int getTeacherPsqMaxScore(int id) {
		return projectApplyDao.getTeacherPsqMaxScore(id);
	}
    
    public int getExpertPsqMaxScore(int id) {
		return projectApplyDao.getExpertPsqMaxScore(id);
	}
    @Override
    public ProjectApply add(ProjectApply t)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.add(t);
    }

    @Override
    public ProjectApply update(ProjectApply t)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.update(t);
    }

    @Override
    public void delete(ProjectApply t)
    {
	// TODO Auto-generated method stub
	projectApplyDao.delete(t);
    }

    @Override
    public ProjectApply load(Integer id)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.load(id);
    }

    @Override
    public ProjectApply get(Integer id)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.get(id);
    }

    @Override
    public List<ProjectApply> loadAll()
    {
	// TODO Auto-generated method stub
	return projectApplyDao.loadAll();
    }

    @Override
    public List<ProjectApply> findAll()
    {
	// TODO Auto-generated method stub
	return projectApplyDao.findAll();
    }

    @Override
    public List<Object> findByHSQL(String querySql)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.findByHSQL(querySql);
    }

    @Override
    public List<ProjectApply> getListForPage(String hql, int offset, int length)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getListForPage(hql, offset, length);
    }

    @Override
    public void executeHSQL(String hql)
    {
	// TODO Auto-generated method stub
	projectApplyDao.executeHSQL(hql);
    }

    @Override
    public List<ProjectApply> getListByHSQL(String hql)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getListByHSQL(hql);
    }

    @Override
    public List executeSQL(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getListBySQL(sql, objParas);
    }

    @Override
    public void executeSQLUpdate(String sql, Object[] objParas)
    {
	// TODO Auto-generated method stub
	projectApplyDao.executeSQLUpdate(sql, objParas);
    }

    @Override
    public List getListPage(String sql, int offset, int length,
	    Object[] objParas)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getListPage(sql, offset, length, objParas);
    }

    /*
     * (non-Javadoc)
     * @see cn.zeppin.service.IProjectApplyService#getList(java.lang.Integer,
     * short)
     */
    @Override
    public List<ProjectApply> getList(Integer projectId, short subjectid)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getList(projectId, subjectid);
    }

    public List<ProjectApply> getList(Integer projectId)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getList(projectId);
    }


    public int getExpertViewCount(int psqId,int projectId,int subjectId,int trainingId){
    	return projectApplyDao.getExpertViewCount(psqId,projectId,subjectId,trainingId);
    }
    

    public int getExpertViewTotalScore(int psqId,int projectId,int subjectId,int trainingId){
    	return projectApplyDao.getExpertViewTotalScore(psqId,projectId,subjectId,trainingId);
    }
    
    
    @Override
    public int getProjectApplyCountByParams(Map<String, Object> params,
	    List<ProjectType> lityps)
    {
	return this.getProjectApplyDao().getProjectApplyCountByParams(params,
		lityps);
    }

    @Override
    public List getProjectApplyByParams(Map<String, Object> params,
	    Map<String, String> sortParams, List<ProjectType> lityps,
	    int offset, int length)
    {
	return this.getProjectApplyDao().getProjectApplyByParams(params,
		sortParams, lityps, offset, length);
    }

    @Override
    public List getProjectApplyByParams(Map<String, Object> params,
	    Map<String, String> sortParams, List<ProjectType> lityps)
    {
	return this.getProjectApplyByParams(params, sortParams, lityps);
    }

    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IProjectApplyService#getlistByTrainingCollege(java.
     * lang.Integer, int, int, java.lang.String)
     */
    @Override
    public List<ProjectApply> getlistByTrainingCollege(Integer id, int start,
	    int limit, String sort)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getlistByTrainingCollege(id, start, limit, sort);
    }

    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IProjectApplyService#getlistForPojectApply(java.lang
     * .Integer, int, int, java.lang.String)
     */
    @Override
    public List<ProjectApply> getlistForPojectApply(Integer id, int start,
	    int limit, String sort)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getlistForPojectApply(id, start, limit, sort);
    }
   
    @Override
    public List<ProjectApply> getlistForRunningPojectApply(Integer id, int start,
	    int limit, String sort)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getlistForRunningPojectApply(id, start, limit, sort);
    }
    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IProjectApplyService#getByTrainingRecord(cn.zeppin.
     * entity.TeacherTrainingRecords)
     */
    @Override
    public ProjectApply getByTrainingRecord(
	    TeacherTrainingRecords teacherTrainingRecords)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getByParas(teacherTrainingRecords.getProject()
		.getId(), teacherTrainingRecords.getTrainingCollege().getId(),
		teacherTrainingRecords.getTrainingSubject().getId());
    }

    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IProjectApplyService#getCountByTrainingCollege(java
     * .lang.Integer)
     */
    @Override
    public int getCountByTrainingCollege(Integer collegeId)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getCountByTrainingCollege(collegeId);
    }

    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IProjectApplyService#getDistinctProjectByTrainingCollege
     * (java.lang.Integer)
     */
    @Override
    public List<Integer> getDistinctProjectByTrainingCollege(Integer collegeId)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getDistinctProjectByTrainingCollege(collegeId);
    }

    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IProjectApplyService#getlistForPojectApply(java.lang
     * .Integer, int, int, int, int, java.lang.String)
     */
    @Override
    public List<ProjectApply> getlistForPojectApply(Integer id, int projectId,
	    int start, int i, int limit, String sort)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getlistForPojectApply(id, projectId, start, i,
		limit, sort);
    }

    public List<ProjectApply> getlistForRunningPojectApply(Integer id, int projectId,
    	    int start, int i, int limit, String sort)
        {
    	// TODO Auto-generated method stub
    	return projectApplyDao.getlistForRunningPojectApply(id, projectId, start, i,
    		limit, sort);
        }
    /*
     * (non-Javadoc)
     * @see
     * cn.zeppin.service.IProjectApplyService#getCountByTrainingCollege(java
     * .lang.Integer, int, int)
     */
    @Override
    public int getCountByTrainingCollege(Integer id, int projectId,
	    int subjectId)
    {
	// TODO Auto-generated method stub
	return projectApplyDao.getCountByTrainingCollege(id, projectId,
		subjectId);
    }
    
    /**
     * 获取 项目承训单位管理员数
     * 
     * @param projectId
     * @param subjectId
     * @param collegeId
     * @return
     */
    public Integer getTrainingAdminCount(Integer projectId ,Integer subjectId ,Integer collegeId){
    	return this.projectApplyDao.getTrainingAdminCount(projectId , subjectId , collegeId);
    }
    
    
    /**
     * 获取 项目承训单位管理员
     * 
     * @return
     */
    public List<Object[]> getTrainingAdminList(Integer projectId ,Integer subjectId ,Integer collegeId , String sortName, String sortType,int offset, int length){
    	return this.projectApplyDao.getTrainingAdminList(projectId , subjectId , collegeId , sortName, sortType,offset ,length);
    }

    /**
     * 通过项目ID取得已项目中标信息
     * @param projectId
     * @return
     */
	@Override
	public List<ProjectApply> getCheckedProjectApply(Integer projectId) {
		// TODO Auto-generated method stub
		return projectApplyDao.getCheckedProjectApply(projectId);
	}

	@Override
	public List getListByCollege(Integer trainingCollege) {
		// TODO Auto-generated method stub
		return this.projectApplyDao.getListByCollege(trainingCollege);
	}

	@Override
	public List getListByProject(Integer projectId, Integer trainingCollege) {
		// TODO Auto-generated method stub
		return this.projectApplyDao.getListByProject(projectId, trainingCollege);
	}

	@Override
	public List getProjectByParams(Map<String, Object> params,
			List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		return this.projectApplyDao.getProjectByParams(params, lityps);
	}

	@Override
	public List getProjectByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.projectApplyDao.getProjectByParams(params);
	}
	
	@Override
	public List getProjectInfoByCollege(Integer trainingCollege) {
		// TODO Auto-generated method stub
		return this.projectApplyDao.getProjectInfoByCollege(trainingCollege);
	}

	
}
