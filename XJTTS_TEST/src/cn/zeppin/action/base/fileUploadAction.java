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

import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.utility.dataTimeConvertUtility;

import com.opensymphony.xwork2.ActionSupport;

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
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxSize);
			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
			File file = new File(serverPath + "/temp");
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
						default:
							break;
						}

						fileInfo.setFileName(item.getName().substring(0, item.getName().indexOf(".")));
						fileInfo.setFileGuid(UUID.randomUUID().toString());
						fileInfo.setFileSize((int) item.getSize());
						fileInfo.setFileExt(item.getName().substring(item.getName().indexOf(".")));
						fileInfo.setFilePath("/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid() + "/" + fileInfo.getFileName() + fileInfo.getFileExt());
						File file2 = new File(serverPath + "/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid() + "/" + fileInfo.getFileName() + fileInfo.getFileExt());
						try {
							File file3 = new File(serverPath + "/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid());
							if (!file3.exists()) {
								File tf = new File(serverPath + "/upload/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd"));
								if (!tf.exists()) {
									tf.mkdir();
								}
								file3.mkdir();
							}

							item.write(file2);
							session = request.getSession();
							HashMap<EDocumentType, fileInfo> hmFiles = new HashMap<>();
							if (session.getAttribute("uploadfile") != null) {
								hmFiles = (HashMap<EDocumentType, fileInfo>) session.getAttribute("uploadfile");
							}
							hmFiles.put(fileInfo.getFileType(), fileInfo);
							session.setAttribute("uploadfile", hmFiles);
						} catch (Exception e) {
							e.getStackTrace().toString();
						}

					}
				}
			}

		}

	}
}
