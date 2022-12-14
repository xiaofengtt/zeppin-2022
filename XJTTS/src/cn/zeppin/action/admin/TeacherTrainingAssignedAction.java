package cn.zeppin.action.admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingAssigned;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectSubjectRangeService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingAssginedService;
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

@SuppressWarnings({"unchecked","rawtypes"})
public class TeacherTrainingAssignedAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(TeacherTrainingAssignedAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private ITeacherTrainingAssginedService teacherTrainingAssignedService;
	private IProjectService iProjectService;
	private ITrainingSubjectService iTrainingSubjectService;
	private ITeacherService iTeacherService;
	private IProjectSubjectRangeService iProjectSubjectRangeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecords;
	private ITrainingCollegeService iTrainingCollegeService; // ????????????
	private IProjectAdminService iProjectAdminService; // ???????????????
	private IOrganizationService iOrganization; // ????????????
	private IProjectApplyService iProjectApplyService;
	
	private ITeachingLanguageService iTeachingLanguageService;// ??????????????????
	private ITeachingGradeService iTeachingGradeService;// ??????????????????
	private ITeachingSubjectService iTeachingSubjectService;// ??????????????????
	
	private List<String> searchYear;
	private List<Project> searchProject;
//	private List<ProjectType> searchProjectType;
	private HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
	private String pageStatus;// ????????????
	private String message;// ????????????
	
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
//		UserSession us = (UserSession) session.getAttribute("usersession");
		String projectId = request.getParameter("projectId") == null ? "0" : request.getParameter("projectId");
		
		List<ProjectSubjectRange> lst = this.iProjectSubjectRangeService.getListByProject(Integer.parseInt(projectId));
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append("\"subjects\":[");
		sb.append("{\"id\":" + 0 + ",\"name\":\"" + "??????" + "\"},");
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
	
	public String initPage(){
		initServlert();
		String year = request.getParameter("year");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String,Object> map = new HashMap<String, Object>();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
		this.searchProject = this.iProjectService.getProjectByParams(map,null,lityps);

		return "presult";
	}
	
	/**
	 * ????????????????????????????????????
	 */
	public void getList(){
		
		initServlert();

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
		Map<String, String> sortParams = new HashMap<String, String>();
		sortParams.put(sortArr[0], sortArr[1]);
		
		//??????????????????
		String projectId = request.getParameter("project") == null ? "0" : request.getParameter("project");
		String subjectId = request.getParameter("subject") == null ? "0" : request.getParameter("subject");
		String statuss = request.getParameter("assStatus") == null ? "-1" : request.getParameter("assStatus");//assStatus
		if("".equals(statuss)){
			statuss = "-1";
		}
		
		HashMap<String, String> searchMap = new HashMap<>();
		searchMap.put("project", projectId);
		searchMap.put("subject", subjectId);
		if(Integer.parseInt(statuss)>-1){
			searchMap.put("status", statuss);
		}
//		searchMap.put("trainingCollege", trainingCollegeId);
		
		// ???????????????
		int count = this.teacherTrainingAssignedService.getCountByParams(searchMap);

		List lidata = this.teacherTrainingAssignedService.getListByParams(searchMap, sortParams, start, limit);
		
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
	
		sbPack.append("\"TotalRecordCount\":" + count);
		sbPack.append(",");
		sbPack.append("\"Records\":[");
		
		for(int i = 0; i < lidata.size(); i++){
			Object[] obj = (Object[])lidata.get(i);
			TeacherTrainingAssigned tta = (TeacherTrainingAssigned)obj[0];
			Teacher teacher = (Teacher)obj[1];
			Project project = (Project)obj[2];
			TrainingSubject ts = (TrainingSubject)obj[3];
			
			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + tta.getId() + "\",");//ID

			sb.append("\"name\":\"" + teacher.getName() + "\",");//????????????
			sb.append("\"idcard\":\"" + teacher.getIdcard() + "\",");//??????????????????
			
			sb.append("\"projectId\":\"" + project.getId() + "\",");//??????ID
			sb.append("\"projectName\":\"" + project.getName() + "\",");//????????????

			sb.append("\"subjectId\":\"" + ts.getId() + "\",");//????????????ID
			sb.append("\"subjectName\":\"" + ts.getName() + "\",");//??????????????????
			
			String status = tta.getStatus() == 0 ? "?????????" : "?????????";
			
			sb.append("\"status\":\"" + status + "\"");

			sb.append("},");
			sbPack.append(sb.toString());
		}
		
		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");
	
		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);
		
	}
	
	
	public void getStatusCount(){
			
			initServlert();
	
//			// ?????????
//			String ist = (String) request.getParameter("jtStartIndex");
//			if (ist == null || ist.equals("") || ist.equals("NaN")) {
//				ist = "0";
//			}
//			// ???????????????
//			String ien = (String) request.getParameter("jtPageSize");
//			if (ien == null || ien.equals("")) {
//				ien = DictionyMap.maxPageSize + "";
//			}
//	
//			int start = Integer.parseInt(ist);
//			int limit = Integer.parseInt(ien);
	
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
			Map<String, String> sortParams = new HashMap<String, String>();
			sortParams.put(sortArr[0], sortArr[1]);
			
			//??????????????????
			String projectId = request.getParameter("project") == null ? "0" : request.getParameter("project");
			String subjectId = request.getParameter("subject") == null ? "0" : request.getParameter("subject");
			String statuss = request.getParameter("assStatus") == null ? "-1" : request.getParameter("assStatus");//assStatus
			if("".equals(statuss)){
				statuss = "-1";
			}
			
			HashMap<String, String> searchMap = new HashMap<>();
			searchMap.put("project", projectId);
			searchMap.put("subject", subjectId);
//			if(Integer.parseInt(statuss)>-1){
//				searchMap.put("status", statuss);
//			}
	//		searchMap.put("trainingCollege", trainingCollegeId);
			
			// ???????????????
			int records = this.teacherTrainingAssignedService.getCountByParams(searchMap);
			searchMap.put("status", "1");
			int noCheck = this.teacherTrainingAssignedService.getCountByParams(searchMap);
			searchMap.put("status", "0");
			int checkPass = this.teacherTrainingAssignedService.getCountByParams(searchMap);
			
			StringBuilder sb = new StringBuilder();
			sb.append("{\"totalCount\":" + (records) + ",");//??????
			sb.append("\"noCheck\":" + (noCheck) + ",");//?????????
			sb.append("\"checkPass\":" + (checkPass));//?????????
			sb.append("}");
//			sb.append("{\"totalCount\":" + (records) + ",");
//			sb.append("\"currentPage\":" + (start) + ",");
//			sb.append("\"totalPage\":" + total);
//			sb.append("}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * ??????????????????????????????
	 * @throws IOException 
	 */
	public void outputList() throws IOException{
		initServlert();
		String projectId = request.getParameter("projectName") == null ? "0" : request.getParameter("projectName");
		String subjectId = request.getParameter("subjectName") == null ? "0" : request.getParameter("subjectName");
		String status = request.getParameter("assStatus") == null ? "-1" : request.getParameter("assStatus");//assStatus
		if("".equals(status)){
			status = "-1";
		}
		
		HashMap<String, String> searchMap = new HashMap<>();
		searchMap.put("project", projectId);
		searchMap.put("subject", subjectId);
		if(Integer.parseInt(status)>-1){
			searchMap.put("status", status);
		}
//		searchMap.put("trainingCollege", trainingCollegeId);
		
		// ???????????????
//		int count = this.teacherTrainingAssignedService.getCountByParams(searchMap);

		
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
		
		List lidata = this.teacherTrainingAssignedService.getListByParams(searchMap, null, null, null);
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "Sheet1");
		XSSFRow row = s.createRow(0);
		String title[] = { "??????ID", "????????????", "????????????ID", "??????????????????", "????????????", "??????ID", "????????????", "??????????????????","????????????", "????????????","??????", "????????????","??????????????????", "??????????????????", "??????????????????","????????????", "??????", "??????" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
//		String Name = "";
		for (int i = 0; i < lidata.size(); i++) {
			Object[] obj = (Object[]) lidata.get(i);
//			TeacherTrainingAssigned tta = (TeacherTrainingAssigned)obj[0];
			Teacher teacher = (Teacher)obj[1];
			Project project = (Project)obj[2];
			TrainingSubject ts = (TrainingSubject)obj[3];
			t++;
			row = s.createRow(t);
			
			String projectID = project.getId()+"";
			String projectName = project.getName();
			String subjectID = ts.getId()+"";
			String subjectName = ts.getName();
			String trainingCollege = "";
			String teacherID = teacher.getId()+"";
			String teacherName = teacher.getName();
			String idcard = teacher.getIdcard();
			String otherStr = "";
			
			String tArea = "";//????????????
			Organization current = teacher.getOrganization().getOrganization();
			short tOrgLevel = current.getOrganizationLevel().getLevel();
			while (tOrgLevel > 1) {
				tArea = current.getName() + "  " + tArea;
				current = current.getOrganization();
				tOrgLevel = current.getOrganizationLevel().getLevel();
			}
			
			String schoolName = teacher.getOrganization().getName();//????????????
			String tEthnic = teacher.getEthnic().getName();//??????
			String tPolitics = teacher.getPolitics().getName();//????????????
			
			
//			// ????????????????????????
//			String hqlString = "from TeachingLanguage where teacher="
//					+ teacher.getId() + " and isprime=true";
//			String tMainLanguage = "";
//			List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService
//					.getListByHSQL(hqlString);
//			if (lstTeachingLanguages.size() > 0) {
//				TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
//				tMainLanguage = teachingLanguage.getLanguage().getName();
//			}
//
//			// ????????????????????????
//			hqlString = "from TeachingGrade where teacher=" + teacher.getId()
//					+ " and isprime=1";
//			String tMainGrade = "";
//			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
//					.getListByHSQL(hqlString);
//			if (lstTeachingGrades.size() > 0) {
//				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
//				tMainGrade = teachingGrade.getGrade().getName();
//			}
//
//			// ????????????????????????
//			hqlString = "from TeachingSubject where teacher=" + teacher.getId()
//					+ " and isprime=1";
//			String tMainSubject = "";
//			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
//					.getListByHSQL(hqlString);
//			if (lsttTeachingSubjects.size() > 0) {
//				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
//				tMainSubject = teachingSubject.getSubject().getName();
//			}
			if(tlMap.get(teacher.getId()) != null){
				tMainLanguage = tlMap.get(teacher.getId()).getLanguage().getName();
			}

			// ????????????????????????
//			hqlString = "from TeachingGrade where teacher=" + th.getId() + " and isprime=1";
//			String tMainGrade = "";
//			List<TeachingGrade> lstTeachingGrades = iTeachingGradeService.getListByHSQL(hqlString);
//			if (lstTeachingGrades.size() > 0) {
//				TeachingGrade teachingGrade = lstTeachingGrades.get(0);
//				tMainGrade = teachingGrade.getGrade().getName();
//			}
			if(tgMap.get(teacher.getId()) != null){
				tMainGrade = tgMap.get(teacher.getId()).getGrade().getName();
			}

			// ????????????????????????
//			hqlString = "from TeachingSubject where teacher=" + th.getId() + " and isprime=1";
//			String tMainSubject = "";
//			List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService.getListByHSQL(hqlString);
//			if (lsttTeachingSubjects.size() > 0) {
//				TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
//				tMainSubject = teachingSubject.getSubject().getName();
//			}
			if(tsMap.get(teacher.getId()) != null){
				tMainSubject = tsMap.get(teacher.getId()).getSubject().getName();
			}
			
			String tMobile = teacher.getMobile() == null ? "" : teacher.getMobile();//????????????
			String tEmail = teacher.getEmail() == null ? "" : teacher.getEmail();//??????
			
			String tInfo[] = { projectID, projectName, subjectID, subjectName, trainingCollege, teacherID, teacherName, idcard, tArea, schoolName, tEthnic, tPolitics, tMainSubject, tMainGrade, tMainLanguage, tMobile, tEmail, otherStr };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		String fileName = "????????????????????????????????????";
		response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("gbk"), "iso8859-1")+".xlsx");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	public String uploadInit(){
		return "upload";
	}
	
	/**
	 * ??????????????????????????????????????????
	 *???????????????
	 *?????????????????????????????????
	 *?????????????????????????????????????????????
	 *????????????????????????
	 */
	public void upload(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		Organization taddOra = this.iOrganization.get(us.getOrganization());
		DecimalFormat df = new DecimalFormat("#");
		pageStatus = "OK";
		message = "??????????????????";
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
				message = "?????????Excel";
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
				XSSFSheet s = wb.getSheet("Sheet1");
				XSSFRow row;
				if(s.getLastRowNum()<1){
					pageStatus = "ERROR";
					message = "??????????????????";
					rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
					Utlity.ResponseWrite(rString, "json", response);
					return;
				}
				this.session.setAttribute("maxIndex", s.getLastRowNum());
				this.session.setAttribute("percent",0);
				
				HashMap<String,Project> projectMap = new HashMap<String,Project>();
				List<Project> projectList = this.iProjectService.findAll();
				for(Project ts : projectList){
					projectMap.put(ts.getName().trim(), ts);
				}
				
				HashMap<String,TrainingSubject> tsMap = new HashMap<String,TrainingSubject>();
				List<TrainingSubject> tsList = this.iTrainingSubjectService.findAll();
				for(TrainingSubject ts : tsList){
					tsMap.put(ts.getName().trim(), ts);
				}
				
				HashMap<String,TrainingCollege> tcMap = new HashMap<String,TrainingCollege>();
				List<TrainingCollege> tcList = this.iTrainingCollegeService.findAll();//???????????????????????????
				for(TrainingCollege tc : tcList){
					tcMap.put(tc.getName().trim(), tc);
				}
				
				HashMap<String,Teacher> teaMap = new HashMap<String,Teacher>();
				List<Teacher> teaList = this.iTeacherService.findAll();//???????????????????????????
				for(Teacher tea : teaList){
					teaMap.put(tea.getIdcard().trim(), tea);
				}
				
				for (int i = 1; i <= s.getLastRowNum(); i++) {// ??????Excel??????
					row = s.getRow(i);
					if(row!=null){
						HashMap<String, Object> infomation = new HashMap<String, Object>();
						String projectId = "";
						XSSFCell cell = row.getCell(0);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								projectId = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								projectId = cell.getStringCellValue();
							}
						}
						String projectName = "";
						cell = row.getCell(1);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								projectName = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								projectName = cell.getStringCellValue();
							}
						}
						String subjectId = "";
						cell = row.getCell(2);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								subjectId = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								subjectId = cell.getStringCellValue();
							}
						}
						String subjectName = "";
						cell = row.getCell(3);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								subjectName = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								subjectName = cell.getStringCellValue();
							}
						}
						String trainingCollege = "";
						cell = row.getCell(4);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								trainingCollege = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								trainingCollege = cell.getStringCellValue();
							}
						}
						String teacherId = "";
						cell = row.getCell(5);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								teacherId = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								teacherId = cell.getStringCellValue();
							}
						}
						String teacherName = "";
						cell = row.getCell(6);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								teacherName = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								teacherName = cell.getStringCellValue();
							}
						}
						String idcard = "";
						cell = row.getCell(7);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								idcard = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								idcard = cell.getStringCellValue();
							}
						}
						String remark = "";
						cell = row.getCell(17);
						if (cell != null) {
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								remark = df.format(cell.getNumericCellValue());
							} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								remark = cell.getStringCellValue();
							}
						}
						if(!IdCardUtil.IDCardValidate(idcard).equals("")){//????????????????????????
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "???????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * ????????????????????????????????????
						 */
						if( trainingCollege==null || trainingCollege.equals("")){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if(tcMap.get(trainingCollege)==null){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * ??????????????????????????????
						 */
						if( projectId==null || projectId.equals("")){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "????????????????????????ID";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if( projectName==null || projectName.equals("")){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if(projectMap.get(projectName)==null){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * ??????????????????????????????
						 */
						if( subjectId==null || subjectId.equals("")){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????ID";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if( subjectName==null || subjectName.equals("")){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "????????????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if(tsMap.get(subjectName)==null){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * ??????????????????????????????
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
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "???????????????????????????????????????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * ??????????????????????????????
						 */
						if( teacherId==null || teacherId.equals("")){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "????????????????????????ID";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						if( teacherName==null || teacherName.equals("")){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
//						HashMap<String,String> searchMap = new HashMap<String,String>();
//						searchMap.put("idcard", idcard);
//						List<Teacher> tList = this.iTeacherService.getTeacherListByParams(searchMap);
						if(teaMap.get(idcard) == null){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "??????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						Teacher teacher = teaMap.get(idcard);
						if(!teacher.getName().trim().equals(teacherName)){
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "????????????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						/*
						 * ??????????????????????????????????????????
						 */
						HashMap<String, String> searchMaps = new HashMap<>();
						searchMaps.put("project", projectId);
						searchMaps.put("subject", subjectId);
						searchMaps.put("teacher", teacher.getId()+"");
//						int countt = this.teacherTrainingAssignedService.getCountByParams(searchMaps);
						List list = this.teacherTrainingAssignedService.getListByParams(searchMaps, null, null, null);
						int countt = 0;
						
						if(list != null && !list.isEmpty()){
							countt = list.size();
							TeacherTrainingAssigned ttaa = (TeacherTrainingAssigned) ((Object[])list.get(0))[0];
							if(ttaa != null){//?????????????????????
								ttaa.setStatus((short)0);
								this.teacherTrainingAssignedService.update(ttaa);
							}
						}
						if(countt == 0){//?????????
							pageStatus = "ERROR";
							message = "???" + row.getRowNum() +"???,???????????????" + idcard + "?????????????????????????????????";
							rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message + "\"}";
							Utlity.ResponseWrite(rString, "json", response);
							return;
						}
						
						infomation.put("teacher", teacher);
						infomation.put("project", projectMap.get(projectName));
						infomation.put("trainingSubject", tsMap.get(subjectName));
						infomation.put("trainingCollege", tcMap.get(trainingCollege));
						infomation.put("remark", remark);
						infomationList.add(infomation);
						int percent = (int) Math.ceil((i * 100) /  (s.getLastRowNum() * 2));
						session.setAttribute("percent", percent);
						
					}
				}
				this.iTeacherTrainingRecords.addTeacherTrainingRecords(us, taddOra,s.getLastRowNum(), infomationList);
				session.removeAttribute("maxIndex");
				session.removeAttribute("percent");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public void getPercent() {
		initServlert();
		pageStatus = "OK";
		message = "";
		String rString = "";
		if(session.getAttribute("percent")!=null && session.getAttribute("maxIndex")!=null){
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
		}else{
			rString = "{\"Result\":\"" + pageStatus + "\",\"Message\":\"" + message
					+ "\",\"Percent\":\"" + 0 + "\",\"MaxIndex\":\""
					+ 0 + "\"}";
		}
		Utlity.ResponseWrite(rString, "json", response);
	}
	
	
	public ITeacherTrainingAssginedService getTeacherTrainingAssignedService() {
		return teacherTrainingAssignedService;
	}

	public void setTeacherTrainingAssignedService(
			ITeacherTrainingAssginedService teacherTrainingAssignedService) {
		this.teacherTrainingAssignedService = teacherTrainingAssignedService;
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
	
	
}
