/** 
 * Project Name:Self_Cool  
 * File Name:SsoUserAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: SsoUserAction <br/>
 * date: 2014年7月20日 下午7:05:24 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class SsoUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;
	
	private ISsoUserService ssoUserService;
	
	private IResourceService resourceService;


	/**
	 * 更新个人信息
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@ActionParam(key = "nickname", type = ValueType.STRING)
	@ActionParam(key = "school", type = ValueType.STRING)
	@ActionParam(key = "professional", type = ValueType.STRING)
	@ActionParam(key = "imageUrl", type = ValueType.STRING)
	@ActionParam(key = "password", type = ValueType.STRING)
	@ActionParam(key = "age", type = ValueType.NUMBER)
	@ActionParam(key = "gender", type = ValueType.NUMBER)
	@ActionParam(key = "user.id", type = ValueType.NUMBER)
	public void Update() {
		ActionResult result = new ActionResult();
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if(currentUser == null){
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);
		}
		
		
		if (request.getParameterMap().containsKey("nickname")) {
			String nickname = request.getParameter("nickname");
			currentUser.setNickname(nickname);
		}
		if (request.getParameterMap().containsKey("school")) {
			String school = request.getParameter("school");
			currentUser.setSchool(school);
		}
		if (request.getParameterMap().containsKey("professional")) {
			String professional = request.getParameter("professional");
			currentUser.setProfessional(professional);
		}
		if (request.getParameterMap().containsKey("imageUrl")) {
			String imageUrl = request.getParameter("imageUrl");
			currentUser.setImageUrl(imageUrl);
		}
		if (request.getParameterMap().containsKey("password")) {
			String password = request.getParameter("password");
			currentUser.setPassword(password);
		}
		if (request.getParameterMap().containsKey("age")) {
			Short age = Short.valueOf(request.getParameter("age"));
			currentUser.setAge(age);
		}
		if (request.getParameterMap().containsKey("gender")) {
			Short gender = Short.valueOf(request.getParameter("gender"));
			currentUser.setGender(gender);
		}
		this.getSsoUserService().updateSsoUser(currentUser);
		result.init(SUCCESS_STATUS, "修改成功", null);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	
	/**
	 * 上传头像，分别保存图片及图存存储路径
	 */
	
	@ActionParam(key = "user.id", type = ValueType.NUMBER)
	public void UploadImage(){

		ActionResult result = new ActionResult();
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if(currentUser == null){
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(Dictionary.UPLOAD_FILE_MAX_SIZE);
			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
			File file = new File(serverPath + "/temp");
			factory.setRepository(file);
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(Dictionary.UPLOAD_FILE_MAX_SIZE);
			List<FileItem> items = new ArrayList<>();
			
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			if (items != null) {
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (!item.isFormField()) {
						String[] dir = UUID.randomUUID().toString().split("-");
						String basePath = "upload/";

						for (String sPath : dir) {
							basePath += sPath + "/";

							File tfFile = new File(serverPath + "/" + basePath);
							if (!tfFile.exists()) {
								tfFile.mkdirs();
							}
						}
						// 存储文件
						
						// 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
								"yyyyMMddHHmmssS");
						String fileName = simpleDateFormat.format(new Date());
						Random random = new Random();
						String randomCode = "";
						for (int i = 0; i < 8; i++) {
							randomCode += Integer.toString(random.nextInt(36), 36);
						}
						fileName = fileName + randomCode;
						

						File file2 = new File(serverPath + "/" + basePath 
								+ fileName 
								+ item.getName().substring(item.getName().indexOf(".")));
						try {

							item.write(file2);
							String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
							currentUser.setImageUrl(path + "/"+basePath + fileName + item.getName().substring(item.getName().indexOf(".")));
							this.ssoUserService.updateSsoUser(currentUser);
							
							Map<String, Object> data = new HashMap<String, Object>();
							data.put("url", currentUser.getImageUrl());
							result.init(SUCCESS, "资源上传成功", data);

						} catch (Exception e) {
							e.getStackTrace().toString();
							result.init(FAIL_STATUS, "资源上传失败", null);
						}

					}
				}
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
//	/**
//	 * 上传头像,分别保存图片及图存存储路径
//	 */
//	@ActionParam(key = "user.id", type = ValueType.NUMBER)
//	public void getHeadImage(){
//		ActionResult result = new ActionResult();
//		SsoUser currentUser = (SsoUser) session.getAttribute("user");
//		if(currentUser == null){
//			int uid = this.getIntValue(request.getParameter("user.id"));
//			currentUser = this.getSsoUserService().getById(uid);
//		}
//		
//		
//		ServletInputStream sis = null;
//		FileOutputStream fos = null;
//		try {
////			FileInputStream fin = new FileInputStream(new File("D:\\welcome.jpg"));
////			
////			byte[] btyes = new byte[fin.available()];
////			
////			fin.read(btyes);
////			fin.close();
//			
//			//获取字节流
//			sis = request.getInputStream();
//			
//			byte[] btyes = new byte[sis.available()];
//			
//			sis.read(btyes);
//			
//			
//			// 定义一个变量用以储存当前头像的序号
//			int avatarNumber = 1;
//			// 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//					"yyyyMMddHHmmssS");
//			String fileName = simpleDateFormat.format(new Date());
//			Random random = new Random();
//			String randomCode = "";
//			for (int i = 0; i < 8; i++) {
//				randomCode += Integer.toString(random.nextInt(36), 36);
//			}
//			fileName = fileName + randomCode;
//			//文件存储路径
//			String virtualPath = "/upload/zixueku_"
//					+ avatarNumber + "_" + fileName + ".jpg";
//			
//			//写入文件
////			fos = new FileOutputStream(new File("D:/test.jpg"));
//			fos = new FileOutputStream(new File(ServletActionContext.getServletContext().getRealPath("/") + virtualPath.replace("/", "\\")));
//			fos.write(btyes);
//			
//			currentUser.setImageUrl(virtualPath);
//			this.ssoUserService.updateSsoUser(currentUser);
//			Map<String, Object> data = new HashMap<String, Object>();
//			data.put("imageUrl", currentUser.getImageUrl());
//			
//			result.init(SUCCESS_STATUS, "上传成功", data);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			result.init(FAIL_STATUS, "上传失败", null);
//			
//		} finally {
//			if(sis != null){
//				try {
//					sis.close();
//				} catch (Exception e2) {
//					// TODO: handle exception
//					e2.printStackTrace();
//				}
//				
//			}
//			if(fos != null){
//				try {
//					fos.close();
//				} catch (Exception e3) {
//					// TODO: handle exception
//					e3.printStackTrace();
//				}
//				
//			}
//		}
//		Utlity.ResponseWrite(result, dataType, response);
//	}
	
	
	
	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
