package cn.product.treasuremall.service.front.impl;

import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.service.front.FrontCapitalAccountService;
import cn.product.treasuremall.service.impl.BaseServiceImpl;

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

	@Override
	public void accountList(InputParams params, DataResult<Object> result) {
		this.transferScoreWebServiceImpl.execute(params, result);		
	}
	
}
