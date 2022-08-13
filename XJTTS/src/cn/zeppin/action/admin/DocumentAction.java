package cn.zeppin.action.admin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IMailInformationService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectApplyWorkReportService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITeachingGradeService;
import cn.zeppin.service.ITeachingLanguageService;
import cn.zeppin.service.ITeachingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.OpenOfficeUtility;
import cn.zeppin.utility.Utlity;

@SuppressWarnings("rawtypes")
public class DocumentAction extends baseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(DocumentAction.class);
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	private IDocumentService iDocumentService;
	private IProjectService iProjectService;
	private IProjectApplyService iProjectApplyService;
	private IMailInformationService mailInformationService;
	private IProjectApplyWorkReportService iProjectApplyWorkReportService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private ITeachingLanguageService iTeachingLanguageService;// 教学语言操作
	private ITeachingGradeService iTeachingGradeService;// 教学学段操作
	private ITeachingSubjectService iTeachingSubjectService;// 教学科目操作
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}
	
	public String initPage() {
		return "init";
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
	 * 获取项目类型简称列表
	 */
	public void getProjectTypeList(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		try {
			String sql = "select pt.shortname,pt.scode from project_type pt where pt.PROJECT_LEVEL<3 and pt.`STATUS`=1 and pt.`LEVEL`=1 group by pt.shortname order by pt.shortname";
			List list = this.iProjectApplyService.executeSQL(sql, null);
			
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Records\":[");
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					Object[] o = (Object[]) list.get(i);
					String name = o[0].toString();
					String scode = o[1].toString();
					sb.append("{");
					sb.append("\"index\":"+i);
					sb.append(",");
					sb.append("\"name\":\""+name+"\"");
					sb.append(",");
					sb.append("\"scode\":\""+scode+"\"");
					sb.append("},");
				}
				sb.delete(sb.length() -1 , sb.length());
			}
			sb.append("]");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} catch (Exception e) {
			// TODO: handle exception
			StringBuilder checkSB = new StringBuilder();
			checkSB.append("{");
			checkSB.append("\"Result\":\"ERROR\"");
			checkSB.append(",");
			checkSB.append("\"Message\":\"查询结果异常\"");
			checkSB.append("}");
			Utlity.ResponseWrite(checkSB.toString(), "application/json", response);
			return;
		}
		
	}
	/**
	 * documentType--1培训文件--红头文件
	 * 				-2开班通知--开班通知
	 * 				-3培训简报--工作简报
	 * 				-4培训动态--公告
	 * 	
	 * 				-0--所有
	 * pagenum -- 分页参数（页码）
	 * pagesize -- 分页参数（条目数）
	 * 
	 * return 	totalCount -- 总记录数
	 * 			status -- 状态
	 * 			message -- 消息
	 * 			records[] -- 记录
	 * 				redHeadDocument -- 红头文件
	 * 				workReport -- 工作简报
	 * 				startMessage -- 开班通知
	 * 				dynamic  -- 培训动态（公告）
	 * 					id -- 文件记录ID
	 * 					title -- 显示标题
	 * 					content -- 显示内容
	 * 					path -- 文件存储路径
	 * 					creattime -- 时间
	 */
	public void list(){
		initServlert();
		String ist = (String) request.getParameter("pagenum");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}

		// 显示的条数
		String ien = (String) request.getParameter("pagesize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

//		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);
		int start = (Integer.parseInt(ist)-1)*limit;
		String documentType = request.getParameter("documentType") == null ? "0" : request.getParameter("documentType");
		if("".equals(documentType)){
			documentType = "0";
		}
		
		String titles = request.getParameter("title") == null ? "" : request.getParameter("title");
		titles = titles.trim();
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"success\"");
		sb.append(",");
		sb.append("\"message\":\"成功\"");
		sb.append(",");
		sb.append("\"records\":");
		sb.append("{");
		int totalCount = 0;
		Map<String, String> sortParams = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		if("1".equals(documentType)){//红头文件
			/*
			 * 红头文件内容存在于项目表中，取项目表中数据列表，进而取到对应document信息
			 */
			
			params.put("status", 2);
			params.put("redHeadDocument", true);
			if(!"".equals(titles)){
				params.put("title", titles);
			}
			sortParams.put("createtime", "desc");
			int count = this.iProjectService.getProjectCountByParams(params);
			
			List list = this.iProjectService.getProjectByParams(params, sortParams,start,limit);
			totalCount += count;
			if(list != null && list.size() > 0){
				StringBuilder sbred = new StringBuilder();
				sbred.append("\"redHeadDocument\":[");
				for(int i=0 ;i<list.size();i++){
					Object[] ob=(Object[]) list.get(i);
//					Document doc = p.getRedHeadDocument();
					String id = ob[0].toString();
					String title = ob[1].toString();
					title = title.substring(0,title.lastIndexOf("."));
					Timestamp creattime = (Timestamp) ob[3];
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("]");
				sb.append(sbred);
			}
			
		}else if ("2".equals(documentType)) {//开班通知
			
			String projecttype = request.getParameter("projectType") == null ? "" : request.getParameter("projectType");
			if(!"".equals(projecttype)){
				params.put("projecttype", projecttype);
			}
			params.put("status", 2);
			params.put("startMessage", true);
			
			if(!"".equals(titles)){
				params.put("titles", titles);
			}
			sortParams.put("createtime", "desc");
			int count = this.iProjectApplyService.getProjectApplyCountByParams(params,sortParams);
			
			List list = this.iProjectApplyService.getProjectApplyByParams(params, sortParams,start,limit);
			
			totalCount += count;
			if(list != null && list.size() > 0){
				StringBuilder sbred = new StringBuilder();
				sbred.append("\"startMessage\":[");
				for(int i=0 ;i<list.size();i++){
					Object[] ob=(Object[]) list.get(i);
//					Document doc = p.getRedHeadDocument();
					String id = ob[0].toString();
					String title = ob[1].toString();
					title = title.substring(0,title.lastIndexOf("."));
					Timestamp creattime = (Timestamp) ob[3];
					String pid = ob[4].toString();
					id = id + "_" + pid;
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("]");
				sb.append(sbred);
			}
			
		}else if ("3".equals(documentType)) {//工作简报(projectApplyWordReport)
			
			params.put("status", 2);
			String projecttype = request.getParameter("projectType") == null ? "" : request.getParameter("projectType");
			if(!"".equals(projecttype)){
				params.put("projecttype", projecttype);
			}
			
			if(!"".equals(titles)){
				params.put("titles", titles);
			}
			sortParams.put("createtime", "desc");
			int count = this.iProjectApplyWorkReportService.getWorkReportCountByParams(params,sortParams);
			
			List list = this.iProjectApplyWorkReportService.getWorkReportByParams(params, sortParams,start,limit);
			
			totalCount += count;
			if(list != null && list.size() > 0){
				StringBuilder sbred = new StringBuilder();
				sbred.append("\"workReport\":[");
				for(int i=0 ;i<list.size();i++){
					Object[] ob=(Object[]) list.get(i);
					String id = ob[0].toString();
					String title = ob[1].toString();
					title = title.substring(0,title.lastIndexOf("."));
//					String path = ob[2].toString();
					Timestamp creattime = (Timestamp) ob[3];
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
//					sbred.append(",");
//					path.replaceAll("\"", "\\\"");
//					sbred.append("\"path\":\""+path+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("]");
				sb.append(sbred);
			}
			
		}else if ("4".equals(documentType)) {//公告 mailInformation
			
//			params.put("status", 2);
			params.put("type", 2);//只查询公告
			
			if(!"".equals(titles)){
				params.put("content", titles);
			}
			int count = this.mailInformationService.getListCountByParams(params);
			String sort = "creattime desc";
			List<MailInformation> list = this.mailInformationService.getListByParams(params, start, limit, sort);
			
			totalCount += count;
			if(list != null && list.size() > 0){
				StringBuilder sbred = new StringBuilder();
				sbred.append("\"dynamic\":[");
				for(MailInformation mail : list){
					String id = mail.getId() + "";
					String title = mail.getTitle();
//					String path = "";
					Timestamp creattime = mail.getCreattime();
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
//					sbred.append(",");
//					path.replaceAll("\"", "\\\"");
//					sbred.append("\"path\":\""+path+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("]");
				sb.append(sbred);
			}
		}else if("5".equals(documentType)){//搜索
			if(!"".equals(titles)){
				params.put("titles", titles);
			}
			int count = this.iProjectApplyService.getSearchCountByParams(params, sortParams);
			List list = this.iProjectApplyService.getSearchByParams(params, sortParams, start, limit);
			totalCount += count;
			if(list != null && list.size() > 0){
				StringBuilder sbred = new StringBuilder();
				sbred.append("\"search\":[");
				for(int i=0 ;i<list.size();i++){
					Object[] ob=(Object[]) list.get(i);
					
					String id = ob[1].toString();
					String title = ob[2].toString();
					if(title.lastIndexOf(".")>0){
						title = title.substring(0,title.lastIndexOf("."));
					}
					String type = ob[3].toString();
					if("2".equals(type)){
						String pid = "";
						if(ob[0] != null ){
							pid = ob[0].toString();
						}
						id = (id + "_" + pid);
					}
					Timestamp creattime = (Timestamp) ob[4];
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append(",");
					sbred.append("\"type\":\""+type+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("]");
				sb.append(sbred);
			}
		}else{//首页显示
			params.put("status", 2);
			params.put("redHeadDocument", true);
			
			if(!"".equals(titles)){
				params.put("title", titles);
			}
			
			sortParams.put("createtime", "desc");
			int count = this.iProjectService.getProjectCountByParams(params);
			
			List list = this.iProjectService.getProjectByParams(params, sortParams,start,limit);
			totalCount += count;
			StringBuilder sbred = new StringBuilder();
			sbred.append("\"redHeadDocument\":[");
			if(list != null && list.size() > 0){//培训文件
				
				for(int i=0 ;i<list.size();i++){
					Object[] ob=(Object[]) list.get(i);
					String id = ob[0].toString();
					String title = ob[1].toString();
					title = title.substring(0,title.lastIndexOf("."));
					Timestamp creattime = (Timestamp) ob[3];
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("],");
//				sb.append(sbred);
//				sb.append(",");
			}else{
				sbred.append("],");
//				sb.append(sbred);
//				sb.append(",");
			}
			
			params.clear();
			params.put("status", 2);
			
			if(!"".equals(titles)){
				params.put("titles", titles);
			}
			sortParams.put("createtime", "desc");
			int count1 = this.iProjectApplyService.getProjectApplyCountByParams(params,sortParams);
			
			List list1 = this.iProjectApplyService.getProjectApplyByParams(params, sortParams,start,limit);
			
			totalCount += count1;
			sbred.append("\"startMessage\":[");
			if(list1 != null && list1.size() > 0){//开班通知
//				StringBuilder sbred = new StringBuilder();
				
				for(int i=0 ;i<list1.size();i++){
					Object[] ob=(Object[]) list1.get(i);
//					Document doc = p.getRedHeadDocument();
					String id = ob[0].toString();
					String title = ob[1].toString();
					title = title.substring(0,title.lastIndexOf("."));
					Timestamp creattime = (Timestamp) ob[3];
					String pid = ob[4].toString();
					id = id + "_" + pid;
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("],");
//				sb.append(sbred);
//				sb.append(",");
			}else{
				sbred.append("],");
//				sb.append(sbred);
//				sb.append(",");
			}
			
			params.clear();
			params.put("status", 2);
			
			if(!"".equals(titles)){
				params.put("titles", titles);
			}
			sortParams.put("createtime", "desc");
			int count2 = this.iProjectApplyWorkReportService.getWorkReportCountByParams(params,sortParams);
			
			List list2 = this.iProjectApplyWorkReportService.getWorkReportByParams(params, sortParams,start,limit);
			
			totalCount += count2;
			sbred.append("\"workReport\":[");
			if(list2 != null && list2.size() > 0){//培训简报
//				StringBuilder sbred = new StringBuilder();
				
				for(int i=0 ;i<list2.size();i++){
					Object[] ob=(Object[]) list2.get(i);
//					Document doc = p.getRedHeadDocument();
					String id = ob[0].toString();
					String title = ob[1].toString();
					title = title.substring(0,title.lastIndexOf("."));
					Timestamp creattime = (Timestamp) ob[3];
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("],");
//				sb.append(sbred);
//				sb.append(",");
			}else{
				sbred.append("],");
//				sb.append(sbred);
//				sb.append(",");
			}
			
			params.clear();
			params.put("type", 2);//只查询站内信
			
			if(!"".equals(titles)){
				params.put("content", titles);
			}
			int count3 = this.mailInformationService.getListCountByParams(params);
			String sort = "creattime desc";
			List<MailInformation> list3 = this.mailInformationService.getListByParams(params, start, limit, sort);
			
			totalCount += count3;
			sbred.append("\"dynamic\":[");
			if(list3 != null && list3.size() > 0){//培训动态
//				StringBuilder sbred = new StringBuilder();
				
				for(MailInformation mail : list3){
					String id = mail.getId() + "";
					String title = mail.getTitle();
					Timestamp creattime = mail.getCreattime();
					sbred.append("{");
					sbred.append("\"id\":\""+id+"\"");
					sbred.append(",");
					title.replaceAll("\"", "\\\"");
					sbred.append("\"title\":\""+title+"\"");
					String time = Utlity.timeSpanToDateString(creattime);
					sbred.append(",");
					sbred.append("\"creattime\":\""+time+"\"");
					sbred.append("},");
				}
				sbred.delete(sbred.length()-1, sbred.length());
				sbred.append("]");
//				sb.append(sbred);
//				sb.append(",");
			}else{
				sbred.append("]");
//				sb.append(sbred);
//				sb.append(",");
			}
			sb.append(sbred);
			
		}

		sb.append("}");
		sb.append(",");
		sb.append("\"totalCount\":"+totalCount+"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	
	/**
	 * 内容页接口
	 * id -- 必填参数
	 * documentType -- 必填
	 * 
	 * return 
	 * 	status -- 状态 success/fail/login(login如果是login需要登录后才能继续跳转显示否则显示跳回当前页)
	 * 	message -- 消息
	 * 	records
	 * 		title--标题
	 * 		content -- 内容
	 * 		creattime -- 时间
	 * 		disciption -- 落款
	 * 		docURL -- 内嵌页面URL（只有type是123的有内嵌页面）
	 * 		downloadURL -- 下载链接（开班通知里下载名单处的链接）
	 * 
	 * 
	 */
	public void load(){
		initServlert();
		
		String documentType = request.getParameter("documentType") == null ? "0" : request.getParameter("documentType");
		if("".equals(documentType)){
			documentType = "0";
		}
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if("".equals(id)){
			id = "0";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"status\":\"success\"");
		sb.append(",");
		sb.append("\"message\":\"成功\"");
		sb.append(",");
		sb.append("\"records\":");
//		sb.append("[");
		if("1".equals(documentType) || "2".equals(documentType) || "3".equals(documentType)){//红头文件
			UserSession us = (UserSession)session.getAttribute("usersession");
			if("2".equals(documentType)){
				id = id.split("_")[0];
			}
			Document doc = this.iDocumentService.get(Integer.parseInt(id));
//			StringBuilder sbstr = new StringBuilder();
			
			sb.append("{");
			if(doc != null){
				String title = doc.getTitle().substring(0,doc.getTitle().lastIndexOf("."));
				title.replaceAll("\"", "\\\"");
				String content = "";
				String repath = doc.getResourcePath();
				String time = Utlity.timeSpanToDateString(doc.getCreatetime());
//				sb.append("\"title\":");
				
				String urlReal = doc.getResourcePath();
				String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
				serverPath = Utlity.getRealPath(ServletActionContext.getServletContext());
				System.out.println(serverPath);
//				String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ServletActionContext.getRequest().getContextPath();
				String path = "//" + ServletActionContext.getRequest().getServerName() + ServletActionContext.getRequest().getContextPath();
				String port = ":" + request.getServerPort();
				String fileName = urlReal.substring(0,urlReal.lastIndexOf(".")+1);
				String pdfFile = fileName + "pdf";
				
				String docURL = "";
				try {//转换Word文件
					File tofile = new File(serverPath + pdfFile);
		            if (tofile.exists()) {//文件存在不需要转换
		            	docURL = (path + port + pdfFile);
		            }else{
		            	OpenOfficeUtility du = new OpenOfficeUtility();
						
						boolean officeToPDF = du.openOffice2Pdf(serverPath + urlReal, serverPath + pdfFile);
						
						if(officeToPDF){
							docURL = (path + port + pdfFile);
						}else{
							docURL = (path + port + pdfFile);
//							StringBuilder sbstr = new StringBuilder();
//							sbstr.append("{");
//							sbstr.append("\"status\":\"ERROR\"");
//							sbstr.append(",");
//							sbstr.append("\"message\":\"文件打开失败\"");
//							sbstr.append("}");
//							Utlity.ResponseWrite(sbstr.toString(), "application/json", response);
//							return;
						}
		            }
				} catch (Exception e) {
					e.printStackTrace();
					docURL = (path + port + pdfFile);
					// TODO: handle exception
//					StringBuilder sbstr = new StringBuilder();
//					sbstr.append("{");
//					sbstr.append("\"status\":\"ERROR\"");
//					sbstr.append(",");
//					sbstr.append("\"message\":\"文件打开失败\"");
//					sbstr.append("}");
//					Utlity.ResponseWrite(sbstr.toString(), "application/json", response);
//					return;
				}
//				docURL = "http://127.0.0.1/XJTTS/upload/2016-07-13/f88b7fa0-b72f-4ff6-ae94-21ff37ba4fdb/新疆教师培训管理平台对接培训平台接口文档.pdf";
				sb.append("\"title\":\""+title+"\"");
				sb.append(",");
				sb.append("\"content\":\""+content+"\"");
				sb.append(",");
				sb.append("\"creattime\":\""+time+"\"");
				sb.append(",");
				sb.append("\"disciption\":\"\"");
				sb.append(",");
				sb.append("\"docURL\":\""+docURL+"\"");
				sb.append(",");
				sb.append("\"downloadURL\":\""+repath+"\"");
				sb.append(",");
				if(us == null){
					sb.append("\"islogin\":\"0\"");
				}else{
					sb.append("\"islogin\":\"1\"");
				}
			}
			
			sb.append("}");
			
		} else if ("4".equals(documentType)){//培训动态
			
			MailInformation mi = this.mailInformationService.get(Integer.parseInt(id));
			sb.append("{");
			if(mi != null){
				String title = mi.getTitle();
				String content = mi.getText();
				String time = Utlity.timeSpanToDateString(mi.getCreattime());
				String disciption = mi.getInscription();
				sb.append("\"title\":\""+title+"\"");
				sb.append(",");
				sb.append("\"content\":\""+content+"\"");
				sb.append(",");
				sb.append("\"creattime\":\""+time+"\"");
				sb.append(",");
				sb.append("\"disciption\":\""+disciption+"\"");
				sb.append(",");
				sb.append("\"docURL\":\"\"");
				sb.append(",");
				sb.append("\"downloadURL\":\"\"");
			}
			sb.append("}");
			
		} else {
			StringBuilder sbstr = new StringBuilder();
			sbstr.append("{");
			sbstr.append("\"status\":\"FAIL\"");
			sbstr.append(",");
			sbstr.append("\"message\":\"加载失败\"");
			sbstr.append("}");
			Utlity.ResponseWrite(sbstr.toString(), "application/json", response);
			return;
		}
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 文件下载接口(名单)
	 * id-pid
	 * @throws IOException 
	 */
	public void download() throws IOException{
		initServlert();
		String pid = request.getParameter("id") == null ? "0" : request.getParameter("id");
		if(!pid.equals("0")){
			pid = pid.split("_")[1];
		}
		ProjectApply pa = this.iProjectApplyService.get(Integer.parseInt(pid));
		if(pa != null){
			Project project = pa.getProject();
			TrainingSubject ts = pa.getTrainingSubject();
			TrainingCollege tc = pa.getTrainingCollege();
			int projectId = project.getId();
			int subjectId = ts.getId();
			int trainingId = tc.getId();
			int offset = 0, status = 2;
			
			
			int number = iTeacherTrainingRecordsService.getAduTeacherCount(
					projectId, subjectId, trainingId, -1, 0);
			List li = iTeacherTrainingRecordsService.getAduTeacher(projectId,
					subjectId, trainingId, status, -1, offset, number);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "学员名单");
			HSSFRow row = s.createRow(0);
			String title[] = { "项目名称", "培训学科", "承训院校", "学员姓名", "学员编号", "所属地区", "所在学校",
					"身份证号", "年龄", "性别", "民族", "政治面貌", "学历", "教龄", "编制", "状态", "职务", "职称",
					"汉语水平", "主要教学学科", "主要教学学段", "主要教学语言", "联系电话", "邮箱", "报到状态",
					"班级"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for (int i = 0; i < li.size(); i++) {
				Object[] obj = (Object[]) li.get(i);
				TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
				Teacher th = (Teacher) obj[1];
				t++;
				String tStatus = "未报到";
				if (ttRecord.getTrainingStatus() == 0) {
					tStatus = "异动";
				} else if (ttRecord.getTrainingStatus() == 2) {
					tStatus = "已报到";
				} else if (ttRecord.getTrainingStatus() == 3) {
					tStatus = "培训合格（已结业）";
				} else if (ttRecord.getTrainingStatus() == 4) {
					tStatus = "培训不合格";
				} else if (ttRecord.getTrainingStatus() == 5) {
					tStatus = "培训优秀（已结业）";
				} else if (ttRecord.getTrainingStatus() == 6) {
					tStatus = "培训良好（已结业）";
				}
				row = s.createRow(t);
				String projectName = ttRecord.getProject().getName();
				String subjectName = ttRecord.getTrainingSubject().getName();
				String trainingName = ttRecord.getTrainingCollege().getName();
				String tIdcard = Utlity.getStarIdcard(th.getIdcard());//*号加密
				String tName = th.getName();
				String tClass = ttRecord.getClasses() + "";
				tClass = (tClass.equals("null") ? "" : tClass);
				String tSex = (th.getSex() == 1 ? "男" : "女");
				String tAge = String.valueOf(Utlity.getAge(th.getBirthday()));
				String tEthnic = th.getEthnic().getName();
				String tPolitics = th.getPolitics().getName();
				String schoolName = th.getOrganization().getName();
				String tTeachAge = String
						.valueOf(Utlity.getAge(th.getTeachingAge()));
				String tDuty = th.getJobDuty().getName();
				String tTitle = th.getJobTitle().getName();
				String tMobile = Utlity.getStarMobile(th.getMobile());//*号加密
				String tEmail = th.getEmail();
				String tArea = "";
				String teacherStatus= "" ;
				if(th.getStatus()==1){
					teacherStatus = "在职";
				}else if(th.getStatus()==2){
					teacherStatus = "离职";
				}else{
					teacherStatus = "转出";
				}
				String authorized = th.getAuthorized()==1? "在编":"非编";
				Organization current = th.getOrganization().getOrganization();
				short tOrgLevel = current.getOrganizationLevel().getLevel();
				while (tOrgLevel > 1) {
					tArea = current.getName() + "  " + tArea;
					current = current.getOrganization();
					tOrgLevel = current.getOrganizationLevel().getLevel();
				}

				// 获取主要教学语言
				String hqlString = "from TeachingLanguage where teacher="
						+ th.getId() + " and isprime=true";
				String tMainLanguage = "";
				List<TeachingLanguage> lstTeachingLanguages = iTeachingLanguageService
						.getListByHSQL(hqlString);
				if (lstTeachingLanguages.size() > 0) {
					TeachingLanguage teachingLanguage = lstTeachingLanguages.get(0);
					tMainLanguage = teachingLanguage.getLanguage().getName();
				}

				// 获取主要教学学段
				hqlString = "from TeachingGrade where teacher=" + th.getId()
						+ " and isprime=1";
				String tMainGrade = "";
				List<TeachingGrade> lstTeachingGrades = iTeachingGradeService
						.getListByHSQL(hqlString);
				if (lstTeachingGrades.size() > 0) {
					TeachingGrade teachingGrade = lstTeachingGrades.get(0);
					tMainGrade = teachingGrade.getGrade().getName();
				}

				// 获取主要教学学科
				hqlString = "from TeachingSubject where teacher=" + th.getId()
						+ " and isprime=1";
				String tMainSubject = "";
				List<TeachingSubject> lsttTeachingSubjects = iTeachingSubjectService
						.getListByHSQL(hqlString);
				if (lsttTeachingSubjects.size() > 0) {
					TeachingSubject teachingSubject = lsttTeachingSubjects.get(0);
					tMainSubject = teachingSubject.getSubject().getName();
				}

				String tChineseLanguageLevel = th.getChineseLanguageLevel()
						.getName();
				String tEductionBackground = th.getEductionBackground().getName();
				
				String teacherId = th.getId().toString();
				
				String tInfo[] = { projectName, subjectName, trainingName, tName, teacherId,
						tArea, schoolName, tIdcard, tAge, tSex, tEthnic, tPolitics,
						tEductionBackground, tTeachAge, authorized, teacherStatus, tDuty, tTitle,
						tChineseLanguageLevel, tMainSubject, tMainGrade,
						tMainLanguage, tMobile, tEmail, tStatus, tClass };
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=studentlist.xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		}
		
	}
	

	public IDocumentService getiDocumentService() {
		return iDocumentService;
	}

	public void setiDocumentService(IDocumentService iDocumentService) {
		this.iDocumentService = iDocumentService;
	}
	
	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IMailInformationService getMailInformationService() {
		return mailInformationService;
	}

	public void setMailInformationService(
			IMailInformationService mailInformationService) {
		this.mailInformationService = mailInformationService;
	}
	
	/**
	 * @return the iProjectApplyWorkReportService
	 */
	public IProjectApplyWorkReportService getiProjectApplyWorkReportService() {
		return iProjectApplyWorkReportService;
	}

	/**
	 * @param iProjectApplyWorkReportService
	 *            the iProjectApplyWorkReportService to set
	 */
	public void setiProjectApplyWorkReportService(
			IProjectApplyWorkReportService iProjectApplyWorkReportService) {
		this.iProjectApplyWorkReportService = iProjectApplyWorkReportService;
	}

	
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	
	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(
			ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
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
	
	
	
}
