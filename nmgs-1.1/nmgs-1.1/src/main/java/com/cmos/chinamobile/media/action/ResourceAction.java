package com.cmos.chinamobile.media.action;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;

import com.cmos.chinamobile.media.action.base.ActionResult;
import com.cmos.chinamobile.media.util.ExceptionUtil;
import com.cmos.chinamobile.media.util.Utlity;
import com.cmos.chinamobile.media.util.VideoUtlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.ControlConstants;

public class ResourceAction extends BaseAction {
	
	private static final long serialVersionUID = 2318623804948146943L;
	private static final Logger logger = LoggerFactory.getActionLog(ResourceAction.class);
	private InputObject inputObject;
	private Map<String,Object> resource = new HashMap<String, Object>();
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
	public InputObject getInputObject() {
		inputObject = (InputObject) ServletActionContext.getContext().get(ControlConstants.INPUTOBJECT);
		File file = (File) inputObject.getParams().get("video");
		try {
			if(inputObject.getBeans().toString().length() < 30 )
			{
			resource = writeFile(file);
			}else{
				List<Map<String,Object>> list = inputObject.getBeans();
					Map<String,Object> map = list.get(0);
					resource =(Map<String, Object>) map.get("res");	
			}
		} catch (Exception e) {
			logger.info("错误", e);
		}
		
		Map<String,Object> bean = new HashMap<String,Object>();
		bean.put("res", resource);
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
	 * @throws IOException 
	 */
	public Map<String,Object> writeFile(File file) throws ExceptionUtil, IOException {
		String fileName = inputObject.getValue("videoFileName");
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		
		
		Map<String,Object> res = new HashMap<String, Object>();

		String serverPath = Utlity.basePath;
		if(file==null){
			throw new ExceptionUtil("没有上传文件");
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
		String name = dir[dir.length-1];
		res.put("id", id);
		res.put("fileType", fileType);
		res.put("fileName", name);
		res.put("size", BigInteger.valueOf(file.length()));
		res.put("status", "normal");
		res.put("url", basePath + name + "." + res.get("fileType"));
		res.put("path", basePath + name + "." + res.get("fileType"));

		File file2 = new File(serverPath + res.get("url"));
        file.renameTo(file2);
        
        
		if("jpg".equalsIgnoreCase(fileType)||"jpeg".equalsIgnoreCase(fileType)||"png".equalsIgnoreCase(fileType)
				||"bmp".equalsIgnoreCase(fileType)||"tiff".equalsIgnoreCase(fileType)||"gif".equalsIgnoreCase(fileType)
				||"psd".equalsIgnoreCase(fileType)){
			res.put("type","2");
			BufferedImage src = ImageIO.read(file2);
			res.put("dpi",src.getWidth(null) + "x" + src.getHeight(null));
		}else if("mp4".equalsIgnoreCase(fileType)||"rmvb".equalsIgnoreCase(fileType)||"avi".equalsIgnoreCase(fileType)
				||"mov".equalsIgnoreCase(fileType)||"3gp".equalsIgnoreCase(fileType)||"flv".equalsIgnoreCase(fileType)
				||"wmv".equalsIgnoreCase(fileType)||"mpg".equalsIgnoreCase(fileType)){
			res.put("type","1");
			try {
				 res.put("dpi",(VideoUtlity.getVideoDpi(serverPath + res.get("url"))));
			} catch (Exception e) {
				logger.warn("文件上传失败", e);
				throw new ExceptionUtil("文件上传失败");
			}
		}else{
			throw new ExceptionUtil("文件格式错误");
		}
		return res;
	}

	
	
	
	
}
