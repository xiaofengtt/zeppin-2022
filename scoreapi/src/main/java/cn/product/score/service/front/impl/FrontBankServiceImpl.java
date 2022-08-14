package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontBankService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontBankService")
public class FrontBankServiceImpl extends BaseServiceImpl implements FrontBankService{

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
