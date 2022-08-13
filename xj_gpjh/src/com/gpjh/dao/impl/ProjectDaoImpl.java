package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.ProjectDao;
import com.gpjh.model.Project;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl<Project, String> implements ProjectDao{

}
