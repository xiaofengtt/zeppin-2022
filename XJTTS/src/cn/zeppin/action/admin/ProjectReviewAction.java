package cn.zeppin.action.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.util.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyWorkReport;
import cn.zeppin.entity.Submit;
import cn.zeppin.service.IDocumentService;
import cn.zeppin.service.IProjectApplyEvaluateService;
import cn.zeppin.service.IProjectApplyExpertService;
import cn.zeppin.service.IProjectApplyService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.utility.DownloadZipFiles;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class ProjectReviewAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(ProjectExpertManaOptAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
	private IDocumentService iDocumentService;
	private IProjectService iProjectService;
	private IProjectApplyService iProjectApplyService;
	private IProjectApplyExpertService iProjectApplyExpertService;
	private IProjectApplyEvaluateService iProjectApplyEvaluateService;
	
	private List<String> searchYear;
	
	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String initPage() {
		this.searchYear = this.iProjectService.getProjectYearList();
		return "init";
	}

	public String initSummarizePage() {
		this.searchYear = this.iProjectService.getProjectYearList();
		return "initSummarize";
	}
	
	public void getProjectList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String rprojectId = request.getParameter("projectId");
		String rsubjectId = request.getParameter("subjectId");
		String ryear = request.getParameter("year");
		String rstatus = request.getParameter("status");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		if(rprojectId!=null && !rprojectId.equals("0")){
			searchMap.put("project", rprojectId);
		}
		if(rsubjectId!=null && !rsubjectId.equals("0")){
			searchMap.put("subject", rsubjectId);
		}
		if(ryear!=null && !ryear.equals("0")){
			searchMap.put("year", ryear);
		}
		if(rstatus!=null && !rstatus.equals("-1")){
			searchMap.put("status", rstatus);
		}
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);
		int pageSize = Integer.parseInt(request.getParameter("jtPageSize").toString());
		
		int expertid = us.getId();
		searchMap.put("expertid", expertid);
		int records = this.iProjectApplyExpertService.getCountForExpert(searchMap);
		List li = this.iProjectApplyService.getListForExpert(searchMap, start, pageSize);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		boolean flag = false;
		sb.append("\"Records\":[");
		for (int i = 0; i < li.size(); i++) {
			flag = true;
			Object[] ob = (Object[]) li.get(i);
			ProjectApply pa = (ProjectApply) ob[0];
			String paid = pa.getId().toString();
			String year = pa.getProject().getYear();
			String project = pa.getProject().getName();
			String subject = pa.getTrainingSubject().getName();
			String training = pa.getTrainingCollege().getName();
			String startTime = pa.getTrainingStarttime() == null ? "待定" : pa.getTrainingStarttime().toString().substring(0, 10);
			String endTime = pa.getTrainingEndtime() == null ? "" : pa.getTrainingEndtime().toString().substring(0, 10);
			String classHour = pa.getTrainingClasshour().toString();
			String report = "";
			String path = "";
			if (pa.getDocumentByProjectApplyDocument() != null) {
				report = pa.getDocumentByProjectApplyDocument().getTitle();
				path = pa.getDocumentByProjectApplyDocument().getResourcePath();
			}
			int id = 0;
			int pid = 0;
			int sid = 0;
			int tc = 0;
			int type = 0;
			int valuator = 0;
			String score="";
			if (pa.getProject().getPsqByProjectJudgePsq() != null) {
				id = pa.getProject().getPsqByProjectJudgePsq().getId();
				pid = pa.getProject().getId();
				sid = pa.getTrainingSubject().getId();
				tc = pa.getTrainingCollege().getId();
				type = 1;
				valuator = us.getId();
				int psq=pa.getProject().getPsqByProjectJudgePsq().getId();
				List lis=this.iProjectApplyService.getSubmitForExpert(pid,sid,tc,psq,valuator);
				if(lis.size()>0){
					Submit submit=(Submit) lis.get(0);
					int sub=submit.getId();
					int sc=this.iProjectApplyService.getScoreBySubmit(sub);
					score=sc+"";
				}
			}
			StringBuilder sbstr = new StringBuilder();
			sbstr.append("{");
			sbstr.append("\"paid\":" + paid);
			sbstr.append(",");
			sbstr.append("\"year\":\"" + year + "\"");
			sbstr.append(",");
			sbstr.append("\"project\":\"" + project + "\"");
			sbstr.append(",");
			sbstr.append("\"subject\":\"" + subject + "\"");
			sbstr.append(",");
			sbstr.append("\"training\":\"" + training + "\"");
			sbstr.append(",");
			sbstr.append("\"startTime\":\"" + startTime + "\"");
			sbstr.append(",");
			sbstr.append("\"endTime\":\"" + endTime + "\"");
			sbstr.append(",");
			sbstr.append("\"classHour\":\"" + classHour + "\"");
			sbstr.append(",");
			sbstr.append("\"score\":\"" + score + "\"");
			sbstr.append(",");
			sbstr.append("\"report\":\"" + report + "\"");
			sbstr.append(",");
			sbstr.append("\"path\":\"" + path + "\"");
			sbstr.append(",");
			sbstr.append("\"id\":\"" + id + "\"");
			sbstr.append(",");
			sbstr.append("\"pid\":\"" + pid + "\"");
			sbstr.append(",");
			sbstr.append("\"sid\":\"" + sid + "\"");
			sbstr.append(",");
			sbstr.append("\"tc\":\"" + tc + "\"");
			sbstr.append(",");
			sbstr.append("\"type\":\"" + type + "\"");
			sbstr.append(",");
			sbstr.append("\"valuator\":\"" + valuator + "\"");
			sbstr.append("},");
			sb.append(sbstr.toString());
		}
		if (flag) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append(",");
		sb.append("\"TotalRecordCount\":" + records);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	public void getProjectSummarizeList() {
		initServlert();
		UserSession us = (UserSession) session.getAttribute("usersession");
		String rprojectId = request.getParameter("projectId");
		String rsubjectId = request.getParameter("subjectId");
		String ryear = request.getParameter("year");
		String rstatus = request.getParameter("status");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		if(rprojectId!=null && !rprojectId.equals("0")){
			searchMap.put("project", rprojectId);
		}
		if(rsubjectId!=null && !rsubjectId.equals("0")){
			searchMap.put("subject", rsubjectId);
		}
		if(ryear!=null && !ryear.equals("0")){
			searchMap.put("year", ryear);
		}
		if(rstatus!=null && !rstatus.equals("-1")){
			searchMap.put("status", rstatus);
		}
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);
		int pageSize = Integer.parseInt(request.getParameter("jtPageSize").toString());
		
		int expertid = us.getId();
		searchMap.put("expertid", expertid);
		int records = this.iProjectApplyEvaluateService.getCountForExpert(searchMap);
		List li = this.iProjectApplyService.getListForEvaluate(searchMap, start, pageSize);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		boolean flag = false;
		sb.append("\"Records\":[");
		for (int i = 0; i < li.size(); i++) {
			flag = true;
			Object[] ob = (Object[]) li.get(i);
			ProjectApply pa = (ProjectApply) ob[0];
			String paid = pa.getId().toString();
			String year = pa.getProject().getYear();
			String project = pa.getProject().getName();
			String subject = pa.getTrainingSubject().getName();
			String training = pa.getTrainingCollege().getName();
			String startTime = pa.getTrainingStarttime() == null ? "待定" : pa.getTrainingStarttime().toString().substring(0, 10);
			String endTime = pa.getTrainingEndtime() == null ? "" : pa.getTrainingEndtime().toString().substring(0, 10);
			String classHour = pa.getTrainingClasshour().toString();
			String report = "";
			String path = "";
			if (pa.getDocumentByProjectApplyDocument() != null) {
				report = pa.getDocumentByProjectApplyDocument().getTitle();
				path = pa.getDocumentByProjectApplyDocument().getResourcePath();
			}
			int id = 0;
			int pid = 0;
			int sid = 0;
			int tc = 0;
			int type = 0;
			int valuator = 0;
			String score="";
			if (pa.getProject().getPsqByProjectSummarizePsq() != null) {
				id = pa.getProject().getPsqByProjectSummarizePsq().getId();
				pid = pa.getProject().getId();
				sid = pa.getTrainingSubject().getId();
				tc = pa.getTrainingCollege().getId();
				type = 4;
				valuator = us.getId();
				int psq=pa.getProject().getPsqByProjectSummarizePsq().getId();
				List lis=this.iProjectApplyService.getSubmitForExpert(pid,sid,tc,psq,valuator);
				if(lis.size()>0){
					Submit submit=(Submit) lis.get(0);
					int sub=submit.getId();
					int sc=this.iProjectApplyService.getScoreBySubmit(sub);
					score=sc+"";
				}
			}
			StringBuilder sbstr = new StringBuilder();
			sbstr.append("{");
			sbstr.append("\"paid\":" + paid);
			sbstr.append(",");
			sbstr.append("\"year\":\"" + year + "\"");
			sbstr.append(",");
			sbstr.append("\"project\":\"" + project + "\"");
			sbstr.append(",");
			sbstr.append("\"subject\":\"" + subject + "\"");
			sbstr.append(",");
			sbstr.append("\"training\":\"" + training + "\"");
			sbstr.append(",");
			sbstr.append("\"startTime\":\"" + startTime + "\"");
			sbstr.append(",");
			sbstr.append("\"endTime\":\"" + endTime + "\"");
			sbstr.append(",");
			sbstr.append("\"classHour\":\"" + classHour + "\"");
			sbstr.append(",");
			sbstr.append("\"score\":\"" + score + "\"");
			sbstr.append(",");
			sbstr.append("\"report\":\"" + report + "\"");
			sbstr.append(",");
			sbstr.append("\"path\":\"" + path + "\"");
			sbstr.append(",");
			sbstr.append("\"id\":\"" + id + "\"");
			sbstr.append(",");
			sbstr.append("\"pid\":\"" + pid + "\"");
			sbstr.append(",");
			sbstr.append("\"sid\":\"" + sid + "\"");
			sbstr.append(",");
			sbstr.append("\"tc\":\"" + tc + "\"");
			sbstr.append(",");
			sbstr.append("\"type\":\"" + type + "\"");
			sbstr.append(",");
			sbstr.append("\"valuator\":\"" + valuator + "\"");
			sbstr.append("},");
			sb.append(sbstr.toString());
		}
		if (flag) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append(",");
		sb.append("\"TotalRecordCount\":" + records);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}
	
	public void downloadFiles() throws Exception{
		initServlert();
		String absolutePath = ServletActionContext.getServletContext().getRealPath(""); 
		absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
		String paid = request.getParameter("id");
		ProjectApply pa = this.iProjectApplyService.get(Integer.valueOf(paid));
		List<Document> fileList = new ArrayList<Document>();
		if(pa.getDocumentByCurriculumDocument()!=null){
			fileList.add(pa.getDocumentByCurriculumDocument());
		}
		if(pa.getDocumentByProformanceReport()!=null){
			fileList.add(pa.getDocumentByProformanceReport());
		}
		if(pa.getDocumentByProjectApplyDocument()!=null){
			fileList.add(pa.getDocumentByProjectApplyDocument());
		}
		if(pa.getDocumentByStartMessage()!=null){
			fileList.add(pa.getDocumentByStartMessage());
		}
		if(pa.getProjectApplyWorkReport().size()>0){
			for(ProjectApplyWorkReport pawr :pa.getProjectApplyWorkReport()){
				fileList.add(pawr.getDocument());
			}
		}
		if(fileList.size()>0){
			BufferedInputStream inputStream;
			BufferedOutputStream outputStream;
			String fileName = "files" + (int)(Math.random() * 1000);
			
			for(Document document : fileList){
				String oldPath = absolutePath + document.getResourcePath();
				String newPath = absolutePath + "/upload/"+ fileName + "/" + document.getTitle();
				File newFile = new File(absolutePath + "/upload/"+ fileName);
				if (!newFile.exists()) {
					newFile.mkdirs();
				}
				inputStream = new BufferedInputStream(new FileInputStream(oldPath));
				outputStream = new BufferedOutputStream(new FileOutputStream(newPath));
				Streams.copy(inputStream, outputStream, true);
				inputStream.close();
				outputStream.flush();
				outputStream.close();
			}
			
			File zipFile = new File(absolutePath + "/upload/" + fileName + ".zip");
			FileOutputStream fous = new FileOutputStream(zipFile); 
			ZipOutputStream zipOut  = new ZipOutputStream(fous);
			DownloadZipFiles.zip(zipOut , new File(absolutePath + "/upload/" + fileName), "");
	        zipOut.close();
	        fous.close();
			 
	        response = DownloadZipFiles.downloadZip(zipFile,response);
			DownloadZipFiles.deleteDir(zipFile);
			DownloadZipFiles.deleteDir(new File(absolutePath + "/upload/" + fileName));
		}else{
			String fileName = "files" + (int)(Math.random() * 1000);
			File newFile = new File(absolutePath + "/upload/" + fileName);
			newFile.mkdirs();
			File zipFile = new File(absolutePath + "/upload/" + fileName + ".zip");
			FileOutputStream fous = new FileOutputStream(zipFile); 
			ZipOutputStream zipOut  = new ZipOutputStream(fous);
			DownloadZipFiles.zip(zipOut , new File(absolutePath + "/upload/" + fileName), "");
	        zipOut.close();
	        fous.close();
			 
	        response = DownloadZipFiles.downloadZip(zipFile,response);
			DownloadZipFiles.deleteDir(zipFile);
			DownloadZipFiles.deleteDir(new File(absolutePath + "/upload/" + fileName));
		}
	}
	
	public IDocumentService getiDocumentService() {
		return iDocumentService;
	}

	public void setiDocumentService(IDocumentService iDocumentService) {
		this.iDocumentService = iDocumentService;
	}
	
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}
	
	public IProjectApplyService getiProjectApplyService() {
		return iProjectApplyService;
	}

	public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
		this.iProjectApplyService = iProjectApplyService;
	}

	public IProjectApplyExpertService getiProjectApplyExpertService() {
		return iProjectApplyExpertService;
	}

	public void setiProjectApplyExpertService(IProjectApplyExpertService iProjectApplyExpertService) {
		this.iProjectApplyExpertService = iProjectApplyExpertService;
	}
	
	public IProjectApplyEvaluateService getiProjectApplyEvaluateService() {
		return iProjectApplyEvaluateService;
	}

	public void setiProjectApplyEvaluateService(IProjectApplyEvaluateService iProjectApplyEvaluateService) {
		this.iProjectApplyEvaluateService = iProjectApplyEvaluateService;
	}
	
	public List<String> getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(List<String> searchYear) {
		this.searchYear = searchYear;
	}
}
