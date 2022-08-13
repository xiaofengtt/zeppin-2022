package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IProjectCycleDao;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.service.IProjectCycleService;

@SuppressWarnings("rawtypes")
public class ProjectCycleServiceImpl extends
		BaseServiceImpl<ProjectCycle, Integer> implements
		IProjectCycleService {

	private IProjectCycleDao projectCycleDao;


	public IProjectCycleDao getProjectCycleDao() {
		return projectCycleDao;
	}

	public void setProjectCycleDao(IProjectCycleDao projectCycleDao) {
		this.projectCycleDao = projectCycleDao;
	}

	public ProjectCycle add(ProjectCycle t) {
		return projectCycleDao.add(t);
	}

	public ProjectCycle update(ProjectCycle t) {
		return projectCycleDao.update(t);
	}

	public void delete(ProjectCycle t) {
		projectCycleDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return projectCycleDao.getHibeTemplate();
	}

	public ProjectCycle load(Integer id) {
		return projectCycleDao.load(id);
	}

	public ProjectCycle get(Integer id) {
		return projectCycleDao.get(id);
	}

	public List<ProjectCycle> loadAll() {
		return projectCycleDao.loadAll();
	}

	public List<ProjectCycle> findAll() {
		return projectCycleDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return projectCycleDao.findByHSQL(querySql);
	}

	public List<ProjectCycle> getListForPage(String hql, int offset,
			int length) {
		return projectCycleDao.getListForPage(hql, offset, length);
	}

	public List<ProjectCycle> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return projectCycleDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<ProjectCycle> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return projectCycleDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		projectCycleDao.executeHSQL(hql);
	}

	public List<ProjectCycle> getListByHSQL(String hql) {
		return projectCycleDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return projectCycleDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		projectCycleDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return projectCycleDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return projectCycleDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return projectCycleDao.getObjectBySql(sql, paras);
	}

	@Override
	public List<ProjectCycle> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.projectCycleDao.getListByParams(params, start, limit, sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.projectCycleDao.getListCountByParams(params);
	}
	
	
	
}
