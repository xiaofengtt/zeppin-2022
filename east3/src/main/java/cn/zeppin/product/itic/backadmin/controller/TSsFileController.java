/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.zeppin.product.itic.backadmin.service.api.ITSsFileService;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TSsFile;

/**
 * 上传文件
 */

@Controller
@RequestMapping(value = "/backadmin/file")
public class TSsFileController extends BaseController {
	
	@Autowired
	private ITSsFileService TSsFileService;
	
	/**
	 * 上传文件
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(HttpServletRequest request) {
		if (request instanceof MultipartHttpServletRequest) {  
			String beanPath = TSsFile.class.getResource("").getPath();
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
				TSsFile res = new TSsFile();
				res.setId(uuid);
				String name = dir[dir.length-1];
				res.setFilename(name);
				res.setType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
				res.setUrl(basePath + name + "." + res.getType());
				File file2 = new File(serverPath + res.getUrl());
	            try {
					file.transferTo(file2);
				} catch (IOException e) {
					e.printStackTrace();
					return ResultManager.createFailResult("文件上传失败");
				}

	            TSsFileService.insert(res);
	            return ResultManager.createDataResult(res, "文件上传成功");
            }
		}
		return ResultManager.createFailResult("文件上传失败");
	}
}
