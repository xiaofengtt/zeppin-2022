package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontUserRechargeService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontUserRechargeService")
public class FrontUserRechargeServiceImpl extends BaseServiceImpl implements FrontUserRechargeService{

	@Override
	public void byBankcard(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void byAlipay(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

}
