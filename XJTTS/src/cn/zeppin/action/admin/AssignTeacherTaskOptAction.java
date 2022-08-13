package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class AssignTeacherTaskOptAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(AssignTeacherTaskOptAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IOrganizationService iOrganization; // 组织架构
	private ITrainingCollegeService iTrainingCollegeService; // 承训单位
	private ITrainingSubjectService iTrainingSubjectService; // 培训科目

	private IProjectApplyService iProjectApplyService; // 项目中标信息
	private IProjectService iProjectService; // 项目信息
	private IProjectAdminService iProjectAdminService;

	private IAssignTeacherTaskService iAssignTeacherTaskService;

	// 添加
	public List<Project> listProject;

	private String projectId;
	private String subjectId;

	private String timeup;
	private String status;
	private String assginTeacher;

	private String pageType;

	private String adminLevel;
	private String isEdit;
	// 分配任务

	private String allotProjectId;
	private String allotSubjectId;
	private String allotProjectName;
	private String allotSubjectName;
	private String allotLevel;
	private String allotAssginTeacher;
	
	private String enrolltype;
	private String assId;

	// 预览
	private LinkedHashMap<String, LinkedHashMap<String, Object>> tchashMap;
	private String[] baseInfo;

	public AssignTeacherTaskOptAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	@SuppressWarnings("unchecked")
	public String initPage() {
		initServlert();
		// 已经发布的项目
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.listProject = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
		this.assginTeacher = "";

		return "init";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String initPageChild() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		// 已经发布的项目
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.listProject = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
		
		Map<String,Object> map = new HashMap<String,Object>();
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
//		map.put("organization", 26124);
		map.put("organization", us.getOrganization());
		map.put("level", us.getOrganizationLevel());
		this.listProject = new ArrayList<Project>();
		List list2level = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list2level != null && list2level.size() > 0) {
			for (int i = 0; i < list2level.size(); i++) {
				Object[] obj = (Object[]) list2level.get(i);
				Project p = (Project) obj[0];
				this.listProject.add(p);
			}
		}
		Organization oo = this.iOrganization.get(us.getOrganization());
		if(us.getOrganizationLevel()>2){
			this.listProject = new ArrayList<Project>();
			map.remove("level");
			if(us.getOrganizationLevel() == 3){
				map.put("organization", oo.getOrganization().getId());//市级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.listProject.add(p);
					}
				}
			}else{
				map.put("organization", oo.getOrganization().getId());//县级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.listProject.add(p);
					}
				}
				map.put("organization", oo.getOrganization().getOrganization().getId());//市级
				List list2 = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list2 != null && list2.size() > 0){
					for(int i = 0; i < list2.size(); i++){
						Object[] obj = (Object[])list2.get(i);
						Project p = (Project)obj[0];
						this.listProject.add(p);
					}
				}
			}
		}
		
		this.assginTeacher = "";

		return "init";
	}

	/**
	 * 获取分配任务的头信息
	 */
	@SuppressWarnings("rawtypes")
	public void getTaskHeader() {

		initServlert();

		// 首先获取 申报信息里的承训单位以及人数
		String projectId = request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId");

		UserSession user = (UserSession) session.getAttribute("usersession");

		String checkHql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
		List<AssignTeacherTask> checkList = this.iAssignTeacherTaskService.getListByHSQL(checkHql);

		if (checkList != null && checkList.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"当前项目和学科已经分配过任务请重新选择！\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"OK\",");

		sb.append("\"top\":[");

		String hql = "from ProjectApply t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;

		List<ProjectApply> liapply = this.iProjectApplyService.getListByHSQL(hql);

		for (ProjectApply pa : liapply) {
			String sr = "{\"id\":" + pa.getTrainingCollege().getId() + ",\"name\":\"" + pa.getTrainingCollege().getName() + "\",\"number\":" + pa.getApproveNumber() + "},";
			sb.append(sr);
		}
		if (liapply.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");

		sb.append(",");

		sb.append("\"left\":[");

		// 获取下级组织架构

		List li = this.iOrganization.getLevelOrganization(user.getOrganization());
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String sr = "{\"id\":" + obj[0].toString() + ",\"name\":\"" + obj[1].toString() + "\"},";
			sb.append(sr);
		}

		if (li.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");

		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	/**
	 * 新建分配任务
	 * 
	 * @return
	 */
	public void addAssignTeacherTask() {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		try {

			Map<String, String[]> parMap = request.getParameterMap();
			String projectId = parMap.get("projectId")[0];
			String subjectId = parMap.get("subjectId")[0];

			String timeup = parMap.get("timeup")[0];
			String status = parMap.get("status")[0];
			String assginTeacher = parMap.get("assginTeacher")[0];

			@SuppressWarnings("unused")
			String[] vs = assginTeacher.split("#");
			int projectid = Integer.valueOf(projectId);
			short subjectid = Short.valueOf(subjectId);

			Project tmP = iProjectService.get(projectid);
			TrainingSubject tmTs = iTrainingSubjectService.get(subjectid);
			Organization oza = iOrganization.get(user.getOrganization());

			if (timeup == null || timeup.equals("") || timeup.equals("0")) {
				sendResponse("ERROR", "报送截止日期为空");
				return;
			}
			// sj 添加
			List<ProjectApply> lstProjectApplies = new ArrayList<>();
			lstProjectApplies = iProjectApplyService.getList(projectid, subjectid);
			if (lstProjectApplies.size() > 0) {
				for (ProjectApply projectApply : lstProjectApplies) {

					AssignTeacherTask att = new AssignTeacherTask();

					att.setProject(tmP);
					att.setTrainingSubject(tmTs);
					att.setOrganizationByGOrganization(oza);

					att.setReceiveFlag((short) 1);

					// 设置
					@SuppressWarnings("unused")
					Organization poza = oza;
					TrainingCollege tc = projectApply.getTrainingCollege();

					att.setOrganizationByPOrganization(null);
					att.setTrainingCollege(tc);
					att.setLevel((short) 1);

					att.setTimeup(Timestamp.valueOf(timeup + " 00:00:00"));

					att.setStatus(Short.valueOf(status));
					att.setTeacherNumber(Integer.parseInt(projectApply.getApproveNumber().toString()));
					att.setCreator(user.getId());
					att.setCreattime(new Timestamp(System.currentTimeMillis()));
					iAssignTeacherTaskService.add(att);
				}

			}

			// 循环获取组织架构和
			// for (String str : vs)
			// {
			// String[] otvs = str.split(",");
			// int orid = Integer.valueOf(otvs[0]); // 组织架构id
			// int tcid = Integer.valueOf(otvs[1]); // 承训单位id
			// int vid = Integer.valueOf(otvs[2]); // 填写的数字
			//
			// AssignTeacherTask att = new AssignTeacherTask();
			//
			// att.setProject(tmP);
			// att.setTrainingSubject(tmTs);
			// att.setOrganizationByGOrganization(oza);
			//
			// att.setReceiveFlag((short) 1);
			//
			// // 设置
			// Organization poza = iOrganization.get(orid);
			// TrainingCollege tc = iTrainingCollegeService.get(tcid);
			//
			// att.setOrganizationByPOrganization(poza);
			// att.setTrainingCollege(tc);
			// att.setLevel((short) 1);
			//
			// att.setTimeup(Timestamp.valueOf(timeup + " 00:00:00"));
			//
			// att.setStatus(Short.valueOf(status));
			// att.setTeacherNumber(vid);
			// att.setCreator(user.getId());
			//
			// iAssignTeacherTaskService.add(att);
			//
			// }
			sendResponse("OK", "添加学员任务分配成功");
			return;

		} catch (Exception ex) {
			ex.printStackTrace();
			sendResponse("ERROR", "操作异常失败");
			return;
		}
	}

	/**
	 * 任务分配页面初始化
	 */
	public String allotPage() {

		initServlert();
		String psid = request.getParameter("key");
		String[] t = psid.split("-");
		String projectId = t[0];
		String subjectId = t[1];

		UserSession user = (UserSession) session.getAttribute("usersession");
		int orid = user.getOrganization();

		String hql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
		hql += " and t.organizationByGOrganization=" + orid;
		hql += " group by t.project,t.trainingSubject,t.organizationByGOrganization";

		List<AssignTeacherTask> li = this.iAssignTeacherTaskService.getListByHSQL(hql);
		if (li != null && li.size() > 0) {
			AssignTeacherTask att = li.get(0);
			this.allotLevel = att.getLevel().toString();
			this.allotProjectId = projectId;
			this.allotProjectName = att.getProject().getName();
			this.allotSubjectId = subjectId;
			this.allotSubjectName = att.getTrainingSubject().getName();
			this.timeup = Utlity.timeSpanToDateString(att.getTimeup());
			this.status = att.getStatus().toString();
			this.pageType = "edit";
		} else {
			String hqlt = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
			hql += " and t.organizationByPOrganization=" + orid;
			hql += " group by t.project,t.trainingSubject,t.organizationByPOrganization";

			List<AssignTeacherTask> lit = this.iAssignTeacherTaskService.getListByHSQL(hqlt);

			if (lit != null && lit.size() > 0) {
				AssignTeacherTask att = lit.get(0);
				this.allotLevel = att.getLevel().toString();
				this.allotProjectId = projectId;
				this.allotProjectName = att.getProject().getName();
				this.allotSubjectId = subjectId;
				this.allotSubjectName = att.getTrainingSubject().getName();
				this.pageType = "add";
			}
		}

		this.assginTeacher = "";

		return "allot";
	}

	/**
	 * 获取任务分配头信息
	 */
	@SuppressWarnings("rawtypes")
	public void getAllotTaskHeader() {
		initServlert();

		// 首先获取 申报信息里的承训单位以及人数

		String projectId = request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId");

		UserSession user = (UserSession) session.getAttribute("usersession");

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"OK\",");
		sb.append("\"top\":[");

		// 分配给当前用户的任务
		String hql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
		hql += " and t.organizationByPOrganization=" + user.getOrganization();

		List<AssignTeacherTask> liapply = this.iAssignTeacherTaskService.getListByHSQL(hql);
		String[] tcstrids = new String[liapply.size()];

		for (int i = 0; i < liapply.size(); i++) {
			AssignTeacherTask pa = liapply.get(i);
			int paid = pa.getTrainingCollege().getId();
			String sr = "{\"tid\":" + paid + ",\"name\":\"" + pa.getTrainingCollege().getName() + "\",\"number\":" + pa.getTeacherNumber() + "},";
			sb.append(sr);
			tcstrids[i] = paid + "";
		}
		if (liapply.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append(",");

		// 由当前用户创建的任务
		String checkHql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
		checkHql += " and t.organizationByGOrganization=" + user.getOrganization();

		List<AssignTeacherTask> checkList = this.iAssignTeacherTaskService.getListByHSQL(checkHql);

		if (checkList != null && checkList.size() > 0) {
			// 已存在
			Hashtable<String, String> tmphs = new Hashtable<>();
			for (AssignTeacherTask att : checkList) {
				String key = att.getOrganizationByPOrganization().getId() + "_" + att.getTrainingCollege().getId();
				if (!tmphs.containsKey(key)) {
					tmphs.put(key, att.getTeacherNumber().toString());
				}
			}

			sb.append("\"left\":[");

			// 获取下级组织架构
			List li = this.iOrganization.getLevelOrganization(user.getOrganization());

			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				String sr = "{\"lid\":" + obj[0].toString() + ",\"name\":\"" + obj[1].toString() + "\",\"data\":[";
				for (String s : tcstrids) {
					String key = obj[0].toString() + "_" + s;
					if (tmphs.containsKey(key)) {
						sr += "{\"id\":" + s + ",\"value\":" + tmphs.get(key) + "},";
					} else {
						sr += "{\"id\":" + s + ",\"value\":0},";
					}
				}
				if (sr.endsWith(",")) {
					sr = sr.substring(0, sr.length() - 1);
				}
				sr += "]},";
				sb.append(sr);
			}

			if (li.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}

			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} else {

			sb.append("\"left\":[");

			// 获取下级组织架构
			List li = this.iOrganization.getLevelOrganization(user.getOrganization());
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				String sr = "{\"lid\":" + obj[0].toString() + ",\"name\":\"" + obj[1].toString() + "\",\"data\":[";
				for (String s : tcstrids) {
					sr += "{\"id\":" + s + ",\"value\":" + 0 + "},";
				}
				if (sr.endsWith(",")) {
					sr = sr.substring(0, sr.length() - 1);
				}
				sr += "]},";
				sb.append(sr);
			}

			if (li.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}

			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	public void addAllotAssignTeacherTask() {

		initServlert();

		UserSession user = (UserSession) session.getAttribute("usersession");

		try {

			Map<String, String[]> parMap = request.getParameterMap();
			String allotProjectId = parMap.get("allotProjectId")[0];
			String allotSubjectId = parMap.get("allotSubjectId")[0];
			String allotLevel = parMap.get("allotLevel")[0];

			String timeup = parMap.get("timeup")[0];
			String status = parMap.get("status")[0];
			String type = parMap.get("pageType")[0];

			String assginTeacher = parMap.get("assginTeacher")[0];
			String[] vs = assginTeacher.split("#");

			int projectid = Integer.valueOf(allotProjectId);
			short subjectid = Short.valueOf(allotSubjectId);
			short level = Short.valueOf(allotLevel);
			level = (short) (level + 1);

			if (timeup == null || timeup.equals("") || timeup.equals("0")) {
				sendResponse("ERROR", "报送截止日期为空");
				return;
			}

			if (type.equals("add")) {

				String hql = "from AssignTeacherTask t where t.project=" + projectid + " and t.trainingSubject=" + subjectid;
				hql += " and t.organizationByPOrganization=" + user.getOrganization();

				List<AssignTeacherTask> liapply = this.iAssignTeacherTaskService.getListByHSQL(hql);

				Hashtable<Integer, AssignTeacherTask> tmpPids = new Hashtable<>();

				// 查询出所有父信息
				for (AssignTeacherTask pa : liapply) {
					if (!tmpPids.containsKey(pa.getTrainingCollege().getId())) {
						tmpPids.put(pa.getTrainingCollege().getId(), pa);
					}
				}

				Project tmP = this.iProjectService.get(projectid);
				TrainingSubject tmTs = this.iTrainingSubjectService.get(subjectid);
				Organization oza = this.iOrganization.get(user.getOrganization());

				// 循环获取组织架构和
				for (String str : vs) {
					String[] otvs = str.split(",");
					int orid = Integer.valueOf(otvs[0]); // 组织架构id
					int tcid = Integer.valueOf(otvs[1]); // 承训单位id
					int vid = Integer.valueOf(otvs[2]); // 填写的数字

					AssignTeacherTask patt = tmpPids.get(tcid);
					patt.setReceiveFlag((short) 2);

					AssignTeacherTask att = new AssignTeacherTask();

					att.setProject(tmP);
					att.setTrainingSubject(tmTs);
					att.setOrganizationByGOrganization(oza);

					// 设置
					Organization poza = this.iOrganization.get(orid);
					TrainingCollege tc = this.iTrainingCollegeService.get(tcid);

					att.setOrganizationByPOrganization(poza);
					att.setTrainingCollege(tc);

					att.setAssignTeacherTask(patt);

					att.setLevel(level);
					att.setReceiveFlag((short) 1);

					att.setTimeup(Timestamp.valueOf(timeup + " 00:00:00"));

					att.setStatus(Short.valueOf(status));
					att.setTeacherNumber(vid);
					att.setCreator(user.getId());

					this.iAssignTeacherTaskService.add(att);
					this.iAssignTeacherTaskService.update(patt);

				}

			} else if (type.equals("edit")) {
				String hql = "from AssignTeacherTask t where t.project=" + projectid + " and t.trainingSubject=" + subjectid;
				hql += " and t.organizationByGOrganization=" + user.getOrganization();

				List<AssignTeacherTask> liapply = this.iAssignTeacherTaskService.getListByHSQL(hql);
				Hashtable<String, AssignTeacherTask> tmpPids = new Hashtable<>();
				// 查询出所有父信息
				for (AssignTeacherTask pa : liapply) {
					String key = pa.getTrainingCollege().getId().toString() + "-" + pa.getOrganizationByPOrganization().getId().toString();
					if (!tmpPids.containsKey(key)) {
						tmpPids.put(key, pa);
					}
				}

				// 循环获取组织架构和
				for (String str : vs) {
					String[] otvs = str.split(",");
					int orid = Integer.valueOf(otvs[0]); // 组织架构id
					int tcid = Integer.valueOf(otvs[1]); // 承训单位id
					int vid = Integer.valueOf(otvs[2]); // 填写的数字

					String key = tcid + "-" + orid;

					AssignTeacherTask att = tmpPids.get(key);
					if (att == null) {
						continue;
					}

					att.setTimeup(Timestamp.valueOf(this.timeup + " 00:00:00"));
					att.setStatus(Short.valueOf(this.status));
					att.setTeacherNumber(vid);

					this.iAssignTeacherTaskService.update(att);

				}
			}

			sendResponse("OK", "学员任务分配成功");
			return;

		} catch (Exception ex) {
			ex.printStackTrace();
			sendResponse("ERROR", "学员任务分配失败异常");
			return;
		}
	}

	/**
	 * 编辑页面
	 */
	public String editPage() {

		initServlert();

		String psid = request.getParameter("key");

		if (psid == null) {
			return "error";
		}

		String[] t = psid.split("-");

		String projectId = t[0];
		String subjectId = t[1];
		
		UserSession user = (UserSession) session.getAttribute("usersession");
		int orid = user.getOrganization();
		boolean flagEdit = false;
		Project p = this.iProjectService.get(Integer.parseInt(projectId));
		if(p != null){
			ProjectAdmin pa = this.iProjectAdminService.get(p.getCreator());
			if(pa != null){
				int org = pa.getOrganization().getId();
				if(org == orid){
					flagEdit = true;
				}
			}
		}
		if(flagEdit){
			this.isEdit = "1";
		}else{
			this.isEdit = "0";
		}
		
		adminLevel = String.valueOf(user.getOrganizationLevel());
		String hql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
		hql += " and t.organizationByGOrganization=" + orid;
		hql += " group by t.project,t.trainingSubject,t.organizationByGOrganization";

		List<AssignTeacherTask> li = this.iAssignTeacherTaskService.getListByHSQL(hql);

		if (li != null && li.size() > 0) {
			AssignTeacherTask att = li.get(0);
			this.assId = att.getId()+"";
			this.allotLevel = att.getLevel().toString();
			this.allotProjectId = projectId;
			this.allotProjectName = att.getProject().getName();
			this.allotSubjectId = subjectId;
			this.allotSubjectName = att.getTrainingSubject().getName();
			this.timeup = Utlity.timeSpanToDateString(att.getTimeup());
			this.status = att.getStatus().toString();
			this.enrolltype = att.getProject().getEnrollType()+"";
		}
		this.assginTeacher = "";

		return "edit";
	}

	/**
	 * 获取编辑数据
	 */
	@SuppressWarnings("rawtypes")
	public void getEditTaskHeader() {
		initServlert();

		// 首先获取 申报信息里的承训单位以及人数

		String projectId = request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId");
		String level = request.getParameter("level");

		UserSession user = (UserSession) session.getAttribute("usersession");

		int orid = user.getOrganization();
		String datahql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
		datahql += " and ( t.organizationByPOrganization=" + orid + " or t.organizationByGOrganization=" + orid + ")";

		List<AssignTeacherTask> dataList = this.iAssignTeacherTaskService.getListByHSQL(datahql);

		Hashtable<String, String> tmphs = new Hashtable<>();
		for (AssignTeacherTask att : dataList) {
			String key = att.getOrganizationByGOrganization().getId() + "_" + att.getTrainingCollege().getId();
			if (!tmphs.containsKey(key)) {
				tmphs.put(key, att.getTeacherNumber().toString());
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"OK\",");

		String[] tcstrids;

		if (level.equals("1")) {
			// 添加者
			sb.append("\"top\":[");

			String hql = "from ProjectApply t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;

			List<ProjectApply> liapply = this.iProjectApplyService.getListByHSQL(hql);
			tcstrids = new String[liapply.size()];
			for (int i = 0; i < liapply.size(); i++) {
				ProjectApply pa = liapply.get(i);
				String sr = "{\"tid\":" + pa.getTrainingCollege().getId() + ",\"name\":\"" + pa.getTrainingCollege().getName() + "\",\"number\":" + pa.getApproveNumber() + "},";
				sb.append(sr);
				tcstrids[i] = pa.getTrainingCollege().getId().toString();
			}
			if (liapply.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]");
		} else {
			sb.append("\"top\":[");

			String hql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId + " and (t.organizationByPOrganization=" + user.getOrganization() + " or t.organizationByGOrganization=" + user.getOrganization() + ")";

			List<AssignTeacherTask> liapply = this.iAssignTeacherTaskService.getListByHSQL(hql);

			tcstrids = new String[liapply.size()];
			List<Integer> lstTcids = new ArrayList<>();

			for (int i = 0; i < liapply.size(); i++) {
				AssignTeacherTask pa = liapply.get(i);

				int paid = pa.getTrainingCollege().getId();
				if (!lstTcids.contains(paid)) {
					String sr = "{\"tid\":" + paid + ",\"name\":\"" + pa.getTrainingCollege().getName() + "\",\"number\":" + pa.getTeacherNumber() + "},";
					sb.append(sr);
					tcstrids[i] = paid + "";
					lstTcids.add(paid);
				}

			}
			if (liapply.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]");
		}

		sb.append(",");

		sb.append("\"left\":[");

		// 获取下级组织架构
		List li = this.iOrganization.getLevelOrganization(user.getOrganization());

		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			String sr = "{\"lid\":" + obj[0].toString() + ",\"name\":\"" + obj[1].toString() + "\",\"data\":[";
			for (String s : tcstrids) {
				if (s != null) {
					String key = obj[0].toString() + "_" + s;
					if (tmphs.containsKey(key)) {
						sr += "{\"id\":" + s + ",\"value\":" + tmphs.get(key) + "},";
					} else {
						sr += "{\"id\":" + s + ",\"value\":0},";
					}
				}
			}

			if (sr.endsWith(",")) {
				sr = sr.substring(0, sr.length() - 1);
			}
			sr += "]},";

			sb.append(sr);
		}

		if (li.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");

		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * 编辑数据 action
	 * 
	 * @return
	 */
	public void editAssignTeacherTask() {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		adminLevel = String.valueOf(user.getOrganizationLevel());
		try {

			Map<String, String[]> parMap = request.getParameterMap();
			Organization organization = iOrganization.get(user.getOrganization());
			String allotProjectId = parMap.get("allotProjectId")[0];
			String allotSubjectId = parMap.get("allotSubjectId")[0];
			String allotLevel = parMap.get("allotLevel")[0];

			int projectid = Integer.valueOf(allotProjectId);
			short subjectid = Short.valueOf(allotSubjectId);
			List<AssignTeacherTask> lstAssignTeacherTasks = new ArrayList<>();
			lstAssignTeacherTasks = iAssignTeacherTaskService.getlist(projectid, subjectid, organization.getId(), allotLevel);
			
			int oridn = user.getOrganization();
			boolean flagEdit = false;
			Project p = this.iProjectService.get(projectid);
			if(p != null){
				ProjectAdmin pa = this.iProjectAdminService.get(p.getCreator());
				if(pa != null){
					int org = pa.getOrganization().getId();
					if(org == oridn){
						flagEdit = true;
					}
				}
			}
			
			String assginTeacher = "";
			if(parMap.get("assginTeacher") != null){
				assginTeacher =  parMap.get("assginTeacher")[0];
				String[] vs = assginTeacher.split("#");
				
				// 循环获取组织架构和
				for (String str : vs) {
					AssignTeacherTask att = new AssignTeacherTask();
					String[] otvs = str.split(",");
					int orid = Integer.valueOf(otvs[0]); // 组织架构id
					int tcid = Integer.valueOf(otvs[1]); // 承训单位id
					int vid = Integer.valueOf(otvs[2]); // 填写的数字
					att.setTimeup(Timestamp.valueOf(this.timeup.trim() + " 23:59:59"));
					att.setStatus(Short.valueOf(this.status));
					att.setTeacherNumber(vid);
					att.setCreator(user.getId());
					att.setCreattime(new Timestamp(System.currentTimeMillis()));
					att.setLevel((short) (Short.parseShort(allotLevel) + 1));
					att.setOrganizationByGOrganization(iOrganization.get(orid));
					att.setOrganizationByPOrganization(organization);
					att.setProject(iProjectService.get(projectid));
					att.setReceiveFlag(Short.parseShort("0"));
					att.setTrainingCollege(iTrainingCollegeService.get(tcid));
					att.setTrainingSubject(iTrainingSubjectService.get(subjectid));
					att.setAssignTeacherTask(getParent(lstAssignTeacherTasks, att));
					AssignTeacherTask atemp = compareAssignTeacherTask(lstAssignTeacherTasks, att);
					if (atemp.getId() == null) {

						if (att.getTeacherNumber() > 0) {
							this.iAssignTeacherTaskService.add(att);
						}

					}

					else {
						if (atemp.getTeacherNumber() != att.getTeacherNumber() || 
								atemp.getStatus() != att.getStatus() || 
								atemp.getTimeup() != att.getTimeup()) {
							AssignTeacherTask aTask = atemp;
							// Calendar c = Calendar.getInstance();
							// c.set(att.getTimeup().getYear(),
							// att.getTimeup().getMonth(), att.getTimeup()
							// .getDate(), 23, 59, 59);
							// Timestamp ts1 = new Timestamp(c.getTime().getTime());
							aTask.setTimeup(Timestamp.valueOf(this.timeup.trim() + " 23:59:59"));
							aTask.setCreator(att.getCreator());
							aTask.setStatus(att.getStatus());
							aTask.setTeacherNumber(att.getTeacherNumber());
							this.iAssignTeacherTaskService.update(aTask);

							if (adminLevel.equals("1") || flagEdit) {
								String hqlString = "update AssignTeacherTask set status=" + att.getStatus() + ", timeup='" + aTask.getTimeup() + "' where project=" + projectid + " and trainingSubject=" + subjectid;
								iAssignTeacherTaskService.executeHSQL(hqlString);

							}

						}
					} 
				}
			}else{
				String str = request.getParameter("assId") == null ? "" : request.getParameter("assId");
				if(!"".equals(str)){
					AssignTeacherTask aTask = this.iAssignTeacherTaskService.get(Integer.parseInt(str));
					aTask.setTimeup(Timestamp.valueOf(this.timeup.trim() + " 23:59:59"));
					aTask.setStatus(Short.valueOf(this.status));
					this.iAssignTeacherTaskService.update(aTask);
				}
				
			}
			
			
			sendResponse("OK", "学员任务分配编辑成功");
			return;

		} catch (Exception ex) {
			ex.printStackTrace();
			sendResponse("ERROR", "学员任务分配异常失败");
			return;
		}
	}

	private AssignTeacherTask compareAssignTeacherTask(List<AssignTeacherTask> lstAssignTeacherTasks, AssignTeacherTask att) {
		AssignTeacherTask assignTeacherTask = new AssignTeacherTask();
		for (AssignTeacherTask atemp : lstAssignTeacherTasks) {
			if (atemp.getProject().getId() == att.getProject().getId() && atemp.getTrainingSubject().getId() == att.getTrainingSubject().getId() && atemp.getTrainingCollege().getId() == att.getTrainingCollege().getId()
					&& atemp.getOrganizationByGOrganization().getId() == att.getOrganizationByGOrganization().getId() && atemp.getOrganizationByPOrganization().getId() == att.getOrganizationByPOrganization().getId()) {
				assignTeacherTask = atemp;
				break;
			}
		}
		return assignTeacherTask;
	}

	private AssignTeacherTask getParent(List<AssignTeacherTask> lstAssignTeacherTasks, AssignTeacherTask att) {
		AssignTeacherTask assignTeacherTask = new AssignTeacherTask();
		for (AssignTeacherTask atemp : lstAssignTeacherTasks) {
			if (atemp.getProject().getId() == att.getProject().getId() && atemp.getTrainingSubject().getId() == att.getTrainingSubject().getId() && atemp.getTrainingCollege().getId() == att.getTrainingCollege().getId()
					&& atemp.getOrganizationByGOrganization().getId() == att.getOrganizationByPOrganization().getId() && atemp.getLevel() == (att.getLevel() - 1)) {
				assignTeacherTask = atemp;
				break;
			}
		}
		return assignTeacherTask;
	}

	/**
	 * 查看学员报送情况
	 */
	@SuppressWarnings("rawtypes")
	public String viewPage() {
		/**
		 * 1.以组织架构为key和以承训单位为key
		 */
		initServlert();
		String psid = request.getParameter("key");

		if (psid == null) {
			return "error";
		}

		String[] t = psid.split("-");

		String projectId = t[0];
		String subjectId = t[1];

		UserSession user = (UserSession) session.getAttribute("usersession");
		int orid = user.getOrganization();

		String hql = "from AssignTeacherTask t where t.project=" + projectId + " and t.trainingSubject=" + subjectId;
		hql += " and  t.organizationByPOrganization=" + orid;

		List<AssignTeacherTask> dataList = this.iAssignTeacherTaskService.getListByHSQL(hql);

		this.tchashMap = new LinkedHashMap<>();
		this.tchashMap.put("top", new LinkedHashMap<String, Object>()); // 头
		this.tchashMap.put("left", new LinkedHashMap<String, Object>()); // left
		this.tchashMap.put("value", new LinkedHashMap<String, Object>()); // 值
		this.tchashMap.put("oz", new LinkedHashMap<String, Object>()); // 组织架构值
		this.tchashMap.put("tc", new LinkedHashMap<String, Object>()); // 承训单位值
		this.tchashMap.put("total", new LinkedHashMap<String, Object>()); // 合计
		LinkedHashMap<String, Object> top = this.tchashMap.get("top");
		LinkedHashMap<String, Object> left = this.tchashMap.get("left");
		LinkedHashMap<String, Object> value = this.tchashMap.get("value");
		LinkedHashMap<String, Object> voz = this.tchashMap.get("oz");
		LinkedHashMap<String, Object> vtc = this.tchashMap.get("tc");
		LinkedHashMap<String, Object> vtotal = this.tchashMap.get("total");
		for (int i = 0; i < dataList.size(); i++) {

			AssignTeacherTask att = dataList.get(i);
			// if (att.getLevel() != 1 )
			// {
			Project p = att.getProject();
			TrainingCollege tc = att.getTrainingCollege();
			Organization o = att.getOrganizationByGOrganization();

			// 获取已经上报的人数
			String recordsSql = "SELECT count(*) ";
			recordsSql += "FROM teacher_training_records t,organization oz,teacher th ";
			recordsSql += "WHERE 1=1 AND th.organization=oz.id and t.teacher=th.id  and t.project=" + projectId + " and t.subject=" + subjectId;
			recordsSql += " and t.training_college=" + tc.getId();
			boolean isSchool = o.getIsschool();

			if (isSchool) {
				// 是学校
				recordsSql += " and oz.id=" + o.getId();
			} else {
				if (o.getOrganizationLevel().getLevel() == DictionyMap.ORGANIZATION_LEVEL_CITY) {

					recordsSql += " and ( (oz.pid in (select id from organization o where 1=1 and o.pid=" + o.getId() + ")) or ";
					recordsSql += "(oz.id in (select id from organization o where 1=1 and o.pid=" + o.getId() + ")) or oz.id=" + o.getId() + " )";

				} else if (o.getOrganizationLevel().getLevel() == DictionyMap.ORGANIZATION_LEVEL_COUNTY) {
					recordsSql += " and (oz.pid=" + o.getId() + " or oz.id=" + o.getId() + ")";
				}
			}

			// System.out.println(recordsSql);

			List li = this.iAssignTeacherTaskService.executeSQL(recordsSql, null);

			int count = Integer.valueOf(li.get(0).toString());

			if (i == 0) {
				baseInfo = new String[4];
				this.baseInfo[0] = p.getName();
				this.baseInfo[1] = att.getTrainingSubject().getName();
				this.baseInfo[2] = Utlity.timeSpanToDateString(att.getTimeup());
				this.baseInfo[3] = att.getStatus() == 1 ? "可报送" : "停止报送";
			}
			if (att.getLevel() != 1) {

				if (!vtotal.containsKey("total")) {
					int[] zi = new int[2];
					zi[0] = att.getTeacherNumber();
					zi[1] = count;
					vtotal.put("total", zi);
				} else {
					int[] zi = (int[]) vtotal.get("total");
					zi[0] += att.getTeacherNumber();
					zi[1] += count;

				}

				// 组织架构
				if (!voz.containsKey(o.getId().toString())) {
					int[] zi = new int[2];
					zi[0] = att.getTeacherNumber();
					zi[1] = count;
					voz.put(o.getId().toString(), zi);
				} else {
					int[] zi = (int[]) voz.get(o.getId().toString());
					zi[0] += att.getTeacherNumber();
					zi[1] += count;

				}

				// 承训单位
				if (!vtc.containsKey(tc.getId().toString())) {
					int[] zi = new int[2];
					zi[0] = att.getTeacherNumber();
					zi[1] = count;

					vtc.put(tc.getId().toString(), zi);

				} else {
					int[] zi = (int[]) vtc.get(tc.getId().toString());
					zi[0] += att.getTeacherNumber();
					zi[1] += count;

				}

				// 承训单位 名称
				if (!top.containsKey(tc.getName())) {
					top.put(tc.getId() + "", tc.getName());
				}

				// 组织架构名称
				if (!left.containsKey(o.getName())) {

					left.put(o.getId() + "", o.getName());

				}

				// 承训单位和组织架构值
				String key = o.getId() + "-" + tc.getId();
				// logger.info("ffffffff" + key);
				if (!value.containsKey(key)) {
					String[] svi = new String[2];
					svi[0] = att.getTeacherNumber().toString();
					svi[1] = count + "";
					value.put(key, svi);
				}

			}
		}

		return "view";
	}

	/**
	 * 获取学科 根据项目id 来判断 申报的学科
	 */
	@SuppressWarnings("rawtypes")
	public void getTrainingSubjectWithProject() {
		initServlert();

		String projectId = request.getParameter("projectId");
		String sql = "SELECT DISTINCT(t.subject) FROM project_apply t WHERE 1=1 AND t.proejct=" + projectId;
		List li = this.iTrainingSubjectService.executeSQL(sql, null);
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0; i < li.size(); i++) {
			short id = (short) li.get(i);
			TrainingSubject ts = this.iTrainingSubjectService.get(id);
			String sr = "{\"id\":" + ts.getId() + ",\"name\":\"" + ts.getName() + "\"},";
			sb.append(sr);
		}
		if (li.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	// 发送页面返回信息
	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	// 属性区

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public IAssignTeacherTaskService getiAssignTeacherTaskService() {
		return iAssignTeacherTaskService;
	}

	public void setiAssignTeacherTaskService(IAssignTeacherTaskService iAssignTeacherTaskService) {
		this.iAssignTeacherTaskService = iAssignTeacherTaskService;
	}

	public List<Project> getListProject() {
		return listProject;
	}

	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTimeup() {
		return timeup;
	}

	public void setTimeup(String timeup) {
		this.timeup = timeup;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssginTeacher() {
		return assginTeacher;
	}

	public void setAssginTeacher(String assginTeacher) {
		this.assginTeacher = assginTeacher;
	}

	public LinkedHashMap<String, LinkedHashMap<String, Object>> getTchashMap() {
		return tchashMap;
	}

	public void setTchashMap(LinkedHashMap<String, LinkedHashMap<String, Object>> tchashMap) {
		this.tchashMap = tchashMap;
	}

	public String[] getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String[] baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getAllotProjectId() {
		return allotProjectId;
	}

	public void setAllotProjectId(String allotProjectId) {
		this.allotProjectId = allotProjectId;
	}

	public String getAllotSubjectId() {
		return allotSubjectId;
	}

	public void setAllotSubjectId(String allotSubjectId) {
		this.allotSubjectId = allotSubjectId;
	}

	public String getAllotProjectName() {
		return allotProjectName;
	}

	public void setAllotProjectName(String allotProjectName) {
		this.allotProjectName = allotProjectName;
	}

	public String getAllotSubjectName() {
		return allotSubjectName;
	}

	public void setAllotSubjectName(String allotSubjectName) {
		this.allotSubjectName = allotSubjectName;
	}

	public String getAllotLevel() {
		return allotLevel;
	}

	public void setAllotLevel(String allotLevel) {
		this.allotLevel = allotLevel;
	}

	public String getAllotAssginTeacher() {
		return allotAssginTeacher;
	}

	public void setAllotAssginTeacher(String allotAssginTeacher) {
		this.allotAssginTeacher = allotAssginTeacher;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	/**
	 * @return the adminLevel
	 */
	public String getAdminLevel() {
		return adminLevel;
	}

	/**
	 * @param adminLevel
	 *            the adminLevel to set
	 */
	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}

	public String getEnrolltype() {
		return enrolltype;
	}

	public void setEnrolltype(String enrolltype) {
		this.enrolltype = enrolltype;
	}

	public String getAssId() {
		return assId;
	}

	public void setAssId(String assId) {
		this.assId = assId;
	}


	public String getIsEdit() {
		return isEdit;
	}
	

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

}
