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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.ChineseLanguageLevel;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectLevel;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IAssignTeacherCheckService;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.service.IChineseLanguageLevelService;
import cn.zeppin.service.IJobDutyService;
import cn.zeppin.service.IJobTitleService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectSubjectRangeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 培训成绩管理功能
 * @author Administrator
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class TeacherTrainingResultAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(TeacherTrainingAssignedAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private IProjectService iProjectService;
	private ITrainingSubjectService iTrainingSubjectService;
	private ITeacherService iTeacherService;
	private IProjectSubjectRangeService iProjectSubjectRangeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecords;
	private ITrainingCollegeService iTrainingCollegeService; // 承训单位
	private IProjectAdminService iProjectAdminService; // 管理员信息
	private IOrganizationService iOrganization; // 组织架构
	private IProjectApplyService iProjectApplyService;
	
	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作
	
	private IJobDutyService iJobDutyService;// 职务
	private IJobTitleService iJobTitleService;// 职称
	private IChineseLanguageLevelService iChineseLanguageLevelService;// 汉语言水平
	private IAreaService iAreaService;// 地区操作
	
	private IAssignTeacherTaskService iAssignTeacherTaskService;
	private IAssignTeacherCheckService iAssignTeacherCheckService;
	
	private List<String> searchYear;
	private List<Project> searchProject;
//	private List<ProjectType> searchProjectType;
	private HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
	private String pageStatus;// 页面状态
	private String message;// 错误信息
	private String organizationLevel;
	private List<JobTitle> lstJobTitles;// 职称
	private List<JobDuty> lstJobDuties;// 职务
	private List<ChineseLanguageLevel> lstChineseLanguageLevels;// 汉语言水平
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	public void sendResponse(String status, String value) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	public void getAssignTaskProject(){
		initServlert();
		
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String year = this.request.getParameter("year");
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("0")){
			map.put("year", year);
		}
		map.put("isAdvance", (short)1);
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
	
	public void getAssignTaskSubject() {
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
		String projectId = request.getParameter("projectId") == null ? "0" : request.getParameter("projectId");
		
		List<ProjectSubjectRange> lst = this.iProjectSubjectRangeService.getListByProject(Integer.parseInt(projectId));
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append("\"subjects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
		if(lst != null && lst.size() > 0){
			
			for(ProjectSubjectRange psr : lst){
				TrainingSubject ts = psr.getTrainingSubject();
				short key = ts.getId();
				String name = ts.getName();
				String sr = "{\"id\":" + key + ",\"name\":\"" + name + "\",\"search\":\"" + name + pinyingUtil.getFirstSpell(name) + "\"},";
				sb.append(sr);
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]}");
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 五五周期
	 * @return
	 */
	public String initPage(){
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		Map<String, String> sortParams = new HashMap<>();
		sortParams.put("year", "desc");
		Map<String, Object> map = new HashMap<>();
		map.put("organization", us.getOrganization());
//		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
//		this.searchProject = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_PLAN);
		 
		
		map.put("projectCycle", 1);
		this.searchProject = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Object[] obj = (Object[])list.get(i);
				Project p = (Project)obj[0];
				this.searchProject.add(p);
			}
		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		return "presult";
//		initServlert();
//		String year = request.getParameter("year");
//		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchYear = this.iProjectService.getProjectYearList();
//		Map<String,Object> map = new HashMap<String, Object>();
//		if(year != null && !year.equals("") && !year.equals("0")){
//			map.put("year", year);
//		}
//		this.searchProject = this.iProjectService.getProjectByParams(map,null,lityps);
//
//		return "presult";
	}
	
	/**
	 * 六五周期
	 * @return
	 */
	public String init65Page(){
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		Map<String, String> sortParams = new HashMap<>();
		sortParams.put("year", "desc");
		Map<String, Object> map = new HashMap<>();
		map.put("organization", us.getOrganization());
//		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
//		this.searchProject = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_PLAN);
		 
		map.put("projectCycle", 2);
		this.searchProject = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Object[] obj = (Object[])list.get(i);
				Project p = (Project)obj[0];
				this.searchProject.add(p);
			}
		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		return "presult65";
//		initServlert();
//		String year = request.getParameter("year");
//		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchYear = this.iProjectService.getProjectYearList();
//		Map<String,Object> map = new HashMap<String, Object>();
//		if(year != null && !year.equals("") && !year.equals("0")){
//			map.put("year", year);
//		}
//		this.searchProject = this.iProjectService.getProjectByParams(map,null,lityps);
//
//		return "presult";
	}
	
	/**
	 * 其他项目培训记录--五五周期
	 * 只有校本及县级有
	 * @return
	 */
	public String initOtherPage(){
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		Map<String, String> sortParams = new HashMap<>();
		sortParams.put("year", "desc");
		Map<String, Object> map = new HashMap<>();
		map.put("projectCycle", 1);
		Organization oo = this.iOrganization.get(us.getOrganization());
		if(us.getOrganizationLevel()>2){
			if(us.getOrganizationLevel() == 3){
				map.put("organization", oo.getOrganization().getId());//市级
				this.searchProject = new ArrayList<Project>();
				List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}else{
				map.put("organization", oo.getOrganization().getId());//县级
				this.searchProject = new ArrayList<Project>();
				List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
				map.put("organization", oo.getOrganization().getOrganization().getId());//市级
//				this.searchProject = new ArrayList<Project>();
				List list2 = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
				if(list2 != null && list2.size() > 0){
					for(int i = 0; i < list2.size(); i++){
						Object[] obj = (Object[])list2.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}
		}
		
//		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
//		this.searchProject = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_PLAN);
		 
		
//		this.searchProject = new ArrayList<Project>();
//		List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
//		if(list != null && list.size() > 0){
//			for(int i = 0; i < list.size(); i++){
//				Object[] obj = (Object[])list.get(i);
//				Project p = (Project)obj[0];
//				this.searchProject.add(p);
//			}
//		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		return "opresult";
//		initServlert();
//		String year = request.getParameter("year");
//		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchYear = this.iProjectService.getProjectYearList();
//		Map<String,Object> map = new HashMap<String, Object>();
//		if(year != null && !year.equals("") && !year.equals("0")){
//			map.put("year", year);
//		}
//		this.searchProject = this.iProjectService.getProjectByParams(map,null,lityps);
//
//		return "presult";
	}
	
	/**
	 * 其他项目培训记录--六五周期
	 * 只有校本及县级有
	 * @return
	 */
	public String initOther65Page(){
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		Map<String, String> sortParams = new HashMap<>();
		sortParams.put("year", "desc");
		Map<String, Object> map = new HashMap<>();
		map.put("projectCycle", 2);
		Organization oo = this.iOrganization.get(us.getOrganization());
		if(us.getOrganizationLevel()>2){
			if(us.getOrganizationLevel() == 3){
				map.put("organization", oo.getOrganization().getId());//市级
				this.searchProject = new ArrayList<Project>();
				List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}else{
				map.put("organization", oo.getOrganization().getId());//县级
				this.searchProject = new ArrayList<Project>();
				List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
				map.put("organization", oo.getOrganization().getOrganization().getId());//市级
//				this.searchProject = new ArrayList<Project>();
				List list2 = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
				if(list2 != null && list2.size() > 0){
					for(int i = 0; i < list2.size(); i++){
						Object[] obj = (Object[])list2.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}
		}
		
//		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);
//		this.searchProject = this.iProjectService.getProjectByStatusAndType(DictionyMap.releaseProject, lityps, DictionyMap.PROJECT_ENROLL_TYPE_PLAN);
		 
		
//		this.searchProject = new ArrayList<Project>();
//		List list = this.iProjectService.getProjectListByParams(map, sortParams, lityps, -1, -1);
//		if(list != null && list.size() > 0){
//			for(int i = 0; i < list.size(); i++){
//				Object[] obj = (Object[])list.get(i);
//				Project p = (Project)obj[0];
//				this.searchProject.add(p);
//			}
//		}

		lstChineseLanguageLevels = this.getiChineseLanguageLevelService().loadAll();
		lstJobDuties = this.getiJobDutyService().loadAll();
		lstJobTitles = this.getiJobTitleService().loadAll();
		return "opresult65";
//		initServlert();
//		String year = request.getParameter("year");
//		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchYear = this.iProjectService.getProjectYearList();
//		Map<String,Object> map = new HashMap<String, Object>();
//		if(year != null && !year.equals("") && !year.equals("0")){
//			map.put("year", year);
//		}
//		this.searchProject = this.iProjectService.getProjectByParams(map,null,lityps);
//
//		return "presult";
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

//		String organizationid = request.getParameter("organizationName");
//		organizationid = organizationid == null ? "0" : organizationid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

//		String trainingUnit = request.getParameter("trainingUnit");
//		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String teacherName = request.getParameter("teacherName");

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

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
//		if(status.equals("1")){//未审核
//			status = "2";
//		}

		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
			}
		}

//		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
//		if (organizationid.equals("0")) {
//			oz = this.iOrganization.get(us.getOrganization());
//		}
		Organization oz = this.iOrganization.get(us.getOrganization());
		// 搜索出条数
		int records = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, Integer.valueOf(projectCycle), lityps);

		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecords.getLevelAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, Integer.valueOf(projectCycle), lityps, offset, DictionyMap.maxPageSize);

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
			if(us.getOrganizationLevel() == 4){
				sb.append("\"trainingName\":\"" + oz.getName() + "\",");
			}else{
				sb.append("\"trainingName\":\"" + ttRecord.getTrainingCollege().getName() + "\",");
			}
			
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
			sb.append("\"authorized\":\"" + (th.getAuthorized() == 1 ? "在编" : "非编") + "\",");
			if(th.getStatus()==1){
				sb.append("\"workStatus\":\"在职\",");
			}else if(th.getStatus()==2){
				sb.append("\"workStatus\":\"离职\",");
			}else{
				sb.append("\"workStatus\":\"转出\",");
			}
			ProjectAdmin pa = this.iProjectAdminService.get(ttRecord.getCreator());
			if (pa != null) {
				sb.append("\"creator\":\"" + pa.getName() + "\",");
			} else {
				sb.append("\"creator\":\"" + "" + "\",");
			}

			int finalStatus = (int) ttRecord.getFinalStatus();

//			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
//				sb.append("\"status\":\"" + "已通过" + "\",");
//			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
//				sb.append("\"status\":\"" + "未审核" + "\",");
//			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
//				sb.append("\"status\":\"" + "未通过" + "\",");
//			}
			sb.append("\"status\":\"" + finalStatus + "\",");
			
			String ttStatus="";
			if (ttRecord.getTrainingStatus() == 0) {
				ttStatus="学员异动";
			}else if(ttRecord.getTrainingStatus() == 1){
				ttStatus="未报到";
			}else if(ttRecord.getTrainingStatus() == 2){
				ttStatus="已报到";
			}else if(ttRecord.getTrainingStatus() == 3){
				ttStatus="培训合格";
			}else if(ttRecord.getTrainingStatus() == 4){
				ttStatus="培训不合格";
			}else if(ttRecord.getTrainingStatus() == 5){
				ttStatus="培训优秀";
			}else if(ttRecord.getTrainingStatus() == 6){
				ttStatus="培训良好";
			}
			String tScore="";
			if(ttRecord.getTrainingScore()!=null){
				tScore=ttRecord.getTrainingScore().toString();
			}
			String reason = "";
			if(ttRecord.getTrainingReason() != null){
				reason = ttRecord.getTrainingReason();
			}
			String certificate = "";
			if(ttRecord.getCertificate() != null){
				certificate = ttRecord.getCertificate();
			}
			sb.append("\"trainingStatus\":\"" + ttStatus + "\",");
			sb.append("\"score\":\"" + tScore + "\",");
			sb.append("\"reason\":\"" + reason + "\",");
			sb.append("\"certificate\":\"" + certificate + "\",");

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
	
	public void searchOAduPage() {

		/**
		 * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
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

//		String organizationid = request.getParameter("organizationName");
//		organizationid = organizationid == null ? "0" : organizationid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

//		String trainingUnit = request.getParameter("trainingUnit");
//		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String teacherName = request.getParameter("teacherName");

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

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
//		if(status.equals("1")){//未审核
//			status = "2";
//		}

		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
			}
		}

//		Organization oz = this.iOrganization.get(Integer.valueOf(organizationid));
//		if (organizationid.equals("0")) {
//			oz = this.iOrganization.get(us.getOrganization());
//		}
		Organization oz = this.iOrganization.get(us.getOrganization());
		// 搜索出条数
		int records = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, lityps,us.getOrganizationLevel(), Integer.valueOf(projectCycle));

		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecords.getLevelAduTeacher(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, lityps, offset, DictionyMap.maxPageSize,us.getOrganizationLevel(), Integer.valueOf(projectCycle));

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
			if(us.getOrganizationLevel() == 4){
				sb.append("\"trainingName\":\"" + oz.getName() + "\",");
			}else{
				sb.append("\"trainingName\":\"" + ttRecord.getTrainingCollege().getName() + "\",");
			}
			
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
			sb.append("\"authorized\":\"" + (th.getAuthorized() == 1 ? "在编" : "非编") + "\",");
			if(th.getStatus()==1){
				sb.append("\"workStatus\":\"在职\",");
			}else if(th.getStatus()==2){
				sb.append("\"workStatus\":\"离职\",");
			}else{
				sb.append("\"workStatus\":\"转出\",");
			}
			ProjectAdmin pa = this.iProjectAdminService.get(ttRecord.getCreator());
			if (pa != null) {
				sb.append("\"creator\":\"" + pa.getName() + "\",");
			} else {
				sb.append("\"creator\":\"" + "" + "\",");
			}

			int finalStatus = (int) ttRecord.getFinalStatus();

//			if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
//				sb.append("\"status\":\"" + "已通过" + "\",");
//			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
//				sb.append("\"status\":\"" + "未审核" + "\",");
//			} else if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
//				sb.append("\"status\":\"" + "未通过" + "\",");
//			}
			sb.append("\"status\":\"" + finalStatus + "\",");
			
			String ttStatus="";
			if (ttRecord.getTrainingStatus() == 0) {
				ttStatus="学员异动";
			}else if(ttRecord.getTrainingStatus() == 1){
				ttStatus="未报到";
			}else if(ttRecord.getTrainingStatus() == 2){
				ttStatus="已报到";
			}else if(ttRecord.getTrainingStatus() == 3){
				ttStatus="培训合格";
			}else if(ttRecord.getTrainingStatus() == 4){
				ttStatus="培训不合格";
			}else if(ttRecord.getTrainingStatus() == 5){
				ttStatus="培训优秀";
			}else if(ttRecord.getTrainingStatus() == 6){
				ttStatus="培训良好";
			}
			String tScore="";
			if(ttRecord.getTrainingScore()!=null){
				tScore=ttRecord.getTrainingScore().toString();
			}
			String reason = "";
			if(ttRecord.getTrainingReason() != null){
				reason = ttRecord.getTrainingReason();
			}
			String certificate = "";
			if(ttRecord.getCertificate() != null){
				certificate = ttRecord.getCertificate();
			}
			sb.append("\"trainingStatus\":\"" + ttStatus + "\",");
			sb.append("\"score\":\"" + tScore + "\",");
			sb.append("\"reason\":\"" + reason + "\",");
			sb.append("\"certificate\":\"" + certificate + "\",");

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

				TeacherTrainingRecords ttr = this.iTeacherTrainingRecords.get(Integer.valueOf(ttrId));
				short checkStatus = ttr.getCheckStatus();
				if (ttr != null) {
					Integer olevel = us.getOrganizationLevel();
					Project p = ttr.getProject();
					ProjectType pt = p.getProjectType();
					ProjectLevel pl = pt.getProjectLevel();
					boolean flagEndAdu = false;
					if(pl.getId() == 3){//市级
						if(olevel == 2){//是最上级
							flagEndAdu = true;
						}
					} else if (pl.getId() == 4) {
						if(olevel == 3){//是最上级
							flagEndAdu = true;
						}
					} else if (pl.getId() == 5) {
						if(olevel == 4){//是最上级
							flagEndAdu = true;
						}
					}
					
					if(p.getEnrollType() == 2) {//自主报送
						if(checkStatus == -1){
							checkStatus = 6;
						}
					}
					
					if (method.equals(DictionyMap.TEACHER_TRAINING_RECORDS_ADU)) {

						// 如果已经到最高级别审核，直接就是最终
						if(flagEndAdu){
							// 管理员审核
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {

								status = "已审核";

								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
								this.iTeacherTrainingRecords.update(ttr);

								// 添加审核记录
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
						if(reason == null || "".equals(reason)){
							StringBuilder checkSB = new StringBuilder();
							checkSB.append("{");
							checkSB.append("\"Result\":\"OK\"");
							checkSB.append(",");
							checkSB.append("\"status\":\"" + status + "\"");
							checkSB.append(",");
							checkSB.append("\"Message\":\"" + "请认真填写审核不通过原因" + "\"");
							checkSB.append("}");
							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
							return;
						}
						// 审核不通过
						if(flagEndAdu) {
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

						if (ttr.getFinalStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {//未终审的才可以撤回
							// 可以删除
//							this.iTeacherTrainingRecords.delete(ttr);
							ttr.setFinalStatus((short)-1);//撤回
							this.iTeacherTrainingRecords.update(ttr);
							//添加撤回记录
							// 添加审核记录
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);//撤回原因
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 7);//撤回记录
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							status = "已撤回";
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
							status = "已终审，不能撤回";
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
//						if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
//							// 可以删除
//							this.iTeacherTrainingRecords.delete(ttr);
//							status = "已删除";
//							StringBuilder checkSB = new StringBuilder();
//							checkSB.append("{");
//							checkSB.append("\"Result\":\"OK\"");
//							checkSB.append(",");
//							checkSB.append("\"status\":\"" + status + "\"");
//							checkSB.append(",");
//							checkSB.append("\"Message\":\"" + "操作成功" + "\"");
//							checkSB.append("}");
//							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//							return;
//						} else {
//							status = "不能删除";
//							StringBuilder checkSB = new StringBuilder();
//							checkSB.append("{");
//							checkSB.append("\"Result\":\"ERROR\"");
//							checkSB.append(",");
//							checkSB.append("\"status\":\"" + status + "\"");
//							checkSB.append(",");
//							checkSB.append("\"Message\":\"" + "已审核，不能删除" + "\"");
//							checkSB.append("}");
//							Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//							return;
//						}

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
	 * 格式化地区字符串
	 * @param a
	 * @return
	 */
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
	 * 学员审核管理状态数据
	 */
	public void getAduPageStatusCount() {

		/**
		 * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");


		// 其他搜索条件
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;


		String teacherName = request.getParameter("teacherName");

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;
		

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
			}
		}

		Organization oz = this.iOrganization.get(us.getOrganization());
	
		/*
		 * -1 全部
		 * 2 已审核
		 * 1未审核
		 * -2通过
		 * 0未通过
		 */

		String status = "-1";//全部
//		int isadmin = 0;
		
		// 搜索出条数
		int records = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, Integer.valueOf(projectCycle), lityps);
		
		status = "2";//已通过
		int check = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, Integer.valueOf(projectCycle), lityps);
		
		status = "1";//未审核
		int noCheck = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, Integer.valueOf(projectCycle), lityps);
		
		status = "0";//未通过
		int noPass = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, Integer.valueOf(projectCycle), lityps);
		
		status = "3";//已撤回
		int revote = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, Integer.valueOf(projectCycle), lityps);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");//全部
		sb.append("\"check\":" + (check) + ",");//已审核
		sb.append("\"noCheck\":" + (noCheck) + ",");//未审核
		sb.append("\"revote\":" + (revote) + ",");//已撤回
		sb.append("\"noPass\":" + (noPass));//未通过
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
	}
	
	/**
	 * 学员审核管理状态数据
	 */
	public void getOAduPageStatusCount() {

		/**
		 * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");


		// 其他搜索条件
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;


		String teacherName = request.getParameter("teacherName");


		String searchKey = request.getParameter("searchKey");
		if (searchKey != null && searchKey.trim().length() > 0) {
			String[] tmpKey = searchKey.split("-");
			if (tmpKey.length >= 3) {
				String tmpProjectid = tmpKey[0];
				String tmpSubjectid = tmpKey[1];
				if (projectid.equals("0")) {
					projectid = tmpProjectid;
				}
				if (subjectid.equals("0")) {
					subjectid = tmpSubjectid;
				}
			}
		}
		
		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

		Organization oz = this.iOrganization.get(us.getOrganization());
	
		/*
		 * -1 全部
		 * 2 已审核
		 * 1未审核
		 * -2通过
		 * 0未通过
		 */

		String status = "-1";//全部
//		int isadmin = 0;
		
		// 搜索出条数
		int records = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, lityps,us.getOrganizationLevel(), Integer.valueOf(projectCycle));
		
		status = "2";//已通过
		int check = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, lityps,us.getOrganizationLevel(), Integer.valueOf(projectCycle));
		
		status = "1";//未审核
		int noCheck = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, lityps,us.getOrganizationLevel(), Integer.valueOf(projectCycle));
		
		status = "0";//未通过
		int noPass = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, lityps,us.getOrganizationLevel(), Integer.valueOf(projectCycle));
		
		status = "3";//已撤回
		int revote = this.iTeacherTrainingRecords.getLevelAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, Integer.valueOf(status), oz, lityps,us.getOrganizationLevel(), Integer.valueOf(projectCycle));
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");//全部
		sb.append("\"check\":" + (check) + ",");//已审核
		sb.append("\"noCheck\":" + (noCheck) + ",");//未审核
		sb.append("\"revote\":" + (revote) + ",");//已撤回
		sb.append("\"noPass\":" + (noPass));//未通过
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
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

//		String trainingUnit = request.getParameter("trainingUnit");
//		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;
		
		String name = request.getParameter("teacherName");
		Organization oz = this.iOrganization.get(us.getOrganization());
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pid", projectid);
		paramMap.put("sid", subjectid);
//		paramMap.put("tid", trainingUnit);
		paramMap.put("status", "2");
		paramMap.put("name", name);
//		int records = this.iTeacherTrainingRecords.getLevelAduTeacherCount(name, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, 2, oz, lityps);
		List li = this.iTeacherTrainingRecords.getLevelAduTeacher(name, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, -1, oz, Integer.valueOf(projectCycle), lityps, -1, -1);
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员培训记录表");
		XSSFRow row = s.createRow(0);
		String title[] = { "项目名称", "培训学科", "承训院校", "学员姓名", "所属地区", "所在学校", "身份证号", "年龄", "性别", "民族", "政治面貌", "教龄", "职务", "职称","手机","审核状态","培训状态","成绩","结业证编号","备注" };
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
			short tStatusCheck = ttRecord.getFinalStatus();
			row = s.createRow(t);
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			if(us.getOrganizationLevel() == 4){
				trainingName = oz.getName();
			}
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
			}else if(tStatus == 5){
				ttStatus="培训优秀";
			}else if(tStatus == 6){
				ttStatus="培训良好";
			}
			String ttStatusCheck = "";
			if (tStatusCheck == 0) {
				ttStatusCheck = "未通过";
			} else if(tStatusCheck == -1) {
				ttStatusCheck = "已撤销";
			} else {
				ttStatusCheck = (tStatusCheck == 1 ? "未审核" : "已审核");
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
			
			String tInfo[] = { projectName, subjectName, trainingName, tName, tArea, schoolName, tIdcard, tAge, tSex, tEthnic, tPolitics, tTeachAge, tDuty, tTitle, tMobile, ttStatusCheck, ttStatus,tScore,tcertificate,remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=score.xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		
	}
	
	/**
	 * 导出学员成绩
	 * @throws IOException 
	 */
	public void outputOScore() throws IOException{
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

//		String trainingUnit = request.getParameter("trainingUnit");
//		trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		
		String projectCycle = request.getParameter("projectCycle");
		projectCycle = projectCycle == null ? "0" : projectCycle;

		String name = request.getParameter("teacherName");
		Organization oz = this.iOrganization.get(us.getOrganization());
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("pid", projectid);
		paramMap.put("sid", subjectid);
//		paramMap.put("tid", trainingUnit);
		paramMap.put("status", "2");
		paramMap.put("name", name);
//		int records = this.iTeacherTrainingRecords.getLevelAduTeacherCount(name, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, 2, oz, lityps);
		List li = this.iTeacherTrainingRecords.getLevelAduTeacher(name, Integer.valueOf(projectid), Integer.valueOf(subjectid), 0, 0, -1, oz, lityps, -1, -1,us.getOrganizationLevel(), Integer.valueOf(projectCycle));
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员培训记录表");
		XSSFRow row = s.createRow(0);
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
			if(us.getOrganizationLevel() == 4){
				trainingName = oz.getName();
			}
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
			}else if(tStatus == 5){
				ttStatus="培训优秀";
			}else if(tStatus == 6){
				ttStatus="培训良好";
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
		response.setHeader("Content-disposition", "attachment;filename=score.xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		
	}

	
	/**
	 * 初始化上传培训记录页面
	 * @return
	 */
	public String uploadInit(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		this.organizationLevel = us.getOrganizationLevel() + "";
		return "upload";
	}
	
	/**
	 * 导入学员培训记录（先报后分）
	 *校验条件：
	 *承训院校是否存在并有效(校本级项目承训单位可空)
	 *项目申报记录是否存在并审核通过
	 *老师是否先报过名
	 */
	public void upload(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization taddOra = this.iOrganization.get(us.getOrganization());
		DecimalFormat df = new DecimalFormat("#");
		pageStatus = "OK";
		message = "文件上传成功";
		String rString = "";
		String Path = "";
		hmFiles = new HashMap<>();
		try {
			List<HashMap<String, Object>> infomationList = new ArrayList<HashMap<String, Object>>();
			initServlert();
			HttpSession session = request.getSession();
			hmFiles = (HashMap<EDocumentType, fileInfo>) session.getAttribute("uploadfile");
			if (hmFiles == null) {
				pageStatus = "ERROR";
				message = "未上传Excel";
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			if (hmFiles.size() > 0) {
				fileInfo fileInfo = new fileInfo();
				Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles
						.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<EDocumentType, fileInfo> entry = (Entry<EDocumentType, fileInfo>) iter.next();
					fileInfo = (fileInfo) entry.getValue();
//					String rootpath = this.getClass().getClassLoader().getResource("/").getPath()+ "../..";
//					Path = rootpath + fileInfo.getFilePath();
				}
				//根据系统获取实际地址
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
//				XSSFSheet s = wb.createSheet();
				XSSFSheet s = wb.getSheet("Sheet1");
				XSSFRow row;
				if(s.getLastRowNum()<1){
					pageStatus = "ERROR";
					message = "表内没有记录";
					rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
					Utlity.ResponseWrite(rString, "json", response);
					return;
				}
				this.session.setAttribute("maxIndex", s.getLastRowNum());
				this.session.setAttribute("percent",0);
				
				HashMap<String,Project> projectMap = new HashMap<String,Project>();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("organization", us.getOrganization());
				List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
				List<Project> projectList = new ArrayList<Project>();
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						projectList.add(p);
					}
				}
//				List<Project> projectList = this.iProjectService.findAll();
				for(Project ts : projectList){
					projectMap.put(ts.getName().trim(), ts);
				}
				
				HashMap<String,TrainingSubject> tsMap = new HashMap<String,TrainingSubject>();
				List<TrainingSubject> tsList = this.iTrainingSubjectService.findAll();
				for(TrainingSubject ts : tsList){
					tsMap.put(ts.getName().trim(), ts);
				}
				
				HashMap<String,TrainingCollege> tcMap = new HashMap<String,TrainingCollege>();
				List<TrainingCollege> tcList = this.iTrainingCollegeService.findAll();//预加载承训单位信息
				for(TrainingCollege tc : tcList){
					tcMap.put(tc.getName().trim(), tc);
				}
				
				for (int i = 1; i <= s.getLastRowNum(); i++) {// 解析Excel过程
					row = s.getRow(i);
					if(row!=null){
						/*
						 * 0-项目名称
						 * 1-学科名称
						 * 2-承训院校
						 * 3-学员姓名
						 * 4-学员身份证号
						 * 5-培训状态
						 * 6-不合格原因
						 * 7-成绩
						 * 8-结业证编号
						 * 9-备注
						 */
						HashMap<String, Object> infomation = new HashMap<String, Object>();
//						String projectId = "";
//						HSSFCell cell = row.getCell(0);
//						if (cell != null) {
//							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//								projectId = df.format(cell.getNumericCellValue());
//							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//								projectId = cell.getStringCellValue();
//							}
//						}
						String projectName = "";
						XSSFCell cell = row.getCell(0);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								projectName = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								projectName = cell.getStringCellValue();
							}
						}
//						String subjectId = "";
//						cell = row.getCell(2);
//						if (cell != null) {
//							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//								subjectId = df.format(cell.getNumericCellValue());
//							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//								subjectId = cell.getStringCellValue();
//							}
//						}
						String subjectName = "";
						cell = row.getCell(1);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								subjectName = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								subjectName = cell.getStringCellValue();
							}
						}
						String trainingCollege = "";
						cell = row.getCell(2);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								trainingCollege = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								trainingCollege = cell.getStringCellValue();
							}
						}
//						String teacherId = "";
//						cell = row.getCell(5);
//						if (cell != null) {
//							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//								teacherId = df.format(cell.getNumericCellValue());
//							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//								teacherId = cell.getStringCellValue();
//							}
//						}
						String teacherName = "";
						cell = row.getCell(3);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								teacherName = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								teacherName = cell.getStringCellValue();
							}
						}
						String idcard = "";
						cell = row.getCell(4);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								idcard = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								idcard = cell.getStringCellValue();
							}
						}
						String trainingStatus = "";
						cell = row.getCell(5);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								trainingStatus = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								trainingStatus = cell.getStringCellValue();
							}
						}
						String reason = "";
						cell = row.getCell(6);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								reason = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								reason = cell.getStringCellValue();
							}
						}
						String score = "";
						cell = row.getCell(7);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								score = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								score = cell.getStringCellValue();
							}
						}
						String certificate = "";
						cell = row.getCell(8);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								certificate = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								certificate = cell.getStringCellValue();
							}
						}
						String remark = "";
						cell = row.getCell(9);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								remark = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								remark = cell.getStringCellValue();
							}
						}
						if(!IdCardUtil.IDCardValidate(idcard).equals("")){//先校验身份证信息
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员身份证不合法";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * 校验承训单位信息是否合法
						 */
						if( trainingCollege==null || trainingCollege.equals("")){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写承训院校";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						if(us.getOrganizationLevel() < 4){
							if(tcMap.get(trainingCollege)==null){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员承训院校未在系统中找到";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							
							/*
							 * 校验申报记录是否存在
							 */
							int count = 0;
							Map<String,Object> params = new HashMap<String,Object>();
							params.put("project", projectMap.get(projectName).getId());
							params.put("trainingSubject", tsMap.get(subjectName).getId());
							params.put("trainingCollege", tcMap.get(trainingCollege).getId());
							params.put("status", 2);
							List<ProjectApply> lst = this.iProjectApplyService.getProjectApplyByParams(params, null, null);
							if(lst != null && !lst.isEmpty()){
								count = lst.size();
							}
							if(count == 0){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员分配的承训单位未找到对应项目申报记录";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
						}else{//校本级项目承训单位为学校本身（可以添加一个固定的承训单位代表校本级承训单位）
							if(tcMap.get("校本级项目承训单位")==null){
								pageStatus = "ERROR";
								message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员承训院校未在系统中找到";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
						}
						
						
						/*
						 * 校验项目信息是否合法
						 */
						if( projectName==null || projectName.equals("")){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写项目名称";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if(projectMap.get(projectName)==null){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员培训项目未在系统中找到";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * 校验学科信息是否合法
						 */
						
						if( subjectName==null || subjectName.equals("")){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写培训学科名称";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if(tsMap.get(subjectName)==null){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员培训学科未在系统中找到";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						

						
						/*
						 * 校验教师信息是否合法
						 */
//						if( teacherId==null || teacherId.equals("")){
//							pageStatus = "ERROR";
//							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写学员ID";
//							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
//							Utlity.ResponseWrite(rString, "json", response);
//							return;
//						}
						
						if( teacherName==null || teacherName.equals("")){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写学员姓名";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						HashMap<String,String> searchMap = new HashMap<String,String>();
						searchMap.put("idcard", idcard);
						List<Teacher> tList = this.iTeacherService.getTeacherListByParams(searchMap);
						if(tList.size()<1){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未在系统中录入";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						Teacher teacher = tList.get(0);
						if(!teacher.getName().trim().equals(teacherName)){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员身份证与姓名不相符";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if( trainingStatus==null || trainingStatus.equals("")){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写培训状态";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if ((trainingStatus == null) || (trainingStatus.equals(""))
								|| (trainingStatus.equals("培训合格"))
								|| (trainingStatus.equals("培训不合格"))) {
							trainingStatus = row.getCell(5).getStringCellValue()
									.equals("培训合格") ? "3" : "4";
						} else {
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员培训状态错误";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if( score==null || score.equals("")){
							pageStatus = "ERROR";
							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未填写培训成绩";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
//						/*
//						 * 校验先报后分学员记录是否存在
//						 */
//						HashMap<String, String> searchMaps = new HashMap<>();
//						searchMaps.put("project", projectId);
//						searchMaps.put("subject", subjectId);
//						searchMaps.put("teacher", teacher.getId()+"");
////						int countt = this.teacherTrainingAssignedService.getCountByParams(searchMaps);
//						List list = this.teacherTrainingAssignedService.getListByParams(searchMaps, null, null, null);
//						int countt = 0;
//						
//						if(list != null && !list.isEmpty()){
//							countt = list.size();
//							TeacherTrainingAssigned ttaa = (TeacherTrainingAssigned) ((Object[])list.get(0))[0];
//							if(ttaa != null){//标识已处理学员
//								ttaa.setStatus((short)0);
//								this.teacherTrainingAssignedService.update(ttaa);
//							}
//						}
//						if(countt == 0){//不存在
//							pageStatus = "ERROR";
//							message = "第" + row.getRowNum() +"行,身份证号为" + idcard + "的学员未自主报名该项目";
//							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
//							Utlity.ResponseWrite(rString, "json", response);
//							return;
//						}
						
						infomation.put("teacher", teacher);
						infomation.put("project", projectMap.get(projectName));
						infomation.put("trainingSubject", tsMap.get(subjectName));
						infomation.put("trainingCollege", tcMap.get(trainingCollege));
						infomation.put("trainingStatus", trainingStatus);
						infomation.put("score", score);
						infomation.put("reason", reason);
						infomation.put("certificate", certificate);
						infomation.put("remark", remark);
						infomationList.add(infomation);
						int percent = (int) Math.ceil((i * 100) /  (s.getLastRowNum() * 2));
						session.setAttribute("percent", percent);
						
					}
				}
				this.iTeacherTrainingRecords.addBatchTeacherTrainingRecords(us, taddOra,s.getLastRowNum(), infomationList);
				session.removeAttribute("maxIndex");
				session.removeAttribute("percent");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public void getPercent() {
		initServlert();
		pageStatus = "OK";
		message = "";
		String rString = "";
		if(session.getAttribute("percent")!=null && session.getAttribute("maxIndex")!=null){
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
		}else{
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
					+ "\",\"Percent\":\"" + 0 + "\",\"MaxIndex\":\""
					+ 0 + "\"}";
		}
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	
	public void getAssignTaskProjects(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		String year = this.request.getParameter("year");
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("0")){
			map.put("year", year);
		}
//		if(projectType != null && !projectType.endsWith("0")){
//			ProjectType pt = this.iProjectTypeService.get(Integer.valueOf(projectType));
//			map.put("projectType.scode", pt.getScode());
//		}
//		List<Project> lip = this.iProjectService.getProjectByParams(map,null,lityps);
//		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		List<Project> lip = new ArrayList<Project>();
//		List list = this.iProjectService.getProjectListByParams(map,
//				null, lityps, -1, -1);
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				Object[] obj = (Object[]) list.get(i);
//				Project p = (Project) obj[0];
//				lip.add(p);
//			}
//		}
		
		map.put("organization", us.getOrganization());
		map.put("level", us.getOrganizationLevel());
		List list2level = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list2level != null && list2level.size() > 0) {
			for (int i = 0; i < list2level.size(); i++) {
				Object[] obj = (Object[]) list2level.get(i);
				Project p = (Project) obj[0];
				lip.add(p);
			}
		}
		Organization oo = this.iOrganization.get(us.getOrganization());
		if(us.getOrganizationLevel()>2){
			lip = new ArrayList<Project>();
			map.remove("level");
			if(us.getOrganizationLevel() == 3){
				map.put("organization", oo.getOrganization().getId());//市级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						lip.add(p);
					}
				}
			}else{
				map.put("organization", oo.getOrganization().getId());//县级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						lip.add(p);
					}
				}
				map.put("organization", oo.getOrganization().getOrganization().getId());//市级
				List list2 = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list2 != null && list2.size() > 0){
					for(int i = 0; i < list2.size(); i++){
						Object[] obj = (Object[])list2.get(i);
						Project p = (Project)obj[0];
						lip.add(p);
					}
				}
			}
		}
		
		
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
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
//		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		this.searchProject = new ArrayList<Project>();
//		List list = this.iProjectService.getProjectListByParams(map,
//				null, lityps, -1, -1);
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				Object[] obj = (Object[]) list.get(i);
//				Project p = (Project) obj[0];
//				this.searchProject.add(p);
//			}
//		}
		
		map.put("organization", us.getOrganization());
		map.put("level", us.getOrganizationLevel());
		this.searchProject = new ArrayList<Project>();
		List list2level = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list2level != null && list2level.size() > 0) {
			for (int i = 0; i < list2level.size(); i++) {
				Object[] obj = (Object[]) list2level.get(i);
				Project p = (Project) obj[0];
				this.searchProject.add(p);
			}
		}
		Organization oo = this.iOrganization.get(us.getOrganization());
		if(us.getOrganizationLevel()>2){
			this.searchProject = new ArrayList<Project>();
			map.remove("level");
			if(us.getOrganizationLevel() == 3){
				map.put("organization", oo.getOrganization().getId());//市级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}else{
				map.put("organization", oo.getOrganization().getId());//县级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
				map.put("organization", oo.getOrganization().getOrganization().getId());//市级
				List list2 = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list2 != null && list2.size() > 0){
					for(int i = 0; i < list2.size(); i++){
						Object[] obj = (Object[])list2.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}
		}
		
		return "assPage";
	}
	
	/**
	 * 初始化学员报分配任务送管理
	 * 
	 * @return
	 */
	public String initOReortAsstPage() {
		initServlert();
		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
//		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		this.searchProject = new ArrayList<Project>();
//		List list = this.iProjectService.getProjectListByParams(map,
//				null, lityps, -1, -1);
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				Object[] obj = (Object[]) list.get(i);
//				Project p = (Project) obj[0];
//				this.searchProject.add(p);
//			}
//		}
		
		map.put("organization", us.getOrganization());
		map.put("level", us.getOrganizationLevel());
		this.searchProject = new ArrayList<Project>();
		List list2level = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list2level != null && list2level.size() > 0) {
			for (int i = 0; i < list2level.size(); i++) {
				Object[] obj = (Object[]) list2level.get(i);
				Project p = (Project) obj[0];
				this.searchProject.add(p);
			}
		}
		Organization oo = this.iOrganization.get(us.getOrganization());
		if(us.getOrganizationLevel()>2){
			this.searchProject = new ArrayList<Project>();
			map.remove("level");
			if(us.getOrganizationLevel() == 3){
				map.put("organization", oo.getOrganization().getId());//市级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}else{
				map.put("organization", oo.getOrganization().getId());//县级
				List list = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
				map.put("organization", oo.getOrganization().getOrganization().getId());//市级
				List list2 = this.iProjectService.getProjectListByParams(map, null, lityps, -1, -1);
				if(list2 != null && list2.size() > 0){
					for(int i = 0; i < list2.size(); i++){
						Object[] obj = (Object[])list2.get(i);
						Project p = (Project)obj[0];
						this.searchProject.add(p);
					}
				}
			}
		}
		
		return "oassPage";
	}
	
	/**
	 * 搜索 任务 分配 接口
	 */
	public void searchReportAssPage() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		int rprojectId = Integer.valueOf(request.getParameter("projectName"));
		int rsubjectId = Integer.valueOf(request.getParameter("subjectName"));
		String year = request.getParameter("year");
		// 搜索参数Page
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		/**
		 * 1.获取当前用户下的所有子任务 2.可以根据项目 学科 承训单位进行筛选 返回JSON 包括 page信息
		 */
		String leveltype = request.getParameter("leveltype") == null ? "" : request.getParameter("leveltype");
		String projectlevel = "1,2";
//		if("3".equals(leveltype)){
//			projectlevel = "3";
//		} else if ("4".equals(leveltype)){
//			projectlevel = "4";
//		} else if ("5".equals(leveltype)){
//			projectlevel = "5";
//		} else {
//			projectlevel = "0";
//		}
//		String projectlevel = "1,2";
		int olevel = us.getOrganizationLevel();
		String orgNum = "";
		if(olevel == 2){
			projectlevel = "3";
			Organization oa = this.iOrganization.get(us.getOrganization());
			orgNum = oa.getId()+"";
		} else if (olevel == 3) {
			Organization oa = this.iOrganization.get(us.getOrganization());
			if("1".equals(leveltype)){//本级
				projectlevel = "4";
				orgNum = oa.getId()+"";
			} else {
				projectlevel = "3";//其他
				orgNum = oa.getOrganization().getId()+"";
			}
			
		} else if (olevel == 4) {
			Organization oa = this.iOrganization.get(us.getOrganization());
			projectlevel = "3,4,5";
			if("1".equals(leveltype)){//本级
				projectlevel = "5";
				orgNum = oa.getId()+"";
			} else {
				projectlevel = "3,4";//其他
				orgNum = oa.getOrganization().getId()+","+oa.getOrganization().getOrganization().getId();
			}
		}
		// 以后的搜索字段值
		// 查看当前角色 当前任务已经分配的任务
		int records = this.getiAssignTeacherTaskService().getAssignTeacherTaskCountLevel(year ,rprojectId,rsubjectId, us.getOrganization(), us,projectlevel,orgNum);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;

		List<Map<String, Object>> results = this.getiAssignTeacherTaskService().getAssignTeacherTaskResultsLevel(year ,rprojectId,rsubjectId, us, offset, DictionyMap.maxPageSize,projectlevel,orgNum);

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

			sb.append("\"projectCycle\":" + att.get("projectCycle")); 
			sb.append(",");

			sb.append("\"leveltype\":" + leveltype); 
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
			sb.append(",");
			
			//training_starttime:开班时间
			if(att.get("training_starttime") != null){
				sb.append("\"training_starttime\":\"" + Utlity.timeSpanToDateString(att.get("training_starttime")) + "\"");
			}else{
				sb.append("\"training_starttime\":\"待定\"");
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
	
	
	// 初始化学员报送弹出页面
	public String initReportPage() throws IOException {

		initServlert();
		return "report";
	}
	
	// 初始化学员报送弹出页面
	public String initOtherReportPage() throws IOException {

		initServlert();
		return "oreport";
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
		paramsMap.put("school", request.getParameter("organization"));
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
			String status = "";
			if(t.getStatus()==1){
				status = "在职";
			}else if(t.getStatus()==2){
				status = "离职";
			}else if(t.getStatus()==3){
				status = "转出";
			}else if(t.getStatus()==4){
				status = "退休";
			}else{
				status = "死亡";
			}
			tb.append("\"status\":\"" + status + "\",");
			String authorized = t.getAuthorized() == 1 ? "在编" : "非编";
			tb.append("\"authorized\":\"" + authorized + "\",");
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
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(
			ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public List<Project> getSearchProject() {
		return searchProject;
	}

	public void setSearchProject(List<Project> searchProject) {
		this.searchProject = searchProject;
	}

	public IProjectSubjectRangeService getiProjectSubjectRangeService() {
		return iProjectSubjectRangeService;
	}

	public void setiProjectSubjectRangeService(
			IProjectSubjectRangeService iProjectSubjectRangeService) {
		this.iProjectSubjectRangeService = iProjectSubjectRangeService;
	}

	
	public ITeacherTrainingRecordsService getiTeacherTrainingRecords() {
		return iTeacherTrainingRecords;
	}
	

	public void setiTeacherTrainingRecords(
			ITeacherTrainingRecordsService iTeacherTrainingRecords) {
		this.iTeacherTrainingRecords = iTeacherTrainingRecords;
	}
	

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}
	

	public void setiTrainingCollegeService(
			ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	
	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	
	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	
	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}
	

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	
	public ITeachingLanguageService getiTeachingLanguageService() {
		return iTeachingLanguageService;
	}
	

	public void setiTeachingLanguageService(
			ITeachingLanguageService iTeachingLanguageService) {
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
	

	public void setiTeachingSubjectService(
			ITeachingSubjectService iTeachingSubjectService) {
		this.iTeachingSubjectService = iTeachingSubjectService;
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
	

	public IChineseLanguageLevelService getiChineseLanguageLevelService() {
		return iChineseLanguageLevelService;
	}
	

	public void setiChineseLanguageLevelService(
			IChineseLanguageLevelService iChineseLanguageLevelService) {
		this.iChineseLanguageLevelService = iChineseLanguageLevelService;
	}
	

	public String getOrganizationLevel() {
		return organizationLevel;
	}
	

	public void setOrganizationLevel(String organizationLevel) {
		this.organizationLevel = organizationLevel;
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
	

	public List<ChineseLanguageLevel> getLstChineseLanguageLevels() {
		return lstChineseLanguageLevels;
	}
	

	public void setLstChineseLanguageLevels(
			List<ChineseLanguageLevel> lstChineseLanguageLevels) {
		this.lstChineseLanguageLevels = lstChineseLanguageLevels;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	
	public IAssignTeacherCheckService getiAssignTeacherCheckService() {
		return iAssignTeacherCheckService;
	}
	

	public void setiAssignTeacherCheckService(
			IAssignTeacherCheckService iAssignTeacherCheckService) {
		this.iAssignTeacherCheckService = iAssignTeacherCheckService;
	}

	
	public IAssignTeacherTaskService getiAssignTeacherTaskService() {
		return iAssignTeacherTaskService;
	}
	

	public void setiAssignTeacherTaskService(
			IAssignTeacherTaskService iAssignTeacherTaskService) {
		this.iAssignTeacherTaskService = iAssignTeacherTaskService;
	}

	
	
	
}
