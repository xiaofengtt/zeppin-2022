package cn.product.worldmall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.front.FrontExplanationService;
import cn.product.worldmall.service.impl.BaseServiceImpl;

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
