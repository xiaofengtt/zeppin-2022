package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.entity.Language;
import cn.zeppin.entity.Politics;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectLevel;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherSignupActionScope;
import cn.zeppin.entity.TeacherSignupCondition;
import cn.zeppin.entity.TeacherSignupReActionScope;
import cn.zeppin.entity.TeacherSignupSizer;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IEthnicService;
import cn.zeppin.service.IGradeService;
import cn.zeppin.service.IJobTitleService;
import cn.zeppin.service.ILanguageService;
import cn.zeppin.service.IPoliticsService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectLevelService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectSubjectRangeService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ISubjectService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherSignupSizerService;
import cn.zeppin.service.ITeacherTrainingAssginedService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.TeacherSignupUtlity;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @category ?????????
 */
public class TeacherSignupSizerAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(TeacherSignupSizerAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private ITeacherSignupSizerService teacherSignupSizerService;
//	private IProjectCycleClasshoursService projectCycleClasshoursService;
	
	private ILanguageService iLanguageService;// ????????????
	private ISubjectService iSubjectService;// ????????????
	private IGradeService iGradeService;// ????????????
	private IJobTitleService iJobTitleService;// ??????
	private IEthnicService iEthnicService;// ??????
	private IPoliticsService iPoliticsService;// ????????????
	private IProjectLevelService projectLevelService;//????????????
	private IProjectTypeService projectTypeService;//????????????
	private IProjectService iProjectService;//??????
	private ITrainingSubjectService iTrainingSubjectService;//????????????
	
	private ITeacherTrainingRecordsService iTeacherTrainingRecords;
	private IProjectApplyService iProjectApplyService; // ??????????????????
	private ITeacherService iTeacherService;// ????????????
	private ITeacherTrainingAssginedService teacherTrainingAssignedService;
	private IProjectSubjectRangeService iProjectSubjectRangeService;
	
	private String[] yearArray;
	
//	private String[] yearArray;

	public TeacherSignupSizerAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * ?????????????????????
	 */
	public void getList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
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
		String sort = request.getParameter("jtSorting");
		String sorts = "";
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sorts += sortname + " " + sorttype;
		}
		//???????????????
		String name = request.getParameter("name") == null? "" : request.getParameter("name");
		String status = request.getParameter("status") == null? "-1" : request.getParameter("status");
		if("".equals(status)){
			status = "-1";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(name)){
			params.put("name", name);
		}
		if(!"-1".equals(status)){
			params.put("status", status);
		}
		int count = this.teacherSignupSizerService.getListCountByParams(params);
		List<TeacherSignupSizer> lst = this.teacherSignupSizerService.getListByParams(params, start, limit, sorts);
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"TotalRecordCount\":" + count);
		sb.append(",");
		sb.append("\"Records\":[");
		if(lst != null && lst.size() > 0){
			for(TeacherSignupSizer tss : lst){
				sb.append("{");
				sb.append("\"id\":"+tss.getId());
				sb.append(",");
				sb.append("\"name\":\""+tss.getName()+"\"");
				sb.append(",");
				sb.append("\"weight\":\""+tss.getWeight()+"\"");
				sb.append(",");
				sb.append("\"createtime\":\""+Utlity.timeSpanToString(tss.getCreatetime())+"\"");
				sb.append(",");
				sb.append("\"status\":\""+tss.getStatus()+"\"");
				sb.append("},");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * ?????????????????????
	 * ???????????????
	 */
	public void save(){
		initServlert();
//		StringBuilder sb = new StringBuilder();
		String name = request.getParameter("name") == null ? "" :  request.getParameter("name");
		if("".equals(name)){
			sendResponse("ERROR", "????????????????????????");
			return;
		}
		
		String status = request.getParameter("status") == null ? "" :  request.getParameter("status");
		if("".equals(status)){
			sendResponse("ERROR", "????????????????????????");
			return;
		}
		
		String weight = request.getParameter("weight") == null ? "" :  request.getParameter("weight");
		if("".equals(weight)){
			sendResponse("ERROR", "????????????????????????");
			return;
		}
		
		String startyear = request.getParameter("startyear") == null ? "" :  request.getParameter("startyear");
		if("".equals(startyear)){
			sendResponse("ERROR", "??????????????????????????????");
			return;
		}
		
		String endyear = request.getParameter("endyear") == null ? "" :  request.getParameter("endyear");
		if("".equals(endyear)){
			sendResponse("ERROR", "??????????????????????????????");
			return;
		}
		
		String projectlevel = request.getParameter("projectlevel") == null ? "" :  request.getParameter("projectlevel");
		if("".equals(projectlevel)){
			sendResponse("ERROR", "?????????????????????");
			return;
		}
		
		String projecttype = request.getParameter("projecttype") == null ? "" :  request.getParameter("projecttype");
		if("".equals(projecttype)){
			sendResponse("ERROR", "?????????????????????");
			return;
		}
		
		String project = request.getParameter("project") == null ? "" :  request.getParameter("project");
		if("".equals(project)){
			sendResponse("ERROR", "?????????????????????");
			return;
		}
		
		String trainingsubject = request.getParameter("trainingsubject") == null ? "" :  request.getParameter("trainingsubject");
		if("".equals(trainingsubject)){
			sendResponse("ERROR", "?????????????????????");
			return;
		}
		
		String starthours = request.getParameter("starthours") == null ? "" :  request.getParameter("starthours");
		if("".equals(starthours)){
			sendResponse("ERROR", "???????????????????????????????????????");
			return;
		}
		
		String endhours = request.getParameter("endhours") == null ? "" :  request.getParameter("endhours");
		if("".equals(endhours)){
			sendResponse("ERROR", "???????????????????????????????????????");
			return;
		}
		
		
		String teachingGrade = request.getParameter("teachingGrade") == null ? "" : request.getParameter("teachingGrade");
		
		String teachingSubject = request.getParameter("teachingSubject") == null ? "" : request.getParameter("teachingSubject");
		
		String teachingLanguage = request.getParameter("teachingLanguage") == null ? "" : request.getParameter("teachingLanguage");
		
		String multiLanguage = request.getParameter("multiLanguage") == null ? "" : request.getParameter("multiLanguage");
		
		String jobTitle = request.getParameter("jobTitle") == null ? "" : request.getParameter("jobTitle");
		
		String ethnic = request.getParameter("ethnic") == null ? "" : request.getParameter("ethnic");
		
		String startTeachingAge = request.getParameter("startTeachingAge") == null ? "" : request.getParameter("startTeachingAge");
		
		String endTeachingAge = request.getParameter("endTeachingAge") == null ? "" : request.getParameter("endTeachingAge");
		if(!"".equals(startTeachingAge)){
			if("".equals(endTeachingAge)){
				sendResponse("ERROR", "????????????????????????");
				return;
			}
		}
		
		String startTeacherAge = request.getParameter("startTeacherAge") == null ? "" : request.getParameter("startTeacherAge");
		
		String endTeacherAge = request.getParameter("endTeacherAge") == null ? "" : request.getParameter("endTeacherAge");
		if(!"".equals(startTeacherAge)){
			if("".equals(endTeacherAge)){
				sendResponse("ERROR", "????????????????????????");
				return;
			}
		}
		
		String police = request.getParameter("police") == null ? "" : request.getParameter("police");
		
		/*
		 * ??????????????????????????????
		 */
		TeacherSignupActionScope tsas = new TeacherSignupActionScope();
		
		//????????????
		Map<String, Object> years = new HashMap<String, Object>();
		years.put("startyear", startyear);
		years.put("endyear", endyear);
		tsas.setYears(years);
		
		if("0".equals(starthours) && "0".equals(endhours)){
			//???????????????0???????????????????????????
		}else{
			Map<String, Object> hours = new HashMap<String, Object>();
			hours.put("starthours", starthours);
			hours.put("endhours", endhours);
			tsas.setHours(hours);
		}
		
		
		//??????????????????
		if("all".equals(projectlevel)){
			List<String> levels = new ArrayList<String>();
			levels.add("all");
			tsas.setProjectLevel(levels);
		}else{
			String[] level = projectlevel.split(",");
			if(level.length > 0){
				List<String> levels = new ArrayList<String>();
				for(String str : level){
					levels.add(str);
				}
				tsas.setProjectLevel(levels);
			}
		}
		
		//??????????????????
		if("all".equals(projecttype)){
			List<String> types = new ArrayList<String>();
			types.add("all");
			tsas.setProjectType(types);
		}else{
			String[] type = projecttype.split(",");
			if(type.length > 0){
				List<String> types = new ArrayList<String>();
				for(String str : type){
					types.add(str);
				}
				tsas.setProjectType(types);
			}
		}
		
		//????????????
		if("all".equals(project)){
			List<String> levels = new ArrayList<String>();
			levels.add("all");
			tsas.setProject(levels);
		}else{
			String[] level = project.split(",");
			if(level.length > 0){
				List<String> levels = new ArrayList<String>();
				for(String str : level){
					levels.add(str);
				}
				tsas.setProject(levels);
			}
		}
		
		//????????????
		if("all".equals(trainingsubject)){
			List<String> levels = new ArrayList<String>();
			levels.add("all");
			tsas.setSubject(levels);
		}else{
			String[] level = trainingsubject.split(",");
			if(level.length > 0){
				List<String> levels = new ArrayList<String>();
				for(String str : level){
					levels.add(str);
				}
				tsas.setSubject(levels);
			}
		}
		
		
		String count1 = request.getParameter("count1") == null ? "" : request.getParameter("count1");
		
		String count2 = request.getParameter("count2") == null ? "" : request.getParameter("count2");
		
		String count3 = request.getParameter("count1") == null ? "" : request.getParameter("count2");
		
		if(!"".equals(count1)){
			if("".equals(count2) || "".equals(count3)){
				sendResponse("ERROR", "???????????????????????????");
				return;
			}
		}
		
		String startyearre = request.getParameter("startyearre") == null ? "" :  request.getParameter("startyearre");
		
		String endyearre = request.getParameter("endyearre") == null ? "" :  request.getParameter("endyearre");
		if(!"".equals(startyearre)){
			if("".equals(endyearre)){
				sendResponse("ERROR", "??????????????????????????????");
				return;
			}
		}
		
		String projectlevelre = request.getParameter("projectlevelre") == null ? "" :  request.getParameter("projectlevelre");
		
		String projecttypere = request.getParameter("projecttypere") == null ? "" :  request.getParameter("projecttypere");
		
		String projectre = request.getParameter("projectre") == null ? "" :  request.getParameter("projectre");
		
		String trainingsubjectre = request.getParameter("trainingsubjectre") == null ? "" :  request.getParameter("trainingsubjectre");
		
		String starthoursre = request.getParameter("starthoursre") == null ? "" :  request.getParameter("starthoursre");
		
		String endhoursre = request.getParameter("endhoursre") == null ? "" :  request.getParameter("endhoursre");
		if(!"".equals(starthoursre)){
			if("".equals(endhoursre)){
				sendResponse("ERROR", "???????????????????????????????????????");
				return;
			}
		}
		
//	    private List<String> police;
		TeacherSignupReActionScope tsrs = new TeacherSignupReActionScope();
		
		if(!"".equals(count1)){
			Map<String, Object> trainingCount = new HashMap<String, Object>();
			trainingCount.put("count1", count1);
			trainingCount.put("count2", count2);
			trainingCount.put("count3", count3);
			tsrs.setTrainingCount(trainingCount);
		}
		
		if(!"".equals(startyearre)){
			//????????????
			years.put("startyear", startyearre);
			years.put("endyear", endyearre);
			tsrs.setYears(years);
		}
		
		if(!"".equals(starthoursre)){
			Map<String, Object> hours = new HashMap<String, Object>();
			hours.put("starthours", starthoursre);
			hours.put("endhours", endhoursre);
			tsrs.setHours(hours);
		}
		
		if(!"".equals(projectlevelre)){
			//??????????????????
			if("all".equals(projectlevelre)){
				List<String> levels = new ArrayList<String>();
				levels.add("all");
				tsrs.setProjectLevel(levels);
			}else{
				String[] level = projectlevelre.split(",");
				if(level.length > 0){
					List<String> levels = new ArrayList<String>();
					for(String str : level){
						levels.add(str);
					}
					tsrs.setProjectLevel(levels);
				}
			}
		}
		
		if(!"".equals(projecttypere)){
			//??????????????????
			if("all".equals(projecttypere)){
				List<String> types = new ArrayList<String>();
				types.add("all");
				tsrs.setProjectType(types);
			}else{
				String[] type = projecttypere.split(",");
				if(type.length > 0){
					List<String> types = new ArrayList<String>();
					for(String str : type){
						types.add(str);
					}
					tsrs.setProjectType(types);
				}
			}
		}
		
		if(!"".equals(projectre)){
			//????????????
			if("all".equals(projectre)){
				List<String> levels = new ArrayList<String>();
				levels.add("all");
				tsrs.setProject(levels);
			}else{
				String[] level = projectre.split(",");
				if(level.length > 0){
					List<String> levels = new ArrayList<String>();
					for(String str : level){
						levels.add(str);
					}
					tsrs.setProject(levels);
				}
			}
		}
		
		if(!"".equals(trainingsubjectre)){
			//????????????
			if("all".equals(trainingsubjectre)){
				List<String> levels = new ArrayList<String>();
				levels.add("all");
				tsrs.setSubject(levels);
			}else{
				String[] level = trainingsubjectre.split(",");
				if(level.length > 0){
					List<String> levels = new ArrayList<String>();
					for(String str : level){
						levels.add(str);
					}
					tsrs.setSubject(levels);
				}
			}
		}
			
		
		/*
		 * ????????????????????????
		 */
		TeacherSignupCondition tsc = new TeacherSignupCondition();
		
		if(!"".equals(teachingGrade)){
			String[] teaching = teachingGrade.split(",");
			if(teaching.length > 0){
				List<String> teachings = new ArrayList<String>();
				for(String str : teaching){
					teachings.add(str);
				}
				tsc.setTeachingGrade(teachings);
			}
		}
		
		if(!"".equals(teachingSubject)){
			String[] teaching = teachingSubject.split(",");
			if(teaching.length > 0){
				List<String> teachings = new ArrayList<String>();
				for(String str : teaching){
					teachings.add(str);
				}
				tsc.setTeachingSubject(teachings);
			}
		}
		
		if(!"".equals(teachingLanguage)){
			String[] teaching = teachingLanguage.split(",");
			if(teaching.length > 0){
				List<String> teachings = new ArrayList<String>();
				for(String str : teaching){
					teachings.add(str);
				}
				tsc.setTeachingLanguage(teachings);;
			}
		}
		
		if(!"".equals(multiLanguage)){
			tsc.setMultiLanguage(multiLanguage);
		} 
		
		if(!"".equals(jobTitle)){
			String[] teaching = jobTitle.split(",");
			if(teaching.length > 0){
				List<String> teachings = new ArrayList<String>();
				for(String str : teaching){
					teachings.add(str);
				}
				tsc.setJobTitle(teachings);
			}
		}
		
		if(!"".equals(ethnic)){
			String[] teaching = ethnic.split(",");
			if(teaching.length > 0){
				List<String> teachings = new ArrayList<String>();
				for(String str : teaching){
					teachings.add(str);
				}
				tsc.setEthnic(teachings);
			}
		}
		
		if(!"".equals(startTeachingAge)){
			Map<String, Object> teachingAge = new HashMap<String, Object>();
			teachingAge.put("startTimeAge", startTeachingAge);
			teachingAge.put("endTimeAge", endTeachingAge);
			tsc.setTeachingAge(teachingAge);
		}
		
		if(!"".equals(startTeacherAge)){
			Map<String, Object> teacherAge = new HashMap<String, Object>();
			teacherAge.put("startAge", startTeacherAge);
			teacherAge.put("endAge", endTeacherAge);
			tsc.setTeacherAge(teacherAge);;
		}
		
		if(!"".equals(police)){
			String[] teaching = police.split(",");
			if(teaching.length > 0){
				List<String> teachings = new ArrayList<String>();
				for(String str : teaching){
					teachings.add(str);
				}
				tsc.setPolice(teachings);
			}
		}
		
		/*
		 * ??????JSON?????????
		 */
		String actionScope = "";
		String condition = "";
		String reactionScope = "";
		
		JSONObject jsonObject = JSONObject.fromObject(tsas);
		actionScope = jsonObject.toString();
		
		jsonObject = JSONObject.fromObject(tsrs);
		reactionScope = jsonObject.toString();
		
		jsonObject = JSONObject.fromObject(tsc);
		condition = jsonObject.toString();
		
		/*
		 * ????????????????????????????????????"???
		 */
//		actionScope = actionScope.replace("\"", "\\\"");
//		condition = condition.replace("\"", "\\\"");
//		reactionScope = reactionScope.replace("\"", "\\\"");
//		System.out.println(actionScope.indexOf("\""));
//		System.out.println("actionScope:"+actionScope+"\n");
//		System.out.println("condition:"+condition+"\n");
//		System.out.println("reactionScope:"+reactionScope+"\n");
		
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		if(!"".equals(id)){//??????
			TeacherSignupSizer tss = this.teacherSignupSizerService.get(Integer.parseInt(id));
			if(tss == null){
				sendResponse("ERROR", "?????????????????????");
				return;
			}
			tss.setName(name);
			tss.setWeight(Integer.parseInt(weight));
			tss.setStatus(Short.parseShort(status));
			tss.setCreatetime(new Timestamp(System.currentTimeMillis()));
			tss.setActionScope(actionScope);
			tss.setReactionScope(reactionScope);
			tss.setCondition(condition);
			this.teacherSignupSizerService.update(tss);
			sendResponse("OK", "????????????");
		}else{
			TeacherSignupSizer tss = new TeacherSignupSizer();
			tss.setName(name);
			tss.setWeight(Integer.parseInt(weight));
			tss.setStatus(Short.parseShort(status));
			tss.setCreatetime(new Timestamp(System.currentTimeMillis()));
			tss.setActionScope(actionScope);
			tss.setReactionScope(reactionScope);
			tss.setCondition(condition);
			this.teacherSignupSizerService.add(tss);
			sendResponse("OK", "????????????");
		}
		
	}
	
	
	public String initLoad(){
		initServlert();
		if (yearArray == null) {
			int year = 2015;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}
		
		return "initLoad";
	}
	
	/**
	 * ??????????????????
	 */
	@SuppressWarnings("deprecation")
	public void getYearList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		String startyear = request.getParameter("startyear") == null ? "":request.getParameter("startyear");
		if("".equals(startyear)){
			if (yearArray == null) {
				int year = new Date().getYear() + 1900;
				yearArray = new String[50];
				yearArray[0] = (year - 1) + "";
				yearArray[1] = year + "";
				for (int i = 2; i < yearArray.length; i++) {
					yearArray[i] = (year + i - 1) + "";
				}
			}
		}else{
			int year = Integer.parseInt(startyear);
			yearArray = new String[50];
//			yearArray[0] = year + "";
//			yearArray[1] = (year + 1) + "";
			for (int i = 0; i < yearArray.length; i++) {
				yearArray[i] = (year + i + 1) + "";
			}
		}
		
		sb.append("{");
		sb.append("\"Result\":\"OK\",");
		sb.append("\"Options\":");
		String ret = "[";
		String st = "";
		st += "{\"Value\":0,\"DisplayText\":\"?????????\"},";
		for(String str:yearArray){
			st += "{\"Value\":"+str+",\"DisplayText\":\""+str+"\"},";
		}
		
		if (st.length() > 0) {
			st = st.substring(0, st.length() - 1);
		}
//				+ "{\"Value\":1,\"DisplayText\":\"??????\"},"
//				+ "{\"Value\":2,\"DisplayText\":\"??????\"},"
//				+ "{\"Value\":3,\"DisplayText\":\"??????\"},"
//				+ "{\"Value\":4,\"DisplayText\":\"???\"},"
//				+ "{\"Value\":5,\"DisplayText\":\"???\"},"
//				+ "{\"Value\":6,\"DisplayText\":\"?????????\"}";
//		for (Grade g :lstGrades){
//			st += "{\"Value\":" + g.getId() + ",\"DisplayText\":\"" + g.getName() + "\"},";
//		}
//		if (st.length() > 0) {
//			st = st.substring(0, st.length() - 1);
//		}
		ret += st + "]";

		sb.append(ret);
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	/**
	 * ???????????????????????????
	 * ??????
	 */
	public void load(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Message\":\"??????\"");
		sb.append(",");
		sb.append("\"Records\":{");
		
		String id = request.getParameter("id") == null ? "" :  request.getParameter("id");
		
		if(!"".equals(id)){
			TeacherSignupSizer tss = this.teacherSignupSizerService.get(Integer.parseInt(id));
			if(tss == null){
				sendResponse("ERROR", "??????????????????");
				return;
			}
			sb.append("\"teachersignupsizer\":{");
			sb.append("\"id\":"+tss.getId());
			sb.append(",");
			sb.append("\"name\":\""+tss.getName()+"\"");
			sb.append(",");
			sb.append("\"status\":"+tss.getStatus());
			sb.append(",");
			sb.append("\"weight\":"+tss.getWeight());
			sb.append(",");
			String actionScope = tss.getActionScope();
			String condition = tss.getReactionScope();
			String reactionScope = tss.getCondition();
			sb.append("\"actionScope\":"+actionScope);
			sb.append(",");
			sb.append("\"reactionScope\":"+condition);
			sb.append(",");
			sb.append("\"condition\":"+reactionScope);
			sb.append("},");
		}
		
		//????????????
		List<ProjectLevel> li = this.projectLevelService.getList();
		sb.append("\"projectlevel\":[");
		sb.append("{");
		sb.append("\"id\":\"all\"");
		sb.append(",");
		sb.append("\"name\":\"??????\"");
		sb.append("},");
		if(li != null && li.size() > 0){
			
			for(ProjectLevel pl : li){
				sb.append("{");
				sb.append("\"id\":\""+pl.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+pl.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		//????????????
		Map<String, Object> paramss = new HashMap<>();
		paramss.put("pid", "0");
		List<ProjectType> searchProjectType = this.projectTypeService.getProjectTypeList(null,paramss);
		sb.append("\"projecttype\":[");
		sb.append("{");
		sb.append("\"id\":\"all\"");
		sb.append(",");
		sb.append("\"name\":\"??????\"");
		sb.append("},");
		if(searchProjectType != null && searchProjectType.size() > 0){
			
			for(ProjectType pt : searchProjectType){
				sb.append(getTreeList(pt));
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//??????
		List<ProjectType> lityps = new ArrayList<ProjectType>();
		List<Project> projectlist = this.iProjectService.getProjectByStatus((short)2, lityps);
		sb.append("\"project\":[");
		sb.append("{");
		sb.append("\"id\":\"all\"");
		sb.append(",");
		sb.append("\"name\":\"??????\"");
		sb.append("},");
		if(projectlist != null && projectlist.size() > 0){
			
			for(Project p : projectlist){
				sb.append("{");
				sb.append("\"id\":\""+p.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+p.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//????????????
		List<TrainingSubject> subjectlist = this.iTrainingSubjectService.findAll();
		sb.append("\"trainingsubject\":[");
		sb.append("{");
		sb.append("\"id\":\"all\"");
		sb.append(",");
		sb.append("\"name\":\"??????\"");
		sb.append("},");
		if(subjectlist != null && subjectlist.size() > 0){
			
			for(TrainingSubject ts : subjectlist){
				sb.append("{");
				sb.append("\"id\":\""+ts.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+ts.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//????????????
		List<Language> lstLanguages = this.iLanguageService.findAll();// ??????
		sb.append("\"teachinglanguage\":[");
		if(lstLanguages != null && lstLanguages.size() > 0){
			
			for(Language l : lstLanguages){
				sb.append("{");
				sb.append("\"id\":\""+l.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+l.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//????????????
		List<Grade> lstGrades = new ArrayList<Grade>();
		String getStuGradesHQL = " from Grade g where g.isSchool=0 ";
		lstGrades = iGradeService.getListByHSQL(getStuGradesHQL);
		sb.append("\"teachinggrade\":[");
		if(lstGrades != null && lstGrades.size() > 0){
			
			for(Grade g : lstGrades){
				sb.append("{");
				sb.append("\"id\":\""+g.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+g.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//????????????
		List<Subject> lstSubjects = this.iSubjectService.findAll();// ??????
		sb.append("\"teachingsubject\":[");
		if(lstSubjects != null && lstSubjects.size() > 0){
			
			for(Subject s : lstSubjects){
				sb.append("{");
				sb.append("\"id\":\""+s.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+s.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//??????
		List<JobTitle> lstJobtitles = this.iJobTitleService.findAll();// ??????
		sb.append("\"jobtitle\":[");
		if(lstSubjects != null && lstJobtitles.size() > 0){
			
			for(JobTitle jt : lstJobtitles){
				sb.append("{");
				sb.append("\"id\":\""+jt.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+jt.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//??????
		List<Ethnic> lstEthics = this.iEthnicService.findAll();// ??????
		sb.append("\"ethnic\":[");
		if(lstEthics != null && lstEthics.size() > 0){
			
			for(Ethnic e : lstEthics){
				sb.append("{");
				sb.append("\"id\":\""+e.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+e.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("],");
		
		//????????????
		List<Politics> lstPoliticses = this.iPoliticsService.findAll();// ??????
		sb.append("\"politics\":[");
		if(lstPoliticses != null && lstPoliticses.size() > 0){
			
			for(Politics p : lstPoliticses){
				sb.append("{");
				sb.append("\"id\":\""+p.getId()+"\"");
				sb.append(",");
				sb.append("\"name\":\""+p.getName()+"\"");
				sb.append("},");
			}
			
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]");
		
		
		sb.append("}");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String getTreeList(ProjectType pt){
//		for(ProjectType pt : ptlist){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":"+pt.getId());
		sb.append(",");
		sb.append("\"name\":\""+pt.getName()+"\"");
		
		Set<ProjectType> ptset = pt.getProjectTypes();
		if(ptset != null && ptset.size() > 0){
			sb.append(",");
			sb.append("\"records\":[");
			for(ProjectType pp : ptset){
				sb.append(getTreeList(pp));
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("]");
		}
		sb.append("},");
		return sb.toString();
//		}
	}
	
	/**
	 * ???????????????
	 */
	public void changeStatus(){
		initServlert();
		String id = request.getParameter("id") == null ? "" :  request.getParameter("id");
		if("".equals(id)){
			sendResponse("ERROR", "????????????");
			return;
		}
		
		TeacherSignupSizer tss = this.teacherSignupSizerService.get(Integer.parseInt(id));
		if(tss == null){
			sendResponse("ERROR", "??????????????????");
			return;
		}
		
		if(tss.getStatus() == 1){
			tss.setStatus((short)0);
			this.teacherSignupSizerService.update(tss);
			sendResponse("OK", "????????????");
		}else{
			tss.setStatus((short)1);
			this.teacherSignupSizerService.update(tss);
			sendResponse("OK", "????????????");
		}
	}
	
	/**
	 * ??????
	 */
	public void delete(){
		initServlert();
		String id = request.getParameter("id") == null ? "" :  request.getParameter("id");
		if("".equals(id)){
			sendResponse("ERROR", "????????????");
			return;
		}
		
		TeacherSignupSizer tss = this.teacherSignupSizerService.get(Integer.parseInt(id));
		if(tss == null){
			sendResponse("ERROR", "??????????????????");
			return;
		}
		
		tss.setStatus((short)-1);
		this.teacherSignupSizerService.update(tss);
		sendResponse("OK", "????????????");
	}

	/**
	 * ???????????????
	 */
	@SuppressWarnings("unchecked")
	public void signupCheckerSizer(){
		initServlert();
		Project p = null;
		TrainingSubject ts = null;
		Teacher teacher = null;
		Integer hours = 0;
		Map<String, String[]> requestMap = request.getParameterMap();
		if(requestMap.containsKey("isAdvance")){
			UserSession us = (UserSession) session.getAttribute("teachersession");
			Integer teacherId = 0;
			if (us != null) {
				teacherId = us.getId();
			} else {
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"FAIL\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"?????????????????????????????????????????????\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json",
						response);
				return;
			}
			teacher = this.iTeacherService.get(teacherId);
			Integer projectApplyId = 0;
			if (request.getParameter("id") != null
					&& !"".equals(request.getParameter("id"))) {
				projectApplyId = Integer.parseInt(request.getParameter("id"));
			}

			String isAdvance = request.getParameter("isAdvance") == null ? "0"
					: request.getParameter("isAdvance");
			if ("".equals(isAdvance)) {
				isAdvance = "0";
			}

			if ("0".equals(isAdvance)) {
				ProjectApply pa = this.iProjectApplyService.get(projectApplyId);
				p = pa.getProject();
				ts = pa.getTrainingSubject();
				List<Map> studyhourList = JSONUtils.json2list(pa.getStudyhour(), Map.class);
				if(studyhourList != null) {
					for(Map map: studyhourList){
						hours = hours + Integer.valueOf(map.get("value").toString());
					}
				}
			}else{
				ProjectSubjectRange psr = this.iProjectSubjectRangeService.get(Integer.valueOf(projectApplyId));
				p = psr.getProject();
				ts = psr.getTrainingSubject();
			}
		}else{
			String teacherId = request.getParameter("teacherId") == null ? "" : request.getParameter("teacherId");
			if("".equals(teacherId)){
				teacherId = "0";
			}
			String projectId = request.getParameter("projectId") == null ? "" : request.getParameter("projectId");
			String subjectId = request.getParameter("subjectId") == null ? "" : request.getParameter("subjectId");
			String tcid = request.getParameter("collegeId");
			
			//??????????????????????????????
			Map<String,Object> searchMap = new HashMap<String,Object>();
			searchMap.put("project", projectId);
			searchMap.put("trainingSubject", subjectId);
			searchMap.put("trainingCollege", tcid);
			ProjectApply pa = null;
			try {
				List<ProjectApply> lstpa = this.iProjectApplyService.getProjectApplyByParams(searchMap, null, null);
				if(lstpa!= null && lstpa.size() > 0){
					pa = lstpa.get(0);
				}
				if(pa == null){
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"FAIL\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\"????????????????????????\"");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringBuilder checkSB = new StringBuilder();
				checkSB.append("{");
				checkSB.append("\"Result\":\"FAIL\"");
				checkSB.append(",");
				checkSB.append("\"Message\":\"????????????????????????\"");
				checkSB.append("}");
				Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
				return;
			}
			
			teacher = this.iTeacherService.get(Integer.parseInt(teacherId));
			//????????????
			p = pa.getProject();
			ts = pa.getTrainingSubject();
			List<Map> studyhourList = JSONUtils.json2list(pa.getStudyhour(), Map.class);
			if(studyhourList != null) {
				for(Map map: studyhourList){
					hours = hours + Integer.valueOf(map.get("value").toString());
				}
			}
		}
		if(teacher == null){
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAIL\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"????????????????????????\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		//20171130????????????????????????
		//????????????????????????????????????????????????????????????
		if(teacher.getStatus() == -1){
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAIL\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"????????????????????????????????????????????????\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		if(teacher.getStatus() == 0){
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"FAIL\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"?????????????????????????????????????????????\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		ProjectType pt = p.getProjectType();
		ProjectLevel pl = pt.getProjectLevel();
		

		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 1);
		List<TeacherSignupSizer> tssList = this.teacherSignupSizerService.getListByParams(params, -1, -1, "");
		if(tssList != null){
			params.remove("status");
			params.put("teacherSignupSizer", tssList);
			params.put("projectYear", p.getYear());
			params.put("projectLevel", pl.getId());
			params.put("projectType", pt.getId());
			params.put("project", p.getId());
			params.put("subject", ts.getId());
			params.put("projectHours", hours.toString());
			
			boolean flag = TeacherSignupUtlity.checkSizer(params);
			if(flag){
				String isNext = request.getParameter("isNext") == null ? "" : request.getParameter("isNext");
				params.put("teacher", teacher);
				if(!"1".equals(isNext)){
					Map<String, String> records = TeacherSignupUtlity.checkCondition(params);
					if(!"ok".equals(records.get("status"))){//????????????????????????????????????????????????????????????????????????
						List<TeacherSignupCondition> tscList = (List<TeacherSignupCondition>) params.get("teacherSignupCondition");
//						List<String> teachingGrade = new ArrayList<String>();
//						List<String> teachingSubject = new ArrayList<String>();
//						List<String> teachingLanguage = new ArrayList<String>();
//						List<String> jobTitle = new ArrayList<String>();
//						List<String> ethnic = new ArrayList<String>();
//						List<String> politic = new ArrayList<String>();
//						List<String> multi = new ArrayList<String>();
						Map<String, Object> teachingGrade = new HashMap<String, Object>();
						Map<String, Object> teachingSubject = new HashMap<String, Object>();
						Map<String, Object> teachingLanguage = new HashMap<String, Object>();
						Map<String, Object> jobTitle = new HashMap<String, Object>();
						Map<String, Object> ethnic = new HashMap<String, Object>();
						Map<String, Object> politic = new HashMap<String, Object>();
						Map<String, Object> multi = new HashMap<String, Object>();
						
						List<String> teachingAge = new ArrayList<String>();
						List<String> teacherAge = new ArrayList<String>();
						for(TeacherSignupCondition tsc:tscList){
							if(tsc.getTeachingGrade() != null && tsc.getTeachingGrade().size() > 0){
//								teachingGrade.addAll(tsc.getTeachingGrade());
								for(String srt : tsc.getTeachingGrade()){
									teachingGrade.put(srt, srt);
								}
							}
							
							//????????????
							if(tsc.getTeachingSubject() != null && tsc.getTeachingSubject().size() > 0){
//								teachingSubject.addAll(tsc.getTeachingSubject());
								for(String srt : tsc.getTeachingSubject()){
									teachingSubject.put(srt, srt);
								}
							}
							
							//????????????
							if(tsc.getTeachingLanguage() != null && tsc.getTeachingLanguage().size() > 0){
//								teachingLanguage.addAll(tsc.getTeachingLanguage());
								for(String srt : tsc.getTeachingLanguage()){
									teachingLanguage.put(srt, srt);
								}
							}
							
							//????????????
							if(tsc.getMultiLanguage() != null && !"".equals(tsc.getMultiLanguage())){
//								multi.add(tsc.getMultiLanguage()+"");
								multi.put(tsc.getMultiLanguage()+"", tsc.getMultiLanguage());
							}
							
							//????????????
							if(tsc.getJobTitle() != null && tsc.getJobTitle().size() > 0){
//								jobTitle.addAll(tsc.getJobTitle());
								for(String srt : tsc.getJobTitle()){
									jobTitle.put(srt, srt);
								}
							}
							
							//????????????
							if(tsc.getEthnic() != null && tsc.getEthnic().size() > 0){
//								ethnic.addAll(tsc.getEthnic());
								for(String srt : tsc.getEthnic()){
									ethnic.put(srt, srt);
								}
							}
							
							//????????????
							if(tsc.getTeachingAge()!= null && tsc.getTeachingAge().size() > 0){
								Map<String, Object> ages = tsc.getTeachingAge();
								String startage = ages.get("startTimeAge").toString();
								String endage = ages.get("endTimeAge").toString();
								teachingAge.add(startage+"-"+endage+"???");
							}
							
							//????????????
							if(tsc.getTeacherAge()!= null && tsc.getTeacherAge().size() > 0){
								Map<String, Object> ages = tsc.getTeacherAge();
								String startage = ages.get("startAge").toString();
								String endage = ages.get("endAge").toString();
								teacherAge.add(startage+"-"+endage+"???");
							}
							
							//??????????????????
							if(tsc.getPolice()!= null && tsc.getPolice().size() > 0){
//								politic.addAll(tsc.getPolice());
								for(String srt : tsc.getPolice()){
									politic.put(srt, srt);
								}
							}
						}
						/*
						 * 					List<String> teachingGrade = new ArrayList<String>();
						List<String> teachingSubject = new ArrayList<String>();
						List<String> teachingLanguage = new ArrayList<String>();
						List<String> jobTitle = new ArrayList<String>();
						List<String> ethnic = new ArrayList<String>();
						List<String> politic = new ArrayList<String>();
						List<String> multi = new ArrayList<String>();
						List<String> teachingAge = new ArrayList<String>();
						List<String> teacherAge = new ArrayList<String>();
						 */
						StringBuilder checkSB = new StringBuilder();
						checkSB.append("{");
						checkSB.append("\"Result\":\"WARNING\"");
						checkSB.append(",");
						checkSB.append("\"Message\":\""+records.get("message")+"\"");
						checkSB.append(",");
						checkSB.append("\"Records\":{");
						checkSB.append("\"sizer\":{");
						checkSB.append("\"teachingGrade\":[");
						if(teachingGrade.size() > 0){
							for(String id : teachingGrade.keySet()){
								Grade g = this.iGradeService.get(Short.parseShort(id));
								checkSB.append("{");
								checkSB.append("\"id\":"+id);
								checkSB.append(",");
								checkSB.append("\"name\":\""+g.getName()+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"teachingSubject\":[");
						if(teachingSubject.size() > 0){
							for(String id : teachingSubject.keySet()){
								Subject g = this.iSubjectService.get(Short.parseShort(id));
								checkSB.append("{");
								checkSB.append("\"id\":"+id);
								checkSB.append(",");
								checkSB.append("\"name\":\""+g.getName()+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"teachingLanguage\":[");
						if(teachingLanguage.size() > 0){
							for(String id : teachingLanguage.keySet()){
								Language g = this.iLanguageService.get(Short.parseShort(id));
								checkSB.append("{");
								checkSB.append("\"id\":"+id);
								checkSB.append(",");
								checkSB.append("\"name\":\""+g.getName()+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"jobTitle\":[");
						if(jobTitle.size() > 0){
							for(String id : jobTitle.keySet()){
								JobTitle g = this.iJobTitleService.getJobTitleById(id);
								checkSB.append("{");
								checkSB.append("\"id\":"+id);
								checkSB.append(",");
								checkSB.append("\"name\":\""+g.getName()+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"ethnic\":[");
						if(ethnic.size() > 0){
							for(String id : ethnic.keySet()){
								Ethnic g = this.iEthnicService.get(Short.parseShort(id));
								checkSB.append("{");
								checkSB.append("\"id\":"+id);
								checkSB.append(",");
								checkSB.append("\"name\":\""+g.getName()+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"politic\":[");
						if(politic.size() > 0){
							for(String id : politic.keySet()){
								Politics g = this.iPoliticsService.getPoliticsById(id);
								checkSB.append("{");
								checkSB.append("\"id\":"+id);
								checkSB.append(",");
								checkSB.append("\"name\":\""+g.getName()+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"multi\":[");
						if(multi.size() > 0){
							for(String id : multi.keySet()){
								checkSB.append("{");
								checkSB.append("\"id\":"+id);
								checkSB.append(",");
								checkSB.append("\"name\":\""+id+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"teachingAge\":[");
						if(teachingAge.size() > 0){
							for(String id : teachingAge){
								checkSB.append("{");
								checkSB.append("\"id\":\""+id+"\"");
								checkSB.append(",");
								checkSB.append("\"name\":\""+id+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("],");
						
						checkSB.append("\"teacherAge\":[");
						if(teacherAge.size() > 0){
							for(String id : teacherAge){
								checkSB.append("{");
								checkSB.append("\"id\":\""+id+"\"");
								checkSB.append(",");
								checkSB.append("\"name\":\""+id+"\"");
								checkSB.append("},");
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
						checkSB.append("]");
						checkSB.append("},");
						checkSB.append("\"teacher\":{");
						checkSB.append("\"name\":\""+teacher.getName()+"\"");
						checkSB.append(",");
						checkSB.append("\"idCard\":\""+teacher.getIdcard()+"\"");
						checkSB.append(",");
						checkSB.append("\"jobTitle\":\""+teacher.getJobTitle().getName()+"\"");
						checkSB.append(",");
						checkSB.append("\"ethnic\":\""+teacher.getEthnic().getName()+"\"");
						checkSB.append(",");
						checkSB.append("\"politics\":\""+teacher.getPolitics().getName()+"\"");
						checkSB.append(",");
						checkSB.append("\"teachingAge\":\""+Utlity.getAge(teacher.getTeachingAge())+"\"");
						int teacherAge2 = 0;
						if(teacher.getBirthday() != null){
							teacherAge2 = Utlity.getAge(teacher.getBirthday());
						}
						checkSB.append(",");
						checkSB.append("\"teacherAge\":\""+teacherAge2+"\"");
						checkSB.append(",");
						//??????????????????
						String multiLanguage = teacher.getMultiLanguage() == true?"???":"???";
						checkSB.append("\"multiLanguage\":\""+multiLanguage+"\"");
						checkSB.append(",");
						
						// ????????????????????????
						Set<TeachingLanguage> lsTeachingLanguages = teacher.getTeachingLanguages();
											   
						if (lsTeachingLanguages != null && lsTeachingLanguages.size() > 0) {
							TeachingLanguage language = null;
							for(TeachingLanguage tl : lsTeachingLanguages){
								if(tl.getIsprime()){
									language = tl;
								}
							}
							if(language != null){
								checkSB.append("\"mainTeachingLanguage\":\""+language.getLanguage().getName()+"\"");
								checkSB.append(",");
							}else{
								checkSB.append("\"mainTeachingLanguage\":\"???\"");
								checkSB.append(",");
							}
							
						}else{
							checkSB.append("\"mainTeachingLanguage\":\"???\"");
							checkSB.append(",");
						}
			
						// ????????????????????????
						Set<TeachingGrade> lstTeachingGrades = teacher.getTeachingGrades();
						if (lstTeachingGrades != null && lstTeachingGrades.size() > 0) {
							TeachingGrade grade = null;
							for(TeachingGrade tg : lstTeachingGrades){
								if(tg.getIsprime()){
									grade = tg;
								}
							}
							if(grade != null){
								checkSB.append("\"mainTeachingGrades\":\""+grade.getGrade().getName()+"\"");
								checkSB.append(",");
							}else{
								checkSB.append("\"mainTeachingGrades\":\"???\"");
								checkSB.append(",");
							}
							
						}else{
							checkSB.append("\"mainTeachingGrades\":\"???\"");
							checkSB.append(",");
						}
			
						// ????????????????????????
						Set<TeachingSubject> lsttTeachingSubjects = teacher.getTeachingSubjects();
						if (lsttTeachingSubjects != null && lsttTeachingSubjects.size() > 0) {
							TeachingSubject subject = null;
							for(TeachingSubject ts1 : lsttTeachingSubjects){
								if(ts1.getIsprime()){
									subject = ts1;
								}
							}
							if(subject != null){
								checkSB.append("\"mainTeachingSubject\":\""+subject.getSubject().getName()+"\"");
							}else{
								checkSB.append("\"mainTeachingSubject\":\"???\"");
							}
							
						}else{
							checkSB.append("\"mainTeachingSubject\":\"???\"");
						}
						checkSB.append("}");
						checkSB.append("}");
						checkSB.append("}");
						Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
						return;
					}
				}
				
				
				
				//??????????????????
				params.put("project", p);
				params.put("trainingSubject", ts);
				Map<String, String> records2 = checkReSignupSizer(params);
				if(!"ok".equals(records2.get("status"))){//??????????????????
					List<TeacherSignupReActionScope> tsrsList = (List<TeacherSignupReActionScope>) params.get("teacherSignupReActionScope");
					Map<String, Object> projectLevel = new HashMap<String, Object>();
					Map<String, Object> projectType = new HashMap<String, Object>();
					Map<String, Object> project = new HashMap<String, Object>();
					Map<String, Object> subject = new HashMap<String, Object>();
					
					List<String> trainingCount = new ArrayList<String>();
					List<String> years = new ArrayList<String>();
					List<String> hourss = new ArrayList<String>();
					for(TeacherSignupReActionScope tsrs : tsrsList){
						if(tsrs.getTrainingCount()!= null && tsrs.getTrainingCount().size() > 0){
							Map<String, Object> counts = tsrs.getTrainingCount();
							String count1 = counts.get("count1").toString();
							String count2 = counts.get("count2").toString();
							String count3 = counts.get("count3").toString();
//							teacherAge.add(startage+"-"+endage+"???");
							String count = "";
							switch (count1) {
							case "1":
								count += "??????????????????";
								break;
							case "2":
								count += "??????????????????";
								break;
							case "3":
								count += "???????????????????????????????????????";
								break;
							case "4":
								count += "??????????????????????????????";
								break;
							case "5":
								count += "???????????????????????????????????????????????????";
								break;
							case "6":
								count += "??????????????????";
								break;
							default:
								break;
							}
							
							String count22 = "";
							if("1".equals(count2)){
								count22+="??????????????????";
							}
							count += (count22 + count3 + "???");
							trainingCount.add(count);
						}
						
						if(tsrs.getYears() != null && tsrs.getYears().size() > 0){
							Map<String, Object> yearss = tsrs.getYears();
							String startyear = yearss.get("startyear").toString();
							String endyear = yearss.get("endyear").toString();
							years.add(startyear+"-"+endyear+"???");
							
						}
						
						if(tsrs.getProjectLevel() != null && tsrs.getProjectLevel().size() > 0){
//							ethnic.addAll(tsc.getEthnic());
							for(String srt : tsrs.getProjectLevel()){
								projectLevel.put(srt, srt);
							}
						}
						
						if(tsrs.getProjectType() != null && tsrs.getProjectType().size() > 0){
//							ethnic.addAll(tsc.getEthnic());
							for(String srt : tsrs.getProjectType()){
								projectType.put(srt, srt);
							}
						}
						
						if(tsrs.getProject() != null && tsrs.getProject().size() > 0){
//							ethnic.addAll(tsc.getEthnic());
							for(String srt : tsrs.getProject()){
								project.put(srt, srt);
							}
						}
						
						if(tsrs.getSubject() != null && tsrs.getSubject().size() > 0){
//							ethnic.addAll(tsc.getEthnic());
							for(String srt : tsrs.getSubject()){
								subject.put(srt, srt);
							}
						}
						
						if(tsrs.getHours() != null && tsrs.getHours().size() > 0){
							Map<String, Object> hoursss = tsrs.getHours();
							String starthours = hoursss.get("starthours").toString();
							String endhours = hoursss.get("endhours").toString();
							hourss.add(starthours+"-"+endhours+"??????");
						}
						
					}
					
					StringBuilder checkSB = new StringBuilder();
					checkSB.append("{");
					checkSB.append("\"Result\":\"FAIL\"");
					checkSB.append(",");
					checkSB.append("\"Message\":\""+records2.get("message")+"\"");
					checkSB.append(",");
					checkSB.append("\"Records\":{");
					checkSB.append("\"sizer\":{");
					checkSB.append("\"trainingcount\":[");
					if(trainingCount.size() > 0){
//						for(String id : teachingGrade.keySet()){
//							Grade g = this.iGradeService.get(Short.parseShort(id));
//							checkSB.append("{");
//							checkSB.append("\"id\":"+id);
//							checkSB.append(",");
//							checkSB.append("\"name\":\""+g.getName()+"\"");
//							checkSB.append("},");
//						}
						for(String id : trainingCount){
							checkSB.append("{");
							checkSB.append("\"id\":\""+id+"\"");
							checkSB.append(",");
							checkSB.append("\"name\":\""+id+"\"");
							checkSB.append("},");
						}
						checkSB.delete(checkSB.length() - 1, checkSB.length());
					}
					checkSB.append("],");
					
					checkSB.append("\"years\":[");
					if(years.size() > 0){
						for(String id : years){
							checkSB.append("{");
							checkSB.append("\"id\":\""+id+"\"");
							checkSB.append(",");
							checkSB.append("\"name\":\""+id+"\"");
							checkSB.append("},");
						}
						checkSB.delete(checkSB.length() - 1, checkSB.length());
					}
					checkSB.append("],");
					
					checkSB.append("\"projectLevel\":[");
					if(projectLevel.size() > 0){
						for(String id : projectLevel.keySet()){
							if("all".equals(id)){
								checkSB.append("{");
								checkSB.append("\"id\":\"??????\"");
								checkSB.append(",");
								checkSB.append("\"name\":\"??????\"");
								checkSB.append("},");
								continue;
							}
							ProjectLevel g = this.projectLevelService.get(Short.parseShort(id));
							checkSB.append("{");
							checkSB.append("\"id\":"+id);
							checkSB.append(",");
							checkSB.append("\"name\":\""+g.getName()+"\"");
							checkSB.append("},");
						}
						checkSB.delete(checkSB.length() - 1, checkSB.length());
					}
					checkSB.append("],");
					
					checkSB.append("\"projectType\":[");
					if(projectType.size() > 0){
						for(String id : projectType.keySet()){
							if("all".equals(id)){
								checkSB.append("{");
								checkSB.append("\"id\":\"??????\"");
								checkSB.append(",");
								checkSB.append("\"name\":\"??????\"");
								checkSB.append("},");
								continue;
							}
							ProjectType g = this.projectTypeService.get(Integer.parseInt(id));
							checkSB.append("{");
							checkSB.append("\"id\":"+id);
							checkSB.append(",");
							checkSB.append("\"name\":\""+g.getName()+"\"");
							checkSB.append("},");
						}
						checkSB.delete(checkSB.length() - 1, checkSB.length());
					}
					checkSB.append("],");
					
					checkSB.append("\"project\":[");
					if(project.size() > 0){
						for(String id : project.keySet()){
							if("all".equals(id)){
								checkSB.append("{");
								checkSB.append("\"id\":\"??????\"");
								checkSB.append(",");
								checkSB.append("\"name\":\"??????\"");
								checkSB.append("},");
								continue;
							}
							Project g = this.iProjectService.get(Integer.parseInt(id));
							checkSB.append("{");
							checkSB.append("\"id\":"+id);
							checkSB.append(",");
							checkSB.append("\"name\":\""+g.getName()+"\"");
							checkSB.append("},");
						}
						checkSB.delete(checkSB.length() - 1, checkSB.length());
					}
					checkSB.append("],");
					
					checkSB.append("\"subject\":[");
					if(subject.size() > 0){
						for(String id : subject.keySet()){
							if("all".equals(id)){
								checkSB.append("{");
								checkSB.append("\"id\":\"??????\"");
								checkSB.append(",");
								checkSB.append("\"name\":\"??????\"");
								checkSB.append("},");
								continue;
							}
							TrainingSubject g = this.iTrainingSubjectService.get(Short.parseShort(id));
							checkSB.append("{");
							checkSB.append("\"id\":"+id);
							checkSB.append(",");
							checkSB.append("\"name\":\""+g.getName()+"\"");
							checkSB.append("},");
						}
						checkSB.delete(checkSB.length() - 1, checkSB.length());
					}
					checkSB.append("]");
					checkSB.append("},");
					
					//???????????????????????????????????????
					checkSB.append("\"teacher\":{");
					checkSB.append("\"records\":[");
					if(params.containsKey("records")){
						Map<String, Object> record = (Map<String, Object>) params.get("records");
						if(record != null && record.size() > 0){
							for (Object value : record.values()) {  
								TeacherTrainingRecords ttr = (TeacherTrainingRecords) value; 
								Project pp = ttr.getProject();
								ProjectType ptt = pp.getProjectType();
								ProjectLevel pll = ptt.getProjectLevel();
								TrainingSubject tss = ttr.getTrainingSubject();
								checkSB.append("{");
								checkSB.append("\"projectLevel\":\""+pll.getName()+"\"");
								checkSB.append(",");
								checkSB.append("\"projectType\":\""+ptt.getName()+"\"");
								checkSB.append(",");
								checkSB.append("\"project\":\""+pp.getName()+"\"");
								checkSB.append(",");
								checkSB.append("\"trainingSubject\":\""+tss.getName()+"\"");
								checkSB.append(",");
								checkSB.append("\"year\":\""+pp.getYear()+"\"");
								checkSB.append("},");
							  
							}
							checkSB.delete(checkSB.length() - 1, checkSB.length());
						}
					}
					checkSB.append("]");
					checkSB.append("}");
					checkSB.append("}");
					checkSB.append("}");
					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
					return;
					
//					StringBuilder checkSB = new StringBuilder();
//					checkSB.append("{");
//					checkSB.append("\"Result\":\"FAIL\"");
//					checkSB.append(",");
//					checkSB.append("\"Message\":\""+records2.get("message")+"\"");
//					checkSB.append("}");
//					Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
//					return;
				}
			}
			
		}
		
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"OK\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"??????????????????\"");
		checkSB.append("}");
		Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
		return;
		
	}
	
	/**
	 * ??????????????????????????????
	 * ??????????????????
	 * count1=1 --??????????????????
		count1=2  -- ????????????
		count1=3 --??????????????????????????????
		count1=4 --???????????????????????????
		count1=5 --???????????????????????????????????????
		count1=6 --???????????????????????????????????????
	 * @param params
	 * @return
	 */
	public Map<String, String> checkReSignupSizer(Map<String, Object> params){
		Map<String, String> info = new HashMap<String, String>();
		
		if(params != null){
			if(!params.containsKey("teacher")){
				info.put("status", "error");
				info.put("message", "???????????????????????????");
				return info;
			}
			Teacher teacher = (Teacher) params.get("teacher");
			
			if(!params.containsKey("project")){
				info.put("status", "error");
				info.put("message", "?????????????????????????????????");
				return info;
			}
			Project p = (Project) params.get("project");
			
			if(!params.containsKey("trainingSubject")){
				info.put("status", "error");
				info.put("message", "?????????????????????????????????");
				return info;
			}
			TrainingSubject ts = (TrainingSubject) params.get("trainingSubject");
			boolean flag = true;
			StringBuilder message = new StringBuilder();
			if(params.containsKey("teacherSignupReActionScope")){
				/*
				 * ??????????????????????????????????????????????????????
				 * ????????????
				 * ??????????????????????????????????????????---???????????????????????????
				 */
				@SuppressWarnings("unchecked")
				List<TeacherSignupReActionScope> tsrsList = (List<TeacherSignupReActionScope>) params.get("teacherSignupReActionScope");
				if(tsrsList != null && tsrsList.size() > 0){
//					Project p = pa.getProject();
//					TrainingSubject ts = pa.getTrainingSubject();
					ProjectType pt = p.getProjectType();
//					ProjectLevel pl = pt.getProjectLevel();
					Map<String, Object> record = new HashMap<String, Object>();
					for(TeacherSignupReActionScope tsrs : tsrsList){
						if(tsrs.getTrainingCount() != null){
							Map<String, Object> trainingCount = tsrs.getTrainingCount();
							String count1 = trainingCount.get("count1").toString();
							String count2 = trainingCount.get("count2").toString();
							String count3 = trainingCount.get("count3").toString();
							StringBuilder sql = new StringBuilder();
							sql.append("select ttr from TeacherTrainingRecords ttr, Project p, ProjectType pt, ProjectLevel pl where 1=1");
							sql.append(" and ttr.project=p.id and p.projectType=pt.id and pt.projectLevel=pl.id and ttr.finalStatus in(0,1,2)");
							sql.append(" and ttr.teacher="+teacher.getId());
							String errorMessage = "";
							switch (count1) {
							case "1":
								sql.append(" and pt.id="+pt.getId());
								errorMessage = "??????????????????"+pt.getName()+"???????????????????????????????????????????????????????????????????????????????????????";
								break;
							case "2":
								sql.append(" and ttr.trainingSubject="+ts.getId());
								errorMessage = "??????????????????"+ts.getName()+"???????????????????????????????????????????????????????????????????????????????????????";
								break;
							case "3":
								sql.append(" and pt.id="+pt.getId() + " and ttr.trainingSubject="+ts.getId());
								errorMessage = "??????????????????"+pt.getName()+"???????????????"+ts.getName()+"???????????????????????????????????????????????????????????????????????????????????????";
								break;
							case "4":
								sql.append(" and pt.id="+pt.getId() + " and p.year='"+p.getYear()+"'");
								errorMessage = "??????????????????"+p.getYear()+"???"+pt.getName()+"???????????????????????????????????????????????????????????????????????????????????????";
								break;
							case "5":
								sql.append(" and pt.id="+pt.getId() + " and ttr.trainingSubject="+ts.getId() + " and p.year='"+p.getYear()+"'");
								errorMessage = "??????????????????"+p.getYear()+"???"+pt.getName()+"???????????????"+ts.getName()+"???????????????????????????????????????????????????????????????????????????????????????";
								break;
							case "6":
								/*
								 * ??????????????????
								 */
								if(tsrs.getYears() != null && tsrs.getYears().size() > 0){
									List<Integer> years = new ArrayList<Integer>();
									Map<String, Object> yearLine = tsrs.getYears();
									Integer startYear = Integer.parseInt(yearLine.get("startyear").toString());
									Integer endYear = Integer.parseInt(yearLine.get("endyear").toString());
									years.add(startYear);
									while(startYear < endYear){
										startYear++;
										years.add(startYear);
									}
									sql.append(" and p.year in(");
									for(Integer year: years){
										sql.append("'"+year+"',");
									}
									sql.delete(sql.length() -1 , sql.length());
									sql.append(")");
								}
								if(tsrs.getProjectLevel() != null && tsrs.getProjectLevel().size() > 0){
									List<String> levels = tsrs.getProjectLevel();
									if(!"all".equals(levels.get(0))){
										sql.append(" and pl.id in(");
										for(String level: levels){
											sql.append(level+",");
										}
										sql.delete(sql.length() -1 , sql.length());
										sql.append(")");
									}
									
								}
								if(tsrs.getProjectType() != null && tsrs.getProjectType().size() > 0){
									List<String> types = tsrs.getProjectType();
									if(!"all".equals(types.get(0))){
										sql.append(" and pt.id in(");
										for(String type: types){
											sql.append(type+",");
										}
										sql.delete(sql.length() -1 , sql.length());
										sql.append(")");
									}
								}
								if(tsrs.getProject() != null && tsrs.getProject().size() > 0){
									List<String> projects = tsrs.getProject();
									if(!"all".equals(projects.get(0))){
										sql.append(" and p.id in(");
										for(String project: projects){
											sql.append(project+",");
										}
										sql.delete(sql.length() -1 , sql.length());
										sql.append(")");
									}
								}
								if(tsrs.getSubject() != null && tsrs.getSubject().size() > 0){
									List<String> subjects = tsrs.getSubject();
									if(!"all".equals(subjects.get(0))){
										sql.append(" and ttr.trainingSubject in(");
										for(String subject: subjects){
											sql.append(subject+",");
										}
										sql.delete(sql.length() -1 , sql.length());
										sql.append(")");
									}
								}
								break;
							default:
								break;
							}
							
							List<TeacherTrainingRecords> list = this.iTeacherTrainingRecords.getListByHSQL(sql.toString());
							if(list != null && list.size() > 0){
								for(TeacherTrainingRecords ttr : list){
									
									//??????????????????
									if(tsrs.getHours() != null && tsrs.getHours().size() > 0){
										
										Map<String, Object> hoursLine = tsrs.getHours();
										Integer startHours = Integer.parseInt(hoursLine.get("starthours").toString());
										Integer endHours = Integer.parseInt(hoursLine.get("endhours").toString()); 
										Integer full = 0;
										if(ttr.getProjectCycle().getId() == 1) {
											Integer centralize = ttr.getCentralize() == null ? 0 : ttr.getCentralize();
											Integer information = ttr.getInformation() == null ? 0 : ttr.getInformation();
											Integer regional = ttr.getRegional() == null ? 0 : ttr.getRegional();
											Integer school = ttr.getSchool() == null ? 0 : ttr.getSchool();
											Integer totalhours = ttr.getTotalhours() == null ? 0 : ttr.getTotalhours();
											full = (centralize + information + regional + school + totalhours);
										} else {
											List<Map> studyhour = JSONUtils.json2list(ttr.getStudyhour(), Map.class);
											if(studyhour != null) {
												for(Map studyMap : studyhour) {
													Integer value = Integer.valueOf(studyMap.get("value").toString());
													full += value;
												}
											}
										}
										if((full >= startHours) && (full <= endHours)) {
											record.put(ttr.getId()+"", ttr);
										}
									} else {
										record.put(ttr.getId()+"", ttr);
									}
								}
							}
							if("1".equals(count2)){//????????? count2==2
								if(list != null && list.size() >= Integer.parseInt(count3)){
									flag = false;
									if("".equals(errorMessage)){
										errorMessage = "??????????????????"+list.get(0).getProject().getName()+"???????????????????????????????????????????????????????????????????????????";
									}
									message.append(errorMessage);
									message.append(",");
								}
							}
						}
	
					}
					params.put("records", record);
				}
			}
			String status = "ok";
			if(!flag){
				status = "error";
				message.delete(message.length() - 1, message.length());
			}
			info.put("status", status);
			info.put("message", message.toString());
		}else{
			info.put("status", "error");
			info.put("message", "???????????????????????????");
		}
		
		return info;
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
	
	public ITeacherSignupSizerService getTeacherSignupSizerService() {
		return teacherSignupSizerService;
	}

	public void setTeacherSignupSizerService(
			ITeacherSignupSizerService teacherSignupSizerService) {
		this.teacherSignupSizerService = teacherSignupSizerService;
	}

	public ILanguageService getiLanguageService() {
		return iLanguageService;
	}

	public void setiLanguageService(ILanguageService iLanguageService) {
		this.iLanguageService = iLanguageService;
	}

	public ISubjectService getiSubjectService() {
		return iSubjectService;
	}

	public void setiSubjectService(ISubjectService iSubjectService) {
		this.iSubjectService = iSubjectService;
	}

	public IGradeService getiGradeService() {
		return iGradeService;
	}

	public void setiGradeService(IGradeService iGradeService) {
		this.iGradeService = iGradeService;
	}

	public IJobTitleService getiJobTitleService() {
		return iJobTitleService;
	}

	public void setiJobTitleService(IJobTitleService iJobTitleService) {
		this.iJobTitleService = iJobTitleService;
	}

	public IEthnicService getiEthnicService() {
		return iEthnicService;
	}

	public void setiEthnicService(IEthnicService iEthnicService) {
		this.iEthnicService = iEthnicService;
	}

	public IPoliticsService getiPoliticsService() {
		return iPoliticsService;
	}

	public void setiPoliticsService(IPoliticsService iPoliticsService) {
		this.iPoliticsService = iPoliticsService;
	}

	public IProjectLevelService getProjectLevelService() {
		return projectLevelService;
	}

	public void setProjectLevelService(IProjectLevelService projectLevelService) {
		this.projectLevelService = projectLevelService;
	}

	public IProjectTypeService getProjectTypeService() {
		return projectTypeService;
	}

	public void setProjectTypeService(IProjectTypeService projectTypeService) {
		this.projectTypeService = projectTypeService;
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

	public String[] getYearArray() {
		return yearArray;
	}

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
	}

	
	public ITeacherTrainingRecordsService getiTeacherTrainingRecords() {
		return iTeacherTrainingRecords;
	}
	

	public void setiTeacherTrainingRecords(
			ITeacherTrainingRecordsService iTeacherTrainingRecords) {
		this.iTeacherTrainingRecords = iTeacherTrainingRecords;
	}

	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public ITeacherTrainingAssginedService getTeacherTrainingAssignedService() {
		return teacherTrainingAssignedService;
	}

	public void setTeacherTrainingAssignedService(
			ITeacherTrainingAssginedService teacherTrainingAssignedService) {
		this.teacherTrainingAssignedService = teacherTrainingAssignedService;
	}

	public IProjectSubjectRangeService getiProjectSubjectRangeService() {
		return iProjectSubjectRangeService;
	}

	public void setiProjectSubjectRangeService(
			IProjectSubjectRangeService iProjectSubjectRangeService) {
		this.iProjectSubjectRangeService = iProjectSubjectRangeService;
	}
	
	


}
