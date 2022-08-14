package cn.product.worldmall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.back.MethodService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

@Service("methodService")
public class MethodServiceImpl extends BaseServiceImpl implements MethodService{

	@Override
	public void loadFilterChainDefinitions(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

}
