package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

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
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectCycleService;
import cn.zeppin.service.ITeacherEduAdvanceService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 学员个人档案 教师基本信息、教师所有培训记录、教师学时认定结果、教师学历提升申报记录
 */
public class PersonalFileAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	@SuppressWarnings("unused")
	private HttpSession session;
	private HttpServletResponse response;

	private ITeacherService iTeacherService;
	private IAreaService iAreaService;// 地区操作
	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private IProjectApplyService iProjectApplyService;
	private IProjectCycleService projectCycleService;
	private ITeacherEduAdvanceService iTeacherEduAdvanceService;

	private Teacher teacher;// 教师表基本信息
	private teacherEx teacherEx;// 教师其他信息
	// 项目培训周期
	private LinkedHashMap<Integer, String[]> cycleHash;
	
	// 认定标准
	private Map<String, Map<String, Object>> cycleStudyhourMap;
	
	// 已认定学时(周期内培训项目的总学时)
	private Map<String, Map<String, Object>> studyhourMap;
	
	private int teacherCredit;
	// 教师id
	private int id;

	private ProjectCycle projectCycle;
	//头像
	private String teacherHeadPath;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String init() {
		initServlert();
		id = Integer.parseInt(request.getParameter("Id"));// 通过学员id获取学员信息
		teacherBaseInfo();
		getProjectCycle(null);
		return "init";
	}
	
	public void reLoad(){
		initServlert();
		int startYear;// 开始年份
		int endYear;// 结束年份
		String startYearStr = request.getParameter("startYear");
		String endYearStr = request.getParameter("endYear");
		
		id = Integer.parseInt(request.getParameter("Id"));// 通过学员id获取学员信息
		cycleStudyhourMap = new HashMap<String,Map<String, Object>>();
		
		//先获取周期信息，
		StringBuilder sb = new StringBuilder();
		//如果前端没有传时间，则显示周期里最近一段的记录信息
		if (startYearStr.equals("") || endYearStr.equals("")) {//默认
			getProjectCycle(null);
			startYear = Integer.parseInt(projectCycle.getStartyear());
			endYear = Integer.parseInt(projectCycle.getEndyear());
			
			List<Map> cycleStudyhour = JSONUtils.json2list(projectCycle.getStudyhour(), Map.class);
			for(Map studyhour : cycleStudyhour){
				cycleStudyhourMap.put(studyhour.get("name").toString(), studyhour);
			}
		} else {
			String cycleId = request.getParameter("cycleId");//获取周期id
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
		sb.append(teacherTrainingRecordInfo(cycle));//培训记录
		sb.append(getTeacherEduAdvance(id, startyear, endyear));//学历提升认证记录
		sb.append(getClassHours());//认定学时
		
		responseJsonToHtml(sb);
	}

	/**
	 * 教师基本信息
	 */
	public void teacherBaseInfo() {
		String hqlString;
		// id = Integer.parseInt(request.getParameter("id"));// 通过学员id获取学员信息
		// id = 36705;// 通过学员id获取学员信息
		String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
		
		teacher = iTeacherService.get(id);
		teacherHeadPath = teacher.getHeadPicPath() == null ? "":path+teacher.getHeadPicPath();
//		teacherHeadPath = teacher.getHeadPicPath();		
		teacherEx = new teacherEx();
		teacherEx.setTeacher(teacher);
		teacherEx.setSexString(teacher.getSex() == 1 ? "男" : "女");
		if (teacher.getStatus() == 1) {
			teacherEx.setStatusString("在职");
		} else if (teacher.getStatus() == 2) {
			teacherEx.setStatusString("离职");
		} else if (teacher.getStatus() == 3) {
			teacherEx.setStatusString("转出");
		} else if (teacher.getStatus() == 4) {
			teacherEx.setStatusString("退休");
		} else {
			teacherEx.setStatusString("死亡");
		}
		teacherEx.setAuthorized(teacher.getAuthorized() == 1 ? "在编" : "非编");
		if (teacher.getTeachingAge() != null) {
			teacherEx.setTeachingAge(String.valueOf(Utlity.getAge(teacher
					.getTeachingAge())));
		}
		teacherEx.setIsMultiLanguage(teacher.getMultiLanguage() ? "是" : "否");
		// 获取主要教学语言
		hqlString = "from TeachingLanguage where teacher=" + teacher.getId()
				+ " and isprime=true";

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
	}

	/**
	 * 获取学员培训记录
	 * @param cycle 
	 */
	@SuppressWarnings("rawtypes")
	public StringBuilder teacherTrainingRecordInfo(List<String> cycle) {
		StringBuilder sb = new StringBuilder();
		Object[] obj = null;
		TeacherTrainingRecords ttrRecords = null;
		TrainingCollege tc = null;
		TrainingSubject ts = null;
		Project project = null;
		int credit = 0;

		String traintype;

		// 根据教师id和周期查培训 44781
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

				// 培训方式
				if (project.getTraintype() == 1) {
					traintype = "集中面授（同步在线）";
				} else if (project.getTraintype() == 2) {
					traintype = "远程培训";
				} else {
					traintype = "混合培训方式";
				}
				// 周期学时
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
				sb.append("{");
				sb.append("\"time\":\""
						+ getRecordStartTimeAndEndTime(project.getId(),
								ts.getId(), tc.getId()) + "\",");// 开始日期-截止日期
				sb.append("\"tcName\":\"" + tc.getName() + "\",");// 承训单位
				sb.append("\"projectName\":\"" + project.getName() + "\",");// 培训项目
				sb.append("\"tsName\":\"" + ts.getName() + "\",");// 培训学科
//				sb.append("\"trainingStatus\":\""
//						+ (ttrRecords.getTrainingStatus() == 3 ? "合格" : "不合格")
//						+ "\",");// 是否合格
				String status = "未报到";
				if (ttrRecords.getTrainingStatus() == 0) {
					status = "异动";
				} else if (ttrRecords.getTrainingStatus() == 2) {
					status = "已报到";
				} else if (ttrRecords.getTrainingStatus() == 3) {
					status = "培训合格（已结业）";
				} else if (ttrRecords.getTrainingStatus() == 4) {
					status = "培训不合格";
				} else if (ttrRecords.getTrainingStatus() == 5) {
					status = "培训优秀（已结业）";
				} else if (ttrRecords.getTrainingStatus() == 6) {
					status = "培训良好（已结业）";
				}
				sb.append("\"trainingStatus\":\""
						+ status
						+ "\",");// 是否合格
				
				sb.append("\"trainingScore\":\""
						+ (ttrRecords.getTrainingScore()==null?"0":ttrRecords.getTrainingScore()) + "\",");// 成绩
				sb.append("\"traintype\":\"" + traintype + "\",");// 培训方式
				sb.append("\"certificate\":\"" + (ttrRecords.getCertificate() == null ? "暂无": ttrRecords.getCertificate())
						+ "\",");// 证书编号
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
				sb.append("\"credit\":\"" + credit + "\",");// 学分
				sb.append("\"uuid\":\"" + ttrRecords.getUuid() + "\",");
				if (ttrRecords.getRegistrant() != null && ttrRecords.getRegistrant() > 0) {
					sb.append("\"show\":" + true + "");
				} else {
					sb.append("\"show\":" + false + "");
				}
				sb.append(",");
				sb.append("\"trainingReason\":\""
						+ (ttrRecords.getTrainingReason() == null ? "无":ttrRecords.getTrainingReason()) + "\"");// 不合格原因
				sb.append("},");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("],");
		return sb;
	}

	/**
	 * 获取培训记录的开始时间到结束时间
	 * 
	 * @param projectId
	 * @param subjectId
	 * @param collegeId
	 * @return
	 */
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
		return startTime + " 至 " + endTime;
	}

	/**
	 * 获取周期信息
	 */
	public void getProjectCycle(String cycleId) {
		String[] str = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 1);
		if(cycleId != null){
			params.put("id", cycleId);
		}
		List<ProjectCycle> list = projectCycleService.getListByParams(params, 0, -1, "id desc");
		if (list != null && list.size() > 0) {
			projectCycle = list.get(0);
			cycleHash = new LinkedHashMap<Integer, String[]>();
			for (ProjectCycle projectCycle : list) {
				if (!this.cycleHash.containsKey(projectCycle.getId())) {
					str = new String[5];
					str[0] = projectCycle.getStartyear();// 开始年份
					str[1] = projectCycle.getEndyear();// 结束年份
					str[2] = projectCycle.getName();// 周期名称
					cycleStudyhourMap = new HashMap<String,Map<String, Object>>();
					List<Map> cycleStudyhour = JSONUtils.json2list(projectCycle.getStudyhour(), Map.class);
					for(Map studyhour : cycleStudyhour){
						cycleStudyhourMap.put(studyhour.get("name").toString(), studyhour);
					}
					this.cycleHash.put(projectCycle.getId(), str);
				}
			}
		}
	}

	/**
	 *  认定学时
	 * @return
	 */
	public StringBuilder getClassHours() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"classHoursCount\":{");
		
		Map<String, String> statusMap = new HashMap<String, String>();
		for(String name : studyhourMap.keySet()){
			Map<String, Object> cycleStudyhour = cycleStudyhourMap.get(name);
			Map<String, Object> teacherStudyhour = studyhourMap.get(name);
			int cycleValue = Integer.valueOf(cycleStudyhour.get("value").toString());
			int teacherValue = Integer.valueOf(teacherStudyhour.get("value").toString());
			String teacherStatus = cycleValue > teacherValue ?"未完成":"已完成";
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
					statusMap.put("certralize", "已完成");
					statusMap.put("information", "已完成");
					statusMap.put("regional", "已完成");
					statusMap.put("school", "已完成");
					statusMap.put("totalhours", "已完成");
				}
			}
			
			if(teacherCentralize >= cycleCentralize && teacherInformation >= cycleInformation && teacherRegional >= cycleRegional && teacherSchool >= cycleSchool){
				statusMap.put("totalhours", "已完成");
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

	
	public StringBuilder getTeacherEduAdvance(int teacherId, int startYear, int endYear){
		StringBuilder sb = new StringBuilder();
		String startTime = Utlity.timeSpanToDateString(Utlity.getYearFirst(startYear));//周期第一天为起始年份
		String endTime = Utlity.timeSpanToDateString(Utlity.getYearLast(endYear));//取周期的最后一天为截止日期
		
		//获取周期内教师提交的审核通过的学历提升认定记录
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
			String time = (starttime + "至" + endtime);
			sb.append("\"time\":\""+time+"\"");
			sb.append(",");
			sb.append("\"graduation\":\""+tea.getGraduation()+"\"");
			sb.append(",");
			sb.append("\"major\":\""+tea.getMajor()+"\"");
			sb.append(",");
			sb.append("\"certificate\":\""+tea.getCertificate()+"\"");
			sb.append(",");
			String status = "认证通过";
			sb.append("\"status\":\""+status+"\"");
			sb.append(",");
			sb.append("\"oldBackground\":\""+tea.getOldEducationBack()+"\"");
			sb.append(",");
			sb.append("\"background\":\""+tea.getEducationBackground().getName()+"\"");
			sb.append("},");
		}
		return sb;
	}
	
	/**
	 * 提交json到html中
	 * 
	 * @param builder
	 *            不同情况 拼接的json
	 */
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

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(
			ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public teacherEx getTeacherEx() {
		return teacherEx;
	}

	public void setTeacherEx(teacherEx teacherEx) {
		this.teacherEx = teacherEx;
	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(
			IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectCycleService getProjectCycleService() {
		return projectCycleService;
	}

	public void setProjectCycleService(IProjectCycleService projectCycleService) {
		this.projectCycleService = projectCycleService;
	}

	public LinkedHashMap<Integer, String[]> getCycleHash() {
		return cycleHash;
	}

	public void setCycleHash(LinkedHashMap<Integer, String[]> cycleHash) {
		this.cycleHash = cycleHash;
	}

	
	public ITeacherEduAdvanceService getiTeacherEduAdvanceService() {
		return iTeacherEduAdvanceService;
	}
	

	public void setiTeacherEduAdvanceService(
			ITeacherEduAdvanceService iTeacherEduAdvanceService) {
		this.iTeacherEduAdvanceService = iTeacherEduAdvanceService;
	}

	public String getTeacherHeadPath() {
		return teacherHeadPath;
	}

	public void setTeacherHeadPath(String teacherHeadPath) {
		this.teacherHeadPath = teacherHeadPath;
	}

}
