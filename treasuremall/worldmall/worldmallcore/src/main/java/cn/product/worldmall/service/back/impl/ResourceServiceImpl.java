package cn.product.worldmall.service.back.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.service.back.ResourceService;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService{
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void insert(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Resource res = (Resource) paramsMap.get("res");
		this.resourceDao.insert(res);
		result.setData(res);
		result.setMessage("文件上传成功");
		result.setStatus(ResultStatusType.SUCCESS);
	}

}
