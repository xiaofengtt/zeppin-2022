package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.ResourceService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl implements ResourceService{

	@Override
	public void insert(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
}
