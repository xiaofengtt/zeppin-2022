package cn.product.treasuremall.service.back.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.back.MethodService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("methodService")
public class MethodServiceImpl extends BaseServiceImpl implements MethodService{

	@Override
	public void loadFilterChainDefinitions(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);
	}

}
