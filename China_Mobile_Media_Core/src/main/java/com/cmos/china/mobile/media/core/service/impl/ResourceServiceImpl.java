package com.cmos.china.mobile.media.core.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.cmos.china.mobile.media.core.bean.Entity.GerneralStatusType;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.service.IResourceService;
import com.cmos.china.mobile.media.core.util.DeCompressUtil;
import com.cmos.china.mobile.media.core.util.VideoUtlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class ResourceServiceImpl extends BaseServiceImpl implements IResourceService {

	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception {
		File file = (File) inputObject.getParams().get("video");
		String fileTypeString = inputObject.getValue("videoContentType");
		String[] fileType = fileTypeString.split("/");
		String beanPath = Resource.class.getResource("").getPath();
		String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
		
		if(file==null||file.equals("")){
			throw new Exception("没有上传文件");
		}
		String id = UUID.randomUUID().toString();
		String[] dir = id.split("-");
		String basePath = "/upload/";
		for (String sPath : dir) {
			basePath += sPath + "/";

			File tfFile = new File(serverPath + "/" + basePath);
			if (!tfFile.exists()) {
				tfFile.mkdir();
			}
		}
		Resource res = new Resource();
		res.setId(id);
		String name = dir[dir.length-1];res.setFilename(name);
		res.setFiletype(fileType[1]);
		res.setSize(BigInteger.valueOf(file.length()));
		res.setStatus(GerneralStatusType.NORMAL);
		res.setUrl(basePath + name + "." + res.getFiletype());
		res.setPath(serverPath + basePath + name + "." + res.getFiletype());
		
		File file2 = new File(serverPath + res.getUrl());
        file.renameTo(file2);
		if(res.getFiletype().toLowerCase().equals("jpg")||res.getFiletype().toLowerCase().equals("jpeg")||res.getFiletype().toLowerCase().equals("png")
				||res.getFiletype().toLowerCase().equals("bmp")||res.getFiletype().toLowerCase().equals("tiff")||res.getFiletype().toLowerCase().equals("gif")
				||res.getFiletype().toLowerCase().equals("psd")){
			res.setType("2");
			BufferedImage src = ImageIO.read(file2);
			res.setDpi(src.getWidth(null) + "x" + src.getHeight(null));
		}else if(res.getFiletype().toLowerCase().equals("mp4")||res.getFiletype().toLowerCase().equals("rmvb")||res.getFiletype().toLowerCase().equals("avi")||res.getFiletype().toLowerCase().equals("mov")
				||res.getFiletype().toLowerCase().equals("3gp")||res.getFiletype().toLowerCase().equals("flv")||res.getFiletype().toLowerCase().equals("wmv")){
			res.setType("1");
			try {
				 res.setDpi(VideoUtlity.getVideoDpi(serverPath + res.getUrl()));
			} catch (Exception e) {
				throw new Exception("文件上传失败");
			}
		}else{
			throw new Exception("文件格式错误");
		}
		this.getBaseDao().insert("resource_add", res);
		res = this.getBaseDao().queryForObject("resource_get", id, Resource.class);
		outputObject.convertBean2Map(res);
	}

	@Override
	public void displayAdd(InputObject inputObject, OutputObject outputObject) throws Exception {
		File file = (File) inputObject.getParams().get("video");
		String fileTypeString = inputObject.getValue("videoContentType");
		String[] fileType = fileTypeString.split("/");
		
		if(file==null||file.equals("")){
			throw new Exception("没有上传文件");
		}
		
		String uploadpath = "upload";// 文件上传地址
		@SuppressWarnings("unused")
		String unpackpath = "";
		long maxSize = 100 * 1024 * 1024;// 最大文件大小100M
		String limitfile = "zip,rar";// 限制上传类型doc,docx,xls,xlsx,ppt,htm,html,txt,
		String beanPath = Resource.class.getResource("").getPath();
		String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
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
			StringBuilder displays = new StringBuilder();
			long fileSize = file.length();
			// 检查文件大小
			if (fileSize > maxSize) {
				throw new Exception("文件内容超过最大限制!");
			}
			// 检查扩展名
			String fileExt = fileType[1];
			if (limitfile.indexOf(fileExt) < 0) {
				throw new Exception("上传文件扩展名是不允许的扩展名。只允许" + limitfile + "格式。!");
			}
			String newFileName = System.currentTimeMillis() + "_"
					+ new Random().nextInt(1000) + "." + fileExt;
			File uploadedFile = new File(serverPath + basePath, newFileName);
			try {
				file.renameTo(uploadedFile);
			} catch (Exception e) {
				throw new Exception("上传失败");
			}
			
			try {
				List<File> files = DeCompressUtil.deCompress(uploadedFile.getPath(), serverPath + basePath);
				if(files != null && files.size() > 0){
					
					for(File fi:files){
						Resource res = new Resource();
						String id = UUID.randomUUID().toString();
						res.setId(id);
						String name = fi.getName();
						String[] str = name.split("\\.");
						res.setFilename(str[0]);
						res.setFiletype(fi.getName().substring(fi.getName().indexOf(".") + 1));
						res.setSize(BigInteger.valueOf(fi.length()));
						res.setStatus(GerneralStatusType.NORMAL);
						res.setUrl(basePath + fi.getName());
						res.setPath(serverPath + basePath + fi.getName());
						res.setType("2");
						BufferedImage src = ImageIO.read(fi);
						res.setDpi(src.getWidth(null) + "x" + src.getHeight(null));
						try {
							this.getBaseDao().insert("resource_add", res);
						} catch (Exception e) {
							throw new Exception("解压过程异常,上传失败");
						}
						displays.append (res.getId() + "_" + str[0].split("_")[1] + ",");
					}
					if(displays.length() > 0){
						displays.delete(displays.length()-1, displays.length());
					}
				}else{
					throw new Exception("压缩包内文件格式错误");
				}
				
			} catch (Exception e) {
				throw new Exception("解压过程异常,上传失败");
			}
			outputObject.convertBean2Map(displays.toString());
		} catch (Exception e) {
			throw new Exception("解压过程异常,上传失败");
		}
		
	}

}
