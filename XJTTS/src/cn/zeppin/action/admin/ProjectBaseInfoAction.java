package cn.zeppin.action.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectGroup;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IAreaService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectGroupService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IProjectTypeService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings({"unchecked","unused","rawtypes"})
public class ProjectBaseInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectBaseInfoAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectService iProjectService;
	private IProjectTypeService iProjectTypeService;
	private IProjectApplyService iProjectApplyService;

	private IAreaService iAreaService;
	private IProjectAdminService iProjectAdminService;
	private IProjectGroupService iProjectGroupService;

	private LinkedHashMap<Integer, String[]> projectHash;
	private LinkedHashMap<String, LinkedHashMap<String, Object>> chashMap;
	private ArrayList<String[]> projectApplyList;
	private ArrayList<String[]> subject;
	private ArrayList<String[]> training;
	private ArrayList<String[]> value;
	private List<Project> searchReportTask;
	private List<ProjectType> searchProjectType;
	private List<String> searchYear;
	private Integer selectProjectType;
	private String selectYear;
	private String selectStatus;
	private List<ProjectGroup> lstGroup;
	private Project stageProject;
	private List<ProjectApply> lstProjectApply = new ArrayList<ProjectApply>();
	private int isAdd;
	private int isCheck;

	public ProjectBaseInfoAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * @category 列表
	 */
	public String initPage() {

		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.isCheck = 1;
		this.isAdd = 1;
		if(us != null && us.getOrganizationLevel() > 1){
			this.isAdd = 0;
			ProjectAdmin pa = this.iProjectAdminService.get(us.getId());
			Organization org = pa.getOrganization();
			if(org.getIsschool()){
				this.isCheck = 0;
			}else{
				this.isCheck = 1;
			}
		}
		
		// 起始页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 搜索参数
		Map<String, Object> params = new HashMap<>();
		Map<String, String> sortParams = new HashMap<>();
		
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		String year = request.getParameter("year");
		String status = request.getParameter("status");
		this.selectYear = year;
		this.selectStatus = status;
		if(request.getParameter("projectType")!=null && !request.getParameter("projectType").equals("0")){
			Integer projectType = Integer.valueOf(request.getParameter("projectType"));
			this.selectProjectType = projectType;
			ProjectType pt = this.iProjectTypeService.get(projectType);
			params.put("projectType.scode", pt.getScode());
		}
		
		this.searchYear = this.iProjectService.getProjectYearList();
		Map<String, Object> paramss = new HashMap<>();
		paramss.put("level", us.getOrganizationLevel());
		this.searchProjectType = this.iProjectTypeService.getProjectTypeList(lityps,paramss);
		for(ProjectType pt : this.searchProjectType){
			if(pt.getLevel() >1){
				String space = "";
				for(int i=0;i<pt.getLevel();i++){
					space += "--";
				}
				pt.setName(space + pt.getName());
			}
		}
		if(year != null  && !year.equals("") && !year.equals("0")){
			params.put("year", year);
		}
		if(status != null  && !status.equals("") && !status.equals("0")){
			params.put("status", Integer.valueOf(status));
		}
		// 判断其他搜索字段是否存在
		String q = request.getParameter("q");
		q = q == null ? "" : q;
		String stype = request.getParameter("stype");
		stype = stype == null ? "" : stype;
		if(!stype.equals("") && !q.equals("")){
			params.put(stype, q);
		}
		
		// 排序
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];

			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("id", "DESC");
		}

		int offset = (start - 1) * DictionyMap.maxPageSize;

		params.put("organization", us.getOrganization());
		params.put("level", us.getOrganizationLevel());
		List li = this.iProjectService.getProjectListByParams(params, sortParams, lityps, offset, DictionyMap.maxPageSize);
		this.projectHash = new LinkedHashMap<Integer, String[]>();
		DecimalFormat df1 = new DecimalFormat("0.00");
		for(int i = 0; i < li.size(); i++){
			Object[] obj = (Object[])li.get(i);
			Project pro = (Project)obj[0];
			
//		for (Project pro : li) {
			int id = pro.getId();
			if (!this.projectHash.containsKey(id)) {
				String[] sv = new String[30];
				sv[0] = pro.getName();
//				switch (pro.getStatus()) {
//				case 1:
//					sv[1] = "未发布";
//					break;
//				case 2:
//					sv[1] = "发布";
//					break;
//				case 3:
//					sv[1] = "完成";
//					break;
//				case 4:
//					sv[1] = "关闭";
//					break;
//				}

				sv[1] = pro.getStatus()+"";
				sv[2] = pro.getYear();

				ProjectType pt = pro.getProjectType();

				sv[3] = getNaviString(pt);
				sv[4] = pro.getShortname();
				sv[5] = getAreaNaviString(pt.getArea());
				sv[6] = Utlity.timeSpanToDateString(pro.getTimeup());
				sv[7] = df1.format(pro.getFunds());
				sv[8] = pro.getNumber().toString();
				sv[9] = pro.getSubjectMax().toString();

				sv[10] = pro.getRestrictCollege() ? "邀标" : "不限";
				sv[11] = pro.getRestrictSubject() ? "限制" : "不限";
				if(pro.getTraintype()==1){
					sv[12]="集中面授（同步在线）";
				}else if(pro.getTraintype()==2){
					sv[12]="远程培训";
				}else{
					sv[12]="混合培训方式";
				}
				sv[13] = pt.getProjectLevel().getName();
				sv[14] = pro.getDocument() == null ? "未上传" : "已上传";
				sv[15] = pro.getPsqByProjectJudgePsq() == null ? "未设定" : "已设定";
				ProjectAdmin pa = this.iProjectAdminService.get(pro.getCreator());
				sv[16] = pa.getName();
				sv[17] = Utlity.timeSpanToDateString(pro.getCreattime());
				sv[18] = pro.getEnrollType().toString();
				sv[19] = pro.getRedHeadDocument() == null ? "未上传" : "已上传";
				if(pro.getTraintype()==1 || pro.getTraintype()==3){
//					sv[20] = pro.getFundsType() == 1 ? " 元/人/天" : " 元/人/年";
					if(pro.getFundsType() == 1) {
						sv[20] = " 元/人/天";
					} else if (pro.getFundsType() == 2) {
						sv[20] = " 元/人/年";
					} else if (pro.getFundsType() == 4) {
						sv[20] = " 元/县";
					} else if (pro.getFundsType() == 5) {
						sv[20] = " 元/校";
					} else {
						sv[20] = " 元/人/学时";
					}
				}else{
					sv[20] = " 元/人/学时";
				}
				sv[21] = pro.getIndex().toString();
				this.projectHash.put(pro.getId(), sv);
			}
		}
		if(request.getParameter("pageType")!=null&&request.getParameter("pageType").equals("easy")){
			return "initEasy";
		}else{
			return "init";
		}
	}
	
	/**
	 * 获取项目类型信息列表（树状结构）
	 */
	public void getProjectTypeTreeList(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		Map<String, Object> paramss = new HashMap<>();
		paramss.put("level", us.getOrganizationLevel());
		paramss.put("pid", "0");
		this.searchProjectType = this.iProjectTypeService.getProjectTypeList(lityps,paramss);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"OK\"");
		sb.append(",");
		sb.append("\"records\":[");
		for(ProjectType pt : this.searchProjectType){
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
		}
		sb.append("},");
		return sb.toString();
//		}
	}

	public void getPageJson() {
		initServlert();

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);

		// 判断其他搜索字段是否存在
		Map<String, Object> params = new HashMap<>();
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		String year = request.getParameter("year");
		if(year != null  && !year.equals("") && !year.equals("0")){
			params.put("year", year);
		}
		String projectType = request.getParameter("projectType");
		if(projectType != null  && !projectType.equals("") && !projectType.equals("0")){
			params.put("projectType", Integer.valueOf(projectType));
		}
		String status = request.getParameter("status");
		if(status != null  && !status.equals("") && !status.equals("0")){
			params.put("status", Integer.valueOf(status));
		}
		
		// 判断其他搜索字段是否存在
		String q = request.getParameter("q");
		q = q == null ? "" : q;
		String stype = request.getParameter("stype");
		stype = stype == null ? "" : stype;
		if(!stype.equals("") && !q.equals("")){
			try {
				q = URLDecoder.decode(q, "utf-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params.put(stype, q);
		}
		
		UserSession us = (UserSession) session.getAttribute("usersession");
		params.put("organization", us.getOrganization());
		params.put("level", us.getOrganizationLevel());
		int records = this.iProjectService.getProjectListCountByParams(params, lityps);
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");
		sb.append("\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + total);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 获取不同状态条目数
	 */
	public void getStatusCount() {
		initServlert();

//		String ist = (String) request.getParameter("page");
//		if (ist == null || ist.equals("") || ist.equals("NaN")) {
//			ist = "1";
//		}
//
//		int start = Integer.parseInt(ist);

		// 判断其他搜索字段是否存在
		Map<String, Object> params = new HashMap<>();
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		String year = request.getParameter("year");
		if(year != null  && !year.equals("") && !year.equals("0")){
			params.put("year", year);
		}
		String projectType = request.getParameter("projectType");
		if(projectType != null  && !projectType.equals("") && !projectType.equals("0")){
			params.put("projectType", Integer.valueOf(projectType));
		}
//		String status = request.getParameter("status");
//		if(status != null  && !status.equals("") && !status.equals("0")){
//			params.put("status", Integer.valueOf(status));
//		}
		// 判断其他搜索字段是否存在
		String q = request.getParameter("q");
		q = q == null ? "" : q;
		String stype = request.getParameter("stype");
		stype = stype == null ? "" : stype;
		if(!stype.equals("") && !q.equals("")){
			try {
				q = URLDecoder.decode(q, "utf-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params.put(stype, q);
		}
		UserSession us = (UserSession) session.getAttribute("usersession");
		params.put("organization", us.getOrganization());
		params.put("level", us.getOrganizationLevel());
		
		int records = this.iProjectService.getProjectListCountByParams(params, lityps);
		params.put("status", 1);
		int noCheck = this.iProjectService.getProjectListCountByParams(params, lityps);
		params.put("status", 2);
		int checkPass = this.iProjectService.getProjectListCountByParams(params, lityps);
		params.put("status", 4);
		int checkNoPass = this.iProjectService.getProjectListCountByParams(params, lityps);
		
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":" + (records) + ",");//全部
		sb.append("\"noCheck\":" + (noCheck) + ",");//未审核
		sb.append("\"checkPass\":" + (checkPass) + ",");//已通过
		sb.append("\"checkNoPass\":" + (checkNoPass));//未通过
		sb.append("}");
//		sb.append("{\"totalCount\":" + (records) + ",");
//		sb.append("\"currentPage\":" + (start) + ",");
//		sb.append("\"totalPage\":" + total);
//		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	public String analysis(){
		return "analysis";
	}
	
	public void analysisList(){
		initServlert();
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		List<String> years = this.iProjectService.getProjectYearList();
		List<ProjectType> projectTypes= this.iProjectTypeService.getProjectTypeList(lityps);
		for(ProjectType pt : projectTypes){
			if(pt.getLevel() >1){
				String space = "";
				for(int i=0;i<pt.getLevel();i++){
					space += "--";
				}
				pt.setName(space + pt.getName());
			}
		}
		List<Object[]> lio = this.iProjectService.getProjectAnalysis(lityps);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"OK\",");
		sb.append("\"year\":[");
		for(String year : years){
			sb.append(year).append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append("],\"projectType\":[");
		for(ProjectType pt : projectTypes){
			sb.append("{\"id\":").append(pt.getId());
			sb.append(",\"name\":\"").append(pt.getName());
			sb.append("\"},");
		}
		sb.setLength(sb.length() - 1);
		sb.append("],\"value\":{");
		for(Object[] obj : lio){
			sb.append("\"").append(obj[0]).append("_").append(obj[1]).append("\":");
			sb.append("[\"").append(obj[2]).append("\",\"").append(obj[3]).append("\"],");
		}
		sb.setLength(sb.length() - 1);
		sb.append("}}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public void output() throws IOException{
		initServlert();
		
		Map<String, Object> params = new HashMap<>();
		
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		
		String year = request.getParameter("year");
		if(year != null  && !year.equals("") && !year.equals("0")){
			params.put("year", year);
		}
		UserSession us = (UserSession) session.getAttribute("usersession");
		params.put("organization", us.getOrganization());
		params.put("level", us.getOrganizationLevel());
		
		List li = this.iProjectService.getProjectListByParams(params, null, lityps, -1, -1);
		DecimalFormat df1 = new DecimalFormat("0.00");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "项目列表");
		HSSFRow row = s.createRow(0);
		String title[] = {"开设年份", "项目类型", "项目名称", "项目简称", "所属地区", "申报截止日期", "经费标准", "计划培训人数",
				"最大申报学科", "招标类型", "学科范围", "培训方式", "项目级别", "红头文件", "申报模板", "评审指标", " 创建人", "创建时间"};
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t=0;
		for(int i = 0; i < li.size(); i++){
			Object[] obj = (Object[])li.get(i);
			Project pro = (Project)obj[0];
			t++;
			row = s.createRow(t);
			String[] sv = new String[20];
			sv[0] = pro.getYear();
			ProjectType pt = pro.getProjectType();
			sv[1] = getNaviString(pt);
			sv[2] = pro.getName();
			sv[3] = pro.getShortname();
			sv[4] = getAreaNaviString(pt.getArea());
			sv[5] = Utlity.timeSpanToDateString(pro.getTimeup());
			String fundsType = "";
			if(pro.getTraintype()==1 || pro.getTraintype()==3){
//				fundsType = pro.getFundsType()==1? " 元/人/天":" 元/人/年";
				if(pro.getFundsType() == 1) {
					fundsType = " 元/人/天";
				} else if (pro.getFundsType() == 2) {
					fundsType = " 元/人/年";
				} else if (pro.getFundsType() == 4) {
					sv[20] = " 元/县";
				} else if (pro.getFundsType() == 5) {
					sv[20] = " 元/校";
				} else {
					fundsType = " 元/人/学时";
				}
			}else{
				fundsType = "元/人/学时";
			}
			sv[6] = df1.format(pro.getFunds()) + fundsType;
			sv[7] = pro.getNumber().toString();
			sv[8] = pro.getSubjectMax().toString();

			sv[9] = pro.getRestrictCollege() ? "邀标" : "不限";
			sv[10] = pro.getRestrictSubject() ? "限制" : "不限";
			if(pro.getTraintype()==1){
				sv[11]="集中面授（同步在线）";
			}else if(pro.getTraintype()==2){
				sv[11]="远程培训";
			}else{
				sv[11]="混合培训方式";
			}
			sv[12] = pt.getProjectLevel().getName();
			sv[13] = pro.getRedHeadDocument() == null ? "未上传" : "已上传";
			sv[14] = pro.getDocument() == null ? "未上传" : "已上传";
			sv[15] = pro.getPsqByProjectJudgePsq() == null ? "未设定" : "已设定";
			ProjectAdmin pa = this.iProjectAdminService.get(pro.getCreator());
			sv[16] = pa.getName();
			sv[17] = Utlity.timeSpanToDateString(pro.getCreattime());
			for (int j = 0; j < sv.length; j++) {
				row.createCell(j).setCellValue(sv[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=projectList.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}
	
	
	public void getPageJsonForTrainingCollege() {
		initServlert();

		String collegeid=(String) request.getParameter("id");
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);
		Date date = new Date();
		List li= this.iProjectService.getProjectForTrainingCollege(null,null,Integer.valueOf(collegeid),date,0,1000000);
		int records =li.size();
		int total = (int) Math.ceil((float) records / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + total);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String initProjectResult(){
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		String year = request.getParameter("year");
		
		this.searchYear = this.iProjectService.getProjectYearList();
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		Map<String,Object> map = new HashMap();
		if(year != null && !year.equals("") && !year.equals("0")){
			map.put("year", year);
		}
//		this.searchReportTask = this.iProjectService.getProjectByParams(map,null,lityps);
		map.put("organization", 26124);
		// map.put("level", us.getOrganizationLevel());
		this.searchReportTask = new ArrayList<Project>();
		List list = this.iProjectService.getProjectListByParams(map,
				null, lityps, -1, -1);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				Project p = (Project) obj[0];
				this.searchReportTask.add(p);
			}
		}
		
		return "presult";
	}
	
	public void projectResult() {
		initServlert();
		String projectId = request.getParameter("id");
		String error ="{\"status\":\"ERROR\"}";
		if (projectId != null && !projectId.equals("")) {
			
			List<ProjectApply> li = this.iProjectApplyService.getList(Integer.parseInt(projectId));
			if(li !=null && !li.isEmpty()){
				this.projectApplyList = new ArrayList<String[]>();
				this.subject=new ArrayList<String[]>(); 
				this.training=new ArrayList<String[]>();
				this.value=new ArrayList<String[]>();
				for (int i=0;i<li.size();i++) {
					String[] sv = new String[5];
					sv[0]=Integer.toString(li.get(i).getTrainingSubject().getId());
					sv[1]=li.get(i).getTrainingSubject().getName();
					sv[2]=Integer.toString(li.get(i).getTrainingCollege().getId());
					sv[3]=li.get(i).getTrainingCollege().getName();
					sv[4]=li.get(i).getApproveNumber()+"";													
					Boolean add_s = true,add_t = true;
					String[] sj={sv[0],sv[1]};
					String[] tc={sv[2],sv[3]};
					String[] vl={sv[0],sv[2],sv[4]};
					for(int j=0;j<subject.size();j++){
						if(sv[0].equals(subject.get(j)[0]))add_s=false;
					}					
					if(add_s)subject.add(sj);
					for(int k=0;k<training.size();k++){
						if(sv[2].equals(training.get(k)[0]))add_t=false;
					}					
					if(add_t)training.add(tc);
					value.add(vl);
				}		
				StringBuilder sb = new StringBuilder();
				sb.append("{\"status\":\"OK\",");
				sb.append("\"training\":[");
				for(int i=0;i<training.size();i++){
					if(i!=0)sb.append(",");
					sb.append("{\"id\":"+(training.get(i)[0])+",\"name\":\""+(training.get(i)[1])+"\"}");
				}
				sb.append("],\"subject\":[");
				for(int i=0;i<subject.size();i++){
					if(i!=0)sb.append(",");
					sb.append("{\"id\":"+(subject.get(i)[0])+",\"name\":\""+(subject.get(i)[1])+"\"}");
				}
				sb.append("],\"value\":{");
				for(int i=0;i<value.size();i++){
					if(i!=0)sb.append(",");
					sb.append("\""+(value.get(i)[0])+"_"+(value.get(i)[1])+"\":\""+(value.get(i)[2])+"\"");
				}
				sb.append("}}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);

			}else{
				Utlity.ResponseWrite(error, "application/json", response);
			}
		}else{
			Utlity.ResponseWrite(error, "application/json", response);
		}
	}

	public String initProjectGroupResult(){
		initServlert();

		// 查询出所有已经发布的项目
		UserSession us = (UserSession) session.getAttribute("usersession");
		String year = request.getParameter("year");
		
		this.searchYear = this.iProjectService.getProjectYearList();
		this.lstGroup = this.iProjectGroupService.getList();
		
		return "pgresult";
	}
	
	public void projectGroupResult() {
		initServlert();
		String projectGroupId = request.getParameter("id");
		String error ="{\"status\":\"ERROR\"}";
		if (projectGroupId != null && !projectGroupId.equals("")) {
			
			/*
			 * 增加多阶段项目查询成组显示功能
			 */
			ProjectGroup pg = this.iProjectGroupService.get(Integer.parseInt(projectGroupId));
			List<Project> lstPro = this.iProjectService.getProjectByGroup(pg.getId());
			if(lstPro != null && !lstPro.isEmpty()){
				StringBuilder sb = new StringBuilder();
				sb.append("{\"status\":\"OK\",");
				sb.append("\"records\":[");
				for(Project pro : lstPro){
					List<ProjectApply> li = this.iProjectApplyService.getList(pro.getId());
					if ( li !=null && !li.isEmpty() ) {
						this.projectApplyList = new ArrayList<String[]>();
						this.subject=new ArrayList<String[]>(); 
						this.training=new ArrayList<String[]>();
						this.value=new ArrayList<String[]>();
						for (int i=0;i<li.size();i++) {
							String[] sv = new String[5];
							sv[0]=Integer.toString(li.get(i).getTrainingSubject().getId());
							sv[1]=li.get(i).getTrainingSubject().getName();
							sv[2]=Integer.toString(li.get(i).getTrainingCollege().getId());
							sv[3]=li.get(i).getTrainingCollege().getName();
							sv[4]=li.get(i).getApproveNumber()+"";													
							Boolean add_s = true,add_t = true;
							String[] sj={sv[0],sv[1]};
							String[] tc={sv[2],sv[3]};
							String[] vl={sv[0],sv[2],sv[4]};
							for(int j=0;j<subject.size();j++){
								if(sv[0].equals(subject.get(j)[0]))add_s=false;
							}					
							if(add_s)subject.add(sj);
							for(int k=0;k<training.size();k++){
								if(sv[2].equals(training.get(k)[0]))add_t=false;
							}					
							if(add_t)training.add(tc);
							value.add(vl);
						}
						sb.append("{");
						sb.append("\"training\":[");
						for(int i=0;i<training.size();i++){
							if(i!=0)sb.append(",");
							sb.append("{\"id\":"+(training.get(i)[0])+",\"name\":\""+(training.get(i)[1])+"\"}");
						}
						sb.append("],\"subject\":[");
						for(int i=0;i<subject.size();i++){
							if(i!=0)sb.append(",");
							sb.append("{\"id\":"+(subject.get(i)[0])+",\"name\":\""+(subject.get(i)[1])+"\"}");
						}
						sb.append("],\"value\":{");
						for(int i=0;i<value.size();i++){
							if(i!=0)sb.append(",");
							sb.append("\""+(value.get(i)[0])+"_"+(value.get(i)[1])+"\":\""+(value.get(i)[2])+"\"");
						}
						sb.append("}},");
						
					} else {
						Utlity.ResponseWrite(error, "application/json", response);
						return;
					}
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else{
				Utlity.ResponseWrite(error, "application/json", response);
			}
			
		}else{
			Utlity.ResponseWrite(error, "application/json", response);
		}
	}
	
	public void deleteProject() {
		initServlert();
		String id = this.request.getParameter("id");
		if (id != null && !id.equals("")) {
			Project pro = this.iProjectService.get(Integer.valueOf(id));
			if (pro != null) {
				try {

					// TODO 可能根据当前用户与要删除的用户进行 工作单位，也就是组织架构的从属关系，是否能删除，

					if (pro.getProjectApplies().size() > 0) {
						sendResponse("ERROR", "删除失败,存在项目申报信息");
					}else if(pro.getAssignTeacherTasks().size()>0){
						sendResponse("ERROR", "删除失败,存在项目名额分配任务信息");
					}
					else if(pro.getTeacherTrainingRecordses().size()>0){
						sendResponse("ERROR", "删除失败,存在项目学员培训记录信息");
					}
					else {
						this.iProjectService.delete(pro);
						sendResponse("OK", "删除成功");
					}

				} catch (Exception ex) {
					sendResponse("ERROR", "删除失败,存在项目申报信息");
				}
			} else {
				sendResponse("ERROR", "删除失败，不存在项目信息");
			}

		} else {
			sendResponse("ERROR", "删除失败，不存在项目信息");
		}
	}

	public void changeStatus() {
		initServlert();

		String id = this.request.getParameter("id");
		String method = this.request.getParameter("method");
		if (id != null && !id.equals("")) {
			Project pro = this.iProjectService.get(Integer.valueOf(id));
			if (pro != null) {
				try {
					if (method.equals("release")) {
						// 发布
						pro.setStatus((short) 2);
						this.iProjectService.update(pro);
						sendResponse("OK", "发布成功");

					} else if (method.equals("close")) {
						// 关闭
						pro.setStatus((short) 4);
						this.iProjectService.update(pro);
						sendResponse("OK", "关闭成功");
					}

				} catch (Exception ex) {
					sendResponse("ERROR", "删除失败,存在项目申报信息");
				}
			} else {
				sendResponse("ERROR", "删除失败，不存在项目信息");
			}

		} else {
			sendResponse("ERROR", "删除失败，不存在项目信息");
		}
	}

	/**
	 * 根据参数获取项目信息 返回json id和名称
	 */
	public void getProjectsByPramers() {
		initServlert();
		String year = request.getParameter("year");
		String projectType = request.getParameter("projectType");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DictionyMap.releaseProject);

		if (year != null && !year.equals("")) {
			params.put("year", year);
		}
		if (projectType != null && !projectType.equals("")) {
			params.put("projectType", Integer.valueOf(projectType));
		}
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		List<Project> li = this.iProjectService.getProjectByParams(params, null, lityps);
		
		Map<String, String> sortParams = new HashMap<String, String>();
		sortParams.put("id", "desc");
		UserSession us = (UserSession) session.getAttribute("usersession");
		params.put("organization", us.getOrganization());
		params.put("level", us.getOrganizationLevel());
		List li = this.iProjectService.getProjectListByParams4LevelThree(params, sortParams, lityps, -1, -1);

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("{\"id\":0,\"name\":\"请选择\"},");
		for(int i = 0; i < li.size(); i++){
			Object[] obj = (Object[])li.get(i);
			Project p = (Project)obj[0];
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
//		if (li.size() > 0) {
		sb.delete(sb.length() - 1, sb.length());
//		}
		sb.append("]");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}
	
	/**
	 * 根据参数获取项目信息 返回json id和名称(多阶段项目专用)
	 */
	public void getProjectsByPramer() {
		initServlert();
		String year = request.getParameter("year");
		String projectType = request.getParameter("projectType");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", DictionyMap.releaseProject);

		if (year != null && !year.equals("")) {
			params.put("year", year);
		}
		if (projectType != null && !projectType.equals("")) {
			params.put("projectType", Integer.valueOf(projectType));
		}
		params.put("projectgroup", 0);
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		List<Project> li = this.iProjectService.getProjectByParams(params, null, lityps);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("{\"id\":0,\"name\":\"请选择\"},");
		for (Project p : li) {
			String sr = "{\"id\":" + p.getId() + ",\"name\":\"" + p.getName() + "\"},";
			sb.append(sr);
		}
		if (li.size() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	// 项目类型
	public String getNaviString(ProjectType pt) {
		if (pt.getProjectType() == null) {
			return pt.getName();
		} else {
			return getNaviString(pt.getProjectType()) + " > " + pt.getName();
		}
	}

	public String getAreaNaviString(Area a) {
		String areaid = a.getName();
		String areaid1 = "";
		String areaid2 = "";
		String areacode = a.getParentcode();
		if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
			// 当前的地区的父地区存在
			Area pae = this.iAreaService.getAreaByCode(areacode);
			areaid1 = pae.getName();
			areacode = pae.getParentcode();
			// 父父地区
			if (areacode != null && !areacode.equals("0") && !areacode.equals("")) {
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
	 * 添加多阶段项目页面初始化
	 * 需要初始化year
	 * 通过year 拿到projectType
	 * 通过projectType拿到project
	 * 通过project拿到trainingSubject
	 * 
	 * @return
	 */
	public String addStageProjectInit(){
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		this.searchYear = new ArrayList<String>();
		this.searchYear.add("请选择");
		List<String> lstYear = this.iProjectService.getProjectYearList();
		this.searchYear.addAll(lstYear);
		
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
//		this.searchProjectType = this.iProjectTypeService.getProjectTypeList(lityps);
		Map<String, Object> paramss = new HashMap<>();
		paramss.put("level", us.getOrganizationLevel());
		this.searchProjectType = this.iProjectTypeService.getProjectTypeList(lityps,paramss);
		
		return "stageproject";
		
	}
	
	/**
	 * 通过项目获取相关学科  and 承训院校信息
	 */
	public void getprojectApply(){
		initServlert();
		
		StringBuilder sb = new StringBuilder();
		String projectId = request.getParameter("projectId") == null ? "0" : request.getParameter("projectId");
		
		if(Integer.parseInt(projectId) > 0){
			List<ProjectApply> lstProjectApplys = this.iProjectApplyService.getCheckedProjectApply(Integer.parseInt(projectId));
			
			if(lstProjectApplys != null && lstProjectApplys.size() > 0){
				sb.append("{");
				sb.append("\"status\":\"OK\"");
				sb.append(",");
				sb.append("\"records\":[");
				for(ProjectApply pa : lstProjectApplys){
					sb.append("{\"id\":"+pa.getId());
					sb.append(",");
					
					String paStr = getProjectApplyString(pa);
					sb.append("\"paStr\":\""+paStr+"\"");
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]");
				sb.append("}");
				
			}else{
				sb.append("{");
				sb.append("\"status\":\"ERROR\"");
				sb.append(",");
				sb.append("\"message\":\"暂无该项目中标信息\"");
				sb.append("}");
			}
		}else{
			sb.append("{");
			sb.append("\"status\":\"ERROR\"");
			sb.append(",");
			sb.append("\"message\":\"未选择项目\"");
			sb.append("}");
		}
//		this.iProjectApplyService.getCheckedProjectApply(projectId);
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
	}
	
//	public String getProjectApplyIdString(ProjectApply pa){
//		String paIdStr = "";
//		paIdStr+=pa.getId()+"-"+pa.getProject().getId();
//		return paIdStr;
//	}
	
	public String getProjectApplyString(ProjectApply pa){
		String paStr = "";
		paStr+=pa.getTrainingSubject().getName() + " >> " + pa.getTrainingCollege().getName();
		return paStr;
	}
	
	/**
	 * 新增多阶段项目
	 * 获取页面传递的相关信息：
	 * projectId
	 * subjectId[]--trainingCollegeId[] 即一组projectApplyId
	 * 获取到相关的project、projectApply、teachertrainingrecords、学院审核记录、名额分配任务记录等所有被继承项目相关信息
	 * New 一个新的project 按照先后顺序依次入库相关信息
	 * 注：入库操作在Service中完成
	 */
	public void addStageProject(){
		initServlert();
		
		String projectId = request.getParameter("project") == null? "0" : request.getParameter("project");
		String method = request.getParameter("method") == null? "" : request.getParameter("method");
		String nextProjectId = request.getParameter("nextproject") == null? "0" : request.getParameter("nextproject");
		Map<String, Object> params = new HashMap<String, Object>();
		
		Project project = null;
		if(Integer.parseInt(projectId) > 0){
			project = this.iProjectService.get(Integer.parseInt(projectId));
		}
		Project nextProject = null;
		if(Integer.parseInt(nextProjectId) > 0){
			nextProject = this.iProjectService.get(Integer.parseInt(nextProjectId));
			params.put("nextProject", nextProject);
		}
		
		String[] ids = request.getParameterValues("projectApplys");
		
		List<ProjectApply> lstProjectApplys = new ArrayList<ProjectApply>();
		if(ids != null && ids.length > 0){
			
			for(int i = 0; i < ids.length; i++){
				Integer projectApplyId = Integer.parseInt(ids[i]);
				ProjectApply pa = this.iProjectApplyService.get(projectApplyId);
				lstProjectApplys.add(pa);
			}
		}
		params.put("method", method);
		String status = this.iProjectGroupService.addStageProject(project, lstProjectApplys, params);
		StringBuilder sb = new StringBuilder();
		if("OK".equals(status)){
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Message\":\"操作成功\"");
			sb.append("}");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"操作失败,"+status+"\"");
			sb.append("}");
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String updateStageProjectInit(){
		initServlert();
		String projectId = request.getParameter("id") == null? "0" : request.getParameter("id");
		
		Project project = null;
		List<ProjectApply> lstProjectApplys = new ArrayList<ProjectApply>();
		List<ProjectApply> lstStageProjectApplys = new ArrayList<ProjectApply>();
		if(Integer.parseInt(projectId) > 0){
			project = this.iProjectService.get(Integer.parseInt(projectId));
			lstProjectApplys = this.iProjectApplyService.getCheckedProjectApply(Integer.parseInt(projectId));
		}
		
		this.searchReportTask = new ArrayList<Project>();
		this.searchReportTask.add(project);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectGroup", project.getProjectGroup().getId());
		params.put("index", project.getIndex()+1);
		List<Project> li = this.iProjectService.getProjectByParams(params, null, null);
		if(li != null && li.size() > 0){
			this.stageProject = li.get(0);
			lstStageProjectApplys = this.iProjectApplyService.getCheckedProjectApply(li.get(0).getId());
			//去掉重复的学科和承训单位
			if(lstStageProjectApplys.size() > 0){
				for(ProjectApply pa : lstProjectApplys){
					boolean flag = true;
					for(ProjectApply stagepa : lstStageProjectApplys){
						if((pa.getTrainingCollege() == stagepa.getTrainingCollege()) && (pa.getTrainingSubject() == stagepa.getTrainingSubject())){
							flag = false;
							break;
						}
					}
					if(flag == true){
						this.lstProjectApply.add(pa);
					}
				}
			}
		} else {
			this.lstProjectApply.addAll(lstProjectApplys);
		}
		
		
		
		return "updateStageProject";
	}
//	/**
//	 * 获取跨年项目列表
//	 */
//	public void addYearProject(){
//		initServlert();
//		
//	}
	
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IProjectTypeService getiProjectTypeService() {
		return iProjectTypeService;
	}

	public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
		this.iProjectTypeService = iProjectTypeService;
	}

	public LinkedHashMap<Integer, String[]> getProjectHash() {
		return projectHash;
	}

	public void setProjectHash(LinkedHashMap<Integer, String[]> projectHash) {
		this.projectHash = projectHash;
	}

	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}

	public IAreaService getiAreaService() {
		return iAreaService;
	}

	public void setiAreaService(IAreaService iAreaService) {
		this.iAreaService = iAreaService;
	}
	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
	}

	public List<ProjectType> getSearchProjectType() {
		return searchProjectType;
	}

	public void setSearchProjectType(List<ProjectType> searchProjectType) {
		this.searchProjectType = searchProjectType;
	}
	
	public Integer getSelectProjectType() {
		return selectProjectType;
	}

	public void setSelectProjectType(Integer selectProjectType) {
		this.selectProjectType = selectProjectType;
	}
	
	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}
	
	public String getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(String selectYear) {
		this.selectYear = selectYear;
	}

	public String getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}
	
	public IProjectGroupService getiProjectGroupService() {
		return iProjectGroupService;
	}

	public void setiProjectGroupService(IProjectGroupService iProjectGroupService) {
		this.iProjectGroupService = iProjectGroupService;
	}

	public List<ProjectGroup> getLstGroup() {
		return lstGroup;
	}

	public void setLstGroup(List<ProjectGroup> lstGroup) {
		this.lstGroup = lstGroup;
	}

	
	public Project getStageProject() {
		return stageProject;
	}
	

	public void setStageProject(Project stageProject) {
		this.stageProject = stageProject;
	}

	public List<ProjectApply> getLstProjectApply() {
		return lstProjectApply;
	}

	public void setLstProjectApply(List<ProjectApply> lstProjectApply) {
		this.lstProjectApply = lstProjectApply;
	}

	
	public int getIsAdd() {
		return isAdd;
	}
	

	public void setIsAdd(int isAdd) {
		this.isAdd = isAdd;
	}
	

	public int getIsCheck() {
		return isCheck;
	}
	

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	
}
