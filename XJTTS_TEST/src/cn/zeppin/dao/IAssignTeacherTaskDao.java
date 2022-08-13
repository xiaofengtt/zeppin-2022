package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherTask;

public interface IAssignTeacherTaskDao extends
	IBaseDao<AssignTeacherTask, Integer>
{

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
    public List<AssignTeacherTask> getAssignTeacherTask(int projectId,
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
    public int getAssignTeacherTaskCount(int projectId,int subjectId, int organizationId,
	    UserSession us);

    /**
     * @category 判断表中是否已经含有记录
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
     * @param opid
     * @param allotLevel
     * @param lstPid
     * @return
     */
    public AssignTeacherTask get(int projectid, short subjectid, int tcid,
	    int orid, Integer opid, String allotLevel, List<Integer> lstPid);

    /**
     * @author Clark 2014.05.29 依据项目条件和执行部门获取任务分配列表
     * @param int rprojectId 项目ID
     * @param UserSession
     *            us 用户所在部门
     * @param pageIndex
     * @return List<Map<String,Object>> 任务分配列表
     */
    public List<Map<String, Object>> getAssignTeacherTaskResults(
	    int rprojectId,int rsubjectId, UserSession us, int offset, int length);

    /**
     * 通过唯一索引取得唯一记录
     * 
     * @author Clark 2014.05.30
     * @param project
     * @param subject
     * @param trainingCollege
     * @param g_organization
     * @param p_organization
     * @return AssignTeacherTask
     */
    public AssignTeacherTask get(String project, String subject,
	    String trainingCollege, short level);
}
