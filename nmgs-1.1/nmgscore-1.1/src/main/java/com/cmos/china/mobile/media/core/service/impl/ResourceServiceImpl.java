package com.cmos.china.mobile.media.core.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;



import com.cmos.china.mobile.media.core.bean.Entity.GerneralStatusType;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.service.IResourceService;
import com.cmos.china.mobile.media.core.util.DeCompressUtil;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class ResourceServiceImpl extends BaseServiceImpl implements IResourceService {
 
	private static Logger logger = LoggerFactory.getServiceLog(ResourceServiceImpl.class);
	/**
	 * 上传视频，图片资源
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		Resource res = new Resource();
		List<Map<String,Object>> list = inputObject.getBeans();
		if(list != null)
		{
			Map<String,Object> map = list.get(0);
			Map<String,Object> mapRes=(Map<String, Object>) map.get("res");			
			res.setId((String) mapRes.get("id"));
			res.setFilename((String) mapRes.get("name"));
			res.setFiletype((String) mapRes.get("fileType"));
			res.setSize((BigInteger) mapRes.get("size"));
			res.setStatus((String) mapRes.get("status"));
			res.setUrl((String) mapRes.get("url"));
			res.setPath((String) mapRes.get("path"));
			res.setDpi((String) mapRes.get("dpi"));
			res.setType((String) mapRes.get("type"));
		}
		else{
			throw new ExceptionUtil("文件错误");
		}
		this.getBaseDao().insert("resource_add", res);
		outputObject.convertBean2Map(res);
	}

	/**
	 * 上传360度展示资源
	 */
	@Override
	public void displayAdd(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String uploadedFile = "";
		List<Map<String,Object>> list = inputObject.getBeans();
		if(list != null)
		{
			Map<String,Object> map = list.get(0);
			uploadedFile = (String) map.get("res");
		}
		String base = uploadedFile.substring(0, uploadedFile.indexOf("."));
		String basePath = uploadedFile.substring(uploadedFile.indexOf("upload"), uploadedFile.indexOf("."));
		StringBuilder displays = new StringBuilder();
			try {
				List<File> files = DeCompressUtil.deCompress(uploadedFile, base);
				if(files != null && !files.isEmpty()){
					
					for(File fi:files){
						Resource res = new Resource();
						String id = UUID.randomUUID().toString();
						res.setId(id);
						String name = fi.getName();
						String[] str = name.split("\\.");
						res.setFilename(str[0]);
						
						logger.warn("解压过程：", res.getFilename());
						
						
						res.setFiletype(fi.getName().substring(fi.getName().indexOf(".") + 1));
						res.setSize(BigInteger.valueOf(fi.length()));
						res.setStatus(GerneralStatusType.NORMAL);
						res.setUrl(basePath +"/"+ fi.getName());
						res.setPath(basePath +"/"+ fi.getName());
						
						logger.warn("解压地址：", res.getPath());
						
						res.setType("2");
						BufferedImage src = ImageIO.read(fi);
						res.setDpi(src.getWidth(null) + "x" + src.getHeight(null));
						try {
							this.getBaseDao().insert("resource_add", res);
						} catch (Exception e) {
							logger.warn("解压过程异常,上传失败", e);
							throw new ExceptionUtil("解压过程异常,上传失败");
						}
						displays.append (res.getId() + "_" + str[0].split("_")[1] + ",");
					}
					if(displays.length() > 0){
						displays.delete(displays.length()-1, displays.length());
					}
				}else{
					throw new ExceptionUtil("压缩包内文件格式错误");
				}
				
			} catch (Exception e) {
				logger.warn("解压过程异常,上传失败", e);
				throw new ExceptionUtil("解压过程异常,上传失败");
			}
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("display", displays.toString());
			outputObject.setBean(data);
		}

	@SuppressWarnings("unchecked")
	@Override
	public void updateApk(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		Resource res = new Resource();
		List<Map<String,Object>> list = inputObject.getBeans();
		if(list != null)
		{
			Map<String,Object> map = list.get(0);
			Map<String,Object> mapRes=(Map<String, Object>) map.get("apkRes");			
			res.setId((String) mapRes.get("id"));
			res.setFilename((String) mapRes.get("fileName"));
			res.setFiletype((String) mapRes.get("fileType"));
			res.setSize((BigInteger) mapRes.get("size"));
			res.setStatus((String) mapRes.get("status"));
			res.setUrl((String) mapRes.get("url"));
			res.setPath((String) mapRes.get("path"));
			res.setDpi((String) mapRes.get("dpi"));
			res.setType((String) mapRes.get("type"));
		}
		else{
			throw new ExceptionUtil("文件错误");
		}
		this.getBaseDao().insert("resource_add", res);
		outputObject.convertBean2Map(res);
		
	} 

	}

