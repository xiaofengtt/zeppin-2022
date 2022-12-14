package cn.zeppin.service.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.IAssignTeacherTaskDao;
import cn.zeppin.dao.impl.AssignTeacherTaskDaoImpl;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.utility.DictionyMap;

public class AssignTeacherTaskServiceImpl extends
BaseServiceImpl<AssignTeacherTask, Integer> implements
IAssignTeacherTaskService
{

	private IAssignTeacherTaskDao assignTeacherTaskDao;

	public IAssignTeacherTaskDao getAssignTeacherTaskDao()
	{
		return assignTeacherTaskDao;
	}

	public void setAssignTeacherTaskDao(
			IAssignTeacherTaskDao assignTeacherTaskDao)
	{
		this.assignTeacherTaskDao = assignTeacherTaskDao;
	}

	@Override
	public AssignTeacherTask add(AssignTeacherTask t)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.add(t);
	}

	@Override
	public AssignTeacherTask update(AssignTeacherTask t)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.update(t);
	}

	@Override
	public void delete(AssignTeacherTask t)
	{
		// TODO Auto-generated method stub
		assignTeacherTaskDao.delete(t);
	}

	@Override
	public AssignTeacherTask load(Integer id)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.load(id);
	}

	@Override
	public AssignTeacherTask get(Integer id)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.get(id);
	}

	@Override
	public List<AssignTeacherTask> loadAll()
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.loadAll();
	}

	@Override
	public List<AssignTeacherTask> findAll()
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.findByHSQL(querySql);
	}

	@Override
	public List<AssignTeacherTask> getListForPage(String hql, int offset,
			int length)
			{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.getListForPage(hql, offset, length);
			}

	@Override
	public void executeHSQL(String hql)
	{
		// TODO Auto-generated method stub
		assignTeacherTaskDao.executeHSQL(hql);
	}

	@Override
	public List<AssignTeacherTask> getListByHSQL(String hql)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas)
	{
		// TODO Auto-generated method stub
		assignTeacherTaskDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.getListPage(sql, offset, length, objParas);
	}

	/**
	 * ?????????????????????????????????????????????????????????
	 * 
	 * @param projectId
	 *            ??????ID
	 * @param organizationId
	 *            ??????????????????
	 * @param offset
	 *            ???????????????
	 * @param length
	 *            ???????????????
	 * @return List<AssignTeacherTask> ??????????????????
	 */
	@Override
	public List<AssignTeacherTask> getAssignTeacherTask(int projectId,
			int organizationId, int offset, int length)
			{
		// TODO Auto-generated method stub
		return this.getAssignTeacherTaskDao().getAssignTeacherTask(projectId,
				organizationId, offset, length);

			}

	/**
	 * ?????????????????????????????????????????????????????????
	 * 
	 * @param projectId
	 *            ??????ID
	 * @param organizationId
	 *            ??????????????????
	 * @return ?????????????????????????????????????????????
	 */
	@Override
	public int getAssignTeacherTaskCount(int projectId, int subjectId,int organizationId,
			UserSession us)
	{
		// TODO Auto-generated method stub
		return this.getAssignTeacherTaskDao().getAssignTeacherTaskCount(
				projectId, subjectId,organizationId, us);
	}

	public int getAssignStatus(int projectid,int subjectid, int organizationid)
	{
		int Assingstatus = 0;
		String sql = "SElECT count(*) FROM assign_teacher_task t WHERE t.project="+ projectid +" and t.subject="+subjectid+" and t.p_organization="+organizationid;		
		List li = this.executeSQL(sql, null);
		Assingstatus = Integer.valueOf(li.get(0).toString());
		return Assingstatus;
	}

	/**
	 * ?????? ???????????????????????????
	 */
	@Override
	public int getAssignTeacherTaskLevel(Object[] params, UserSession us)
	{

		// ???????????????????????????level
		String aduSql = " FROM AssignTeacherTask t ";
		aduSql += " WHERE 1=1 ";
		aduSql += " and t.project=" + params[0] + " and t.trainingSubject="
				+ params[1];
		aduSql += " and t.trainingCollege=" + params[2];
		if (us.isSchool())
		{
			aduSql += " AND t.organizationByPOrganization="
					+ us.getOrganization();
		}
		else
		{
			aduSql += " AND t.organizationByGOrganization="
					+ us.getOrganization();
		}
		List<AssignTeacherTask> li = this.getListByHSQL(aduSql);
		if (li != null && li.size() > 0)
		{
			return li.get(0).getLevel();
		}
		else
		{
			return 1;
		}

	}

	/**
	 * ?????? ?????????????????? by ??????id ???????????????
	 */
	@Override
	public List<AssignTeacherTask> getAssignTeacherTaskByProject(int project,
			int organization)
			{
		String hql = "from AssignTeacherTask t where t.project=" + project
				+ " and t.organizationByPOrganization=" + organization;
		return this.getListByHSQL(hql);
			}

	/**
	 * ??????????????????????????????
	 */
	@Override
	public void getAssignTeacherTaskByOrganization(HttpServletRequest request,
			UserSession us, String year ,String project, String subject, Hashtable<String, Object> outHt)
	{

		// ?????????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN"))
		{
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// ??????
		String sort = request.getParameter("sort");

		String sql = "SELECT t.id, t.project ,p.name AS projectname,t.subject,ts.name AS subjectname,t.status,t.timeup,t.creator,t.creattime,t.level, SUM(t.teacher_number) asum";
		sql += " FROM assign_teacher_task t,project p,training_subject ts";
		sql += " WHERE 1=1 AND t.project=p.id AND t.subject=ts.id";
		
		if (year != null && year.length() > 0 && !year.equals("0"))
		{
			sql += " AND p.year='" + year + "'";
		}
		
		
		if (project != null && project.length() > 0 && !project.equals("0"))
		{
			sql += " AND t.project=" + project;
		}
		
		if (subject != null && subject.length() > 0 && !subject.equals("0"))
		{
			sql += " AND t.subject=" + subject;
		}
		// TODO ????????????????????????????????????

		int organizationLevel = us.getOrganizationLevel();

		// ??????????????????????????????????????????
		// sql += " AND ( t.g_organization=" + us.getOrganization()
		// + " OR t.p_organization=" + us.getOrganization() + ") ";
		sql += " AND ( t.g_organization=" + us.getOrganization() + ") ";
		List<ProjectType> lstProjectType = us.getLstProjectType();
		String projectString = "";
		if (lstProjectType.size() > 0)
		{
			for (ProjectType projectType : lstProjectType)
			{
				projectString += projectType.getId() + ",";
			}
			projectString = projectString.substring(0,
					projectString.length() - 1);
			sql += " and p.type in (" + projectString + ")";
		}

		sql += " GROUP BY t.project,t.subject having asum>0";
		// ?????? ??????
		if (sort != null && !sort.equals(""))
		{
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sql += " order by t." + sortname + " " + sorttype;
		}
		else
		{
			sql += " order by t.project desc, t.subject ";
		}

		outHt.put("assigntasksql", sql);

		// ????????????
		List<Object> li = this.getListPage(sql, (start - 1)
				* DictionyMap.maxPageSize, DictionyMap.maxPageSize, null);

		outHt.put("data", li);
	}

	/**
	 * ?????? ???????????? ?????????????????????????????????
	 */
	@Override
	public void getTrainingRecordsAllTeacherNumber(Object[] params,
			UserSession us, Hashtable<String, Object> outHt)
	{

		// boolean isSchool = us.isSchool();

		String recordsSql = "SELECT t.FINAL_STATUS,count(*) ";
		recordsSql += "FROM teacher_training_records t ,teacher tch, organization o ";
		recordsSql += "WHERE t.teacher=tch.id and tch.organization=o.id and t.project=" + params[0];
		recordsSql += " and t.subject=" + params[1];
		recordsSql += " and o.scode like '" + us.getOrganizationScode() + "%'";

//		if (us.getOrganizationLevel() == 1)
//		{
//			// ???????????????
//			// ????????????????????????????????????????????????
//		}
//		else
//		{
//			String sidString = "";
//			for (Organization o1 : us.getLstSubSchool())
//			{
//				sidString += o1.getId() + ",";
//			}
//			if (!sidString.equals(""))
//			{
//				sidString = sidString.substring(0, sidString.length() - 1);
//			}
//
//			recordsSql += " and tch.organization in (" + sidString + ")";
//		}

		recordsSql += " group by  t.FINAL_STATUS ";

		int total = 0;
		int passNum = 0;
		int uncheck = 0;
		int noPass = 0;

		List li = executeSQL(recordsSql, null);
		for (int i = 0; i < li.size(); i++)
		{
			Object[] obj = (Object[]) li.get(i);

			byte finalStatus = (byte) obj[0];
			// byte checkStatus = (byte) obj[1];

			int at = Integer.valueOf(obj[1].toString());

			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS)
			{
				passNum += at;
			}
			else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS)
			{
				// ???????????????
				noPass += at;
			}
			else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK)
			{

				uncheck += at;
			}

		}
		total = passNum + uncheck + noPass;

		outHt.put("total", total);
		outHt.put("passNum", passNum);
		outHt.put("noPass", noPass);

	}

	/*
	 * (non-Javadoc)
	 * @see cn.zeppin.service.IAssignTeacherTaskService#exist(int, short, int,
	 * int, java.lang.Integer, java.lang.String, java.util.List)
	 */
	@Override
	public boolean exist(int projectid, short subjectid, int tcid, int orid,
			Integer opid, String allotLevel, List<Integer> lstPid)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.exist(projectid, subjectid, tcid, orid,
				opid, allotLevel, lstPid);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.zeppin.service.IAssignTeacherTaskService#get(int, short, int,
	 * int, java.lang.Integer, java.lang.String, java.util.List)
	 */
	@Override
	public AssignTeacherTask get(int projectid, short subjectid, int tcid,
			int orid, Integer id, String allotLevel, List<Integer> lstPid)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.get(projectid, subjectid, tcid, orid, id,
				allotLevel, lstPid);
	}

	/**
	 * ?????????????????????????????????????????????????????????
	 * 
	 * @param projectId
	 *            ??????ID
	 * @param organizationId
	 *            ??????????????????
	 * @param offset
	 *            ???????????????
	 * @param length
	 *            ???????????????
	 * @return Map<String,Object> ?????????????????????????????????????????????????????????????????????????????????
	 */
	@Override
	public List<Map<String, Object>> getAssignTeacherTaskResults(
			int rprojectId, int rsubjectId,UserSession us, int offset, int maxpagesize)
			{
		// TODO Auto-generated method stub
		return this.getAssignTeacherTaskDao().getAssignTeacherTaskResults(
				rprojectId,rsubjectId, us, offset, maxpagesize);
			}

	/**
	 * ????????????????????????????????????
	 * 
	 * @author Clark 2014.05.30
	 * @param project
	 * @param subject
	 * @param trainingCollege
	 * @param g_organization
	 * @param p_organization
	 * @return AssignTeacherTask
	 */
	@Override
	public AssignTeacherTask get(String project, String subject,
			String trainingCollege, short level)
	{
		// TODO Auto-generated method stub
		return assignTeacherTaskDao.get(project, subject, trainingCollege,
				level);
	}

	/**
	 * ????????????????????????????????????
	 * @param projectId
	 * @param org
	 * @return
	 */
	@Override
	public List<AssignTeacherTask> getAssignTeacherTaskByOrganization(
			int projectId, Organization org) {
		String hql = "from AssignTeacherTask t where t.project=" + projectId
				+ " and t.organizationByGOrganization=" + org.getId();
		return this.getListByHSQL(hql);
	}

	@Override
	public AssignTeacherTask getAssignTeacherTask(Integer projectId,
			Short trainingSubjectId, Integer trainingCollegeId,
			Integer p_orgId, Integer g_orgId) {
		// TODO Auto-generated method stub
		String hql = "from AssignTeacherTask t where t.project=" + projectId
				+ " and t.trainingSubject=" + trainingSubjectId
				+ " and t.trainingCollege=" + trainingCollegeId
				+ " and t.organizationByGOrganization=" + g_orgId
				+ " and t.organizationByPOrganization=" + p_orgId;
		return (AssignTeacherTask) this.getAssignTeacherTaskDao().getObjectByHql(hql, null);
	}


	/*
	 * (non-Javadoc)
	 * @see cn.zeppin.service.IAssignTeacherTaskService#getlist(int, short,
	 * java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<AssignTeacherTask> getlist(int projectid, short subjectid,
			Integer pid, String allotLevel) {
		// TODO Auto-generated method stub
		String hqlsString = "from AssignTeacherTask where project=" + projectid
				+ " and trainingSubject=" + subjectid
				+ " and (organizationByGOrganization=" + pid
				+ " or organizationByPOrganization=" + pid + ")";
		return assignTeacherTaskDao.getListByHSQL(hqlsString);
	}
}

