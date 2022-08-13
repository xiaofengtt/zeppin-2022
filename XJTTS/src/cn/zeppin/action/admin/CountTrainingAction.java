package cn.zeppin.action.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.ICountTeacherYearService;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.utility.Utlity;

public class CountTrainingAction extends baseAction{
	
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(CountTrainingAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private IProjectService iProjectService;
	private ICountTeacherYearService iCountTeacherYearService;
	private IOrganizationService iOrganization; // organization
	private IProjectTypeService projectTypeService;
	
	private String selectYear;
	private List<String> searchYear;
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	@SuppressWarnings("unused")
	private String getProjectLevelCN(Integer level) {
		switch (level) {

		case 1:
			return "国家级培训";
		case 2:
			return "自治区级培训";
		case 3:
			return "地市州级培训";
		case 4:
			return "县区市级培训";
		case 5:
			return "校本级培训";
		default:
			return "其他";

		}
	}
	
	private String getProjectTypeCN(Integer typeId,List<ProjectType> list){
		String type = "无";
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		for(ProjectType pt:list){
			typeMap.put(pt.getId(), pt.getName());
		}
		type = typeMap.get(typeId);
		return type;
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
	
	public String initPoor(){
		initServlert();
		List<String> projectYears = this.iProjectService.getProjectYearList();
		Calendar calendar = Calendar.getInstance();
		Integer thisYear = calendar.get(Calendar.YEAR);
		List<String> years = new ArrayList<String>();
		for (String year:projectYears){
			if(thisYear >= Integer.valueOf(year)){
				years.add(year);
			}
		}
		this.searchYear = years;
		this.selectYear = this.searchYear.get(0);
		return "initPoor";
	}
	
	public String initVillage(){
		initServlert();
		List<String> projectYears = this.iProjectService.getProjectYearList();
		Calendar calendar = Calendar.getInstance();
		Integer thisYear = calendar.get(Calendar.YEAR);
		List<String> years = new ArrayList<String>();
		for (String year:projectYears){
			if(thisYear >= Integer.valueOf(year)){
				years.add(year);
			}
		}
		this.searchYear = years;
		this.selectYear = this.searchYear.get(0);
		return "initVillage";
	}
	
	public String initCategory(){
		initServlert();
		List<String> projectYears = this.iProjectService.getProjectYearList();
		Calendar calendar = Calendar.getInstance();
		Integer thisYear = calendar.get(Calendar.YEAR);
		List<String> years = new ArrayList<String>();
		for (String year:projectYears){
			if(thisYear >= Integer.valueOf(year)){
				years.add(year);
			}
		}
		this.searchYear = years;
		this.selectYear = this.searchYear.get(0);
		return "initCategory";
	}
	
	public void getPoorCount(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String projectType = request.getParameter("projectType");
		String years = "";
		
		if(startYear!=null && endYear!=null){
			Integer startYearInt = Integer.valueOf(startYear);
			Integer endYearInt = Integer.valueOf(endYear);
			if(endYearInt>=startYearInt){
				for(int i=startYearInt;i<=endYearInt;i++){
					years = years + i + ",";
				}
				years = years.substring(0,years.length()-1);
			}else{
				sendResponse("ERROR", "年份选择错误");
				return;
			}
		}else{
			sendResponse("ERROR", "年份选择错误");
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("us", us);
		params.put("years", years);
		params.put("endYear", endYear);
		params.put("projectType", projectType);
		String parent = request.getParameter("parent") == null ? "" : request.getParameter("parent");
		if(!"".equals(parent)){
			Organization o = this.iOrganization.get(Integer.parseInt(parent));
			if(o != null){
				params.put("parent", o);
			}
		}
		
		List<List<Object[]>> objList = this.iCountTeacherYearService.getPoorCountList(params);
		List<Object[]> yearList= objList.get(0);
		List<Object[]> dataList= objList.get(1);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"yearRecords\":[");
		for(int i=0;i<yearList.size();i++){
			if(i!=0){sb.append(",");}
			sb.append("{\"year\":\""+yearList.get(i)[0]+"\",");
			sb.append("\"poorTrainingCount\":"+yearList.get(i)[1]+"}");
		}
		sb.append("],");
		sb.append("\"records\":[");
		DecimalFormat df=new DecimalFormat("0.00"); 
		Integer totalTeacher = 0;
		Integer totalTraining = 0;
		Integer totalPoorTraining = 0;
		Integer totalPoorTeacher = 0;
		Integer totalPoorTrainingTeacher = 0;
		for(int i=0;i<dataList.size();i++){
			totalTeacher += Integer.valueOf(dataList.get(i)[5].toString());
			totalTraining += Integer.valueOf(dataList.get(i)[3].toString());
			totalPoorTraining += Integer.valueOf(dataList.get(i)[4].toString());
			totalPoorTeacher += Integer.valueOf(dataList.get(i)[6].toString());
			totalPoorTrainingTeacher += Integer.valueOf(dataList.get(i)[7].toString());
			if(i!=0){sb.append(",");}
			Double poorTrainingRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[3].toString())!=0){
				poorTrainingRate = Double.valueOf((dataList.get(i)[4].toString())) / Double.valueOf((dataList.get(i)[3].toString())) * 100;
			}
			Double poorTrainingTeacherRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[6].toString())!=0){
				poorTrainingTeacherRate = Double.valueOf((dataList.get(i)[7].toString())) / Double.valueOf((dataList.get(i)[6].toString())) * 100;
			}
			sb.append("{\"id\":"+dataList.get(i)[0]+",\"name\":\""+dataList.get(i)[1]+"\",");
			sb.append("\"trainingCount\":"+dataList.get(i)[3]+",\"poorTrainingCount\":"+dataList.get(i)[4]+",");
			sb.append("\"teacherCount\":"+dataList.get(i)[5]+",\"poorTeacherCount\":"+dataList.get(i)[6]+",");
			sb.append("\"poorTrainingRate\":"+df.format(poorTrainingRate)+",\"poorTrainingTeacherRate\":"+df.format(poorTrainingTeacherRate)+",");
			sb.append("\"poorTrainingTeacherCount\":"+dataList.get(i)[7]+"}");
		}
		sb.append("],");
		Double totalPoorTrainingRate = 0.0;
		if(totalTraining!=0){
			totalPoorTrainingRate = Double.valueOf((totalPoorTraining.toString())) / Double.valueOf((totalTraining.toString())) * 100;
		}
		Double totalPoorTrainingTeacherRate = 0.0;
		if(totalPoorTeacher!=0){
			totalPoorTrainingTeacherRate = Double.valueOf((totalPoorTrainingTeacher.toString())) / Double.valueOf((totalPoorTeacher.toString())) * 100;
		}
		sb.append("\"level\":").append(us.getOrganizationLevel()).append(",");
		sb.append("\"totalTeacher\":").append(totalTeacher).append(",");
		sb.append("\"totalPoorTeacher\":").append(totalPoorTeacher).append(",");
		sb.append("\"totalTraining\":").append(totalTraining).append(",");
		sb.append("\"totalPoorTraining\":").append(totalPoorTraining).append(",");
		sb.append("\"totalPoorTrainingTeacher\":").append(totalPoorTrainingTeacher).append(",");
		sb.append("\"totalPoorTrainingRate\":").append(df.format(totalPoorTrainingRate)).append(",");
		sb.append("\"totalPoorTrainingTeacherRate\":").append(df.format(totalPoorTrainingTeacherRate));
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getVillageCount(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String projectType = request.getParameter("projectType");
		String years = "";
		
		if(startYear!=null && endYear!=null){
			Integer startYearInt = Integer.valueOf(startYear);
			Integer endYearInt = Integer.valueOf(endYear);
			if(endYearInt>=startYearInt){
				for(int i=startYearInt;i<=endYearInt;i++){
					years = years + i + ",";
				}
				years = years.substring(0,years.length()-1);
			}else{
				sendResponse("ERROR", "年份选择错误");
				return;
			}
		}else{
			sendResponse("ERROR", "年份选择错误");
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("us", us);
		params.put("years", years);
		params.put("endYear", endYear);
		params.put("projectType", projectType);
		String parent = request.getParameter("parent") == null ? "" : request.getParameter("parent");
		if(!"".equals(parent)){
			Organization o = this.iOrganization.get(Integer.parseInt(parent));
			if(o != null){
				params.put("parent", o);
			}
		}
		
		List<List<Object[]>> objList = this.iCountTeacherYearService.getVillageCountList(params);
		List<Object[]> yearList= objList.get(0);
		List<Object[]> dataList= objList.get(1);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"yearRecords\":[");
		for(int i=0;i<yearList.size();i++){
			if(i!=0){sb.append(",");}
			sb.append("{\"year\":\""+yearList.get(i)[0]+"\",");
			sb.append("\"poorTrainingCount\":"+yearList.get(i)[1]+"}");
		}
		sb.append("],");
		
		sb.append("\"records\":[");
		DecimalFormat df=new DecimalFormat("0.00"); 
		Integer totalTeacher = 0;
		Integer totalTraining = 0;
		Integer totalVillageTraining = 0;
		Integer totalVillageTeacher = 0;
		Integer totalVillageTrainingTeacher = 0;
		for(int i=0;i<dataList.size();i++){
			totalTeacher += Integer.valueOf(dataList.get(i)[5].toString());
			totalTraining += Integer.valueOf(dataList.get(i)[3].toString());
			totalVillageTraining += Integer.valueOf(dataList.get(i)[4].toString());
			totalVillageTeacher += Integer.valueOf(dataList.get(i)[6].toString());
			totalVillageTrainingTeacher += Integer.valueOf(dataList.get(i)[7].toString());
			if(i!=0){sb.append(",");}
			Double villageTrainingRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[3].toString())!=0){
				villageTrainingRate = Double.valueOf((dataList.get(i)[4].toString())) / Double.valueOf((dataList.get(i)[3].toString())) * 100;
			}
			Double villageTrainingTeacherRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[6].toString())!=0){
				villageTrainingTeacherRate = Double.valueOf((dataList.get(i)[7].toString())) / Double.valueOf((dataList.get(i)[6].toString())) * 100;
			}
			sb.append("{\"id\":"+dataList.get(i)[0]+",\"name\":\""+dataList.get(i)[1]+"\",");
			sb.append("\"trainingCount\":"+dataList.get(i)[3]+",\"villageTrainingCount\":"+dataList.get(i)[4]+",");
			sb.append("\"teacherCount\":"+dataList.get(i)[5]+",\"villageTeacherCount\":"+dataList.get(i)[6]+",");
			sb.append("\"villageTrainingRate\":"+df.format(villageTrainingRate)+",\"villageTrainingTeacherRate\":"+df.format(villageTrainingTeacherRate)+",");
			sb.append("\"villageTrainingTeacherCount\":"+dataList.get(i)[7]+"}");
		}
		sb.append("],");
		Double totalVillageTrainingRate = 0.0;
		if(totalTraining!=0){
			totalVillageTrainingRate = Double.valueOf((totalVillageTraining.toString())) / Double.valueOf((totalTraining.toString())) * 100;
		}
		Double totalVillageTrainingTeacherRate = 0.0;
		if(totalVillageTeacher!=0){
			totalVillageTrainingTeacherRate = Double.valueOf((totalVillageTrainingTeacher.toString())) / Double.valueOf((totalVillageTeacher.toString())) * 100;
		}
		sb.append("\"level\":").append(us.getOrganizationLevel()).append(",");
		sb.append("\"totalTeacher\":").append(totalTeacher).append(",");
		sb.append("\"totalVillageTeacher\":").append(totalVillageTeacher).append(",");
		sb.append("\"totalTraining\":").append(totalTraining).append(",");
		sb.append("\"totalVillageTraining\":").append(totalVillageTraining).append(",");
		sb.append("\"totalVillageTrainingTeacher\":").append(totalVillageTrainingTeacher).append(",");
		sb.append("\"totalVillageTrainingRate\":").append(df.format(totalVillageTrainingRate)).append(",");
		sb.append("\"totalVillageTrainingTeacherRate\":").append(df.format(totalVillageTrainingTeacherRate));
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getYearCount(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String years = "";
		
		if(startYear!=null && endYear!=null){
			Integer startYearInt = Integer.valueOf(startYear);
			Integer endYearInt = Integer.valueOf(endYear);
			if(endYearInt>=startYearInt){
				for(int i=startYearInt;i<=endYearInt;i++){
					years = years + i + ",";
				}
				years = years.substring(0,years.length()-1);
			}else{
				sendResponse("ERROR", "年份选择错误");
				return;
			}
		}else{
			sendResponse("ERROR", "年份选择错误");
			return;
		}
		
		List<Object[]> dataList= this.iCountTeacherYearService.getYearCountList(us,years);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"records\":[");
		
		DecimalFormat df=new DecimalFormat("0.00"); 
		Integer totalTeacher = 0;
		Integer totalTraining = 0;
		Integer totalTrainingTeacher = 0;
		Integer totalTrainingComplete = 0;
		for(int i=0;i<dataList.size();i++){
			totalTeacher = Integer.valueOf(dataList.get(i)[1].toString());
			totalTraining += Integer.valueOf(dataList.get(i)[2].toString());
			totalTrainingTeacher = Integer.valueOf(dataList.get(i)[5].toString());
			totalTrainingComplete += Integer.valueOf(dataList.get(i)[4].toString());
			if(i!=0){sb.append(",");}
			Double trainingTeacherRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[3].toString())!=0){
				trainingTeacherRate = Double.valueOf((dataList.get(i)[3].toString())) / Double.valueOf((dataList.get(i)[1].toString())) * 100;
			}
			Double trainingCompleteRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[2].toString())!=0){
				trainingCompleteRate = Double.valueOf((dataList.get(i)[4].toString())) / Double.valueOf((dataList.get(i)[2].toString())) * 100;
			}
			sb.append("{\"year\":\""+dataList.get(i)[0]+"\",");
			sb.append("\"teacherCount\":"+dataList.get(i)[1]+",\"trainingCount\":"+dataList.get(i)[2]+",");
			sb.append("\"trainingTeacherCount\":"+dataList.get(i)[3]+",\"trainingCompleteCount\":"+dataList.get(i)[4]+",");
			sb.append("\"trainingTeacherRate\":"+df.format(trainingTeacherRate)+",\"trainingCompleteRate\":"+df.format(trainingCompleteRate)+"}");
		}
		sb.append("],");
		Double totalTrainingTeacherRate = 0.0;
		if(totalTeacher!=0){
			totalTrainingTeacherRate = Double.valueOf((totalTrainingTeacher.toString())) / Double.valueOf((totalTeacher.toString())) * 100;
		}
		Double totalTrainingCompleteRate = 0.0;
		if(totalTraining!=0){
			totalTrainingCompleteRate = Double.valueOf((totalTrainingComplete.toString())) / Double.valueOf((totalTraining.toString())) * 100;
		}
		sb.append("\"totalTeacher\":").append(totalTeacher).append(",");
		sb.append("\"totalTraining\":").append(totalTraining).append(",");
		sb.append("\"totalTrainingTeacher\":").append(totalTrainingTeacher).append(",");
		sb.append("\"totalTrainingComplete\":").append(totalTrainingComplete).append(",");
		sb.append("\"totalTrainingTeacherRate\":").append(df.format(totalTrainingTeacherRate)).append(",");
		sb.append("\"totalTrainingCompleteRate\":").append(df.format(totalTrainingCompleteRate));
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getLevelCount(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String years = "";
		
		if(startYear!=null && endYear!=null){
			Integer startYearInt = Integer.valueOf(startYear);
			Integer endYearInt = Integer.valueOf(endYear);
			if(endYearInt>=startYearInt){
				for(int i=startYearInt;i<=endYearInt;i++){
					years = years + i + ",";
				}
				years = years.substring(0,years.length()-1);
			}else{
				sendResponse("ERROR", "年份选择错误");
				return;
			}
		}else{
			sendResponse("ERROR", "年份选择错误");
			return;
		}
		List<ProjectType> lstType = this.projectTypeService.getProjectTypeList(null,null);
		
		List<Object[]> dataList= this.iCountTeacherYearService.getLevelCountList(us,years,endYear);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"records\":[");
		
		DecimalFormat df=new DecimalFormat("0.00"); 
		Integer totalTeacher = 0;
		Integer totalTraining = 0;
		Integer totalTrainingTeacher = 0;
		Integer totalTrainingComplete = 0;
		for(int i=0;i<dataList.size();i++){
			totalTeacher = Integer.valueOf(dataList.get(i)[1].toString());
			totalTraining += Integer.valueOf(dataList.get(i)[2].toString());
			totalTrainingTeacher = Integer.valueOf(dataList.get(i)[5].toString());
			totalTrainingComplete += Integer.valueOf(dataList.get(i)[4].toString());
			if(i!=0){sb.append(",");}
			Double trainingTeacherRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[3].toString())!=0){
				trainingTeacherRate = Double.valueOf((dataList.get(i)[3].toString())) / Double.valueOf((dataList.get(i)[1].toString())) * 100;
			}
			Double trainingCompleteRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[2].toString())!=0){
				trainingCompleteRate = Double.valueOf((dataList.get(i)[4].toString())) / Double.valueOf((dataList.get(i)[2].toString())) * 100;
			}
			String level = "无";
			if(lstType != null && lstType.size() > 0){
				level = this.getProjectTypeCN(Integer.valueOf(dataList.get(i)[0].toString()),lstType);
			}
			sb.append("{\"level\":\""+level+"\",");
			sb.append("\"teacherCount\":"+dataList.get(i)[1]+",\"trainingCount\":"+dataList.get(i)[2]+",");
			sb.append("\"trainingTeacherCount\":"+dataList.get(i)[3]+",\"trainingCompleteCount\":"+dataList.get(i)[4]+",");
			sb.append("\"trainingTeacherRate\":"+df.format(trainingTeacherRate)+",\"trainingCompleteRate\":"+df.format(trainingCompleteRate)+"}");
		}
		sb.append("],");
		Double totalTrainingTeacherRate = 0.0;
		if(totalTeacher!=0){
			totalTrainingTeacherRate = Double.valueOf((totalTrainingTeacher.toString())) / Double.valueOf((totalTeacher.toString())) * 100;
		}
		Double totalTrainingCompleteRate = 0.0;
		if(totalTraining!=0){
			totalTrainingCompleteRate = Double.valueOf((totalTrainingComplete.toString())) / Double.valueOf((totalTraining.toString())) * 100;
		}
		sb.append("\"totalTeacher\":").append(totalTeacher).append(",");
		sb.append("\"totalTraining\":").append(totalTraining).append(",");
		sb.append("\"totalTrainingTeacher\":").append(totalTrainingTeacher).append(",");
		sb.append("\"totalTrainingComplete\":").append(totalTrainingComplete).append(",");
		sb.append("\"totalTrainingTeacherRate\":").append(df.format(totalTrainingTeacherRate)).append(",");
		sb.append("\"totalTrainingCompleteRate\":").append(df.format(totalTrainingCompleteRate));
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getSubjectCount(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String years = "";
		
		if(startYear!=null && endYear!=null){
			Integer startYearInt = Integer.valueOf(startYear);
			Integer endYearInt = Integer.valueOf(endYear);
			if(endYearInt>=startYearInt){
				for(int i=startYearInt;i<=endYearInt;i++){
					years = years + i + ",";
				}
				years = years.substring(0,years.length()-1);
			}else{
				sendResponse("ERROR", "年份选择错误");
				return;
			}
		}else{
			sendResponse("ERROR", "年份选择错误");
			return;
		}
		
		List<Object[]> dataList= this.iCountTeacherYearService.getSubjectCountList(us,years,endYear);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"records\":[");
		
		DecimalFormat df=new DecimalFormat("0.00"); 
		Integer totalTeacher = 0;
		Integer totalTraining = 0;
		Integer totalTrainingTeacher = 0;
		Integer totalTrainingComplete = 0;
		for(int i=0;i<dataList.size();i++){
			totalTeacher += Integer.valueOf(dataList.get(i)[1].toString());
			totalTraining += Integer.valueOf(dataList.get(i)[2].toString());
			totalTrainingTeacher += Integer.valueOf(dataList.get(i)[3].toString());
			totalTrainingComplete += Integer.valueOf(dataList.get(i)[4].toString());
			if(i!=0){sb.append(",");}
			Double trainingTeacherRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[3].toString())!=0){
				trainingTeacherRate = Double.valueOf((dataList.get(i)[3].toString())) / Double.valueOf((dataList.get(i)[1].toString())) * 100;
			}
			Double trainingCompleteRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[2].toString())!=0){
				trainingCompleteRate = Double.valueOf((dataList.get(i)[4].toString())) / Double.valueOf((dataList.get(i)[2].toString())) * 100;
			}
			sb.append("{\"grade\":\""+dataList.get(i)[5]+"\",");
			sb.append("\"teacherCount\":"+dataList.get(i)[1]+",\"trainingCount\":"+dataList.get(i)[2]+",");
			sb.append("\"trainingTeacherCount\":"+dataList.get(i)[3]+",\"trainingCompleteCount\":"+dataList.get(i)[4]+",");
			sb.append("\"trainingTeacherRate\":"+df.format(trainingTeacherRate)+",\"trainingCompleteRate\":"+df.format(trainingCompleteRate)+"}");
		}
		sb.append("],");
		Double totalTrainingTeacherRate = 0.0;
		if(totalTeacher!=0){
			totalTrainingTeacherRate = Double.valueOf((totalTrainingTeacher.toString())) / Double.valueOf((totalTeacher.toString())) * 100;
		}
		Double totalTrainingCompleteRate = 0.0;
		if(totalTraining!=0){
			totalTrainingCompleteRate = Double.valueOf((totalTrainingComplete.toString())) / Double.valueOf((totalTraining.toString())) * 100;
		}
		sb.append("\"totalTeacher\":").append(totalTeacher).append(",");
		sb.append("\"totalTraining\":").append(totalTraining).append(",");
		sb.append("\"totalTrainingTeacher\":").append(totalTrainingTeacher).append(",");
		sb.append("\"totalTrainingComplete\":").append(totalTrainingComplete).append(",");
		sb.append("\"totalTrainingTeacherRate\":").append(df.format(totalTrainingTeacherRate)).append(",");
		sb.append("\"totalTrainingCompleteRate\":").append(df.format(totalTrainingCompleteRate));
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getGradeCount(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String years = "";
		
		if(startYear!=null && endYear!=null){
			Integer startYearInt = Integer.valueOf(startYear);
			Integer endYearInt = Integer.valueOf(endYear);
			if(endYearInt>=startYearInt){
				for(int i=startYearInt;i<=endYearInt;i++){
					years = years + i + ",";
				}
				years = years.substring(0,years.length()-1);
			}else{
				sendResponse("ERROR", "年份选择错误");
				return;
			}
		}else{
			sendResponse("ERROR", "年份选择错误");
			return;
		}
		
		List<Object[]> dataList= this.iCountTeacherYearService.getGradeCountList(us,years,endYear);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"records\":[");
		
		DecimalFormat df=new DecimalFormat("0.00"); 
		Integer totalTeacher = 0;
		Integer totalTraining = 0;
		Integer totalTrainingTeacher = 0;
		Integer totalTrainingComplete = 0;
		for(int i=0;i<dataList.size();i++){
			totalTeacher += Integer.valueOf(dataList.get(i)[1].toString());
			totalTraining += Integer.valueOf(dataList.get(i)[2].toString());
			totalTrainingTeacher += Integer.valueOf(dataList.get(i)[3].toString());
			totalTrainingComplete += Integer.valueOf(dataList.get(i)[4].toString());
			if(i!=0){sb.append(",");}
			Double trainingTeacherRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[3].toString())!=0){
				trainingTeacherRate = Double.valueOf((dataList.get(i)[3].toString())) / Double.valueOf((dataList.get(i)[1].toString())) * 100;
			}
			Double trainingCompleteRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[2].toString())!=0){
				trainingCompleteRate = Double.valueOf((dataList.get(i)[4].toString())) / Double.valueOf((dataList.get(i)[2].toString())) * 100;
			}
			sb.append("{\"grade\":\""+dataList.get(i)[5]+"\",");
			sb.append("\"teacherCount\":"+dataList.get(i)[1]+",\"trainingCount\":"+dataList.get(i)[2]+",");
			sb.append("\"trainingTeacherCount\":"+dataList.get(i)[3]+",\"trainingCompleteCount\":"+dataList.get(i)[4]+",");
			sb.append("\"trainingTeacherRate\":"+df.format(trainingTeacherRate)+",\"trainingCompleteRate\":"+df.format(trainingCompleteRate)+"}");
		}
		sb.append("],");
		Double totalTrainingTeacherRate = 0.0;
		if(totalTeacher!=0){
			totalTrainingTeacherRate = Double.valueOf((totalTrainingTeacher.toString())) / Double.valueOf((totalTeacher.toString())) * 100;
		}
		Double totalTrainingCompleteRate = 0.0;
		if(totalTraining!=0){
			totalTrainingCompleteRate = Double.valueOf((totalTrainingComplete.toString())) / Double.valueOf((totalTraining.toString())) * 100;
		}
		sb.append("\"totalTeacher\":").append(totalTeacher).append(",");
		sb.append("\"totalTraining\":").append(totalTraining).append(",");
		sb.append("\"totalTrainingTeacher\":").append(totalTrainingTeacher).append(",");
		sb.append("\"totalTrainingComplete\":").append(totalTrainingComplete).append(",");
		sb.append("\"totalTrainingTeacherRate\":").append(df.format(totalTrainingTeacherRate)).append(",");
		sb.append("\"totalTrainingCompleteRate\":").append(df.format(totalTrainingCompleteRate));
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void getOrganizationCount(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String years = "";
		
		if(startYear!=null && endYear!=null){
			Integer startYearInt = Integer.valueOf(startYear);
			Integer endYearInt = Integer.valueOf(endYear);
			if(endYearInt>=startYearInt){
				for(int i=startYearInt;i<=endYearInt;i++){
					years = years + i + ",";
				}
				years = years.substring(0,years.length()-1);
			}else{
				sendResponse("ERROR", "年份选择错误");
				return;
			}
		}else{
			sendResponse("ERROR", "年份选择错误");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("us", us);
		params.put("years", years);
		params.put("endYear", endYear);
		String parent = request.getParameter("parent") == null ? "" : request.getParameter("parent");
		if(!"".equals(parent)){
			params.put("parent", parent);
		}
		List<Object[]> dataList= this.iCountTeacherYearService.getOrganizationCountList(params);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"records\":[");
		
		DecimalFormat df=new DecimalFormat("0.00"); 
		Integer totalTeacher = 0;
		Integer totalTraining = 0;
		Integer totalTrainingTeacher = 0;
		Integer totalTrainingComplete = 0;
		for(int i=0;i<dataList.size();i++){
			totalTeacher += Integer.valueOf(dataList.get(i)[1].toString());
			totalTraining += Integer.valueOf(dataList.get(i)[2].toString());
			totalTrainingTeacher += Integer.valueOf(dataList.get(i)[3].toString());
			totalTrainingComplete += Integer.valueOf(dataList.get(i)[4].toString());
			if(i!=0){sb.append(",");}
			Double trainingTeacherRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[3].toString())!=0){
				trainingTeacherRate = Double.valueOf((dataList.get(i)[3].toString())) / Double.valueOf((dataList.get(i)[1].toString())) * 100;
			}
			Double trainingCompleteRate = 0.0;
			if(Integer.valueOf(dataList.get(i)[2].toString())!=0){
				trainingCompleteRate = Double.valueOf((dataList.get(i)[4].toString())) / Double.valueOf((dataList.get(i)[2].toString())) * 100;
			}
			sb.append("{\"organization\":\""+dataList.get(i)[5]+"\",");
			sb.append("\"parent\":\""+dataList.get(i)[0]+"_"+dataList.get(i)[6]+"\",");
			sb.append("\"teacherCount\":"+dataList.get(i)[1]+",\"trainingCount\":"+dataList.get(i)[2]+",");
			sb.append("\"trainingTeacherCount\":"+dataList.get(i)[3]+",\"trainingCompleteCount\":"+dataList.get(i)[4]+",");
			sb.append("\"trainingTeacherRate\":"+df.format(trainingTeacherRate)+",\"trainingCompleteRate\":"+df.format(trainingCompleteRate)+"}");
		}
		sb.append("],");
		Double totalTrainingTeacherRate = 0.0;
		if(totalTeacher!=0){
			totalTrainingTeacherRate = Double.valueOf((totalTrainingTeacher.toString())) / Double.valueOf((totalTeacher.toString())) * 100;
		}
		Double totalTrainingCompleteRate = 0.0;
		if(totalTraining!=0){
			totalTrainingCompleteRate = Double.valueOf((totalTrainingComplete.toString())) / Double.valueOf((totalTraining.toString())) * 100;
		}
		sb.append("\"level\":").append(us.getOrganizationLevel()).append(",");
		sb.append("\"totalTeacher\":").append(totalTeacher).append(",");
		sb.append("\"totalTraining\":").append(totalTraining).append(",");
		sb.append("\"totalTrainingTeacher\":").append(totalTrainingTeacher).append(",");
		sb.append("\"totalTrainingComplete\":").append(totalTrainingComplete).append(",");
		sb.append("\"totalTrainingTeacherRate\":").append(df.format(totalTrainingTeacherRate)).append(",");
		sb.append("\"totalTrainingCompleteRate\":").append(df.format(totalTrainingCompleteRate));
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 导出教师培训情况名单。
	 * 已培训名单、未培训名单
	 */
	public void output(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("usersession");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("us", us);
//		params.put("years", years);
//		params.put("endYear", endYear);
		String parent = request.getParameter("parent") == null ? "" : request.getParameter("parent");
		if(!"".equals(parent)){
			params.put("parent", parent);
		}
	}
	
	/**
	 * 获取项目列表
	 */
	@SuppressWarnings("rawtypes")
	public void getProjectList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		Map<String,Object> map = new HashMap<String,Object>();

		// UserSession us = (UserSession) session.getAttribute("usersession");
		map.put("organization", 26124);
		Map<String, String> sortMap = new HashMap<String, String>();
		sortMap.put("creattime", "desc");
		
		List list = this.iProjectService.getProjectListByParams(map,
				sortMap, null, -1, -1);
		sb.append("{");
		sb.append("\"status\":\"OK\"");
		sb.append(",");
		sb.append("\"records\":[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				sb.append("{");
				sb.append("\"id\":"+p.getId());
				sb.append(",");
				sb.append("\"name\":\""+p.getName()+"\"");
				sb.append("},");
			}
			sb.delete(sb.length()-1, sb.length());
			
		}
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}
	
	public ICountTeacherYearService getiCountTeacherYearService() {
		return iCountTeacherYearService;
	}
	
	public void setiCountTeacherYearService(ICountTeacherYearService iCountTeacherYearService) {
		this.iCountTeacherYearService = iCountTeacherYearService;
	}
	
	public String getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(String selectYear) {
		this.selectYear = selectYear;
	}
	
	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	
	public IProjectTypeService getProjectTypeService() {
		return projectTypeService;
	}
	

	public void setProjectTypeService(IProjectTypeService projectTypeService) {
		this.projectTypeService = projectTypeService;
	}
	
	
}
