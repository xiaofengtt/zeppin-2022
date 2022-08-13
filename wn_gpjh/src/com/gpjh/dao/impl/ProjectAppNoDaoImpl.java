package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.model.ProjectAppNo;
import com.gpjh.dao.ProjectAppNoDao;

@Repository("projectAppNoDao")
public class ProjectAppNoDaoImpl extends BaseDaoImpl<ProjectAppNo, String>
		implements ProjectAppNoDao {

}
