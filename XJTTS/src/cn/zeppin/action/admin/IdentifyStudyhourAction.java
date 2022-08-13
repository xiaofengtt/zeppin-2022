package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.IdentifyStudyhour;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.teachingSubjectEx;
import cn.zeppin.service.IIdentifyStudyhourService;
import cn.zeppin.service.IProjectCycleService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.pinyingUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @category 培训学时配置
 */
public class IdentifyStudyhourAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(IdentifyStudyhourAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IIdentifyStudyhourService identifyStudyhourService;
	private IProjectCycleService projectCycleService;
	private IProjectTypeService projectTypeService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IProjectService iProjectService;
	
	private List<teachingSubjectEx> subjectList;
	private String[] yearArray;
	private List<Map> studyHourList;
	
	public IdentifyStudyhourAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	/**
	 * 获取学时配置列表
	 */
	public void getList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		
		//分页参数
		String ist = request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = Integer.parseInt(ien);
		
		int offset = (start - 1) * DictionyMap.maxPageSize;
		//筛选参数
		String projectCycleId = request.getParameter("projectCycleId") == null ? "" : request.getParameter("projectCycleId");
		String projectTypeId = request.getParameter("projectTypeId") == null ? "" : request.getParameter("projectTypeId");
		String projectId = request.getParameter("projectId") == null ? "" : request.getParameter("projectId");
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "" : request.getParameter("trainingSubjectId");
		String year = request.getParameter("year") == null ? "" : request.getParameter("year");
		if("0".equals(year)) {
			year = "";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(projectCycleId)){
			params.put("projectCycle", projectCycleId);
		}
		
		if(!"".equals(projectTypeId)){
			params.put("projectType", projectTypeId);
		}
		
		if(!"".equals(projectId)){
			params.put("project", projectId);
		}
		
		if(!"".equals(trainingSubjectId)){
			params.put("trainingSubject", trainingSubjectId);
		}
		
		if(!"".equals(year)){
			params.put("year", year);
		}
		
		params.put("status", 1);
		int count = this.identifyStudyhourService.getListCountByParams(params);
		int total = (int) Math.ceil((float) count / DictionyMap.maxPageSize);
		List<IdentifyStudyhour> lst = this.identifyStudyhourService.getListByParams(params, offset, limit, "projectType, ids.year, ids.project, ids.trainingSubject");
		sb.append("{");
		sb.append("\"status\":\"OK\"");
		sb.append(",");
		sb.append("\"totalcount\":"+count);
		sb.append(",");
		sb.append("\"currentPage\":"+(start));
		sb.append(",");
		sb.append("\"totalPage\":" + total);
		sb.append(",");
		sb.append("\"records\":[");
		if(lst != null && lst.size() > 0){
			for(IdentifyStudyhour is : lst){
				Project project = is.getProject();
				TrainingSubject ts = is.getTrainingSubject();
				sb.append("{");
				sb.append("\"id\":"+is.getId());
				sb.append(",");
				if(project != null){
					sb.append("\"projectId\":"+project.getId());
					sb.append(",");
					sb.append("\"name\":\""+project.getName()+"\"");
					sb.append(",");
				}else{
					sb.append("\"projectId\":" + 0);
					sb.append(",");
					sb.append("\"name\":\"\"");
					sb.append(",");
				}
				if(is.getProjectType() != null){
					if(is.getProjectType().getProjectType() != null){
						sb.append("\"projectTypeFirst\":\""+is.getProjectType().getProjectType().getName()+"\"");
						sb.append(",");
						sb.append("\"projectTypeSecond\":\""+is.getProjectType().getName()+"\"");
						sb.append(",");
					}else{
						sb.append("\"projectTypeFirst\":\""+is.getProjectType().getName()+"\"");
						sb.append(",");
						sb.append("\"projectTypeSecond\":\"\"");
						sb.append(",");
					}
				}else{
					sb.append("\"parentTypeFirst\":\"0\"");
					sb.append(",");
					sb.append("\"parentTypeSecond\":\"0\"");
					sb.append(",");
				}
				if(ts != null){
					sb.append("\"trainingSubject\":\""+ts.getName()+"\"");
				}else{
					sb.append("\"trainingSubject\":\"\"");
				}
				
				sb.append(",");
				int credit = is.getCredit();//学分
				String years = is.getYear();
				sb.append("\"studyhour\":" + is.getStudyhour());
				sb.append(",");
				sb.append("\"credit\":"+credit);
				sb.append(",");
				sb.append("\"year\":\""+ (years == null ? "" : years) + "\"");
				sb.append("},");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String initAddPage(){
		initServlert();
		String projectCycle = request.getParameter("projectCycle");
		if(projectCycle != null){
			ProjectCycle pc = this.projectCycleService.get(Integer.valueOf(projectCycle));
			studyHourList = JSONUtils.json2list(pc.getStudyhour(), Map.class);
		}
		
		
		List<TrainingSubject> sList = this.iTrainingSubjectService.findAll();
		List<teachingSubjectEx> lis = new ArrayList<teachingSubjectEx>();
		for(TrainingSubject ts : sList){
			teachingSubjectEx tse = new teachingSubjectEx();
			tse.setId(ts.getId().toString());
			tse.setName(ts.getName());
			tse.setSearchString(ts.getName() + pinyingUtil.getFirstSpell(ts.getName()));
			lis.add(tse);
		}
		this.subjectList = lis;
		if (yearArray == null) {
			@SuppressWarnings("deprecation")
			int year = new Date().getYear() + 1878;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}
		return "initAdd";
	}
	
	
	/**
	 * 根据参数获取项目信息 返回json id和名称
	 */
	@SuppressWarnings({ "rawtypes" })
	public void getProjectsByPramers() {
		initServlert();
		String year = request.getParameter("year");
		String projectType = request.getParameter("projectType");

		Map<String, Object> params = new HashMap<String, Object>();

		if (year != null && !year.equals("")) {
			params.put("year", year);
		}
		if (projectType != null && !projectType.equals("")) {
			params.put("projectType", Integer.valueOf(projectType));
		}
		
		Map<String, String> sortParams = new HashMap<String, String>();
		sortParams.put("creattime", "desc");
		List li = this.iProjectService.getProjectListByParams(params, sortParams, null, -1, -1);

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("{\"id\":0,\"name\":\"请选择\"},");
		for(int i = 0; i < li.size(); i++){
			Object[] obj = (Object[])li.get(i);
			Project p = (Project)obj[0];
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}
	
	/**
	 * 添加学时认定
	 */
	public void add(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String method = request.getParameter("method") == null?"":request.getParameter("method");
		if("".equals(method)){
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"参数异常\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		String projectCycleId = request.getParameter("projectCycleId") == null ? "0" : request.getParameter("projectCycleId");
		String projectTypeId = request.getParameter("projectTypeId") == null ? "0" : request.getParameter("projectTypeId");
		
		String projectId = request.getParameter("projectId") == null ? "0" : request.getParameter("projectId");
		if("0".equals(projectId) || "".equals(projectId)){
			projectId = "0";
		}
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "0" : request.getParameter("trainingSubjectId");
		if("0".equals(trainingSubjectId) || "".equals(trainingSubjectId)){
			trainingSubjectId = "0";
		}
		String year = request.getParameter("year") == null ? "0" : request.getParameter("year");
		if("0".equals(year) || "".equals(year)){
			year = null;
		}
		String identifycId = request.getParameter("id") == null ? "" : request.getParameter("id");
		
		String credit = request.getParameter("credit") == null ? "0" : request.getParameter("credit");
		if("".equals(credit)){
			credit = "0";
		}
		
		if(method.equals("add")){//添加
			if("0".equals(projectCycleId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"参数异常\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			if("0".equals(projectTypeId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"参数异常\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			ProjectCycle pc = this.projectCycleService.get(Integer.valueOf(projectCycleId));
			List<Map> studyhourList = JSONUtils.json2list(pc.getStudyhour(), Map.class);
			
			List<Map> resultList = new ArrayList<Map>();
			for(Map map : studyhourList){
				String name = map.get("name").toString();
				String nameCN = map.get("nameCN").toString();
				String value = request.getParameter(name) == null ? "0" : request.getParameter(name);
				if("".equals(value)){
					value = "0";
				}
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("name", name);
				resultMap.put("nameCN", nameCN);
				resultMap.put("value", Integer.parseInt(value));
				resultList.add(resultMap);
			}
			
			ProjectType pt = this.projectTypeService.get(Integer.parseInt(projectTypeId));
			if(pt == null){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"项目类型不存在,请刷新页面\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			Project project = null;
			if(!"0".equals(projectId)){
				project = this.iProjectService.get(Integer.parseInt(projectId));
				if(project == null){
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"培训项目不存在,请刷新页面\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
			}
			
			TrainingSubject ts = null;
			if(!"0".equals(trainingSubjectId)){
				ts = this.iTrainingSubjectService.get(Short.parseShort(trainingSubjectId));
				if(ts == null){
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"培训学科不存在,请刷新页面\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
			}
			
			IdentifyStudyhour is = new IdentifyStudyhour();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projectCycle", projectCycleId);
			params.put("projectType", projectTypeId);
			params.put("project", projectId == null ? "empty" : projectId);
			params.put("trainingSubject", trainingSubjectId == null ? "empty" : trainingSubjectId);
			params.put("year", year == null ? "empty" : year);
			
			List<IdentifyStudyhour> lst = this.identifyStudyhourService.getListByParams(params, -1, -1, "id");
			if(lst != null && lst.size() > 0){//已存在更新
				is =  lst.get(0);
				is.setStudyhour(JSONUtils.obj2json(resultList));
				is.setCredit(Integer.parseInt(credit));
				is.setStatus((short)1);
				
				try {
					this.identifyStudyhourService.update(is);
					sb.append("{");
					sb.append("\"status\":\"OK\"");
					sb.append(",");
					sb.append("\"message\":\"操作成功\"");
					sb.append(",");
					sb.append("\"id\":"+is.getId());
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"操作异常\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
			}
			
			is.setProjectCycle(pc);
			is.setProjectType(pt);
			is.setProject(project);
			is.setTrainingSubject(ts);
			is.setStudyhour(JSONUtils.obj2json(resultList));
			is.setCredit(Integer.parseInt(credit));
			is.setYear(year);
			is.setCreator(us.getId());
			is.setStatus((short)1);
			
			try {
				is = this.identifyStudyhourService.add(is);
				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"message\":\"操作成功\"");
				sb.append(",");
				sb.append("\"id\":"+is.getId());
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"操作异常\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}else{
			if("".equals(identifycId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"参数异常\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			IdentifyStudyhour is = this.identifyStudyhourService.get(Integer.parseInt(identifycId));
			if(is != null){
				List<Map> studyhourList = JSONUtils.json2list(is.getProjectCycle().getStudyhour(), Map.class);
				
				List<Map> resultList = new ArrayList<Map>();
				for(Map map : studyhourList){
					String name = map.get("name").toString();
					String nameCN = map.get("nameCN").toString();
					String value = request.getParameter(name) == null ? "0" : request.getParameter(name);
					if("".equals(value)){
						value = "0";
					}
					
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("name", name);
					resultMap.put("nameCN", nameCN);
					resultMap.put("value", Integer.parseInt(value));
					resultList.add(resultMap);
				}
				
				is.setStudyhour(JSONUtils.obj2json(resultList));
				is.setCredit(Integer.parseInt(credit));
				try {
					this.identifyStudyhourService.update(is);
				} catch (Exception e) {
					e.printStackTrace();
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"操作异常\"");
					sb.append(",");
					sb.append("\"id\":"+is.getId());
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				
				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"message\":\"操作成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}else{
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"学时配置记录不存在\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
		}

	}
	
	/**
	 * 编辑
	 * （含删除）
	 */
	public void delete(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		String identifycId = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(identifycId)){
			identifycId = "0";
		}
		if("0".equals(identifycId)){
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"参数异常\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		IdentifyStudyhour is = this.identifyStudyhourService.get(Integer.parseInt(identifycId));
		if(is == null){
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"删除项不存在\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		is.setStatus((short)0);
		try {
			this.identifyStudyhourService.update(is);
			sb.append("{");
			sb.append("\"status\":\"OK\"");
			sb.append(",");
			sb.append("\"message\":\"删除成功\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"操作异常\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
	}

	public IIdentifyStudyhourService getIdentifyStudyhourService() {
		return identifyStudyhourService;
	}

	public void setIdentifyStudyhourService(IIdentifyStudyhourService identifyStudyhourService) {
		this.identifyStudyhourService = identifyStudyhourService;
	}

	public IProjectTypeService getProjectTypeService() {
		return projectTypeService;
	}

	public void setProjectTypeService(IProjectTypeService projectTypeService) {
		this.projectTypeService = projectTypeService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(
			ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}
	
	public List<teachingSubjectEx> getSubjectList() {
		return subjectList;
	}
	
	public void setSubjectList(List<teachingSubjectEx> subjectList) {
		this.subjectList = subjectList;
	}

	public String[] getYearArray() {
		return yearArray;
	}

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
	}

	public List<Map> getStudyHourList() {
		return studyHourList;
	}

	public void setStudyHourList(List<Map> studyHourList) {
		this.studyHourList = studyHourList;
	}

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IProjectCycleService getProjectCycleService() {
		return projectCycleService;
	}

	public void setProjectCycleService(IProjectCycleService projectCycleService) {
		this.projectCycleService = projectCycleService;
	}
}
