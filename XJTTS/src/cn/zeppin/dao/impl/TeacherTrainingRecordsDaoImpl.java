package cn.zeppin.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.zeppin.dao.ITeacherTrainingRecordsDao;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.utility.DictionyMap;

public class TeacherTrainingRecordsDaoImpl extends
		BaseDaoImpl<TeacherTrainingRecords, Integer> implements
		ITeacherTrainingRecordsDao {

	/**
	 * @param projectId
	 * @param subjectId
	 * @param collegeId
	 * @param tlstOrg
	 * @return 报送教师数量
	 */
	public Map<Object, Long> getTrainingRecordsTeacherNumber(int projectId,
			int subjectId, int collegeId, Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select r.finalStatus, count(*) from TeacherTrainingRecords r, Teacher t, Organization o where r.teacher=t.id ");
		sb.append(" and t.organization = o.id ");
		if (projectId > 0) {
			sb.append(" and r.project=");
			sb.append(projectId);
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=");
			sb.append(subjectId);
		}
		if (collegeId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(collegeId);
		}
		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");
		}

		sb.append(" group by r.finalStatus");

		long checkPassNum = 0;
		long checkNoPassNum = 0;
		long uncheckNum = 0;
		@SuppressWarnings("rawtypes")
		List lst = this.getListByHSQL(sb.toString());
		for (int i = 0; i < lst.size(); i++) {
			Object[] obj = (Object[]) lst.get(i);
			short finalStatus = (short) obj[0];
			long at = (long) obj[1];
			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
				checkPassNum = checkPassNum + at;
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
				checkNoPassNum = checkNoPassNum + at;
			} else { // if (finalStatus == TEACHER_TRAINING_RECORDS_UNCHECK)
				uncheckNum = uncheckNum + at;
			}
		}
		Map<Object, Long> result = new HashMap<>();
		result.put(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS, checkPassNum);
		result.put(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS,
				checkNoPassNum);
		result.put(DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK, uncheckNum);
		return result;
	}

	public void updateClasses(String classes, String ids) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE teacher_training_records SET classes=");
		sb.append(classes);
		sb.append(" WHERE id in (");
		sb.append(ids);
		sb.append(")");
		this.executeSQLUpdate(sb.toString(), null);
	}

	@Override
	public int getAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps) {

		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id");
		sb.append(" and t.organization = o.id and r.project = p.id ");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (isAdmin == 0) {
			if (status > -1) {
				sb.append(" and r.finalStatus=" + status);
			}
		} else {
			if (isAdmin == 1) {
				// 没有审核
				sb.append(" and r.checkStatus>=" + status);
			} else if (isAdmin == 2) {
				sb.append(" and r.checkStatus<" + status);
			}
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");
		}
		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (").append(organizations).append(")");
		// }

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public TeacherTrainingRecords getTeacherTrainingRecordByName(
			String teacherName, String projectName, String trainingtName,
			String subjectName) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"select ttr from TeacherTrainingRecords ttr, Project p, Teacher t, TrainingSubject ts,TrainingCollege tc")
				.append(" where ttr.teacher=t.id and ttr.project=p.id and ttr.trainingSubject=ts.id and ttr.trainingCollege=tc.id");
		sb.append(" and t.name='");
		sb.append(teacherName);
		sb.append("' and p.name='");
		sb.append(projectName);
		sb.append("' and tc.name='");
		sb.append(trainingtName);
		sb.append("' and ts.name='");
		sb.append(subjectName);
		sb.append("'");

		TeacherTrainingRecords ttr = (TeacherTrainingRecords) this
				.getObjectByHql(sb.toString(), null);
		return ttr;
	}

	public TeacherTrainingRecords getTeacherTrainingRecordByIdCard(
			String teacherName, String teacherIdCard, String projectName,
			String trainingtName, String subjectName) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"select ttr from TeacherTrainingRecords ttr, Project p, Teacher t, TrainingSubject ts,TrainingCollege tc")
				.append(" where ttr.teacher=t.id and ttr.project=p.id and ttr.trainingSubject=ts.id and ttr.trainingCollege=tc.id and ttr.finalStatus=2");
		sb.append(" and t.name='");
		sb.append(teacherName);
		sb.append("' and t.idcard='");
		sb.append(teacherIdCard);
		sb.append("' and p.name='");
		sb.append(projectName);
		sb.append("' and tc.name='");
		sb.append(trainingtName);
		sb.append("' and ts.name='");
		sb.append(subjectName);
		sb.append("'");

		TeacherTrainingRecords ttr = (TeacherTrainingRecords) this
				.getObjectByHql(sb.toString(), null);
		return ttr;
	}

	public int getAduTeacherCountByIdcard(int projectId, int subjectId,
			int trainingCollegeId, int classes, String idcard) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r ,Teacher t where r.teacher=t.id");

		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingCollegeId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingCollegeId);
		}
		if (classes > 0) {
			sb.append(" and r.classes=");
			sb.append(classes);
		}
		sb.append(" and r.finalStatus = 2 ");
		if (idcard != null && !idcard.equals("")) {
			sb.append(" and t.idcard like '%");
			sb.append(idcard);
			sb.append("%'");
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;

	}

	public List getAduTeacherByIdcard(int projectId, int subjectId,
			int trainingCollegeId, int classes, String idcard, int offset,
			int length) {
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t where r.teacher=t.id ");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingCollegeId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingCollegeId);
		}
		if (classes > 0) {
			sb.append(" and r.classes=");
			sb.append(classes);
		}
		sb.append(" and r.finalStatus = 2 ");
		if (idcard != null && !idcard.equals("")) {
			sb.append(" and t.idcard like '%");
			sb.append(idcard);
			sb.append("%'");
		}
		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public List getAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id ");
		sb.append(" and t.organization = o.id and r.project=p.id");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}
		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");
		}
		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (" + organizations + ")");
		// }
		if (isAdmin == 0) {
			if (status > -1) {
				sb.append(" and r.finalStatus=" + status);
			}
		} else {
			if (isAdmin == 1) {
				// 没有审核
				sb.append(" and r.checkStatus>=" + status);

			} else if (isAdmin == 2) {
				sb.append(" and r.checkStatus<" + status);
			}

			sb.append(" and r.finalStatus=1 ");
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}

	public List getAduTeacher(int projectId, int subjectId, int trainingUnit,
			int status, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t, Organization o where r.teacher=t.id ");
		sb.append(" and t.organization = o.id ");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}
		sb.append(" and r.finalStatus=" + status);
		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}

	public List getAduTeacher(int projectId, int subjectId, int trainingUnit,
			int status, int trainingStatus, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t, Organization o where r.teacher=t.id ");
		sb.append(" and t.organization = o.id ");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}
		sb.append(" and r.finalStatus=" + status);
		if (trainingStatus > -1) {
			if (trainingStatus == 2) {
				sb.append(" and r.trainingStatus in (2,3,4,5,6)");
			} else {
				sb.append(" and r.trainingStatus=" + trainingStatus);

			}

		}
		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
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
	public TeacherTrainingRecords getTeacherTrainingRecord(String project,
			String subject, String trainingCollege, String teacher) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from TeacherTrainingRecords where finalStatus != -1 and project=")
				.append(project);
		sb.append(" and trainingSubject=").append(subject);
		sb.append(" and trainingCollege=").append(trainingCollege);
		sb.append(" and teacher=").append(teacher);
		TeacherTrainingRecords ttr = (TeacherTrainingRecords) this
				.getObjectByHql(sb.toString(), null);
		return ttr;
	}

	/**
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingUnitId
	 * @param state
	 * @return
	 */
	public int getAduTeacherCount(Integer projectId, Integer trainingSubjectId,
			Integer trainingUnitId, int state, int classes) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r where 1=1");

		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (trainingSubjectId > 0) {
			sb.append(" and r.trainingSubject=" + trainingSubjectId);
		}
		if (trainingUnitId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnitId);
		}

		if (classes > 0) {
			sb.append(" and r.classes=");
			sb.append(classes);
		}

		sb.append(" and r.finalStatus = 2 ");

		if (state > -1) {
			if (state == 2) {
				sb.append(" and r.trainingStatus in (2,3,4,5,6)");
			} else {
				sb.append(" and r.trainingStatus=" + state);

			}

		}

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;

	}

	/**
	 * @param projectId
	 * @param sbujectId
	 * @param trainingUnitId
	 * @param start
	 * @param limit
	 * @param sort
	 * @return
	 */
	public List<TeacherTrainingRecords> getListForPage(Integer projectId,
			Short sbujectId, Integer trainingUnitId, int classes, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r where finalStatus=2");

		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (sbujectId > 0) {
			sb.append(" and r.trainingSubject=" + sbujectId);
		}
		if (classes > 0) {
			sb.append(" and r.classes=" + classes);
		}
		if (trainingUnitId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnitId);
		}

		sb.append(" order by " + sort);

		return this.getListForPage(sb.toString(), start, limit, null);

	}

	@Override
	public TeacherTrainingRecords getTeacherTrainingRecordsByUuid(String uuid) {
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r where 1=1");
		sb.append(" and r.uuid='" + uuid + "'");

		Object obj = this.getObjectByHql(sb.toString(), null);

		if (obj != null) {
			TeacherTrainingRecords ttr = (TeacherTrainingRecords) obj;
			return ttr;
		} else {
			return null;
		}
	}

	/**
	 * 获取培训人员个数
	 */
	@Override
	public int getAduTeacherCount(HashMap<String, String> map,
			Organization organization, List<ProjectType> lityps) {

		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id");
		sb.append(" and t.organization = o.id and r.project=p.id ");

		if (map.containsKey("projectCycle") && map.get("projectCycle") != null
				&& !map.get("projectCycle").equals("0")) {
			sb.append(" and r.projectCycle=" + map.get("projectCycle"));
		}
		if(map.containsKey("projectlevel")) {
			sb.append(" and p.projectType.projectLevel in("+map.get("projectlevel")+")");
		}
		if (map.containsKey("pid") && map.get("pid") != null
				&& !map.get("pid").equals("0")) {
			sb.append(" and r.project=" + map.get("pid"));
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}

		if (map.containsKey("sid") && map.get("sid") != null
				&& !map.get("sid").equals("0")) {
			sb.append(" and r.trainingSubject=" + map.get("sid"));
		}

		if (map.containsKey("tid") && map.get("tid") != null
				&& !map.get("tid").equals("0")) {
			sb.append(" and r.trainingCollege=" + map.get("tid"));
		}

		if (map.containsKey("status") && map.get("status") != null
				&& !map.get("status").equals("")) {
			sb.append(" and r.finalStatus=" + map.get("status"));
		}

		if (map.containsKey("name") && map.get("name") != null
				&& !map.get("name").equals("")) {
			sb.append(" and (t.name like'%" + map.get("name")
					+ "%' or t.idcard like'%" + map.get("name") + "%')");
		}

		if (map.containsKey("trainingStatus")
				&& map.get("trainingStatus") != null
				&& !map.get("trainingStatus").equals("")
				&& !map.get("trainingStatus").equals("-1")) {
			if("3".equals(map.get("trainingStatus"))) {
				sb.append(" and r.trainingStatus in (3,5,6)");
			} else {
				sb.append(" and r.trainingStatus=" + map.get("trainingStatus"));
			}
			
		}

		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;

	}

	@Override
	public List getAduTeacher(HashMap<String, String> map,
			Organization organization, List<ProjectType> lityps, int offset,
			int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("select r from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id");
		sb.append(" and t.organization = o.id and r.project=p.id ");

		if (map.containsKey("projectCycle") && map.get("projectCycle") != null
				&& !map.get("projectCycle").equals("0")) {
			sb.append(" and r.projectCycle=" + map.get("projectCycle"));
		}
		
		if(map.containsKey("projectlevel")) {
			sb.append(" and p.projectType.projectLevel in("+map.get("projectlevel")+")");
		}
		
		if (map.containsKey("pid") && map.get("pid") != null
				&& !map.get("pid").equals("0")) {
			sb.append(" and r.project=" + map.get("pid"));
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}

		if (map.containsKey("sid") && map.get("sid") != null
				&& !map.get("sid").equals("0")) {
			sb.append(" and r.trainingSubject=" + map.get("sid"));
		}

		if (map.containsKey("tid") && map.get("tid") != null
				&& !map.get("tid").equals("0")) {
			sb.append(" and r.trainingCollege=" + map.get("tid"));
		}

		if (map.containsKey("status") && map.get("status") != null
				&& !map.get("status").equals("")) {
			sb.append(" and r.finalStatus=" + map.get("status"));
		}

		if (map.containsKey("name") && map.get("name") != null
				&& !map.get("name").equals("")) {
			sb.append(" and (t.name like'%" + map.get("name")
					+ "%' or t.idcard like'%" + map.get("name") + "%')");
		}

		if (map.containsKey("trainingStatus")
				&& map.get("trainingStatus") != null
				&& !map.get("trainingStatus").equals("")
				&& !map.get("trainingStatus").equals("-1")) {
			if("3".equals(map.get("trainingStatus"))) {
				sb.append(" and r.trainingStatus in (3,5,6)");
			} else {
				sb.append(" and r.trainingStatus=" + map.get("trainingStatus"));
			}
		}

		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");
		}
		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}

	/**
	 * @param pid
	 * @param sid
	 * @param cid
	 * @return
	 */
	public int getListCount(Integer pid, Short sid, Integer cid, int classes) {
		// TODO Auto-generated method stub
		String hqlString = "select count(*) from TeacherTrainingRecords where finalStatus=2 and project="
				+ pid
				+ " and trainingSubject="
				+ sid
				+ " and trainingCollege="
				+ cid;
		if (classes > 0) {
			hqlString = hqlString + " and classes=" + classes;
		}
		int count = Integer
				.parseInt(getObjectByHql(hqlString, null).toString());
		return count;
	}

	/**
	 * 按项目、学科、承训单位统计报送学员记录
	 * 
	 * @param projectId
	 * @param organization
	 * @return List
	 */
	public int getTeacherTrainingRecordsCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) ");
		sb.append(" from teacher_training_records ttr, teacher t, organization o ");
		sb.append(" where ttr.teacher=t.id and t.organization=o.id ");
		sb.append(" and ttr.project=").append(projectId);
		sb.append(" and ttr.subject=").append(trainingSubjectId);
		sb.append(" and ttr.training_college=").append(trainingCollegeId);
		sb.append(" and ttr.final_status = 2 ");
		sb.append(" and o.scode like '").append(organization.getScode())
				.append("%' ");
		// if (lstSubSchool != null && lstSubSchool.size()>0) {
		// sb.append(" and t.organization in (");
		// String comba = "";
		// for (Organization org : lstSubSchool){
		// sb.append(comba);
		// sb.append(org.getId());
		// comba = ",";
		// }
		// sb.append(")");
		// }
		return Integer.valueOf(this.getObjectBySql(sb.toString(), null)
				.toString());
	}

	public int getTeacherTrainingRecordsAduCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) ");
		sb.append(" from teacher_training_records ttr, teacher t, organization o ");
		sb.append(" where ttr.teacher=t.id and t.organization=o.id ");
		sb.append(" and ttr.project=").append(projectId);
		sb.append(" and ttr.subject=").append(trainingSubjectId);
		sb.append(" and ttr.training_college=").append(trainingCollegeId);
		sb.append(" and ttr.final_status = 2 ");
		sb.append(" and o.scode like '").append(organization.getScode())
				.append("%' ");
		return Integer.valueOf(this.getObjectBySql(sb.toString(), null)
				.toString());
	}

	public int getTeacherTrainingRecordsRegistCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) ");
		sb.append(" from teacher_training_records ttr, teacher t, organization o ");
		sb.append(" where ttr.teacher=t.id and t.organization=o.id ");
		sb.append(" and ttr.project=").append(projectId);
		sb.append(" and ttr.subject=").append(trainingSubjectId);
		sb.append(" and ttr.training_college=").append(trainingCollegeId);
		sb.append(" and ttr.final_status = 2 ");
		sb.append(" and ttr.training_status > 1 ");
		sb.append(" and o.scode like '").append(organization.getScode())
				.append("%' ");
		return Integer.valueOf(this.getObjectBySql(sb.toString(), null)
				.toString());
	}

	public int getTeacherTrainingRecordsFinishCount(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Organization organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) ");
		sb.append(" from teacher_training_records ttr, teacher t, organization o ");
		sb.append(" where ttr.teacher=t.id and t.organization=o.id ");
		sb.append(" and ttr.project=").append(projectId);
		sb.append(" and ttr.subject=").append(trainingSubjectId);
		sb.append(" and ttr.training_college=").append(trainingCollegeId);
		sb.append(" and ttr.final_status = 0 ");
		sb.append(" and ttr.training_status = 2 ");
		sb.append(" and o.scode like '").append(organization.getScode())
				.append("%' ");
		return Integer.valueOf(this.getObjectBySql(sb.toString(), null)
				.toString());
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

		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherTrainingRecords t where 1=1 and t.finalStatus=2  ");
		sb.append(" and t.teacher.mobile='").append(mobile).append("' ");

		return this.getListByHSQL(sb.toString());

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
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherTrainingRecords t where 1=1 and t.finalStatus=2  ");
		sb.append(" and t.teacher.idcard='").append(idCard).append("' ");
		sb.append(" and t.trainingCollege=").append(trainingCollegeId);

		List<TeacherTrainingRecords> lstRecords = this.getListByHSQL(sb
				.toString());

		if (lstRecords != null && lstRecords.size() > 0) {
			return lstRecords.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List getListForPage(Integer projectId, Short sbujectId,
			Integer trainingUnitId, String idcard, String name, int classes,
			int start, int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t where r.teacher=t.id ");

		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (sbujectId > 0) {
			sb.append(" and r.trainingSubject=" + sbujectId);
		}
		if (classes > 0) {
			sb.append(" and r.classes=" + classes);
		}
		if (trainingUnitId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnitId);
		}
		sb.append(" and r.finalStatus = 2 ");
		if (idcard != null && !idcard.equals("")) {
			sb.append(" and t.idcard like '%");
			sb.append(idcard);
			sb.append("%'");
		}
		if (name != null && !name.equals("")) {
			sb.append(" and t.name like '%");
			sb.append(name);
			sb.append("%'");
		}

		sb.append(" order by r." + sort);

		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListTeacherCount(int projectId, int subjectId,
			int trainingCollegeId, int classes, String idcard, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r ,Teacher t where r.teacher=t.id");

		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingCollegeId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingCollegeId);
		}
		if (classes > 0) {
			sb.append(" and r.classes=");
			sb.append(classes);
		}
		sb.append(" and r.finalStatus = 2 ");
		if (idcard != null && !idcard.equals("")) {
			sb.append(" and t.idcard like '%");
			sb.append(idcard);
			sb.append("%'");
		}
		if (name != null && !name.equals("")) {
			sb.append(" and t.idcard like '%");
			sb.append(name);
			sb.append("%'");
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public int getAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,
			String enrollType, int projectCycle, int teacherOrg) {
		StringBuilder sb = new StringBuilder();
//		sb.append(" select count(*) from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id");
//		sb.append(" and t.organization = o.id and r.project = p.id ");
		sb.append(" select count(*) from TeacherTrainingRecords r, Project p where 1=1");
		sb.append(" and r.project=p.id");
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		//20180702增加限制
		sb.append(" and p.projectType.projectLevel in (1,2)");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" r.project.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and r.project.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and r.project.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and r.project.status=2");
		} else if (projectId == 0) {
			sb.append(" and r.project.status=2");
		}

		if (teacherOrg > 0) {
			sb.append(" and r.teacher.organization.id=");
			sb.append(teacherOrg);
		}
		
		if (organization != null) {
			sb.append(" and r.teacher.organization.scode like '").append(organization.getScode())
					.append("%'");
		}
		
		if (enrollType != null) {
			sb.append(" and r.project.enrollType=");
			sb.append(enrollType);

			if (enrollType.equals("1")) {

				if (isAdmin == 0) {
					if (status > -1) {
						if(status == 3){
							sb.append(" and r.finalStatus=-1");
						}else{
							sb.append(" and r.finalStatus=" + status);
						}
						
					}
				} else if (isAdmin == 1) {

					sb.append(" and r.checkStatus>=" + status);
					sb.append(" and r.finalStatus=1");
				} else {

					sb.append(" and r.checkStatus<" + status);

				}
			} else if (enrollType.equals("2")) {
				if (status == 0) {// 已审核未通过

					sb.append(" and r.finalStatus=0");

				} else if (status == -1) {// 未审核

					sb.append(" and r.finalStatus=1");
					// sb.append(" and r.checkStatus=" + status);

				} else if (status > 0) {// 已审核 已通过

					if(status == 3){
						sb.append(" and r.finalStatus=-1");
					}else{
						sb.append(" and r.finalStatus=2");
					}
					
				}
				// sb.append(" and r.checkStatus>" +status);
			}
		} else {
			// if (isAdmin == 0) {
			// if (status > -1) {
			// sb.append(" and r.finalStatus=" + status);
			// }
			// } else if(isAdmin == 1) {
			//
			// sb.append(" and r.checkStatus>=" + status);
			//
			// } else {
			//
			// sb.append(" and r.checkStatus<" + status);
			//
			// }
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
			
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (r.teacher.name like'%" + teacherName
					+ "%' or r.teacher.idcard like'%" + teacherName + "%') ");
		}

		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (").append(organizations).append(")");
		// }

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public List getAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length, String enrollType, int projectCycle, int teacherOrg) {
		StringBuilder sb = new StringBuilder();
		sb.append("select r from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id ");
		sb.append(" and t.organization = o.id and r.project=p.id");
		
		//20190731增加周期筛选
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		//20180702增加限制
		sb.append(" and p.projectType.projectLevel in (1,2)");
		
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0) {
			sb.append(" and p.status=2");
		}

		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (teacherOrg > 0) {
			sb.append(" and o.id=");
			sb.append(teacherOrg);
		}
		
		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");
		}
		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (" + organizations + ")");
		// }
		if (enrollType != null) {
			sb.append(" and p.enrollType=");
			sb.append(enrollType);

			if (enrollType.equals("1")) {

				if (isAdmin == 0) {
					if (status > -1) {
//						sb.append(" and r.finalStatus=" + status);
						if(status == 3){
							sb.append(" and r.finalStatus=-1");
						}else{
							sb.append(" and r.finalStatus=" + status);
						}
					}
				} else if (isAdmin == 1) {

					sb.append(" and r.checkStatus>=" + status);
					sb.append(" and r.finalStatus=1");
				} else {

					sb.append(" and r.checkStatus<" + status);

				}
			} else if (enrollType.equals("2")) {
				if (status == 0) {// 已审核未通过

					sb.append(" and r.finalStatus=0");

				} else if (status == -1) {// 未审核

					sb.append(" and r.finalStatus=1");
					// sb.append(" and r.checkStatus=" + status);

				} else if (status > 0) {// 已审核 已通过

					if(status == 3){
						sb.append(" and r.finalStatus=-1");
					}else{
						sb.append(" and r.finalStatus=2");
					}
					
				}
				// sb.append(" and r.checkStatus>" +status);
			}
		} else {
			// if (isAdmin == 0) {
			// if (status > -1) {
			// sb.append(" and r.finalStatus=" + status);
			// }
			// } else if(isAdmin == 1) {
			//
			// sb.append(" and r.checkStatus>=" + status);
			//
			// } else {
			//
			// sb.append(" and r.checkStatus<" + status);
			//
			// }
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
			
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getTrainAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,
			String enrollType, int projectCycle, int teacherOrg) {
		StringBuilder sb = new StringBuilder();
//		sb.append(" select count(*) from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id");
//		sb.append(" and t.organization = o.id and r.project = p.id ");
		sb.append(" select count(*) from TeacherTrainingRecords r, Project p where 1=1");
		sb.append(" and r.project=p.id");
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" r.project.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and r.project.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and r.project.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and r.project.status=2");
		} else if (projectId == 0) {
			sb.append(" and r.project.status=2");
		}

		if (teacherOrg > 0) {
			sb.append(" and r.teacher.organization.id=");
			sb.append(teacherOrg);
		}
		
		if (organization != null) {
			sb.append(" and r.teacher.organization.scode like '").append(organization.getScode())
					.append("%'");
		}
		
		if (enrollType != null) {
			sb.append(" and r.project.enrollType=");
			sb.append(enrollType);

			if (enrollType.equals("1")) {

				if (isAdmin == 0) {
					if (status > -1) {
						if(status == 3){
							sb.append(" and r.finalStatus=-1");
						}else{
							sb.append(" and r.finalStatus=" + status);
						}
						
					}
				} else if (isAdmin == 1) {

					sb.append(" and r.checkStatus>=" + status);
					sb.append(" and r.finalStatus=1");
				} else {

					sb.append(" and r.checkStatus<" + status);

				}
			} else if (enrollType.equals("2")) {
				if (status == 0) {// 已审核未通过

					sb.append(" and r.finalStatus=0");

				} else if (status == -1) {// 未审核

					sb.append(" and r.finalStatus=1");
					// sb.append(" and r.checkStatus=" + status);

				} else if (status > 0) {// 已审核 已通过

					if(status == 3){
						sb.append(" and r.finalStatus=-1");
					}else{
						sb.append(" and r.finalStatus=2");
					}
					
				}
				// sb.append(" and r.checkStatus>" +status);
			}
		} else {
			// if (isAdmin == 0) {
			// if (status > -1) {
			// sb.append(" and r.finalStatus=" + status);
			// }
			// } else if(isAdmin == 1) {
			//
			// sb.append(" and r.checkStatus>=" + status);
			//
			// } else {
			//
			// sb.append(" and r.checkStatus<" + status);
			//
			// }
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
			
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (r.teacher.name like'%" + teacherName
					+ "%' or r.teacher.idcard like'%" + teacherName + "%') ");
		}

		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (").append(organizations).append(")");
		// }

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public List getTrainAduTeacher(String teacherName, int projectId, int subjectId,
			int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length, String enrollType, int projectCycle, int teacherOrg) {
		StringBuilder sb = new StringBuilder();
		sb.append("select r from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id ");
		sb.append(" and t.organization = o.id and r.project=p.id");
		
		//20190731增加周期筛选
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0) {
			sb.append(" and p.status=2");
		}

		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (teacherOrg > 0) {
			sb.append(" and o.id=");
			sb.append(teacherOrg);
		}
		
		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");
		}
		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (" + organizations + ")");
		// }
		if (enrollType != null) {
			sb.append(" and p.enrollType=");
			sb.append(enrollType);

			if (enrollType.equals("1")) {

				if (isAdmin == 0) {
					if (status > -1) {
//						sb.append(" and r.finalStatus=" + status);
						if(status == 3){
							sb.append(" and r.finalStatus=-1");
						}else{
							sb.append(" and r.finalStatus=" + status);
						}
					}
				} else if (isAdmin == 1) {

					sb.append(" and r.checkStatus>=" + status);
					sb.append(" and r.finalStatus=1");
				} else {

					sb.append(" and r.checkStatus<" + status);

				}
			} else if (enrollType.equals("2")) {
				if (status == 0) {// 已审核未通过

					sb.append(" and r.finalStatus=0");

				} else if (status == -1) {// 未审核

					sb.append(" and r.finalStatus=1");
					// sb.append(" and r.checkStatus=" + status);

				} else if (status > 0) {// 已审核 已通过

					if(status == 3){
						sb.append(" and r.finalStatus=-1");
					}else{
						sb.append(" and r.finalStatus=2");
					}
					
				}
				// sb.append(" and r.checkStatus>" +status);
			}
		} else {
			// if (isAdmin == 0) {
			// if (status > -1) {
			// sb.append(" and r.finalStatus=" + status);
			// }
			// } else if(isAdmin == 1) {
			//
			// sb.append(" and r.checkStatus>=" + status);
			//
			// } else {
			//
			// sb.append(" and r.checkStatus<" + status);
			//
			// }
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
			
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}

	public int getAduTeacherCount(String teacherName, Integer projectId,
			Integer trainingSubjectId, Integer trainingUnitId, int status) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r,Project p,Teacher t, Organization o where 1=1 and r.project=p.id and r.teacher=t.id ");
		sb.append(" and t.organization = o.id");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (trainingSubjectId > 0) {
			sb.append(" and r.trainingSubject=" + trainingSubjectId);
		}
		if (trainingUnitId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnitId);
		}

		// sb.append(" and r.finalStatus = 2");
		// sb.append(" and r.finalStatus = 2 and p.enrollType=2 and p.traintype=1");
		sb.append(" and p.enrollType=2 and p.traintype in(1,3)");

		if (status == 1) {// 未确认
			sb.append(" and r.isConfirm=-1");
			sb.append(" and r.finalStatus = 2");
		} else if (status == -2) {// 确认通过
			sb.append(" and r.isConfirm=1");
			sb.append(" and r.finalStatus = 2");
		} else if (status == 0) {// 确认未通过
			sb.append(" and r.isConfirm=0");
			sb.append(" and r.finalStatus = 0");
		} else {
			sb.append(" and ((r.isConfirm in(-1,1) and r.finalStatus =2) or (r.isConfirm=0 and r.finalStatus=0)) ");
		}

		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;

	}

	@Override
	public List getListTeacherCount(String teacherName, Integer projectId,
			Integer trainingSubjectId, Integer trainingUnitId, int status,
			int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t, Organization o,Project p where r.teacher=t.id ");
		sb.append(" and t.organization = o.id and r.project=p.id");
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		}
		if (trainingSubjectId > 0) {
			sb.append(" and r.trainingSubject=" + trainingSubjectId);
		}
		if (trainingUnitId > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnitId);
		}

		sb.append(" and p.enrollType=2 and p.traintype in(1,3)");

		if (status == 1) {// 未确认
			sb.append(" and r.isConfirm=-1");
			sb.append(" and r.finalStatus = 2");
		} else if (status == -2) {// 确认通过
			sb.append(" and r.isConfirm=1");
			sb.append(" and r.finalStatus = 2");
		} else if (status == 0) {// 确认未通过
			sb.append(" and r.isConfirm=0");
			sb.append(" and r.finalStatus = 0");
		} else {
			sb.append(" and ((r.isConfirm in(-1,1) and r.finalStatus =2) or (r.isConfirm=0 and r.finalStatus=0)) ");
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getTeacherRecordsCountByTid(int teacherId,
			Map<String, Object> pagram) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		// sb.append(" select count(*) from TeacherTrainingRecords r,Project p where 1=1 and r.project=p.id and p.enrollType=2");
		sb.append(" select count(*) from TeacherTrainingRecords r,Project p where 1=1 and r.project=p.id");
		if (teacherId > 0) {
			sb.append(" and r.teacher=" + teacherId);
		}

		// 其他筛选参数
		if (pagram != null && pagram.size() > 0) {

			if (pagram.containsKey("projectId")) {
				int projectId = (int) pagram.get("projectId");
				sb.append(" and r.project=" + projectId);
			}

			if (pagram.containsKey("subjectId")) {
				int subjectId = (int) pagram.get("subjectId");
				sb.append(" and r.trainingSubject=" + subjectId);
			}

			if (pagram.containsKey("trainCollegeId")) {
				int trainingCollege = (int) pagram.get("trainCollegeId");
				sb.append(" and r.trainingCollege=" + trainingCollege);
			}

			if (pagram.containsKey("trainType")) {
				int traintype = (int) pagram.get("trainType");
				sb.append(" and p.traintype=" + traintype);
			}

			// 是否已结束
			if (pagram.containsKey("isStop")) {
				int isStop = (int) pagram.get("isStop");
				if (isStop == 1) {
					sb.append(" and r.trainingStatus>2");
				}
			}
			if (pagram.containsKey("finalStatus")) {
				int finalStatus = (int) pagram.get("finalStatus");
				sb.append(" and r.finalStatus=" + finalStatus);
			}

			if (pagram.containsKey("otherStatus")) {

				sb.append(" and r.finalStatus in (0,1,2)");
			}

		}

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();

		return result;
	}

	@Override
	public List getTeacherRecordsByTid(int teacherId, int offset, int length,
			Map<String, String> sortParams, Map<String, Object> pagram) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		// sb.append(" from TeacherTrainingRecords r, Project p, ProjectApply pa where 1=1 and r.project = p.id and pa.project=p.id and r.trainingSubject=pa.trainingSubject and p.enrollType=2");
		sb.append(" from TeacherTrainingRecords r, Project p, ProjectApply pa where 1=1 and r.project = p.id and pa.project=p.id and r.trainingSubject=pa.trainingSubject and r.trainingCollege=pa.trainingCollege ");
		sb.append(" and r.project=pa.project ");
		if (teacherId > 0) {
			sb.append(" and r.teacher=" + teacherId);
		}

		// 其他筛选参数
		if (pagram != null && pagram.size() > 0) {

			if (pagram.containsKey("projectId")) {
				int projectId = (int) pagram.get("projectId");
				sb.append(" and r.project=" + projectId);
			}

			if (pagram.containsKey("subjectId")) {
				int subjectId = (int) pagram.get("subjectId");
				sb.append(" and r.trainingSubject=" + subjectId);
			}

			if (pagram.containsKey("trainCollegeId")) {
				int trainingCollege = (int) pagram.get("trainCollegeId");
				sb.append(" and r.trainingCollege=" + trainingCollege);
			}

			if (pagram.containsKey("trainType")) {
				int traintype = (int) pagram.get("trainType");
				sb.append(" and p.traintype=" + traintype);
			}
			// 是否已结束
			if (pagram.containsKey("isStop")) {
				int isStop = (int) pagram.get("isStop");
				if (isStop == 1) {
					sb.append(" and r.trainingStatus>2");
				}
			}
			if (pagram.containsKey("finalStatus")) {
				int finalStatus = (int) pagram.get("finalStatus");
				sb.append(" and r.finalStatus=" + finalStatus);
			}

			if (pagram.containsKey("otherStatus")) {

				sb.append(" and r.finalStatus in (0,1,2) ");
			}
		}

		// 排序参数
		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("r." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public List getTeacherRecordsForSubject(int offset, int length,
			Map<String, Object> sortParams, Map<String, Object> param) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherTrainingRecords r, Project p, Teacher t where 1=1 and r.project = p.id and r.teacher = t.id ");
		sb.append(" and r.finalStatus=2 ");
		if (param != null && param.size() > 0) {
			if (param.containsKey("projectId")
					&& param.get("projectId") != null
					&& !"".equals(param.get("projectId"))) {
				String projectId = param.get("projectId").toString();
				sb.append(" and r.project=" + projectId);
			}

			if (param.containsKey("subjectId")
					&& param.get("subjectId") != null
					&& !"".equals(param.get("subjectId"))) {
				String subjectId = param.get("subjectId").toString();
				sb.append(" and r.trainingSubject=" + subjectId);
			}

			if (param.containsKey("trainCollegeId")
					&& param.get("trainCollegeId") != null
					&& !"".equals(param.get("trainCollegeId"))) {
				String trainingCollege = param.get("trainCollegeId").toString();
				sb.append(" and r.trainingCollege=" + trainingCollege);
			}

			if (param.containsKey("teacherName")
					&& param.get("teacherName") != null
					&& !"".equals(param.get("teacherName"))) {
				String teacherName = (String) param.get("teacherName");
				sb.append(" and t.name like'%" + teacherName + "%'");
			}

			if (param.containsKey("teacherIdCard")
					&& param.get("teacherIdCard") != null
					&& !"".equals(param.get("teacherIdCard"))) {
				String teacherIdCard = (String) param.get("teacherIdCard");
				sb.append(" and t.idcard like'%" + teacherIdCard + "%'");
			}

			if (param.containsKey("teacherOrganization")
					&& param.get("teacherOrganization") != null
					&& !"".equals(param.get("teacherOrganization"))) {
				String teacherOrganization = param.get("teacherOrganization")
						.toString();
				sb.append(" and t.organization=" + teacherOrganization);
			}

			if (param.containsKey("classes") && param.get("classes") != null
					&& !"".equals(param.get("classes"))) {
				String classes = param.get("classes").toString();
				sb.append(" and r.classes=" + classes);
			}

			if (param.containsKey("trainStatus")) {
				sb.append(" and r.trainingStatus<3 ");
			}
			// sb.append(" and (t.name like'%" + teacherName +
			// "%' or t.idcard like'%" + teacherName + "%') ");
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = (String) sortParams.get(key);
				sb.append("r." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getTeacherRecordsForSubject(Map<String, Object> sortParams,
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r, Project p, Teacher t where 1=1 and r.project = p.id and r.teacher = t.id ");
		sb.append(" and r.finalStatus=2 ");
		if (param != null && param.size() > 0) {
			if (param.containsKey("projectId")
					&& param.get("projectId") != null
					&& !"".equals(param.get("projectId"))) {
				String projectId = param.get("projectId").toString();
				sb.append(" and r.project=" + projectId);
			}

			if (param.containsKey("subjectId")
					&& param.get("subjectId") != null
					&& !"".equals(param.get("subjectId"))) {
				String subjectId = param.get("subjectId").toString();
				sb.append(" and r.trainingSubject=" + subjectId);
			}

			if (param.containsKey("trainCollegeId")
					&& param.get("trainCollegeId") != null
					&& !"".equals(param.get("trainCollegeId"))) {
				String trainingCollege = param.get("trainCollegeId").toString();
				sb.append(" and r.trainingCollege=" + trainingCollege);
			}

			if (param.containsKey("teacherName")
					&& param.get("teacherName") != null
					&& !"".equals(param.get("teacherName"))) {
				String teacherName = (String) param.get("teacherName");
				sb.append(" and t.name like'%" + teacherName + "%'");
			}

			if (param.containsKey("teacherIdCard")
					&& param.get("teacherIdCard") != null
					&& !"".equals(param.get("teacherIdCard"))) {
				String teacherIdCard = (String) param.get("teacherIdCard");
				sb.append(" and t.idcard like'%" + teacherIdCard + "%'");
			}

			if (param.containsKey("teacherOrganization")
					&& param.get("teacherOrganization") != null
					&& !"".equals(param.get("teacherOrganization"))) {
				String teacherOrganization = param.get("teacherOrganization")
						.toString();
				sb.append(" and t.organization=" + teacherOrganization);
			}

			if (param.containsKey("classes") && param.get("classes") != null
					&& !"".equals(param.get("classes"))) {
				String classes = param.get("classes").toString();
				sb.append(" and r.classes=" + classes);
			}

			if (param.containsKey("trainStatus")) {
				sb.append(" and r.trainingStatus<3 ");
			}
			// sb.append(" and (t.name like'%" + teacherName +
			// "%' or t.idcard like'%" + teacherName + "%') ");
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = (String) sortParams.get(key);
				sb.append("r." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public int getAduTeacherCountByTimePeriod(Timestamp beginDate,
			Timestamp endDate) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r");
		if (beginDate != null && endDate != null) {
			sb.append(" where r.creattime between \'" + beginDate + "\' and \'"
					+ endDate + "\'");
		}

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public int getLevelAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, int projectCycle, List<ProjectType> lityps) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r, Teacher t, Organization o, Project p, ProjectAdmin pa where r.teacher=t.id");
		sb.append(" and t.organization = o.id and r.project = p.id ");
		sb.append(" and p.creator=pa.id");
		
		//20190731增加周期筛选
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0) {
			sb.append(" and p.status=2");
		}

		if (status > -1) {
//			sb.append(" and r.finalStatus=" + status);
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
		}

		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");

			sb.append(" and pa.organization=");
			sb.append(organization.getId());
		}
		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (").append(organizations).append(")");
		// }

		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public List getLevelAduTeacher(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, int projectCycle, List<ProjectType> lityps, int offset,
			int length) {
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t, Organization o,Project p, ProjectAdmin pa where r.teacher=t.id ");
		sb.append(" and t.organization = o.id and r.project=p.id");
		sb.append(" and p.creator=pa.id");
		
		//20190731增加周期筛选
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0) {
			sb.append(" and p.status=2");
		}

		if (status > -1) {
//			sb.append(" and r.finalStatus=" + status);
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
		}

		if (organization != null) {
			sb.append(" and o.scode like '").append(organization.getScode())
					.append("%'");

			sb.append(" and pa.organization=");
			sb.append(organization.getId());
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}
	
	public int getLevelAduTeacherCount(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps,int level, int projectCycle) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherTrainingRecords r, Teacher t, Organization o, Project p, ProjectAdmin pa where r.teacher=t.id");
		sb.append(" and t.organization = o.id and r.project = p.id ");
		sb.append(" and p.creator=pa.id");
		
		//20190731增加周期筛选
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0) {
			sb.append(" and p.status=2");
		}

		if (status > -1) {
//			sb.append(" and r.finalStatus=" + status);
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
		}

		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		if (organization != null) {
			if(level == 3){
				sb.append(" and o.scode like '").append(organization.getScode())
				.append("%'");

				sb.append(" and pa.organization=");
				sb.append(organization.getOrganization().getId());
			}else{
				sb.append(" and o.scode like '").append(organization.getScode())
				.append("%'");

				sb.append(" and pa.organization in(");
				sb.append(organization.getOrganization().getOrganization().getId());
				sb.append(",");
				sb.append(organization.getOrganization().getId());
				sb.append(")");
			}
			
		}
		// if (organizations.length() > 0) {
		// sb.append(" and t.organization in (").append(organizations).append(")");
		// }

		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}
		
		int result = ((Long) this.getObjectByHql(sb.toString(), null))
				.intValue();
		return result;
	}

	@Override
	public List getLevelAduTeacher(String teacherName, int projectId,
			int subjectId, int trainingUnit, int isAdmin, int status,
			Organization organization, List<ProjectType> lityps, int offset,
			int length,int level, int projectCycle) {
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r, Teacher t, Organization o,Project p, ProjectAdmin pa where r.teacher=t.id ");
		sb.append(" and t.organization = o.id and r.project=p.id");
		sb.append(" and p.creator=pa.id");
		
		//20190731增加周期筛选
		if(projectCycle > 0) {
			sb.append(" and r.projectCycle="+projectCycle);
		}
		if (projectId > 0) {
			sb.append(" and r.project=" + projectId);
		} else {
			if (lityps.size() > 0) {
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%")
							.append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		if (subjectId > 0) {
			sb.append(" and r.trainingSubject=" + subjectId);
		}
		if (trainingUnit > 0) {
			sb.append(" and r.trainingCollege=");
			sb.append(trainingUnit);
		}

		if (projectId == 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId > 0 && subjectId == 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0 && subjectId > 0) {
			sb.append(" and p.status=2");
		} else if (projectId == 0) {
			sb.append(" and p.status=2");
		}

		if (status > -1) {
//			sb.append(" and r.finalStatus=" + status);
			if(status == 3){
				sb.append(" and r.finalStatus=-1");
			}else{
				sb.append(" and r.finalStatus=" + status);
			}
		}

		if (organization != null) {
			if(level == 3){
				sb.append(" and o.scode like '").append(organization.getScode())
				.append("%'");

				sb.append(" and pa.organization=");
				sb.append(organization.getOrganization().getId());
			}else{
				sb.append(" and o.scode like '").append(organization.getScode())
				.append("%'");

//				sb.append(" and pa.organization=");
//				sb.append(organization.getOrganization().getOrganization().getId());
				sb.append(" and pa.organization in(");
				sb.append(organization.getOrganization().getOrganization().getId());
				sb.append(",");
				sb.append(organization.getOrganization().getId());
				sb.append(")");
			}
		}
		if (teacherName != null && teacherName.trim().length() > 0) {
			sb.append(" and (t.name like'%" + teacherName
					+ "%' or t.idcard like'%" + teacherName + "%') ");
		}

		sb.append(" order by r.creattime DESC");

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public List getAduTeacherListByTimePeriod(Timestamp beginDate,
			Timestamp endDate) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from TeacherTrainingRecords r");
		if (beginDate != null && endDate != null) {
			sb.append(" where r.creattime between \'" + beginDate + "\' and \'"
					+ endDate + "\'");
		}

		return this.getListByHSQL(sb.toString());
	}

	@Override
	public List getTTRecordsByCycle(int teacherId, List<String> cycle) {
		String cycleStr = "";
		for (String str : cycle) {
			cycleStr += "'" + str +"'" + ",";
		}
		cycleStr = cycleStr.substring(0, cycleStr.length() - 1);
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingRecords r,TrainingCollege tc,TrainingSubject ts, Project p");
		sb.append(" where r.trainingCollege = tc.id");
		sb.append(" and r.trainingSubject = ts.id");
		sb.append(" and r.project = p.id");
		sb.append(" and r.teacher = " + teacherId);
		sb.append(" and r.trainingStatus in (3,4,5,6)");
		sb.append(" and p.year in(" + cycleStr + ")");
		sb.append(" order by r.creattime DESC");
		return this.getListByHSQL(sb.toString());
	}

	@Override
	public List getAduTeacherTimeListByTimePeriod(Timestamp beginDate,
			Timestamp endDate) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select DATE_FORMAT(r.creattime,'%Y-%m-%d'),count(*)");
		sb.append(" from TeacherTrainingRecords r");
		if (beginDate != null && endDate != null) {
			sb.append(" where r.creattime between \'" + beginDate + "\' and \'"
					+ endDate + "\'");
		}
		sb.append(" and r.finalStatus>0 group by DATE_FORMAT(r.creattime,'%Y-%m-%d') order by r.creattime desc");//筛选排除审核不通过的
		return this.getListByHSQL(sb.toString());
	}

	@Override
	public int getAduTeacherCount(int isAdmin, int status,
			Organization organization,int level) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(t.teacher) from (");
		sb.append("select ttr.teacher as teacher from teacher_training_records ttr left join project p on ttr.project=p.id where 1=1 and p.status=2 ");
		if(isAdmin != 1){
			sb.append(" and ttr.final_status=" + status);
		}else{
			sb.append(" and ((p.enroll_type = 1 and ttr.check_status>=" + level);
			sb.append(") or ( p.enroll_type = 2 and ");
			sb.append("ttr.final_status =" + status+"))");
		}
		
		sb.append(") as t LEFT JOIN teacher tt on t.teacher=tt.id LEFT JOIN organization o on tt.organization=o.id where 1=1");
		if (organization != null) {
			sb.append("  and o.scode like '"+organization.getScode()+"%'");
		}
		

		int result = ((BigInteger)this.getObjectBySql(sb.toString(), null)).intValue();
		return result;
	}
}
