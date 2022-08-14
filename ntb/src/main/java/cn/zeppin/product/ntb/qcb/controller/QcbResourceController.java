/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.Resource.ResourceStatus;

/**
 * @author hehe
 *
 * 后台上传文件
 */

@Controller
@RequestMapping(value = "/qcb/resource")
public class QcbResourceController extends BaseController {

	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 添加一个资源
	 * @param name
	 * @param logo
	 * @return
	 */
	@RequestMapping(value="/add", method = RequestMethod.POST) 
	@ResponseBody
	public Result add(HttpServletRequest request) {
		if (request instanceof MultipartHttpServletRequest) {  
			String beanPath = Resource.class.getResource("").getPath();
			String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFiles("file").get(0);  
            if (!file.isEmpty()) { 
            	String uuid = UUID.randomUUID().toString();
	            String[] dir = uuid.split("-");
				String basePath = "/upload/";
				for (String sPath : dir) {
					basePath += sPath + "/";
					File tfFile = new File(serverPath + "/" + basePath);
					if (!tfFile.exists()) {
						tfFile.mkdir();
					}
				}
				Resource res = new Resource();
				res.setUuid(uuid);
				String name = dir[dir.length-1];res.setFilename(name);
				res.setFiletype(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
				if(res.getFiletype().toLowerCase().equals("apk")||res.getFiletype().toLowerCase().equals("ipa")){
					name = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
					res.setFilename(name);
				}
				res.setSize(BigInteger.valueOf(file.getSize()));
				res.setStatus(ResourceStatus.NORMAL);
				res.setUrl(basePath + name + "." + res.getFiletype());
				File file2 = new File(serverPath + res.getUrl());
	            try {
					file.transferTo(file2);
				} catch (IOException e) {
					e.printStackTrace();
					return ResultManager.createFailResult("文件上传失败");
				}
				if(res.getFiletype().toLowerCase().equals("jpg")||res.getFiletype().toLowerCase().equals("jpeg")||res.getFiletype().toLowerCase().equals("png")
						||res.getFiletype().toLowerCase().equals("bmp")||res.getFiletype().toLowerCase().equals("tiff")||res.getFiletype().toLowerCase().equals("gif")
						||res.getFiletype().toLowerCase().equals("psd")){
					res.setType("2");
				}else if(res.getFiletype().toLowerCase().equals("mp4")||res.getFiletype().toLowerCase().equals("rmvb")||res.getFiletype().toLowerCase().equals("avi")||res.getFiletype().toLowerCase().equals("mov")
						||res.getFiletype().toLowerCase().equals("3gp")||res.getFiletype().toLowerCase().equals("flv")||res.getFiletype().toLowerCase().equals("wmv")){
					res.setType("1");
				}else if(res.getFiletype().toLowerCase().equals("doc")||res.getFiletype().toLowerCase().equals("docx")||res.getFiletype().toLowerCase().equals("pdf")){
					res.setType("3");///文本文档
				}else if(res.getFiletype().toLowerCase().equals("xls")||res.getFiletype().toLowerCase().equals("xlsx")){
					res.setType("4");///文本文档
				}else if(res.getFiletype().toLowerCase().equals("apk")||res.getFiletype().toLowerCase().equals("ipa")){
					res.setType("5");///安装包
				}else{
					return ResultManager.createFailResult("文件格式错误");
				}

	            res = resourceService.insert(res);
	            return ResultManager.createDataResult(res, "文件上传成功");
            }
		}
		return ResultManager.createFailResult("文件上传失败");
	}
}
