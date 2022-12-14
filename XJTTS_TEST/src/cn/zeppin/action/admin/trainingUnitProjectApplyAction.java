/**
 * 
 */
package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectCollegeRange;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.entity.teachingSubjectEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectCollegeRangeService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectSubjectRangeService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;
import cn.zeppin.utility.pinyingUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author sj
 * 
 */
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
	private ITrainingAdminService iTrainingAdminService;
	private IDocumentService iDocumentService;
	private IProjectSubjectRangeService iProjectSubjectRangeService;
	private IProjectCollegeRangeService iProjectCollegeRangeService;

	private LinkedHashMap<Integer, String[]> projectHash;

	private List<TrainingSubject> lsttTrainingSubjects = new ArrayList<TrainingSubject>();// ?????????
	private List<teachingSubjectEx> lstteacTeachingSubjectExs = new ArrayList<>();// ????????????ex
	private HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
	private List<Project> lstProjects = new ArrayList<>();

	private Project project;
	private ProjectApply projectApply;
	private String pageStatus;// ????????????
	private String message;// ????????????
	private String id = "";
	private Document document;
	private String collegeId;
	private List<String> searchYear;
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @throws ParseException
	 * @category ??????
	 */
	@SuppressWarnings("unchecked")
	public String initPage() throws ParseException {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		TrainingAdmin trainingAdmin = iTrainingAdminService.get(user.getId());
		// ?????????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// ????????????
		Map<String, Object> params = new HashMap<>();
		Map<String, String> sortParams = new HashMap<>();

		// ????????????????????????????????????

		// ??????
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
		List li = this.iProjectService.getProjectForTrainingCollege(params,sortParams,trainingAdmin.getTrainingCollege().getId(), date, offset,DictionyMap.maxPageSize);		
		this.projectHash = new LinkedHashMap<Integer, String[]>();
		this.collegeId=user.getOrganization()+"";
		DecimalFormat df1 = new DecimalFormat("0.00");
		for(int i=0 ;i<li.size();i++){
			Object[] ob=(Object[]) li.get(i);
			Project pro =(Project)ob[0];
			int id = pro.getId();
			if (!this.projectHash.containsKey(id)) {
				String[] sv = new String[21];
				sv[0] = pro.getName();
				switch (pro.getStatus()) {
				case 1:
					sv[1] = "?????????";
					break;
				case 2:
					sv[1] = "?????????";
					break;
				case 3:
					sv[1] = "??????";
					break;
				case 4:
					sv[1] = "??????";
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

				sv[10] = pro.getRestrictCollege() ? "??????" : "??????";
				sv[11] = pro.getRestrictSubject() ? "??????" : "??????";
				sv[12] = pro.getTraintype() == 1 ? "????????????" : "????????????";
				sv[13] = pt.getProjectLevel().getName();
				sv[14] = pro.getDocument() == null ? "?????????" : "?????????";
				sv[15] = pro.getPsqByProjectJudgePsq() == null ? "?????????" : "?????????";
				ProjectAdmin pa = this.iProjectAdminService.get(pro
						.getCreator());
				sv[16] = pa.getName();
				sv[17] = Utlity.timeSpanToDateString(pro.getCreattime());
				if(sv[14].equals("?????????")){
					sv[18] = pro.getDocument().getResourcePath();
					sv[19] = pro.getDocument().getTitle();						
				}
				sv[20] = pro.getEnrollType().toString();
				this.projectHash.put(pro.getId(), sv);
			}
		}

		return "init";
	}

	// ????????????
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

		// ????????????????????????????????????
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
			// ?????????????????????????????????
			Area pae = this.iAreaService.getAreaByCode(areacode);
			areaid1 = pae.getName();
			areacode = pae.getParentcode();
			// ????????????
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
			if (year != null && !year.equals("") && !year.endsWith("0")){
				if(tProject.getYear().endsWith(year)){
					lstProjects.add(tProject);
				}
			}else{
				lstProjects.add(tProject);
			}
		}
		return "historyInit";
	}

	public void history() {

		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
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
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);

		// ??????
		// String sort = request.getParameter("jtSorting");
		String sort = "id desc";
		// TODO ??????????????????

		// ?????? ??????
		// if (sort != null && !sort.equals(""))
		// {
		// String[] sortarray = sort.split(" ");
		// String sortname = sortarray[0];
		// String sorttype = sortarray[1];
		// sort = sortname + " " + sorttype;
		// }

		try {
			// ?????????????????????????????????
			@SuppressWarnings("unused")
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
					records = iProjectApplyService.getCountByTrainingCollege(iTrainingAdminService.get(user.getId()).getTrainingCollege().getId());
				}
				sb.append("\"TotalRecordCount\":" + records);
				sb.append(",");
				sb.append("\"Records\":[");
				for (ProjectApply projectApply : lstProjectApplies) {
					StringBuilder sbstr = new StringBuilder();
					sbstr.append("{");
					sbstr.append("\"id\":" + projectApply.getId());
					sbstr.append(",");
					sbstr.append("\"project\":\""
							+ projectApply.getProject().getName() + "\"");
					sbstr.append(",");
					sbstr.append("\"trainingSubject\":\""
							+ projectApply.getTrainingSubject().getName()
							+ "\"");
					sbstr.append(",");
					if (projectApply.getTrainingStarttime() != null) {
						sbstr.append("\"trainingStarttime\":\""
								+ cn.zeppin.utility.dataTimeConvertUtility
										.timespanToString(projectApply
												.getTrainingStarttime(),
												"yyyy-MM-dd") + "\"");
						sbstr.append(",");
					}
					if (projectApply.getTrainingEndtime() != null) {
						sbstr.append("\"trainingEndtime\":\""
								+ cn.zeppin.utility.dataTimeConvertUtility
										.timespanToString(projectApply
												.getTrainingEndtime(),
												"yyyy-MM-dd") + "\"");
						sbstr.append(",");
					}

					// ????????????
					sbstr.append("\"approveNumber\":\""
							+ projectApply.getApproveNumber() + "\"");
					sbstr.append(",");
					sbstr.append("\"trainingClasshour\":\""
							+ projectApply.getTrainingClasshour() + "\"");
					sbstr.append(",");

					String status = "?????????";
					if (projectApply.getStatus() == 2) {
						status = "?????????";

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

		// return result;
	}

	public String projectApply() {
		initServlert();
		HttpSession session = request.getSession();
		hmFiles = new HashMap<>();
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
			document = projectApply.getDocumentByWorkReport();
			if (document != null && document.getId() != null) {
				fileInfo fileInfo = new fileInfo();
				fileInfo = initFileInfo(document);
				hmFiles.put(fileInfo.getFileType(), fileInfo);
			}

		}

		if (request.getParameterMap().containsKey("id")) {
			id = request.getParameter("id");
			projectApply = new ProjectApply();
			/* session.setAttribute("uploadfile", null); */
			if (!id.equals("") && !id.equals(null)) {
				project = iProjectService.get(Integer.parseInt(id));
			}
		}
		session.setAttribute("uploadfile", hmFiles);

		// ??????
		if(project.getRestrictSubject()==false)	{	
			lsttTrainingSubjects = iTrainingSubjectService.findAll();
		}else{
			UserSession us = (UserSession) session.getAttribute("usersession");
			List<ProjectSubjectRange> psrlist=iProjectSubjectRangeService.getListByProject(Integer.valueOf(project.getId()));
			for(ProjectSubjectRange psr : psrlist){
				lsttTrainingSubjects.add(psr.getTrainingSubject());
			}
		}
		
		lstteacTeachingSubjectExs.clear();
		for (TrainingSubject ts : lsttTrainingSubjects) {
			teachingSubjectEx tt = new teachingSubjectEx();
			tt.setId(ts.getId().toString());
			tt.setName(ts.getName());
			tt.setSearchString(ts.getName() + pinyingUtil.getFirstSpell(ts.getName()));
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

		}
		return fileInfo;
	}

	@SuppressWarnings({ "null", "unchecked" })
	public void addProjectapply() {
		initServlert();
		UserSession user = (UserSession) session.getAttribute("usersession");
		pageStatus = "OK";
		message = "";
		String pid = "";
		String subjectId = "";
		String contacts = "";
		String phone = "";
		String trainingClasshour = null;
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

			if (request.getParameterMap().containsKey("pid")) {
				pid = request.getParameter("pid");

			}
			/*
			 * else { pageStatus = "ERROR"; message = "???????????????"; }
			 */
			subjectId = request.getParameter("mainTeachingSubject");
			if (subjectId.equals("-1")) {
				pageStatus = "ERROR";
				message = "???????????????";
			}
			contacts = request.getParameter("contacts");
			phone = request.getParameter("phone");
			trainingAddress = request.getParameter("trainingAddress");
			if (request.getParameterMap().containsKey("trainingClasshour")) {
				trainingClasshour = request.getParameter("trainingClasshour");
				if (trainingClasshour.equals("")
						|| trainingClasshour.equals(null)) {
					pageStatus = "ERROR";
					message = "???????????????";
				}
			}
			if (!request.getParameter("trainingStarttime").equals("")) {

				trainingStarttime = dataTimeConvertUtility
						.stringToTimeStamp(request
								.getParameter("trainingStarttime")
								+ " 00:00:00");
			}else{
				pageStatus = "ERROR";
				message = "???????????????????????????";
			}
			if (!request.getParameter("trainingEndtime").equals("")) {

				trainingEndtime = dataTimeConvertUtility
						.stringToTimeStamp(request
								.getParameter("trainingEndtime") + " 00:00:00");
			}else{
				pageStatus = "ERROR";
				message = "???????????????????????????";
			}
			
			
			if (!request.getParameter("approveNumber").equals("")) {
				approveNumber = Integer.parseInt(request
						.getParameter("approveNumber"));
			}

			TrainingAdmin trainingAdmin = iTrainingAdminService.get(user
					.getId());
			if (pageStatus.equals("OK")) {

				ProjectApply projectApply = new ProjectApply();
				if (Utlity.isNumeric(request.getParameter("id"))) {
					int id = Integer.parseInt(request.getParameter("id"));
					Project project = this.iProjectService.get(id);
					
					
					if(project.getEnrollType() == 2){
						//??????????????????
						if (request.getParameter("enrollEndTime")!=null && !request.getParameter("enrollEndTime").equals("")) {

							enrollEndTime = dataTimeConvertUtility
									.stringToTimeStamp(request
											.getParameter("enrollEndTime") + " 00:00:00");
						}else{
							pageStatus = "ERROR";
							message = "???????????????????????????";
						}
					}
					
					int totalNumber = project.getNumber();//?????????????????????
//					int totalSubjectCount = project.getSubjectMax();
					if(approveNumber>totalNumber){//?????????????????????????????????
						pageStatus = "ERROR";
						message = "??????????????????????????????????????????????????????????????????";
						rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
								+ message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
					}
					
//					int subjectCount = 0;
					int isApproveNumber = 0;//???????????????
					List<ProjectApply> lstpa = this.iProjectApplyService.getCheckedProjectApply(project.getId());
					for(ProjectApply pa : lstpa){
						isApproveNumber += pa.getApproveNumber();
//						subjectCount++ ;
					}
					
					if((totalNumber - isApproveNumber) <= 0){
						pageStatus = "ERROR";
						message = "???????????????????????????????????????????????????????????????????????????";
						rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
								+ message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
						
					}
					
					
					projectApply.setProject(project);

				}

				if (hmFiles.size() > 0) {
				  /*document = projectApply.getDocumentByProformanceReport();
					if (document != null) {
						iDocumentService.delete(document);
					}
					document = projectApply.getDocumentByProjectApplyDocument();
					if (document != null) {
						iDocumentService.delete(document);
					}
					document = projectApply.getDocumentByStartMessage();
					if (document != null) {
						iDocumentService.delete(document);
					}
					document = projectApply.getDocumentByWorkReport();
					if (document != null) {
						iDocumentService.delete(document);
					}*/
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
						document.setResourcePath(fileInfo.getFilePath());
						if (fileInfo.getFileName().contains(".")) {
							document.setTitle(fileInfo.getFileName());
						} else {
							document.setTitle(fileInfo.getFileName()
									+ fileInfo.getFileExt());
						}
						switch (fileInfo.getFileType()) {
						case applyReportBook:
							document.setType((short) 2);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply
									.setDocumentByProjectApplyDocument(document);
							break;
						case classStartReport:
							document.setType((short) 4);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply.setDocumentByStartMessage(document);
							break;
						case workReport:
							document.setType((short) 5);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply.setDocumentByWorkReport(document);
							break;
						case proformanctReport:
							document.setType((short) 6);
							document.setResourceType((short) 1);
							document = iDocumentService.add(document);
							projectApply
									.setDocumentByProformanceReport(document);
							break;
						default:
							break;

						}

					}

				}
				projectApply.setTrainingSubject(iTrainingSubjectService
						.get((short) Integer.parseInt(subjectId)));
				projectApply.setType((short) 1);

				projectApply.setContacts(contacts);
				projectApply.setPhone(phone);
				projectApply.setTrainingAddress(trainingAddress);
				projectApply.setPhone(phone);
				if (trainingClasshour != null) {
					projectApply.setTrainingClasshour(Integer
							.parseInt(trainingClasshour));
				}

				if (trainingStarttime != null) {
					projectApply.setTrainingStarttime(trainingStarttime);
				}
				if (trainingEndtime != null) {

					projectApply.setTrainingEndtime(trainingEndtime);
				}

				if(enrollEndTime != null){
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
							iDocumentService.delete(document);
						}
						document = tp.getDocumentByProjectApplyDocument();
						if (document != null) {
							iDocumentService.delete(document);
						}
						document = tp.getDocumentByStartMessage();
						if (document != null) {
							iDocumentService.delete(document);
						}
						document = tp.getDocumentByWorkReport();
						if (document != null) {
							iDocumentService.delete(document);
						}
					}
					tp.setDocumentByProformanceReport(projectApply
							.getDocumentByProformanceReport());
					tp.setDocumentByStartMessage(projectApply
							.getDocumentByStartMessage());
					tp.setDocumentByWorkReport(projectApply
							.getDocumentByWorkReport());
					tp.setDocumentByProjectApplyDocument(projectApply
							.getDocumentByProjectApplyDocument());
					tp.setProject(projectApply.getProject());
					tp.setTrainingSubject(projectApply.getTrainingSubject());
					tp.setType(projectApply.getType());
					tp.setContacts(projectApply.getContacts());
					tp.setPhone(projectApply.getPhone());
					tp.setTrainingAddress(projectApply.getTrainingAddress());
					tp.setTrainingClasshour(projectApply.getTrainingClasshour());
					tp.setTrainingStarttime(projectApply.getTrainingStarttime());
					tp.setTrainingEndtime(projectApply.getTrainingEndtime());
					tp.setCreator(projectApply.getCreator());
					tp.setApproveNumber(projectApply.getApproveNumber());
					tp.setTrainingCollege(projectApply.getTrainingCollege());

					/*
					 * if (projectApply.getStatus() == 2) { pageStatus =
					 * "ERROR"; message = "?????????????????????????????????"; } else {
					 */
					iProjectApplyService.update(tp);
					// }

				} else {
					int pa=this.iProjectApplyService.getCountByTrainingCollege(user.getOrganization(), Integer.valueOf(request.getParameter("id")), Integer.valueOf(subjectId));
					if(pa!=0){
						pageStatus = "ERROR";
						message = "??????????????????????????????";
						rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
								+ message + "\"}";
						Utlity.ResponseWrite(rString, "json", response);
						return;
					}
					projectApply.setStatus((short) 1);
					iProjectApplyService.add(projectApply);
				}

			}

			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			pageStatus = "ERROR";
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
					+ message + "\"}";
			Utlity.ResponseWrite(rString, "json", response);
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
			message = "?????????????????????????????????";
		} else {
			iProjectApplyService.delete(projectApply);
		}
		String rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\""
				+ message + "\"}";
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	/**
	 * 
	 * ????????????????????????????????????
	 */
	public void getProjectApplyListById(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		
		StringBuilder sb = new StringBuilder();
		
		String pId = request.getParameter("pId") == null? "":request.getParameter("pId");
		
		if("".equals(pId)){
			pId = "0";
		}
		
		String sId = "0";
		if(pId.split("a").length > 1){
			String[] ids = pId.split("a");
			pId = ids[0];
			sId = ids[1];
		}
		
		int id = Integer.parseInt(pId);
		int sid = Integer.parseInt(sId);
		
		if(sid == 0){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", 2);
			params.put("trainingCollege", us.getOrganization());
			if(id <= 0){
				params.put("group", "t.project");
			}else{
				params.put("projectId", id);
			}
			
			
			List lstProjectApply = this.iProjectApplyService.getProjectByParams(params, null);
			
			if(id <= 0){
				if(lstProjectApply != null && lstProjectApply.size() > 0){
					sb.append("[");
					for(int i = 0; i < lstProjectApply.size(); i++){
						Object[] obj = (Object[])lstProjectApply.get(i);
						
						ProjectApply pa = (ProjectApply)obj[0];
						
						sb.append("{");
						sb.append("\"id\":"+pa.getProject().getId());
						sb.append(",");
						sb.append("\"name\":\""+pa.getProject().getName()+"\"");
						sb.append(",");
						sb.append("\"isProject\":1");
						sb.append("},");
					}
					
					sb.delete(sb.length()-1, sb.length());
					sb.append("]");
				}
				
				
			}else{
				if(lstProjectApply != null && lstProjectApply.size() > 0){
					sb.append("[");
					sb.append("{");
					sb.append("\"pid\":"+pId);
					sb.append(",");
					sb.append("\"id\":"+0);
					sb.append(",");
					sb.append("\"name\":\"??????\"");
					sb.append(",");
					sb.append("\"isProject\":0");
					sb.append("},");
					for(int i = 0; i < lstProjectApply.size(); i++){
						Object[] obj = (Object[])lstProjectApply.get(i);
						
						ProjectApply pa = (ProjectApply)obj[0];
						
						sb.append("{");
						sb.append("\"pid\":"+pId);
						sb.append(",");
						sb.append("\"id\":"+pa.getTrainingSubject().getId());
						sb.append(",");
						sb.append("\"name\":\""+pa.getTrainingSubject().getName()+"\"");
						sb.append(",");
						sb.append("\"isProject\":0");
						sb.append("},");
					}
					
					sb.delete(sb.length()-1, sb.length());
					sb.append("]");
				}
			}
		}else{
			sb.append("[");
			sb.append("{");
			sb.append("\"pid\":"+pId);
			sb.append(",");
			sb.append("\"sid\":"+sid);
			sb.append(",");
			sb.append("\"id\":"+0);
			sb.append(",");
			sb.append("\"name\":\"??????\"");
			sb.append(",");
			sb.append("\"isProject\":2");
			sb.append("},");
			for(int i = 0; i < 8; i++){
				int classId = i+1;
				sb.append("{");
				sb.append("\"pid\":"+pId);
				sb.append(",");
				sb.append("\"sid\":"+sid);
				sb.append(",");
				sb.append("\"id\":"+classId);
				sb.append(",");
				sb.append("\"name\":\""+classId+"???\"");
				sb.append(",");
				sb.append("\"isProject\":2");
				sb.append("},");
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("]");
		}
		
		
		Utlity.ResponseWrite(sb.toString(), "json", response);
		
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
	public void setiProjectSubjectRangeService(IProjectSubjectRangeService iProjectSubjectRangeService) {
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

	
}
