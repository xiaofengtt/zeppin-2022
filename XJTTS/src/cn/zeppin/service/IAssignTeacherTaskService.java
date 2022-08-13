package cn.zeppin.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Organization;

public interface IAssignTeacherTaskService extends
	IBaseService<AssignTeacherTask, Integer>
{

    /**
     * 获取 任务分配的当前级别
     * 
     * @param params
     * @param us
     * @return
     */
    public int getAssignTeacherTaskLevel(Object[] params, UserSession us);

    /**
     * 获取 任务分配列表 by 项目id 和组织架构
     * 
     * @param project
     * @param organization
     * @return
     */
    public List<AssignTeacherTask> getAssignTeacherTaskByProject(int project,
	    int organization);

    /**
     * 获取当前用户的任务列表
     * 
     * @param request
     * @param us
     * @param outHt
     */
    public void getAssignTeacherTaskByOrganization(HttpServletRequest request,
	    UserSession us, String year, String project, String subject,String enrollType, Hashtable<String, Object> outHt,String projectlevel);

    public int getAssignStatus(int projectid,int subjectid, int organizationid);

    public void getTrainingRecordsAllTeacherNumber(Object[] params,
	    UserSession us, Hashtable<String, Object> outHt);

    /**
     * 依据项目条件和执行部门获取任务分配列表
     * 
     * @param projectId
     *            项目ID
     * @param organizationId
     *            用户所在部门
     * @param offset
     *            起始记录数
     * @param length
     *            结果记录数
     * @return List<AssignTeacherTask> 任务分配列表
     */
    public List<AssignTeacherTask> getAssignTeacherTask(int projectid,
	    int organizationId, int offset, int length);

    /**
     * 依据项目条件和执行部门获取任务分配总数
     * 
     * @param projectId
     *            项目ID
     * @param organizationId
     *            用户所在部门
     * @return 当前用户所能看到的任务分配总数
     */
    public int getAssignTeacherTaskCount(String year, int projectId, int subjectId,int organizationId,
	    UserSession us);

    /**
     * @category 查询记录是否已经存在，并返回pid
     * @param projectid
     * @param subjectid
     * @param tcid
     * @param orid
     * @param opid
     * @param allotLevel
     * @param lstPid
     * @return
     */
    public boolean exist(int projectid, short subjectid, int tcid, int orid,
	    Integer opid, String allotLevel, List<Integer> lstPid);

    /**
     * @param projectid
     * @param subjectid
     * @param tcid
     * @param orid
     * @param id
     * @param allotLevel
     * @param lstPid
     * @return
     */
    public AssignTeacherTask get(int projectid, short subjectid, int tcid,
	    int orid, Integer id, String allotLevel, List<Integer> lstPid);

    /**
     * 通过唯一索引取得唯一记录
     * 
     * @param project
     * @param subject
     * @param trainingCollege
     * @param g_organization
     * @param p_organization
     * @return AssignTeacherTask
     */
    public AssignTeacherTask get(String project, String subject,
	    String trainingCollege, short level);

    /**
     * 依据项目条件和执行部门获取任务分配列表
     * 
     * @param projectId
     *            项目ID
     * @param organizationId
     *            用户所在部门
     * @param offset
     *            起始记录数
     * @param length
     *            结果记录数
     * @return List<Map<String,Object>> 除了任务分配列表外，还有汇总的报送人员数量、审核数量等
     */

    public List<Map<String, Object>> getAssignTeacherTaskResults(String year, int rprojectId ,int rsubjectId, UserSession us, int offset, int maxpagesize);

    /**
     * 获取本部门的项目任务列表
     * @param projectId
     * @param org
     * @return
     */
	public List<AssignTeacherTask> getAssignTeacherTaskByOrganization(int projectId, Organization org);

    /**
     * @param projectid
     * @param subjectid
     * @param pid
     * @param allotLevel
     * @return
     * @author suijing 2014年7月16日 上午11:35:51
     */
    public List<AssignTeacherTask> getlist(int projectid, short subjectid,
	    Integer pid, String allotLevel);

	/**
	 * 根据候选唯一主键  得到AssignTeacherTask
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingCollegeId
	 * @param id
	 * @param id2
	 * @return
	 */
	public AssignTeacherTask getAssignTeacherTask(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Integer p_orgId, Integer g_orgId);


	/**
	 * 获取当前用户可操作任务个数
	 * @param params
	 * @return
	 */
	public int getAssignTeacherTaskCount(Map<String, Object> params);
	
	
	public int getTrainingStartCount(String starttime, int organizationId, UserSession us, String projectlevel);

    public List<Map<String, Object>> getTrainingStartResults(String starttime, int organizationId, UserSession us, int offset, int maxpagesize, String projectlevel);

    public int getAssignTeacherTaskCount(String year, int projectId, int subjectId,int organizationId,
    	    UserSession us, String projectlevel);
    public List<Map<String, Object>> getAssignTeacherTaskResults(String year, int rprojectId ,int rsubjectId, UserSession us, int offset, int maxpagesize, String projectlevel);
    public int getAssignTeacherTaskCountLevel(String year, int projectId, int subjectId,int organizationId,
    	    UserSession us, String projectlevel, String orgNum);
    public List<Map<String, Object>> getAssignTeacherTaskResultsLevel(String year, int rprojectId ,int rsubjectId, UserSession us, int offset, int maxpagesize, String projectlevel, String orgNum);
}
