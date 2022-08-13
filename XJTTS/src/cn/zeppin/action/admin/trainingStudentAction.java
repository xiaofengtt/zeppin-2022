/**
 * 
 */
package cn.zeppin.action.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.util.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.entity.Psq;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeacherTrainingReversal;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingAdminAuthority;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingStudentSub;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.entity.teacherEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IAssignTeacherCheckService;
import cn.zeppin.service.IHsdtestService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectCycleService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IPsqService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeacherTrainingReversalService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.DictionyMap.ROLEENUM;
import cn.zeppin.utility.DownloadZipFiles;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

/**
 * @author sj
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
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
	private IPsqService iPsqService;
	private IProjectCycleService iProjectCycleService;

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
	// private List lstSubject;

	private IHsdtestService iHsdTestService;
	private ITeacherTrainingReversalService iTeacherTrainingReversalService;

	private teacherEx teacherEx;// 教师ex

	private TrainingSubject trainingSubject;
	private String classes;

	private TrainingSubject oldTrainingSubject;
	private String oldClasses;
	private Psq psq;
	private HashMap<String, String[]> paperHashMap;
	
	private List<Map> paStudyhourList;
	private List<Map> ttrStudyhourList;
	// 学时
//	private final String CERTRALIZE = "certralize";
//	private final String INFORMATION = "information";
//	private final String REGIONAL = "regional";
//	private final String SCHOOL = "school";
//	private final String TOTALHOURS = "totalhours";
//	private final String CREDIT = "credit";

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
			if (year != null && !year.equals("") && !year.equals("0")) {
				if (tProject.getYear().endsWith(year)) {
					lstProjects.add(tProject);
				}
			} else {
				lstProjects.add(tProject);
			}
		}
		lstStatus.add("学员异动");
		lstStatus.add("未报到");
		lstStatus.add("已报到");
		lstStatus.add("培训合格");
		lstStatus.add("培训不合格");
		showAddButton = 0;
		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue()
				|| user.getRole() == ROLEENUM.ADMIN.getValue()
				&& user.getOrganizationLevel() == 1
				|| (user.getRole() == ROLEENUM.TRAINING.getValue() && user
						.getCreateuser() == 1)) {
			showAddButton = 1;
		}
		return "init";
	}

	public String evaluate() {
		initServlert();
		projectApply = iProjectApplyService.get(Integer.parseInt(request
				.getParameter("id")));

		String psqid = projectApply.getProject()
				.getPsqByEvaluationTrainingPsq().getId().toString(); // 问卷id
		String projectId = projectApply.getProject().getId().toString();
		String trainingSubjectId = projectApply.getTrainingSubject().getId()
				.toString();
		String trainingCollegeId = projectApply.getTrainingCollege().getId()
				.toString();

		HashMap<String, String> mapParams = new HashMap<>();
		mapParams.put("paperid", psqid);
		mapParams.put("pid", projectId);
		mapParams.put("stid", trainingSubjectId);
		mapParams.put("tcid", trainingCollegeId);

		this.paperHashMap = this.getiPsqService().getPsqPaper(mapParams);
		this.psq = this.iPsqService.get(Integer.valueOf(psqid));
		return "evaluate";
	}

	public String authority() {
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
			// 获取用户权限
			Set<TrainingAdminAuthority> authority = ta
					.getTrainingAdminAuthoritys();
			int records = 0;
			if (ta.getRestrictRight() && authority != null
					&& authority.size() > 0) {// 有权限
				// int counts = 0;
				for (TrainingAdminAuthority taa : authority) {
					Project project = taa.getProject();

					Short subjectId = -1;
					if (taa.getTrainingSubject() != null) {
						subjectId = taa.getTrainingSubject().getId();

					}
					List<ProjectApply> lstProjectAppliess = iProjectApplyService
							.getlistForRunningPojectApply(iTrainingAdminService
									.get(user.getId()).getTrainingCollege()
									.getId(), project.getId(), subjectId,
									(int) Math.ceil(start / limit), limit, sort);
					lstProjectApplies.addAll(lstProjectAppliess);
					int count = iProjectApplyService.getCountByTrainingCollege(
							iTrainingAdminService.get(user.getId())
									.getTrainingCollege().getId(),
							project.getId(), subjectId);
					records += count;
				}

			} else {

				if (request.getParameterMap().containsKey("projectName")
						&& request.getParameterMap().containsKey("subjectName")) {
					int projectId = Integer.parseInt(request
							.getParameter("projectName"));
					int subjectId = Integer.parseInt(request
							.getParameter("subjectName"));
					lstProjectApplies = iProjectApplyService
							.getlistForRunningPojectApply(iTrainingAdminService
									.get(user.getId()).getTrainingCollege()
									.getId(), projectId, subjectId,
									(int) Math.ceil(start / limit), limit, sort);
				} else {
					lstProjectApplies = iProjectApplyService
							.getlistForRunningPojectApply(iTrainingAdminService
									.get(user.getId()).getTrainingCollege()
									.getId(), (int) Math.ceil(start / limit),
									limit, sort);

				}

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
							.getCountByTrainingCollegeId(iTrainingAdminService
									.get(user.getId()).getTrainingCollege()
									.getId());
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			if (lstProjectApplies.size() > 0) {

				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");
				sb.append("\"Records\":[");
				for (ProjectApply projectApply : lstProjectApplies) {
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + projectApply.getId());
					sbstr.append(",");
					if (projectApply.getTrainingStarttime() != null) {
						sbstr.append("\"year\":\""
								+ cn.zeppin.utility.dataTimeConvertUtility
										.timespanToString(projectApply
												.getTrainingStarttime(),
												"yyyy-MM-dd") + "\"");
					} else {
						sbstr.append("\"year\":\"待定\"");
					}

					sbstr.append(",");
					sbstr.append("\"project\":\""
							+ projectApply.getProject().getName() + "\"");
					sbstr.append(",");
					Boolean evaluate = projectApply.getProject()
							.getPsqByEvaluationTrainingPsq() == null ? false
							: true;
					sbstr.append("\"evaluate\":\"" + evaluate + "\"");
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
					if (startTimestamp != null
							&& startTimestamp.before(nowTimestamp)
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
		
		Map<Integer,TeachingLanguage> tlMap = new HashMap<Integer, TeachingLanguage>();
		// 获取主要教学语言
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
		// 获取主要教学学段
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
		// 获取主要教学学科
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

		List li = new ArrayList();
		// 获取用户权限
		Set<TrainingAdminAuthority> authority = ta.getTrainingAdminAuthoritys();

		if (ta.getRestrictRight() && authority != null && authority.size() > 0) {// 有权限){

			for (TrainingAdminAuthority taa : authority) {
				Project project = taa.getProject();

				Short sId = 0;
				if (taa.getTrainingSubject() != null) {
					sId = taa.getTrainingSubject().getId();
				}
				int num = iTeacherTrainingRecordsService.getAduTeacherCount(
						project.getId(), Integer.parseInt(sId.toString()),
						trainingId, rstatus, 0);
				List lis = iTeacherTrainingRecordsService.getAduTeacher(
						project.getId(), Integer.parseInt(sId.toString()),
						trainingId, status, rstatus, offset, number);
				number += num;
				if (lis != null && lis.size() > 0) {
					li.addAll(lis);
				}

			}

		} else {
			number = iTeacherTrainingRecordsService.getAduTeacherCount(
					projectId, subjectId, trainingId, rstatus, 0);
			li = iTeacherTrainingRecordsService.getAduTeacher(projectId,
					subjectId, trainingId, status, rstatus, offset, number);
		}

		// int number = iTeacherTrainingRecordsService.getAduTeacherCount(
		// projectId, subjectId, trainingId, rstatus, 0);
		// List li = iTeacherTrainingRecordsService.getAduTeacher(projectId,
		// subjectId, trainingId, status, rstatus, offset, number);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员名单");
		XSSFRow row = s.createRow(0);
		String title[] = { "项目名称", "培训学科", "承训院校", "学员姓名", "学员编号", "所属地区",
				"所在学校", "学校所在区域", "学校类别", "身份证号", "年龄", "性别", "民族", "政治面貌", "学历", "教龄", "编制",
				"状态", "职务", "职称", "最高学历", "毕业院校", "所学专业", "汉语水平", "主要教学学科", "主要教学学段", "主要教学语言", "联系电话",
				"邮箱", "报到状态", "班级", "备注" };
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
			} else if (ttRecord.getTrainingStatus() == 5) {
				tStatus = "培训优秀";
			} else if (ttRecord.getTrainingStatus() == 6) {
				tStatus = "培训良好";
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
			String tDuty = "无";
			if(th.getJobDuty() != null){
				tDuty = th.getJobDuty().getName();
			}
			String tTitle = "无";
			if(th.getJobTitle() != null){
				tTitle = th.getJobTitle().getName();
			}
			
			String tMobile = th.getMobile() == null ? "":th.getMobile();
			String tEmail = th.getEmail() == null ? "":th.getEmail();
//			String tDuty = th.getJobDuty().getName();
//			String tTitle = th.getJobTitle().getName();
//			String tMobile = th.getMobile();
//			String tEmail = th.getEmail();
			String tArea = "";
			String teacherStatus = "";
			if (th.getStatus() == 1) {
				teacherStatus = "在职";
			} else if (th.getStatus() == 2) {
				teacherStatus = "离职";
			} else {
				teacherStatus = "转出";
			}
			String authorized = th.getAuthorized() == 1 ? "在编" : "非编";
			Organization current = th.getOrganization().getOrganization();
			short tOrgLevel = current.getOrganizationLevel().getLevel();
			while (tOrgLevel > 1) {
				tArea = current.getName() + "  " + tArea;
				current = current.getOrganization();
				tOrgLevel = current.getOrganizationLevel().getLevel();
			}

//			// 获取主要教学语言
//			String hqlString = "from TeachingLanguage where teacher="
//					+ th.getId() + " and isprime=true";
//			String tMainLanguage = "";
//			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService
//					.getListByHSQL(hqlString);
//			if (lstTeachingLanguages.size() > 0) {
//				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
//				tMainLanguage = teachingLanguage.getLanguage().getName();
//			}
//
//			// 获取主要教学学段
//			hqlString = "from TeachingGrade where teacher=" + th.getId()
//					+ " and isprime=1";
//			String tMainGrade = "";
//			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
//					.getListByHSQL(hqlString);
//			if (lstTeachingGrades.size() > 0) {
//				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
//				tMainGrade = teachingGrade.getGrade().getName();
//			}
//
//			// 获取主要教学学科
//			hqlString = "from TeachingSubject where teacher=" + th.getId()
//					+ " and isprime=1";
//			String tMainSubject = "";
//			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
//					.getListByHSQL(hqlString);
//			if (lsttTeachingSubjects.size() > 0) {
//				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
//				tMainSubject = teachingSubject.getSubject().getName();
//			}
			if(tlMap.get(th.getId()) != null){
				tMainLanguage = tlMap.get(th.getId()).getLanguage().getName();
			}

			// 获取主要教学学段
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

			// 获取主要教学学科
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


			String tChineseLanguageLevel = th.getChineseLanguageLevel()
					.getName();
			String tEductionBackground = th.getEductionBackground().getName();

			// 添加备注 备注信息为教师替换信息
			String remarks = "";
			if (ttRecord.getReplaceStatus() == 1) {
				Teacher tt = this.iTeacherService.get(ttRecord
						.getReplaceTeacher());
				remarks += "由教师 " + tt.getName() + " 替换";
			} else if (ttRecord.getReplaceStatus() == 2) {
				Teacher tt = this.iTeacherService.get(ttRecord
						.getReplaceTeacher());
				remarks += "替换了教师  " + tt.getName();
			}

			String teacherId = th.getId().toString();
			
			//2016年12月26日新增导出信息：学校所在区域、学校类别、最高学历、毕业院校、所学专业
			String ftype = th.getOrganization().getFtype() == null ? "无" : th.getOrganization().getFtype();
			String type = th.getOrganization().getType() == null ? "无" : th.getOrganization().getType();
			String education = "";
			if(th.getEductionBackground() != null){
				education = th.getEductionBackground().getName();
			}
			String graduation = th.getGraduation() == null ? "未填" : th.getGraduation();
			String major = th.getMajor() == null ? "未填" : th.getMajor();


			String tInfo[] = { projectName, subjectName, trainingName, tName,
					teacherId, tArea, schoolName,ftype,type, tIdcard, tAge, tSex, tEthnic,
					tPolitics, tEductionBackground, tTeachAge, authorized,
					teacherStatus, tDuty, tTitle,education,graduation,major, tChineseLanguageLevel,
					tMainSubject, tMainGrade, tMainLanguage, tMobile, tEmail,
					tStatus, tClass, remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=studentlist.xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}

	public String registInit() {
		projectApply = null;
		initServlert();
		// UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			projectApply = iProjectApplyService.get(Integer.parseInt(request
					.getParameter("id")));
		}

		return "registInit";
	}

	public void getRegistList() {
		projectApply = null;
		initServlert();
		// UserSession user = (UserSession) session.getAttribute("usersession");

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
					} else if (tRecords.getTrainingStatus() == 5) {
						status = "培训优秀";
						registTime = dataTimeConvertUtility.timespanToString(
								tRecords.getRegisttime(), "yyyy-MM-dd");
					} else if (tRecords.getTrainingStatus() == 6) {
						status = "培训良好";
						registTime = dataTimeConvertUtility.timespanToString(
								tRecords.getRegisttime(), "yyyy-MM-dd");
					}
					sbstr.append("\"REGISTTIME\":\"" + registTime + "\"");
					sbstr.append(",");
					sbstr.append("\"TRAINING_STATUS\":\"" + status + "\"");
					sbstr.append(",");

					// 备注异动信息
					String reversalStr = "无";

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("projectId", tRecords.getProject().getId());
					params.put("subjectId", tRecords.getTrainingSubject()
							.getId());
					params.put("trainCollege", tRecords.getTrainingCollege()
							.getId());
					params.put("teacher", tRecords.getTeacher().getId());
					params.put("status", 1);

					// 排序
					String sorts = "id_desc";
					String[] sortArr = sorts.split("_");
					Map<String, Object> sortParams = new HashMap<String, Object>();
					sortParams.put(sortArr[0], sortArr[1]);

					int totalcount = this.iTeacherTrainingReversalService
							.getRecordsListByParams(params, sortParams);
					if (totalcount > 0) {
						List<TeacherTrainingReversal> reversals = this.iTeacherTrainingReversalService
								.getRecordsListByParams(params, sortParams, 0,
										totalcount);
						TeacherTrainingReversal ttre = reversals.get(0);
						TrainingSubject tss = this.iTrainingSubjectService
								.get(ttre.getSubject());
						if (ttre.getOldClasses() > 0) {
							reversalStr = "由培训学科“" + tss.getName() + ">>"
									+ ttre.getOldClasses() + "班”转到本学科本班级";
						} else {
							reversalStr = "由培训学科“" + tss.getName() + "”转到本学科";
						}
					}
					sbstr.append("\"reversalStr\":\"" + reversalStr + "\",");

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

				// 自主报名的项目学科 集中式培训形式下 未确认的学员不能修改状态 
				//20180614 增加非国培区培项目 判断 不需要进行该判断
				if (trr.getProject().getEnrollType() == 2 && trr.getProject().getProjectType().getProjectLevel().getLevel() <= 2
						&& (trr.getProject().getTraintype() == 1 || trr.getProject().getTraintype() == 3)
						&& trr.getIsConfirm() < 0) {
					Utlity.sendResponse("ERROR", "当前培训项目学科下，未确认的学员不能修改培训状态！",
							response);
					return;
				}

				if (status == 0
						&& (trr.getTrainingStatus() == 3 || trr
								.getTrainingStatus() == 4 || trr
								.getTrainingStatus() == 5 || trr
								.getTrainingStatus() == 6)) {
					Utlity.sendResponse("ERROR", "已经结业的学员不能将状态修改为异动！", response);
					return;
				} else if (status == 1
						&& (trr.getTrainingStatus() == 3 || trr
								.getTrainingStatus() == 4 || trr
								.getTrainingStatus() == 5 || trr
								.getTrainingStatus() == 6)) {
					Utlity.sendResponse("ERROR", "已经结业的学员不能将状态修改为未报到！",
							response);
					return;
				} else if (status == 2
						&& (trr.getTrainingStatus() == 3 || trr
								.getTrainingStatus() == 4 || trr
								.getTrainingStatus() == 5 || trr
								.getTrainingStatus() == 6)) {
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

	/**
	 * 上传学员成绩
	 */
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
				rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
				Utlity.ResponseWrite(rString, "json", response);
				return;
			}
			if (hmFiles.size() > 0) {
				fileInfo fileInfo = new fileInfo();
				Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<EDocumentType, fileInfo> entry = (Entry<EDocumentType, fileInfo>) iter.next();
					fileInfo = (fileInfo) entry.getValue();
//					String rootpath = this.getClass().getClassLoader().getResource("/").getPath() + "../..";
//					Path = rootpath + fileInfo.getFilePath();
//					Path = Path.replace("%20", " ");
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
//				HSSFSheet s = wb.getSheet("学员名单");
				XSSFSheet s = wb.getSheet("学员名单");
				XSSFRow row;
				p = new HashMap<Integer, HashMap<String, String>>();
				String rtrainingName = iTrainingCollegeService.get(us.getOrganization()).getName();
				// int t = s.getLastRowNum();
				row = s.getRow(0);
				List<String> titleList = new ArrayList<String>();
				for(int k=16; k<50 ; k++){
					if(k==49){
						pageStatus = "ERROR";
						message = "请重新导出表格进行上传，请勿更改表头！";
						rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
					}
					if("身份证号".equals(row.getCell(k).getStringCellValue().trim())){
						break;
					}
					titleList.add(row.getCell(k).getStringCellValue().trim());
				}
				List<ProjectCycle> listpc = this.iProjectCycleService.findAll();
				Map<Integer, ProjectCycle> mapPc = new HashMap<Integer, ProjectCycle>();
				if(listpc != null){
					for(ProjectCycle pc : listpc){
						mapPc.put(pc.getId(), pc);
					}
				}
				for (int i = 1; i <= s.getLastRowNum(); i++) {// 解析Excel过程
					row = s.getRow(i);

					if (rtrainingName.equals(row.getCell(2).getStringCellValue())) {
						HashMap<String, String> teacher = new HashMap<String, String>();
						String projectName = row.getCell(0).getStringCellValue();
						teacher.put("projectName", projectName);
						String subjectName = row.getCell(1).getStringCellValue();
						teacher.put("subjectName", subjectName);
						String trainingName = row.getCell(2).getStringCellValue();
						teacher.put("trainingName", trainingName);
						String teacherName = row.getCell(5).getStringCellValue();
						teacher.put("teacherName", teacherName);
						String status;
						
						//20190920--增加到四个状态 5-优秀 6-良好 3-合格 4-不合格
						if ((row.getCell(11).getStringCellValue() == null)
								|| (row.getCell(11).getStringCellValue().equals(""))
								|| (row.getCell(11).getStringCellValue().equals("合格"))
								|| (row.getCell(11).getStringCellValue().equals("不合格"))
								|| (row.getCell(11).getStringCellValue().equals("优秀"))
								|| (row.getCell(11).getStringCellValue().equals("良好"))) {
							if("优秀".equals(row.getCell(11).getStringCellValue())) {
								status = "5";
							} else if("良好".equals(row.getCell(11).getStringCellValue())) {
								status = "6";
							} else if("合格".equals(row.getCell(11).getStringCellValue())) {
								status = "3";
							} else {//不合格
								status = "4";
							}
//							status = row.getCell(11).getStringCellValue().equals("合格") ? "3" : "4";
						} else {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue() + "结业状态不正确";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						teacher.put("status", status);
						if (row.getCell(12) != null) {
							row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
						}
						String certificat = row.getCell(12) == null ? "" : row.getCell(12).getStringCellValue();
						teacher.put("certificat", certificat);
						if (row.getCell(13) != null) {
							row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
						}
						String reason = row.getCell(13) == null ? "" : row.getCell(13).getStringCellValue();
						teacher.put("reason", reason);
						if (row.getCell(14) != null) {
							row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
						} else {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue() + "未填写成绩";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						String score = "";
						XSSFCell cell = row.getCell(14);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								score = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								score = cell.getStringCellValue();
							}
						}
						if (score.equals("")) {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue() + "未填写成绩";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						} else if (Float.valueOf(score.trim()) > Integer
								.valueOf(row.getCell(15).getStringCellValue())) {
							pageStatus = "ERROR";
							message = row.getCell(5).getStringCellValue() + "成绩大于总分";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						teacher.put("score", score);
						
						int rowNum = 16;
						for(String title : titleList){
							if (row.getCell(rowNum) != null) {
								row.getCell(rowNum).setCellType(Cell.CELL_TYPE_STRING);
							} else {
								pageStatus = "ERROR";
								message = row.getCell(5).getStringCellValue() + "未填写获得" + title;
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							
							String value = "";
							cell = row.getCell(rowNum);
							if (cell != null) {
								if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									value = df.format(cell.getNumericCellValue());
								} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
									value = cell.getStringCellValue();
								}
							}
							teacher.put(title, value);
							rowNum++;
						}
						
						// 加入比对项--教师身份证号
						String teacherIdCard = row.getCell(rowNum).getStringCellValue();
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

					String teacherIdCard = p.get(i).get("teacherIdCard");// 教师身份证号
					// TeacherTrainingRecords
					// ttRecord=iTeacherTrainingRecordsService.getTeacherTrainingRecordByName(teacherName,
					// projectName, trainingName, subjectName);
					TeacherTrainingRecords ttRecord = iTeacherTrainingRecordsService.getTeacherTrainingRecordByIdCard(teacherName,
									teacherIdCard, projectName, trainingName, subjectName);
					if (ttRecord == null) {
						pageStatus = "ERROR";
						message = teacherName + "学员信息有误！请检查表格或重新导出学员成绩";
						rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
					}
					
					if(ttRecord.getProject().getStatus() > 2) {
						pageStatus = "ERROR";
						message = projectName + "项目已关闭，不允许传入成绩";
						rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
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
					
					ProjectApply pa = this.iProjectApplyService.getByTrainingRecord(ttRecord);
					if (status.equals("3")) {// 合格
						//获取应得学分
						String credit = "0";
						if(pa!= null){
							credit = pa.getCredit() == null ? "0":pa.getCredit().toString();
						}
						ttRecord.setCertificate(certificat);
						ttRecord.setTrainingScore(Float.valueOf(score));
						ttRecord.setTrainingClasshour(0);
						ttRecord.setTrainingOnlineHour(0);
						
						List<Map> studyhour = JSONUtils.json2list(pa.getStudyhour(), Map.class);
						if(studyhour != null){
							for(Map map : studyhour){
								String nameCN = map.get("nameCN").toString();
								if(p.get(i).get(nameCN) == null){
									pageStatus = "ERROR";
									message = "表头有误，请重新处理!";
									rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
									Utlity.ResponseWrite(rString, "json", response);
									return;
								}
								Integer value = Integer.valueOf(p.get(i).get(nameCN));
								map.put("value", value);
							}
						}else{
							Boolean studyhourFlag = true;
							for(ProjectCycle pc : mapPc.values()){
								Integer startyear = Integer.valueOf(pc.getStartyear());
								Integer endyear = Integer.valueOf(pc.getEndyear());
								Integer projectyear = Integer.valueOf(pa.getProject().getYear());
								if((startyear <= projectyear) && (projectyear <= endyear)){
									studyhour = JSONUtils.json2list(pc.getStudyhour(), Map.class);
									for(Map map : studyhour){
										map.put("value", 0);
									}
									studyhourFlag = false;
									break;
								}
							}
							if(studyhourFlag){
								pageStatus = "ERROR";
								message = "数据异常!";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
						}
						ttRecord.setStudyhour(JSONUtils.obj2json(studyhour));

						ttRecord.setCredit(Integer.parseInt(credit));
						// 9月12号 培训记录表冗余教师信息
//						newAddTeacherInfo(ttRecord);

					} else {
						ttRecord.setTrainingReason(reason);
						ttRecord.setTrainingScore(Float.valueOf(score));
						ttRecord.setTrainingClasshour(0);
						ttRecord.setTrainingOnlineHour(0);
						List<Map> studyhour = JSONUtils.json2list(pa.getStudyhour(), Map.class);
						if(studyhour != null){
							for(Map map: studyhour){
								String nameCN = map.get("nameCN").toString();
								if(p.get(i).get(nameCN) == null){
									pageStatus = "ERROR";
									message = teacherName + "表头有误，请重新处理!";
									rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
									Utlity.ResponseWrite(rString, "json", response);
									return;
								}
								Integer value = Integer.valueOf(p.get(i).get(nameCN));
								map.put("value", value);
							}
						}else{
							Boolean studyhourFlag = true;
							for(ProjectCycle pc : mapPc.values()){
								Integer startyear = Integer.valueOf(pc.getStartyear());
								Integer endyear = Integer.valueOf(pc.getEndyear());
								Integer projectyear = Integer.valueOf(pa.getProject().getYear());
								if((startyear <= projectyear) && (projectyear <= endyear)){
									studyhour = JSONUtils.json2list(pc.getStudyhour(), Map.class);
									for(Map map : studyhour){
										map.put("value", 0);
									}
									studyhourFlag = false;
									break;
								}
							}
							if(studyhourFlag){
								pageStatus = "ERROR";
								message = "数据异常!";
								rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
						}
						ttRecord.setStudyhour(JSONUtils.obj2json(studyhour));
					}
					TrainingAdmin rater = this.iTrainingAdminService.get(us
							.getId());
					if (rater != null) {
						ttRecord.setRater(rater);
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

	/**
	 * 导出学员成绩-增加标准学时
	 * @throws IOException
	 */
	public void outputScore() throws IOException {
		initServlert();
		int classes = 0;
		if (request.getParameter("classes") != null && !request.getParameter("classes").equals("")) {
			classes = Integer.valueOf(request.getParameter("classes"));
		}
		projectApply = iProjectApplyService.get(Integer.parseInt(request.getParameter("id")));
		int subjectId = projectApply.getTrainingSubject().getId();
		int trainingId = projectApply.getTrainingCollege().getId();
		int projectId = projectApply.getProject().getId();
		int offset = 0;
		int number = iTeacherTrainingRecordsService.getAduTeacherCountByIdcard(projectId, subjectId, trainingId, classes, "");
		List li = iTeacherTrainingRecordsService.getAduTeacherByIdcard(projectId, subjectId, trainingId, classes, "", offset, number);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员名单");
		XSSFRow row = s.createRow(0);
		
		List<String> studyhourTitleList = new ArrayList<String>();
		List<Map> studyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
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
			studyhourList = JSONUtils.json2list(projectApply.getProjectCycle().getStudyhour(), Map.class);
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

		List<String> titleList = new ArrayList<String>();
		String[] titleArrayFront = { "项目名称", "培训学科", "承训院校", "培训开始时间", "培训结束时间", "学员姓名", "所属地区", "所在学校", "学校所在区域", "学校类别", "报到状态", "结业状态（合格/不合格）", "证书编号", "不合格原因", "成绩（不大于总分）", "总分"};
		for(String title : titleArrayFront){
			titleList.add(title);
		}
		for(String title : studyhourTitleList){
			titleList.add(title);
		}
		// XXXX 获得XXXX XXXX 获得XXXX XXXX 获得XXXX XXXX 获得XXXX 
		String[] titleArrayBack = {"身份证号", "年龄", "性别", "民族", "政治面貌", "学历", "教龄", "编制", "状态", "职务", "职称", "最高学历", "毕业院校", "所学专业", "汉语水平", "主要教学学科", "主要教学学段", "主要教学语言", "联系电话", "邮箱", "班级", "备注", "备注2" };
		for(String title : titleArrayBack){
			titleList.add(title);
		}
		for (int j = 0; j < titleList.size(); j++) {
			row.createCell(j).setCellValue(titleList.get(j));
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
			String tDuty = "无";
			if(th.getJobDuty() != null){
				tDuty = th.getJobDuty().getName();
			}
			String tTitle = "无";
			if(th.getJobTitle() != null){
				tTitle = th.getJobTitle().getName();
			}
			
			String tMobile = th.getMobile() == null ? "":th.getMobile();
			String tEmail = th.getEmail() == null ? "":th.getEmail();
//			String tDuty = th.getJobDuty().getName();
//			String tTitle = th.getJobTitle().getName();
//			String tMobile = th.getMobile();
//			String tEmail = th.getEmail();
			String tStatus = "";
			if (th.getStatus() == 1) {
				tStatus = "在职";
			} else if (th.getStatus() == 2) {
				tStatus = "离职";
			} else {
				tStatus = "转出";
			}
			String authorized = th.getAuthorized() == 1 ? "在编" : "非编";
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
			String startTime = "待定";
			if (projectApply.getTrainingStarttime() != null) {
				startTime = projectApply.getTrainingStarttime().toString();
				startTime = startTime.substring(0, 10);
			}

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
			} else if (trainingStatus == 5) {
				endStatus = "优秀";
			} else if (trainingStatus == 6) {
				endStatus = "良好";
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
			
			List<String> studyhourInfoList = new ArrayList<String>();
			List<Map> teacherStudyhourList = JSONUtils.json2list(ttRecord.getStudyhour(), Map.class);
			for(String title : studyhourTitleList){
				if(teacherStudyhourList != null){
					Boolean teacherTitleFlag = true;
					for(Map map : teacherStudyhourList){
						String titleName = map.get("nameCN").toString();
						if(title.equals(titleName)){
							teacherTitleFlag = false;
							studyhourInfoList.add(map.get("value").toString());
						}
					}
					if(teacherTitleFlag){
						studyhourInfoList.add("0");
					}
				}else{
					studyhourInfoList.add("0");
				}
			}

			// 添加备注 备注信息为教师替换信息
			String remarks = "";
			if (ttRecord.getReplaceStatus() == 1) {
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks += "由教师 " + tt.getName() + " 替换";
			} else if (ttRecord.getReplaceStatus() == 2) {
				Teacher tt = this.iTeacherService.get(ttRecord.getReplaceTeacher());
				remarks += "替换了教师  " + tt.getName();
			}
			String remarks2 = "";
			if(ttRecord.getRemark1() != null && !"".equals(ttRecord.getRemark1())){
				remarks2 = ttRecord.getRemark1();
			}
			
			//2016年12月26日新增导出信息：学校所在区域、学校类别、最高学历、毕业院校、所学专业
			String ftype = th.getOrganization().getFtype() == null ? "无" : th.getOrganization().getFtype();
			String type = th.getOrganization().getType() == null ? "无" : th.getOrganization().getType();
			String education = "";
			if(th.getEductionBackground() != null){
				education = th.getEductionBackground().getName();
			}
			String graduation = th.getGraduation() == null ? "未填" : th.getGraduation();
			String major = th.getMajor() == null ? "未填" : th.getMajor();
			
			List<String> tInfoList = new ArrayList<String>();
			String tInfoFront[] = { projectName, subjectName, trainingName, startTime, endTime, tName, tArea, schoolName,
					ftype,type, startStatus, endStatus, tcertificate, reason, realScore, tmaxScore};
			for(String info : tInfoFront){
				tInfoList.add(info);
			}
			for(String info : studyhourInfoList){
				tInfoList.add(info);
			}
			String tInfoBack[] =  {tIdcard, tAge, tSex, tEthnic, tPolitics, tEductionBackground, tTeachAge, authorized,
					tStatus, tDuty, tTitle,education,graduation,major, tChineseLanguageLevel, tMainSubject, tMainGrade, 
					tMainLanguage, tMobile, tEmail, tClass + "", remarks, remarks2 };
			for(String info : tInfoBack){
				tInfoList.add(info);
			}
			for (int j = 0; j < tInfoList.size(); j++) {
				row.createCell(j).setCellValue(tInfoList.get(j));
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=studentscore.xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}

	public String scoreInit() {
		projectApply = null;
		initServlert();
		// UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			String id = request.getParameter("id");
			projectApply = iProjectApplyService.get(Integer.parseInt(id));

		}
		return "scoreInit";
	}

	public void getScoreList() {
		projectApply = null;
		
		initServlert();
		// UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			projectApply = iProjectApplyService.get(Integer.parseInt(request
					.getParameter("id")));
		}
		int classes = 0;
		if (request.getParameter("classes") != null
				&& !request.getParameter("classes").equals("")) {
			classes = Integer.valueOf(request.getParameter("classes"));
		}
		// 查询参数:身份证号
		String idcard = "";
		if (request.getParameter("idCard") != null
				&& !request.getParameter("idCard").equals("")) {
			idcard = request.getParameter("idCard");
		}
		// 查询参数:姓名
		String name = "";
		if (request.getParameter("name") != null
				&& !request.getParameter("name").equals("")) {
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
			// 按条件查询所需要的教师培训记录
			List li = iTeacherTrainingRecordsService.getListForPage(
					projectApply.getProject().getId(), projectApply
							.getTrainingSubject().getId(), projectApply
							.getTrainingCollege().getId(), idcard, name,
					classes, start, limit, sort);
			// 按条件查询教师培训记录数目
			int records = iTeacherTrainingRecordsService.getListTeacherCount(projectApply.getProject().getId(), 
					projectApply.getTrainingSubject().getId(), projectApply.getTrainingCollege().getId(), classes, idcard, name);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			if (li.size() > 0) {
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");
				sb.append("\"Records\":[");
				for (int i = 0; i < li.size(); i++) {
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
					} else if (ttRecord.getTrainingStatus() == 5) {
						status = "培训优秀（已结业）";
						registTime = dataTimeConvertUtility.timespanToString(
								ttRecord.getRegisttime(), "yyyy-MM-dd");
					} else if (ttRecord.getTrainingStatus() == 6) {
						status = "培训良好（已结业）";
						registTime = dataTimeConvertUtility.timespanToString(
								ttRecord.getRegisttime(), "yyyy-MM-dd");
					}
					if (ttRecord.getRater() != null) {
						sbstr.append("\"rater\":\""
								+ ttRecord.getRater().getName() + "\"");
					} else {
						sbstr.append("\"rater\":\"\"");
					}
					sbstr.append(",");
					sbstr.append("\"REGISTTIME\":\"" + registTime + "\"");
					sbstr.append(",");

					// 备注异动信息
					String reversalStr = "无";

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("projectId", ttRecord.getProject().getId());
					params.put("subjectId", ttRecord.getTrainingSubject()
							.getId());
					params.put("trainCollege", ttRecord.getTrainingCollege()
							.getId());
					params.put("teacher", ttRecord.getTeacher().getId());
					params.put("status", 1);

					// 排序
					String sorts = "id_desc";
					String[] sortArr = sorts.split("_");
					Map<String, Object> sortParams = new HashMap<String, Object>();
					sortParams.put(sortArr[0], sortArr[1]);

					int totalcount = this.iTeacherTrainingReversalService
							.getRecordsListByParams(params, sortParams);
					if (totalcount > 0) {
						List<TeacherTrainingReversal> reversals = this.iTeacherTrainingReversalService
								.getRecordsListByParams(params, sortParams, 0,
										totalcount);
						TeacherTrainingReversal ttre = reversals.get(0);
						TrainingSubject tss = this.iTrainingSubjectService
								.get(ttre.getSubject());
						if (ttre.getOldClasses() > 0) {
							reversalStr = "由培训学科“" + tss.getName() + ">>"
									+ ttre.getOldClasses() + "班”转到本学科本班级";
						} else {
							reversalStr = "由培训学科“" + tss.getName() + "”转到本学科";
						}
					}
					sbstr.append("\"reversalStr\":\"" + reversalStr + "\",");
					// 4个学时
					sbstr.append("\"studyhour\":").append(ttRecord.getStudyhour());
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
			teacherTrainingRecords = iTeacherTrainingRecordsService.get(Integer.parseInt(id));
			ttrStudyhourList = JSONUtils.json2list(teacherTrainingRecords.getStudyhour(), Map.class);
			int projectid = teacherTrainingRecords.getProject().getId();
			projectApply = iProjectApplyService.getByTrainingRecord(teacherTrainingRecords);
			paStudyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
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

	/**
	 * 结业
	 */
	public void complete() {
		initServlert();
		String id = "";
		if (request.getParameterMap().containsKey("id")) {
			id = request.getParameter("id");
		}
		if (!id.equals("")) {
			String remark1 = request.getParameter("remarks") == null ? "":request.getParameter("remarks");
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

				if (trainingStatus.equals("3") || trainingStatus.equals("5") || trainingStatus.equals("6")) {
					short tStatus = Short.valueOf(trainingStatus);
					certificate = request.getParameter("certificate");
					teacherTrainingRecords.setCertificate(certificate);
					teacherTrainingRecords.setTrainingReason("");
					teacherTrainingRecords.setTrainingStatus(tStatus);

					// 9月12号 结业时冗余4个学时
					List<Map> studyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
					List<Map> resultList = new ArrayList<Map>();
					for(Map map :studyhourList){
						String name = map.get("name").toString();
						String nameCN = map.get("nameCN").toString();
						Integer value1 = Integer.valueOf(map.get("value").toString());
						String value = request.getParameter(name) == null ? "0" : request.getParameter(name);
						if("".equals(value)){
							value = "0";
						}
						 
						if (isNumeric(value) && value.length() <= 4) {
							
							if(Integer.parseInt(value) <= value1){
								Map<String, Object> resultMap = new HashMap<String, Object>();
								resultMap.put("name", name);
								resultMap.put("nameCN", nameCN);
								resultMap.put("value", Integer.parseInt(value));
								resultList.add(resultMap);
							}else {
								Utlity.sendResponse("ERROR", "“" + nameCN + "”输入有误：不能超过最大设定值"+ value1, response);
								return;
							}
						} else {
							Utlity.sendResponse("ERROR", "“" + nameCN + "”输入有误：请输入4位以下（包含四位）的正整数",
									response);
							return;
						}
					}
					teacherTrainingRecords.setStudyhour(JSONUtils.obj2json(resultList));
					//增加学分
					teacherTrainingRecords.setCredit(projectApply.getCredit());
				} else {
					teacherTrainingRecords.setCertificate("");
					String trainingReason = request.getParameter("trainingReason");
					teacherTrainingRecords.setTrainingReason(trainingReason);
					teacherTrainingRecords.setTrainingStatus((short) 4);
					
					List<Map> studyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
					List<Map> resultList = new ArrayList<Map>();
					for(Map map :studyhourList){
						String name = map.get("name").toString();
						String nameCN = map.get("nameCN").toString();
						
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("name", name);
						resultMap.put("nameCN", nameCN);
						resultMap.put("value", 0);
						resultList.add(resultMap);
					}
					teacherTrainingRecords.setStudyhour(JSONUtils.obj2json(resultList));
					teacherTrainingRecords.setCredit(0);
				}
			} else {
				teacherTrainingRecords.setCertificate("");
				String trainingReason = request.getParameter("trainingReason");
				teacherTrainingRecords.setTrainingReason(trainingReason);
				teacherTrainingRecords.setTrainingStatus((short) 4);
				
				List<Map> studyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
				List<Map> resultList = new ArrayList<Map>();
				for(Map map :studyhourList){
					String name = map.get("name").toString();
					String nameCN = map.get("nameCN").toString();
					
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("name", name);
					resultMap.put("nameCN", nameCN);
					resultMap.put("value", 0);
					resultList.add(resultMap);
				}
				teacherTrainingRecords.setStudyhour(JSONUtils.obj2json(resultList));
				teacherTrainingRecords.setCredit(0);
			}

			teacherTrainingRecords.setRemark1(remark1);
			iTeacherTrainingRecordsService.update(teacherTrainingRecords);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	/**
	 * 培训记录表冗余教师信息
	 */
//	private void newAddTeacherInfo(TeacherTrainingRecords teacherTrainingRecords) {
//		// 通过id，从教师表中获取
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("id", teacherTrainingRecords.getTeacher().getId() + "");
//		List<Teacher> teacherList = iTeacherService.getTeacherListByParams(map);
//		Teacher teacher = teacherList.get(0);
//		teacherTrainingRecords.setTeacherOrganization(teacher.getOrganization()
//				.getId());// 学校
//		teacherTrainingRecords.setJobTitle(teacher.getJobTitle());// 职称
//		teacherTrainingRecords.setEductionBackground(teacher
//				.getEductionBackground());// 学历
//		teacherTrainingRecords.setPolitics(teacher.getPolitics());// 政治面貌
//		// 通过教师id，从TeachingSubject表中获取
//		Map<String, Object> tsMap = new HashMap<String, Object>();
//		tsMap.put("id", teacher.getId() + "");
//		List<TeachingSubject> teachingSubjectList = iTeachingSubjectService
//				.getListByParams(tsMap);
//		for (TeachingSubject teachingSubject : teachingSubjectList) {
//			if (teachingSubject.getIsprime().equals("1")) {
//				teacherTrainingRecords.setSubject(teachingSubject.getSubject());// 主要学科
//			}
//		}
//		// 通过教师id，从TeachingGrade表中获取
//		List<TeachingGrade> teachingGradeList = iTeachingGradeService
//				.getListByParams(tsMap);
//		for (TeachingGrade teachingGrade : teachingGradeList) {
//			if (teachingGrade.getIsprime().equals("1")) {
//				teacherTrainingRecords.setGrade(teachingGrade.getGrade());// 主要教学学段
//			}
//		}
//		List<TeachingLanguage> teachingLanguageList = iTeachingLanguageService
//				.getListByParams(tsMap);
//		for (TeachingLanguage teachingLanguage : teachingLanguageList) {
//			if (teachingLanguage.getIsprime().equals("1")) {
//				teacherTrainingRecords.setLanguage(teachingLanguage
//						.getLanguage());// 主要教学语言
//			}
//		}
//	}

//	/**
//	 * 获取学员的学时
//	 * 
//	 * @param projectType
//	 *            项目类型
//	 * @param tSubject
//	 *            培训学科
//	 */
//	private Map<String, Integer> getClassHoursForTeacher(
//			ProjectType projectType, TrainingSubject tSubject) {
//		Map<String, Integer> result = new HashMap<String, Integer>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		int centralize = 0;
//		int information = 0;
//		int regional = 0;
//		int school = 0;
//		int totalhours = 0;
//		int credit = 0;
//		
//		if (!"".equals(projectType)) {
//			params.put("projecttype", projectType.getId());
//		}
//
//		if (!"".equals(tSubject)) {
//			params.put("trainingsubject", tSubject.getId());
//		}
//		List<IdentifyClasshoursSubject> list = this.identifyClasshoursSubjectService
//				.getListByParams(params, 0, -1, "createtime");
//		if (list != null && list.size() > 0) {
//			IdentifyClasshoursSubject idc = list.get(0);
//			centralize = idc.getCentralize();
//			information = idc.getInformation();
//			regional = idc.getRegional();
//			school = idc.getSchool();
//			totalhours = idc.getTotalhours();
//			credit = idc.getCredit();
//		} else {
//			List<IdentifyClasshours> lst = this.identifyClasshoursService
//					.getListByParams(params, -1, -1, "id");
//			if (lst != null && lst.size() > 0) {
//				IdentifyClasshours idc = lst.get(0);
//				centralize = idc.getCentralize();
//				information = idc.getInformation();
//				regional = idc.getRegional();
//				school = idc.getSchool();
//				totalhours = idc.getTotalhours();
//				credit = idc.getCredit();
//			}
//		}
//		result.put(CERTRALIZE, centralize);
//		result.put(INFORMATION, information);
//		result.put(REGIONAL, regional);
//		result.put(SCHOOL, school);
//		result.put(TOTALHOURS, totalhours);
//		result.put(CREDIT, credit);
//		return result;
//	}

	/**
	 * 自主报名学员信息确认页面初始化
	 * 
	 * @return
	 */
	public String initConfirmPage() {
		initServlert();

		// 查询出该承训单位所申报的所有项目信息
		UserSession us = (UserSession) session.getAttribute("usersession");
		Integer trainingCollege = us.getOrganization();

		List object = this.iProjectApplyService
				.getListByCollege(trainingCollege);

		List<Project> pp = new ArrayList<Project>();
		if (object.size() > 0) {
			for (int i = 0; i < object.size(); i++) {
				Object[] obj = (Object[]) object.get(i);
				Project project = (Project) obj[1];
				if (project != null) {
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
	public void getSubject() {
		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");

		String projectId = this.request.getParameter("projectId") == null ? "0"
				: request.getParameter("projectId");

		TrainingAdmin ta = this.iTrainingAdminService.get(us.getId());
		// 获取用户权限
		Set<TrainingAdminAuthority> authority = ta.getTrainingAdminAuthoritys();

		if (ta.getRestrictRight() && authority != null && authority.size() > 0) {// 有权限
			Set<TrainingSubject> tsSet = new HashSet<TrainingSubject>();

			for (TrainingAdminAuthority taa : authority) {
				Project project = taa.getProject();
				if (projectId.equals(project.getId().toString())) {
					if (taa.getTrainingSubject() != null) {
						tsSet.add(taa.getTrainingSubject());
					}
				}
			}
			if (!tsSet.isEmpty()) {
				StringBuilder sb = new StringBuilder();

				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"subjects\":[");
				sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
				for (TrainingSubject ts : tsSet) {
					sb.append("{\"id\":" + ts.getId() + ",\"name\":\""
							+ ts.getName() + "\"},");
				}
				sb.delete(sb.length() - 1, sb.length());

				sb.append("]}");

				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			} else {
				Integer trainingCollege = us.getOrganization();

				String enrollType = this.request.getParameter("enrollType") == null ? "0"
						: request.getParameter("enrollType");

				Map<String, Object> params = new HashMap<String, Object>();
				if (Integer.parseInt(projectId) > 0) {
					params.put("projectId", projectId);
				}
				params.put("trainingCollege", trainingCollege);
				params.put("status", 2);

				List object = null;
				if (Integer.parseInt(enrollType) > 0) {
					object = this.iProjectApplyService.getListByProject(
							Integer.parseInt(projectId), trainingCollege);
				} else {
					object = this.iProjectApplyService.getProjectByParams(
							params, null);
				}
				if (object.size() > 0) {
					StringBuilder sb = new StringBuilder();

					sb.append("{");
					sb.append("\"status\":\"OK\"");
					sb.append(",");
					sb.append("\"subjects\":[");
					sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
					int subjectId = 0;
					String subjectName = "";

					for (int i = 0; i < object.size(); i++) {
						Object[] obj = (Object[]) object.get(i);
						ProjectApply pa = (ProjectApply) obj[0];
						subjectId = pa.getTrainingSubject().getId();
						subjectName = pa.getTrainingSubject().getName();

						sb.append("{\"id\":" + subjectId + ",\"name\":\""
								+ subjectName + "\"},");
					}

					sb.delete(sb.length() - 1, sb.length());

					sb.append("]}");

					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				}
			}
		} else {
			Integer trainingCollege = us.getOrganization();

			String enrollType = this.request.getParameter("enrollType") == null ? "0"
					: request.getParameter("enrollType");

			Map<String, Object> params = new HashMap<String, Object>();

			if (Integer.parseInt(projectId) > 0) {
				params.put("projectId", projectId);
			}
			params.put("trainingCollege", trainingCollege);
			params.put("status", 2);

			List object = null;
			if (Integer.parseInt(enrollType) > 0) {
				object = this.iProjectApplyService.getListByProject(
						Integer.parseInt(projectId), trainingCollege);
			} else {
				object = this.iProjectApplyService.getProjectByParams(params,
						null);
			}
			if (object.size() > 0) {
				StringBuilder sb = new StringBuilder();

				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"subjects\":[");
				sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
				int subjectId = 0;
				String subjectName = "";

				for (int i = 0; i < object.size(); i++) {
					Object[] obj = (Object[]) object.get(i);
					ProjectApply pa = (ProjectApply) obj[0];
					subjectId = pa.getTrainingSubject().getId();
					subjectName = pa.getTrainingSubject().getName();

					sb.append("{\"id\":" + subjectId + ",\"name\":\""
							+ subjectName + "\"},");
				}

				sb.delete(sb.length() - 1, sb.length());

				sb.append("]}");

				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
			} else {
				StringBuilder sb = new StringBuilder();

				sb.append("{");

				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"没有查询到培训学科信息\"");
				sb.append("}");

				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
			}
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
		// List<ProjectType> lityps = (List<ProjectType>)
		// session.getAttribute("lstProjectType");

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

		// int isadmin = 0;

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
		int records = this.iTeacherTrainingRecordsService.getAduTeacherCount(
				teacherName, Integer.valueOf(projectid),
				Integer.valueOf(subjectid), trainingUnit,
				Integer.valueOf(status));

		int totalPage = (int) Math.ceil((float) records
				/ DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecordsService.getListTeacherCount(
				teacherName, Integer.valueOf(projectid),
				Integer.valueOf(subjectid), trainingUnit,
				Integer.valueOf(status), offset, DictionyMap.maxPageSize);
		// System.out.println(lidata.size());
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

			sb.append("\"projectName\":\""
					+ ttRecord.getProject().getShortname() + "\",");
			sb.append("\"subjectName\":\""
					+ ttRecord.getTrainingSubject().getName() + "\",");
			sb.append("\"trainingName\":\""
					+ ttRecord.getTrainingCollege().getName() + "\",");
			sb.append("\"schoolName\":\"" + th.getOrganization().getName()
					+ "\",");

			sb.append("\"idcard\":\"" + th.getIdcard() + "\",");
			sb.append("\"name\":\"" + th.getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");

			sb.append("\"sex\":\"" + th.getSex() + "\",");
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "男" : "女") + "\",");
			sb.append("\"age\":\""
					+ String.valueOf(Utlity.getAge(th.getBirthday())) + "\",");
			sb.append("\"ethnic\":\"" + th.getEthnic().getName() + "\",");
			sb.append("\"politics\":\"" + th.getPolitics().getName() + "\",");

			sb.append("\"teachAge\":\""
					+ String.valueOf(Utlity.getAge(th.getTeachingAge()))
					+ "\",");
			sb.append("\"jobDuty\":\"" + th.getJobDuty().getName() + "\",");
			sb.append("\"jobTitle\":\"" + th.getJobTitle().getName() + "\",");
			sb.append("\"muLanguage\":\"" + (th.getMultiLanguage() ? "是" : "否")
					+ "\",");

			for (TeachingLanguage tl : th.getTeachingLanguages()) {
				if (tl.getIsprime()) {
					sb.append("\"mainLanguage\":\""
							+ tl.getLanguage().getName() + "\",");
					break;
				}
			}

			for (TeachingGrade tg : th.getTeachingGrades()) {
				if (tg.getIsprime()) {
					sb.append("\"mainGrade\":\"" + tg.getGrade().getName()
							+ "\",");
					break;
				}
			}

			for (TeachingSubject ts : th.getTeachingSubjects()) {
				if (ts.getIsprime()) {
					sb.append("\"mainSubject\":\"" + ts.getSubject().getName()
							+ "\",");
					break;
				}
			}

			sb.append("\"area\":\"" + getAreaNaviString(th.getArea()) + "\",");

			sb.append("\"edubackground\":\""
					+ th.getEductionBackground().getName() + "\",");
			sb.append("\"chineseLanguageLevel\":\""
					+ th.getChineseLanguageLevel().getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");

			Teacher teacher = this.iTeacherService.get(ttRecord.getCreator());
			if (teacher != null) {
				sb.append("\"creator\":\"" + teacher.getName() + "\",");
			} else {
				sb.append("\"creator\":\"" + "" + "\",");
			}

			int isConfirm = 1;
			if (ttRecord.getIsConfirm() != null) {
				isConfirm = (int) ttRecord.getIsConfirm();
			}

			if (isConfirm == -1) {
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
			if (areacode != null && !areacode.equals("0")
					&& !areacode.equals("")) {
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
		String reason = request.getParameter("reason");

		String[] spKey = key.split(",");

		if (spKey.length > 0) {

			boolean flag = spKey.length > 1 ? true : false; // 是否是多条

			String status = "";
			for (String spKeyStr : spKey) {
				String[] kls = spKeyStr.split("-");
				String ttrId = kls[0];
				Short state = Short.parseShort(kls[1]);

				TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService
						.get(Integer.valueOf(ttrId));

				if (ttr != null) {
					if (method.equals("adu")) {
						// 确认通过
						if (ttr.getIsConfirm() != state) {
							ttr.setIsConfirm(state);
							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS);
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
								Utlity.ResponseWrite(checkSB.toString(),
										"application/json", response);
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
								checkSB.append("\"Message\":\"" + "确认状态已经是已确认"
										+ "\"");
								checkSB.append("}");
								Utlity.ResponseWrite(checkSB.toString(),
										"application/json", response);
								return;
							}
						}
					} else if (method.equals("noadu")) {
						// 确认未通过
						if (ttr.getIsConfirm() != state) {
							ttr.setIsConfirm(state);

							if (ttr.getFinalStatus() != DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
								ttr.setFinalStatus((short) DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS);
							}

							this.iTeacherTrainingRecordsService.update(ttr);

							// 添加审核记录
							try {
								AssignTeacherCheck atc = new AssignTeacherCheck();
								if (reason != null && !reason.equals("")) {
									atc.setReason(reason);
								}
								atc.setTeacherTrainingRecords(ttr);
								atc.setType((short) 8);//确认不通过
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
								Utlity.ResponseWrite(checkSB.toString(),
										"application/json", response);
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
								checkSB.append("\"Message\":\"" + "确认状态已经是未通过"
										+ "\"");
								checkSB.append("}");
								Utlity.ResponseWrite(checkSB.toString(),
										"application/json", response);
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
			Utlity.ResponseWrite(checkSB.toString(), "application/json",
					response);
		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "没有审核记录" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json",
					response);
		}

	}

	/**
	 * 初始化调整学员培训学科页
	 */
	public String changeSubjectInit() {
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
			if (year != null && !year.equals("") && !year.equals("0")) {
				if (tProject.getYear().endsWith(year)) {
					lstProjects.add(tProject);
				}
			} else {
				lstProjects.add(tProject);
			}
		}
		lstStatus.add("学员异动");
		lstStatus.add("未报到");
		lstStatus.add("已报到");
		lstStatus.add("培训合格");
		lstStatus.add("培训不合格");
		showAddButton = 0;
		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue()
				|| user.getRole() == ROLEENUM.ADMIN.getValue()
				&& user.getOrganizationLevel() == 1
				|| (user.getRole() == ROLEENUM.TRAINING.getValue() && user
						.getCreateuser() == 1)) {
			showAddButton = 1;
		}

		return "changeSubjectInit";

	}

	/**
	 * 获取调整学科的学员的列表 要有筛选参数 projectId subjectId teacherIdCard teacherName
	 * teacherOrganization
	 */
	public void getChangeSubjectStuList() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		showAddButton = 0;
		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue()
				|| user.getRole() == ROLEENUM.ADMIN.getValue()
				&& user.getOrganizationLevel() == 1
				|| (user.getRole() == ROLEENUM.TRAINING.getValue() && user
						.getCreateuser() == 1)) {
			showAddButton = 1;
		}
		StringBuilder sb = new StringBuilder();
		if (showAddButton == 1) {
			String projectId = request.getParameter("projectId") == null ? "0"
					: request.getParameter("projectId");
			String subjectId = request.getParameter("subjectId") == null ? "0"
					: request.getParameter("subjectId");
			String teacherIdCard = request.getParameter("teacherIdCard") == null ? ""
					: request.getParameter("teacherIdCard");
			String teacherName = request.getParameter("teacherName") == null ? ""
					: request.getParameter("teacherName");
			String teacherOrganization = request
					.getParameter("teacherOrganization") == null ? "" : request
					.getParameter("teacherOrganization");

			String trainingCollegeId = user.getOrganization() + "";

			Map<String, Object> param = new HashMap<String, Object>();
			if (Integer.parseInt(projectId) > 0) {
				param.put("projectId", projectId);
			}
			if (Integer.parseInt(subjectId) > 0) {
				param.put("subjectId", subjectId);
			}
			param.put("trainCollegeId", trainingCollegeId);
			param.put("teacherName", teacherName);
			param.put("teacherIdCard", teacherIdCard);
			param.put("teacherOrganization", teacherOrganization);
			param.put("trainStatus", 0);

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

				List lstTeacherRecords = this.iTeacherTrainingRecordsService
						.getTeacherRecordsForSubject(start, limit, sortParams,
								param);
				int totalCount = this.iTeacherTrainingRecordsService
						.getTeacherRecordsForSubject(sortParams, param);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				if (lstTeacherRecords != null && lstTeacherRecords.size() > 0) {
					sb.append("\"TotalRecordCount\":" + totalCount);
					sb.append(",");
					sb.append("\"Records\":[");
					for (int i = 0; i < lstTeacherRecords.size(); i++) {
						Object[] obj = (Object[]) lstTeacherRecords.get(i);
						TeacherTrainingRecords ttr = (TeacherTrainingRecords) obj[0];
						Teacher teacher = (Teacher) obj[2];

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
						sb.append("\"organazation\":\""
								+ teacher.getOrganization().getName() + "\"");
						sb.append(",");
						sb.append("\"project\":\"" + ttr.getProject().getName()
								+ "\"");
						sb.append(",");
						sb.append("\"trainingSubject\":\""
								+ ttr.getTrainingSubject().getName() + "\"");
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
						} else if (ttr.getTrainingStatus() == 5) {
							status = "培训优秀";
						} else if (ttr.getTrainingStatus() == 6) {
							status = "培训良好";
						}
						sb.append("\"trainingStatus\":\"" + status + "\"");
						sb.append("},");
					}
					sb.delete(sb.length() - 1, sb.length());

				} else {
					sb.append("\"TotalRecordCount\":0");
					sb.append(",");
					sb.append("\"Records\":[");
				}
				sb.append("]}");

				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			Utlity.sendResponse("ERROR", "无权进行此操作，如有需要请联系本单位总管理员！", response);
		}
	}

	public String changeTeacherSubjectInit() {

		initServlert();
		// String hqlString = "";
		UserSession us = (UserSession) session.getAttribute("usersession");
		String ttrId = request.getParameter("id");
		this.lsttTrainingSubjects.clear();
		this.teacherTrainingRecords = new TeacherTrainingRecords();
		if (ttrId != null && !"".equals(ttrId)) {
			teacherTrainingRecords = this.iTeacherTrainingRecordsService
					.get(Integer.parseInt(ttrId));
			Integer projectId = teacherTrainingRecords.getProject().getId();
			//
			Integer trainCollege = us.getOrganization();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projectId", projectId);
			params.put("trainingCollege", trainCollege);
			params.put("status", 2);

			List lstSubject = this.iProjectApplyService.getProjectByParams(
					params, null);

			if (lstSubject != null && lstSubject.size() > 0) {
				for (int i = 0; i < lstSubject.size(); i++) {
					Object[] obj = (Object[]) lstSubject.get(i);
					ProjectApply pa = (ProjectApply) obj[0];
					TrainingSubject ts = pa.getTrainingSubject();

					this.lsttTrainingSubjects.add(ts);
				}
			}
		}

		this.teacherEx = new teacherEx();

		Teacher teacher = this.teacherTrainingRecords.getTeacher();
		this.teacherEx.setTeacher(teacher);
		String areasString = "";
		List<String> lstA = iAreaService.getParentNodes(teacher.getArea()
				.getCode());
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
			teacherEx.setAreaString(areasString.substring(0,
					areasString.length() - 2));
		}
		teacherEx.setAgeString((String.valueOf(Utlity.getAge(teacher
				.getBirthday()))));
		teacherEx.setSexString(teacher.getSex() == 1 ? "男" : "女");
		if (teacher.getTeachingAge() != null) {
			teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher
					.getTeachingAge())));
		}
		teacherEx.setAuthorized(teacher.getAuthorized() == 1 ? "在编" : "非编");
		teacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "是" : "否");
		// // 获取主要教学语言
		// hqlString = "from TeachingLanguage where teacher=" + teacher.getId()
		// + " and isprime=true";
		//
		// List<TeachingLanguage> lstTeachingLanguages =
		// iTeachingLanguageService.getListByHSQL(hqlString);
		// if (lstTeachingLanguages.size() > 0) {
		// TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
		// teacherEx.setMainTeachingLanguage(teachingLanguage);
		// }
		//
		// // 获取主要教学学段
		// hqlString = "from TeachingGrade where teacher=" + teacher.getId() +
		// " and isprime=1";
		// List<TeachingGrade> lstTeachingGrades =
		// iTeachingGradeService.getListByHSQL(hqlString);
		// if (lstTeachingGrades.size() > 0) {
		// TeachingGrade teachingGrade = lstTeachingGrades.get(0);
		// teacherEx.setMainTeachingClass(teachingGrade);
		// }
		//
		// // 获取主要教学学科
		// hqlString = "from TeachingSubject where teacher=" + teacher.getId() +
		// " and isprime=1";
		// List<TeachingSubject> lsttTeachingSubjects =
		// iTeachingSubjectService.getListByHSQL(hqlString);
		// if (lsttTeachingSubjects.size() > 0) {
		// TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
		// teacherEx.setMainTeachingCourse(teachingSubject);
		// }
		teacherEx.setOrganization(teacher.getOrganization());

		return "changeTeacherSubjectInit";
	}

	/**
	 * 添加学员培训学科调整记录 参数：ttrId rep_trainingSubjectId classId creator creatTime
	 * 需要注意的地方：已有完成培训的不能调整学科，项目关闭的不能调整学科
	 */
	public void addchangeSubjectRecords() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();

		String ttrId = request.getParameter("ttrId") == null ? "0" : request
				.getParameter("ttrId");

		Integer id = Integer.parseInt(ttrId);
		TeacherTrainingRecords ttr = null;
		if (id > 0) {
			ttr = this.iTeacherTrainingRecordsService.get(id);

		}
		if (ttr != null) {
			if (ttr.getProject().getStatus() == 4) {
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"" + "该学员报名的项目已关闭" + "\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			if (ttr.getTrainingStatus() > 2) {// 已结业的学员
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"" + "该学员已结业" + "\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			String repSubjectId = request.getParameter("subjectId") == null ? "0"
					: request.getParameter("subjectId");
			Short subjectId = Short.parseShort(repSubjectId);
			if (subjectId <= 0) {
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"操作失败，请选择调整后的学科！\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			String classes = request.getParameter("classes") == null ? ""
					: request.getParameter("classes");
			Integer classesId = 0;
			if (!"".equals(classes)) {
				classesId = Integer.parseInt(classes);
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projectId", ttr.getProject().getId());
			params.put("subjectId", ttr.getTrainingSubject().getId());
			params.put("trainCollege", ttr.getTrainingCollege().getId());
			params.put("teacher", ttr.getTeacher().getId());
			params.put("status", -1);

			// 排序
			String sort = "id_desc";
			String[] sortArr = sort.split("_");
			Map<String, Object> sortParams = new HashMap<String, Object>();
			sortParams.put(sortArr[0], sortArr[1]);

			int count = this.iTeacherTrainingReversalService
					.getRecordsListByParams(params, sortParams);
			if (count > 0) {
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"" + "该学员正在调整中，请等待管理员审核后再继续。。。" + "\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
				return;
			}

			TrainingSubject ts = this.iTrainingSubjectService.get(subjectId);
			if (ts.getId() == ttr.getTrainingSubject().getId()) {
				// 同学科内只能转班级，同学科、同班级的不做处理
				if (classesId == ttr.getClasses()) {
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"" + "该学员已经在本学科本班级内，无需调整。。。" + "\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				}
			}

			// 信息入库
			TeacherTrainingReversal ttre = new TeacherTrainingReversal();
			ttre.setTeacherTrainingRecords(ttr);
			ttre.setTrainingSubject(ts);
			ttre.setClasses(classesId);
			ttre.setProject(ttr.getProject().getId());
			ttre.setSubject(ttr.getTrainingSubject().getId());
			ttre.setTrainingCollege(ttr.getTrainingCollege().getId());

			int oldClasses = 0;
			if (ttr.getClasses() != null) {
				oldClasses = ttr.getClasses();
			}
			ttre.setOldClasses(oldClasses);

			// 状态 -1未审核 0审核未通过 1审核通过
			ttre.setStatus((short) -1);

			// 创建者信息
			ttre.setCreator(us.getId());
			ttre.setCreattime(new Timestamp(System.currentTimeMillis()));
			ttre.setTeacher(ttr.getTeacher().getId());
			try {
				// 入库
				this.iTeacherTrainingReversalService.add(ttre);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"" + "成功" + "\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);
			} catch (Exception e) {
				e.printStackTrace();
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"ERROR\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"" + "失败，出现异常。。。" + "\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json",
						response);
				return;
			}

		} else {
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"" + "失败,教师信息不存在。。。" + "\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json",
					response);
			return;
		}

	}

	/**
	 * 初始化审核学员学科班级调整记录列表页
	 * 
	 * @return
	 */
	public String getChangeSubjectRecordsInit() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		this.searchYear = this.iProjectService.getProjectYearList();
		lsttTrainingSubjects.clear();
		lstProjects.clear();
		lstStatus.add("未审核");
		lstStatus.add("已通过");
		lstStatus.add("未通过");
		showAddButton = 0;
		if (user.getRole() == ROLEENUM.SUPERADMIN.getValue()
				|| user.getRole() == ROLEENUM.ADMIN.getValue()
				&& user.getOrganizationLevel() == 1
				|| (user.getRole() == ROLEENUM.TRAINING.getValue() && user
						.getCreateuser() == 1)) {
			showAddButton = 1;
		}

		return "changeSubjectrecordsInit";

	}

	/**
	 * 获取学员培训学科调整记录列表
	 */
	public void getChangeSubjectRecordsList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");

		showAddButton = 0;
		if (us.getRole() == ROLEENUM.SUPERADMIN.getValue()
				|| us.getRole() == ROLEENUM.ADMIN.getValue()
				&& us.getOrganizationLevel() == 1
				|| (us.getRole() == ROLEENUM.TRAINING.getValue() && us
						.getCreateuser() == 1)) {
			showAddButton = 1;
		}
		if (showAddButton == 1) {
			// 其他搜索条件
			String projectid = request.getParameter("projectId");
			projectid = projectid == null ? "0" : projectid;

			String subjectid = request.getParameter("subjectId");
			subjectid = subjectid == null ? "0" : subjectid;

			String classes = request.getParameter("classes") == null ? "0"
					: request.getParameter("classes");

			String status = request.getParameter("status");
			status = status == null ? "-2" : status;

			String trainingUnit = us.getOrganization() + "";

			Map<String, Object> param = new HashMap<String, Object>();
			if (Integer.parseInt(projectid) > 0) {
				param.put("projectId", projectid);
			}
			if (Integer.parseInt(subjectid) > 0) {
				param.put("subjectId", subjectid);
			}
			if (Integer.parseInt(classes) > 0) {
				param.put("classes", classes);
			}
			param.put("trainingCollege", trainingUnit);
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
				StringBuilder sb = new StringBuilder();
				int totalCount = this.iTeacherTrainingReversalService
						.getRecordsListByParams(param, sortParams);
				List<TeacherTrainingReversal> lstTTre = this.iTeacherTrainingReversalService
						.getRecordsListByParams(param, sortParams, start, limit);
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				if (lstTTre != null && lstTTre.size() > 0) {
					sb.append("\"TotalRecordCount\":" + totalCount);
					sb.append(",");
					sb.append("\"Records\":[");
					for (TeacherTrainingReversal ttre : lstTTre) {
						TeacherTrainingRecords ttr = ttre
								.getTeacherTrainingRecords();
						Teacher teacher = ttr.getTeacher();
						Project project = ttr.getProject(); // 所属项目
						TrainingSubject ts = this.iTrainingSubjectService
								.get(ttre.getSubject()); // 原学科
						TrainingSubject reTs = ttre.getTrainingSubject(); // 调整后的学科
						Integer classesId = ttre.getOldClasses() == 0 ? 0
								: ttre.getOldClasses(); // 原班级
						Integer reClassesId = ttre.getClasses(); // 替换后的班级
						String reStatus = "未审核";
						if (ttre.getStatus() == 1) {
							reStatus = "已通过";
						} else if (ttre.getStatus() == 0) {
							reStatus = "未通过";
						}
						sb.append("{");
						sb.append("\"id\":" + ttre.getId());
						sb.append(",");
						sb.append("\"teacher\":\"" + teacher.getName() + "\"");
						sb.append(",");
						sb.append("\"idcard\":\"" + teacher.getIdcard() + "\"");
						sb.append(",");
						sb.append("\"project\":\"" + project.getName() + "\"");
						sb.append(",");
						sb.append("\"trainingSubject\":\"" + ts.getName()
								+ "\"");
						sb.append(",");
						sb.append("\"classes\":" + classesId + "");
						sb.append(",");
						sb.append("\"reTrainingSubject\":\"" + reTs.getName()
								+ "\"");
						sb.append(",");
						sb.append("\"reClasses\":" + reClassesId + "");
						sb.append(",");
						sb.append("\"status\":\"" + reStatus + "\"");
						sb.append("},");
					}
					sb.delete(sb.length() - 1, sb.length());
				} else {
					sb.append("\"TotalRecordCount\":0");
					sb.append(",");
					sb.append("\"Records\":[");
				}
				sb.append("]}");

				Utlity.ResponseWrite(sb.toString(), "application/json",
						response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Utlity.sendResponse("ERROR", "无权进行此操作，如有需要请联系本单位总管理员！", response);
		}
	}

	public void getChangeSubjectStatus() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");

		showAddButton = 0;
		if (us.getRole() == ROLEENUM.SUPERADMIN.getValue()
				|| us.getRole() == ROLEENUM.ADMIN.getValue()
				&& us.getOrganizationLevel() == 1
				|| (us.getRole() == ROLEENUM.TRAINING.getValue() && us
						.getCreateuser() == 1)) {
			showAddButton = 1;
		}
		if (showAddButton == 1) {
			// 其他搜索条件
			String projectid = request.getParameter("projectId");
			projectid = projectid == null ? "0" : projectid;

			String subjectid = request.getParameter("subjectId");
			subjectid = subjectid == null ? "0" : subjectid;

			String classes = request.getParameter("classes") == null ? "0"
					: request.getParameter("classes");

			String status = request.getParameter("status");
			status = status == null ? "-2" : status;

			String trainingUnit = us.getOrganization() + "";

			Map<String, Object> param = new HashMap<String, Object>();
			if (Integer.parseInt(projectid) > 0) {
				param.put("projectId", projectid);
			}
			if (Integer.parseInt(subjectid) > 0) {
				param.put("subjectId", subjectid);
			}
			if (Integer.parseInt(classes) > 0) {
				param.put("classes", classes);
			}
			param.put("trainingCollege", trainingUnit);

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

			// try {
			StringBuilder sb = new StringBuilder();
			param.put("status", "-2");
			int records = this.iTeacherTrainingReversalService
					.getRecordsListByParams(param, sortParams);
			param.put("status", "-1");
			int noCheck = this.iTeacherTrainingReversalService
					.getRecordsListByParams(param, sortParams);
			param.put("status", "1");
			int checkPass = this.iTeacherTrainingReversalService
					.getRecordsListByParams(param, sortParams);
			param.put("status", "0");
			int checkNoPass = this.iTeacherTrainingReversalService
					.getRecordsListByParams(param, sortParams);

			sb.append("{\"totalCount\":" + (records) + ",");// 全部
			sb.append("\"noCheck\":" + (noCheck) + ",");// 未审核
			sb.append("\"checkPass\":" + (checkPass) + ",");// 已通过
			sb.append("\"checkNoPass\":" + (checkNoPass));// 未通过
			sb.append("}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("{\"totalCount\":0,");// 全部
			sb.append("\"noCheck\":0,");// 未审核
			sb.append("\"checkPass\":0,");// 已通过
			sb.append("\"checkNoPass\":0");// 未通过
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	/**
	 * 审核异动学员初始化
	 * 
	 * @return
	 */
	public String reviewReversalInit() {
		initServlert();
		String hqlString = "";
		if (request.getParameter("id") != null
				&& !request.getParameter("id").equals("")) {
			Integer ttrid = Integer.valueOf(request.getParameter("id"));

			TeacherTrainingReversal ttre = this.iTeacherTrainingReversalService
					.get(ttrid);
			this.teacherEx = new teacherEx();
			this.teacherTrainingRecords = ttre.getTeacherTrainingRecords();
			this.trainingSubject = ttre.getTrainingSubject();
			if (ttre.getClasses() > 0) {
				this.classes = ttre.getClasses() + "";
			} else {
				this.classes = "无";
			}
			if (ttre.getOldClasses() > 0) {
				this.oldClasses = ttre.getOldClasses() + "";
			} else {
				this.oldClasses = "无";
			}
			this.oldTrainingSubject = this.iTrainingSubjectService.get(ttre
					.getSubject());

			Teacher teacher = this.teacherTrainingRecords.getTeacher();
			this.teacherEx.setTeacher(teacher);
			String areasString = "";
			List<String> lstA = iAreaService.getParentNodes(teacher.getArea()
					.getCode());
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
				teacherEx.setAreaString(areasString.substring(0,
						areasString.length() - 2));
			}
			teacherEx.setAgeString((String.valueOf(Utlity.getAge(teacher
					.getBirthday()))));
			teacherEx.setSexString(teacher.getSex() == 1 ? "男" : "女");
			if (teacher.getTeachingAge() != null) {
				teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher
						.getTeachingAge())));
			}
			teacherEx.setAuthorized(teacher.getAuthorized() == 1 ? "在编" : "非编");
			teacherEx
					.setIsMultiLanguage(teacher.getMultiLanguage() ? "是" : "否");
			// 获取主要教学语言
			hqlString = "from TeachingLanguage where teacher="
					+ teacher.getId() + " and isprime=true";

			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService
					.getListByHSQL(hqlString);
			if (lstTeachingLanguages.size() > 0) {
				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
				teacherEx.setMainTeachingLanguage(teachingLanguage);
			}

			// 获取主要教学学段
			hqlString = "from TeachingGrade where teacher=" + teacher.getId()
					+ " and isprime=1";
			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
					.getListByHSQL(hqlString);
			if (lstTeachingGrades.size() > 0) {
				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
				teacherEx.setMainTeachingClass(teachingGrade);
			}

			// 获取主要教学学科
			hqlString = "from TeachingSubject where teacher=" + teacher.getId()
					+ " and isprime=1";
			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
					.getListByHSQL(hqlString);
			if (lsttTeachingSubjects.size() > 0) {
				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
				teacherEx.setMainTeachingCourse(teachingSubject);
			}
			teacherEx.setOrganization(teacher.getOrganization());

		}
		return "reviewreversal";
	}

	/**
	 * 审核调整学员学科
	 */
	public void changeSubject() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		TrainingCollege tc = this.iTrainingCollegeService.get(user
				.getOrganization());

		String id = request.getParameter("id") == null ? "" : request
				.getParameter("id");
		Integer ttreId = 0;
		if (!"".equals(id)) {
			ttreId = Integer.parseInt(id);
		}

		TeacherTrainingReversal ttre = this.iTeacherTrainingReversalService
				.get(ttreId);

		if (ttre != null) {
			String method = request.getParameter("method") == null ? ""
					: request.getParameter("method");

			if (!"".equals(method) && "pass".equals(method)) {// 审核通过
				/*
				 * 审核流程
				 * 1、判断原纪录是否审核过：未审核的直接改变审核状态；已审核通过的，不做任何处理，直接返回成功；已审核不通过的，不能改变状态
				 * 2、根据1的判断执行不同操作：未审核的，把教师报送记录相关信息更新；其余的不做任何处理
				 */
				if (ttre.getStatus() == 1) {
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"操作成功！\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				} else if (ttre.getStatus() == 0) {
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"操作失败，已最终审核不通过！\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				} else {
					ttre.setStatus((short) 1);
					TeacherTrainingRecords ttr = ttre
							.getTeacherTrainingRecords();
					TrainingSubject ts = ttr.getTrainingSubject();
					Integer classesId = ttr.getClasses();
					ttr.setTrainingSubject(ttre.getTrainingSubject());
					if (ttre.getClasses() > 0) {
						ttr.setClasses(ttre.getClasses());
					}

					AssignTeacherCheck atc = new AssignTeacherCheck();
					atc.setChecker(user.getId());
					atc.setTeacherTrainingRecords(ttr);
					atc.setType((short) 6);// type=6代表是承训单位进行学科调整的记录
					String classes = "";
					if (classesId != null && classesId > 0) {
						classes = classesId + "班";
					}
					String reClasses = "";
					if (ttre.getClasses() > 0) {
						reClasses = ttre.getClasses() + "班";
					}
					String reason = "由承训单位" + tc.getName() + "管理员"
							+ user.getName() + "调整培训学科，由“" + ts.getName()
							+ classes + "”调整为“"
							+ ttre.getTrainingSubject().getName() + reClasses
							+ "”";
					atc.setReason(reason);

					try {
						this.iTeacherTrainingRecordsService.update(ttr);
						this.iAssignTeacherCheckService.add(atc);
						this.iTeacherTrainingReversalService.update(ttre);
					} catch (Exception e) {
						e.printStackTrace();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"操作失败，过程异常！\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"操作成功！\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				}

			} else {
				/*
				 * 审核不通过流程
				 * 1、判断原纪录是否审核过：未审核的直接改变审核状态；已审核不通过的，不做任何处理，直接返回，已审核通过的，不能改变状态
				 */
				if (ttre.getStatus() == 0) {
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"操作成功！\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				} else if (ttre.getStatus() == 1) {
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"操作失败，已最终审核通过！\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				} else {
					ttre.setStatus((short) 0);
					try {
						this.iTeacherTrainingReversalService.update(ttre);
					} catch (Exception e) {
						e.printStackTrace();
						sb.append("{");
						sb.append("\"Result\":\"ERROR\"");
						sb.append(",");
						sb.append("\"Message\":\"操作失败，过程异常！\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json",
								response);
						return;
					}
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"操作成功！\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json",
							response);
					return;
				}
			}

		} else {
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"操作失败，过程异常！\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

	}

	public void getTrainingAdminList() {
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
			// UserSession user = (UserSession)
			// session.getAttribute("usersession");
			String id = request.getParameter("id");

			ProjectApply pa = this.iProjectApplyService
					.get(Integer.valueOf(id));
			int projectId = pa.getProject().getId();
			int subjectId = pa.getTrainingSubject().getId();
			int collegeId = pa.getTrainingCollege().getId();

			int records = this.iProjectApplyService.getTrainingAdminCount(
					projectId, subjectId, collegeId);
			List<Object[]> li = this.iProjectApplyService.getTrainingAdminList(
					projectId, subjectId, collegeId, sortname, sorttype, start,
					limit);

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");

			sb.append("\"Records\":[");
			for (Object[] obj : li) {
				if (obj[9] == null) {
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

	public void getProject() {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		String year = this.request.getParameter("year");
		StringBuilder sb = new StringBuilder();

		TrainingAdmin ta = this.iTrainingAdminService.get(user.getId());
		// 获取用户权限
		Set<TrainingAdminAuthority> authority = ta.getTrainingAdminAuthoritys();

		if (ta.getRestrictRight() && authority != null && authority.size() > 0) {// 有权限
			Set<Project> projectSet = new HashSet<Project>();

			for (TrainingAdminAuthority taa : authority) {
				Project project = taa.getProject();
				if (!year.equals(project.getYear())) {
					continue;
				}
				projectSet.add(project);
			}
			sb.append("{\"projects\":[");
			sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
			for (Project pp : projectSet) {
				String sr = "{\"id\":" + pp.getId() + ",\"name\":\""
						+ pp.getName() + "\"},";
				sb.append(sr);
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			if (year != null && !year.equals("") && !year.equals("0")) {
				map.put("year", year);
				map.put("status", 2);
			}

			List<Object[]> lip = this.iProjectApplyService
					.getProjectByParams(map);

			sb.append("{\"projects\":[");
			sb.append("{\"id\":" + 0 + ",\"name\":\"" + "全部" + "\"},");
			for (Object[] o : lip) {
				Project p = (Project) o[1];
				String sr = "{\"id\":" + p.getId() + ",\"name\":\""
						+ p.getName() + "\"},";
				sb.append(sr);
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	/**
	 * 初始化学员全信息页
	 * 
	 * @return
	 */
	public String getTeacherInfoListInit() {

		initServlert();
		this.searchYear = this.iProjectService.getProjectYearList();
		lsttTrainingSubjects.clear();
		lstProjects.clear();
		return "teacherInfoList";
	}

	/**
	 * 获取学员全信息列表 查询参数有projectId、subjectId、classId、tName or idcard(可选) 固有参数
	 * enrollType year subjectName teacherName projectName classes searchKey
	 * page 暂不考虑管理员权限问题 返回的学员信息包括成绩与是否完成测评
	 */
	public void getTeacherInfoList() {
		initServlert();

		String projectid = request.getParameter("projectName") == null ? "0"
				: request.getParameter("projectName");
		String subjectid = request.getParameter("subjectName") == null ? "0"
				: request.getParameter("subjectName");
		String classes = request.getParameter("classes") == null ? "0"
				: request.getParameter("classes");
		String teacherName = request.getParameter("teacherName") == null ? ""
				: request.getParameter("teacherName");

		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		UserSession us = (UserSession) session.getAttribute("usersession");
		int offset = (start - 1) * DictionyMap.maxPageSize;
		TrainingAdmin ta = this.iTrainingAdminService.get(us.getId());
		TrainingCollege tc = ta.getTrainingCollege();
		List lidata = new ArrayList();
		int records = 0;
		int totalPage = 0;
		Set<TrainingAdminAuthority> taaSet = ta.getTrainingAdminAuthoritys();
		if (ta.getRestrictRight() && taaSet != null && taaSet.size() > 0) {
			if (Integer.parseInt(projectid) > 0
					|| Integer.parseInt(subjectid) > 0
					|| Integer.parseInt(classes) > 0 || !"".equals(teacherName)) {
				Map<String, Object> param = new HashMap<String, Object>();
				if (Integer.parseInt(projectid) > 0) {
					param.put("projectId", projectid);
				}
				if (Integer.parseInt(subjectid) > 0) {
					param.put("subjectId", subjectid);
				}
				if (Integer.parseInt(classes) > 0) {
					param.put("classes", classes);
				}
				if (!"".equals(teacherName)) {
					if (isNumeric(teacherName)) {
						param.put("teacherIdCard", teacherName);
					} else {
						param.put("teacherName", teacherName);
					}
				}
				param.put("trainCollegeId", tc.getId());

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

				records = this.iTeacherTrainingRecordsService
						.getTeacherRecordsForSubject(sortParams, param);
				totalPage = (int) Math.ceil((float) records
						/ DictionyMap.maxPageSize);
				lidata = this.iTeacherTrainingRecordsService
						.getTeacherRecordsForSubject(offset,
								DictionyMap.maxPageSize, sortParams, param);
			} else {
				for (TrainingAdminAuthority taa : taaSet) {
					Project project = taa.getProject();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("projectId", project.getId());
					if (taa.getTrainingSubject() != null) {
						params.put("subjectId", taa.getTrainingSubject()
								.getId());
					}
					if (taa.getClasses() != null && taa.getClasses() > 0) {
						params.put("classes", taa.getClasses());
					}

					params.put("trainCollegeId", tc.getId());
					String sort = "id_desc";
					String[] sortArr = sort.split("_");
					Map<String, Object> sortParams = new HashMap<String, Object>();
					sortParams.put(sortArr[0], sortArr[1]);

					records += this.iTeacherTrainingRecordsService
							.getTeacherRecordsForSubject(sortParams, params);
					List lst = this.iTeacherTrainingRecordsService
							.getTeacherRecordsForSubject(0, records,
									sortParams, params);
					lidata.addAll(lst);
				}
				totalPage = (int) Math.ceil((float) records
						/ DictionyMap.maxPageSize);
			}

		} else {// 全部权限
			Map<String, Object> param = new HashMap<String, Object>();
			if (Integer.parseInt(projectid) > 0) {
				param.put("projectId", projectid);
			}
			if (Integer.parseInt(subjectid) > 0) {
				param.put("subjectId", subjectid);
			}
			if (Integer.parseInt(classes) > 0) {
				param.put("classes", classes);
			}
			if (!"".equals(teacherName)) {
				if (isNumeric(teacherName)) {
					param.put("teacherIdCard", teacherName);
				} else {
					param.put("teacherName", teacherName);
				}
			}
			param.put("trainCollegeId", tc.getId());

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

			records = this.iTeacherTrainingRecordsService
					.getTeacherRecordsForSubject(sortParams, param);
			totalPage = (int) Math.ceil((float) records
					/ DictionyMap.maxPageSize);

			lidata = this.iTeacherTrainingRecordsService
					.getTeacherRecordsForSubject(offset,
							DictionyMap.maxPageSize, sortParams, param);
		}

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
			Teacher th = (Teacher) obj[2];

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

			if (us.getOrganization() == ttRecord.getOrganization().getId()
					.intValue()
					&& us.getId().intValue() == ttRecord.getCreator()
							.intValue()) {
				// 是当前组织架构添加
				sb.append("\"add\":" + true + ",");
			} else {
				// 不是当前组织架构添加
				sb.append("\"add\":" + false + ",");
			}

			sb.append("\"projectName\":\""
					+ ttRecord.getProject().getShortname() + "\",");
			sb.append("\"subjectName\":\""
					+ ttRecord.getTrainingSubject().getName() + "\",");
			sb.append("\"trainingName\":\""
					+ ttRecord.getTrainingCollege().getName() + "\",");
			sb.append("\"schoolName\":\"" + th.getOrganization().getName()
					+ "\",");

			sb.append("\"idcard\":\"" + th.getIdcard() + "\",");
			sb.append("\"name\":\"" + th.getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");

			sb.append("\"sex\":\"" + th.getSex() + "\",");
			sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "男" : "女") + "\",");
			sb.append("\"age\":\""
					+ String.valueOf(Utlity.getAge(th.getBirthday())) + "\",");
			sb.append("\"ethnic\":\"" + th.getEthnic().getName() + "\",");
			sb.append("\"politics\":\"" + th.getPolitics().getName() + "\",");

			sb.append("\"teachAge\":\""
					+ String.valueOf(Utlity.getAge(th.getTeachingAge()))
					+ "\",");
			sb.append("\"jobDuty\":\"" + th.getJobDuty().getName() + "\",");
			sb.append("\"jobTitle\":\"" + th.getJobTitle().getName() + "\",");
			sb.append("\"muLanguage\":\"" + (th.getMultiLanguage() ? "是" : "否")
					+ "\",");
			sb.append("\"authorized\":\""
					+ (th.getAuthorized() == 1 ? "在编" : "非编") + "\",");
			if (th.getStatus() == 1) {
				sb.append("\"status\":\"在职\",");
			} else if (th.getStatus() == 2) {
				sb.append("\"status\":\"离职\",");
			} else {
				sb.append("\"status\":\"转出\",");
			}

			for (TeachingLanguage tl : th.getTeachingLanguages()) {
				if (tl.getIsprime()) {
					sb.append("\"mainLanguage\":\""
							+ tl.getLanguage().getName() + "\",");
					break;
				}
			}

			for (TeachingGrade tg : th.getTeachingGrades()) {
				if (tg.getIsprime()) {
					sb.append("\"mainGrade\":\"" + tg.getGrade().getName()
							+ "\",");
					break;
				}
			}

			for (TeachingSubject ts : th.getTeachingSubjects()) {
				if (ts.getIsprime()) {
					sb.append("\"mainSubject\":\"" + ts.getSubject().getName()
							+ "\",");
					break;
				}
			}

			sb.append("\"area\":\"" + getAreaNaviString(th.getArea()) + "\",");

			sb.append("\"edubackground\":\""
					+ th.getEductionBackground().getName() + "\",");
			sb.append("\"chineseLanguageLevel\":\""
					+ th.getChineseLanguageLevel().getName() + "\",");
			sb.append("\"mobile\":\"" + th.getMobile() + "\",");

			String trainingStatus = "";
			if (ttRecord.getTrainingStatus() == 0) {
				trainingStatus = "学员异动";
			} else if (ttRecord.getTrainingStatus() == 1) {
				trainingStatus = "未报到";
			} else if (ttRecord.getTrainingStatus() == 2) {
				trainingStatus = "已报到";
			} else if (ttRecord.getTrainingStatus() == 3) {
				trainingStatus = "培训合格";
			} else if (ttRecord.getTrainingStatus() == 5) {
				trainingStatus = "培训优秀";
			} else if (ttRecord.getTrainingStatus() == 6) {
				trainingStatus = "培训良好";
			} else {
				trainingStatus = "培训不合格";
			}
			sb.append("\"trainingStatus\":\"" + trainingStatus + "\",");

			Float score = ttRecord.getTrainingScore() == null ? 0 : ttRecord
					.getTrainingScore();
			sb.append("\"trainingScore\":" + score + ",");

			Integer hour = ttRecord.getTrainingClasshour() == null ? 0
					: ttRecord.getTrainingClasshour();
			Integer onHour = ttRecord.getTrainingOnlineHour() == null ? 0
					: ttRecord.getTrainingOnlineHour();
			Integer hours = hour + onHour;

			sb.append("\"trainingHour\":" + hours + ",");

			String reason = ttRecord.getTrainingReason() == null ? "无"
					: ttRecord.getTrainingReason();
			sb.append("\"trainingReason\":\"" + reason + "\",");

			String certificate = ttRecord.getCertificate() == null ? "无"
					: ttRecord.getCertificate();
			sb.append("\"certificate\":\"" + certificate + "\",");

			// 是否完成学前测评
			Calendar a = Calendar.getInstance();
			int year = a.get(Calendar.YEAR);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("teacher", th.getId());
			map.put("year", year);

			int count = this.getiHsdTestService().getHsdTestCount(map);
			if (count > 0) {
				sb.append("\"isPeTest\":\"是\",");
			} else {
				sb.append("\"isPeTest\":\"否\",");
			}

			// 备注异动信息
			String reversalStr = "无";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projectId", ttRecord.getProject().getId());
			params.put("subjectId", ttRecord.getTrainingSubject().getId());
			params.put("trainCollege", ttRecord.getTrainingCollege().getId());
			params.put("teacher", ttRecord.getTeacher().getId());
			params.put("status", 1);

			// 排序
			String sort = "id_desc";
			String[] sortArr = sort.split("_");
			Map<String, Object> sortParams = new HashMap<String, Object>();
			sortParams.put(sortArr[0], sortArr[1]);

			int totalcount = this.iTeacherTrainingReversalService
					.getRecordsListByParams(params, sortParams);
			if (totalcount > 0) {
				List<TeacherTrainingReversal> reversals = this.iTeacherTrainingReversalService
						.getRecordsListByParams(params, sortParams, 0,
								totalcount);
				TeacherTrainingReversal ttre = reversals.get(0);
				TrainingSubject tss = this.iTrainingSubjectService.get(ttre
						.getSubject());
				if (ttre.getOldClasses() > 0) {
					reversalStr = "由培训学科“" + tss.getName() + ">>"
							+ ttre.getOldClasses() + "班”转到本学科本班级";
				} else {
					reversalStr = "由培训学科“" + tss.getName() + "”转到本学科";
				}
			}
			sb.append("\"reversalStr\":\"" + reversalStr + "\",");

			sb.append("\"teacherStatus\":\"" + ttRecord.getReplaceStatus()
					+ "\"");
			sb.append("},");
			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

		// /**
		// * 1.传入参数 ProjectId 和 SubjectId 、organizationId,status
		// */
		//
		// initServlert();
		//
		// UserSession us = (UserSession) session.getAttribute("usersession");
		// @SuppressWarnings("unchecked")
		// List<ProjectType> lityps = (List<ProjectType>)
		// session.getAttribute("lstProjectType");
		//
		// // 分页
		// String ist = (String) request.getParameter("page");
		// if (ist == null || ist.equals("") || ist.equals("NaN")) {
		// ist = "1";
		// }
		// int start = Integer.parseInt(ist);
		//
		// // 其他搜索条件
		// String projectid = request.getParameter("projectName");
		// projectid = projectid == null ? "0" : projectid;
		//
		// String organizationid = request.getParameter("organizationName");
		// organizationid = organizationid == null ? "0" : organizationid;
		//
		// String subjectid = request.getParameter("subjectName");
		// subjectid = subjectid == null ? "0" : subjectid;
		//
		// String trainingUnit = request.getParameter("trainingUnit");
		// trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		//
		// String teacherName = request.getParameter("teacherName");
		//
		// String status = request.getParameter("status");
		// status = status == null ? "-1" : status;
		//
		// int isadmin = 0;
		// if (us.getOrganizationLevel() !=
		// DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
		//
		// if (status.equals("1")) {
		// // 未审核
		// isadmin = 1;
		// status = us.getOrganizationLevel() + "";
		// } else if (status.equals("-2")) {
		// isadmin = 2;
		// status = us.getOrganizationLevel() + "";
		// }
		// } else {
		// if (status.equals("-2")) {
		// status = "2";
		// }
		// }
		//
		// String searchKey = request.getParameter("searchKey");
		// if (searchKey != null && searchKey.trim().length() > 0) {
		// String[] tmpKey = searchKey.split("-");
		// if (tmpKey.length >= 3) {
		// String tmpProjectid = tmpKey[0];
		// String tmpSubjectid = tmpKey[1];
		// String tmpTrainingUnit = tmpKey[2];
		// if (projectid.equals("0")) {
		// projectid = tmpProjectid;
		// }
		// if (subjectid.equals("0")) {
		// subjectid = tmpSubjectid;
		// }
		// if (trainingUnit.equals("0")) {
		// trainingUnit = tmpTrainingUnit;
		// }
		// }
		// }
		//
		// Organization oz =
		// this.iOrganization.get(Integer.valueOf(organizationid));
		// if (organizationid.equals("0")) {
		// oz = this.iOrganization.get(us.getOrganization());
		// }
		//
		// // 搜索出条数
		// int records =
		// this.iTeacherTrainingRecords.getAduTeacherCount(teacherName,
		// Integer.valueOf(projectid), Integer.valueOf(subjectid),
		// Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz,
		// lityps ,DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString());
		//
		// int totalPage = (int) Math.ceil((float) records /
		// DictionyMap.maxPageSize);
		// int offset = (start - 1) * DictionyMap.maxPageSize;
		//
		// List lidata = this.iTeacherTrainingRecords.getAduTeacher(teacherName,
		// Integer.valueOf(projectid), Integer.valueOf(subjectid),
		// Integer.valueOf(trainingUnit), isadmin, Integer.valueOf(status), oz,
		// lityps, offset, DictionyMap.maxPageSize,
		// DictionyMap.PROJECT_ENROLL_TYPE_PLAN.toString());
		//
		// if (lidata == null || lidata.size() == 0) {
		// StringBuilder sb = new StringBuilder();
		// sb.append("{");
		// sb.append("\"status\":\"ERROR\",");
		// sb.append("\"message\":\"没有搜索到数据!\"");
		// sb.append("}");
		// Utlity.ResponseWrite(sb.toString(), "application/json", response);
		// return;
		// }
		//
		// StringBuilder sbPack = new StringBuilder();
		// sbPack.append("{");
		// sbPack.append("\"status\":\"OK\"");
		//
		// sbPack.append(",");
		// sbPack.append("\"page\":{");
		// sbPack.append("\"currentPage\":" + start + ",");
		// sbPack.append("\"totalPage\":" + totalPage);
		// sbPack.append("}");
		//
		// sbPack.append(",");
		// sbPack.append("\"records\":[");
		//
		// for (int i = 0; i < lidata.size(); i++) {
		//
		// Object[] obj = (Object[]) lidata.get(i);
		//
		// TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
		// Teacher th = (Teacher) obj[1];
		//
		// // TeachingSubject ts = (TeachingSubject) obj[2];
		// // TeachingLanguage tl = (TeachingLanguage) obj[3];
		// // TeachingGrade tg = (TeachingGrade) obj[4];
		//
		// // int projectId = ttRecord.getProject().getId();
		// // int subjectId = ttRecord.getTrainingSubject().getId();
		// // int tcid = ttRecord.getTrainingCollege().getId();
		// // Object[] params = new Object[] { projectId, subjectId, tcid };
		// // int level
		// //
		// =this.iAssignTeacherTaskService.getAssignTeacherTaskLevel(params,us);
		//
		// StringBuilder sb = new StringBuilder();
		// sb.append("{");
		//
		// sb.append("\"id\":\"" + ttRecord.getId() + "\",");
		//
		// if (us.getOrganizationLevel() ==
		// DictionyMap.ORGANIZATION_LEVEL_PROVINCE) {
		// // 是最高级别审核
		// sb.append("\"level\":" + true + ",");
		// } else {
		// // 不是最高级别审核
		// sb.append("\"level\":" + false + ",");
		// }
		//
		// if (us.getOrganization() ==
		// ttRecord.getOrganization().getId().intValue() &&
		// us.getId().intValue() == ttRecord.getCreator().intValue()) {
		// // 是当前组织架构添加
		// sb.append("\"add\":" + true + ",");
		// } else {
		// // 不是当前组织架构添加
		// sb.append("\"add\":" + false + ",");
		// }
		//
		// sb.append("\"projectName\":\"" + ttRecord.getProject().getShortname()
		// + "\",");
		// sb.append("\"subjectName\":\"" +
		// ttRecord.getTrainingSubject().getName() + "\",");
		// sb.append("\"trainingName\":\"" +
		// ttRecord.getTrainingCollege().getName() + "\",");
		// sb.append("\"schoolName\":\"" + th.getOrganization().getName() +
		// "\",");
		//
		// sb.append("\"idcard\":\"" + th.getIdcard() + "\",");
		// sb.append("\"name\":\"" + th.getName() + "\",");
		// sb.append("\"mobile\":\"" + th.getMobile() + "\",");
		//
		// sb.append("\"sex\":\"" + th.getSex() + "\",");
		// sb.append("\"sexName\":\"" + (th.getSex() == 1 ? "男" : "女") + "\",");
		// sb.append("\"age\":\"" +
		// String.valueOf(Utlity.getAge(th.getBirthday())) + "\",");
		// sb.append("\"ethnic\":\"" + th.getEthnic().getName() + "\",");
		// sb.append("\"politics\":\"" + th.getPolitics().getName() + "\",");
		//
		// sb.append("\"teachAge\":\"" +
		// String.valueOf(Utlity.getAge(th.getTeachingAge())) + "\",");
		// sb.append("\"jobDuty\":\"" + th.getJobDuty().getName() + "\",");
		// sb.append("\"jobTitle\":\"" + th.getJobTitle().getName() + "\",");
		// sb.append("\"muLanguage\":\"" + (th.getMultiLanguage() ? "是" : "否") +
		// "\",");
		//
		// // 主要教学语言
		// // sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() +
		// // "\",");
		//
		// // 获取主要教学学段
		// // sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");
		//
		// // 获取主要教学学科
		// // sb.append("\"mainSubject\":\"" + ts.getSubject().getName() +
		// // "\",");
		//
		// for (TeachingLanguage tl : th.getTeachingLanguages()) {
		// if (tl.getIsprime()) {
		// sb.append("\"mainLanguage\":\"" + tl.getLanguage().getName() +
		// "\",");
		// break;
		// }
		// }
		//
		// for (TeachingGrade tg : th.getTeachingGrades()) {
		// if (tg.getIsprime()) {
		// sb.append("\"mainGrade\":\"" + tg.getGrade().getName() + "\",");
		// break;
		// }
		// }
		//
		// for (TeachingSubject ts : th.getTeachingSubjects()) {
		// if (ts.getIsprime()) {
		// sb.append("\"mainSubject\":\"" + ts.getSubject().getName() + "\",");
		// break;
		// }
		// }
		//
		// sb.append("\"area\":\"" + getAreaNaviString(th.getArea()) + "\",");
		//
		// sb.append("\"edubackground\":\"" +
		// th.getEductionBackground().getName() + "\",");
		// sb.append("\"chineseLanguageLevel\":\"" +
		// th.getChineseLanguageLevel().getName() + "\",");
		// sb.append("\"mobile\":\"" + th.getMobile() + "\",");
		//
		// ProjectAdmin pa =
		// this.iProjectAdminService.get(ttRecord.getCreator());
		// if (pa != null) {
		// sb.append("\"creator\":\"" + pa.getName() + "\",");
		// } else {
		// sb.append("\"creator\":\"" + "" + "\",");
		// }
		//
		// int finalStatus = (int) ttRecord.getFinalStatus();
		//
		// if (finalStatus == DictionyMap.TEACHER_TRAINING_RECORDS_CHECKPASS) {
		// sb.append("\"status\":\"" + "已审核" + "\",");
		// } else if (finalStatus ==
		// DictionyMap.TEACHER_TRAINING_RECORDS_UNCHECK) {
		//
		// // 没有最终审核
		// // 判断中间审核状态
		// int checkStatus = (int) ttRecord.getCheckStatus();
		// if (us.getOrganizationLevel() > checkStatus) {
		// // 当前人物 的组织架构级别大于 审核状态
		// // 说明 自己已经审核通过
		// sb.append("\"status\":\"" + "通过" + "\",");
		// } else {
		// sb.append("\"status\":\"" + "未审核" + "\",");
		// }
		// } else if (finalStatus ==
		// DictionyMap.TEACHER_TRAINING_RECORDS_CHECKNOPASS) {
		// sb.append("\"status\":\"" + "未通过" + "\",");
		// }
		//
		// sb.append("\"teacherStatus\":\""+ttRecord.getReplaceStatus()+"\"");
		// sb.append("},");
		// sbPack.append(sb.toString());
		//
		// }
		//
		// if (lidata != null && lidata.size() > 0) {
		// sbPack.delete(sbPack.length() - 1, sbPack.length());
		// }
		// sbPack.append("]}");
		//
		// Utlity.ResponseWrite(sbPack.toString(), "application/json",
		// response);
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 导出学员全信息
	 * 
	 * @throws IOException
	 */
	public void outputTeacherInfoList() throws IOException {
		initServlert();
		int classes = 0;
		if (request.getParameter("classes") != null
				&& !request.getParameter("classes").equals("")) {
			classes = Integer.valueOf(request.getParameter("classes"));
		}

		String projectid = request.getParameter("projectName") == null ? "0"
				: request.getParameter("projectName");
		String subjectid = request.getParameter("subjectName") == null ? "0"
				: request.getParameter("subjectName");

		UserSession us = (UserSession) session.getAttribute("usersession");
		TrainingAdmin ta = this.iTrainingAdminService.get(us.getId());
		TrainingCollege tc = ta.getTrainingCollege();

		int offset = 0;
		int number = iTeacherTrainingRecordsService.getAduTeacherCountByIdcard(
				Integer.parseInt(projectid), Integer.parseInt(subjectid),
				tc.getId(), classes, "");
		List li = iTeacherTrainingRecordsService.getAduTeacherByIdcard(
				Integer.parseInt(projectid), Integer.parseInt(subjectid),
				tc.getId(), classes, "", offset, number);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "学员名单");
		XSSFRow row = s.createRow(0);
		String title[] = { "项目名称", "培训学科", "承训院校", "培训开始时间", "培训结束时间", "学员姓名",
				"所属地区", "所在学校", "学校所在区域", "学校类别", "报到状态", "结业状态（合格/不合格）", "证书编号", "不合格原因",
				"成绩（不大于总分）", "总分", "身份证号",
				"年龄", "性别", "民族", "政治面貌", "学历", "教龄", "编制", "状态", "职务", "职称", "最高学历", "毕业院校", "所学专业",
				"汉语水平", "主要教学学科", "主要教学学段", "主要教学语言", "联系电话", "邮箱", "班级",
				"审核时间", "备注" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
		for (int i = 0; i < li.size(); i++) {

			Object[] obj = (Object[]) li.get(i);
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			projectApply = this.iProjectApplyService
					.getByTrainingRecord(ttRecord);
			Teacher th = (Teacher) obj[1];
			Project pro = ttRecord.getProject();
			t++;
			row = s.createRow(t);
			String projectName = pro.getName();
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
			String tDuty = "无";
			if(th.getJobDuty() != null){
				tDuty = th.getJobDuty().getName();
			}
			String tTitle = "无";
			if(th.getJobTitle() != null){
				tTitle = th.getJobTitle().getName();
			}
			
			String tMobile = th.getMobile() == null ? "":th.getMobile();
			String tEmail = th.getEmail() == null ? "":th.getEmail();
//			String tDuty = th.getJobDuty().getName();
//			String tTitle = th.getJobTitle().getName();
//			String tMobile = th.getMobile();
//			String tEmail = th.getEmail();
			String tStatus = "";
			if (th.getStatus() == 1) {
				tStatus = "在职";
			} else if (th.getStatus() == 2) {
				tStatus = "离职";
			} else if (th.getStatus() == 4) {
				tStatus = "退休";
			} else if (th.getStatus() == 5) {
				tStatus = "死亡";
			} else {
				tStatus = "转出";
			}
			String authorized = th.getAuthorized() == 1 ? "在编" : "非编";
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
			String startTime = "待定";
			if (projectApply.getTrainingStarttime() != null) {
				startTime = projectApply.getTrainingStarttime().toString();
				startTime = startTime.substring(0, 10);
			}
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
			} else if (trainingStatus == 5) {
				endStatus = "优秀";
			} else if (trainingStatus == 6) {
				endStatus = "良好";
			}
			String tcertificate = ttRecord.getCertificate();
			String reason = ttRecord.getTrainingReason();
			String tmaxScore = "0";
			if (pro.getPsqByEvaluationTeacherPsq() != null) {
				tmaxScore = iProjectApplyService.getTeacherPsqMaxScore(pro
						.getId()) + "";
			}
			String realScore = "0";
			if (ttRecord.getTrainingScore() != null) {
				realScore = ttRecord.getTrainingScore().intValue() + "";
			}
			
			// 添加备注 备注信息为教师替换信息
			String remarks = "";
			if (ttRecord.getReplaceStatus() == 1) {
				Teacher tt = this.iTeacherService.get(ttRecord
						.getReplaceTeacher());
				remarks += "由教师 " + tt.getName() + " 替换";
			} else if (ttRecord.getReplaceStatus() == 2) {
				Teacher tt = this.iTeacherService.get(ttRecord
						.getReplaceTeacher());
				remarks += "替换了教师  " + tt.getName();
			}

			// 添加审核时间信息
			String audTime = "";
			List<AssignTeacherCheck> setTtr = this.iAssignTeacherCheckService
					.getAssignTeacherChecksByTeacherRecordId(ttRecord.getId());
			if (setTtr != null && setTtr.size() > 0) {
				AssignTeacherCheck atc = setTtr.get(0);
				audTime = Utlity.timeSpanToString(atc.getChecktime());
			}
			
			//2016年12月26日新增导出信息：学校所在区域、学校类别、最高学历、毕业院校、所学专业
			String ftype = th.getOrganization().getFtype() == null ? "无" : th.getOrganization().getFtype();
			String type = th.getOrganization().getType() == null ? "无" : th.getOrganization().getType();
			String education = "";
			if(th.getEductionBackground() != null){
				education = th.getEductionBackground().getName();
			}
			String graduation = th.getGraduation() == null ? "未填" : th.getGraduation();
			String major = th.getMajor() == null ? "未填" : th.getMajor();

			String tInfo[] = { projectName, subjectName, trainingName,
					startTime, endTime, tName, tArea, schoolName,ftype,type, startStatus,
					endStatus, tcertificate, reason, realScore, tmaxScore,
					tIdcard, tAge, tSex, tEthnic,
					tPolitics, tEductionBackground, tTeachAge, authorized,
					tStatus, tDuty, tTitle,education,graduation,major, tChineseLanguageLevel,
					tMainSubject, tMainGrade, tMainLanguage, tMobile, tEmail,
					tClass + "", audTime, remarks };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=studentInfoList.xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}

	/**
	 * 下载全部学员照片
	 * 1、照片文件命名方式：姓名+身份证号
	 * 2、打包压缩zip
	 * @throws Exception
	 */
	public void downloadFiles() throws Exception{
		initServlert();
		String absolutePath = ServletActionContext.getServletContext().getRealPath(""); 
//		absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
		projectApply = null;
		initServlert();
		// UserSession user = (UserSession) session.getAttribute("usersession");

		if (request.getParameterMap().containsKey("id")) {
			projectApply = iProjectApplyService.get(Integer.parseInt(request
					.getParameter("id")));
		}

		String sort = "r.id desc";
		
		List<Map<String, String>> fileTeacherList = new ArrayList<Map<String, String>>();
		try {
			
			List<Object[]> listTeacherInfo = iTeacherService
					.getListForPageBySql(projectApply.getProject().getId(),
							projectApply.getTrainingSubject().getId(),
							projectApply.getTrainingCollege().getId(), 0,
							-1, -1, sort);
			if (listTeacherInfo != null && listTeacherInfo.size() > 0) {
				for (Object[] attr : listTeacherInfo) {
					Map<String, String> teaMap = new HashMap<String, String>();
//					String teaName = String.valueOf(attr[0]);
					String idcard = String.valueOf(attr[1]);
					String path = String.valueOf(attr[2]);
					if(attr[2] != null && !Utlity.checkStringNull(path)){
//						fileTeacherList.add(tea);
						teaMap.put("idcard", idcard);
						teaMap.put("path", path);
						fileTeacherList.add(teaMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		String fileName = "photos" + (int)(Math.random() * 1000);
		try {
			if(fileTeacherList.size()>0){
				
				for(Map<String, String> teacher : fileTeacherList){
					String oldPath = absolutePath + teacher.get("path");
					String type = oldPath.substring(oldPath.lastIndexOf("."));
					String newPath = absolutePath + "/upload/"+ fileName + "/" + teacher.get("idcard")+type;
					File newFile = new File(absolutePath + "/upload/"+ fileName);
					if (!newFile.exists()) {
						newFile.mkdirs();
					}
					inputStream = new BufferedInputStream(new FileInputStream(oldPath));
					outputStream = new BufferedOutputStream(new FileOutputStream(newPath));
					Streams.copy(inputStream, outputStream, true);
					inputStream.close();
					outputStream.flush();
					outputStream.close();
				}
				String result = DownloadZipFiles.zip(absolutePath + "/upload/" + fileName + ".zip", absolutePath + "/upload/" + fileName);
				if(result.indexOf("error") > -1 || result.indexOf("warn") > -1){
					DownloadZipFiles.deleteDir(new File(absolutePath + "/upload/" + fileName));
				}
				File zipFile = new File(absolutePath + "/upload/" + fileName + ".zip");
		        response = DownloadZipFiles.downloadZip(zipFile,response);
				DownloadZipFiles.deleteDir(zipFile);
				DownloadZipFiles.deleteDir(new File(absolutePath + "/upload/" + fileName));
			}else{
//				String fileName = "photos" + (int)(Math.random() * 1000);
				File newFile = new File(absolutePath + "/upload/" + fileName);
				newFile.mkdirs();
				File zipFile = new File(absolutePath + "/upload/" + fileName + ".zip");
				FileOutputStream fous = new FileOutputStream(zipFile); 
				ZipOutputStream zipOut  = new ZipOutputStream(fous);
				DownloadZipFiles.zip(zipOut , new File(absolutePath + "/upload/" + fileName), "");
		        zipOut.close();
		        fous.close();
				 
		        response = DownloadZipFiles.downloadZip(zipFile,response);
				DownloadZipFiles.deleteDir(zipFile);
				DownloadZipFiles.deleteDir(new File(absolutePath + "/upload/" + fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			File newFile = new File(absolutePath + "/upload/" + fileName);
			if(!newFile.exists()){
				newFile.mkdirs();
			}
			File zipFile = new File(absolutePath + "/upload/" + fileName + ".zip");
			FileOutputStream fous = new FileOutputStream(zipFile); 
			ZipOutputStream zipOut  = new ZipOutputStream(fous);
			DownloadZipFiles.zip(zipOut , new File(absolutePath + "/upload/" + fileName), "");
	        zipOut.close();
	        fous.close();
			 
	        response = DownloadZipFiles.downloadZip(zipFile,response);
			DownloadZipFiles.deleteDir(zipFile);
			DownloadZipFiles.deleteDir(newFile);
		} finally {
			if(inputStream != null){
				inputStream.close();
			}
			if(outputStream != null){
				outputStream.close();
			}
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

	public IHsdtestService getiHsdTestService() {
		return iHsdTestService;
	}

	public void setiHsdTestService(IHsdtestService iHsdTestService) {
		this.iHsdTestService = iHsdTestService;
	}

	public IPsqService getiPsqService() {
		return iPsqService;
	}

	public void setiPsqService(IPsqService iPsqService) {
		this.iPsqService = iPsqService;
	}

	public ITeacherTrainingReversalService getiTeacherTrainingReversalService() {
		return iTeacherTrainingReversalService;
	}

	public void setiTeacherTrainingReversalService(
			ITeacherTrainingReversalService iTeacherTrainingReversalService) {
		this.iTeacherTrainingReversalService = iTeacherTrainingReversalService;
	}

	public teacherEx getTeacherEx() {
		return teacherEx;
	}

	public void setTeacherEx(teacherEx teacherEx) {
		this.teacherEx = teacherEx;
	}

	public TrainingSubject getTrainingSubject() {
		return trainingSubject;
	}

	public void setTrainingSubject(TrainingSubject trainingSubject) {
		this.trainingSubject = trainingSubject;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public TrainingSubject getOldTrainingSubject() {
		return oldTrainingSubject;
	}

	public void setOldTrainingSubject(TrainingSubject oldTrainingSubject) {
		this.oldTrainingSubject = oldTrainingSubject;
	}

	public String getOldClasses() {
		return oldClasses;
	}

	public void setOldClasses(String oldClasses) {
		this.oldClasses = oldClasses;
	}

	public HashMap<String, String[]> getPaperHashMap() {
		return paperHashMap;
	}

	public void setPaperHashMap(HashMap<String, String[]> paperHashMap) {
		this.paperHashMap = paperHashMap;
	}

	public Psq getPsq() {
		return psq;
	}

	public void setPsq(Psq psq) {
		this.psq = psq;
	}

	public IProjectCycleService getiProjectCycleService() {
		return iProjectCycleService;
	}

	public void setiProjectCycleService(IProjectCycleService iProjectCycleService) {
		this.iProjectCycleService = iProjectCycleService;
	}

	public List<Map> getPaStudyhourList() {
		return paStudyhourList;
	}

	public void setPaStudyhourList(List<Map> paStudyhourList) {
		this.paStudyhourList = paStudyhourList;
	}

	public List<Map> getTtrStudyhourList() {
		return ttrStudyhourList;
	}

	public void setTtrStudyhourList(List<Map> ttrStudyhourList) {
		this.ttrStudyhourList = ttrStudyhourList;
	}

}
