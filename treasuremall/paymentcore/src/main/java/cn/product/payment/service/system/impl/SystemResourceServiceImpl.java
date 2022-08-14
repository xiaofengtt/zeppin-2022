/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.ResourceDao;
import cn.product.payment.entity.Resource;
import cn.product.payment.service.system.SystemResourceService;

/**
 * 资源文件
 */
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
		result.setMessage("文件上传成功");
		result.setStatus(ResultStatusType.SUCCESS);
	}

}
