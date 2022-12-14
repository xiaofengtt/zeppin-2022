package cn.zeppin.action.teacher;
 
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.baseAction;
import cn.zeppin.action.admin.ProjectBaseInfoAction;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.EDocumentType;
import cn.zeppin.entity.ImgResult;
import cn.zeppin.entity.TeacherTrainingCertificate;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.fileInfo;
import cn.zeppin.service.ITeacherTrainingCertificateService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.dataTimeConvertUtility;

public class CertificateAction extends baseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8102948942441565476L;
	
	static Logger logger = LogManager.getLogger(ProjectBaseInfoAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	
//	private IProjectApplyService iProjectApplyService;
//	private IProjectService iProjectService;
//	private IProjectAdminService iProjectAdminService;
//	private ITrainingAdminService iTrainingAdminService;
//	private IAreaService iAreaService;
//	private IProjectTypeService iProjectTypeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private ITeacherTrainingCertificateService iTeacherTrainingCertificateService;
	
	
	public CertificateAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 */
	@SuppressWarnings("rawtypes")
	public void getCertificateList(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		// ??????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		
		// ???????????????creatime-desc or creattime-asc???
		Map<String, String> sortParams = new HashMap<>();
		String sort = request.getParameter("sort");
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split("-");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			
			sortParams.put(sortname, sorttype);
		} else {
			sortParams.put("creattime", "desc");
		}
		
		
		Map<String, Object> pagram = new HashMap<String, Object>();
		//??????????????????
		//??????ID
		int projectId = 0;
		if(request.getParameter("projectId")!=null){
			projectId = Integer.parseInt(request.getParameter("projectId"));
			pagram.put("projectId", projectId);
		}
		//??????ID
		int subjectId = 0;
		if(request.getParameter("subjectId")!=null){
			projectId = Integer.parseInt(request.getParameter("subjectId"));
			pagram.put("subjectId", subjectId);
		}
		//????????????ID
		int trainCollegeId = 0;
		if(request.getParameter("trainCollegeId")!=null){
			projectId = Integer.parseInt(request.getParameter("trainCollegeId"));
			pagram.put("trainCollegeId", trainCollegeId);
		}
		//????????????ID
		int trainType = 0;
		if(request.getParameter("trainType")!=null){
			projectId = Integer.parseInt(request.getParameter("trainType"));
			pagram.put("trainType", trainType);
		}
		
		pagram.put("finalStatus", 2);
		
		
		int count = this.iTeacherTrainingRecordsService.getTeacherRecordsCountByTid(us.getId(), pagram);
		List li = this.iTeacherTrainingRecordsService.getTeacherRecordsByTid(us.getId(), offset, count, sortParams, pagram);
		
		if(li != null && li.size()>0){
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Records\":[");
			StringBuilder sbu = new StringBuilder();
			for(int i = 0; i < li.size(); i++){
				Object[] obj = (Object[])li.get(i);
				TeacherTrainingRecords ttr = (TeacherTrainingRecords)obj[0];
				if(ttr.getCertificate() == null || "".equals(ttr.getCertificate())){
					count--;
					continue;
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ttrId", ttr.getId());
				
				List<TeacherTrainingCertificate> lstCertificate = this.iTeacherTrainingCertificateService.getTeacherCertificateListByParams(params, null, offset, count);
				
				String imageURL = "";
				if(lstCertificate != null && lstCertificate.size() > 0){
					imageURL = lstCertificate.get(0).getImageurl();
				}
				sbu.append("{");
				sbu.append("\"ttrId\":"+ttr.getId());
				sbu.append(",");
				sbu.append("\"project\":\""+ttr.getProject().getName()+"\"");
				sbu.append(",");
				sbu.append("\"subject\":\""+ttr.getTrainingSubject().getName()+"\"");
				sbu.append(",");
				sbu.append("\"trainingCollege\":\""+ttr.getTrainingCollege().getName()+"\"");
				sbu.append(",");
				sbu.append("\"certificate\":\""+ttr.getCertificate()+"\"");
				sbu.append(",");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String time = sdf.format(ttr.getRegisttime());
				sbu.append("\"time\":\""+time+"\"");
				sbu.append(",");
				sbu.append("\"imageURL\":\""+imageURL+"\"");
				sbu.append("},");
			}
			if(sbu.length()>0){
				sb.append(sbu);
				sb.delete(sb.length()-1, sb.length());
			}
			
			sb.append("],");
			sb.append("\"TotalCount\":"+count);
			sb.append("}");
		}else{
			sb.append("{");
			sb.append("\"Result\":\"OK\"");
			sb.append(",");
			sb.append("\"Records\":[],");
			sb.append("\"TotalCount\":"+count);
			sb.append("}");
		}
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		
	}
	
	/**
	 * ??????????????????
	 */
	public void getImage(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
	}
	
	/**
	 * ????????????
	 * @throws Exception
	 */
//	@SuppressWarnings("unchecked")
	public void upload() throws Exception {
		StringBuilder sb = new StringBuilder();
		int maxSize = 1024 * 1024 * 1024;
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
						fileInfo.setFilePath("/upload/certificate/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid() + "/" + fileInfo.getFileName() + fileInfo.getFileExt());
						File file2 = new File(serverPath + "/upload/certificate/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid() + "/" + fileInfo.getFileName() + fileInfo.getFileExt());
						try {
							File file3 = new File(serverPath + "/upload/certificate/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd") + "/" + fileInfo.getFileGuid());
							if (!file3.exists()) {
								File tf = new File(serverPath + "/upload/certificate/" + dataTimeConvertUtility.timespanToString(dataTimeConvertUtility.getCurrentTime(""), "yyyy-MM-dd"));
								if (!tf.exists()) {
									File tfs = new File(serverPath + "/upload/certificate");
									if(!tfs.exists()){
										tfs.mkdir();
									}
									tf.mkdir();
								}
								file3.mkdir();
							}

							item.write(file2);
							
//							TeacherTrainingCertificate ttc = new TeacherTrainingCertificate();
							
							ImgResult result = new ImgResult();
							result.success = true;
							result.msg = "Success!";
							
							String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
							String imageURL = path + fileInfo.getFilePath();
							result.sourceUrl = imageURL;
							
							JSONObject obj = JSONObject.fromObject(result);
							sb.append(obj.toString());
							
							Utlity.ResponseWrite(sb.toString(), "application/json", response);
							
						} catch (Exception e) {
							e.getStackTrace().toString();
							ImgResult result = new ImgResult();
							result.success = true;
							result.msg = "Success!";
							JSONObject obj = JSONObject.fromObject(result);
							sb.append(obj.toString());
							
							Utlity.ResponseWrite(sb.toString(), "application/json", response);
						}

					}
				}
			}

		}

	}
	
	
	/**
	 * ????????????
	 * ????????????ttrId+imageURL
	 */
	public void addImgCertificate(){
		initServlert();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"???????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		String ttrId = request.getParameter("ttrId") == null? "0" : request.getParameter("ttrId");
		String imageURL = request.getParameter("imageURL") == null? "" : request.getParameter("imageURL");
		
		if(!"0".equals(ttrId) && !"".equals(imageURL)){
			
			//??????????????????????????? ???????????????update ??????????????????add
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ttrId", ttrId);
			List<TeacherTrainingCertificate> lstCertificate = this.iTeacherTrainingCertificateService.getTeacherCertificateListByParams(params, null, 0, DictionyMap.maxPageSize);
			if(lstCertificate != null && lstCertificate.size() > 0){
				TeacherTrainingCertificate ttcf = lstCertificate.get(0);
				ttcf.setImageurl(imageURL);
				this.iTeacherTrainingCertificateService.update(ttcf);
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			//??????????????????
			try {
				TeacherTrainingCertificate ttc = new TeacherTrainingCertificate();
				ttc.setImageurl(imageURL);
				ttc.setTeacherTrainingRecords(this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId)));
				
				this.iTeacherTrainingCertificateService.add(ttc);
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"??????????????????????????????\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"????????????????????????\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
//	/**
//	 * ??????/??????????????????
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void inputImage() {
//
//		initServlert();
//		
//		UserSession us = (UserSession)session.getAttribute("teachersession");
//		
//		StringBuilder sb = new StringBuilder();
//		
//
//		ImgResult result = new ImgResult();
//		result.avatarUrls = new ArrayList();
//		result.success = false;
//		result.msg = "Failure!";
//
//
//		if(us == null){
//			result.msg = "???????????????";
//			sb.append(JSONObject.fromObject(result).toString());
//			Utlity.ResponseWrite(sb.toString(), "application/json", response);
//			return;
//		}
//		
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		
//		if (isMultipart) {
//			Teacher teacher = this.iTeacherService.get(us.getId());
//			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
//
////			String userid;
////			String username;
//
//			try {
//				DiskFileItemFactory factory = new DiskFileItemFactory();
//				ServletFileUpload upload = new ServletFileUpload(factory);
//				FileItemIterator fileItems = upload.getItemIterator(request);
//				// ???????????????????????????????????????????????????
//				int avatarNumber = 1;
//				// ??????????????????+8???????????????????????????????????????????????????????????????
//				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//						"yyyyMMddHHmmssS");
//				String fileName = simpleDateFormat.format(new Date());
//				Random random = new Random();
//				String randomCode = "";
//				for (int i = 0; i < 8; i++) {
//					randomCode += Integer.toString(random.nextInt(36), 36);
//				}
//				fileName = fileName + randomCode;
//				// ??????????????????????????????
//				String initParams = "";
//				BufferedInputStream inputStream;
//				BufferedOutputStream outputStream;
//				// ???????????????
//				while (fileItems.hasNext()) {
//					FileItemStream fileItem = fileItems.next();
//					String fieldName = fileItem.getFieldName();
//					// ????????????????????? file ???????????????????????? file
//					// ???????????????__source??????????????????????????????????????????????????????src_field_name???
//					Boolean isSourcePic = fieldName.equals("__source");
//					// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????POST???????????????????????????????????????????????????????????????????????????????????????????????????
//					// ??????????????????????????????????????????url???????????????url+??????????????????????????????????????????url????????????????????????????????????
//					if (fieldName.equals("__initParams")) {
//						inputStream = new BufferedInputStream(
//								fileItem.openStream());
//						byte[] bytes = new byte[inputStream.available()];
//						inputStream.read(bytes);
//						initParams = new String(bytes, "UTF-8");
//						inputStream.close();
//					}
//					// ????????????????????? file
//					// ?????????????????????????????????????????????????????????__avatar?????????(???????????????????????????__avatar1,2,3...??????????????????????????????????????????????????????avatar_field_names)
//					else if (isSourcePic || fieldName.startsWith("__avatar")) {
//						
//						String[] dir = UUID.randomUUID().toString().split("-");
//						String basePath = "upload/headImage/";
//
//						for (String sPath : dir) {
//							basePath += sPath + "/";
//
//							File tfFile = new File(serverPath + "/" + basePath);
//							if (!tfFile.exists()) {
//								tfFile.mkdirs();
//							}
//						}
//						
//						String virtualPath = basePath
//								+ avatarNumber + "_" + fileName + ".jpg";
//						
//						// ???????????????????????? file
//						// ???????????????__source??????????????????????????????????????????????????????src_field_name??????
//						if (isSourcePic) {
//							// ????????????????????????????????????????????????????????????????????????????????????????????? *FromWebcam.jpg
//							String sourceFileName = fileItem.getName();
//							// ????????????????????????(????????????.???)
//							String sourceExtendName = sourceFileName
//									.substring(sourceFileName.lastIndexOf('.') + 1);
////							result.sourceUrl = virtualPath = String.format(
////									"/upload/headImage/jsp_source_%s.%s", fileName,
////									sourceExtendName);
//							result.sourceUrl = basePath + fileName +"."+ sourceExtendName;
//						}
//						// ???????????????????????? file
//						// ???????????????__avatar1,2,3...??????????????????????????????????????????????????????avatar_field_names??????
//						else {
//							result.avatarUrls.add("/"+virtualPath);
//							avatarNumber++;
//						}
//						inputStream = new BufferedInputStream(
//								fileItem.openStream());
//						outputStream = new BufferedOutputStream(
//								new FileOutputStream(serverPath + "/"
//										+ virtualPath));
//						Streams.copy(inputStream, outputStream, true);
//						inputStream.close();
//						outputStream.flush();
//						outputStream.close();
//					} else {
//						// ?????????
//						// upload_url??????????????????????????????????????????method???post????????????????????????????????????????????????????????????????????????????????????????????????
//						inputStream = new BufferedInputStream(
//								fileItem.openStream());
//						byte[] bytes = new byte[inputStream.available()];
//						inputStream.read(bytes);
//						inputStream.close();
//						if (fieldName.equals("userid")) {
//							result.userid = new String(bytes, "UTF-8");
//						} else if (fieldName.equals("username")) {
//							result.username = new String(bytes, "UTF-8");
//						}
//					}
//				}
//				// ????????? upload_url??????????????????????????????????????????method???get??????????????????????????????
//				/*
//				 * result.userid = request.getParameter("userid");
//				 * result.username = request.getParameter("username");
//				 */
//
//				if (result.sourceUrl != null) {
//					result.sourceUrl += initParams;
//					teacher.setHeadPicPath(result.getSourceUrl());
//				}else{
//					teacher.setHeadPicPath(result.avatarUrls.get(0).toString());
//				}
//				try {
//					this.iTeacherService.update(teacher);
//					result.success = true;
//					result.msg = "Success!";
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					result.msg = "??????????????????!";
//				}
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//				result.setSuccess(false);
//				result.msg = "????????????????????????!";
//			}
//			/*
//			 * To Do...??????????????????????????????
//			 */
//			// ?????????????????????????????????????????????json??????????????????????????????????????????fastjson?????????
//			JSONObject obj = JSONObject.fromObject(result);
//			sb.append(obj.toString());
//			
//			Utlity.ResponseWrite(sb.toString(), "application/json", response);
//		}
//	}
	
	

	
//		public IProjectApplyService getiProjectApplyService() {
//			return iProjectApplyService;
//		}
//
//		public void setiProjectApplyService(IProjectApplyService iProjectApplyService) {
//			this.iProjectApplyService = iProjectApplyService;
//		}
//
//		public IProjectService getiProjectService() {
//			return iProjectService;
//		}
//
//		public void setiProjectService(IProjectService iProjectService) {
//			this.iProjectService = iProjectService;
//		}
//
//		public IProjectAdminService getiProjectAdminService() {
//			return iProjectAdminService;
//		}
//
//		public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
//			this.iProjectAdminService = iProjectAdminService;
//		}
//
//		public ITrainingAdminService getiTrainingAdminService() {
//			return iTrainingAdminService;
//		}
//
//		public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
//			this.iTrainingAdminService = iTrainingAdminService;
//		}
//
//		public IAreaService getiAreaService() {
//			return iAreaService;
//		}
//
//		public void setiAreaService(IAreaService iAreaService) {
//			this.iAreaService = iAreaService;
//		}
//
//		public IProjectTypeService getiProjectTypeService() {
//			return iProjectTypeService;
//		}
//
//		public void setiProjectTypeService(IProjectTypeService iProjectTypeService) {
//			this.iProjectTypeService = iProjectTypeService;
//		}


		public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
			return iTeacherTrainingRecordsService;
		}

		public void setiTeacherTrainingRecordsService(
				ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
			this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
		}

		public ITeacherTrainingCertificateService getiTeacherTrainingCertificateService() {
			return iTeacherTrainingCertificateService;
		}

		public void setiTeacherTrainingCertificateService(
				ITeacherTrainingCertificateService iTeacherTrainingCertificateService) {
			this.iTeacherTrainingCertificateService = iTeacherTrainingCertificateService;
		}

		
}
