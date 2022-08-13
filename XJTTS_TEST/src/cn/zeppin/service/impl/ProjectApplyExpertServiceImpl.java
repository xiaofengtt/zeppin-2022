package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IProjectApplyExpertDao;
import cn.zeppin.entity.ProjectApplyExpert;
import cn.zeppin.service.IProjectApplyExpertService;

public class ProjectApplyExpertServiceImpl extends BaseServiceImpl<ProjectApplyExpert, Integer> implements IProjectApplyExpertService {

	private IProjectApplyExpertDao ProjectApplyExpertDao;

	public ProjectApplyExpert getProjectApplyExpertByAll(int paid,int expertid){
		return this.ProjectApplyExpertDao.getProjectApplyExpertByAll(paid,expertid);
	}
	
	public int checkProjectApplyExpert(int paid,int expertid){
		return this.ProjectApplyExpertDao.checkProjectApplyExpert(paid,expertid);
	}
	
	public void deleteByProjectApply(int id){
		this.ProjectApplyExpertDao.deleteByProjectApply(id);
	}
	
	public int getCountForExpert (int expertid){
		return this.ProjectApplyExpertDao.getCountForExpert(expertid);
	}
	
	public IProjectApplyExpertDao getProjectApplyExpertDao() {
		return ProjectApplyExpertDao;
	}

	public void setProjectApplyExpertDao(IProjectApplyExpertDao ProjectApplyExpertDao) {
		this.ProjectApplyExpertDao = ProjectApplyExpertDao;
	}

	@Override
	public ProjectApplyExpert add(ProjectApplyExpert t) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.add(t);
	}

	@Override
	public ProjectApplyExpert update(ProjectApplyExpert t) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.update(t);
	}

	@Override
	public void delete(ProjectApplyExpert t) {
		// TODO Auto-generated method stub
		ProjectApplyExpertDao.delete(t);
	}

	@Override
	public ProjectApplyExpert load(Integer id) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.load(id);
	}

	@Override
	public ProjectApplyExpert get(Integer id) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.get(id);
	}

	@Override
	public List<ProjectApplyExpert> loadAll() {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.loadAll();
	}

	@Override
	public List<ProjectApplyExpert> findAll() {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectApplyExpert> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		ProjectApplyExpertDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectApplyExpert> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		ProjectApplyExpertDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return ProjectApplyExpertDao.getListPage(sql, offset, length, objParas);
	}


}
