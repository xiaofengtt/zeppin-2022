package cn.zeppin.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.IAssignTeacherCheckDao;
import cn.zeppin.dao.ITeacherDao;
import cn.zeppin.dao.ITeacherTrainingRecordsDao;
import cn.zeppin.dao.ITeachingGradeDao;
import cn.zeppin.dao.ITeachingLanguageDao;
import cn.zeppin.dao.ITeachingSubjectDao;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.utility.Utlity;

public class TeacherTrainingRecordsServiceImpl extends
		BaseServiceImpl<TeacherTrainingRecords, Integer> implements
		ITeacherTrainingRecordsService {

	private ITeacherTrainingRecordsDao teacherTrainingRecordsDao;
	private IAssignTeacherCheckDao assignTeacherCheckDao;
	private ITeacherDao teacherDao;
	private ITeachingSubjectDao teachingSubjectDao;
	private ITeachingGradeDao teachingGradeDao;
	private ITeachingLanguageDao teachingLanguageDao;

	public ITeacherTrainingRecordsDao getTeacherTrainingRecordsDao() {
		return teacherTrainingRecordsDao;
	}

	public void setTeacherTrainingRecordsDao(
			ITeacherTrainingRecordsDao teacherTrainingRecordsDao) {
		this.teacherTrainingRecordsDao = teacherTrainingRecordsDao;
	}

	public IAssignTeacherCheckDao getAssignTeacherCheckDao() {
		return assignTeacherCheckDao;
	}

	public void setAssignTeacherCheckDao(
			IAssignTeacherCheckDao assignTeacherCheckDao) {
		this.assignTeacherCheckDao = assignTeacherCheckDao;
	}

	public ITeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public ITeachingSubjectDao getTeachingSubjectDao() {
		return teachingSubjectDao;
	}

	public void setTeachingSubjectDao(ITeachingSubjectDao teachingSubjectDao) {
		this.teachingSubjectDao = teachingSubjectDao;
	}

	public ITeachingGradeDao getTeachingGradeDao() {
		return teachingGradeDao;
	}

	public void setTeachingGradeDao(ITeachingGradeDao teachingGradeDao) {
		this.teachingGradeDao = teachingGradeDao;
	}

	public ITeachingLanguageDao getTeachingLanguageDao() {
		return teachingLanguageDao;
	}

	public void setTeachingLanguageDao(ITeachingLanguageDao teachingLanguageDao) {
		this.teachingLanguageDao = teachingLanguageDao;
	}

	public int getAduTeacherCountByIdcard(int projectId, int subjectId,
			int trainingCollegeId, int classes, String idcard) {
		return teacherTrainingRecordsDao.getAduTeacherCountByIdcard(projectId,
				subjectId, trainingCollegeId, classes, idcard);
	}

	public List getAduTeacherByIdcard(int projectId, int subjectId,
			int trainingCollegeId, int classes, String idcard, int offset,
			int length) {
		return teacherTrainingRecordsDao.getAduTeacherByIdcard(projectId,
				subjectId, trainingCollegeId, classes, idcard, offset, length);
	}

	public void updateClasses(String classes, String ids) {
		teacherTrainingRecordsDao.updateClasses(classes, ids);
	}

	@Override
	public TeacherTrainingRecords add(TeacherTrainingRecords t) {

		return teacherTrainingRecordsDao.add(t);
	}

	@Override
	public TeacherTrainingRecords update(TeacherTrainingRecords t) {

		return teacherTrainingRecordsDao.update(t);
	}

	@Override
	public void delete(TeacherTrainingRecords t) {

		teacherTrainingRecordsDao.delete(t);
	}

	@Override
	public TeacherTrainingRecords load(Integer id) {

		return teacherTrainingRecordsDao.load(id);
	}

	@Override
	public TeacherTrainingRecords get(Integer id) {

		return teacherTrainingRecordsDao.get(id);
	}

	@Override
	public List<TeacherTrainingRecords> loadAll() {

		return teacherTrainingRecordsDao.loadAll();
	}

	@Override
	public List<TeacherTrainingRecords> findAll() {

		return teacherTrainingRecordsDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {

		return teacherTrainingRecordsDao.findByHSQL(querySql);
	}

	@Override
	public List<TeacherTrainingRecords> getListForPage(String hql, int offset,
			int length) {

		return teacherTrainingRecordsDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {

		teacherTrainingRecordsDao.executeHSQL(hql);
	}

	@Override
	public List<TeacherTrainingRecords> getListByHSQL(String hql) {

		return teacherTrainingRecordsDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {

		return teacherTrainingRecordsDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {

		teacherTrainingRecordsDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {

		return teacherTrainingRecordsDao.getListPage(sql, offset, length,
				objParas);
	}

	@Override
	public int getAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps) {
		return this.teacherTrainingRecordsDao.getAduTeacherCount(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, lityps);
	}

	@Override
	public List getAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length) {
		return this.teacherTrainingRecordsDao.getAduTeacher(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, lityps, offset, length);
	}

	public List getAduTeacher(int projectId, int subjectId, int trainingUnit,
			int status, int offset, int length) {
		return this.teacherTrainingRecordsDao.getAduTeacher(projectId,
				subjectId, trainingUnit, status, offset, length);
	}

	public List getAduTeacher(int projectId, int subjectId, int trainingUnit,
			int status, int trainingStatus, int offset, int length) {
		return this.teacherTrainingRecordsDao
				.getAduTeacher(projectId, subjectId, trainingUnit, status,
						trainingStatus, offset, length);
	}

	/**
	 * @author Clark 2014.05.27 通过项目、学科、承训单位，得到所有下属单位报送的老师数量
	 * @param projectId
	 * @param subjectId
	 * @param collegeId
	 * @param tlstOrg
	 * @return 报送教师数量
	 */
	@Override
	public Map<Object, Long> getTrainingRecordsTeacherNumber(int projectId,
			int subjectId, int collegeId, Organization organization) {

		Map<Object, Long> result = this.getTeacherTrainingRecordsDao()
				.getTrainingRecordsTeacherNumber(projectId, subjectId,
						collegeId, organization);
		return result;
	}

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
	@Override
	public TeacherTrainingRecords getTeacherTrainingRecord(String project,
			String subject, String trainingCollege, String teacher) {

		return this.getTeacherTrainingRecordsDao().getTeacherTrainingRecord(
				project, subject, trainingCollege, teacher);
	}

	/**
	 * 通过Name到培训记录
	 * 
	 * @author Clark 2014.08.5
	 * @param teacherName
	 * @param projectName
	 * @param trainingtName
	 * @param subjectName
	 * @return TeacherTrainingRecords
	 */
	@Override
	public TeacherTrainingRecords getTeacherTrainingRecordByName(
			String teacherName, String projectName, String trainingtName,
			String subjectName) {

		return this.getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordByName(teacherName, projectName,
						trainingtName, subjectName);
	}

	/**
	 * 根据条件查询教师培训记录
	 * 
	 * @param teacherName
	 * @param teacherIdCard
	 * @param projectName
	 * @param trainingtName
	 * @param subjectName
	 * @return
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordByIdCard(
			String teacherName, String teacherIdCard, String projectName,
			String trainingtName, String subjectName) {
		return this.getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordByIdCard(teacherName, teacherIdCard,
						projectName, trainingtName, subjectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.ITeacherTrainingRecordsService#getAduTeacherCount(java
	 * .lang.Integer, java.lang.Integer, java.lang.Integer, int)
	 */
	@Override
	public int getAduTeacherCount(Integer projectId, Integer trainingSubjectId,
			Integer trainingUnitId, int state, int classes) {

		return teacherTrainingRecordsDao.getAduTeacherCount(projectId,
				trainingSubjectId, trainingUnitId, state, classes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.ITeacherTrainingRecordsService#getListForPage(java.
	 * lang.Integer, java.lang.Short, java.lang.Integer, int, int,
	 * java.lang.String)
	 */
	@Override
	public List<TeacherTrainingRecords> getListForPage(Integer projectId,
			Short sbujectId, Integer trainingUnitId, int classes, int start,
			int limit, String sort) {

		return teacherTrainingRecordsDao.getListForPage(projectId, sbujectId,
				trainingUnitId, classes, start, limit, sort);
	}

	@Override
	public TeacherTrainingRecords getTeacherTrainingRecordsByUuid(String uuid) {

		return this.getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordsByUuid(uuid);
	}

	@Override
	public int getAduTeacherCount(HashMap<String, String> map,
			Organization organization, List<ProjectType> lityps) {

		return this.getTeacherTrainingRecordsDao().getAduTeacherCount(map,
				organization, lityps);
	}

	@Override
	public List getAduTeacher(HashMap<String, String> map,
			Organization organization, List<ProjectType> lityps, int offset,
			int length) {

		return this.getTeacherTrainingRecordsDao().getAduTeacher(map,
				organization, lityps, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.ITeacherTrainingRecordsService#getListCount(java.lang
	 * .Integer, java.lang.Short, java.lang.Integer)
	 */
	@Override
	public int getListCount(Integer pid, Short sid, Integer cid, int classes) {

		return getTeacherTrainingRecordsDao().getListCount(pid, sid, cid,
				classes);
	}

	/**
	 * 按项目、学科、承训单位统计报送学员记录
	 * 
	 * @param projectId
	 * @param trainingCollegeId
	 * @param trainingSubjectId
	 * @param organization
	 * @return List
	 */
	@Override
	public int getTeacherTrainingRecordsCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {

		return getTeacherTrainingRecordsDao().getTeacherTrainingRecordsCount(
				projectId, trainingSubjectId, trainingCollegeId, organization);
	}

	@Override
	public int getTeacherTrainingRecordsAduCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {

		return getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordsAduCount(projectId,
						trainingSubjectId, trainingCollegeId, organization);
	}

	@Override
	public int getTeacherTrainingRecordsRegistCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {

		return getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordsRegistCount(projectId,
						trainingSubjectId, trainingCollegeId, organization);
	}

	@Override
	public int getTeacherTrainingRecordsFinishCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {

		return getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordsFinishCount(projectId,
						trainingSubjectId, trainingCollegeId, organization);
	}

	/**
	 * 获取教师培训记录列表 根据手机号码
	 * 
	 * @author Administrator
	 * @date: 2014年8月4日 上午11:34:09 <br/>
	 * @param mobile
	 * @return
	 */
	@Override
	public List<TeacherTrainingRecords> getTeacherTrainingRecordsByMobile(
			String mobile) {
		return getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordsByMobile(mobile);
	}

	/**
	 * 获取教师培训记录
	 * 
	 * @param trainingCollegeId
	 * @param idCard
	 * @return
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordsByIdCard(
			int trainingCollegeId, String idCard) {

		return this.getTeacherTrainingRecordsDao()
				.getTeacherTrainingRecordsByIdCard(trainingCollegeId, idCard);
	}

	@Override
	public List getListForPage(Integer projectId, Short sbujectId,
			Integer trainingUnitId, String idcard, String name, int classes,
			int start, int limit, String sort) {
		// TODO Auto-generated method stub
		return teacherTrainingRecordsDao.getListForPage(projectId, sbujectId,
				trainingUnitId, idcard, name, classes, start, limit, sort);
	}

	@Override
	public int getListTeacherCount(int projectId, int subjectId,
			int trainingCollegeId, int classes, String idcard, String name) {
		// TODO Auto-generated method stub
		return teacherTrainingRecordsDao.getListTeacherCount(projectId,
				subjectId, trainingCollegeId, classes, idcard, name);
	}

	@Override
	public int getAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,
			String enrollType, int projectCycle, int teacherOrg) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacherCount(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, lityps, enrollType, projectCycle, teacherOrg);
	}

	@Override
	public List getAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length, String enrollType, int projectCycle, int teacherOrg) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacher(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, lityps, offset, length, enrollType, projectCycle, teacherOrg);
	}
	
	@Override
	public int getTrainAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,
			String enrollType, int projectCycle, int teacherOrg) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTrainAduTeacherCount(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, lityps, enrollType, projectCycle, teacherOrg);
	}

	@Override
	public List getTrainAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length, String enrollType, int projectCycle, int teacherOrg) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTrainAduTeacher(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, lityps, offset, length, enrollType, projectCycle, teacherOrg);
	}

	@Override
	public int getAduTeacherCount(String teacherName, Integer projectId,
			Integer trainingSubjectId, Integer trainingUnitId, int status) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacherCount(teacherName,
				projectId, trainingSubjectId, trainingUnitId, status);
	}

	@Override
	public List getListTeacherCount(String teacherName, Integer projectId,
			Integer trainingSubjectId, Integer trainingUnitId, int status,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getListTeacherCount(teacherName,
				projectId, trainingSubjectId, trainingUnitId, status, offset,
				length);
	}

	@Override
	public int getTeacherRecordsCountByTid(int teacherId,
			Map<String, Object> pagram) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsCountByTid(
				teacherId, pagram);
	}

	@Override
	public List getTeacherRecordsByTid(int teacherId, int offset, int length,
			Map<String, String> sortParams, Map<String, Object> pagram) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsByTid(teacherId,
				offset, length, sortParams, pagram);
	}

	@Override
	public List getTeacherRecordsForSubject(int offset, int length,
			Map<String, Object> sortParams, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsForSubject(
				offset, length, sortParams, param);
	}

	@Override
	public int getTeacherRecordsForSubject(Map<String, Object> sortParams,
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsForSubject(
				sortParams, param);
	}

	@Override
	public void addTeacherTrainingRecords(UserSession us, Organization taddOra,
			Integer rowCount, List<HashMap<String, Object>> infomationList) {
		// TODO Auto-generated method stub
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (infomationList != null && !infomationList.isEmpty()) {
			for (HashMap<String, Object> info : infomationList) {
				Project project = (Project) info.get("project");
				Teacher teacher = (Teacher) info.get("teacher");
				TrainingSubject trainingSubject = (TrainingSubject) info
						.get("trainingSubject");
				TrainingCollege trainingCollege = (TrainingCollege) info
						.get("trainingCollege");
				Map<String, Object> pagram = new HashMap<String, Object>();
				pagram.put("projectId", project.getId());
				pagram.put("subjectId", (int) trainingSubject.getId());
				pagram.put("trainCollegeId", trainingCollege.getId());

				if (this.getTeacherRecordsCountByTid(teacher.getId(), pagram) == 0) {
					TeacherTrainingRecords ttr = new TeacherTrainingRecords();
					try {
						String vid = Utlity.getUuidPwd();
						ttr.setProject(project);
						ttr.setTrainingSubject(trainingSubject);
						ttr.setTrainingCollege(trainingCollege);

						ttr.setTeacher(teacher);
						ttr.setCreator(us.getId());

						ttr.setOrganization(taddOra);

						ttr.setCheckStatus((short) (us.getOrganizationLevel()));
						ttr.setUuid(vid);
						ttr.setIsPrepare(false);

						// 其他状态
						ttr.setFinalStatus((short) 2);
						ttr.setTrainingStatus((short) 1);

						// 重置替换状态
						ttr.setReplaceStatus((short) 0);
						ttr.setReplaceTeacher(0);
						ttr.setProjectCycle(project.getProjectCycle());

						if (project.getEnrollType() == 2) {
							if (project.getTraintype() == 1) {
								ttr.setIsConfirm((short) -1); // 集中培训 默认未确认状态
							} else {
								ttr.setIsConfirm((short) 1);// 非集中培训默认 确认通过状态
							}
						}
						// 冗余教师信息
						newAddTeacherInfo(ttr);
						this.add(ttr);

						AssignTeacherCheck atc = new AssignTeacherCheck();
						atc.setChecker(us.getId());
						atc.setTeacherTrainingRecords(ttr);
						atc.setType((short) 1);
						AssignTeacherCheck atc1 = new AssignTeacherCheck();
						atc1.setTeacherTrainingRecords(ttr);
						atc1.setType((short) 2);
						atc1.setChecker(us.getId());
						this.assignTeacherCheckDao.add(atc1);
						this.assignTeacherCheckDao.add(atc);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				int percent = (int) Math.ceil(((rowCount + infomationList
						.indexOf(info)) * 100) / (rowCount * 2));
				session.setAttribute("percent", percent);
			}
		}

	}

	@Override
	public int getAduTeacherCountByTimePeriod(Timestamp beginDate,
			Timestamp endDate) {
		return this.teacherTrainingRecordsDao.getAduTeacherCountByTimePeriod(
				beginDate, endDate);
	}

	@Override
	public List getLevelAduTeacher(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, int projectCycle, List<ProjectType> lityps, int offset,
			int length) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getLevelAduTeacher(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, projectCycle, lityps, offset, length);
	}

	@Override
	public int getLevelAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, int projectCycle, List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getLevelAduTeacherCount(
				teacherName, projectId, subjectId, trainingUnit, isAdmin,
				status, organization, projectCycle, lityps);
	}
	
	@Override
	public List getLevelAduTeacher(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length,int level, int projectCycle) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getLevelAduTeacher(teacherName,
				projectId, subjectId, trainingUnit, isAdmin, status,
				organization, lityps, offset, length,level, projectCycle);
	}

	@Override
	public int getLevelAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,int level, int projectCycle) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getLevelAduTeacherCount(
				teacherName, projectId, subjectId, trainingUnit, isAdmin,
				status, organization, lityps, level, projectCycle);
	}

	/**
	 * infomation.put("teacher", teacher); infomation.put("project",
	 * projectMap.get(projectName)); infomation.put("trainingSubject",
	 * tsMap.get(subjectName)); infomation.put("trainingCollege",
	 * tcMap.get(trainingCollege)); infomation.put("trainingStatus",
	 * trainingStatus); infomation.put("score", score); infomation.put("reason",
	 * reason); infomation.put("certificate", certificate);
	 * infomation.put("remark", remark);
	 */
	@Override
	public void addBatchTeacherTrainingRecords(UserSession us,
			Organization taddOra, Integer rowCount,
			List<HashMap<String, Object>> infomationList) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (infomationList != null && !infomationList.isEmpty()) {
			for (HashMap<String, Object> info : infomationList) {
				Project project = (Project) info.get("project");
				Teacher teacher = (Teacher) info.get("teacher");
				TrainingSubject trainingSubject = (TrainingSubject) info
						.get("trainingSubject");
				TrainingCollege trainingCollege = (TrainingCollege) info
						.get("trainingCollege");
				String status = info.get("trainingStatus").toString();
				String certificat = info.get("certificate").toString();
				String score = info.get("score").toString();
				String reason = info.get("reason") == null ? "" : info.get(
						"reason").toString();
				String remark = info.get("remark") == null ? "" : info.get(
						"remark").toString();

				Map<String, Object> pagram = new HashMap<String, Object>();
				pagram.put("projectId", project.getId());
				pagram.put("subjectId", (int) trainingSubject.getId());
				pagram.put("trainCollegeId", trainingCollege.getId());

				if (this.getTeacherRecordsCountByTid(teacher.getId(), pagram) == 0) {
					TeacherTrainingRecords ttr = new TeacherTrainingRecords();
					try {
						String vid = Utlity.getUuidPwd();
						ttr.setProject(project);
						ttr.setTrainingSubject(trainingSubject);
						ttr.setTrainingCollege(trainingCollege);

						ttr.setTeacher(teacher);
						ttr.setCreator(us.getId());

						ttr.setOrganization(taddOra);

						ttr.setCheckStatus((short) (us.getOrganizationLevel()));
						ttr.setUuid(vid);
						ttr.setIsPrepare(false);

						// 其他状态
						ttr.setFinalStatus((short) 2);
						ttr.setTrainingStatus(Short.valueOf(status));
						if (status.equals("3") || status.equals("5") || status.equals("6")) {
							ttr.setCertificate(certificat);
							ttr.setTrainingScore(Float.valueOf(score));

						} else {
							ttr.setTrainingReason(reason);
							ttr.setTrainingScore(Float.valueOf(score));
						}
						ttr.setTrainingClasshour(0);
						ttr.setTrainingOnlineHour(0);
						ttr.setRemark1(remark);
						// 重置替换状态
						ttr.setReplaceStatus((short) 0);
						ttr.setReplaceTeacher(0);

						if (project.getEnrollType() == 2) {
							if (project.getTraintype() == 1) {
								ttr.setIsConfirm((short) -1); // 集中培训 默认未确认状态
							} else {
								ttr.setIsConfirm((short) 1);// 非集中培训默认 确认通过状态
							}
						}
						newAddTeacherInfo(ttr);
						ttr.setProjectCycle(project.getProjectCycle());
						this.add(ttr);

						AssignTeacherCheck atc = new AssignTeacherCheck();
						atc.setChecker(us.getId());
						atc.setTeacherTrainingRecords(ttr);
						atc.setType((short) 1);
						AssignTeacherCheck atc1 = new AssignTeacherCheck();
						atc1.setTeacherTrainingRecords(ttr);
						atc1.setType((short) 2);
						atc1.setChecker(us.getId());
						this.assignTeacherCheckDao.add(atc1);
						this.assignTeacherCheckDao.add(atc);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				int percent = (int) Math.ceil(((rowCount + infomationList
						.indexOf(info)) * 100) / (rowCount * 2));
				session.setAttribute("percent", percent);
			}
		}

	}

	public List getAduTeacherListByTimePeriod(Timestamp beginDate,
			Timestamp endDate) {
		return this.teacherTrainingRecordsDao.getAduTeacherListByTimePeriod(
				beginDate, endDate);
	}

	@Override
	public List getTTRecordsByCycle(int teacherId, List<String> cycle) {
		return this.teacherTrainingRecordsDao.getTTRecordsByCycle(teacherId,
				cycle);
	}

	/**
	 * 培训记录表冗余教师信息
	 */
	private void newAddTeacherInfo(TeacherTrainingRecords teacherTrainingRecords) {
		// 通过id，从教师表中获取
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("id", teacherTrainingRecords.getTeacher().getId() + "");
//		List<Teacher> teacherList = teacherDao.getTeacherListByParams(map);
//		Teacher teacher = teacherList.get(0);
		Teacher teacher = teacherTrainingRecords.getTeacher();
		teacherTrainingRecords.setTeacherOrganization(teacher.getOrganization()
				.getId());// 学校
		teacherTrainingRecords.setJobTitle(teacher.getJobTitle());// 职称
		teacherTrainingRecords.setEductionBackground(teacher
				.getEductionBackground());// 学历
		teacherTrainingRecords.setPolitics(teacher.getPolitics());// 政治面貌
		// 通过教师id，从TeachingSubject表中获取
//		Map<String, Object> tsMap = new HashMap<String, Object>();
//		tsMap.put("id", teacher.getId() + "");
//		List<TeachingSubject> teachingSubjectList = teachingSubjectDao
//				.getListByParams(tsMap);
//		for (TeachingSubject teachingSubject : teachingSubjectList) {
//			if (teachingSubject.getIsprime().equals("1")) {
//				teacherTrainingRecords.setSubject(teachingSubject.getSubject());// 主要学科
//			}
//		}
//		// 通过教师id，从TeachingGrade表中获取
//		List<TeachingGrade> teachingGradeList = teachingGradeDao
//				.getListByParams(tsMap);
//		for (TeachingGrade teachingGrade : teachingGradeList) {
//			if (teachingGrade.getIsprime().equals("1")) {
//				teacherTrainingRecords.setGrade(teachingGrade.getGrade());// 主要教学学段
//			}
//		}
//		List<TeachingLanguage> teachingLanguageList = teachingLanguageDao
//				.getListByParams(tsMap);
//		for (TeachingLanguage teachingLanguage : teachingLanguageList) {
//			if (teachingLanguage.getIsprime().equals("1")) {
//				teacherTrainingRecords.setLanguage(teachingLanguage
//						.getLanguage());// 主要教学语言
//			}
//		}
		// 获取主要教学语言
		String hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

		List<TeachingLanguage> lstTeachingLanguages = teachingLanguageDao.getListByHSQL(hqlString);
		if (lstTeachingLanguages.size() > 0) {
			TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
			teacherTrainingRecords.setLanguage(teachingLanguage.getLanguage());// 主要教学语言
		}

		// 获取主要教学学段
		hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
		List<TeachingGrade> lstTeachingGrades = teachingGradeDao.getListByHSQL(hqlString);
		if (lstTeachingGrades.size() > 0) {
			TeachingGrade teachingGrade = lstTeachingGrades.get(0);
			teacherTrainingRecords.setGrade(teachingGrade.getGrade());// 主要教学学段
		}

		// 获取主要教学学科
		hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
		List<TeachingSubject> lsttTeachingSubjects = teachingSubjectDao.getListByHSQL(hqlString);
		if (lsttTeachingSubjects.size() > 0) {
			TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
			teacherTrainingRecords.setSubject(teachingSubject.getSubject());// 主要学科
		}
	}

	@Override
	public List getAduTeacherTimeListByTimePeriod(Timestamp beginDate,
			Timestamp endDate) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacherTimeListByTimePeriod(beginDate, endDate);
	}

	@Override
	public int getAduTeacherCount(int isAdmin, int status,
			Organization organization, int level) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacherCount(isAdmin, status, organization,level);
	}

}
