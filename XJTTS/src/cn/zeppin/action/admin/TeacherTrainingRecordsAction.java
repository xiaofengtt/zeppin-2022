package cn.zeppin.action.admin;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.ChineseLanguageLevel;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.entity.Language;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Politics;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.entity.ProjectGroup;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeacherTrainingReplace;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.entity.teacherEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IAssignTeacherCheckService;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.service.IChineseLanguageLevelService;
import cn.zeppin.service.IEductionBackgroundService;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IGradeService;
import cn.zeppin.service.IIdentifyClasshoursService;
import cn.zeppin.service.IIdentifyClasshoursSubjectService;
import cn.zeppin.service.IJobDutyService;
import cn.zeppin.service.IJobTitleService;
import cn.zeppin.service.ILanguageService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IPoliticsService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectCycleService;
import cn.zeppin.service.IProjectGroupService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ISubjectService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeacherTrainingReplaceService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ??????????????????action
 * 
 * @author Administrator
 * 
 */
public class TeacherTrainingRecordsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private ITeacherTrainingRecordsService iTeacherTrainingRecords;

	private IOrganizationService iOrganization; // ????????????
	private ITrainingCollegeService iTrainingCollegeService; // ????????????
	private ITrainingSubjectService iTrainingSubjectService; // ????????????
	private IProjectApplyService iProjectApplyService; // ??????????????????
	private IProjectService iProjectService; // ????????????
	private IProjectTypeService iProjectTypeService; // ????????????
	private IProjectAdminService iProjectAdminService; // ???????????????
	private IAssignTeacherTaskService iAssignTeacherTaskService;
	private IIdentifyClasshoursService identifyClasshoursService;
	private IIdentifyClasshoursSubjectService identifyClasshoursSubjectService;

	private ITeacherService iTeacherService;// ????????????
	private IAreaService iAreaService;// ????????????
	private ILanguageService iLanguageService;// ????????????
	private ISubjectService iSubjectService;// ????????????
	private IGradeService iGradeService;// ????????????

	private ITeachingLanguageService iTeachingLanguageService;// ??????????????????
	private ITeachingGradeService iTeachingGradeService;// ??????????????????
	private ITeachingSubjectService iTeachingSubjectService;// ??????????????????

	private IEthnicService iEthnicService;// ??????
	private IJobDutyService iJobDutyService;// ??????
	private IJobTitleService iJobTitleService;// ??????

	private IEductionBackgroundService iEductionBackgroundService;// ??????
	private IPoliticsService iPoliticsService;// ????????????
	private IChineseLanguageLevelService iChineseLanguageLevelService;// ???????????????

	private IAssignTeacherCheckService iAssignTeacherCheckService;
	private ITrainingAdminService iTrainingAdminService;
	
	private ITeacherTrainingReplaceService iTeacherTrainingReplaceService;
	private IProjectGroupService iProjectGroupService;
	
	private IProjectCycleService iProjectCycleService;
	
	
	// adu
	private List<String> searchYear;
	private List<Project> searchReportTask;
	private String organizationLevel;
	private int level;
	// report
	private List<Politics> lstPolitics;// ????????????
	private List<Ethnic> lstEthnics;// ??????

	private List<JobTitle> lstJobTitles;// ??????
	private List<JobDuty> lstJobDuties;// ??????

	private List<Grade> lstGrades;// ??????
	private List<Language> lstLanguages;// ??????
	private List<Subject> lstSubjects;// ??????
	private List<EductionBackground> lstBackgrounds; // ??????
	private List<ChineseLanguageLevel> lstChineseLanguageLevels;// ???????????????
	
	private Teacher tTeacher;
//	private Teacher ttTeacher;
	private TeacherTrainingRecords ttRe;
	
	private teacherEx teacherEx;// ??????ex
	private teacherEx reTeacherEx;// ??????ex
	
	private Area province;// ???
	private Area city;// ???
	private Area county;// ???
	
	private String areas;
	private String areas2;
	
	private List<ProjectGroup> lstGroup;


	public TeacherTrainingRecordsAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * ?????????????????????????????????--??????????????????
	 */
	public String initAduPage() {

		initServlert();

		// ????????????????????????????????????
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
//		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_PLAN);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());enrollType
		map.put("enrollType", 1);
		map.put("projectCycle",1);//????????????
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		this.level=us.getOrganization();

		String key = request.getParameter("key") == null ? "" : request.getParameter("key");
		if(!"".equals(key)) {
			String projectId = key.split("-")[0];
			Project p = this.iProjectService.get(Integer.valueOf(projectId));
			if(p.getProjectCycle().getId() > 1) {
				return "adu65";
			}
		}
		return "adu";
	}
	
	/**
	 * ?????????????????????????????????--??????????????????
	 */
	public String initAdu65Page() {

		initServlert();

		// ????????????????????????????????????
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
//		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_PLAN);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());enrollType
		map.put("enrollType", 1);
		map.put("projectCycle",2);//????????????
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		this.level=us.getOrganization();
		return "adu65";
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	public String initReviewPage() {
		initServlert();

		// ????????????????????????????????????
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("organization", 26124);
		map.put("enrollType", 2);
		map.put("projectCycle",1);//????????????
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		this.level=us.getOrganization();
		
		String key = request.getParameter("key") == null ? "" : request.getParameter("key");
		if(!"".equals(key)) {
			String projectId = key.split("-")[0];
			Project p = this.iProjectService.get(Integer.valueOf(projectId));
			if(p.getProjectCycle().getId() > 1) {
				return "enrollReview65";
			}
		}
		
		return "enrollReview";
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	public String initReview65Page() {
		initServlert();

		// ????????????????????????????????????
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("organization", 26124);
		map.put("enrollType", 2);
		map.put("projectCycle",2);//????????????
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		this.level=us.getOrganization();
		
		return "enrollReview65";
	}

	/**
	 * ?????????????????????????????????
	 */
	public String initttRecordResult() {
		initServlert();
		// ????????????????????????????????????
		// UserSession us = (UserSession) session.getAttribute("usersession");
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap<String,Object>();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		return "presult";
	}

	public void ttRecordResult() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		List<Map<String, Object>> results = new ArrayList<>();
		if (Utlity.isNumeric(request.getParameter("id"))) {
			int projectId = Integer.valueOf(request.getParameter("id"));

			Organization currentOrg = this.iOrganization.get(us.getOrganization());
			List<ProjectApply> projectApplys = this.iProjectApplyService.getCheckedProjectApply(projectId);
			List<Organization> subOrgs = this.iOrganization.getOrganizationByPid(us.getOrganization());
			// ???????????????????????????????????????
			if (subOrgs != null && subOrgs.size() > 0) {
				for (Organization org : subOrgs) {
					for (ProjectApply projectApply : projectApplys) {
						Short trainingSubjectId = projectApply.getTrainingSubject().getId();
						Integer trainingCollegeId = projectApply.getTrainingCollege().getId();
						int planNum = 0;
						String resultId = trainingCollegeId.toString() + "_" + trainingSubjectId.toString() + "_" + org.getId().toString();
						AssignTeacherTask subTask = this.iAssignTeacherTaskService.getAssignTeacherTask(projectId, trainingSubjectId, trainingCollegeId, currentOrg.getId(), org.getId());
						if (subTask != null) {
							planNum = subTask.getTeacherNumber();
						}
						int realNum = this.iTeacherTrainingRecords.getTeacherTrainingRecordsCount(projectId, trainingSubjectId, trainingCollegeId, org);
						Map<String, Object> result = new HashMap<>();
						result.put("key", resultId);
						result.put("orgId", org.getId());
						result.put("orgName", org.getName());
						result.put("trainingid", projectApply.getTrainingCollege().getId());
						result.put("trainingname", projectApply.getTrainingCollege().getName());
						result.put("subjectid", projectApply.getTrainingSubject().getId());
						result.put("subjectname", projectApply.getTrainingSubject().getName());
						result.put("planNum", planNum);
						result.put("realNum", realNum);
						results.add(result);
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append("{\"status\":\"OK\",");
			sb.append("\"organization\":[");
			String comba = "";

			// ??????????????????????????????????????????????????????????????????????????????????????????
			for (Organization org : subOrgs) {
				boolean flag = false;
				for (Map<String, Object> map : results) {
					int planNum = (Integer) map.get("planNum");
					int realNum = (Integer) map.get("realNum");
					if (map.get("orgId").equals(org.getId()) && (planNum > 0 || realNum > 0)) {
						flag = true;
					}
				}
				if (flag) {
					sb.append(comba);
					sb.append("{\"id\":" + org.getId());
					sb.append(",\"name\":\"" + org.getName() + "\"}");
					comba = ",";
				}

			}
			// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
			comba = "";
			sb.append("],\"trainingsubject\":[");
			for (ProjectApply projectApply : projectApplys) {
				boolean flag = false;
				for (Map<String, Object> map : results) {
					int planNum = (Integer) map.get("planNum");
					int realNum = (Integer) map.get("realNum");
					if (map.get("trainingid").equals(projectApply.getTrainingCollege().getId()) && map.get("subjectid").equals(projectApply.getTrainingSubject().getId()) && (planNum > 0 || realNum > 0)) {
						flag = true;
					}
				}
				if (flag) {
					sb.append(comba);
					sb.append("{\"trainingid\":" + projectApply.getTrainingCollege().getId());
					sb.append(",\"trainingname\":\"" + projectApply.getTrainingCollege().getName());
					sb.append("\",\"subjectid\":" + projectApply.getTrainingSubject().getId());
					sb.append(",\"subjectname\":\"" + projectApply.getTrainingSubject().getName() + "\"}");
					comba = ",";
				}
			}
			// ?????????????????????????????????????????????????????????????????????
			comba = "";
			sb.append("],\"value\":{");
			for (Map<String, Object> map : results) {
				int planNum = (Integer) map.get("planNum");
				int realNum = (Integer) map.get("realNum");
				if (planNum > 0 || realNum > 0) {
					sb.append(comba);
					sb.append("\"" + map.get("key") + "\":[\"" + map.get("planNum") + "\",\"" + map.get("realNum") + "\"]");
					comba = ",";
				}
			}
			sb.append("}}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			Utlity.ResponseWrite("", "application/json", response);
		}
	}
	
	public String initttRecordGroupResult() {
		initServlert();
		// ?????????????????????????????????
		this.searchYear = this.iProjectService.getProjectYearList();
		this.lstGroup = this.iProjectGroupService.getList();

		return "pgresult";
	}

	public void ttRecordGroupResult() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String error = "{\"status\":\"ERROR\"}";
		Organization currentOrg = this.iOrganization.get(us.getOrganization());
		if (Utlity.isNumeric(request.getParameter("id"))) {
			int projectGroupId = Integer.valueOf(request.getParameter("id"));
			
			ProjectGroup pg = this.iProjectGroupService.get(projectGroupId);
			List<Project> lstPro = this.iProjectService.getProjectByGroup(pg.getId());

			if(lstPro != null && !lstPro.isEmpty()){
				StringBuilder sb = new StringBuilder();
				sb.append("{\"status\":\"OK\",");
				sb.append("\"records\":[");
				for(Project pro : lstPro){
					List<Map<String, Object>> results = new ArrayList<>();
					
					List<ProjectApply> projectApplys = this.iProjectApplyService.getCheckedProjectApply(pro.getId());
					List<Organization> subOrgs = this.iOrganization.getOrganizationByPid(us.getOrganization());
					// ???????????????????????????????????????
					if (subOrgs != null && subOrgs.size() > 0) {
						for (Organization org : subOrgs) {
							for (ProjectApply projectApply : projectApplys) {
								Short trainingSubjectId = projectApply.getTrainingSubject().getId();
								Integer trainingCollegeId = projectApply.getTrainingCollege().getId();
								int planNum = 0;
								String resultId = trainingCollegeId.toString() + "_" + trainingSubjectId.toString() + "_" + org.getId().toString();
								AssignTeacherTask subTask = this.iAssignTeacherTaskService.getAssignTeacherTask(pro.getId(), trainingSubjectId, trainingCollegeId, currentOrg.getId(), org.getId());
								if (subTask != null) {
									planNum = subTask.getTeacherNumber();
								}
								int realNum = this.iTeacherTrainingRecords.getTeacherTrainingRecordsCount(pro.getId(), trainingSubjectId, trainingCollegeId, org);
								Map<String, Object> result = new HashMap<>();
								result.put("key", resultId);
								result.put("orgId", org.getId());
								result.put("orgName", org.getName());
								result.put("trainingid", projectApply.getTrainingCollege().getId());
								result.put("trainingname", projectApply.getTrainingCollege().getName());
								result.put("subjectid", projectApply.getTrainingSubject().getId());
								result.put("subjectname", projectApply.getTrainingSubject().getName());
								result.put("planNum", planNum);
								result.put("realNum", realNum);
								results.add(result);
							}
						}
					}
		
//					StringBuilder sb = new StringBuilder();
					sb.append("{");
					sb.append("\"organization\":[");
					String comba = "";
		
					// ??????????????????????????????????????????????????????????????????????????????????????????
					for (Organization org : subOrgs) {
						boolean flag = false;
						for (Map<String, Object> map : results) {
							int planNum = (Integer) map.get("planNum");
							int realNum = (Integer) map.get("realNum");
							if (map.get("orgId").equals(org.getId()) && (planNum > 0 || realNum > 0)) {
								flag = true;
							}
						}
						if (flag) {
							sb.append(comba);
							sb.append("{\"id\":" + org.getId());
							sb.append(",\"name\":\"" + org.getName() + "\"}");
							comba = ",";
						}
		
					}
					// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
					comba = "";
					sb.append("],\"trainingsubject\":[");
					for (ProjectApply projectApply : projectApplys) {
						boolean flag = false;
						for (Map<String, Object> map : results) {
							int planNum = (Integer) map.get("planNum");
							int realNum = (Integer) map.get("realNum");
							if (map.get("trainingid").equals(projectApply.getTrainingCollege().getId()) && map.get("subjectid").equals(projectApply.getTrainingSubject().getId()) && (planNum > 0 || realNum > 0)) {
								flag = true;
							}
						}
						if (flag) {
							sb.append(comba);
							sb.append("{\"trainingid\":" + projectApply.getTrainingCollege().getId());
							sb.append(",\"trainingname\":\"" + projectApply.getTrainingCollege().getName());
							sb.append("\",\"subjectid\":" + projectApply.getTrainingSubject().getId());
							sb.append(",\"subjectname\":\"" + projectApply.getTrainingSubject().getName() + "\"}");
							comba = ",";
						}
					}
					// ?????????????????????????????????????????????????????????????????????
					comba = "";
					sb.append("],\"value\":{");
					for (Map<String, Object> map : results) {
						int planNum = (Integer) map.get("planNum");
						int realNum = (Integer) map.get("realNum");
						if (planNum > 0 || realNum > 0) {
							sb.append(comba);
							sb.append("\"" + map.get("key") + "\":[\"" + map.get("planNum") + "\",\"" + map.get("realNum") + "\"]");
							comba = ",";
						}
					}
					sb.append("}},");
				}
				
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else {
				Utlity.ResponseWrite(error, "application/json", response);
			}
		} else {
			Utlity.ResponseWrite(error, "application/json", response);
		}
	}

	/**
	 * ?????? ?????? ??????
	 */
	public void searchAduPage() {

		/**
		 * 1.???????????? ProjectId ??? SubjectId ???organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// ??????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String organizationid = request.getParameter("organizationName");
		organizationid = organizationid == null ? "0" : organizationid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String teacherName = request.getParameter("teacherName");
		
		String teacherOrg = request.getParameter("teacherOrg");
		teacherOrg = teacherOrg == null ? "0" : teacherOrg;
		if("".equals(teacherOrg)) {
			teacherOrg = "0";
		}

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;
		
		int isadmin = 0;
		if (us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {

			if (status.equals("1")) {
				// ?????????
				isadmin = 1;
				status = us.getOrganizationLevel() + "";
			} else if (status.equals("-2")) {
				isadmin = 2;
				status = us.getOrganizationLevel() + "";
			}
		} else {
			if (status.equals("-2")) {
				status = "2";
			}
		}

		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				String tmpTrainingUnit = tmpKey[2];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
				if (trainingUnit.equals("0")) {
					trainingUnit = tmpTrainingUnit;
				}
			}
		}

		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
		if (organizationid.equals("0")) {
			oz = this.iOrganization.get(us.getOrganization());
		}

		// ???????????????
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit)
				, isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));

		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit)
				, isadmin, Integer.valueOf(status), oz, lityps, offset, DictionyMap.maxPageSize, DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"?????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sbPack = new StringBuilder();
		sbPack.append("{");
		sbPack.append("\"status\":\"OK\"");

		sbPack.append(",");
		sbPack.append("\"page\":{");
		sbPack.append("\"currentPage\":" + start + ",");
		sbPack.append("\"totalPage\":" + totalPage);
		sbPack.append("}");

		sbPack.append(",");
		sbPack.append("\"records\":[");

		for (int i = 0; i < lidata.size(); i++) {

//			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) lidata.get(i);
//			Teacher th = (Teacher) obj[1];
			Teacher th = ttRecord.getTeacher();

			// TeachingSubject ts = (TeachingSubject) obj[2];
			// TeachingLanguage tl = (TeachingLanguage) obj[3];
			// TeachingGrade tg = (TeachingGrade) obj[4];

			// int projectId = ttRecord.getProject().getId();
			// int subjectId = ttRecord.getTrainingSubject().getId();
			// int tcid = ttRecord.getTrainingCollege().getId();
			// Object[] params = new Object[] { projectId, subjectId, tcid };
			// int level
			// =this.iAssignTeacherTaskService.getAssignTeacherTaskLevel(params,us);

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + ttRecord.getId() + "\",");

			if (us.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
				// ?????????????????????
				sb.append("\"level\":" + true + ",");
			} else {
				// ????????????????????????
				sb.append("\"level\":" + false + ",");
			}

			if (us.getOrganization() == ttRecord.getOrganization().getId().intValue() && us.getId().intValue() == ttRecord.getCreator().intValue()) {
				// ???????????????????????????
				sb.append("\"add\":" + true + ",");
			} else {
				// ??????????????????????????????
				sb.append("\"add\":" + false + ",");
			}

			sb.append("\"projectName\":\"" + ttRecord.getProject().getShortname() + "\",");
			sb.append("\"subjectName\":\"" + ttRecord.getTrainingSubject().getName() + "\",");
			sb.append("\"trainingName\":\"" + ttRecord.getTrainingCollege().getName() + "\",");
			sb.append("\"schoolName\":\"" + th.getOrganization().getName() + "\",");

			sb.append("\"idcard\":\"" + th.getIdcard() + "\",");
			sb.append("\"name\":\"" + th.getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");

			sb.append("\"sex\":\"" + th.getSex() + "\",");
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "???" : "???") + "\",");
			sb.append("\"age\":\"" + String.valueOf(Utlity.getAge(th.getBirthday())) + "\",");
			sb.append("\"ethnic\":\"" + th.getEthnic().getName() + "\",");
			sb.append("\"politics\":\"" + th.getPolitics().getName() + "\",");

			sb.append("\"teachAge\":\"" + String.valueOf(Utlity.getAge(th.getTeachingAge())) + "\",");
			sb.append("\"jobDuty\":\"" + th.getJobDuty().getName() + "\",");
			sb.append("\"jobTitle\":\"" + th.getJobTitle().getName() + "\",");
			sb.append("\"muLanguage\":\"" + (th.getMultiLanguage() ? "???" : "???") + "\",");

			// ??????????????????
			// sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() +
			// "\",");

			// ????????????????????????
			// sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");

			// ????????????????????????
			// sb.append("\"mainSubject\":\"" + ts.getSubject().getName() +
			// "\",");

			for (TeachingLanguage tl : th.getTeachingLanguages()) {
				if (tl.getIsprime()) {
					sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() + "\",");
					break;
				}
			}

			for (TeachingGrade tg : th.getTeachingGrades()) {
				if (tg.getIsprime()) {
					sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");
					break;
				}
			}

			for (TeachingSubject ts : th.getTeachingSubjects()) {
				if (ts.getIsprime()) {
					sb.append("\"mainSubject\":\"" + ts.getSubject().getName() + "\",");
					break;
				}
			}

			sb.append("\"area\":\"" + getAreaNaviString(th.getArea()) + "\",");

			sb.append("\"edubackground\":\"" + th.getEductionBackground().getName() + "\",");
			sb.append("\"chineseLanguageLevel\":\"" + th.getChineseLanguageLevel().getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");
			sb.append("\"authorized\":\"" + (th.getAuthorized() == 1 ? "??????" : "??????") + "\",");
			if(th.getStatus()==1){
				sb.append("\"workStatus\":\"??????\",");
			}else if(th.getStatus()==2){
				sb.append("\"workStatus\":\"??????\",");
			}else{
				sb.append("\"workStatus\":\"??????\",");
			}
			ProjectAdmin pa = this.iProjectAdminService.get(ttRecord.getCreator());
			if (pa != null) {
				sb.append("\"creator\":\"" + pa.getName() + "\",");
			} else {
				sb.append("\"creator\":\"" + "" + "\",");
			}

			int finalStatus = (int) ttRecord.getFinalStatus();

			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
				sb.append("\"status\":\"" + "?????????" + "\",");
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {

				// ??????????????????
				// ????????????????????????
				int checkStatus = (int) ttRecord.getCheckStatus();
				if (us.getOrganizationLevel() > checkStatus) {
					// ???????????? ??????????????????????????? ????????????
					// ?????? ????????????????????????
					sb.append("\"status\":\"" + "??????" + "\",");
				} else {
					sb.append("\"status\":\"" + "?????????" + "\",");
				}
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
				sb.append("\"status\":\"" + "?????????" + "\",");
			} else if(finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_REVOKE){
				sb.append("\"status\":\"" + "?????????" + "\",");
			}

			sb.append("\"teacherStatus\":\""+ttRecord.getReplaceStatus()+"\"");
			sb.append("},");
			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

	}
	
	/**
	 * ??????????????????????????????
	 */
	public void getAduPageStatusCount() {

		/**
		 * 1.???????????? ProjectId ??? SubjectId ???organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");


		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String organizationid = request.getParameter("organizationName");
		organizationid = organizationid == null ? "0" : organizationid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String teacherName = request.getParameter("teacherName");


		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;
		
		String teacherOrg = request.getParameter("teacherOrg");
		teacherOrg = teacherOrg == null ? "0" : teacherOrg;
		if("".equals(teacherOrg)) {
			teacherOrg = "0";
		}

		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				String tmpTrainingUnit = tmpKey[2];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
				if (trainingUnit.equals("0")) {
					trainingUnit = tmpTrainingUnit;
				}
			}
		}

		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
		if (organizationid.equals("0")) {
			oz = this.iOrganization.get(us.getOrganization());
		}
	
		/*
		 * -1 ??????
		 * 2 ?????????
		 * 1?????????
		 * -2??????
		 * 0?????????
		 * 3?????????
		 */

		String status = "-1";//??????
		int isadmin = 0;
		
		// ???????????????
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "2";//?????????
		int check = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "1";//?????????
		if(us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE){
			isadmin = 1;
			status = us.getOrganizationLevel() + "";
		}
		int noCheck = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "-2";//??????
		if(us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE){
			isadmin = 2;
			status = us.getOrganizationLevel() + "";
		}else{
			isadmin = 0;
			status = "2";
		}
		int pass = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "0";//?????????
		isadmin = 0;
		int noPass = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "3";//?????????
		isadmin = 0;
		int revote = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");//??????
		sb.append("\"check\":" + (check) + ",");//?????????
		sb.append("\"noCheck\":" + (noCheck) + ",");//?????????
		sb.append("\"pass\":" + (pass) + ",");//??????
		sb.append("\"revote\":" + (revote) + ",");//??????
		sb.append("\"noPass\":" + (noPass));//?????????
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
	}

	public void searchEnrollReviewPage() {
		/**
		 * 1.???????????? ProjectId ??? SubjectId ???organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// ??????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String organizationid = request.getParameter("organizationName");
		organizationid = organizationid == null ? "0" : organizationid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String teacherName = request.getParameter("teacherName");

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

		String teacherOrg = request.getParameter("teacherOrg");
		teacherOrg = teacherOrg == null ? "0" : teacherOrg;
		if("".equals(teacherOrg)) {
			teacherOrg = "0";
		}

		int isadmin = 0;
//		if (us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
//
//			if (status.equals("1")) {
//				// ?????????
//				isadmin = 1;
//				status = us.getOrganizationLevel() + "";
//			} else if (status.equals("-2")) {
//				isadmin = 2;
//				status = us.getOrganizationLevel() + "";
//			}
//		} else {
//			if (status.equals("-2")) {
//				status = "2";
//			}
//		}
		if (status.equals("-1")) {//????????????
			status = "-2";
		} else if (status.equals("-2")) {//???????????????
			status = "1";
		} else if (status.equals("1")) {//?????????
			status = "-1";
		}
			
		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				String tmpTrainingUnit = tmpKey[2];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
				if (trainingUnit.equals("0")) {
					trainingUnit = tmpTrainingUnit;
				}
			}
		}

		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
		if (organizationid.equals("0")) {
			oz = this.iOrganization.get(us.getOrganization());
		}

		// ???????????????
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));

		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, offset, DictionyMap.maxPageSize, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"?????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sbPack = new StringBuilder();
		sbPack.append("{");
		sbPack.append("\"status\":\"OK\"");

		sbPack.append(",");
		sbPack.append("\"page\":{");
		sbPack.append("\"currentPage\":" + start + ",");
		sbPack.append("\"totalPage\":" + totalPage);
		sbPack.append("}");

		sbPack.append(",");
		sbPack.append("\"records\":[");

		for (int i = 0; i < lidata.size(); i++) {

//			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) lidata.get(i);
//			Teacher th = (Teacher) obj[1];
			Teacher th = ttRecord.getTeacher();

			// TeachingSubject ts = (TeachingSubject) obj[2];
			// TeachingLanguage tl = (TeachingLanguage) obj[3];
			// TeachingGrade tg = (TeachingGrade) obj[4];

			// int projectId = ttRecord.getProject().getId();
			// int subjectId = ttRecord.getTrainingSubject().getId();
			// int tcid = ttRecord.getTrainingCollege().getId();
			// Object[] params = new Object[] { projectId, subjectId, tcid };
			// int level
			// =this.iAssignTeacherTaskService.getAssignTeacherTaskLevel(params,us);

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + ttRecord.getId() + "\",");

			if (us.getOrganizationLevel() == DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
				// ?????????????????????
				sb.append("\"level\":" + true + ",");
			} else {
				// ????????????????????????
				sb.append("\"level\":" + false + ",");
			}

			if (us.getOrganization() == ttRecord.getOrganization().getId().intValue()) {
				// ???????????????????????????
				sb.append("\"add\":" + true + ",");
			} else {
				// ??????????????????????????????
				sb.append("\"add\":" + false + ",");
			}

			sb.append("\"projectName\":\"" + ttRecord.getProject().getShortname() + "\",");
			sb.append("\"subjectName\":\"" + ttRecord.getTrainingSubject().getName() + "\",");
			sb.append("\"trainingName\":\"" + ttRecord.getTrainingCollege().getName() + "\",");
			sb.append("\"schoolName\":\"" + th.getOrganization().getName() + "\",");

			sb.append("\"idcard\":\"" + th.getIdcard() + "\",");
			sb.append("\"name\":\"" + th.getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");

			sb.append("\"sex\":\"" + th.getSex() + "\",");
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "???" : "???") + "\",");
			sb.append("\"age\":\"" + String.valueOf(Utlity.getAge(th.getBirthday())) + "\",");
			sb.append("\"ethnic\":\"" + th.getEthnic().getName() + "\",");
			sb.append("\"politics\":\"" + th.getPolitics().getName() + "\",");

			sb.append("\"teachAge\":\"" + String.valueOf(Utlity.getAge(th.getTeachingAge())) + "\",");
			sb.append("\"jobDuty\":\"" + th.getJobDuty().getName() + "\",");
			sb.append("\"jobTitle\":\"" + th.getJobTitle().getName() + "\",");
			sb.append("\"muLanguage\":\"" + (th.getMultiLanguage() ? "???" : "???") + "\",");

			// ??????????????????
			// sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() +
			// "\",");

			// ????????????????????????
			// sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");

			// ????????????????????????
			// sb.append("\"mainSubject\":\"" + ts.getSubject().getName() +
			// "\",");

			for (TeachingLanguage tl : th.getTeachingLanguages()) {
				if (tl.getIsprime()) {
					sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() + "\",");
					break;
				}
			}

			for (TeachingGrade tg : th.getTeachingGrades()) {
				if (tg.getIsprime()) {
					sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");
					break;
				}
			}

			for (TeachingSubject ts : th.getTeachingSubjects()) {
				if (ts.getIsprime()) {
					sb.append("\"mainSubject\":\"" + ts.getSubject().getName() + "\",");
					break;
				}
			}

			sb.append("\"area\":\"" + getAreaNaviString(th.getArea()) + "\",");

			sb.append("\"edubackground\":\"" + th.getEductionBackground().getName() + "\",");
			sb.append("\"chineseLanguageLevel\":\"" + th.getChineseLanguageLevel().getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");
			sb.append("\"authorized\":\"" + (th.getAuthorized() == 1 ? "??????" : "??????") + "\",");
			if(th.getStatus()==1){
				sb.append("\"workStatus\":\"??????\",");
			}else if(th.getStatus()==2){
				sb.append("\"workStatus\":\"??????\",");
			}else{
				sb.append("\"workStatus\":\"??????\",");
			}
//			ProjectAdmin pa = this.iProjectAdminService.get(ttRecord.getCreator());
//			if (pa != null) {
//				sb.append("\"creator\":\"" + pa.getName() + "\",");
//			} else {
//				sb.append("\"creator\":\"" + "????????????" + "\",");
//			}
			sb.append("\"creator\":\"" + "????????????" + "\",");

			int finalStatus = (int) ttRecord.getFinalStatus();

			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
				sb.append("\"status\":\"" + "?????????" + "\",");
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {

				sb.append("\"status\":\"" + "?????????" + "\",");
//				// ??????????????????
//				// ????????????????????????
//				int checkStatus = (int) ttRecord.getCheckStatus();
//				if (us.getOrganizationLevel() > checkStatus) {
//					// ???????????? ??????????????????????????? ????????????
//					// ?????? ????????????????????????
//					sb.append("\"status\":\"" + "??????" + "\",");
//				} else {
//					sb.append("\"status\":\"" + "?????????" + "\",");
//				}
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
				sb.append("\"status\":\"" + "?????????" + "\",");
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_REVOKE) {
				sb.append("\"status\":\"" + "?????????" + "\",");
			}

			sb.append("\"teacherStatus\":\"\"");
			sb.append("},");
			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

		
	}
	
	public void searchEnrollReviewStatusCount() {
		/**
		 * 1.???????????? ProjectId ??? SubjectId ???organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

//		// ??????
//		String ist = (String) request.getParameter("page");
//		if (ist == null || ist.equals("") || ist.equals("NaN")) {
//			ist = "1";
//		}
//		int start = Integer.parseInt(ist);

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String organizationid = request.getParameter("organizationName");
		organizationid = organizationid == null ? "0" : organizationid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String teacherName = request.getParameter("teacherName");

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

		String teacherOrg = request.getParameter("teacherOrg");
		teacherOrg = teacherOrg == null ? "0" : teacherOrg;
		if("".equals(teacherOrg)) {
			teacherOrg = "0";
		}

		int isadmin = 0;

			
		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				String tmpTrainingUnit = tmpKey[2];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
				if (trainingUnit.equals("0")) {
					trainingUnit = tmpTrainingUnit;
				}
			}
		}

		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
		if (organizationid.equals("0")) {
			oz = this.iOrganization.get(us.getOrganization());
		}
		
//		String status = request.getParameter("status");
//		status = status == null ? "-1" : status;
//		if (status.equals("-1")) {//????????????
//			status = "-2";
//		} else if (status.equals("-2")) {//???????????????
//			status = "1";
//		} else if (status.equals("1")) {//?????????
//			status = "-1";
//		}
		/*
		 * -1 ??????
		 * 1 ?????????
		 * -2 ??????
		 * 0 ?????????
		 */
		String status = "-2";//??????
		// ???????????????
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "-1";//?????????
		// ???????????????
		int noCheck = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "1";//?????????
		// ???????????????
		int pass = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "0";//?????????
		// ???????????????
		int noPass = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		status = "3";//?????????
		// ???????????????
		int revote = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString(), Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");//??????
		sb.append("\"noCheck\":" + (noCheck) + ",");//?????????
		sb.append("\"pass\":" + (pass) + ",");//??????
		sb.append("\"revote\":" + (revote) + ",");//??????
		sb.append("\"noPass\":" + (noPass));//?????????
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
	}
	
	
	
	/**
	 * ?????? ?????? ??????
	 * 
	 * @throws IOException
	 */
	public void outputAduPage() throws IOException {

		/**
		 * 1.???????????? ProjectId ??? SubjectId ???organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String organizationid = request.getParameter("organizationName");
		organizationid = organizationid == null ? "0" : organizationid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;

		String teacherName = request.getParameter("teacherName");
		
		String enrollType = "1";
		if(request.getParameter("enrollType") != null){
			enrollType = request.getParameter("enrollType");
		}

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

		String teacherOrg = request.getParameter("teacherOrg");
		teacherOrg = teacherOrg == null ? "0" : teacherOrg;
		if("".equals(teacherOrg)) {
			teacherOrg = "0";
		}

		int isadmin = 0;
		if (us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {

			if (status.equals("1")) {
				// ?????????
				isadmin = 1;
				status = us.getOrganizationLevel() + "";
			} else if (status.equals("-2")) {
				isadmin = 2;
				status = us.getOrganizationLevel() + "";
			}
		} else {
			if (status.equals("-2")) {
				status = "2";
			}
		}
		
		if(enrollType.equals("2")){
			if (status.equals("-1")) {//????????????
				status = "-2";
			} else if (status.equals("-2")) {//???????????????
				status = "1";
			} else if (status.equals("1")) {//?????????
				status = "-1";
			}
		}

		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				String tmpTrainingUnit = tmpKey[2];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
				if (trainingUnit.equals("0")) {
					trainingUnit = tmpTrainingUnit;
				}
			}
		}
		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
		if (organizationid.equals("0")) {
			oz = this.iOrganization.get(us.getOrganization());
		}
		
		Map<Integer,TeachingLanguage> tlMap = new HashMap<Integer, TeachingLanguage>();
		// ????????????????????????
		String hqlString = "from TeachingLanguage where isprime=true";
		String tMainLanguage = "";
		List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
		if (lstTeachingLanguages.size() > 0) {
//			TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
//			tMainLanguage = teachingLanguage.getLanguage().getName();
			for(TeachingLanguage tl : lstTeachingLanguages){
				tlMap.put(tl.getTeacher().getId(), tl);
			}
		}

		Map<Integer,TeachingGrade> tgMap = new HashMap<Integer, TeachingGrade>();
		// ????????????????????????
		hqlString = "from TeachingGrade where isprime=1";
		String tMainGrade = "";
		List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
		if (lstTeachingGrades.size() > 0) {
//			TeachingGrade teachingGrade = lstTeachingGrades.get(0);
//			tMainGrade = teachingGrade.getGrade().getName();
			for(TeachingGrade tl : lstTeachingGrades){
				tgMap.put(tl.getTeacher().getId(), tl);
			}
		}

		Map<Integer,TeachingSubject> tsMap = new HashMap<Integer, TeachingSubject>();
		// ????????????????????????
		hqlString = "from TeachingSubject where isprime=1";
		String tMainSubject = "";
		List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
		if (lsttTeachingSubjects.size() > 0) {
//			TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
//			tMainSubject = teachingSubject.getSubject().getName();
			for(TeachingSubject tl : lsttTeachingSubjects){
				tsMap.put(tl.getTeacher().getId(), tl);
			}
		}
		
		HashMap<String, Object> paParamMap = new HashMap<>();
		paParamMap.put("project", projectid);
		paParamMap.put("trainingSubject", subjectid);
		paParamMap.put("trainingCollege", trainingUnit);
		List<ProjectApply> paList = this.iProjectApplyService.getProjectApplyByParams(paParamMap, null, lityps);
		Map<String,ProjectApply> paMap = new HashMap<String,ProjectApply>();
		for(ProjectApply pa : paList){
			paMap.put(pa.getProject()+"_"+pa.getTrainingSubject()+"_"+pa.getTrainingCollege(), pa);
		}
		
		// ???????????????
		int offset = 0;
		int number = 0;
		List lidata = null;
		if(enrollType != null){
			number = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, enrollType, Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
			lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, offset, number, enrollType, Integer.valueOf(projectCycle), Integer.valueOf(teacherOrg));
		}
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "???????????????");
		XSSFRow row = s.createRow(0);
		String title[] = { "????????????", "????????????", "????????????","????????????","????????????","????????????", "????????????", "????????????", "????????????", "??????", "??????", "??????", "????????????", "??????", "??????", "??????", "??????", "??????", "??????", "????????????", "??????????????????", "??????????????????", "??????????????????", "????????????", "??????", "????????????", "??????" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
		for (int i = 0; i < lidata.size(); i++) {
//			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) lidata.get(i);
//			Teacher th = (Teacher) obj[1];
			Teacher th = ttRecord.getTeacher();
			t++;
			short tStatus = ttRecord.getFinalStatus();
			row = s.createRow(t);
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			
			String startTime = "";
			String endTime = "";
			ProjectApply pa = paMap.get(ttRecord.getProject()+"_"+ttRecord.getTrainingSubject()+"_"+ttRecord.getTrainingCollege());
			if(pa != null){
				if(pa.getTrainingStarttime() != null){
					startTime = Utlity.timeSpanToDateString(pa.getTrainingStarttime());
				}
				if(pa.getTrainingEndtime() != null){
					endTime = Utlity.timeSpanToDateString(pa.getTrainingEndtime());
				}
			}
			
			String tIdcard = th.getIdcard();
			String tName = th.getName();
			String tSex = (th.getSex() == 1 ? "???" : "???");
			String tAge = String.valueOf(Utlity.getAge(th.getBirthday()));
			String tEthnic = th.getEthnic().getName();
			String tPolitics = th.getPolitics().getName();
			String schoolName = th.getOrganization().getName();
			String tTeachAge = String.valueOf(Utlity.getAge(th.getTeachingAge()));
			String tDuty = "???";
			if(th.getJobDuty() != null){
//				System.out.println(th.getId());
				tDuty = th.getJobDuty().getName();
			}
			String tTitle = "???";
			if(th.getJobTitle() != null){
				tTitle = th.getJobTitle().getName();
			}
			
			String tMobile = th.getMobile() == null ? "":th.getMobile();
			String tEmail = th.getEmail() == null ? "":th.getEmail();
			String tstatus="";
			if(th.getStatus()==1){
				tstatus="??????";
			}else if(th.getStatus()==2){
				tstatus="??????";
			}else{
				tstatus="??????";
			}
			String authorized = th.getAuthorized() == 1? "??????":"??????";
			String tArea = "";
			Organization current = th.getOrganization().getOrganization();
			short tOrgLevel = current.getOrganizationLevel().getLevel();
			while (tOrgLevel > 1) {
				tArea = current.getName() + "  " + tArea;
				current = current.getOrganization();
				tOrgLevel = current.getOrganizationLevel().getLevel();
			}

			// ????????????????????????
//			String hqlString = "from TeachingLanguage where teacher=" + th.getId() + " and isprime=true";
//			String tMainLanguage = "";
//			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
//			if (lstTeachingLanguages.size() > 0) {
//				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
//				tMainLanguage = teachingLanguage.getLanguage().getName();
//			}
			if(tlMap.get(th.getId()) != null){
				tMainLanguage = tlMap.get(th.getId()).getLanguage().getName();
			}

			// ????????????????????????
//			hqlString = "from TeachingGrade where teacher=" + th.getId() + " and isprime=1";
//			String tMainGrade = "";
//			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
//			if (lstTeachingGrades.size() > 0) {
//				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
//				tMainGrade = teachingGrade.getGrade().getName();
//			}
			if(tgMap.get(th.getId()) != null){
				tMainGrade = tgMap.get(th.getId()).getGrade().getName();
			}

			// ????????????????????????
//			hqlString = "from TeachingSubject where teacher=" + th.getId() + " and isprime=1";
//			String tMainSubject = "";
//			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
//			if (lsttTeachingSubjects.size() > 0) {
//				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
//				tMainSubject = teachingSubject.getSubject().getName();
//			}
			if(tsMap.get(th.getId()) != null){
				tMainSubject = tsMap.get(th.getId()).getSubject().getName();
			}

			String tChineseLanguageLevel = th.getChineseLanguageLevel().getName();
			String tEductionBackground = th.getEductionBackground().getName();
			String ttStatus;
			if (tStatus == 0) {
				ttStatus = "?????????";
			} else if(tStatus == -1) {
				ttStatus = "?????????";
			} else {
				ttStatus = (tStatus == 1 ? "?????????" : "?????????");
			}
			
			String remarks = "";
//			System.out.println(ttRecord.getId());
			if(ttRecord.getReplaceStatus() == 1){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="????????? "+tt.getName()+" ??????";
			}else if(ttRecord.getReplaceStatus() == 2){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="???????????????  "+tt.getName();
			}
			String tInfo[] = { projectName, subjectName, trainingName, startTime, endTime, tName, tArea, schoolName, tIdcard, tAge, tSex, tEthnic, tPolitics, tEductionBackground, tTeachAge, authorized, tstatus, tDuty, tTitle, tChineseLanguageLevel, tMainSubject, tMainGrade, tMainLanguage, tMobile, tEmail, ttStatus, remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=teacherAdu.xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}


	/**
	 * ???????????????????????????????????????
	 * 
	 * @return
	 */
	public String initReortAsstPage() {
		initServlert();
		// ????????????????????????????????????
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}
		return "assPage";
	}
	
	/**
	 * ?????????????????????????????????
	 * 
	 * @return
	 */
	public String initTrainingStartPage() {
		return "trainingStartPage";
	}
	
	public void getTrainingProject(){
		initServlert();
		
		String year = this.request.getParameter("year");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
		List<Object[]> lip = this.iProjectApplyService.getProjectByParams(map);
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + -1 + ",\"name\":\"" + "??????" + "\"},");
		for(Object[] o : lip){
			Project p = (Project) o[1];
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * ??????????????????
	 */
	public void getAssignTaskProject(){
		initServlert();
		
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String year = this.request.getParameter("year");
		String projectType = this.request.getParameter("projectType");
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("0")){
			map.put("year", year);
		}
		if(projectType != null && !projectType.endsWith("0")){
			ProjectType pt = this.iProjectTypeService.get(Integer.valueOf(projectType));
			map.put("projectType.scode", pt.getScode());
		}
		List<Project> lip = this.iProjectService.getProjectByParams(map,null,lityps);
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");
		for(Project p : lip){
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * ??????????????????,?????????????????????
	 */
	public void getAssignTaskProjects(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String year = this.request.getParameter("year");
		String projectType = this.request.getParameter("projectType");
		Map<String,Object> map = new HashMap<String,Object>();
		if(year != null && !year.equals("0")){
			map.put("year", year);
		}
		if(projectType != null && !projectType.endsWith("0")){
			ProjectType pt = this.iProjectTypeService.get(Integer.valueOf(projectType));
			map.put("projectType.scode", pt.getScode());
		}
//		List<Project> lip = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		String projectCycle = request.getParameter("projectCycle") == null ? "" : request.getParameter("projectCycle");
		if("".equals(projectCycle)) {
			projectCycle = "0";
		}
		if(!"0".equals(projectCycle)) {
			map.put("projectCycle", projectCycle);
		}
		// map.put("level", us.getOrganizationLevel());
		List<Project> lip = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				lip.add(p);
			}
		}
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");
		for(Project p : lip){
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * ????????????????????????
	 */
	@SuppressWarnings("unchecked")
	public void getAssignTaskProjectsp(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String year = this.request.getParameter("year");
		String projectType = this.request.getParameter("projectType");
		Map<String,Object> map = new HashMap<String,Object>();
		if(year != null && !year.equals("0")){
			map.put("year", year);
		}
		if(projectType != null && !projectType.endsWith("0")){
			ProjectType pt = this.iProjectTypeService.get(Integer.valueOf(projectType));
			map.put("projectType.scode", pt.getScode());
		}
//		List<Project> lip = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", us.getOrganization());
		// map.put("level", us.getOrganizationLevel());
		List<Project> lip = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				lip.add(p);
			}
		}
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");
		for(Project p : lip){
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getAssignTaskSubject() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String projectId = this.request.getParameter("projectId");
		List<AssignTeacherTask> liatts = new ArrayList<AssignTeacherTask>();
		if(us.getRole()== 4){
			liatts = this.iAssignTeacherTaskService.getAssignTeacherTaskByProject(Integer.valueOf(projectId), 0);
		}else{
			liatts = this.iAssignTeacherTaskService.getAssignTeacherTaskByProject(Integer.valueOf(projectId), us.getOrganization());
		}
		Hashtable<Short, String> htSubject = new Hashtable<>();

		for (AssignTeacherTask att : liatts) {
			if (!htSubject.containsKey(att.getTrainingSubject().getId())) {
				htSubject.put(att.getTrainingSubject().getId(), att.getTrainingSubject().getName());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"subjects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");

		for (Iterator itr = htSubject.keySet().iterator(); itr.hasNext();) {
			short key = (short) itr.next();
			String name = (String) htSubject.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\",\"search\":\"" + name + pinyingUtil.getFirstSpell(name) + "\"},";
			sb.append(sr);
		}

		sb.delete(sb.length() - 1, sb.length());


		sb.append("]}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	public void getAssignTaskOrganization() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String projectId = this.request.getParameter("projectId");
		List<AssignTeacherTask> liatts = this.iAssignTeacherTaskService.getAssignTeacherTaskByProject(Integer.valueOf(projectId), us.getOrganization());
		Hashtable<Integer, String> htOrganization = new Hashtable<>();
		Hashtable<Short, String> htSubject = new Hashtable<>();
		Hashtable<Integer, String> htTrainingUnit = new Hashtable<>();

		for (AssignTeacherTask att : liatts) {
			if (!htOrganization.containsKey(att.getOrganizationByGOrganization().getId())) {
				htOrganization.put(att.getOrganizationByGOrganization().getId(), att.getOrganizationByGOrganization().getName());
			}
			if (!htSubject.containsKey(att.getTrainingSubject().getId())) {
				htSubject.put(att.getTrainingSubject().getId(), att.getTrainingSubject().getName());
			}
			if (!htTrainingUnit.containsKey(att.getTrainingCollege().getId())) {
				htTrainingUnit.put(att.getTrainingCollege().getId(), att.getTrainingCollege().getName());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"organizations\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");
		for (Iterator<Integer> itr = htOrganization.keySet().iterator(); itr.hasNext();) {
			int key = (int) itr.next();
			String name = (String) htOrganization.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\"},";
			sb.append(sr);
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append("]");

		sb.append(",\"subjects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");

		for (Iterator<Short> itr = htSubject.keySet().iterator(); itr.hasNext();) {
			short key = (short) itr.next();
			String name = (String) htSubject.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\"},";
			sb.append(sr);
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append("]");

		sb.append(",\"trainingUnits\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");

		for (Iterator<Integer> itr = htTrainingUnit.keySet().iterator(); itr.hasNext();) {
			int key = (int) itr.next();
			String name = (String) htTrainingUnit.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());

		sb.append("]}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * ?????? ?????? ?????? ??????
	 */
	public void searchReportAssPage() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		int rprojectId = Integer.valueOf(request.getParameter("projectName"));
		int rsubjectId = Integer.valueOf(request.getParameter("subjectName"));
		String year = request.getParameter("year");
		
		// ????????????Page
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		/**
		 * 1.??????????????????????????????????????? 2.?????????????????? ?????? ???????????????????????? ??????JSON ?????? page??????
		 */
		String projectlevel = "1,2";
//		int olevel = us.getOrganizationLevel();
//		if(olevel == 2){
//			projectlevel = "3";
//		} else if (olevel == 3) {
//			projectlevel = "3,4";
//		} else if (olevel == 4) {
//			projectlevel = "3,4,5";
//		}
		// ????????????????????????
		// ?????????????????? ?????????????????????????????????
		int records = this.getiAssignTeacherTaskService().getAssignTeacherTaskCount(year ,rprojectId,rsubjectId, us.getOrganization(), us,projectlevel);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		List<Map<String, Object>> results = this.getiAssignTeacherTaskService().getAssignTeacherTaskResults(year ,rprojectId,rsubjectId, us, offset, DictionyMap.maxPageSize,projectlevel);

		if (results == null || results.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"???????????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sbPack = new StringBuilder();
		sbPack.append("{");
		sbPack.append("\"status\":\"OK\"");
		sbPack.append(",");
		sbPack.append("\"page\":{");
		sbPack.append("\"currentPage\":" + startPage + ",");
		sbPack.append("\"totalPage\":" + totalPage);
		sbPack.append("}");
		sbPack.append(",");
		sbPack.append("\"records\":[");

		for (Map<String, Object> att : results) {
			String key = att.get("project") + "-" + att.get("subject") + "-" + att.get("training_college") + "-" + att.get("g_organization");
			if (att.get("p_organization") != null) {
				key += "-" + att.get("p_organization");
			} else {
				key += "-";
			}

			int teacherNum = (int) att.get("teacher_number");
			int passNum = (int) att.get("passnum");
			int noPassNum = (int) att.get("nopassnum");
			int totalNum = (int) att.get("totalnum");
			byte status = (byte) att.get("status");
			
			int projectCycle = (int) att.get("projectCycle");

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":" + att.get("id"));
			sb.append(",");

			sb.append("\"projectId\":" + att.get("project"));
			sb.append(",");

			sb.append("\"projectCycle\":" + att.get("projectCycle"));
			sb.append(",");
			
			sb.append("\"subjectId\":" + att.get("subject"));
			sb.append(",");

			sb.append("\"tcid\":" + att.get("training_college"));
			sb.append(",");

			sb.append("\"gorganizationId\":" + att.get("g_organization"));
			sb.append(",");

			if (att.get("p_organization") != null) {
				sb.append("\"porganizationId\":" + att.get("p_organization"));
				sb.append(",");
			} else {
				sb.append("\"porganizationId\":" + "");
				sb.append(",");
			}

			sb.append("\"key\":\"" + key + "\"");
			sb.append(",");

			sb.append("\"projectName\":\"" + att.get("projectname") + "\"");
			sb.append(",");

			sb.append("\"subjectName\":\"" + att.get("subjectname") + "\"");
			sb.append(",");

			sb.append("\"trainingName\":\"" + att.get("training_collegename") + "\"");
			sb.append(",");

			sb.append("\"organizationName\":\"" + att.get("g_organizationname") + "\"");
			sb.append(",");
			
			sb.append("\"enrollType\":" + att.get("enrollType"));
			sb.append(",");
			
			sb.append("\"teacherNum\":" + teacherNum);
			sb.append(",");

			sb.append("\"totalNum\":" + totalNum);
			sb.append(",");

			sb.append("\"passNum\":" + passNum);
			sb.append(",");

			sb.append("\"noPass\":" + noPassNum);
			sb.append(",");

			if (totalNum >= teacherNum) {
				sb.append("\"style\":" + 1);
				sb.append(",");
			} else {
				sb.append("\"style\":" + 2);
				sb.append(",");
			}

			sb.append("\"status\":\"" + status + "\"");
			sb.append(",");
			sb.append("\"status_name\":\"" + (status == 1 ? "?????????" : "????????????") + "\"");
			sb.append(",");

			int timeup_status = 1;
			if ((new Date()).getTime() > ((Timestamp) att.get("timeup")).getTime()) {
				timeup_status = 0;
			}
			Timestamp.valueOf(att.get("timeup").toString());
			sb.append("\"timeup_status\":\"" + timeup_status + "\"");
			sb.append(",");

			sb.append("\"timeup\":\"" + Utlity.timeSpanToDateString(att.get("timeup")) + "\"");
			sb.append(",");

			ProjectAdmin pa = this.iProjectAdminService.get((int) att.get("creator"));
			String taskCreator = pa.getOrganization().getName() + "-" + pa.getName();

			sb.append("\"creator\":\"" + taskCreator + "\"");
			sb.append(",");

			sb.append("\"creattime\":\"" + Utlity.timeSpanToString(att.get("creattime")) + "\"");
			sb.append(",");
			
			//training_starttime:????????????
			if(att.get("training_starttime") != null){
				sb.append("\"training_starttime\":\"" + Utlity.timeSpanToDateString(att.get("training_starttime")) + "\"");
			}else{
				sb.append("\"training_starttime\":\"??????\"");
			}
			
			
			sb.append("}");

			sbPack.append(sb.toString() + ",");

		}
		if (results.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}

		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

	}
	
	/**
	 * ??????????????????
	 */
	public void searchTrainingStartPage() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String starttime = request.getParameter("starttime");
		
		// ????????????Page
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		/**
		 * 1.??????????????????????????????????????? 2.?????????????????? ?????? ???????????????????????? ??????JSON ?????? page??????
		 */
		String projectlevel = "1,2";
		// ????????????????????????
		// ?????????????????? ?????????????????????????????????
		int records = this.getiAssignTeacherTaskService().getTrainingStartCount(starttime, us.getOrganization(), us,projectlevel);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		List<Map<String, Object>> results = this.getiAssignTeacherTaskService().getTrainingStartResults(starttime, us.getOrganization(), us, offset, DictionyMap.maxPageSize,projectlevel);

		if (results == null || results.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"???????????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sbPack = new StringBuilder();
		sbPack.append("{");
		sbPack.append("\"status\":\"OK\"");
		sbPack.append(",");
		sbPack.append("\"page\":{");
		sbPack.append("\"currentPage\":" + startPage + ",");
		sbPack.append("\"totalPage\":" + totalPage);
		sbPack.append("}");
		sbPack.append(",");
		sbPack.append("\"records\":[");

		for (Map<String, Object> att : results) {
			String key = att.get("project") + "-" + att.get("subject") + "-" + att.get("training_college") + "-" + att.get("g_organization");
			if (att.get("p_organization") != null) {
				key += "-" + att.get("p_organization");
			} else {
				key += "-";
			}

			int teacherNum = (int) att.get("teacher_number");
			int passNum = (int) att.get("passnum");
			int noPassNum = (int) att.get("nopassnum");
			int totalNum = (int) att.get("totalnum");
			byte status = (byte) att.get("status");

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":" + att.get("id"));
			sb.append(",");

			sb.append("\"projectId\":" + att.get("project"));
			sb.append(",");

			sb.append("\"subjectId\":" + att.get("subject"));
			sb.append(",");

			sb.append("\"tcid\":" + att.get("training_college"));
			sb.append(",");

			sb.append("\"gorganizationId\":" + att.get("g_organization"));
			sb.append(",");

			if (att.get("p_organization") != null) {
				sb.append("\"porganizationId\":" + att.get("p_organization"));
				sb.append(",");
			} else {
				sb.append("\"porganizationId\":" + "");
				sb.append(",");
			}

			sb.append("\"key\":\"" + key + "\"");
			sb.append(",");

			sb.append("\"projectName\":\"" + att.get("projectname") + "\"");
			sb.append(",");

			sb.append("\"subjectName\":\"" + att.get("subjectname") + "\"");
			sb.append(",");

			sb.append("\"trainingName\":\"" + att.get("training_collegename") + "\"");
			sb.append(",");

			sb.append("\"organizationName\":\"" + att.get("g_organizationname") + "\"");
			sb.append(",");
			
			sb.append("\"enrollType\":" + att.get("enrollType"));
			sb.append(",");
			
			sb.append("\"teacherNum\":" + teacherNum);
			sb.append(",");

			sb.append("\"totalNum\":" + totalNum);
			sb.append(",");

			sb.append("\"passNum\":" + passNum);
			sb.append(",");

			sb.append("\"noPass\":" + noPassNum);
			sb.append(",");

			if (totalNum >= teacherNum) {
				sb.append("\"style\":" + 1);
				sb.append(",");
			} else {
				sb.append("\"style\":" + 2);
				sb.append(",");
			}

			sb.append("\"status\":\"" + status + "\"");
			sb.append(",");
			sb.append("\"status_name\":\"" + (status == 1 ? "?????????" : "????????????") + "\"");
			sb.append(",");

			int timeup_status = 1;
			if ((new Date()).getTime() > ((Timestamp) att.get("timeup")).getTime()) {
				timeup_status = 0;
			}
			Timestamp.valueOf(att.get("timeup").toString());
			sb.append("\"timeup_status\":\"" + timeup_status + "\"");
			sb.append(",");

			sb.append("\"timeup\":\"" + Utlity.timeSpanToDateString(att.get("timeup")) + "\"");
			sb.append(",");

			ProjectAdmin pa = this.iProjectAdminService.get((int) att.get("creator"));
			String taskCreator = pa.getOrganization().getName() + "-" + pa.getName();

			sb.append("\"creator\":\"" + taskCreator + "\"");
			sb.append(",");

			sb.append("\"creattime\":\"" + Utlity.timeSpanToString(att.get("creattime")) + "\"");
			sb.append(",");
			
			//training_starttime:????????????
			if(att.get("training_starttime") != null){
				sb.append("\"training_starttime\":\"" + Utlity.timeSpanToDateString(att.get("training_starttime")) + "\"");
			}else{
				sb.append("\"training_starttime\":\"??????\"");
			}
			sb.append(",");
			
			if(att.get("training_endtime") != null){
				sb.append("\"training_endtime\":\"" + Utlity.timeSpanToDateString(att.get("training_endtime")) + "\"");
			}else{
				sb.append("\"training_endtime\":\"??????\"");
			}
			
			sb.append("}");

			sbPack.append(sb.toString() + ",");

		}
		if (results.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}

		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

	}
	
	// ?????????????????????????????????
	public String initReportPage() throws IOException {

		initServlert();

//		if (lstEthnics == null) {
//			// ??????
//			lstEthnics = iEthnicService.getEthnicByWight();
//		}
//
//		if (lstJobDuties == null) {
//			// ??????
//			lstJobDuties = iJobDutyService.findAll();
//		}
//
//		if (lstJobTitles == null) {
//			// ??????
//			lstJobTitles = iJobTitleService.findAll();
//		}
//
//		if (lstBackgrounds == null) {
//			// ??????
//			lstBackgrounds = iEductionBackgroundService.findAll();
//		}
//
//		if (lstPolitics == null) {
//			// ????????????
//			lstPolitics = iPoliticsService.findAll();
//
//		}
//
//		if (lstChineseLanguageLevels == null) {
//			// ???????????????
//			lstChineseLanguageLevels = iChineseLanguageLevelService.findAll();
//		}
//
//		if (lstGrades == null) {
//			// ??????
//			lstGrades = iGradeService.findAll();
//		}
//
//		if (lstSubjects == null) {// ??????
//			lstSubjects = iSubjectService.findAll();
//		}
//		if (lstLanguages == null) {
//			// ??????
//			lstLanguages = iLanguageService.findAll();
//		}

		return "report";
	}

	/**
	 * ?????? ???????????? ??????
	 */
	public void searchReportTeacher() {
		/**
		 * ?????? ??????
		 */
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization oz = this.iOrganization.get(us.getOrganization());

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teacherIdCard", request.getParameter("teacherIdCard"));
		paramsMap.put("teacherName", request.getParameter("teacherName"));
//		paramsMap.put("age1", request.getParameter("age1"));
//		paramsMap.put("age2", request.getParameter("age2"));
//		paramsMap.put("tage1", request.getParameter("tage1"));
//		paramsMap.put("tage2", request.getParameter("tage2"));
		paramsMap.put("school", request.getParameter("organization"));
//		paramsMap.put("sex", request.getParameter("sex"));
//		paramsMap.put("isMu", request.getParameter("isMu"));
//		paramsMap.put("ethnic", request.getParameter("ethnic"));
//		paramsMap.put("jobTitle", request.getParameter("jobTitle"));
//		paramsMap.put("chineseLanguageLevel", request.getParameter("chineseLanguageLevel"));
//		paramsMap.put("mainTeachingSubject", request.getParameter("mainGrade"));
//		paramsMap.put("politics", request.getParameter("politics"));
//		paramsMap.put("jobDuty", request.getParameter("jobDuty"));
//		paramsMap.put("eductionBackground", request.getParameter("eductionBackground"));
//		paramsMap.put("mainTeachingLanguage", request.getParameter("mainTeachingLanguage"));
//		paramsMap.put("mainTeachingGrades", request.getParameter("mainTeachingGrades"));
		paramsMap.put("organization", oz);
		paramsMap.put("report","0");

		String projectId = request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId");
		String collegeId = request.getParameter("collegeId");
		String gId = request.getParameter("gorganizationId");
		String pId = request.getParameter("porganizationId");

		String key = projectId + "-" + subjectId + "-" + collegeId + "-" + gId + "-" + pId;

		String project = projectId;
		String subject = subjectId;
		String trainingCollege = collegeId;

		// ????????????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		List liT = this.getiTeacherService().getTeachers(paramsMap, offset, DictionyMap.maxPageSize);

		if (liT == null || liT.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"?????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		int records = this.getiTeacherService().getTeacherCount(paramsMap);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"OK\"");
		sb.append(",");
		sb.append("\"page\":{");

		sb.append("\"currentPage\":" + startPage + ",");
		sb.append("\"totalPage\":" + totalPage);
		sb.append("}");

		sb.append(",");
		sb.append("\"records\":[");

		for (Object obj : liT) {
//			Object[] ot = (Object[]) obj;
			Teacher t = (Teacher) obj;
			Organization o = t.getOrganization();

			StringBuilder tb = new StringBuilder();
			tb.append("{");

			tb.append("\"idcard\":\"" + t.getIdcard() + "\",");
			tb.append("\"name\":\"" + t.getName().trim() + "\",");
			tb.append("\"school\":\"" + o.getName() + "\",");
			tb.append("\"mobile\":\"" + t.getMobile() + "\",");
			tb.append("\"sexIndex\":\"" + (t.getSex()) + "\",");
			tb.append("\"sex\":\"" + (t.getSex() == 1 ? "???" : "???") + "\",");
			tb.append("\"age\":\"" + String.valueOf(Utlity.getAge(t.getBirthday())) + "\",");
			tb.append("\"ethnic\":\"" + t.getEthnic().getName() + "\",");
			tb.append("\"politic\":\"" + t.getPolitics().getName() + "\",");

			tb.append("\"tage\":\"" + String.valueOf(Utlity.getAge(t.getTeachingAge())) + "\",");
			tb.append("\"jobTitle\":\"" + t.getJobTitle().getName() + "\",");
			tb.append("\"jobDuty\":\"" + t.getJobDuty().getName() + "\",");
			tb.append("\"isMu\":\"" + (t.getMultiLanguage() ? "???" : "???") + "\",");

			for (TeachingLanguage tl : t.getTeachingLanguages()) {
				if (tl.getIsprime()) {
					tb.append("\"language\":\"" + tl.getLanguage().getName() + "\",");
					break;
				}
			}

			for (TeachingGrade tg : t.getTeachingGrades()) {
				if (tg.getIsprime()) {
					tb.append("\"grade\":\"" + tg.getGrade().getName() + "\",");
					break;
				}
			}

			for (TeachingSubject ts : t.getTeachingSubjects()) {
				if (ts.getIsprime()) {
					tb.append("\"subject\":\"" + ts.getSubject().getName() + "\",");
					break;
				}
			}
			String status = "";
			if(t.getStatus()==1){
				status = "??????";
			}else if(t.getStatus()==2){
				status = "??????";
			}else if(t.getStatus()==3){
				status = "??????";
			}else if(t.getStatus()==4){
				status = "??????";
			}else{
				status = "??????";
			}
			tb.append("\"status\":\"" + status + "\",");
			String authorized = t.getAuthorized() == 1 ? "??????" : "??????";
			tb.append("\"authorized\":\"" + authorized + "\",");
			tb.append("\"area\":\"" + getAreaNaviString(t.getArea()) + "\",");
			tb.append("\"edubackground\":\"" + t.getEductionBackground().getName() + "\",");
			tb.append("\"languageLevel\":\"" + t.getChineseLanguageLevel().getName() + "\",");
			if(t.getCreator() != null){
				tb.append("\"creator\":\"" + this.iProjectAdminService.get(t.getCreator()).getName() + "\",");
			}else{
				tb.append("\"creator\":\"????????????\",");
			}
//			tb.append("\"creator\":\"" + this.iProjectAdminService.get(t.getCreator()).getName() + "\",");
			tb.append("\"creattime\":\"" + Utlity.timeSpanToString(t.getCreattime()) + "\",");
			tb.append("\"id\":\"" + key + "-" + t.getId().toString() + "\",");

			tb.append("\"teacherId\":" + t.getId());
			tb.append(",");

			tb.append("\"projectId\":" + projectId);
			tb.append(",");

			tb.append("\"subjectId\":" + subjectId);
			tb.append(",");

			tb.append("\"tcid\":" + collegeId);
			tb.append(",");

			tb.append("\"gorganizationId\":" + gId);
			tb.append(",");

			tb.append("\"porganizationId\":" + pId);
			tb.append(",");

			TeacherTrainingRecords ttr = this.getiTeacherTrainingRecords().getTeacherTrainingRecord(project, subject, trainingCollege, t.getId().toString());
			tb.append("\"record_status\":" + (ttr == null));

			tb.append("}");

			sb.append(tb.toString() + ",");
		}

		if (liT.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");

		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	/**
	 * ??????????????????
	 * 9???9??? ????????????????????? ????????????
	 * @throws InterruptedException
	 */
	public void addTeacherReport() throws InterruptedException {

		// ?????? gOrganizationId pOrganizationId
		// ?????????????????????????????????
		// ????????????????????????

		initServlert();

		String projectId = request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId");
		String tcid = request.getParameter("collegeId");
		String gOrgaId = request.getParameter("gorganizationId");
		String pOrgaId = request.getParameter("porganizationId");
		String teacherId = request.getParameter("teacherId");

//		System.out.println("addTeacher:" + projectId + "-" + subjectId + "-" + tcid + "-" + gOrgaId + "-" + pOrgaId + "-" + teacherId);

		String goon = request.getParameter("goon");

		UserSession us = (UserSession) session.getAttribute("usersession");

		StringBuffer sb = new StringBuffer();
		sb.append(" from AssignTeacherTask t where 1=1 ");
		if (projectId != null && projectId.length() > 0) {
			sb.append(" and t.project=");
			sb.append(projectId);
		}
		if (subjectId != null && subjectId.length() > 0) {
			sb.append(" and t.trainingSubject=");
			sb.append(subjectId);
		}
		if (tcid != null && tcid.length() > 0) {
			sb.append(" and t.trainingCollege=");
			sb.append(tcid);
		}
		if (gOrgaId != null && gOrgaId.length() > 0) {
			sb.append(" and t.organizationByGOrganization=");
			sb.append(gOrgaId);
		}
		if (pOrgaId != null && pOrgaId.length() > 0) {
			sb.append(" and t.organizationByPOrganization=");
			sb.append(pOrgaId);
		}

		List<AssignTeacherTask> liatts = this.iAssignTeacherTaskService.getListByHSQL(sb.toString());
		if (liatts != null && liatts.size() > 0) {
			AssignTeacherTask att = liatts.get(0);

			int totalCount = att.getTeacherNumber();

			// ???????????????????????????
			String ttrsqlCount = "select count(*) from teacher_training_records t where t.project=" + projectId;
			ttrsqlCount += " and t.subject=" + subjectId + " and t.training_college=" + tcid;

			List littrs = this.iTeacherTrainingRecords.executeSQL(ttrsqlCount, null);
			int ttrCount = Integer.valueOf(littrs.get(0).toString());

			if (ttrCount >= totalCount) {
				// ????????????????????????????????????????????????????????????
				if (goon == null || goon.equals("") || goon.equals("0")) {
					// ????????????????????????
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"WARNING\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "?????????????????????" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					return;
				}
			}

//			// ????????????????????????????????????????????????????????????????????????????????????
//			List<Project> projectList = this.iProjectService.getSameTypeProjectList(projectId);
//			StringBuilder trainedCount = new StringBuilder();
//			trainedCount.append("select count(*) from teacher_training_records t where 1=1");
//			trainedCount.append(" and t.teacher=").append(teacherId);
//			trainedCount.append(" and t.subject=").append(subjectId);
//			if (projectList != null && projectList.size() > 0) {
//				trainedCount.append(" and t.project in (");
//				String comba = "";
//				for (Project project : projectList) {
//					trainedCount.append(comba);
//					trainedCount.append(project.getId());
//					comba = ",";
//				}
//				trainedCount.append(")");
//			}
//			// trainedCount.append(" and t.final_status <>").append(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
//
//			littrs = this.iTeacherTrainingRecords.executeSQL(trainedCount.toString(), null);
//			ttrCount = Integer.valueOf(littrs.get(0).toString());
//			// ???????????????/????????????
//			if (ttrCount > 0) {
//				StringBuilder checkSB = new StringBuilder();
//				checkSB.append("{");
//				checkSB.append("\"Result\":\"FAIL\"");
//				checkSB.append(",");
//				TrainingSubject subject = this.iTrainingSubjectService.get(Short.valueOf(subjectId));
//				checkSB.append("\"Message\":\"" + "??????????????????????????????????????????????????????" + subject.getName() + "???????????????" + "\"");
//				checkSB.append("}");
//				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//				return;
//			}
//			//???????????????????????????????????????????????????
//			Map<String,Object> searchMap = new HashMap<String,Object>();
//			searchMap.put("project", projectId);
//			searchMap.put("trainingSubject", subjectId);
//			searchMap.put("trainingCollege", tcid);
//			ProjectApply pa = this.iProjectApplyService.getProjectApplyByParams(searchMap, null, null).get(0);
//			
//			StringBuilder trainingCount = new StringBuilder();
//			trainingCount.append("select count(*) from teacher_training_records ttr , project_apply pa where ttr.project=pa.proejct and ttr.subject=pa.subject and ttr.training_college=pa.training_college");
//			trainingCount.append(" and ttr.teacher=").append(teacherId);
//			trainingCount.append(" and ttr.project=").append(projectId);
//			if(pa.getTrainingStarttime() != null){
//				trainingCount.append(" and ((pa.training_starttime<'").append(pa.getTrainingStarttime()).append("'");
//				trainingCount.append(" and pa.training_endtime>'").append(pa.getTrainingStarttime()).append("')");
//				trainingCount.append(" or (pa.training_starttime>'").append(pa.getTrainingStarttime()).append("'");
//				trainingCount.append(" and pa.training_starttime<'").append(pa.getTrainingEndtime()).append("'))");
//			}
//			
//			littrs = this.iTeacherTrainingRecords.executeSQL(trainingCount.toString(), null);
//			ttrCount = Integer.valueOf(littrs.get(0).toString());
//			if (ttrCount > 0) {
//				StringBuilder checkSB = new StringBuilder();
//				checkSB.append("{");
//				checkSB.append("\"Result\":\"FAIL\"");
//				checkSB.append(",");
//				checkSB.append("\"Message\":\"" + "???????????????????????????????????????????????????" + "\"");
//				checkSB.append("}");
//				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//				return;
//			}
			// ??????????????????

			TeacherTrainingRecords ttr = new TeacherTrainingRecords();
			Project pj = this.iProjectService.get(Integer.valueOf(projectId));
			TrainingSubject ts = this.iTrainingSubjectService.get(Short.valueOf(subjectId));
			TrainingCollege tc = this.iTrainingCollegeService.get(Integer.valueOf(tcid));
			Teacher th = this.iTeacherService.get(Integer.valueOf(teacherId));
			// UUID uuid = UUID.randomUUID();
			String vid = Utlity.getUuidPwd(); // uuid.toString().replaceAll("-",
			// "");

			ttr.setProject(pj);
			ttr.setTrainingSubject(ts);
			ttr.setTrainingCollege(tc);

			ttr.setTeacher(th);
			ttr.setCreator(us.getId());

			Organization taddOra = this.iOrganization.get(us.getOrganization());
			ttr.setOrganization(taddOra);

			ttr.setCheckStatus((short) (us.getOrganizationLevel()));
			ttr.setUuid(vid);
			ttr.setIsPrepare(false);

			// ????????????
			ttr.setFinalStatus((short) 1);
			ttr.setTrainingStatus((short) 1);
			
			//??????????????????
			ttr.setReplaceStatus((short)0);
			ttr.setReplaceTeacher(0);

			if(pj.getEnrollType() == 2){
				if(pj.getTraintype() == 1 || pj.getTraintype() == 3){
					ttr.setIsConfirm((short)-1); //???????????? ?????????????????????
				} else {
					ttr.setIsConfirm((short)1);//????????????????????? ??????????????????
				}
			}

			// 9???9???????????????????????????
			newAddTeacherInfo(ttr);
			ttr.setProjectCycle(pj.getProjectCycle());
			
			this.iTeacherTrainingRecords.add(ttr);
			

			// ??????????????????

			AssignTeacherCheck atc = new AssignTeacherCheck();
			atc.setChecker(us.getId());
			atc.setTeacherTrainingRecords(ttr);
			atc.setType((short) 1);
			this.iAssignTeacherCheckService.add(atc);

			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"OK\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "??????????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);

		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "??????????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}

	}

	//????????????
	public void aduTeachers(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;
		if(projectid.equals("0")){
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"status\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"message\":\"" + "?????????????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		String organizationid = request.getParameter("organizationName");
		organizationid = organizationid == null ? "0" : organizationid;
		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;
		if(subjectid.equals("0")){
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"status\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"message\":\"" + "?????????????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		String status = request.getParameter("status");
		status = "1";
		String teacherName = request.getParameter("teacherName");
		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
		if (organizationid.equals("0")) {
			oz = this.iOrganization.get(us.getOrganization());
		}
		int isadmin = 0;
		// ???????????????
		int offset = 0;
		int number = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps);
		List lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, offset, number);
		for (int i = 0; i < lidata.size(); i++) {
			Object[] obj = (Object[]) lidata.get(i);
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			if(ttRecord.getFinalStatus()!=2){
				ttRecord.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
				this.iTeacherTrainingRecords.update(ttRecord);
				AssignTeacherCheck atc = new AssignTeacherCheck();
				atc.setTeacherTrainingRecords(ttRecord);
				atc.setType((short) 2);
				atc.setChecker(us.getId());
				this.iAssignTeacherCheckService.add(atc);
			}
		}
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"status\":\"OK\"");
		checkSB.append(",");
		checkSB.append("\"message\":\"" + "????????????" + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}
	
	// ?????? ????????????
	public void aduTeacherReport() {

		/**
		 * 1.key????????????,???????????????????????????????????? 2.method ?????????????????????????????? ****-level,*****-level
		 * ****-level 2.??????????????????????????????????????????????????????????????????????????????????????????
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");

		String key = request.getParameter("key");

		String method = request.getParameter("method");
		String reason= request.getParameter("reason");
		if(reason != null){
			try {
				reason = URLDecoder.decode(reason, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		String[] spKey = key.split(",");

		if (spKey.length > 0) {

			boolean flag = spKey.length > 1 ? true : false; // ???????????????

			String status = "";
			for (String spKeyStr : spKey) {
				String[] kls = spKeyStr.split("-");
				String ttrId = kls[0];
				String level = kls[1];

				TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.valueOf(ttrId));
				short checkStatus = ttr.getCheckStatus();

				if (ttr != null) {
					if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_ADU)) {

						// ??????????????????????????????????????????????????????
						if (level.equals("true")) {
							if(ttr.getReplaceStatus() > 0){
								if(ttr.getReplaceStatus() != 2){
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "???????????????????????????????????????????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
							// ???????????????
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {

								status = "?????????";

								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
								this.iTeacherTrainingRecords.update(ttr);

								// ??????????????????
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									atc.setTeacherTrainingRecords(ttr);
									if(reason!=null&&!reason.equals("")){
										atc.setReason(reason);
									}
									atc.setType((short) 2);
									atc.setChecker(us.getId());
									this.iAssignTeacherCheckService.add(atc);
								} catch (Exception ex) {
								}

								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"OK\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}

							} else {
								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "??????????????????????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						} else {

							// ???????????????????????????

							// ????????????????????????
							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// ???????????????????????? ????????????????????? ??????????????????????????????

								int uslevel = us.getOrganizationLevel();

								if (checkStatus >= uslevel) {
									// ????????????
									status = "??????";

									ttr.setCheckStatus((short) (uslevel - 1));
									this.iTeacherTrainingRecords.update(ttr);

									// ??????????????????
									try {
										AssignTeacherCheck atc = new AssignTeacherCheck();
										atc.setTeacherTrainingRecords(ttr);
										if(reason!=null&&!reason.equals("")){
											atc.setReason(reason);
										}
										atc.setType((short) 2);
										atc.setChecker(us.getId());
										this.iAssignTeacherCheckService.add(atc);
									} catch (Exception ex) {
									}

									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"OK\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "????????????" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}

								} else {
									// ????????????????????????????????????????????????
									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"ERROR\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "????????????,????????????????????????\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}

							} else {
								// ???????????????????????????????????????????????????
								if (!flag) {

									String msg = ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS ? "?????????" : "?????????";

									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "???????????????????????????" + msg + ",????????????????????????\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_NOADU)) {
						if(reason == null || "".equals(reason)){
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"OK\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "????????????????????????????????????" + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						}

						// ???????????????
						if (level.equals("true")) {
							if(ttr.getReplaceStatus() > 0){
								if(ttr.getReplaceStatus() != 2){
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "???????????????????????????????????????????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
							// ??????????????????
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {

								status = "?????????";
								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
								this.iTeacherTrainingRecords.update(ttr);

								// ??????????????????
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									if(reason!=null&&!reason.equals("")){
										atc.setReason(reason);
									}
									atc.setTeacherTrainingRecords(ttr);
									atc.setType((short) 3);
									atc.setChecker(us.getId());
									this.iAssignTeacherCheckService.add(atc);
								} catch (Exception ex) {
								}

								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"OK\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}

							} else {
								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "??????????????????????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
						} else {

							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// ???????????????????????? ????????????????????? ??????????????????????????????

								int uslevel = us.getOrganizationLevel();

								if (checkStatus + 1 >= uslevel) {
									// ????????????
									status = "?????????";

									ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
									this.iTeacherTrainingRecords.update(ttr);

									// ??????????????????
									try {
										AssignTeacherCheck atc = new AssignTeacherCheck();
										if(reason!=null&&!reason.equals("")){
											atc.setReason(reason);
										}
										atc.setTeacherTrainingRecords(ttr);
										atc.setType((short) 3);
										atc.setChecker(us.getId());
										this.iAssignTeacherCheckService.add(atc);
									} catch (Exception ex) {
									}

									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"OK\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "????????????" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}

								} else {
									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"ERROR\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "????????????,?????????????????????" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}

							} else {
								// ???????????????????????????????????????????????????
								if (!flag) {

									String msg = ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS ? "?????????" : "?????????";

									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "???????????????????????????" + msg + ",????????????????????????\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_DEL)) {//20161104??????????????????????????????

						if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {//???????????????????????????
							// ????????????
//							this.iTeacherTrainingRecords.delete(ttr);
							ttr.setFinalStatus((short)-1);//??????
							this.iTeacherTrainingRecords.update(ttr);
							//??????????????????
							// ??????????????????
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);//????????????
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 7);//????????????
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							status = "?????????";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"OK\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "????????????" + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						} else {
							status = "????????????????????????";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"ERROR\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + status + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						}

					}
				}// end if

			}// end for
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"OK\"");
			checkSB.append(",");
			checkSB.append("\"status\":\"" + status + "\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "??????????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}

	}

	// ?????? ????????????
	public void reviewTeacherReport() {

		/**
		 * 1.key????????????,???????????????????????????????????? 2.method ?????????????????????????????? ****-level,*****-level
		 * ****-level 2.??????????????????????????????????????????????????????????????????????????????????????????
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");

		String key = request.getParameter("key");

		String method = request.getParameter("method");
		String reason= request.getParameter("reason");

		String[] spKey = key.split(",");

		if (spKey.length > 0) {

			boolean flag = spKey.length > 1 ? true : false; // ???????????????

			String status = "";
			for (String spKeyStr : spKey) {
				String[] kls = spKeyStr.split("-");
				String ttrId = kls[0];
				String level = kls[1];

				TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.valueOf(ttrId));
				short checkStatus = ttr.getCheckStatus();
				if(checkStatus == -1){
					checkStatus = 6;
				}

				if (ttr != null) {
					if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_ADU)) {

						// ??????????????????????????????????????????????????????
						if (level.equals("true")) {
							// ???????????????
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
								
								if(ttr.getIsConfirm() == 0){//???????????????????????????????????????????????????
									status = "???????????????????????????";
									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"ERROR\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "???????????????????????????????????????????????????" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}

								status = "?????????";

								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
								ttr.setCheckStatus((short)us.getOrganizationLevel());
								this.iTeacherTrainingRecords.update(ttr);

								// ??????????????????
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									atc.setTeacherTrainingRecords(ttr);
									atc.setType((short) 2);
									if(reason!=null&&!reason.equals("")){
										atc.setReason(reason);
									}
									atc.setChecker(us.getId());
									this.iAssignTeacherCheckService.add(atc);
								} catch (Exception ex) {
								}

								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"OK\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}

							} else {
								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "??????????????????????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						} else {

							// ???????????????????????????

							// ????????????????????????
//							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// ???????????????????????? ????????????????????? ??????????????????????????????

							int uslevel = us.getOrganizationLevel();

//							if (checkStatus >= uslevel) {
								
								if(ttr.getIsConfirm() == 0){//???????????????????????????????????????????????????
									status = "???????????????????????????";
									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"ERROR\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "???????????????????????????????????????????????????" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}
								// ????????????
								status = "??????";

								ttr.setCheckStatus((short) (uslevel - 1));
								ttr.setFinalStatus((short)DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
								this.iTeacherTrainingRecords.update(ttr);

								// ??????????????????
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									atc.setTeacherTrainingRecords(ttr);
									if(reason!=null&&!reason.equals("")){
										atc.setReason(reason);
									}
									atc.setType((short) 2);
									atc.setChecker(us.getId());
									this.iAssignTeacherCheckService.add(atc);
								} catch (Exception ex) {
								}

								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"OK\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}

//							} else {
//								// ????????????????????????????????????????????????
//								if (!flag) {
//									StringBuilder checkSB = new StringBuilder();
//									checkSB.append("{");
//									checkSB.append("\"Result\":\"ERROR\"");
//									checkSB.append(",");
//									checkSB.append("\"status\":\"" + status + "\"");
//									checkSB.append(",");
//									checkSB.append("\"Message\":\"" + "????????????,????????????????????????\"");
//									checkSB.append("}");
//									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//									return;
//								}
//							}

						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_NOADU)) {

						// ???????????????
						if (level.equals("true")) {
							// ??????????????????
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {

								status = "?????????";
								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
								ttr.setCheckStatus((short)us.getOrganizationLevel());
								this.iTeacherTrainingRecords.update(ttr);

								// ??????????????????
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									if(reason!=null&&!reason.equals("")){
										atc.setReason(reason);
									}
									atc.setTeacherTrainingRecords(ttr);
									atc.setType((short) 3);
									atc.setChecker(us.getId());
									this.iAssignTeacherCheckService.add(atc);
								} catch (Exception ex) {
								}

								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"OK\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}

							} else {
								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "??????????????????????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
						} else {

//							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// ???????????????????????? ????????????????????? ??????????????????????????????

							int uslevel = us.getOrganizationLevel();

							if (checkStatus + 1 >= uslevel) {
								// ????????????
								status = "?????????";

								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
								ttr.setCheckStatus((short)us.getOrganizationLevel());
								this.iTeacherTrainingRecords.update(ttr);

								// ??????????????????
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									atc.setTeacherTrainingRecords(ttr);
									atc.setType((short) 3);
									atc.setChecker(us.getId());
									this.iAssignTeacherCheckService.add(atc);
								} catch (Exception ex) {
									ex.printStackTrace();
								}

								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"OK\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}

							} else {
								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "????????????,?????????????????????" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_DEL)) {

						if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {//???????????????????????????
							// ????????????
//							this.iTeacherTrainingRecords.delete(ttr);
							ttr.setFinalStatus((short)-1);//??????
							this.iTeacherTrainingRecords.update(ttr);
							//??????????????????
							// ??????????????????
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);//????????????
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 7);//????????????
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							status = "?????????";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"OK\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "????????????" + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						} else {
							status = "????????????????????????";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"ERROR\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "????????????????????????" + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						}

					}
				}// end if

			}// end for
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"OK\"");
			checkSB.append(",");
			checkSB.append("\"status\":\"" + status + "\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "??????????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}

	}
	
	/**
	 * ??????????????????????????????
	 */
	public void getTeacherReportInfomation() {

		initServlert();
		String psid = request.getParameter("key");

		String[] t = psid.split("-");
		String teacherId = t[0];

		int tid = Integer.valueOf(teacherId);

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"rows\":[");

		List<AssignTeacherCheck> setTtr = this.iAssignTeacherCheckService.getAssignTeacherChecksByTeacherRecordId(tid);
		int index = 1;
		for (AssignTeacherCheck atc : setTtr) {

			short type = atc.getType();
			String m = "";
			switch (type) {
			case 0:
				m = "????????????";
				break;
			case 1:
				m = "????????????";
				break;
			case 2:
				m = "????????????";
				break;
			case 3:
				m = "???????????????";
				break;
			case 4:
				m = "????????????";
				break;
			case 7:
				m = "??????";
				break;
			case 8:
				m = "???????????????";
				break;
			}
			

			String name = "";
			if(atc.getTeacherTrainingRecords().getIsConfirm() != null && atc.getTeacherTrainingRecords().getIsConfirm() == 0 && atc.getType() == 8){
				TrainingAdmin ta = this.iTrainingAdminService.get(atc.getChecker());
				name = ta.getTrainingCollege().getName() + ta.getName() + m;
				if(type==8&&atc.getReason()!=null){
					name=name+"</br>?????????"+atc.getReason();
				}

				String st = "{\"id\":" + index + ",\"time\":\"" + Utlity.timeSpanToString(atc.getChecktime()) + "\",\"info\":\"" + name + "\"},";

				sb.append(st);

				index++;
				continue;
			}
			
			if(atc.getType() == 6){//??????????????????
				TrainingAdmin ta = this.iTrainingAdminService.get(atc.getChecker());
				name = ta.getTrainingCollege().getName()+ta.getName()+"??????????????????";
				if(atc.getReason() != null){
					name=name+"</br>?????????" + atc.getReason();
				}
				String st = "{\"id\":" + index + ",\"time\":\"" + Utlity.timeSpanToString(atc.getChecktime()) + "\",\"info\":\"" + name + "\"},";

				sb.append(st);

				index++;
				continue;
			}
			
			if(atc.getType() == 0){//??????????????????
				String st = "{\"id\":" + index + ",\"time\":\"" + Utlity.timeSpanToString(atc.getChecktime()) + "\",\"info\":\"" + "??????????????????" + "\"},";

				sb.append(st);

				index++;
				continue;
			}
			
			if(atc.getType() == 7){//??????????????????
				if("??????????????????".equals(atc.getReason())){
					Teacher teacher = this.iTeacherService.get(atc.getChecker());
					name = teacher.getOrganization().getName() + teacher.getName() + m;
				} else {
					ProjectAdmin pa = this.iProjectAdminService.get(atc.getChecker());
					name = pa.getOrganization().getName() + pa.getName() + m;
				}
				if(atc.getReason() != null){
					name=name+"</br>?????????" + atc.getReason();
				}
				String st = "{\"id\":" + index + ",\"time\":\"" + Utlity.timeSpanToString(atc.getChecktime()) + "\",\"info\":\"" + name + "\"},";

				sb.append(st);

				index++;
				continue;
			}
			
			ProjectAdmin pa = this.iProjectAdminService.get(atc.getChecker());
			name = pa.getOrganization().getName() + pa.getName() + m;
			if(type==3&&atc.getReason()!=null){
				name=name+"</br>?????????"+atc.getReason();
			}else if(type==1 && atc.getReason()!=null){
				name=name+"</br>?????????"+atc.getReason();
			}else if(type == 7 && atc.getReason() != null){
				name=name+"</br>?????????"+atc.getReason();
			}
			

			String st = "{\"id\":" + index + ",\"time\":\"" + Utlity.timeSpanToString(atc.getChecktime()) + "\",\"info\":\"" + name + "\"},";

			sb.append(st);

			index++;
		}

		if (setTtr.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");

		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	public String getAreaNaviString(Area a) {
		String areaid = a.getName();
		String areaid1 = "";
		String areaid2 = "";
		String areacode = a.getParentcode();
		if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
			// ?????????????????????????????????
			Area pae = this.iAreaService.getAreaByCode(areacode);
			areaid1 = pae.getName();
			areacode = pae.getParentcode();
			// ????????????
			if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
				pae = this.iAreaService.getAreaByCode(areacode);
				areaid2 = pae.getName();
				return areaid2 + " > " + areaid1 + " > " + areaid;

			} else {
				return areaid1 + " > " + areaid;
			}
		} else {
			return areaid;
		}
	}

	/**
	 * ?????????????????????--????????????
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String initMarkPage() {
		initServlert();

		// ????????????????????????????????????
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		map.put("projectCycle", 1);
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		return "mark";
	}
	
	/**
	 * ?????????????????????--????????????
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String initMark65Page() {
		initServlert();

		// ????????????????????????????????????
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		map.put("projectCycle", 2);
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}

		return "mark65";
	}

	/**
	 * ??????????????????
	 */
	@SuppressWarnings("unchecked")
	public void getSearchMark() {

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// ?????????
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}
		// ???????????????
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = Integer.parseInt(ien);

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String name = request.getParameter("teacherName");
		
		String trainingStatus = request.getParameter("trainingStatus");
		trainingStatus = trainingStatus == null ? "-1" : trainingStatus;
		
		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;
		
		Organization oz = this.iOrganization.get(us.getOrganization());

		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pid", projectid);
		paramMap.put("sid", subjectid);
		paramMap.put("tid", trainingUnit);
		paramMap.put("status", "2");
		paramMap.put("name", name);
		paramMap.put("trainingStatus", trainingStatus);
		paramMap.put("projectCycle", projectCycle);
		paramMap.put("projectlevel", "1,2");
		// ???????????????
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);

		List lidata = this.iTeacherTrainingRecords.getAduTeacher(paramMap, oz, lityps, start, limit);

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\",");
			sb.append("\"Message\":\"?????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sbPack = new StringBuilder();
		sbPack.append("{");

		sbPack.append("\"Result\":\"OK\"");
		sbPack.append(",");

		sbPack.append("\"TotalRecordCount\":" + records);
		sbPack.append(",");
		sbPack.append("\"Records\":[");

		for (int i = 0; i < lidata.size(); i++) {

//			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) lidata.get(i);
			Teacher th = ttRecord.getTeacher();
			// ????????????????????????????????????,???????????????????????????????????????????????????

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + ttRecord.getId() + "\",");

			sb.append("\"projectName\":\"" + ttRecord.getProject().getName() + "\",");
			sb.append("\"subjectName\":\"" + ttRecord.getTrainingSubject().getName() + "\",");
			sb.append("\"trainingName\":\"" + ttRecord.getTrainingCollege().getName() + "\",");
			sb.append("\"schoolName\":\"" + th.getOrganization().getName() + "\",");

			sb.append("\"name\":\"" + th.getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");

			sb.append("\"uuid\":\"" + ttRecord.getUuid() + "\",");
			sb.append("\"sex\":\"" + th.getSex() + "\",");
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "???" : "???") + "\",");
			
			sb.append("\"studyhour\":").append(ttRecord.getStudyhour());
			sb.append(",");
			
			sb.append("\"certificate\":\"" + (ttRecord.getCertificate() == null ? "" : ttRecord.getCertificate()) + "\","); 

			sb.append("\"mark\":\"" + (ttRecord.getTrainingScore() == null ? "?????????" : ttRecord.getTrainingScore()) + "\",");

			switch (ttRecord.getTrainingStatus()) {
			case DictionyMap.TEACHER_TRAINING_RECORDS_Transaction:
				sb.append("\"status\":\"??????\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_NOReport:
				sb.append("\"status\":\"?????????\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_Report:
				sb.append("\"status\":\"?????????\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_Graduation:
				sb.append("\"status\":\"??????\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_NoGraduation:
				sb.append("\"status\":\"?????????\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_Fine:
				sb.append("\"status\":\"??????\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_Good:
				sb.append("\"status\":\"??????\",");
				break;
			}

			if (ttRecord.getRegistrant() != null && ttRecord.getRegistrant() > 0) {
				sb.append("\"show\":" + true + "");
			} else {
				sb.append("\"show\":" + false + "");
			}

			sb.append("},");
			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);
	}
	
	
	/**
	 * ????????????????????????????????????????????????
	 */
	@SuppressWarnings("unchecked")
	public void getSearchMarkStatus() {

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

//		// ?????????
//		String ist = (String) request.getParameter("jtStartIndex");
//		if (ist == null || ist.equals("") || ist.equals("NaN")) {
//			ist = "0";
//		}
//		// ???????????????
//		String ien = (String) request.getParameter("jtPageSize");
//		if (ien == null || ien.equals("")) {
//			ien = DictionyMap.maxPageSize + "";
//		}
//
//		int start = Integer.parseInt(ist);
//		int limit = Integer.parseInt(ien);

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String name = request.getParameter("teacherName");
		
		String trainingStatus = request.getParameter("trainingStatus");
		trainingStatus = trainingStatus == null ? "-1" : trainingStatus;

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;
		
		Organization oz = this.iOrganization.get(us.getOrganization());

		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pid", projectid);
		paramMap.put("sid", subjectid);
		paramMap.put("tid", trainingUnit);
		paramMap.put("status", "2");
		paramMap.put("name", name);
		paramMap.put("projectCycle", projectCycle);
		paramMap.put("projectlevel", "1,2");
		paramMap.put("trainingStatus", "-1");
		// ???????????????
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		
		paramMap.put("trainingStatus", "1");
		int noCheck = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		
		paramMap.put("trainingStatus", "2");
		int checkPass = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		
		paramMap.put("trainingStatus", "4");
		int noComplete = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		
		paramMap.put("trainingStatus", "3");
		int complete = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		
		paramMap.put("trainingStatus", "0");
		int other = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		
//		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");//??????
		sb.append("\"noCheck\":" + (noCheck) + ",");//?????????
		sb.append("\"checkPass\":" + (checkPass) + ",");//?????????
		sb.append("\"noComplete\":" + (noComplete) + ",");//?????????
		sb.append("\"complete\":" + (complete) + ",");//?????????
		sb.append("\"other\":" + (other));//?????????
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	/**
	 * ??????????????????
	 * @throws IOException 
	 */
	public void outputScore() throws IOException{
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		
		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

		String name = request.getParameter("teacherName");
		Organization oz = this.iOrganization.get(us.getOrganization());
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pid", projectid);
		paramMap.put("sid", subjectid);
		paramMap.put("tid", trainingUnit);
		paramMap.put("status", "2");
		paramMap.put("name", name);
		paramMap.put("projectlevel", "1,2");
		paramMap.put("projectCycle", projectCycle);
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		List li = this.iTeacherTrainingRecords.getAduTeacher(paramMap, oz, lityps, 0, records);
		
		HashMap<String, Object> paParamMap = new HashMap<>();
		paParamMap.put("project", projectid);
		paParamMap.put("trainingSubject", subjectid);
		paParamMap.put("trainingCollege", trainingUnit);
		List<ProjectApply> paList = this.iProjectApplyService.getProjectApplyByParams(paParamMap, null, lityps);
		Map<String,ProjectApply> paMap = new HashMap<String,ProjectApply>();
		
		List<ProjectCycle> listpc = this.iProjectCycleService.findAll();
		Map<Integer, ProjectCycle> mapPc = new HashMap<Integer, ProjectCycle>();
		if(listpc != null){
			for(ProjectCycle pc : listpc){
				mapPc.put(pc.getId(), pc);
			}
		}
		
		List<String> studyhourTitleList = new ArrayList<String>();
			
		for(ProjectApply pa : paList){
			paMap.put(pa.getProject()+"_"+pa.getTrainingSubject()+"_"+pa.getTrainingCollege(), pa);
			
			List<Map> studyhourList = JSONUtils.json2list(pa.getStudyhour(), Map.class);
			if(studyhourList != null) {
				for(Map map : studyhourList){
					String titleName = map.get("nameCN").toString();
					Boolean flagName = true;
					for(String title : studyhourTitleList){
						if(title.equals(titleName)){
							flagName = false;
						}
					}
					if(flagName){
						studyhourTitleList.add(map.get("nameCN").toString());
					}
				}
			} else {
				for(ProjectCycle pc : mapPc.values()){
					Integer startyear = Integer.valueOf(pc.getStartyear());
					Integer endyear = Integer.valueOf(pc.getEndyear());
					Integer projectyear = Integer.valueOf(pa.getProject().getYear());
					if((startyear <= projectyear) && (projectyear <= endyear)){
						studyhourList = JSONUtils.json2list(pc.getStudyhour(), Map.class);
						for(Map map : studyhourList){
							String titleName = map.get("nameCN").toString();
							Boolean flagName = true;
							for(String title : studyhourTitleList){
								if(title.equals(titleName)){
									flagName = false;
								}
							}
							if(flagName){
								studyhourTitleList.add(map.get("nameCN").toString());
							}
						}
					}
				}
			}
			
		}
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "???????????????");
		XSSFRow row = s.createRow(0);
		List<String> titleList = new ArrayList<String>();
		String[] titleArrayFront = { "????????????", "????????????", "????????????", "????????????", "????????????", "????????????", "????????????", "??????", "??????", "??????", "????????????", "??????", "??????", "??????","??????","????????????"};
		for(String title : titleArrayFront){
			titleList.add(title);
		}
		for(String title : studyhourTitleList){
			titleList.add(title);
		}
		String[] titleArrayBack = {"??????","????????????","????????????","???????????????","??????","??????2", "??????????????????", "????????????", "????????????","????????????", "????????????" };
		for(String title : titleArrayBack){
			titleList.add(title);
		}
		
		for (int j = 0; j < titleList.size(); j++) {
			row.createCell(j).setCellValue(titleList.get(j));
		}
		int t = 0;
		for (int i = 0; i < li.size(); i++) {
//			Object[] obj = (Object[]) li.get(i);
//			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
//			Teacher th = (Teacher) obj[1];
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) li.get(i);
//			System.out.println(ttRecord.getId());
			Teacher th = ttRecord.getTeacher();
			t++;
			short tStatus = ttRecord.getTrainingStatus();
			row = s.createRow(t);
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			String tIdcard = th.getIdcard();
			String tName = th.getName();
			String tSex = (th.getSex() == 1 ? "???" : "???");
			String tAge = String.valueOf(Utlity.getAge(th.getBirthday()));
			String tEthnic = th.getEthnic().getName();
			String tPolitics = th.getPolitics().getName();
			String schoolName = th.getOrganization().getName();
			String tTeachAge = String.valueOf(Utlity.getAge(th.getTeachingAge()));
			
//			String tDuty = th.getJobDuty().getName();
//			String tTitle = th.getJobTitle().getName();
			String tDuty = "???";
			if(th.getJobDuty() != null){
				tDuty = th.getJobDuty().getName();
			}
			String tTitle = "???";
			if(th.getJobTitle() != null){
				tTitle = th.getJobTitle().getName();
			}
			
			String tMobile = th.getMobile() == null ? "":th.getMobile();
//			String tEmail = th.getEmail() == null ? "":th.getEmail();
//			String tMobile = th.getMobile();	
			String tcertificate = ttRecord.getCertificate() == null ? "" : ttRecord.getCertificate();
			String tArea = "";
			Organization current = th.getOrganization().getOrganization();
			short tOrgLevel = current.getOrganizationLevel().getLevel();
			while (tOrgLevel > 1) {
				tArea = current.getName() + "  " + tArea;
				current = current.getOrganization();
				tOrgLevel = current.getOrganizationLevel().getLevel();
			}
			
			String ttStatus="";
			if (tStatus == 0) {
				ttStatus="????????????";
			}else if(tStatus == 1){
				ttStatus="?????????";
			}else if(tStatus == 2){
				ttStatus="?????????";
			}else if(tStatus == 3){
				ttStatus="????????????";
			}else if(tStatus == 4){
				ttStatus="???????????????";
			}else if(tStatus == 5){
				ttStatus="????????????";
			}else if(tStatus == 6){
				ttStatus="????????????";
			}
			String tScore="";
			if(ttRecord.getTrainingScore()!=null){
				tScore=ttRecord.getTrainingScore().toString();
			}
			
			List<String> studyhourInfoList = new ArrayList<String>();
			List<Map> studyhourList = JSONUtils.json2list(ttRecord.getStudyhour(), Map.class);
			if(studyhourList != null){
				for(String title : studyhourTitleList){
					Boolean titleFlag = true;
					for(Map map : studyhourList){
						String titleName = map.get("nameCN").toString();
						if(title.equals(titleName)){
							titleFlag = false;
							studyhourInfoList.add(map.get("value").toString());
						}
					}
					if(titleFlag){
						studyhourInfoList.add("--");
					}
				}
			}else{
				for (int j = 0; j < studyhourTitleList.size(); j++) {
					studyhourInfoList.add("--");
				}
			}
			
			//???????????? ?????????????????????????????????
			String remarks = "";
			if(ttRecord.getReplaceStatus() == 1){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="????????? "+tt.getName()+" ??????";
			}else if(ttRecord.getReplaceStatus() == 2){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="???????????????  "+tt.getName();
			}
			
			String remarks2 = "";
			if(ttRecord.getRemark1() != null && !"".equals(ttRecord.getRemark1())){
				remarks2 = ttRecord.getRemark1();
			}
			String startTime = "";
			String endTime = "";
			ProjectApply pa = paMap.get(ttRecord.getProject()+"_"+ttRecord.getTrainingSubject()+"_"+ttRecord.getTrainingCollege());
			if(pa != null){
				if(pa.getTrainingStarttime() != null){
					startTime = Utlity.timeSpanToDateString(pa.getTrainingStarttime());
				}
				if(pa.getTrainingEndtime() != null){
					endTime = Utlity.timeSpanToDateString(pa.getTrainingEndtime());
				}
			}
			
			//20180514??????????????????
			Organization currento = th.getOrganization();
			String ftype = currento.getFtype() == null ? "" : currento.getFtype();//??????????????????
			String type = currento.getType() == null ? "" : currento.getType();//????????????
			String education = th.getEductionBackground() == null ? "" : th.getEductionBackground().getName();//????????????
			String graduation = th.getGraduation() == null ? "" : th.getGraduation();//????????????
			String major = th.getMajor() == null ? "" : th.getMajor();//????????????
			
			List<String> tInfoList = new ArrayList<String>();
			String[] tInfoFront = { projectName, subjectName, trainingName, tName, tArea, schoolName, tIdcard, tAge, tSex, tEthnic, tPolitics, tTeachAge, tDuty, tTitle, tMobile, ttStatus};
			for(String info : tInfoFront){
				tInfoList.add(info);
			}
			for(String info : studyhourInfoList){
				tInfoList.add(info);
			}
			String[] tInfoBack = {tScore,startTime,endTime,tcertificate,remarks,remarks2,ftype,type,education,graduation,major };
			for(String info : tInfoBack){
				tInfoList.add(info);
			}
			for (int j = 0; j < tInfoList.size(); j++) {
				row.createCell(j).setCellValue(tInfoList.get(j));
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=score.xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		
	}
	
	public String initTeacherInfo(){
		initServlert();
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}
		return("teacherinfo");
	}
	
	public void teacherInfo(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		List<Map<String, Object>> results = new ArrayList<>();
		if (Utlity.isNumeric(request.getParameter("id"))) {
			int projectId = Integer.valueOf(request.getParameter("id"));

			Organization currentOrg = this.iOrganization.get(us.getOrganization());
			List<ProjectApply> projectApplys = this.iProjectApplyService.getCheckedProjectApply(projectId);
			List<Organization> subOrgs = this.iOrganization.getOrganizationByPid(us.getOrganization());
			// ???????????????????????????????????????
			if (subOrgs != null && subOrgs.size() > 0) {
				for (Organization org : subOrgs) {
					for (ProjectApply projectApply : projectApplys) {
						Short trainingSubjectId = projectApply.getTrainingSubject().getId();
						Integer trainingCollegeId = projectApply.getTrainingCollege().getId();
						int planNum = 0;
						String resultId = trainingCollegeId.toString() + "_" + trainingSubjectId.toString() + "_" + org.getId().toString();
						AssignTeacherTask subTask = this.iAssignTeacherTaskService.getAssignTeacherTask(projectId, trainingSubjectId, trainingCollegeId, currentOrg.getId(), org.getId());
						if (subTask != null) {
							planNum = subTask.getTeacherNumber();
						}
						int realNum = this.iTeacherTrainingRecords.getTeacherTrainingRecordsCount(projectId, trainingSubjectId, trainingCollegeId, org);
						int aduNum = this.iTeacherTrainingRecords.getTeacherTrainingRecordsAduCount(projectId, trainingSubjectId, trainingCollegeId, org);
						int registNum=this.iTeacherTrainingRecords.getTeacherTrainingRecordsRegistCount(projectId, trainingSubjectId, trainingCollegeId, org);
						int finishNum=this.iTeacherTrainingRecords.getTeacherTrainingRecordsFinishCount(projectId, trainingSubjectId, trainingCollegeId, org);
						Map<String, Object> result = new HashMap<>();
						result.put("key", resultId);
						result.put("orgId", org.getId());
						result.put("orgName", org.getName());
						result.put("trainingid", projectApply.getTrainingCollege().getId());
						result.put("trainingname", projectApply.getTrainingCollege().getName());
						result.put("subjectid", projectApply.getTrainingSubject().getId());
						result.put("subjectname", projectApply.getTrainingSubject().getName());
						result.put("planNum", planNum);
						result.put("realNum", realNum);
						result.put("aduNum", aduNum);
						result.put("registNum", registNum);
						result.put("finishNum", finishNum);
						results.add(result);
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append("{\"status\":\"OK\",");
			sb.append("\"organization\":[");
			String comba = "";

			// ??????????????????????????????????????????????????????????????????????????????????????????
			for (Organization org : subOrgs) {
				boolean flag = false;
				for (Map<String, Object> map : results) {
					int planNum = (Integer) map.get("planNum");
					int realNum = (Integer) map.get("realNum");
					if (map.get("orgId").equals(org.getId()) && (planNum > 0 || realNum > 0)) {
						flag = true;
					}
				}
				if (flag) {
					sb.append(comba);
					sb.append("{\"id\":" + org.getId());
					sb.append(",\"name\":\"" + org.getName() + "\"}");
					comba = ",";
				}

			}
			// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
			comba = "";
			sb.append("],\"trainingsubject\":[");
			for (ProjectApply projectApply : projectApplys) {
				boolean flag = false;
				for (Map<String, Object> map : results) {
					int planNum = (Integer) map.get("planNum");
					int realNum = (Integer) map.get("realNum");
					if (map.get("trainingid").equals(projectApply.getTrainingCollege().getId()) && map.get("subjectid").equals(projectApply.getTrainingSubject().getId()) && (planNum > 0 || realNum > 0)) {
						flag = true;
					}
				}
				if (flag) {
					sb.append(comba);
					sb.append("{\"trainingid\":" + projectApply.getTrainingCollege().getId());
					sb.append(",\"trainingname\":\"" + projectApply.getTrainingCollege().getName());
					sb.append("\",\"subjectid\":" + projectApply.getTrainingSubject().getId());
					sb.append(",\"subjectname\":\"" + projectApply.getTrainingSubject().getName() + "\"}");
					comba = ",";
				}
			}
			// ?????????????????????????????????????????????????????????????????????
			comba = "";
			sb.append("],\"value\":{");
			for (Map<String, Object> map : results) {
				int planNum = (Integer) map.get("planNum");
				int realNum = (Integer) map.get("realNum");
				int aduNum = (Integer) map.get("aduNum");
				int registNum = (Integer) map.get("registNum");
				int finishNum = (Integer) map.get("finishNum");
				if (planNum > 0 || realNum > 0) {
					sb.append(comba);
					sb.append("\"" + map.get("key") + "\":[\"" + map.get("planNum") + "\",\"" + map.get("realNum")  + "\",\"" + map.get("aduNum") + "\",\"" + map.get("registNum") + "\",\"" + map.get("finishNum")+ "\"]");
					comba = ",";
				}
			}
			sb.append("}}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			Utlity.ResponseWrite("", "application/json", response);
		}
	}
	
	public String initTeacherGroupInfo(){
		initServlert();
		// ?????????????????????????????????
		this.searchYear = this.iProjectService.getProjectYearList();
		this.lstGroup = this.iProjectGroupService.getList();
		return("teacherginfo");
	}
	
	public void teacherGroupInfo(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String error = "{\"status\":\"ERROR\"}";
		Organization currentOrg = this.iOrganization.get(us.getOrganization());
		
		if (Utlity.isNumeric(request.getParameter("id"))) {
			int projectGroupId = Integer.valueOf(request.getParameter("id"));

			/*
			 * ?????????????????????????????????????????????
			 */
			ProjectGroup pg = this.iProjectGroupService.get(projectGroupId);
			List<Project> lstPro = this.iProjectService.getProjectByGroup(pg.getId());
			
			if(lstPro != null && !lstPro.isEmpty()){
				StringBuilder sb = new StringBuilder();
				sb.append("{\"status\":\"OK\",");
				sb.append("\"records\":[");
				for(Project pro : lstPro){
					List<Map<String, Object>> results = new ArrayList<>();
					List<ProjectApply> projectApplys = this.iProjectApplyService.getCheckedProjectApply(pro.getId());
					List<Organization> subOrgs = this.iOrganization.getOrganizationByPid(us.getOrganization());
					// ???????????????????????????????????????
					if (subOrgs != null && subOrgs.size() > 0) {
						for (Organization org : subOrgs) {
							for (ProjectApply projectApply : projectApplys) {
								Short trainingSubjectId = projectApply.getTrainingSubject().getId();
								Integer trainingCollegeId = projectApply.getTrainingCollege().getId();
								int planNum = 0;
								String resultId = trainingCollegeId.toString() + "_" + trainingSubjectId.toString() + "_" + org.getId().toString();
								AssignTeacherTask subTask = this.iAssignTeacherTaskService.getAssignTeacherTask(pro.getId(), trainingSubjectId, trainingCollegeId, currentOrg.getId(), org.getId());
								if (subTask != null) {
									planNum = subTask.getTeacherNumber();
								}
								int realNum = this.iTeacherTrainingRecords.getTeacherTrainingRecordsCount(pro.getId(), trainingSubjectId, trainingCollegeId, org);
								int aduNum = this.iTeacherTrainingRecords.getTeacherTrainingRecordsAduCount(pro.getId(), trainingSubjectId, trainingCollegeId, org);
								int registNum=this.iTeacherTrainingRecords.getTeacherTrainingRecordsRegistCount(pro.getId(), trainingSubjectId, trainingCollegeId, org);
								int finishNum=this.iTeacherTrainingRecords.getTeacherTrainingRecordsFinishCount(pro.getId(), trainingSubjectId, trainingCollegeId, org);
								Map<String, Object> result = new HashMap<>();
								result.put("key", resultId);
								result.put("orgId", org.getId());
								result.put("orgName", org.getName());
								result.put("trainingid", projectApply.getTrainingCollege().getId());
								result.put("trainingname", projectApply.getTrainingCollege().getName());
								result.put("subjectid", projectApply.getTrainingSubject().getId());
								result.put("subjectname", projectApply.getTrainingSubject().getName());
								result.put("planNum", planNum);
								result.put("realNum", realNum);
								result.put("aduNum", aduNum);
								result.put("registNum", registNum);
								result.put("finishNum", finishNum);
								results.add(result);
							}
						}
					}
		
					sb.append("{");
					sb.append("\"organization\":[");
					String comba = "";
		
					// ??????????????????????????????????????????????????????????????????????????????????????????
					for (Organization org : subOrgs) {
						boolean flag = false;
						for (Map<String, Object> map : results) {
							int planNum = (Integer) map.get("planNum");
							int realNum = (Integer) map.get("realNum");
							if (map.get("orgId").equals(org.getId()) && (planNum > 0 || realNum > 0)) {
								flag = true;
							}
						}
						if (flag) {
							sb.append(comba);
							sb.append("{\"id\":" + org.getId());
							sb.append(",\"name\":\"" + org.getName() + "\"}");
							comba = ",";
						}
		
					}
					// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
					comba = "";
					sb.append("],\"trainingsubject\":[");
					for (ProjectApply projectApply : projectApplys) {
						boolean flag = false;
						for (Map<String, Object> map : results) {
							int planNum = (Integer) map.get("planNum");
							int realNum = (Integer) map.get("realNum");
							if (map.get("trainingid").equals(projectApply.getTrainingCollege().getId()) && map.get("subjectid").equals(projectApply.getTrainingSubject().getId()) && (planNum > 0 || realNum > 0)) {
								flag = true;
							}
						}
						if (flag) {
							sb.append(comba);
							sb.append("{\"trainingid\":" + projectApply.getTrainingCollege().getId());
							sb.append(",\"trainingname\":\"" + projectApply.getTrainingCollege().getName());
							sb.append("\",\"subjectid\":" + projectApply.getTrainingSubject().getId());
							sb.append(",\"subjectname\":\"" + projectApply.getTrainingSubject().getName() + "\"}");
							comba = ",";
						}
					}
					// ?????????????????????????????????????????????????????????????????????
					comba = "";
					sb.append("],\"value\":{");
					for (Map<String, Object> map : results) {
						int planNum = (Integer) map.get("planNum");
						int realNum = (Integer) map.get("realNum");
						if (planNum > 0 || realNum > 0) {
							sb.append(comba);
							sb.append("\"" + map.get("key") + "\":[\"" + map.get("planNum") + "\",\"" + map.get("realNum")  + "\",\"" + map.get("aduNum") + "\",\"" + map.get("registNum") + "\",\"" + map.get("finishNum")+ "\"]");
							comba = ",";
						}
					}
					sb.append("}},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else{
				Utlity.ResponseWrite(error, "application/json", response);
			}
			
		} else {
			Utlity.ResponseWrite(error, "application/json", response);
		}
	}
	
	/**
	 * ???????????????????????????
	 * ?????????????????????????????????????????????????????????
	 */
	public void inputScore() {
		initServlert();
		DecimalFormat df = new DecimalFormat("#");
		String pageStatus = "OK";
		String message = "??????????????????";
		String rString = "";
		String Path = "";
		HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
		List<Project> listProject = this.iProjectService.findAll();
		Map<String, Project> mapProject = new HashMap<String, Project>();
		for(Project pro : listProject){
			mapProject.put(pro.getName(), pro);
		}
		List<TrainingSubject> listSubject = this.iTrainingSubjectService.findAll();
		Map<String, TrainingSubject> subjectMap = new HashMap<String, TrainingSubject>();
		for(TrainingSubject pro : listSubject){
			subjectMap.put(pro.getName(), pro);
		}
		List<TrainingCollege> listCollege = this.iTrainingCollegeService.findAll();
		Map<String, TrainingCollege> collegeMap = new HashMap<String, TrainingCollege>();
		for(TrainingCollege pro : listCollege){
			collegeMap.put(pro.getName(), pro);
		}
		
//		List<Teacher> listTeacher = this.iTeacherService.findAll();
//		Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
//		for(Teacher pro : listTeacher){
//			teacherMap.put(pro.getIdcard(), pro);
//		}
		
		try {
			HashMap<Integer, HashMap<String, String>> p;
			initServlert();
			UserSession us = (UserSession) session.getAttribute("usersession");
			HttpSession session = request.getSession();
			hmFiles = (HashMap<EDocumentType, fileInfo>) session
					.getAttribute("uploadfile");
			if (hmFiles == null) {
				pageStatus = "ERROR";
				message = "?????????Excel";
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
						+ message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			if (hmFiles.size() > 0) {
				fileInfo fileInfo = new fileInfo();
				Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles
						.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<EDocumentType, fileInfo> entry = (Entry<EDocumentType, fileInfo>) iter
							.next();
					fileInfo = (fileInfo) entry.getValue();
//					String rootpath = this.getClass().getClassLoader()
//							.getResource("/").getPath()
//							+ "../..";
//					Path = rootpath + fileInfo.getFilePath();
				}
				//??????????????????????????????
				String rootpath = Utlity.getRealPath(ServletActionContext.getServletContext());
				Path = rootpath + fileInfo.getFilePath();
				Path = Path.replace("%20", " ");
				
				InputStream is = null;
				try {
					is = new FileInputStream(Path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				XSSFWorkbook wb = new XSSFWorkbook(is);
//				HSSFSheet s = wb.getSheet("????????????");
				XSSFSheet s = wb.getSheet("Sheet1");
				XSSFRow row;
				p = new HashMap<Integer, HashMap<String, String>>();
//				String rtrainingName = iTrainingCollegeService.get(
//						us.getOrganization()).getName();
				int t = s.getLastRowNum();
				for (int i = 1; i <= s.getLastRowNum(); i++) {// ??????Excel??????
					row = s.getRow(i);
					
//					String index = "";
					String projectName = "";
					String subjectName = "";
					String trainCollege = "";
					String teacherName = "";
					String organization = "";
					String school = "";
					String idcard = "";
					String phone = "";
					String email = "";
					String status = "";
					String reason = "";
					
//					if(row.getCell(0)!=null){
//						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//						index = row.getCell(0).getStringCellValue();
//					}
					if(row.getCell(0)!=null){
						row.getCell(0).setCellType(XSSFCell.CELL_TYPE_STRING);
						projectName = row.getCell(0).getStringCellValue();
					}
					if(row.getCell(1)!=null){
						row.getCell(1).setCellType(XSSFCell.CELL_TYPE_STRING);
						subjectName = row.getCell(1).getStringCellValue();
					}
					if(row.getCell(2)!=null){
						row.getCell(2).setCellType(XSSFCell.CELL_TYPE_STRING);
						trainCollege = row.getCell(2).getStringCellValue();
					}
					if(row.getCell(3)!=null){
						row.getCell(3).setCellType(XSSFCell.CELL_TYPE_STRING);
						teacherName = row.getCell(3).getStringCellValue();
					}
					if(row.getCell(4)!=null){
						row.getCell(4).setCellType(XSSFCell.CELL_TYPE_STRING);
						organization = row.getCell(4).getStringCellValue();
					}
					if(row.getCell(5)!=null){
						row.getCell(5).setCellType(XSSFCell.CELL_TYPE_STRING);
						school = row.getCell(5).getStringCellValue();
					}
					if(row.getCell(6)!=null){
						row.getCell(6).setCellType(XSSFCell.CELL_TYPE_STRING);
						idcard = row.getCell(6).getStringCellValue();
					}
					if(row.getCell(7)!=null){
						row.getCell(7).setCellType(XSSFCell.CELL_TYPE_STRING);
						phone = row.getCell(7).getStringCellValue();
					}
					if(row.getCell(8)!=null){
						row.getCell(8).setCellType(XSSFCell.CELL_TYPE_STRING);
						email = row.getCell(8).getStringCellValue();
					}
					if(row.getCell(9)!=null){
						row.getCell(9).setCellType(XSSFCell.CELL_TYPE_STRING);
						status = row.getCell(9).getStringCellValue();
					}
					if(row.getCell(10)!=null){
						row.getCell(10).setCellType(XSSFCell.CELL_TYPE_STRING);
						reason = row.getCell(10).getStringCellValue();
					}
					/*
					 * if (tStatus == 0) {
							ttStatus = "?????????";
						} else if(tStatus == -1) {
							ttStatus = "?????????";
						} else {
							ttStatus = (tStatus == 1 ? "?????????" : "?????????");
						}
					 */
//					int statusNum = 0;
//					if("?????????".equals(status)){
//						statusNum = 1;
//					} else if ("?????????".equals(status)){
//						statusNum = -1;
//					} else if ("?????????".equals(status)){
//						statusNum = 0;
//					} else if ("?????????".equals(status)){
//						statusNum = 2;
//					}
//					List ttrs = this.iTeacherTrainingRecords.getAduTeacherByIdcard(350000087, 251, 35, 0, idcard, 0, 100);
					Integer projectid = mapProject.get(projectName).getId();
					Short subjectid = subjectMap.get(subjectName).getId();
					Integer trainingUnit = collegeMap.get(trainCollege).getId();
					System.out.println(idcard);
					List ttrs = this.iTeacherTrainingRecords.getAduTeacher(idcard, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), 0, Integer.valueOf(-1), null, null, -1, -1, "1",1,0);
//					Integer teacherid = teacherMap.get(idcard).getId();
//					List ttrs = this.iTeacherTrainingRecords.getAduTeacherByIdcard(projectid, Integer.valueOf(subjectid), trainingUnit, 0, teacherid+"", -1, -1);
//					System.out.println("000000000000+"+ttrs.size());
//					if(ttrs.size()>0){
//						System.out.println("count:"+ttrs.size());
//						for(int j = 0; j < ttrs.size(); j++){
//							TeacherTrainingRecords ttr = (TeacherTrainingRecords) ttrs.get(j);
////							if(j > 0){
////								this.iTeacherTrainingRecords.delete(ttr);
////								continue;
////							}
//							if(ttr.getFinalStatus() == 2){
////								ttr.setProject(mapProject.get("??????????????????2017)????????????????????????????????????????????????????????????"));
////								ttr.setTrainingSubject(subjectMap.get("?????????????????????????????????????????????"));
////								ttr.setProject(mapProject.get("??????????????????2017???????????????????????????????????????????????????????????????"));
////								ttr.setTrainingCollege(collegeMap.get("????????????????????????"));
//								ttr.setTrainingStatus((short)1);
//								this.iTeacherTrainingRecords.update(ttr);
//							}
//							
////							if(ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS){
////								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
////								this.iTeacherTrainingRecords.update(ttr);
////								// ??????????????????
////								try {
////									AssignTeacherCheck atc = new AssignTeacherCheck();
//////									reason = "?????????????????????";
////									if(reason!=null&&!reason.equals("")){
////										atc.setReason(reason);
////									}
////									atc.setTeacherTrainingRecords(ttr);
////									atc.setType((short) 2);
////									atc.setChecker(350000004);
////									this.iAssignTeacherCheckService.add(atc);
////								} catch (Exception ex) {
////									ex.printStackTrace();
////								}
////							}
//							
//						}
////						Object[] obj = (Object[])ttrs.get(0);
////						TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
//////						System.out.println(ttr.getTeacher().getName());
////						
////						ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
////						this.iTeacherTrainingRecords.update(ttr);
////
////						// ??????????????????
////						try {
////							AssignTeacherCheck atc = new AssignTeacherCheck();
////							if(reason!=null&&!reason.equals("")){
////								atc.setReason(reason);
////							}
////							atc.setTeacherTrainingRecords(ttr);
////							atc.setType((short) 3);
////							atc.setChecker(us.getId());
////							this.iAssignTeacherCheckService.add(atc);
////						} catch (Exception ex) {
////						}
//					}
				}
			}
		} catch (IOException e) {
			pageStatus = "ERROR";
			message = "?????????????????????";
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
			Utlity.ResponseWrite(rString, "json", response);
		}
		rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
				+ "\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}

	/**
	 * ???????????????????????????
	 */
	public void getPercent() {
		initServlert();
		String pageStatus = "OK";
		String message = "";
		String rString = "";
		int percent = (int) session.getAttribute("percent");
		int maxIndex = (int) session.getAttribute("maxIndex");
		if (percent >= 0 && maxIndex >= 0) {
			message = "??????????????????";
		} else {
			pageStatus = "ERROR";
			message = "???????????????????????????,????????????";
		}
		rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
				+ "\",\"Percent\":\"" + percent + "\",\"MaxIndex\":\""
				+ maxIndex + "\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	public void delete() {
		initServlert();
		String id = request.getParameter("id");
		TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.parseInt(id));
		if(ttr != null){
			this.iTeacherTrainingRecords.delete(ttr);
		}
		String rString = "{\"Result\":\"OK\",\"Message\":\"??????\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	public void replaceTeacherInit(){
		initServlert();
		
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String ttrId = request.getParameter("ttrId");
		
		if(ttrId != null && !"".equals(ttrId)){
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.parseInt(ttrId));
//			if(us.getOrganizationLevel() > 1){
//				if (ttr.getCheckStatus() + 1 <= us.getOrganizationLevel()) {
//					sb.append("{");
//					sb.append("\"status\":\"ERROR\",");
//					sb.append("\"message\":\"????????????????????????????????????????????????????????????!\"");
//					sb.append("}");
//					Utlity.ResponseWrite(sb.toString(), "application/json", response);
//					return;
//				}
//			}
			AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService
					.get(ttr.getProject().getId().toString(), ttr.getTrainingSubject().getId().toString(), ttr.getTrainingCollege()
							.getId().toString(), Short.parseShort(us.getOrganizationLevel()+""));
			if(assignTeacherTask != null && assignTeacherTask.getStatus() != 1){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"?????????????????????????????????????????????!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if(ttr.getFinalStatus() == 1){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"???????????????????????????????????????????????????????????????????????????????????????????????????!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if(ttr.getTrainingStatus() >= 2){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"????????????????????????????????????????????????!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if(ttr.getFinalStatus() != 2){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"??????????????????????????????????????????????????????!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if(ttr.getReplaceStatus() != null && ttr.getReplaceStatus() == 1){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"???????????????????????????????????????????????????!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			if(ttr.getReplaceStatus() != null && ttr.getReplaceStatus() == 3){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"????????????????????????????????????????????????!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projectId", ttr.getProject().getId());
			params.put("subjectId", ttr.getTrainingSubject().getId());
			params.put("trainCollege", ttr.getTrainingCollege().getId());
			params.put("ttr", ttr.getId());
			params.put("status", 1);
			
			// ??????
			String sort = "id_desc";
			String[] sortArr = sort.split("_");
			Map<String, Object> sortParams = new HashMap<String, Object>();
			sortParams.put(sortArr[0], sortArr[1]);
			
			int count = this.iTeacherTrainingReplaceService.getRecordsListByParams(params, sortParams);
			if(count > 0){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"????????????????????????????????????????????????!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
		}
		sb.append("{");
		sb.append("\"status\":\"OK\",");
		sb.append("\"message\":\"??????!\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		return;

//		return "replace";
	}
	
	/**
	 * ???????????????????????????????????????
	 */
	public void searchReplaceTeachers(){
		/**
		 * ?????? ??????
		 */
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization oz = this.iOrganization.get(us.getOrganization());

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teacherIdCard", request.getParameter("teacherIdCard"));
		paramsMap.put("teacherName", request.getParameter("teacherName"));
//		paramsMap.put("age1", request.getParameter("age1"));
//		paramsMap.put("age2", request.getParameter("age2"));
//		paramsMap.put("tage1", request.getParameter("tage1"));
//		paramsMap.put("tage2", request.getParameter("tage2"));
		paramsMap.put("school", request.getParameter("organization"));
//		paramsMap.put("sex", request.getParameter("sex"));
//		paramsMap.put("isMu", request.getParameter("isMu"));
//		paramsMap.put("ethnic", request.getParameter("ethnic"));
//		paramsMap.put("jobTitle", request.getParameter("jobTitle"));
//		paramsMap.put("chineseLanguageLevel", request.getParameter("chineseLanguageLevel"));
//		paramsMap.put("mainTeachingSubject", request.getParameter("mainGrade"));
//		paramsMap.put("politics", request.getParameter("politics"));
//		paramsMap.put("jobDuty", request.getParameter("jobDuty"));
//		paramsMap.put("eductionBackground", request.getParameter("eductionBackground"));
//		paramsMap.put("mainTeachingLanguage", request.getParameter("mainTeachingLanguage"));
//		paramsMap.put("mainTeachingGrades", request.getParameter("mainTeachingGrades"));
		paramsMap.put("organization", oz);
		paramsMap.put("report","0");

		String projectId = request.getParameter("projectId") == null? "":request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId") == null? "":request.getParameter("subjectId");
		String collegeId = request.getParameter("collegeId") == null? "":request.getParameter("collegeId");
		String gId = request.getParameter("gorganizationId") == null? "0":request.getParameter("gorganizationId");
		String pId = request.getParameter("porganizationId") == null? "0":request.getParameter("porganizationId");
		
		String ttrId = request.getParameter("ttrId");
		String teacherId = "";
		if(ttrId != null && !"".equals(ttrId)){
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.parseInt(ttrId));
			projectId = ttr.getProject().getId().toString();
			subjectId = ttr.getTrainingSubject().getId().toString();
			collegeId = ttr.getTrainingCollege().getId().toString();
			teacherId = ttr.getTeacher().getId().toString();
		}

		gId = us.getOrganization()+"";
		if(oz.getOrganization() != null){
			pId = oz.getOrganization().getId()+"";
		}else{
			pId = us.getOrganization()+"";
		}
//		pId = oz.getOrganization().getId()+"";
		
		String key = projectId + "-" + subjectId + "-" + collegeId + "-" + gId + "-" + pId;

		String project = projectId;
		String subject = subjectId;
		String trainingCollege = collegeId;

		// ????????????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		@SuppressWarnings("rawtypes")
		List liT = this.getiTeacherService().getTeachers(paramsMap, offset, DictionyMap.maxPageSize);

		if (liT == null || liT.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"?????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		int records = this.getiTeacherService().getTeacherCount(paramsMap);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"OK\"");
		sb.append(",");
		sb.append("\"page\":{");

		sb.append("\"currentPage\":" + startPage + ",");
		sb.append("\"totalPage\":" + totalPage);
		sb.append("}");

		sb.append(",");
		sb.append("\"records\":[");

		for (Object obj : liT) {
//			Object[] ot = (Object[]) obj;
			Teacher t = (Teacher) obj;
			if(teacherId.equals(t.getId().toString())){//?????????????????????????????????????????????
				continue;
			}
			
			
			Organization o = t.getOrganization();

			StringBuilder tb = new StringBuilder();
			tb.append("{");

			tb.append("\"idcard\":\"" + t.getIdcard() + "\",");
			tb.append("\"name\":\"" + t.getName().trim() + "\",");
			tb.append("\"school\":\"" + o.getName() + "\",");
			tb.append("\"mobile\":\"" + t.getMobile() + "\",");
			tb.append("\"sexIndex\":\"" + (t.getSex()) + "\",");
			tb.append("\"sex\":\"" + (t.getSex() == 1 ? "???" : "???") + "\",");
			tb.append("\"age\":\"" + String.valueOf(Utlity.getAge(t.getBirthday())) + "\",");
			tb.append("\"ethnic\":\"" + t.getEthnic().getName() + "\",");
			tb.append("\"politic\":\"" + t.getPolitics().getName() + "\",");

			tb.append("\"tage\":\"" + String.valueOf(Utlity.getAge(t.getTeachingAge())) + "\",");
			tb.append("\"jobTitle\":\"" + t.getJobTitle().getName() + "\",");
			tb.append("\"jobDuty\":\"" + t.getJobDuty().getName() + "\",");
			tb.append("\"isMu\":\"" + (t.getMultiLanguage() ? "???" : "???") + "\",");
			tb.append("\"authorized\":\"" + (t.getAuthorized() == 1 ? "??????" : "??????") + "\",");
			if(t.getStatus()==1){
				tb.append("\"status\":\"??????\",");
			}else if(t.getStatus()==2){
				tb.append("\"status\":\"??????\",");
			}else{
				tb.append("\"status\":\"??????\",");
			}
			for (TeachingLanguage tl : t.getTeachingLanguages()) {
				if (tl.getIsprime()) {
					tb.append("\"language\":\"" + tl.getLanguage().getName() + "\",");
					break;
				}
			}

			for (TeachingGrade tg : t.getTeachingGrades()) {
				if (tg.getIsprime()) {
					tb.append("\"grade\":\"" + tg.getGrade().getName() + "\",");
					break;
				}
			}

			for (TeachingSubject ts : t.getTeachingSubjects()) {
				if (ts.getIsprime()) {
					tb.append("\"subject\":\"" + ts.getSubject().getName() + "\",");
					break;
				}
			}

			tb.append("\"area\":\"" + getAreaNaviString(t.getArea()) + "\",");
			tb.append("\"edubackground\":\"" + t.getEductionBackground().getName() + "\",");
			tb.append("\"languageLevel\":\"" + t.getChineseLanguageLevel().getName() + "\",");
			if(t.getCreator() != null){
				tb.append("\"creator\":\"" + this.iProjectAdminService.get(t.getCreator()).getName() + "\",");
			}else{
				tb.append("\"creator\":\"????????????\",");
			}
//			tb.append("\"creator\":\"" + this.iProjectAdminService.get(t.getCreator()).getName() + "\",");
			tb.append("\"creattime\":\"" + Utlity.timeSpanToString(t.getCreattime()) + "\",");
			tb.append("\"id\":\"" + key + "-" + t.getId().toString() + "\",");

			tb.append("\"teacherId\":" + t.getId());
			tb.append(",");

			tb.append("\"projectId\":" + projectId);
			tb.append(",");

			tb.append("\"subjectId\":" + subjectId);
			tb.append(",");

			tb.append("\"tcid\":" + collegeId);
			tb.append(",");

			tb.append("\"gorganizationId\":" + gId);
			tb.append(",");

			tb.append("\"porganizationId\":" + pId);
			tb.append(",");

			TeacherTrainingRecords ttr = this.getiTeacherTrainingRecords().getTeacherTrainingRecord(project, subject, trainingCollege, t.getId().toString());
			if(ttr != null && ttr.getFinalStatus() > -1){//?????????????????????????????????
				tb.append("\"record_status\":" + true);
			}else if(ttr == null){
				tb.append("\"record_status\":" + true);
			}else{
				tb.append("\"record_status\":" + false);
			}
//			tb.append("\"record_status\":" + (ttr == null));
			tb.append(",");

			tb.append("\"replacedTtrId\":" + ttrId);

			tb.append("}");

			sb.append(tb.toString() + ",");
		}

		if (liT.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");

		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
	}
	
	public String addReplaceTeacherInit(){
		initServlert();
		
		Integer teacherId = 0;
		Integer replaceTtrId = 0;
		String hqlString = "";
		
		if(request.getParameter("teacherId") != null && !"".equals(request.getParameter("teacherId"))){
			teacherId = Integer.parseInt(request.getParameter("teacherId"));
//			this.tTeacher = this.iTeacherService.get(teacherId);
			this.teacherEx = new teacherEx();
			Teacher teacher = this.iTeacherService.get(teacherId);
			this.teacherEx.setTeacher(teacher);
			String areasString = "";
			List<String> lstA = iAreaService.getParentNodes(teacher.getArea().getCode());
			
			if (lstA.size() > 0) {
				List<String> t = new ArrayList<>();
				for (int i = lstA.size() - 1; i >= 0; i--) {
					t.add(lstA.get(i));
				}

				for (String string : t) {
					areasString += string + ">>";
				}
			}
			if (areasString.length() > 2) {
				teacherEx.setAreaString(areasString.substring(0, areasString.length() - 2));
			}
			teacherEx.setAgeString((String.valueOf(Utlity.getAge(teacher.getBirthday()))));
			if(teacher.getStatus()==1){
				teacherEx.setStatusString("??????");
			}else if(teacher.getStatus()==2){
				teacherEx.setStatusString("??????");
			}else{
				teacherEx.setStatusString("??????");
			}
			teacherEx.setAuthorized(teacher.getAuthorized() == 1 ? "??????" : "??????");
			teacherEx.setSexString(teacher.getSex() == 1 ? "???" : "???");
			if (teacher.getTeachingAge() != null) {
				teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher.getTeachingAge())));
			}
			
			teacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "???" : "???");
			// ????????????????????????
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				teacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// ????????????????????????
			hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				teacherEx.setMainTeachingClass(teachingGrade);
			}

			// ????????????????????????
			hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				teacherEx.setMainTeachingCourse(teachingSubject);
			}
			teacherEx.setOrganization(teacher.getOrganization());
			
			
		}
		if(request.getParameter("replacedTtrId") != null && !"".equals(request.getParameter("replacedTtrId"))){
			replaceTtrId = Integer.parseInt(request.getParameter("replacedTtrId"));
			this.ttRe = this.iTeacherTrainingRecords.get(replaceTtrId);
			Teacher teacher = this.ttRe.getTeacher();
			this.reTeacherEx = new teacherEx();
			this.reTeacherEx.setTeacher(teacher);
			String areasString = "";
			List<String> lstA = iAreaService.getParentNodes(teacher.getArea().getCode());
			
			if (lstA.size() > 0) {
				List<String> t = new ArrayList<>();
				for (int i = lstA.size() - 1; i >= 0; i--) {
					t.add(lstA.get(i));
				}

				for (String string : t) {
					areasString += string + ">>";
				}
			}
			if (areasString.length() > 2) {
				reTeacherEx.setAreaString(areasString.substring(0, areasString.length() - 2));
			}
			reTeacherEx.setAgeString((String.valueOf(Utlity.getAge(teacher.getBirthday()))));
			reTeacherEx.setSexString(teacher.getSex() == 1 ? "???" : "???");
			if(teacher.getStatus()==1){
				reTeacherEx.setStatusString("??????");
			}else if(teacher.getStatus()==2){
				reTeacherEx.setStatusString("??????");
			}else{
				reTeacherEx.setStatusString("??????");
			}
			reTeacherEx.setAuthorized(teacher.getAuthorized() == 1 ? "??????" : "??????");
			if (teacher.getTeachingAge() != null) {
				reTeacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher.getTeachingAge())));
			}
			
			reTeacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "???" : "???");
			// ????????????????????????
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				reTeacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// ????????????????????????
			hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				reTeacherEx.setMainTeachingClass(teachingGrade);
			}

			// ????????????????????????
			hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				reTeacherEx.setMainTeachingCourse(teachingSubject);
			}
			reTeacherEx.setOrganization(teacher.getOrganization());
			
		}
		
		return "replaceInit";
	}
	
	/**
	 * ????????????????????????
	 */
	public void replaceTeacher(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		
		String projectId = request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId");
		String tcid = request.getParameter("collegeId");
		String gOrgaId = request.getParameter("gorganizationId");
		String pOrgaId = request.getParameter("porganizationId");
		String teacherId = request.getParameter("teacherId");
		String ttrId = request.getParameter("replacedTtrId");
		String reason = request.getParameter("reason") == null ? "" : request.getParameter("reason");
		if("".equals(reason)){
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAIL\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"???????????????????????????\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		
		Integer ottrId = 0;
		if(ttrId != null && !"".equals(ttrId)){
		
			ottrId = Integer.parseInt(ttrId);
		}
		TeacherTrainingRecords reTtr = this.iTeacherTrainingRecords.get(ottrId);
		
//		if(reTtr.getReplaceStatus() != null && reTtr.getReplaceStatus() == 0){
//			//????????????????????????????????????????????????????????????
//			StringBuilder checkSB = new StringBuilder();
//			checkSB.append("{");
//			checkSB.append("\"Result\":\"REPLACE\"");
//			checkSB.append(",");
//			checkSB.append("\"Message\":\"" + "??????????????????????????????????????????????????????????????????" + "\"");
//			checkSB.append("}");
//			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//			return;
//		}
		
		if(reTtr.getReplaceStatus() != null && reTtr.getReplaceStatus() == 1){
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"???????????????????????????????????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		if(reTtr.getReplaceStatus() != null && reTtr.getReplaceStatus() == 3){
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"????????????????????????????????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String goon = request.getParameter("goon");
		
		sb.append(" from AssignTeacherTask t where 1=1 ");
		if (projectId != null && projectId.length() > 0) {
			sb.append(" and t.project=");
			sb.append(projectId);
		}
		if (subjectId != null && subjectId.length() > 0) {
			sb.append(" and t.trainingSubject=");
			sb.append(subjectId);
		}
		if (tcid != null && tcid.length() > 0) {
			sb.append(" and t.trainingCollege=");
			sb.append(tcid);
		}
		if (gOrgaId != null && gOrgaId.length() > 0) {
			sb.append(" and t.organizationByGOrganization=");
			sb.append(gOrgaId);
		}
		if (pOrgaId != null && pOrgaId.length() > 0) {
			sb.append(" and t.organizationByPOrganization=");
			sb.append(pOrgaId);
		}

		List<AssignTeacherTask> liatts = this.iAssignTeacherTaskService.getListByHSQL(sb.toString());
		if (liatts != null && liatts.size() > 0) {
			
			try {
				AssignTeacherTask att = liatts.get(0);
	
				int totalCount = att.getTeacherNumber();
	
				// ???????????????????????????
				String ttrsqlCount = "select count(*) from teacher_training_records t where t.project=" + projectId;
				ttrsqlCount += " and t.subject=" + subjectId + " and t.training_college=" + tcid;
	
				List littrs = this.iTeacherTrainingRecords.executeSQL(ttrsqlCount, null);
				int ttrCount = Integer.valueOf(littrs.get(0).toString());
	
				if (ttrCount >= totalCount) {
					// ????????????????????????????????????????????????????????????
					if (goon == null || goon.equals("") || goon.equals("0")) {
						// ????????????????????????
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"WARNING\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "?????????????????????" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}
				}
	
//				// ????????????????????????????????????????????????????????????????????????????????????
//				List<Project> projectList = this.iProjectService.getSameTypeProjectList(projectId);
//				StringBuilder trainedCount = new StringBuilder();
//				trainedCount.append("select count(*) from teacher_training_records t where 1=1");
//				trainedCount.append(" and t.teacher=").append(teacherId);
//				trainedCount.append(" and t.subject=").append(subjectId);
//				if (projectList != null && projectList.size() > 0) {
//					trainedCount.append(" and t.project in (");
//					String comba = "";
//					for (Project project : projectList) {
//						trainedCount.append(comba);
//						trainedCount.append(project.getId());
//						comba = ",";
//					}
//					trainedCount.append(")");
//				}
//				// trainedCount.append(" and t.final_status <>").append(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
//	
//				littrs = this.iTeacherTrainingRecords.executeSQL(trainedCount.toString(), null);
//				ttrCount = Integer.valueOf(littrs.get(0).toString());
//				// ???????????????/????????????
//				if (ttrCount > 0) {
//					StringBuilder checkSB = new StringBuilder();
//					checkSB.append("{");
//					checkSB.append("\"Result\":\"FAIL\"");
//					checkSB.append(",");
//					TrainingSubject subject = this.iTrainingSubjectService.get(Short.valueOf(subjectId));
//					checkSB.append("\"Message\":\"" + "??????????????????????????????????????????????????????" + subject.getName() + "???????????????" + "\"");
//					checkSB.append("}");
//					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//					return;
//				}
				
				//??????????????????????????????
				TeacherTrainingReplace ttReplace = new TeacherTrainingReplace();
				Teacher th = this.iTeacherService.get(Integer.valueOf(teacherId));
				
				ttReplace.setBereplacedttr(reTtr);
				ttReplace.setCreator(us.getId());
				ttReplace.setTeacher(th);
				
				Integer projectIds = 0;
				if(projectId != null && !"".equals(projectId)){
					projectIds = Integer.parseInt(projectId);
				}
				ttReplace.setProject(projectIds);
				
				Short subjectIds = 0;
				if(subjectId != null && !"".equals(subjectId)){
					subjectIds = Short.parseShort(subjectId);
				}
				ttReplace.setSubject(subjectIds);
				
				Integer trainingCollege = 0;
				if(tcid != null && !"".equals(tcid)){
					trainingCollege = Integer.parseInt(tcid);
				}
				ttReplace.setTrainCollege(trainingCollege);
				
				ttReplace.setReplaceStatus((short)1);
				ttReplace.setCreattime(new Timestamp(System.currentTimeMillis()));
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("projectId", projectIds);
				params.put("subjectId", subjectIds);
				params.put("trainCollege", trainingCollege);
				params.put("teacher", Integer.valueOf(teacherId));
//				params.put("status", 1);
				
				// ??????
				String sort = "id_desc";
				String[] sortArr = sort.split("_");
				Map<String, Object> sortParams = new HashMap<String, Object>();
				sortParams.put(sortArr[0], sortArr[1]);
				
				int count = this.iTeacherTrainingReplaceService.getRecordsListByParams(params, sortParams);
//				List<TeacherTrainingReplace> lstTTR = this.iTeacherTrainingReplaceService.getRecordsListByParams(params, sortParams, 0, DictionyMap.maxPageSize);
				if(count > 0){
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"ERROR\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "????????????????????????????????????????????????????????????" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					return;
				}
				ttReplace.setReplaceReason(reason);
				this.iTeacherTrainingReplaceService.add(ttReplace);
				reTtr.setReplaceStatus((short)3);//??????????????????
				this.iTeacherTrainingRecords.update(reTtr);
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"OK\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"" + "????????????????????????" + "\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "????????????????????????" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	public String initReplaceAdu() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel()+"";
		String year = request.getParameter("year");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
//		String ist = (String) request.getParameter("page");
//		if (ist == null || ist.equals("") || ist.equals("NaN")) {
//			ist = "1";
//		}
//		int startPage = Integer.parseInt(ist);
//		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}
		
		return "replaceAdu";
	}
	
	/**
	 * ??????????????????????????????
	 */
	public void getReplaceTeacherList(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		

		// ??????????????????
		String projectid = request.getParameter("projectId");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectId");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("tcId");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		
		String teacherName = request.getParameter("teacherName");
		teacherName = teacherName == null ? "" : teacherName;

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(Integer.parseInt(projectid) > 0){
			param.put("projectId", projectid);
		}
		if(Integer.parseInt(subjectid) >0){
			param.put("subjectId", subjectid);
		}
		if(!"".equals(teacherName)){
			param.put("teacherName", teacherName);
		}
		param.put("trainCollege", trainingUnit);
		param.put("status", status);
		
		
		if(us.getOrganizationLevel()>1){
			param.put("organizationScode", us.getOrganizationScode());
		}else{
			param.put("organizationScode", us.getOrganizationScode());
		}
		
		// ?????????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		
		
		
		// ???????????????
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}
		int startPage = Integer.parseInt(ist);
		int start = (startPage - 1) * DictionyMap.maxPageSize;
//		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);

		// ??????
		String sort = request.getParameter("jtSorting");

		// ?????? ??????
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sort = sortname + "_" + sorttype;
		} else {
			sort = "id_desc";
		}
		
		String[] sortArr = sort.split("_");
		Map<String, Object> sortParams = new HashMap<String, Object>();
		sortParams.put(sortArr[0], sortArr[1]);
		
		try {
			List<TeacherTrainingReplace> lstTTre = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams, start, limit);
			int ttreRecordsCount = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams);
			
			if (lstTTre == null || lstTTre.size() == 0) {
				StringBuilder sbs = new StringBuilder();
				sbs.append("{");
				sbs.append("\"status\":\"ERROR\",");
				sbs.append("\"message\":\"?????????????????????!\"");
				sbs.append("}");
				Utlity.ResponseWrite(sbs.toString(), "application/json", response);
				return;
			}
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			
			if(lstTTre != null && lstTTre.size() > 0){
				sb.append("\"TotalRecordCount\":" + ttreRecordsCount);
				sb.append(",");
				sb.append("\"Records\":[");
				for(TeacherTrainingReplace ttr : lstTTre){
					TeacherTrainingRecords t = ttr.getBereplacedttr();
					Teacher teacher = t.getTeacher();//???????????????
					Teacher reTeacher = ttr.getTeacher();//????????????
					TrainingCollege tc = this.iTrainingCollegeService.get(ttr.getTrainCollege());//????????????
					
					sb.append("{");
					sb.append("\"id\":" + ttr.getId());
					sb.append(",");
					String name = teacher.getOrganization().getName()+"??????-"+teacher.getName();
					sb.append("\"name\":\"" + name + "\"");
					sb.append(",");
					sb.append("\"idCard\":\"" + teacher.getIdcard() + "\"");
					sb.append(",");
					
					String reName = reTeacher.getOrganization().getName()+"??????-"+reTeacher.getName();
					sb.append("\"reName\":\"" + reName + "\"");
					sb.append(",");
					sb.append("\"reIdCard\":\"" + reTeacher.getIdcard() + "\"");
					sb.append(",");
					sb.append("\"project\":\"" + t.getProject().getName() + "\"");
					sb.append(",");
					sb.append("\"trainingSubject\":\"" + t.getTrainingSubject().getName() + "\"");
					sb.append(",");
					sb.append("\"trainingCollege\":\"" + tc.getName() + "\"");
					sb.append(",");
//					String reStatus = "?????????";
//					if(ttr.getReplaceStatus() == 0){
//						reStatus = "?????????";
//					}else if(ttr.getReplaceStatus() == 2){
//						reStatus = "?????????";
//					}
					sb.append("\"createtime\":\"" + Utlity.timeSpanToString(ttr.getCreattime()) + "\"");
					sb.append(",");
					String creator = "";
					if(ttr.getCreator() != null){
						ProjectAdmin pa = this.iProjectAdminService.get(ttr.getCreator());
						if(pa != null){
							creator += (pa.getOrganization().getName()+"?????????"+pa.getName());
						}
					}
					sb.append("\"creator\":\"" + creator + "\"");
					sb.append(",");
					sb.append("\"reStatus\":\"" + ttr.getReplaceStatus() + "\"");
					sb.append(",");
					sb.append("\"replaceReason\":\"" + ttr.getReplaceReason() + "\"");
					sb.append(",");
					sb.append("\"organizationLevel\":\"" + us.getOrganizationLevel() + "\"");
					sb.append(",");
					String nopassReason = "";
					if(ttr.getNopassReason() != null){
						nopassReason = ttr.getNopassReason();
					}
					sb.append("\"nopassReason\":\"" + nopassReason + "\"");
					sb.append("},");
				}
				sb.delete(sb.length() - 1, sb.length());
			}else{
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
				sb.append("\"Records\":[");
			}
			sb.append("]}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ??????????????????????????????
	 */
	public void outputReplaceTeacher(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
		
		// ??????????????????
		String projectid = request.getParameter("projectId");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectId");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("tcId");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		
		String teacherName = request.getParameter("teacherName");
		teacherName = teacherName == null ? "" : teacherName;

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(Integer.parseInt(projectid) > 0){
			param.put("projectId", projectid);
		}
		if(Integer.parseInt(subjectid) >0){
			param.put("subjectId", subjectid);
		}
		if(!"".equals(teacherName)){
			param.put("teacherName", teacherName);
		}
		param.put("trainCollege", trainingUnit);
		param.put("status", status);
		
		
		// ??????
		String sort = request.getParameter("jtSorting");

		// ?????? ??????
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sort = sortname + "_" + sorttype;
		} else {
			sort = "id_desc";
		}
		
		String[] sortArr = sort.split("_");
		Map<String, Object> sortParams = new HashMap<String, Object>();
		sortParams.put(sortArr[0], sortArr[1]);
		
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "???????????????");
			XSSFRow row = s.createRow(0);
			String title[] = { "????????????", "????????????", "????????????", "?????????????????????", "???????????????????????????", "?????????????????????", "??????????????????", "????????????????????????", "??????????????????", "????????????", "????????????", "?????????????????????", "???????????????", "??????????????????" };
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			
			List<TeacherTrainingReplace> lstTTre = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams, -1, -1);
			
			int t = 0;
			for(TeacherTrainingReplace ttr : lstTTre){
				t++;
				row = s.createRow(t);
				TeacherTrainingRecords tr = ttr.getBereplacedttr();
				Teacher teacher = tr.getTeacher();//???????????????
				Teacher reTeacher = ttr.getTeacher();//????????????
				TrainingCollege tc = tr.getTrainingCollege();//????????????
				
				String projectName = tr.getProject().getName();
				String subjectName = tr.getTrainingSubject().getName();
				String collegeName = tc.getName();
				
				//?????????
				String teacherNames = teacher.getName();
				String teacherIdcard = teacher.getIdcard();
				String teacherOrg = teacher.getOrganization().getName();
				
				//??????
				String reTeacherName = reTeacher.getName();
				String reTeacherIdcard = reTeacher.getIdcard();
				String reTeacherOrg = reTeacher.getOrganization().getName();
				
				String reason = ttr.getReplaceReason();
				
				String aduStatus = "";
				if(ttr.getReplaceStatus() == 0){
					aduStatus = "?????????";
				} else if(ttr.getReplaceStatus() == 1) {
					aduStatus = "?????????";
				} else if(ttr.getReplaceStatus() == 2){
					aduStatus = "?????????";
				}
				
				String nopassReason = ttr.getNopassReason() == null ? "" : ttr.getNopassReason();
				
				String creator = "";
				if(ttr.getCreator() != null){
					ProjectAdmin pa = this.iProjectAdminService.get(ttr.getCreator());
					if(pa != null){
						creator += (pa.getOrganization().getName()+"?????????"+pa.getName());
					}
				}

				String createtime = Utlity.timeSpanToString(ttr.getCreattime());
				
				String tInfo[] = { projectName, subjectName, collegeName, teacherNames, teacherIdcard, teacherOrg, reTeacherName, reTeacherIdcard, reTeacherOrg, reason, aduStatus, nopassReason, creator, createtime };
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=replaceTeacher.xlsx");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getReplaceTeacherStatus(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		
//		@SuppressWarnings("unchecked")
//		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

//		// ??????
//		String ist = (String) request.getParameter("page");
//		if (ist == null || ist.equals("") || ist.equals("NaN")) {
//			ist = "1";
//		}
//		int start = Integer.parseInt(ist);

		// ??????????????????
		String projectid = request.getParameter("projectId");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectId");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("tcId");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		
		String teacherName = request.getParameter("teacherName");
		teacherName = teacherName == null ? "" : teacherName;

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(Integer.parseInt(projectid) > 0){
			param.put("projectId", projectid);
		}
		if(Integer.parseInt(subjectid) >0){
			param.put("subjectId", subjectid);
		}
		if(!"".equals(teacherName)){
			param.put("teacherName", teacherName);
		}
		param.put("trainCollege", trainingUnit);
		
		if(us.getOrganizationLevel()>1){
			param.put("organizationScode", us.getOrganizationScode());
		}else{
			param.put("organizationScode", us.getOrganizationScode());
		}
		
//		// ?????????
//		String ist = (String) request.getParameter("jtStartIndex");
//		if (ist == null || ist.equals("") || ist.equals("NaN")) {
//			ist = "0";
//		}
//
//		// ???????????????
//		String ien = (String) request.getParameter("jtPageSize");
//		if (ien == null || ien.equals("")) {
//			ien = DictionyMap.maxPageSize + "";
//		}
//
//		int start = Integer.parseInt(ist);
//		int limit = DictionyMap.maxPageSize;
//		limit = Integer.parseInt(ien);

		// ??????
		String sort = request.getParameter("jtSorting");

		// ?????? ??????
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sort = sortname + "_" + sorttype;
		} else {
			sort = "id_desc";
		}
		
		String[] sortArr = sort.split("_");
		Map<String, Object> sortParams = new HashMap<String, Object>();
		sortParams.put(sortArr[0], sortArr[1]);
		
//		try {
			
//			List<TeacherTrainingReplace> lstTTre = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams, start, limit);
		param.put("status", "-1"); 
		int records = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams);
		param.put("status", "1");
		int noCheck = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams);
		param.put("status", "2");
		int checkPass = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams);
		param.put("status", "0");
		int checkNoPass = this.iTeacherTrainingReplaceService.getRecordsListByParams(param, sortParams);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");//??????
		sb.append("\"noCheck\":" + (noCheck) + ",");//?????????
		sb.append("\"checkPass\":" + (checkPass) + ",");//?????????
		sb.append("\"noComplete\":" + (checkNoPass));//?????????
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	
	public String reviewReplaceTeacherInit(){
		initServlert();
		String hqlString = "";
		if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
			Integer ttrid = Integer.valueOf(request.getParameter("id"));
			
			TeacherTrainingReplace ttr = this.iTeacherTrainingReplaceService.get(ttrid);
			
			/*
			 * ??????????????????
			 */
			this.teacherEx = new teacherEx();
			Teacher teacher = ttr.getTeacher();
			this.teacherEx.setTeacher(teacher);
			String areasString = "";
			List<String> lstA = iAreaService.getParentNodes(teacher.getArea().getCode());
			
			if (lstA.size() > 0) {
				List<String> t = new ArrayList<>();
				for (int i = lstA.size() - 1; i >= 0; i--) {
					t.add(lstA.get(i));
				}

				for (String string : t) {
					areasString += string + ">>";
				}
			}
			if (areasString.length() > 2) {
				teacherEx.setAreaString(areasString.substring(0, areasString.length() - 2));
			}
			teacherEx.setAgeString((String.valueOf(Utlity.getAge(teacher.getBirthday()))));
			teacherEx.setSexString(teacher.getSex() == 1 ? "???" : "???");
			if (teacher.getTeachingAge() != null) {
				teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher.getTeachingAge())));
			}

			teacherEx.setAuthorized(teacher.getAuthorized() == 1 ? "??????" : "??????");
			teacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "???" : "???");
			// ????????????????????????
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				teacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// ????????????????????????
			hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				teacherEx.setMainTeachingClass(teachingGrade);
			}

			// ????????????????????????
			hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				teacherEx.setMainTeachingCourse(teachingSubject);
			}
			teacherEx.setOrganization(teacher.getOrganization());
			
			/*
			 * ?????????????????????
			 */
			this.ttRe = ttr.getBereplacedttr();
			
			Teacher reTeacher = this.ttRe.getTeacher();
			this.reTeacherEx = new teacherEx();
			this.reTeacherEx.setTeacher(reTeacher);
			String reAreasString = "";
			List<String> reLstA = iAreaService.getParentNodes(reTeacher.getArea().getCode());
			
			if (reLstA.size() > 0) {
				List<String> t = new ArrayList<>();
				for (int i = reLstA.size() - 1; i >= 0; i--) {
					t.add(reLstA.get(i));
				}

				for (String string : t) {
					reAreasString += string + ">>";
				}
			}
			if (reAreasString.length() > 2) {
				reTeacherEx.setAreaString(reAreasString.substring(0, reAreasString.length() - 2));
			}
			reTeacherEx.setAgeString((String.valueOf(Utlity.getAge(reTeacher.getBirthday()))));
			reTeacherEx.setSexString(reTeacher.getSex() == 1 ? "???" : "???");
			if (reTeacher.getTeachingAge() != null) {
				reTeacherEx.setTeachingAge(String.valueOf(Utlity.getAge(reTeacher.getTeachingAge())));
			}
			reTeacherEx.setAuthorized(reTeacher.getAuthorized() == 1 ? "??????" : "??????");
			reTeacherEx.setIsMultiLanguage(reTeacher.getMultiLanguage() ? "???" : "???");
			// ????????????????????????
			hqlString = "from TeachingLanguage where teacher=" + reTeacher.getId() + " and isprime=true";

			List<TeachingLanguage> reLstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (reLstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = reLstTeachingLanguages.get(0);
				reTeacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// ????????????????????????
			hqlString = "from TeachingGrade where teacher=" + reTeacher.getId() + " and isprime=1";
			List<TeachingGrade> reLstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (reLstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = reLstTeachingGrades.get(0);
				reTeacherEx.setMainTeachingClass(teachingGrade);
			}

			// ????????????????????????
			hqlString = "from TeachingSubject where teacher=" + reTeacher.getId() + " and isprime=1";
			List<TeachingSubject> reLsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			if (reLsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = reLsttTeachingSubjects.get(0);
				reTeacherEx.setMainTeachingCourse(teachingSubject);
			}
			reTeacherEx.setOrganization(reTeacher.getOrganization());
			
			
		}
		
		return "reviewReplace";
	}
	
	/**
	 * ??????????????????
	 */
	public void reviewReplaceTeacher(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String id = request.getParameter("id") == null?"0":request.getParameter("id");
		String method = request.getParameter("method") == null?"":request.getParameter("method");
		
		if(us.getOrganizationLevel() == 1){
			try {
				TeacherTrainingReplace ttre = this.iTeacherTrainingReplaceService.get(Integer.parseInt(id));
				
				if("pass".equals(method)){//????????????
					
					if(ttre.getReplaceStatus() == 2){
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"OK\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "?????????????????????????????????" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}
					
					TeacherTrainingRecords ttr = ttre.getBereplacedttr();
					Teacher teacher = ttre.getTeacher();
					Integer projectId = ttre.getProject();
					Short subjectId = ttre.getSubject();
					Integer trainingCollegeId = ttre.getTrainCollege();
					ProjectAdmin pa = this.iProjectAdminService.get(ttre.getCreator());
					TeacherTrainingRecords ttrr = this.iTeacherTrainingRecords.getTeacherTrainingRecord(projectId.toString(), subjectId.toString(), trainingCollegeId.toString(), teacher.getId().toString());
					if(ttrr != null){
						// ????????????
						ttrr.setFinalStatus((short) 2);//????????????????????????
						ttrr.setTrainingStatus((short) 1);
//						
//						//??????????????????
						ttrr.setReplaceStatus((short)2);//????????????
						ttrr.setReplaceTeacher(ttr.getTeacher().getId());//??????????????????ID
						
						ttr.setReplaceStatus((short)1);
						ttr.setReplaceTeacher(teacher.getId());
						
						//????????????????????????????????? ??????????????????
						if(ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS){
							ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
							
							this.iTeacherTrainingRecords.update(ttr);
							// ??????????????????
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								String reason = "?????????"+teacher.getName()+"??????";
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 3);
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}else{
							this.iTeacherTrainingRecords.update(ttr);
							// ??????????????????
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								String reason = "?????????"+teacher.getName()+"??????";
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 3);
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						ttre.setReplaceStatus((short)2);
						this.iTeacherTrainingReplaceService.update(ttre);
						this.iTeacherTrainingRecords.update(ttrr);
						// ??????????????????

						AssignTeacherCheck atc = new AssignTeacherCheck();
						atc.setChecker(pa.getId());
						atc.setTeacherTrainingRecords(ttrr);
						atc.setType((short) 1);
//						atc.setChecktime(ttre.getCreattime());
						String reason = "???"+pa.getOrganization().getName()+"?????????-"+pa.getName()+",????????????"+ttr.getTeacher().getOrganization().getName()+"?????????"+ttr.getTeacher().getName();
						atc.setReason(reason);
						this.iAssignTeacherCheckService.add(atc);
						
						AssignTeacherCheck atcc = new AssignTeacherCheck();
						atcc.setChecker(us.getId());
						atcc.setTeacherTrainingRecords(ttrr);
						atcc.setType((short) 1);
//						atcc.setChecktime(ttre.getCreattime());
						String reasons = "";
						reasons+="???"+this.iOrganization.get(us.getOrganization()).getName()+"?????????-"+us.getName()+",??????????????????????????????";
						atcc.setReason(reasons);
						this.iAssignTeacherCheckService.add(atcc);
						
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"OK\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "?????????????????????????????????" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					}else{
						TeacherTrainingRecords rettr = new TeacherTrainingRecords();
						
//						System.out.println(pa.getName());
						Project project = this.iProjectService.get(projectId);
						TrainingSubject ts = this.iTrainingSubjectService.get(subjectId);
						TrainingCollege tc = this.iTrainingCollegeService.get(trainingCollegeId);
						String vid = Utlity.getUuidPwd(); 
						
						rettr.setProject(project);
						rettr.setTrainingSubject(ts);
						rettr.setTrainingCollege(tc);
						
						rettr.setTeacher(teacher);
						rettr.setCreator(us.getId());
						
						Organization taddOra = this.iOrganization.get(us.getOrganization());
						rettr.setOrganization(taddOra);
						
						rettr.setCheckStatus((short) (us.getOrganizationLevel()));
						rettr.setUuid(vid);
						rettr.setIsPrepare(false);
						
						// ????????????
						rettr.setFinalStatus((short) 2);//????????????????????????
						rettr.setTrainingStatus((short) 1);
//						
//						//??????????????????
						rettr.setReplaceStatus((short)2);//????????????
						rettr.setReplaceTeacher(ttr.getTeacher().getId());//??????????????????ID
						
						newAddTeacherInfo(rettr);//??????????????????
						
						ttr.setReplaceStatus((short)1);
						ttr.setReplaceTeacher(teacher.getId());
						
						//????????????????????????????????? ??????????????????
						if(ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS){
							ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
							
							this.iTeacherTrainingRecords.update(ttr);
							// ??????????????????
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								String reason = "?????????"+teacher.getName()+"??????";
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 3);
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}else{
							this.iTeacherTrainingRecords.update(ttr);
							// ??????????????????
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								String reason = "?????????"+teacher.getName()+"??????";
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 3);
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						ttre.setReplaceStatus((short)2);
						this.iTeacherTrainingReplaceService.update(ttre);
						rettr.setProjectCycle(project.getProjectCycle());
						this.iTeacherTrainingRecords.add(rettr);
						// ??????????????????

						AssignTeacherCheck atc = new AssignTeacherCheck();
						atc.setChecker(pa.getId());
						atc.setTeacherTrainingRecords(rettr);
						atc.setType((short) 1);
						atc.setChecktime(ttre.getCreattime());
						String reason = "???"+pa.getOrganization().getName()+"?????????-"+pa.getName()+",????????????"+ttr.getTeacher().getOrganization().getName()+"?????????"+ttr.getTeacher().getName();
						atc.setReason(reason);
						this.iAssignTeacherCheckService.add(atc);
						
						AssignTeacherCheck atcc = new AssignTeacherCheck();
						atcc.setChecker(us.getId());
						atcc.setTeacherTrainingRecords(rettr);
						atcc.setType((short) 1);
//						atcc.setChecktime(ttre.getCreattime());
						String reasons = "";
						reasons+="???"+this.iOrganization.get(us.getOrganization()).getName()+"?????????-"+us.getName()+",??????????????????????????????";
						atcc.setReason(reasons);
						this.iAssignTeacherCheckService.add(atcc);
						
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"OK\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "?????????????????????????????????" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					}
					
					
					
					
				}else{//?????????????????????????????????
					String reason = request.getParameter("reason") == null ? "" : request.getParameter("reason");
					if("".equals(reason)){
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"ERROR\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"??????????????????????????????\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}
					//??????????????????????????????
					TeacherTrainingRecords rettr = ttre.getBereplacedttr();
					rettr.setFinalStatus((short)2);
					rettr.setReplaceStatus((short)0);
					rettr.setReplaceTeacher(0);
					if(ttre.getReplaceStatus() == 2){
						//????????????????????????????????????????????????????????????????????????
						TeacherTrainingRecords ttr = this.getiTeacherTrainingRecords().getTeacherTrainingRecord(ttre.getProject()+"", ttre.getSubject()+"", ttre.getTrainCollege()+"", ttre.getTeacher().getId()+"");
						if(ttr != null){
							ttr.setFinalStatus((short) -1);//??????
							
							
							
							AssignTeacherCheck atcc = new AssignTeacherCheck();
							atcc.setChecker(us.getId());
							atcc.setTeacherTrainingRecords(ttr);
							atcc.setType((short) 7);
//							atcc.setChecktime(ttre.getCreattime());
							String reasons = "";
							reasons+="???"+this.iOrganization.get(us.getOrganization()).getName()+"?????????-"+us.getName()+",???????????????????????????????????????????????????";
							atcc.setReason(reasons);
							try {
								this.iTeacherTrainingRecords.update(ttr);
								this.iAssignTeacherCheckService.add(atcc);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
//						StringBuilder checkSB = new StringBuilder();
//						checkSB.append("{");
//						checkSB.append("\"Result\":\"ERROR\"");
//						checkSB.append(",");
//						checkSB.append("\"Message\":\"" + "?????????????????????????????????" + "\"");
//						checkSB.append("}");
//						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//						return;
					}
					
					
					ttre.setNopassReason(reason);//???????????????????????????
					ttre.setReplaceStatus((short)0);
					this.iTeacherTrainingReplaceService.update(ttre);
					this.iTeacherTrainingRecords.update(rettr);
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"OK\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "?????????????????????" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"" + "??????????????????????????????????????????" + "\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * ?????????????????????????????????
	 */
	private void newAddTeacherInfo(TeacherTrainingRecords teacherTrainingRecords) {
		// ??????id????????????????????????
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("id", teacherTrainingRecords.getTeacher().getId() + "");
//		List<Teacher> teacherList = iTeacherService.getTeacherListByParams(map);
		Teacher teacher = teacherTrainingRecords.getTeacher();
		teacherTrainingRecords.setTeacherOrganization(teacher.getOrganization()
				.getId());// ??????
		teacherTrainingRecords.setJobTitle(teacher.getJobTitle());// ??????
		teacherTrainingRecords.setEductionBackground(teacher
				.getEductionBackground());// ??????
		teacherTrainingRecords.setPolitics(teacher.getPolitics());// ????????????
//		// ????????????id??????TeachingSubject????????????
//		Map<String, Object> tsMap = new HashMap<String, Object>();
//		tsMap.put("id", teacher.getId() + "");
//		List<TeachingSubject> teachingSubjectList = iTeachingSubjectService
//				.getListByParams(tsMap);
//		for (TeachingSubject teachingSubject : teachingSubjectList) {
//			if (teachingSubject.getIsprime().equals("1")) {
//				teacherTrainingRecords.setSubject(teachingSubject.getSubject());// ????????????
//			}
//		}
//		// ????????????id??????TeachingGrade????????????
//		List<TeachingGrade> teachingGradeList = iTeachingGradeService
//				.getListByParams(tsMap);
//		for (TeachingGrade teachingGrade : teachingGradeList) {
//			if (teachingGrade.getIsprime().equals("1")) {
//				teacherTrainingRecords.setGrade(teachingGrade.getGrade());// ??????????????????
//			}
//		}
//		List<TeachingLanguage> teachingLanguageList = iTeachingLanguageService
//				.getListByParams(tsMap);
//		for (TeachingLanguage teachingLanguage : teachingLanguageList) {
//			if (teachingLanguage.getIsprime().equals("1")) {
//				teacherTrainingRecords.setLanguage(teachingLanguage.getLanguage());// ??????????????????
//			}
//		}
		// ????????????????????????
		String hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

		List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
		if (lstTeachingLanguages.size() > 0) {
			TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
			teacherTrainingRecords.setLanguage(teachingLanguage.getLanguage());// ??????????????????
		}

		// ????????????????????????
		hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
		List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
		if (lstTeachingGrades.size() > 0) {
			TeachingGrade teachingGrade = lstTeachingGrades.get(0);
			teacherTrainingRecords.setGrade(teachingGrade.getGrade());// ??????????????????
		}

		// ????????????????????????
		hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
		List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
		if (lsttTeachingSubjects.size() > 0) {
			TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
			teacherTrainingRecords.setSubject(teachingSubject.getSubject());// ????????????
		}
	}

	public void all(){	
		List<TeacherTrainingRecords> findAll = iTeacherTrainingRecords.findAll();
		int i = 0;
		for (TeacherTrainingRecords teacherTrainingRecords : findAll) {
			newAddTeacherInfo(teacherTrainingRecords);
			iTeacherTrainingRecords.update(teacherTrainingRecords);
			System.out.println(i);
			i++;
		}
	}
	
	// ??????

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

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
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

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	public ILanguageService getiLanguageService() {
		return iLanguageService;
	}

	public void setiLanguageService(ILanguageService iLanguageService) {
		this.iLanguageService = iLanguageService;
	}

	public ISubjectService getiSubjectService() {
		return iSubjectService;
	}

	public void setiSubjectService(ISubjectService iSubjectService) {
		this.iSubjectService = iSubjectService;
	}

	public IGradeService getiGradeService() {
		return iGradeService;
	}

	public void setiGradeService(IGradeService iGradeService) {
		this.iGradeService = iGradeService;
	}

	public ITeachingLanguageService getiTeachingLanguageService() {
		return iTeachingLanguageService;
	}

	public void setiTeachingLanguageService(ITeachingLanguageService iTeachingLanguageService) {
		this.iTeachingLanguageService = iTeachingLanguageService;
	}

	public ITeachingGradeService getiTeachingGradeService() {
		return iTeachingGradeService;
	}

	public void setiTeachingGradeService(ITeachingGradeService iTeachingGradeService) {
		this.iTeachingGradeService = iTeachingGradeService;
	}

	public ITeachingSubjectService getiTeachingSubjectService() {
		return iTeachingSubjectService;
	}

	public void setiTeachingSubjectService(ITeachingSubjectService iTeachingSubjectService) {
		this.iTeachingSubjectService = iTeachingSubjectService;
	}

	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
	}

	public IJobDutyService getiJobDutyService() {
		return iJobDutyService;
	}

	public void setiJobDutyService(IJobDutyService iJobDutyService) {
		this.iJobDutyService = iJobDutyService;
	}

	public IJobTitleService getiJobTitleService() {
		return iJobTitleService;
	}

	public void setiJobTitleService(IJobTitleService iJobTitleService) {
		this.iJobTitleService = iJobTitleService;
	}

	public IEductionBackgroundService getiEductionBackgroundService() {
		return iEductionBackgroundService;
	}

	public void setiEductionBackgroundService(IEductionBackgroundService iEductionBackgroundService) {
		this.iEductionBackgroundService = iEductionBackgroundService;
	}

	public IPoliticsService getiPoliticsService() {
		return iPoliticsService;
	}

	public void setiPoliticsService(IPoliticsService iPoliticsService) {
		this.iPoliticsService = iPoliticsService;
	}

	public IChineseLanguageLevelService getiChineseLanguageLevelService() {
		return iChineseLanguageLevelService;
	}

	public void setiChineseLanguageLevelService(IChineseLanguageLevelService iChineseLanguageLevelService) {
		this.iChineseLanguageLevelService = iChineseLanguageLevelService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecords() {
		return iTeacherTrainingRecords;
	}

	public void setiTeacherTrainingRecords(ITeacherTrainingRecordsService iTeacherTrainingRecords) {
		this.iTeacherTrainingRecords = iTeacherTrainingRecords;
	}

	public IAssignTeacherCheckService getiAssignTeacherCheckService() {
		return iAssignTeacherCheckService;
	}

	public void setiAssignTeacherCheckService(IAssignTeacherCheckService iAssignTeacherCheckService) {
		this.iAssignTeacherCheckService = iAssignTeacherCheckService;
	}

	public List<Politics> getLstPolitics() {
		return lstPolitics;
	}

	public void setLstPolitics(List<Politics> lstPolitics) {
		this.lstPolitics = lstPolitics;
	}

	public List<Ethnic> getLstEthnics() {
		return lstEthnics;
	}

	public void setLstEthnics(List<Ethnic> lstEthnics) {
		this.lstEthnics = lstEthnics;
	}

	public List<JobTitle> getLstJobTitles() {
		return lstJobTitles;
	}

	public void setLstJobTitles(List<JobTitle> lstJobTitles) {
		this.lstJobTitles = lstJobTitles;
	}

	public List<JobDuty> getLstJobDuties() {
		return lstJobDuties;
	}

	public void setLstJobDuties(List<JobDuty> lstJobDuties) {
		this.lstJobDuties = lstJobDuties;
	}

	public List<Grade> getLstGrades() {
		return lstGrades;
	}

	public void setLstGrades(List<Grade> lstGrades) {
		this.lstGrades = lstGrades;
	}

	public List<Language> getLstLanguages() {
		return lstLanguages;
	}

	public void setLstLanguages(List<Language> lstLanguages) {
		this.lstLanguages = lstLanguages;
	}

	public List<Subject> getLstSubjects() {
		return lstSubjects;
	}

	public void setLstSubjects(List<Subject> lstSubjects) {
		this.lstSubjects = lstSubjects;
	}

	public List<EductionBackground> getLstBackgrounds() {
		return lstBackgrounds;
	}

	public void setLstBackgrounds(List<EductionBackground> lstBackgrounds) {
		this.lstBackgrounds = lstBackgrounds;
	}

	public List<ChineseLanguageLevel> getLstChineseLanguageLevels() {
		return lstChineseLanguageLevels;
	}

	public void setLstChineseLanguageLevels(List<ChineseLanguageLevel> lstChineseLanguageLevels) {
		this.lstChineseLanguageLevels = lstChineseLanguageLevels;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
	}

	public String getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(String organizationLevel) {
		this.organizationLevel = organizationLevel;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}

	public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}
	
	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public Teacher getTTeacher() {
		return tTeacher;
	}

	public void setTTeacher(Teacher tTeacher) {
		this.tTeacher = tTeacher;
	}

	public TeacherTrainingRecords getTtRe() {
		return ttRe;
	}

	public void setTtRe(TeacherTrainingRecords ttRe) {
		this.ttRe = ttRe;
	}

	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}

	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}

	public Area getCounty() {
		return county;
	}

	public void setCounty(Area county) {
		this.county = county;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getAreas2() {
		return areas2;
	}

	public void setAreas2(String areas2) {
		this.areas2 = areas2;
	}

	public ITeacherTrainingReplaceService getiTeacherTrainingReplaceService() {
		return iTeacherTrainingReplaceService;
	}

	public void setiTeacherTrainingReplaceService(
			ITeacherTrainingReplaceService iTeacherTrainingReplaceService) {
		this.iTeacherTrainingReplaceService = iTeacherTrainingReplaceService;
	}

	public teacherEx getTeacherEx() {
		return teacherEx;
	}

	public void setTeacherEx(teacherEx teacherEx) {
		this.teacherEx = teacherEx;
	}

	public teacherEx getReTeacherEx() {
		return reTeacherEx;
	}

	public void setReTeacherEx(teacherEx reTeacherEx) {
		this.reTeacherEx = reTeacherEx;
	}

	
	public IProjectGroupService getiProjectGroupService() {
		return iProjectGroupService;
	}
	

	public void setiProjectGroupService(IProjectGroupService iProjectGroupService) {
		this.iProjectGroupService = iProjectGroupService;
	}

	public List<ProjectGroup> getLstGroup() {
		return lstGroup;
	}

	public void setLstGroup(List<ProjectGroup> lstGroup) {
		this.lstGroup = lstGroup;
	}

	public IIdentifyClasshoursService getIdentifyClasshoursService() {
		return identifyClasshoursService;
	}

	public void setIdentifyClasshoursService(
			IIdentifyClasshoursService identifyClasshoursService) {
		this.identifyClasshoursService = identifyClasshoursService;
	}

	public IIdentifyClasshoursSubjectService getIdentifyClasshoursSubjectService() {
		return identifyClasshoursSubjectService;
	}

	public void setIdentifyClasshoursSubjectService(
			IIdentifyClasshoursSubjectService identifyClasshoursSubjectService) {
		this.identifyClasshoursSubjectService = identifyClasshoursSubjectService;
	}

	public IProjectCycleService getiProjectCycleService() {
		return iProjectCycleService;
	}

	public void setiProjectCycleService(IProjectCycleService iProjectCycleService) {
		this.iProjectCycleService = iProjectCycleService;
	}
	
	
}
