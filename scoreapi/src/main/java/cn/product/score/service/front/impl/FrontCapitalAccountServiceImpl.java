package cn.product.score.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.service.front.FrontCapitalAccountService;
import cn.product.score.service.impl.BaseServiceImpl;

@Service("frontCapitalAccountService")
public class FrontCapitalAccountServiceImpl extends BaseServiceImpl implements FrontCapitalAccountService{

	@Override
	public void platformList(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
