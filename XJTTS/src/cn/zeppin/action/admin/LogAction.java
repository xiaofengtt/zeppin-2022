package cn.zeppin.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.entity.Log;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.ILogService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

public class LogAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ILogService iLogService;
	private IProjectAdminService iProjectAdminService;
	private IProjectExpertService iProjectExpertService;
	private ITrainingAdminService iTrainingAdminService;
	private ITeacherService iTeacherService;
	
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private List<String> tableList;
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	public String initPage() {
		this.tableList = this.iLogService.getTableList();
		return "init";
	}
	
	public void getLogList(){
		initServlert();
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
		
		String tableName = request.getParameter("tableName");
		String tableId = request.getParameter("tableId");
		Map<String,Object> searchMap = new HashMap<String,Object>();
		if(tableName!=null && !tableName.equals("All") && !tableName.equals("")){
			searchMap.put("tableName", tableName);
		}
		if(tableId!=null && !tableId.equals("")){
			searchMap.put("tableId", tableId);
		}
		
		Integer count = this.iLogService.getLogCountByParams(searchMap);
		List<Log> logList = this.iLogService.getLogListByParams(searchMap, start, limit);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		
		sb.append("\"Records\":[");
		for (Log log : logList) {
			String type = "";
			if(log.getType() == DictionyMap.LOG_TYPE_ADD){
				type="添加";
			}else if(log.getType() == DictionyMap.LOG_TYPE_UPDATE){
				type="修改";
			}else{
				type="删除";
			}
			
			String user = "";
			if(log.getUserRole() == 1){
				ProjectAdmin pa = this.iProjectAdminService.get(log.getUserId());
				user = pa.getOrganization().getName() + "项目管理员" + pa.getName();
			}else if(log.getUserRole() == 2){
				TrainingAdmin ta = this.iTrainingAdminService.get(log.getUserId());
				user =  ta.getTrainingCollege().getName() + "承训单位管理员" + ta.getName();
			}else if(log.getUserRole() == 3){
				Teacher t = this.iTeacherService.get(log.getUserId());
				user =  t.getOrganization().getName() + "教师" + t.getName();
			}else if(log.getUserRole() == 4){
				ProjectExpert pe = this.iProjectExpertService.get(log.getUserId());
				user = pe.getOrganization() + "评审专家" + pe.getName();
			}else if(log.getUserRole() == 5){
				user = "超级管理员王扬";
			}
			
			StringBuilder sbstr = new StringBuilder();
			sbstr.append("{");
			sbstr.append("\"id\":" + log.getId());
			sbstr.append(",");
			sbstr.append("\"type\":\"" + type + "\"");
			sbstr.append(",");
			sbstr.append("\"tableName\":\"" + log.getTableName() + "\"");
			sbstr.append(",");
			sbstr.append("\"tableId\":\"" + log.getTableId() + "\"");
			sbstr.append(",");
			sbstr.append("\"user\":\"" + user + "\"");
			sbstr.append(",");
			sbstr.append("\"remark\":" + log.getRemark());
			sbstr.append("},");
			sb.append(sbstr.toString());
		}
		if (logList.size()>0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append(",");
		sb.append("\"TotalRecordCount\":" + count);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public ILogService getiLogService() {
		return iLogService;
	}
	
	public void setiLogService(ILogService iLogService) {
		this.iLogService = iLogService;
	}
	
	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}
	
	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}
	
	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}
	
	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}
	
	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}
	
	public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}
	
	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}
	
	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}
	
	public List<String> getTableList() {
		return tableList;
	}

	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}
}
