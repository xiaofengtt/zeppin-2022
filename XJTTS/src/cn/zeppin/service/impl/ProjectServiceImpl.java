package cn.zeppin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectDao;
import cn.zeppin.dao.IProjectTypeDao;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IProjectService;
@SuppressWarnings("rawtypes")	
public class ProjectServiceImpl extends BaseServiceImpl<Project, Integer> implements IProjectService {

	private IProjectDao projectDao;
	private IProjectTypeDao projectTypeDao;

	/**
	 * @return the projectTypeDao
	 */
	public IProjectTypeDao getProjectTypeDao() {
		return projectTypeDao;
	}

	/**
	 * @param projectTypeDao the projectTypeDao to set
	 */
	public void setProjectTypeDao(IProjectTypeDao projectTypeDao) {
		this.projectTypeDao = projectTypeDao;
	}

	public List getProjectForTrainingCollege(Map<String, Object> params, Map<String, String> sortParams,int id, Date date,int offset, int length){
		return this.projectDao.getProjectForTrainingCollege(params,sortParams,id,date,offset,length);
	}
	
	public IProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(IProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	@Override
	public Project add(Project t) {
		// TODO Auto-generated method stub
		return projectDao.add(t);
	}

	@Override
	public Project update(Project t) {
		// TODO Auto-generated method stub
		return projectDao.update(t);
	}

	@Override
	public void delete(Project t) {
		// TODO Auto-generated method stub
		projectDao.delete(t);
	}

	@Override
	public Project load(Integer id) {
		// TODO Auto-generated method stub
		return projectDao.load(id);
	}

	@Override
	public Project get(Integer id) {
		// TODO Auto-generated method stub
		return projectDao.get(id);
	}

	@Override
	public List<Project> loadAll() {
		// TODO Auto-generated method stub
		return projectDao.loadAll();
	}

	@Override
	public List<Project> findAll() {
		// TODO Auto-generated method stub
		return projectDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return projectDao.findByHSQL(querySql);
	}

	@Override
	public List<Project> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return projectDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		projectDao.executeHSQL(hql);
	}

	@Override
	public List<Project> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return projectDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		projectDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<Project> getProjectByStatus(short status, List<ProjectType> lityps) {
		return this.projectDao.getProjectByStatus(status, lityps);
	}

	@Override
	public int getProjectCountByParams(Map<String, Object> params, List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		return this.getProjectDao().getProjectCountByParams(params, lityps);
	}

	@Override
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, List<ProjectType> lityps, int offset, int lengths) {
		// TODO Auto-generated method stub
		return this.getProjectDao().getProjectByParams(params, sortParams, lityps, offset, lengths);
	}

	@Override
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		return this.getProjectDao().getProjectByParams(params, sortParams, lityps);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.IProjectService#getProjectByParams(java.util.Map,
	 * java.util.Map, java.util.Date, int, int, int)
	 */
	@Override
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, Date date, int projectStatus, int offset, int maxpagesize) {
		// TODO Auto-generated method stub
		return projectDao.getProjectByParams(params, sortParams, date, projectStatus, offset, maxpagesize);
	}

	@Override
	public List<Project> getProjectByTypes(String type) {
		// TODO Auto-generated method stub
		return this.getProjectDao().getProjectByTypes(type);
	}

	public List<String> getProjectYearList() {
		// TODO Auto-generated method stub
		return this.getProjectDao().getProjectYearList();
	}
	
	/**
	 * ?????? ?????????????????????
	 * 
	 * @param us
	 * @return
	 */
	public int getProjectAdminCount(String organizationScode , Integer organizationLevel , List<Integer> projectTypeList){
		return this.projectDao.getProjectAdminCount(organizationScode , organizationLevel , projectTypeList);
	}
	
	/**
	 * ?????? ?????????????????????
	 * 
	 * @param us
	 * @return
	 */
	public List<Object[]> getProjectAdminList(String organizationScode , Integer organizationLevel , List<Integer> projectTypeList){
		return this.getProjectDao().getProjectAdminList(organizationScode , organizationLevel , projectTypeList);
	}
	
	/**
	 * ?????? ????????????????????????
	 * 
	 * @return
	 */
	public List<Object[]> getProjectAnalysis(List<ProjectType> lityps){
		return this.getProjectDao().getProjectAnalysis(lityps);
	}
	
	/**
	 * ????????????ID??????????????????????????????????????????
	 * @param projectId
	 * @param projectTypeLevel ???????????????????????????
	 * @return
	 */
	@Override
	public List<Project> getSameTypeProjectList(String projectId) {
		// TODO Auto-generated method stub
		Project project = getProjectDao().get(Integer.valueOf(projectId));
		//??????Level???????????????????????????
		ProjectType rootProjectType = getProjectTypeDao().getRootProjectType(project.getProjectType());
		//????????????????????????????????????????????????????????????
		List<ProjectType> leafTypes = getProjectTypeDao().getSubProjectType(rootProjectType);
		//???????????????????????????????????????????????????
		return this.getProjectDao().getProjectList(project.getYear(),leafTypes);
	}

	@Override
	public List<Project> getProjectByStatusAndType(short status,
			List<ProjectType> lityps, short enrollType, String organization) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectByStatusAndType(status, lityps, enrollType, organization);
	}

	@Override
	public int getProjectCountByParams(short status, List<ProjectType> lityps,
			short enrollType, String organization) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectCountByParams(status, lityps, enrollType, organization);
	}

	@Override
	public List<Project> getProjectByGroup(Integer groupId) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectByGroup(groupId);
	}

	@Override
	public int getProjectCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectCountByParams(params);
	}

	@Override
	public List getProjectByParams(Map<String, Object> params,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectByParams(params, sortParams, offset, length);
	}

	@Override
	public List getProjectListByParams(Map<String, Object> params,
			Map<String, String> sortParams, List<ProjectType> lityps,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectListByParams(params, sortParams, lityps, offset, length);
	}

	@Override
	public int getProjectListCountByParams(Map<String, Object> params,
			List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectListCountByParams(params, lityps);
	}

	@Override
	public List<Project> getProjectByTypes(String type, int organization) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectByTypes(type, organization);
	}

	@Override
	public List getProjectListByParams4LevelThree(Map<String, Object> params,
			Map<String, String> sortParams, List<ProjectType> lityps,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectListByParams4LevelThree(params, sortParams, lityps, offset, length);
	}

	@Override
	public int getProjectListCountByParams4LevelThree(
			Map<String, Object> params, List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		return this.projectDao.getProjectListCountByParams4LevelThree(params, lityps);
	}

}
