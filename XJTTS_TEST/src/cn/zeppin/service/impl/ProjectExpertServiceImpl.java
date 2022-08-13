package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IBaseDao;
import cn.zeppin.dao.IProjectExpertDao;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IProjectExpertService;

public class ProjectExpertServiceImpl extends BaseServiceImpl<ProjectExpert, Integer> implements IProjectExpertService {
	
	private IProjectExpertDao projectExpertDao;
	
	public int getProjectExpertCount(String searchName, String searchType, String sortName, String sortType){
		return projectExpertDao.getProjectExpertCount(searchName,  searchType,  sortName,  sortType);
	}
	
	public List getProjectExpert(String searchName, String searchType, String sortName, String sortType,int offset, int length){
		return projectExpertDao.getProjectExpert( searchName,  searchType,  sortName,  sortType, offset,  length);
	}

	public List<ProjectExpert> getProjectExpertList(){
		return projectExpertDao.getProjectExpertList();
	}
	
	public IProjectExpertDao getProjectExpertDao() {
		return projectExpertDao;
	}


	public void setProjectExpertDao(IProjectExpertDao projectExpertDao) {
		this.projectExpertDao = projectExpertDao;
	}


	@Override
	public ProjectExpert add(ProjectExpert t) {
		// TODO Auto-generated method stub
		return projectExpertDao.add(t);
	}


	@Override
	public ProjectExpert update(ProjectExpert t) {
		// TODO Auto-generated method stub
		return projectExpertDao.update(t);
	}


	@Override
	public void delete(ProjectExpert t) {
		// TODO Auto-generated method stub
		projectExpertDao.delete(t);
	}


	@Override
	public ProjectExpert load(Integer id) {
		// TODO Auto-generated method stub
		return projectExpertDao.load(id);
	}


	@Override
	public ProjectExpert get(Integer id) {
		// TODO Auto-generated method stub
		return projectExpertDao.get(id);
	}


	@Override
	public List<ProjectExpert> loadAll() {
		// TODO Auto-generated method stub
		return projectExpertDao.loadAll();
	}


	@Override
	public List<ProjectExpert> findAll() {
		// TODO Auto-generated method stub
		return projectExpertDao.findAll();
	}


	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return projectExpertDao.findByHSQL(querySql);
	}


	@Override
	public List<ProjectExpert> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return projectExpertDao.getListForPage(hql, offset, length);
	}


	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		projectExpertDao.executeHSQL(hql);
	}


	@Override
	public List<ProjectExpert> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return projectExpertDao.getListByHSQL(hql);
	}


	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectExpertDao.getListBySQL(sql, objParas);
	}


	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		projectExpertDao.executeSQLUpdate(sql, objParas);
	}


	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return projectExpertDao.getListPage(sql, offset, length, objParas);
	}
	
	@Override
	public int checkUserInfo(Object[] pars) {
		return this.projectExpertDao.checkUserInfo(pars);
	}
	

}
