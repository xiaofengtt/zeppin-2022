package cn.zeppin.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.IAssignTeacherTaskDao;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.utility.DictionyMap;

public class AssignTeacherTaskDaoImpl extends BaseDaoImpl<AssignTeacherTask, Integer> implements IAssignTeacherTaskDao {
	/**
	 * @author Clark 2014.05.27 依据项目条件和执行部门获取任务分配列表
	 * @param projectId
	 *            项目ID
	 * @param organizationId
	 *            用户所在部门
	 * @param pageIndex
	 * @return List<AssignTeacherTask> 任务分配列表
	 */

	@Override
	public List<AssignTeacherTask> getAssignTeacherTask(int projectId, int organizationId, int offset, int length) {
		// TODO Auto-generated method stub+
		StringBuilder hql = new StringBuilder("from AssignTeacherTask t where 1=1 ");
		hql.append(" and t.teacherNumber>0");
		// 额外搜索条件值
		if (projectId > 0) {
			hql.append(" and t.project=");
			hql.append(projectId);
		}

		if (organizationId > 0) {
			hql.append(" and t.organizationByGOrganization=");
			hql.append(organizationId);
		}
		hql.append(" order by t.creattime desc");
		return this.getListForPage(hql.toString(), offset, length, null);
	}

	/**
	 * 依据项目条件和执行部门获取任务分配总数
	 * 
	 * @param projectId
	 *            项目ID
	 * @param organizationId
	 *            用户所在部门
	 * @return 当前用户所能看到的任务分配总数
	 */
	@Override
	public int getAssignTeacherTaskCount(int projectId,int subjectId, int organizationId, UserSession us) {
		// TODO Auto-generated method stub
		List<ProjectType> lstProjectType = us.getLstProjectType();
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from AssignTeacherTask t, Project p where t.project=p.id and t.teacherNumber>0  ");

		// 额外搜索条件值
		if (projectId > 0) {
			hql.append(" and t.project=");
			hql.append(projectId);
		}
		if (subjectId > 0) {
			hql.append(" and t.trainingSubject=");
			hql.append(subjectId);
		}
		// 权限
		if (lstProjectType != null && lstProjectType.size() > 0) {
			hql.append(" and p.projectType in ( ");
			String comma = "";
			for (ProjectType pt : lstProjectType) {
				hql.append(comma);
				hql.append(pt.getId());
				comma = ",";
			}
			hql.append(" )");
		}

		if (organizationId > 0) {
			hql.append(" and t.organizationByGOrganization=");
			hql.append(organizationId);
		}

		Object result = this.getObjectByHql(hql.toString(), null);
		return Integer.parseInt(result.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.IAssignTeacherTaskDao#exist(int, short, int, int,
	 * java.lang.String, java.util.List)
	 */
	@Override
	public boolean exist(int projectid, short subjectid, int tcid, int orid, Integer opid, String allotLevel, List<Integer> lstPid) {
		// TODO Auto-generated method stub
		boolean isExist = false;
		String hqlsString = "from AssignTeacherTask where project=" + projectid + " and organizationByGOrganization=" + orid + " and trainingSubject=" + subjectid + " and trainingCollege=" + tcid + " and organizationByPOrganization=" + opid;
		Object object = getObjectByHql(hqlsString, null);
		if (object != null) {
			isExist = true;
		}
		hqlsString = "from AssignTeacherTask where project=" + projectid + " and organizationByGOrganization=" + opid + " and trainingSubject=" + subjectid + " and trainingCollege=" + tcid + " and level=" + allotLevel;
		object = getObjectByHql(hqlsString, null);
		if (object != null) {
			AssignTeacherTask assignTeacherTask = (AssignTeacherTask) object;
			lstPid.clear();
			lstPid.add(assignTeacherTask.getId());
		}
		return isExist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.IAssignTeacherTaskDao#get(int, short, int, int,
	 * java.lang.Integer, java.lang.String, java.util.List)
	 */
	@Override
	public AssignTeacherTask get(int projectid, short subjectid, int tcid, int orid, Integer opid, String allotLevel, List<Integer> lstPid) {
		AssignTeacherTask assignTeacherTask = null;
		// TODO Auto-generated method stub
		String hqlsString = "from AssignTeacherTask where project=" + projectid + " and organizationByGOrganization=" + orid + " and trainingSubject=" + subjectid + " and trainingCollege=" + tcid + " and organizationByPOrganization=" + opid;
		Object object = getObjectByHql(hqlsString, null);
		if (object != null) {
			assignTeacherTask = (AssignTeacherTask) object;
		}
		return assignTeacherTask;
	}

	/**
	 * @author Clark 2014.05.29 依据项目条件和执行部门获取任务分配列表
	 * @param int rprojectId 项目ID
	 * @param UserSession
	 *            us 用户所在部门
	 * @param pageIndex
	 * @return List<Map<String,Object>> 任务分配列表
	 */
	public List<Map<String, Object>> getAssignTeacherTaskResults(int rprojectId,int rsubjectId,UserSession us, int offset, int length) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		Random random = new Random();
		int x = random.nextInt(899999);
		x = x + 100000;
		String tableName = "tmp_task" + x;
		// String tableName = "abc";
		List<ProjectType> lstProjectType = us.getLstProjectType();
		try {
			sql.append(" create TEMPORARY table ");
			sql.append(tableName);
			sql.append(" select t.*, pa.training_starttime, pa.training_endtime, 0 as totalNum, 0 as passNum, 0 as noPassNum ");
			sql.append(" from assign_teacher_task t, project p ,training_subject ts,project_apply pa ");
			sql.append("where t.project=p.id AND t.subject=ts.id AND pa.proejct=p.id AND pa.subject=ts.id AND pa.training_college=t.training_college and t.teacher_number>0  ");

			// 额外搜索条件值
			if (rprojectId > 0) {
				sql.append(" and t.project=");
				sql.append(rprojectId);
			}
			if (rsubjectId > 0) {
				sql.append(" and t.subject=");
				sql.append(rsubjectId);
			}
			// 权限
			if (lstProjectType != null && lstProjectType.size() > 0) {
				sql.append(" and p.type in ( ");
				String comma = "";
				for (ProjectType pt : lstProjectType) {
					sql.append(comma);
					sql.append(pt.getId());
					comma = ",";
				}
				sql.append(" )");
			}
			if (us.getOrganization() > 0) {
				sql.append(" and t.g_organization=");
				sql.append(us.getOrganization());
			}
			sql.append(" order by t.project desc, t.subject, pa.training_starttime, t.training_college ");
			sql.append(" limit ").append(offset).append(",").append(length);
			this.executeSQLUpdate(sql.toString(), null);

			// ///////////////////////////////////////////////////SQL清空
			sql.delete(0, sql.length());
			sql.append(" update ");
			sql.append(tableName);
			sql.append(" set totalNum=");
			sql.append(" (select count(*) from teacher_training_records ttr, teacher t, organization o ");
			sql.append(" where ttr.teacher=t.id and t.organization=o.id ");
			sql.append(" and ttr.project=").append(tableName).append(".project ");
			sql.append(" and ttr.subject=").append(tableName).append(".subject ");
			sql.append(" and ttr.training_college=").append(tableName).append(".training_college ");
			sql.append(" and o.scode like '").append(us.getOrganizationScode()).append("%' ");
//			if (tlstSchool != null && tlstSchool.size() > 0) {
//				sql.append(" and t.organization in ( ");
//				String comma = "";
//				for (Organization school : tlstSchool) {
//					sql.append(comma);
//					sql.append(school.getId());
//					comma = ",";
//				}
//				sql.append(" )");
//			}
			sql.append(" ) ");
			this.executeSQLUpdate(sql.toString(), null);

			// ///////////////////////////////////////////////////SQL清空
			sql.delete(0, sql.length());
			sql.append(" update ");
			sql.append(tableName);
			sql.append(" set passNum=");
			sql.append(" (select count(*) from teacher_training_records ttr, teacher t, organization o ");
			sql.append(" where ttr.teacher=t.id and t.organization=o.id ");
			sql.append(" and ttr.project=").append(tableName).append(".project ");
			sql.append(" and ttr.subject=").append(tableName).append(".subject ");
			sql.append(" and ttr.training_college=").append(tableName).append(".training_college ");
			sql.append(" and o.scode like '").append(us.getOrganizationScode()).append("%' ");
//			if (tlstSchool != null && tlstSchool.size() > 0) {
//				sql.append(" and t.organization in ( ");
//				String comma = "";
//				for (Organization school : tlstSchool) {
//					sql.append(comma);
//					sql.append(school.getId());
//					comma = ",";
//				}
//				sql.append(" ) ");
//			}
			sql.append(" and ttr.final_status=").append(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);

			sql.append(" ) ");
			this.executeSQLUpdate(sql.toString(), null);

			// ///////////////////////////////////////////////////SQL清空
			sql.delete(0, sql.length());
			sql.append(" update ");
			sql.append(tableName);
			sql.append(" set noPassNum=");
			sql.append(" (select count(*) from teacher_training_records ttr, teacher t, organization o  ");
			sql.append(" where ttr.teacher=t.id and t.organization=o.id ");
			sql.append(" and ttr.project=").append(tableName).append(".project ");
			sql.append(" and ttr.subject=").append(tableName).append(".subject ");
			sql.append(" and ttr.training_college=").append(tableName).append(".training_college ");
			sql.append(" and o.scode like '").append(us.getOrganizationScode()).append("%' ");
//			if (tlstSchool != null && tlstSchool.size() > 0) {
//				sql.append(" and t.organization in ( ");
//				String comma = "";
//				for (Organization school : tlstSchool) {
//					sql.append(comma);
//					sql.append(school.getId());
//					comma = ",";
//				}
//				sql.append(" ) ");
//			}
			sql.append(" and ttr.final_status=").append(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);

			sql.append(" ) ");
			this.executeSQLUpdate(sql.toString(), null);

			// ///////////////////////////////////////////////////SQL清空
			sql.delete(0, sql.length());
			sql.append(" select tmp.id, tmp.project, tmp.subject, tmp.training_college, tmp.g_organization, tmp.p_organization, tmp.training_starttime, tmp.training_endtime,");
			sql.append(" tmp.teacher_number, tmp.totalNum, tmp.passNum, tmp.noPassNum, tmp.status, tmp.timeup, tmp.creator, tmp.creattime, ");
			sql.append(" p.name as projectname, ts.name as subjectname, tc.name as collegename, go.name as g_organizationname ");
			sql.append(" from project p, training_subject ts, training_college tc, organization go, ");
			sql.append(tableName).append(" tmp ");
			sql.append(" where tmp.project=p.id ");
			sql.append(" and tmp.subject=ts.id ");
			sql.append(" and tmp.training_college=tc.id ");
			sql.append(" and tmp.g_organization=go.id ");
			sql.append(" order by tmp.project desc, tmp.subject, tmp.training_college");
			@SuppressWarnings("unchecked")
			List<Object[]> rst = this.getListBySQL(sql.toString(), null);

			// ///////////////////////////////////////////////////封装结果
			for (Object[] obj : rst) {
				Map<String, Object> record = new HashMap<>();
				int i = 0;
				record.put("id", obj[i++]);
				record.put("project", obj[i++]);
				record.put("subject", obj[i++]);
				record.put("training_college", obj[i++]);
				record.put("g_organization", obj[i++]);
				record.put("p_organization", obj[i++]);
				record.put("training_starttime", obj[i++]);
				record.put("training_endtime", obj[i++]);
				record.put("teacher_number", obj[i++]);
				record.put("totalnum", obj[i++]);
				record.put("passnum", obj[i++]);
				record.put("nopassnum", obj[i++]);
				record.put("status", obj[i++]);
				record.put("timeup", obj[i++]);
				record.put("creator", obj[i++]);
				record.put("creattime", obj[i++]);
				record.put("projectname", obj[i++]);
				record.put("subjectname", obj[i++]);
				record.put("training_collegename", obj[i++]);
				record.put("g_organizationname", obj[i++]);

				result.add(record);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sql.delete(0, sql.length());
			sql.append("drop table ").append(tableName);
			this.executeSQLUpdate(sql.toString(), null);
		}

		return result;
	}

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
	public AssignTeacherTask get(String project, String subject, String trainingCollege, short level) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		int pid = Integer.parseInt(project);
		int sid = Integer.parseInt(subject);
		int tcid = Integer.parseInt(trainingCollege);
		sb.append(" from AssignTeacherTask where project=").append(pid);
		sb.append(" and trainingSubject=").append(sid);
		sb.append(" and trainingCollege=").append(tcid);
		sb.append(" and level=").append(level);
		// sb.append(" and organizationByPOrganization=").append(p_organization);

		AssignTeacherTask result = (AssignTeacherTask) getObjectByHql(sb.toString(), null);
		return result;
	}
}
