package cn.zeppin.action.teacher;
 
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.action.admin.ProjectBaseInfoAction;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherEduAdvance;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.teacherEx;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IHsdtestService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectCycleService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITeacherEduAdvanceService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DESUtil;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

@SuppressWarnings("rawtypes")
public class TrainRecordsAction extends baseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8102948942441565476L;
	
	static Logger logger = LogManager.getLogger(ProjectBaseInfoAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private IProjectApplyService iProjectApplyService;
	private IProjectService iProjectService;
	private IProjectAdminService iProjectAdminService;
	private ITrainingAdminService iTrainingAdminService;
	private IAreaService iAreaService;
	private IProjectTypeService iProjectTypeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private IHsdtestService iHsdTestService;
	
	private ITeacherService iTeacherService;
	private ITeachingLanguageService iTeachingLanguageService;// ??????????????????
	private ITeachingGradeService iTeachingGradeService;// ??????????????????
	private ITeachingSubjectService iTeachingSubjectService;// ??????????????????
	private IProjectCycleService projectCycleService;
	private ITeacherEduAdvanceService iTeacherEduAdvanceService;
	
	// ????????????
	private Map<String, Map<String, Object>> cycleStudyhourMap;
	
	// ???????????????(?????????????????????????????????)
	private Map<String, Map<String, Object>> studyhourMap;
	
	private int teacherCredit;
	private ProjectCycle projectCycle;
	
	public TrainRecordsAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	
	/**
	 * ???????????????????????????
	 * ??????????????????teacher_train_records??????????????????
	 * totalCount--??????????????????
	 * createTime--????????????
	 * trainingStatus--????????????
	 * subjectName--????????????
	 * projectName--????????????
	 * trainingCollege--????????????
	 * trainingAdd--????????????
	 * trainingScore--????????????
	 * trainHour--????????????
	 * certificate--????????????
	 * trainingOnlineHour--????????????????????????
	 * trainingClassHour--???????????????
	 * trainingStartTime--??????????????????
	 * trainingEndTime--??????????????????
	 * 
	 * trainType --????????????
	 * 
	 * ??????????????????????????????????????????????????????????????????
	 * 
	 */
	public void initPage(){
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		Integer teacherId = 0;
		
		if(us!=null){
			teacherId = us.getId();
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		// ??????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		
		// ???????????????creatime-desc or creattime-asc???
		Map<String, String> sortParams = new HashMap<>();
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			
			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("creattime", "desc");
		}
		
		
		Map<String, Object> pagram = new HashMap<String, Object>();
		//??????????????????
		//??????ID
		int projectId = 0;
		if(request.getParameter("projectId")!=null){
			projectId = Integer.parseInt(request.getParameter("projectId"));
			pagram.put("projectId", projectId);
		}
		//??????ID
		int subjectId = 0;
		if(request.getParameter("subjectId")!=null){
			projectId = Integer.parseInt(request.getParameter("subjectId"));
			pagram.put("subjectId", subjectId);
		}
		//????????????ID
		int trainCollegeId = 0;
		if(request.getParameter("trainCollegeId")!=null){
			projectId = Integer.parseInt(request.getParameter("trainCollegeId"));
			pagram.put("trainCollegeId", trainCollegeId);
		}
		//????????????ID
		int trainType = 0;
		if(request.getParameter("trainType")!=null){
			projectId = Integer.parseInt(request.getParameter("trainType"));
			pagram.put("trainType", trainType);
		}
		
		pagram.put("finalStatus", 2);
		
		
		int count = this.iTeacherTrainingRecordsService.getTeacherRecordsCountByTid(teacherId, pagram);
		List li = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(teacherId, offset, count, sortParams, pagram);
		int endCount = 0;
		
		if(li!=null && !li.isEmpty()){
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for(int i = 0; i < li.size(); i++){
				Object[] obj = (Object[])li.get(i);
				TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
				Project p = (Project)obj[1];
				ProjectApply pa = (ProjectApply)obj[2];
				
				//?????????????????????
				if(ttr.getTrainingStatus()>2){
					endCount++;
				}
				
//				* totalCount--??????????????????
//				 * createTime--????????????
//				 * trainingStatus--????????????
//				 * subjectName--????????????
//				 * projectName--????????????
//				 * trainingCollege--????????????
//				 * trainingAddr--????????????
//				 * trainingScore--????????????
//				 * trainHour--????????????
//				 * certificate--????????????
//				 * trainingOnlineHour--????????????????????????
//				 * trainingClassHour--???????????????
//				 * trainingStartTime--??????????????????
//				 * trainingEndTime--??????????????????
//				 * trainingReason--?????????????????????
//				 * trainType --????????????
//				 * ttrId --????????????ID
				//20170320 ????????????????????? ?????????????????????
				sb.append("{");
				sb.append("\"createTime\":\""+ttr.getCreattime()+"\"");
				sb.append(",");
				sb.append("\"trainingStatus\":\""+ttr.getTrainingStatus()+"\"");
				sb.append(",");
				sb.append("\"subjectName\":\""+ttr.getTrainingSubject().getName()+"\"");
				sb.append(",");
				sb.append("\"projectName\":\""+ttr.getProject().getName()+"\"");
				sb.append(",");
				sb.append("\"trainingCollege\":\""+ttr.getTrainingCollege().getName()+"\"");
				sb.append(",");
				
				if(ttr.getTrainingScore() == null){
					sb.append("\"trainingScore\":\"0\"");
				}else{
					sb.append("\"trainingScore\":\""+ttr.getTrainingScore()+"\"");
				}
				sb.append(",");
				
				if(ttr.getTrainingClasshour() == null){
					sb.append("\"trainHour\":\"0\"");
				}else{
					sb.append("\"trainHour\":\""+ttr.getTrainingClasshour()+"\"");
				}
				sb.append(",");
				
				sb.append("\"studyhour\":").append(ttr.getStudyhour());
				sb.append(",");
				
				if(ttr.getCertificate() == null){
					sb.append("\"certificate\":\"?????????\"");
				}else{
					sb.append("\"certificate\":\""+ttr.getCertificate()+"\"");
				}
				sb.append(",");
				if(ttr.getTrainingOnlineHour() == null){
					sb.append("\"trainingOnlineHour\":\"0\"");
				}else{
					sb.append("\"trainingOnlineHour\":\""+ttr.getTrainingOnlineHour()+"\"");
				}
				sb.append(",");
				
				if(ttr.getTrainingReason() == null){
					sb.append("\"trainingReason\":\"???\"");
				}else{
					sb.append("\"trainingReason\":\""+ttr.getTrainingReason()+"\"");
				}
				sb.append(",");
				if(pa.getTrainingAddress() == null){
					sb.append("\"trainingAddr\":\"???\"");
				}else{
					sb.append("\"trainingAddr\":\""+pa.getTrainingAddress()+"\"");
				}
				sb.append(",");
				sb.append("\"trainingClassHour\":\""+(pa.getTrainingClasshour()+pa.getTrainingOnlineHour())+"\"");
				sb.append(",");
				if(pa.getTrainingStarttime() != null){
					sb.append("\"trainingStartTime\":\""+pa.getTrainingStarttime()+"\"");
				}else{
					sb.append("\"trainingStartTime\":\"??????\"");
				}
				
				sb.append(",");
				sb.append("\"trainingEndTime\":\""+pa.getTrainingEndtime()+"\"");
				sb.append(",");
				String trainUrl = "";//??????????????????????????????
				if(p.getTraintype() == 1){
					sb.append("\"trainType\":\"??????????????????????????????\"");
				}else if(p.getTraintype() == 2){
					sb.append("\"trainType\":\"????????????\"");
					trainUrl = pa.getTrainingCollege().getTrainURL();
					if(trainUrl != null && !"".equals(trainUrl)){
						if(!trainUrl.startsWith("http://")){
							trainUrl = "http://"+trainUrl;
						}
					}else{
						trainUrl="";
					}
				}else{
					sb.append("\"trainType\":\"??????????????????\"");
					trainUrl = pa.getTrainingCollege().getTrainURL();
					if(trainUrl != null && !"".equals(trainUrl)){
						if(!trainUrl.startsWith("http://")){
							trainUrl = "http://"+trainUrl;
						}
					}else{
						trainUrl="";
					}
				}
				sb.append(",");
				sb.append("\"trainTypeN\":\""+p.getTraintype()+"\"");//????????????--?????????????????????????????????????????????
				sb.append(",");
				sb.append("\"trainURL\":\""+trainUrl+"\"");
				
				int trainingSubjectId = pa.getTrainingSubject().getId();
				String teacherUuid = "";
				String str = us.getId()+"_"+us.getName();
				try {
					DESUtil des = new DESUtil();
					teacherUuid = des.encrypt(str);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				sb.append(",");
				sb.append("\"projectId\":\"" +p.getId()+ "\"");
				sb.append(",");
				sb.append("\"trainingSubjectId\":\"" +trainingSubjectId+ "\"");
				sb.append(",");
				sb.append("\"teacherUuid\":\"" +teacherUuid+ "\"");
				sb.append(",");
				sb.append("\"ttrId\":\"" +ttr.getId()+ "\"");
				
				sb.append("},");
				
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("],");
			sb.append("\"TotalRecordCount\":\""+count+"\"");
			sb.append(",");
			sb.append("\"EndCount\":\""+endCount+"\"");
			sb.append(",");
			int onTrainCount = count-endCount;
			sb.append("\"OnTrainCount\":\""+onTrainCount+"\"");
			sb.append("}");
			
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}else{
			
			sb.append("{");
			sb.append("\"Result\":\"EMPTY\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			
		}
		
		
		
	}
	
	public void checkIsTest(){ 
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		

		if(us==null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String ttrId = request.getParameter("ttrId") == null? "":request.getParameter("ttrId");
		
		if(!"".equals(ttrId)){
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId));
			if(ttr.getProject().getIsTest()){
				//??????????????? ???????????????????????????
				List<ProjectCycle> list = this.projectCycleService.findAll();
				ProjectCycle pc = null;
				if(list != null && list.size() > 0){
					for(ProjectCycle pcs : list){
						String startYear = pcs.getStartyear();
						String endYear = pcs.getEndyear();
						if(Integer.parseInt(ttr.getProject().getYear())>= Integer.parseInt(startYear) && Integer.parseInt(ttr.getProject().getYear())<=Integer.parseInt(endYear)){//???????????? ??????true
							pc = pcs;
						}
					}
				}
				if(pc != null){
					String startYear = pc.getStartyear();
					String endYear = pc.getEndyear();
//					List<String> years = new ArrayList<String>();
					StringBuilder yearsSb = new StringBuilder();
					for(int i = Integer.parseInt(startYear); i <= Integer.parseInt(endYear); i++){
//						years.add(i+"");
						yearsSb.append(i+",");
					}
					Calendar a=Calendar.getInstance();
					int yearNew = a.get(Calendar.YEAR);
					int endYearNew = Integer.parseInt(endYear);
					if(yearNew > endYearNew){
						yearsSb.append(yearNew+",");
					}
					yearsSb.delete(yearsSb.length() - 1, yearsSb.length());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("teacher", us.getId());
					map.put("yearss", yearsSb);

					int count = this.getiHsdTestService().getHsdTestCount(map);
					if(count == 0){
						sb.append("{");
						sb.append("\"Result\":\"REDIRECT\"");
						sb.append(",");
						sb.append("\"Message\":\"????????????????????????????????????????????????????????????????????????????????????????????????????????????\"");
						sb.append("}");
						
					}else{
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Message\":\"?????????????????????\"");
						sb.append("}");
					}
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				// ?????????????????????????????????
//				int year = new Date().getYear() + 1900;
				Calendar a=Calendar.getInstance();
				int year = a.get(Calendar.YEAR);
//				if(ttr.getProject().getYear() != null && Utlity.isNumeric(ttr.getProject().getYear())){
//					year = Integer.parseInt(ttr.getProject().getYear());
//				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("teacher", us.getId());
				map.put("year", year);

				int count = this.getiHsdTestService().getHsdTestCount(map);
				
				if(count == 0){
					sb.append("{");
					sb.append("\"Result\":\"REDIRECT\"");
					sb.append(",");
					sb.append("\"Message\":\"????????????????????????????????????????????????????????????????????????????????????????????????????????????\"");
					sb.append("}");
					
				}else{
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"?????????????????????\"");
					sb.append("}");
				}
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}else{
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"?????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"??????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
	}
	
	/**
	 * ?????????????????????????????????
	 */
	public void initPersonalFile(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us==null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Records\":[");
		sb.append(teacherBaseInfo(us));
		sb.append(",");
		sb.append(getProjectCycle(null));
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String teacherBaseInfo(UserSession us){
		StringBuilder sb = new StringBuilder();
		String hqlString;
		// id = Integer.parseInt(request.getParameter("id"));// ????????????id??????????????????
		// id = 36705;// ????????????id??????????????????
		String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
		
		
		Teacher teacher = iTeacherService.get(us.getId());
		String teacherHeadPath = teacher.getHeadPicPath() == null ? "":path+teacher.getHeadPicPath();
//		String teacherHeadPath = teacher.getHeadPicPath();		
		teacherEx teacherEx = new teacherEx();
		teacherEx.setTeacher(teacher);
		teacherEx.setSexString(teacher.getSex() == 1 ? "???" : "???");
		
		if (teacher.getStatus() == 1) {
			teacherEx.setStatusString("??????");
		} else if (teacher.getStatus() == 2) {
			teacherEx.setStatusString("??????");
		} else if (teacher.getStatus() == 3) {
			teacherEx.setStatusString("??????");
		} else if (teacher.getStatus() == 4) {
			teacherEx.setStatusString("??????");
		} else {
			teacherEx.setStatusString("??????");
		}
		teacherEx.setAuthorized(teacher.getAuthorized() == 1 ? "??????" : "??????");
		if (teacher.getTeachingAge() != null) {
			teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher
					.getTeachingAge())));
		}
		teacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "???" : "???");
		// ????????????????????????
		hqlString = "from TeachingLanguage where teacher=" + teacher.getId()
				+ " and isprime=true";

		List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService
				.getListByHSQL(hqlString);
		if (lstTeachingLanguages.size() > 0) {
			TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
			teacherEx.setMainTeachingLanguage(teachingLanguage);
		}

		// ????????????????????????
		hqlString = "from TeachingGrade where teacher=" + teacher.getId()
				+ " and isprime=1";
		List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
				.getListByHSQL(hqlString);
		if (lstTeachingGrades.size() > 0) {
			TeachingGrade teachingGrade = lstTeachingGrades.get(0);
			teacherEx.setMainTeachingClass(teachingGrade);
		}

		// ????????????????????????
		hqlString = "from TeachingSubject where teacher=" + teacher.getId()
				+ " and isprime=1";
		List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
				.getListByHSQL(hqlString);
		if (lsttTeachingSubjects.size() > 0) {
			TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
			teacherEx.setMainTeachingCourse(teachingSubject);
		}
		sb.append("{\"teacher\":{");
		sb.append("\"teacherName\":\""+teacher.getName()+"\",");
		String mobile = teacher.getMobile() == null ?"" : teacher.getMobile();
		if("".equals(mobile)){
			mobile = "??????";
		}
		sb.append("\"teacherMobile\":\""+mobile+"\",");
		sb.append("\"teacherSex\":\""+teacherEx.getSexString()+"\",");
		sb.append("\"teacherFolk\":\""+teacher.getEthnic().getName()+"\",");
		sb.append("\"teacherIdcard\":\""+teacher.getIdcard()+"\",");
		String politicss = "??????";
		if(teacher.getPolitics() != null){
			politicss = teacher.getPolitics().getName();
		}
		sb.append("\"teacherPolice\":\""+politicss+"\",");
		sb.append("\"teacherTeachAge\":\""+teacherEx.getTeachingAge()+"\",");
		String subject = "";
		if (lsttTeachingSubjects.size() > 0) {
			subject = teacherEx.getMainTeachingCourse().getSubject().getName();
		}
		sb.append("\"teacherMainTeachSubject\":\""+subject+"\",");
		sb.append("\"teacherStatus\":\""+teacherEx.getStatusString()+"\",");
		String grade = "";
		if (lstTeachingGrades.size() > 0) {
			grade = teacherEx.getMainTeachingClass().getGrade().getName();
		}
		sb.append("\"teacherMainTeachGrade\":\""+grade+"\",");
		sb.append("\"teacherAuthorize\":\""+teacherEx.getAuthorized()+"\",");
		String language = "";
		if (lstTeachingLanguages.size() > 0) {
			language = teacherEx.getMainTeachingLanguage().getLanguage().getName();
		}
		sb.append("\"teacherMainTeachLanguage\":\""+language+"\",");
		sb.append("\"teacherJobTitle\":\""+teacher.getJobTitle().getName()+"\",");
		sb.append("\"teacherBackground\":\""+teacher.getEductionBackground().getName()+"\",");
		sb.append("\"teacherJobDuty\":\""+teacher.getJobDuty().getName()+"\",");
		sb.append("\"teacherChineselevel\":\""+teacher.getChineseLanguageLevel().getName()+"\",");
		sb.append("\"teacherOrganization\":\""+teacher.getOrganization().getName()+"\",");
		sb.append("\"teacherIsMultiLanguage\":\""+teacherEx.getIsMultiLanguage()+"\",");
		if(teacherHeadPath != null && !"".equals(teacherHeadPath)){
			teacherHeadPath = teacher.getHeadPicPath();
		}else{
			if(teacher.getSex() == 2){
				teacherHeadPath = "img/women.png";
			}else{
				teacherHeadPath = "img/USER.png";
			}
		}
		sb.append("\"teacherHeadPath\":\""+teacherHeadPath+"\"");
		sb.append("}}");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getProjectCycle(String cycleId) {
		StringBuilder sb = new StringBuilder();
//		String[] str = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 1);
		if(cycleId != null){
			params.put("id", cycleId);
		}
		List<ProjectCycle> list = projectCycleService.getListByParams(params,
				0, -1, "createtime desc");
		sb.append("{\"projectCycle\":[");
		
		if (list != null && list.size() > 0) {
			this.projectCycle = list.get(0);
			cycleStudyhourMap = new HashMap<String,Map<String, Object>>();
			
			for (ProjectCycle pc : list) {
				sb.append("{");
				sb.append("\"id\":"+pc.getId());
				sb.append(",");
				sb.append("\"name\":\""+pc.getName()+"\"");
				sb.append(",");
				sb.append("\"startyear\":\""+pc.getStartyear()+"\"");
				sb.append(",");
				sb.append("\"endyear\":\""+pc.getEndyear()+"\"");
//				str = new String[5];
//				str[0] = pc.getStartyear();// ????????????
//				str[1] = pc.getEndyear();// ????????????
//				str[2] = pc.getName();// ????????????
				
				sb.append("},");
				//????????????????????????
				
				List<Map> cycleStudyhour = JSONUtils.json2list(pc.getStudyhour(), Map.class);
				for(Map studyhour : cycleStudyhour){
					cycleStudyhourMap.put(studyhour.get("name").toString(), studyhour);
				}
			}
			
			sb.delete(sb.length()-1, sb.length());
			
		}
		sb.append("]}");
		
		return sb.toString();
	}
	
	/**
	 * ????????????????????????
	 */
	@SuppressWarnings("unchecked")
	public void reLoad(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us==null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		int startYear;// ????????????
		int endYear;// ????????????
		String startYearStr = request.getParameter("startYear");
		String endYearStr = request.getParameter("endYear");
		
		Integer id = us.getId();// ????????????id??????????????????
		
		//????????????????????????
		//???????????????????????????????????????????????????????????????????????????
		if (startYearStr.equals("") || endYearStr.equals("")) {//??????
			getProjectCycle(null);
//			this.projectCycle = (ProjectCycle)session.getAttribute("projectCycle");
			startYear = Integer.parseInt(projectCycle.getStartyear());
			endYear = Integer.parseInt(projectCycle.getEndyear());
			//???????????????????????????
			cycleStudyhourMap = new HashMap<String,Map<String, Object>>();
			List<Map> cycleStudyhour = JSONUtils.json2list(projectCycle.getStudyhour(), Map.class);
			for(Map studyhour : cycleStudyhour){
				cycleStudyhourMap.put(studyhour.get("name").toString(), studyhour);
			}
			
		} else {
			String cycleId = request.getParameter("cycleId");//????????????id
			getProjectCycle(cycleId);
			startYear = Integer.parseInt(startYearStr);
			endYear = Integer.parseInt(endYearStr);
		}
		
		int startyear = startYear;
		int endyear = endYear;
		// 2014 ~ 2019
		List<String> cycle = new ArrayList<String>();
		while (startYear <= endYear) {
			cycle.add(startYear + "");
			startYear += 1;
		}
		sb.append(teacherTrainingRecordInfo(cycle,id));//????????????
		sb.append(getTeacherEduAdvance(id, startyear, endyear));//????????????????????????
		sb.append(getClassHours());//????????????
		
		responseJsonToHtml(sb);
	}
	
	
	/**
	 * ????????????????????????
	 * @param cycle 
	 */
	public StringBuilder teacherTrainingRecordInfo(List<String> cycle,int id) {
		StringBuilder sb = new StringBuilder();
		Object[] obj = null;
		TeacherTrainingRecords ttrRecords = null;
		TrainingCollege tc = null;
		TrainingSubject ts = null;
		Project project = null;
		int credit = 0;

		String traintype;

		// ????????????id?????????????????? 44781
		List list = this.iTeacherTrainingRecordsService.getTTRecordsByCycle(id, cycle);
		studyhourMap = new HashMap<String,Map<String, Object>>();
		sb.append("\"ttRecords\":[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				obj = (Object[]) list.get(i);
				ttrRecords = (TeacherTrainingRecords) obj[0];
				tc = (TrainingCollege) obj[1];
				ts = (TrainingSubject) obj[2];
				project = (Project) obj[3];

				// ????????????
				if (project.getTraintype() == 1) {
					traintype = "??????????????????????????????";
				} else if (project.getTraintype() == 2) {
					traintype = "????????????";
				} else {
					traintype = "??????????????????";
				}
				// ????????????
				List<Map> studyhourList = JSONUtils.json2list(ttrRecords.getStudyhour(), Map.class);
				for(Map map : studyhourList){
					if(studyhourMap.get(map.get("name").toString()) == null){
						Map<String, Object> amap = new HashMap<String, Object>();
						amap.put("name", map.get("name").toString());
						amap.put("nameCN", map.get("nameCN").toString());
						amap.put("value", 0);
						studyhourMap.put(map.get("name").toString(), amap);
					}
					
					Map<String, Object> hourMap = studyhourMap.get(map.get("name").toString());
					hourMap.put("value", Integer.valueOf(hourMap.get("value").toString()) + Integer.valueOf(map.get("value").toString()));
				}
				
				credit = ttrRecords.getCredit() == null ? 0 : ttrRecords.getCredit();
				teacherCredit += credit;
				
				sb.append("{");
				sb.append("\"time\":\""
						+ getRecordStartTimeAndEndTime(project.getId(),
								ts.getId(), tc.getId()) + "\",");// ????????????-????????????
				sb.append("\"tcName\":\"" + tc.getName() + "\",");// ????????????
				sb.append("\"projectName\":\"" + project.getName() + "\",");// ????????????
				sb.append("\"tsName\":\"" + ts.getName() + "\",");// ????????????
//				sb.append("\"trainingStatus\":\""
//						+ (ttrRecords.getTrainingStatus() == 3 ? "??????" : "?????????")
//						+ "\",");// ????????????
				String status = "?????????";
				if (ttrRecords.getTrainingStatus() == 0) {
					status = "??????";
				} else if (ttrRecords.getTrainingStatus() == 2) {
					status = "?????????";
				} else if (ttrRecords.getTrainingStatus() == 3) {
					status = "??????";
				} else if (ttrRecords.getTrainingStatus() == 4) {
					status = "?????????";
				} else if (ttrRecords.getTrainingStatus() == 5) {
					status = "??????";
				} else if (ttrRecords.getTrainingStatus() == 6) {
					status = "??????";
				}
				sb.append("\"trainingStatus\":\""
						+ status
						+ "\",");// ????????????
				sb.append("\"trainingScore\":\""
						+ (ttrRecords.getTrainingScore()==null?"0":ttrRecords.getTrainingScore()) + "\",");// ??????
				sb.append("\"traintype\":\"" + traintype + "\",");// ????????????
				sb.append("\"certificate\":\"" + (ttrRecords.getCertificate() == null ? "??????": ttrRecords.getCertificate())
						+ "\",");// ????????????
				
				sb.append("\"studyhour\":[");
				String studyhourStr = "";
				for(Map map : studyhourList){
					if(Integer.valueOf(map.get("value").toString()) > 0){
						studyhourStr = studyhourStr + JSONUtils.obj2json(map);
						studyhourStr = studyhourStr + ",";
					}
				}
				if(!"".equals(studyhourStr)){
					studyhourStr = studyhourStr.substring(0, studyhourStr.length() - 1);
					sb.append(studyhourStr);
				}
				sb.append("],");
				
				sb.append("\"credit\":\"" + credit + "\",");// ??????
				sb.append("\"trainingReason\":\""
						+ (ttrRecords.getTrainingReason() == null ? "???":ttrRecords.getTrainingReason()) + "\"");// ???????????????
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("],");
		return sb;
	}
	
	private String getRecordStartTimeAndEndTime(Integer projectId,
			Short subjectId, Integer collegeId) {
		String startTime = "";
		String endTime = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", projectId);
		params.put("trainingSubject", subjectId);
		params.put("trainingCollege", collegeId);
		List<ProjectApply> projectApplyList = iProjectApplyService
				.getProjectApplyByParams(params, null, null);
		Timestamp trainingStarttime = projectApplyList.get(0)
				.getTrainingStarttime();
		Timestamp trainingEndtime = projectApplyList.get(0)
				.getTrainingEndtime();
		try {
			if(trainingStarttime != null) {
				startTime = dataTimeConvertUtility.DateToString(trainingStarttime,
						"yyyy-MM-dd");
			} else {
				startTime = "0000-00-00";
			}
			
			if(trainingEndtime != null) {
				endTime = dataTimeConvertUtility.DateToString(trainingEndtime,
						"yyyy-MM-dd");
			} else {
				endTime = "0000-00-00";
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		return startTime + " ??? " + endTime;
	}
	
	/**
	 *  ????????????
	 * @return
	 */
	public StringBuilder getClassHours() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"classHoursCount\":{");
		/*
		 * ??????????????????????????? ??????????????????????????? ????????????
		 * ????????????????????????????????????????????????????????????????????? ???????????? 
		 */
		Map<String, String> statusMap = new HashMap<String, String>();
		for(String name : studyhourMap.keySet()){
			Map<String, Object> cycleStudyhour = cycleStudyhourMap.get(name);
			Map<String, Object> teacherStudyhour = studyhourMap.get(name);
			int cycleValue = Integer.valueOf(cycleStudyhour.get("value").toString());
			int teacherValue = Integer.valueOf(teacherStudyhour.get("value").toString());
			String teacherStatus = cycleValue > teacherValue ?"?????????":"?????????";
			teacherStatus = teacherValue == 0 ? "--" : teacherStatus;
			statusMap.put(name, teacherStatus);
		}
		
		if(studyhourMap.get("certralize") != null && studyhourMap.get("information") != null && studyhourMap.get("regional") != null 
				&& studyhourMap.get("school") != null && studyhourMap.get("totalhours") != null ){
			Map<String, Object> cycleCentralizeMap = cycleStudyhourMap.get("certralize");
			Map<String, Object> teacherCentralizeMap = studyhourMap.get("certralize");
			Map<String, Object> cycleInformationMap = cycleStudyhourMap.get("information");
			Map<String, Object> teacherInformationMap = studyhourMap.get("information");
			Map<String, Object> cycleRegionalMap = cycleStudyhourMap.get("regional");
			Map<String, Object> teacherRegionalMap = studyhourMap.get("regional");
			Map<String, Object> cycleSchoolMap = cycleStudyhourMap.get("school");
			Map<String, Object> teacherSchoolMap = studyhourMap.get("school");
			Map<String, Object> cycleTotalhoursMap = cycleStudyhourMap.get("totalhours");
			Map<String, Object> teacherTotalhoursMap = studyhourMap.get("totalhours");
			int cycleCentralize = Integer.valueOf(cycleCentralizeMap.get("value").toString());
			int teacherCentralize = Integer.valueOf(teacherCentralizeMap.get("value").toString());
			int cycleInformation = Integer.valueOf(cycleInformationMap.get("value").toString());
			int teacherInformation = Integer.valueOf(teacherInformationMap.get("value").toString());
			int cycleRegional = Integer.valueOf(cycleRegionalMap.get("value").toString());
			int teacherRegional = Integer.valueOf(teacherRegionalMap.get("value").toString());
			int cycleSchool = Integer.valueOf(cycleSchoolMap.get("value").toString());
			int teacherSchool = Integer.valueOf(teacherSchoolMap.get("value").toString());
			int cycleTotalhours = Integer.valueOf(cycleTotalhoursMap.get("value").toString());
			int teacherTotalhours = Integer.valueOf(teacherTotalhoursMap.get("value").toString());
			
			if(teacherTotalhours >= cycleTotalhours){
				if(teacherCentralize == 0 && teacherInformation == 0 && teacherRegional == 0 && teacherSchool == 0){
					statusMap.put("certralize", "?????????");
					statusMap.put("information", "?????????");
					statusMap.put("regional", "?????????");
					statusMap.put("school", "?????????");
					statusMap.put("totalhours", "?????????");
				}
			}
			
			if(teacherCentralize >= cycleCentralize && teacherInformation >= cycleInformation && teacherRegional >= cycleRegional && teacherSchool >= cycleSchool){
				statusMap.put("totalhours", "?????????");
			}
		}
		sb.append("\"studyhour\":[");
		String studyhour = "";
		List<Map> cycleStudyhour = JSONUtils.json2list(projectCycle.getStudyhour(), Map.class);
		if(!studyhourMap.isEmpty()) {
			for(Map pcMap : cycleStudyhour) {
				String name = pcMap.get("name").toString();
				Map<String, Object> teacherMap = studyhourMap.get(name);
				Map<String, Object> cycleMap = cycleStudyhourMap.get(name);
				String status = statusMap.get(name);
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("name", teacherMap.get("name"));
				resultMap.put("nameCN", teacherMap.get("nameCN"));
				resultMap.put("teacherValue", teacherMap.get("value"));
				resultMap.put("cycleValue", cycleMap.get("value"));
				resultMap.put("teacherStatus", status);
				
				studyhour = studyhour + JSONUtils.obj2json(resultMap);
				studyhour = studyhour + ",";
			}
		}
//		for(String name : studyhourMap.keySet()){
//			Map<String, Object> teacherMap = studyhourMap.get(name);
//			Map<String, Object> cycleMap = cycleStudyhourMap.get(name);
//			String status = statusMap.get(name);
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("name", teacherMap.get("name"));
//			resultMap.put("nameCN", teacherMap.get("nameCN"));
//			resultMap.put("teacherValue", teacherMap.get("value"));
//			resultMap.put("cycleValue", cycleMap.get("value"));
//			resultMap.put("teacherStatus", status);
//			
//			studyhour = studyhour + JSONUtils.obj2json(resultMap);
//			studyhour = studyhour + ",";
//		}
		if(!"".equals(studyhour)){
			studyhour = studyhour.substring(0, studyhour.length() - 1);
			sb.append(studyhour);
		}
		sb.append("],");
		
		sb.append("\"teacherCredit\":\"" + teacherCredit + "\"");

		sb.append("}");
		return sb;
	}
	
	
	@SuppressWarnings("unchecked")
	public StringBuilder getTeacherEduAdvance(int teacherId, int startYear, int endYear){
		StringBuilder sb = new StringBuilder();
		String startTime = Utlity.timeSpanToDateString(Utlity.getYearFirst(startYear));//??????????????????????????????
		String endTime = Utlity.timeSpanToDateString(Utlity.getYearLast(endYear));//???????????????????????????????????????
		
		//?????????????????????????????????????????????????????????????????????
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("teacher", teacherId);
		params.put("startYear", startTime);
		params.put("endYear", endTime);
		params.put("finalStatus", 1);
		List<TeacherEduAdvance> list = this.iTeacherEduAdvanceService.getListByParams(params, -1, -1, "createtime desc");
		if(list != null && list.size() > 0){
			studyhourMap = new HashMap<String,Map<String, Object>>();
			String cycleStudyhourStr = JSONUtils.obj2json(cycleStudyhourMap);
			Map<String, Map> map = JSONUtils.json2map(cycleStudyhourStr, Map.class);
			for(String name :map.keySet()){
				studyhourMap.put(name, (Map<String, Object>)map.get(name));
			}
			
			sb.append("\"teacherEduAdvance\":{");
			TeacherEduAdvance tea = list.get(0);
			String starttime = "";
			String endtime = "";
			try {
				starttime = dataTimeConvertUtility.DateToString(tea.getStarttime(), "yyyy-MM-dd");
				endtime = dataTimeConvertUtility.DateToString(tea.getEndtime(), "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String time = (starttime + "???" + endtime);
			sb.append("\"time\":\""+time+"\"");
			sb.append(",");
			sb.append("\"graduation\":\""+tea.getGraduation()+"\"");
			sb.append(",");
			sb.append("\"major\":\""+tea.getMajor()+"\"");
			sb.append(",");
			sb.append("\"certificate\":\""+tea.getCertificate()+"\"");
			sb.append(",");
			String status = "????????????";
			sb.append("\"status\":\""+status+"\"");
			sb.append(",");
			sb.append("\"oldBackground\":\""+tea.getOldEducationBack()+"\"");
			sb.append(",");
			sb.append("\"background\":\""+tea.getEducationBackground().getName()+"\"");
			sb.append("},");
		}
		return sb;
	}
	
	private void responseJsonToHtml(StringBuilder builder) {
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("{");
		countBuilder.append("\"status\":\"OK\"");
		countBuilder.append(",");
		countBuilder.append("\"records\":{");
		countBuilder.append(builder);
		countBuilder.append("}");
		countBuilder.append("}");

		Utlity.ResponseWrite(countBuilder.toString(), "application/json",
				response);
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

		public ITrainingAdminService getiTrainingAdminService() {
			return iTrainingAdminService;
		}

		public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
			this.iTrainingAdminService = iTrainingAdminService;
		}

		public IAreaService getiAreaService() {
			return iAreaService;
		}

		public void setiAreaService(IAreaService iAreaService) {
			this.iAreaService = iAreaService;
		}

		public IProjectTypeService getiProjectTypeService() {
			return iProjectTypeService;
		}

		public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
			this.iProjectTypeService = iProjectTypeService;
		}


		public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
			return iTeacherTrainingRecordsService;
		}

		public void setiTeacherTrainingRecordsService(
				ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
			this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
		}

		public IHsdtestService getiHsdTestService() {
			return iHsdTestService;
		}

		public void setiHsdTestService(IHsdtestService iHsdTestService) {
			this.iHsdTestService = iHsdTestService;
		}

		public ITeacherService getiTeacherService() {
			return iTeacherService;
		}

		public void setiTeacherService(ITeacherService iTeacherService) {
			this.iTeacherService = iTeacherService;
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

		public IProjectCycleService getProjectCycleService() {
			return projectCycleService;
		}

		public void setProjectCycleService(IProjectCycleService projectCycleService) {
			this.projectCycleService = projectCycleService;
		}

		
		public ITeacherEduAdvanceService getiTeacherEduAdvanceService() {
			return iTeacherEduAdvanceService;
		}
		

		public void setiTeacherEduAdvanceService(
				ITeacherEduAdvanceService iTeacherEduAdvanceService) {
			this.iTeacherEduAdvanceService = iTeacherEduAdvanceService;
		}

		
}
