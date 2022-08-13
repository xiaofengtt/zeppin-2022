/**
 * 
 */
package cn.zeppin.action.admin;

import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.IdentifyClasshours;
import cn.zeppin.entity.IdentifyClasshoursProjectYear;
import cn.zeppin.entity.IdentifyClasshoursSubject;
import cn.zeppin.entity.IdentifyClasshoursSubjectYear;
import cn.zeppin.entity.IdentifyStudyhour;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyWorkReport;
import cn.zeppin.entity.ProjectCollegeRange;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingAdminAuthority;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.entity.teachingSubjectEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IIdentifyClasshoursProjectYearService;
import cn.zeppin.service.IIdentifyClasshoursService;
import cn.zeppin.service.IIdentifyClasshoursSubjectService;
import cn.zeppin.service.IIdentifyClasshoursSubjectYearService;
import cn.zeppin.service.IIdentifyStudyhourService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectApplyWorkReportService;
import cn.zeppin.service.IProjectCollegeRangeService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectSubjectRangeService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;
import cn.zeppin.utility.pinyingUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author sj
 * 
 */
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class trainingUnitProjectApplyAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(AssignTeacherTaskAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectService iProjectService;
	private IProjectTypeService iProjectTypeService;

	private IAreaService iAreaService;
	private IProjectAdminService iProjectAdminService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IProjectApplyService iProjectApplyService;
	private IProjectApplyWorkReportService iProjectApplyWorkReportService;
	private ITrainingAdminService iTrainingAdminService;
	private IDocumentService iDocumentService;
	private IProjectSubjectRangeService iProjectSubjectRangeService;
	private IProjectCollegeRangeService iProjectCollegeRangeService;
	private IIdentifyClasshoursService identifyClasshoursService;
	private IIdentifyClasshoursSubjectService identifyClasshoursSubjectService;
	private IIdentifyClasshoursSubjectYearService identifyClasshoursSubjectYearService;
	private IIdentifyClasshoursProjectYearService identifyClasshoursProjectYearService;
	private IIdentifyStudyhourService identifyStudyhourService;
	
	private LinkedHashMap<Integer, String[]> projectHash;

	private List<TrainingSubject> lsttTrainingSubjects = new ArrayList<TrainingSubject>();// 教学科
	private List<teachingSubjectEx> lstteacTeachingSubjectExs = new ArrayList<>();// 教学学科ex
	private HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
	private List<fileInfo> lsFiles = new ArrayList<>();
	private List<Project> lstProjects = new ArrayList<>();

	private Project project;
	private ProjectApply projectApply;
	private String pageStatus;// 页面状态
	private String message;// 错误信息
	private String id = "";
	private Document document;
	private String collegeId;
	private List<String> searchYear;

	// private fileInfo redHeadDocument = new fileInfo();
	private HashMap<EDocumentType, fileInfo> redHead = new HashMap<>();

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @throws ParseException
	 * @category 列表
	 */
	public String initPage() throws ParseException {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		TrainingAdmin trainingAdmin = iTrainingAdminService.get(user.getId());
		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 搜索参数
		Map<String, Object> params = new HashMap<>();
		Map<String, String> sortParams = new HashMap<>();

		// 判断其他搜索字段是否存在

		// 排序
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("creattime", "DESC");
		}

		int offset = (start - 1) * DictionyMap.maxPageSize;
		Date date = new Date();
		List li = this.iProjectService.getProjectForTrainingCollege(params,
				sortParams, trainingAdmin.getTrainingCollege().getId(), date,
				offset, DictionyMap.maxPageSize);
		this.projectHash = new LinkedHashMap<Integer, String[]>();
		this.collegeId = user.getOrganization() + "";
		DecimalFormat df1 = new DecimalFormat("0.00");
		for (int i = 0; i < li.size(); i++) {
			Object[] ob = (Object[]) li.get(i);
			Project pro = (Project) ob[0];
			int id = pro.getId();
			if (!this.projectHash.containsKey(id)) {
				String[] sv = new String[30];
				sv[0] = pro.getName();
				switch (pro.getStatus()) {
				case 1:
					sv[1] = "未发布";
					break;
				case 2:
					sv[1] = "可申报";
					break;
				case 3:
					sv[1] = "完成";
					break;
				case 4:
					sv[1] = "关闭";
					break;
				}

				sv[2] = pro.getYear();

				ProjectType pt = pro.getProjectType();

				sv[3] = getNaviString(pt);
				sv[4] = pro.getShortname();
				sv[5] = getAreaNaviString(pt.getArea());
				sv[6] = Utlity.timeSpanToDateString(pro.getTimeup());
				sv[7] = df1.format(pro.getFunds());
				sv[8] = pro.getNumber().toString();
				sv[9] = pro.getSubjectMax().toString();

				sv[10] = pro.getRestrictCollege() ? "邀标" : "不限";
				sv[11] = pro.getRestrictSubject() ? "限制" : "不限";
				if (pro.getTraintype() == 1) {
					sv[12] = "集中面授（同步在线）";
				} else if (pro.getTraintype() == 2) {
					sv[12] = "远程培训";
				} else {
					sv[12] = "混合培训方式";
				}
				sv[13] = pt.getProjectLevel().getName();
				sv[14] = pro.getDocument() == null ? "未上传" : "已上传";
				sv[15] = pro.getPsqByProjectJudgePsq() == null ? "未设定" : "已设定";
				ProjectAdmin pa = this.iProjectAdminService.get(pro
						.getCreator());
				sv[16] = pa.getName();
				sv[17] = Utlity.timeSpanToDateString(pro.getCreattime());
				if (sv[14].equals("已上传")) {
					sv[18] = pro.getDocument().getResourcePath();
					sv[19] = pro.getDocument().getTitle();
				}
				sv[20] = pro.getEnrollType().toString();
				if (pro.getTraintype() == 1 || pro.getTraintype() == 3) {
//					sv[21] = pro.getFundsType() == 1 ? " 元/人/天" : " 元/人/年";
					if(pro.getFundsType() == 1) {
						sv[21] = " 元/人/天";
					} else if (pro.getFundsType() == 2) {
						sv[21] = " 元/人/年";
					} else if (pro.getFundsType() == 4) {
						sv[21] = " 元/县";
					} else if (pro.getFundsType() == 5) {
						sv[21] = " 元/校";
					} else {
						sv[21] = " 元/人/学时";
					}
				} else {
					sv[21] = " 元/人/学时";
				}
				sv[22] = pro.getRedHeadDocument() == null ? "未上传" : "已上传";
				if (sv[22].equals("已上传")) {
					sv[23] = pro.getRedHeadDocument().getResourcePath();
					sv[24] = pro.getRedHeadDocument().getTitle();
				}
				this.projectHash.put(pro.getId(), sv);
			}
		}

		return "init";
	}

	// 项目类型
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + " > " + pt.getName();
		}
	}

	public void getPageJson() {
		initServlert();

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 判断其他搜索字段是否存在
		Map<String, Object> params = new HashMap<>();

		int records = this.iProjectService
				.getProjectCountByParams(params, null);
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + total);
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

	public String historyInit() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		String year = request.getParameter("year");
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
		return "historyInit";
	}

	public void history() {
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
			// 获取承训单位所有的项目
			List<ProjectApply> lstProjectApplies = new ArrayList<>();
			if (request.getParameterMap().containsKey("projectName")
					&& request.getParameterMap().containsKey("subjectName")) {
				int projectId = Integer.parseInt(request
						.getParameter("projectName"));
				int subjectId = Integer.parseInt(request
						.getParameter("subjectName"));
				lstProjectApplies = iProjectApplyService.getlistForPojectApply(
						iTrainingAdminService.get(user.getId())
								.getTrainingCollege().getId(), projectId,
						subjectId, (int) Math.ceil(start / limit), limit, sort);
			} else {
				lstProjectApplies = iProjectApplyService.getlistForPojectApply(
						iTrainingAdminService.get(user.getId())
								.getTrainingCollege().getId(),
						(int) Math.ceil(start / limit), limit, sort);

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
					Project p = projectApply.getProject();
					TrainingSubject ts = projectApply.getTrainingSubject();
					TrainingCollege tc = projectApply.getTrainingCollege();
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + projectApply.getId());
					sbstr.append(",");
					sbstr.append("\"project\":\""
							+ projectApply.getProject().getName() + "\"");
					sbstr.append(",");
					sbstr.append("\"projectCycle\":\""
							+ projectApply.getProjectCycle().getId() + "\"");
					sbstr.append(",");
					sbstr.append("\"trainingSubject\":\""
							+ projectApply.getTrainingSubject().getName()
							+ "\"");
					sbstr.append(",");
					if (projectApply.getTrainingStarttime() != null) {
						sbstr.append("\"trainingStarttime\":\"" + dataTimeConvertUtility.timespanToString(projectApply.getTrainingStarttime(), "yyyy-MM-dd") + "\"");
						sbstr.append(",");
					} else {
						sbstr.append("\"trainingStarttime\":\"待定\"");
						sbstr.append(",");
					}
					if (projectApply.getTrainingEndtime() != null) {
						sbstr.append("\"trainingEndtime\":\"" + dataTimeConvertUtility.timespanToString(projectApply.getTrainingEndtime(), "yyyy-MM-dd") + "\"");
						sbstr.append(",");
					}

					// 分配总数
					sbstr.append("\"approveNumber\":\""
							+ projectApply.getApproveNumber() + "\"");
					sbstr.append(",");
					//4个学时
					sbstr.append("\"studyhour\":").append(projectApply.getStudyhour());
					sbstr.append(",");
					
					String status = "未审核";
					if (projectApply.getStatus() == 2) {
						status = "已审核";

					} else if (projectApply.getStatus() == 0) {
						status = "未通过";
					}
					// 申报人信息
					String creator = "";// 申报人
					String creatorPhone = "";// 申报人联系方式
					if (projectApply.getType() == 1) {
						TrainingAdmin taa = this.iTrainingAdminService
								.get(projectApply.getCreator());
						creator = taa.getName();
						creatorPhone = taa.getMobile();
					} else if (projectApply.getType() == 2) {
						ProjectAdmin paa = this.iProjectAdminService
								.get(projectApply.getCreator());
						creator = paa.getName();
						creatorPhone = paa.getMobile();
					}

					sbstr.append("\"trainAdminName\":\"" + creator + "\"");
					sbstr.append(",");
					sbstr.append("\"trainAdminPhone\":\"" + creatorPhone + "\"");
					sbstr.append(",");

					// 专家评分
					String score = "";
					if (p.getPsqByProjectJudgePsq() != null) {
						int projectId = p.getId();
						int subjectId = ts.getId();
						int trainingId = tc.getId();
						int psqId = p.getPsqByProjectJudgePsq().getId();
						int viewNum = iProjectApplyService.getExpertViewCount(
								psqId, projectId, subjectId, trainingId);
						if (viewNum != 0) {
							int totalScore = iProjectApplyService
									.getExpertViewTotalScore(psqId, projectId,
											subjectId, trainingId);
							int maxScore = iProjectApplyService
									.getExpertPsqMaxScore(projectId);
							float scores = (float) (Math
									.round(((float) totalScore / (float) viewNum) * 100)) / 100;
							score = scores + "(满分：" + maxScore + ")";
						} else {
							score = "无";
						}
					} else {
						score = "无";
					}
					sbstr.append("\"expertScore\":\"" + score + "\"");
					sbstr.append(",");

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

		// return result;
	}

	public String projectApply() {
		initServlert();
		HttpSession session = request.getSession();
		hmFiles = new HashMap<>();
		lsFiles = new ArrayList<>();
		session.removeAttribute("uploadfile");
		session.removeAttribute("workreport");
		String pid = "";
		if (request.getParameterMap().containsKey("pid")) {
			pid = request.getParameter("pid");
			projectApply = iProjectApplyService.get(Integer.parseInt(pid));
			project = projectApply.getProject();

			document = projectApply.getDocumentByProjectApplyDocument();
			if (document != null && document.getId() != null) {
				fileInfo fileInfo = new fileInfo();
				fileInfo = initFileInfo(document);
				hmFiles.put(fileInfo.getFileType(), fileInfo);
			}
			document = projectApply.getDocumentByProformanceReport();
			if (document != null && document.getId() != null) {
				fileInfo fileInfo = new fileInfo();
				fileInfo = initFileInfo(document);
				hmFiles.put(fileInfo.getFileType(), fileInfo);
			}
			document = projectApply.getDocumentByStartMessage();
			if (document != null && document.getId() != null) {
				fileInfo fileInfo = new fileInfo();
				fileInfo = initFileInfo(document);
				hmFiles.put(fileInfo.getFileType(), fileInfo);
			}
			List<ProjectApplyWorkReport> wrlist = this.iProjectApplyWorkReportService
					.getListByProjectApply(projectApply.getId());
			if (wrlist != null && wrlist.size() > 0) {
				for (ProjectApplyWorkReport wr : wrlist) {
					fileInfo fileInfo = new fileInfo();
					document = wr.getDocument();
					fileInfo = initFileInfo(document);
					lsFiles.add(fileInfo);
				}
			}
			// 显示课程表
			document = projectApply.getDocumentByCurriculumDocument();
			if (document != null && document.getId() != null) {
				fileInfo fileInfo = new fileInfo();
				fileInfo = initFileInfo(document);
				hmFiles.put(fileInfo.getFileType(), fileInfo);
			}
			
			// 显示实施方案
			document = projectApply.getDocumentByProjectPlan();
			if (document != null && document.getId() != null) {
				fileInfo fileInfo = new fileInfo();
				fileInfo = initFileInfo(document);
				hmFiles.put(fileInfo.getFileType(), fileInfo);
			}

			Document doc = project.getRedHeadDocument();
			if (doc != null) {
				fileInfo fileInfo = new fileInfo();
				fileInfo = initFileInfo(doc);
				this.redHead.put(fileInfo.getFileType(), fileInfo);
			}
		}

		if (request.getParameterMap().containsKey("id")) {
			id = request.getParameter("id");
			projectApply = new ProjectApply();
			/* session.setAttribute("uploadfile", null); */
			if (!id.equals("") && !id.equals(null)) {
				project = iProjectService.get(Integer.parseInt(id));
				Document doc = project.getRedHeadDocument();
				if (doc != null) {
					fileInfo fileInfo = new fileInfo();
					fileInfo = initFileInfo(doc);
					this.redHead.put(fileInfo.getFileType(), fileInfo);
				}
			}
		}
		session.setAttribute("uploadfile", hmFiles);
		session.setAttribute("workreport", lsFiles);

		// 学科
		if (project.getRestrictSubject() == false) {
			lsttTrainingSubjects = iTrainingSubjectService.findAll();
		} else {
			// UserSession us = (UserSession)
			// session.getAttribute("usersession");
			List<ProjectSubjectRange> psrlist = iProjectSubjectRangeService
					.getListByProject(Integer.valueOf(project.getId()));
			for (ProjectSubjectRange psr : psrlist) {
				lsttTrainingSubjects.add(psr.getTrainingSubject());
			}
		}

		lstteacTeachingSubjectExs.clear();
		for (TrainingSubject ts : lsttTrainingSubjects) {
			teachingSubjectEx tt = new teachingSubjectEx();
			tt.setId(ts.getId().toString());
			tt.setName(ts.getName());
			tt.setSearchString(ts.getName()
					+ pinyingUtil.getFirstSpell(ts.getName()));
			lstteacTeachingSubjectExs.add(tt);
		}
		return "projectApply";
	}

	private fileInfo initFileInfo(Document document) {
		fileInfo fileInfo = new fileInfo();
		fileInfo.setFileGuid(document.getName());
		fileInfo.setFileExt(document.getTitle().substring(
				document.getTitle().indexOf(".")));
		fileInfo.setFileSize(document.getSize());
		fileInfo.setFileName(document.getTitle());
		fileInfo.setFilePath(document.getResourcePath());
		switch (document.getType()) {
		case 2:
			fileInfo.setFileType(EDocumentType.applyReportBook);
			break;
		case 4:
			fileInfo.setFileType(EDocumentType.classStartReport);
			break;
		case 5:
			fileInfo.setFileType(EDocumentType.workReport);
			break;
		case 6:
			fileInfo.setFileType(EDocumentType.proformanctReport);
			break;
		case 7:
			fileInfo.setFileType(EDocumentType.redHeadModel);
			break;
		case 8:
			fileInfo.setFileType(EDocumentType.curriculumReport);
			break;
		case 3:
			fileInfo.setFileType(EDocumentType.projectPlan);//实施方案
			break;
		}
		return fileInfo;
	}

	public void addProjectapply() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		pageStatus = "OK";
		message = "";
		String pid = "";
		String subjectId = "";
		String contacts = "";
		String phone = "";
//		String trainingClasshour = null;
//		String trainingOnlineHour = null;
		String trainingAddress = "";
		Timestamp trainingStarttime = null;
		Timestamp trainingEndtime = null;
		Timestamp enrollEndTime = null;
		int approveNumber = 0;
		String rString = "";
		message = "";
		try {
			HttpSession session = request.getSession();
			hmFiles = (HashMap<EDocumentType, fileInfo>) session
					.getAttribute("uploadfile");
			lsFiles = (List<fileInfo>) session.getAttribute("workreport");

			if (request.getParameterMap().containsKey("pid")) {
				pid = request.getParameter("pid");

			}
			/*
			 * else { pageStatus = "ERROR"; message = "提交错误！"; }
			 */
			subjectId = request.getParameter("mainTeachingSubject") == null ? ""
					: request.getParameter("mainTeachingSubject");
			if (!"".equals(subjectId) && subjectId.equals("-1")) {
				pageStatus = "ERROR";
				message = "请选择学科";
			}
			contacts = request.getParameter("contacts");
			phone = request.getParameter("phone");
			trainingAddress = request.getParameter("trainingAddress");

//			if (request.getParameterMap().containsKey("trainingClasshour")) {
//				trainingClasshour = request.getParameter("trainingClasshour");
//			}
//
//			if (request.getParameterMap().containsKey("trainingOnlineHour")) {
//				trainingOnlineHour = request.getParameter("trainingOnlineHour");
//			}

			if (request.getParameter("trainingStarttime") != null
					&& !request.getParameter("trainingStarttime").equals("")) {

				trainingStarttime = dataTimeConvertUtility
						.stringToTimeStamp(request
								.getParameter("trainingStarttime")
								+ " 00:00:00");
			}
			if (request.getParameter("trainingEndtime") != null
					&& !request.getParameter("trainingEndtime").equals("")) {

				trainingEndtime = dataTimeConvertUtility
						.stringToTimeStamp(request
								.getParameter("trainingEndtime") + " 00:00:00");
			} else {
				pageStatus = "ERROR";
				message = "请填写计划结束时间";
			}

			if (request.getParameter("approveNumber") != null
					&& !request.getParameter("approveNumber").equals("")) {
				approveNumber = Integer.parseInt(request
						.getParameter("approveNumber"));
			}

			TrainingAdmin trainingAdmin = iTrainingAdminService.get(user
					.getId());
			if (pageStatus.equals("OK")) {
				String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
				serverPath = Utlity.getRealPath(ServletActionContext.getServletContext());
				ProjectApply projectApply = new ProjectApply();
				TrainingSubject ts = iTrainingSubjectService.get((short) Integer.parseInt(subjectId));
				if (Utlity.isNumeric(request.getParameter("id"))) {
					int id = Integer.parseInt(request.getParameter("id"));
					Project project = this.iProjectService.get(id);

//					if (project.getTraintype() == 1
//							|| project.getTraintype() == 3) {
//						if (trainingClasshour == null
//								|| trainingClasshour.equals("")) {
//							Utlity.sendResponse("ERROR", "没有设置集中培训课时", response);
//							return;
//						}
//					}
//					if (project.getTraintype() == 2
//							|| project.getTraintype() == 3) {
//						if (trainingOnlineHour == null
//								|| trainingOnlineHour.equals("")) {
//							Utlity.sendResponse("ERROR", "没有设置远程培训课时", response);
//							return;
//						}
//					}

					if (project.getEnrollType() == 2) {
						// 报名截止日期
						if (request.getParameter("enrollEndTime") != null
								&& !request.getParameter("enrollEndTime")
										.equals("")) {

							enrollEndTime = dataTimeConvertUtility
									.stringToTimeStamp(request
											.getParameter("enrollEndTime")
											+ " 00:00:00");
						} else {
							pageStatus = "ERROR";
							message = "请填写报名截止日期";
						}
					}

					if (request.getParameter("pid") == null
							|| request.getParameter("pid").equals("")) {
						int totalNumber = project.getNumber();// 总计划培训人数
						// int totalSubjectCount = project.getSubjectMax();
						if (approveNumber > totalNumber) {// 申报人数大于总培训人数
							pageStatus = "ERROR";
							message = "培训人数超过该项目计划培训人数，请修改。。。";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}

						// int subjectCount = 0;
						int isApproveNumber = 0;// 已申报人数
						List<ProjectApply> lstpa = this.iProjectApplyService
								.getCheckedProjectApply(project.getId());
						for (ProjectApply pa : lstpa) {
							isApproveNumber += pa.getApproveNumber();
							// subjectCount++ ;
						}

						if ((totalNumber - isApproveNumber) <= 0) {
							pageStatus = "ERROR";
							message = "培训人数超过该项目计划培训人数剩余量，请修改。。。";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;

						}
					}

					projectApply.setProject(project);

				}
				if (lsFiles != null && lsFiles.size() > 0) {//培训简报
					
					int i = 0;
					Set<ProjectApplyWorkReport> setWr = new HashSet<ProjectApplyWorkReport>();
					for (fileInfo fileInfo : lsFiles) {
						String newName = projectApply.getProject().getShortname()+"-"+ts.getName()+"培训-"+trainingAdmin.getTrainingCollege().getName()+"-培训简报";
						i++;
						newName+="["+i+"]"+fileInfo.getFileExt();
						
						/*
						 * 重命名文件
						 */
						String realPath = fileInfo.getFilePath();
						String url = realPath.substring(0,realPath.lastIndexOf("/"));
						String newPath = (url+"/"+newName);
						String result = Utlity.renameFile(serverPath, realPath, newPath);
						if(!"ok".equals(result)){
							pageStatus = "ERROR";
							message = "培训简报上传失败，请重新操作。。。";
							rString = "{\"Result\":\"" + pageStatus
									+ "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						document = new Document();
						document.setCreater(user.getId());
						document.setCreatetime(dataTimeConvertUtility
								.getCurrentTime(""));
						document.setName(fileInfo.getFileGuid());
						document.setSize(fileInfo.getFileSize());
//						document.setResourcePath(fileInfo.getFilePath());
//						if (fileInfo.getFileName().contains(".")) {
//							document.setTitle(fileInfo.getFileName());
//						} else {
//							document.setTitle(fileInfo.getFileName()
//									+ fileInfo.getFileExt());
//						}
						document.setResourcePath(newPath);
						document.setTitle(newName);
						document.setType((short) 5);
						document.setResourceType((short) 1);
						document = iDocumentService.add(document);
						ProjectApplyWorkReport wr = new ProjectApplyWorkReport();
						wr.setProjectApply(projectApply);
						wr.setDocument(document);
						setWr.add(wr);
					}
					projectApply.setProjectApplyWorkReport(setWr);
				}
				if (hmFiles != null && hmFiles.size() > 0) {//其他文件
					/*
					 * document = projectApply.getDocumentByProformanceReport();
					 * if (document != null) {
					 * iDocumentService.delete(document); } document =
					 * projectApply.getDocumentByProjectApplyDocument(); if
					 * (document != null) { iDocumentService.delete(document); }
					 * document = projectApply.getDocumentByStartMessage(); if
					 * (document != null) { iDocumentService.delete(document); }
					 * document = projectApply.getDocumentByWorkReport(); if
					 * (document != null) { iDocumentService.delete(document); }
					 */
					
					fileInfo fileInfo = new fileInfo();
					Iterator<Entry<EDocumentType, fileInfo>> iter = hmFiles
							.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<EDocumentType, fileInfo> entry = (Entry<EDocumentType, fileInfo>) iter
								.next();
						fileInfo = (fileInfo) entry.getValue();
						
						
						
						document = new Document();
						document.setCreater(user.getId());
						document.setCreatetime(dataTimeConvertUtility
								.getCurrentTime(""));
						document.setName(fileInfo.getFileGuid());
						document.setSize(fileInfo.getFileSize());
//						document.setResourcePath(fileInfo.getFilePath());
//						if (fileInfo.getFileName().contains(".")) {
//							document.setTitle(fileInfo.getFileName());
//						} else {
//							document.setTitle(fileInfo.getFileName()
//									+ fileInfo.getFileExt());
//						}
						/*
						 * 重命名文件
						 */
						String realPath = fileInfo.getFilePath();
						String url = realPath.substring(0,realPath.lastIndexOf("/"));
						String newPath = "";
						String newName = "";
						String result = "ERROR";
						switch (fileInfo.getFileType()) {
						case applyReportBook:
							
							newName = projectApply.getProject().getShortname()+"-"+ts.getName()+"培训-"+trainingAdmin.getTrainingCollege().getName()+"-申报书";
							newName+=fileInfo.getFileExt();
							
							newPath = (url+"/"+newName);
							result = Utlity.renameFile(serverPath, realPath, newPath);
							if(!"ok".equals(result)){
								pageStatus = "ERROR";
								message = "申报书上传失败，请重新操作。。。";
								rString = "{\"Result\":\"" + pageStatus
										+ "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							document.setResourcePath(newPath);
							document.setTitle(newName);
							document.setType((short) 2);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply
									.setDocumentByProjectApplyDocument(document);
							break;
						case classStartReport:
							/*
							 * 重命名文件
							 */
							newName = projectApply.getProject().getShortname()+"-"+ts.getName()+"培训-"+trainingAdmin.getTrainingCollege().getName()+"-开班通知";
							newName+=fileInfo.getFileExt();
							newPath = (url+"/"+newName);
							result = Utlity.renameFile(serverPath, realPath, newPath);
							if(!"ok".equals(result)){
								pageStatus = "ERROR";
								message = "开班通知上传失败，请重新操作。。。";
								rString = "{\"Result\":\"" + pageStatus
										+ "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							document.setResourcePath(newPath);
							document.setTitle(newName);
							document.setType((short) 4);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply.setDocumentByStartMessage(document);
							break;
						case proformanctReport:
							/*
							 * 重命名文件
							 */
							newName = projectApply.getProject().getShortname()+"-"+ts.getName()+"培训-"+trainingAdmin.getTrainingCollege().getName()+"-绩效报告";
							newName+=fileInfo.getFileExt();
							newPath = (url+"/"+newName);
							result = Utlity.renameFile(serverPath, realPath, newPath);
							if(!"ok".equals(result)){
								pageStatus = "ERROR";
								message = "绩效报告上传失败，请重新操作。。。";
								rString = "{\"Result\":\"" + pageStatus
										+ "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							document.setResourcePath(newPath);
							document.setTitle(newName);
							document.setType((short) 6);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply
									.setDocumentByProformanceReport(document);
							break;
						case curriculumReport:
							/*
							 * 重命名文件
							 */
							newName = projectApply.getProject().getShortname()+"-"+ts.getName()+"培训-"+trainingAdmin.getTrainingCollege().getName()+"-课程表";
							newName+=fileInfo.getFileExt();
							newPath = (url+"/"+newName);
							result = Utlity.renameFile(serverPath, realPath, newPath);
							if(!"ok".equals(result)){
								pageStatus = "ERROR";
								message = "课程表上传失败，请重新操作。。。";
								rString = "{\"Result\":\"" + pageStatus
										+ "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							document.setResourcePath(newPath);
							document.setTitle(newName);
							document.setType((short) 8);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply
									.setDocumentByCurriculumDocument(document);
							break;
						case projectPlan://实施方案
							/*
							 * 重命名文件
							 */
							newName = projectApply.getProject().getShortname()+"-"+ts.getName()+"培训-"+trainingAdmin.getTrainingCollege().getName()+"-实施方案";
							newName+=fileInfo.getFileExt();
							newPath = (url+"/"+newName);
							result = Utlity.renameFile(serverPath, realPath, newPath);
							if(!"ok".equals(result)){
								pageStatus = "ERROR";
								message = "实施方案上传失败，请重新操作。。。";
								rString = "{\"Result\":\"" + pageStatus
										+ "\",\"Message\":\"" + message + "\"}";
								Utlity.ResponseWrite(rString, "json", response);
								return;
							}
							document.setResourcePath(newPath);
							document.setTitle(newName);
							document.setType((short) 3);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply
									.setDocumentByProjectPlan(document);
							break;
						default:
							break;

						}

					}

				}
				projectApply.setTrainingSubject(ts);
				projectApply.setType((short) 1);

				projectApply.setContacts(contacts);
				projectApply.setPhone(phone);
				projectApply.setTrainingAddress(trainingAddress);
				projectApply.setPhone(phone);
//				if (projectApply.getProject().getTraintype() == 1) {
//					projectApply.setTrainingClasshour(Integer
//							.valueOf(trainingClasshour));
//					projectApply.setTrainingOnlineHour(0);
//				} else if (projectApply.getProject().getTraintype() == 2) {
//					projectApply.setTrainingClasshour(0);
//					projectApply.setTrainingOnlineHour(Integer
//							.valueOf(trainingOnlineHour));
//				} else {
//					projectApply.setTrainingClasshour(Integer
//							.valueOf(trainingClasshour));
//					projectApply.setTrainingOnlineHour(Integer
//							.valueOf(trainingOnlineHour));
//				}
				projectApply.setTrainingClasshour(0);
				projectApply.setTrainingOnlineHour(0);

				if (trainingStarttime != null) {
					projectApply.setTrainingStarttime(trainingStarttime);
				}
				if (trainingEndtime != null) {

					projectApply.setTrainingEndtime(trainingEndtime);
				}

				if (enrollEndTime != null) {
					projectApply.setEnrollEndTime(enrollEndTime);
				}
				projectApply.setCreator(user.getId());
				projectApply.setCreattime(dataTimeConvertUtility
						.getCurrentTime(""));
				projectApply.setApproveNumber(approveNumber);
				projectApply.setTrainingCollege(trainingAdmin
						.getTrainingCollege());

				if (!request.getParameter("pid").equals("")) {
					ProjectApply tp = iProjectApplyService.get(Integer
							.parseInt(request.getParameter("pid")));
					if (hmFiles.size() > 0) {
						document = tp.getDocumentByProformanceReport();
						if (document != null) {
							tp.setDocumentByProformanceReport(null);
							iProjectApplyService.update(tp);
							iDocumentService.delete(document);
						}
						document = tp.getDocumentByProjectApplyDocument();
						if (document != null) {
							tp.setDocumentByProjectApplyDocument(null);
							iProjectApplyService.update(tp);
							iDocumentService.delete(document);
						}
						document = tp.getDocumentByStartMessage();
						if (document != null) {
							tp.setDocumentByStartMessage(null);
							iProjectApplyService.update(tp);
							iDocumentService.delete(document);
						}
						document = tp.getDocumentByCurriculumDocument();
						if (document != null) {
							tp.setDocumentByCurriculumDocument(null);
							iProjectApplyService.update(tp);
							iDocumentService.delete(document);
						}
						document = tp.getDocumentByProjectPlan();
						if (document != null) {
							tp.setDocumentByProjectPlan(null);
							iProjectApplyService.update(tp);
							iDocumentService.delete(document);
						}
					}
					if (lsFiles.size() > 0) {
						if (tp.getProjectApplyWorkReport().size() > 0) {
							Set<ProjectApplyWorkReport> wrSet = tp
									.getProjectApplyWorkReport();
							tp.setProjectApplyWorkReport(new HashSet<ProjectApplyWorkReport>());
							for (ProjectApplyWorkReport wr : wrSet) {
								document = wr.getDocument();
								iProjectApplyWorkReportService.delete(wr);
								iDocumentService.delete(document);
							}
						}
					}
					tp.setDocumentByProformanceReport(projectApply
							.getDocumentByProformanceReport());
					tp.setDocumentByStartMessage(projectApply
							.getDocumentByStartMessage());
					Set<ProjectApplyWorkReport> wrSet = projectApply
							.getProjectApplyWorkReport();
					for (ProjectApplyWorkReport wr : wrSet) {
						wr.setProjectApply(tp);
					}
					tp.setProjectApplyWorkReport(wrSet);
					tp.setDocumentByProjectApplyDocument(projectApply
							.getDocumentByProjectApplyDocument());
					tp.setProject(projectApply.getProject());
					tp.setTrainingSubject(projectApply.getTrainingSubject());
					tp.setType(projectApply.getType());
					tp.setContacts(projectApply.getContacts());
					tp.setPhone(projectApply.getPhone());
					tp.setTrainingAddress(projectApply.getTrainingAddress());
					tp.setTrainingClasshour(projectApply.getTrainingClasshour());
					tp.setTrainingOnlineHour(projectApply
							.getTrainingOnlineHour());
					tp.setTrainingStarttime(projectApply.getTrainingStarttime());
					tp.setTrainingEndtime(projectApply.getTrainingEndtime());
					tp.setCreator(projectApply.getCreator());
					tp.setApproveNumber(projectApply.getApproveNumber());
					tp.setTrainingCollege(projectApply.getTrainingCollege());

					tp.setDocumentByCurriculumDocument(projectApply
							.getDocumentByCurriculumDocument());// 更新课程表
					//更新实施方案
					tp.setDocumentByProjectPlan(projectApply.getDocumentByProjectPlan());
					/*
					 * if (projectApply.getStatus() == 2) { pageStatus =
					 * "ERROR"; message = "项目已经审批，不能修改"; } else {
					 */
					tp.setProjectCycle(projectApply.getProject().getProjectCycle());
					iProjectApplyService.update(tp);
					// }

				} else {
					int pa = this.iProjectApplyService
							.getCountByTrainingCollege(
									user.getOrganization(),
									Integer.valueOf(request.getParameter("id")),
									Integer.valueOf(subjectId));
					if (pa != 0) {
						pageStatus = "ERROR";
						message = "该科目已经进行过申报";
						rString = "{\"Result\":\"" + pageStatus
								+ "\",\"Message\":\"" + message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
					}
					projectApply.setStatus((short) 1);
					projectApply.setProjectCycle(projectApply.getProject().getProjectCycle());
					// 9月9号新加4个学时
					Map<String, Object> classHoursForTeacher = getClassHoursForTeacher(projectApply.getProject(),projectApply.getTrainingSubject().getId(),projectApply.getProject().getYear());
					if (classHoursForTeacher.size() > 0) {
						List<Map<String, Object>> studyhour = (List<Map<String, Object>>) classHoursForTeacher.get("studyhour");
						projectApply.setStudyhour(JSONUtils.obj2json(studyhour));
						projectApply.setCredit(Integer.valueOf(classHoursForTeacher.get("credit").toString()));
					}
					iProjectApplyService.add(projectApply);
				}

			}

			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
			pageStatus = "ERROR";
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
			Utlity.ResponseWrite(rString, "json", response);
		}

		if("OK".equals(pageStatus)){
			session.removeAttribute("uploadfile");
			session.removeAttribute("workreport");
		}
		
		Utlity.ResponseWrite(rString, "json", response);

	}

	public void delet() {
		initServlert();
		String pid = request.getParameter("pid");
		pageStatus = "OK";
		projectApply = iProjectApplyService.get(Integer.parseInt(pid));
		if (projectApply.getStatus() == 2) {
			pageStatus = "ERROR";
			message = "项目已经审批，不能删除";
		} else {
			iProjectApplyService.delete(projectApply);
		}
		String rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
				+ message + "\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}

	/**
	 * 
	 * 根据条件获取项目学科列表
	 */
	public void getProjectApplyListById() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");

		StringBuilder sb = new StringBuilder();

		String pId = request.getParameter("pId") == null ? "" : request
				.getParameter("pId");
		String rpId = request.getParameter("rpId") == null ? "0" : request
				.getParameter("rpId");

		if ("".equals(pId)) {
			pId = "0";
		}
		
		if ("".equals(rpId)) {
			rpId = "0";
		}

		String sId = "0";
		if (pId.split("a").length > 1) {
			String[] ids = pId.split("a");
			pId = ids[0];
			sId = ids[1];
		}

		int id = Integer.parseInt(pId);
		int sid = Integer.parseInt(sId);

		Integer trainAdminId = Integer.parseInt(rpId);
		TrainingAdmin ta = this.iTrainingAdminService.get(trainAdminId);
		if (sid == 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", 2);
			if (ta != null) {
				params.put("trainingCollege", ta.getTrainingCollege().getId());
			} else {
				params.put("trainingCollege", us.getOrganization());
			}
			if (id <= 0) {
				params.put("group", "t.project");
			} else {
				params.put("projectId", id);
			}

			List lstProjectApply = this.iProjectApplyService
					.getProjectByParams(params, null);

			if (id <= 0) {
				if (lstProjectApply != null && lstProjectApply.size() > 0) {
					sb.append("[");
					for (int i = 0; i < lstProjectApply.size(); i++) {
						Object[] obj = (Object[]) lstProjectApply.get(i);

						ProjectApply pa = (ProjectApply) obj[0];

						sb.append("{");
						sb.append("\"id\":" + pa.getProject().getId());
						sb.append(",");
						sb.append("\"name\":\"" + pa.getProject().getName()
								+ "\"");
						sb.append(",");
						sb.append("\"isProject\":1");
						sb.append("},");
					}

					sb.delete(sb.length() - 1, sb.length());
					sb.append("]");
				}

			} else {
				if (lstProjectApply != null && lstProjectApply.size() > 0) {
					sb.append("[");
					sb.append("{");
					sb.append("\"pid\":" + pId);
					sb.append(",");
					sb.append("\"id\":" + 0);
					sb.append(",");
					sb.append("\"name\":\"全部\"");
					sb.append(",");
					sb.append("\"isProject\":0");
					sb.append("},");
					for (int i = 0; i < lstProjectApply.size(); i++) {
						Object[] obj = (Object[]) lstProjectApply.get(i);

						ProjectApply pa = (ProjectApply) obj[0];

						sb.append("{");
						sb.append("\"pid\":" + pId);
						sb.append(",");
						sb.append("\"id\":" + pa.getTrainingSubject().getId());
						sb.append(",");
						sb.append("\"name\":\""
								+ pa.getTrainingSubject().getName() + "\"");
						sb.append(",");
						sb.append("\"isProject\":0");
						sb.append("},");
					}

					sb.delete(sb.length() - 1, sb.length());
					sb.append("]");
				}
			}
		} else {
			sb.append("[");
			sb.append("{");
			sb.append("\"pid\":" + pId);
			sb.append(",");
			sb.append("\"sid\":" + sid);
			sb.append(",");
			sb.append("\"id\":" + 0);
			sb.append(",");
			sb.append("\"name\":\"全部\"");
			sb.append(",");
			sb.append("\"isProject\":2");
			sb.append("},");
			for (int i = 0; i < 8; i++) {
				int classId = i + 1;
				sb.append("{");
				sb.append("\"pid\":" + pId);
				sb.append(",");
				sb.append("\"sid\":" + sid);
				sb.append(",");
				sb.append("\"id\":" + classId);
				sb.append(",");
				sb.append("\"name\":\"" + classId + "班\"");
				sb.append(",");
				sb.append("\"isProject\":2");
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("]");
		}

		Utlity.ResponseWrite(sb.toString(), "json", response);

	}

	/**
	 * 获取材料上传情况
	 */
	public void getDocumentUpdateInfo() {
		initServlert();

		String paid = request.getParameter("id") == null ? "0" : request
				.getParameter("id");
		StringBuilder sb = new StringBuilder();
		if (!"".equals(paid)) {
			ProjectApply pa = this.iProjectApplyService.get(Integer
					.parseInt(paid));
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"TotalRecordCount\":" + 1);
			sb.append(",");
			sb.append("\"Records\":[{");
			// 材料上传情况
			// 需要上传的材料有：申报书、开班通知、工作简报、绩效报告、课程表
			String projectApplyReport = "";
			if (pa.getDocumentByProjectApplyDocument() != null) {
				projectApplyReport = "已上传";
			} else {
				projectApplyReport = "未上传";
			}
			sb.append("\"projectApplyReport\":\"" + projectApplyReport + "\"");
			sb.append(",");

			String startMessageReport = "";
			if (pa.getDocumentByStartMessage() != null) {
				startMessageReport = "已上传";
			} else {
				startMessageReport = "未上传";
			}
			sb.append("\"startMessageReport\":\"" + startMessageReport + "\"");
			sb.append(",");

			String workReport = "";
			if (pa.getProjectApplyWorkReport().size() > 0) {
				workReport = "已上传";
			} else {
				workReport = "未上传";
			}
			sb.append("\"workReport\":\"" + workReport + "\"");
			sb.append(",");

			String proformanceReport = "";
			if (pa.getDocumentByProformanceReport() != null) {
				proformanceReport = "已上传";
			} else {
				proformanceReport = "未上传";
			}
			sb.append("\"proformanceReport\":\"" + proformanceReport + "\"");
			sb.append(",");

			String curriculumDocument = "";
			if (pa.getDocumentByCurriculumDocument() != null) {
				curriculumDocument = "已上传";
			} else {
				curriculumDocument = "未上传";
			}
			sb.append("\"curriculumDocument\":\"" + curriculumDocument + "\"");
			sb.append(",");
			//实施方案
			String projectPlan = "";
			if (pa.getDocumentByProjectPlan() != null) {
				projectPlan = "已上传";
			} else {
				projectPlan = "未上传";
			}
			sb.append("\"projectPlan\":\"" + projectPlan + "\"");
			sb.append("}]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"TotalRecordCount\":0");
			sb.append(",");
			sb.append("\"Records\":[");
			sb.append("]}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	/**
	 * 项目申报记录信息导出
	 */
	public void outputProjectApplyList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		TrainingAdmin ta = this.iTrainingAdminService.get(us.getId());
		TrainingCollege tc = ta.getTrainingCollege();
		// Set<TrainingAdminAuthority> taaSet = ta.getTrainingAdminAuthoritys();
		// if(ta.getRestrictRight() && taaSet != null && taaSet.size() > 0){
		// //有权限
		//
		// }
		String projectId = request.getParameter("projectName") == null ? "0" : request.getParameter("projectName");
		String subjectId = request.getParameter("subjectName") == null ? "0" : request.getParameter("subjectName");

		String sort = "id desc";
		
		int records = iProjectApplyService.getCountByTrainingCollege(tc.getId(), Integer.parseInt(projectId), Integer.parseInt(subjectId));
		List<ProjectApply> lstProjectApplies = iProjectApplyService.getlistForPojectApply(tc.getId(), Integer.parseInt(projectId), Integer.parseInt(subjectId), 0, records, sort);
		List<String> studyhourTitleList = new ArrayList<String>();
		for(ProjectApply pa : lstProjectApplies){
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
				studyhourList = JSONUtils.json2list(pa.getProjectCycle().getStudyhour(), Map.class);
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
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "申报记录表");
		HSSFRow row = s.createRow(0);
		List<String> titleList = new ArrayList<String>();
		String[] titleArrayFront = { "申报项目", "申报学科", "开始时间", "结束时间", "分配人数"};
		for(String title : titleArrayFront){
			titleList.add(title);
		}
		for(String title : studyhourTitleList){
			titleList.add(title);
		}
		String[] titleArrayBack = {"申报人", "申报人联系方式", "申报书上传情况", "开班通知上传情况", "工作简报上传情况", "绩效报告上传情况", "课程表上传情况", "专家评分", "申报状态", "备注" };
		for(String title : titleArrayBack){
			titleList.add(title);
		}
		for (int j = 0; j < titleList.size(); j++) {
			row.createCell(j).setCellValue(titleList.get(j));
		}
		try {
			if (lstProjectApplies != null && lstProjectApplies.size() > 0) {
				int t = 0;
				for (int i = 0; i < lstProjectApplies.size(); i++) {
					ProjectApply pa = lstProjectApplies.get(i);
					Project p = pa.getProject();
					TrainingSubject ts = pa.getTrainingSubject();
					t++;
					row = s.createRow(t);
					// 专家评分
					String score = "";
					if (p.getPsqByProjectJudgePsq() != null) {
						int projectid = p.getId();
						int subjectid = ts.getId();
						int trainingId = tc.getId();
						int psqId = p.getPsqByProjectJudgePsq().getId();
						int viewNum = iProjectApplyService.getExpertViewCount(
								psqId, projectid, subjectid, trainingId);
						if (viewNum != 0) {
							int totalScore = iProjectApplyService
									.getExpertViewTotalScore(psqId, projectid,
											subjectid, trainingId);
							int maxScore = iProjectApplyService
									.getExpertPsqMaxScore(projectid);
							float scores = (float) (Math
									.round(((float) totalScore / (float) viewNum) * 100)) / 100;
							score = scores + "(满分：" + maxScore + ")";
						} else {
							score = "无";
						}
					} else {
						score = "无";
					}
					String projectName = p.getName();// 申报项目
					String subjectName = ts.getName();// 申报学科

					String trainingStarttime = "待定";
					if (pa.getTrainingStarttime() != null) {
						trainingStarttime = Utlity.timeSpanToDateString(pa
								.getTrainingStarttime());// 开始时间
					}
					String trainingEndtime = Utlity.timeSpanToDateString(pa
							.getTrainingEndtime());// 结束时间

					String approveNumber = pa.getApproveNumber().toString();// 分配人数
//					String trainingClasshour = pa.getTrainingClasshour()
//							.toString();// 课时
//					String trainingOnlineHour = pa.getTrainingOnlineHour()
//							.toString();// 课时

					List<String> studyhourInfoList = new ArrayList<String>();
					List<Map> studyhourList = JSONUtils.json2list(pa.getStudyhour(), Map.class);
					if(studyhourList != null){
						for(String title : studyhourTitleList){
							Boolean titleFlag = true;
							for(Map map : studyhourList){
								String titleName = map.get("nameCN").toString();
								if(title.equals(titleName)){
									titleFlag = false;
//									studyhourInfoList.add(map.get("value").toString());
									studyhourInfoList.add("--");
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
					
					String creator = "";// 申报人
					String creatorPhone = "";// 申报人联系方式
					if (pa.getType() == 1) {
						TrainingAdmin taa = this.iTrainingAdminService.get(pa
								.getCreator());
						creator = taa.getName();
						creatorPhone = taa.getMobile();
					} else if (pa.getType() == 2) {
						ProjectAdmin paa = this.iProjectAdminService.get(pa
								.getCreator());
						creator = paa.getName();
						creatorPhone = paa.getMobile();
					}

					// 材料上传情况
					// 需要上传的材料有：申报书、开班通知、工作简报、绩效报告、课程表
					String projectApplyReport = "";
					if (pa.getDocumentByProjectApplyDocument() != null) {
						projectApplyReport = "已上传";
					} else {
						projectApplyReport = "未上传";
					}

					String startMessageReport = "";
					if (pa.getDocumentByStartMessage() != null) {
						startMessageReport = "已上传";
					} else {
						startMessageReport = "未上传";
					}

					String workReport = "";
					if (pa.getProjectApplyWorkReport().size() > 0) {
						workReport = "已上传";
					} else {
						workReport = "未上传";
					}

					String proformanceReport = "";
					if (pa.getDocumentByProformanceReport() != null) {
						proformanceReport = "已上传";
					} else {
						proformanceReport = "未上传";
					}

					String curriculumDocument = "";
					if (pa.getDocumentByCurriculumDocument() != null) {
						curriculumDocument = "已上传";
					} else {
						curriculumDocument = "未上传";
					}

					String status = "未审核";// 审核状态
					if (pa.getStatus() == 2) {
						status = "已审核";

					} else if (pa.getStatus() == 0) {
						status = "未通过";
					}

					String node = "";// 备注
					List<String> tInfoList = new ArrayList<String>();
					String[] tInfoFront = { projectName, subjectName, trainingStarttime, trainingEndtime, approveNumber};
					for(String info : tInfoFront){
						tInfoList.add(info);
					}
					for(String info : studyhourInfoList){
						tInfoList.add(info);
					}
					String[] tInfoBack = {creator, creatorPhone, projectApplyReport, startMessageReport, workReport, proformanceReport, curriculumDocument, score, status, node };
					for(String info : tInfoBack){
						tInfoList.add(info);
					}
					for (int j = 0; j < tInfoList.size(); j++) {
						row.createCell(j).setCellValue(tInfoList.get(j));
					}
				}

			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=records.xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 材料信息管理页面初始化,查看材料上传情况总揽
	 * 
	 * @return
	 */
	public String documentInit() {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");

		String year = request.getParameter("year");
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

		return "document";

	}

	public void getDocumentList() {
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
		String sort = "id desc";

		try {
			// 获取承训单位所有的项目
			List<ProjectApply> lstProjectApplies = new ArrayList<>();
			if (request.getParameterMap().containsKey("projectName")
					&& request.getParameterMap().containsKey("subjectName")) {
				int projectId = Integer.parseInt(request
						.getParameter("projectName"));
				int subjectId = Integer.parseInt(request
						.getParameter("subjectName"));
				lstProjectApplies = iProjectApplyService.getlistForPojectApply(
						iTrainingAdminService.get(user.getId())
								.getTrainingCollege().getId(), projectId,
						subjectId, (int) Math.ceil(start / limit), limit, sort);
			} else {
				lstProjectApplies = iProjectApplyService.getlistForPojectApply(
						iTrainingAdminService.get(user.getId())
								.getTrainingCollege().getId(),
						(int) Math.ceil(start / limit), limit, sort);
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

				for (ProjectApply pa : lstProjectApplies) {
					sb.append("{");
					sb.append("\"id\":" + pa.getId());
					sb.append(",");
					// 申报项目学科信息
					String projectName = pa.getProject().getName();
					String trainingSubject = pa.getTrainingSubject().getName();
					sb.append("\"projectName\":\"" + projectName + "\"");
					sb.append(",");
					sb.append("\"trainingSubject\":\"" + trainingSubject + "\"");
					sb.append(",");

					// 材料上传情况
					// 需要上传的材料有：申报书、开班通知、工作简报、绩效报告、课程表
					String projectApplyReport = "";
					if (pa.getDocumentByProjectApplyDocument() != null) {
						projectApplyReport = "已上传";
					} else {
						projectApplyReport = "未上传";
					}
					sb.append("\"projectApplyReport\":\"" + projectApplyReport
							+ "\"");
					sb.append(",");

					String startMessageReport = "";
					if (pa.getDocumentByStartMessage() != null) {
						startMessageReport = "已上传";
					} else {
						startMessageReport = "未上传";
					}
					sb.append("\"startMessageReport\":\"" + startMessageReport
							+ "\"");
					sb.append(",");

					String workReport = "";
					if (pa.getProjectApplyWorkReport().size() > 0) {
						workReport = "已上传";
					} else {
						workReport = "未上传";
					}
					sb.append("\"workReport\":\"" + workReport + "\"");
					sb.append(",");

					String proformanceReport = "";
					if (pa.getDocumentByProformanceReport() != null) {
						proformanceReport = "已上传";
					} else {
						proformanceReport = "未上传";
					}
					sb.append("\"proformanceReport\":\"" + proformanceReport
							+ "\"");
					sb.append(",");

					String curriculumDocument = "";
					if (pa.getDocumentByCurriculumDocument() != null) {
						curriculumDocument = "已上传";
					} else {
						curriculumDocument = "未上传";
					}
					sb.append("\"curriculumDocument\":\"" + curriculumDocument
							+ "\"");
					sb.append(",");
					
					String projectPlan = "";
					if (pa.getDocumentByProjectPlan() != null) {
						projectPlan = "已上传";
					} else {
						projectPlan = "未上传";
					}
					sb.append("\"projectPlan\":\"" + projectPlan
							+ "\"");
					sb.append("},");
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

	/**
	 * 获取学院的学时
	 * 
	 * @param projectType
	 *            项目类型
	 * @param tSubject
	 *            培训学科
	 */
	private Map<String, Object> getClassHoursForTeacher(Project project,short trainingSubjectId,String year) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map> studyhourList = new ArrayList<Map>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (project != null) {
			params.put("projectType", project.getProjectType().getId());
			params.put("project", project.getId());
		}
		if (trainingSubjectId > 0) {
			params.put("trainingSubject", trainingSubjectId);
		}
		if(!"".equals(year)){
			params.put("year", year);
		}
		params.put("status", 1);//取正常状态的学时认定标准
		int credit = 0;
		List<IdentifyStudyhour> listih = this.identifyStudyhourService.getListByParams(params, -1, -1, "createtime");
		if(listih != null && listih.size() > 0){
			IdentifyStudyhour is = listih.get(0);
			studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
			credit = is.getCredit();
		}else{
			params.remove("trainingSubject");
			listih = this.identifyStudyhourService.getListByParams(params, -1, -1, "createtime");
			if(listih != null && listih.size() > 0){
				IdentifyStudyhour is = listih.get(0);
				studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
				credit = is.getCredit();
			}else{
				if (trainingSubjectId > 0) {
					params.put("trainingSubject", trainingSubjectId);
				}
				params.remove("project");
				listih = this.identifyStudyhourService.getListByParams(params, -1, -1, "createtime");
				if(listih != null && listih.size() > 0){
					IdentifyStudyhour is = listih.get(0);
					studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
					credit = is.getCredit();
				}else{
					params.remove("year");
					listih = this.identifyStudyhourService.getListByParams(params, 0, -1, "createtime");
					if (listih != null && listih.size() > 0) {
						IdentifyStudyhour is = listih.get(0);
						studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
						credit = is.getCredit();
					} else {
						if (year != null && !"".equals(year)) {
							// 培训学科
							params.put("year", year);
						}
						params.remove("trainingSubject");
						listih = this.identifyStudyhourService.getListByParams(params, -1, -1, "id");
						if (listih != null && listih.size() > 0) {
							IdentifyStudyhour is = listih.get(0);
							studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
							credit = is.getCredit();
						} else {
							params.remove("year");
							listih = this.identifyStudyhourService.getListByParams(params, -1, -1, "id");
							if (listih != null && listih.size() > 0) {
								IdentifyStudyhour is = listih.get(0);
								studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
								credit = is.getCredit();
							} 
						}
					}
				}
			}
		}
		
		result.put("studyhour", studyhourList);
		result.put("credit", credit);
		return result;
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
	 * @return the iProjectTypeService
	 */
	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	/**
	 * @param iProjectTypeService
	 *            the iProjectTypeService to set
	 */
	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}

	/**
	 * @return the iAreaService
	 */
	public IAreaService getiAreaService() {
		return iAreaService;
	}

	/**
	 * @param iAreaService
	 *            the iAreaService to set
	 */
	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	/**
	 * @return the iProjectAdminService
	 */
	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	/**
	 * @param iProjectAdminService
	 *            the iProjectAdminService to set
	 */
	public void setiProjectAdminService(
			IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	/**
	 * @return the projectHash
	 */
	public LinkedHashMap<Integer, String[]> getProjectHash() {
		return projectHash;
	}

	/**
	 * @param projectHash
	 *            the projectHash to set
	 */
	public void setProjectHash(LinkedHashMap<Integer, String[]> projectHash) {
		this.projectHash = projectHash;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the lstteacTeachingSubjectExs
	 */
	public List<teachingSubjectEx> getLstteacTeachingSubjectExs() {
		return lstteacTeachingSubjectExs;
	}

	/**
	 * @param lstteacTeachingSubjectExs
	 *            the lstteacTeachingSubjectExs to set
	 */
	public void setLstteacTeachingSubjectExs(
			List<teachingSubjectEx> lstteacTeachingSubjectExs) {
		this.lstteacTeachingSubjectExs = lstteacTeachingSubjectExs;
	}

	/**
	 * @return the pageStatus
	 */
	public String getPageStatus() {
		return pageStatus;
	}

	/**
	 * @param pageStatus
	 *            the pageStatus to set
	 */
	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return the iProjectApplyWorkReportService
	 */
	public IProjectApplyWorkReportService getiProjectApplyWorkReportService() {
		return iProjectApplyWorkReportService;
	}

	/**
	 * @param iProjectApplyWorkReportService
	 *            the iProjectApplyWorkReportService to set
	 */
	public void setiProjectApplyWorkReportService(
			IProjectApplyWorkReportService iProjectApplyWorkReportService) {
		this.iProjectApplyWorkReportService = iProjectApplyWorkReportService;
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
	 * @return the iDocumentService
	 */
	public IDocumentService getiDocumentService() {
		return iDocumentService;
	}

	/**
	 * @param iDocumentService
	 *            the iDocumentService to set
	 */
	public void setiDocumentService(IDocumentService iDocumentService) {
		this.iDocumentService = iDocumentService;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	public String getCollegeId() {
		return collegeId;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}

	/**
	 * @return the hmFiles
	 */
	public HashMap<EDocumentType, fileInfo> getHmFiles() {
		return hmFiles;
	}

	/**
	 * @param hmFiles
	 *            the hmFiles to set
	 */
	public void setHmFiles(HashMap<EDocumentType, fileInfo> hmFiles) {
		this.hmFiles = hmFiles;
	}

	/**
	 * @return the lsFiles
	 */
	public List<fileInfo> getLsFiles() {
		return lsFiles;
	}

	/**
	 * @param lsFiles
	 *            the lsFiles to set
	 */
	public void setLsFiles(List<fileInfo> lsFiles) {
		this.lsFiles = lsFiles;
	}

	/**
	 * @return the iProjectCollegeRangeService
	 */
	public IProjectCollegeRangeService getiProjectCollegeRangeService() {
		return iProjectCollegeRangeService;
	}

	/**
	 * @param iProjectCollegeRangeService
	 *            the iProjectCollegeRangeService to set
	 */
	public void setiProjectCollegeRangeService(
			IProjectCollegeRangeService iProjectCollegeRangeService) {
		this.iProjectCollegeRangeService = iProjectCollegeRangeService;
	}

	/**
	 * @return the iProjectSubjectRangeService
	 */
	public IProjectSubjectRangeService getiProjectSubjectRangeService() {
		return iProjectSubjectRangeService;
	}

	/**
	 * @param iProjectSubjectRangeService
	 *            the iProjectSubjectRangeService to set
	 */
	public void setiProjectSubjectRangeService(
			IProjectSubjectRangeService iProjectSubjectRangeService) {
		this.iProjectSubjectRangeService = iProjectSubjectRangeService;
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

	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public HashMap<EDocumentType, fileInfo> getRedHead() {
		return redHead;
	}

	public void setRedHead(HashMap<EDocumentType, fileInfo> redHead) {
		this.redHead = redHead;
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

	public IIdentifyClasshoursSubjectYearService getIdentifyClasshoursSubjectYearService() {
		return identifyClasshoursSubjectYearService;
	}

	public void setIdentifyClasshoursSubjectYearService(
			IIdentifyClasshoursSubjectYearService identifyClasshoursSubjectYearService) {
		this.identifyClasshoursSubjectYearService = identifyClasshoursSubjectYearService;
	}

	public IIdentifyClasshoursProjectYearService getIdentifyClasshoursProjectYearService() {
		return identifyClasshoursProjectYearService;
	}

	public void setIdentifyClasshoursProjectYearService(
			IIdentifyClasshoursProjectYearService identifyClasshoursProjectYearService) {
		this.identifyClasshoursProjectYearService = identifyClasshoursProjectYearService;
	}

	public IIdentifyStudyhourService getIdentifyStudyhourService() {
		return identifyStudyhourService;
	}

	public void setIdentifyStudyhourService(
			IIdentifyStudyhourService identifyStudyhourService) {
		this.identifyStudyhourService = identifyStudyhourService;
	}
}
