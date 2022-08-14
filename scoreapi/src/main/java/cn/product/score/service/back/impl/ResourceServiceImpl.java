package cn.product.score.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.back.ResourceService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl implements ResourceService{

	@Override
	public void insert(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
