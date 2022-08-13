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
	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作
	private IProjectCycleService projectCycleService;
	private ITeacherEduAdvanceService iTeacherEduAdvanceService;
	
	// 认定标准
	private Map<String, Map<String, Object>> cycleStudyhourMap;
	
	// 已认定学时(周期内培训项目的总学时)
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
	 * 培训记录页面初始化
	 * 回传的信息有teacher_train_records中的相关信息
	 * totalCount--总培训记录数
	 * createTime--发布时间
	 * trainingStatus--培训状态
	 * subjectName--培训学科
	 * projectName--培训项目
	 * trainingCollege--承训单位
	 * trainingAdd--培训地址
	 * trainingScore--培训成绩
	 * trainHour--获得学时
	 * certificate--证书编号
	 * trainingOnlineHour--获得网络研修学时
	 * trainingClassHour--培训总学时
	 * trainingStartTime--培训开始时间
	 * trainingEndTime--培训结束时间
	 * 
	 * trainType --培训方式
	 * 
	 * 未报道的、培训未结束的都没有培训成绩相关内容
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
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		
		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		
		// 排序（格式creatime-desc or creattime-asc）
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
		//其他筛选参数
		//项目ID
		int projectId = 0;
		if(request.getParameter("projectId")!=null){
			projectId = Integer.parseInt(request.getParameter("projectId"));
			pagram.put("projectId", projectId);
		}
		//学科ID
		int subjectId = 0;
		if(request.getParameter("subjectId")!=null){
			projectId = Integer.parseInt(request.getParameter("subjectId"));
			pagram.put("subjectId", subjectId);
		}
		//承训单位ID
		int trainCollegeId = 0;
		if(request.getParameter("trainCollegeId")!=null){
			projectId = Integer.parseInt(request.getParameter("trainCollegeId"));
			pagram.put("trainCollegeId", trainCollegeId);
		}
		//培训方式ID
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
			sb.append("\"Message\":\"查询完毕\"");
			sb.append(",");
			sb.append("\"Records\":[");
			for(int i = 0; i < li.size(); i++){
				Object[] obj = (Object[])li.get(i);
				TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
				Project p = (Project)obj[1];
				ProjectApply pa = (ProjectApply)obj[2];
				
				//培训结束的记录
				if(ttr.getTrainingStatus()>2){
					endCount++;
				}
				
//				* totalCount--总培训记录数
//				 * createTime--发布时间
//				 * trainingStatus--培训状态
//				 * subjectName--培训学科
//				 * projectName--培训项目
//				 * trainingCollege--承训单位
//				 * trainingAddr--培训地址
//				 * trainingScore--培训成绩
//				 * trainHour--获得学时
//				 * certificate--证书编号
//				 * trainingOnlineHour--获得网络研修学时
//				 * trainingClassHour--培训总学时
//				 * trainingStartTime--培训开始时间
//				 * trainingEndTime--培训结束时间
//				 * trainingReason--培训不合格原因
//				 * trainType --培训方式
//				 * ttrId --培训记录ID
				//20170320 采用新学时标准 取缔原学时信息
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
					sb.append("\"certificate\":\"未获得\"");
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
					sb.append("\"trainingReason\":\"无\"");
				}else{
					sb.append("\"trainingReason\":\""+ttr.getTrainingReason()+"\"");
				}
				sb.append(",");
				if(pa.getTrainingAddress() == null){
					sb.append("\"trainingAddr\":\"无\"");
				}else{
					sb.append("\"trainingAddr\":\""+pa.getTrainingAddress()+"\"");
				}
				sb.append(",");
				sb.append("\"trainingClassHour\":\""+(pa.getTrainingClasshour()+pa.getTrainingOnlineHour())+"\"");
				sb.append(",");
				if(pa.getTrainingStarttime() != null){
					sb.append("\"trainingStartTime\":\""+pa.getTrainingStarttime()+"\"");
				}else{
					sb.append("\"trainingStartTime\":\"待定\"");
				}
				
				sb.append(",");
				sb.append("\"trainingEndTime\":\""+pa.getTrainingEndtime()+"\"");
				sb.append(",");
				String trainUrl = "";//教师一键登录连接地址
				if(p.getTraintype() == 1){
					sb.append("\"trainType\":\"集中面授（同步在线）\"");
				}else if(p.getTraintype() == 2){
					sb.append("\"trainType\":\"远程培训\"");
					trainUrl = pa.getTrainingCollege().getTrainURL();
					if(trainUrl != null && !"".equals(trainUrl)){
						if(!trainUrl.startsWith("http://")){
							trainUrl = "http://"+trainUrl;
						}
					}else{
						trainUrl="";
					}
				}else{
					sb.append("\"trainType\":\"混合培训方式\"");
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
				sb.append("\"trainTypeN\":\""+p.getTraintype()+"\"");//培训方式--用以区分是否显示‘去报名’按钮
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
			sb.append("\"Message\":\"暂无数据\"");
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
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String ttrId = request.getParameter("ttrId") == null? "":request.getParameter("ttrId");
		
		if(!"".equals(ttrId)){
			TeacherTrainingRecords ttr = this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId));
			if(ttr.getProject().getIsTest()){
				//判断周期内 是否已经进行过测评
				List<ProjectCycle> list = this.projectCycleService.findAll();
				ProjectCycle pc = null;
				if(list != null && list.size() > 0){
					for(ProjectCycle pcs : list){
						String startYear = pcs.getStartyear();
						String endYear = pcs.getEndyear();
						if(Integer.parseInt(ttr.getProject().getYear())>= Integer.parseInt(startYear) && Integer.parseInt(ttr.getProject().getYear())<=Integer.parseInt(endYear)){//符合条件 返回true
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
						sb.append("\"Message\":\"您还未进行信息技术能力提升工程学前测评，请先完成学前测评后，再去培训吧！\"");
						sb.append("}");
						
					}else{
						sb.append("{");
						sb.append("\"Result\":\"OK\"");
						sb.append(",");
						sb.append("\"Message\":\"继续跳转。。。\"");
						sb.append("}");
					}
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				// 判断当年已经回答过几次
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
					sb.append("\"Message\":\"您还未进行信息技术能力提升工程学前测评，请先完成学前测评后，再去培训吧！\"");
					sb.append("}");
					
				}else{
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"继续跳转。。。\"");
					sb.append("}");
				}
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}else{
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"继续跳转。。。\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"异常\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
	}
	
	/**
	 * 初始化教师个人培训档案
	 */
	public void initPersonalFile(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		
		if(us==null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"用户未登录\"");
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
		// id = Integer.parseInt(request.getParameter("id"));// 通过学员id获取学员信息
		// id = 36705;// 通过学员id获取学员信息
		String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
		
		
		Teacher teacher = iTeacherService.get(us.getId());
		String teacherHeadPath = teacher.getHeadPicPath() == null ? "":path+teacher.getHeadPicPath();
//		String teacherHeadPath = teacher.getHeadPicPath();		
		teacherEx teacherEx = new teacherEx();
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
		sb.append("{\"teacher\":{");
		sb.append("\"teacherName\":\""+teacher.getName()+"\",");
		String mobile = teacher.getMobile() == null ?"" : teacher.getMobile();
		if("".equals(mobile)){
			mobile = "暂无";
		}
		sb.append("\"teacherMobile\":\""+mobile+"\",");
		sb.append("\"teacherSex\":\""+teacherEx.getSexString()+"\",");
		sb.append("\"teacherFolk\":\""+teacher.getEthnic().getName()+"\",");
		sb.append("\"teacherIdcard\":\""+teacher.getIdcard()+"\",");
		String politicss = "暂无";
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
//				str[0] = pc.getStartyear();// 开始年份
//				str[1] = pc.getEndyear();// 结束年份
//				str[2] = pc.getName();// 周期名称
				
				sb.append("},");
				//周期学时认定标准
				
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
	 * 加载培训记录列表
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
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		int startYear;// 开始年份
		int endYear;// 结束年份
		String startYearStr = request.getParameter("startYear");
		String endYearStr = request.getParameter("endYear");
		
		Integer id = us.getId();// 通过学员id获取学员信息
		
		//先获取周期信息，
		//如果前端没有传时间，则显示周期里最近一段的记录信息
		if (startYearStr.equals("") || endYearStr.equals("")) {//默认
			getProjectCycle(null);
//			this.projectCycle = (ProjectCycle)session.getAttribute("projectCycle");
			startYear = Integer.parseInt(projectCycle.getStartyear());
			endYear = Integer.parseInt(projectCycle.getEndyear());
			//周期内学时认定标准
			cycleStudyhourMap = new HashMap<String,Map<String, Object>>();
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
		sb.append(teacherTrainingRecordInfo(cycle,id));//培训记录
		sb.append(getTeacherEduAdvance(id, startyear, endyear));//学历提升认证记录
		sb.append(getClassHours());//认定学时
		
		responseJsonToHtml(sb);
	}
	
	
	/**
	 * 获取学员培训记录
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
				
				credit = ttrRecords.getCredit() == null ? 0 : ttrRecords.getCredit();
				teacherCredit += credit;
				
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
					status = "合格";
				} else if (ttrRecords.getTrainingStatus() == 4) {
					status = "不合格";
				} else if (ttrRecords.getTrainingStatus() == 5) {
					status = "优秀";
				} else if (ttrRecords.getTrainingStatus() == 6) {
					status = "良好";
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
				sb.append("\"trainingReason\":\""
						+ (ttrRecords.getTrainingReason() == null ? "无":ttrRecords.getTrainingReason()) + "\"");// 不合格原因
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
		return startTime + " 至 " + endTime;
	}
	
	/**
	 *  认定学时
	 * @return
	 */
	public StringBuilder getClassHours() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"classHoursCount\":{");
		/*
		 * 认定规则：只有大项 所有小项按大项设定 认定合格
		 * 既有大项也有小项，以小项为主，所有小项均合格的 认定合格 
		 */
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
	
	
	@SuppressWarnings("unchecked")
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
