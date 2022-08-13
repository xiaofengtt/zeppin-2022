package cn.zeppin.action.admin;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
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
import cn.zeppin.service.IJobDutyService;
import cn.zeppin.service.IJobTitleService;
import cn.zeppin.service.ILanguageService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IPoliticsService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
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
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 学员培训记录action
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

	private IOrganizationService iOrganization; // 组织架构
	private ITrainingCollegeService iTrainingCollegeService; // 承训单位
	private ITrainingSubjectService iTrainingSubjectService; // 培训科目
	private IProjectApplyService iProjectApplyService; // 项目中标信息
	private IProjectService iProjectService; // 项目信息
	private IProjectAdminService iProjectAdminService; // 管理员信息
	private IAssignTeacherTaskService iAssignTeacherTaskService;

	private ITeacherService iTeacherService;// 教师操作
	private IAreaService iAreaService;// 地区操作
	private ILanguageService iLanguageService;// 语言操作
	private ISubjectService iSubjectService;// 学科操作
	private IGradeService iGradeService;// 学段操作

	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作

	private IEthnicService iEthnicService;// 民族
	private IJobDutyService iJobDutyService;// 职务
	private IJobTitleService iJobTitleService;// 职称

	private IEductionBackgroundService iEductionBackgroundService;// 学历
	private IPoliticsService iPoliticsService;// 政治面貌
	private IChineseLanguageLevelService iChineseLanguageLevelService;// 汉语言水平

	private IAssignTeacherCheckService iAssignTeacherCheckService;
	private ITrainingAdminService iTrainingAdminService;
	
	private ITeacherTrainingReplaceService iTeacherTrainingReplaceService;
	
	
	// adu
	private List<String> searchYear;
	private List<Project> searchReportTask;
	private String organizationLevel;
	private int level;
	// report
	private List<Politics> lstPolitics;// 政治面貌
	private List<Ethnic> lstEthnics;// 民族

	private List<JobTitle> lstJobTitles;// 职称
	private List<JobDuty> lstJobDuties;// 职务

	private List<Grade> lstGrades;// 学段
	private List<Language> lstLanguages;// 语言
	private List<Subject> lstSubjects;// 学科
	private List<EductionBackground> lstBackgrounds; // 学历
	private List<ChineseLanguageLevel> lstChineseLanguageLevels;// 汉语言水平
	
	private Teacher tTeacher;
//	private Teacher ttTeacher;
	private TeacherTrainingRecords ttRe;
	
	private teacherEx teacherEx;// 教师ex
	private teacherEx reTeacherEx;// 教师ex
	
	private Area province;// 省
	private Area city;// 市
	private Area county;// 县
	
	private String areas;
	private String areas2;

	public TeacherTrainingRecordsAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 初始化审核报送学员页面
	 */
	public String initAduPage() {

		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_PLAN);

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		this.level=us.getOrganization();
		return "adu";
	}
	
	public String initReviewPage() {
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchReportTask = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM);

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		this.level=us.getOrganization();
		
		return "enrollReview";
	}

	/**
	 * 初始化审核报送学员页面
	 */
	public String initttRecordResult() {
		initServlert();
		// 查询出所有已经发布的项目
		// UserSession us = (UserSession) session.getAttribute("usersession");
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);

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
			// 如果不是学校，下级还有部门
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

			// 输出下级机构表头，计划分配学员和实际报送学员都为零的列不显示
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
			// 输出承训机构、培训科目的左边栏，计划分配学员和实际报送学员都为零的行不显示
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
			// 输出计划分配学员和实际报送学员都不为零的单元格
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

	/**
	 * 学员 审核 搜索
	 */
	public void searchAduPage() {

		/**
		 * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		// 其他搜索条件
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

		int isadmin = 0;
		if (us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {

			if (status.equals("1")) {
				// 未审核
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

		// 搜索出条数
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString());

		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, offset, DictionyMap.maxPageSize, DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString());

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"没有搜索到数据!\"");
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

			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];

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
				// 是最高级别审核
				sb.append("\"level\":" + true + ",");
			} else {
				// 不是最高级别审核
				sb.append("\"level\":" + false + ",");
			}

			if (us.getOrganization() == ttRecord.getOrganization().getId().intValue() && us.getId().intValue() == ttRecord.getCreator().intValue()) {
				// 是当前组织架构添加
				sb.append("\"add\":" + true + ",");
			} else {
				// 不是当前组织架构添加
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
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "男" : "女") + "\",");
			sb.append("\"age\":\"" + String.valueOf(Utlity.getAge(th.getBirthday())) + "\",");
			sb.append("\"ethnic\":\"" + th.getEthnic().getName() + "\",");
			sb.append("\"politics\":\"" + th.getPolitics().getName() + "\",");

			sb.append("\"teachAge\":\"" + String.valueOf(Utlity.getAge(th.getTeachingAge())) + "\",");
			sb.append("\"jobDuty\":\"" + th.getJobDuty().getName() + "\",");
			sb.append("\"jobTitle\":\"" + th.getJobTitle().getName() + "\",");
			sb.append("\"muLanguage\":\"" + (th.getMultiLanguage() ? "是" : "否") + "\",");

			// 主要教学语言
			// sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() +
			// "\",");

			// 获取主要教学学段
			// sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");

			// 获取主要教学学科
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

			ProjectAdmin pa = this.iProjectAdminService.get(ttRecord.getCreator());
			if (pa != null) {
				sb.append("\"creator\":\"" + pa.getName() + "\",");
			} else {
				sb.append("\"creator\":\"" + "" + "\",");
			}

			int finalStatus = (int) ttRecord.getFinalStatus();

			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
				sb.append("\"status\":\"" + "已审核" + "\",");
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {

				// 没有最终审核
				// 判断中间审核状态
				int checkStatus = (int) ttRecord.getCheckStatus();
				if (us.getOrganizationLevel() > checkStatus) {
					// 当前人物 的组织架构级别大于 审核状态
					// 说明 自己已经审核通过
					sb.append("\"status\":\"" + "通过" + "\",");
				} else {
					sb.append("\"status\":\"" + "未审核" + "\",");
				}
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
				sb.append("\"status\":\"" + "未通过" + "\",");
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

	public void searchEnrollReviewPage() {
		/**
		 * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		// 其他搜索条件
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

		int isadmin = 0;
//		if (us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
//
//			if (status.equals("1")) {
//				// 未审核
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
		if (status.equals("-1")) {//查询全部
			status = "-2";
		} else if (status.equals("-2")) {//查询已通过
			status = "1";
		} else if (status.equals("1")) {//未审核
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

		// 搜索出条数
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString());

		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, offset, DictionyMap.maxPageSize, DictionyMap.PROJECT_ENROLL_TYPE_FREEDOM.toString());

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"没有搜索到数据!\"");
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

			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];

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
				// 是最高级别审核
				sb.append("\"level\":" + true + ",");
			} else {
				// 不是最高级别审核
				sb.append("\"level\":" + false + ",");
			}

			if (us.getOrganization() == ttRecord.getOrganization().getId().intValue() && us.getId().intValue() == ttRecord.getCreator().intValue()) {
				// 是当前组织架构添加
				sb.append("\"add\":" + true + ",");
			} else {
				// 不是当前组织架构添加
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
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "男" : "女") + "\",");
			sb.append("\"age\":\"" + String.valueOf(Utlity.getAge(th.getBirthday())) + "\",");
			sb.append("\"ethnic\":\"" + th.getEthnic().getName() + "\",");
			sb.append("\"politics\":\"" + th.getPolitics().getName() + "\",");

			sb.append("\"teachAge\":\"" + String.valueOf(Utlity.getAge(th.getTeachingAge())) + "\",");
			sb.append("\"jobDuty\":\"" + th.getJobDuty().getName() + "\",");
			sb.append("\"jobTitle\":\"" + th.getJobTitle().getName() + "\",");
			sb.append("\"muLanguage\":\"" + (th.getMultiLanguage() ? "是" : "否") + "\",");

			// 主要教学语言
			// sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() +
			// "\",");

			// 获取主要教学学段
			// sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");

			// 获取主要教学学科
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

			ProjectAdmin pa = this.iProjectAdminService.get(ttRecord.getCreator());
			if (pa != null) {
				sb.append("\"creator\":\"" + pa.getName() + "\",");
			} else {
				sb.append("\"creator\":\"" + "" + "\",");
			}

			int finalStatus = (int) ttRecord.getFinalStatus();

			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
				sb.append("\"status\":\"" + "已审核" + "\",");
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {

				sb.append("\"status\":\"" + "未审核" + "\",");
//				// 没有最终审核
//				// 判断中间审核状态
//				int checkStatus = (int) ttRecord.getCheckStatus();
//				if (us.getOrganizationLevel() > checkStatus) {
//					// 当前人物 的组织架构级别大于 审核状态
//					// 说明 自己已经审核通过
//					sb.append("\"status\":\"" + "通过" + "\",");
//				} else {
//					sb.append("\"status\":\"" + "未审核" + "\",");
//				}
			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
				sb.append("\"status\":\"" + "未通过" + "\",");
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
	/**
	 * 学员 审核 导出
	 * 
	 * @throws IOException
	 */
	public void outputAduPage() throws IOException {

		/**
		 * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// 其他搜索条件
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
		

		int isadmin = 0;
		if (us.getOrganizationLevel() != DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {

			if (status.equals("1")) {
				// 未审核
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

		// 搜索出条数
		int offset = 0;
		int number = 0;
		List lidata = null;
		if(enrollType != null){
			number = this.iTeacherTrainingRecords.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, enrollType);
			lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz, lityps, offset, number, enrollType);
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "审核统计表");
		HSSFRow row = s.createRow(0);
		String title[] = { "项目名称", "培训学科", "承训院校", "学员姓名", "所属地区", "所在学校", "身份证号", "年龄", "性别", "民族", "政治面貌", "学历", "教龄", "职务", "职称", "汉语水平", "主要教学学科", "主要教学学段", "主要教学语言", "联系电话", "邮箱", "审核状态", "备注" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
		for (int i = 0; i < lidata.size(); i++) {
			Object[] obj = (Object[]) lidata.get(i);
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];
			t++;
			short tStatus = ttRecord.getFinalStatus();
			row = s.createRow(t);
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			String tIdcard = th.getIdcard();
			String tName = th.getName();
			String tSex = (th.getSex() == 1 ? "男" : "女");
			String tAge = String.valueOf(Utlity.getAge(th.getBirthday()));
			String tEthnic = th.getEthnic().getName();
			String tPolitics = th.getPolitics().getName();
			String schoolName = th.getOrganization().getName();
			String tTeachAge = String.valueOf(Utlity.getAge(th.getTeachingAge()));
			String tDuty = th.getJobDuty().getName();
			String tTitle = th.getJobTitle().getName();
			String tMobile = th.getMobile();
			String tEmail = th.getEmail();
			String tArea = "";
			Organization current = th.getOrganization().getOrganization();
			short tOrgLevel = current.getOrganizationLevel().getLevel();
			while (tOrgLevel > 1) {
				tArea = current.getName() + "  " + tArea;
				current = current.getOrganization();
				tOrgLevel = current.getOrganizationLevel().getLevel();
			}

			// 获取主要教学语言
			String hqlString = "from TeachingLanguage where teacher=" + th.getId() + " and isprime=true";
			String tMainLanguage = "";
			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				tMainLanguage = teachingLanguage.getLanguage().getName();
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + th.getId() + " and isprime=1";
			String tMainGrade = "";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				tMainGrade = teachingGrade.getGrade().getName();
			}

			// 获取主要教学学科
			hqlString = "from TeachingSubject where teacher=" + th.getId() + " and isprime=1";
			String tMainSubject = "";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				tMainSubject = teachingSubject.getSubject().getName();
			}

			String tChineseLanguageLevel = th.getChineseLanguageLevel().getName();
			String tEductionBackground = th.getEductionBackground().getName();
			String ttStatus;
			if (tStatus == 0) {
				ttStatus = "未通过";
			} else {
				ttStatus = (tStatus == 1 ? "未审核" : "已审核");
			}
			
			String remarks = "";
			if(ttRecord.getReplaceStatus() == 1){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="由教师 "+tt.getName()+" 替换";
			}else if(ttRecord.getReplaceStatus() == 2){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="替换了教师  "+tt.getName();
			}
			String tInfo[] = { projectName, subjectName, trainingName, tName, tArea, schoolName, tIdcard, tAge, tSex, tEthnic, tPolitics, tEductionBackground, tTeachAge, tDuty, tTitle, tChineseLanguageLevel, tMainSubject, tMainGrade, tMainLanguage, tMobile, tEmail, ttStatus, remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=teacherAdu.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}


	/**
	 * 初始化学员报分配任务送管理
	 * 
	 * @return
	 */
	public String initReortAsstPage() {
		initServlert();
		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		return "assPage";
	}
	
	public void getTrainingProject(){
		initServlert();
		
		String year = this.request.getParameter("year");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		List<Object[]> lip = this.iProjectApplyService.getProjectByParams(map);
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + -1 + ",\"name\":\"" + "全部" + "\"},");
		for(Object[] o : lip){
			Project p = (Project) o[1];
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getAssignTaskProject(){
		initServlert();
		
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String year = this.request.getParameter("year");

		Map<String,Object> map = new HashMap();
		if(year != null && !year.endsWith("0")){
			map.put("year", year);
		}
		List<Project> lip = this.iProjectService.getProjectByParams(map,null,lityps);
		
		StringBuilder sb = new StringBuilder();

		sb.append("{\"projects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
		for(Project p : lip){
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
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
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
		for (Iterator itr = htOrganization.keySet().iterator(); itr.hasNext();) {
			int key = (int) itr.next();
			String name = (String) htOrganization.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\"},";
			sb.append(sr);
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append("]");

		sb.append(",\"subjects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");

		for (Iterator itr = htSubject.keySet().iterator(); itr.hasNext();) {
			short key = (short) itr.next();
			String name = (String) htSubject.get(key);

			String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\"},";
			sb.append(sr);
		}

		sb.delete(sb.length() - 1, sb.length());

		sb.append("]");

		sb.append(",\"trainingUnits\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");

		for (Iterator itr = htTrainingUnit.keySet().iterator(); itr.hasNext();) {
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
	 * 搜索 任务 分配 接口
	 */
	public void searchReportAssPage() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		int rprojectId = Integer.valueOf(request.getParameter("projectName"));
		int rsubjectId = Integer.valueOf(request.getParameter("subjectName"));

		// 搜索参数Page
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		/**
		 * 1.获取当前用户下的所有子任务 2.可以根据项目 学科 承训单位进行筛选 返回JSON 包括 page信息
		 */
		// 以后的搜索字段值
		// 查看当前角色 当前任务已经分配的任务
		int records = this.getiAssignTeacherTaskService().getAssignTeacherTaskCount(rprojectId,rsubjectId, us.getOrganization(), us);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		List<Map<String, Object>> results = this.getiAssignTeacherTaskService().getAssignTeacherTaskResults(rprojectId,rsubjectId, us, offset, DictionyMap.maxPageSize);

		if (results == null || results.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"没有符合条件的数据!\"");
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
			sb.append("\"status_name\":\"" + (status == 1 ? "可报送" : "停止报送") + "\"");
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
			sb.append("}");

			sbPack.append(sb.toString() + ",");

		}
		if (results.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}

		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

	}

	// 初始化学员报送弹出页面
	public String initReportPage() throws IOException {

		initServlert();

		if (lstEthnics == null) {
			// 民族
			lstEthnics = iEthnicService.getEthnicByWight();
		}

		if (lstJobDuties == null) {
			// 职务
			lstJobDuties = iJobDutyService.findAll();
		}

		if (lstJobTitles == null) {
			// 职称
			lstJobTitles = iJobTitleService.findAll();
		}

		if (lstBackgrounds == null) {
			// 学历
			lstBackgrounds = iEductionBackgroundService.findAll();
		}

		if (lstPolitics == null) {
			// 政治面貌
			lstPolitics = iPoliticsService.findAll();

		}

		if (lstChineseLanguageLevels == null) {
			// 汉语言水平
			lstChineseLanguageLevels = iChineseLanguageLevelService.findAll();
		}

		if (lstGrades == null) {
			// 学段
			lstGrades = iGradeService.findAll();
		}

		if (lstSubjects == null) {// 学科
			lstSubjects = iSubjectService.findAll();
		}
		if (lstLanguages == null) {
			// 语言
			lstLanguages = iLanguageService.findAll();
		}

		return "report";
	}

	/**
	 * 搜索 报送学员 接口
	 */
	public void searchReportTeacher() {
		/**
		 * 搜索 学员
		 */
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization oz = this.iOrganization.get(us.getOrganization());

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teacherIdCard", request.getParameter("teacherIdCard"));
		paramsMap.put("teacherName", request.getParameter("teacherName"));
		paramsMap.put("age1", request.getParameter("age1"));
		paramsMap.put("age2", request.getParameter("age2"));
		paramsMap.put("tage1", request.getParameter("tage1"));
		paramsMap.put("tage2", request.getParameter("tage2"));
		paramsMap.put("school", request.getParameter("organization"));
		paramsMap.put("sex", request.getParameter("sex"));
		paramsMap.put("isMu", request.getParameter("isMu"));
		paramsMap.put("ethnic", request.getParameter("ethnic"));
		paramsMap.put("jobTitle", request.getParameter("jobTitle"));
		paramsMap.put("chineseLanguageLevel", request.getParameter("chineseLanguageLevel"));
		paramsMap.put("mainTeachingSubject", request.getParameter("mainGrade"));
		paramsMap.put("politics", request.getParameter("politics"));
		paramsMap.put("jobDuty", request.getParameter("jobDuty"));
		paramsMap.put("eductionBackground", request.getParameter("eductionBackground"));
		paramsMap.put("mainTeachingLanguage", request.getParameter("mainTeachingLanguage"));
		paramsMap.put("mainTeachingGrades", request.getParameter("mainTeachingGrades"));
		paramsMap.put("organization", oz);

		String projectId = request.getParameter("projectId");
		String subjectId = request.getParameter("subjectId");
		String collegeId = request.getParameter("collegeId");
		String gId = request.getParameter("gorganizationId");
		String pId = request.getParameter("porganizationId");

		String key = projectId + "-" + subjectId + "-" + collegeId + "-" + gId + "-" + pId;

		String project = projectId;
		String subject = subjectId;
		String trainingCollege = collegeId;

		// 搜索参数
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
			sb.append("\"message\":\"没有搜索到数据!\"");
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
			Object[] ot = (Object[]) obj;
			Teacher t = (Teacher) ot[0];
			Organization o = (Organization) ot[1];

			StringBuilder tb = new StringBuilder();
			tb.append("{");

			tb.append("\"idcard\":\"" + t.getIdcard() + "\",");
			tb.append("\"name\":\"" + t.getName().trim() + "\",");
			tb.append("\"school\":\"" + o.getName() + "\",");
			tb.append("\"mobile\":\"" + t.getMobile() + "\",");
			tb.append("\"sexIndex\":\"" + (t.getSex()) + "\",");
			tb.append("\"sex\":\"" + (t.getSex() == 1 ? "男" : "女") + "\",");
			tb.append("\"age\":\"" + String.valueOf(Utlity.getAge(t.getBirthday())) + "\",");
			tb.append("\"ethnic\":\"" + t.getEthnic().getName() + "\",");
			tb.append("\"politic\":\"" + t.getPolitics().getName() + "\",");

			tb.append("\"tage\":\"" + String.valueOf(Utlity.getAge(t.getTeachingAge())) + "\",");
			tb.append("\"jobTitle\":\"" + t.getJobTitle().getName() + "\",");
			tb.append("\"jobDuty\":\"" + t.getJobDuty().getName() + "\",");
			tb.append("\"isMu\":\"" + (t.getMultiLanguage() ? "是" : "否") + "\",");

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
				tb.append("\"creator\":\"自主注册\",");
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
	 * 添加学员报送
	 * 
	 * @throws InterruptedException
	 */
	public void addTeacherReport() throws InterruptedException {

		// 根据 gOrganizationId pOrganizationId
		// 来判断可分配多少条任务
		// 如果超出人数提示

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

			// 获取已经分配的人数
			String ttrsqlCount = "select count(*) from teacher_training_records t where t.project=" + projectId;
			ttrsqlCount += " and t.subject=" + subjectId + " and t.training_college=" + tcid;

			List littrs = this.iTeacherTrainingRecords.executeSQL(ttrsqlCount, null);
			int ttrCount = Integer.valueOf(littrs.get(0).toString());

			if (ttrCount >= totalCount) {
				// 前端提示仍然确认添加，仍然可以添加此人。
				if (goon == null || goon.equals("") || goon.equals("0")) {
					// 人数已经达到上限
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"WARNING\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "人数达到上限！" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					return;
				}
			}

			// 一个学员在一个年度中的一个项目大类的一个学科只能申报一次
			List<Project> projectList = this.iProjectService.getSameTypeProjectList(projectId);
			StringBuilder trainedCount = new StringBuilder();
			trainedCount.append("select count(*) from teacher_training_records t where 1=1");
			trainedCount.append(" and t.teacher=").append(teacherId);
			trainedCount.append(" and t.subject=").append(subjectId);
			if (projectList != null && projectList.size() > 0) {
				trainedCount.append(" and t.project in (");
				String comba = "";
				for (Project project : projectList) {
					trainedCount.append(comba);
					trainedCount.append(project.getId());
					comba = ",";
				}
				trainedCount.append(")");
			}
			// trainedCount.append(" and t.final_status <>").append(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);

			littrs = this.iTeacherTrainingRecords.executeSQL(trainedCount.toString(), null);
			ttrCount = Integer.valueOf(littrs.get(0).toString());
			// 存在过报送/培训记录
			if (ttrCount > 0) {
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"FAIL\"");
				checkSB.append(",");
				TrainingSubject subject = this.iTrainingSubjectService.get(Short.valueOf(subjectId));
				checkSB.append("\"Message\":\"" + "该教师已经报送过本年度相同项目类型的" + subject.getName() + "学科培训！" + "\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
				return;
			}

			// 增加一条记录

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

			// 其他状态
			ttr.setFinalStatus((short) 1);
			ttr.setTrainingStatus((short) 1);
			
			//重置替换状态
			ttr.setReplaceStatus((short)0);
			ttr.setReplaceTeacher(0);

			this.iTeacherTrainingRecords.add(ttr);

			// 添加报送记录

			AssignTeacherCheck atc = new AssignTeacherCheck();
			atc.setChecker(us.getId());
			atc.setTeacherTrainingRecords(ttr);
			atc.setType((short) 1);
			this.iAssignTeacherCheckService.add(atc);

			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"OK\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "任务分配成功" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);

		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "任务分配失败" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}

	}

	//批量审核
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
			checkSB.append("\"message\":\"" + "请选择项目名称" + "\"");
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
			checkSB.append("\"message\":\"" + "请选择培训科目" + "\"");
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
		// 搜索出条数
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
		checkSB.append("\"message\":\"" + "操作成功" + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}
	
	// 审核 报送学员
	public void aduTeacherReport() {

		/**
		 * 1.key是一个以,号分割的一个多条记录信息 2.method 方法是通过还是不通过 ****-level,*****-level
		 * ****-level 2.判断是否超出任务人数，如果超出任务人数，显示该学员为后补学员
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");

		String key = request.getParameter("key");

		String method = request.getParameter("method");
		String reason= request.getParameter("reason");

		String[] spKey = key.split(",");

		if (spKey.length > 0) {

			boolean flag = spKey.length > 1 ? true : false; // 是否是多条

			String status = "";
			for (String spKeyStr : spKey) {
				String[] kls = spKeyStr.split("-");
				String ttrId = kls[0];
				String level = kls[1];

				TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.valueOf(ttrId));
				short checkStatus = ttr.getCheckStatus();

				if (ttr != null) {
					if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_ADU)) {

						// 如果已经到最高级别审核，直接就是最终
						if (level.equals("true")) {
							// 管理员审核
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {

								status = "已审核";

								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
								this.iTeacherTrainingRecords.update(ttr);

								// 添加审核记录
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									atc.setTeacherTrainingRecords(ttr);
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
									checkSB.append("\"Message\":\"" + "操作成功" + "\"");
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
									checkSB.append("\"Message\":\"" + "审核状态已经是已审核" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						} else {

							// 不是最终管理员审核

							// 首先判断最终审核
							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// 如果最终审核状态 不是未审核说明 还没有到最高级别审核

								int uslevel = us.getOrganizationLevel();

								if (checkStatus >= uslevel) {
									// 可以审核
									status = "通过";

									ttr.setCheckStatus((short) (uslevel - 1));
									this.iTeacherTrainingRecords.update(ttr);

									// 添加审核记录
									try {
										AssignTeacherCheck atc = new AssignTeacherCheck();
										atc.setTeacherTrainingRecords(ttr);
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
										checkSB.append("\"Message\":\"" + "操作成功" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}

								} else {
									// 已经审核到上级，没有权限在审核了
									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"ERROR\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "操作失败,您无权限在修改！\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}

							} else {
								// 已经到最高审核了。那就不可以操作了
								if (!flag) {

									String msg = ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS ? "已通过" : "不通过";

									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "操作失败，最终审核" + msg + ",您无权限在修改！\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_NOADU)) {

						// 审核不通过
						if (level.equals("true")) {
							// 是最终管理员
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {

								status = "未通过";
								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
								this.iTeacherTrainingRecords.update(ttr);

								// 添加审核记录
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
									checkSB.append("\"Message\":\"" + "操作成功" + "\"");
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
									checkSB.append("\"Message\":\"" + "审核状态已经是未通过" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
						} else {

							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// 如果最终审核状态 不是未审核说明 还没有到最高级别审核

								int uslevel = us.getOrganizationLevel();

								if (checkStatus + 1 >= uslevel) {
									// 可以审核
									status = "未通过";

									ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
									this.iTeacherTrainingRecords.update(ttr);

									// 添加审核记录
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
										checkSB.append("\"Message\":\"" + "操作成功" + "\"");
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
										checkSB.append("\"Message\":\"" + "操作失败,您无权限在修改" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}

							} else {
								// 已经到最高审核了。那就不可以操作了
								if (!flag) {

									String msg = ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS ? "已通过" : "不通过";

									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "操作失败，最终审核" + msg + ",您无权限在修改！\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_DEL)) {

						if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
							// 可以删除
							this.iTeacherTrainingRecords.delete(ttr);
							status = "已删除";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"OK\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "操作成功" + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						} else {
							status = "不能删除";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"ERROR\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "已审核，不能删除" + "\"");
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
			checkSB.append("\"Message\":\"" + "操作成功" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "没有审核记录" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}

	}

	// 审核 报送学员
	public void reviewTeacherReport() {

		/**
		 * 1.key是一个以,号分割的一个多条记录信息 2.method 方法是通过还是不通过 ****-level,*****-level
		 * ****-level 2.判断是否超出任务人数，如果超出任务人数，显示该学员为后补学员
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");

		String key = request.getParameter("key");

		String method = request.getParameter("method");
		String reason= request.getParameter("reason");

		String[] spKey = key.split(",");

		if (spKey.length > 0) {

			boolean flag = spKey.length > 1 ? true : false; // 是否是多条

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

						// 如果已经到最高级别审核，直接就是最终
						if (level.equals("true")) {
							// 管理员审核
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
								
								if(ttr.getIsConfirm() == 0){//承训单位审核未通过的不能再进行审核
									status = "承训单位确认未通过";
									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"ERROR\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "承训单位确认未通过，不能进行此操作" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}

								status = "已审核";

								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
								ttr.setCheckStatus((short)us.getOrganizationLevel());
								this.iTeacherTrainingRecords.update(ttr);

								// 添加审核记录
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									atc.setTeacherTrainingRecords(ttr);
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
									checkSB.append("\"Message\":\"" + "操作成功" + "\"");
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
									checkSB.append("\"Message\":\"" + "审核状态已经是已审核" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						} else {

							// 不是最终管理员审核

							// 首先判断最终审核
//							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// 如果最终审核状态 不是未审核说明 还没有到最高级别审核

							int uslevel = us.getOrganizationLevel();

							if (checkStatus >= uslevel) {
								
								if(ttr.getIsConfirm() == 0){//承训单位审核未通过的不能再进行审核
									status = "承训单位确认未通过";
									if (!flag) {
										StringBuilder checkSB = new StringBuilder();
										checkSB.append("{");
										checkSB.append("\"Result\":\"ERROR\"");
										checkSB.append(",");
										checkSB.append("\"status\":\"" + status + "\"");
										checkSB.append(",");
										checkSB.append("\"Message\":\"" + "承训单位确认未通过，不能进行此操作" + "\"");
										checkSB.append("}");
										Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
										return;
									}
								}
								// 可以审核
								status = "通过";

								ttr.setCheckStatus((short) (uslevel - 1));
								ttr.setFinalStatus((short)DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
								this.iTeacherTrainingRecords.update(ttr);

								// 添加审核记录
								try {
									AssignTeacherCheck atc = new AssignTeacherCheck();
									atc.setTeacherTrainingRecords(ttr);
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
									checkSB.append("\"Message\":\"" + "操作成功" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}

							} else {
								// 已经审核到上级，没有权限在审核了
								if (!flag) {
									StringBuilder checkSB = new StringBuilder();
									checkSB.append("{");
									checkSB.append("\"Result\":\"ERROR\"");
									checkSB.append(",");
									checkSB.append("\"status\":\"" + status + "\"");
									checkSB.append(",");
									checkSB.append("\"Message\":\"" + "操作失败,您无权限在修改！\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_NOADU)) {

						// 审核不通过
						if (level.equals("true")) {
							// 是最终管理员
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {

								status = "未通过";
								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
								ttr.setCheckStatus((short)us.getOrganizationLevel());
								this.iTeacherTrainingRecords.update(ttr);

								// 添加审核记录
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
									checkSB.append("\"Message\":\"" + "操作成功" + "\"");
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
									checkSB.append("\"Message\":\"" + "审核状态已经是未通过" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}
						} else {

//							if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
								// 如果最终审核状态 不是未审核说明 还没有到最高级别审核

							int uslevel = us.getOrganizationLevel();

							if (checkStatus + 1 >= uslevel) {
								// 可以审核
								status = "未通过";

								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
								ttr.setCheckStatus((short)us.getOrganizationLevel());
								this.iTeacherTrainingRecords.update(ttr);

								// 添加审核记录
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
									checkSB.append("\"Message\":\"" + "操作成功" + "\"");
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
									checkSB.append("\"Message\":\"" + "操作失败,您无权限在修改" + "\"");
									checkSB.append("}");
									Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
									return;
								}
							}

						}

					} else if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_DEL)) {

						if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
							// 可以删除
							this.iTeacherTrainingRecords.delete(ttr);
							status = "已删除";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"OK\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "操作成功" + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						} else {
							status = "不能删除";
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"ERROR\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "已审核，不能删除" + "\"");
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
			checkSB.append("\"Message\":\"" + "操作成功" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "没有审核记录" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}

	}
	
	/**
	 * 获取报送审核详细信息
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
			case 1:
				m = "选派报送";
				break;
			case 2:
				m = "审核通过";
				break;
			case 3:
				m = "审核不通过";
				break;
			case 4:
				m = "终审通过";
				break;
			}

			String name = "";
			if(atc.getTeacherTrainingRecords().getIsConfirm() != null && atc.getTeacherTrainingRecords().getIsConfirm() == 0 && atc.getType() == 3){
				TrainingAdmin ta = this.iTrainingAdminService.get(atc.getChecker());
				name = ta.getTrainingCollege().getName() + ta.getName() + m;
				if(type==3&&atc.getReason()!=null){
					name=name+"</br>原因："+atc.getReason();
				}

				String st = "{\"id\":" + index + ",\"time\":\"" + Utlity.timeSpanToString(atc.getChecktime()) + "\",\"info\":\"" + name + "\"},";

				sb.append(st);

				index++;
				continue;
			}
			
			if(atc.getType() == 6){//调整学科记录
				TrainingAdmin ta = this.iTrainingAdminService.get(atc.getChecker());
				name = ta.getTrainingCollege().getName()+ta.getName()+"调整培训学科";
				if(atc.getReason() != null){
					name=name+"</br>原因：" + atc.getReason();
				}
				String st = "{\"id\":" + index + ",\"time\":\"" + Utlity.timeSpanToString(atc.getChecktime()) + "\",\"info\":\"" + name + "\"},";

				sb.append(st);

				index++;
				continue;
			}
			
			ProjectAdmin pa = this.iProjectAdminService.get(atc.getChecker());
			name = pa.getOrganization().getName() + pa.getName() + m;
			if(type==3&&atc.getReason()!=null){
				name=name+"</br>原因："+atc.getReason();
			}else if(type==1 && atc.getReason()!=null){
				name=name+"</br>原因："+atc.getReason();
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
			// 当前的地区的父地区存在
			Area pae = this.iAreaService.getAreaByCode(areacode);
			areaid1 = pae.getName();
			areacode = pae.getParentcode();
			// 父父地区
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
	 * 初始化成绩页面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String initMarkPage() {
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);

		return "mark";
	}

	/**
	 * 搜索学员成绩
	 */
	@SuppressWarnings("unchecked")
	public void getSearchMark() {

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// 起始页
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}
		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		// 排序 参数
		String sortname = "";
		String sorttype = "";
		String sort = request.getParameter("jtSorting");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			sortname = sortarray[0];
			sorttype = sortarray[1];
		}

		int start = Integer.parseInt(ist);
		int limit = Integer.parseInt(ien);

		// 其他搜索条件
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String name = request.getParameter("teacherName");

		Organization oz = this.iOrganization.get(us.getOrganization());

		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pid", projectid);
		paramMap.put("sid", subjectid);
		paramMap.put("tid", trainingUnit);
		paramMap.put("status", "2");
		paramMap.put("name", name);

		// 搜索出条数
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);

		List lidata = this.iTeacherTrainingRecords.getAduTeacher(paramMap, oz, lityps, start, limit);

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\",");
			sb.append("\"Message\":\"没有搜索到数据!\"");
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

			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];
			// 所属项目，学科，承训单位,姓名，电话，性别，单位，成绩，状态

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
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "男" : "女") + "\",");
			
			sb.append("\"trainingClasshour\":\"" + (ttRecord.getTrainingClasshour() == null ? "" : ttRecord.getTrainingClasshour()) + "\","); 
			sb.append("\"onlineHour\":\"" + (ttRecord.getTrainingOnlineHour() == null ? "" : ttRecord.getTrainingOnlineHour()) + "\","); 
			sb.append("\"certificate\":\"" + (ttRecord.getCertificate() == null ? "" : ttRecord.getCertificate()) + "\","); 

			sb.append("\"mark\":\"" + (ttRecord.getTrainingScore() == null ? "未打分" : ttRecord.getTrainingScore()) + "\",");

			switch (ttRecord.getTrainingStatus()) {
			case DictionyMap.TEACHER_TRAINING_RECORDS_Transaction:
				sb.append("\"status\":\"异动\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_NOReport:
				sb.append("\"status\":\"未报到\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_Report:
				sb.append("\"status\":\"已报到\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_Graduation:
				sb.append("\"status\":\"已结业\",");
				break;
			case DictionyMap.TEACHER_TRAINING_RECORDS_NoGraduation:
				sb.append("\"status\":\"未结业\",");
				break;
			}

			if (ttRecord.getTrainingScore() == null) {
				sb.append("\"show\":" + false + "");
			} else {
				sb.append("\"show\":" + true + "");
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
	 * 导出学员成绩
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

		String name = request.getParameter("teacherName");
		Organization oz = this.iOrganization.get(us.getOrganization());
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pid", projectid);
		paramMap.put("sid", subjectid);
		paramMap.put("tid", trainingUnit);
		paramMap.put("status", "2");
		paramMap.put("name", name);
		int records = this.iTeacherTrainingRecords.getAduTeacherCount(paramMap, oz, lityps);
		List li = this.iTeacherTrainingRecords.getAduTeacher(paramMap, oz, lityps, 0, records);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员成绩表");
		HSSFRow row = s.createRow(0);
		String title[] = { "项目名称", "培训学科", "承训院校", "学员姓名", "所属地区", "所在学校", "身份证号", "年龄", "性别", "民族", "政治面貌", "教龄", "职务", "职称","手机","培训状态","成绩","结业证编号","备注" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];
			t++;
			short tStatus = ttRecord.getTrainingStatus();
			row = s.createRow(t);
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			String tIdcard = th.getIdcard();
			String tName = th.getName();
			String tSex = (th.getSex() == 1 ? "男" : "女");
			String tAge = String.valueOf(Utlity.getAge(th.getBirthday()));
			String tEthnic = th.getEthnic().getName();
			String tPolitics = th.getPolitics().getName();
			String schoolName = th.getOrganization().getName();
			String tTeachAge = String.valueOf(Utlity.getAge(th.getTeachingAge()));
			String tDuty = th.getJobDuty().getName();
			String tTitle = th.getJobTitle().getName();
			String tMobile = th.getMobile();	
			String tcertificate = ttRecord.getCertificate();
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
				ttStatus="学员异动";
			}else if(tStatus == 1){
				ttStatus="未报到";
			}else if(tStatus == 2){
				ttStatus="已报到";
			}else if(tStatus == 3){
				ttStatus="培训合格";
			}else if(tStatus == 4){
				ttStatus="培训不合格";
			}
			String tScore="";
			if(ttRecord.getTrainingScore()!=null){
				tScore=ttRecord.getTrainingScore().toString();
			}
			
			//添加备注 备注信息为教师替换信息
			String remarks = "";
			if(ttRecord.getReplaceStatus() == 1){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="由教师 "+tt.getName()+" 替换";
			}else if(ttRecord.getReplaceStatus() == 2){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="替换了教师  "+tt.getName();
			}
			
			String tInfo[] = { projectName, subjectName, trainingName, tName, tArea, schoolName, tIdcard, tAge, tSex, tEthnic, tPolitics, tTeachAge, tDuty, tTitle, tMobile, ttStatus,tScore,tcertificate,remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=score.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		
	}
	
	public String initTeacherInfo(){
		initServlert();
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
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
			// 如果不是学校，下级还有部门
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

			// 输出下级机构表头，计划分配学员和实际报送学员都为零的列不显示
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
			// 输出承训机构、培训科目的左边栏，计划分配学员和实际报送学员都为零的行不显示
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
			// 输出计划分配学员和实际报送学员都不为零的单元格
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
	
	/**
	 * 一次性使用处理方法
	 * 用来批量导入指定的审核未通过的人员名单
	 */
	public void inputScore() {
		initServlert();
		DecimalFormat df = new DecimalFormat("#");
		String pageStatus = "OK";
		String message = "文件上传成功";
		String rString = "";
		String Path = "";
		HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
		try {
			HashMap<Integer, HashMap<String, String>> p;
			initServlert();
			UserSession us = (UserSession) session.getAttribute("usersession");
			HttpSession session = request.getSession();
			hmFiles = (HashMap<EDocumentType, fileInfo>) session
					.getAttribute("uploadfile");
			if (hmFiles == null) {
				pageStatus = "ERROR";
				message = "未上传Excel";
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
					String rootpath = this.getClass().getClassLoader()
							.getResource("/").getPath()
							+ "../..";
					Path = rootpath + fileInfo.getFilePath();
				}
				InputStream is = null;
				try {
					is = new FileInputStream(Path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				HSSFWorkbook wb = new HSSFWorkbook(is);
//				HSSFSheet s = wb.getSheet("学员名单");
				HSSFSheet s = wb.getSheet("Sheet1");
				HSSFRow row;
				p = new HashMap<Integer, HashMap<String, String>>();
//				String rtrainingName = iTrainingCollegeService.get(
//						us.getOrganization()).getName();
				int t = s.getLastRowNum();
				for (int i = 1; i <= s.getLastRowNum(); i++) {// 解析Excel过程
					row = s.getRow(i);
					
					String index = "";
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
					
					if(row.getCell(0)!=null){
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						index = row.getCell(0).getStringCellValue();
					}
					if(row.getCell(1)!=null){
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						projectName = row.getCell(1).getStringCellValue();
					}
					if(row.getCell(2)!=null){
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						subjectName = row.getCell(2).getStringCellValue();
					}
					if(row.getCell(3)!=null){
						row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
						trainCollege = row.getCell(3).getStringCellValue();
					}
					if(row.getCell(4)!=null){
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						teacherName = row.getCell(4).getStringCellValue();
					}
					if(row.getCell(5)!=null){
						row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
						organization = row.getCell(5).getStringCellValue();
					}
					if(row.getCell(6)!=null){
						row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
						school = row.getCell(6).getStringCellValue();
					}
					if(row.getCell(7)!=null){
						row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
						idcard = row.getCell(7).getStringCellValue();
					}
					if(row.getCell(8)!=null){
						row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
						phone = row.getCell(8).getStringCellValue();
					}
					if(row.getCell(9)!=null){
						row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
						email = row.getCell(9).getStringCellValue();
					}
					if(row.getCell(10)!=null){
						row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
						status = row.getCell(10).getStringCellValue();
					}
					if(row.getCell(11)!=null){
						row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
						reason = row.getCell(11).getStringCellValue();
					}
//					System.out.println(i);
					
//					System.out.println("哈哈哈");
					
					List ttrs = this.iTeacherTrainingRecords.getAduTeacherByIdcard(350000087, 251, 35, 0, idcard, 0, 100);
//					System.out.println("000000000000+"+ttrs.size());
					if(ttrs.size()>0){
						Object[] obj = (Object[])ttrs.get(0);
						TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
//						System.out.println(ttr.getTeacher().getName());
						
						ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
						this.iTeacherTrainingRecords.update(ttr);

						// 添加审核记录
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
					}
				}
			}
		} catch (IOException e) {
			pageStatus = "ERROR";
			message = "上传的文件有误";
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
			Utlity.ResponseWrite(rString, "json", response);
		}
		rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
				+ "\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}

	/**
	 * 定时发送进度百分比
	 */
	public void getPercent() {
		initServlert();
		String pageStatus = "OK";
		String message = "";
		String rString = "";
		int percent = (int) session.getAttribute("percent");
		int maxIndex = (int) session.getAttribute("maxIndex");
		if (percent >= 0 && maxIndex >= 0) {
			message = "文件处理成功";
		} else {
			pageStatus = "ERROR";
			message = "上传过程中发生异常,上传失败";
		}
		rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
				+ "\",\"Percent\":\"" + percent + "\",\"MaxIndex\":\""
				+ maxIndex + "\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	/**
	 * 初始化替换学员页
	 * @return
	 */
	public void replaceTeacherInit(){
		initServlert();
		
//		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		String ttrId = request.getParameter("ttrId");
		
		if(ttrId != null && !"".equals(ttrId)){
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.parseInt(ttrId));
//			if(us.getOrganizationLevel() > 1){
//				if (ttr.getCheckStatus() + 1 <= us.getOrganizationLevel()) {
//					sb.append("{");
//					sb.append("\"status\":\"ERROR\",");
//					sb.append("\"message\":\"该学员已报名或已有成绩，不能进行替换操作!\"");
//					sb.append("}");
//					Utlity.ResponseWrite(sb.toString(), "application/json", response);
//					return;
//				}
//			}
			if(ttr.getReplaceStatus() != null && ttr.getReplaceStatus() == 1){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"该学员已经被替换，不能进行替换操作!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if(ttr.getReplaceStatus() != null && ttr.getTrainingStatus() >= 2){
				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"该学员已报名或已有成绩，不能进行替换操作!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}
		sb.append("{");
		sb.append("\"status\":\"OK\",");
		sb.append("\"message\":\"成功!\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		return;

//		return "replace";
	}
	
	/**
	 * 通过条件查询替换的学员名单
	 */
	public void searchReplaceTeachers(){
		/**
		 * 搜索 学员
		 */
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization oz = this.iOrganization.get(us.getOrganization());

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("teacherIdCard", request.getParameter("teacherIdCard"));
		paramsMap.put("teacherName", request.getParameter("teacherName"));
		paramsMap.put("age1", request.getParameter("age1"));
		paramsMap.put("age2", request.getParameter("age2"));
		paramsMap.put("tage1", request.getParameter("tage1"));
		paramsMap.put("tage2", request.getParameter("tage2"));
		paramsMap.put("school", request.getParameter("organization"));
		paramsMap.put("sex", request.getParameter("sex"));
		paramsMap.put("isMu", request.getParameter("isMu"));
		paramsMap.put("ethnic", request.getParameter("ethnic"));
		paramsMap.put("jobTitle", request.getParameter("jobTitle"));
		paramsMap.put("chineseLanguageLevel", request.getParameter("chineseLanguageLevel"));
		paramsMap.put("mainTeachingSubject", request.getParameter("mainGrade"));
		paramsMap.put("politics", request.getParameter("politics"));
		paramsMap.put("jobDuty", request.getParameter("jobDuty"));
		paramsMap.put("eductionBackground", request.getParameter("eductionBackground"));
		paramsMap.put("mainTeachingLanguage", request.getParameter("mainTeachingLanguage"));
		paramsMap.put("mainTeachingGrades", request.getParameter("mainTeachingGrades"));
		paramsMap.put("organization", oz);

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

		// 搜索参数
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		List<Teacher> liT = this.getiTeacherService().getTeachers(paramsMap, offset, DictionyMap.maxPageSize);

		if (liT == null || liT.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"没有搜索到数据!\"");
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
			Object[] ot = (Object[]) obj;
			Teacher t = (Teacher) ot[0];
			if(teacherId.equals(t.getId().toString())){//被替换的学员不应该出现在列表里
				continue;
			}
			
			
			Organization o = (Organization) ot[1];

			StringBuilder tb = new StringBuilder();
			tb.append("{");

			tb.append("\"idcard\":\"" + t.getIdcard() + "\",");
			tb.append("\"name\":\"" + t.getName().trim() + "\",");
			tb.append("\"school\":\"" + o.getName() + "\",");
			tb.append("\"mobile\":\"" + t.getMobile() + "\",");
			tb.append("\"sexIndex\":\"" + (t.getSex()) + "\",");
			tb.append("\"sex\":\"" + (t.getSex() == 1 ? "男" : "女") + "\",");
			tb.append("\"age\":\"" + String.valueOf(Utlity.getAge(t.getBirthday())) + "\",");
			tb.append("\"ethnic\":\"" + t.getEthnic().getName() + "\",");
			tb.append("\"politic\":\"" + t.getPolitics().getName() + "\",");

			tb.append("\"tage\":\"" + String.valueOf(Utlity.getAge(t.getTeachingAge())) + "\",");
			tb.append("\"jobTitle\":\"" + t.getJobTitle().getName() + "\",");
			tb.append("\"jobDuty\":\"" + t.getJobDuty().getName() + "\",");
			tb.append("\"isMu\":\"" + (t.getMultiLanguage() ? "是" : "否") + "\",");

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
				tb.append("\"creator\":\"自主注册\",");
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
			if(ttr != null && ttr.getFinalStatus() == 0){
				tb.append("\"record_status\":" + true);
			}else if(ttr == null){
				tb.append("\"record_status\":" + true);
			}else if (ttr != null && ttr.getFinalStatus() ==1) {
				tb.append("\"record_status\":" + false);
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
			teacherEx.setSexString(teacher.getSex() == 1 ? "男" : "女");
			if (teacher.getTeachingAge() != null) {
				teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher.getTeachingAge())));
			}
			
			teacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "是" : "否");
			// 获取主要教学语言
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				teacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				teacherEx.setMainTeachingClass(teachingGrade);
			}

			// 获取主要教学学科
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
			reTeacherEx.setSexString(teacher.getSex() == 1 ? "男" : "女");
			if (teacher.getTeachingAge() != null) {
				reTeacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher.getTeachingAge())));
			}
			
			reTeacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "是" : "否");
			// 获取主要教学语言
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				reTeacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				reTeacherEx.setMainTeachingClass(teachingGrade);
			}

			// 获取主要教学学科
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
	 * 添加替换学员记录
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
		
		Integer ottrId = 0;
		if(ttrId != null && !"".equals(ttrId)){
		
			ottrId = Integer.parseInt(ttrId);
		}
		TeacherTrainingRecords reTtr = this.iTeacherTrainingRecords.get(ottrId);
		
		if(reTtr.getReplaceStatus() != null && reTtr.getReplaceStatus() == 1){
			//已经是被替换（异动）状态的学员不能被替换
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"REPLACE\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "该教师已经被替换过，请重新选择被替换的教师。" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
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
	
				// 获取已经分配的人数
				String ttrsqlCount = "select count(*) from teacher_training_records t where t.project=" + projectId;
				ttrsqlCount += " and t.subject=" + subjectId + " and t.training_college=" + tcid;
	
				List littrs = this.iTeacherTrainingRecords.executeSQL(ttrsqlCount, null);
				int ttrCount = Integer.valueOf(littrs.get(0).toString());
	
				if (ttrCount >= totalCount) {
					// 前端提示仍然确认添加，仍然可以添加此人。
					if (goon == null || goon.equals("") || goon.equals("0")) {
						// 人数已经达到上限
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"WARNING\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "人数达到上限！" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}
				}
	
				// 一个学员在一个年度中的一个项目大类的一个学科只能申报一次
				List<Project> projectList = this.iProjectService.getSameTypeProjectList(projectId);
				StringBuilder trainedCount = new StringBuilder();
				trainedCount.append("select count(*) from teacher_training_records t where 1=1");
				trainedCount.append(" and t.teacher=").append(teacherId);
				trainedCount.append(" and t.subject=").append(subjectId);
				if (projectList != null && projectList.size() > 0) {
					trainedCount.append(" and t.project in (");
					String comba = "";
					for (Project project : projectList) {
						trainedCount.append(comba);
						trainedCount.append(project.getId());
						comba = ",";
					}
					trainedCount.append(")");
				}
				// trainedCount.append(" and t.final_status <>").append(DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
	
				littrs = this.iTeacherTrainingRecords.executeSQL(trainedCount.toString(), null);
				ttrCount = Integer.valueOf(littrs.get(0).toString());
				// 存在过报送/培训记录
				if (ttrCount > 0) {
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"FAIL\"");
					checkSB.append(",");
					TrainingSubject subject = this.iTrainingSubjectService.get(Short.valueOf(subjectId));
					checkSB.append("\"Message\":\"" + "该教师已经报送过本年度相同项目类型的" + subject.getName() + "学科培训！" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					return;
				}
				
				//添加一条学员替换记录
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
				params.put("status", 1);
				
				// 排序
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
					checkSB.append("\"Message\":\"" + "该学员正在替换中，请等待管理员审核后再继续。。。" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					return;
				}
				
				this.iTeacherTrainingReplaceService.add(ttReplace);
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"OK\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"" + "添加替换记录成功" + "\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
			
			
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "添加替换记录失败" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		}
	}
	
	/**
	 * 初始化审核替换学员页面
	 * @return
	 */
	public String initReplaceAdu() {
		initServlert();
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.endsWith("0")){
			map.put("year", year);
		}
		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		
		return "replaceAdu";
	}
	
	/**
	 * 获取替换学员记录列表
	 */
	public void getReplaceTeacherList(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		
		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

//		// 分页
//		String ist = (String) request.getParameter("page");
//		if (ist == null || ist.equals("") || ist.equals("NaN")) {
//			ist = "1";
//		}
//		int start = Integer.parseInt(ist);

		// 其他搜索条件
		String projectid = request.getParameter("projectId");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectId");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("tcId");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(Integer.parseInt(projectid) > 0){
			param.put("projectId", projectid);
		}
		if(Integer.parseInt(subjectid) >0){
			param.put("subjectId", subjectid);
		}
		param.put("trainCollege", trainingUnit);
		param.put("status", status);
		
		
		// 起始页
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}

		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);

		// 排序
		String sort = request.getParameter("jtSorting");

		// 排序 参数
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
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			
			if(lstTTre != null && lstTTre.size() > 0){
				sb.append("\"TotalRecordCount\":" + ttreRecordsCount);
				sb.append(",");
				sb.append("\"Records\":[");
				for(TeacherTrainingReplace ttr : lstTTre){
					TeacherTrainingRecords t = ttr.getBereplacedttr();
					Teacher teacher = t.getTeacher();//被替换学员
					Teacher reTeacher = ttr.getTeacher();//替换学员
					TrainingCollege tc = this.iTrainingCollegeService.get(ttr.getTrainCollege());//承训单位
					
					sb.append("{");
					sb.append("\"id\":" + ttr.getId());
					sb.append(",");
					String name = teacher.getOrganization().getName()+"教师-"+teacher.getName();
					sb.append("\"name\":\"" + name + "\"");
					sb.append(",");
					sb.append("\"idCard\":\"" + teacher.getIdcard() + "\"");
					sb.append(",");
					
					String reName = reTeacher.getOrganization().getName()+"教师-"+reTeacher.getName();
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
					String reStatus = "未审核";
					if(ttr.getReplaceStatus() == 0){
						reStatus = "未通过";
					}else if(ttr.getReplaceStatus() == 2){
						reStatus = "已通过";
					}
					sb.append("\"reStatus\":\"" + reStatus + "\"");
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
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String reviewReplaceTeacherInit(){
		initServlert();
		String hqlString = "";
		if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
			Integer ttrid = Integer.valueOf(request.getParameter("id"));
			
			TeacherTrainingReplace ttr = this.iTeacherTrainingReplaceService.get(ttrid);
			
			/*
			 * 替换学员信息
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
			teacherEx.setSexString(teacher.getSex() == 1 ? "男" : "女");
			if (teacher.getTeachingAge() != null) {
				teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher.getTeachingAge())));
			}
			
			teacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "是" : "否");
			// 获取主要教学语言
			hqlString = "from TeachingLanguage where teacher=" + teacher.getId() + " and isprime=true";

			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				teacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				teacherEx.setMainTeachingClass(teachingGrade);
			}

			// 获取主要教学学科
			hqlString = "from TeachingSubject where teacher=" + teacher.getId() + " and isprime=1";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				teacherEx.setMainTeachingCourse(teachingSubject);
			}
			teacherEx.setOrganization(teacher.getOrganization());
			
			/*
			 * 原报送学员信息
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
			reTeacherEx.setSexString(reTeacher.getSex() == 1 ? "男" : "女");
			if (reTeacher.getTeachingAge() != null) {
				reTeacherEx.setTeachingAge(String.valueOf(Utlity.getAge(reTeacher.getTeachingAge())));
			}
			
			reTeacherEx.setIsMultiLanguage(reTeacher.getMultiLanguage() ? "是" : "否");
			// 获取主要教学语言
			hqlString = "from TeachingLanguage where teacher=" + reTeacher.getId() + " and isprime=true";

			List<TeachingLanguage> reLstTeachingLanguages = iTeachingLanguageService.getListByHSQL(hqlString);
			if (reLstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = reLstTeachingLanguages.get(0);
				reTeacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + reTeacher.getId() + " and isprime=1";
			List<TeachingGrade> reLstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
			if (reLstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = reLstTeachingGrades.get(0);
				reTeacherEx.setMainTeachingClass(teachingGrade);
			}

			// 获取主要教学学科
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
	 * 审核替换学员
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
				
				if("pass".equals(method)){//审核通过
					
					if(ttre.getReplaceStatus() == 2){
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"OK\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "审核成功，学员替换成功" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}
					
					TeacherTrainingRecords ttr = ttre.getBereplacedttr();
					Teacher teacher = ttre.getTeacher();
					Integer projectId = ttre.getProject();
					Short subjectId = ttre.getSubject();
					Integer trainingCollegeId = ttre.getTrainCollege();
					TeacherTrainingRecords rettr = new TeacherTrainingRecords();
					
					ProjectAdmin pa = this.iProjectAdminService.get(ttre.getCreator());
//					System.out.println(pa.getName());
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
					
					// 其他状态
					rettr.setFinalStatus((short) 2);//直接最终审核通过
					rettr.setTrainingStatus((short) 1);
//					
//					//替换操作处理
					rettr.setReplaceStatus((short)2);//替换状态
					rettr.setReplaceTeacher(ttr.getTeacher().getId());//被替换的学员ID
					
					ttr.setReplaceStatus((short)1);
					ttr.setReplaceTeacher(teacher.getId());
					
					//如果是已经审核不通过的 就不做处理了
					if(ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS){
						ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
						
						this.iTeacherTrainingRecords.update(ttr);
						// 添加审核记录
						try {
							AssignTeacherCheck atc = new AssignTeacherCheck();
							String reason = "由教师"+teacher.getName()+"替换";
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
						// 添加审核记录
						try {
							AssignTeacherCheck atc = new AssignTeacherCheck();
							String reason = "由教师"+teacher.getName()+"替换";
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
					this.iTeacherTrainingRecords.add(rettr);
					// 添加报送记录

					AssignTeacherCheck atc = new AssignTeacherCheck();
					atc.setChecker(pa.getId());
					atc.setTeacherTrainingRecords(rettr);
					atc.setType((short) 1);
					atc.setChecktime(ttre.getCreattime());
					String reason = "由"+pa.getOrganization().getName()+"管理员-"+pa.getName()+",申请替换"+ttr.getTeacher().getOrganization().getName()+"学员："+ttr.getTeacher().getName();
					atc.setReason(reason);
					this.iAssignTeacherCheckService.add(atc);
					
					AssignTeacherCheck atcc = new AssignTeacherCheck();
					atcc.setChecker(us.getId());
					atcc.setTeacherTrainingRecords(rettr);
					atcc.setType((short) 1);
//					atcc.setChecktime(ttre.getCreattime());
					String reasons = "";
					reasons+="由"+this.iOrganization.get(us.getOrganization()).getName()+"管理员-"+us.getName()+",批准审核通过替换操作";
					atcc.setReason(reasons);
					this.iAssignTeacherCheckService.add(atcc);
					
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"OK\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "审核成功，学员替换成功" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					
					
				}else{//审核不通过不做任何操作
					
					if(ttre.getReplaceStatus() == 0){
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"OK\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "审核不通过成功" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}else if(ttre.getReplaceStatus() == 2){
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"ERROR\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\"" + "已经审核通过不能修改。" + "\"");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}
					
					ttre.setReplaceStatus((short)0);
					this.iTeacherTrainingReplaceService.update(ttre);
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"OK\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"" + "审核不通过成功" + "\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"" + "审核失败，您无权进行审核操作" + "\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	// 属性

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

//	public Teacher getTtTeacher() {
//		return ttTeacher;
//	}
//
//	public void setTtTeacher(Teacher ttTeacher) {
//		this.ttTeacher = ttTeacher;
//	}

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
	
	
}
