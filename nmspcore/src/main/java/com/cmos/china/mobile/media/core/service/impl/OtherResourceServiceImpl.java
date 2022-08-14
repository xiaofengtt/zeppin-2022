package com.cmos.china.mobile.media.core.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.cmos.china.mobile.media.core.bean.OtherResource;
import com.cmos.china.mobile.media.core.service.IOtherResourceService;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
public class OtherResourceServiceImpl extends BaseServiceImpl implements IOtherResourceService {
 
	private static Logger logger = LoggerFactory.getServiceLog(OtherResourceServiceImpl.class);
	/**
	 * 上传视频，图片资源
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		OtherResource res = new OtherResource();
		List<Map<String,Object>> list = inputObject.getBeans();
		if(list != null)
		{
			Map<String,Object> map = list.get(0);
			Map<String,Object> mapRes=(Map<String, Object>) map.get("res");			
			res.setId((String) mapRes.get("id"));
			res.setName((String) mapRes.get("name"));
			res.setSize((BigInteger) mapRes.get("size"));
			res.setStatus((String) mapRes.get("status"));
			res.setUrl((String) mapRes.get("url"));
			res.setDpi((String) mapRes.get("dpi"));
			res.setType((String) mapRes.get("type"));
		}
		else{
			logger.error("resourceAdd", "error_access_file"+"普通资源文件写入为空");
			throw new ExceptionUtil("文件错误");
		}
		this.getBaseDao().insert("resource_add", res);
		res.setUrl(Utlity.getFullUrl(res.getUrl()));
		outputObject.convertBean2Map(res);
	}


	@Override
	public void verifyFile(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		String id =  inputObject.getValue("resource");
		OtherResource res = (OtherResource) this.getBaseDao().queryForObject("resource_get", id);
		if(res!=null  && !"".equals(res)){
			outputObject.setBusiCode("0");
			outputObject.convertBean2Map(res);
		}else{
			outputObject.setBusiCode("-9999");
		}
	} 

	}

