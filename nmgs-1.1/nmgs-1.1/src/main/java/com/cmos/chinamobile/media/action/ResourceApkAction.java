package com.cmos.chinamobile.media.action;



import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;







import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.ApkMeta;

import org.apache.struts2.ServletActionContext;

import com.cmos.chinamobile.media.action.base.ActionResult;
import com.cmos.chinamobile.media.util.ExceptionUtil;
import com.cmos.chinamobile.media.util.Utlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.ControlConstants;

public class ResourceApkAction extends BaseAction {
	
	private static final long serialVersionUID = 2318623804948146943L;
	private static final Logger logger = LoggerFactory.getActionLog(ResourceApkAction.class);
	private InputObject inputObject;
	Map<String, Object> resource = new HashMap<String, Object>();
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
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public InputObject getInputObject(){
		inputObject = (InputObject) ServletActionContext.getContext().get(ControlConstants.INPUTOBJECT);
		File file = (File) inputObject.getParams().get("apk");
		try {
			if(inputObject.getBeans().toString().length() < 15 )
			{
			resource = writeFile(file);
			}else{
				List<Map<String,Object>> list = inputObject.getBeans();
					Map<String,Object> map = list.get(0);
					resource =(Map<String, Object>) map.get("apkRes");	
			}
		} catch (Exception e) {
			logger.info("错误", e);
		}
		Map<String,Object> bean = new HashMap<String,Object>();
		bean.put("apkRes", resource);
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
	public Map<String,Object> writeFile(File file) throws ExceptionUtil {
		String fileName = inputObject.getValue("apkFileName");
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		String serverPath = Utlity.basePath;
		if(file==null){
			throw new ExceptionUtil("没有上传文件");
		}
		String uploadpath = "/upload";// 文件上传地址
		int maxSize = 100 * 1024 * 1024;// 最大文件大小100M
		String limitfile = "apk";// 限制上传类型apk
		String path = serverPath + "/" + uploadpath + "/";
		
		Map<String,Object> apkRes = new HashMap<String, Object>();
		// 创建文件夹
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		String id = UUID.randomUUID().toString();
		String[] dir = id.split("-");
		String basePath = "/upload/";
		for (String sPath : dir) {
			basePath += sPath + "/";

			File tfFile = new File(serverPath + "/" + basePath);
			if (!tfFile.exists()) {
				tfFile.mkdir();
				if (!tfFile.exists()) {
					throw new ExceptionUtil(serverPath + "/" + basePath+ "路径创建失败");
				}
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
			String name = dir[dir.length-1];
			apkRes.put("id", id);
			apkRes.put("fileType", fileType);
			
			apkRes.put("size", BigInteger.valueOf(fileSize));
			apkRes.put("status", "normal");
			apkRes.put("type", "3");
			apkRes.put("url", basePath + name + "." + apkRes.get("fileType"));
			apkRes.put("path", basePath + name + "." + apkRes.get("fileType"));
			File file2 = new File(serverPath + apkRes.get("url"));
	        file.renameTo(file2);
	        ApkParser apkParser = new ApkParser(file2); 
			ApkMeta apkMeta = apkParser.getApkMeta(); 
			String fName = "app-"+apkMeta.getVersionName()+"-"+apkMeta.getVersionCode()+".apk";
			apkRes.put("fileName", fName);
			apkParser.close(); 
			} catch (Exception e) {
				logger.warn("文件上传失败", e);
				throw new ExceptionUtil("文件上传失败");
			}
		return apkRes;
	
}
	}
