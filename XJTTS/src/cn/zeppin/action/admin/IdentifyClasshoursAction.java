package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.IdentifyClasshours;
import cn.zeppin.entity.IdentifyClasshoursProjectYear;
import cn.zeppin.entity.IdentifyClasshoursSubject;
import cn.zeppin.entity.IdentifyClasshoursSubjectYear;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.entity.teachingSubjectEx;
import cn.zeppin.service.IIdentifyClasshoursProjectYearService;
import cn.zeppin.service.IIdentifyClasshoursService;
import cn.zeppin.service.IIdentifyClasshoursSubjectService;
import cn.zeppin.service.IIdentifyClasshoursSubjectYearService;
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
public class IdentifyClasshoursAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(IdentifyClasshoursAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IIdentifyClasshoursService identifyClasshoursService;
	private IIdentifyClasshoursSubjectService identifyClasshoursSubjectService;
	private IIdentifyClasshoursSubjectYearService identifyClasshoursSubjectYearService;
	private IIdentifyClasshoursProjectYearService identifyClasshoursProjectYearService;
	private IProjectCycleService projectCycleService;
	private IProjectTypeService projectTypeService;
	private ITrainingSubjectService iTrainingSubjectService;
	private IProjectService iProjectService;
	
	private List<teachingSubjectEx> subjectList;
	private String[] yearArray;

	private Integer projectCycleId = 1;
	private List<Map> studyhourList;
	
	public IdentifyClasshoursAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	
	
	/**
	 * 获取培训学时配置列表
	 * 通过获取projectType列表进入Identify表进行查询
	 * 先取所有1级的项目列表
	 */
	public void getList(){
		initServlert();
		ProjectCycle pc = this.projectCycleService.get(projectCycleId);
		studyhourList = JSONUtils.json2list(pc.getStudyhour(), Map.class);
		
//		UserSession us = (UserSession) session.getAttribute("usersession");
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", "0");
		List<ProjectType> lspt = this.projectTypeService.getProjectTypeList(null, params);//
		sb.append("{");
		sb.append("\"status\":\"OK\"");
		sb.append(",");
		sb.append("\"records\":[");
		for(ProjectType pt : lspt){
			sb.append(getTreeList(pt));
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append("]");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 递归获取树形项目类型字符串
	 * @param pt
	 * @return
	 */
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
		}else{
			sb.append(",");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projecttype", pt.getId());
			params.put("status", 1);
			List<IdentifyClasshours> lst = this.identifyClasshoursService.getListByParams(params, -1, -1, "id");
			if(lst != null && lst.size() > 0){
				IdentifyClasshours idc =  lst.get(0);
				int credit = idc.getCredit();//学分
				sb.append("\"identifyClasshours\":{");
				sb.append("\"id\":"+idc.getId());
				sb.append(",");
				sb.append("\"studyhour\":" + idc.getStudyhour());
				sb.append(",");
				sb.append("\"credit\":"+credit);
				sb.append("}");
			}else{
				sb.append("\"identifyClasshours\":{");
				sb.append("\"id\":"+0);
				sb.append(",");
				List<Map> resultList = new ArrayList<Map>();
				for(Map map : studyhourList){
					String name = map.get("name").toString();
					String nameCN = map.get("nameCN").toString();
					
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("name", name);
					resultMap.put("nameCN", nameCN);
					resultMap.put("value", 0);
					resultList.add(resultMap);
				}
				sb.append("\"studyhour\":" + JSONUtils.obj2json(resultList));
				sb.append(",");
				sb.append("\"credit\":"+0);
				sb.append("}");
			}
		}
		sb.append("},");
		return sb.toString();
//		}
	}
	
	
	/**
	 * 添加
	 * projectTypeId -- projectTypeId
	 * id  -- identifycId
	 * centralizeCH -- 集中学时
	 * informationCH -- 信息技术学时
	 * regionalCH -- 区域特色学时
	 * schoolCH -- 校本培训学时
	 */
	public void add(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		String projectTypeId = request.getParameter("projectTypeId") == null ? "0" : request.getParameter("projectTypeId");
		if("".equals(projectTypeId)){
			projectTypeId = "0";
		}
		String identifycId = request.getParameter("id") == null ? "" : request.getParameter("id");
		
		ProjectCycle pc = this.projectCycleService.get(projectCycleId);
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
		
		String credit = request.getParameter("credit") == null ? "0" : request.getParameter("credit");
		if("".equals(credit)){
			credit = "0";
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
		
		if(!"".equals(identifycId)){//编辑
			
			IdentifyClasshours idc = this.identifyClasshoursService.get(Integer.parseInt(identifycId));
			if(idc != null){
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
				try {
					this.identifyClasshoursService.update(idc);
				} catch (Exception e) {
					e.printStackTrace();
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"操作异常\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
			
		}else{//添加
			
			IdentifyClasshours idc = new IdentifyClasshours();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projecttype", pt.getId());
			params.put("status", 1);
			List<IdentifyClasshours> lst = this.identifyClasshoursService.getListByParams(params, -1, -1, "id");
			if(lst != null && lst.size() > 0){//已存在更新
				idc =  lst.get(0);
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
				try {
					this.identifyClasshoursService.update(idc);
					sb.append("{");
					sb.append("\"status\":\"OK\"");
					sb.append(",");
					sb.append("\"message\":\"操作成功\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
			
			
			idc.setStudyhour(JSONUtils.obj2json(resultList));
			idc.setCredit(Integer.parseInt(credit));
			idc.setCreator(us.getId());
			idc.setProjecttype(pt);
			idc.setStatus((short)1);
			
			try {
				idc = this.identifyClasshoursService.add(idc);
				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"message\":\"操作成功\"");
				sb.append(",");
				sb.append("\"id\":"+idc.getId());
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
	}
	
	
	/**
	 * 获取特殊学科培训学时配置列表
	 * 查询参数：1、projectTypeId
	 * 		2、trainingSubjectId
	 */
	public void getSpecialList(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
//		System.out.println(us.getIdcard());
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
		String projectTypeId = request.getParameter("projectTypeId") == null ? "" : request.getParameter("projectTypeId");
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "" : request.getParameter("trainingSubjectId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(projectTypeId)){
			params.put("projecttype", projectTypeId);
		}
		
		if(!"".equals(trainingSubjectId)){
			params.put("trainingsubject", trainingSubjectId);
		}
		
		params.put("status", 1);
		int count = this.identifyClasshoursSubjectService.getListCountByParams(params);
		int total = (int) Math.ceil((float) count / DictionyMap.maxPageSize);
		List<IdentifyClasshoursSubject> lst = this.identifyClasshoursSubjectService.getListByParams(params, offset, limit, "createtime");
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
			for(IdentifyClasshoursSubject idc : lst){
				ProjectType pt = idc.getProjecttype();
				TrainingSubject ts = idc.getTrainingsubject();
				sb.append("{");
				sb.append("\"id\":"+idc.getId());
				sb.append(",");
				sb.append("\"projectTypeId\":"+pt.getId());
				sb.append(",");
				sb.append("\"name\":\""+pt.getName()+"\"");
				sb.append(",");
				if(pt.getProjectType() != null){
					sb.append("\"parentType\":\""+pt.getProjectType().getName()+"\"");
					sb.append(",");
				}else{
					sb.append("\"parentType\":\"0\"");
					sb.append(",");
				}
				sb.append("\"trainingSubject\":\""+ts.getName()+"\"");
				sb.append(",");
				sb.append("\"studyhour\":" + idc.getStudyhour());
				sb.append(",");
				int credit = idc.getCredit();//学分
				sb.append("\"credit\":"+credit);
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
//		UserSession us = (UserSession) session.getAttribute("usersession");
		List<TrainingSubject> sList = this.iTrainingSubjectService.findAll();
		subjectList = new ArrayList<teachingSubjectEx>();
		for(TrainingSubject ts : sList){
			teachingSubjectEx tse = new teachingSubjectEx();
			tse.setId(ts.getId().toString());
			tse.setName(ts.getName());
			tse.setSearchString(ts.getName() + pinyingUtil.getFirstSpell(ts.getName()));
			subjectList.add(tse);
		}
		return "initAdd";
	}
	
	/**
	 * 添加特殊学科学时认定
	 * method -- add/edit
	 * projectTypeId -- projectTypeId
	 * trainingSubjectId -- 学科ID
	 * id  -- identifycId
	 * centralizeCH -- 集中学时
	 * informationCH -- 信息技术学时
	 * regionalCH -- 区域特色学时
	 * schoolCH -- 校本培训学时
	 */
	public void specialAdd(){
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
		String projectTypeId = request.getParameter("projectTypeId") == null ? "0" : request.getParameter("projectTypeId");
		if("".equals(projectTypeId)){
			projectTypeId = "0";
		}
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "0" : request.getParameter("trainingSubjectId");
		if("".equals(trainingSubjectId)){
			trainingSubjectId = "0";
		}
		
		String identifycId = request.getParameter("id") == null ? "" : request.getParameter("id");

		ProjectCycle pc = this.projectCycleService.get(projectCycleId);
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
		
		String credit = request.getParameter("credit") == null ? "0" : request.getParameter("credit");
		if("".equals(credit)){
			credit = "0";
		}
		
		if(method.equals("add")){//添加
			if("0".equals(projectTypeId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"请选择项目类型\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if("0".equals(trainingSubjectId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"请选择培训学科\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
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
			
			TrainingSubject ts = this.iTrainingSubjectService.get(Short.parseShort(trainingSubjectId));
			if(ts == null){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"培训学科不存在,请刷新页面\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			IdentifyClasshoursSubject idc = new IdentifyClasshoursSubject();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projecttype", pt.getId());
			params.put("trainingsubject", ts.getId());
//			params.put("status", 1);
			List<IdentifyClasshoursSubject> lst = this.identifyClasshoursSubjectService.getListByParams(params, -1, -1, "id");
			if(lst != null && lst.size() > 0){//已存在更新
				idc =  lst.get(0);
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
				idc.setStatus((short)1);
				
				try {
					this.identifyClasshoursSubjectService.update(idc);
					sb.append("{");
					sb.append("\"status\":\"OK\"");
					sb.append(",");
					sb.append("\"message\":\"操作成功\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
			
			idc.setTrainingsubject(ts);
			idc.setStudyhour(JSONUtils.obj2json(resultList));
			idc.setCredit(Integer.parseInt(credit));
			idc.setCreator(us.getId());
			idc.setProjecttype(pt);
			idc.setStatus((short)1);
			
			try {
				idc = this.identifyClasshoursSubjectService.add(idc);
				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"message\":\"操作成功\"");
				sb.append(",");
				sb.append("\"id\":"+idc.getId());
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
			IdentifyClasshoursSubject idc = this.identifyClasshoursSubjectService.get(Integer.parseInt(identifycId));
			if(idc != null){
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
				try {
					this.identifyClasshoursSubjectService.update(idc);
				} catch (Exception e) {
					e.printStackTrace();
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"操作异常\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
		
		IdentifyClasshoursSubject idc = this.identifyClasshoursSubjectService.get(Integer.parseInt(identifycId));
		if(idc == null){
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"删除项不存在\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		idc.setStatus((short)0);
		try {
			this.identifyClasshoursSubjectService.update(idc);
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

	
	/**
	 * 获取特殊学科培训分年份学时配置列表
	 * 查询参数：1、projectTypeId
	 * 		2、trainingSubjectId
	 */
	public void getSpecialYearList(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
//		System.out.println(us.getIdcard());
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
		String projectTypeId = request.getParameter("projectTypeId") == null ? "" : request.getParameter("projectTypeId");
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "" : request.getParameter("trainingSubjectId");
		String year = request.getParameter("year") == null ? "" : request.getParameter("year");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(projectTypeId)){
			params.put("projecttype", projectTypeId);
		}
		
		if(!"".equals(trainingSubjectId)){
			params.put("trainingsubject", trainingSubjectId);
		}
		
		if(!"".equals(year)){
			params.put("year", year);
		}
		
		params.put("status", 1);
		int count = this.identifyClasshoursSubjectYearService.getListCountByParams(params);
		int total = (int) Math.ceil((float) count / DictionyMap.maxPageSize);
		List<IdentifyClasshoursSubjectYear> lst = this.identifyClasshoursSubjectYearService.getListByParams(params, offset, limit, "createtime");
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
			for(IdentifyClasshoursSubjectYear idc : lst){
				ProjectType pt = idc.getProjecttype();
				TrainingSubject ts = idc.getTrainingsubject();
				sb.append("{");
				sb.append("\"id\":"+idc.getId());
				sb.append(",");
				sb.append("\"projectTypeId\":"+pt.getId());
				sb.append(",");
				sb.append("\"name\":\""+pt.getName()+"\"");
				sb.append(",");
				if(pt.getProjectType() != null){
					sb.append("\"parentType\":\""+pt.getProjectType().getName()+"\"");
					sb.append(",");
				}else{
					sb.append("\"parentType\":\"0\"");
					sb.append(",");
				}
				sb.append("\"trainingSubject\":\""+ts.getName()+"\"");
				sb.append(",");
				int credit = idc.getCredit();//学分
				String years = idc.getYear();
				sb.append("\"studyhour\":" + idc.getStudyhour());
				sb.append(",");
				sb.append("\"credit\":"+credit);
				sb.append(",");
				sb.append("\"year\":"+years);
				sb.append("},");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String initAddYearPage(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
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
//			int year = 1994;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}
		return "initAddYear";
	}
	
	
	/**
	 * 添加特殊学科学时认定
	 * method -- add/edit
	 * projectTypeId -- projectTypeId
	 * trainingSubjectId -- 学科ID
	 * id  -- identifycId
	 * centralizeCH -- 集中学时
	 * informationCH -- 信息技术学时
	 * regionalCH -- 区域特色学时
	 * schoolCH -- 校本培训学时
	 */
	public void specialYearAdd(){
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
		String projectTypeId = request.getParameter("projectTypeId") == null ? "0" : request.getParameter("projectTypeId");
		if("".equals(projectTypeId)){
			projectTypeId = "0";
		}
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "0" : request.getParameter("trainingSubjectId");
		if("".equals(trainingSubjectId)){
			trainingSubjectId = "0";
		}
		
		String year = request.getParameter("startyear") == null ? "0" : request.getParameter("startyear");
		if("".equals(year)){
			year = "0";
		}
		
		if("0".equals(year)){
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"请选择年份\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String identifycId = request.getParameter("id") == null ? "" : request.getParameter("id");
		
		ProjectCycle pc = this.projectCycleService.get(projectCycleId);
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
		String credit = request.getParameter("credit") == null ? "0" : request.getParameter("credit");
		if("".equals(credit)){
			credit = "0";
		}
		
		if(method.equals("add")){//添加
			if("0".equals(projectTypeId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"请选择项目类型\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if("0".equals(trainingSubjectId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"请选择培训学科\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
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
			
			TrainingSubject ts = this.iTrainingSubjectService.get(Short.parseShort(trainingSubjectId));
			if(ts == null){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"培训学科不存在,请刷新页面\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			IdentifyClasshoursSubjectYear idc = new IdentifyClasshoursSubjectYear();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("projecttype", pt.getId());
			params.put("trainingsubject", ts.getId());
//			params.put("status", 1);
			List<IdentifyClasshoursSubjectYear> lst = this.identifyClasshoursSubjectYearService.getListByParams(params, -1, -1, "id");
			if(lst != null && lst.size() > 0){//已存在更新
				idc =  lst.get(0);
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
				idc.setYear(year);
				idc.setStatus((short)1);
				
				try {
					this.identifyClasshoursSubjectYearService.update(idc);
					sb.append("{");
					sb.append("\"status\":\"OK\"");
					sb.append(",");
					sb.append("\"message\":\"操作成功\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
			
			idc.setTrainingsubject(ts);
			idc.setStudyhour(JSONUtils.obj2json(resultList));
			idc.setCredit(Integer.parseInt(credit));
			idc.setYear(year);
			idc.setCreator(us.getId());
			idc.setProjecttype(pt);
			idc.setStatus((short)1);
			
			try {
				idc = this.identifyClasshoursSubjectYearService.add(idc);
				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"message\":\"操作成功\"");
				sb.append(",");
				sb.append("\"id\":"+idc.getId());
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
			IdentifyClasshoursSubjectYear idc = this.identifyClasshoursSubjectYearService.get(Integer.parseInt(identifycId));
			if(idc != null){
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
				idc.setYear(year);
				try {
					this.identifyClasshoursSubjectYearService.update(idc);
				} catch (Exception e) {
					e.printStackTrace();
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"操作异常\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
	public void deleteYear(){
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
		
		IdentifyClasshoursSubjectYear idc = this.identifyClasshoursSubjectYearService.get(Integer.parseInt(identifycId));
		if(idc == null){
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"删除项不存在\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		idc.setStatus((short)0);
		try {
			this.identifyClasshoursSubjectYearService.update(idc);
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
	
	/**
	 * 获取特殊学科培训分年份学时配置列表
	 * 查询参数：1、projectTypeId
	 * 		2、trainingSubjectId
	 */
	public void getSpecialProjectYearList(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
//		System.out.println(us.getIdcard());
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
		String projectId = request.getParameter("projectId") == null ? "" : request.getParameter("projectId");
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "" : request.getParameter("trainingSubjectId");
		String year = request.getParameter("year") == null ? "" : request.getParameter("year");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(projectId)){
			params.put("project", projectId);
		}
		
		if(!"".equals(trainingSubjectId)){
			params.put("trainingsubject", trainingSubjectId);
		}
		
		if(!"".equals(year)){
			params.put("year", year);
		}
		
		params.put("status", 1);
		int count = this.identifyClasshoursProjectYearService.getListCountByParams(params);
		int total = (int) Math.ceil((float) count / DictionyMap.maxPageSize);
		List<IdentifyClasshoursProjectYear> lst = this.identifyClasshoursProjectYearService.getListByParams(params, offset, limit, "createtime");
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
			for(IdentifyClasshoursProjectYear idc : lst){
				Project pt = idc.getProject();
				TrainingSubject ts = idc.getTrainingsubject();
				sb.append("{");
				sb.append("\"id\":"+idc.getId());
				sb.append(",");
				sb.append("\"projectId\":"+pt.getId());
				sb.append(",");
				sb.append("\"name\":\""+pt.getName()+"\"");
				sb.append(",");
				if(pt.getProjectType() != null){
					sb.append("\"parentType\":\""+pt.getProjectType().getName()+"\"");
					sb.append(",");
				}else{
					sb.append("\"parentType\":\"0\"");
					sb.append(",");
				}
				if(ts != null){
					sb.append("\"trainingSubject\":\""+ts.getName()+"\"");
				}else{
					sb.append("\"trainingSubject\":\"\"");
				}
				
				sb.append(",");
				int credit = idc.getCredit();//学分
				String years = idc.getYear();
				sb.append("\"studyhour\":" + idc.getStudyhour());
				sb.append(",");
				sb.append("\"credit\":"+credit);
				sb.append(",");
				sb.append("\"year\":"+years);
				sb.append("},");
			}
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String initAddProjectYearPage(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
//		List<TrainingSubject> sList = this.iTrainingSubjectService.findAll();
//		List<teachingSubjectEx> lis = new ArrayList<teachingSubjectEx>();
//		for(TrainingSubject ts : sList){
//			teachingSubjectEx tse = new teachingSubjectEx();
//			tse.setId(ts.getId().toString());
//			tse.setName(ts.getName());
//			tse.setSearchString(ts.getName() + pinyingUtil.getFirstSpell(ts.getName()));
//			lis.add(tse);
//		}
//		this.subjectList = lis;
//		this.subjectList = lis;
		if (yearArray == null) {
			int year = 2014;
//			int year = 1994;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}
		return "initAddProjectYear";
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
	 * 添加特殊学科学时认定
	 * method -- add/edit
	 * projectId -- projectId
	 * trainingSubjectId -- 学科ID
	 * id  -- identifycId
	 * centralizeCH -- 集中学时
	 * informationCH -- 信息技术学时
	 * regionalCH -- 区域特色学时
	 * schoolCH -- 校本培训学时
	 */
	public void specialProjectYearAdd(){
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
		String projectId = request.getParameter("projectId") == null ? "0" : request.getParameter("projectId");
		if("".equals(projectId)){
			projectId = "0";
		}
		String trainingSubjectId = request.getParameter("trainingSubjectId") == null ? "0" : request.getParameter("trainingSubjectId");
		if("".equals(trainingSubjectId)){
			trainingSubjectId = "0";
		}
		
		String year = request.getParameter("year") == null ? "0" : request.getParameter("year");
		if("".equals(year)){
			year = "0";
		}
		
		
		String identifycId = request.getParameter("id") == null ? "" : request.getParameter("id");
		
		ProjectCycle pc = this.projectCycleService.get(projectCycleId);
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
		String credit = request.getParameter("credit") == null ? "0" : request.getParameter("credit");
		if("".equals(credit)){
			credit = "0";
		}
		
		if(method.equals("add")){//添加
			
			if("0".equals(year)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"请选择年份\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			if("0".equals(projectId)){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"请选择培训项目\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			Project pt = this.iProjectService.get(Integer.parseInt(projectId));
			if(pt == null){
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"培训项目不存在,请刷新页面\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
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
				}else{
					params.put("trainingsubject", ts.getId());
				}
			}else{
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"请选择培训学科\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			IdentifyClasshoursProjectYear idc = new IdentifyClasshoursProjectYear();
			
			
			params.put("project", pt.getId());
			
//			params.put("status", 1);
			List<IdentifyClasshoursProjectYear> lst = this.identifyClasshoursProjectYearService.getListByParams(params, -1, -1, "id");
			if(lst != null && lst.size() > 0){//已存在更新
				idc =  lst.get(0);
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
//				idc.setYear(year);
				idc.setStatus((short)1);
				
				try {
					this.identifyClasshoursProjectYearService.update(idc);
					sb.append("{");
					sb.append("\"status\":\"OK\"");
					sb.append(",");
					sb.append("\"message\":\"操作成功\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
			
			idc.setTrainingsubject(ts);
			idc.setStudyhour(JSONUtils.obj2json(resultList));
			idc.setCredit(Integer.parseInt(credit));
			idc.setYear(year);
			idc.setCreator(us.getId());
			idc.setProject(pt);
			idc.setStatus((short)1);
			
			try {
				idc = this.identifyClasshoursProjectYearService.add(idc);
				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"message\":\"操作成功\"");
				sb.append(",");
				sb.append("\"id\":"+idc.getId());
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
			IdentifyClasshoursProjectYear idc = this.identifyClasshoursProjectYearService.get(Integer.parseInt(identifycId));
			if(idc != null){
				idc.setStudyhour(JSONUtils.obj2json(resultList));
				idc.setCredit(Integer.parseInt(credit));
//				idc.setYear(year);
				try {
					this.identifyClasshoursProjectYearService.update(idc);
				} catch (Exception e) {
					e.printStackTrace();
					sb.append("{");
					sb.append("\"status\":\"ERROR\"");
					sb.append(",");
					sb.append("\"message\":\"操作异常\"");
					sb.append(",");
					sb.append("\"id\":"+idc.getId());
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
	public void deleteProjectYear(){
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
		
		IdentifyClasshoursProjectYear idc = this.identifyClasshoursProjectYearService.get(Integer.parseInt(identifycId));
		if(idc == null){
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"删除项不存在\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		idc.setStatus((short)0);
		try {
			this.identifyClasshoursProjectYearService.update(idc);
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

	public IIdentifyClasshoursSubjectYearService getIdentifyClasshoursSubjectYearService() {
		return identifyClasshoursSubjectYearService;
	}

	public void setIdentifyClasshoursSubjectYearService(
			IIdentifyClasshoursSubjectYearService identifyClasshoursSubjectYearService) {
		this.identifyClasshoursSubjectYearService = identifyClasshoursSubjectYearService;
	}

	public String[] getYearArray() {
		return yearArray;
	}

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
	}

	public IIdentifyClasshoursProjectYearService getIdentifyClasshoursProjectYearService() {
		return identifyClasshoursProjectYearService;
	}

	public void setIdentifyClasshoursProjectYearService(
			IIdentifyClasshoursProjectYearService identifyClasshoursProjectYearService) {
		this.identifyClasshoursProjectYearService = identifyClasshoursProjectYearService;
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
