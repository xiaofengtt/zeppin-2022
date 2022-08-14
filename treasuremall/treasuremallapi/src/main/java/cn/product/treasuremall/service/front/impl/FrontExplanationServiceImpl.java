package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontExplanationService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

@Service("frontExplanationService")
public class FrontExplanationServiceImpl extends BaseServiceImpl implements FrontExplanationService{

	@Override
	public void recharge(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}

	@Override
	public void withdraw(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);			
	}
	
}
