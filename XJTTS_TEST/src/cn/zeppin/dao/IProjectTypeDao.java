package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.ProjectType;

public interface IProjectTypeDao extends IBaseDao<ProjectType, Integer>{

	/**
	 * @param projectType
	 * @return
	 */
	ProjectType getRootProjectType(ProjectType projectType);

	/**
	 * @param rootProjectType
	 * @return
	 */
	List<ProjectType> getSubProjectType(ProjectType rootProjectType);
	
	
	public List<ProjectType> findByName(String name);
	
	public int instByParams(Object[] pars);
	
	public int updateByParams(Object[] pars);

}
