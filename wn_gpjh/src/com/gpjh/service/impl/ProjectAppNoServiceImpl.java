package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.ProjectAppNoDao;
import com.gpjh.model.ProjectAppNo;
import com.gpjh.service.ProjectAppNoService;

@Service("projectAppNoService")
public class ProjectAppNoServiceImpl extends
		BaseServiceImpl<ProjectAppNo, String> implements ProjectAppNoService {

	@Autowired
	private ProjectAppNoDao projectAppNoDao;

	public ProjectAppNo add(ProjectAppNo t) {
		return projectAppNoDao.add(t);
	}

	public void delete(ProjectAppNo t) {
		projectAppNoDao.delete(t);
	}

	public ProjectAppNo load(String id) {
		return projectAppNoDao.load(id);
	}

	public ProjectAppNo get(String id) {
		return projectAppNoDao.get(id);
	}

	public List<ProjectAppNo> loadAll() {
		return projectAppNoDao.loadAll();
	}

	public List<ProjectAppNo> findAll() {
		return projectAppNoDao.findAll();
	}

	public ProjectAppNo update(ProjectAppNo t) {
		return projectAppNoDao.update(t);
	}

	public List<Object> findByHSQL(String querySql) {
		return projectAppNoDao.findByHSQL(querySql);
	}

	public List<ProjectAppNo> getListForPage(String hql, int offset, int length) {
		return projectAppNoDao.getListForPage(hql, offset, length);
	}

	public void executeHSQL(String hql) {
		projectAppNoDao.executeHSQL(hql);
	}

	public List executeSQL(String sql) {
		return projectAppNoDao.executeSQL(sql);
	}

	public int queryRowCount(String hql) {
		return projectAppNoDao.queryRowCount(hql);
	}

	public List<ProjectAppNo> getListByHSQL(String hql) {
		return projectAppNoDao.getListByHSQL(hql);
	}

}
