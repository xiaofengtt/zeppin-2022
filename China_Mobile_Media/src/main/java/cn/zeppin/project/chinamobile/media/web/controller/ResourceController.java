package cn.zeppin.project.chinamobile.media.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity.GerneralStatusType;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.utility.DeCompressUtil;
import cn.zeppin.project.chinamobile.media.utility.VideoUtlity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;


@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {
	
	private static final long serialVersionUID = 2318623804948146943L;
	
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping(value="/add", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<Entity> add(HttpServletRequest request) throws IOException {
		ActionResult<Entity> result = new ActionResult<Entity>();
		if (request instanceof MultipartHttpServletRequest) {  
			String serverPath = request.getSession().getServletContext().getRealPath("");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFiles("video").get(0);  
            if (!file.isEmpty()) {  
	            String[] dir = UUID.randomUUID().toString().split("-");
				String basePath = "/upload/";
	
				for (String sPath : dir) {
					basePath += sPath + "/";
	
					File tfFile = new File(serverPath + "/" + basePath);
					if (!tfFile.exists()) {
						tfFile.mkdir();
					}
				}
				Resource res = new Resource();
				String name = dir[dir.length-1];res.setFilename(name);
				res.setFiletype(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1));
				res.setSize(BigInteger.valueOf(file.getSize()));
				res.setStatus(GerneralStatusType.NORMAL);
				res.setUrl(basePath + name + "." + res.getFiletype());
				res.setPath(serverPath + basePath + name + "." + res.getFiletype());
				File file2 = new File(serverPath + res.getUrl());
	            file.transferTo(file2);
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
						e.printStackTrace();
						result.setMessage("文件上传失败");
						result.setStatus("error");
						return result;
					}
				}else{
					result.setMessage("文件格式错误");
					result.setStatus("error");
					return result;
				}

	            res = resourceService.insert(res);
	            result.setData(res);
				result.setMessage("文件上传成功");
				result.setStatus("success");
				return result;
            }
		}
		result.setMessage("文件上传失败");
		result.setStatus("error");
		return result;
	}
	
	@RequestMapping(value="/displayAdd", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> processFileUpload(HttpServletRequest request)
			throws ServletException, IOException {
		ActionResult<String> result = new ActionResult<String>();
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFiles("display").get(0);  
            if (!file.isEmpty()) {  
            	String uploadpath = "upload";// 文件上传地址
    			@SuppressWarnings("unused")
    			String unpackpath = "";
    			long maxSize = 100 * 1024 * 1024;// 最大文件大小100M
    			String limitfile = "zip,rar";// 限制上传类型doc,docx,xls,xlsx,ppt,htm,html,txt,
    			String serverPath = request.getSession().getServletContext().getRealPath("");
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
					String fileName = file.getOriginalFilename();
					long fileSize = file.getSize();
						// 检查文件大小
						if (fileSize > maxSize) {
							result.setMessage("文件内容超过最大限制!");
							result.setStatus("error");
							return result;
						}
						// 检查扩展名
						String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						if (limitfile.indexOf(fileExt) < 0) {
							result.setMessage("上传文件扩展名是不允许的扩展名。只允许" + limitfile + "格式。!");
							result.setStatus("error");
							return result;
						}
						String newFileName = System.currentTimeMillis() + "_"
								+ new Random().nextInt(1000) + "." + fileExt;
						File uploadedFile = new File(serverPath + basePath, newFileName);
						try {
							file.transferTo(uploadedFile);
						} catch (Exception e) {
							e.printStackTrace();
							result.setMessage("上传失败");
							result.setStatus("error");
							return result;
						}
						
						try {
							List<File> files = DeCompressUtil.deCompress(uploadedFile.getPath(), serverPath + basePath);
							if(files != null && files.size() > 0){
								
								for(File fi:files){
									Resource res = new Resource();
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
										res = resourceService.insert(res);
									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
										result.setMessage("解压过程异常,上传失败");
										result.setStatus("error");
										return result;
									}
									displays.append (res.getId() + "_" + str[0].split("_")[1] + ",");
								}
								if(displays.length() > 0){
									displays.delete(displays.length()-1, displays.length());
								}
							}else{
								result.setMessage("压缩包内文件格式错误");
								result.setStatus("error");
								return result;
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							result.setMessage("解压过程异常,上传失败");
							result.setStatus("error");
							return result;
						}
//    						}
//    				}
    				result.setData(displays.toString());
    				result.setMessage("文件上传成功");
    				result.setStatus("success");
    				return result;
    			} catch (Exception e) {
    				e.printStackTrace();
    				return result;
    			}
            }else{
            	result.setMessage("未选择文件");
        		result.setStatus("error");
        		return result;
            }
			
		}else{
			result.setMessage("上传失败");
			result.setStatus("error");
			return result;
		}
		
	}
}
