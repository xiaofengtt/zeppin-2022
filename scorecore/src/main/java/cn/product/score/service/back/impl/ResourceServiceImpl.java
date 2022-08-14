package cn.product.score.service.back.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.ResourceDao;
import cn.product.score.entity.Resource;
import cn.product.score.service.back.ResourceService;

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
