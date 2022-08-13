package cn.zeppin.action.admin;

import java.sql.Timestamp;
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
import cn.zeppin.entity.ProjectCycle;
import cn.zeppin.service.IProjectCycleService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("deprecation")
/**
 * @author Administrator
 * @category 项目周期概念
 */
public class ProjectCycleAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectCycleAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectCycleService projectCycleService;
//	private IProjectCycleClasshoursService projectCycleClasshoursService;
	
	private String[] yearArray;

	public ProjectCycleAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String initPage(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
//		System.out.println(us.getIdcard());
		if (yearArray == null) {
			int year = 1994;
			yearArray = new String[50];
			yearArray[0] = (year - 1) + "";
			yearArray[1] = year + "";
			for (int i = 2; i < yearArray.length; i++) {
				yearArray[i] = (year + i - 1) + "";
			}
		}
		return "init";
	}
	
	/**
	 * 获取年份列表
	 */
	public void getYearList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		String startyear = request.getParameter("startyear") == null ? "":request.getParameter("startyear");
		if("".equals(startyear)){
			if (yearArray == null) {
//				int year = new Date().getYear() + 1900;1878
				int year = new Date().getYear() + 1878;
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
		st += "{\"Value\":0,\"DisplayText\":\"请选择\"},";
		for(String str:yearArray){
			st += "{\"Value\":"+str+",\"DisplayText\":\""+str+"\"},";
		}
		
		if (st.length() > 0) {
			st = st.substring(0, st.length() - 1);
		}
//				+ "{\"Value\":1,\"DisplayText\":\"城市\"},"
//				+ "{\"Value\":2,\"DisplayText\":\"县城\"},"
//				+ "{\"Value\":3,\"DisplayText\":\"镇区\"},"
//				+ "{\"Value\":4,\"DisplayText\":\"乡\"},"
//				+ "{\"Value\":5,\"DisplayText\":\"村\"},"
//				+ "{\"Value\":6,\"DisplayText\":\"教学点\"}";
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
	 * 获取项目周期列表
	 */
	public void getList(){
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
//		System.out.println(us.getIdcard());
		StringBuilder sb = new StringBuilder();
		// 起始页
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}

		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);

		// 排序
		String sort = request.getParameter("jtSorting");
		String sorts = "";
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			sorts += sortname + " " + sorttype;
		}
		//其他啊参数
		String name = request.getParameter("name") == null? "" : request.getParameter("name");
		String year = request.getParameter("year") == null? "0" : request.getParameter("year");
		if("".equals(year)){
			year = "0";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(name)){
			params.put("name", name);
		}
		if(!"0".equals(year)){
			params.put("startyear", year);
		}
		int count = this.projectCycleService.getListCountByParams(params);
		List<ProjectCycle> lst = this.projectCycleService.getListByParams(params, start, limit, sorts);
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"TotalRecordCount\":" + count);
		sb.append(",");
		sb.append("\"Records\":[");
		if(lst != null && lst.size() > 0){
			for(ProjectCycle pc : lst){
				sb.append("{");
				sb.append("\"id\":"+pc.getId());
				sb.append(",");
				sb.append("\"name\":\""+pc.getName()+"\"");
				sb.append(",");
				sb.append("\"startyear\":\""+pc.getStartyear()+"\"");
				sb.append(",");
				sb.append("\"endyear\":\""+pc.getEndyear()+"\"");
				sb.append(",");
				sb.append("\"studyhour\":").append(pc.getStudyhour());
				sb.append(",");
				sb.append("\"status\":\""+pc.getStatus()+"\"");
				sb.append("},");
				
			}
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("]");
		sb.append("}");
		
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String addInit(){
		if (yearArray == null) {
			int year = new Date().getYear() + 1878;
//			int year = 1994;
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
	 * 修改项目周期
	 * 2016年12月23日：增加年份重复判断，若起始年份至终止年份在数据库中已有范围内存在，那么不允许周期入库
	 */
	public void add(){
		initServlert();
		try {
			String id = request.getParameter("id");
			if(id != null){
				ProjectCycle pc = this.projectCycleService.get(Integer.parseInt(id));
				if(pc != null){
					// 名称
					String name = request.getParameter("name");
					// 简称
					String startyear = request.getParameter("startyear") == null ? "0" : request.getParameter("startyear");
					if("".equals(startyear)){
						startyear = "0";
					}
					String endyear = request.getParameter("endyear") == null ? "0" : request.getParameter("endyear");
					if("".equals(startyear)){
						startyear = "0";
					}

					if (Utlity.checkStringNull(name)) {
						sendResponse("ERROR", "名称为空");
						return;
					}
					
					if("0".equals(startyear)){
						sendResponse("ERROR", "请选择初始年份");
						return;
					}
					
					if("0".equals(endyear)){
						sendResponse("ERROR", "请选择截止年份");
						return;
					}
					
					List<Map> studyhourList = JSONUtils.json2list(pc.getStudyhour(), Map.class);
					List<Map> resultList = new ArrayList<Map>();
					for(Map map : studyhourList){
						String names = map.get("name").toString();
						String nameCN = map.get("nameCN").toString();
						String value = "".equals(request.getParameter(names)) ? "0" : request.getParameter(names);
						
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("name", names);
						resultMap.put("nameCN", nameCN);
						resultMap.put("value", Integer.parseInt(value));
						resultList.add(resultMap);
					}
					pc.setName(name);
					pc.setStartyear(startyear);
					pc.setEndyear(endyear);
					pc.setStudyhour(JSONUtils.obj2json(resultList));
					this.projectCycleService.update(pc);
					sendResponse("OK", "更新成功");
				}else{
					sendResponse("ERROR", "不存在的项目周期");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			sendResponse("ERROR", "操作失败");
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
	
	public IProjectCycleService getProjectCycleService() {
		return projectCycleService;
	}
	

	public void setProjectCycleService(IProjectCycleService projectCycleService) {
		this.projectCycleService = projectCycleService;
	}
	
	public String[] getYearArray() {
		return yearArray;
	}
	

	public void setYearArray(String[] yearArray) {
		this.yearArray = yearArray;
	}

}
