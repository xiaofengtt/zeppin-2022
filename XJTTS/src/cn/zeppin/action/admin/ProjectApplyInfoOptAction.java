package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.IdentifyClasshours;
import cn.zeppin.entity.IdentifyClasshoursProjectYear;
import cn.zeppin.entity.IdentifyClasshoursSubject;
import cn.zeppin.entity.IdentifyClasshoursSubjectYear;
import cn.zeppin.entity.IdentifyStudyhour;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyEvaluate;
import cn.zeppin.entity.ProjectApplyExpert;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IAssignTeacherTaskService;
import cn.zeppin.service.IIdentifyClasshoursProjectYearService;
import cn.zeppin.service.IIdentifyClasshoursService;
import cn.zeppin.service.IIdentifyClasshoursSubjectService;
import cn.zeppin.service.IIdentifyClasshoursSubjectYearService;
import cn.zeppin.service.IIdentifyStudyhourService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyEvaluateService;
import cn.zeppin.service.IProjectApplyExpertService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.DictionyMap.ROLEENUM;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectApplyInfoOptAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager
			.getLogger(ProjectApplyInfoOptAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectApplyService iProjectApplyService;
	private IProjectTypeService iProjectTypeService;
	private IProjectService iProjectService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IAreaService iAreaService;
	private IProjectAdminService iProjectAdminService;
	private IAssignTeacherTaskService iAssignTeacherTaskService;
	private IOrganizationService iOragnizationService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private IProjectExpertService iProjectExpertService;
	private IProjectApplyExpertService iProjectApplyExpertService;
	private IProjectApplyEvaluateService iProjectApplyEvaluateService;
	private IIdentifyClasshoursService identifyClasshoursService;
	private IIdentifyClasshoursSubjectService identifyClasshoursSubjectService;
	private IIdentifyClasshoursSubjectYearService identifyClasshoursSubjectYearService;
	private IIdentifyClasshoursProjectYearService identifyClasshoursProjectYearService;
	private IIdentifyStudyhourService identifyStudyhourService;

	private String id;
	private String evaluateType;
	private String document;
	private String project; // ??????
	private String trainingSubject; // ??????
	private String trainingCollege; // ????????????
	private String type; // ????????????
	private String status; // ??????
	private String trainingStarttime; // ??????????????????
	private String trainingEndtime; // ??????????????????
	private String trainingClasshour; // ??????
	private String trainingAddress; // ????????????
	private String contacts; // ?????????
	private String phone; // ??????
	private String creator; // ?????????
	private String creattime; // ????????????
	private String approver; // ?????????
	private String approvetime; // ????????????
	private String approveNumber;// ????????????
	private String[] restrictExpertId;
	private List<String[]> restrictExpertList;

	private ProjectApply projectApply;
	private List<Map> studyhourList;
	//
	// ??????
	private String[] yearArray;
	private String year;
	private List<Project> lsProject;
	private List<ProjectExpert> lsProjectExpert;

	private String pageStatus;
	private String message;

	// ??????
//	private final String CERTRALIZE = "certralize";
//	private final String INFORMATION = "information";
//	private final String REGIONAL = "regional";
//	private final String SCHOOL = "school";

	public ProjectApplyInfoOptAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * ????????????
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public String initPage() {

		initServlert();

		String id = request.getParameter("id");
		this.id = id;

		this.pageStatus = "";
		this.message = "";
		this.year = (new Date().getYear() + 1900) + "";

		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");

		Map<String, Object> params = new HashMap<String, Object>();
		UserSession us = (UserSession) session.getAttribute("usersession");
		params.put("organization", us.getOrganization());
		params.put("level", us.getOrganizationLevel());

		params.put("status", 2);

		Map<String, String> sortParams = new HashMap<String, String>();
		sortParams.put("id", "desc");
		List li = this.iProjectService.getProjectListByParams4LevelThree(params,
				sortParams, lityps, -1, -1);
		if (li != null && li.size() > 0) {
			lsProject = new ArrayList<Project>();
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				Project pro = (Project) obj[0];
				lsProject.add(pro);
			}
		}

		// lsProject =
		// this.iProjectService.getProjectByStatus(DictionyMap.releaseProject,
		// lityps);

		if (yearArray == null) {
			int year = 2015;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}

		// ??????????????????

		if (this.id == null || this.id.equals("0") || this.id.equals("")) {
			// ??????
			projectApply = new ProjectApply();
			
		} else {
			// ??????
			projectApply = iProjectApplyService.get(Integer.parseInt(id));
			this.year = projectApply.getProject().getYear();
			this.studyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
		}

		return "init";
	}

	/**
	 * ??????????????????
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public String initPassPage() {

		initServlert();

		String id = request.getParameter("id");
		this.id = id;

		List<ProjectType> lityps = (List<ProjectType>) session
				.getAttribute("lstProjectType");

		Map<String, Object> params = new HashMap<String, Object>();
		UserSession us = (UserSession) session.getAttribute("usersession");
		params.put("organization", us.getOrganization());
		params.put("level", us.getOrganizationLevel());
		params.put("status", 2);

		Map<String, String> sortParams = new HashMap<String, String>();
		sortParams.put("id", "desc");
		List li = this.iProjectService.getProjectListByParams(params,
				sortParams, lityps, -1, -1);
		if (li != null && li.size() > 0) {
			lsProject = new ArrayList<Project>();
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				Project pro = (Project) obj[0];
				lsProject.add(pro);
			}
		}

		// lsProject =
		// this.iProjectService.getProjectByStatus(DictionyMap.releaseProject,
		// lityps);

		if (yearArray == null) {
			int year = 2015;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}

		projectApply = iProjectApplyService.get(Integer.parseInt(id));
		this.year = projectApply.getProject().getYear();
		this.studyhourList = JSONUtils.json2list(projectApply.getStudyhour(), Map.class);
		
		return "initPass";
	}

	public void getProjectTraintype() {
		initServlert();
		String id = request.getParameter("id");
		if (id != null && !id.equals("") && !id.equals("0")) {
			Project p = this.iProjectService.get(Integer.valueOf(id));
			if (p != null) {
				String json = "{\"traintype\":" + p.getTraintype() + "}";
				Utlity.ResponseWrite(json, "application/json", response);
			}
		}
	}

	/**
	 * ??????????????????
	 */
	public String initExpertPage() {
		initServlert();
		String id = request.getParameter("id");
		String evaluateType = request.getParameter("evaluateType");
		this.id = id;
		this.evaluateType = evaluateType;

		projectApply = iProjectApplyService.get(Integer.parseInt(id));
		this.year = projectApply.getProject().getYear();
		this.project = projectApply.getProject().getName();
		this.trainingSubject = projectApply.getTrainingSubject().getName();
		this.trainingCollege = projectApply.getTrainingCollege().getName();
		if (evaluateType.equals("1")) {
			Set<ProjectApplyExpert> setExpertRanges = projectApply
					.getProjectApplyExpert();
			this.restrictExpertId = new String[setExpertRanges.size()];
			this.restrictExpertList = new ArrayList<String[]>();
			int index = 0;
			for (ProjectApplyExpert pae : setExpertRanges) {
				ProjectExpert pe1 = pae.getProjectExpert();
				this.restrictExpertId[index] = pe1.getId().toString();
				String pe2 = pe1.getName();
				if (pe2.length() > 0) {
					String[] spts = pe2.split("&");
					String[] rerights = new String[spts.length + 1];
					for (int i = 0; i < spts.length; i++) {
						rerights[i] = spts[i];
					}
					rerights[spts.length] = pe1.getId().toString();
					this.restrictExpertList.add(rerights);
				}
				index++;
			}
		} else if (evaluateType.equals("2")) {
			Set<ProjectApplyEvaluate> setExpertRanges = projectApply
					.getProjectApplyEvaluate();
			this.restrictExpertId = new String[setExpertRanges.size()];
			this.restrictExpertList = new ArrayList<String[]>();
			int index = 0;
			for (ProjectApplyEvaluate pae : setExpertRanges) {
				ProjectExpert pe1 = pae.getProjectExpert();
				this.restrictExpertId[index] = pe1.getId().toString();
				String pe2 = pe1.getName();
				if (pe2.length() > 0) {
					String[] spts = pe2.split("&");
					String[] rerights = new String[spts.length + 1];
					for (int i = 0; i < spts.length; i++) {
						rerights[i] = spts[i];
					}
					rerights[spts.length] = pe1.getId().toString();
					this.restrictExpertList.add(rerights);
				}
				index++;
			}
		}
		return "initExpert";
	}

	/**
	 * ??????????????????
	 */
	public String initExpertAllPage() {
		initServlert();
		String id = request.getParameter("id");
		String evaluateType = request.getParameter("evaluateType");
		this.id = id;
		this.evaluateType = evaluateType;
		this.lsProjectExpert = this.iProjectExpertService
				.getProjectExpertList();
		return "initExpertAll";
	}

	@SuppressWarnings("rawtypes")
	public void getProjectApplyExpert() {
		initServlert();
		String paid = request.getParameter("paid");
		String evaluateType = request.getParameter("evaluateType");
		String expertName = request.getParameter("expertName");
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int startPage = Integer.parseInt(ist);
		int offset = (startPage - 1) * DictionyMap.maxPageSize;
		List liT = this.iProjectExpertService.getProjectExpert(expertName,
				"name", null, null, offset, DictionyMap.maxPageSize);
		if (liT == null || liT.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"?????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		int records = this.iProjectExpertService.getProjectExpertCount(
				expertName, "name", null, null);
		int totalPage = (int) Math.ceil((float) records
				/ DictionyMap.maxPageSize);
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
			ProjectExpert e = (ProjectExpert) obj;

			StringBuilder tb = new StringBuilder();
			tb.append("{");
			tb.append("\"id\":\"" + e.getId() + "\",");
			tb.append("\"name\":\"" + e.getName() + "\",");
			tb.append("\"phone\":\"" + e.getMobile() + "\",");
			tb.append("\"organization\":\"" + e.getOrganization() + "\",");
			tb.append("\"jobtitle\":\"" + e.getJobTitle() + "\",");
			tb.append("\"teach\":\"" + e.getTeach() + "\",");
			tb.append("\"research\":\"" + e.getResearch() + "\",");
			int paid1 = Integer.valueOf(paid);
			int eid = e.getId();
			int ttr = 0;
			if (evaluateType.equals("1")) {
				ttr = this.iProjectApplyExpertService.checkProjectApplyExpert(
						paid1, eid);
			} else if (evaluateType.equals("2")) {
				ttr = this.iProjectApplyEvaluateService
						.checkProjectApplyEvaluate(paid1, eid);
			}
			tb.append("\"record_status\":" + (ttr == 0));
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

	public void addProjectApplyExpert() {
		initServlert();
		String paid = request.getParameter("paid");
		String evaluateType = request.getParameter("evaluateType");
		String expertid = request.getParameter("id");
		ProjectApply pa = this.iProjectApplyService.get(Integer.valueOf(paid));
		ProjectExpert pe = this.iProjectExpertService.get(Integer
				.valueOf(expertid));
		if (evaluateType.equals("1")) {
			ProjectApplyExpert pae = new ProjectApplyExpert();
			pae.setProjectApply(pa);
			pae.setProjectExpert(pe);
			pae.setStatus((short) 0);
			this.iProjectApplyExpertService.add(pae);
		} else if (evaluateType.equals("2")) {
			ProjectApplyEvaluate pae = new ProjectApplyEvaluate();
			pae.setProjectApply(pa);
			pae.setProjectExpert(pe);
			pae.setStatus((short) 0);
			this.iProjectApplyEvaluateService.add(pae);
		}
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"OK\"");
		checkSB.append(",");
		checkSB.append("\"evaluateType\":\"" + evaluateType + "\"");
		checkSB.append(",");
		checkSB.append("\"paid\":\"" + paid + "\"");
		checkSB.append(",");
		checkSB.append("\"expertid\":\"" + expertid + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	public void addExpertAll() {
		initServlert();
		String paids = request.getParameter("id");
		String evaluateType = request.getParameter("evaluateType");
		String expertids = request.getParameter("expertid");

		if (evaluateType.equals("1")) {
			this.iProjectApplyExpertService.addProjectApplyExperts(paids,
					expertids);
		} else if (evaluateType.equals("2")) {
			this.iProjectApplyEvaluateService.addProjectApplyEvaluates(paids,
					expertids);
		}
		Utlity.ResponseWrite("{\"Result\":\"OK\"}", "application/json",
				response);
	}

	public void deleteProjectApplyExpert() {
		initServlert();
		String paid = request.getParameter("paid");
		String evaluateType = request.getParameter("evaluateType");
		String expertid = request.getParameter("id");
		if (evaluateType.equals("1")) {
			ProjectApplyExpert pae = this.iProjectApplyExpertService
					.getProjectApplyExpertByAll(Integer.valueOf(paid),
							Integer.valueOf(expertid));
			this.iProjectApplyExpertService.delete(pae);
		} else if (evaluateType.equals("2")) {
			ProjectApplyEvaluate pae = this.iProjectApplyEvaluateService
					.getProjectApplyEvaluateByAll(Integer.valueOf(paid),
							Integer.valueOf(expertid));
			this.iProjectApplyEvaluateService.delete(pae);
		}
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"OK\"");
		checkSB.append(",");
		checkSB.append("\"evaluateType\":\"" + evaluateType + "\"");
		checkSB.append(",");
		checkSB.append("\"paid\":\"" + paid + "\"");
		checkSB.append(",");
		checkSB.append("\"expertid\":\"" + expertid + "\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
	}

	public void getExpertListForPage() {
		initServlert();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		List<ProjectExpert> list = this.iProjectExpertService
				.getProjectExpertList();
		for (ProjectExpert pe : list) {
			int peid = pe.getId();
			String sr = "{\"id\":" + peid + ",\"name\":\"" + pe.getName()
					+ "\"},";
			sb.append(sr);
		}
		if (list.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * ???????????? 9???8?????????4???????????????????????????
	 * 
	 * @category ??????????????????
	 */
	@SuppressWarnings({ "unused" })
	public void addProjectApply() {
		// ????????????????????????
		initServlert();

		try {
			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";
			String document = parMap.containsKey("document") ? parMap
					.get("document")[0] : "";
			String project = parMap.containsKey("project") ? parMap
					.get("project")[0] : ""; // ??????

			String trainingSubject = parMap.get("trainingSubject")[0]; // ??????
			String trainingCollege = parMap.get("trainingCollege")[0]; // ????????????

			String type = parMap.containsKey("type") ? parMap.get("type")[0]
					: ""; // ????????????
			String status = parMap.containsKey("status") ? parMap.get("status")[0]
					: ""; // ??????

			String enrollEndTime = "";
			if (parMap.get("enrollEndTime") != null) {
				enrollEndTime = parMap.get("enrollEndTime")[0];
			}
			// String
			String trainingStarttime = parMap.get("trainingStarttime")[0]; // ??????????????????
			String trainingEndtime = parMap.get("trainingEndtime")[0]; // ??????????????????
//			String trainingClasshour = parMap.get("trainingClasshour")[0]; // ??????
//			String trainingOnlineHour = parMap.get("trainingOnlineHour")[0]; // ??????
			
			//20160926??????????????????
			String remark = parMap.get("remark") == null ? "" : parMap.get("remark")[0];

			String trainingAddress = parMap.containsKey("trainingAddress") ? parMap
					.get("trainingAddress")[0] : ""; // ????????????
			String contacts = parMap.get("contacts")[0]; // ?????????
			String phone = parMap.get("phone")[0]; // ??????

			String creator = parMap.containsKey("creator") ? parMap
					.get("creator")[0] : ""; // ?????????
			String creattime = parMap.containsKey("creattime") ? parMap
					.get("creattime")[0] : ""; // ????????????

			String approver = parMap.containsKey("approver") ? parMap
					.get("approver")[0] : ""; // ?????????
			String approvetime = parMap.containsKey("approvetime") ? parMap
					.get("approvetime")[0] : ""; // ????????????
			String approveNumber = parMap.get("approveNumber")[0];// ????????????

			// ??????????????????
			if (project == null || project.equals("0") || project.equals("")) {
				// ??????????????????
				Utlity.sendResponse("ERROR", "??????????????????", response);
				return;
			}

			if (trainingStarttime == null || trainingStarttime.equals("")
					|| trainingStarttime.equals("0")) {
				Utlity.sendResponse("ERROR", "??????????????????????????????", response);
				return;
			}

			if (trainingEndtime == null || trainingEndtime.equals("")
					|| trainingEndtime.equals("0")) {
				Utlity.sendResponse("ERROR", "??????????????????????????????", response);
				return;
			}

			if (approveNumber == null || approveNumber.equals("")) {
				Utlity.sendResponse("ERROR", "????????????????????????", response);
				return;
			}

			// ??????
			Project p = iProjectService.get(Integer.valueOf(project));

//			if (p.getTraintype() == 1 || p.getTraintype() == 3) {
//				if (trainingClasshour == null || trainingClasshour.equals("")) {
//					Utlity.sendResponse("ERROR", "??????????????????????????????", response);
//					return;
//				}
//			}
//			if (p.getTraintype() == 2 || p.getTraintype() == 3) {
//				if (trainingOnlineHour == null || trainingOnlineHour.equals("")) {
//					Utlity.sendResponse("ERROR", "??????????????????????????????", response);
//					return;
//				}
//			}

			ProjectApply pa = new ProjectApply();
			ProjectApply paa = new ProjectApply();
			if (!id.equals("") && id != null) {
				pa = iProjectApplyService.get(Integer.parseInt(id));
				paa = pa;
			}

			pa.setProject(p);
			pa.setProjectCycle(p.getProjectCycle());

			// ????????????
			TrainingCollege tc = iTrainingCollegeService.get(Integer
					.valueOf(trainingCollege));
			pa.setTrainingCollege(tc);

			// ????????????
			TrainingSubject ts = iTrainingSubjectService.get(Short
					.valueOf(trainingSubject));
			pa.setTrainingSubject(ts);

			// ???????????????????????? ??? ????????????
			pa.setTrainingStarttime(Timestamp.valueOf(trainingStarttime
					+ " 00:00:00"));
			pa.setTrainingEndtime(Timestamp.valueOf(trainingEndtime
					+ " 00:00:00"));

			if (enrollEndTime != null && !enrollEndTime.equals("")
					&& !enrollEndTime.equals("0")) {
				pa.setEnrollEndTime(Timestamp.valueOf(enrollEndTime
						+ " 00:00:00"));
			}

			// if(pa.getTrainingStarttime().after(pa.getCreattime())) {
			// Utlity.sendResponse("ERROR", "???????????? ", response);
			// return;
			// }
//			if (p.getTraintype() == 1) {
//				pa.setTrainingClasshour(Integer.valueOf(trainingClasshour));
//				pa.setTrainingOnlineHour(0);
//			} else if (p.getTraintype() == 2) {
//				pa.setTrainingClasshour(0);
//				pa.setTrainingOnlineHour(Integer.valueOf(trainingOnlineHour));
//			} else {
//				pa.setTrainingClasshour(Integer.valueOf(trainingClasshour));
//				pa.setTrainingOnlineHour(Integer.valueOf(trainingOnlineHour));
//			}
			pa.setTrainingClasshour(0);
			pa.setTrainingOnlineHour(0);

			// ????????????
			pa.setApproveNumber(Integer.valueOf(approveNumber));

			pa.setContacts(contacts);
			pa.setPhone(phone);

			// ?????????
			UserSession us = (UserSession) session.getAttribute("usersession");
			Organization organization = iOragnizationService.get(us
					.getOrganization());
			// ?????????
			pa.setApprover(us.getId());
			// ?????????????????????
			List<Map> studyhourList = new ArrayList<Map>();
			
			Map<String, Object> paramsw = new  HashMap<String, Object>();
			if (p != null) {
				// ??????
				paramsw.put("projectType", p.getProjectType().getId());
				paramsw.put("project", p.getId());
			}
			if (year != null && !"".equals(year)) {
				// ????????????
				paramsw.put("year", year);
			}
			if (trainingSubject != null && !"".equals(trainingSubject)) {
				// ????????????
				paramsw.put("trainingSubject", trainingSubject);
			}
			paramsw.put("status", 1);//????????????????????????????????????
			
			int credit = 0;
			List<IdentifyStudyhour> listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
			if(listih != null && listih.size() > 0){
				IdentifyStudyhour is = listih.get(0);
				studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
				credit = is.getCredit();
			}else{
				paramsw.remove("trainingSubject");
				listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
				if(listih != null && listih.size() > 0){
					IdentifyStudyhour is = listih.get(0);
					studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
					credit = is.getCredit();
				}else{
					if (trainingSubject != null && !"".equals(trainingSubject)) {
						paramsw.put("trainingSubject", trainingSubject);
					}
					paramsw.remove("project");
					listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
					if(listih != null && listih.size() > 0){
						IdentifyStudyhour is = listih.get(0);
						studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
						credit = is.getCredit();
					}else{
						paramsw.remove("year");
						listih = this.identifyStudyhourService.getListByParams(paramsw, 0, -1, "createtime");
						if (listih != null && listih.size() > 0) {
							IdentifyStudyhour is = listih.get(0);
							studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
							credit = is.getCredit();
						} else {
							if (year != null && !"".equals(year)) {
								// ????????????
								paramsw.put("year", year);
							}
							paramsw.remove("trainingSubject");
							listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "id");
							if (listih != null && listih.size() > 0) {
								IdentifyStudyhour is = listih.get(0);
								studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
								credit = is.getCredit();
							} else {
								paramsw.remove("year");
								listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "id");
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
			
			List<Map> resultList = new ArrayList<Map>();
			for(Map map :studyhourList){
				String name = map.get("name").toString();
				String nameCN = map.get("nameCN").toString();
				Integer value1 = Integer.valueOf(map.get("value").toString());
				String value = "0";
				if(parMap.get(name) != null) {
					value = "".equals(parMap.get(name)[0]) ? "0" : parMap.get(name)[0];
				}
				
				
				if (isNumeric(value) && value.length() <= 4) {
					
					if(Integer.parseInt(value) <= value1){
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("name", name);
						resultMap.put("nameCN", nameCN);
						resultMap.put("value", Integer.parseInt(value));
						resultList.add(resultMap);
					}else {
						Utlity.sendResponse("ERROR", "???" + nameCN + "?????????????????????????????????????????????"+ value1, response);
						return;
					}
				} else {
					Utlity.sendResponse("ERROR", "???" + nameCN + "???????????????????????????4???????????????????????????????????????",
							response);
					return;
				}
			}
			pa.setStudyhour(JSONUtils.obj2json(resultList));
			pa.setRemark(remark.trim());//??????????????????
			
			//????????????
			pa.setCredit(credit);
			
			if (!id.equals("") && id != null) {// ??????
				//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????---?????????????????? ????????????
//				ProjectApply paa = this.iProjectApplyService.get(Integer.parseInt(id));
//				if(paa.getId() != null){
//					if(paa.getProject().getId() != p.getId()){
//						p = paa.getProject();
//					}
//					if(paa.getTrainingCollege().getId() != tc.getId()){
//						tc = paa.getTrainingCollege();
//					}
//					if(paa.getTrainingSubject().getId() != ts.getId()){
//						ts = paa.getTrainingSubject();
//					}
//				}
				pa.setId(Integer.parseInt(id));
				iProjectApplyService.update(pa);

				// if(p.getEnrollType() == 1){

				//?????????????????? ???????????????????????????
//				if(organization.getOrganizationLevel().getId() == 1){
					// ?????????????????????
//					AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService
//							.get(p.getId().toString(), ts.getId().toString(), tc
//									.getId().toString(), (short) 1);
					AssignTeacherTask assignTeacherTask = null;
					List<Integer> attList = new ArrayList<Integer>();
					boolean flag = this.iAssignTeacherTaskService.exist(p.getId(), ts.getId(), tc.getId(), us.getOrganization(), us.getOrganization(), "1", attList);
					if(flag){
						assignTeacherTask = this.iAssignTeacherTaskService.get(attList.get(0));
					}
					
					boolean isupdate = true;
					if (assignTeacherTask == null) {
						assignTeacherTask = new AssignTeacherTask();
						isupdate = false;
					}
					assignTeacherTask.setProject(p);
					assignTeacherTask.setTrainingSubject(ts);
					assignTeacherTask.setTrainingCollege(tc);
					assignTeacherTask.setOrganizationByGOrganization(organization);
					assignTeacherTask.setOrganizationByPOrganization(organization);
					assignTeacherTask.setLevel((short) 1);

					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, 30);
					String v = Utlity.timeSpanToDateString(c.getTime())
							+ " 23:59:59";
					Timestamp ts1 = Timestamp.valueOf(v);
					
					assignTeacherTask.setTeacherNumber(Integer
							.valueOf(approveNumber));
					assignTeacherTask.setReceiveFlag((short) 0);
					assignTeacherTask.setCreator(us.getId());
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String time = df.format(new Date());
					Timestamp ts2 = Timestamp.valueOf(time);
					
					if (isupdate) {
						iAssignTeacherTaskService.update(assignTeacherTask);
					} else {
						assignTeacherTask.setStatus((short) 1);
						assignTeacherTask.setCreattime(ts2);
						assignTeacherTask.setTimeup(ts1);
						iAssignTeacherTaskService.add(assignTeacherTask);
					}
//				}
				
				// }
				Utlity.sendResponse("OK", "????????????", response);
			} else {// ??????

//				//????????????
//				pa.setCredit(credit);
				
				pa.setCreator(us.getId());
				pa.setStatus((short) 2);
				pa.setType((short) 2);
				// 9???8?????????4?????????
				// Map<String, Integer> classHoursForTeacher =
				// getClassHoursForTeacher(
				// p.getProjectType(), ts);
				// if (classHoursForTeacher.size() > 0) {
				// pa.setCentralize(classHoursForTeacher.get(CERTRALIZE));
				// pa.setInformation(classHoursForTeacher.get(INFORMATION));
				// pa.setRegional(classHoursForTeacher.get(REGIONAL));
				// pa.setSchool(classHoursForTeacher.get(SCHOOL));
				// }

				iProjectApplyService.add(pa);

				// if(p.getEnrollType() == 1){

				//?????????????????? ???????????????????????????
//				if(organization.getOrganizationLevel().getId() == 1){
					// ???????????????????????????
					AssignTeacherTask assignTeacherTask = new AssignTeacherTask();
					assignTeacherTask.setProject(p);
					assignTeacherTask.setTrainingSubject(ts);
					assignTeacherTask.setTrainingCollege(tc);
					assignTeacherTask.setOrganizationByGOrganization(organization);
					assignTeacherTask.setOrganizationByPOrganization(organization);
					assignTeacherTask.setLevel((short) 1);

					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, 30);
					String v = Utlity.timeSpanToDateString(c.getTime())
							+ " 23:59:59";
					Timestamp ts1 = Timestamp.valueOf(v);
					assignTeacherTask.setTimeup(ts1);
					assignTeacherTask.setStatus((short) 1);
					assignTeacherTask.setTeacherNumber(Integer
							.valueOf(approveNumber));
					assignTeacherTask.setReceiveFlag((short) 0);
					assignTeacherTask.setCreator(us.getId());
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String time = df.format(new Date());
					Timestamp ts2 = Timestamp.valueOf(time);
					assignTeacherTask.setCreattime(ts2);
					iAssignTeacherTaskService.add(assignTeacherTask);
//				}
				
				// }
				Utlity.sendResponse("OK", "????????????", response);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			Utlity.sendResponse("ERROR", "???????????????????????????????????????????????????????????????", response);
			return;

		}
	}

	public void delete() {
		// ????????????????????????
		initServlert();
		Map<String, String[]> parMap = request.getParameterMap();
		String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization organization = iOragnizationService.get(us.getOrganization());

		if (id.equals(null) || id.equals("")) {
			Utlity.sendResponse("ERROR", "ID??????????????????????????????????????????", response);
			return;
		}

		if (us.getRole() != ROLEENUM.ADMIN.getValue()) {
			Utlity.sendResponse("ERROR", "?????????????????????????????????????????????", response);
			return;
		}

		projectApply = iProjectApplyService.get(Integer.parseInt(id));
		Integer projectId = projectApply.getProject().getId();
		Integer trainingSubjectId = Integer.valueOf(projectApply
				.getTrainingSubject().getId());
		Integer trainingUnitId = Integer.valueOf(projectApply
				.getTrainingCollege().getId());

		AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService.get(
				projectId.toString(), trainingSubjectId.toString(),
				trainingUnitId.toString(), (short) 1);
		List<Integer> attList = new ArrayList<Integer>();
		boolean flag = this.iAssignTeacherTaskService.exist(projectId, projectApply
				.getTrainingSubject().getId(), trainingUnitId, us.getOrganization(), us.getOrganization(), "1", attList);
		if(flag){
			assignTeacherTask = this.iAssignTeacherTaskService.get(attList.get(0));
		}

		if (assignTeacherTask != null
				&& assignTeacherTask.getAssignTeacherTasks().size() > 0) {
			Utlity.sendResponse("ERROR", "????????????????????????????????????????????????????????????????????????????????????",
					response);
			return;
		}

		int ttrCount = iTeacherTrainingRecordsService.getAduTeacherCount(
				projectId, trainingSubjectId, trainingUnitId, -1, 0);

		if (ttrCount > 0) {
			Utlity.sendResponse("ERROR", "????????????????????????/????????????????????????????????????????????????", response);
			return;
		}
		// assignTeacherTask==null????????????????????????
		if (assignTeacherTask != null)
			iAssignTeacherTaskService.delete(assignTeacherTask);
		// ????????????????????????
		iProjectApplyService.delete(projectApply);
		// ?????????????????????????????????????????????????????????????????????????????????????????????????????????
		Utlity.sendResponse("OK", "????????????", response);

	}

	/**
	 * ????????????
	 */
	public void passProjectApply() {
		initServlert();
		try {

			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";
			String project = parMap.containsKey("project") ? parMap
					.get("project")[0] : ""; // ??????

			String trainingSubject = parMap.get("trainingSubject")[0]; // ??????
			String trainingCollege = parMap.get("trainingCollege")[0]; // ????????????

			String trainingStarttime = parMap.get("trainingStarttime")[0]; // ??????????????????
			String trainingEndtime = parMap.get("trainingEndtime")[0]; // ??????????????????
//			String trainingClasshour = parMap.get("trainingClasshour")[0]; // ??????
//			String trainingOnlineHour = parMap.get("trainingOnlineHour")[0]; // ??????
			
			String contacts = parMap.get("contacts")[0]; // ?????????
			String phone = parMap.get("phone")[0]; // ??????

			String approveNumber = parMap.get("approveNumber")[0];// ????????????

			// ??????????????????
			if (project == null || project.equals("0") || project.equals("")) {
				// ??????????????????
				Utlity.sendResponse("ERROR", "??????????????????", response);
				return;
			}

			// if (trainingStarttime == null || trainingStarttime.equals("") ||
			// trainingStarttime.equals("0")) {
			// Utlity.sendResponse("ERROR", "??????????????????????????????", response);
			// return;
			// }

			if (trainingEndtime == null || trainingEndtime.equals("")
					|| trainingEndtime.equals("0")) {
				Utlity.sendResponse("ERROR", "??????????????????????????????", response);
				return;
			}

			if (approveNumber == null || approveNumber.equals("")) {
				Utlity.sendResponse("ERROR", "????????????????????????", response);
				return;
			}

			// ??????
			Project p = iProjectService.get(Integer.valueOf(project));

			String remark = parMap.get("remark") == null ? "" : parMap.get("remark")[0];

			ProjectApply pa = this.iProjectApplyService.get(Integer
					.parseInt(id));
			
			// ?????????????????????
			List<Map> studyhourList = new ArrayList<Map>();
			
			Map<String, Object> paramsw = new  HashMap<String, Object>();
			if (p != null) {
				// ??????
				paramsw.put("projectType", p.getProjectType().getId());
				paramsw.put("project", p.getId());
			}
			if (year != null && !"".equals(year)) {
				// ????????????
				paramsw.put("year", year);
			}
			if (trainingSubject != null && !"".equals(trainingSubject)) {
				// ????????????
				paramsw.put("trainingSubject", trainingSubject);
			}
			paramsw.put("status", 1);//????????????????????????????????????
			
			int credit = 0;
			List<IdentifyStudyhour> listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
			if(listih != null && listih.size() > 0){
				IdentifyStudyhour is = listih.get(0);
				studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
				credit = is.getCredit();
			}else{
				paramsw.remove("trainingSubject");
				listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
				if(listih != null && listih.size() > 0){
					IdentifyStudyhour is = listih.get(0);
					studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
					credit = is.getCredit();
				}else{
					if (trainingSubject != null && !"".equals(trainingSubject)) {
						paramsw.put("trainingSubject", trainingSubject);
					}
					paramsw.remove("project");
					listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
					if(listih != null && listih.size() > 0){
						IdentifyStudyhour is = listih.get(0);
						studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
						credit = is.getCredit();
					}else{
						paramsw.remove("year");
						listih = this.identifyStudyhourService.getListByParams(paramsw, 0, -1, "createtime");
						if (listih != null && listih.size() > 0) {
							IdentifyStudyhour is = listih.get(0);
							studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
							credit = is.getCredit();
						} else {
							if (year != null && !"".equals(year)) {
								// ????????????
								paramsw.put("year", year);
							}
							paramsw.remove("trainingSubject");
							listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "id");
							if (listih != null && listih.size() > 0) {
								IdentifyStudyhour is = listih.get(0);
								studyhourList = JSONUtils.json2list(is.getStudyhour(), Map.class);
								credit = is.getCredit();
							} else {
								paramsw.remove("year");
								listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "id");
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
			
			List<Map> resultList = new ArrayList<Map>();
			for(Map map :studyhourList){
				String name = map.get("name").toString();
				String nameCN = map.get("nameCN").toString();
				Integer value1 = Integer.valueOf(map.get("value").toString());
				String value = "0";
				if(parMap.get(name) != null) {
					value = "".equals(parMap.get(name)[0]) ? "0" : parMap.get(name)[0];
				}
				
				if (isNumeric(value) && value.length() <= 4) {
					
					if(Integer.parseInt(value) <= value1){
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("name", name);
						resultMap.put("nameCN", nameCN);
						resultMap.put("value", Integer.parseInt(value));
						resultList.add(resultMap);
					}else {
						Utlity.sendResponse("ERROR", "???" + nameCN + "?????????????????????????????????????????????"+ value1, response);
						return;
					}
				} else {
					Utlity.sendResponse("ERROR", "???" + nameCN + "???????????????????????????4???????????????????????????????????????",
							response);
					return;
				}
			}
			pa.setStudyhour(JSONUtils.obj2json(resultList));
			pa.setCredit(credit);
			
			pa.setProject(p);
			pa.setProjectCycle(p.getProjectCycle());
			pa.setRemark(remark.trim());//??????????????????
			// ????????????
			TrainingCollege tc = iTrainingCollegeService.get(Integer
					.valueOf(trainingCollege));
			pa.setTrainingCollege(tc);

			// ????????????
			TrainingSubject ts = iTrainingSubjectService.get(Short
					.valueOf(trainingSubject));
			pa.setTrainingSubject(ts);

			// ???????????????????????? ??? ????????????
			if (trainingStarttime != null && !trainingStarttime.equals("")
					&& !trainingStarttime.equals("0")) {
				pa.setTrainingStarttime(Timestamp.valueOf(trainingStarttime
						+ " 00:00:00"));
			}
			pa.setTrainingEndtime(Timestamp.valueOf(trainingEndtime
					+ " 00:00:00"));

			pa.setStatus((short) 2);

//			if (p.getTraintype() == 1) {
//				pa.setTrainingClasshour(Integer.valueOf(trainingClasshour));
//				pa.setTrainingOnlineHour(0);
//			} else if (p.getTraintype() == 2) {
//				pa.setTrainingClasshour(0);
//				pa.setTrainingOnlineHour(Integer.valueOf(trainingOnlineHour));
//			} else {
//				pa.setTrainingClasshour(Integer.valueOf(trainingClasshour));
//				pa.setTrainingOnlineHour(Integer.valueOf(trainingOnlineHour));
//			}
			//?????????????????????????????????????????????0
			pa.setTrainingClasshour(0);
			pa.setTrainingOnlineHour(0);

			// ????????????
			pa.setApproveNumber(Integer.valueOf(approveNumber));

			pa.setContacts(contacts);
			pa.setPhone(phone);

			UserSession us = (UserSession) session.getAttribute("usersession");
			// ?????????
			pa.setApprover(us.getId());

			Organization organization = iOragnizationService.get(us
					.getOrganization());

			// if(pa.getProject().getEnrollType() == 2){//?????????????????????????????????????????????
			// iProjectApplyService.update(pa);
			//
			// Utlity.sendResponse("OK", "????????????", response);
			// return;
			// }
			//?????????????????? ???????????????????????????
			if(organization.getOrganizationLevel().getId() == 1){
				// ?????????????????????
				AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService
						.get(p.getId().toString(), ts.getId().toString(), tc
								.getId().toString(), (short) 1);
				boolean isupdate = true;
				if (assignTeacherTask == null) {
					assignTeacherTask = new AssignTeacherTask();
					isupdate = false;
				}
				assignTeacherTask.setProject(p);
				assignTeacherTask.setTrainingSubject(ts);
				assignTeacherTask.setTrainingCollege(tc);
				assignTeacherTask.setOrganizationByGOrganization(organization);
				assignTeacherTask.setOrganizationByPOrganization(organization);
				assignTeacherTask.setLevel((short) 1);

				Calendar c = Calendar.getInstance();
				c.add(Calendar.DAY_OF_MONTH, 30);
				String v = Utlity.timeSpanToDateString(c.getTime()) + " 23:59:59";
				Timestamp ts1 = Timestamp.valueOf(v);

				assignTeacherTask.setTimeup(ts1);
				assignTeacherTask.setTeacherNumber(Integer.valueOf(approveNumber));
				assignTeacherTask.setReceiveFlag((short) 0);
				assignTeacherTask.setCreator(us.getId());

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = df.format(new Date());
				Timestamp ts2 = Timestamp.valueOf(time);

				

				if (isupdate) {
					iAssignTeacherTaskService.update(assignTeacherTask);
				} else {
					assignTeacherTask.setStatus((short) 1);
					assignTeacherTask.setCreattime(ts2);
					assignTeacherTask.setTimeup(ts1);
					iAssignTeacherTaskService.add(assignTeacherTask);
				}
			}

			iProjectApplyService.update(pa);

			Utlity.sendResponse("OK", "????????????", response);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			Utlity.sendResponse("ERROR", "????????????", response);
			return;

		}
	}

	/**
	 * ???????????????
	 */
	@SuppressWarnings("unused")
	public void passNoProjectApply() {
		initServlert();
		try {
			/**
			 * ????????????????????????????????????????????????????????????????????????????????????????????????
			 */
			Map<String, String[]> parMap = request.getParameterMap();

			String id = parMap.containsKey("id") ? parMap.get("id")[0] : "";

			ProjectApply pa = this.iProjectApplyService.get(Integer.parseInt(id));
			UserSession us = (UserSession) session.getAttribute("usersession");
			Organization organization = iOragnizationService.get(us.getOrganization());

			// if(pa.getProject().getEnrollType() == 2){
			// pa.setStatus((short) 0);
			// pa.setApprover(us.getId());
			// iProjectApplyService.update(pa);
			//
			// Utlity.sendResponse("OK", "???????????????", response);
			// return;
			// }
			// ?????????????????????
			AssignTeacherTask assignTeacherTask = iAssignTeacherTaskService.get(pa.getProject().getId().toString(), 
					pa.getTrainingSubject().getId().toString(), pa.getTrainingCollege().getId().toString(), (short) 1);

			if (assignTeacherTask != null && assignTeacherTask.getAssignTeacherTasks().size() > 0) {
				// ???????????????????????????
				Utlity.sendResponse("ERROR", "?????????????????????????????????????????????!", response);
			} else {

				// ??????????????????
				if (assignTeacherTask != null) {
					iAssignTeacherTaskService.delete(assignTeacherTask);
				}

				pa.setStatus((short) 0);
				pa.setApprover(us.getId());
				iProjectApplyService.update(pa);

				Utlity.sendResponse("OK", "???????????????", response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			Utlity.sendResponse("ERROR", "????????????", response);
			return;

		}
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

	/**
	 * ?????????????????????
	 * 
	 * @param projectTypeID
	 *            ????????????id
	 * @param tsID
	 *            ????????????id
	 */
	public void getClassHoursForTeacher() {
		initServlert();
		String project = request.getParameter("project");
		String trainingSubject = request.getParameter("trainingSubject");
		String year = request.getParameter("year");
		
		String studyhour = "[]";
		
		Map<String, Object> paramsw = new  HashMap<String, Object>();
		if (project != null && !"".equals(project)) {
			if("0".equals(project)){
				paramsw.put("projectType", 0);
				paramsw.put("project", 0);
			}else{
				// ??????
				Project p = iProjectService.get(Integer.valueOf(project));

				paramsw.put("projectType", p.getProjectType().getId());
				paramsw.put("project", p.getId());
			}
		}
		if (year != null && !"".equals(year)) {
			// ????????????
			paramsw.put("year", year);
		}
		if (trainingSubject != null && !"".equals(trainingSubject)) {
			// ????????????
			paramsw.put("trainingSubject", trainingSubject);
		}
		paramsw.put("status", 1);//????????????????????????????????????
		
		int credit = 0;
		List<IdentifyStudyhour> listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
		if(listih != null && listih.size() > 0){
			IdentifyStudyhour is = listih.get(0);
			studyhour = is.getStudyhour();
			credit = is.getCredit();
		}else{
			paramsw.remove("trainingSubject");
			listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
			if(listih != null && listih.size() > 0){
				IdentifyStudyhour is = listih.get(0);
				studyhour = is.getStudyhour();
				credit = is.getCredit();
			}else{
				if (trainingSubject != null && !"".equals(trainingSubject)) {
					paramsw.put("trainingSubject", trainingSubject);
				}
				paramsw.remove("project");
				listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "createtime");
				if(listih != null && listih.size() > 0){
					IdentifyStudyhour is = listih.get(0);
					studyhour = is.getStudyhour();
					credit = is.getCredit();
				}else{
					paramsw.remove("year");
					listih = this.identifyStudyhourService.getListByParams(paramsw, 0, -1, "createtime");
					if (listih != null && listih.size() > 0) {
						IdentifyStudyhour is = listih.get(0);
						studyhour = is.getStudyhour();
						credit = is.getCredit();
					} else {
						if (year != null && !"".equals(year)) {
							// ????????????
							paramsw.put("year", year);
						}
						paramsw.remove("trainingSubject");
						listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "id");
						if (listih != null && listih.size() > 0) {
							IdentifyStudyhour is = listih.get(0);
							studyhour = is.getStudyhour();
							credit = is.getCredit();
						} else {
							paramsw.remove("year");
							listih = this.identifyStudyhourService.getListByParams(paramsw, -1, -1, "id");
							if (listih != null && listih.size() > 0) {
								IdentifyStudyhour is = listih.get(0);
								studyhour = is.getStudyhour();
								credit = is.getCredit();
							} 
						}
					}
				}
			}
		}
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("{");
		sBuilder.append("\"studyhour\":" + studyhour);
		sBuilder.append("}");
		Utlity.ResponseWrite(sBuilder.toString(), "application/json", response);
	}

	/**
	 * ?????????????????? ????????????????????????????????????
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+$");
		return pattern.matcher(str).matches();
	}

	// ?????????

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(
			IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(
			ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(
			ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(
			IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getTrainingSubject() {
		return trainingSubject;
	}

	public void setTrainingSubject(String trainingSubject) {
		this.trainingSubject = trainingSubject;
	}

	public String getTrainingCollege() {
		return trainingCollege;
	}

	public void setTrainingCollege(String trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrainingStarttime() {
		return trainingStarttime;
	}

	public void setTrainingStarttime(String trainingStarttime) {
		this.trainingStarttime = trainingStarttime;
	}

	public String getTrainingEndtime() {
		return trainingEndtime;
	}

	public void setTrainingEndtime(String trainingEndtime) {
		this.trainingEndtime = trainingEndtime;
	}

	public String getTrainingClasshour() {
		return trainingClasshour;
	}

	public void setTrainingClasshour(String trainingClasshour) {
		this.trainingClasshour = trainingClasshour;
	}

	public String getTrainingAddress() {
		return trainingAddress;
	}

	public void setTrainingAddress(String trainingAddress) {
		this.trainingAddress = trainingAddress;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApprovetime() {
		return approvetime;
	}

	public void setApprovetime(String approvetime) {
		this.approvetime = approvetime;
	}

	public String getApproveNumber() {
		return approveNumber;
	}

	public void setApproveNumber(String approveNumber) {
		this.approveNumber = approveNumber;
	}

	public String[] getYearArray() {
		return yearArray;
	}

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the iAssignTeacherTaskService
	 */
	public IAssignTeacherTaskService getiAssignTeacherTaskService() {
		return iAssignTeacherTaskService;
	}

	/**
	 * @param iAssignTeacherTaskService
	 *            the iAssignTeacherTaskService to set
	 */
	public void setiAssignTeacherTaskService(
			IAssignTeacherTaskService iAssignTeacherTaskService) {
		this.iAssignTeacherTaskService = iAssignTeacherTaskService;
	}

	/**
	 * @return the iOragnizationService
	 */
	public IOrganizationService getiOragnizationService() {
		return iOragnizationService;
	}

	/**
	 * @param iOragnizationService
	 *            the iOragnizationService to set
	 */
	public void setiOragnizationService(
			IOrganizationService iOragnizationService) {
		this.iOragnizationService = iOragnizationService;
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

	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(
			IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}

	public IProjectApplyExpertService getiProjectApplyExpertService() {
		return iProjectApplyExpertService;
	}

	public void setiProjectApplyExpertService(
			IProjectApplyExpertService iProjectApplyExpertService) {
		this.iProjectApplyExpertService = iProjectApplyExpertService;
	}

	public IProjectApplyEvaluateService getiProjectApplyEvaluateService() {
		return iProjectApplyEvaluateService;
	}

	public void setiProjectApplyEvaluateService(
			IProjectApplyEvaluateService iProjectApplyEvaluateService) {
		this.iProjectApplyEvaluateService = iProjectApplyEvaluateService;
	}

	public List<Project> getLsProject() {
		return lsProject;
	}

	public void setLsProject(List<Project> lsProject) {
		this.lsProject = lsProject;
	}

	public List<ProjectExpert> getLsProjectExpert() {
		return lsProjectExpert;
	}

	public void setLsProjectExpert(List<ProjectExpert> lsProjectExpert) {
		this.lsProjectExpert = lsProjectExpert;
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

	public String[] getRestrictExpertId() {
		return restrictExpertId;
	}

	public void setRestrictExpertId(String[] restrictExpertId) {
		this.restrictExpertId = restrictExpertId;
	}

	public List<String[]> getRestrictExpertList() {
		return restrictExpertList;
	}

	public void setRestrictExpertList(List<String[]> restrictExpertList) {
		this.restrictExpertList = restrictExpertList;
	}

	public IIdentifyClasshoursSubjectService getIdentifyClasshoursSubjectService() {
		return identifyClasshoursSubjectService;
	}

	public void setIdentifyClasshoursSubjectService(
			IIdentifyClasshoursSubjectService identifyClasshoursSubjectService) {
		this.identifyClasshoursSubjectService = identifyClasshoursSubjectService;
	}

	public IIdentifyClasshoursService getIdentifyClasshoursService() {
		return identifyClasshoursService;
	}

	public void setIdentifyClasshoursService(
			IIdentifyClasshoursService identifyClasshoursService) {
		this.identifyClasshoursService = identifyClasshoursService;
	}

	
	public IIdentifyClasshoursSubjectYearService getIdentifyClasshoursSubjectYearService() {
		return identifyClasshoursSubjectYearService;
	}
	

	public void setIdentifyClasshoursSubjectYearService(IIdentifyClasshoursSubjectYearService identifyClasshoursSubjectYearService) {
		this.identifyClasshoursSubjectYearService = identifyClasshoursSubjectYearService;
	}

	public IIdentifyClasshoursProjectYearService getIdentifyClasshoursProjectYearService() {
		return identifyClasshoursProjectYearService;
	}

	public void setIdentifyClasshoursProjectYearService(IIdentifyClasshoursProjectYearService identifyClasshoursProjectYearService) {
		this.identifyClasshoursProjectYearService = identifyClasshoursProjectYearService;
	}

	public IIdentifyStudyhourService getIdentifyStudyhourService() {
		return identifyStudyhourService;
	}

	public void setIdentifyStudyhourService(
			IIdentifyStudyhourService identifyStudyhourService) {
		this.identifyStudyhourService = identifyStudyhourService;
	}

	public List<Map> getStudyhourList() {
		return studyhourList;
	}

	public void setStudyhourList(List<Map> studyhourList) {
		this.studyhourList = studyhourList;
	}
}
