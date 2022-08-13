package com.gpjh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpjh.dao.ProjectDao;
import com.gpjh.model.Project;
import com.gpjh.service.ProjectService;

@Service("projectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project, Integer> implements ProjectService {

    @Autowired
	private ProjectDao projectDao;

	@Override
	public Project add(Project t) {
		// TODO Auto-generated method stub
		return projectDao.add(t);
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
	public Project update(Project t) {
		// TODO Auto-generated method stub
		return projectDao.update(t);
	}

	@Override
	public Project get(Integer id) {
		// TODO Auto-generated method stub
		return projectDao.get(id);
	}

	@Override
	public List<Project> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return projectDao.getListForPage(hql, pageId, pageSize);
	}
}
