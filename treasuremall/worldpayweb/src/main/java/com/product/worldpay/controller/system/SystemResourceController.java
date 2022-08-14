/**
 * 
 */
package com.product.worldpay.controller.system;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.controller.base.ResultManager;
import com.product.worldpay.entity.Resource;

/**
 * 上传文件
 */

@Controller
@RequestMapping(value = "/system/resource")
public class SystemResourceController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1158274793369608923L;
	
	@Value("${app.filePath}")
    private String filePath;
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping(value="/add", method = RequestMethod.POST) 
	@ResponseBody
	public Result add(HttpServletRequest request) {
		if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFiles("file").get(0);  
            if (!file.isEmpty()) { 
            	String uuid = UUID.randomUUID().toString();
	            String[] dir = uuid.split("-");
				String basePath = "/upload/";
				for (String sPath : dir) {
					basePath += sPath + "/";
					File tfFile = new File(filePath + "/" + basePath);
					if (!tfFile.exists()) {
						tfFile.mkdir();
					}
				}
				Resource res = new Resource();
				res.setUuid(uuid);
				String name = dir[dir.length-1];
				res.setName(name);
				res.setType(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
				
				res.setSize(BigInteger.valueOf(file.getSize()));
				res.setUrl(basePath + name + "." + res.getType());
				File file2 = new File(filePath + res.getUrl());
	            try {
					file.transferTo(file2);
				} catch (IOException e) {
					e.printStackTrace();
					return ResultManager.createFailResult("file upload failed");
				}
				res.setCreatetime(new Timestamp(System.currentTimeMillis()));
				
				InputParams params = new InputParams("systemResourceService", "add");
				params.addParams("res", null, res);
				return this.execute(params);
            }
		}
		return ResultManager.createFailResult("file upload failed");
	}
}
