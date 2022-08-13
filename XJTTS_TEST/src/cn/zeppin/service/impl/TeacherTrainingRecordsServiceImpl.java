package cn.zeppin.service.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.ITeacherTrainingRecordsDao;
import cn.zeppin.dao.impl.TeacherTrainingRecordsDaoImpl;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.utility.DictionyMap;

public class TeacherTrainingRecordsServiceImpl extends BaseServiceImpl<TeacherTrainingRecords, Integer> implements ITeacherTrainingRecordsService {

	private ITeacherTrainingRecordsDao teacherTrainingRecordsDao;

	public ITeacherTrainingRecordsDao getTeacherTrainingRecordsDao() {
		return teacherTrainingRecordsDao;
	}

	public void setTeacherTrainingRecordsDao(ITeacherTrainingRecordsDao teacherTrainingRecordsDao) {
		this.teacherTrainingRecordsDao = teacherTrainingRecordsDao;
	}

	public int getAduTeacherCountByIdcard(int projectId, int subjectId, int trainingCollegeId, int classes, String idcard) {
		return teacherTrainingRecordsDao.getAduTeacherCountByIdcard(projectId, subjectId, trainingCollegeId, classes, idcard);
	}

	public List getAduTeacherByIdcard(int projectId, int subjectId, int trainingCollegeId, int classes, String idcard, int offset, int length) {
		return teacherTrainingRecordsDao.getAduTeacherByIdcard(projectId, subjectId, trainingCollegeId, classes, idcard, offset, length);
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
	public List<TeacherTrainingRecords> getListForPage(String hql, int offset, int length) {

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
	public List getListPage(String sql, int offset, int length, Object[] objParas) {

		return teacherTrainingRecordsDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public int getAduTeacherCount(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps) {
		return this.teacherTrainingRecordsDao.getAduTeacherCount(teacherName, projectId, subjectId, trainingUnit, isAdmin, status, organization, lityps);
	}

	@Override
	public List getAduTeacher(String teacherName, int projectId, int subjectId, int trainingUnit, int isAdmin, int status, Organization organization, List<ProjectType> lityps, int offset, int length) {
		return this.teacherTrainingRecordsDao.getAduTeacher(teacherName, projectId, subjectId, trainingUnit, isAdmin, status, organization, lityps, offset, length);
	}

	public List getAduTeacher(int projectId, int subjectId, int trainingUnit, int status, int offset, int length) {
		return this.teacherTrainingRecordsDao.getAduTeacher(projectId, subjectId, trainingUnit, status, offset, length);
	}

	public List getAduTeacher(int projectId, int subjectId, int trainingUnit, int status, int trainingStatus, int offset, int length) {
		return this.teacherTrainingRecordsDao.getAduTeacher(projectId, subjectId, trainingUnit, status, trainingStatus, offset, length);
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
	public Map<Object, Long> getTrainingRecordsTeacherNumber(int projectId, int subjectId, int collegeId, Organization organization) {

		Map<Object, Long> result = this.getTeacherTrainingRecordsDao().getTrainingRecordsTeacherNumber(projectId, subjectId, collegeId, organization);
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
	public TeacherTrainingRecords getTeacherTrainingRecord(String project, String subject, String trainingCollege, String teacher) {

		return this.getTeacherTrainingRecordsDao().getTeacherTrainingRecord(project, subject, trainingCollege, teacher);
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
	public TeacherTrainingRecords getTeacherTrainingRecordByName(String teacherName, String projectName, String trainingtName, String subjectName) {

		return this.getTeacherTrainingRecordsDao().getTeacherTrainingRecordByName(teacherName, projectName, trainingtName, subjectName);
	}
	
	/**
	 * 根据条件查询教师培训记录
	 * @param teacherName
	 * @param teacherIdCard
	 * @param projectName
	 * @param trainingtName
	 * @param subjectName
	 * @return
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordByIdCard(String teacherName, String teacherIdCard, String projectName, String trainingtName, String subjectName){
		return this.getTeacherTrainingRecordsDao().getTeacherTrainingRecordByIdCard(teacherName, teacherIdCard, projectName, trainingtName, subjectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.ITeacherTrainingRecordsService#getAduTeacherCount(java
	 * .lang.Integer, java.lang.Integer, java.lang.Integer, int)
	 */
	@Override
	public int getAduTeacherCount(Integer projectId, Integer trainingSubjectId, Integer trainingUnitId, int state,int classes) {

		return teacherTrainingRecordsDao.getAduTeacherCount(projectId, trainingSubjectId, trainingUnitId, state,classes);
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
	public List<TeacherTrainingRecords> getListForPage(Integer projectId, Short sbujectId, Integer trainingUnitId, int classes, int start, int limit, String sort) {

		return teacherTrainingRecordsDao.getListForPage(projectId, sbujectId, trainingUnitId, classes, start, limit, sort);
	}

	@Override
	public TeacherTrainingRecords getTeacherTrainingRecordsByUuid(String uuid) {

		return this.getTeacherTrainingRecordsDao().getTeacherTrainingRecordsByUuid(uuid);
	}

	@Override
	public int getAduTeacherCount(HashMap<String, String> map, Organization organization, List<ProjectType> lityps) {

		return this.getTeacherTrainingRecordsDao().getAduTeacherCount(map, organization, lityps);
	}

	@Override
	public List getAduTeacher(HashMap<String, String> map, Organization organization, List<ProjectType> lityps, int offset, int length) {

		return this.getTeacherTrainingRecordsDao().getAduTeacher(map, organization, lityps, offset, length);
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

		return getTeacherTrainingRecordsDao().getListCount(pid, sid, cid, classes);
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
	public int getTeacherTrainingRecordsCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization) {

		return getTeacherTrainingRecordsDao().getTeacherTrainingRecordsCount(projectId, trainingSubjectId, trainingCollegeId, organization);
	}

	@Override
	public int getTeacherTrainingRecordsAduCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization) {

		return getTeacherTrainingRecordsDao().getTeacherTrainingRecordsAduCount(projectId, trainingSubjectId, trainingCollegeId, organization);
	}

	@Override
	public int getTeacherTrainingRecordsRegistCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization) {

		return getTeacherTrainingRecordsDao().getTeacherTrainingRecordsRegistCount(projectId, trainingSubjectId, trainingCollegeId, organization);
	}

	@Override
	public int getTeacherTrainingRecordsFinishCount(Integer projectId, Short trainingSubjectId, Integer trainingCollegeId, Organization organization) {

		return getTeacherTrainingRecordsDao().getTeacherTrainingRecordsFinishCount(projectId, trainingSubjectId, trainingCollegeId, organization);
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
	public List<TeacherTrainingRecords> getTeacherTrainingRecordsByMobile(String mobile) {
		return getTeacherTrainingRecordsDao().getTeacherTrainingRecordsByMobile(mobile);
	}

	/**
	 * 获取教师培训记录
	 * 
	 * @param trainingCollegeId
	 * @param idCard
	 * @return
	 */
	public TeacherTrainingRecords getTeacherTrainingRecordsByIdCard(int trainingCollegeId, String idCard) {

		return this.getTeacherTrainingRecordsDao().getTeacherTrainingRecordsByIdCard(trainingCollegeId, idCard);
	}

	@Override
	public List getListForPage(Integer projectId,
			Short sbujectId, Integer trainingUnitId, String idcard,
			String name, int classes, int start, int limit, String sort) {
		// TODO Auto-generated method stub
		return teacherTrainingRecordsDao.getListForPage(projectId, sbujectId, trainingUnitId, idcard, name, classes, start, limit, sort);
	}

	@Override
	public int getListTeacherCount(int projectId, int subjectId,
			int trainingCollegeId, int classes, String idcard, String name) {
		// TODO Auto-generated method stub
		return teacherTrainingRecordsDao.getListTeacherCount(projectId, subjectId, trainingCollegeId, classes, idcard, name);
	}

	@Override
	public int getAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,
			String enrollType) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacherCount(teacherName, projectId, subjectId, trainingUnit, isAdmin, status, organization, lityps, enrollType);
	}

	@Override
	public List getAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length, String enrollType) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacher(teacherName, projectId, subjectId, trainingUnit, isAdmin, status, organization, lityps, offset, length, enrollType);
	}

	@Override
	public int getAduTeacherCount(String teacherName, Integer projectId,
			Integer trainingSubjectId, Integer trainingUnitId, int status) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getAduTeacherCount(teacherName, projectId, trainingSubjectId, trainingUnitId, status);
	}

	@Override
	public List getListTeacherCount(String teacherName, Integer projectId,
			Integer trainingSubjectId, Integer trainingUnitId, int status,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getListTeacherCount(teacherName, projectId, trainingSubjectId, trainingUnitId, status, offset, length);
	}

	@Override
	public int getTeacherRecordsCountByTid(int teacherId, Map<String, Object> pagram) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsCountByTid(teacherId, pagram);
	}

	@Override
	public List getTeacherRecordsByTid(int teacherId, int offset, int length, Map<String, String> sortParams, Map<String, Object> pagram) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsByTid(teacherId, offset, length, sortParams, pagram);
	}

	@Override
	public List getTeacherRecordsForSubject(int offset, int length,
			 Map<String, Object> sortParams,
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsForSubject(offset, length, sortParams, param);
	}

	@Override
	public int getTeacherRecordsForSubject(Map<String, Object> sortParams,
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.teacherTrainingRecordsDao.getTeacherRecordsForSubject(sortParams, param);
	}

}
