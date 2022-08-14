package com.cmos.china.mobile.media.core.service.impl;

import java.util.List;
import java.util.Map;

import com.cmos.china.mobile.media.core.bean.OtherInformation;
import com.cmos.china.mobile.media.core.service.IOtherInformationService;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
public class OtherInformationServiceImpl extends BaseServiceImpl implements IOtherInformationService {
 
	private static Logger logger = LoggerFactory.getServiceLog(OtherInformationServiceImpl.class);
	/**
	 * 上传视频，图片资源
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		OtherInformation information = new OtherInformation();
		List<Map<String,Object>> list = inputObject.getBeans();
		if(list != null)
		{
			Map<String,Object> map = list.get(0);
			Map<String,Object> mapRes=(Map<String, Object>) map.get("res");			
			information.setId((Integer) mapRes.get("id"));
			information.setName((String) mapRes.get("name"));
			information.setStatus((String) mapRes.get("status"));
			information.setPassword((String) mapRes.get("password"));
		}
		else{
			logger.error("otherInformationAdd", "error_access_file"+"普通资源文件写入为空");
			throw new ExceptionUtil("文件错误");
		}
		this.getBaseDao().insert("information_add", information);
		outputObject.convertBean2Map(information);
	}


	@Override
	public void get(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		String id =  inputObject.getValue("resource");
		OtherInformation information = (OtherInformation) this.getBaseDao().queryForObject("information_get", id);
		if(information!=null  && !"".equals(information)){
			outputObject.setBusiCode("0");
			outputObject.convertBean2Map(information);
		}else{
			outputObject.setBusiCode("-9999");
		}
	} 

	}

