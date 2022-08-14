/**
 * 
 */
package com.product.worldpay.service.system.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.ResourceDao;
import com.product.worldpay.entity.Resource;
import com.product.worldpay.service.system.SystemResourceService;

@Service("systemResourceService")
public class SystemResourceServiceImpl implements SystemResourceService{
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Resource res = (Resource) paramsMap.get("res");
		this.resourceDao.insert(res);
		
		result.setData(res);
		result.setMessage("file upload successed !");
		result.setStatus(ResultStatusType.SUCCESS);
	}

}
