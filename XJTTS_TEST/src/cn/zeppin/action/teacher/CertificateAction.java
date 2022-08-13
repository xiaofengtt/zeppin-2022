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
	 * 获取教师证书信息列表（由培训记录中获取基本信息，图片信息由证书表记录并单独接口获取）
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
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		
		// 排序（格式creatime-desc or creattime-asc）
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
		//其他筛选参数
		//项目ID
		int projectId = 0;
		if(request.getParameter("projectId")!=null){
			projectId = Integer.parseInt(request.getParameter("projectId"));
			pagram.put("projectId", projectId);
		}
		//学科ID
		int subjectId = 0;
		if(request.getParameter("subjectId")!=null){
			projectId = Integer.parseInt(request.getParameter("subjectId"));
			pagram.put("subjectId", subjectId);
		}
		//承训单位ID
		int trainCollegeId = 0;
		if(request.getParameter("trainCollegeId")!=null){
			projectId = Integer.parseInt(request.getParameter("trainCollegeId"));
			pagram.put("trainCollegeId", trainCollegeId);
		}
		//培训方式ID
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
	 * 获取证书图片
	 */
	public void getImage(){
		initServlert();
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
	}
	
	/**
	 * 上传证书
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
	 * 保存证书
	 * 两个参数ttrId+imageURL
	 */
	public void addImgCertificate(){
		initServlert();
		
		UserSession us = (UserSession)session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		
		if(us == null){
			sb.append("{");
			sb.append("\"Result\":\"FAILED\"");
			sb.append(",");
			sb.append("\"Message\":\"用户未登录\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		String ttrId = request.getParameter("ttrId") == null? "0" : request.getParameter("ttrId");
		String imageURL = request.getParameter("imageURL") == null? "" : request.getParameter("imageURL");
		
		if(!"0".equals(ttrId) && !"".equals(imageURL)){
			
			//先查一下是否已存在 如果存在则update 如果不存在则add
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
				sb.append("\"Message\":\"上传证书保存成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
			//添加一条记录
			try {
				TeacherTrainingCertificate ttc = new TeacherTrainingCertificate();
				ttc.setImageurl(imageURL);
				ttc.setTeacherTrainingRecords(this.iTeacherTrainingRecordsService.get(Integer.parseInt(ttrId)));
				
				this.iTeacherTrainingCertificateService.add(ttc);
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"上传证书保存成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"上传证书保存过程异常\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
			
		}else{
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"上传证书保存失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
//	/**
//	 * 上传/修改用户头像
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
//			result.msg = "用户未登录";
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
//				// 定义一个变量用以储存当前头像的序号
//				int avatarNumber = 1;
//				// 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
//				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//						"yyyyMMddHHmmssS");
//				String fileName = simpleDateFormat.format(new Date());
//				Random random = new Random();
//				String randomCode = "";
//				for (int i = 0; i < 8; i++) {
//					randomCode += Integer.toString(random.nextInt(36), 36);
//				}
//				fileName = fileName + randomCode;
//				// 基于原图的初始化参数
//				String initParams = "";
//				BufferedInputStream inputStream;
//				BufferedOutputStream outputStream;
//				// 遍历表单域
//				while (fileItems.hasNext()) {
//					FileItemStream fileItem = fileItems.next();
//					String fieldName = fileItem.getFieldName();
//					// 是否是原始图片 file 域的名称（默认的 file
//					// 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）
//					Boolean isSourcePic = fieldName.equals("__source");
//					// 当前头像基于原图的初始化参数（只有上传原图时才会发送该数据，且发送的方式为POST），用于修改头像时保证界面的视图跟保存头像时一致，提升用户体验度。
//					// 修改头像时设置默认加载的原图url为当前原图url+该参数即可，可直接附加到原图url中储存，不影响图片呈现。
//					if (fieldName.equals("__initParams")) {
//						inputStream = new BufferedInputStream(
//								fileItem.openStream());
//						byte[] bytes = new byte[inputStream.available()];
//						inputStream.read(bytes);
//						initParams = new String(bytes, "UTF-8");
//						inputStream.close();
//					}
//					// 如果是原始图片 file
//					// 域的名称或者以默认的头像域名称的部分“__avatar”打头(默认的头像域名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names)
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
//						// 原始图片（默认的 file
//						// 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）。
//						if (isSourcePic) {
//							// 文件名，如果是本地或网络图片为原始文件名、如果是摄像头拍照则为 *FromWebcam.jpg
//							String sourceFileName = fileItem.getName();
//							// 原始文件的扩展名(不包含“.”)
//							String sourceExtendName = sourceFileName
//									.substring(sourceFileName.lastIndexOf('.') + 1);
////							result.sourceUrl = virtualPath = String.format(
////									"/upload/headImage/jsp_source_%s.%s", fileName,
////									sourceExtendName);
//							result.sourceUrl = basePath + fileName +"."+ sourceExtendName;
//						}
//						// 头像图片（默认的 file
//						// 域的名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names）。
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
//						// 注释①
//						// upload_url中传递的查询参数，如果定义的method为post请使用下面的代码，否则请删除或注释下面的代码块并使用注释②的代码
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
//				// 注释② upload_url中传递的查询参数，如果定义的method为get请使用下面注释的代码
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
//					result.msg = "修改头像失败!";
//				}
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//				result.setSuccess(false);
//				result.msg = "图片保存过程异常!";
//			}
//			/*
//			 * To Do...可在此处处理储存事项
//			 */
//			// 返回图片的保存结果（返回内容为json字符串，可自行构造，该处使用fastjson构造）
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
