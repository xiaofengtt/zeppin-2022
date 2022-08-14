package com.cmos.chinamobile.media.action;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.cmos.chinamobile.media.action.base.ActionResult;
import com.cmos.chinamobile.media.util.ExceptionUtil;
import com.cmos.chinamobile.media.util.Utlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.ControlConstants;

public class Resource360Action extends BaseAction {
	
	private static final long serialVersionUID = 2318623804948146943L;
	private static final Logger logger = LoggerFactory.getActionLog(Resource360Action.class);
	private InputObject inputObject;
	String resource = new String();
	/**
	 * 资源相关操作Action
	 */
	public String execute() {
		logger.info("execute", "Start");
		ActionResult<Object> result = super.getActionResult();
		super.sendJson(super.convertOutputObject2Json(result));
		logger.info("execute", "End");
		return null;
	}
	
	
	
	
	
	public InputObject getInputObject() {
		inputObject = (InputObject) ServletActionContext.getContext().get(ControlConstants.INPUTOBJECT);
		File file = (File) inputObject.getParams().get("display");

		try {
			if(inputObject.getBeans().toString().length() < 15 )
			{
			resource = writeFile(file);
			}else{
				List<Map<String,Object>> list = inputObject.getBeans();
					Map<String,Object> map = list.get(0);
					resource =(String) map.get("res");	
			}
		} catch (Exception e) {
			logger.warn("上传失败", e);
		}
		
		Map<String,Object> bean = new HashMap<String,Object>();
		bean.put("res", resource);
		bean.put("reso", "------for input more.-----");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(bean);
		inputObject.setBeans(list);
		return inputObject;
	}
	
	
	
	/**
	 * 普通取文件写入磁盘  返回res
	 * @param file
	 * @return
	 * @throws ExceptionUtil
	 */
	public String writeFile(File file) throws ExceptionUtil {
		String fileName = inputObject.getValue("displayFileName");
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		
		
		File uploadedFile;
		String serverPath = Utlity.basePath;
		if(file==null){
			throw new ExceptionUtil("没有上传文件");
		}
		String uploadpath = "upload";// 文件上传地址
		int maxSize = 100 * 1024 * 1024;// 最大文件大小100M
		String limitfile = "zip,rar";// 限制上传类型doc,docx,xls,xlsx,ppt,htm,html,txt,
		String path = serverPath + "/" + uploadpath + "/";
		// 创建文件夹
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		String[] dir = UUID.randomUUID().toString().split("-");
		String basePath = "/upload/";

		for (String sPath : dir) {
			basePath += sPath + "/";

			File tfFile = new File(serverPath + "/" + basePath);
			if (!tfFile.exists()) {
				tfFile.mkdir();
			}
		}

		try {
			long fileSize = file.length();
			// 检查文件大小
			if (fileSize > maxSize) {
				throw new ExceptionUtil("文件内容超过最大限制!");
			}
			// 检查扩展名
			String fileExt = fileType;
			if (limitfile.indexOf(fileExt) < 0) {
				throw new ExceptionUtil("上传文件扩展名是不允许的扩展名。只允许" + limitfile + "格式。!");
			}
			String newFileName = System.currentTimeMillis() + "_"
					+ new Random().nextInt(1000) + "." + fileExt;
			uploadedFile = new File(serverPath + basePath, newFileName);
			
			try {
				file.renameTo(uploadedFile);

			} catch (Exception e) {
				logger.warn("上传失败", e);
				throw new ExceptionUtil("上传失败");
			}
	}catch (Exception e) {
		logger.warn("上传失败", e);
		throw new ExceptionUtil("上传失败");
	}
		return uploadedFile.getPath();
	}	
}
