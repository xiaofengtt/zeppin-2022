package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.ResourceService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl implements ResourceService{

	@Override
	public void insert(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
