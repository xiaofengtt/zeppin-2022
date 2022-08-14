package cn.product.worldmall.service.support.impl;

import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.service.impl.BaseServiceImpl;
import cn.product.worldmall.service.support.SupportStripeService;


@Service("supportStripeService")
public class SupportStripeServiceImpl extends BaseServiceImpl implements SupportStripeService{

	@Override
	public void load(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void checkout(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
