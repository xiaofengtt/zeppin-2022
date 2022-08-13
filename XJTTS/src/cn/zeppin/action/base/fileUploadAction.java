/**
 * 
 */
package cn.zeppin.action.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.fileInfo;
//import cn.zeppin.utility.DocumentUtility;
import cn.zeppin.utility.OpenOfficeUtility;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

/**
 * @author sj
 * 
 */
public class fileUploadAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(fileUploadAction.class);
	int maxSize = 1024 * 1024 * 1024;
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	@SuppressWarnings("unchecked")
	public void upload() throws Exception {
		initServlert();
//		UserSession us = (UserSession) session.getAttribute("usersession");
//		String pid = request.getParameter("pid") == null ? "" : request.getParameter("pid");
//		System.out.println(pid);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxSize);
			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
			File file = new File(serverPath + "/temp");
			//根据系统获取实际地址
			serverPath = Utlity.getRealPath(ServletActionContext.getServletContext());
			factory.setRepository(file);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxSize);
			List<FileItem> items = new ArrayList<>();
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (items != null) {
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (!item.isFormField()) {
						fileInfo fileInfo = new fileInfo();

						switch (item.getFieldName()) {
						case "workReport":
							fileInfo.setFileType(EDocumentType.workReport);
							break;
						case "proformanctReport":
							fileInfo.setFileType(EDocumentType.proformanctReport);
							break;
						case "classStartReport":

							fileInfo.setFileType(EDocumentType.classStartReport);
							break;
						case "applyReportBook":
							fileInfo.setFileType(EDocumentType.applyReportBook);
							break;
						case "curriculumReport":
							fileInfo.setFileType(EDocumentType.curriculumReport);
							break;
						case "applyReportModle":
							fileInfo.setFileType(EDocumentType.applyReportModle);
							break;
						case "redHeadModel":
							fileInfo.setFileType(EDocumentType.redHeadModel);
							break;
						case "trainingRecords":
							fileInfo.setFileType(EDocumentType.trainingRecords);
							break;
						case "teachereduevidence":
							fileInfo.setFileType(EDocumentType.teachereduevidence);
							break;
						case "attachment":
							fileInfo.setFileType(EDocumentType.attachment);
							break;
						case "projectPlan":
							fileInfo.setFileType(EDocumentType.projectPlan);
							break;
						default:
							break;
						}
						String title = item.getName();
						if(title.contains("\\")){
							title = title.substring(title.lastIndexOf("\\") + 1);
						}
						fileInfo.setFileName(title.substring(0, title.lastIndexOf(".")));
						fileInfo.setFileGuid(UUID.randomUUID().toString());
						fileInfo.setFileSize((int) item.getSize());
						fileInfo.setFileExt(item.getName().substring(item.getName().lastIndexOf(".")));
						//20210709修改记录：将serverPath修改为系统实际文件存储绝对路径，后续更新需要修改此路径
						fileInfo.setFilePath("/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid() + "/" + fileInfo.getFileName() + fileInfo.getFileExt());
						File file2 = new File(serverPath + "/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid() + "/" + fileInfo.getFileName() + fileInfo.getFileExt());
						try {
							File file3 = new File(serverPath + "/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid());
							if (!file3.exists()) {
								File tf = new File(serverPath + "/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd"));
								if (!tf.exists()) {
									File file4 = new File(serverPath + "/upload/");
									if(!file4.exists()){
										file4.mkdir();
									}
									tf.mkdir();
								}
								file3.mkdir();
							}

							item.write(file2);
							session = request.getSession();
							if (fileInfo.getFileType().equals(EDocumentType.workReport)){
								List<fileInfo> liFiles = new ArrayList<>();
								if (session.getAttribute("workreport") != null) {
									liFiles = (List<fileInfo>) session.getAttribute("workreport");
								}
								liFiles.add(fileInfo);
								session.setAttribute("workreport", liFiles);
							}else if (fileInfo.getFileType().equals(EDocumentType.teachereduevidence)){
								List<fileInfo> liFiles = new ArrayList<>();
								if (session.getAttribute("teachereduevidence") != null) {
									liFiles = (List<fileInfo>) session.getAttribute("teachereduevidence");
								}
								liFiles.add(fileInfo);
								session.setAttribute("teachereduevidence", liFiles);
							}else if (fileInfo.getFileType().equals(EDocumentType.attachment)){
								List<fileInfo> liFiles = new ArrayList<>();
								if (session.getAttribute("attachment") != null) {
									liFiles = (List<fileInfo>) session.getAttribute("attachment");
								}
								liFiles.add(fileInfo);
								session.setAttribute("attachment", liFiles);
							}else{
								HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
								if (session.getAttribute("uploadfile") != null) {
									hmFiles = (HashMap<EDocumentType, fileInfo>) session.getAttribute("uploadfile");
								}
								hmFiles.put(fileInfo.getFileType(), fileInfo);
								session.setAttribute("uploadfile", hmFiles);
							}
						} catch (Exception e) {
							e.getStackTrace().toString();
						}

					}
				}
			}

		}

	}

	/**
	 * 在线预览文档
	 */
	public void checkDoc(){
		initServlert();
		StringBuilder sb = new StringBuilder();
		String urlReal = request.getParameter("url") == null?"":request.getParameter("url");
		String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
		
//		System.out.println(urlReal);
//		System.out.println(serverPath+urlReal);
		if(!"".equals(urlReal)){
			String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ServletActionContext.getRequest().getContextPath();
//			System.out.println(path+urlReal);
			String pdfFile = "";
			String fileName = urlReal.substring(0,urlReal.lastIndexOf(".")+1);
			pdfFile = fileName + "pdf";
//			System.out.println(pdfFile);
			try {
				//20210709修改为绝对路径
				serverPath = Utlity.getRealPath(ServletActionContext.getServletContext());
				File tofile = new File(serverPath + pdfFile);      
	            if (tofile.exists()) {      
	            	sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"成功\"");
					sb.append(",");
					sb.append("\"docurl\":\"" + path + pdfFile + "\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
	            }
	            
//				DocumentUtility du = new DocumentUtility();
//				
//				boolean officeToPDF = du.convert2PDF(serverPath + urlReal, serverPath + pdfFile);
	            OpenOfficeUtility du = new OpenOfficeUtility();
				
				boolean officeToPDF = du.openOffice2Pdf(serverPath + urlReal, serverPath + pdfFile);
				if(officeToPDF){
					//成功转化，返回pdf的url
					sb.append("{");
					sb.append("\"Result\":\"OK\"");
					sb.append(",");
					sb.append("\"Message\":\"成功\"");
					sb.append(",");
					sb.append("\"docurl\":\"" + path + pdfFile + "\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}else{
					sb.append("{");
					sb.append("\"Result\":\"ERROR\"");
					sb.append(",");
					sb.append("\"Message\":\"失败，转换过程异常\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"失败，转换过程异常\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
		} else {
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"失败，空数据\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
}
