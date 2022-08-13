package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.dao.IProjectLevelDao;
import cn.zeppin.entity.ProjectLevel;
import cn.zeppin.service.IProjectLevelService;

public class ProjectLevelServiceImpl extends BaseServiceImpl<ProjectLevel, Short> implements IProjectLevelService {

	private IProjectLevelDao projectLevelDao;

	public IProjectLevelDao getProjectLevelDao() {
		return projectLevelDao;
	}

	public void setProjectLevelDao(IProjectLevelDao projectLevelDao) {
		this.projectLevelDao = projectLevelDao;
	}

	@Override
	public ProjectLevel add(ProjectLevel t) {
		// TODO Auto-generated method stub
		return projectLevelDao.add(t);
	}

	@Override
	public ProjectLevel update(ProjectLevel t) {
		// TODO Auto-generated method stub
		return projectLevelDao.update(t);
	}

	@Override
	public void delete(ProjectLevel t) {
		// TODO Auto-generated method stub
		projectLevelDao.delete(t);
	}

	@Override
	public ProjectLevel load(Short id) {
		// TODO Auto-generated method stub
		return projectLevelDao.load(id);
	}

	@Override
	public ProjectLevel get(Short id) {
		// TODO Auto-generated method stub
		return projectLevelDao.get(id);
	}

	@Override
	public List<ProjectLevel> loadAll() {
		// TODO Auto-generated method stub
		return projectLevelDao.loadAll();
	}

	@Override
	public List<ProjectLevel> findAll() {
		// TODO Auto-generated method stub
		return projectLevelDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return projectLevelDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectLevel> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return projectLevelDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		projectLevelDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectLevel> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return projectLevelDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectLevelDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		projectLevelDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectLevelDao.getListPage(sql, offset, length, objParas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.IProjectLevelService#getList()
	 */
	@Override
	public List<ProjectLevel> getList() {
		String hqlsString = "from ProjectLevel order by level";
		List<ProjectLevel> tlst = new ArrayList<ProjectLevel>();
		tlst = getListByHSQL(hqlsString);
		return tlst;
	}

}
