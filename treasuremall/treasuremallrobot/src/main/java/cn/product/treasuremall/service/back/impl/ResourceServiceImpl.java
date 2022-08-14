package cn.product.treasuremall.service.back.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.ResourceService;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

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
