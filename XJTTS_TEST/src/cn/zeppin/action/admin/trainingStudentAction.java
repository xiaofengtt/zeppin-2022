/**
 * 
 */
package cn.zeppin.action.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingAdminAuthority;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingStudentSub;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IAssignTeacherCheckService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author sj
 * 
 */
public class trainingStudentAction extends ActionSupport {
	/**
  	 * 
  	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IOrganizationService iOrganization;
	private IProjectApplyService iProjectApplyService;
	private ITrainingAdminService iTrainingAdminService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IProjectService iProjectService;
	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作
	private IAssignTeacherCheckService iAssignTeacherCheckService;

	private List<TrainingStudentSub> lstTrainingStudentSubs = new ArrayList<>();
	private List<TeacherTrainingRecords> lstTeacherTrainingRecords = new ArrayList<>();
	private List<TrainingSubject> lsttTrainingSubjects = new ArrayList<TrainingSubject>();// 教学科
	private List<Project> lstProjects = new ArrayList<>();
	private List<String> lstStatus = new ArrayList<>();
	private ProjectApply projectApply;
	private TeacherTrainingRecords teacherTrainingRecords;
	private int classHour;
	private int onlineHour;
	private int maxScore;
	private String certificate;
	private int maxHour;
	int maxSize = 1024 * 1024 * 1024;
	private HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
	private String pageStatus;// 页面状态
	private String message;// 错误信息
	private List<String> searchYear;

	private List<Project> searchReportTask = new ArrayList<Project>();
	
	private IAreaService iAreaService;// 地区操作
	private ITeacherService iTeacherService;// 教师操作
	
	static Logger logger = LogManager.getLogger(TrainingAdminOptAction.class);
	private int showAddButton;
//	private List lstSubject;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String initPage() {
		initServlert();
		
		String year = request.getParameter("year");
		UserSession user = (UserSession) session.getAttribute("usersession");
		
		this.searchYear = this.iProjectService.getProjectYearList();
		lsttTrainingSubjects = iTrainingSubjectService.findAll();
		List<Integer> projectIds = iProjectApplyService
				.getDistinctProjectByTrainingCollege(iTrainingAdminService
						.get(user.getId()).getTrainingCollege().getId());
		lstProjects.clear();
		for (int s : projectIds) {
			Project tProject = new Project();
			tProject = iProjectService.get(s);
			if (year != null && !year.equals("") && !year.endsWith("0")){
				if(tProject.getYear().endsWith(year)){
					lstProjects.add(tProject);
				}
			}else{
				lstProjects.add(tProject);
			}
		}
		lstStatus.add("学员异动");
		lstStatus.add("未报到");
		lstStatus.add("已报到");
		lstStatus.add("培训合格");
		lstStatus.add("培训不合格");
		showAddButton = 0;
		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue() || 
				user.getRole() == ROLEENUM.ADMIN.getValue() && user.getOrganizationLevel() == 1 ||   
				(user.getRole() == ROLEENUM.TRAINING.getValue() && user.getCreateuser() == 1))
		{
			showAddButton = 1;
		}
		return "init";
	}
	
	public String authority(){
		return "authority";
	}
	
	public void getTrainingList() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
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
		// String sort = request.getParameter("jtSorting");
		String sort = "id desc";
		// TODO 加上权限搜索

		// 排序 参数
		// if (sort != null && !sort.equals(""))
		// {
		// String[] sortarray = sort.split(" ");
		// String sortname = sortarray[0];
		// String sorttype = sortarray[1];
		// sort = sortname + " " + sorttype;
		// }

		try {
			List<ProjectApply> lstProjectApplies = new ArrayList<>();
			TrainingAdmin ta = this.iTrainingAdminService.get(user.getId());
			//获取用户权限
			Set<TrainingAdminAuthority> authority = ta.getTrainingAdminAuthoritys();
			
			if(ta.getRestrictRight() && authority != null && authority.size() > 0){//有权限
				for(TrainingAdminAuthority taa : authority){
					Project project = taa.getProject();
					
					Short subjectId = -1;
					if(taa.getTrainingSubject() != null){
						subjectId = taa.getTrainingSubject().getId();
						
					}
					List<ProjectApply> lstProjectAppliess = iProjectApplyService
							.getlistForRunningPojectApply(
									iTrainingAdminService.get(user.getId()).getTrainingCollege().getId(),project.getId(), subjectId,(int) Math.ceil(start / limit), limit, sort);
					lstProjectApplies.addAll(lstProjectAppliess);
				}
				
				
			}else{
				if (request.getParameterMap().containsKey("projectName")
						&& request.getParameterMap().containsKey("subjectName")) {
					int projectId = Integer.parseInt(request
							.getParameter("projectName"));
					int subjectId = Integer.parseInt(request
							.getParameter("subjectName"));
					lstProjectApplies = iProjectApplyService
							.getlistForRunningPojectApply(
									iTrainingAdminService.get(user.getId())
											.getTrainingCollege().getId(),
									projectId, subjectId,
									(int) Math.ceil(start / limit), limit, sort);
				} else {
					lstProjectApplies = iProjectApplyService
							.getlistForRunningPojectApply(
									iTrainingAdminService.get(user.getId())
											.getTrainingCollege().getId(),
									(int) Math.ceil(start / limit), limit, sort);
	
				}
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			if (lstProjectApplies.size() > 0) {
				int records = 0;
				if (request.getParameterMap().containsKey("projectName")
						&& request.getParameterMap().containsKey("subjectName")) {
					int projectId = Integer.parseInt(request
							.getParameter("projectName"));
					int subjectId = Integer.parseInt(request
							.getParameter("subjectName"));
					records = iProjectApplyService.getCountByTrainingCollege(
							iTrainingAdminService.get(user.getId())
									.getTrainingCollege().getId(), projectId,
							subjectId);
				}

				else {
					records = iProjectApplyService
							.getCountByTrainingCollege(iTrainingAdminService
									.get(user.getId()).getTrainingCollege()
									.getId());
				}
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");
				sb.append("\"Records\":[");
				for (ProjectApply projectApply : lstProjectApplies) {
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + projectApply.getId());
					sbstr.append(",");
					sbstr.append("\"year\":\""
							+ cn.zeppin.utility.dataTimeConvertUtility.timespanToString(
									projectApply.getTrainingStarttime(),
									"yyyy-MM-dd") + "\"");
					sbstr.append(",");
					sbstr.append("\"project\":\""
							+ projectApply.getProject().getName() + "\"");
					sbstr.append(",");
					sbstr.append("\"subject\":\""
							+ projectApply.getTrainingSubject().getName()
							+ "\"");
					sbstr.append(",");
					// 分配总数
					sbstr.append("\"assignNum\":\""
							+ projectApply.getApproveNumber() + "\"");
					sbstr.append(",");
					// 报送总数
					int registNum = 0;
					registNum = iTeacherTrainingRecordsService
							.getAduTeacherCount(projectApply.getProject()
									.getId(), Integer.parseInt(projectApply
									.getTrainingSubject().getId().toString()),
									projectApply.getTrainingCollege().getId(),
									2, 0);
					sbstr.append("\"registerNum\":\"" + registNum + "\"");
					sbstr.append(",");
					// 异动总数
					int abnormalNum = 0;
					abnormalNum = iTeacherTrainingRecordsService
							.getAduTeacherCount(projectApply.getProject()
									.getId(), Integer.parseInt(projectApply
									.getTrainingSubject().getId().toString()),
									projectApply.getTrainingCollege().getId(),
									0, 0);
					sbstr.append("\"abnormalNum\":\"" + abnormalNum + "\"");
					sbstr.append(",");
					Timestamp startTimestamp = projectApply
							.getTrainingStarttime();
					Timestamp endtTimestamp = projectApply.getTrainingEndtime();
					Timestamp nowTimestamp = cn.zeppin.utility.dataTimeConvertUtility
							.getCurrentTime("yyyy-MM-dd hh:mm:ss");
					String status = "未开课";
					if (startTimestamp.before(nowTimestamp)
							&& endtTimestamp.after(nowTimestamp)) {
						status = "进行中";
					} else if (endtTimestamp.before(nowTimestamp)) {
						status = "已结束";

					}
					sbstr.append("\"status\":\"" + status + "\"");
					sbstr.append("}");
					sbstr.append(",");
					sb.append(sbstr.toString());

				}

				sb.delete(sb.length() - 1, sb.length());

			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
				sb.append("\"Records\":[");

			}

			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	public void outputAduPage() throws IOException {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		TrainingAdmin ta = this.iTrainingAdminService.get(us.getId());
		
		int projectId = 0, subjectId = 0, rstatus = -1;
		if (request.getParameterMap().containsKey("projectName")) {
			projectId = Integer.parseInt(request.getParameter("projectName"));
		}
		if (request.getParameterMap().containsKey("subjectName")) {
			subjectId = Integer.parseInt(request.getParameter("subjectName"));
		}
		if (request.getParameterMap().containsKey("Status")) {
			if (request.getParameter("Status").equals("学员异动")) {
				rstatus = 0;
			} else if (request.getParameter("Status").equals("未报到")) {
				rstatus = 1;
			} else if (request.getParameter("Status").equals("已报到")) {
				rstatus = 2;
			} else if (request.getParameter("Status").equals("培训合格")) {
				rstatus = 3;
			} else if (request.getParameter("Status").equals("培训不合格")) {
				rstatus = 4;
			}
		}
		int trainingId = iTrainingAdminService.get(us.getId())
				.getTrainingCollege().getId();
		int offset = 0, status = 2;
		
		int number = 0;
		List li = new ArrayList();
		//获取用户权限
		Set<TrainingAdminAuthority> authority = ta.getTrainingAdminAuthoritys();
		
		if(ta.getRestrictRight() && authority != null && authority.size() > 0){//有权限){

			for(TrainingAdminAuthority taa : authority){
				Project project = taa.getProject();
				
				Short sId = 0;
				if(taa.getTrainingSubject() != null){
					sId = taa.getTrainingSubject().getId();
				}
				int num = iTeacherTrainingRecordsService.getAduTeacherCount(
						project.getId(), Integer.parseInt(sId.toString()), trainingId, rstatus, 0);
				List lis = iTeacherTrainingRecordsService.getAduTeacher(project.getId(),
						Integer.parseInt(sId.toString()), trainingId, status, rstatus, offset, number);
				number += num;
				if(lis != null && lis.size() > 0){
					li.addAll(lis);
				}
				
			}
			
		}else{
			number = iTeacherTrainingRecordsService.getAduTeacherCount(
					projectId, subjectId, trainingId, rstatus, 0);
			li = iTeacherTrainingRecordsService.getAduTeacher(projectId,
					subjectId, trainingId, status, rstatus, offset, number);
		}
		
//		int number = iTeacherTrainingRecordsService.getAduTeacherCount(
//				projectId, subjectId, trainingId, rstatus, 0);
//		List li = iTeacherTrainingRecordsService.getAduTeacher(projectId,
//				subjectId, trainingId, status, rstatus, offset, number);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员名单");
		HSSFRow row = s.createRow(0);
		String title[] = { "项目名称", "培训学科", "承训院校", "学员姓名", "学员编号", "所属地区", "所在学校",
				"身份证号", "年龄", "性别", "民族", "政治面貌", "学历", "教龄", "职务", "职称",
				"汉语水平", "主要教学学科", "主要教学学段", "主要教学语言", "联系电话", "邮箱", "报到状态",
				"班级", "备注" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];
			t++;
			String tStatus = "未报到";
			if (ttRecord.getTrainingStatus() == 0) {
				tStatus = "异动";
			} else if (ttRecord.getTrainingStatus() == 2) {
				tStatus = "已报到";
			} else if (ttRecord.getTrainingStatus() == 3) {
				tStatus = "培训合格（已结业）";
			} else if (ttRecord.getTrainingStatus() == 4) {
				tStatus = "培训不合格";
			}
			row = s.createRow(t);
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			String tIdcard = th.getIdcard();
			String tName = th.getName();
			String tClass = ttRecord.getClasses() + "";
			tClass = (tClass.equals("null") ? "" : tClass);
			String tSex = (th.getSex() == 1 ? "男" : "女");
			String tAge = String.valueOf(Utlity.getAge(th.getBirthday()));
			String tEthnic = th.getEthnic().getName();
			String tPolitics = th.getPolitics().getName();
			String schoolName = th.getOrganization().getName();
			String tTeachAge = String
					.valueOf(Utlity.getAge(th.getTeachingAge()));
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
			String hqlString = "from TeachingLanguage where teacher="
					+ th.getId() + " and isprime=true";
			String tMainLanguage = "";
			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService
					.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				tMainLanguage = teachingLanguage.getLanguage().getName();
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + th.getId()
					+ " and isprime=1";
			String tMainGrade = "";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
					.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				tMainGrade = teachingGrade.getGrade().getName();
			}

			// 获取主要教学学科
			hqlString = "from TeachingSubject where teacher=" + th.getId()
					+ " and isprime=1";
			String tMainSubject = "";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
					.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				tMainSubject = teachingSubject.getSubject().getName();
			}

			String tChineseLanguageLevel = th.getChineseLanguageLevel()
					.getName();
			String tEductionBackground = th.getEductionBackground().getName();
			
			//添加备注 备注信息为教师替换信息
			String remarks = "";
			if(ttRecord.getReplaceStatus() == 1){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="由教师 "+tt.getName()+" 替换";
			}else if(ttRecord.getReplaceStatus() == 2){
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks+="替换了教师  "+tt.getName();
			}
			
			String teacherId = th.getId().toString();
			
			String tInfo[] = { projectName, subjectName, trainingName, tName, teacherId,
					tArea, schoolName, tIdcard, tAge, tSex, tEthnic, tPolitics,
					tEductionBackground, tTeachAge, tDuty, tTitle,
					tChineseLanguageLevel, tMainSubject, tMainGrade,
					tMainLanguage, tMobile, tEmail, tStatus, tClass, remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=studentlist.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}

	public String registInit() {
		projectApply = null;
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			projectApply = iProjectApplyService.get(Integer.parseInt(request
					.getParameter("id")));
		}

		return "registInit";
	}

	public void getRegistList() {
		projectApply = null;
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			projectApply = iProjectApplyService.get(Integer.parseInt(request
					.getParameter("id")));
		}
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
			sort = sortname + " " + sorttype;
		} else {
			sort = "id desc";
		}
		try {
			lstTeacherTrainingRecords = iTeacherTrainingRecordsService
					.getListForPage(projectApply.getProject().getId(),
							projectApply.getTrainingSubject().getId(),
							projectApply.getTrainingCollege().getId(), 0,
							(int) Math.ceil(start / limit), limit, sort);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			if (lstTeacherTrainingRecords.size() > 0) {
				int records = iTeacherTrainingRecordsService.getListCount(
						projectApply.getProject().getId(), projectApply
								.getTrainingSubject().getId(), projectApply
								.getTrainingCollege().getId(), 0);
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");
				sb.append("\"Records\":[");
				for (TeacherTrainingRecords tRecords : lstTeacherTrainingRecords) {
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + tRecords.getId());
					sbstr.append(",");
					sbstr.append("\"name\":\""
							+ tRecords.getTeacher().getName() + "\"");
					sbstr.append(",");
					sbstr.append("\"idCard\":\""
							+ tRecords.getTeacher().getIdcard() + "\"");
					sbstr.append(",");
					sbstr.append("\"phone\":\""
							+ tRecords.getTeacher().getMobile() + "\"");
					sbstr.append(",");
					String sexString = tRecords.getTeacher().getSex() == 1 ? "男"
							: "女";
					sbstr.append("\"sex\":\"" + sexString + "\"");
					sbstr.append(",");
					sbstr.append("\"age\":\""
							+ IdCardUtil.getageByIdcard(tRecords.getTeacher()
									.getIdcard()) + "\"");
					sbstr.append(",");
					sbstr.append("\"jobTitle\":\""
							+ tRecords.getTeacher().getJobDuty().getName()
							+ "\"");
					sbstr.append(",");
					sbstr.append("\"organazation\":\""
							+ tRecords.getTeacher().getOrganization().getName()
							+ "\"");
					sbstr.append(",");
					String registTime = "";
					String status = "未报到";
					if (tRecords.getTrainingStatus() == 0) {
						status = "异动";
					} else if (tRecords.getTrainingStatus() == 2) {
						status = "已报到";
						registTime = dataTimeConvertUtility.timespanToString(
								tRecords.getRegisttime(), "yyyy-MM-dd");
					} else if (tRecords.getTrainingStatus() == 3) {
						status = "培训合格（已结业）";
						registTime = dataTimeConvertUtility.timespanToString(
								tRecords.getRegisttime(), "yyyy-MM-dd");
					} else if (tRecords.getTrainingStatus() == 4) {
						status = "培训不合格";
						registTime = dataTimeConvertUtility.timespanToString(
								tRecords.getRegisttime(), "yyyy-MM-dd");
					}
					sbstr.append("\"REGISTTIME\":\"" + registTime + "\"");
					sbstr.append(",");
					sbstr.append("\"TRAINING_STATUS\":\"" + status + "\"");
					sbstr.append(",");
					String classes = "";
					if (tRecords.getClasses() != null) {
						classes = tRecords.getClasses().toString();
					}
					sbstr.append("\"classes\":\"" + classes + "\"");
					sbstr.append("}");
					sbstr.append(",");
					sb.append(sbstr.toString());

				}

				sb.delete(sb.length() - 1, sb.length());

			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
				sb.append("\"Records\":[");

			}

			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void editClasses() {
		initServlert();
		String classes = request.getParameter("classes");
		String ids = request.getParameter("ids");
		this.iTeacherTrainingRecordsService.updateClasses(classes, ids);
		Utlity.ResponseWrite("{\"Result\":\"OK\"}", "application/json",
				response);
	}

	public void changeStudentStatus() {
		initServlert();
		String result = "OK";
		String ids = "";
		Short status = Short.parseShort(request.getParameter("status"));
		if (request.getParameterMap().containsKey("id")
				&& request.getParameterMap().containsKey("status")) {
			if (status != -1) {
				Integer id = Integer.parseInt(request.getParameter("id"));
				TeacherTrainingRecords trr = new TeacherTrainingRecords();
				trr = iTeacherTrainingRecordsService.get(id);

				//自主报名的项目学科 集中式培训形式下 未确认的学员不能修改状态
				if(trr.getProject().getEnrollType() == 2 && trr.getProject().getTraintype() == 1 && trr.getIsConfirm() <0 ){
					Utlity.sendResponse("ERROR", "当前培训项目学科下，未确认的学员不能修改培训状态！", response);
					return;
				}
				
				if (status == 0
						&& (trr.getTrainingStatus() == 3 || trr
								.getTrainingStatus() == 4)) {
					Utlity.sendResponse("ERROR", "已经结业的学员不能将状态修改为异动！", response);
					return;
				} else if (status == 1
						&& (trr.getTrainingStatus() == 3 || trr
								.getTrainingStatus() == 4)) {
					Utlity.sendResponse("ERROR", "已经结业的学员不能将状态修改为未报到！",
							response);
					return;
				} else if (status == 2
						&& (trr.getTrainingStatus() == 3 || trr
								.getTrainingStatus() == 4)) {
					Utlity.sendResponse("ERROR", "已经结业的学员不能将状态修改为已报到！",
							response);
					return;
				} else if (status == 3
						&& (trr.getTrainingStatus() == 0 || trr
								.getTrainingStatus() == 1)) {
					Utlity.sendResponse("ERROR", "未报到的学员不能将状态修改为结业成绩合格！",
							response);
					return;
				} else if (status == 4
						&& (trr.getTrainingStatus() == 0 || trr
								.getTrainingStatus() == 1)) {
					Utlity.sendResponse("ERROR", "未报到的学员不能将状态修改为结业成绩不合格！",
							response);
					return;
				} else if (status == 2
						&& (trr.getTrainingStatus() == 0 || trr
								.getTrainingStatus() == 1)) {
					trr.setTrainingStatus(status);
					trr.setRegisttime(dataTimeConvertUtility
							.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
					iTeacherTrainingRecordsService.update(trr);
				} else {
					trr.setTrainingStatus(status);
					iTeacherTrainingRecordsService.update(trr);
				}
			}
		} else if (request.getParameterMap().containsKey("ids")
				&& request.getParameterMap().containsKey("status")) {
			if (status != -1) {
				ids = request.getParameter("ids");
				if (!ids.equals("")) {
					String[] ss = ids.split(",");
					for (String string : ss) {
						TeacherTrainingRecords ttrRecords = new TeacherTrainingRecords();
						ttrRecords = iTeacherTrainingRecordsService.get(Integer
								.parseInt(string));
						// 状态是已报到学员，则可以批量结业合格
						if (ttrRecords.getTrainingStatus() == 2 && status == 3) {
							ttrRecords.setTrainingStatus(status);
							iTeacherTrainingRecordsService.update(ttrRecords);
						}
						// 状态是未报到学员，可以批量报到
						else if (ttrRecords.getTrainingStatus() == 1
								&& status == 2) {
							ttrRecords.setTrainingStatus(status);
							ttrRecords.setRegisttime(dataTimeConvertUtility
									.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
							iTeacherTrainingRecordsService.update(ttrRecords);
						}
						// 除了以上两种情况不做操作
					}
				}
			} else {
				result = "ERROR";
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"" + result + "\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	public String inputInit() {
		return "inputInit";
	}

	public void inputScore() {
		initServlert();
		DecimalFormat df = new DecimalFormat("#");
		pageStatus = "OK";
		message = "文件上传成功";
		String rString = "";
		String Path = "";
		hmFiles = new HashMap<>();
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
				HSSFSheet s = wb.getSheet("学员名单");
				HSSFRow row;
				p = new HashMap<Integer, HashMap<String, String>>();
				String rtrainingName = iTrainingCollegeService.get(
						us.getOrganization()).getName();
				int t = s.getLastRowNum();
				for (int i = 1; i <= s.getLastRowNum(); i++) {// 解析Excel过程
					row = s.getRow(i);

					if (rtrainingName.equals(row.getCell(2)
							.getStringCellValue())) {
						HashMap<String, String> teacher = new HashMap<String, String>();
						String projectName = row.getCell(0)
								.getStringCellValue();
						teacher.put("projectName", projectName);
						String subjectName = row.getCell(1)
								.getStringCellValue();
						teacher.put("subjectName", subjectName);
						String trainingName = row.getCell(2)
								.getStringCellValue();
						teacher.put("trainingName", trainingName);
						String teacherName = row.getCell(5)
								.getStringCellValue();
						teacher.put("teacherName", teacherName);
						String status;
						if ((row.getCell(9).getStringCellValue() == null)
								|| (row.getCell(9).getStringCellValue()
										.equals(""))
								|| (row.getCell(9).getStringCellValue()
										.equals("合格"))
								|| (row.getCell(9).getStringCellValue()
										.equals("不合格"))) {
							status = row.getCell(9).getStringCellValue()
									.equals("合格") ? "3" : "4";
						} else {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue()
									+ "结业状态不正确";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						teacher.put("status", status);
						if (row.getCell(10) != null) {
							row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
						}
						String certificat = row.getCell(10) == null ? "" : row
								.getCell(10).getStringCellValue();
						teacher.put("certificat", certificat);
						if (row.getCell(11) != null) {
							row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
						}
						String reason = row.getCell(11) == null ? "" : row
								.getCell(11).getStringCellValue();
						teacher.put("reason", reason);
						if (row.getCell(12) != null) {
							row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
						} else {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue()
									+ "未填写成绩";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						String score = "";
						HSSFCell cell = row.getCell(12);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								score = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								score = cell.getStringCellValue();
							}
						}
						if (score.equals("")) {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue()
									+ "未填写成绩";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						} else if (Integer.valueOf(score.trim()) > Integer
								.valueOf(row.getCell(13).getStringCellValue())) {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue()
									+ "成绩大于总分";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						teacher.put("score", score);
						if (row.getCell(14) != null) {
							row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
						} else {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue()
									+ "未填写获得学时";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						String hour = "";
						cell = row.getCell(14);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								hour = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								hour = cell.getStringCellValue();
							}
						}
						if (Integer.valueOf(hour) > Integer.valueOf(row
								.getCell(16).getStringCellValue())) {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue()
									+ "获得学时大于总学时";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						teacher.put("hour", hour);
						String netHour = "";
						cell = row.getCell(15);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								netHour = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								netHour = cell.getStringCellValue();
							}
						}
						teacher.put("netHour", netHour);

						// 加入比对项--教师身份证号
						String teacherIdCard = row.getCell(17)
								.getStringCellValue();
						teacher.put("teacherIdCard", teacherIdCard);
						p.put(i, teacher);
					} else {
						pageStatus = "ERROR";
						message = row.getCell(5).getStringCellValue() + "非本校学员";
						rString = "{\"Result\":\"" + pageStatus
								+ "\",\"Message\":\"" + message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
					}
				}
				this.session.setAttribute("maxIndex", p.size());
				for (int i = 1; i < p.size() + 1; i++) {// 解析完Excel后,将其入库的过程
					String teacherName = p.get(i).get("teacherName");
					String projectName = p.get(i).get("projectName");
					String trainingName = p.get(i).get("trainingName");
					String subjectName = p.get(i).get("subjectName");
					String status = p.get(i).get("status");
					String certificat = p.get(i).get("certificat");
					String reason = p.get(i).get("reason");
					String score = p.get(i).get("score");
					String hour = p.get(i).get("hour");
					String netHour = p.get(i).get("netHour");

					String teacherIdCard = p.get(i).get("teacherIdCard");// 教师身份证号
					// TeacherTrainingRecords
					// ttRecord=iTeacherTrainingRecordsService.getTeacherTrainingRecordByName(teacherName,
					// projectName, trainingName, subjectName);
					TeacherTrainingRecords ttRecord = iTeacherTrainingRecordsService
							.getTeacherTrainingRecordByIdCard(teacherName,
									teacherIdCard, projectName, trainingName,
									subjectName);
					if (ttRecord == null) {
						pageStatus = "ERROR";
						message = teacherName + "学员信息有误！请检查表格或重新导出学员成绩";
						rString = "{\"Result\":\"" + pageStatus
								+ "\",\"Message\":\"" + message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
					}
					// 如果学员未报到,培训信息不做任何更改.
					if (ttRecord.getTrainingStatus() == DictionyMap.TEACHER_TRAINING_RECORDS_NOReport) {
						int percent = (int) Math.ceil((i * 100) / p.size());
						this.session.setAttribute("percent", percent);
						continue;
					}
					ttRecord.setTrainingStatus(Short.valueOf(status));
					if (status.equals("3")) {
						ttRecord.setCertificate(certificat);
						ttRecord.setTrainingScore(Float.valueOf(score));
						ttRecord.setTrainingClasshour(Integer.valueOf(hour));
						ttRecord.setTrainingOnlineHour(Integer.valueOf(netHour));
					} else {
						ttRecord.setTrainingReason(reason);
						ttRecord.setTrainingScore(Float.valueOf(score));
						ttRecord.setTrainingClasshour(0);
						ttRecord.setTrainingOnlineHour(0);
					}
					iTeacherTrainingRecordsService.update(ttRecord);
					int percent = (int) Math.ceil((i * 100) / p.size());
					this.session.setAttribute("percent", percent);
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
		pageStatus = "OK";
		message = "";
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

	public void outputScore() throws IOException {
		initServlert();
		int classes = 0;
		if (request.getParameter("classes") != null
				&& !request.getParameter("classes").equals("")) {
			classes = Integer.valueOf(request.getParameter("classes"));
		}
		projectApply = iProjectApplyService.get(Integer.parseInt(request
				.getParameter("id")));
		int subjectId = projectApply.getTrainingSubject().getId();
		int trainingId = projectApply.getTrainingCollege().getId();
		int projectId = projectApply.getProject().getId();
		int offset = 0;
		int number = iTeacherTrainingRecordsService.getAduTeacherCountByIdcard(
				projectId, subjectId, trainingId, classes, "");
		List li = iTeacherTrainingRecordsService.getAduTeacherByIdcard(
				projectId, subjectId, trainingId, classes, "", offset, number);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员名单");
		HSSFRow row = s.createRow(0);
		String title[] = { "项目名称", "培训学科", "承训院校", "培训开始时间", "培训结束时间", "学员姓名",
				"所属地区", "所在学校", "报到状态", "结业状态（合格/不合格）", "证书编号", "不合格原因",
				"成绩（不大于总分）", "总分", "获得学时（不大于总学时）", "获得网络研修学时", "总学时", "身份证号",
				"年龄", "性别", "民族", "政治面貌", "学历", "教龄", "职务", "职称", "汉语水平",
				"主要教学学科", "主要教学学段", "主要教学语言", "联系电话", "邮箱", "班级", "备注" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
		for (int i = 0; i < li.size(); i++) {
			Object[] obj = (Object[]) li.get(i);
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];
			t++;
			row = s.createRow(t);
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			String tIdcard = th.getIdcard();
			String tName = th.getName();
			String tClass = ttRecord.getClasses() + "";
			tClass = (tClass.equals("null") ? "" : tClass);
			String tSex = (th.getSex() == 1 ? "男" : "女");
			String tAge = String.valueOf(Utlity.getAge(th.getBirthday()));
			String tEthnic = th.getEthnic().getName();
			String tPolitics = th.getPolitics().getName();
			String schoolName = th.getOrganization().getName();
			String tTeachAge = String
					.valueOf(Utlity.getAge(th.getTeachingAge()));
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
			String hqlString = "from TeachingLanguage where teacher="
					+ th.getId() + " and isprime=true";
			String tMainLanguage = "";
			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService
					.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				tMainLanguage = teachingLanguage.getLanguage().getName();
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + th.getId()
					+ " and isprime=1";
			String tMainGrade = "";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
					.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				tMainGrade = teachingGrade.getGrade().getName();
			}

			// 获取主要教学学科
			hqlString = "from TeachingSubject where teacher=" + th.getId()
					+ " and isprime=1";
			String tMainSubject = "";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
					.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				tMainSubject = teachingSubject.getSubject().getName();
			}

			String tChineseLanguageLevel = th.getChineseLanguageLevel()
					.getName();
			String tEductionBackground = th.getEductionBackground().getName();
			String startTime = projectApply.getTrainingStarttime().toString();
			startTime = startTime.substring(0, 10);
			String endTime = projectApply.getTrainingEndtime().toString();
			endTime = endTime.substring(0, 10);
			short trainingStatus = ttRecord.getTrainingStatus();
			String startStatus, endStatus = "";
			if (trainingStatus == 0 || trainingStatus == 1) {
				startStatus = "未报到";
			} else {
				startStatus = "已报到";
			}
			if (trainingStatus == 3) {
				endStatus = "合格";
			} else if (trainingStatus == 4) {
				endStatus = "不合格";
			}
			String tcertificate = ttRecord.getCertificate();
			String reason = ttRecord.getTrainingReason();
			String tmaxScore = "0";
			if (ttRecord.getProject().getPsqByEvaluationTeacherPsq() != null) {
				tmaxScore = iProjectApplyService
						.getTeacherPsqMaxScore(projectId) + "";
			}
			String realScore = "0";
			if (ttRecord.getTrainingScore() != null) {
				realScore = ttRecord.getTrainingScore().intValue() + "";
			}
			String maxHour = projectApply.getTrainingClasshour().toString();
			String realHour = "0";
			if (ttRecord.getTrainingClasshour() != null) {
				realHour = ttRecord.getTrainingClasshour().toString();
			}
			String netHour = "0";
			if (ttRecord.getTrainingOnlineHour() != null) {
				netHour = ttRecord.getTrainingOnlineHour().toString();
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
			String tInfo[] = { projectName, subjectName, trainingName,
					startTime, endTime, tName, tArea, schoolName, startStatus,
					endStatus, tcertificate, reason, realScore, tmaxScore,
					realHour, netHour, maxHour, tIdcard, tAge, tSex, tEthnic,
					tPolitics, tEductionBackground, tTeachAge, tDuty, tTitle,
					tChineseLanguageLevel, tMainSubject, tMainGrade,
					tMainLanguage, tMobile, tEmail, tClass + "", remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=studentscore.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}

	public String scoreInit() {
		projectApply = null;
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			String id = request.getParameter("id");
			projectApply = iProjectApplyService.get(Integer.parseInt(id));

		}
		return "scoreInit";
	}

	public void getScoreList() {
		projectApply = null;
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			projectApply = iProjectApplyService.get(Integer.parseInt(request
					.getParameter("id")));
		}
		int classes = 0;
		if (request.getParameter("classes") != null
				&& !request.getParameter("classes").equals("")) {
			classes = Integer.valueOf(request.getParameter("classes"));
		}
		//查询参数:身份证号
		String idcard = "";
		if(request.getParameter("idCard") != null
				&& !request.getParameter("idCard").equals("")){
			idcard = request.getParameter("idCard");
		}
		//查询参数:姓名
		String name = "";
		if(request.getParameter("name") != null
				&& !request.getParameter("name").equals("")){
			name = request.getParameter("name");
		}
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
			sort = sortname + " " + sorttype;
		} else {
			sort = "id desc";
		}
		try {
			//按条件查询所需要的教师培训记录
			List li = iTeacherTrainingRecordsService
					.getListForPage(projectApply.getProject().getId(),
							projectApply.getTrainingSubject().getId(),
							projectApply.getTrainingCollege().getId(), idcard, name, classes, 
							(int) Math.ceil(start / limit), limit, sort);
			//按条件查询教师培训记录数目
			int records = iTeacherTrainingRecordsService.getListTeacherCount(projectApply.getProject().getId(), projectApply
							.getTrainingSubject().getId(), projectApply
							.getTrainingCollege().getId(), classes, idcard, name);
//			lstTeacherTrainingRecords = iTeacherTrainingRecordsService
//					.getListForPage(projectApply.getProject().getId(),
//							projectApply.getTrainingSubject().getId(),
//							projectApply.getTrainingCollege().getId(), classes,
//							(int) Math.ceil(start / limit), limit, sort);
//			int records = iTeacherTrainingRecordsService.getListCount(
//					projectApply.getProject().getId(), projectApply
//							.getTrainingSubject().getId(), projectApply
//							.getTrainingCollege().getId(), classes);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			if (li.size() > 0) {
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");
				sb.append("\"Records\":[");
				for(int i = 0; i < li.size(); i++){
					Object[] obj = (Object[]) li.get(i);
					TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + ttRecord.getId());
					sbstr.append(",");
					sbstr.append("\"name\":\""
							+ ttRecord.getTeacher().getName() + "\"");
					sbstr.append(",");
					sbstr.append("\"idCard\":\""
							+ ttRecord.getTeacher().getIdcard() + "\"");
					sbstr.append(",");
					sbstr.append("\"phone\":\""
							+ ttRecord.getTeacher().getMobile() + "\"");
					sbstr.append(",");
					String sexString = ttRecord.getTeacher().getSex() == 1 ? "男"
							: "女";
					sbstr.append("\"sex\":\"" + sexString + "\"");
					sbstr.append(",");
					sbstr.append("\"organazation\":\""
							+ ttRecord.getTeacher().getOrganization().getName()
							+ "\"");
					sbstr.append(",");
					String classHoure = ttRecord.getTrainingClasshour() == null ? "0"
							: ttRecord.getTrainingClasshour().toString();
					String onlineHoure = ttRecord.getTrainingOnlineHour() == null ? "0":ttRecord.getTrainingOnlineHour().toString();
					String houre = "学时+网络学时："+classHoure+"+"+onlineHoure;
					sbstr.append("\"classHoure\":\"" + houre + "\"");
					sbstr.append(",");
					String score = ttRecord.getTrainingScore() == null ? "0"
							: ttRecord.getTrainingScore().toString();
					sbstr.append("\"score\":\"" + score + "\"");
					sbstr.append(",");
					String tclasses = "";
					if (ttRecord.getClasses() != null) {
						tclasses = ttRecord.getClasses().toString();
					}
					sbstr.append("\"classes\":\"" + tclasses + "\"");
					sbstr.append(",");
					String registTime = "";
					String status = "未报到";
					if (ttRecord.getTrainingStatus() == 0) {
						status = "异动";
					} else if (ttRecord.getTrainingStatus() == 2) {
						status = "已报到";
						registTime = dataTimeConvertUtility.timespanToString(
								ttRecord.getRegisttime(), "yyyy-MM-dd");
					} else if (ttRecord.getTrainingStatus() == 3) {
						status = "培训合格（已结业）";
						registTime = dataTimeConvertUtility.timespanToString(
								ttRecord.getRegisttime(), "yyyy-MM-dd");
					} else if (ttRecord.getTrainingStatus() == 4) {
						status = "培训不合格";
						registTime = dataTimeConvertUtility.timespanToString(
								ttRecord.getRegisttime(), "yyyy-MM-dd");
					}
					sbstr.append("\"REGISTTIME\":\"" + registTime + "\"");
					sbstr.append(",");
					sbstr.append("\"trainingStatus\":\"" + status + "\"");
					sbstr.append(",");
					sbstr.append("\"pid\":" + ttRecord.getProject().getId());
					sbstr.append(",");

					sbstr.append("\"sid\":"
							+ ttRecord.getTrainingSubject().getId());
					sbstr.append(",");

					sbstr.append("\"tc\":"
							+ ttRecord.getTrainingCollege().getId());
					sbstr.append(",");

					sbstr.append("\"tid\":" + ttRecord.getTeacher().getId());
					sbstr.append(",");
					if (ttRecord.getProject().getPsqByEvaluationTeacherPsq() != null) {
						sbstr.append("\"ttid\":"
								+ ttRecord.getProject()
										.getPsqByEvaluationTeacherPsq().getId());
					} else {
						sbstr.append("\"ttid\":0");
					}

					sbstr.append("}");
					sbstr.append(",");
					sb.append(sbstr.toString());
					sbstr.append("}");
					sbstr.append(",");
				}

				sb.delete(sb.length() - 1, sb.length());

			} else {
				sb.append("\"TotalRecordCount\":0");
				sb.append(",");
				sb.append("\"Records\":[");

			}

			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public String completeInit() {
		initServlert();
		String id = "";
		if (request.getParameterMap().containsKey("id")) {

			id = request.getParameter("id");
		}
		if (!id.equals("")) {
			classHour = 0;
			maxScore = 0;
			onlineHour = 0;
			teacherTrainingRecords = iTeacherTrainingRecordsService.get(Integer
					.parseInt(id));
			int projectid = teacherTrainingRecords.getProject().getId();
			projectApply = iProjectApplyService
					.getByTrainingRecord(teacherTrainingRecords);
			if (teacherTrainingRecords.getTrainingClasshour() != null) {
				classHour = teacherTrainingRecords.getTrainingClasshour();
			}
			if (teacherTrainingRecords.getTrainingOnlineHour() != null) {
				onlineHour = teacherTrainingRecords.getTrainingOnlineHour();
			}
			certificate = teacherTrainingRecords.getCertificate();
			maxHour = projectApply.getTrainingClasshour();
			if (teacherTrainingRecords.getProject()
					.getPsqByEvaluationTeacherPsq() != null) {
				maxScore = iProjectApplyService
						.getTeacherPsqMaxScore(projectid);
			}

		}
		return "completeInit";
	}

	public void complete() {
		initServlert();
		String id = "";
		if (request.getParameterMap().containsKey("id")) {

			id = request.getParameter("id");
		}
		if (!id.equals("")) {
			teacherTrainingRecords = iTeacherTrainingRecordsService.get(Integer
					.parseInt(id));
			projectApply = iProjectApplyService
					.getByTrainingRecord(teacherTrainingRecords);
			maxHour = projectApply.getTrainingClasshour();
			if (request.getParameterMap().containsKey("trainingStatus")) {
				String trainingStatus = request.getParameter("trainingStatus");
				if (teacherTrainingRecords.getTrainingStatus() == 0
						|| teacherTrainingRecords.getTrainingStatus() == 1) {
					Utlity.sendResponse("ERROR", "未报到的学员不能将状态修改为结业成绩合格！",
							response);
					return;
				}

				if (trainingStatus.equals("1")) {
					classHour = Integer.valueOf(request
							.getParameter("classHour"));
					onlineHour = Integer.valueOf(request
							.getParameter("onlineHour"));
					certificate = request.getParameter("certificate");
					if (classHour <= maxHour) {
						teacherTrainingRecords.setCertificate(certificate);
						teacherTrainingRecords.setTrainingClasshour(classHour);
						teacherTrainingRecords
								.setTrainingOnlineHour(onlineHour);
						teacherTrainingRecords.setTrainingReason("");
						teacherTrainingRecords.setTrainingStatus((short) 3);
					} else {
						Utlity.sendResponse("ERROR", "获得学时不可大于总学时", response);
						return;
					}
				}
			} else {
				teacherTrainingRecords.setCertificate("");
				teacherTrainingRecords.setTrainingClasshour(0);
				teacherTrainingRecords.setTrainingOnlineHour(0);
				String trainingReason = request.getParameter("trainingReason");
				teacherTrainingRecords.setTrainingReason(trainingReason);
				teacherTrainingRecords.setTrainingStatus((short) 4);
			}

			iTeacherTrainingRecordsService.update(teacherTrainingRecords);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * 自主报名学员信息确认页面初始化
	 * @return
	 */
	public String initConfirmPage() {
		initServlert();

		//查询出该承训单位所申报的所有项目信息
		UserSession us = (UserSession) session.getAttribute("usersession");
		Integer trainingCollege = us.getOrganization();
		
		List object = this.iProjectApplyService.getListByCollege(trainingCollege);
		
		List<Project> pp = new ArrayList<Project>();
		if(object.size()>0){
			for(int i = 0; i < object.size(); i++){
				Object[] obj = (Object[]) object.get(i);
				Project project = (Project)obj[1];
				System.out.println(project.getName());
				if(project != null){
					pp.add(project);
				}
			}
		}
		this.searchReportTask.addAll(pp);
		
		return "confirm";
	}
	
	/**
	 * 获取学科信息
	 */
	public void getSubject(){
		initServlert();
		
		UserSession us = (UserSession) session.getAttribute("usersession");
		Integer trainingCollege = us.getOrganization();
		
		String projectId = this.request.getParameter("projectId");
		
		List object = this.iProjectApplyService.getListByProject(Integer.parseInt(projectId), trainingCollege);
		
		if(object.size() > 0){
			StringBuilder sb = new StringBuilder();

			sb.append("{");
			sb.append("\"status\":\"OK\"");
			sb.append(",");
			sb.append("\"subjects\":[");
			sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
			int subjectId = 0;
			String subjectName = "";
			
			for(int i = 0; i < object.size(); i++){
				Object[] obj = (Object[]) object.get(i);
				ProjectApply pa = (ProjectApply)obj[0];
				subjectId = pa.getTrainingSubject().getId();
				subjectName = pa.getTrainingSubject().getName();
				
				sb.append("{\"id\":" + subjectId + ",\"name\":\"" + subjectName + "\"},");
			}
			
			sb.delete(sb.length() - 1, sb.length());

			sb.append("]}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
			StringBuilder sb = new StringBuilder();

			sb.append("{");

			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"没有查询到培训学科信息\"");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * 学员报名信息列表
	 */
	public void searchTeacherConfirmPage() {
		/**
		 * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		 */

		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");
		@SuppressWarnings("unchecked")
//		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		// 其他搜索条件
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;


		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		Integer trainingUnit = us.getOrganization();

		String teacherName = request.getParameter("teacherName");

		String status = request.getParameter("status");
		status = status == null ? "-1" : status;

//		int isadmin = 0;
		
			
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
				
				trainingUnit = Integer.parseInt(tmpTrainingUnit);
				
			}
		}

		// 搜索出条数
		int records = this.iTeacherTrainingRecordsService.getAduTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), trainingUnit, Integer.valueOf(status));

		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecordsService.getListTeacherCount(teacherName, Integer.valueOf(projectid), Integer.valueOf(subjectid), trainingUnit, Integer.valueOf(status), offset, DictionyMap.maxPageSize);
//		System.out.println(lidata.size());
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

			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + ttRecord.getId() + "\",");


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

			Teacher teacher = this.iTeacherService.get(ttRecord.getCreator());
			if (teacher != null) {
				sb.append("\"creator\":\"" + teacher.getName() + "\",");
			} else {
				sb.append("\"creator\":\"" + "" + "\",");
			}

			int isConfirm = 1;
			if(ttRecord.getIsConfirm() != null){
				isConfirm = (int) ttRecord.getIsConfirm();
			}
			
			if(isConfirm == -1){
				sb.append("\"status\":\"" + "未确认" + "\",");
			} else if (isConfirm == 1) {
				sb.append("\"status\":\"" + "通过" + "\",");
			} else if (isConfirm == 0) {
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
	
	// 确认 报送学员
	public void confirmTeacherReport() {


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
				Short state = Short.parseShort(kls[1]);

				TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.get(Integer.valueOf(ttrId));

				if (ttr != null) {
					if(method.equals("adu")){
						//确认通过
						if(ttr.getIsConfirm() != state){
							ttr.setIsConfirm(state);
							if(ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS){
								ttr.setFinalStatus((short)DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
							}
							this.iTeacherTrainingRecordsService.update(ttr);
							status = "已通过";
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
							
						}else{
							if (!flag) {
								StringBuilder checkSB = new StringBuilder();
								checkSB.append("{");
								checkSB.append("\"Result\":\"ERROR\"");
								checkSB.append(",");
								checkSB.append("\"status\":\"" + status + "\"");
								checkSB.append(",");
								checkSB.append("\"Message\":\"" + "确认状态已经是已确认" + "\"");
								checkSB.append("}");
								Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
								return;
							}
						}
					}else if(method.equals("noadu")){
						//确认未通过
						if(ttr.getIsConfirm() != state){
							ttr.setIsConfirm(state);
							
							if(ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS){
								ttr.setFinalStatus((short)DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
							}
							
							this.iTeacherTrainingRecordsService.update(ttr);
							
							// 添加审核记录
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								if(reason!=null&&!reason.equals("")){
									atc.setReason(reason);
									System.out.println(reason);
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 3);
								atc.setChecker(us.getId());
								this.iAssignTeacherCheckService.add(atc);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							
							status = "未通过";
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
								checkSB.append("\"Message\":\"" + "确认状态已经是未通过" + "\"");
								checkSB.append("}");
								Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
								return;
							}
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
	 * 初始化调整学员培训学科页
	 */
	public String changeSubjectInit(){
		initServlert();
		String year = request.getParameter("year");
		UserSession user = (UserSession) session.getAttribute("usersession");
		
		this.searchYear = this.iProjectService.getProjectYearList();
		lsttTrainingSubjects = iTrainingSubjectService.findAll();
		List<Integer> projectIds = iProjectApplyService
				.getDistinctProjectByTrainingCollege(iTrainingAdminService
						.get(user.getId()).getTrainingCollege().getId());
		lstProjects.clear();
		for (int s : projectIds) {
			Project tProject = new Project();
			tProject = iProjectService.get(s);
			if (year != null && !year.equals("") && !year.endsWith("0")){
				if(tProject.getYear().endsWith(year)){
					lstProjects.add(tProject);
				}
			}else{
				lstProjects.add(tProject);
			}
		}
		lstStatus.add("学员异动");
		lstStatus.add("未报到");
		lstStatus.add("已报到");
		lstStatus.add("培训合格");
		lstStatus.add("培训不合格");
		showAddButton = 0;
		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue() || 
				user.getRole() == ROLEENUM.ADMIN.getValue() && user.getOrganizationLevel() == 1 ||   
				(user.getRole() == ROLEENUM.TRAINING.getValue() && user.getCreateuser() == 1))
		{
			showAddButton = 1;
		}
		
		return "changeSubjectInit";
				
	}
	
	/**
	 * 获取调整学科的学员的列表
	 * 要有筛选参数
	 * projectId subjectId teacherIdCard teacherName teacherOrganization 
	 */
	public void getChangeSubjectStuList(){
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		showAddButton = 0;
		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue() || 
				user.getRole() == ROLEENUM.ADMIN.getValue() && user.getOrganizationLevel() == 1 ||   
				(user.getRole() == ROLEENUM.TRAINING.getValue() && user.getCreateuser() == 1))
		{
			showAddButton = 1;
		}
		StringBuilder sb = new StringBuilder();
		if(showAddButton == 1){
			String projectId = request.getParameter("projectId") == null ? "0":request.getParameter("projectId");
			String subjectId = request.getParameter("subjectId") == null ? "0":request.getParameter("subjectId");
			String teacherIdCard = request.getParameter("teacherIdCard") == null ? "":request.getParameter("teacherIdCard");
			String teacherName = request.getParameter("teacherName") == null ? "":request.getParameter("teacherName");
			String teacherOrganization = request.getParameter("teacherOrganization") == null ? "":request.getParameter("teacherOrganization");
			
			String trainingCollegeId = user.getOrganization()+"";
			
			Map<String, Object> param = new HashMap<String, Object>();
			if(Integer.parseInt(projectId) > 0){
				param.put("projectId", projectId);
			}
			if(Integer.parseInt(subjectId) >0){
				param.put("subjectId", subjectId);
			}
			param.put("trainCollegeId", trainingCollegeId);
			param.put("teacherName", teacherName);
			param.put("teacherIdCard", teacherIdCard);
			param.put("teacherOrganization", teacherOrganization);
			
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
				
				List lstTeacherRecords = this.iTeacherTrainingRecordsService.getTeacherRecordsForSubject(start, limit, sortParams, param);
				int totalCount = this.iTeacherTrainingRecordsService.getTeacherRecordsForSubject(sortParams, param);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				if(lstTeacherRecords != null && lstTeacherRecords.size() > 0){
					sb.append("\"TotalRecordCount\":" + totalCount);
					sb.append(",");
					sb.append("\"Records\":[");
					for(int i = 0; i < lstTeacherRecords.size(); i++){
						Object[] obj = (Object[])lstTeacherRecords.get(i);
						TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
						Teacher teacher = (Teacher)obj[2];
						
						sb.append("{");
						sb.append("\"id\":" + ttr.getId());
						sb.append(",");
						sb.append("\"name\":\"" + teacher.getName() + "\"");
						sb.append(",");
						sb.append("\"idCard\":\"" + teacher.getIdcard() + "\"");
						sb.append(",");
						sb.append("\"mobile\":\"" + teacher.getMobile() + "\"");
						sb.append(",");
						String sexString = teacher.getSex() == 1 ? "男" : "女";
						sb.append("\"sex\":\"" + sexString + "\"");
						sb.append(",");
						sb.append("\"organazation\":\"" + teacher.getOrganization().getName() + "\"");
						sb.append(",");
						sb.append("\"project\":\"" + ttr.getProject().getName() + "\"");
						sb.append(",");
						sb.append("\"trainingSubject\":\"" + ttr.getTrainingSubject().getName() + "\"");
						sb.append(",");
						String status = "未报到";
						if (ttr.getTrainingStatus() == 0) {
							status = "异动";
						} else if (ttr.getTrainingStatus() == 2) {
							status = "已报到";
						} else if (ttr.getTrainingStatus() == 3) {
							status = "培训合格（已结业）";
						} else if (ttr.getTrainingStatus() == 4) {
							status = "培训不合格";
						}
						sb.append("\"trainingStatus\":\"" + status + "\"");
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
			
		} else {
			Utlity.sendResponse("ERROR", "无权进行此操作，如有需要请联系本单位总管理员！", response);
		}
	}
	
	public String changeTeacherSubjectInit(){
		
		initServlert();
		
		UserSession us = (UserSession)session.getAttribute("usersession");
		String ttrId = request.getParameter("id");
		this.lsttTrainingSubjects.clear();
		this.teacherTrainingRecords = new TeacherTrainingRecords();
		if(ttrId != null && !"".equals(ttrId)){
			teacherTrainingRecords = this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId));
			Integer projectId = teacherTrainingRecords.getProject().getId();
//			
			Integer trainCollege = us.getOrganization();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projectId", projectId);
			params.put("trainingCollege", trainCollege);
			params.put("status", 2);
			
			@SuppressWarnings("rawtypes")
			List lstSubject = this.iProjectApplyService.getProjectByParams(params, null);
			
			if(lstSubject != null && lstSubject.size()>0){
				for(int i = 0; i < lstSubject.size(); i++){
					Object[] obj = (Object[])lstSubject.get(i);
					ProjectApply pa = (ProjectApply)obj[0];
					TrainingSubject ts = pa.getTrainingSubject();
					
					this.lsttTrainingSubjects.add(ts);
				}
			}
		}
//		StringBuilder sb = new StringBuilder();
//		
//		TeacherTrainingRecords ttr = null;
//		if(ttrId != null && !"".equals(ttrId)){
//			ttr = this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId));
//			
//			if(ttr.getTrainingStatus() != 1){
//				sb.append("{");
//				sb.append("\"Result\":\"ERROR\"");
//				sb.append(",");
//				sb.append("\"Message\":\"只有未报到学员可以调整培训学科\"");
//				sb.append("}");
//				Utlity.ResponseWrite(sb.toString(), "application/json", response);
//				return;
//			}
//			
//			Integer projectId = ttr.getProject().getId();
//			
//			Integer trainCollege = us.getOrganization();
//			
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("projectId", projectId);
//			params.put("trainingCollege", trainCollege);
//			params.put("status", 2);
//			
//			@SuppressWarnings("rawtypes")
//			List lstSubject = this.iProjectApplyService.getProjectByParams(params, null);
//			
//			if(lstSubject != null && lstSubject.size()>0){
//				sb.append("{");
//				sb.append("\"Result\":\"OK\"");
//				sb.append(",");
//				sb.append("\"Records\":[");
//				
//				for(int i = 0; i < lstSubject.size(); i++){
//					Object[] obj = (Object[])lstSubject.get(i);
//					ProjectApply pa = (ProjectApply)obj[0];
//					TrainingSubject ts = pa.getTrainingSubject();
//					
//					sb.append("{");
//					sb.append("\"id\":"+ts.getId());
//					sb.append(",");
//					sb.append("\"name\":\""+ts.getName()+"\"");
//					sb.append("},");
//				}
//				sb.delete(sb.length()-1, sb.length());
//				sb.append("]}");
//				
//			}else{
//				sb.append("{");
//				sb.append("\"Result\":\"ERROR\"");
//				sb.append(",");
//				sb.append("\"Message\":\"暂无记录\"");
//				sb.append("}");
//			}
//			Utlity.ResponseWrite(sb.toString(), "application/json", response);
//		}
		return "changeTeacherSubjectInit";
	}
	
	/**
	 * 调整学员学科
	 */
	public void changeSubject(){
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		
		TrainingCollege tc = this.iTrainingCollegeService.get(user.getOrganization());
		StringBuilder sb = new StringBuilder();
//		showAddButton = 0;
//		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue() || 
//				user.getRole() == ROLEENUM.ADMIN.getValue() && user.getOrganizationLevel() == 1 ||   
//				(user.getRole() == ROLEENUM.TRAINING.getValue() && user.getCreateuser() == 1))
//		{
//			showAddButton = 1;
//		}
//		if(showAddButton == 1){
		String ttrId = request.getParameter("ttrId");
		if(ttrId != null && !"".equals(ttrId)){
			
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId));
			TrainingSubject ots = ttr.getTrainingSubject();
			
			String subjectId = request.getParameter("subjectId") == null ? "":request.getParameter("subjectId");
			
			if(subjectId ==null && "".equals(subjectId)){
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"操作失败，请选择调整后的学科！\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			TrainingSubject ts = this.iTrainingSubjectService.get(Short.parseShort(subjectId));
			ttr.setTrainingSubject(ts);
			
			AssignTeacherCheck atc = new AssignTeacherCheck();
			atc.setChecker(user.getId());
			atc.setTeacherTrainingRecords(ttr);
			atc.setType((short)6);//type=6代表是承训单位进行学科调整的记录
			String reason = "由承训单位"+tc.getName()+"管理员"+user.getName()+"调整培训学科，由“"+ots.getName()+"”调整为“"+ts.getName()+"”";
			atc.setReason(reason);
			try {
				this.iTeacherTrainingRecordsService.update(ttr);
				this.iAssignTeacherCheckService.add(atc);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"操作成功！\"");
				sb.append("}");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"操作异常，请重新操作！\"");
				sb.append("}");
			}
			
		}
			
			
//		}else{
//			sb.append("{");
//			sb.append("\"Result\":\"ERROR\"");
//			sb.append(",");
//			sb.append("\"Message\":\"您无权限进行此操作！\"");
//			sb.append("}");
//		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getTrainingAdminList(){
		initServlert();
		
		try {
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
			
			// 排序 参数
			String sortname = "";
			String sorttype = "";
			String sort = request.getParameter("jtSorting");
			if (sort != null && !sort.equals("")) {
				String[] sortarray = sort.split(" ");
				sortname = sortarray[0];
				sorttype = sortarray[1];
			}
			UserSession user = (UserSession) session.getAttribute("usersession");
			String id = request.getParameter("id");
			
			ProjectApply pa = this.iProjectApplyService.get(Integer.valueOf(id));
			int projectId = pa.getProject().getId();
			int subjectId = pa.getTrainingSubject().getId();
			int collegeId = pa.getTrainingCollege().getId();

			int records = this.iProjectApplyService.getTrainingAdminCount(projectId , subjectId , collegeId);
			List<Object[]> li = this.iProjectApplyService.getTrainingAdminList(projectId , subjectId , collegeId , sortname, sorttype, start, limit);
					
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			
			sb.append("\"Records\":[");
			for(Object[] obj : li){
				if (obj[9] ==null){
					obj[9] = "全部";
				}
				StringBuilder sbstr = new StringBuilder();
				sbstr.append("{");
				sbstr.append("\"id\":" + obj[0]);
				sbstr.append(",");
				sbstr.append("\"name\":\"" + obj[1] + "\"");
				sbstr.append(",");
				sbstr.append("\"mobile\":\"" + obj[2] + "\"");
				sbstr.append(",");
				sbstr.append("\"sex\":" + obj[3]);
				sbstr.append(",");
				sbstr.append("\"ethnic\":\"" + obj[4] + "\"");
				sbstr.append(",");
				sbstr.append("\"organization\":\"" + obj[5] + "\"");
				sbstr.append(",");
				sbstr.append("\"department\":\"" + obj[6] + "\"");
				sbstr.append(",");
				sbstr.append("\"phone\":\"" + obj[7] + "\"");
				sbstr.append(",");
				sbstr.append("\"jobDuty\":\"" + obj[8] + "\"");
				sbstr.append(",");
				sbstr.append("\"classes\":\"" + obj[9] + "\"");
				sbstr.append("},");
				sb.append(sbstr.toString());
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("],");
			sb.append("\"TotalRecordCount\":" + records);
			sb.append("}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * @return the iProjectApplyService
	 */
	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	/**
	 * @param iProjectApplyService
	 *            the iProjectApplyService to set
	 */
	public void setiProjectApplyService(
			IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	/**
	 * @return the lstTrainingStudentSubs
	 */
	public List<TrainingStudentSub> getLstTrainingStudentSubs() {
		return lstTrainingStudentSubs;
	}

	/**
	 * @param lstTrainingStudentSubs
	 *            the lstTrainingStudentSubs to set
	 */
	public void setLstTrainingStudentSubs(
			List<TrainingStudentSub> lstTrainingStudentSubs) {
		this.lstTrainingStudentSubs = lstTrainingStudentSubs;
	}

	/**
	 * @return the iTrainingAdminService
	 */
	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}

	/**
	 * @param iTrainingAdminService
	 *            the iTrainingAdminService to set
	 */
	public void setiTrainingAdminService(
			ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}

	/**
	 * @return the iTrainingAdminService
	 */
	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	/**
	 * @param iTrainingAdminService
	 *            the iTrainingAdminService to set
	 */
	public void setiTrainingCollegeService(
			ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	/**
	 * @return the iTeacherTrainingRecordsService
	 */
	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	/**
	 * @param iTeacherTrainingRecordsService
	 *            the iTeacherTrainingRecordsService to set
	 */
	public void setiTeacherTrainingRecordsService(
			ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	/**
	 * @return the projectApply
	 */
	public ProjectApply getProjectApply() {
		return projectApply;
	}

	/**
	 * @param projectApply
	 *            the projectApply to set
	 */
	public void setProjectApply(ProjectApply projectApply) {
		this.projectApply = projectApply;
	}

	/**
	 * @return the lstTeacherTrainingRecords
	 */
	public List<TeacherTrainingRecords> getLstTeacherTrainingRecords() {
		return lstTeacherTrainingRecords;
	}

	/**
	 * @param lstTeacherTrainingRecords
	 *            the lstTeacherTrainingRecords to set
	 */
	public void setLstTeacherTrainingRecords(
			List<TeacherTrainingRecords> lstTeacherTrainingRecords) {
		this.lstTeacherTrainingRecords = lstTeacherTrainingRecords;
	}

	/**
	 * @return the teacherTrainingRecords
	 */
	public TeacherTrainingRecords getTeacherTrainingRecords() {
		return teacherTrainingRecords;
	}

	/**
	 * @param teacherTrainingRecords
	 *            the teacherTrainingRecords to set
	 */
	public void setTeacherTrainingRecords(
			TeacherTrainingRecords teacherTrainingRecords) {
		this.teacherTrainingRecords = teacherTrainingRecords;
	}

	/**
	 * @return the maxHour
	 */
	public int getMaxHour() {
		return maxHour;
	}

	/**
	 * @param maxHour
	 *            the maxHour to set
	 */
	public void setMaxHour(int maxHour) {
		this.maxHour = maxHour;
	}

	/**
	 * @return the maxHour
	 */
	public int getMaxScore() {
		return maxScore;
	}

	/**
	 * @param maxHour
	 *            the maxHour to set
	 */
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	/**
	 * @return the classHour
	 */
	public int getClassHour() {
		return classHour;
	}

	/**
	 * @param OnlineHour
	 *            the classHour to set
	 */
	public void setOnlineHour(int onlineHour) {
		this.onlineHour = onlineHour;
	}

	/**
	 * @return the OnlineHour
	 */
	public int getOnlineHour() {
		return onlineHour;
	}

	/**
	 * @param classHour
	 *            the classHour to set
	 */
	public void setClassHour(int classHour) {
		this.classHour = classHour;
	}

	/**
	 * @return the classHour
	 */
	public int getmaxScore() {
		return maxScore;
	}

	/**
	 * @param classHour
	 *            the classHour to set
	 */
	public void setmaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	/**
	 * @return the iTrainingSubjectService
	 */
	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	/**
	 * @param iTrainingSubjectService
	 *            the iTrainingSubjectService to set
	 */
	public void setiTrainingSubjectService(
			ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	/**
	 * @return the iProjectService
	 */
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	/**
	 * @param iProjectService
	 *            the iProjectService to set
	 */
	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	/**
	 * @return the lsttTrainingSubjects
	 */
	public List<TrainingSubject> getLsttTrainingSubjects() {
		return lsttTrainingSubjects;
	}

	/**
	 * @param lsttTrainingSubjects
	 *            the lsttTrainingSubjects to set
	 */
	public void setLsttTrainingSubjects(
			List<TrainingSubject> lsttTrainingSubjects) {
		this.lsttTrainingSubjects = lsttTrainingSubjects;
	}

	/**
	 * @return the lstProjects
	 */
	public List<Project> getLstProjects() {
		return lstProjects;
	}

	/**
	 * @param lstProjects
	 *            the lstProjects to set
	 */
	public void setLstProjects(List<Project> lstProjects) {
		this.lstProjects = lstProjects;
	}

	/**
	 * @return the lstProjects
	 */
	public List<String> getLstStatus() {
		return lstStatus;
	}

	/**
	 * @param lstProjects
	 *            the lstProjects to set
	 */
	public void setLstStatus(List<String> lstStatus) {
		this.lstStatus = lstStatus;
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

	public void setiTeachingGradeService(
			ITeachingGradeService iTeachingGradeService) {
		this.iTeachingGradeService = iTeachingGradeService;
	}

	public ITeachingSubjectService getiTeachingSubjectService() {
		return iTeachingSubjectService;
	}

	public void setiTeachingSubjectService(
			ITeachingSubjectService iTeachingSubjectService) {
		this.iTeachingSubjectService = iTeachingSubjectService;
	}

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public IAssignTeacherCheckService getiAssignTeacherCheckService() {
		return iAssignTeacherCheckService;
	}

	public void setiAssignTeacherCheckService(
			IAssignTeacherCheckService iAssignTeacherCheckService) {
		this.iAssignTeacherCheckService = iAssignTeacherCheckService;
	}
	
	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public int getShowAddButton() {
		return showAddButton;
	}

	public void setShowAddButton(int showAddButton) {
		this.showAddButton = showAddButton;
	}
}
