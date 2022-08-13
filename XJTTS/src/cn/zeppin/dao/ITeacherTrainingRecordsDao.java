package cn.zeppin.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingRecords;

public interface ITeacherTrainingRecordsDao extends IBaseDao<TeacherTrainingRecords, Integer> {

	/**
	 * @author Clark 2014.05.27 通过项目、学科、承训单位，得到所有下属单位报送的老师数量
	 * @param projectId
	 * @param subjectId
	 * @param collegeId
	 * @param tlstOrg
	 * @return 报送教师数量
	 */
	public Map<Object, Long> getTrainingRecordsTeacherNumber(int projectId, int subjectId, int collegeId, Organization organization);

	/**
	 * 批量修改学员分班
	 * 
	 * @param classes
	 * @param ids
	 */
	public void updateClasses(String classes,String ids);
	
	/**
	 * 获取 登陆码列表总数
	 * 
	 * @param projectId
	 * @param subjectId
	 * @param trainingCollegeId
	 * @param idcard
	 * @return
	 */
	public int  getAduTeacherCountByIdcard(int projectId,int subjectId,int trainingCollegeId,int classes,String idcard);
	
	/**
	 * 获取 登陆码列表
	 * 
	 * @param projectId
	 * @param subjectId
	 * @param trainingCollegeId
	 * @param idcard
	 * @return
	 */
	public List  getAduTeacherByIdcard(int projectId,int subjectId,int trainingCollegeId,int classes,String idcard,int offset, int length);
	
	/**
	 * 获取 审核学员个数
	 * 
	 * @param projectId
	 *            项目Id
	 * @param subjectId
	 *            学科Id
	 * @param organizationId
	 * @param status
	 * @return
	 */
	public int getAduTeacherCount(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps);

	/**
	 * 获取 审核学员个数
	 * 
	 * @param projectId
	 *            项目Id
	 * @param subjectId
	 *            学科Id
	 * @param organizationId
	 * @param status
	 * @return
	 */
	public List getAduTeacher(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps, int offset, int length);

	/**
	 * 获取 审核学员个数
	 * 
	 * @param projectId
	 *            项目Id
	 * @param subjectId
	 *            学科Id
	 * @param organizationId
	 * @param status
	 * @return
	 */
	public List getAduTeacher(int projectId, int subjectId, int trainingUnit, int status, int offset, int length);

	/**
	 * 获取 审核学员个数
	 * 
	 * @param projectId
	 *            项目Id
	 * @param subjectId
	 *            学科Id
	 * @param organizationId
	 * @param trainingUnit
	 * @param status
	 * @param trainingStatus
	 * @return
	 */
	public List getAduTeacher(int projectId, int subjectId, int trainingUnit, int status, int trainingStatus,int offset, int length);
	
	/**
	 * 通过唯一索引拿到培训记录
	 * 
	 * @author Clark 2014.05.30
	 * @param project
	 * @param subject
	 * @param trainingCollege
	 * @param teacher
	 * @return TeacherTrainingRecords
	 */
	public TeacherTrainingRecords getTeacherTrainingRecord(String project, String subject, String trainingCollege, String teacher);
	
	/**
	 * 通过Name拿到培训记录
	 * 
	 * @author Clark 2014.8.5
	 * @param teacherName
	 * @param projectName
	 * @param trainingtName
	 * @param subjectName
	 * @return TeacherTrainingRecords
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordByName(String teacherName, String projectName, String trainingtName, String subjectName);
	
	/**
	 * 通过条件查询教师培训记录
	 * @param teacherName
	 * @param teacherIdCard
	 * @param projectName
	 * @param trainingtName
	 * @param subjectName
	 * @return
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordByIdCard(String teacherName, String teacherIdCard, String projectName, String trainingtName, String subjectName);

	/**
	 * 获取教师培训记录
	 * 
	 * @param uuid
	 * @return
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordsByUuid(String uuid);
	
	/**
	 * 获取教师培训记录列表 根据手机号码
	 * @author Administrator
	 * @date: 2014年8月4日 上午11:34:09 <br/> 
	 * @param mobile
	 * @return
	 */
	public List<TeacherTrainingRecords> getTeacherTrainingRecordsByMobile(String mobile);

	/**
	 * 获取培训记录个数
	 * 
	 * @param map
	 * @return
	 */
	public int getAduTeacherCount(HashMap<String, String> map, Organization organization, List<ProjectType> lityps);

	/**
	 * 获取培训记录信息
	 * 
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getAduTeacher(HashMap<String, String> map, Organization organization, List<ProjectType> lityps, int offset, int length);

	/**
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingCollegeId
	 * @param organization
	 * @return
	 */
	public int getTeacherTrainingRecordsCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization);

	/**
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingCollegeId
	 * @param organization
	 * @return
	 */
	public int getTeacherTrainingRecordsAduCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization);
	
	/**
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingCollegeId
	 * @param organization
	 * @return
	 */
	public int getTeacherTrainingRecordsRegistCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization);
	
	/**
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingCollegeId
	 * @param organization
	 * @return
	 */
	public int getTeacherTrainingRecordsFinishCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization);
	/**
	 * @param pid
	 * @param sid
	 * @param cid
	 * @return
	 */
	public int getListCount(Integer pid, Short sid, Integer cid,int classes);

	/**
	 * @param projectId
	 * @param sbujectId
	 * @param trainingUnitId
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	public List<TeacherTrainingRecords> getListForPage(Integer projectId, Short sbujectId, Integer trainingUnitId, int classes,int start, int limit, String sort);

	/**
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingUnitId
	 * @param state
	 * @return
	 */
	public int getAduTeacherCount(Integer projectId, Integer trainingSubjectId, Integer trainingUnitId, int state,int classes);
	
	
	/**
	 * 获取教师培训记录
	 * @param trainingCollegeId
	 * @param idCard
	 * @return
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordsByIdCard(int trainingCollegeId,String idCard);

	
	/**
	 * 按条件查询教师培训记录
	 * @param projectId
	 * @param sbujectId
	 * @param trainingUnitId
	 * @param idcard
	 * @param name
	 * @param classes
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	public List getListForPage(Integer projectId, Short sbujectId, Integer trainingUnitId, String idcard, String name, int classes,int start, int limit, String sort);
	
	
	/**
	 * 根据条件查询教师培训记录数目
	 * @param projectId
	 * @param subjectId
	 * @param trainingCollegeId
	 * @param classes
	 * @param idcard
	 * @return
	 */
	public int  getListTeacherCount(int projectId,int subjectId,int trainingCollegeId,int classes,String idcard, String name);
	
	/**
	 * 获取审核学员个数
	 * @param teacherName
	 * @param projectId
	 * @param subjectId
	 * @param trainingUnit
	 * @param isAdmin
	 * @param status
	 * @param organization
	 * @param lityps
	 * @param enrollType
	 * @return
	 */
	public int getAduTeacherCount(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps, String enrollType, int projectCycle, int teacherOrg);

	/**
	 * 获取审核学员列表
	 * @param teacherName
	 * @param projectId
	 * @param subjectId
	 * @param trainingUnit
	 * @param isAdmin
	 * @param status
	 * @param organization
	 * @param lityps
	 * @param offset
	 * @param length
	 * @param enrollType
	 * @return
	 */
	public List getAduTeacher(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps, int offset, int length, String enrollType, int projectCycle, int teacherOrg);

	/**
	 * 获取审核学员个数
	 * @param teacherName
	 * @param projectId
	 * @param subjectId
	 * @param trainingUnit
	 * @param isAdmin
	 * @param status
	 * @param organization
	 * @param lityps
	 * @param enrollType
	 * @return
	 */
	public int getTrainAduTeacherCount(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps, String enrollType, int projectCycle, int teacherOrg);

	/**
	 * 获取审核学员列表
	 * @param teacherName
	 * @param projectId
	 * @param subjectId
	 * @param trainingUnit
	 * @param isAdmin
	 * @param status
	 * @param organization
	 * @param lityps
	 * @param offset
	 * @param length
	 * @param enrollType
	 * @return
	 */
	public List getTrainAduTeacher(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps, int offset, int length, String enrollType, int projectCycle, int teacherOrg);

	/**
	 * 获取自主报名项目集中培训形式教师报名培训记录数目
	 * @param teacherName
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingUnitId
	 * @param status
	 * @return
	 */
	public int getAduTeacherCount(String teacherName, Integer projectId, Integer trainingSubjectId, Integer trainingUnitId, int status);
	
	/**
	 * 获取自主报名项目集中培训形式教师报名培训记录
	 * @param teacherName
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingUnitId
	 * @param status
	 * @return
	 */
	public List getListTeacherCount(String teacherName, Integer projectId, Integer trainingSubjectId, Integer trainingUnitId, int status, int offset, int length);
	
	public int getTeacherRecordsCountByTid(int teacherId, Map<String, Object> pagram);
	
	public List getTeacherRecordsByTid(int teacherId, int offset, int length, Map<String, String> sortParams, Map<String, Object> pagram);
	
	/**
	 * 根据条件获取教师培训记录
	 * @param offset
	 * @param length
	 * @param lsType
	 * @param sortParams
	 * @param param
	 * @return
	 */
	public List getTeacherRecordsForSubject(int offset, int length, Map<String, Object> sortParams, Map<String, Object> param);
	
	public int getTeacherRecordsForSubject(Map<String, Object> sortParams, Map<String, Object> param);
	
	/**
	 * 统计某一时间段的记录，
	 * @param before 开始时间
	 * @param after  结束时间
	 * @return
	 */
	public int getAduTeacherCountByTimePeriod(Timestamp beginDate, Timestamp endDate);
	/**
	 * 某一时间段的所有记录，
	 * @param before 开始时间
	 * @param after  结束时间
	 * @return
	 */
	public List getAduTeacherListByTimePeriod(Timestamp beginDate, Timestamp endDate);
	
	public List getLevelAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, int projectCycle, List<ProjectType> lityps, int offset,
			int length);
	
	public int getLevelAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, int projectCycle, List<ProjectType> lityps);
	
	public List getLevelAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length,int level, int projectCycle);
	
	public int getLevelAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,int level, int projectCycle);
	
	/**
	 * 通过周期和教师id,获取培训记录数据, 记录的状态必须>=3即合格或者不合格
	 * @param cycle
	 * @return
	 */
	public List getTTRecordsByCycle(int teacherId,List<String> cycle);
	
	/**
	 * 获取单位时间内教师培训记录的时间信息（为统计使用）
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List getAduTeacherTimeListByTimePeriod(Timestamp beginDate, Timestamp endDate);
	
	/**
	 * 首页统计未审核的教师培训记录
	 * @param isAdmin
	 * @param status
	 * @param organization
	 * @return
	 */
	public int getAduTeacherCount(int isAdmin, int status,Organization organization,int level);
}
