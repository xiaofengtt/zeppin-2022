package cn.zeppin.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectType;
	
public interface IProjectDao extends IBaseDao<Project, Integer> {
	/**
	 * 获取 项目列表
	 * 
	 * @param status
	 * @param us
	 * @return
	 */
	public List<Project> getProjectByStatus(short status, List<ProjectType> lityps);
	

	/**
	 * 获取 项目个数
	 * @param params
	 * @param lityps
	 * @return
	 */
	public int getProjectCountByParams(Map<String, Object> params, List<ProjectType> lityps);

	/**
	 * 获取 项目管理员个数
	 * 
	 * @param us
	 * @return
	 */
	public int getProjectAdminCount(String organizationScode , Integer organizationLevel , List<Integer> projectTypeList);
	
	/**
	 * 获取 项目管理员列表
	 * 
	 * @param us
	 * @return
	 */
	public List<Object[]> getProjectAdminList(String organizationScode , Integer organizationLevel , List<Integer> projectTypeList);

	
	/**
	 * 根据参数获取 项目列表
	 * 
	 * @param params
	 * @param lityps
	 * @return
	 */
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, List<ProjectType> lityps,int offset,int length);
	
	public List getProjectForTrainingCollege(Map<String, Object> params, Map<String, String> sortParams,int id, Date date,int offset, int length);
	
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, List<ProjectType> lityps);
	
	/**
	 * 根据问卷类型来搜索项目
	 * @param type
	 * @return
	 */
	public List<Project> getProjectByTypes(String type);

	/**
	 * 根据项目年份列表
	 * 
	 * @return
	 */
	public List<String> getProjectYearList();
	
	/**
	 * @param params
	 * @param sortParams
	 * @param date
	 * @param projectStatus
	 * @param offset
	 * @param maxpagesize
	 * @return
	 */
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, Date date, int projectStatus, int offset, int maxpagesize);

	/**
	 * @param year
	 * @param leafTypes
	 * @return
	 */
	public List<Project> getProjectList(String year, List<ProjectType> leafTypes);

	/**
	 * 通过报名类型状态获取项目列表
	 * @param status
	 * @param lityps
	 * @param enrollType
	 * @return
	 */
	public List<Project> getProjectByStatusAndType(short status, List<ProjectType> lityps, short enrollType);
	
	public int getProjectCountByParams(short status, List<ProjectType> lityps, short enrollType);
}
