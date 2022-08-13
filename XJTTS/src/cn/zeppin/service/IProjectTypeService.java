package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ProjectType;

public interface IProjectTypeService extends IBaseService<ProjectType, Integer> {

	public List<ProjectType> findByName(String name);

	public int instByParams(Object[] pars);

	public int updateByParams(Object[] pars);

	public List<ProjectType> getListByPid(int id);

	public int getProjectHasChild(int id);
	
	public List<ProjectType> getProjectTypeList(List<ProjectType> lityps);
	/**
	 * 跟新组织架构的状态
	 * 
	 * @param pt
	 * @return 是否成功
	 */
	public void updateProjectTypeStatus(ProjectType pt, short status);

	/**
	 * 获得当前项目类型树的根节点
	 * @param projectType
	 * @return
	 */
	public ProjectType getRootProjectType(ProjectType projectType);
	
	public List<ProjectType> getProjectTypeList(List<ProjectType> lityps,Map<String, Object> params);

}
